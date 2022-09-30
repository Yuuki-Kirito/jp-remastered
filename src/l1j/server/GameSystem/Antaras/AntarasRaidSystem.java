/* Eva Pack -http://eva.gg.gg
 * Bonseop Renewed Antaras Raid System
 */

package l1j.server.GameSystem.Antaras;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import l1j.server.Config;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.TrapTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1TrapInstance;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.trap.L1Trap;
import l1j.server.server.model.trap.L1WorldTraps;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.types.Point;
import l1j.server.server.utils.L1SpawnUtil;

public class AntarasRaidSystem {
	// private static Logger _log =
	// Logger.getLogger(AntarasRaidSystem.class.getName());

	private static AntarasRaidSystem _instance;
	private final Map<Integer, AntarasRaid> _list = new ConcurrentHashMap<Integer, AntarasRaid>();
	private int[] _mapid2 = { 0, 0, 0, 0, 0, 0 };
	private static Random random = new Random(System.nanoTime());

	public static AntarasRaidSystem getInstance() {
		if (_instance == null) {
			_instance = new AntarasRaidSystem();
		}
		return _instance;
	}

	static class antamsg implements Runnable {
		private int _mapid = 0;
		private int _type = 0;

		public antamsg(int mapid, int type) {
			_mapid = mapid;
			_type = type;
		}

		public void run() {
			try {
				switch (_type) {
				case 0:// When entering Yong Rare for the first time
					try {
						AntaTrapSpawn();
						AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
								_mapid);
						ar.setAntaras(true);
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						/*
						 * 入場セメントアンタラス：私の睡眠を目覚めさせる！ 誰ですか？ 5秒クレイ：アンタラス！ 君を追う
						 * ここは漆黒の闇まで来た！ アンタラス：可憐ですね。 もう一度殺してください、クレイ！ 一次ヒットスポーン
						 */
						S_SystemMessage sm = new S_SystemMessage(
								"アンタラス：私の睡眠を目覚めさせる！ 誰ですか？");
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
						S_SystemMessage sm1 = new S_SystemMessage(
								"クレイ：アンタラス！ 君を追いかけてここ漆黒の闇までやってきた！");
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
							pc.sendPackets(sm1);
						}
						list = null;
						sm1 = null;
						Thread.sleep(5000);

						S_SystemMessage sm2 = new S_SystemMessage(
								"アンタラス：可憐ですね。 もう一度殺してください、クレイ！");
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
						AntarasRaidSpawn.getInstance()
								.fillSpawnTable(_mapid, 5);

						antapc.clear();
					} catch (Exception exception) {
					}
					break;
				case 1:
					/*
					 * 失敗した時のメンツアンタラス：あなたのやつの無謀さもここまでだ。 ここで終末を迎えろ！ クレイ:
					 * もはや大切な勇者を失うことはできません。 最後の残りの力で今あなたを召喚します。
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
								"アンタラス：あなたのやつの無謀さもここまでだ..！ ここで終末を迎えろ！");
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
						S_SystemMessage sm1 = new S_SystemMessage(
								"クレイ：これ以上貴重な勇者を失うことはできません。 最後の残りの力で今あなたを召喚します。");
						S_SystemMessage sm2 = new S_SystemMessage(
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
							pc.sendPackets(sm1);
							pc.sendPackets(sm2);
						}
						list = null;
						sm1 = null;
						sm2 = null;
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
						AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
								_mapid);
						removeanta(_mapid);
						ar.setAntaras(false);
						ar.clLairUser();
						antapc.clear();

						exitAR(_mapid);
					} catch (Exception exception) {
					}
					break;
				case 2:
					/*
					 * 一次ダイメントアンタラス：愚かな者！ 私の怒りを刺激するね。 クレイ：勇者たち
					 * 剣にアデンの運命がかかっている。 アンタラスの黒い息を止める者はあなただけだ！ アンタラス：こんな
					 * チョムラギたちで私を倒せるようだ！ クハハハ..
					 */
					try {
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						S_SystemMessage sm = new S_SystemMessage(
								"アンタラス：愚かな子よ！ 私の怒りを刺激するね。");
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
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
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(4000);
						S_SystemMessage sm1 = new S_SystemMessage(
								"クレイ：勇者たちがあなたの剣にアデンの運命がかかっている。 アンタラスの黒い息を止める者はあなただけだ！");
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
							pc.sendPackets(sm1);
						}
						list = null;
						sm1 = null;
						Thread.sleep(4000);
						S_SystemMessage sm2 = new S_SystemMessage(
								"アンタラス：こんなコックピットで私を倒せるようだ！ クハハハ..");
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
								"アンタラス：今おいしい食事をしましょうか？ あなたの血の臭いが私を狂わせます。");
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
						AntarasRaidSpawn.getInstance()
								.fillSpawnTable(_mapid, 6);
						antapc.clear();
					} catch (Exception exception) {
					}
					break;
				case 3:
					/*
					 * セカンドダイメントクレイ：ウオオオ服！ 結ばれた原婚の叫び声が聞こえないか！ 死んで！ アンタラス:
					 * あえて私を相手にしようとするなんて。
					 */
					try {
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						S_SystemMessage sm = new S_SystemMessage(
								"クレイ：うおおおお！ 結ばれた原婚の叫び声が聞こえないか！ 死んで！");
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
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
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(4000);
						S_SystemMessage sm1 = new S_SystemMessage(
								"アンタラス：あえて私を相手にしようとしてるなんて。");
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
							pc.sendPackets(sm1);
						}
						list = null;
						sm1 = null;
						Thread.sleep(10000);

