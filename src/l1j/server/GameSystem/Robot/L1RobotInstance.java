package l1j.server.GameSystem.Robot;

import static l1j.server.server.model.skill.L1SkillId.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Astar.AStar;
import l1j.server.GameSystem.Astar.Node;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1BugBearRace;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1EffectSpawn;
import l1j.server.server.model.L1GroundInventory;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillDelay;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_Fishing;
import l1j.server.server.serverpackets.S_MoveCharPacket;
import l1j.server.server.serverpackets.S_OtherCharPacks;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.SQLUtil;


public class L1RobotInstance extends L1PcInstance {
	private static final long serialVersionUID = 1L;
	private static final int MOVE_SPEED = 0;
	private static final int ATTACK_SPEED = 1;
	private static final int MAGIC_SPEED = 2;
	public static final int DMG_MOTION_SPEED = 3;

	private static final int MOVE = 0;
	private static final int ATTACK = 1;
	private static Random _random = new Random(System.nanoTime());
	private L1Skills l1skills;


	public Robot_Location_bean loc = null;
	public L1Character _target;
	public L1PcInstance _target2;
	public L1ItemInstance _targetItem;
	
	public int berkyung_bot_previous_lawful = 0;
	public byte lisbot_spawn_witch = -1;
	public boolean is_LISBOT = false;
	public boolean is_AFFILIATED_MONARCH = false; 
	public boolean is_FISHING_BOT = false;
	public boolean is_HUNTING_BOT = false;
	public boolean is_BERKYUNG_BOT = false;
	public boolean is_DOLL_SPAWN = false;
	private byte listbot_move = 0;
	public boolean is_TELL_HUNTING = false;
	private int actionStatus = 0;
	private boolean FirstSkill = false;
	//private boolean FirstSkill2 = false;
	//private boolean FirstSkill3 = false;
	//private boolean FirstSkill4 = false;
	//private boolean FirstSkill5 = false;
	//private boolean FirstSkill6 = false;
	//private boolean FirstSkill7 = false;
	//private boolean FirstSkill8 = false;
	//private boolean FirstSkill9 = false;
	//private boolean FirstSkill10 = false;
	//private boolean FirstSkill11 = false;
	public boolean is_HUNT_END = false;
	public boolean is_LGNORE_HIT_RETURN = true;

	public String hunting_bot_location;
	public int hunting_bot_type = 0;
	//private short 빨갱이 = 900;
	//private short 주홍이 = 10;
	private short horse_mackerel = 1000;
	private short jade_potion = 10;

	private AStar aStar; // pathfinding variable
	private int[][] iPath; // pathfinding variable
	private Node tail; // pathfinding variable
	private int iCurrentPath; // pathfinding variable
	//private L1RobotInstance _instance = null;
	
	private boolean _Rsaid = false;
	
	private boolean Rsaid() {
		return _Rsaid;
	}

	protected void setRsaid(boolean flag) {
		_Rsaid = flag;
	}
	
	private boolean _Townsaid = false;
	
	private boolean Townsaid() {
		return _Townsaid;
	}

	protected void setTownsaid(boolean flag) {
		_Townsaid = flag;
	}
	
	private boolean _Dissaid = false;
	
	private boolean Dissaid() {
		return _Dissaid;
	}

	protected void setDissaid(boolean flag) {
		_Dissaid = flag;
	}
	
	private boolean _GLsaid = false;
	
	private boolean Glsaid() {
		return _GLsaid;
	}

	protected void setGlsaid(boolean flag) {
		_GLsaid = flag;
	}
	
	private int _shockStunDuration;
	private static final int[] stunTimeArray = {2500, 3000, 3500};
	

	private String _himent;
	private static final String[] himentArray = { "hi", "わんわん", "wwwwwww" , "ハイ？" , "様？", "行きなさい", "www?"};

	private String _townment;
	private static final String[] townmentArray = { "Arekboso", "私だけが飛び散る？", "話す","寝るだけ","ㅇㅇ","I almost lost",
			"トークハム？","ああ","ㅡㅡ","?","タイプ","自動犬;;","私のお兄ちゃん","人がたくさんいます。 ;;","昔のジャガイモの臭いがする","たぶん？"
			,"兄ちょっとナチキン","ㅇㅇ","私は少し外に出ました。","村に少しあります。","10階に行きましょう。","そして犬の痛み","what?",
            "私はご飯を食べます。","ちょっと待って、彼女のせいで長くはできません。","弱い値もありません。 ;","は何ですか？？","眠る","わんわん"};
	
	private String _disment;
	private static final String[] dismentArray = { "hi  ", "寝ています。", "hi?" , "寝てください。" , " しばらく〜", " わんわん", "痛いㅠㅠ", "治らないで痛み"};
	
	private String _glment;
	private static final String[] glmentArray = {"デイジェル1枚あたり3万1万5千人", "密封された血風", "元気なナバルㅍㅍジェシーマン.","気象戦争 ㄱㄱ",
			"ヨンダン1階ビッグファイト","1日あたり30,000.","わんわん","8以上の冷たいキーを買います。","デイ・アクデイ・ダサム","密封された台風の斧が表示されます.","巨人の斧ㅅㅅㅅ.","安い血源募集中",
			"記念1階戦争中","忘れ島パーティー狩りをする人を救う","忘れられない島に乗る必要があります??","ノーマルパーティーを求める.","バラの息吹"
			,"デイ一番高価に買います", "wanwan", "5パーグルポップ400","7ママンで7マツを求めるㅁ","ブルサーを求めます"
			,"7オーダン1千ㅍㅍ.","ソウルを救う5万","9無量売って9進安を求めます","7シンマトㅍㅍジェッシュ","6神聖ヨトゥㅍㅍジェッシュ","タラス手袋を買う","ワンブーを求めます"};
	
	private static final int[] leesbot_BuffSkill4 = {
			L1SkillId.PHYSICAL_ENCHANT_STR, L1SkillId.PHYSICAL_ENCHANT_DEX,
			L1SkillId.BLESS_WEAPON, L1SkillId.REMOVE_CURSE
			};
	
	public L1RobotInstance() {
		
		iPath = new int[300][2];
		aStar = new AStar();
	//	_instance = this;
	}

	public void startAI() {
		new BrainThread().start();
	}

	
	private boolean _aiRunning = false;
	private boolean _actived = false;
	private int _sleep_time;
	public String _userTitle;

	protected void setAiRunning(boolean aiRunning) {
		_aiRunning = aiRunning;
	}

	protected boolean isAiRunning() {
		return _aiRunning;
	}

	protected void setActived(boolean actived) {
		_actived = actived;
	}

	protected boolean isActived() {
		return _actived;
	}

	protected void setSleepTime(int sleep_time) {
		_sleep_time = sleep_time;
	}

	protected int getSleepTime() {
		return _sleep_time;
	}
	
		

	public boolean is_THREAD_EXIT = false;

	class BrainThread implements Runnable {

		public void start() {
			setAiRunning(true);
			GeneralThreadPool.getInstance().execute(BrainThread.this);
			if (is_HUNTING_BOT)
				GeneralThreadPool.getInstance().execute(new PotionThread());
		}

		public void run() {
			try {
				if (is_THREAD_EXIT) {
					setAiRunning(false);
					return;
				}
				if (isParalyzed() || isSleeped()) {
					GeneralThreadPool.getInstance().schedule(this, 200);
					return;
				}

				if (delay != 0) {
					GeneralThreadPool.getInstance().schedule(this, delay);
					delay = 0;
					return;
				}
				if (actionStatus == MOVE && move_delay != 0) {
					GeneralThreadPool.getInstance().schedule(this, move_delay);
					move_delay = 0;
					return;
				}
				if (AI()) {
					setAiRunning(false);
					return;
				}
				if (getSleepTime() == 0)
					setSleepTime(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			GeneralThreadPool.getInstance().schedule(this, getSleepTime());

		}
	}

	class PotionThread implements Runnable {
		public void start() {
			setAiRunning(true);
			GeneralThreadPool.getInstance().execute(PotionThread.this);
		}

		public void run() {
			try {
				if (is_THREAD_EXIT) {
					setAiRunning(false);
					return;
				}

				if (isDead()) {
					GeneralThreadPool.getInstance().schedule(this, 500);
					return;
				}

				if (isParalyzed() || isSleeped()) {
					GeneralThreadPool.getInstance().schedule(this, 200);
					return;
				}

				if (isTeleport()) {
					GeneralThreadPool.getInstance().schedule(this, 400);
					return;
				}

				int percent = (int) Math.round((double) getCurrentHp() / (double) getMaxHp() * 100);
				if (percent < 10 && hunting_bot_type == HUNT && !hunting_bot_location.startsWith("잊섬")) {
					setCurrentHp(getCurrentHp() + 500);
					_return();
					GeneralThreadPool.getInstance().schedule(this, 2000);
					return;
				} else if (percent < 30 && hunting_bot_type == HUNT && !hunting_bot_location.startsWith("잊섬")) {
					setCurrentHp(getCurrentHp() + 500);
					random_tell();
					GeneralThreadPool.getInstance().schedule(this, 2000);
					return;
				}
				int delay = Debuff();
				if (delay > 0) {
					GeneralThreadPool.getInstance().schedule(this, delay);
					return;
				}
				if (Poison()) {
					GeneralThreadPool.getInstance().schedule(this, 300);
					return;
				}
				delay = Potion();
				if (delay > 0) {
					GeneralThreadPool.getInstance().schedule(this, delay);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			GeneralThreadPool.getInstance().schedule(this, 100);
		}
	}

	private boolean AI() {
		if (isTeleport()) {
			return false;
		}


		if (is_LISBOT) {
			if (lisbot_spawn_witch == 1 || lisbot_spawn_witch == 0 || lisbot_spawn_witch == 3)
				return false;
			if (listbot_move > 0)
				move_bot();
			else if (_random.nextInt(1000) <= 1)
				listbot_move = 1;
		} else if (is_FISHING_BOT) {
			fishing_bot();
		} else if (is_HUNTING_BOT) {
			hunting_bot();
		} else if (is_BERKYUNG_BOT) {
			berkyung_bot();
		}
		return false;
	}

	private int Debuff() {
		// TODO Auto-generated method stubs
		// Curse waiting
		if (getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_CURSE_PARALYZING)) {
			_return();
			delay = 8000;
			return 8000;
		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DECAY_POTION)) {
			_return();
			int time = getSkillEffectTimerSet().getSkillEffectTimeSec(
					L1SkillId.DECAY_POTION) * 1000;
			return (int) (delay = time);
		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)) {
			_return();
			int time = getSkillEffectTimerSet().getSkillEffectTimeSec(
					L1SkillId.SILENCE) * 1000;
			return (int) (delay = time);
		}
		return 0;
	}

	public boolean Poison() {
		// TODO Auto-generated method stubs
		if (getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ディケーション状態
			return false;
		}
		if (getPoison() != null) {
			cancelAbsoluteBarrier(); // アブソルトバリアの解放
			Broadcaster.broadcastPacket(this, new S_SkillSound(getId(), 192),
					true);
			curePoison();
			jade_potion--;
			if (jade_potion <= 0)
				potion_reset();
			return true;
		}
		return false;
	}

	private boolean arrival(int range) {
		return isDistance(getX(), getY(), getMapId(), loc.getX(), loc.getY(),
				getMapId(), range);
	}

	public int berkyung_bot_type = 0;

