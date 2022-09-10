package l1j.server.server.clientpackets;

import java.io.IOException;

import l1j.server.Config;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.QuestInfoTable;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_QuestTalkIsland;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1QuestInfo;
import l1j.server.server.templates.L1QuestView;
import l1j.server.server.utils.CalcExp;
import server.LineageClient;

public class C_TalkIslandQuest extends ClientBasePacket {
	private static final String C_CludiaQuest = "[C] C_CludiaQuest";
	private static final int subQuestIds[] = { 309 };// �̱��� �Ϸ��Ű��.

	public C_TalkIslandQuest(byte[] decrypt, LineageClient client) throws IOException {
		super(decrypt);

		L1PcInstance pc = client.getActiveChar();
		if (pc == null)
			return;
		int type = readH();
		if (type == 0x208 || type == 0x020a || type == 0x020c || type == 0x020f) {
		}
		switch (type) {
		case 0x0208: { // �ű� ����Ʈ ���
			readP(3);
			int nextid = readBit();
			//synchronized (pc.syncTalkIsland) {
				L1QuestInfo Q_info = pc.getQuestList(nextid);
				if (Q_info != null)
					return;

				Q_info = new L1QuestInfo();
				Q_info.quest_id = nextid;

				pc.quest_list.put(nextid, Q_info);
				if (nextid == 259) { // ����
					// ����
					//pc.getInventory().TalkIsland_storeItem(60154, 1);
					pc.getInventory().storeItem(60154, 1);
				}
				
				if (nextid == 260) { // ������ ���� �ֹ���
					// ����
					pc.getInventory().storeItem(40096, 5);
				}
				
				if (nextid == 262) { // �������� ���� ���� �ֹ���
					// ����
					pc.getInventory().storeItem(60718, 1);
				}
				
				if (nextid == 264) { // �������� ���� ���� �ֹ���
					// ����
					pc.getInventory().storeItem(60717, 1);
				}
				
				if (nextid == 274) { // ������
					// ����
					pc.getInventory().storeItem(20089, 1);
					pc.getInventory().storeItem(41245, 10);
				}
				
				if (nextid == 275) { // �������� ��ȯ
					// ����
					pc.getInventory().storeItem(430506, 1);
					pc.getInventory().storeItem(41246, 50);
				}
				
				if (nextid == 276) { // ����� ȭ���� ���
					// ����
					pc.getInventory().storeItem(60738, 1);
				}
				
				if (nextid == 282) { // �������� ��ű� ���� �ֹ���
					// ����
					pc.getInventory().storeItem(60731, 1);
					pc.getInventory().storeItem(21100, 1);
				}
				
				/*if (nextid == 361) { // �Է��� ���� �ָӴ� ����
					pc.getInventory().TalkIsland_storeItem(6012, 1);
				}*/
				pc.sendPackets(new S_QuestTalkIsland(9, nextid));
		//	}
		}
			break;
		case 0x020a: { // �ð� ������Ʈ
			readP(3);
			int startid = readBit();

			L1QuestInfo info = pc.getQuestList(startid);
			boolean isSub = false;
			if (info == null || info.end_time != 0)
				return;

			if (info.st_time == 0)
				info.st_time = System.currentTimeMillis();


			if (startid == 257) {// ��������Ʈ�� ��� ���� ���ſ�/ ���� �Ʒ�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 5) {
					info.ck[0] = 5;
				}
			}
			
			if (startid == 266) {
				info.ck[0] = 1;
			}
			if (startid == 269) {
				info.ck[0] = 1;
			} 
			
			if (startid == 279) {// ��������Ʈ�� ��� ���� ���ſ� 35���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 35) {
					info.ck[0] = 35;
				}
			}
			
			if (startid == 286) {
				info.ck[0] = 1;
			} 
			
			if (startid == 290) {
				info.ck[0] = 1;
			} 
			
			if (startid == 292) {
				info.ck[0] = 1;
			} 
			
			if (startid == 303) {
				info.ck[0] = 1;
			} 
			
			/*if (startid == 309) {
				info.ck[0] = 1;
			}*/
			
			if (startid == 308) {
				info.ck[0] = 1;
				info.ck[1] = 1;
				info.ck[2] = 1;
				info.ck[3] = 1;
			} 
			
			if (startid == 283) {// ��������Ʈ�� ��� ���� ���ſ� 40���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 40) {
					info.ck[0] = 40;
				}
			}
			
