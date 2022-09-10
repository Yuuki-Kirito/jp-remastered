package l1j.server.GameSystem.Robot;

import static l1j.server.server.model.skill.L1SkillId.HASTE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.GMCommands;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.WeaponSkill;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;

public class Robot_Hunt {

	public static boolean �Ⱘ = false;
	private static Random _random = new Random(System.nanoTime());
	private static Queue<L1RobotInstance> _queue;
	private static Robot_Hunt _instance;

	public static Robot_Hunt getInstance() {
		if (_instance == null) {
			_instance = new Robot_Hunt();
		}
		return _instance;
	}

	public Robot_Hunt() {
		_queue = new ConcurrentLinkedQueue<L1RobotInstance>();
		loadbot();
		ArrayList<L1RobotInstance> list = new ArrayList<L1RobotInstance>();
		while (_queue.size() > 0) {
			L1RobotInstance ro = _queue.poll();
			list.add(ro);
		}
		Collections.shuffle(list);
		for (L1RobotInstance ro : list) {
			_queue.offer(ro);
		}
	}

	public void put(L1RobotInstance bot) {
		synchronized (_queue) {
			_queue.offer(bot);
		}
		if (bot.getClanid() != 0) {
			bot.getClan().removeOnlineClanMember(bot.getName());
		}
	}

	// ��踮��������� ���� 15����
	private static final String[] mapName = { "����", 
			"���̱�1", "���̱�2", "���̱�3","���̱�4", "���̱�5", "���̱�6", 
			"����1��", "����2��", "����3��", "����4��", "����ħ����1��",
			"����ħ����2��", "����ħ����3��", "���ڽ���", "�ؼ�",
		    "���̾�Ʈ��", "ȭ��", "���̳����", "���", "ǳ��",
			"���1��", "���2��", "���3��", "���4��", "���5��", "���6��", "���7��", 
			"����1��", "����2��", "����3��", "����4��", "����5��", "����6��", "����7��", "�Ⱘ1��"
             , "�Ⱘ2��" , "�Ⱘ3��" , "�Ⱘ4��"
	         , "���ž4��", "���ž5��", "����8��", "����9��", "����10��" , "���"};

	private static final int[] mapCount = { 0, // ����
			20, 20, 20, 20, 20, 20,// 
			3, 3, 3, 3, // ����
			0, 0, 0,// ħ����
			0,// ���ڽ���
			0, // �ؼ�
			0, 10, 0, 10, 10,//���̾�Ʈ��, ȭ��, ���̳� ���, ���, ǳ��
			3, 3, 3, 3, 3, 0, 0, //���1 -7
			20, 15, 10, 25, 10, 15, 20, 40, 25, 10, 10// ���� +�Ⱘ
		   ,2, 2, //���ž 4�� 5��
         	30,20,20,20};

	public void start_spawn() {
		for (int a = 0; a < mapCount.length; a++) {
			// System.out.println(mapName[a]+" > "+mapCount[a]);
			for (int i = 0; i < mapCount[a]; i++) {
				L1RobotInstance bot = _queue.poll();
				if (bot == null)
					continue;
				GeneralThreadPool.getInstance().schedule(new botVisible(bot, mapName[a]),
						6000 * (_random.nextInt(120) + 1)); //���� 120
				// GeneralThreadPool.getInstance().schedule(new botVisible(bot,
				// mapName[a]), 6000*(_random.nextInt(10)+1));
			}
		}
	}

	private void direct_spawn(String ��ġ) {
		if (!GMCommands.huntBot)
			return;
		synchronized (_queue) {
			L1RobotInstance bot = _queue.poll();
			if (bot == null)
				return;
			GeneralThreadPool.getInstance().schedule(new botVisible(bot, ��ġ),
					1 * (_random.nextInt(2) + 1));
		}
	}

	public void delay_spawn(String ��ġ, int time) {
		if (!GMCommands.huntBot)
			return;
		synchronized (_queue) {
			L1RobotInstance bot = _queue.poll();
			if (bot == null)
				return;
			GeneralThreadPool.getInstance().schedule(new botVisible(bot, ��ġ),
					time);
		}
	}

	// private static boolean spawning = false;
	class botVisible implements Runnable {
		private L1RobotInstance bot;
		private String �����ġ;