	private void berkyung_bot() {
		// TODO Auto-generated method stubs
		try {
			switch (berkyung_bot_type) {
			case 0: // 設定
				berkyung_bot_coordinate_setting();
				berkyung_bot_type++;
				delay(1000 + _random.nextInt(1000));
				return;
			case 1: // 基本座標到着かプレイ中か
				if (arrival(_random.nextInt(3))) {
					// System.out.println(getName() +" 私が到着しました");
					berkyung_bot_type++;
					return;
				} else if (L1BugBearRace.getInstance().getBugRaceStatus() == L1BugBearRace.STATUS_PLAYING) {
					berkyung_bot_type = 3;
					loc = new Robot_Location_bean(33534 + _random.nextInt(6),
							32853 + _random.nextInt(6), 4);
					// loc = new Robot_Location_bean(33479 + _random.nextInt(8),
					// 32849 + _random.nextInt(11), 4);
					return;
				} else {
					// System.out.println("どうしてここに来たの？" +getName());
				}
				break;
			case 2: // waiting to start
				if (_random.nextInt(100) < 50 && Robot.is_SPEED_BUFF(this)) {
					setSleepTime(calcSleepTime(MAGIC_SPEED));
					return;
				}
				if (L1BugBearRace.getInstance().getBugRaceStatus() == L1BugBearRace.STATUS_PLAYING) {
					delay(500 + _random.nextInt(3500));
					if (_random.nextInt(100) < 98) {
						berkyung_bot_type = 5;
						return;
					}
					loc = new Robot_Location_bean(33534 + _random.nextInt(6),
							32853 + _random.nextInt(6), 4);
					// loc = new Robot_Location_bean(33479 +
					// _random.nextInt(10), 32849 + _random.nextInt(11), 4);
					berkyung_bot_type++;
				} else {
					if (_random.nextInt(1000) > 0)
						return;
					GeneralThreadPool.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							if (is_THREAD_EXIT || berkyung_bot_type != 2)
								return;
							int dir = L1SkillUse.checkObject(getX(), getY(),
									getMapId(), _random.nextInt(20));
							if (dir != -1) {
								setDirectionMove(dir);
							}
							if (_random.nextInt(100) < 60)
								GeneralThreadPool.getInstance().schedule(this,
										getSleepTime());
						}
					}, 1);
				}
				return;
			case 3: // waiting to start
				if (L1BugBearRace.getInstance().getBugRaceStatus() == L1BugBearRace.STATUS_PLAYING) {
					if (Robot.is_SPEED_BUFF(this)) {
						setSleepTime(calcSleepTime(MAGIC_SPEED));
						return;
					}
					if (L1BugBearRace.racing_im) {
						boolean ck = false;
						if (arrival(_random.nextInt(2))) {
							ck = true;
							delay(100 + _random.nextInt(3500));
						} else {
							ck = L1World
									.getInstance()
									.getVisibleObjects(this,
											Config.PC_RECOGNIZE_RANGE).size() > 0;
						}
						if (ck) {
							berkyung_bot_type++;
							loc = new Robot_Location_bean(
									33516 + _random.nextInt(11),
									32849 + _random.nextInt(7), 4);
							return;
						}
					}
				} else
					return;
				break;
			case 4: // Merchant Arrival
				if (arrival(_random.nextInt(3))) {
					delay(3000 + _random.nextInt(7000));
					berkyung_bot_type = 0;
					return;
				}
				break;
			case 5:// There is a certain probability that it does not move when starting
				if (L1BugBearRace.getInstance().getBugRaceStatus() != L1BugBearRace.STATUS_PLAYING)
					berkyung_bot_type = 2;
				return;
			default:
				break;
			}
			if (berkyung_bot_type == 3 || berkyung_bot_type == 4) {
				if (getSleepTime() < 250 && _random.nextInt(100) < 3)
					delay(300 + _random.nextInt(1700));
			}
			if (!isParalyzed()) {
				int dir = moveDirection(loc.getX(), loc.getY(), loc.getMapId());
				if (dir == -1) {
					cnt++;
					if (cnt > 50) {
						Robot.robot_shutdown(this);
						cnt = 0;
					}
				} else {
					boolean tail2 = World.isThroughObject(getX(), getY(),
							getMapId(), dir);
					boolean door = World.door_to_door(getX(), getY(), getMapId(),
							calcheading(this, loc.getX(), loc.getY()));
					if (door || !tail2) {
						cnt++;
						if (cnt > 50) {
							Robot.robot_shutdown(this);
							cnt = 0;
						}
					}
					setDirectionMove(dir);
					setSleepTime(calcSleepTime(MOVE_SPEED));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void berkyung_bot_coordinate_setting() {
		int count = 1000;
		while (count-- > 0) {
			try {
				if (count > 5) {
					switch (_random.nextInt(4)) {
					case 0:
						// loc = new
						// Robot_Location_bean(33502+_random.nextInt(26), 32849,
						// 4);
						// loc = new
						// Robot_Location_bean(33495+_random.nextInt(33), 32849,
						// 4);
						// loc = new Robot_Location_bean(33537, 32853, 4);
						loc = new Robot_Location_bean(33537, 32841, 4);
						break;
					case 1:
						loc = new Robot_Location_bean(33528, 32848, 4);
						break;
					case 2:
						int rrr = _random.nextInt(100);
						if (rrr < 15) {
							loc = new Robot_Location_bean(
									33513 + _random.nextInt(10), 32819, 4);
						} else {
							loc = new Robot_Location_bean(33537, 32853, 4);
						}
						// loc = new Robot_Location_bean(33529,
						// 32844+_random.nextInt(4), 4);
						break;
					case 3:
						// loc = new
						// Robot_Location_bean(33502+_random.nextInt(31), 32859,
						// 4);
						loc = new Robot_Location_bean(
								33495 + _random.nextInt(38), 32859, 4);
						break;
					default:
						break;
					}
				} else {
					int rrr = _random.nextInt(100);
					if (rrr < 30) {
						loc = new Robot_Location_bean(33525, 32851, 4);
					} else if (rrr < 60) {
						loc = new Robot_Location_bean(33537, 32841, 4);
					} else {
						loc = new Robot_Location_bean(33537, 32853, 4);
					}
					/*
					 * if(_random.nextInt(2) == 0){
					 * 
					 * }else
					 */

					// loc = new Robot_Location_bean(33510+_random.nextInt(18),
					// 32850+_random.nextInt(9), 4);
				}
				boolean ck = false;
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (pc.getX() == loc.getX() && pc.getY() == loc.getY()) {
						ck = true;
						break;
					}
				}
				if (!ck) {
					for (L1RobotInstance robot : L1World.getInstance()
							.getAllRobot()) {
						if (robot.loc == null || robot == this)
							continue;
						if (robot.loc.getX() == loc.getX()
								&& robot.loc.getY() == loc.getY()) {
							ck = true;
							break;
						}
					}
				}
				if (ck)
					continue;
				break;
			} catch (Exception e) {
				break;
			}
		}
		if (count <= 0) {

		}
	}

	public static final int SETTING = 0;
	public static final int TEL_NPC_MOVE = 1;
	public static final int HUNT_MOVE = 2;
	public static final int HUNT = 3;
	public static final int DEATH = 4;
	public static final int EXIT = 10;

	public long Hunt_Exit_Time = 0;

	private boolean is_OTHER_VILLAGE_TEL_DONG = false;
	private int cnt2 = 0;
	private Queue<Robot_Location_bean> location_queue = new ConcurrentLinkedQueue<Robot_Location_bean>();
	private Queue<L1ItemInstance> item_queue = new ConcurrentLinkedQueue<L1ItemInstance>();

	private void hunting_bot() {
		try {
			if (isDead() && hunting_bot_type != DEATH) {
				delay(2000 + _random.nextInt(3000));
				hunting_bot_type = DEATH;
				return;
			}
			if (!isDead() && !isTeleport()) {
				if (Hunt_Exit_Time <= System.currentTimeMillis()) {
					delay(20000);
					return_(500);
					end();
					Robot_Hunt.getInstance().delay_spawn(hunting_bot_location, 60000);
					return;
				}
				if (!getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHAPE_CHANGE)) {
					getSkillEffectTimerSet().setSkillEffect(L1SkillId.SHAPE_CHANGE, 1800 * 1000);
					int time = getSkillEffectTimerSet().getSkillEffectTimeSec(L1SkillId.SHAPE_CHANGE);
					if (time == -1) {
						end();
						return;
					}
					Robot.poly(this);
					Broadcaster.broadcastPacket(this, new S_ChangeShape(getId(), getGfxId().getTempCharGfx()));
					Broadcaster.broadcastPacket(this, new S_CharVisualUpdate(this, getCurrentWeapon()));
					return;
				}
				if (Robot.is_SPEED_BUFF(this)) {
					setSleepTime(calcSleepTime(MAGIC_SPEED));
					return;
				}
				if (!isSkillDelay()) {
					if (Robot.is_CLASS_BUFF(this)) {
						setSleepTime(calcSleepTime(MAGIC_SPEED));
						return;
					}
					/** If you are a fairy, add a soul system*/
					if (isElf()) { 
						if (actionStatus == MOVE) { 
							int percent = (int) Math
									.round((double) getCurrentMp() 
											/ (double) getMaxMp() * 100); 
							if ( _random.nextInt(10) +1 > 9 && percent < 95) { 
								new L1SkillUse().handleCommands(this,L1SkillId.BLOODY_SOUL,getId(),getX(), getY(), null, 0, L1SkillUse.TYPE_NORMAL); return; 
							} 
						}
					}
				}

				Robot.Doll_Spawn(this);
			    
			
			}

			// Check if it is in another area and tell to that location
			if (loc == null) {
				location_queue.clear();
				ArrayList<Robot_Location_bean> list = Robot_Location.location(this);
				if (list != null) {
					for (Robot_Location_bean ro : list) {
			
						if (hunting_bot_type == SETTING)
							additional_setting_coordinates(ro);
						location_queue.offer(ro);
					}
					loc = location_queue.poll();
				}
			}
			switch (hunting_bot_type) {
			case SETTING:// shop, warehouse, buff
			case TEL_NPC_MOVE:// telnyeo move
				  	/** chat in town **/
					int townrandom = _random.nextInt(1000) +1;
					if(!Townsaid() && townrandom > 998){
					try{
					Delay(1500);
					_townment = townmentArray[_random.nextInt(townmentArray.length)];
					Broadcaster.broadcastPacket(this ,new S_ChatPacket(this, _townment, Opcodes.S_SAY, 0));
					setTownsaid(true);
					_townment = null;
					}catch(Exception e){
						return;
					}
					}
					/** chat in town **/
				if (loc == null) {
					hunting_bot_type++;
					return;
				}
				if (isDistance(getX(), getY(), getMapId(), loc.getX(),
						loc.getY(), loc.getMapId(), 1 + _random.nextInt(5))) {
					loc = location_queue.poll();
					delay(5000 + _random.nextInt(15000));
					if (loc != null && is_OTHER_VILLAGE_TEL_DONG) {
						getMoveState().setHeading(5);
						tell(loc.getX(), loc.getY(), loc.getMapId(),
								3000 + _random.nextInt(3000));
						loc = location_queue.poll();
						is_OTHER_VILLAGE_TEL_DONG = false;
					}
					if (loc == null) {
						if (hunting_bot_type == SETTING)
							zendorbuff();
							hunting_bot_type++;
					}
					return;
				}
				break;
			case HUNT_MOVE: // 狩り場に行く
				delay(500 + _random.nextInt(1000));
				tell(loc.getX(), loc.getY(), loc.getMapId());
				location_queue.offer(loc);
				loc = location_queue.poll();
				hunting_bot_type++;
				return;
			case HUNT: // 狩り
				if (checkTarget() || checkTarget2()){
					return;
				}
			
				if (is_TELL_HUNTING) {
					delay(1000 + _random.nextInt(500));
					random_tell(500 + _random.nextInt(1000));
					setTownsaid(false);
					setGlsaid(false);
					return;
					
				}

				if (loc == null) {
					delay(3000 + _random.nextInt(6000));
					return_(1000 + _random.nextInt(2000));
					return;
				}

				int range = _random.nextInt(5)+1;

				if (isDistance(getX(), getY(), getMapId(), loc.getX(), loc.getY(), getMapId(), range)) {
					location_queue.offer(loc);
					loc = location_queue.poll();
					cnt2++;
					if (cnt2 >= 3) {
						passTargetList.clear();
						passTargetList2.clear();
						cnt2 = 0;
                        return;
					}
				}
				break;
			case DEATH: // 死亡
				int[] loc = Getback.GetBack_Restart(this);
				Broadcaster.broadcastPacket(this, new S_RemoveObject(this),
						true);
				setCurrentHp(getLevel());
				set_food(225); // 死んだとき100％
				setDead(false);
				L1World.getInstance().moveVisibleObject(this, loc[0], loc[1],
						loc[2]);
				setX(loc[0]);
				setY(loc[1]);
				setMap((short) loc[2]);
				for (L1PcInstance pc2 : L1World.getInstance().getVisiblePlayer(
						this)) {
					pc2.sendPackets(new S_OtherCharPacks(this, pc2));
				}
				_target = null; //Leak-proof
				_targetItem = null; //Leak-proof
				_target2 = null; //Leak-proof
				delay(3000 + _random.nextInt(6000));
				return_(1000 + _random.nextInt(2000));
				setTownsaid(false);
				setGlsaid(false);
				return;
			case EXIT: // 終了
				return;
			default:
				break;
			}
			if (!isDead() && loc != null) {
				move();
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}

	private void additional_setting_coordinates(Robot_Location_bean ro) {
		// TODO Auto-generated method stubs
		if (ro.getX() == 33457 && ro.getY() == 32819 && ro.getMapId() == 4) {// Giran
																				// potion shop
			if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// 오렌
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// tell me
																				// location
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));// tell
																				// する
																				// 場所
			} else if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// silver knight
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));
			}
			if (location_queue.size() > 0)
				is_OTHER_VILLAGE_TEL_DONG = true;
		} else if (ro.getX() == 33432 && ro.getY() == 32815
				&& ro.getMapId() == 4) {// Giran 2 Potion Shop
			if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// Oren
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// tell me
																				// location
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));// tell
																				// will do
																				// location
			} else if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// 은기사
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));
			}
			if (location_queue.size() > 0)
				is_OTHER_VILLAGE_TEL_DONG = true;
		} else if (ro.getX() == 33428 && ro.getY() == 32806
				&& ro.getMapId() == 4) {// Giran 3, 5 Aden Top
			if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// Oren
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// tell me
																				// location
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));// tell
																				// will do
																				// location
			} else if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// silver knight
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));
			}
			if (location_queue.size() > 0)
				is_OTHER_VILLAGE_TEL_DONG = true;
		} else if (ro.getX() == 33437 && ro.getY() == 32803
				&& ro.getMapId() == 4) {// Giran 4 Zendor
			if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// Oren
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// tell me
																				// location
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));// tell
																				// will do
																				// location
			} else if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// silver knight
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));
			}
			if (location_queue.size() > 0)
				is_OTHER_VILLAGE_TEL_DONG = true;
		} else if (ro.getX() == 34065 && ro.getY() == 32287
				&& ro.getMapId() == 4) {// Oren Potion Shop
			if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// silver knight
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(34062, 32278, 4));
			} else if (getX() >= 33410 && getX() <= 33461 && getY() >= 32788
					&& getY() <= 32838 && getMapId() == 4) {// Giran
				location_queue.offer(new Robot_Location_bean(33437, 32794, 4));
				location_queue.offer(new Robot_Location_bean(34062, 32278, 4));
			}
			if (location_queue.size() > 0)
				is_OTHER_VILLAGE_TEL_DONG = true;
		} else if (ro.getX() == 32596 && ro.getY() == 32741
				&& ro.getMapId() == 4) {// Gulmal Potion Shop
			if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// silver knight
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(32608, 32734, 4));
			} else if (getX() >= 33410 && getX() <= 33461 && getY() >= 32788
					&& getY() <= 32838 && getMapId() == 4) {// Giran
				location_queue.offer(new Robot_Location_bean(33437, 32794, 4));
				location_queue.offer(new Robot_Location_bean(32608, 32734, 4));
			} else if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// Oren
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// tell me
																				// location
				location_queue.offer(new Robot_Location_bean(32608, 32734, 4));
			}
			if (location_queue.size() > 0)
				is_OTHER_VILLAGE_TEL_DONG = true;
		} else if (ro.getX() == 33738 && ro.getY() == 32494
				&& ro.getMapId() == 4) {// Well Don Potion Shop
			if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// silver knight
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33709, 32500, 4));
			} else if (getX() >= 33410 && getX() <= 33461 && getY() >= 32788
					&& getY() <= 32838 && getMapId() == 4) {// Giran
				location_queue.offer(new Robot_Location_bean(33437, 32794, 4));
				location_queue.offer(new Robot_Location_bean(33709, 32500, 4));
			} else if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// Oren
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// tell me
																				// location
				location_queue.offer(new Robot_Location_bean(33709, 32500, 4));
			}
			if (location_queue.size() > 0)
				is_OTHER_VILLAGE_TEL_DONG = true;
		}
	}

	public void end() {
		end(1000 + _random.nextInt(20000));
	}

	public void end(int time) {
		is_HUNT_END = true;
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stubs
				is_THREAD_EXIT = true;
				for (L1PcInstance pc : L1World.getInstance()
						.getRecognizePlayer(L1RobotInstance.this)) {
					pc.sendPackets(new S_RemoveObject(L1RobotInstance.this), true);
					pc.getNearObjects().removeKnownObject(L1RobotInstance.this);
				}
				L1World world = L1World.getInstance();
				world.removeVisibleObject(L1RobotInstance.this);
				world.removeObject(L1RobotInstance.this);
				getNearObjects().removeAllKnownObjects();
				stopHalloweenRegeneration();
				stopPapuBlessing();
				stopHalloweenArmorBlessing();
				stopAHRegeneration();
				stopHpRegenerationByDoll();
				stopMpRegenerationByDoll();
				stopSHRegeneration();
				stopMpDecreaseByScales();
				stopEtcMonitor();
				hunting_bot_location = null;
				is_HUNTING_BOT = false;
				hunting_bot_type = 0;
				is_HUNT_END = false;
				is_LGNORE_HIT_RETURN = true; //hit return original false
				loc = null;
				updateconnect(false);
				Robot.Doll_Delete(L1RobotInstance.this, true);
				Robot_Hunt.getInstance().put(L1RobotInstance.this);
			}

		}, time);
	}

	public void random_tell() {
		delay(1000);
		random_tell(1);
		passTargetList.clear();
		passTargetList2.clear();
		
	}

	private void random_tell(int time) {
		L1Location newLocation = getLocation().randomLocation(200, true);
		int newX = newLocation.getX();
		int newY = newLocation.getY();
		short mapId = (short) newLocation.getMapId();
		tell(newX, newY, mapId, time);
	}

	public void _return() {
		return_(1);
	}

	public void return_(int time) {
		int[] loc = new int[3];
		_random.setSeed(System.currentTimeMillis());
		switch (_random.nextInt(10)) {
		case 0:
			loc[0] = 33433;
			loc[1] = 32800;
			loc[2] = 4;
			break;
		case 1:
			loc[0] = 33418;
			loc[1] = 32815;
			loc[2] = 4;
			break;
		case 2:
			loc[0] = 33425;
			loc[1] = 32827;
			loc[2] = 4;
			break;
		case 3:
			loc[0] = 33442;
			loc[1] = 32797;
			loc[2] = 4;
			break;
		case 6:
		case 5:
		case 4:
			loc[0] = 34056;
			loc[1] = 32279;
			loc[2] = 4;
			break;
		case 7:
		case 8:
		case 9:
			loc[0] = 33080;
			loc[1] = 33392;
			loc[2] = 4;
			break;
		default:
			loc[0] = 33442;
			loc[1] = 32797;
			loc[2] = 4;
			break;
		}
		tell(loc[0], loc[1], loc[2], time);
		if (is_HUNTING_BOT) {
			item_queue.clear();
			passTargetList.clear();
			passTargetList2.clear();
			hunting_bot_type = SETTING;
			this.loc = null;
		}
	}

	private int Potion() {
		if (getSkillEffectTimerSet().hasSkillEffect(10513))
			return 1000;
		if (getSkillEffectTimerSet().hasSkillEffect(71) == true) { // Decay Potion Status
			return 0;
		}
		int percent = (int) Math.round((double) getCurrentHp()
				/ (double) getMaxHp() * 100);
		int gfxid = 0;
		int healHp = 0;
		int delay = 0;
	
		if(percent < 95){
			gfxid = 197; //redhead 189
			healHp = Config.BOT_POTION_BASIC_RECOVERY_AMOUNT + _random.nextInt(Config.BOT_POTION_RANDOM_RECOVERY_AMOUNT);
			delay = 500;
			horse_mackerel--;
		}


		if (healHp == 0)
			return 0;
		// アブソリュートバリアの解除
		cancelAbsoluteBarrier();
		Broadcaster.broadcastPacket(this, new S_SkillSound(getId(), gfxid),
				true);
		if (getSkillEffectTimerSet().hasSkillEffect(POLLUTE_WATER)
				|| getSkillEffectTimerSet().hasSkillEffect(10517)) { // ポルトウォーター中
																		// 回復量1/2倍
			healHp /= 2;
		}

		setCurrentHp(getCurrentHp() + healHp);
		if (horse_mackerel <= 0) {
			potion_reset();
		}
		return delay;
	}

	private void potion_reset() {
		/*
		 * ディレイ(2000+_random.nextInt(14000)); 帰還();
		 */
		
		horse_mackerel = (short) (800 + _random.nextInt(1000));
		jade_potion = (short) (1000);
	}

	private boolean checkTarget() {
		
		if (_target == null && _targetItem == null) {
			searchTarget();
		}
		if ( _target != null && _target instanceof L1MonsterInstance) {
			if (((L1MonsterInstance) _target).getHiddenStatus() != L1NpcInstance.HIDDEN_STATUS_NONE
					|| _target.isDead()
					|| ((L1MonsterInstance) _target)._destroyed
					|| ((L1MonsterInstance) _target).getTarget() != this
					|| _target.isInvisble()) {
				_target = null;
				searchTarget();
				setSleepTime(100);
			}
		}
		if (_targetItem != null) {
			L1Inventory groundInventory = L1World.getInstance().getInventory(
					_targetItem.getX(), _targetItem.getY(),
					_targetItem.getMapId());
			if (!groundInventory.checkItem(_targetItem.getItemId())) {
				_targetItem = null;
				searchTarget(); //A.I
				setSleepTime(100);
			} else {
				onTargetItem();
				return true;
			}
		} else if (_target != null){ // && _target2 !=null) { //時が来ないとき
			return onTarget();
			
		}
		_targetItem = null; //12-18
		_target = null; //12-18
		return false;
	}
	
	private boolean checkTarget2() {
		// TODO Auto-generated method stubs
 		if (_target2 == null) {
			searchTarget();
		}

		if (_target2 != null && _target2 instanceof L1PcInstance) {
			if (_target2.isDead() || _target2.isInvisble()) {
				_target2= null;
				searchTarget();
				setSleepTime(100);
			} else {
         	return onTarget2();

			}
		}
		_target2 = null;
		return false;
		
	}

	private void searchTarget() {
		
	
		int MaxRange = 2;
		//if (Hunting Bot_Location.startsWith("Forgot Island")))
		//	MaxRange = 3;
	
		ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(this);
	    ArrayList<L1PcInstance> list2 = L1World.getInstance().getVisiblePlayer(this); 
	  
	    if(list2.contains(_target2)){
	    	return;
	    } 
	    //You won't hit the guy you hit? A.I
	    
	    
	    if (list.size() > 1)
			Collections.shuffle(list);
	    

		
		int mapid = getMapId();
		for (L1Object obj : list) {
			if (obj instanceof L1GroundInventory) {
				L1GroundInventory inv = (L1GroundInventory) obj;
				for (L1ItemInstance item : inv.getItems()) {
					if (item.getItemOwner() != null	&& item.getItemOwner() == this) { //すべてを食べる
						if (item !=null && !isDistance(getX(), getY(), mapid, item.getX(),item.getY(), mapid, 20)){
							continue;
						}
						if (item !=null && isDistance(getX(), getY(), mapid, item.getX(),item.getY(), mapid, 10)
							&& !isDistance(getX(), getY(), mapid, item.getX(),item.getY(), mapid, -1)){
						if(_serchCource(item.getX(), item.getY()) == -1) {
							continue;
						} if (item_queue.contains(item)){
								continue;
						}
						 
						} //すべてを食べる
					
						item_queue.offer(item);
					    list = null; //Leak-proof 2015.11.26
					    obj = null; //Leak-proof 2015.11.26
					    item = null; //Leak-proof
					
					}
		
				}
			}
		}
		if (item_queue.size() > 0) {
			_targetItem = item_queue.poll();
			return;
		}
		
		for (int i = 0; i <= MaxRange; i++) {
	
			list = L1World.getInstance().getVisibleObjects(this, i == 0 ? 1 : 4 * i);
			list2 = L1World.getInstance().getVisiblePlayer(this, i == 0 ? 1 : 4 * i);

			
		
			if (list2.size() > 1)
				Collections.shuffle(list2);	
			if (list.size() > 1)
			    Collections.shuffle(list);
			if (list.size() > 1 && list2.size() > 1)
				Collections.shuffle(list2);
		

			
			for (L1Object obj : list){
				if (obj instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					
					if (passTargetList.contains(obj)){
						continue;
					}
				
					if (mon.getCurrentHp() <= 0 || mon.isDead()){
						continue;
					}
					if (mon.getHiddenStatus() != L1NpcInstance.HIDDEN_STATUS_NONE
							|| mon._destroyed || mon.isInvisble()){
						continue;
					}
					if (mon.getNpcId() == 100623
						|| mon.getNpcId() == 100624
						|| mon.getNpcId() == 45941
						|| (mon.getNpcId() >= 46048 && mon.getNpcId() <= 46052)){
						continue;
					}
                   	if (mon.getNpcId() >= 70981 && mon.getNpcId() <= 70984){
						continue;
					}
					if (mon.getTarget() != null && mon.getTarget() != this){
						continue;
					}
					if (obj != null && !isDistance(getX(), getY(), mapid, obj.getX(), obj.getY(), mapid, 20)){
						continue;
					}
					if (obj != null && _serchCource(obj.getX(), obj.getY()) == -1) { 
						passTargetList.add(obj);
						continue;
					}
								
					_target = mon;
					FirstSkill = false;
//					FirstSkill2 = false;
//					FirstSkill3 = false;
//					FirstSkill4 = false;
//					FirstSkill5 = false;
//					FirstSkill6 = false;
//					FirstSkill7 = false;
//					FirstSkill8 = false;
//					FirstSkill9 = false;
//					FirstSkill10 = false;
//					FirstSkill11 = false;
					list = null; //Leak-proof 2015.11.26
					obj = null; //Leak-proof 2015.11.26
					mon= null;
					return;
				}
				
				for (L1PcInstance obj2 : list2){
					if (obj2 instanceof L1PcInstance) {
						L1PcInstance saram = (L1PcInstance) obj2;
		
					    if (passTargetList2.contains(obj2)){
							continue;
					    }
						if (saram.getCurrentHp() <= 0 || saram.isDead()){
							continue;
						}
						if (saram.isInvisble()){
							continue;
						}
												
						if (!saram.isPinkName() && Config._ROBOT_SKIN == false && (!(getMapId() >= 43 && getMapId() <= 50))){ 
							continue; 
						}
						
						if (saram.getMap().isSafetyZone(saram.getX(), saram.getY())){
							continue;
						}
						if (getClanid() != 0 && getClanid() == saram.getClanid()){
							continue;
						}
						
						if (obj2 != null && !isDistance(getX(), getY(), mapid, obj2.getX(), obj2.getY(), mapid, 20)){
							continue;
						}
						if (obj2 != null && isDistance(getX(), getY(), mapid, obj2.getX(), obj2.getY(), mapid, 10)
						&& !isDistance(getX(), getY(), mapid, obj2.getX(), obj2.getY(), mapid, -1)){ 
						if( _serchCource(obj2.getX(), obj2.getY()) == -1){
						passTargetList2.add(obj2);
						continue;
						}
						}
				
										
						_target2 = saram;
						FirstSkill = false;
//						FirstSkill2 = false;
//						FirstSkill3 = false;
//						FirstSkill4 = false;
//						FirstSkill5 = false;
//						FirstSkill6 = false;
//						FirstSkill7 = false;
//						FirstSkill8 = false;
//						FirstSkill9 = false;
//						FirstSkill10 = false;
//						FirstSkill11 = false;
						setRsaid(false); //robot chat
						setDissaid(false); //street chat
						list2 = null;//Leak-proof 2015.11.26
						obj2 = null; //Leak-proof 2015.11.26
						saram = null;
						return;
				}
			 }
				
			}
		}
	}

	public void onTargetItem() {

		if (_targetItem == null) {
			return;
		}
		if (getLocation().getTileLineDistance(_targetItem.getLocation()) <= 1) {
			pickupTargetItem();
			setSleepTime(800 + _random.nextInt(400));
		
		} else {
			int dir = moveDirection(_targetItem.getX(), _targetItem.getY(),
					_targetItem.getMapId());
			if (dir == -1) {
				_targetItem = null;
			} else {
				boolean tail = World.isThroughObject(getX(), getY(),
						getMapId(), dir);
				int tmpx = aStar.getXY(dir, true) + getX();
				int tmpy = aStar.getXY(dir, false) + getY();
				boolean obj = World.isMapdynamic(tmpx, tmpy, getMapId());
				boolean door = World.door_to_door(getX(), getY(), getMapId(), dir);
				if (tail && !obj && !door) {
					setDirectionMove(dir);
				}
				setSleepTime(calcSleepTime(MOVE_SPEED));
			}
			
		}
		_targetItem = null;
	}

	private void pickupTargetItem() {
		int chdir = calcheading(this, _targetItem.getX(), _targetItem.getY());
		if (getMoveState().getHeading() != chdir) {
			getMoveState().setHeading(chdir);
			Broadcaster.broadcastPacket(this, new S_ChangeHeading(this), true);
		}
		Broadcaster.broadcastPacket(this,
				new S_AttackPacket(this, _targetItem.getId(),
						ActionCodes.ACTION_Pickup), true);
		L1Inventory groundInventory = L1World.getInstance().getInventory(
				_targetItem.getX(), _targetItem.getY(), _targetItem.getMapId());
		groundInventory.tradeItem(_targetItem, _targetItem.getCount(), getInventory());
		_targetItem = null;
	}

	private ArrayList<L1Object> passTargetList = new ArrayList<L1Object>();
	private ArrayList<L1PcInstance> passTargetList2 = new ArrayList<L1PcInstance>();