			if (startid == 287) {// ��������Ʈ�� ��� ���� ���ſ� 45���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 45) {
					info.ck[0] = 45;
				}
			}
			
			if (startid == 291) {// ��������Ʈ�� ��� ���� ���ſ� 48���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 48) {
					info.ck[0] = 48;
				}
			}
			
			if (startid == 294) {// ��������Ʈ�� ��� ���� ���ſ� 50���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 50) {
					info.ck[0] = 50;
				}
			}
			
			if (startid == 297) {// ��������Ʈ�� ��� ���� ���ſ� 52���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 52) {
					info.ck[0] = 52;
				}
			}
			
			if (startid == 300) {// ��������Ʈ�� ��� ���� ���ſ� 54���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 54) {
					info.ck[0] = 54;
				}
			}
			
			if (startid == 302) {// ��������Ʈ�� ��� ���� ���ſ� 55���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 55) {
					info.ck[0] = 55;
				}
			}
			
			/*if (startid == 361) {// ��������Ʈ�� ��� ���� ���ſ� 70���� �޼�
				info.ck[0] = pc.getLevel();
				if (info.ck[0] > 70) {
					info.ck[0] = 70;
				}
			}*/
			
			for (int isSubQuestId : subQuestIds) {
				if (isSubQuestId == startid) {
					isSub = true;
					info.ck[0] = 1; // ���� ����Ʈ��� �ٷ� �Ϸ� ����
					break;
				}
			}

			pc.sendPackets(new S_QuestTalkIsland(pc, startid, info));

			if (isSub) {
				// System.out.println("��������Ʈ �Ϸ� : " + startid);
				info.end_time = System.currentTimeMillis();
				pc.sendPackets(new S_QuestTalkIsland(14, startid)); // �ٷ� ������ ��
			}
		}
			break;
		case 0x020c: { // ����Ʈ ����ޱ�
			try {
				if (Config.STANDBY_SERVER) {
					pc.sendPackets(new S_SystemMessage("\\aH�˸�: ���´�� ���¿��� �ش� �ൿ�� �Ұ��� �մϴ� ."), true);
//					pc.sendPackets("���Ŀ����� �ٽ� �ѹ� �������� �ӵ���� ������ Ŭ���Ͽ�,����Ʈ�� �����ϼ���");
					return;
				}
				readP(3);
				int questid = readBit();

				L1QuestInfo E_info = pc.getQuestList(questid);
				if (E_info == null || E_info.end_time != 0 || E_info.ck[0] == 0)
					return;

				E_info.end_time = System.currentTimeMillis();

				/*
				 * r ��ᰡ �ʿ��Ѱ�� üũ�ؼ� �����Ѵ�.
				 */
				if (questid == 258 // �ķ�
						|| questid == 263// ������
						|| questid == 273// ���� ����
						|| questid == 281// �̻��� ������
						|| questid == 285// �̻��� ��ű� ����
						|| questid == 289// �̻��� ����
						|| questid == 298// ���� 
						|| questid == 304// �� ����� 1�ܰ�
						|| questid == 305// �� ����� 2�ܰ�
						|| questid == 306// �� ����� 3�ܰ�
						|| questid == 307// �� ����� 4�ܰ�
						|| questid == 308// �� ����� 4�ܰ�
				) {
					L1QuestView view = QuestInfoTable.getInstance().getQuestView(questid);
					if (view != null) { // Ȥ�� ���� nullüũ
						for (int i = 0; i < 4; i++) {
							if (view.pick_item[i] != 0) { // ��������
								if (!pc.getInventory().checkItem(view.pick_item[i], view.max_count[i])) {
									pc.sendPackets(new S_SystemMessage("��ᰡ �����մϴ�."));
									return;
								}
							}
						}
						// ��ᰡ �����Ұ�� ����
						for (int i = 0; i < 4; i++) {
							if (view.pick_item[i] != 0) {
								//pc.getInventory().consumeItem(view.pick_item[i], view.max_count[i]);
								pc.getInventory().consumeItem(view.pick_item[i]);
							}
						}
					}
				}

				/*
				 * �丮���� 279~286 45�޼��� 320 �Ƴ�Ŭ��, 322 ������, 324 ���ʽ� 329 ������ , 341
				 * ����ī����, 346 �����ũ 350 ���ɿ��Ǵ��,
				 */
				int cooktype = 0;
				
				if (questid == 301) {
					L1Teleport.teleport(pc, 32562, 33089,(short) 9, 5, true, true, 5000);
					pc.sendPackets(new S_SystemMessage("\\aH�˸�: �׸��ٿ��� ��ȭ�� �ɾ� '�������� ��' ���� �ϼ��� ."), true);
				}
				
				if (questid == 290) {
					readC();
					cooktype = readC() + 1;
				}
				quest_storeitem(pc, questid, cooktype);
				
				pc.sendPackets(new S_QuestTalkIsland(13, questid));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		case 0x020f: {// ����Ʈ �̵�Ŭ��
			readP(3);
			int telid = readBit();
			L1QuestView view = QuestInfoTable.getInstance().getQuestView(telid);
			if (view != null) {
				if (view.tel[1] != 0) {
					//L1Teleport.teleport(pc, view.tel[1], view.tel[2],(short) view.tel[0], 5, true, true, 5000);
					pc.start_teleport(view.tel[1], view.tel[2], (short) view.tel[0], 5, 169, true, true);
					pc.sendPackets(new S_QuestTalkIsland(16, telid)); // â�ݱ�

				} else {
					System.out.println("��ϵ��� ���� ����Ʈ��id = " + telid);
				}
			}
		}
			break;
		}

	}

	/**
	 * ����Ʈ ������ ����
	 */
	private void quest_storeitem(L1PcInstance pc, int questid, int type) {
		try {
			// ������Ʈ�� ��ϵǾ��ִٸ�
			if (QuestInfoTable.getInstance().getQuestViewid(questid)) {
				L1QuestView view = QuestInfoTable.getInstance().getQuestView(questid);
				if (view == null) {
					return;
				}
				if (view._exp != 0) {
					// System.out.println("view._exp : " + view._exp);
					CalcExp.AddExp(pc, view._exp);
					if (pc.getLevel() < 35 && questid == 278) { // ���� 35 �޼�
						pc.setExp(ExpTable.getExpByLevel(35));
					} else if (pc.getLevel() < 40 && questid == 282) { // ���� 40 �޼�
						pc.setExp(ExpTable.getExpByLevel(40));
					} else if (pc.getLevel() < 45 && questid == 286) { // ���� 45 �޼�
						pc.setExp(ExpTable.getExpByLevel(45));
					} else if (pc.getLevel() < 48 && questid == 290) { // ���� 48 �޼�
						pc.setExp(ExpTable.getExpByLevel(48));
					} else if (pc.getLevel() < 50 && questid == 293) { // ���� 50 �޼�
						pc.setExp(ExpTable.getExpByLevel(50));
					} else if (pc.getLevel() < 52 && questid == 296) { // ���� 52 �޼�
						pc.setExp(ExpTable.getExpByLevel(52));
					} else if (pc.getLevel() < 54 && questid == 299) { // ���� 54 �޼�
						pc.setExp(ExpTable.getExpByLevel(54));
					} else if (pc.getLevel() < 55 && questid == 301) { // ���� 55 �޼�
						pc.setExp(ExpTable.getExpByLevel(55));
					}
				}
				for (int i = 0; i < 4; i++) {
					if (view.item_id[i] != 0) {
						pc.getInventory().storeItem(view.item_id[i], view.item_count[i]);
					}
				}
				
				if (type != 0) {
					pc.getInventory().storeItem(60725 + type, 1);
				} else if (type != 0 && questid == 303) { // ������ ȯ���������
					pc.getInventory().storeItem(50751, 1);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getType() {
		return C_CludiaQuest;
	}
}