		public botVisible(L1RobotInstance bot, String _�����ġ) {
			this.bot = bot;
			this.�����ġ = _�����ġ;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				L1PcInstance rob = L1World.getInstance().getPlayer(bot.getName());
				if (rob != null) {
					put(bot);
					direct_spawn(�����ġ);
					return;
				}
				if (!GMCommands.huntBot) {
					put(bot);
					return;
				}
				if ((bot.isWizard()) 
						&& (�����ġ.equalsIgnoreCase("���") || �����ġ.equalsIgnoreCase("����10��") || �����ġ.equalsIgnoreCase("����8��")
						|| �����ġ.equalsIgnoreCase("���1��") || �����ġ.equalsIgnoreCase("���2��") || �����ġ.equalsIgnoreCase("���3��")
						|| �����ġ.equalsIgnoreCase("���4��") || �����ġ.equalsIgnoreCase("���5��") || �����ġ.equalsIgnoreCase("���6��")
						|| �����ġ.equalsIgnoreCase("���7��") || �����ġ.equalsIgnoreCase("����1��") || �����ġ.equalsIgnoreCase("����2��")
						|| �����ġ.equalsIgnoreCase("����3��") || �����ġ.equalsIgnoreCase("����4��") || �����ġ.equalsIgnoreCase("����5��")
//						|| �����ġ.equalsIgnoreCase("����6��") || �����ġ.equalsIgnoreCase("����7��") || �����ġ.equalsIgnoreCase("�Ⱘ1��")
						|| �����ġ.equalsIgnoreCase("����1��") || �����ġ.equalsIgnoreCase("����2��") || �����ġ.equalsIgnoreCase("����3��")
						|| �����ġ.equalsIgnoreCase("����4��") || �����ġ.equalsIgnoreCase("���") || �����ġ.equalsIgnoreCase("���ž4��")
						|| �����ġ.equalsIgnoreCase("���ž5��")|| �����ġ.equalsIgnoreCase("ȭ��")
						|| �����ġ.equalsIgnoreCase("ǳ��") || �����ġ.equalsIgnoreCase("���̱�1")|| �����ġ.equalsIgnoreCase("���̱�2")
						|| �����ġ.equalsIgnoreCase("���̱�3")|| �����ġ.equalsIgnoreCase("���̱�4")|| �����ġ.equalsIgnoreCase("���̱�5")
						|| �����ġ.equalsIgnoreCase("���̱�6"))) {
					put(bot);
					direct_spawn(�����ġ);
					return;
				}
				if ((!bot.isWizard()) && �����ġ.equalsIgnoreCase("����9��")) {
					put(bot);
					direct_spawn(�����ġ);
					return;
				}
				int map = _random.nextInt(2);
				while (true) {
					// ��ǥ ����
					switch (map) {
					case 0:// ���
						bot.setX(33432 + _random.nextInt(30));
						bot.setY(32811 + _random.nextInt(30));
						break;
					/*case 1:// �����
						bot.setX(33078 + _random.nextInt(6));
						bot.setY(33386 + _random.nextInt(14));
						break;*/
					case 1:// ����
						bot.setX(34055 + _random.nextInt(30));
						bot.setY(32278 + _random.nextInt(30));
						break;
					default:
						break;
					}
					bot.setMap((short) 4); //��������ġ
					boolean ck = false;
					for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(bot, 0)) {
						ck = true;
						break;
					}
					if (ck)
						continue;
					if (bot.getMap().isInMap(bot.getX(), bot.getY())
							&& bot.getMap().isPassable(bot.getX(), bot.getY()))
						break;
					Thread.sleep(100);
				}

