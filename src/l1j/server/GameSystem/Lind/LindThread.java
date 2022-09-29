package l1j.server.GameSystem.Lind;

import static l1j.server.server.model.skill.L1SkillId.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Random;

import javolution.util.FastMap;
import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseAttackSkill;

public class LindThread extends Thread {

	private FastTable<Lind> _list;
	private Random _rnd = new Random(System.nanoTime());

	private static LindThread _instance;

	public static LindThread get() {
		if (_instance == null)
			_instance = new LindThread();
		return _instance;
	}

	public LindThread() {
		super("l1j.server.GameSystem.LindThread");
		_list = new FastTable<Lind>();
		start();
	}

	public void run() {
		int size = 0;
		while (true) {
			try {
				Lind[] list = null;
				synchronized (_list) {
					if ((size = _list.size()) > 0) {
						list = _list.toArray(new Lind[size]);
					}
				}
				if (size > 0) {
					for (Lind lind : list) {
						if (lind == null) {
							_list.remove(lind);
							continue;
						}
						if (lind.getEndTIme() < System.currentTimeMillis())
							quit(lind);

						/** user map check **/
						mapUserCheck(lind);

						if (lind.Sleep > System.currentTimeMillis())
							continue;
						Sleep_Setting(lind);
						switch (lind.Step) {
						case 1: // 레어 입장
							if (lind.Sub_Step == 1)
								SystemChat(lind, "リンドビオル：誰が私の短眠を妨げるのか？");
							else if (lind.Sub_Step == 2)
								SystemChat(lind,
										"リンドビオル：ケレニスまた私を怒らせたいのか？");
							else if (lind.Sub_Step == 3)
								SystemChat(lind, "リンドビオル：愚かな人間だね…");
							else if (lind.Sub_Step == 4)
								SystemChat(lind,
										"リンドビオル：私はリンドビオルを怒らせた対価を払うだろう。");
							else if (lind.Sub_Step == 5)
								lind.dragon_lind = LindSpawn.getInstance()
										.fillSpawnTable(lind.getMap().getId(),
												1); // リンドスポーン
							lind.Sub_Step++;
							if (lind.Sub_Step == 6) {
								lind.Step = 2;
								lind.Sub_Step = 0;
							}
							break;
						case 2: // リンドナム
							if (lind.dragon_lind == null
									|| lind.dragon_lind.isDead()
									|| lind.dragon_lind._destroyed) {
								lind.Step = 3;
								// giveItem(lind, 787878);
								// SystemChat(lind,
								// "린드비오르 가 달아난 드래곤의 흔적을 주었습니다.");
							}
							break;
						case 3: // 린드 2페 공중
							if (lind.Sub_Step == 0)
								SystemChat(lind,
										"リンドビオル：可塑だね！ あなたたちの愚かさを骨の奥深く後悔するようにしてくれる！");
							else if (lind.Sub_Step == 1)
								SystemChat(lind,
										"リンドビオル：それでも結構だね！ しかし、いつまで耐えることができますか？");
							else if (lind.Sub_Step == 2)
								SystemChat(lind,
										"リンドビオル：長い間体を解けなかったが、遊んでみよう！");
							else if (lind.Sub_Step == 3)
								Lind_level2_setting(lind);
							else if (lind.Sub_Step == 5)
								cloud_npcChat(lind,
										"雲大政令：リンドヴィオル様の聖所に侵入した人は誰ですか？");
							else if (lind.Sub_Step == 6)
								cloud_npcChat(lind,
										"雲大精霊：うわー..あえてリンドビオル様の植栽を不便にするなんて！");
							else if (lind.Sub_Step == 7)
								cloud_npcChat(lind, "雲大精霊：あえて！ 止まらない！");
							else if (lind.Sub_Step == 8)
								cloud_npcChat(lind, "雲大精霊：雲の精霊が私に力を！");

							if (lind.Sub_Step != 4 && lind.Sub_Step != 9)
								lind.Sub_Step++;
							if (lind.Sub_Step >= 4) {
								if (lind.dragon_lind != null
										&& (lind.dragon_lind.getMaxHp() / 2) > lind.dragon_lind
												.getCurrentHp()
										&& !((L1MonsterInstance) lind.dragon_lind).lind_level2_cloud) {
									((L1MonsterInstance) lind.dragon_lind).lind_level2_cloud = true;
									Broadcaster.broadcastPacket(
											lind.dragon_lind,
											new S_NpcChatPacket(
													lind.dragon_lind,
													"ベン・ライル！ スベルケ..", 0), true);
									lind.cloud_list = LindSpawn.getInstance()
											.fillSpawnTable(
													lind.getMap().getId(), 4,
													true); // 雲大精霊
									lind.Sub_Step++;
								}
								if (lind.dragon_lind == null
										|| lind.dragon_lind.isDead()
										|| lind.dragon_lind._destroyed) {
									Lind_level2_die(lind);
									lind.Step = 4;
									lind.Sub_Step = 0;
									// giveItem(lind, 787878);
									// SystemChat(lind,
									// "린드비오르 가 달아난 드래곤의 흔적을 주었습니다.");
								}
								continue;
							}
							break;
						case 4: // 린드 3페
							if (lind.Sub_Step == 0)
								SystemChat(lind,
										"リンドビオル：こんな面倒…愚かなことよ～～今私を試してるのか？");
							else if (lind.Sub_Step == 1)
								SystemChat(lind, "リンドビオル：さて、もう少し足を踏み入れてください〜〜");
							else if (lind.Sub_Step == 2)
								SystemChat(lind,
										"リンドビオル：あなたの男たちの恐ろしさを責めるだろう。");
							else if (lind.Sub_Step == 3)
								SystemChat(lind,
										"リンドビオル：あなたはそれをもう見ないでしょう。 再起動しよう！");
							else if (lind.Sub_Step == 4)
								lind.dragon_lind = LindSpawn.getInstance()
										.fillSpawnTable(lind.getMap().getId(),
												3); // 린드 스폰
							else if (lind.Sub_Step == 5) {
								if (lind.dragon_lind == null
										|| lind.dragon_lind.isDead()
										|| lind.dragon_lind._destroyed) {
									lind.Step = 6;
									lind.Sub_Step = 0;
									continue;
								}
								if (((L1MonsterInstance) lind.dragon_lind).lind_fly) {
									lind.Step = 5;
									lind.Sub_Step = 0;
								}
								continue;
							}
							lind.Sub_Step++;
							break;
						case 5: // 린드 낙뢰
							if (lind.dragon_lind == null
									|| lind.dragon_lind.isDead()
									|| lind.dragon_lind._destroyed) {
								lind.Step = 6;
								lind.Sub_Step = 0;
								continue;
							}
							if (lind.Sub_Step == 0) {
							} else if (lind.Sub_Step < 11) {
								int x = 32838 + _rnd.nextInt(22);
								int y = 32866 + _rnd.nextInt(22);
								for (L1PcInstance pc : lind.getMember()) {
									S_UseAttackSkill uas = new S_UseAttackSkill(
											pc, 0, 10, x, y, 13, false);
									S_UseAttackSkill uas2 = new S_UseAttackSkill(
											pc, 0, 8118, x, y, 13, false);
									pc.sendPackets(uas);
									uas = null;
									pc.sendPackets(uas2);
									uas2 = null;
									S_PacketBox pb = new S_PacketBox(83, 2);
									pc.sendPackets(pb);
									pb = null;
									if (pc.getX() == x && pc.getY() == y) {
										if (!pc.getSkillEffectTimerSet()
												.hasSkillEffect(EARTH_BIND)
												&& !pc.getSkillEffectTimerSet()
														.hasSkillEffect(
																ICE_LANCE)
												&& !pc.getSkillEffectTimerSet()
														.hasSkillEffect(
																FREEZING_BREATH))
											pc.receiveDamage(lind.dragon_lind,
													5000, true);
									}
								}
							} else if (lind.Sub_Step == 11) {
								if (lind.dragon_lind.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_FLY) {
									lind.dragon_lind
											.setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_NONE);
									Broadcaster.broadcastPacket(
											lind.dragon_lind,
											new S_DoActionGFX(lind.dragon_lind
													.getId(), 11), true);
									lind.dragon_lind.setActionStatus(11);
									Broadcaster.broadcastPacket(
											lind.dragon_lind, new S_NPCPack(
													lind.dragon_lind), true);
								}
							} else {
								continue;
							}
							lind.Sub_Step++;
							break;
						case 6: // 린드 죽음
							if (lind.Sub_Step == 0)
								SystemChat(lind,
										"リンドビオル：こんなことが！ 大きなああ…");
							else if (lind.Sub_Step == 1)
								SystemChat(lind,
										"リンドビオル：ああ〜！ 私の母シレンは私を捕まえて住んでいます...");
							else if (lind.Sub_Step == 2) {
								// 혈흔
								health(lind);
								giveItem(lind, 5000065);
								// SystemChat(lind, "린드비오르가 죽으면서 증표를 남겼습니다.");
							} else if (lind.Sub_Step == 3)
								SystemChat(lind, "システムメッセージ：10秒後にテレポートします。");
							else if (lind.Sub_Step == 4)
								SystemChat(lind, "システムメッセージ：5秒後にテレポートします。");
							else if (lind.Sub_Step == 5)
								SystemChat(lind, "システムメッセージ：4秒後にテレポートします。");
							else if (lind.Sub_Step == 6)
								SystemChat(lind, "システムメッセージ：3秒後にテレポートします。");
							else if (lind.Sub_Step == 7)
								SystemChat(lind, "システムメッセージ：2秒後にテレポートします。");
							else if (lind.Sub_Step == 8)
								SystemChat(lind, "システムメッセージ：1秒後にテレポートします。");
							else {
								lind.Step = 7;
								lind.Sub_Step = 0;
								continue;
							}
							lind.Sub_Step++;
							break;
						case 7: // 종료
							home(lind);
							quit(lind);

							break;
						default:
							break;
						}
						// System.out.println("스탭 >> "+lind.Step);
					}
				}
				list = null;
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** Bloodstains & Items **/
	private void health(Lind lind) {
		// L1SkillUse l1skilluse;
		S_PacketBox pb = new S_PacketBox(S_PacketBox.DRAGONBLOOD, 88, 4320);
		for (L1PcInstance pc : lind.getMember()) {
			pc.sendPackets(new S_SkillSound(pc.getId(), 7783), true);
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7783),
					true);
			pc.getSkillEffectTimerSet().setSkillEffect(
					L1SkillId.DRAGONRAID_BUFF, (86400 * 2) * 1000);
			Timestamp deleteTime = new Timestamp(System.currentTimeMillis()
					+ (86400000 * Config.RAID_TIME));// 7일
			pc.getNetConnection().getAccount().setDragonRaid(deleteTime);
			pc.getNetConnection().getAccount().updateDragonRaidBuff();
			pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGIB_RAID_BUFF, 86400 * 2),
					true);
			// pc.sendPackets(new
			// S_SystemMessage("드래곤 레이드 마법으로 인해 "+C_SelectCharacter.ss.format(pc.getNetConnection().getAccount().getDragonRaid())+" 이후에 드래곤 포탈 입장이 가능합니다."),
			// true);

