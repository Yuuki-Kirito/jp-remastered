package l1j.server.GameSystem.Papoo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import l1j.server.Config;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.L1SpawnUtil;

public class PaPooRaidSystem {

	private static PaPooRaidSystem _instance;
	private final Map<Integer, PaPooRaid> _list = new ConcurrentHashMap<Integer, PaPooRaid>();
	private int[] _mapid2 = { 0, 0, 0, 0, 0, 0 };
	private static Random random = new Random(System.nanoTime());

	public static PaPooRaidSystem getInstance() {
		if (_instance == null) {
			_instance = new PaPooRaidSystem();
		}
		return _instance;
	}

	static class papoomsg implements Runnable {
		private int _mapid = 0;
		private int _type = 0;

		public papoomsg(int mapid, int type) {
			_mapid = mapid;
			_type = type;
		}

		@Override
		public void run() {
			try {

				switch (_type) {
				case 0:
					/*
					 * 失敗した時のメンツパプリオン：もう水遊びは終わりだ。 あなたは今私の呪いを避けることはできません！ 巫女サエル
					 * : 今私ができる最後の力で勇者たちを召喚します。 今は仕方がありません。
					 */
					try {
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						S_SystemMessage sm = new S_SystemMessage(
								"パプリオン：今、水遊びは終わりです。 あなたは今私の呪いを避けることはできません！");
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(5000);
						S_SystemMessage sm2 = new S_SystemMessage(
								"巫女サエル：今、私ができる最後の力で勇者を召喚します。 今は仕方がありません。");
						S_SystemMessage sm3 = new S_SystemMessage(
								"20分を超えてレイド失敗！ 5秒後にギラン村に移動します。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
							pc.sendPackets(sm3);
						}
						list = null;
						sm2 = null;
						sm3 = null;
						Thread.sleep(5000);

						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							L1Teleport.teleport(pc, 33432, 32796, (short) 4, 5,
									true);
						}
						list = null;
						PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
								_mapid);
						removeanta(_mapid);
						ar.setPapoo(false);
						ar.clLairUser();
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {
					}
					break;
				case 1:// パプ入場時
					try {
						for (L1Object npc : L1World.getInstance().getObject()) {
							if (npc instanceof L1MonsterInstance) {
								if (npc.getMapId() == _mapid) {
									L1MonsterInstance _npc = (L1MonsterInstance) npc;
									if (!_npc.isDead()
											&& (_npc.getNpcId() == 4039000
													|| _npc.getNpcId() == 4039006 || _npc
													.getNpcId() == 4039007)) {
										return;
									}
								}
							}
						}
						PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
								_mapid);
						ar.setPapoo(true);
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						/*
						 * 入場セメントあえて私の領域に入るなんて… 勇気が仮想だな…
						 * 
						 * この卑劣なパプリオン！ 今私をだましたコメントを払うでしょう！ (巫女サエル)
						 * 
						 * 封印を解くと大変助かりましたが…私には二度の慈悲はありません。
						 * 
						 * あの時は君の奴が俺の骨の中まで呪われたけど…今は違う！ （巫女サエル）1次ヒットスポーン
						 */
						S_SystemMessage sm = new S_SystemMessage(
								"パプリオン：あえて私の領域に入るなんて…勇気が仮想だな…");
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(5000);
						S_SystemMessage sm2 = new S_SystemMessage(
								"巫女サエル：この卑劣なパプリオン！ 今私をだましたコメントを払うでしょう！");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
						Thread.sleep(5000);
						S_SystemMessage sm3 = new S_SystemMessage(
								"パプリオン : 封印を解く時、あなたは大きな助けになったが..私には二度の慈悲はない。");
						for (L1PcInstance pc : antapc) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm3);
						}
						sm3 = null;
						Thread.sleep(5000);
						S_SystemMessage sm4 = new S_SystemMessage(
								"巫女サエル：その時はあなたの男が私の骨の中まで呪いを下したが…今は違う！");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm4);
						}
						list = null;
						sm4 = null;
						PapooRaidSpawn.getInstance().fillSpawnTable(_mapid, 1);
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {
					}
					break;

				case 2:
					/*
					 * 1次ダイメントパプリオン：可塑性！ 彼らがあなたと一緒にイ・スンを思い出す私の供え物なのか！
					 * 巫女サエル：勇者よ！ あの邪悪なパプリオンを倒してエヴァ王国に下された血の呪いを解き放ち
					 * 住所！ パプリオン：遊び感で十分だね！ ほっぺ…
					 */
					try {
						for (L1Object npc : L1World.getInstance().getObject()) {
							if (npc instanceof L1MonsterInstance) {
								if (npc.getMapId() == _mapid) {
									L1MonsterInstance _npc = (L1MonsterInstance) npc;
									if (!_npc.isDead()
											&& (_npc.getNpcId() == 4039000
													|| _npc.getNpcId() == 4039006 || _npc
													.getNpcId() == 4039007)) {
										return;
									}
								}
							}
						}
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}

						S_SystemMessage sm = new S_SystemMessage(
								"パプリオン：可哀想だね！ 彼らがあなたと一緒にイ・スンを思い出す私の供え物なのか！");
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(4000);
						S_SystemMessage sm2 = new S_SystemMessage(
								"巫女サエル：勇者よ！ あの邪悪なパプリオンを倒してエヴァ王国に下された血の呪いを是非解き放ってください！");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
						Thread.sleep(4000);
						S_SystemMessage sm3 = new S_SystemMessage(
								"パプリオン：遊び感で十分だね！ ほっぺ…");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm3);
						}
						list = null;
						sm3 = null;
						Thread.sleep(10000);
						S_SystemMessage sm4 = new S_SystemMessage(
								"パプリオン：骨の中まで掘り起こす恐れが何なのか、この体を知らせてください！");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm4);
						}
						list = null;
						sm4 = null;
						Thread.sleep(10000);

						PapooRaidSpawn.getInstance().fillSpawnTable(_mapid, 2);
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {

					}
					break;
				case 3:
					/*
					 * 二次ダイメント
					 * 
					 * 巫女サエル：今パプリオンの力がたくさん落ちたようです！ 勇者たちもう少し力を出してください！
					 * パプリオン：あなたの奴が希望と呼ぶのが、ただ無駄な夢想だったことを知らせてください！ パプリオン：サエルと
					 * 一緒にしたことを後悔するでしょう！ 愚かな存在です...
					 */
					try {
						for (L1Object npc : L1World.getInstance().getObject()) {
							if (npc instanceof L1MonsterInstance) {
								if (npc.getMapId() == _mapid) {
									L1MonsterInstance _npc = (L1MonsterInstance) npc;
									if (!_npc.isDead()
											&& (_npc.getNpcId() == 4039000
													|| _npc.getNpcId() == 4039006 || _npc
													.getNpcId() == 4039007)) {
										return;
									}
								}
							}
						}
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						S_SystemMessage sm = new S_SystemMessage(
								"巫女サエル：今パプリオンの力がたくさん落ちたようです！ 勇者たちもう少し力を出してください！");
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(4000);
						S_SystemMessage sm2 = new S_SystemMessage(
								"パプリオン：あなたの奴が希望と呼ぶのが、ただ無駄な夢想だったことを知らせてください！");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
						Thread.sleep(10000);

						S_SystemMessage sm3 = new S_SystemMessage(
								"パプリオン：サエルと一緒にしたことを後悔するでしょう！ 愚かな存在です...");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm3);
						}
						list = null;
						sm3 = null;
						Thread.sleep(10000);
						PapooRaidSpawn.getInstance().fillSpawnTable(_mapid, 3);
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {
					}
					break;
				case 4:

					/*
					 * 3次ダイメントパプリオン：サエル..
					 * 息をする。 いよいよ..エヴァ
					 * 王国の長い呪いが解けそうです。
					 */
					try {
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getNetConnection() != null) {
								if (pc.getMapId() == _mapid) {
									antapc.add(pc);
								}
							}
						}
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(new S_SkillSound(pc.getId(), 7783),
									true);
							Broadcaster.broadcastPacket(pc,
									new S_SkillSound(pc.getId(), 7783), true);
							pc.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.DRAGONRAID_BUFF,
									(86400 * 1) * 1000);
							Timestamp deleteTime = new Timestamp(
									System.currentTimeMillis() + (86400000 * Config.RAID_TIME));// 3日
							pc.sendPackets(new S_PacketBox(
									S_PacketBox.DRAGIB_RAID_BUFF, 86400 * 1), true);
							pc.getNetConnection().getAccount()
									.setDragonRaid(deleteTime);
							pc.getNetConnection().getAccount()
									.updateDragonRaidBuff();
							// pc.sendPackets(new
							// S_SystemMessage("드래곤 레이드 마법으로 인해 "+C_SelectCharacter.ss.format(pc.getNetConnection().getAccount().getDragonRaid())+" 이후에 드래곤 포탈 입장이 가능합니다."),
							// true);

							/*
							 * l1skilluse = new L1SkillUse();
							 * l1skilluse.handleCommands(pc,
							 * L1SkillId.DRAGONBLOOD_P, pc.getId(), pc.getX(),
							 * pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
							 * S_PacketBox pb = new
							 * S_PacketBox(S_PacketBox.DRAGONBLOOD, 85, 4320);
							 * pc.sendPackets(pb);pb = null; Timestamp
							 * deleteTime = null; deleteTime = new
							 * Timestamp(System.currentTimeMillis() +
							 * 259200000);// 3일 pc.setpaTime(deleteTime);
							 */
						}
						list = null;
						// 血痕バフ投与
						Thread.sleep(3000);
						S_SystemMessage sm = new S_SystemMessage(
								"パプリオン：サエル..あなたの男が..どのように...私の母..");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(3000);
						S_SystemMessage sm2 = new S_SystemMessage(
								"巫女サエル：ありがとうございます。 いよいよ..エヴァ王国の長い呪いが解けることができるようです。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
						Thread.sleep(2000);
						S_SystemMessage sm3 = new S_SystemMessage(
								"ドワーフの叫び：さあ、ここを去りなさい。 まもなくドアが閉まります。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm3);
						}
						list = null;
						sm3 = null;
						/*
						 * if(GameList.get용땅() == false){
						 * L1World.getInstance().broadcastServerMessage
						 * ("난쟁이의 외침 : 웰던 마을에 숨겨진 용들의 땅으로 가는 문이 열렸습니다.");
						 * L1SpawnUtil.spawn2( 33726, 32506, (short)4 , 4212013,
						 * 0, 1000*60*60*12 , 0); GameList.set용땅(true); }else{
						 * L1World.getInstance().broadcastServerMessage(
						 * "난쟁이의 외침 : 숨겨진 용들의 땅으로 가는 문이 이미 웰던 마을에 열려 있습니다."); }
						 */

						Thread.sleep(2000);
						// アイテム配布

						Thread.sleep(10000);
						S_SystemMessage sm6 = new S_SystemMessage(
								"システムメッセージ：10秒後にテレポートします。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm6);
						}
						list = null;
						sm6 = null;
						Thread.sleep(5000);
						S_SystemMessage sm7 = new S_SystemMessage(
								"システムメッセージ：5秒後にテレポートします。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm7);
						}
						list = null;
						sm7 = null;
						Thread.sleep(1000);
						S_SystemMessage sm8 = new S_SystemMessage(
								"システムメッセージ：4秒後にテレポートします。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm8);
						}
						list = null;
						sm8 = null;
						Thread.sleep(1000);
						S_SystemMessage sm9 = new S_SystemMessage(
								"システムメッセージ：3秒後にテレポートします。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm9);
						}
						list = null;
						sm9 = null;
						Thread.sleep(1000);
						S_SystemMessage sm10 = new S_SystemMessage(
								"システムメッセージ：2秒後にテレポートします。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm10);
						}
						list = null;
						sm10 = null;
						Thread.sleep(1000);
						S_SystemMessage sm11 = new S_SystemMessage(
								"システムメッセージ：1秒後にテレポートします。");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm11);
						}
						list = null;
						sm11 = null;
						Thread.sleep(1000);
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							L1Teleport.teleport(pc, 33718, 32506, (short) 4, 5,
									true);
						}
						list = null;
						exitAR(_mapid);
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {
					}
					break;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void exitAR(int id) {
			for (L1FieldObjectInstance npc : L1World.getInstance()
					.getAllField()) {
				if (npc.moveMapId == id) {
					npc.deleteMe();
				}
			}
			PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(id);
			ar.clLairUser();
			ar.setPapoo(false);
			ar.setPaPoo(null);
			ar.threadOn = false;
		}

		public void removeanta(int id) {
			PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(id);
			L1NpcInstance npc = ar.PaPoo();
			if (npc != null && !npc.isDead()) {
				npc.deleteMe();
			}
		}
	}

	static class FafulionMsgTimer implements Runnable {
		private int _mapid = 0;
		private int _type = 0;

		public FafulionMsgTimer(int mapid, int type) {
			_mapid = mapid;
			_type = type;
		}

		@Override
		public void run() {
			try {
				int idlist[] = { allophus_mega, allophusvia };

				int x = 0, y = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0, x4 = 0, y4 = 0;

				int ranid = 0;
				int swich = 0;
				int ranx = 0;
				int rany = 0;

				switch (_type) {
				case 1:
					x = 32743;
					y = 32862; // 1番部屋
					x1 = 32752;
					y1 = 32809; // 2番部屋

					x2 = 32809;
					y2 = 32813;
					x3 = 32811;
					y3 = 32854;
					x4 = 32860;
					y4 = 32858;// 3番部屋
					break;
				case 2:
					x = 32743;
					y = 32733;
					x1 = 32752;
					y1 = 32680; // 2番部屋

					x2 = 32809;
					y2 = 32685;
					x3 = 32811;
					y3 = 32724;
					x4 = 32860;
					y4 = 32728;// 3番部屋
					break;
				case 3:
					x = 32743;
					y = 32605;
					x1 = 32752;
					y1 = 32552; // 2番部屋

					x2 = 32809;
					y2 = 32557;
					x3 = 32811;
					y3 = 32596;
					x4 = 32860;
					y4 = 32598;// 3番部屋
					break;
				case 4:
					x = 32935;
					y = 32605;
					x1 = 32943;
					y1 = 32552; // 2番部屋

					x2 = 32999;
					y2 = 32557;
					x3 = 33000;
					y3 = 32596;
					x4 = 33050;
					y4 = 32598;// 3番部屋
					break;
				}
				/*
				 * 1679 ベアサムの叫び：すぐにアロパスが集まる。 万全の準備をしてください。 ベア事務の叫び：
				 * 人は高い知性と柔軟な体を持っており、触手の多い人には特に注意してください。 ベア事務の叫び：
				 * アロパスはパプリオンの安息を守るためにハルパスが派遣した手荷物です。 カイム事務の叫び：パプリオンの黒
				 * 息を止めた勇者たちが誕生しました。
				 */
				PaPooRaid PT = PaPooRaidSystem.getInstance().getAR(_mapid);
				// L1Party PT =
				// PaPooRaidSystem.getInstance().getAR(_mapid).getParty(_type);
				S_ServerMessage sm = new S_ServerMessage(1679);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(sm);
				}
				Thread.sleep(2000);
				sm = new S_ServerMessage(1680);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(sm);
				}

				Thread.sleep(2000);
				sm = new S_ServerMessage(1681);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(sm);
				}
				sm = null;
				Thread.sleep(2000);

				for (int i = 0; i < 40; i++) {
					ranid = random.nextInt(2);
					ranx = random.nextInt(15);
					rany = random.nextInt(15);
					swich = random.nextInt(2);
					if (swich == 0) {
						ranx *= -1;
						rany *= -1;
					}
					// 1番部屋スポーン
					L1SpawnUtil.spawn2(x + ranx, y + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					// 2番部屋スポーン
					L1SpawnUtil.spawn2(x1 + ranx, y1 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					L1SpawnUtil.spawn2(x2 + ranx, y2 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					// 3番部屋スポーン
					L1SpawnUtil.spawn2(x3 + ranx, y3 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					L1SpawnUtil.spawn2(x4 + ranx, y4 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static int allophus_mega = 4039012;
	private static int allophusvia = 4039013;

	public boolean startRaid(L1PcInstance pc) {
		checkAR();
		if (_list.size() >= 15) {
			S_SystemMessage sm = new S_SystemMessage(
					"アデンワールドはもはやパプポータルを召喚できません。");
			pc.sendPackets(sm);
			sm = null;
			return false;
		}

		ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(pc,
				0);
		if (list.size() > 0) {
			pc.sendPackets(new S_SystemMessage("この場所にパプポータルを召喚できません。"),
					true);
			return false;
		}
		// pc.getInventory().consumeItem(430116, 1);
		int id = blankMapId();

		PaPooRaid ar = new PaPooRaid(id);

		L1WorldMap.getInstance().cloneMap(1011, id);
		World.cloneMap(1011, id);

		PapooRaidSpawn.getInstance().fillSpawnTable(id, 0);

		L1NpcInstance npc = L1SpawnUtil.spawn2(pc.getX(), pc.getY(),
				pc.getMapId(), 4212016, 0, 7200 * 1000, id);
		L1FieldObjectInstance foi = (L1FieldObjectInstance) npc;
		foi.Potal_Open_pcid = pc.getId();

		L1SpawnUtil.spawn2(32921, 32727, (short) id, 4500103, 0, 0, id);

		L1SpawnUtil.spawn2(32942, 32671, (short) id, 4500107, 0, 0, id);
		_mapid2[id - 1011] = id;
		_list.put(id, ar);
		return true;
	}

	public void checkAR() {
		PaPooRaid ar = null;
		for (int i = 1011; i <= 1016; i++) {
			if (_list.containsKey(i)) {
				ar = _list.get(i);
				if (ar.getEndTime() <= System.currentTimeMillis()
						|| !ar.threadOn) {
					_list.remove(i);
					_mapid2[i - 1011] = 0;
				}
			}
		}
	}

	public PaPooRaid getAR(int id) {
		return _list.get(id);
	}

	/**
	 * 空のマップIDを取得する
	 * 
	 * @return
	 */
	public int blankMapId() {
		int mapid = 1011;
		int a0 = 1011;
		int a1 = 1012;
		int a2 = 1013;
		int a3 = 1014;
		int a4 = 1015;
		int a5 = 1016;
		if (_list.size() >= 1) {
			for (int id : _mapid2) {
				if (id == 1011) {
					a0 = 0;
				}
				if (id == 1012) {
					a1 = 0;
				}
				if (id == 1013) {
					a2 = 0;
				}
				if (id == 1014) {
					a3 = 0;
				}
				if (id == 1015) {
					a4 = 0;
				}
				if (id == 1016) {
					a5 = 0;
				}
			}
		}
		if (a0 != 0) {
			return a0;
		}
		if (a1 != 0) {
			return a1;
		}
		if (a2 != 0) {
			return a2;
		}
		if (a3 != 0) {
			return a3;
		}
		if (a4 != 0) {
			return a4;
		}
		if (a5 != 0) {
			return a5;
		}
		return mapid;
	}

	public int countRaidPotal() {
		return _list.size();
	}
}