				if (�����ġ.equalsIgnoreCase("���ž4��")
						|| �����ġ.equalsIgnoreCase("���ž5��")) {
					bot.getAC().setAc(-80);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(1);
						bot.addDmgup(1);
					} else {
						bot.addHitup(1);
						bot.addDmgup(1);
					}
					bot.addDamageReductionByArmor(1);
				} else if (�����ġ.startsWith("����")) {
					bot.getAC().setAc(-80);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(110);
					} else {
						bot.addHitup(30);
						bot.addDmgup(110);
					}
					bot.addDamageReductionByArmor(1);
				} else if (�����ġ.startsWith("�Ⱘ")) {
					bot.getAC().setAc(-80);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(110);
					} else {
						bot.addHitup(30);
						bot.addDmgup(110);
					}
					bot.addDamageReductionByArmor(1);
				} else if (�����ġ.startsWith("����10��")) {
					bot.getAC().setAc(-120);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(130);
					} else {
						bot.addHitup(30);
						bot.addDmgup(130);
					}
					bot.addDamageReductionByArmor(1);
				} else if (�����ġ.startsWith("����9��")) {
					bot.getAC().setAc(-120);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(130);
					} else {
						bot.addHitup(30);
						bot.addDmgup(130);
					}
					bot.addDamageReductionByArmor(1);
				} else if (�����ġ.startsWith("����8��")) {
					bot.getAC().setAc(-120);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(130);
					} else {
						bot.addHitup(30);
						bot.addDmgup(130);
					}
					bot.addDamageReductionByArmor(1);
				} else if (�����ġ.startsWith("���")) {
					bot.getAC().setAc(-120);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(130);
					} else {
						bot.addHitup(30);
						bot.addDmgup(130);
					}
					bot.addDamageReductionByArmor(1);
				} else {
					bot.getAC().setAc(-80);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(100);
					} else {
						bot.addHitup(30);
						bot.addDmgup(100);
					}
					bot.addDamageReductionByArmor(1);
				}
				/*
				 * }else{ bot.getAC().setAc(-60); bot.addHitup(20);
				 * bot.addBowHitup(20); bot.addDamageReductionByArmor(5); }
				 */
				bot.��ɺ� = true;
				bot.��ɺ�_��ġ = �����ġ;
				bot._���������� = false;
				bot.getMoveState().setHeading(_random.nextInt(8));
				bot.getMoveState().setMoveSpeed(1); 
				bot.getSkillEffectTimerSet().setSkillEffect(HASTE,
						(_random.nextInt(400) + 1700) * 1000);
				if (bot.isKnight() || bot.isCrown() || bot.isWarrior()) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.STATUS_BRAVE,
							(_random.nextInt(600) + 400) * 1000);
					bot.getMoveState().setBraveSpeed(1);
				} else if (bot.isElf()) {
					int ran = _random.nextInt(100) + 1;
					if(bot.getCurrentWeapon() == 20) {
						if(ran < 50) {
					      bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_ELFBRAVE,(_random.nextInt(600) + 400) * 1000);
					      Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 751), true);
					      Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(), 3, 0), true);
						} else {
							if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��Ŀ�����̺�) && 
							    !bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)) {
							     bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��Ŀ�����̺�,(_random.nextInt(600) + 400) * 1000);
							     bot.getMoveState().setBraveSpeed(1);
							     Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 16531), true);
							     Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(), 10, 0), true);
							}
						}
					} else {
						if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FIRE_BLESS)
								&& !bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)) {
							bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.FIRE_BLESS,(_random.nextInt(50) + 250) * 1000);
							bot.getMoveState().setBraveSpeed(1);
							Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 11775), true);
							Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(), 1, 0), true);
						}
						
					}
				} else if (bot.isDragonknight()) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.BLOOD_LUST,
							(_random.nextInt(300) + 200) * 1000);
					bot.getMoveState().setBraveSpeed(1);
				} else if (bot.isIllusionist()) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.MOVING_ACCELERATION,
							(_random.nextInt(600) + 400) * 1000);
					bot.getMoveState().setBraveSpeed(4);
				} else if (bot.isDarkelf()) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.MOVING_ACCELERATION,
							(_random.nextInt(600) + 400) * 1000);
					bot.getMoveState().setBraveSpeed(4);
				}
				L1Clan clan = L1World.getInstance().getClan(bot.getClanname());
				if (clan != null) {
					if (bot.getClanid() == clan.getClanId() && // ũ���� �ػ���, ����,
																// ������ ũ���� â���Ǿ���
																// ���� ��å
							bot.getClanname().toLowerCase().equals(clan.getClanName().toLowerCase())) {
						clan.addOnlineClanMember(bot.getName(), bot);
						for (L1PcInstance clanMember : clan
								.getOnlineClanMember()) {
							if (clanMember.getId() != bot.getId()) {
								// ����, ���Ϳ���%0%s�� ���ӿ� �����߽��ϴ�.
								clanMember.sendPackets(new S_SystemMessage(
										clanMember, "���Ϳ� " + bot.getName()
												+ "�Բ��� ��� ���ӿ� �����ϼ̽��ϴ�."), true);
							}
						}
					}
				}
				Robot.poly(bot);
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.SHAPE_CHANGE, 1800 * 1000);
				L1World.getInstance().storeObject(bot);
				L1World.getInstance().addVisibleObject(bot);
				Robot.clan_join(bot);
				Robot.Doll_Spawn(bot);
				bot.updateconnect(true);
				bot.�ǫ��쫤(3000 + _random.nextInt(15000));
				//if ((_random.nextInt(100)+1) >= 60) //Ÿ�ݱ�ȯ ���� false
				bot.Ÿ�ݱ�ȯ���� = true;
				bot.Hunt_Exit_Time = System.currentTimeMillis()
						+ (600000000 * (60 + _random.nextInt(40)));
				bot.startAI();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void loadbot() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM robots");
			rs = pstm.executeQuery();
			while (rs.next()) {
				L1RobotInstance newPc = new L1RobotInstance();
				newPc.setId(ObjectIdFactory.getInstance().nextId());
				newPc.setAccountName("");
				newPc.setName(rs.getString("name"));
				// rs.getInt("step");
				int level = _random.nextInt(20) + Config.�����۷���;
				newPc.setHighLevel(level);
				newPc.setLevel(level);
				newPc.setExp(ExpTable.getExpByLevel(level)
						+ _random.nextInt(ExpTable.getNeedExpNextLevel(level)));
				newPc.getAC().setAc(-110);
				newPc.addHitup(50);
				newPc.addBowHitup(50);
				newPc.addBowDmgup(100);
				newPc.addDmgup(100);
				newPc.addDamageReductionByArmor(5);

				if (_random.nextInt(100) > 85)
					newPc.setLawful(12767);
				else
					newPc.setLawful(32767);
				newPc.setDead(false);
				newPc.getResistance().addMr(200 + _random.nextInt(30));
				newPc.setTitle(rs.getString("title"));
				newPc.set_sex(rs.getInt("sex"));

				newPc.setClassId(rs.getInt("class"));
				newPc.getGfxId().setTempCharGfx(rs.getInt("class"));
				newPc.getGfxId().setGfxId(rs.getInt("class"));

				int ran = _random.nextInt(100) + 1;
				if (newPc.isKnight()) {
					newPc.setCurrentWeapon(50);
					newPc.setType(1);
				} else if (newPc.isElf()) {
					if (newPc.getGfxId().getTempCharGfx() != Config.�κ�����Ȱ1
							&& newPc.getGfxId().getTempCharGfx() != Config.�κ�����Ȱ2 && newPc.getGfxId().getTempCharGfx() != Config.�κ�����Ȱ3 && newPc.getGfxId().getTempCharGfx() != Config.�κ�����Ȱ4
									 && newPc.getGfxId().getTempCharGfx() != Config.�κ�����Ȱ5
							&& ran < 20)
						newPc.setCurrentWeapon(4);
					else
						newPc.setCurrentWeapon(20);
					newPc.setType(2);
				} else if (newPc.isDarkelf()) {
					newPc.setCurrentWeapon(54);
					newPc.setType(4);
				} else if (newPc.isDragonknight()) {
					//if (ran < 10)
					//	newPc.setCurrentWeapon(4); //�ܰ�
					//else
						newPc.setCurrentWeapon(24); //���
						//newPc.setCurrentWeapon(24);
					newPc.setType(5);
				} else if (newPc.isIllusionist()) {
					//if (ran < 10)
					//	newPc.setCurrentWeapon(40); //������
					//else
						newPc.setCurrentWeapon(58); //Ű��ũ
					newPc.setType(6);
				} else if (newPc.isCrown()) {
					newPc.setCurrentWeapon(4);
					newPc.setType(0);
				} else if (newPc.isWizard()) {
					newPc.setCurrentWeapon(40);
					newPc.setType(3);
				} else if (newPc.isWarrior()) {
					newPc.setCurrentWeapon(88);
					newPc.setType(7);
				}
				StatSetting(newPc);
				HpMpUp(newPc);

				newPc.getMoveState().setMoveSpeed(0);
				newPc.getMoveState().setBraveSpeed(0);
				newPc.getMoveState().setHeading(0);

				newPc.set_food(225);
				newPc.setClanid(rs.getInt("clanid"));
				newPc.setClanname(rs.getString("clanname"));
				newPc.setClanRank(0);
				newPc.setElfAttr(0);
				newPc.set_PKcount(0);
				newPc.setExpRes(0);
				newPc.setPartnerId(0);
				newPc.setAccessLevel((short) 0);
				newPc.setGm(false);
				newPc.setMonitor(false);
				newPc.setHomeTownId(0);
				newPc.setContribution(0);
				newPc.setHellTime(0);
				newPc.setBanned(false);
				newPc.setKarma(0);
				newPc.setReturnStat(0);
				newPc.setGmInvis(false);
				newPc.noPlayerCK = true;
				newPc.setActionStatus(0);
				newPc.setRobot(true);
				newPc.getLight().turnOnOffLight();
						
				if (_random.nextInt(1000) > 200) {
					newPc.setKills(0);
					newPc.setDeaths(0);
				} else {
					newPc.setKills(0);
					newPc.setDeaths(_random.nextInt(10));
				}
				newPc.setNetConnection(null);
				put(newPc);
			}
		} catch (SQLException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static void StatSetting(L1RobotInstance bot) {
		if (bot.isKnight()) {
			bot.getAbility().setBaseStr(20);
			bot.getAbility().setStr(bot.getLevel());
			bot.getAbility().setBaseCon(14);
			bot.getAbility().setCon(14);
			bot.getAbility().setBaseDex(12);
			bot.getAbility().setDex(12);
			bot.getAbility().setBaseCha(12);
			bot.getAbility().setCha(12);
			bot.getAbility().setBaseInt(8);
			bot.getAbility().setInt(8);
			bot.getAbility().setBaseWis(9);
			bot.getAbility().setWis(9);
			bot.addTechniqueHit(Config.���������);
			bot.addSpiritHit(Config.����������);
			bot.addDragonLangHit(Config.���������);
			bot.addFearHit(Config.����������);
			bot.addTechniqueTolerance(Config.���������);
			bot.addSpiritTolerance(Config.�����ɳ���);
			bot.addDragonLangTolerance(Config.����𳻼�);
			bot.addFearTolerance(Config.����������);
		} else if (bot.isElf()) {
			if (bot.getCurrentWeapon() != 20) {
				bot.getAbility().setBaseStr(18);
				bot.getAbility().setStr(bot.getLevel());
				bot.getAbility().setBaseCon(12);
				bot.getAbility().setCon(12);
				bot.getAbility().setBaseDex(12);
				bot.getAbility().setDex(12);
			} else {
				bot.getAbility().setBaseStr(11);
				bot.getAbility().setStr(11);
				bot.getAbility().setBaseCon(13);
				bot.getAbility().setCon(13);
				bot.getAbility().setBaseDex(18);
				bot.getAbility().setDex(bot.getLevel());
			}
			bot.getAbility().setBaseCha(9);
			bot.getAbility().setCha(9);
			bot.getAbility().setBaseInt(12);
			bot.getAbility().setInt(12);
			bot.getAbility().setBaseWis(12);
			bot.getAbility().setWis(12);
			bot.addTechniqueHit(Config.���������);
			bot.addSpiritHit(Config.����������);
			bot.addDragonLangHit(Config.���������);
			bot.addFearHit(Config.����������);
			bot.addTechniqueTolerance(Config.���������);
			bot.addSpiritTolerance(Config.�����ɳ���);
			bot.addDragonLangTolerance(Config.����𳻼�);
			bot.addFearTolerance(Config.����������);
		} else if (bot.isWizard()) {
			bot.getAbility().setBaseStr(8);
			bot.getAbility().setStr(8);
			bot.getAbility().setBaseCon(16);
			bot.getAbility().setCon(16);
			bot.getAbility().setBaseDex(7);
			bot.getAbility().setDex(7);
			bot.getAbility().setBaseCha(8);
			bot.getAbility().setCha(8);
			bot.getAbility().setBaseInt(18);
			bot.getAbility().setInt(bot.getLevel());
			bot.getAbility().setBaseWis(18);
			bot.getAbility().setWis(bot.getLevel());
			bot.getAbility().addSp(45);
			bot.addTechniqueHit(Config.���������);
			bot.addSpiritHit(Config.����������);
			bot.addDragonLangHit(Config.���������);
			bot.addFearHit(Config.����������);
			bot.addTechniqueTolerance(Config.���������);
			bot.addSpiritTolerance(Config.�����ɳ���);
			bot.addDragonLangTolerance(Config.����𳻼�);
			bot.addFearTolerance(Config.����������);
		} else if (bot.isDarkelf()) {
			bot.getAbility().setBaseStr(18);
			bot.getAbility().setStr(bot.getLevel());
			bot.getAbility().setBaseCon(12);
			bot.getAbility().setCon(12);
			bot.getAbility().setBaseDex(15);
			bot.getAbility().setDex(15);
			bot.getAbility().setBaseCha(9);
			bot.getAbility().setCha(9);
			bot.getAbility().setBaseInt(11);
			bot.getAbility().setInt(11);
			bot.getAbility().setBaseWis(10);
			bot.getAbility().setWis(10);
			bot.addTechniqueHit(Config.���������);
			bot.addSpiritHit(Config.����������);
			bot.addDragonLangHit(Config.���������);
			bot.addFearHit(Config.����������);
			bot.addTechniqueTolerance(Config.���������);
			bot.addSpiritTolerance(Config.�����ɳ���);
			bot.addDragonLangTolerance(Config.����𳻼�);
			bot.addFearTolerance(Config.����������);
		} else if (bot.isDragonknight()) {
			bot.getAbility().setBaseStr(18);
			bot.getAbility().setStr(bot.getLevel());
			bot.getAbility().setBaseCon(15);
			bot.getAbility().setCon(15);
			bot.getAbility().setBaseDex(11);
			bot.getAbility().setDex(11);
			bot.getAbility().setBaseCha(8);
			bot.getAbility().setCha(8);
			bot.getAbility().setBaseInt(11);
			bot.getAbility().setInt(11);
			bot.getAbility().setBaseWis(12);
			bot.getAbility().setWis(12);
			bot.addTechniqueHit(Config.���������);
			bot.addSpiritHit(Config.����������);
			bot.addDragonLangHit(Config.���������);
			bot.addFearHit(Config.����������);
			bot.addTechniqueTolerance(Config.���������);
			bot.addSpiritTolerance(Config.�����ɳ���);
			bot.addDragonLangTolerance(Config.����𳻼�);
			bot.addFearTolerance(Config.����������);
		} else if (bot.isIllusionist()) {
			bot.getAbility().setBaseStr(11);
			bot.getAbility().setStr(11);
			bot.getAbility().setBaseCon(17);
			bot.getAbility().setCon(17);
			bot.getAbility().setBaseDex(10);
			bot.getAbility().setDex(10);
			bot.getAbility().setBaseCha(8);
			bot.getAbility().setCha(8);
			bot.getAbility().setBaseInt(17);
			bot.getAbility().setInt(17);
			bot.getAbility().setBaseWis(12);
			bot.getAbility().setWis(12);
			bot.getAbility().addSp(40);
			bot.addTechniqueHit(Config.���������);
			bot.addSpiritHit(Config.����������);
			bot.addDragonLangHit(Config.���������);
			bot.addFearHit(Config.����������);
			bot.addTechniqueTolerance(Config.���������);
			bot.addSpiritTolerance(Config.�����ɳ���);
			bot.addDragonLangTolerance(Config.����𳻼�);
			bot.addFearTolerance(Config.����������);
		}
	}

	private static void HpMpUp(L1RobotInstance bot) {
		bot.addBaseMaxHp((short) (25 + _random.nextInt(5)));
		bot.setCurrentHp(bot.getBaseMaxHp());
		bot.addBaseMaxMp((short) (10 + _random.nextInt(7)));
		bot.setCurrentMp(bot.getBaseMaxMp());
		for (int i = 0; i < bot.getLevel(); i++) {
		 if(bot.isCrown() || bot.isWarrior() || bot.isKnight()) {
			int randomHp = 40;
			int randomMp = 100;
			bot.addBaseMaxHp((short) randomHp);
			bot.addBaseMaxMp((short) randomMp);
			bot.setCurrentHp(bot.getBaseMaxHp());
			bot.setCurrentMp(bot.getBaseMaxMp());
			} else {
				int randomHp = 30;
				int randomMp = 100;
				bot.addBaseMaxHp((short) randomHp);
				bot.addBaseMaxMp((short) randomMp);
				bot.setCurrentHp(bot.getBaseMaxHp());
				bot.setCurrentMp(bot.getBaseMaxMp());
			}
		}
	}
}