//HashMap 追加(ロボットスキル)
	private HashMap<String, Integer> setRobotSKill(L1Character target,boolean firstAttack) {
		HashMap<String, Integer> skillInfo = new HashMap<String, Integer>();
		boolean isFirstAttackSkill = !firstAttack;
		int skillId = 0;
		int skill_range = 11;
		int rand =  _random.nextInt(100);
		
		if (isElf()) {
			
			//first skill gail
			if (isFirstAttackSkill)
			{
				skillId = L1SkillId.STRIKER_GALE;
				skill_range = 2;
			}
			else
			{
				if(rand < 10)
				{
					skillId = L1SkillId.POLLUTE_WATER;
					skill_range = 2;
				}
				else if(rand < 60)
				{
					skillId = L1SkillId.TRIPLE_ARROW;
					skill_range = 7;
				}
			}
			
		} 
		else if (isDragonknight())
		{
			if (isFirstAttackSkill)
			{
			skillId = L1SkillId.DESTROY;
			skill_range = 2;			
		}
		else
		{
				if(rand < 30)
				{
					skillId = L1SkillId.FOU_SLAYER;
					skill_range = 1;
				}
				if(rand < 14)
				{
					skillId = L1SkillId.DESTROY;
					skill_range = 2;
				}
			}
		} 
		else if (isWizard())
		{
			//first skill dis
			if (isFirstAttackSkill)
			{
				skillId = L1SkillId.ICE_SPIKE;
				skill_range = 7;
			}
			else if(target.getCurrentHp() < 1000 )
			{
				//End with Eternity when target HP is 250 or less
			    skillId = L1SkillId.ETERNITY;
			    skill_range = 7;
			}
			else
			{
				if(rand < 10)
				{
				    skillId = L1SkillId.DEATH_HILL;
				    skill_range = 2;
				}
				else if(rand < 50)
				{
				    skillId = L1SkillId.WEAKNESS;
				    skill_range = 1;
				}
				else if(rand < 50)
				{
					skillId =L1SkillId.DISINTEGRATE;
					skill_range = 7;
				}
				else if(rand < 95)
				{
					skillId =L1SkillId.ICE_SPIKE;
					skill_range = 7;
				}
			}

		}
		else if (isWarrior())
		{
			if (isFirstAttackSkill)
			{
				skillId =L1SkillId.DEMOLITION;
				skill_range = 1;
			}
			else if(rand < 20)
			{
				skillId =L1SkillId.POWER_GRIP;
				skill_range = 1;
			}
			else if(rand < 20)
			{
				skillId =L1SkillId.DESPERADO;
				skill_range = 1;
			}
		}
		else if (isKnight())
		{
			if (isFirstAttackSkill)
			{
				skillId = L1SkillId.SHOCK_STUN;
				skill_range = 1;
			}
		}
		
		else if (isCrown())
		{
			if (isFirstAttackSkill)
			{
				skillId = L1SkillId.EMPIRE;
				skill_range = 1;
			}
			else if(rand < 20)
			{
				skillId =L1SkillId.TRUE_TARGET;
				skill_range = 1;
			}
		}	
		else if (isIllusionist())
		{
			if (isFirstAttackSkill)
			{
				skillId = L1SkillId.BONE_BREAK;
				skill_range = 1;
			}
			else if(rand < 30)
			{
				skillId =L1SkillId.BONE_BREAK;
				skill_range = 1;
			}
			else if(rand < 10)
			{
				skillId =L1SkillId.PHANTASM;
				skill_range = 1;
			}
		}
		skillInfo.put("skillId",skillId);
		skillInfo.put("skill_range",skill_range);
		return skillInfo;
	}


	public boolean onTarget() {
		setActived(true);
		_targetItem=null;
		L1Character target = _target;
		
		int percent = (int) Math.round((double) getCurrentHp() / (double) getMaxHp() * 100);
		if(_target2 !=null && percent < 80){
			_target = null;
			return checkTarget2(); //A.I
			 
		}
	
		if (target == null) {
			return false;
		}
		
		/** 외창 **/
		int glrandom = _random.nextInt(1000) +1;
		 if(!Glsaid() && glrandom > 998){
			 outsole();
		 }
		 /** 외창 **/
		
		int escapeDistance = 15;
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DARKNESS)
				|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CURSE_BLIND))
			escapeDistance = 1;
		int calcx = (int) getLocation().getX() - target.getLocation().getX();
		int calcy = (int) getLocation().getY() - target.getLocation().getY();

		if (Math.abs(calcx) > escapeDistance
				|| Math.abs(calcy) > escapeDistance) {
			_target = null;
			return false;
		}
		boolean tail = World.isThroughAttack(getX(), getY(), getMapId(), calcheading(this, target.getX(), target.getY()));
		
		if (getX() == _target.getX() && getY() == _target.getY()
				&& getMapId() == _target.getMapId())
			tail = true;
	
		boolean door = World.door_to_door(getX(), getY(), getMapId(), calcheading(this, target.getX(), target.getY()));
		
		int range = 1;
		if (isElf() && getCurrentWeapon() == 20)
			range = 11;
		// First Strike or Triple or Magic?
