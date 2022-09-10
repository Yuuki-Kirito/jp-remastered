package l1j.server.server.model.item.function;

import java.util.Random;

import l1j.server.Config;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class LeafItem {

	private static Random _random = new Random(System.nanoTime());

	public static void clickItem(L1PcInstance pc, int itemId,
			L1ItemInstance l1iteminstance, L1ItemInstance l1iteminstance1) {

		switch (itemId) {
		case 31086: {// �����ǳ�����
			int targetItem = l1iteminstance1.getItemId();
			int[] item = new int[] { 31086 };// �ʿ������
			int[] �ݺ����� = new int[] { 20049 }; // �Ŵ� ���� �ݺ� ����
			int[] �������� = new int[] { 20050 }; // �Ŵ� ���� ���� ����
			int[] ����ü�μҵ� = new int[] { 6000 }; // ������ ü�μҵ�
			int[] ������ = new int[] { 420009 }; // ����� ����
			int[] ī�� = new int[] { 41148 }; // �����(ī���� �踮��)
			int[] ���� = new int[] { 59 }; // ����Ʈ�ߵ��� ��հ�
			int[] ��Ű = new int[] { 6001 }; // ������ Ű��ũ
			int[] ���� = new int[] { 119 }; // ������ ������
			int[] �������� = new int[] { 20100 }; // ��������Ʈ�� ����
			int[] �������� = new int[] { 20198 }; // ��������Ʈ�� ����
			int[] ���� = new int[] { 58 }; // ��������Ʈ�� �Ұ�
			int[] �����尩 = new int[] { 20166 }; // ��������Ʈ�� �尩
			int[] �������� = new int[] { 20010 }; // ��������Ʈ�� ����
			int[] ����� = new int[] { 20250 }; // ����۾� ������ �����
			int[] ������� = new int[] { 20277 }; // ����۾� ������ ������ ����
			int[] ������� = new int[] { 20278 }; // ����۾� ������ ���� ����
			int[] ���� = new int[] { 30219 }; // �� �尩
			int[] ���̾ƹ��� = new int[] { 20279 }; // ���̾��� ����
			int[] �е��̵��� = new int[] { 76 }; // �е��� �̵���
			int[] ��ġ�κ� = new int[] { 20107 }; // ��ġ �κ�
			int[] �������� = new int[] { 126 }; // ������ ������
			int[] �������� = new int[] { 500214 }; // ���� ��� ����
			int[] �� = new int[] { 40222 }; // ������(����Ƽ�׷���Ʈ)
			int[] ��Ƽ�� = new int[] { 40219 }; // ������(��Ƽ�� ��Ʈ����ũ)
			int[] ���չ��� = new int[] { 420008 }; // ������ ����
			int[] �ӹ̿հ� = new int[] { 20017 }; // �ӹ̷ε��� �հ�
			int[] �޸����� = new int[] { 20018 }; // �޸�Ű������ ����
			int[] ��ø���� = new int[] { 7215 }; // ��ø�� ����
			int[] ���� = new int[] { 124 }; // ������Ʈ�� ������
			int[] �ݿ��� = new int[] { 21093 }; // �ݿ����� ����
			int[] ���͸��� = new int[] { 20025 }; // �����ڸ��� ����
			int[] ��� = new int[] { 20079 }; // �����̾��� ����
			int[] ����10 = new int[] { 40288 }; // ���ε� ������ ž 10�� �̵� ����
			int[] ����1 = new int[] { 60201 }; // ���ε� ������ ž 1�� �̵� ����
			int[] ����2 = new int[] { 40280 }; // ���ε� ������ ž 2�� �̵� ����
			int[] ����3 = new int[] { 40281 }; // ���ε� ������ ž 3�� �̵� ����
			int[] ����4 = new int[] { 40282 }; // ���ε� ������ ž 4�� �̵� ����
			int[] ����5 = new int[] { 40283 }; // ���ε� ������ ž 5�� �̵� ����
			int[] ����6 = new int[] { 40284 }; // ���ε� ������ ž 6�� �̵� ����
			int[] ����7 = new int[] { 40285 }; // ���ε� ������ ž 7�� �̵� ����
			int[] ����8 = new int[] { 40286 }; // ���ε� ������ ž 8�� �̵� ����
			int[] ����9 = new int[] { 40287 }; // ���ε� ������ ž 9�� �̵� ����
			int[] �� = new int[] { 262 }; // ����� ���� ���弭Ŀ
			int[] ���� = new int[] { 21268 }; // ������ �������� �����
			int[] ���� = new int[] { 21267 }; // ������ �������� ����
			int[] ���� = new int[] { 20029 }; // ������ ����
			int[] ���� = new int[] { 21168 }; // ������ ����
			int[] �ɾ� = new int[] { 22009 }; // �þ��� �ɾ�
			int[] ��Ƽ = new int[] { 900019 }; // ������ Ƽ����
			int[] ���� = new int[] { 121 }; // ���� ������ ������
			int[] ���� = new int[] { 20314 }; // ���̼�Ʈ ���̾�Ʈ�� ����
			int[] ���� = new int[] { 9 }; // �����Ϸ��� �ܰ�
			int[] ������ = new int[] { 21167 }; // ������ �����
			int[] �Ϸ¹� = new int[] { 7214 }; // �Ϸ��� ����
			int[] ��� = new int[] { 40466 }; // ���� ����
			int[] ���� = new int[] { 7310 }; // ������ ����(�������)
			int[] Ÿ��ź�� = new int[] { 7304 }; // ������ ����(Ÿ��ź ��)
			int[] Ÿ��ź���� = new int[] { 7306 }; // ������ ����(Ÿ��ź ����)
			int[] Ÿ��ź�� = new int[] { 7305 }; // ������ ����(Ÿ��ź ��)
			int[] ���� = new int[] { 41149 }; // ������ ����(�ҿ� ���� ������)
			int[] ���� = new int[] { 41153 }; // ������ ����(��Ʈ����ũ ����)
			int[] ��� = new int[] { 40249 }; // ������ ����(� ���ε�)
			int[] ���� = new int[] { 41152 }; // ������ ����(����Ʈ ����)
			int[] ���Ͻ��� = new int[] { 20298 }; // ���Ͻ��� ����
			int[] ���Ĺ� = new int[] { 30229 }; // ������ ����
			int[] ���ְ� = new int[] { 21122 }; // ���ְ��� ����
			int[] ī���ĸ� = new int[] { 20040 }; // ī������ ����
			int[] Ŀ���� = new int[] { 20150 }; // Ŀ���� ����
			int[] Ŀ�� = new int[] { 54 }; // Ŀ���� ��
			int[] Ŀ���� = new int[] { 20214 }; // Ŀ���� ����
			int[] Ŀ���� = new int[] { 20184 }; // Ŀ���� �尩
			int[] Ŀ���� = new int[] { 20041 }; // Ŀ���� ����
			int[] ũ�κ� = new int[] { 20614 }; // ũ�γ뽺�� ��Ʈ
			int[] Ÿ���� = new int[] { 20216 }; // Ÿ���� ����
			int[] Ÿ���� = new int[] { 20186 }; // Ÿ���� �尩
			int[] Ÿ�� = new int[] { 20320 }; // Ÿ��ź�� ��Ʈ
			int[] ���� = new int[] { 20077 }; // ���� ����
			int[] ����� = new int[] { 21258 }; // ������ �����
			int[] ���� = new int[] { 30220 }; // �ı��� ���
			int[] ���� = new int[] { 21260 }; // ������ �����
			int[] ȥ�� = new int[] { 20048 }; // ȥ���� ����
			int[] ����κ� = new int[] { 20160 }; // ������� �κ�
			int[] ������� = new int[] { 20218 }; // ������� ����
			int[] �ƸӺ� = new int[] { 60199 }; // �������� ����(�Ƹ� �극��ũ)
			int[] ���� = new int[] { 131 }; // ���� ������
			int[] �߶���� = new int[] { 40354 }; // �߶����
			int[] �Ÿ��� = new int[] { 220011 }; // �ż��Ѹ����������
			int[] temp = null;

			switch (targetItem) {
			case 56001: // ����� ���� �Ŵ� ���� �ݺ� ����
				temp = �ݺ�����;
				break;
			case 56002: // ����� ���� �Ŵ� ���� ���� ����
				temp = ��������;
				break;
			case 56003: // ����� ���� ������ ü�μҵ�
				temp = ����ü�μҵ�;
				break;
			case 56004: // ����� ���� ����� ����
				temp = ������;
				break;
			case 56005: // ����� ���� �����(ī���� �踮��)
				temp = ī��;
				break;
			case 56006: // ����� ���� ����Ʈ�ߵ��� ��հ�
				temp = ����;
				break;
			case 56007: // ����� ���� ������ Ű��ũ
				temp = ��Ű;
				break;
			case 56008: // ����� ���� ������ ������
				temp = ����;
				break;
			case 56009: // ����� ���� ��������Ʈ�� ����
				temp = ��������;
				break;
			case 56010: // ����� ���� ��������Ʈ�� ����
				temp = ��������;
				break;
			case 56011: // ����� ���� ��������Ʈ�� �Ұ�
				temp = ����;
				break;
			case 56012: // ����� ���� ��������Ʈ�� �尩
				temp = �����尩;
				break;
			case 56013: // ����� ���� ��������Ʈ�� ����
				temp = ��������;
				break;
			case 56014: // ����� ���� ����۾� ������ �����
				temp = �����;
				break;
			case 56015: // ����� ���� ����۾� ������ ������ ����
				temp = �������;
				break;
			case 56016: // ����� ���� ����۾� ������ ���� ����
				temp = �������;
				break;
			case 56017: // ����� ���� �� �尩
				temp = ����;
				break;
			case 56018: // ����� ���� ���̾��� ����
				temp = ���̾ƹ���;
				break;
			case 56019: // ����� ���� �е��� �̵���
				temp = �е��̵���;
				break;
			case 56020: // ����� ���� ��ġ �κ�
				temp = ��ġ�κ�;
				break;
			case 56021: // ����� ���� ������ ������
				temp = ��������;
				break;
			case 56084: // ����� ���� ���� ��� ����
				temp = ��������;
				break;
			case 56022: // ����� ���� ������(����Ƽ�׷���Ʈ)
				temp = ��;
				break;
			case 56000: // ����� ���� ������(��Ƽ�� ��Ʈ����ũ)
				temp = ��Ƽ��;
				break;
			case 56023: // ����� ���� ������ ����
				temp = ���չ���;
				break;
			case 56024: // ����� ���� �ӹ̷ε��� �հ�
				temp = �ӹ̿հ�;
				break;
			case 56025: // ����� ���� �޸�Ű������ ����
				temp = �޸�����;
				break;
			case 56026: // ����� ���� ��ø�� ����
				temp = ��ø����;
				break;
			case 56027: // ����� ���� ������Ʈ�� ������
				temp = ����;
				break;
			case 56028: // ����� ���� �ݿ����� ����
				temp = �ݿ���;
				break;
			case 56029: // ����� ���� �����ڸ��� ����
				temp = ���͸���;
				break;
			case 56030: // ����� ���� �����̾��� ����
				temp = ���;
				break;
			case 56031: // ����� ���� ���ε� ������ ž 10�� �̵� ����
				temp = ����10;
				break;
			case 56032: // ����� ���� ���ε� ������ ž 1�� �̵� ����
				temp = ����1;
				break;
			case 56033: // ����� ���� ���ε� ������ ž 2�� �̵� ����
				temp = ����2;
				break;
			case 56034: // ����� ���� ���ε� ������ ž 3�� �̵� ����
				temp = ����3;
				break;
			case 56035: // ����� ���� ���ε� ������ ž 4�� �̵� ����
				temp = ����4;
				break;
			case 56036: // ����� ���� ���ε� ������ ž 5�� �̵� ����
				temp = ����5;
				break;
			case 56037: // ����� ���� ���ε� ������ ž 6�� �̵� ����
				temp = ����6;
				break;
			case 56038: // ����� ���� ���ε� ������ ž 7�� �̵� ����
				temp = ����7;
				break;
			case 56039: // ����� ���� ���ε� ������ ž 8�� �̵� ����
				temp = ����8;
				break;
			case 56040: // ����� ���� ���ε� ������ ž 9�� �̵� ����
				temp = ����9;
				break;
			case 56041: // ����� ���� ���弭Ŀ
				temp = ��;
				break;
			case 56042: // ����� ���� ������ �������� �����
				temp = ����;
				break;
			case 56043: // ����� ���� ������ �������� ����
				temp = ����;
				break;
			case 56044: // ����� ���� ������ ����
				temp = ����;
				break;
			case 56045: // ����� ���� ������ ����
				temp = ����;
				break;
			case 56046: // ����� ���� �þ��� �ɾ�
				temp = �ɾ�;
				break;
			case 56088: // ����� ���� ������ Ƽ���� ����
				temp = ��Ƽ;
				break;
			case 56047: // ����� ���� ���� ������ ������
				temp = ����;
				break;
			case 56048: // ����� ���� ���̼�Ʈ ���̾�Ʈ�� ����
				temp = ����;
				break;
			case 56049: // ����� ���� �����Ϸ��� �ܰ�
				temp = ����;
				break;
			case 56050: // ����� ���� ������ �����
				temp = ������;
				break;
			case 56051: // ����� ���� �Ϸ��� ����
				temp = �Ϸ¹�;
				break;
			case 56052: // ����� ���� ���� ����
				temp = ���;
				break;
			case 56054: // ����� ���� ������ ����(�������)
				temp = ����;
				break;
			case 56055: // ����� ���� ������ ����(Ÿ��ź ��)
				temp = Ÿ��ź��;
				break;
			case 56056: // ����� ���� ������ ����(Ÿ��ź ����)
				temp = Ÿ��ź����;
				break;
			case 56057: // ����� ���� ������ ����(Ÿ��ź ��)
				temp = Ÿ��ź��;
				break;
			case 56058: // ����� ���� ������ ����(�ҿ� ���� ������)
				temp = ����;
				break;
			case 56059: // ����� ���� ������ ����(��Ʈ����ũ ����)
				temp = ����;
				break;
			case 56085: // ����� ���� ������ ����(� ���ε�)
				temp = ���;
				break;
			case 56060: // ����� ���� ������ ����(����Ʈ ����)
				temp = ����;
				break;
			case 56061: // ����� ���� ���Ͻ��� ����
				temp = ���Ͻ���;
				break;
			case 56062: // ����� ���� ������ ����
				temp = ���Ĺ�;
				break;
			case 56063: // ����� ���� ���ְ��� ����
				temp = ���ְ�;
				break;
			case 56064: // ����� ���� ī������ ����
				temp = ī���ĸ�;
				break;
			case 56065: // ����� ���� Ŀ���� ����
				temp = Ŀ����;
				break;
			case 56066: // ����� ���� Ŀ���� ��
				temp = Ŀ��;
				break;
			case 56067: // ����� ���� Ŀ���� ����
				temp = Ŀ����;
				break;
			case 56068: // ����� ���� Ŀ���� �尩
				temp = Ŀ����;
				break;
			case 56069: // ����� ���� Ŀ���� ����
				temp = Ŀ����;
				break;
			case 56070: // ����� ���� ũ�γ뽺�� ��Ʈ
				temp = ũ�κ�;
				break;
			case 56071: // ����� ���� Ÿ���� ����
				temp = Ÿ����;
				break;
			case 56072: // ����� ���� Ÿ���� �尩
				temp = Ÿ����;
				break;
			case 56073: // ����� ���� Ÿ��ź�� ��Ʈ
				temp = Ÿ��;
				break;
			case 56074: // ����� ���� ���� ����
				temp = ����;
				break;
			case 56075: // ����� ���� ������ �����
				temp = �����;
				break;
			case 56076: // ����� ���� �ı��� ���
				temp = ����;
				break;
			case 56077: // ����� ���� ������ �����
				temp = ����;
				break;
			case 56086: // ����� ���� ȥ���� ���� ��
				temp = ȥ��;
				break;
			case 56078: // ����� ���� ������� �κ�
				temp = ����κ�;
				break;
			case 56087: // ����� ���� ������� ����
				temp = �������;
				break;
			case 56079: // ����� ���� �������� ����(�Ƹ� �극��ũ)
				temp = �ƸӺ�;
				break;
			case 56080: // ����� ���� ���� ������
				temp = ����;
				break;
			case 56082: // ����� �߶�ī���� ����
				temp = �߶����;
				break;
			case 56083: // �Ÿ���
				temp = �Ÿ���;
				break;
			default:
				pc.sendPackets(new S_SystemMessage("\\aA�˸�: ����� ���� �����۸� �����մϴ�."));
				break;
			}
			if (temp != null) {
				boolean chance = false;
				for (int i = 0; i < item.length; i++) {
					if (l1iteminstance.getItemId() == item[i]) {
						if (_random.nextInt(99) + 1 <= Config.�����ǳ�����) {
							chance = true;
							// ���� ó��.
							createNewItem2(pc, temp[i], 1,
									l1iteminstance1.getEnchantLevel());
							pc.sendPackets(new S_SystemMessage(""
									+ l1iteminstance1.getName()
									+ "��(��) �� ������ �ο� �Ǿ����ϴ�."));
							break;
						}
						if (pc.isGm()) {
							pc.sendPackets(new S_SystemMessage("������Ȯ�� >> "
									+ Config.�����ǳ�����));
						}
					}
				}
				// Ȯ�� ���������� �޼��� ó��.
				if (chance == false) {
					pc.sendPackets(new S_SystemMessage(""
							+ l1iteminstance1.getName()
							+ "��(��) ����� ������� ���ϰ� �Ҹ��Ͽ����ϴ�."));
				}
				// ��� ���� ó��.
				pc.getInventory().DeleteEnchant(l1iteminstance1.getItemId(),
						l1iteminstance1.getEnchantLevel());
				pc.getInventory().removeItem(l1iteminstance, 1);
			}
		}
			break;

		}
	}

	private static boolean createNewItem2(L1PcInstance pc, int item_id,
			int count, int EnchantLevel) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else {
				pc.sendPackets(new S_ServerMessage(82));
				// ���� �������� �����ϰų� �κ��丮�� ������ �� �� �� �����ϴ�.
				return false;
			}
			// pc.sendPackets(new S_ServerMessage(403, item.getLogName())); //
			// %0�� �տ� �־����ϴ�.
			return true;
		} else {
			return false;
		}
	}

}