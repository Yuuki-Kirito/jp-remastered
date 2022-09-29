package l1j.server.GameSystem.RedKnightEvent;

import java.util.Random;

import javolution.util.FastTable;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.L1SpawnUtil;

public class RedKnight implements Runnable {

	private static Random rnd = new Random(System.currentTimeMillis());
	private int _mapnum = 0;
	private int step = 0;
	private int READY_TIME = 4;
	private int ROUND_1_STEP = 8;
	private int ROUND_2_STEP = 2;
	private int ROUND_3_STEP = 2;
	private int END_TIME = 13;
	private static final int SPAWN = 0;
	private static final int READY = 1;
	private static final int MEMBER_CHECK = 2;
	private static final int ROUND_1 = 3;
	private static final int ROUND_2 = 4;
	private static final int ROUND_3 = 5;
	private static final int END = 6;
	private static final int TIME_OVER = 7;

	private FastTable<L1NpcInstance> bari1 = null;
	private FastTable<L1NpcInstance> bari2 = null;
	private FastTable<L1NpcInstance> bari3 = null;

	private FastTable<L1NpcInstance> miscellaneous_mobs = null;
	private L1NpcInstance boss = null;

	private L1NpcInstance deformation = null;
	private L1NpcInstance red_knights1 = null;
	private L1NpcInstance red_knights2 = null;

	private boolean on = true;