//		if (!FirstSkill && !isSkillDelay() && getCurrentMp() > 70) {
		//Don't use skills on mobs
		if (!isSkillDelay() && getCurrentMp() > 70 && (target instanceof L1MonsterInstance) == true )
		{
			int skillId = 0;
			int skill_range = 11;
			
			HashMap<String, Integer> skillInfo = setRobotSKill(target, FirstSkill);
			skillId = skillInfo.get("skillId");
			skill_range = skillInfo.get("skill_range");
			
			//L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,msg));

			if (skillId > 0) 
			{
				if (CharPosUtil.isAttackPosition(this, target.getX(), target.getY(), target.getMapId(), skill_range) == true 	
			  && CharPosUtil.isAttackPosition(target, getX(), getY(),	getMapId(), skill_range) == true) {
					FirstSkill = true;
					new L1SkillUse().handleCommands(this, skillId,	_target.getId(), _target.getX(), _target.getY(),	null, 0, L1SkillUse.TYPE_NORMAL);
					setSleepTime(calcSleepTime(MAGIC_SPEED));
					actionStatus = ATTACK;
					return true;
				}
			}
		}
		if (CharPosUtil.isAttackPosition(this, target.getX(), target.getY(), target.getMapId(), range) == true
				&& CharPosUtil.isAttackPosition(target, getX(), getY(), getMapId(), range) == true) {// 基本攻撃範囲
			if (door || !tail) 
			{
				cnt++;
				if (cnt > 5) {
					_target = null;
					cnt = 0;
				}
				return false;
			}
			setHeading( targetDirection(target.getX(), target.getY()));
			attackTarget(target);
			actionStatus = ATTACK;
			return true;

		} else {
			/*int dir = moveDirection(target.getX(), target.getY(),	target.getMapId());*/
			int dir = moveDirection(target.getX(), target.getY(),	target.getMapId());
			if (dir == -1) {
				passTargetList.add(_target);
				_target = null;
				return false;
			} else {
				boolean tail2 = World.isThroughObject(getX(), getY(),getMapId(), dir);
				if (door || !tail2) {
					cnt++;
					if (cnt > 5) {
						_target = null;
						cnt = 0;
					}
					return false;
				}
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(MOVE_SPEED));
			}
		}
		return true;
	}
	
	public <L1Pcinstance> boolean onTarget2() {
		setActived(true);
		_targetItem = null;
		_target = null;
		L1PcInstance target2 = _target2;
		
		if(target2 == null){
			try{
				Delay(1000);
				return false;
				}catch(Exception e){
			}
			
		}
							
		
		/** Chat on Encounter **/
		int hirandom= _random.nextInt(1000)+1;
		if(hirandom > 995 && !Rsaid() && !target2.isRobot()){
		_himent = himentArray[_random.nextInt(himentArray.length)];
		try{
		Delay(1500);
		Broadcaster.broadcastPacket(this ,new S_ChatPacket(this, _himent, Opcodes.S_SAY, 0));
		setRsaid(true);
		_himent = null;
		}catch(Exception e){
		}
		}
		/** Chat on Encounter **/
	
		int escapeDistance = 15;
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DARKNESS)
				|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CURSE_BLIND))
			escapeDistance = 1;
		int calcx = (int) getLocation().getX() - target2.getLocation().getX();
		int calcy = (int) getLocation().getY() - target2.getLocation().getY();

		if (Math.abs(calcx) > escapeDistance
			|| Math.abs(calcy) > escapeDistance) {
			_target2 = null;
			return false;
		}
		
		/** Chat when running away **/
		int disrandom= _random.nextInt(100)+1;
		if(disrandom > 50 && !isElf() &&  Rsaid() && !target2.isRobot() && Math.abs(calcx) > 6 && !Dissaid() 
		|| disrandom > 50 && !isElf() &&  Rsaid() && !target2.isRobot() && Math.abs(calcy) > 6 && !Dissaid()
		){
		_disment = dismentArray[_random.nextInt(dismentArray.length)];
		String NAME = target2.getName();
		try{
        Delay(1500);
		Broadcaster.broadcastPacket(this ,new S_ChatPacket(this, NAME+_disment, Opcodes.S_SAY, 0));
		cnt++;
		if (cnt > 2) {
		setDissaid(true);
		_disment = null;
		}
		}catch(Exception e){}
		}
		/** Chat when running away **/
		
		boolean tail = World.isThroughAttack(getX(), getY(), getMapId(), 
				calcheading(this, target2.getX(), target2.getY()));
	
		if (getX() == _target2.getX() && getY() == _target2.getY() 
				&& getMapId() == _target2.getMapId())
			tail = true;
	
		boolean door = World.door_to_door(getX(), getY(), getMapId(), calcheading(this, target2.getX(), target2.getY()));
		
		int range = 1;
		if (isElf() && getCurrentWeapon() == 20)
			range = 11;
		
		// First Strike or Triple or Magic?
		//if (!FirstSkill && !isSkillDelay() && getCurrentMp() > 70) {
		//とてもスキルを使わない
		if (!isSkillDelay() && getCurrentMp() > 70) {
			int skillId = 0;
			int skill_range = 11;
		
			HashMap<String, Integer> skillInfo = setRobotSKill(target2, FirstSkill);
			skillId = skillInfo.get("skillId");
			skill_range = skillInfo.get("skill_range");
			
			if (skillId > 0) {
				if (CharPosUtil.isAttackPosition(this, target2.getX(), target2.getY(), target2.getMapId(), skill_range) == true
						&& CharPosUtil.isAttackPosition(target2, getX(), getY(),getMapId(), skill_range) == true) {
					
					FirstSkill = true;
					//기사로봇 스턴 추가시작	
					if (isKnight()
							&& skillId == L1SkillId.SHOCK_STUN
							&& !target2.hasSkillEffect(L1SkillId.SHOCK_STUN)) {
						
						int STrnd = _random.nextInt(10) + 1;
						if (STrnd >= 7) {
							_shockStunDuration = stunTimeArray[_random
									.nextInt(stunTimeArray.length)];
							S_SkillSound ss1 = new S_SkillSound(
									target2.getId(), 4434);
							target2.sendPackets(ss1);
							Broadcaster.broadcastPacket(target2, ss1);
							ss1 = null;
							target2.getSkillEffectTimerSet().setSkillEffect( L1SkillId.SHOCK_STUN, _shockStunDuration);
							L1EffectSpawn.getInstance().spawnEffect(81162, _shockStunDuration, target2.getX(), target2.getY(), target2.getMapId());
							S_Paralysis par = new S_Paralysis(S_Paralysis.TYPE_STUN, true);
							target2.sendPackets(par);
							par = null;
							new L1SkillDelay(this, 2000);

						} else {
							S_SkillSound ss1 = new S_SkillSound(
									target2.getId(), 4434);
							target2.sendPackets(ss1);
							Broadcaster.broadcastPacket(target2, ss1);
							ss1 = null;
							new L1SkillDelay(this, 2000);
						}
					} 
					else if (!isKnight()) 
					{
						new L1SkillUse().handleCommands(this, skillId,
								_target2.getId(), _target2.getX(),
								_target2.getY(), null, 0,
								L1SkillUse.TYPE_NORMAL);
					}
					int drandom = _random.nextInt(10) + 1;
					if (drandom > 6 && isDarkelf()) { // 다엘봇 더블
						Broadcaster.broadcastPacket(_target2, new S_SkillSound(
								_target2.getId(), 3398));
						_target2.sendPackets(new S_SkillSound(_target2.getId(),
								3398));
						_target2.receiveDamage(this, 100, false);
					}

//					new L1SkillUse().handleCommands(this, skillId,_target2.getId(), _target2.getX(), _target2.getY(),
//						null, 0, L1SkillUse.TYPE_NORMAL);
//End of adding knight robot stun
					setSleepTime(calcSleepTime(MAGIC_SPEED));
					actionStatus = ATTACK;
					return true;
				}
			}
		}
