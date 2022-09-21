package l1j.server.GameSystem;
import java.util.Date;
import java.util.Random;

import l1j.server.Config;
import l1j.server.MJBookQuestSystem.Loader.UserWeekQuestLoader;
import l1j.server.MJBookQuestSystem.Templates.WeekQuestDateCalculator;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.clientpackets.C_ItemUSe;
import l1j.server.server.model.L1MobGroupSpawn;
import l1j.server.server.model.L1NpcDeleteTimer;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.utils.L1SpawnUtil;

public class BossTimer implements Runnable {
	private static BossTimer _instance;

	public static BossTimer getInstance() {
		if (_instance == null) {
			_instance = new BossTimer();
		}
		return _instance;
	}

	public boolean _is_ZEN_USE = false;

	public boolean _is_USE_OF_NOTICE = false;

	private Date day = new Date(System.currentTimeMillis());

	public BossTimer() {
		// super("l1j.server.GameSystem.BossTimer");
		GeneralThreadPool.getInstance().execute(this);
	}

	@Override
	public void run() {
		try {
			// while (true) {
			day.setTime(System.currentTimeMillis());
			boss();
			fairlyQueen();
			MerchantOneDayBuyReset();
			// Thread.sleep(1000L);
			// }
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	@SuppressWarnings("deprecation")
	private void MerchantOneDayBuyReset() {
		if (day.getMinutes() == 0 && day.getSeconds() == 0 && day.getHours() == 0) {
			L1MerchantInstance.resetOneDayBuy();
			C_ItemUSe.number_of_jars_in_reset();
			C_ItemUSe.reset_marvins_pocket_account_count();
			// C_Shop.reset店舗開設アカウント数();
		}
	}

	private boolean QueenAMspawn = false;
	private boolean QueenPMspawn = false;

	private void fairlyQueen() {
		long time = GameTimeClock.getInstance().getGameTime().getSeconds() % 86400;
		if ((time > 60 * 60 * 9 && time < 60 * 60 * 10) || (time < -60 * 60 * 9 && time > -60 * 60 * 10)) {
			if (!QueenAMspawn) {
				QueenAMspawn = true;
				// 9~12
				fairlyQueenSpawn();
			}
		} else {
			QueenAMspawn = false;
		}

		if ((time > 60 * 60 * 19 && time < 60 * 60 * 20) || (time < -60 * 60 * 19 && time > -60 * 60 * 20)) {
			if (!QueenPMspawn) {
				QueenPMspawn = true;
				// 19~24
				fairlyQueenSpawn();
			}
		} else {
			QueenPMspawn = false;
		}
	}

	private void fairlyQueenSpawn() {
		Random _rnd = new Random(System.nanoTime());
		int delay = _rnd.nextInt(600000 * 3) + 1;
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				Random _rnd = new Random(System.nanoTime());
				int deletetime = (_rnd.nextInt(11) + 10) * 60000;
				L1NpcInstance n = L1SpawnUtil.spawn2(33164, 32284, (short) 4, 70852, 0, deletetime, 0);
				L1MobGroupSpawn.getInstance().doSpawn(n, 107, true, false);
				for (L1NpcInstance npc : n.getMobGroupInfo().getMember()) {
					L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, deletetime);
					timer.begin();
				}
			}

		}, delay);
	}

	private int _4_hour_cycle = (60000 * 60 * 3) + (60000 * 40);
	Random _random = new Random(System.nanoTime());


	@SuppressWarnings("deprecation")
	public void boss() {
		try {
			if (_is_ZEN_USE == true) {
				return;
			}
			if (day.getSeconds() == 0 && day.getMinutes() == 00) {
				int rh = day.getHours();
				_is_ZEN_USE = true;
				BossTimerCheck check = new BossTimerCheck(this);
				check.begin();

				if (rh == Config.ATTENDANCE_RESET_TIME) {
					L1SpawnUtil.spawn6(33174, 33001, (short) 4, 203048, 0, _4_hour_cycle, 0);// 闇の君主
					WeekQuestDateCalculator.getInstance().reloadTime();
					L1World.getInstance().broadcastServerMessage("\\aD出席リストおよび週刊クエスト再更新（毎日午前9時）");
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						UserWeekQuestLoader.load(pc);
					}
				}
			}   
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 *==================== 1時間ゼンボス ======================= ============
         *クロコダイル、ドレイク船長、マンボウサギ。 イフリートドレイクマンボウサギ黒檀のドッペルギャンググレートミノタウルス
         *ジャイアント、フェニックス、大王イカ、ヘッダーファン、
         *
         * 汚染されたオーク闘士、スピリッド、クーマン。
         *バランカ、カプ、ジャイアントワーム、クーマンネクロマンサー======================================
         * =================================
		 **/
	}

	public class EventNoticeTimer implements Runnable {
		private BossTimer notice_check = null;

		public EventNoticeTimer(BossTimer bt) {
			notice_check = bt;
		}

		@Override
		public void run() {
			try {
				notice_check._is_USE_OF_NOTICE = false;
				notice_check = null;
				// this.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void begin() {
			// Timer timer = new Timer();
			// timer.schedule(this, 3000);
			GeneralThreadPool.getInstance().schedule(this, 3000);
		}
	}

	public class BossTimerCheck implements Runnable {
		private BossTimer gen_check = null;

		public BossTimerCheck(BossTimer bt) {
			gen_check = bt;
		}

		@Override
		public void run() {
			try {
				gen_check._is_ZEN_USE = false;
				gen_check = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void begin() {
			// Timer timer = new Timer();
			// timer.schedule(this, 3000);
			GeneralThreadPool.getInstance().schedule(this, 3000);
		}
	}
}