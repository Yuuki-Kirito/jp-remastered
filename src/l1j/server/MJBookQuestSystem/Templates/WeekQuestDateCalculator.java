package l1j.server.MJBookQuestSystem.Templates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.MJBookQuestSystem.Loader.WeekQuestLoader;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.SQLUtil;

/** The class responsible for the weekly quest time **/
public class WeekQuestDateCalculator {
	private static Logger _log = Logger.getLogger(WeekQuestDateCalculator.class.getName());
	
	private static WeekUpdator _updator = null;
	private static WeekQuestDateCalculator _instance;
	public static WeekQuestDateCalculator getInstance(){
		if(_instance == null)
			_instance = new WeekQuestDateCalculator();
		return _instance;
	}
	
	private Timestamp			_updateStamp;
	private ScheduledFuture<?> 	_future;
	private void setLastTime(){
		Connection con							= null;
		PreparedStatement pstm 					= null;
		try {
			con 	= L1DatabaseFactory.getInstance().getConnection();
			pstm 	= con.prepareStatement("insert into tb_weekquest_updateInfo set id=1, lastTime=? on duplicate key update lastTime=?");
			pstm.setTimestamp(1, _updateStamp);
			pstm.setTimestamp(2, _updateStamp);
			pstm.executeUpdate();
		}catch (Exception e){
			StringBuilder sb = new StringBuilder();
			sb.append("[ERROR - WeekQuestDateCalculator] setLastTime()...").append(_updateStamp).append(" write error. \r\n").append(e.getLocalizedMessage());
			_log.log(Level.SEVERE, sb.toString(), e);
			System.out.println(sb.toString());
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	private Date getLastTime(){
		Connection con						= null;
		PreparedStatement pstm 				= null;
		ResultSet rs 						= null;
		String column						= "";
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select * from tb_weekquest_updateInfo");
			rs = pstm.executeQuery();
			if(rs.next()){
				Timestamp ts = rs.getTimestamp("lastTime");
				return new Date(ts.getTime());
			}
		}catch (Exception e){
			StringBuilder sb = new StringBuilder();
			sb.append("[ERROR - WeekQuestDateCalculator] getLastTime()...").append(column).append(" read error. \r\n").append(e.getLocalizedMessage());
			_log.log(Level.SEVERE, sb.toString(), e);
			System.out.println(sb.toString());
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return null;
	}
	
	public void run(){
		long sleepTime = 0;
		long nowMillis = System.currentTimeMillis();
		
		// 以前の更新情報を取得します。
		Date oldDate = getLastTime();
		
		// 以前の更新情報がありません。
		if(oldDate == null){												
			
			// 現在にアップデートをする。
			setUpdate(nowMillis);
		}else{
			// 前の更新情報から次の更新情報を呼び出す。
			Calendar cal = getNextWeekCalendar(oldDate.getTime());
			
			// 現在の時間が次の更新時間よりも大きい場合は更新されませんでした。
			if(nowMillis >= cal.getTimeInMillis()){ 	

				// 現在の更新時間で更新する。
				setUpdate(nowMillis);
		
				
			// まだ更新する時ではありません。
			}else{									
				// 前回の更新情報をマップします。
				_updateStamp = new Timestamp(oldDate.getTime());
				
				// 次の更新までの時間を見つけて休む
				sleepTime = cal.getTimeInMillis() - nowMillis;
				
				// 休む。
				setNextUpdate(sleepTime);
			}
		}
	}
	
	private WeekQuestDateCalculator(){
		
	}
	
	/** Returns the required number of days from the current calendar to the next week. **/
	private int getDayToNextWeek(Calendar cal){
		int week 		= cal.get(Calendar.DAY_OF_WEEK);// 曜日を取得する
		int nextWeek 	= 0;
		if(Config.WQ_UPDATE_TYPE == 0)	// 日型の場合は1日が返却されます。
			nextWeek = 1;
		else if(week >= Config.WQ_UPDATE_WEEK)
			nextWeek = (Config.WQ_UPDATE_WEEK + 7) - week;
		else
			nextWeek = Config.WQ_UPDATE_WEEK - week;
		return nextWeek;
	}
	
	/** Returns today's calendar next week from the given time. **/
	private Calendar getNextWeekCalendar(long sysmillis){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(new Date(sysmillis));
		
		Calendar nextCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		nextCal.setTime(cal.getTime());
		nextCal.add(Calendar.DATE, getDayToNextWeek(cal));
		nextCal.set(Calendar.HOUR_OF_DAY, Config.WQ_UPDATE_TIME);
		nextCal.set(Calendar.MINUTE, 0);
		nextCal.set(Calendar.SECOND, 0);
		return nextCal;
	}
	
	/** Returns whether the weekly quest has been updated. **/
	public boolean isUpdateWeekQuest(Timestamp ts){
		if(ts == null)
			return true;
		
		// 時折、タイムゾーンの設定により小数点以下の単位msが間違って更新できない場合があります.1秒以内の誤差は全て同じです。
		long time = Math.abs(ts.getTime() - _updateStamp.getTime());
		if(time <= 1000)
			return false;
		return true;
	}
	
	public Timestamp getUpdateStamp(){
		return _updateStamp;
	}
	
	/** Update from the current time. **/
	public void setUpdate(){
		setUpdate(System.currentTimeMillis());
	}
	
	/** Update from the given time. **/
	public void setUpdate(long nowMillis){
		_updateStamp = new Timestamp(nowMillis);	// 現在時刻を指定し、
		setLastTime();								// DBに保存します。
		// 来週まで休む。
		long sleepTime = getNextWeekCalendar(_updateStamp.getTime()).getTimeInMillis() - _updateStamp.getTime();
		setNextUpdate(sleepTime);		
	}
	
	/** Let the next update run. **/
	private void setNextUpdate(long sleepTime){
		WeekQuestLoader.reload();
		// 何らかの理由でリロードされる可能性がある場合は、一度だけ処理するように、
		if(_updator == null)
			_updator = new WeekUpdator();
		
		_future = GeneralThreadPool.getInstance().schedule(_updator, sleepTime);
	}
	
	public synchronized void reloadTime(){
		if(_future != null){
			_future.cancel(true);
		}
		
		setUpdate(System.currentTimeMillis());
		L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "週間クエストと出席リストが更新されました。"), true);
	}
	
	/** Helpers to help with periodic updates **/
	class WeekUpdator implements Runnable{
		
		@Override
		public void run() {
			try{
				long now = System.currentTimeMillis();
				if(_updateStamp.getTime() > now){
					GeneralThreadPool.getInstance().schedule(this, _updateStamp.getTime() - now);
					return;
				}
				String s = "Week Quest Updates now time is " + now;
				System.out.println(s);
				_log.log(Level.SEVERE, s);
				
				WeekQuestDateCalculator.getInstance().setUpdate(now);
				Thread.sleep(500);
				L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "週間クエストが更新されました。 キャラクターを再起動すると、新しいクエストのリストを受け取ることができます。"), true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