			/*
			 * l1skilluse = new L1SkillUse(); l1skilluse.handleCommands(pc,
			 * L1SkillId.DRAGONBLOOD_L, pc.getId(), pc.getX(), pc.getY(), null,
			 * 0, L1SkillUse.TYPE_GMBUFF); l1skilluse = null;
			 * pc.sendPackets(pb); Timestamp deleteTime = null; deleteTime = new
			 * Timestamp(System.currentTimeMillis() + 259200000);// 3일
			 * pc.setlindTime(deleteTime);
			 */
		}
		pb = null;
		L1World.getInstance().broadcastServerMessage(
				"ドワーフの叫び：リンドビオールの翼を破った勇者たちが誕生しました。");
		/*
		 * if(GameList.get용땅() == false){
		 * L1World.getInstance().broadcastServerMessage
		 * ("난쟁이의 외침 : 웰던 마을에 숨겨진 용들의 땅으로 가는 문이 열렸습니다."); L1SpawnUtil.spawn2(
		 * 33726, 32506, (short)4 , 4212013, 0, 1000*60*60*12 , 0);
		 * GameList.set용땅(true); }else{
		 * L1World.getInstance().broadcastServerMessage
		 * ("난쟁이의 외침 : 숨겨진 용들의 땅으로 가는 문이 이미 웰던 마을에 열려 있습니다."); }
		 */
	}

	private void giveItem(Lind lind, int id) {
		for (L1PcInstance pc : lind.getMember()) {
			if (pc == null)
				continue;
			createNewItem(lind, pc, id, 1);
		}
	}

	private boolean createNewItem(Lind lind, L1PcInstance pc, int item_id,
			int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // 가질 수 없는 경우는 지면에 떨어뜨리는 처리의 캔슬은 하지 않는다(부정 방지)
				L1World.getInstance()
						.getInventory(pc.getX(), pc.getY(), pc.getMapId())
						.storeItem(item);
			}
			for (L1PcInstance temp : lind.getMember()) {
				temp.sendPackets(new S_ServerMessage(813, "린드비오르", item
						.getName(), temp.getName()));
			}
			// S_ServerMessage sm = new S_ServerMessage(403, item.getLogName());
			// pc.sendPackets(sm); // %0를 손에 넣었습니다.
			// sm = null;
			return true;
		} else {
			return false;
		}
	}

	private void home(Lind lind) {
		for (L1PcInstance pc : lind.getMember()) {
			if (pc == null)
				continue;
			pc.dx = 33718;
			pc.dy = 32506;
			pc.dm = (short) 4;
			pc.dh = 4;
			pc.setTelType(7);
			S_SabuTell st = new S_SabuTell(pc);
			pc.sendPackets(st);
			st = null;
		}
	}

	/** Lind map exit handling **/
	public void quit(Lind lind) {
		for (L1FieldObjectInstance npc : L1World.getInstance().getAllField()) {
			if (npc.moveMapId == lind.getMap().getId()) {
				npc.deleteMe();
			}
		}
		remove(lind);
		LindRaid.get().quit(lind.getMap());
		Object_Delete(lind);
		lind.clear();
	}

	private void Object_Delete(Lind p) {
		for (L1Object ob : L1World.getInstance()
				.getVisibleObjects(p.getMap().getId()).values()) {
			if (ob == null || ob instanceof L1DollInstance
					|| ob instanceof L1SummonInstance
					|| ob instanceof L1PetInstance)
				continue;
			if (ob instanceof L1ItemInstance) {
				L1ItemInstance obj = (L1ItemInstance) ob;
				L1Inventory groundInventory = L1World.getInstance()
						.getInventory(obj.getX(), obj.getY(), obj.getMapId());
				groundInventory.removeItem(obj);
			} else if (ob instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) ob;
				npc.deleteMe();
			}
		}
	}

	public void remove(Lind lind) {
		_list.remove(lind);
		// System.out.println("종료 > "+lind.getMap().getId());
	}

	public void add(Lind lind) {
		_list.add(lind);
		// System.out.println("시작 > "+lind.getMap().getId());
	}

	public Lind getLind(int mapid) {
		for (Lind lind : _list) {
			if (lind == null)
				continue;
			if (lind.getMap().getId() == mapid)
				return lind;
		}
		return null;
	}

	private void cloud_npcChat(Lind lind, String chat) {
		if (lind.cloud_list == null || lind.cloud_list.size() == 0)
			return;
		for (FastMap.Entry<String, L1NpcInstance> e = lind.cloud_list.head(), mapEnd = lind.cloud_list
				.tail(); (e = e.getNext()) != mapEnd;) {
			L1NpcInstance npc = e.getValue();
			if (npc == null || npc._destroyed || npc.isDead())
				continue;
			Broadcaster.broadcastPacket(npc, new S_NpcChatPacket(npc, chat, 0),
					true);
		}
	}

	/** 해당 맵 유저 체크 귀환 or 맵이동 했을경우 리스트에서 제외 **/
	public void mapUserCheck(Lind lind) {
		boolean ck = false;
		boolean ck2 = false;
		for (L1PcInstance pc : lind.getMember()) {
			if (pc == null || pc.getMapId() != lind.getMap().getId()
					|| pc.getNetConnection() == null) {
				ck2 = true;
			} else if ((pc.getX() >= 32825 && pc.getX() <= 32867)
					&& (pc.getY() >= 32857 && pc.getY() <= 32899)) {
				ck = true;
				lind.mapckCount = 0;
				// System.out.println("中にユーザーがいる");
			}
		}
		if ((lind.Step > 0 && lind.Step < 6) && !ck) {
			if (lind.mapckCount++ > 3) {
				Collection<L1Object> list = L1World.getInstance()
						.getVisibleObjects(lind.getMap().getId()).values();
				for (L1Object obj : list) {
					if (obj == null || !(obj instanceof L1MonsterInstance))
						continue;
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					mon.deleteMe();
				}
				lind.Step = 0;
				lind.mapckCount = 0;
				lind.Sleep = System.currentTimeMillis();
				// System.out.println("がんもなくてリセット");
			}
		}
		if (ck2) {
			if (lind.MembermapckCount++ > 3) {
				for (L1PcInstance pc : lind.getMember()) {
					if (pc == null || pc.getMapId() != lind.getMap().getId()
							|| pc.getNetConnection() == null)
						lind.removeMember(pc);
				}
				lind.MembermapckCount = 0;
			}
		}

	}

	private void Sleep_Setting(Lind lind) {
		long time = 0;
		switch (lind.Step) {
		case 1:
			if (lind.Sub_Step == 0)
				time = 90000;
			else if (lind.Sub_Step == 4)
				time = 5000;
			else
				time = 2000;
			break;
		case 3:
			if (lind.Sub_Step >= 0 && lind.Sub_Step <= 2)
				time = 5000;
			else if (lind.Sub_Step >= 5 && lind.Sub_Step <= 7)
				time = 2000;
			break;
		case 4:
			if (lind.Sub_Step >= 0 && lind.Sub_Step <= 3)
				time = 5000;
			break;
		case 5:
			if (lind.Sub_Step >= 0 && lind.Sub_Step <= 10)
				time = 2000;
			break;
		case 6:
			if (lind.Sub_Step >= 0 && lind.Sub_Step <= 1)
				time = 5000;
			else if (lind.Sub_Step == 2)
				time = 10000;
			else if (lind.Sub_Step == 3)
				time = 5000;
			break;
		default:
			break;
		}
		lind.Sleep = System.currentTimeMillis() + time;
	}

	/*
	 * private void ServerChat(Lind p, int msg){ S_ServerMessage sm = new
	 * S_ServerMessage(msg); for(L1PcInstance pc : p.getMember()){ if(pc !=
	 * null){ pc.sendPackets(sm); } } sm = null; }
	 */

	private void SystemChat(Lind p, String msg) {
		S_SystemMessage sm = new S_SystemMessage(msg);
		for (L1PcInstance pc : p.getMember()) {
			if (pc != null) {
				pc.sendPackets(sm);
			}
		}
		sm = null;
	}

	private void Lind_level2_setting(Lind lind) {
		lind.lind_level2 = LindSpawn.getInstance().fillSpawnTable(
				lind.getMap().getId(), 2, true); // 基本NPCスポーン
		int c = _rnd.nextInt(lind.lind_level2.size());
		int c2 = 0;
		for (FastMap.Entry<String, L1NpcInstance> e = lind.lind_level2.head(), mapEnd = lind.lind_level2
				.tail(); (e = e.getNext()) != mapEnd;) {
			if (c2 == c) {
				lind.dragon_lind = e.getValue();
			}
			c2++;
		}
	}

	private void Lind_level2_die(Lind lind) {
		for (FastMap.Entry<String, L1NpcInstance> e = lind.lind_level2.head(), mapEnd = lind.lind_level2
				.tail(); (e = e.getNext()) != mapEnd;) {
			if (e.getValue() == null || e.getValue().isDead()
					|| e.getValue()._destroyed)
				continue;
			e.getValue().deleteMe();
		}
	}
}
