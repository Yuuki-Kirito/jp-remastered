package l1j.server.GameSystem.DogFight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.DogFightRecordTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1RaceTicket;
import l1j.server.server.utils.SQLUtil;

public class DogFight {
	
	private static DogFight _instance;
	
	private final ArrayList<L1NpcInstance> _NpcChant = new ArrayList<L1NpcInstance>();
	
	private DecimalFormat BettingPrice = new DecimalFormat("#,###");

	public static DogFight getInstance() {
		if (_instance == null) {
			_instance = new DogFight();
		}
		return _instance;
	}

	/** ドッグファイトの基本起動 */
	private DogFight() {
		/** 投影エンピーさんまとめ */
		for (L1Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) obj;
				if (npc.getNpcTemplate().get_npcId() == 72320) {
					_NpcChant.add(npc);
				}
			}
		}
		/** 投影情報のリロード */
		race_loading();
		/** ゲームスタート情報 */
		GameStart();
	}
	
	public boolean _Win;
	
	public void GameStart() {
		try {
			clearBug();
			setRoundId(ObjectIdFactory.getInstance().nextId());
			setRound(Race_Counter() + 1);
			clearBetting();
			_Win = false;
			GeneralThreadPool.getInstance().schedule(new Startbug(), 10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	private static int StartTime = 2;
	
	/** ドッグマッチスタート */
	class Startbug implements Runnable {
		public Startbug() {}

		@Override
		public void run() {
			try {
				storeBug();
				setBugRaceStatus(STATUS_READY);
				GeneralThreadPool.getInstance().execute(new L1ReadyThread());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/** スレッド形式でメッセージを出力する */
	class L1ReadyThread implements Runnable {
		@Override
		public void run() {
			try {
				buyTickets = true;
				broadCastTime("私 " + getRound() + "会闘犬トランペットの販売が始まりました。");
				try {
					for (int time = StartTime; time > 0; time--) {
						broadCastTime("投影開始 " + time + "$377");
						if(time == 1){
							Thread.sleep(50000);
						}else Thread.sleep(60000);
					}
				} catch (Exception e) {}
				buyTickets = false;
				broadCastTime("投影ナバルの販売が終了しました。");
				GeneralThreadPool.getInstance().execute(new L1BroadCastDividend());
			} catch (Exception e) {}
		}
	}
	
	class L1BroadCastDividend implements Runnable {
		private L1NpcInstance _Npc;

		public L1BroadCastDividend() {
			for (L1NpcInstance Npc : _NpcChant) {
				_Npc = Npc;
			}
		}

		@Override
		public void run() {
			try {
				L1PetInstance DogChat = null;
				for (L1PetInstance bug : _littleBug) {
					if (!bug.isDogJudge()) continue;
					DogChat = bug;
				}
				for (int i = 10; i > 0; i--) {
					Broadcaster.wideBroadcastPacket(DogChat, new S_NpcChatPacket(DogChat, "スタート "+i+"初戦", 2), true);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {}
				}
				Broadcaster.wideBroadcastPacket(DogChat, new S_NpcChatPacket(DogChat,"$364", 2), true);
				startRace();
				
				Thread.sleep(1000);
				NumberFormat.getInstance().setMaximumFractionDigits(1);
				NumberFormat.getInstance().setMinimumFractionDigits(1);
				for (L1PetInstance bug : _littleBug) {
					if(bug.isDogJudge()) continue; 
					String Chat = bug.getName() + " $402 "+ NumberFormat.getInstance().format(bug.getDividend());
					Broadcaster.wideBroadcastPacket(_Npc, new S_NpcChatPacket(_Npc, Chat, 2), true);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/** ドッグ情報のリセット */
	private void clearBug() {
		try {
			ArrayList<L1PetInstance> clonn = new ArrayList<L1PetInstance>();
			clonn.addAll(_littleBug);
			GeneralThreadPool.getInstance().schedule(new deleteBug(clonn),10000);
			_littleBug.clear();
			for (int i = 0; i < 2; i++) {
				bugStat[i] = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class deleteBug implements Runnable {
		ArrayList<L1PetInstance> clonn;

		public deleteBug(ArrayList<L1PetInstance> ll) {
			clonn = ll;
		}

		@Override
		public void run() {
			for (L1PetInstance bug : clonn) {
				bug.deleteMe();
			}
		}
	}
	
	public final ArrayList<L1PetInstance> _littleBug = new ArrayList<L1PetInstance>();
	private final ArrayList<L1RaceTicket> _ticketPrice = new ArrayList<L1RaceTicket>();
	
	public static final int STATUS_NONE = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_PLAYING = 2;
	
	public boolean buyTickets = true;

	private static final int[] startX = { 33528, 33528, 33531 };
	private static final int[] startY = { 32862, 32867, 32861 };

	private static int[] bugStat = new int[3];

	private static final Random _Random = new Random();

	public int[] _betting = new int[3];

	private int _round;

	private int _roundId;

	private int _bugRaceStatus;

	private static final int[] NPcid = { 72400, 72401, 72402, 72403, 72406, 72407, 72408, 72409, 
		72410, 72411, 72412, 72415, 72416, 72417, 72418, 72419, 72422, 72423,
		72424, 72425, 72426};
	
	private void storeBug() {
		int arr[] = new int[3];
		for (int i = 0; i < 3; i++) {
			arr[i] = _Random.nextInt(21);
			for (int j = 0; j < i; j++) {
				if (arr[i] == arr[j]) {
					arr[i] = _Random.nextInt(21);
					i = i - 1;
					break;
				}
			}
		}
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(1);
		for (int i = 0; i < 3; i++) {
			L1Npc npcTemp = NpcTable.getInstance().getTemplate(NPcid[arr[i]]);
			L1PetInstance bug = new L1PetInstance(npcTemp, arr[i] ,startX[i], startY[i], i, i == 2 ? true : false);
			Broadcaster.broadcastPacket(bug, new S_SkillSound(bug.getId(), 5935), true);
			DogFightRecordTable.getInstance().getDogFightRecord(arr[i], bug);
			float winpoint = 0;
			float record = bug.getWin() + bug.getLose();
			if (record == 0) {
				winpoint = 0;
			} else {
				winpoint = bug.getWin() / record * 100;
			}
			bug.setWinPoint(nf.format(winpoint));
			_littleBug.add(bug);
		}
	}

	private void AttackSpeed() {
		for (L1PetInstance bug : _littleBug) {
			int pulsAtkspeed = bug.getAtkspeed();
			int condition = bug.getCondition();
			if (condition == L1PetInstance.GOOD) {
				pulsAtkspeed -= bug.getAtkspeed() * 0.025;
			} else if (condition == L1PetInstance.NORMAL) {
				pulsAtkspeed -= bug.getAtkspeed() * 0.02;
			} else if (condition == L1PetInstance.BAD) {
				pulsAtkspeed -= bug.getAtkspeed() * 0.01;
			}
			bug.setAtkspeed(pulsAtkspeed);
		}
	}
	
	/** 倍率調整倍率は固定1.95 */
	private void calcDividend() {
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if (pc == null || pc.getNetConnection() == null || !pc.isGm()) continue;
			int i = 0;
			pc.sendPackets(new S_SystemMessage("\\aLチケット情報 : 私 " + getRound()+ " 投影チケット"), true);
			for (L1PetInstance Dog : _littleBug) {
				if(i == 2) continue; 
				else pc.sendPackets(new S_SystemMessage("\\aL"+Dog.getName()+" : "+ BettingPrice.format(_betting[i]) + "章 "), true);
				i++;
			}
		}
		/** ドッグマッチ操作情報チェック */
		if(AutoDogFight){
			ArrayList<Integer> list = new ArrayList<Integer>();
			int i = 0;
			for (int b : _betting) {
				if(i == 2) continue; 
				list.add(b);
				i++;
			}
			int MinCon = -1;
			Integer Min = Collections.min(list);
			for (L1PetInstance Dog : _littleBug) {
				MinCon++;
				if(MinCon == 2) continue; 
				if(_betting[0] == _betting[1]) continue; 
				if(Min == _betting[MinCon]) {
					Dog.setFabrication(true);
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						if (pc == null || pc.getNetConnection() == null || !pc.isGm()) continue;
						pc.sendPackets(new S_SystemMessage("\\aL自動操作ジョブ情報"), true);
						pc.sendPackets(new S_SystemMessage("\\aL"+Dog.getName()+" : "+ BettingPrice.format(_betting[MinCon]) + "章 "), true);
						pc.sendPackets(new S_SystemMessage("\\aL"+Dog.getName()+" : クリティカル確率上昇"), true);
					}
				}
			}
			list.clear();
		}
		
		float[] dividend = new float[3];
		L1PetInstance[] bugs = getBugsArray();
		for (int i = 0; i < dividend.length; i++) {
			if(i == 2) continue; 
			else dividend[i] = (float)1.95;
			bugs[i].setDividend(dividend[i]);
		}
	}

	public void startRace() {
		setBugRaceStatus(STATUS_PLAYING);
		buyTickets = false;
		calcDividend();
		AttackSpeed();
		
		int i = 0;
		for (L1PetInstance bug : _littleBug) {
			if (bug.isDogJudge()) continue;
			GeneralThreadPool.getInstance().execute(new L1BugBearRacing(bug, i++));
		}
	}

	private void broadCastTime(String chat) {
		for (L1NpcInstance npc : _NpcChant) {
			Broadcaster.wideBroadcastPacket(npc, new S_NpcChatPacket(npc, chat,2), true);
		}
	}

	private void broadCastWinner(String winner) {
		String chat = "私 " + getRound() + "会闘犬の勝者は " + " '" + winner + "' "+ "$367";
		for (L1PetInstance bug : _littleBug) {
			if (!bug.isDogJudge()) continue;
			Broadcaster.wideBroadcastPacket(bug, new S_NpcChatPacket(bug, chat,2), true);
		}
	}

	public int bugRound() {
		return getRound();
	}

	public String getTicketName(int i) {
		int[] getBugCount = getDogCount(i);
		L1PetInstance bug = _littleBug.get(getBugCount[0]);
		StringBuilder BugName = new StringBuilder().append(getRound()).append("-").append(bug.getNumber() + 1).append(" ").append(bug.getName());
		if(getBugCount[1] > 1) BugName.append("(").append(getBugCount[1]).append("장)").toString();
		return BugName.toString();
	}

	public int[] getTicketInfo(int i) {
		int[] getBugCount = getDogCount(i);
		return new int[] { getRoundId(), getRound(),_littleBug.get(getBugCount[0]).getNumber(), getBugCount[1]};
	}

	public String getTicketBugName(int i) {
		for (int ai = 0; ai < 3; ai++) {
			L1PetInstance bug = _littleBug.get(ai);
			if (bug.getNumber() == i) return bug.getName();
		}
		return null;
	}
	
	public boolean getDogFight(L1PetInstance Pet) {
		for (L1PetInstance bug : _littleBug) {
			if (Pet.getId() == bug.getId()){
				return true;
			}
		}
		return false;
	}

	public double getTicketPrice(L1ItemInstance item) {
		for (L1RaceTicket ticket : _ticketPrice) {
			if (ticket.getRoundId() == item.getSecondId() && ticket.getWinner() == item.getTicketId()) {
				return ticket.getDividend();
			}
		}
		return 0;
	}

	public String[] makeStatusString() {
		ArrayList<String> status = new ArrayList<String>();
		for (L1PetInstance bug : _littleBug) {
			if(bug.isDogJudge()) continue;
			status.add(bug.getName());
			if (bug.getCondition() == L1PetInstance.GOOD) {
				status.add("差分");// $368
			} else if (bug.getCondition() == L1PetInstance.NORMAL) {
				status.add("緊張");// $369
			} else if (bug.getCondition() == L1PetInstance.BAD) {
				status.add("興奮");// $370
			}
			status.add(bug.getWinPoint() + "%");
		}
		return status.toArray(new String[status.size()]);
	}

	private synchronized void finish(L1PetInstance bug) {
		try {
			if (!_Win && getBugRaceStatus() == STATUS_PLAYING) {
				_Win = true;
				/** すべてのペットaiを止める */
				for (L1PetInstance b : _littleBug) b.stopAI();
				/** 10秒スリープ状態 */
				Thread.sleep(1000L);
				int PetAction = 67;
				Broadcaster.broadcastPacket(bug, new S_DoActionGFX(bug.getId(), PetAction), true);
				int PetLevel = (bug.getLevel() - 41) / 10;
				Broadcaster.broadcastPacket(bug, new S_SkillSound(bug.getId(), bug.getPetType().getPetEffect() + (PetLevel >= 3 ? 3 : PetLevel)), true);
				byte i = 0;
				for (L1PetInstance b : _littleBug) {
					if (b == bug) break;
					i++;
				}
				int allBetting = 0;
				for (int b : _betting) allBetting += b;
				allBetting = allBetting * 500;
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (pc == null || pc.getNetConnection() == null || !pc.isGm()) continue;
					/** 結果値を計算するときに書くもの */
					pc.sendPackets(new S_SystemMessage("\\aL私 " + getRound()+ " 投影優勝"), true);
					pc.sendPackets(new S_SystemMessage("\\aL 総チケット : " + BettingPrice.format(allBetting)));
					pc.sendPackets(new S_SystemMessage("\\aL 優勝チケット金額 : " + BettingPrice.format(_betting[i] * bug.getDividend() * 500)));
					pc.sendPackets(new S_SystemMessage("\\aL 結果チケット差額 : " + BettingPrice.format(allBetting - (_betting[i] * bug.getDividend() * 500))));
					pc.sendPackets(new S_SystemMessage("\\aL チケット情報：総チケット購入枚数を一覧表示"), true);
					int m = 0;
					for (L1PetInstance Dog : _littleBug) {
						if(m == 2) continue; 
						else pc.sendPackets(new S_SystemMessage("\\aL "+Dog.getName()+" : "+ BettingPrice.format(_betting[m]) + "장 "), true);
						m++;
					}
				}

				race_difference((int) allBetting,(int) (_betting[i] * bug.getDividend() * 500));
				L1RaceTicket ticket = new L1RaceTicket(getRoundId(), bug.getNumber(), bug.getDividend());
				_ticketPrice.add(ticket);
				race_divAdd(getRoundId(), bug.getNumber(), bug.getDividend());
				broadCastWinner(bug.getNameId());
				setBugRaceStatus(STATUS_NONE);
				DogFightRecordTable.getInstance().updateDogFightRecord(bug.getNumber(), bug.getWin() + 1, bug.getLose());
				/** 整理してから1分後に再起動 */
				GeneralThreadPool.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						GameStart();
					}
				}, 60 * 1000); //投影時間
			} else {
				DogFightRecordTable.getInstance().updateDogFightRecord(bug.getNumber(),bug.getWin(), bug.getLose() + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public L1PetInstance[] getBugsArray() {
		return _littleBug.toArray(new L1PetInstance[_littleBug.size()]);
	}

	public synchronized void addBetting(int num, int count) {
		int[] getBugCount = getDogCount(num);
		if (getBugRaceStatus() == STATUS_READY) {
			_betting[getBugCount[0]] += count;
		}
	}

	private void clearBetting() {
		_betting = new int[3];
	}

	public void setRound(int i) {
		_round = i;
	}

	public int getRound() {
		return _round;
	}

	public void setRoundId(int i) {
		_roundId = i;
	}

	public int getRoundId() {
		return _roundId;
	}

	private void setBugRaceStatus(int i) {
		_bugRaceStatus = i;
	}

	public int getBugRaceStatus() {
		return _bugRaceStatus;
	}
	
	class L1BugBearRacing implements Runnable {
		private L1PetInstance _Dog;

		public L1BugBearRacing(L1PetInstance Dog, int Num) {
			_Dog = Dog;
		}

		@Override
		public void run() {
			try {
				if (_Dog._destroyed) return;
				if (_Dog.getTarget() != null &&
					_Dog.getTarget().isDead()) {
					finish(_Dog);
					return;
				}else if(_Win){
					if(!_Dog.isDogJudge())
						finish(_Dog);
					return;
				}
				if (_Dog.getTarget() == null){
					for (L1PetInstance bug : _littleBug) {
						if(bug.getId() != _Dog.getId()){
							if(!bug.isDogJudge()){
								_Dog.setTarget(bug);
								_Dog.onNpcAI();
							}
						}
					}
				}
				int Hp = (int) Math.round((double) _Dog.getCurrentHp() / (double) _Dog.getMaxHp() * 100);
				if(!_Dog.isDead() && Hp < 50 && !_Dog.SkillCheck(L1SkillId.Fighting)){
					_Dog.getSkillEffectTimerSet().setSkillEffect(L1SkillId.Fighting, 60 * 1000);
				}
				GeneralThreadPool.getInstance().schedule(this, 100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int[] getDogCount(int i) {
		int Bug = i, Count = 1;
		if(Bug >= 2 && Bug <= 3){
			Bug -= 2;
			Count = 30000;
		}else if(Bug >= 4 && Bug <= 5){
			Bug -= 4;
			Count = 60000;
		}else if(Bug >= 6 && Bug <= 7){
			Bug -= 6;
			Count = 120000;
		}
		return new int[] { Bug, Count };
	}
	
	private static int Race_Counter() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM racedog_difference");
			rs = pstm.executeQuery();
			int Round = 0;
			while (rs.next()) {
				Round++;
			}
			return Round;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return 0;
	}

	private void race_difference(int b, int s) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO racedog_difference SET date=?, Round=?, buy=?, winner_sell=?, difference=?");
			pstm.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstm.setInt(2, getRound());
			pstm.setInt(3, b);
			pstm.setInt(4, s);
			pstm.setInt(5, b - s);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	public void race_divAdd(int id, int b, double d) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO racedog_div_record SET id=?, bug_number=?, dividend=?");
			pstm.setInt(1, id);
			pstm.setInt(2, b);
			pstm.setInt(3, (int) (d * 1000));
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void race_loading() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM racedog_div_record");
			rs = pstm.executeQuery();
			while (rs.next()) {
				L1RaceTicket ticket = new L1RaceTicket(rs.getInt("id"),rs.getInt("bug_number"),(double) (rs.getInt("dividend")) / 1000);
				_ticketPrice.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	public static boolean AutoDogFight = false;
	
	/** 操作用レース バリア名チェック */
	public void aTotalFabrication(L1PcInstance Gm, String DogName) {
		if(getBugRaceStatus() == STATUS_NONE){
			Gm.sendPackets(new S_SystemMessage("\\aLお知らせ：現在、試合準備中なので操作はできません。"), true);
			return;
		}
		int i = 0;
		if(DogName == null){
			Gm.sendPackets(new S_SystemMessage("\\aLチケット情報：総チケット購入ゲット数が表示されます"), true);
			for (L1PetInstance Dog : _littleBug) {
				if(i == 2)continue; 
				else Gm.sendPackets(new S_SystemMessage("\\aL"+Dog.getName()+" : "+ BettingPrice.format(_betting[i]) + "章 "), true);
				i++;
			}
		}else if(DogName.contains("自動")){
			if(AutoDogFight){
				AutoDogFight = false;
				Gm.sendPackets(new S_SystemMessage("\\aL投影操作自動解除されました。"), true);
			}else{
				AutoDogFight = true;
				Gm.sendPackets(new S_SystemMessage("\\aL投影操作自動状態です。 （少数量クリティカル増加）"), true);
			}
		}else{
			for (L1PetInstance Dog : _littleBug) {
				/** 文字列が含まれているかどうかチェックが含まれている場合は変数操作 */
				if(Dog.getName().contains(DogName)){
					Dog.setFabrication(true);
					Gm.sendPackets(new S_SystemMessage("\\aL販売チケット and 操作投影"), true);
					Gm.sendPackets(new S_SystemMessage("\\aL"+Dog.getName()+" : "+ BettingPrice.format(_betting[i]) + "章 "), true);
					Gm.sendPackets(new S_SystemMessage("\\aL"+Dog.getName()+" : クリティカル確率上昇"), true);
					return;
				}
				i++;
			}
			Gm.sendPackets(new S_SystemMessage("\\aL注意：検索した投影は存在しません。"), true);
		}
	}
}