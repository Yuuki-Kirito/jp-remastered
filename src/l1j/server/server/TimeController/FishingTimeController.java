package l1j.server.server.TimeController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ACTION_UI;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class FishingTimeController implements Runnable {
	private static FishingTimeController _instance;
	private final List<L1PcInstance> _fishingList = new ArrayList<L1PcInstance>();
	private static Logger _log = Logger.getLogger(FishingTimeController.class
			.getName());
	private static Random _random = new Random(System.nanoTime());

	public static FishingTimeController getInstance() {
		if (_instance == null) {
			_instance = new FishingTimeController();
		}
		return _instance;
	}

	@Override
	public void run() {
		try {
			// while (true) {
			// Thread.sleep(300);
			fishing();
			GeneralThreadPool.getInstance().schedule(this, 300);
			// }
		} catch (Exception e1) {
			// GeneralThreadPool.getInstance().schedule(this, 1000);
			// e1.printStackTrace();
			_log.log(Level.SEVERE, e1.getLocalizedMessage(), e1);
		}
	}

	public void addMember(L1PcInstance pc) {
		if (pc == null || _fishingList.contains(pc)) {
			return;
		}
		_fishingList.add(pc);
	}

	public void removeMember(L1PcInstance pc) {
		if (pc == null || !_fishingList.contains(pc)) {
			return;
		}
		_fishingList.remove(pc);
	}

	private static final int[] fish = { 41299, 41300, 41298, 41296, 41297,
			41302, 41301, 41303, 41304 };
	private static final String[] fish_name = { "$5257", "$5258", "$5256",
			"$5249", "$5250", "$5260", "$5259", "$5261", "$5262" };

	private void fishing() {
		try {
			if (_fishingList.size() > 0) {
				long currentTime = System.currentTimeMillis();
				L1PcInstance[] list = _fishingList
						.toArray(new L1PcInstance[_fishingList.size()]);
				for (L1PcInstance pc : list) {
					if (pc == null) {
						removeMember(pc);
						continue;
					}

					if (L1World.getInstance().getPlayer(pc.getName()) == null) {
						removeMember(pc);
						continue;
					}

					if (pc.isFishing()) {
						if (pc.getFishingItem() == null) {
							fishingExit(pc);
							removeMember(pc);
							continue;
						}
						long time = pc.getFishingTime();
						if (currentTime <= (time + 1000)
								&& currentTime >= (time - 1000)) {
							pc.setFishingReady(true);
							pc.sendPackets(
									new S_PacketBox(S_PacketBox.FISHING), true);
						} else if (currentTime > (time + 100)) {
							int chance = _random.nextInt(1000);
							if (pc.getFishingItem().getItemId() == 600229) {
								if (chance <= 10) {// 0.1%
									successFishing(pc, 60330, "�޹� �����Ƴ�");
								} else if (chance == 3) {// 0.001%
									successFishing(pc, 600227, "�����ϻ���� ����");
								} else if (chance <= 10) {// 0.1%
									successFishing(pc, 500209, "ǻ�����");
								} else if (chance <= 3) {// 0.1%
									successFishing(pc, 60482, "�ݺ������Ƴ�");
								} else if (chance <= 3) {// 0.1%
									successFishing(pc, 60480,"����� �ݺ� �����Ƴ�");
									L1World.getInstance().broadcastPacketToAll(
											new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
													"�������� ����� �ݺ� �����Ƴ��� ���� �÷Ƚ��ϴ�!"), true);
								} else if (chance <= 3) {// 0.1%
									successFishing(pc, 60483,"���� �����Ƴ�");
								} else if (chance <= 3) {// 0.1%
									successFishing(pc, 60481,"����� ���� �����Ƴ�");
									L1World.getInstance().broadcastPacketToAll(
											new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
													"�������� ����� ���� �����Ƴ��� ���� �÷Ƚ��ϴ�!"), true);
								} else if (chance <= 200) {// 26%
									successFishing(pc, 60329, "��� �����Ƴ�");
								} else /* if(chance < 90100) */{// 60%
									successFishing(pc, 60328, "�����Ƴ�");
								}
							} else {
								if (chance < 5) {
									if (pc.getFishingItem().getItemId() == 60334) {// ��ź�³��ô�
										if (_random.nextInt(1000) > 500)
											successFishing(pc, 60525,
													"���� �����Ƴ�");
										else
											successFishing(pc, 60330,
													"�޹� �����Ƴ�");
									} else if (pc.getFishingItem().getItemId() == 60478) {// �ݺ����ô�
										String ss = "�������� �ݺ� �����Ƴ��� ���� �÷Ƚ��ϴ�!";
										if (_random.nextInt(1000) > 500)
											successFishing(pc, 60525,
													"���� �����Ƴ�");
										else {
											successFishing(pc, 60330,
													"�޹� �����Ƴ�");
										}
										for (L1Object temp : L1World
												.getInstance()
												.getVisibleObjects(
														pc.getMapId()).values()) {
											if (temp instanceof L1PcInstance) {
												L1PcInstance tp = (L1PcInstance) temp;
												tp.sendPackets(
														new S_PacketBox(
																S_PacketBox.GREEN_MESSAGE,
																ss), true);
											}
										}
									} else if (pc.getFishingItem().getItemId() == 60523) {// ����� �ݺ� ���˴�
										String ss = "�������� �ݺ� �����Ƴ��� ���� �÷Ƚ��ϴ�!";
										if (_random.nextInt(1000) > 500)
											successFishing(pc, 60525,
													"���� �����Ƴ�");
										else {
											successFishing(pc, 60330,
													"�޹� �����Ƴ�");
										}
										for (L1Object temp : L1World
												.getInstance()
												.getVisibleObjects(
														pc.getMapId()).values()) {
											if (temp instanceof L1PcInstance) {
												L1PcInstance tp = (L1PcInstance) temp;
												tp.sendPackets(
														new S_PacketBox(
																S_PacketBox.GREEN_MESSAGE,
																ss), true);
											}
										}
									} else if (pc.getFishingItem().getItemId() == 60524) {// ����� ���� ���˴�
										String ss = "�������� ���� �����Ƴ��� ���� �÷Ƚ��ϴ�!";
										if (_random.nextInt(1000) > 500)
											successFishing(pc, 60525,
													"���� �����Ƴ�");
										else {
											successFishing(pc, 60330,
													"�޹� �����Ƴ�");
										}
										for (L1Object temp : L1World
												.getInstance()
												.getVisibleObjects(
														pc.getMapId()).values()) {
											if (temp instanceof L1PcInstance) {
												L1PcInstance tp = (L1PcInstance) temp;
												tp.sendPackets(
														new S_PacketBox(
																S_PacketBox.GREEN_MESSAGE,
																ss), true);
											}
										}
									} else if (pc.getFishingItem().getItemId() == 60479) {// �������ô�
										if (_random.nextInt(1000) > 500)
											successFishing(pc, 60525,
													"���� �����Ƴ�");
										else
											successFishing(pc, 60330,
													"�޹� �����Ƴ�");
									}

								} else if (chance < 505
										&& pc.getFishingItem().getItemId() == 60334) {// 0.2%
									successFishing(pc, 60328, "�����Ƴ�");
								} else if (chance < 605) {// 0.1%
									successFishing(pc, 60328, "�����Ƴ�");
								} else if (chance < 2905) {// 2.3%
									successFishing(pc, 60329, "��� �����Ƴ�");
								} else if (chance < 28905) {// 26%
									successFishing(pc, 60329, "��� �����Ƴ�");
								} else if (chance < 90905) {// 62%
									successFishing(pc, 60328, "�����Ƴ�");
								} else {// ���� 19.4%
									pc.sendPackets(
											new S_ServerMessage(1517, ""), true); // �����ؿ�
																					// �����߽��ϴ�.
									pc.getInventory().consumeItem(60327, 1); // ����
									if (pc.getFishingItem().getItemId() == 60334
											|| pc.getFishingItem().getItemId() == 60478
											|| pc.getFishingItem().getItemId() == 60524 //����� ����
											|| pc.getFishingItem().getItemId() == 60523 //����� �ݺ�
											|| pc.getFishingItem().getItemId() == 60479) {// ��
																							// ����
																							// ��ź��
																							// ���˴�
										pc.getFishingItem().setChargeCount(
												pc.getFishingItem()
														.getChargeCount() - 1);
										pc.getInventory().updateItem(
												pc.getFishingItem(),
												L1PcInventory.COL_CHARGE_COUNT);
										if (pc.getFishingItem()
												.getChargeCount() <= 0) {
											pc.getInventory().removeItem(
													pc.getFishingItem(), 1);
											pc.getInventory().storeItem(60326,
													1);
											fishingExit(pc);
										}
									}
									if (pc.isFishing()
											&& !pc.getInventory().checkItem(
													60327, 1)) { // ����
										fishingExit(pc);
										pc.sendPackets(new S_ServerMessage(1137)); // ���ø��ϱ�
																					// ���ؼ�
																					// ���̰�
																					// �ʿ��մϴ�.
										removeMember(pc);
									}
								}
							}

							pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.FISH_WINDOW, 2, false, 0, 0), true);
							if (pc.isFishing()) {
								long time2 = System.currentTimeMillis() + 240000;
								boolean ck = false;
								int is���� = 0;
								if (pc.getFishingItem().getItemId() == 60334
										|| pc.getFishingItem().getItemId() == 60478
										|| pc.getFishingItem().getItemId() == 60523
										|| pc.getFishingItem().getItemId() == 60524
										|| pc.getFishingItem().getItemId() == 60479) {// ��
																						// ����
																						// ��ź��
																						// ���˴�
									time2 = System.currentTimeMillis() + 80000;
									ck = true;
								}
								if (pc.getFishingItem().getItemId() == 600229) {// ��
																				// ����
																				// ��ź��
																				// ���˴�
									time2 = System.currentTimeMillis() + 40000;
									ck = true;
									is���� = 1;
								}
								pc.setFishingTime(time2);
								pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.FISH_WINDOW, 1, ck, is����, pc.getFishingItem().getChargeCount()), true);
							}
						}
					} else {
						removeMember(pc);
						continue;
					}

				}

				list = null;

			}
		} catch (Exception e) {
		}
	}

	private void fishingExit(L1PcInstance pc) {
		pc.setFishingTime(0);
		pc.setFishingReady(false);
		pc.setFishing(false);
		pc.setFishingItem(null);
		pc.sendPackets(new S_CharVisualUpdate(pc));
		Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc));
	}

	private void successFishing(L1PcInstance pc, int itemId, String message) {
		if (Config.STANDBY_SERVER) {
			return;
		}
		try {
			boolean ck = false;
			int is���� = 0;
			if (pc.getFishingItem().getItemId() == 600229) {// �� ���� ��ź�� ���˴�
				ck = true;
				is���� = 1;
			}

			L1ItemInstance item = ItemTable.getInstance().createItem(itemId);

			if (pc.getInventory().checkAddItem(item, 1) != L1Inventory.OK) {
				fishingExit(pc);
				pc.sendPackets(new S_SystemMessage(
						"�κ��丮�� ���ſ� ���̻� ���ø� �� �� �����ϴ�."));
				removeMember(pc);
				return;
			}

			pc.getInventory().storeItem(item);
			if (!ck) {
				pc.getInventory().consumeItem(60327, 1); // ����
			}

			if (pc.getFishingItem().getItemId() == 60334
					|| pc.getFishingItem().getItemId() == 60478
					|| pc.getFishingItem().getItemId() == 60523
					|| pc.getFishingItem().getItemId() == 60524
					|| pc.getFishingItem().getItemId() == 60479) {// �� ���� ��ź��
																	// ���˴�
				pc.getFishingItem().setChargeCount(
						pc.getFishingItem().getChargeCount() - 1);
				pc.getInventory().updateItem(pc.getFishingItem(),
						L1PcInventory.COL_CHARGE_COUNT);
				if (pc.getFishingItem().getChargeCount() <= 0) {
					pc.getInventory().removeItem(pc.getFishingItem(), 1);
					pc.getInventory().storeItem(60326, 1);
					fishingExit(pc);
				}
			} else if (ck) {
				int exp = (int) Config.fishrate; //225000 ���� ���� ����ġ

				 

				/*
				 * if(Config.��Ƽ��_Event){ if(Config.��Ƽ�����������){ if(pc.getLevel() <
				 * 80){ exp = 30000; } } }
				 */

				double dragon = 1;
				int settingEXP = (int) Config.RATE_XP;
				if (pc.getAinHasad() > 10000) {
					pc.calAinHasad(-(exp+exp/3));
					if (pc.getAinHasad() > 2000000) {
						dragon = 2.3;
					} else {
						dragon = 2;
					}
					if (pc.PC��_����) {
						dragon += 0.20;
					}

					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc));
				}
				if (pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.DRAGON_EME_2)
						&& pc.getAinHasad() > 10000) {
					dragon += 0.8;
					pc.calAinHasad(-exp);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc));
				} else if (pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.DRAGON_PUPLE)
						&& pc.getAinHasad() > 10000) {
					if (pc.getLevel() >= 49 && pc.getLevel() <= 54)
						dragon += 0.53;
					else if (pc.getLevel() >= 55 && pc.getLevel() <= 59)
						dragon += 0.43;
					else if (pc.getLevel() >= 60 && pc.getLevel() <= 64)
						dragon += 0.33;
					else if (pc.getLevel() >= 65)
						dragon += 0.23;
					pc.calAinHasad(-exp);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, pc));
					if (pc.getAinHasad() <= 10000) {
						pc.getSkillEffectTimerSet().removeSkillEffect(
								L1SkillId.DRAGON_PUPLE);
					}
				} /*else if (pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.DRAGON_TOPAZ)
						&& pc.getAinHasad() > 10000) {
					dragon += 0.8;
					pc.calAinHasad(-exp);
					pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
					if (pc.getAinHasad() <= 10000) {
						pc.getSkillEffectTimerSet().removeSkillEffect(
								L1SkillId.DRAGON_TOPAZ);
					}
				}*/

				/*
				 * if(Config.��Ƽ��_Event){ if(Config.��Ƽ�����������){ if(pc.getLevel() <
				 * 80){ if(dragon != 1){ exp = 15000; dragon *= 2; } } } }
				 */

				double exppenalty = ExpTable.getPenaltyRate(pc.getLevel());

				int add_exp = (int) (exp * settingEXP * dragon * exppenalty);
				pc.addExp(add_exp);
				pc.getFishingItem().setChargeCount(
						pc.getFishingItem().getChargeCount() - 1);
				pc.getInventory().updateItem(pc.getFishingItem(),
						L1PcInventory.COL_CHARGE_COUNT);
				if (pc.getFishingItem().getChargeCount() <= 0) {
					pc.getInventory().removeItem(pc.getFishingItem(), 1);
					pc.getInventory().storeItem(60326, 1);
					fishingExit(pc);
				}

				pc.save();
			}

			pc.sendPackets(new S_ServerMessage(1185, message));// ���ÿ� ������ ��
																// �����߽��ϴ�.
			if (!ck && !pc.getInventory().checkItem(60327, 1)) { // ����
				fishingExit(pc);
				pc.sendPackets(new S_ServerMessage(1137)); // ���ø��ϱ� ���ؼ� ���̰�
															// �ʿ��մϴ�.
				removeMember(pc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

}