	public RedKnight(int mapid) {
		_mapnum = mapid;
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			private int TIMER = 90;

			@Override
			public void run() {
				try {
					if (!on)
						return;
					if (TIMER == 5) {
						GREEN_MSG("メジャー：急いでください。 5分後に敵の増援軍が到着しそうです。 その前に作戦を完了できなかったら、村に後退しなければなりません。");
					} else if (TIMER == 4) {
						GREEN_MSG("メジャー：急いでください。 4分後に敵の増援群が到着しそうです。");
					} else if (TIMER == 3) {
						GREEN_MSG("メジャー：急いでください。 3分後に敵の増援群が到着しそうです。");
					} else if (TIMER == 2) {
						GREEN_MSG("メジャー：急いでください。 2分後に敵の増援群が到着しそうです。");
					} else if (TIMER == 1) {
						GREEN_MSG("メジャー：急いでください。 1分後に敵の増援群が到着しそうです。");
					} else if (TIMER == 0) {
						GREEN_MSG("メジャー：敵の増援軍が鼻の前まで到着しました。 もう遅滞できないので町に後退します。");
						step = TIME_OVER;
						return;
					}
					TIMER--;
				} catch (Exception e) {
				}
				GeneralThreadPool.getInstance().schedule(this, 60000);
			}
		}, 60000);
	}

	@Override
	public void run() {
		int sleep = 1;
		try {
			switch (step) {
			case SPAWN:
				bari1 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 0);
				bari2 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 1);
				bari3 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 2);
				sleep = 60;
				step++;
				break;
			case READY:
				if (READY_TIME == 4)
					GREEN_MSG("メジャー：4分後に出発する予定です。");
				else
					GREEN_MSG("メジャー： " + READY_TIME
							+ "分後に出発する予定です。 参加人数が10人未満の場合、出正は取り消されます。");
				sleep = 60;
				READY_TIME--;
				if (READY_TIME <= 0)
					step++;
				break;
			case MEMBER_CHECK:
				int count = 0;
				for (L1Object ob : L1World.getInstance()
						.getVisibleObjects(_mapnum).values()) {
					if (ob == null)
						continue;
					if (ob instanceof L1PcInstance)
						count++;
				}
				if (count < 1) {
					GREEN_MSG("メジャー：参加人数不足で今回の出正が取り消されました。 次の出情をお待ちください。");
					Thread.sleep(3000);
					HOME_TELEPORT();
					Object_Delete();
					return;
				} else {
					GREEN_MSG("伝令：出征に先立ち、デポロジュ様が皆さんを励ましに来られる予定です。");
					sleep = 5;
				}
				step++;
				break;
			case ROUND_1:
				sleep = 5;
				if (ROUND_1_STEP == 8) {
					red_knights1 = L1SpawnUtil.spawn4(32772, 32814, (short) _mapnum,
							4, 100660, 0, 0, 0);
					red_knights2 = L1SpawnUtil.spawn4(32768, 32814, (short) _mapnum,
							4, 100660, 0, 0, 0);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(red_knights1, 4, 5), 50);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(red_knights2, 4, 5), 50);
				} else if (ROUND_1_STEP == 7) {
					SHOUT_MSG(red_knights2, "赤い騎士団：集中してください！ デポロジュ様が来ます。");
					deformation = L1SpawnUtil.spawn4(32770, 32814, (short) _mapnum, 4,
							100659, 0, 0, 0);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(deformation, 4, 7), 50);
				} else if (ROUND_1_STEP == 6) {
					SHOUT_MSG(deformation,
							"デポロジュ：こうして私たちの赤い騎士団を助けに来てくれた君たちの労苦を治めるね。");
					sleep = 10;
				} else if (ROUND_1_STEP == 5) {
					SHOUT_MSG(deformation,
							"デポロジュ：手紙と副官を通じて既に伝えられただろうが今回の任務は本当に重要だね。");
					sleep = 10;
				} else if (ROUND_1_STEP == 4) {
					SHOUT_MSG(deformation,
							"デポロジュ：すぐに…前を遮っている方策を崩すから先に進んで手がかりを訪ねてくる。");
					sleep = 10;
				} else if (ROUND_1_STEP == 3) {
					SHOUT_MSG(deformation, "デポロジュ：あなたを信じて、私は帰って城の奪還を準備します。");
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(red_knights1, 0, 5), 2500);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(red_knights2, 0, 5), 2500);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(deformation, 0, 7), 50);
					sleep = 10;
				} else if (ROUND_1_STEP == 2) {
					barry_delete(bari1);
					miscellaneous_mobs = RedKnightSpawn.getInstance()
							.fillSpawnTable(_mapnum, 3);
					GREEN_MSG("伝令：最初の方策が破壊された。 みんな進撃せよ！");
				} else if (ROUND_1_STEP == 1) {
					if (is_MISCELLANEOUS_MOD_CHECK()) {
						boss = L1SpawnUtil.spawn2(32770, 32923, (short) _mapnum,
								100653, 3, 0, 0);// ラミアス
						GREEN_MSG("部隊長：ここをどうやって見つけたの？ 私たちの黒騎士団に触れた罪値を払わないでください！");
					} else {
						GeneralThreadPool.getInstance()
								.schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_1_STEP == 0) {
					if (is_BOSS_CHECK())
						step++;
				}
				if (ROUND_1_STEP != 0)
					ROUND_1_STEP--;
				break;
			case ROUND_2:
				sleep = 5;
				if (ROUND_2_STEP == 2) {
					barry_delete(bari2);
					miscellaneous_mobs = RedKnightSpawn.getInstance()
							.fillSpawnTable(_mapnum, 5);
					GREEN_MSG("伝令：2番目の方策が破壊された。 突撃～これから～～!!");
				} else if (ROUND_2_STEP == 1) {
					if (is_MISCELLANEOUS_MOD_CHECK()) {
						boss = L1SpawnUtil.spawn2(32771, 33009, (short) _mapnum,
								100654, 3, 0, 0);// バロッド
						GREEN_MSG("部隊長：ここまで来たというのは前の部隊を倒したということなのに…簡単に見る奴ではないね。 私が相手にしてください！");
					} else {
						GeneralThreadPool.getInstance()
								.schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_2_STEP == 0) {
					if (is_BOSS_CHECK())
						step++;
				}
				if (ROUND_2_STEP != 0)
					ROUND_2_STEP--;
				break;
			case ROUND_3:
				sleep = 5;
				if (ROUND_3_STEP == 2) {
					barry_delete(bari3);
					miscellaneous_mobs = RedKnightSpawn.getInstance()
							.fillSpawnTable(_mapnum, 7);
					GREEN_MSG("伝令：最後の方策が破壊された。 もう少し力を出しなさい~~!!");
				} else if (ROUND_3_STEP == 1) {
					if (is_MISCELLANEOUS_MOD_CHECK()) {
						boss = L1SpawnUtil.spawn2(32769, 33093, (short) _mapnum,
								100655, 3, 0, 0);// イラストリッパー
						GREEN_MSG("部隊長：噛んで食べても涼しい奴ら！ 私はあなたがたを止めません！");
					} else {
						GeneralThreadPool.getInstance()
								.schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_3_STEP == 0) {
					if (is_BOSS_CHECK())
						step++;
				}
				if (ROUND_3_STEP != 0)
					ROUND_3_STEP--;
				break;
			case END:
				if (END_TIME <= 0) {
					HOME_TELEPORT();
					Object_Delete();
					return;
				} else if (END_TIME == 13) {
					GREEN_MSG("伝令：今回の遠征を成功裏に終えたことについて、デポロジュ様が喜ばれるでしょう。");
					sleep = 3;
				} else if (END_TIME == 12) {
					GREEN_MSG("伝令：獲得した手がかり3種類を「参謀」に持っていってください。");
					sleep = 3;
				} else if (END_TIME == 11) {
					GREEN_MSG("システムメッセージ：1分後に村に強制テレポートされます。");
					sleep = 50;
				} else {
					GREEN_MSG("システムメッセージ： " + END_TIME + "秒");
				}
				END_TIME--;
				break;
			case TIME_OVER:
				HOME_TELEPORT();
				Object_Delete();
				return;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, sleep * 1000);
	}

	private boolean is_BOSS_CHECK() {
		if (boss == null || boss._destroyed || boss.isDead())
			return true;
		return false;
	}

	private boolean is_MISCELLANEOUS_MOD_CHECK() {
		if (miscellaneous_mobs == null || miscellaneous_mobs.size() <= 0)
			return true;
		for (L1NpcInstance npc : miscellaneous_mobs) {
			if (npc == null || npc._destroyed || npc.isDead())
				continue;
			// System.out.println(npc.getX()+" > "+npc.getY());
			return false;
		}
		return true;
	}

	private void barry_delete(FastTable<L1NpcInstance> list) {
		if (list == null || list.size() <= 0)
			return;
		for (L1NpcInstance npc : list) {
			if (npc == null || npc._destroyed)
				return;
			npc.getMap().setPassable(npc.getLocation(), true);
			npc.deleteMe();
		}
	}

	private void GREEN_MSG(String msg) {
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null)
				continue;
			if (ob instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) ob;
				pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, msg));
			}
		}
	}

	private void SHOUT_MSG(L1NpcInstance npc, String msg) {
		Broadcaster
				.broadcastPacket(npc, new S_NpcChatPacket(npc, msg, 2), true);
		/*
		 * for(L1Object ob :
		 * L1World.getInstance().getVisibleObjects(_mapnum).values()){ if(ob ==
		 * null) continue; if(ob instanceof L1PcInstance){ L1PcInstance pc =
		 * (L1PcInstance) ob; pc.sendPackets(new S_SystemMessage(msg, true)); }
		 * }
		 */
	}

	private void HOME_TELEPORT() {
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null)
				continue;
			if (ob instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) ob;
				L1Teleport.teleport(pc, 33436 + rnd.nextInt(12),
						32795 + rnd.nextInt(14), (short) 4, 5, true);
			}
		}
	}

	private void Object_Delete() {
		on = false;
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null || ob instanceof L1DollInstance
					|| ob instanceof L1SummonInstance
					|| ob instanceof L1PetInstance)
				continue;
			if (ob instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) ob;
				npc.deleteMe();
			}
		}
		for (L1ItemInstance obj : L1World.getInstance().getAllItem()) {
			if (obj.getMapId() != _mapnum)
				continue;
			L1Inventory groundInventory = L1World.getInstance().getInventory(
					obj.getX(), obj.getY(), obj.getMapId());
			groundInventory.removeItem(obj);
		}
	}

	class NpcMove implements Runnable {

		private L1NpcInstance npc = null;
		private int count = 0;
		private int direct = 0;

		public NpcMove(L1NpcInstance _npc, int _direct, int _count) {
			npc = _npc;
			count = _count;
			direct = _direct;
		}

		@Override
		public void run() {
			try {
				if (count <= 0) {
					if (direct == 0)
						npc.deleteMe();
					return;
				}
				count--;
				npc.setDirectionMove(direct);
				GeneralThreadPool.getInstance().schedule(this, 640);
			} catch (Exception e) {
			}
		}

	}
}