//		if (_target2.getCurrentHp() > 1 && !isSkillDelay() && getCurrentMp() > 30) {
//			int skillId = 0;
//			int skill_range = 11;
//		
//			if (skillId > 0) {
//				if (CharPosUtil.isAttackPosition(this, target2.getX(), target2.getY(), target2.getMapId(), skill_range) == true
//						&& CharPosUtil.isAttackPosition(target2, getX(), getY(),	getMapId(), skill_range) == true) {
//					new L1SkillUse().handleCommands(this, skillId,_target2.getId(), _target2.getX(), _target2.getY(),null, 0, L1SkillUse.TYPE_NORMAL);
//					setSleepTime(calcSleepTime(MAGIC_SPEED));
//					actionStatus = ATTACK;
//					return true;
//				}
//			}
//		}
		if (CharPosUtil.isAttackPosition(this, target2.getX(), target2.getY(),target2.getMapId(), range) == true
				&& CharPosUtil.isAttackPosition(target2, getX(), getY(),getMapId(), range) == true) {// 基本攻撃範囲
			if (door || !tail) {
				cnt++;
				if (cnt > 5) {
					_target2 = null;
					cnt = 0;
				}
				return false;
			}

			setHeading(targetDirection(target2.getX(),target2.getY()));
			attackTarget(target2);
			actionStatus = ATTACK;
			return true;

		} else {
			//int dir = moveDirection(target2.getX(), target2.getY(),	target2.getMapId());
			int dir = moveDirection(target2.getX(), target2.getY(),	target2.getMapId());
			if (dir == -1) {
				passTargetList2.add(_target2);
				_target2 = null;
				return false;
			} else {
				boolean tail2 = World.isThroughObject(getX(), getY(), getMapId(), dir);
				if (door || !tail2) {
					cnt++;
					if (cnt > 5) {
						_target2 = null;
						cnt = 0;
					}
					return false;
				}
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(MOVE_SPEED));
			}
		}

		return true;
	}

	public void attackTarget(L1Character target) {
		Random random = new Random();
		if (target instanceof L1PcInstance) {
			L1PcInstance player = (L1PcInstance) target;
			if (player.isTeleport())
				return;
		}

		boolean isCounterBarrier = false;
		boolean isMortalBody = false;
		boolean isLindArmor = false;
		boolean isinferno = false;
		L1Attack attack = new L1Attack(this, target);
		if (attack.calcHit()) {
			if (target.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.COUNTER_BARRIER)) {
				int chan = random.nextInt(100) + 1;
				boolean isProbability = false;
				if (20 > chan) {
					isProbability = true;
				}
				boolean isShortDistance = attack.isShortDistance();
				if (isProbability && isShortDistance) {
					isCounterBarrier = true;
				}
			} else if (target.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.MORTAL_BODY)) {
				int chan = random.nextInt(100) + 1;
				boolean isProbability = false;
				if (15 > chan) {
					isProbability = true;
				}
				// boolean isShortDistance = attack.isShortDistance();
				if (isProbability /* && isShortDistance */) {
					isMortalBody = true;
				}
			} else if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.INFERNO)) {
				int chan = random.nextInt(100) + 1;
				boolean isProbability = false;
				if (25 > chan) {
					isProbability = true;
				}
				boolean isShortDistance = attack.isShortDistance();
				if (isProbability && isShortDistance) {
					isinferno = true;
				}
			}
			if (!isCounterBarrier && !isMortalBody && !isLindArmor && !isinferno) {
				attack.calcDamage();
			}
		}
		if (isCounterBarrier) {
			attack.actionCounterBarrier();
			attack.commitCounterBarrier();
		} else if (isMortalBody) {
			attack.actionMortalBody();
			attack.commitMortalBody();
		} else if (isinferno) {
			int TypeRandom = _random.nextInt(100), Type[] = new int[2];
			if(TypeRandom >=95) {
				Type[0] = 17561;
				Type[1] = 4;
			}else if(TypeRandom >=90) {
				Type[0] = 17563;
				Type[1] = 3;
			}else if(TypeRandom >=80) {
				Type[0] = 17565;
				Type[1] = 2;
			}else {
				Type[0] = 17567;
				Type[1] = 1;
			}
			attack.actionInferno(Type[0]);
			attack.commitisInferno(Type[1]);
		} else {
			attack.action();
			attack.commit();
		}
		attack = null;
		setSleepTime(calcSleepTime(ATTACK_SPEED));

	}
	

	// hunting bot
	private void move() {
		move(loc.getX(), loc.getY());
	}

	private L1Location BackLoc_1th = null;
	private L1Location BackLoc_2th = null;
	private int cnt3 = 0;
	private boolean BackRR = false;

	private void move(int x, int y) {
		int dir = moveDirection(x, y, getMapId());
		if (dir == -1) {
			cnt++;
			if (cnt > 20) {
				delay(3000 + _random.nextInt(2000));
				return_(1000 + _random.nextInt(2000));
				cnt = 0;
				/*
				 * if(getMapId() >= 110 && getMapId() <= 179){
				 * System.out.println(hunting bot_location);
				 * System.out.println("return without a place -> "+getName
				 * ()+" X:"+getX()+" Y:"+
				 * getY()+" m:"+getMapId()+" place to go>"+x+" > "+y); }
				 */
				return;
			}
			setSleepTime(1000 + _random.nextInt(1000));
		} else {
			boolean tail2 = World.isThroughObject(getX(), getY(), getMapId(),
					dir);
			boolean door = World.door_to_door(getX(), getY(), getMapId(),
					calcheading(this, x, y));
			if (door || !tail2) {
				cnt++;
				if (cnt > 20) {
					delay(3000 + _random.nextInt(2000));
					return_(1000 + _random.nextInt(2000));
					cnt = 0;
					return;
				}
			}

			setDirectionMove(dir);
			setSleepTime(calcSleepTime(MOVE_SPEED));

			/*
			 * if(BackLoc_1th != null && BackLoc_2th != null &&
			 * getName().equalsIgnoreCase("オフ")){ System.out.println(cnt3);
			 * System.out.println(BackLoc_1th.getX()+" > "+BackLoc_1th.getY());
			 * System.out.println(BackLoc_2th.getX()+" > "+BackLoc_2th.getY());
			 * }
			 */

			if ((BackLoc_1th != null && getLocation().getTileDistance(
					BackLoc_1th) == 0)
					|| (BackLoc_2th != null && getLocation().getTileDistance(
							BackLoc_2th) == 0))
				cnt3++;
			else
				cnt3 = 0;

			if (!BackRR)
				BackLoc_1th = new L1Location(getLocation());
			else
				BackLoc_2th = new L1Location(getLocation());

			BackRR = !BackRR;

			if (cnt3 > 20) {
				delay(3000 + _random.nextInt(2000));
				return_(1000 + _random.nextInt(2000));
				cnt3 = 0;
				/*
				 * if(getMapId() >= 133 && getMapId() <= 139){
				 * System.out.println(hunting bot_location);
				 * System.out.println("infinite loop return -> "+getName
				 * ()+" X:"+getX()+" Y:"+
				 * getY()+" m:"+getMapId()+" place to go>"+x+" > "+y); }
				 */
				return;
			}
		}
	}

	public void tell(int x, int y, int mapid) {
		tell(x, y, mapid, 1, true);
	}

	public void tell(int x, int y, int mapid, int time) {
		tell(x, y, mapid, time, true);
	}

	public void tell(final int x, final int y, final int mapid, int time,
			final boolean effect) {
		if (is_HUNTING_BOT)
			item_queue.clear();
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stubs
				try {
					if (L1RobotInstance.this.isDead()
							|| L1RobotInstance.this.isTeleport()
							|| L1RobotInstance.this.isParalyzed()
							|| L1RobotInstance.this.isSleeped()
							|| L1RobotInstance.this.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DESPERADO)
							|| L1RobotInstance.this.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.PHANTOM))
					return;
			
					setTeleport(true);
					S_SkillSound ss = new S_SkillSound(getId(), 169);
					S_RemoveObject ro = new S_RemoveObject(L1RobotInstance.this);
					for (L1PcInstance pc : L1World.getInstance()
							.getRecognizePlayer(L1RobotInstance.this)) {
						if (effect)
							pc.sendPackets(ss);
						pc.sendPackets(ro);
					}
					Thread.sleep(280);
					for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(L1RobotInstance.this)) {
						pc.getNearObjects().removeKnownObject(L1RobotInstance.this);
						pc.sendPackets(ro); //Tell me not to stay?
						
					}
					L1World.getInstance().moveVisibleObject(
							L1RobotInstance.this, x, y, mapid);
					setX(x);
					setY(y);
					setMap((short) mapid);
					setTeleport(false);
				} catch (Exception e) {
				}
			}
		}, time);
	}

	public L1Location fishing_coordinates = null;
	public L1Location fishing_movement_coordinates = null;
	public boolean is_IM_FISHING_NOW = false;
	public boolean is_FISHING_IS_OVER = false;
	public boolean is_FISHING_LODGE = false;
	public L1NpcInstance fishing_near_telnpc = null;

	private void fishing_bot() {
		// TODO Auto-generated method stubs
		if (is_IM_FISHING_NOW) {
			if (is_FISHING_IS_OVER) {
				if (fishing_near_telnpc == null) {
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(5490).values()) {
						if (obj == null || !(obj instanceof L1NpcInstance))
							continue;
						L1NpcInstance npc = (L1NpcInstance) obj;
						if (fishing_near_telnpc == null)
							fishing_near_telnpc = npc;
						else {
							if (this.getLocation().getTileLineDistance(
									npc.getLocation()) < this.getLocation()
									.getTileLineDistance(
											fishing_near_telnpc.getLocation()))
								fishing_near_telnpc = npc;
						}
					}
				} else {
					if (isDistance(getX(), getY(), getMapId(),
							fishing_near_telnpc.getX(), fishing_near_telnpc.getY(), getMapId(),
							1 + _random.nextInt(3))) {
						is_FISHING_LODGE = true;
						return;
					}
					if (!isParalyzed()) {
						int dir = moveDirection(fishing_near_telnpc.getX(),
								fishing_near_telnpc.getY(), fishing_near_telnpc.getMapId());
						if (dir == -1) {
							cnt++;
							if (cnt > 30) {
								cnt = 0;
								fishing_near_telnpc = null;
								return;
							}
						} else {
							boolean tail2 = World.isThroughObject(getX(),
									getY(), getMapId(), dir);
							boolean door = World.door_to_door(
									getX(),
									getY(),
									getMapId(),
									calcheading(this, fishing_movement_coordinates.getX(),
											fishing_movement_coordinates.getY()));
							if (door || !tail2) {
								cnt++;
								if (cnt > 30) {
									cnt = 0;
									fishing_near_telnpc = null;
									return;
								}
							}
							setDirectionMove(dir);
							setSleepTime(calcSleepTime(MOVE_SPEED));
						}
					}
				}
			}
			return;
		}
		if (fishing_coordinates == null) {
			L1Map map = L1WorldMap.getInstance().getMap((short) 5490);
			int wi = 50;
			while (wi-- > 0) {
				boolean ck = false;
				int x = map.getX() + _random.nextInt(map.getWidth());
				int y = map.getY() + _random.nextInt(map.getHeight());
				if (map.getOriginalTile(x, y) == 28) {
					for (int h = 0; h < 8; h++) {
						if (map.isPassable(x + HEADING_TABLE_X[h], y
								+ HEADING_TABLE_X[h])) {
							ck = true;
							break;
						}
					}
					if (ck)
						continue;
					int i = 50;
					while (i-- > 0) {
						ck = false;
						int tx = x - 3 + _random.nextInt(7);
						int ty = y - 3 + _random.nextInt(7);
						if (map.isPassable(tx, ty)) {
							for (L1Object obj : L1World.getInstance()
									.getVisibleObjects(5490).values()) {
								if (obj == null)
									continue;
								if (obj.getX() == tx && obj.getY() == ty) {
									ck = true;
									break;
								}
							}
							if ((tx >= 32767 && tx <= 32769 && ty >= 32846 && ty <= 32856)
									|| (tx >= 32743 && tx <= 32754
											&& ty >= 32828 && ty <= 32830)
									|| (tx >= 32764 && tx <= 32766
											&& ty >= 32804 && ty <= 32815)
									|| (tx >= 32782 && tx <= 32794
											&& ty >= 32829 && ty <= 32831)) {
							} else if (!ck) {
								fishing_coordinates = new L1Location(x, y, map);
								fishing_movement_coordinates = new L1Location(tx, ty, map);
								return;
							}
						}
					}
				}
			}
		} else {
			if ((getX() >= 32767 && getX() <= 32769 && getY() >= 32846 && getY() <= 32856)
					|| (getX() >= 32743 && getX() <= 32754 && getY() >= 32828 && getY() <= 32830)
					|| (getX() >= 32764 && getX() <= 32766 && getY() >= 32804 && getY() <= 32815)
					|| (getX() >= 32782 && getX() <= 32794 && getY() >= 32829 && getY() <= 32831)) {
			} else if (isDistance(getX(), getY(), getMapId(), fishing_coordinates.getX(),
					fishing_coordinates.getY(), getMapId(), 1 + _random.nextInt(4))) {
				is_IM_FISHING_NOW = true;
				int chdir = calcheading(this, fishing_coordinates.getX(), fishing_coordinates.getY());
				if (getMoveState().getHeading() != chdir) {
					this.getMoveState().setHeading(chdir);
					Broadcaster.broadcastPacket(this,
							new S_ChangeHeading(this), true);
				}
				Broadcaster.broadcastPacket(this, new S_Fishing(getId(),
						ActionCodes.ACTION_Fishing, fishing_coordinates.getX(), fishing_coordinates.getY()),
						true);
				fishX = fishing_coordinates.getX();
				fishY = fishing_coordinates.getY();
				setFishing(true);
				return;
			}
			if (!isParalyzed()) {
				int dir = moveDirection(fishing_movement_coordinates.getX(), fishing_movement_coordinates.getY(),
						fishing_movement_coordinates.getMapId());
				if (dir == -1) {
					cnt++;
					if (cnt > 30) {
						cnt = 0;
						fishing_coordinates = null;
						fishing_movement_coordinates = null;
						return;
					}
				} else {
					boolean tail2 = World.isThroughObject(getX(), getY(),
							getMapId(), dir);
					boolean door = World.door_to_door(getX(), getY(), getMapId(),
							calcheading(this, fishing_movement_coordinates.getX(), fishing_movement_coordinates.getY()));
					if (door || !tail2) {
						cnt++;
						if (cnt > 30) {
							cnt = 0;
							fishing_coordinates = null;
							fishing_movement_coordinates = null;
							return;
						}
					}
					setDirectionMove(dir);
					setSleepTime(calcSleepTime(MOVE_SPEED));
				}
			}
		}
	}

	private int moveDirection(int x, int y, int m) {
		int dir = 0;
		try {
			aStar.cleanTail();
			tail = aStar.searchTail(this, x, y, m, true);
		} catch (Exception e) {
			return -1;
		}
		if (tail != null) {
			iCurrentPath = -1;
			while (!is_THREAD_EXIT && tail != null) {
				if (tail.x == getX() && tail.y == getY()) {
					// 現在位置なら終了
					break;
				}
				if (iCurrentPath >= 299 || isDead()) {
					return -1;
				}
				iPath[++iCurrentPath][0] = tail.x;
				iPath[iCurrentPath][1] = tail.y;
				tail = tail.prev;

			}
			if (iCurrentPath != -1) {
				return aStar.calcheading(getX(), getY(),
						iPath[iCurrentPath][0], iPath[iCurrentPath][1]);
			} else {
				return -1;
			}
		} else {

			try {
				aStar.cleanTail();
				int calcx = (int) getLocation().getX() - loc.getX();
				int calcy = (int) getLocation().getY() - loc.getY();
				if ((Math.abs(calcx) <= 15 && Math.abs(calcy) <= 15)
						&& loc != null) {
					tail = aStar.close_up_search(this, x, y, m, false);
				} else {
					tail = aStar.close_up_search(this, x, y, m, true);
				}
			} catch (Exception e) {
				return -1;
			}
			if (tail != null && !(tail.x == getX() && tail.y == getY())) {
				iCurrentPath = -1;
				while (!is_THREAD_EXIT && tail != null) {
					if (tail.x == getX() && tail.y == getY()) {
						// 現在位置なら終了
						break;
					}
					if (iCurrentPath >= 299 || isDead()) {
						// System.out.println(getName());
						return -1;
					}
					iPath[++iCurrentPath][0] = tail.x;
					iPath[iCurrentPath][1] = tail.y;
					tail = tail.prev;
				}
				if (iCurrentPath != -1) {
					return aStar.calcheading(getX(), getY(),
							iPath[iCurrentPath][0], iPath[iCurrentPath][1]);
				} else {
					dir = -1;
				}
			} else {
				dir = -1;
				if (!is_HUNTING_BOT) {
					int chdir = calcheading(this, x, y);
					if (getMoveState().getHeading() != chdir) {
						this.getMoveState().setHeading(calcheading(this, x, y));
						Broadcaster.broadcastPacket(this, new S_ChangeHeading(
								this), true);
					}
				}
			}

			return dir;
		}
	}

	private static final byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
	private static final byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	private void setDirectionMove(int dir) {
		if (dir >= 0) {
			int nx = 0;
			int ny = 0;
			if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.THUNDER_GRAB)
					|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DESPERADO)
					|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.POWER_GRIP)
					|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DEMOLITION)
					|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ETERNITY)
					|| isPhantomRippered() || isPhantomDeathed()) {
				return;
			}
			// Broadcaster.broadcastPacket(this, new S_ChatPacket(this,
			// ""+사냥맵.getId(), Opcodes.S_OPCODE_NORMALCHAT, 0));
			int heading = 0;
			nx = HEADING_TABLE_X[dir];
			ny = HEADING_TABLE_Y[dir];
			heading = dir;
			int nnx = getX() + nx;
			int nny = getY() + ny;

			if (World.isMapdynamic(nnx, nny, getMapId())) {
				return;
			}
			getMoveState().setHeading(heading);
			L1World.getInstance().Move(this, nnx, nny);
			getMap().setPassable(getLocation(), true);

			setX(nnx);
			setY(nny);
			getMap().setPassable(nnx, nny, false);
			S_MoveCharPacket mp = new S_MoveCharPacket(this);
			Broadcaster.broadcastPacket(this, mp, true);
			actionStatus = MOVE;
		}
	}

	private static final double HASTE_RATE = 0.745;
	private static final double WAFFLE_RATE = 0.874;// 874;
	private static final double THIRDSPEED_RATE = 0.874;// by WANWAN
	
	private static final double Level_Rate_0 = 1.392;
	private static final double Level_Rate_15 = 1.321;
	private static final double Level_Rate_30 = 1.25;
	private static final double Level_Rate_45 = 1.178;
	private static final double Level_Rate_50 = 1.107;
	private static final double Level_Rate_52 = 1.035;
	private static final double Level_Rate_55 = 0.964;
	private static final double Level_Rate_75 = 0.892;
	private static final double Level_Rate_80 = 0.821;
	private static final double Level_Rate_82 = 0.812;
	private static final double Level_Rate_84 = 0.794;
	private static final double Level_Rate_86 = 0.754;
	private static final double Level_Rate_87 = 0.700;
	private static final double Level_Rate_88 = 0.650;

	private static final double Move_Level_Rate_0 = 1.023;
	private static final double Move_Level_Rate_15 = 0.992;
	private static final double Move_Level_Rate_45 = 0.960;
	private static final double Move_Level_Rate_50 = 0.929;
	private static final double Move_Level_Rate_52 = 0.898;
	private static final double Move_Level_Rate_55 = 0.867;
	private static final double Move_Level_Rate_60 = 0.835;
	private static final double Move_Level_Rate_65 = 0.804;
	private static final double Move_Level_Rate_70 = 0.773;
	private static final double Move_Level_Rate_75 = 0.773;
	private static final double Move_Level_Rate_80 = 0.773;

	// cache
	private int _attack_gfxid = -1;
	private int _attack_weapon = -1;
	private int _attack_interval = -1;
	private int _move_gfxid = -1;
	private int _move_weapon = -1;
	private int _move_interval = -1;
	private int _spelldir_gfxid = -1;
	private long _spelldir_interval = -1;
	private int _spellnodir_gfxid = -1;
	private int _spellnodir_interval = -1;
	
	public int calcSleepTime(int type) {
		int interval = 640;
		int gfxid =  getGfxId().getTempCharGfx();
		int weapon = getCurrentWeapon();

		switch (type) {
		case ATTACK:
			if (_attack_gfxid != gfxid || _attack_weapon != weapon) {
				_attack_gfxid = gfxid;
				_attack_weapon = weapon;
				_attack_interval = SprTable.getInstance().getAttackSpeed(gfxid, weapon + 1);
			}

			interval = _attack_interval;

			if (gfxid == 13140 || gfxid == 15154 || gfxid == 15232 || gfxid == 14923 || gfxid == 15223) {
				interval *= Level_Rate_80;
			} else if (gfxid == 17272 || gfxid == 17273 || gfxid == 17274 || gfxid == 17275 || gfxid == 17276 || gfxid == 17277) {
				interval *= Level_Rate_84;
			} else if (gfxid == 16014 || gfxid == 15986 || gfxid == 16008 || gfxid == 16002 || gfxid == 16027) {
				interval *= Level_Rate_86;
			} else if (gfxid == 16284 || gfxid == 16053 || gfxid == 16056 || gfxid == 16074 || gfxid == 16040) {
				interval *= Level_Rate_88;
			} else {
				if ( getLevel() >= 88) {
					interval *= Level_Rate_88;
				} else if ( getLevel() >= 87) {
					interval *= Level_Rate_87;
				} else if ( getLevel() >= 86) {
					interval *= Level_Rate_86;
				} else if ( getLevel() >= 84) {
					interval *= Level_Rate_84;
				} else if ( getLevel() >= 82) {
					interval *= Level_Rate_82;
				} else if ( getLevel() >= 80) {
					interval *= Level_Rate_80;
				} else if ( getLevel() >= 75) {
					interval *= Level_Rate_75;
				} else if ( getLevel() >= 55) {
					interval *= Level_Rate_55;
				} else if ( getLevel() >= 52) {
					interval *= Level_Rate_52;
				} else if ( getLevel() >= 50) {
					interval *= Level_Rate_50;
				} else if ( getLevel() >= 45) {
					interval *= Level_Rate_45;
				} else if ( getLevel() >= 30) {
					interval *= Level_Rate_30;
				} else if ( getLevel() >= 15) {
					interval *= Level_Rate_15;
				} else {
					interval *= Level_Rate_0;
				}
			
			}
			break;

		case MOVE:
			if (_move_gfxid != gfxid || _move_weapon != weapon) {
				_move_gfxid = gfxid;
				_move_weapon = weapon;
				_move_interval = SprTable.getInstance().getMoveSpeed(gfxid,
						weapon);
				// System.out.printf("%s: gfxid:%d, weapon:%d, interval:%d\n",
				//  getName(), gfxid, weapon, _move_interval);
			}
			interval = _move_interval;
			/*
			 * if(gfxid == 11333 || gfxid == 11355 || gfxid == 11364 || gfxid ==
			 * 11379) interval *= 0.9; else if(gfxid == 11343) interval *= 0.8;
			 */
			if (gfxid == 13140) {
				interval *= Move_Level_Rate_80;
			}
			if (gfxid == 11333 || // "lv1 dwarf" ; dwarf
					gfxid == 11343 || // "lv15 ungoliant" ; Ungoliant
					gfxid == 11355 || // "lv30 cockatrice" ; cockatrice
					gfxid == 11364 || // "lv45 baphomet" ; Baphomet
					gfxid == 11379// "lv52 beleth" ; beleth
			) {
				if ( getLevel() >= 80) {
					interval *= Move_Level_Rate_80;
				} else if ( getLevel() >= 75) {
					interval *= Move_Level_Rate_75;
				} else if ( getLevel() >= 70) {
					interval *= Move_Level_Rate_70;
				} else if ( getLevel() >= 65) {
					interval *= Move_Level_Rate_65;
				} else if ( getLevel() >= 60) {
					interval *= Move_Level_Rate_60;
				} else if ( getLevel() >= 55) {
					interval *= Move_Level_Rate_55;
				} else if ( getLevel() >= 52) {
					interval *= Move_Level_Rate_52;
				} else if ( getLevel() >= 50) {
					interval *= Move_Level_Rate_50;
				} else if ( getLevel() >= 45) {
					interval *= Move_Level_Rate_45;
				} else if ( getLevel() >= 15) {
					interval *= Move_Level_Rate_15;
				} else {
					interval *= Move_Level_Rate_0;
				}
			}
			break;

		case MAGIC_SPEED:
			interval = SprTable.getInstance().getNodirSpellSpeed(gfxid);
			if (interval <= 0) {
				interval = 120;
			}
			break;
		case DMG_MOTION_SPEED:
			interval = SprTable.getInstance().getDmgMotionSpeed(gfxid);
			if (interval <= 0) {
				interval = 120;
			}
			break;
//		case SPELL_NODIR:
//			if (_spellnodir_gfxid != gfxid) {
//				_spellnodir_gfxid = gfxid;
//				_spellnodir_interval = SprTable.getInstance()
//						.getNodirSpellSpeed(gfxid);
//			}
//			interval = _spellnodir_interval;
//			break;

		default:
			return 0;
		}
		if ( isHaste()) {
			interval *= HASTE_RATE;
		}
		if (type == MOVE &&  isFastMovable()) {
			interval *= HASTE_RATE;
		}
		if (type == MOVE &&  isIllusionist()
				&&  isUgdraFruit()) {
			interval *= HASTE_RATE;
		}
		if ( isBloodLust()) { // ブラッドラスト
			interval *= HASTE_RATE;
		}
		if ( isSandstorm()) { // サンドストーム
			interval *= HASTE_RATE;
		}
		if ( isFocuswave()) { // サンドストーム
			interval *= HASTE_RATE;
		}
		
		if ( isdarkhos()) { // サンドストーム
			interval *= HASTE_RATE;
		}
		
		if ( isHurricane()) { // ハリケーン
			interval *= HASTE_RATE;
		}

		if ( isBrave()) {
			interval *= HASTE_RATE;
		}
		if ( getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FIRE_BLESS)) {
			interval *= HASTE_RATE;
		}
		if (type == MOVE &&  isElfBrave()) {
			interval *= HASTE_RATE;
		}

		if (type == ATTACK &&  isElfBrave()) {
			interval *= WAFFLE_RATE;
		}
		if ( isThirdSpeed()) {
			interval *= THIRDSPEED_RATE;
		}
		if ( getMapId() == 5143) {
			interval *= (HASTE_RATE / 2);
		}
		if (type == MOVE && (gfxid == 6697 || gfxid == 6698)) {
			interval *= HASTE_RATE;
		}

		return interval;
	}

	public long delay = 0;
	public int move_delay = 0;

	public void delay(int i) {
		delay = i;
		// ディレイ = System.currentTimeMillis() + i;
	}

	private int cnt = 0;

	public void move_bot() {
		if (listbot_move == 1) {
			if (loc == null) {
				if (lisbot_spawn_witch == 2 || lisbot_spawn_witch == 4 || lisbot_spawn_witch == 5
					|| lisbot_spawn_witch == 9 || lisbot_spawn_witch == 8) // ギラン
					loc = new Robot_Location_bean(33437, 32804, 4);
				else if (lisbot_spawn_witch == 6 || lisbot_spawn_witch == 7) // ハイネ
					loc = new Robot_Location_bean(33613, 33248, 4);
				/*else if (리스봇_스폰위치 == 8) // Raden front door
					loc = new Robot_Location_bean(32693, 32794, 450);
				else if (리스봇_스폰위치 == 9) // Uzbekistan
					loc = new Robot_Location_bean(32640, 33183, 4);*/
				else if (lisbot_spawn_witch == 10 || lisbot_spawn_witch == 11) // Gludin
					loc = new Robot_Location_bean(32609, 32738, 4);
				else if (lisbot_spawn_witch == 12) // horse island
					loc = new Robot_Location_bean(32587, 32929, 0);
				else if (lisbot_spawn_witch == 13) // silver knight
					loc = new Robot_Location_bean(33089, 33393, 4);
				else if (lisbot_spawn_witch == 14) // Oren
					loc = new Robot_Location_bean(34065, 32280, 4);
				else if (lisbot_spawn_witch == 15) // Aden
					loc = new Robot_Location_bean(33938, 33358, 4);
			}
		} else if (listbot_move == 2) {
			if (loc == null) {
				if (lisbot_spawn_witch == 2 || lisbot_spawn_witch == 4 || lisbot_spawn_witch == 5
					|| lisbot_spawn_witch == 9 || lisbot_spawn_witch == 8) // Giran
					loc = new Robot_Location_bean(33437, 32795, 4);
				else if (lisbot_spawn_witch == 6 || lisbot_spawn_witch == 7) // Heine
					loc = new Robot_Location_bean(33613, 33257, 4);
				/*else if (리스봇_스폰위치 == 8) // Raden front door
					loc = new Robot_Location_bean(32685, 32795, 450);
				else if (리스봇_스폰위치 == 9) // Uzbekistan
					loc = new Robot_Location_bean(32640, 33189, 4);*/
				else if (lisbot_spawn_witch == 10 || lisbot_spawn_witch == 11) // Gludin
					loc = new Robot_Location_bean(32611, 32732, 4);
				else if (lisbot_spawn_witch == 12) // horse island
					loc = new Robot_Location_bean(32583, 32922, 0);
				else if (lisbot_spawn_witch == 13) // silver knight
					loc = new Robot_Location_bean(33089, 33396, 4);
				else if (lisbot_spawn_witch == 14) // Oren
					loc = new Robot_Location_bean(34063, 32278, 4);
				else if (lisbot_spawn_witch == 15) // Aden
					loc = new Robot_Location_bean(33934, 33351, 4);
			}
		}
		if (loc == null)
			return;

		if (isDistance(getX(), getY(), getMapId(), loc.getX(), loc.getY(),
				getMapId(), 1 + _random.nextInt(3))) {
			loc = null;
			if (listbot_move == 1) {
				listbot_move = 2;
				zendorbuff();
			} else {
				listbot_move = 0;
				tell(32750, 32809, 39, 1000 + _random.nextInt(3000));
				is_THREAD_EXIT = true;
				stopHalloweenRegeneration();
				stopPapuBlessing();
				stopLindBlessing();
				stopHalloweenArmorBlessing();
				stopAHRegeneration();
				stopHpRegenerationByDoll();
				stopMpRegenerationByDoll();
				stopSHRegeneration();
				stopMpDecreaseByScales();
				stopEtcMonitor();
			}
			setSleepTime(4000 + _random.nextInt(2000));
			return;
		}
		if (loc == null)
			return;
		if (!isParalyzed()) {
			int dir = moveDirection(loc.getX(), loc.getY(), loc.getMapId());
			if (dir == -1) {
				cnt++;
			} else {
				boolean tail2 = World.isThroughObject(getX(), getY(),
						getMapId(), dir);
				boolean door = World.door_to_door(getX(), getY(), getMapId(),
						calcheading(this, loc.getX(), loc.getY()));
				if (door || !tail2) {
					cnt++;
				}
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(MOVE_SPEED));
			}
		}
	}

	/*private static final int[] 리스봇BuffSkill4 = {
			L1SkillId.PHYSICAL_ENCHANT_STR, L1SkillId.PHYSICAL_ENCHANT_DEX,
			L1SkillId.BLESS_WEAPON, L1SkillId.REMOVE_CURSE
			};*/
	
	
	private void outsole(){
		try{
		_glment = glmentArray[_random.nextInt(glmentArray.length)];
		Delay(900000000);
		for (L1PcInstance listner : L1World.getInstance().getAllPlayers()){
		S_ChatPacket cp = new S_ChatPacket(this, _glment, Opcodes.S_MESSAGE, 3);
		listner.sendPackets(cp, true);
		setGlsaid(true);
		listner = null; //Leak-proof
		cp = null;
		}
		}catch(Exception e){
		     return;
		}
		}

	

	private void zendorbuff() {
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stubs
				try {
					int[] skillt = leesbot_BuffSkill4;
					if (_random.nextInt(2) == 0) {
						for (Integer i : skillt) {
							L1Skills skill = SkillsTable.getInstance()
									.getTemplate(i);
							if (i == L1SkillId.HASTE)
								new L1SkillUse().handleCommands(
										L1RobotInstance.this, i,
										L1RobotInstance.this.getId(),
										L1RobotInstance.this.getX(),
										L1RobotInstance.this.getY(), null, 0,
										L1SkillUse.TYPE_GMBUFF);
							else
								Broadcaster.broadcastPacket(
										L1RobotInstance.this, new S_SkillSound(
												L1RobotInstance.this.getId(),
												skill.getCastGfx()), true);
						}
						Thread.sleep(1000 + _random.nextInt(1000));
						// 흑사코인
						// Broadcaster.broadcastPacket(L1RobotInstance.this, new
						// S_SkillSound(L1RobotInstance.this.getId(), 4914),
						// true);
					} else {
						// 흑사코인
						// Broadcaster.broadcastPacket(L1RobotInstance.this, new
						// S_SkillSound(L1RobotInstance.this.getId(), 4914),
						// true);
						Thread.sleep(1000 + _random.nextInt(1000));
						for (Integer i : skillt) {
							L1Skills skill = SkillsTable.getInstance()
									.getTemplate(i);
							if (i == L1SkillId.HASTE)
								new L1SkillUse().handleCommands(
										L1RobotInstance.this, i,
										L1RobotInstance.this.getId(),
										L1RobotInstance.this.getX(),
										L1RobotInstance.this.getY(), null, 0,
										L1SkillUse.TYPE_GMBUFF);
							else
								Broadcaster.broadcastPacket(
										L1RobotInstance.this, new S_SkillSound(
												L1RobotInstance.this.getId(),
												skill.getCastGfx()), true);
						}
					}
				} catch (Exception e) {
				}
			}

		}, 1000 + _random.nextInt(1000));
	}

	/**
	 * distance value extraction.
	 * 
	 * @param o
	 * @param oo
	 * @return
	 */
	public int getDistance(int x, int y, int tx, int ty) {
		long dx = tx - x;
		long dy = ty - y;
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * If you're on the street
	 */
	public boolean isDistance(int x, int y, int m, int tx, int ty, int tm,
			int loc) {
		int distance = getDistance(x, y, tx, ty);
		if (loc < distance)
			return false;
		if (m != tm)
			return false;
		return true;
	}

	/**
	 * Used to change direction to the corresponding coordinates.
	 */
	public int calcheading(int myx, int myy, int tx, int ty) {
		if (tx > myx && ty > myy) {
			return 3;
		} else if (tx < myx && ty < myy) {
			return 7;
		} else if (tx > myx && ty == myy) {
			return 2;
		} else if (tx < myx && ty == myy) {
			return 6;
		} else if (tx == myx && ty < myy) {
			return 0;
		} else if (tx == myx && ty > myy) {
			return 4;
		} else if (tx < myx && ty > myy) {
			return 5;
		} else {
			return 1;
		}
	}
	
	

	public void updateban(boolean swich) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE robots SET ban = ? WHERE name = ?");
			if (swich) {
				pstm.setInt(1, 1);
			} else {
				pstm.setInt(1, 0);
			}
			pstm.setString(2, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int _step = 0;

	public void updateconnect(boolean swich) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE robots SET connect = ?,step = ?,map = ? WHERE name = ?");
			if (swich) {
				pstm.setInt(1, 1);
				pstm.setInt(2, _step);
				pstm.setInt(3, 4);
			} else {
				pstm.setInt(1, 0);
				pstm.setInt(2, 0);
				pstm.setInt(3, 0);
			}
			pstm.setString(4, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateclan(String blood_name, int clanid, String title, boolean swich) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE robots SET clanname = ?,clanid = ?,title = ? WHERE name = ?");
			if (swich) {
				pstm.setString(1, blood_name);
				pstm.setInt(2, clanid);
				pstm.setString(3, title);
			} else {
				pstm.setString(1, "");
				pstm.setInt(2, 0);
				pstm.setString(3, "");
			}
			pstm.setString(4, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int calcheading(L1Object o, int x, int y) {
		return calcheading(o.getX(), o.getY(), x, y);
	}
	
	///Violet attack related

	public synchronized int _serchCource(int x, int y) {
		int courceRange = 10;
	
		int i;
		int locCenter = courceRange + 1;
		int mapId = getMapId();
		int diff_x = x - locCenter;
		int diff_y = y - locCenter;
		int[] locBace = { getX() - diff_x, getY() - diff_y, 0, 0 };
		int[] locNext = new int[4];
		int[] locCopy;
		int[] dirFront = new int[5];
		boolean serchMap[][] = new boolean[locCenter * 2 + 1][locCenter * 2 + 1];
		LinkedList queueSerch = new LinkedList();

		for (int j = courceRange * 2 + 1; j > 0; j--) {
			for (i = courceRange - Math.abs(locCenter - j); i >= 0; i--) {
				serchMap[j][locCenter + i] = true;
				serchMap[j][locCenter - i] = true;
			}
		}
		// 32666 32820 32647 32795 19 25
		// locbase = 현재좌표 - (타겟좌표-25)
		// locNext로 복사
		// locNext에 한칸이동
		// locCenter = 26;
		int[] firstCource = { 2, 4, 6, 0, 1, 3, 5, 7 };
		for (i = 0; i < 8; i++) {
			System.arraycopy(locBace, 0, locNext, 0, 4);
			_moveLocation(locNext, firstCource[i]);
			if (locNext[0] - locCenter == 0 && locNext[1] - locCenter == 0)
				return firstCource[i];
			if (serchMap[locNext[0]][locNext[1]]) {
				if (World.isMapdynamic(locNext[0] + diff_x,
						locNext[1] + diff_y, mapId) == false) {
					locCopy = new int[4];
					System.arraycopy(locNext, 0, locCopy, 0, 4);
					locCopy[2] = firstCource[i];
					locCopy[3] = firstCource[i];
					queueSerch.add(locCopy);
				}
				serchMap[locNext[0]][locNext[1]] = false;
			}
		}
		locBace = null;
		while (queueSerch.size() > 0) {
			locBace = (int[]) queueSerch.removeFirst();
			_getFront(dirFront, locBace[2]);
			for (i = 4; i >= 0; i--) {
				System.arraycopy(locBace, 0, locNext, 0, 4);
				_moveLocation(locNext, dirFront[i]);
				if (locNext[0] - locCenter == 0 && locNext[1] - locCenter == 0)
					return locNext[3];
				if (serchMap[locNext[0]][locNext[1]]) {
					if (World.isMapdynamic(locNext[0] + diff_x, locNext[1]
							+ diff_y, mapId) == false) {
						locCopy = new int[4];
						System.arraycopy(locNext, 0, locCopy, 0, 4);
						locCopy[2] = dirFront[i];
						queueSerch.add(locCopy);
					}
					serchMap[locNext[0]][locNext[1]] = false;
				}
			}
			locBace = null;
		}
		return -1;
	}
	
	

	private void _moveLocation(int[] ary, int d) {
		ary[0] = ary[0] + HEADING_TABLE_X[d];
		ary[1] = ary[1] + HEADING_TABLE_Y[d];
		ary[2] = d;
	}

	private void _getFront(int[] ary, int d) {
		switch (d) {
		case 1:
			ary[4] = 2;
			ary[3] = 0;
			ary[2] = 1;
			ary[1] = 3;
			ary[0] = 7;
			break;
		case 2:
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 0;
			ary[1] = 1;
			ary[0] = 3;
			break;
		case 3:
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 1;
			ary[1] = 3;
			ary[0] = 5;
			break;
		case 4:
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 6;
			ary[1] = 3;
			ary[0] = 5;
			break;
		case 5:
			ary[4] = 4;
			ary[3] = 6;
			ary[2] = 3;
			ary[1] = 5;
			ary[0] = 7;
			break;
		case 6:
			ary[4] = 4;
			ary[3] = 6;
			ary[2] = 0;
			ary[1] = 5;
			ary[0] = 7;
			break;
		case 7:
			ary[4] = 6;
			ary[3] = 0;
			ary[2] = 1;
			ary[1] = 5;
			ary[0] = 7;
			break;
		case 0:
			ary[4] = 2;
			ary[3] = 6;
			ary[2] = 0;
			ary[1] = 1;
			ary[0] = 7;
			break;
		default:
			break;
		}
	}
}