package l1j.server.MJBookQuestSystem;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import l1j.server.MJBookQuestSystem.Compensator.WeekQuestCompensator;
import l1j.server.MJBookQuestSystem.Loader.MonsterBookCompensateLoader;
import l1j.server.MJBookQuestSystem.Loader.WeekQuestLoader;
import l1j.server.MJBookQuestSystem.Templates.UserWeekQuestProgress;
import l1j.server.MJBookQuestSystem.Templates.WeekQuestDateCalculator;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_WeekQuest;
import l1j.server.server.utils.MJBytesOutputStream;

public class UserWeekQuest {
	private static final S_SystemMessage _updateMessage = new S_SystemMessage("週間クエストが更新されました。 再接続して恩恵を受けてください。");
	
	private L1PcInstance 				_owner;
	private UserWeekQuestProgress[][] 	_wq;
	private Object						_lock;
	
	public UserWeekQuest(L1PcInstance pc){
		_owner 	= pc;
		_wq 	= new UserWeekQuestProgress[][]{
				{null, null, null},
				{null, null, null},
				{null, null, null},
		};
		
		_lock = new Object();
	}
	
	/** Map weekly quest information. **/
	public void setWeekQuestInformation(ResultSet rs) throws Exception{
		int bookId; 
		int difficulty; 
		int section; 
		int step;
		Timestamp stamp;
		boolean isCompleted;
		
		// First, scrape the db information.
		while(rs.next()){
			bookId 		= rs.getInt("bookId"); 
			difficulty 	= rs.getInt("difficulty"); 
			section		= rs.getInt("section"); 
			step		= rs.getInt("step");
			stamp		= rs.getTimestamp("stamp");
			isCompleted = rs.getBoolean("completed");
			
			_wq[difficulty - 1][section] = new UserWeekQuestProgress(bookId, difficulty, section, step, stamp, isCompleted);
		}
		
		MonsterBook book					= null;
		UserWeekQuestProgress progress 		= null;
		WeekQuestDateCalculator wqcal 		= WeekQuestDateCalculator.getInstance();
		WeekQuestLoader wqLoader			= WeekQuestLoader.getInstance();
		
		// Inspect the loaded monster book.
		for(int i=0; i<3; i++){			
			// If there is no loaded information, it allocates a new one.
			if(_wq[i][0] == null){
				for(int j=0; j<3; j++)
					_wq[i][j] = new UserWeekQuestProgress(0, 0, 0, 0, null, false);
			}
			
			// Based on number 0 for each difficulty level, if an update is needed, update the weekly quest.
			if(wqcal.isUpdateWeekQuest(_wq[i][0].getStamp())){
				for(int j=0; j<3; j++){
					book = wqLoader.getBook(i, j);
					// Update new book information.
					_wq[i][j].setBookId(book.getBookId());
					_wq[i][j].setDifficulty(i + 1);
					_wq[i][j].setSection(j);
					_wq[i][j].setStamp(wqcal.getUpdateStamp());
					_wq[i][j].setStep(0);				
					_wq[i][j].setCompleted(false);
				}
			}
		}
	}
	
	public byte[] getSerialize() throws Exception{
		MJBytesOutputStream mbos = new MJBytesOutputStream();
		byte[] section1 = null;
		byte[] section2 = null;
		byte[] section3 = null;
		int successfully = 0;
		
		mbos.write(0x20);
		mbos.write(0x37);
		for(int i=0; i<3; i++){
			successfully 	= 1;
			section1 		= _wq[i][0].getSerialize();
			section2 		= _wq[i][1].getSerialize();
			section3 		= _wq[i][2].getSerialize();
			
			mbos.write(0x12);
			mbos.write(section1.length + section2.length + section3.length + 4);
			mbos.write(0x08);
			mbos.write(i);
			mbos.write(0x18);
			
			// If the line is clear, the reward button is activated
			if(isLineClear(i)){
				successfully = 3;
				
				// If you have already received a reward, the line is cleared,
				if(isLineCompleted(i))
					successfully = 5;
			}
			mbos.write(successfully);
			mbos.write(section1);
			mbos.write(section2);
			mbos.write(section3);
		}
		
		return mbos.toArray();
	}
	
	/** true if there are multiple book ids for each section, false otherwise. **/
	private boolean checkDuplicateBookId(MonsterBook book, int difficulty, int section) throws Exception{
		if(difficulty < 0 || difficulty > 2)
			throw new Exception("invalid difficulty " + difficulty);
		
		for(int i=section - 1; i >= 0; i--){
			if(_wq[difficulty][i].getBookId() == book.getBookId())
				return true;
		}
		return false;
	}
	