						S_SystemMessage sm2 = new S_SystemMessage(
								"アンタラス：私の怒りが空に触れた。 もうすぐ私の父が出るだろう。");
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
						AntarasRaidSpawn.getInstance()
								.fillSpawnTable(_mapid, 7);
						antapc.clear();
					} catch (Exception exception) {
					}
					break;
				case 4:

					/*
					 * セカンドダイメントアンタラス：夕暮れの呪いがあなたにいるように！ シレンよ、私の母よ、私の
					 * 息をする。 途方もない試練
					 * 勝ち、あなたの手にアンタラスの血を埋めたか！ いよいよこの恨みを解くだろうな。 ハハハハハ！ ありがとうございます。 土地
					 * 最強の勇者たちよ！ ドワーフの叫び：ウェルデンの村に隠されたドラゴンの地に行くドアが開かれました。
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
									(86400 * 2) * 1000);
							Timestamp deleteTime = new Timestamp(
									System.currentTimeMillis()
											+ (86400000 * Config.RAID_TIME));// 3日
							pc.sendPackets(new S_PacketBox(
									S_PacketBox.DRAGIB_RAID_BUFF, 86400 * 2), true);
							pc.getNetConnection().getAccount()
									.setDragonRaid(deleteTime);
							pc.getNetConnection().getAccount()
									.updateDragonRaidBuff();
						}
						list = null;
						// 血痕バフ投与
						Thread.sleep(3000);
						S_SystemMessage sm = new S_SystemMessage(
								"アンタラス：夕暮れの呪いが君たちにいるのだ！ シレンよ、私の母よ、私の息を…");
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
						S_SystemMessage sm1 = new S_SystemMessage(
								"クレイ：ああ..最強の勇者であることを証明した最高の記事よ！ 途方もない試練に勝ち、あなたの手にアンタラスの血を埋めたか！ いよいよこの恨みを解くだろうな。 ハハハハハ！ ありがとうございます。 地上に一番強い勇者たちよ！");
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
							pc.sendPackets(sm1);
						}
						list = null;
						sm1 = null;
						Thread.sleep(2000);
						S_SystemMessage sm2 = new S_SystemMessage(
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
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
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
						antapc.clear();
					} catch (Exception exception) {
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private static final int[] traplist = { 3, 98, 99, 100 };

		private void AntaTrapSpawn() {
			L1TrapInstance trap = null;
			L1TrapInstance base = null;
			L1Trap trapTemp = null;
			L1Location loc = null;
			Point rndPt = null;
			for (int trapId : traplist) {
				trapTemp = TrapTable.getInstance().getTemplate(trapId);
				loc = new L1Location();
				loc.setMap(_mapid);
				loc.setX(32784);
				loc.setY(32691);
				rndPt = new Point();
				rndPt.setX(20);
				rndPt.setY(20);
				int count = 18;
				int span = 0;
				int trapDoorId = 0;
				for (int i = 0; i < count; i++) {
					trap = new L1TrapInstance(ObjectIdFactory.getInstance()
							.nextId(), trapTemp, loc, rndPt, span, trapDoorId);
					trap.setRespawn(false);
					L1World.getInstance().addVisibleObject(trap);
					L1WorldTraps.getInstance().addTrap(trap);
					// System.out.println(trap.getX()
					// +" > "+trap.getY()+" > "+trap.getMapId());
				}
				base = new L1TrapInstance(ObjectIdFactory.getInstance()
						.nextId(), loc);
				base.setRespawn(false);
				L1World.getInstance().addVisibleObject(base);
				L1WorldTraps.getInstance().addBase(base);
			}
		}

		public void exitAR(int id) {
			for (L1FieldObjectInstance npc : L1World.getInstance()
					.getAllField()) {
				if (npc.moveMapId == id) {
					npc.deleteMe();
				}
			}
			AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(id);
			ar.clLairUser();
			ar.setAntaras(false);
			ar.setanta(null);
			ar.MiniBossReset();
			ar.threadOn = false;
		}

		public void removeanta(int id) {
			AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(id);
			L1NpcInstance npc = ar.anta();
			if (npc != null && !npc.isDead()) {
				npc.deleteMe();
			}
		}
	}

	static class AntarasMsgTimer implements Runnable {
		private int _mapid = 0;
		private int _type = 0;

		public AntarasMsgTimer(int mapid, int type) {
			_mapid = mapid;
			_type = type;
		}

		public void run() {
			try {
				int idlist[] = { comaume, comacin };

				int x = 0, y = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0, x4 = 0, y4 = 0;

				int ranid = 0;
				int ranx = 0;
				int rany = 0;

				switch (_type) {
				case 1:
					x = 32663;
					y = 32792;
					x1 = 32613;
					y1 = 32815;
					x2 = 32631;
					y2 = 32845;
					x3 = 32671;
					y3 = 32865;
					x4 = 32623;
					y4 = 32916;
					break;
				case 2:
					x = 32919;
					y = 32600;
					x1 = 32870;
					y1 = 32624;
					x2 = 32886;
					y2 = 32655;
					x3 = 32925;
					y3 = 32679;
					x4 = 32881;
					y4 = 32713;
					break;
				case 3:
					x = 32919;
					y = 32792;
					x1 = 32868;
					y1 = 32819;
					x2 = 32886;
					y2 = 32846;
					x3 = 32924;
					y3 = 32871;
					x4 = 32881;
					y4 = 32907;
					break;
				case 4:
					x = 32791;
					y = 32792;
					x1 = 32759;
					y1 = 32844;
					x2 = 32740;
					y2 = 32817;
					x3 = 32798;
					y3 = 32869;
					x4 = 32747;
					y4 = 32903;
					break;
				}

				AntarasRaidSpawn.getInstance().fillSpawnTable(_mapid, _type); // egg
																				// spawn
				AntarasRaid PT = AntarasRaidSystem.getInstance().getAR(_mapid);
				// L1Party PT =
				// AntarasRaidSystem.getInstance().getAR(_mapid).getParty(_type);
				S_ServerMessage smm = new S_ServerMessage(1588);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(smm);
				}
				smm = null;
				Thread.sleep(2000);
				S_ServerMessage smm1 = new S_ServerMessage(1589);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(smm1);
				}
				smm1 = null;
				Thread.sleep(2000);
				S_ServerMessage smm2 = new S_ServerMessage(1590);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(smm2);
				}
				smm2 = null;
				Thread.sleep(2000);
				S_ServerMessage smm3 = new S_ServerMessage(1591);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(smm3);
				}
				smm3 = null;
				for (int i = 0; i < 40; i++) {
					ranid = random.nextInt(2);
					ranx = random.nextInt(15);
					rany = random.nextInt(15);
					// Room 1 spawn
					L1SpawnUtil.spawn2(x + ranx, y + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					// 2nd room spawn
					L1SpawnUtil.spawn2(x1 + ranx, y1 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					L1SpawnUtil.spawn2(x2 + ranx, y2 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					// Room 3 spawn
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

	private static int comaume = 4038001;
	private static int comacin = 4038002;

	public boolean startRaid(L1PcInstance pc) {
		checkAR();
		if (_list.size() >= 5) {
			pc.sendPackets(
					new S_SystemMessage("アデンワールドは、もはやヒットポータルを召喚できません。"), true);
			return false;
		}

		ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(pc,
				0);
		if (list.size() > 0) {
			pc.sendPackets(new S_SystemMessage("この場所にヒットポータルを召喚できません。"),
					true);
			return false;
		}

		// pc.getInventory().consumeItem(430116, 1);
		int id = blankMapId();

		AntarasRaid ar = new AntarasRaid(id);

		L1WorldMap.getInstance().cloneMap(1005, id);
		World.cloneMap(1005, id);
		AntarasRaidSpawn.getInstance().fillSpawnTable(id, 0);

		L1NpcInstance npc = L1SpawnUtil.spawn2(pc.getX(), pc.getY(),
				pc.getMapId(), 4212015, 0, 7200 * 1000, id);
		L1FieldObjectInstance foi = (L1FieldObjectInstance) npc;
		foi.Potal_Open_pcid = pc.getId();

		L1SpawnUtil.spawn2(32680, 32744, (short) id, 4500101, 0, 0, id);

		L1SpawnUtil.spawn2(32703, 32670, (short) id, 4500102, 0, 0, id);

		_mapid2[id - 1005] = id;
		_list.put(id, ar);
		return true;
	}

	public void checkAR() {
		AntarasRaid ar = null;
		for (int i = 1005; i <= 1010; i++) {
			if (_list.containsKey(i)) {
				ar = _list.get(i);
				if (ar.getEndTime() <= System.currentTimeMillis()
						|| !ar.threadOn) {
					_list.remove(i);
					_mapid2[i - 1005] = 0;
				}
			}
		}
	}

	public AntarasRaid getAR(int id) {
		return _list.get(id);
	}

	/**
	 * get empty map id
	 * 
	 * @return
	 */
	public int blankMapId() {
		int mapid = 1005;
		int a0 = 1005;
		int a1 = 1006;
		int a2 = 1007;
		int a3 = 1008;
		int a4 = 1009;
		int a5 = 1010;
		if (_list.size() >= 1) {
			for (int id : _mapid2) {
				if (id == 1005) {
					a0 = 0;
				}
				if (id == 1006) {
					a1 = 0;
				}
				if (id == 1007) {
					a2 = 0;
				}
				if (id == 1008) {
					a3 = 0;
				}
				if (id == 1009) {
					a4 = 0;
				}
				if (id == 1010) {
					a5 = 0;
				}
			}
		}
		if (a0 != 0) {
			System.out.println("Antarade map generation: 1005");
			return a0;
		}
		if (a1 != 0) {
			System.out.println("Antarade map generation: 1006");
			return a1;
		}
		if (a2 != 0) {
			System.out.println("Create Antarade Map: 1007");
			return a2;
		}
		if (a3 != 0) {
			System.out.println("Create Antarade Map: 1008");
			return a3;
		}
		if (a4 != 0) {
			System.out.println("Create Antarade Map: 1009");
			return a4;
		}
		if (a5 != 0) {
			System.out.println("Create Antarade Map: 1010");
			return a5;
		}
		return mapid;
	}

	public int countRaidPotal() {
		return _list.size();
	}
}
