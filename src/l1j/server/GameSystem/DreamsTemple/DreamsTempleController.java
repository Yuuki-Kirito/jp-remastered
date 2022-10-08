package l1j.server.GameSystem.DreamsTemple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javolution.util.FastTable;
import l1j.server.GameSystem.GameList;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1GroundInventory;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Sound;
import l1j.server.server.utils.L1SpawnUtil;

public class DreamsTempleController implements Runnable {

	private static Random rnd = new Random(System.currentTimeMillis());
	private boolean on = true;
	private L1PcInstance pc = null;
	private int _mapnum = 0;
	private int step = 0;
	private int sub_step = 0;
	private int sub_step2 = 0;
	private int round = 0;
	private L1NpcInstance unicorn;
	private FastTable<L1NpcInstance> monster;

	public DreamsTempleController(L1PcInstance _pc, int mapid) {
		pc = _pc;
		_mapnum = mapid;

		L1Teleport.teleport(pc, 32798, 32867, (short) mapid, 5, true);

		DreamsTempleSpawn.getInstance().fillSpawnTable(mapid, 0);
		monster = DreamsTempleSpawn.getInstance().fillSpawnTable(mapid, 1);
		unicorn = L1SpawnUtil.spawn4(32801, 32862, (short) mapid, 4, 100749, 0, 0,
				0);

		GeneralThreadPool.getInstance().schedule(new timer(), 1000);

		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (L1NpcInstance npc : monster) {
					if (npc.getNpcId() == 100747)
						SHOUT_MSG(npc, "誰がどうやって来たの？");
				}
			}
		}, 2000);
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (L1NpcInstance npc : monster) {
					if (npc.getNpcId() == 100748) {
						SHOUT_MSG(npc, "彼はあなたを去らないでしょう。");
						break;
					}
				}
			}
		}, 3000);
	}

	class timer implements Runnable {
		int time = 3600 * 60;
		boolean ck = false;

		@Override
		public void run() {
			try {
				if (!on)
					return;
				if (unicorn == null || unicorn._destroyed || unicorn.isDead()) {
					step = 11;
					return;
				}
				if (!ck) {
					int percent = (int) Math.round((double) unicorn.getCurrentHp()
							/ (double) unicorn.getMaxHp() * 100);
					if (percent < 30) {
						GREEN_MSG("ユニコーンが多すぎるダメージを受けた。");
					}
					ck = true;
				}
				if (time-- < 0) {
					quit();
					return;
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
	private static final byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	class Great_spirit implements Runnable {
		private boolean check = false;
		private L1MonsterInstance government_ordinance = null;

		@Override
		public void run() {
			try {

				if (!on)
					return;
				if (!check) {
					switch (rnd.nextInt(4)) {
					case 0:
						GREEN_MSG("地の大精霊が現れました。");
						government_ordinance = (L1MonsterInstance) L1SpawnUtil.spawn2(32806,
								32862, (short) _mapnum, 100758, 3, 0, 0);
						check = true;
						break;
					case 1:
						GREEN_MSG("風の大精霊が現れました。");
						government_ordinance = (L1MonsterInstance) L1SpawnUtil.spawn2(32801,
								32870, (short) _mapnum, 100759, 3, 0, 0);
						check = true;
						break;
					case 2:
						GREEN_MSG("水の大精霊が現れた。");
						government_ordinance = (L1MonsterInstance) L1SpawnUtil.spawn2(32793,
								32861, (short) _mapnum, 100760, 3, 0, 0);
						check = true;
						break;
					case 3:
						GREEN_MSG("火の大精霊が現れました。");
						government_ordinance = (L1MonsterInstance) L1SpawnUtil.spawn2(32799,
								32854, (short) _mapnum, 100761, 3, 0, 0);
						check = true;
						break;
					default:
						break;
					}

				} else {
					if (government_ordinance._destroyed || government_ordinance.isDead()) {
						GREEN_MSG("大精霊が棒を落としました。 バーを使用すると、戦闘に大きな助けになります。");
						List<Integer> dirList = new ArrayList<Integer>();
						for (int j = 0; j < 8; j++) {
							dirList.add(j);
						}
						for (int i = 0; i < 2; i++) {
							int x = 0;
							int y = 0;
							int dir = 0;
							do {
								if (dirList.size() == 0) {
									x = 0;
									y = 0;
									break;
								}
								int randomInt = rnd.nextInt(dirList.size());
								dir = dirList.get(randomInt);
								dirList.remove(randomInt);
								x = HEADING_TABLE_X[dir];
								y = HEADING_TABLE_Y[dir];
							} while (!government_ordinance.getMap().isPassable(government_ordinance.getX() + x,
									government_ordinance.getY() + y));
							L1GroundInventory targetInventory = L1World
									.getInstance().getInventory(government_ordinance.getX() + x,
											government_ordinance.getY() + y, government_ordinance.getMapId());
							targetInventory.storeItem(60513, 1);
						}
						return;
					}
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class Ruler implements Runnable {
		private int ruler_step = 0;
		private L1MonsterInstance mon = null;

		@Override
		public void run() {
			try {
				if (!on)
					return;
				switch (ruler_step) {
				case 0:
					GREEN_MSG("夢幻の支配者：ユニコーンを奪うつもりですか？ そんなに置いてくれないよ！");
					ruler_step++;
					break;
				case 1:
					Random random = new Random(System.nanoTime());
					int chance = random.nextInt(100);
					if (chance <= 35) {
					mon = (L1MonsterInstance) L1SpawnUtil.spawn2(32794, 32861,
							(short) _mapnum, 110001, 3, 0, 0);//Azmodan
					} else if (chance <= 35) {
						mon = (L1MonsterInstance) L1SpawnUtil.spawn2(32794, 32861, 
								(short) _mapnum, 110000, 3, 0, 0);//Abish
					} else {
						mon = (L1MonsterInstance) L1SpawnUtil.spawn2(32794, 32861,
								(short) _mapnum, 100762, 3, 0, 0);//千年九尾の狐
					}
					ruler_step++;
					break;
				case 2:// 死んだかどうかチェック
					if (mon == null || mon._destroyed || mon.isDead()) {
						step = 5;
						GREEN_MSG("夢幻の支配者が退治しました。");
						unicorn.setCurrentHp(unicorn.getMaxHp());
						return;
					}
					break;
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Random _random = new Random(System.nanoTime());

	@Override
	public void run() {
		try {
			if (!on)
				return;
			if (pc == null) {
				quit();
				return;
			}
			L1PcInstance _pc = L1World.getInstance().getPlayer(pc.getName());
			if (_pc == null || _pc.getMapId() != _mapnum) {
				quit();
				return;
			}
			switch (step) {
			case 0:
				int count = 0;
				L1NpcInstance delnpc = null;
				for (L1NpcInstance npc : monster) {
					if (npc == null || npc._destroyed || npc.isDead()) {
						count++;
						delnpc = npc;
					}
				}
				if (count >= monster.size()) {
					L1GroundInventory targetInventory = L1World.getInstance()
							.getInventory(delnpc.getX(), delnpc.getY(),
									delnpc.getMapId());
					targetInventory.storeItem(60512, 200);
					step++;
					monster = null;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else {
					monster.remove(delnpc);
				}
				break;
			case 1:
				if (sub_step == 0)
					MSG(unicorn, "助けに来てくれてありがとう。");
				else if (sub_step == 1)
					MSG(unicorn, "異界の存在がすぐに戻ります。");
				else if (sub_step == 2)
					MSG(unicorn, "その前に私が封印を解くことができるように時間を稼いでください。");
				else if (sub_step == 3) {
					MSG(unicorn, "魔法のバーを使って敵を倒してください。");
					GREEN_MSG("魔法のバーを使って敵を倒してください。");

				}

				if (sub_step++ >= 3) {
					step++;
				} else {
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				}
			case 2:
				if (round == 0)
					GREEN_MSG("敵が集まってきています。");
				else
					GREEN_MSG("敵がより集まってきます。 準備してください");
				Effect_PEQ();
				Round();
				round++;
				step++;
				sub_step = 0;
				sub_step2 = 0;
				if (round == 3)
					sub_step2 = 1;
				else if (round == 4)
					sub_step2 = 2;
				GeneralThreadPool.getInstance().schedule(this, 5000);
				return;
			case 3:
				if (sub_step++ < 4) {
					DreamsTempleSpawn.getInstance().fillSpawnTable(_mapnum,
							1 + sub_step, unicorn, round != 1);
					if (round == 3 || round == 4)
						GeneralThreadPool.getInstance().schedule(this, 8000);
					else
						GeneralThreadPool.getInstance().schedule(this, 12000);
					return;
				}

				if (sub_step2 > 0) {
					sub_step2--;
					sub_step = 0;
					if (round == 4 && sub_step2 == 0) {
						GeneralThreadPool.getInstance().schedule(new Ruler(),
								7000);
					} else if (round == 4 && sub_step2 == 1) {
						MSG(unicorn, "少し残っていませんでした。 もう少し頑張ってください");
					}
					GeneralThreadPool.getInstance().schedule(this, 2000);
					return;
				}

				if (round >= 4)
					step++;
				else {
					step = 2;
					GeneralThreadPool.getInstance().schedule(new Great_spirit(), 1);
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				}
				break;
			case 4:// 대기 몽환의지배자 죽었는지
				break;
			case 5:
				for (L1Object ob : L1World.getInstance()
						.getVisibleObjects(_mapnum).values()) {
					if (ob instanceof L1MonsterInstance) {
						L1MonsterInstance npc = (L1MonsterInstance) ob;
						if (npc == null || npc._destroyed || npc.isDead())
							continue;
						npc.receiveDamage(pc, 10000);
					}
				}
				step++;
				sub_step = 0;
				GeneralThreadPool.getInstance().schedule(this, 5000);
				return;
			case 6:
				if (sub_step == 0) {
					unicorn.setTempCharGfx(12493);
					pc.sendPackets(new S_ChangeShape(unicorn.getId(), 12493), true);
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 1000);
					return;
				} else if (sub_step == 1) {
					MSG(unicorn, "ありがとう");
					GREEN_MSG("ありがとうございます！");
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else if (sub_step == 2) {
					MSG(unicorn, "しばらくは戻れないでしょう。");
					GREEN_MSG("しばらくは戻れないでしょう。");
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else if (sub_step == 3) {
					MSG(unicorn, "さあ、モンファンの島に戻ってみなければなりませんね。");
					GREEN_MSG("さあ、モンファンの島に戻ってみなければなりませんね。");
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else if (sub_step == 4) {
					MSG(unicorn, "プレゼントを差し上げたいですね。 気に入ってほしいですね。");
					GREEN_MSG("プレゼントを差し上げたいですね。 気に入ってほしいですね。");
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else if (sub_step == 5) {
					int rnd = _random.nextInt(1000) + 1;
					int itemid = 0;

					if (rnd == 5) {
						itemid = 50004;//軸ユニアーマー
					} else if (rnd == 5) {
						itemid = 50003;//ユニアーマー
					} else if (rnd <= 5) {
						itemid = 50005;// 呪いユニアーマー
					} else if (rnd <= 15) {
						itemid = 100030;// 古代神の窓
					} else if (rnd <= 15) {
						itemid = 100031;// 古代神の斧
					} else if (rnd <= 30) {
						itemid = 40074;// 鎧魔法注文書
					} else if (rnd <= 25) {
						itemid = 40087;// 武器魔法注文書
					}

					L1GroundInventory targetInventory = L1World.getInstance()
							.getInventory(unicorn.getX(), unicorn.getY(),
									unicorn.getMapId());
					targetInventory.storeItem(60514, 1);
					if (itemid > 0)
						targetInventory.storeItem(itemid, 1);

					pc.sendPackets(new S_SkillSound(unicorn.getId(), 169));
					pc.sendPackets(new S_RemoveObject(unicorn));
					pc.getNearObjects().removeKnownObject(unicorn);
					unicorn.teleport(32771, 32835, 6);
					step++;
					GeneralThreadPool.getInstance().schedule(this, 20000);
					return;
				}
				break;
			case 7:
				GREEN_MSG("5秒後に村に移動します。");
				step++;
				GeneralThreadPool.getInstance().schedule(this, 3000);
				return;
			case 8:
				GREEN_MSG("2秒後に村に移動します。");
				step++;
				GeneralThreadPool.getInstance().schedule(this, 2000);
				return;
			case 9:
				GREEN_MSG("1秒後に村に移動します。");
				step++;
				GeneralThreadPool.getInstance().schedule(this, 1000);
				return;
			case 10:// 正常終了
				quit();
				break;
			case 11:// ユニコーン死亡
				GREEN_MSG("ユニコーンが消えます。");
				for (L1Object ob : L1World.getInstance()
						.getVisibleObjects(_mapnum).values()) {
					if (ob instanceof L1MonsterInstance) {
						L1MonsterInstance npc = (L1MonsterInstance) ob;
						if (npc == null || npc._destroyed || npc.isDead())
							continue;
						npc.deleteMe();
					}
				}
				step = 7;
				GeneralThreadPool.getInstance().schedule(this, 1000);
				return;
			default:
				break;
			}
		} catch (Exception e) {
		}
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	public int getMapId() {
		return _mapnum;
	}

	private void quit() {
		on = false;
		HOME_TELEPORT();
		DreamsTemple DT = GameList.getDT(getMapId());
		if (DT != null) {
			DT.Reset();
		}
	}

	private void GREEN_MSG(String msg) {
		pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, msg));
	}

	private void Effect_PEQ() {
		pc.sendPackets(new S_Sound(184));
		pc.sendPackets(new S_PacketBox(83, 1));
		pc.sendPackets(new S_PacketBox(83, 2));
	}

	private void Round() {
		pc.sendPackets(new S_PacketBox(S_PacketBox.ROUND_SHOW, round + 1, 4),
				true);
	}

	private void MSG(L1NpcInstance npc, String msg) {
		Broadcaster
				.broadcastPacket(npc, new S_NpcChatPacket(npc, msg, 0), true);
	}

	private void SHOUT_MSG(L1NpcInstance npc, String msg) {
		Broadcaster
				.broadcastPacket(npc, new S_NpcChatPacket(npc, msg, 2), true);
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

}