	/** Returns a list of main quests by difficulty. **/
	public UserWeekQuestProgress[] getProgressList(int difficulty){
		UserWeekQuestProgress[] progresses = new UserWeekQuestProgress[3];
		progresses[0] = _wq[difficulty][0];
		progresses[1] = _wq[difficulty][1];
		progresses[2] = _wq[difficulty][2];
		
		return progresses;
	}
	
	/** Returns a list of all main quests.**/
	public ArrayList<UserWeekQuestProgress> getProgressList(){
		ArrayList<UserWeekQuestProgress> list 	= new ArrayList<UserWeekQuestProgress>(9);
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++)
				list.add(_wq[i][j]);
		}
		
		return list;
	}
	
	/** Send the main quest list. **/
	public void sendList(){
		// ジュクエリスト転送
		S_WeekQuest wq = new S_WeekQuest();
		wq.writeWQList(_owner);
		_owner.sendPackets(wq);
	}
	
	/** Respond to teleport requests. **/
	public void teleport(int difficulty, int section){
		if(difficulty < 0 || difficulty > 2)
			return;
		
		_owner.getMonsterBook().teleport(_wq[difficulty][section].getBookId());
	}
	
	/** hunted monsters. **/
	public void addMonster(MonsterBook book){
		if(book == null)
			return;
		
		UserWeekQuestProgress progress 	= null;
		int difficulty					= book.getWeekDifficulty();
		if(difficulty < 0 || difficulty > 2)
			return;
		
		if(WeekQuestDateCalculator.getInstance().isUpdateWeekQuest(_wq[difficulty][0].getStamp())){
			_owner.sendPackets(_updateMessage, false);
			return;
		}
		
		// If the line has already been cleared or a reward has been received, return,
		if(isLineClear(difficulty) || isLineCompleted(difficulty))
			return;
		
		for(int i=0; i<3; i++){
			progress = _wq[difficulty][i];
			if(progress.getBookId() != book.getBookId())
				continue;
			
			synchronized(_lock){
				progress.addStep(1);
				
				// 更新されたクエスト状態を送信する。
				S_WeekQuest wq = new S_WeekQuest();
				wq.writeWQUpdate(difficulty, i, progress.getStep());
				_owner.sendPackets(wq);
				
				// クエスト完了したら、完了メッセージを送信する。
				if(isLineClear(difficulty)){
					wq = new S_WeekQuest();
					wq.writeWQLineClear(difficulty, 3);
					_owner.sendPackets(wq);			
				}
				return;
			}
		}
	}
	
	/** execute compensation. **/
	public void complete(int difficulty, int section){
		synchronized(_lock){
			UserWeekQuestProgress progress 	= null; 
			
			if(difficulty < 0 || difficulty > 2)
				return;
			
			if(WeekQuestDateCalculator.getInstance().isUpdateWeekQuest(_wq[difficulty][0].getStamp())){
				_owner.sendPackets(_updateMessage, false);
				return;
			}
			
			// If you do not clear the line or receive a line reward, it will not be processed.
			if(!isLineClear(difficulty) || isLineCompleted(difficulty)){
				StringBuilder sb = new StringBuilder(128);
				sb.append("すでに週間クエスト報酬を受け取ったユーザー： ").append(_owner.getName()).append("が報酬を試みます。");
				System.out.println(sb.toString());
				return;
			}
			_wq[difficulty][0].setCompleted(true);
			_wq[difficulty][1].setCompleted(true);
			_wq[difficulty][2].setCompleted(true);
			S_WeekQuest wq = new S_WeekQuest();
			wq.writeWQLineClear(difficulty, 5);
			_owner.sendPackets(wq);
			
			WeekQuestCompensator compensator = MonsterBookCompensateLoader.getInstance().getWeekCompensator(section);
			compensator.compensate(_owner);
		}
	}
	
	/** Are all lines cleared? **/
	public boolean isLineClear(int difficulty){
		if(_wq[difficulty][0].isClear() && _wq[difficulty][1].isClear() && _wq[difficulty][2].isClear())
			return true;
		return false;
	}
	
	/** Did you receive line rewards? **/
	public boolean isLineCompleted(int difficulty){
		if(_wq[difficulty][0].isCompleted() || _wq[difficulty][1].isCompleted() || _wq[difficulty][2].isCompleted())
			return true;
		return false;		
	}
}
