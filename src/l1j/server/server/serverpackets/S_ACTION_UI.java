package l1j.server.server.serverpackets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.AttendanceTable;
import l1j.server.server.datatables.AttendanceTable.AttendanceItem;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.templates.L1Item;
import l1j.server.server.utils.BinaryOutputStream;

public class S_ACTION_UI extends ServerBasePacket {

	private byte[] _byte = null;

	public static final int CRAFT_ITEM = 55; // ���� ������
	public static final int CRAFT_ITEMLIST = 57; // ���� ����Ʈ
	public static final int CRAFT_OK = 59; // ���� �Ϸ�
	public static final int CRAFT_GAUGEUI = 93;

	public static final int �Ž�ų = 110;
	public static final int DOLL_START = 123; // ����
	public static final int DOLL_RESULT = 125; // ���
	public static final int PCBANG_SET = 126;
	public static final int DOLL_READY = 128; // ����

	public static final int SAFETYZONE = 207;
	public static final int EMOTICON = 320;
	public static final int CLAN_JOIN_MESSAGE = 323;
	public static final int CLAN_JOIN_SETTING = 333;
	public static final int LOGIN_ADD_SKILL = 401;
	public static final int ADD_SKILL = 402;
	public static final int SPELL_PASSIVE_ONOFF_ACK = 409;
	public static final int TAM = 450;
	public static final int stateProfile = 487;
	public static final int Elixir = 489;
	public static final int ATTENDANCE_START = 544;
	public static final int ATTENDANCE_ITEM = 548;
	public static final int ��������޽��� = 592;
	public static final int BMTYPE_DEL_CHECK_ACK = 605;
	public static final int ATTENDANCE_COMPLETE = 1004;
	public static final int ATTENDANCE_TIMEOVER = 1005;
	public static final int ATTENDANCE_ITEM_COMPLETE = 1007;

	public static final int AINHASAD = 1020;

	public static final int SUMMON_PET_NOTI = 2321; // �������� ������ ǥ��
	public static final int PETBALL_CONTENTS_START_ACK = 2383; // �������� ���۽�

	private static final String S_ACTION_UI = "S_ACTION_UI";

	public S_ACTION_UI(int sub, boolean isSuccess, int objid, int gfxid, boolean isBless) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(sub);
		switch (sub) {
		case DOLL_RESULT:
			writeC(0x00);
			writeC(0x08);
			writeC(isSuccess ? 0 : 1);
			writeC(0x12);
			int Size = getBitSize(objid) + getBitSize(gfxid) + 4;
			writeC(Size);
			writeC(0x08);
			write4bit(objid);
			writeC(0x10);
			write4bit(gfxid);
			writeC(0x18);
			write4bit(isBless ? 0 : 1);
			writeH(0x00);
			break;
		}
	}

	public S_ACTION_UI(int subType, int time, L1DollInstance doll, L1ItemInstance item, boolean repeat) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(subType);
		switch (subType) {
		case SUMMON_PET_NOTI:
			writeC(0x08);
			writeBit(time);

			writeC(0x10);
			if (repeat)
				writeBit(doll.getId());
			else {
				if (time < 0)
					writeBit(-1);
				else
					writeBit(0);
			}

			writeC(0x18);
			if (repeat)
				writeBit(item.getId());
			else {
				if (time < 0)
					writeBit(-1);
				else
					writeBit(0);
			}

			if (item != null) {
				writeC(0x22);
				byte[] stats = item.getStatusBytes();
				writeBit(stats.length); // ������ǥ�� ����
				writeByte(stats); // ������ǥ��
			}
			break;
		}
		writeH(0);
	}

	public S_ACTION_UI(int type, int value, boolean isPCRoom, L1ItemInstance item, boolean world_message) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case ATTENDANCE_ITEM_COMPLETE:
			writeC(0x08);
			writeBit(value);
			writeC(0x10);
			writeBit(isPCRoom ? 0x01 : 0x00);
			writeC(0x18);
			writeBit(3);
			writeC(0x20);
			writeBit(item.getItem().getItemDescId());
			writeC(0x28);
			writeBit(item.getCount());
			writeC(0x30);
			writeBit(world_message ? 1 : 0);
			break;
		}
		writeH(0);
	}

	public S_ACTION_UI(int skillid, int time, int gfxid, int msg) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0x6e);
		writeC(0x00);
		writeC(0x08);
		writeC(0x02);
		writeC(0x10);
		write7B(skillid);
		writeC(0x18);
		write7B(time);
		writeC(0x20);
		int duration_show_type = 8;
		if (skillid == L1SkillId.CLAN_BLESS_BUFF)
			duration_show_type = 10;
		writeC(duration_show_type);

		writeC(0x28);
		write7B(gfxid);
		writeC(0x30);
		writeC(0x00);
		writeC(0x38);
		writeC(0x03);
		writeC(0x40);
		write7B(msg);
		writeC(0x48);
		writeC(0x00);
		writeC(0x50);
		writeC(0x00);
		writeC(0x58);
		writeC(0x01);
		writeC(0x60);
		writeC(0x00);
		writeC(0x68);
		writeC(0x00);
		writeC(0x70);
		writeC(0x00);
		writeH(0x00);
	}

	/**
	 * ��Ƽ ǥ�� ������ �����.
	 */
	public S_ACTION_UI(byte[] flag) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(339);
		writeByte(flag);
		writeH(0);
	}

	public S_ACTION_UI(int type, int MessageType, L1ItemInstance Item) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case ��������޽���:
			String ItemEnchantLV = " +" + Item.getEnchantLevel();
			String Bleesed = "�ູ ���� ";
			String ItemAttrEnchantLV = null;
			switch (Item.getAttrEnchantLevel()) {
			case 1:
				ItemAttrEnchantLV = "ȭ��:1�� ";
				break; // ȭ��1��
			case 2:
				ItemAttrEnchantLV = "ȭ��:2�� ";
				break; // ȭ��2��
			case 3:
				ItemAttrEnchantLV = "ȭ��:3�� ";
				break; // ȭ��3��
			case 4:
				ItemAttrEnchantLV = "ȭ��:4�� ";
				break; // ȭ��4��
			case 5:
				ItemAttrEnchantLV = "ȭ��:5�� ";
				break; // ȭ��5��
			case 6:
				ItemAttrEnchantLV = "����:1�� ";
				break; // ����1��
			case 7:
				ItemAttrEnchantLV = "����:2�� ";
				break; // ����2��
			case 8:
				ItemAttrEnchantLV = "����:3�� ";
				break; // ����3��
			case 9:
				ItemAttrEnchantLV = "����:4�� ";
				break; // ����4��
			case 10:
				ItemAttrEnchantLV = "����:5�� ";
				break; // ����5��
			case 11:
				ItemAttrEnchantLV = "ǳ��:1�� ";
				break; // ǳ��1��
			case 12:
				ItemAttrEnchantLV = "ǳ��:2�� ";
				break; // ǳ��2��
			case 13:
				ItemAttrEnchantLV = "ǳ��:3�� ";
				break; // ǳ��3��
			case 14:
				ItemAttrEnchantLV = "ǳ��:4�� ";
				break; // ǳ��4��
			case 15:
				ItemAttrEnchantLV = "ǳ��:5�� ";
				break; // ǳ��5��

			case 16:
				ItemAttrEnchantLV = "����:1�� ";
				break; // ����1��
			case 17:
				ItemAttrEnchantLV = "����:2�� ";
				break; // ����2��
			case 18:
				ItemAttrEnchantLV = "����:3�� ";
				break; // ����3��
			case 19:
				ItemAttrEnchantLV = "����:4�� ";
				break; // ����4��
			case 20:
				ItemAttrEnchantLV = "����:5�� ";
				break; // ����5��
			default:
				break;
			}
			writeC(0x08);
			writeC(0x03);
			writeC(0x10);
			writeBit(MessageType);
			writeC(0x22);
			if (Item.getEnchantLevel() > 0) {
				if (Item.getAttrEnchantLevel() > 0) {
					writeBit(ItemAttrEnchantLV.getBytes().length + ItemEnchantLV.getBytes().length + Item.getItem().getNameId().getBytes().length);
					writeByte(ItemAttrEnchantLV.getBytes());
					writeByte(ItemEnchantLV.getBytes());
					writeByte(Item.getItem().getNameId().getBytes());
				} else {
					writeBit(ItemEnchantLV.getBytes().length + Item.getItem().getNameId().getBytes().length);
					writeByte(ItemEnchantLV.getBytes());
					writeByte(Item.getItem().getNameId().getBytes());
				}
			} else {
				if (Item.getItem().getBless() == 0) {
					writeBit(Bleesed.getBytes().length + Item.getItem().getNameId().getBytes().length);
					writeByte(Bleesed.getBytes());
					writeByte(Item.getItem().getNameId().getBytes());
				} else {
					writeBit(Item.getItem().getNameId().getBytes().length);
					writeByte(Item.getItem().getNameId().getBytes());
				}
			}
			writeC(0x28);
			writeBit(Item.getItem().getItemDescId());
			break;
		}
		writeH(0);
	}

	public S_ACTION_UI(int type, int MessageType, String addmessage, L1ItemInstance Item) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case ��������޽���:
			String ItemEnchantLV = " +" + Item.getEnchantLevel();
			String Bleesed = "�ູ ���� ";
			String ItemAttrEnchantLV = null;
			switch (Item.getAttrEnchantLevel()) {
			case 1:
				ItemAttrEnchantLV = "ȭ��:1�� ";
				break; // ȭ��1��
			case 2:
				ItemAttrEnchantLV = "ȭ��:2�� ";
				break; // ȭ��2��
			case 3:
				ItemAttrEnchantLV = "ȭ��:3�� ";
				break; // ȭ��3��
			case 4:
				ItemAttrEnchantLV = "ȭ��:4�� ";
				break; // ȭ��4��
			case 5:
				ItemAttrEnchantLV = "ȭ��:5�� ";
				break; // ȭ��5��
			case 6:
				ItemAttrEnchantLV = "����:1�� ";
				break; // ����1��
			case 7:
				ItemAttrEnchantLV = "����:2�� ";
				break; // ����2��
			case 8:
				ItemAttrEnchantLV = "����:3�� ";
				break; // ����3��
			case 9:
				ItemAttrEnchantLV = "����:4�� ";
				break; // ����4��
			case 10:
				ItemAttrEnchantLV = "����:5�� ";
				break; // ����5��
			case 11:
				ItemAttrEnchantLV = "ǳ��:1�� ";
				break; // ǳ��1��
			case 12:
				ItemAttrEnchantLV = "ǳ��:2�� ";
				break; // ǳ��2��
			case 13:
				ItemAttrEnchantLV = "ǳ��:3�� ";
				break; // ǳ��3��
			case 14:
				ItemAttrEnchantLV = "ǳ��:4�� ";
				break; // ǳ��4��
			case 15:
				ItemAttrEnchantLV = "ǳ��:5�� ";
				break; // ǳ��5��

			case 16:
				ItemAttrEnchantLV = "����:1�� ";
				break; // ����1��
			case 17:
				ItemAttrEnchantLV = "����:2�� ";
				break; // ����2��
			case 18:
				ItemAttrEnchantLV = "����:3�� ";
				break; // ����3��
			case 19:
				ItemAttrEnchantLV = "����:4�� ";
				break; // ����4��
			case 20:
				ItemAttrEnchantLV = "����:5�� ";
				break; // ����5��
			default:
				break;
			}
			writeC(0x08);
			writeC(0x03);
			writeC(0x10);
			writeBit(MessageType);
			writeC(0x22);
			if (Item.getEnchantLevel() > 0) {
				if (Item.getAttrEnchantLevel() > 0) {
					writeBit(addmessage.getBytes().length + ItemAttrEnchantLV.getBytes().length + ItemEnchantLV.getBytes().length
							+ Item.getItem().getNameId().getBytes().length);
					writeByte(addmessage.getBytes());
					writeByte(ItemAttrEnchantLV.getBytes());
					writeByte(ItemEnchantLV.getBytes());
					writeByte(Item.getItem().getNameId().getBytes());
				} else {
					writeBit(addmessage.getBytes().length + ItemEnchantLV.getBytes().length + Item.getItem().getNameId().getBytes().length);
					writeByte(addmessage.getBytes());
					writeByte(ItemEnchantLV.getBytes());
					writeByte(Item.getItem().getNameId().getBytes());
				}
			} else {
				if (Item.getItem().getBless() == 0) {
					writeBit(addmessage.getBytes().length + Bleesed.getBytes().length + Item.getItem().getNameId().getBytes().length);
					writeByte(addmessage.getBytes());
					writeByte(Bleesed.getBytes());
					writeByte(Item.getItem().getNameId().getBytes());
				} else {
					writeBit(addmessage.getBytes().length + Item.getItem().getNameId().getBytes().length);
					writeByte(addmessage.getBytes());
					writeByte(Item.getItem().getNameId().getBytes());
				}
			}
			writeC(0x28);
			writeBit(Item.getItem().getItemDescId());
			break;
		}
		writeH(0);
	}

	public S_ACTION_UI(int descid, L1ItemInstance Item) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(592);
		writeC(0x08);
		writeC(0x03);
		writeC(0x10);
		writeBit(3599);
		writeC(0x22);
		writeBit(Item.getItem().getNameId().getBytes().length);
		writeByte(Item.getItem().getNameId().getBytes());
		writeC(0x28);
		writeBit(descid);
		writeH(0);
	}

	public S_ACTION_UI(int type, L1PcInstance pc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case AINHASAD:
			int value = pc.getAinHasad() / 10000;
			writeC(0x08);
			writeBit(value);

			/** �⺻ �ۼ������� 100�� */
			int valueTemp = value;
			if (valueTemp > 0)
				valueTemp = 10000;
			writeC(0x10);
			writeBit(valueTemp);

			writeC(0x18);
			writeBit(pc.get�ູ����ġ());

			/** �ູ �Ҹ��� **/
			writeC(0x20);
			writeBit(pc.get�ູ�Ҹ�ȿ��());

			/** ���� ���ʽ� **/
			writeC(0x30);
			writeBit(pc.getAinHasadDP());

			writeC(0x38);
			writeBit(0x01);
			/** ���͹����� �ֵ��� ���� Ÿ�� ������ */
			if (pc.getClan() != null) {
				short mapId = pc.getMapId();
				if (pc.getClan().isHuntMapChoice()) {
					if (pc.getClan().getBlessHuntMapIds().contains((int) mapId)) {// ������ऻ����
						writeC(0x28);
						writeBit(1); // �ູ ����
					}
				}
			}
			break;
		}
		writeH(0);
	}

	public S_ACTION_UI(int type) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case DOLL_START:
			writeC(0x08);
			writeC(0x03);
			break;
		case CRAFT_ITEM:
			writeH(0x08);
			writeC(0x08);
			writeC(0x03);
			break;
		case CRAFT_OK:
			writeH(0x08);
			break;
		case ATTENDANCE_START:
			writeC(0x08);
			writeBit(60);
			writeC(0x10);
			writeBit(1440);
			writeC(0x18);
			writeBit(0x01);
			writeC(0x20);
			writeBit(0x01);
			writeC(0x28);
			writeBit(0x02);
			break;
		}
		writeH(0);
	}

	/**
	 * PC��,������Ƽ�� ���� by �°�
	 * 
	 * @param isOpen on/off
	 **/
	public S_ACTION_UI(int code, boolean isOpen) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(code);
		switch (code) {
		case PCBANG_SET: {
			writeC(0x00);
			writeC(0x08);
			writeC(isOpen ? 1 : 0);
			writeC(0x10);
			writeC(0x01); // ��ŷ��ưȰ��ȭ ��Ŷ
			writeH(0);
			break;
		}
		case SAFETYZONE: {
			writeC(0x01);
			writeC(0x08);
			write7B(isOpen ? 128 : 0);
			writeC(0x10);
			writeC(0x00);
			writeC(0x18);
			writeC(0x00);
			writeH(0);
			break;
		}
		case CRAFT_GAUGEUI:
			writeC(0x08);
			writeC(0x00);
			writeC(0x10);
			writeC(0);
		}
	}

	// �������̺�
	public S_ACTION_UI(int[] craftList) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(CRAFT_ITEMLIST); // 57
		writeC(0x08);
		writeBit(0);
		for (int i = 0; i < craftList.length; i++) {
			int list = craftList[i];
			int size = getBitSize(list) + 5;
			writeC(0x12);
			writeBit(size);
			writeC(0x08);
			writeBit(list);
			writeC(0x10);
			writeBit(0x00);
			writeC(0x18);
			writeBit(0x00);
		}
		writeH(0x00);
	}

	public S_ACTION_UI(L1NpcInstance npc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(CRAFT_ITEMLIST);
		writeH(0x08);
		int craftlist[] = null;
		try {
			switch (npc.getNpcId()) { // 69,70,71,72,73,74,75,76,77,78�����Ǳ� ~ ����Ǳ�

			/** ���� �һ�����^�׹� */
			case 102010:
				craftlist = new int[] { 5105, 5106, 5107, 5108, 5109, 5110, 5111, 5112, 5075, 5081, 5084, 5093, 5076, 5077, 5078, 5079, 5080, 5082, 5083, 5085,
						5086, 5087, 5088, 5089, 5090, 5091, 5092, 5094, 5095, 5096, 5097, 5098, 5099, 5100, 5101, 5102, 5103, 5104, 5114 };
				break;

			/** �Ǽ����� �һ�����^��� */
			case 102011:
				craftlist = new int[] { 5139, 5151, 5152, 5130, 5131, 5132, 5133, 5134, 5135, 5136, 5137, 5138, 5140, 5141, 5142, 5143, 5144, 5145, 5146, 5147,
						5148, 5149, 5150, 5153 };
				break;

			/** ������ �һ�����^�帮 */
			case 102012:
				craftlist = new int[] { 5030, 5031, 5032, 5020, 5021, 5022, 5023, 5024, 5193, 5194, 5025, 5026, 5027, 5028, 5029, 5820, 5821, 5822, 5823, 5824,
						5825 };
				break;

			/** ���ݼ� �һ�����^�� */
			case 102013:
				craftlist = new int[] { 5190, 5171, 5172, 5173, 5174, 5175, 5176, 5177, 5178, 5179, 5180, 5181, 5182, 5183, 5184, 5185, 5186, 5187, 5188,
						5189 };
				break;
			/** ������� - �׼����� - �巡���� ������ - **/ //
			case 101026:
				craftlist = new int[] { 496, 497 };
				break;

			case 77225:
				craftlist = new int[] { 4078, 4079, 4080, 4081, 4082, 4083, 4084, 4085, 4086, 4087, 4088, 4089 };
				break;
			/** ���� Ž�� - **/ //
			case 100778:
				craftlist = new int[] { 2388, 2389, 2390 };
				break;
			/** ������� - ������ - **/ // �Ϸ�
			case 101035:
				craftlist = new int[] { 1043, 1044, 1045, 1046, 1047, 1048 };
				break;
			/****************************************************************************************************/
			/** �������� - Į��� **/ // �Ϸ�
			case 100629:
				craftlist = new int[] { 2865, 60, 63, 64 };
				break;
			/** ���� ��ų �̺�Ʈ ���� **/ // �Ϸ�
			case 60245:
				craftlist = new int[] { 5726, 6068, 6069, 6070, 6071, 6072, 6073, 6074, 6075, 6076 };
				break;
			/** �������� - �������� **/ // �Ϸ�
			case 4212002:
				craftlist = new int[] { 103, 104, 105, 106, 107, 108, 109, 496, 497 };
				break;
			/** �������� - ������ ���� **/ // �Ϸ�
			case 4212003:
				craftlist = new int[] { 2583, 2584, 2585, 2586, 2587, 2588, 2589, 2590, 2591, 2592, 2593, 2594 };
				break;
			/** �������� - ������ ����ȣ **/ // �Ϸ�
			case 4212004:
				craftlist = new int[] { 2595, 2596, 2597, 2598, 2599, 2600, 2601, 2602, 2603, 2604, 2605, 2606 };
				break;
			/** ������� - ���̿��� **/ // ������� �߰�, ��ɲ��� �߰�, �������� �߰�, ���ְ��� �߰�, �������� �߰�, �븶������ �߰�
			case 4212005:
				craftlist = new int[] { 3043, 3044, 3045, 6129, 6130, 6131, };
				break;
			/** �������� - ������ �ٿ��� **/ // �Ϸ�
			case 4212006:
				craftlist = new int[] { 423, 424, 425, 2182, 2183, 2184, 2227, 2185 };
				break;
			/** �������� - ������ �Ұ� **/ // �Ϸ� ������ ���������� ���۵ǰԲ� ���ļ��� //�Ϸ�
			case 100029:
				craftlist = new int[] { 515, 514, 516, 517, 116, 123, 159, 160, 161, 162, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284,
						285, 286, 287, 288, 289, 290 };
				break;
			/** �ؼ� - ��Ű **/ // ��Ű�Ϸ�
			case 7310086:
				craftlist = new int[] { 1771, 1772, 1773, 1774, 1775, 1776, 1777, 1778, };
				break;
			/****************************************************************************************************/
			/** ���縶�� - �������� ǻ�� **/ // �Ϸ�
			case 4218001:
				craftlist = new int[] { 62, 65, 63, 64, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78 };
				break;
			/** ȯ���縶�� - �������� ��Ʈ�� **/ // �Ϸ� ////�Ϸ�ű�
			case 4219001:
				craftlist = new int[] { 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78 };
				break;
			/****************************************************************************************************/
			/** ȭ���θ��� - ���̶� **/ // �Ϸ� ////�ű� �Ϸ� ����
			case 70811:
				craftlist = new int[] { 417, 418, 419, 420 };
				break;
			/****************************************************************************************************/
			/** ��������3�� - Ÿ�� ������ **/ // �Ϸ� �űԿϷ�
			case 70763:
				craftlist = new int[] { 212, 213, 214, 2057, 2058, 2059, 2060, 2061, 2064, 2063, 2065 };
				break;
			/****************************************************************************************************/
			/** �Ƶ����� - ����� **/ // �Ϸ� //�űԿϷ�
			case 6000101:
				craftlist = new int[] { 4589, 823, 824, 825, 826, 827, 828, 829, 830, 831, 832, 833, 834, 835, 836, 837, 838, 839, 840, 841, 842, 843, 844, 845,
						846, 847, 848, 849, 850, 851, 852 };
				break;
			/****************************************************************************************************/
			/** ������ - ���̷� **/ // �Ϸ�
			case 100644:
				craftlist = new int[] { 577, 578, 579, 580, 581, 4354, 4355, 4356, 4357, 4358, 4359, 4360, 4361, 4362, 4363, 4364, 4365, 4366, 4367, 4368 };
				break;
			/** ������� ���尣 */
			case 60238:
				craftlist = new int[] { 3996, 4091 };
				break;
			/****************************************************************************************************/
			/** ��Ÿ���� **/ // �Ϸ�
			case 71119:
				craftlist = new int[] { 56 };
				break;
			/** �Ƶ����� **/ // �Ϸ�
			case 71125:
				craftlist = new int[] { 704, 706, 707, 705, 305, 309, 313, 1734, 1735, 1736, 1737, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55 };
				break;

			/** �𸮾� **/ // �Ϸ� ������
			case 70598:
				craftlist = new int[] { 5892, 1043, 1044, 1045, 1046, 1047, 1048 };
				break;// 198 ã�ƾ���
			/** ���� �ű� **/ // �Ϸ�
			case 11887:
				craftlist = new int[] { 1771, 1772, 1773, 1774, 1775, 1776, 1777, 1778, 1763, 1764, 1765, 1766, 1767, 1768, 1769, 1770 };
				break;
			/** ����ű� **/ // �巡�� �����̾� ����
			case 70614:
				craftlist = new int[] { 6132 };
				break;
			/** ���� ��ȭ ���� **/ // �����ָӴϻ���Ϸ�
			case 301028:
				craftlist = new int[] { 3385, 3386, 3387, 2747, 4396, 4397, 4398, 4399, 4400, 4401, 4402, 4403, 4404, 4405, 4406, 4407 };
				break;
			/** �߰� ���� **/ // �׷��� �Ϸ�
			case 70838:
				craftlist = new int[] { 2864, 2857, 2865, 2861, 2768, 2769, 2770, 2771, 2772, 2773, 2774, 2759, 173, 183, 211 };
				break; // �׷���

			case 70840: // �κ�
				craftlist = new int[] { 2762, 2864, 2857, 2865, 2861, 2768, 2769, 2770, 2771, 2772, 2773, 2774, 2759, 173, 183, 211 };
				break; // �׷���

			/** ���� ����(�߷�) **/ //// �Ϸ�
			case 80069:
				craftlist = new int[] { 35, 36, 37, 38 };
				break;
			/** �߷����� - �߷��� �н� **/
			case 80068:
				craftlist = new int[] { 39, 40, 41, 42 };
				break;
			/** ���� ���°� **/
			case 70652:
				craftlist = new int[] { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33 };
				break;
			/** ��Ʈ **/
			case 70848:
				craftlist = new int[] { 188, 189, 190 };
				break;
			/** ������ **/
			case 70837:
				craftlist = new int[] { 186 };
				break;
			/** ��Ƽ�� (�ַκδ��̺�Ʈ����) **/
			case 1:
				craftlist = new int[] { 360, 361, 362, 363 };
				break;
			/** ������ **/
			case 7000078:
				craftlist = new int[] { 159, 160 };
				break;
			/** ��� **/
			case 70841:
				craftlist = new int[] { 187, 188 };
				break;
			/** �ƶ�ũ�� **/
			case 70846:
				craftlist = new int[] { 163, 164, 165 };
				break;
			/** �� **/
			case 70850:
				craftlist = new int[] { 166, 167, 168 };
				break;
			/** �� **/
			case 70851:
				craftlist = new int[] { 169, 170 };
				break;
			/** �� �� **/
			case 70852:
				craftlist = new int[] { 169, 170, 171 };
				break;
			/** �������� - ������ �������� **/
			case 80053:
				craftlist = new int[] { 156, 2044 };
				break;
			/** �������� - ������ **/
			case 80054:
				craftlist = new int[] { 7, 8, 9 };
				break;
			/** �������� - ���� **/
			case 80051:
				craftlist = new int[] { 6 };
				break;
			/** �߷����� - �߷��� �������� **/
			case 80072:
				craftlist = new int[] { 156, 2044, 35, 36, 37, 38, 3378, 141, 142, 143, 144, 145, 146, 147, 148, 39, 40, 2036, 1805, 1806, 1807, 1808, 1809 };
				break;
			/** ���� **/
			case 102019:
				craftlist = new int[] { 708, 709, 710, 711, 156, 2044, 35, 36, 37, 38, 3378, 141, 142, 143, 144, 145, 146, 147, 148, 39, 40, 2036, 1805, 1806,
						1807, 1808, 1809 };
				break;
			/** ��Ʈ **/
			case 102020:
				craftlist = new int[] { 3196, 2788, 2789, 194, 195, 196 };
				break;
			// �Ƴ�� �̺�Ʈ ���Ǿ�
			case 6:
				craftlist = new int[] { 1629, 1630, 1631, 1632, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1642, 1643, 1644, 1645, 1646 };
				break;
			case 8:
				craftlist = new int[] { 1647, 1648, 1649, 1650, 1651, 1652, 1653, 1654, 1655 };
				break;

			// �ٷ�Ʈ �Ϸ�
			case 70520:
				craftlist = new int[] { 1861, 95, 96, 97, 98, 99, 1960, 1961 };
				break;

			// ��� ��ǰ ���ݼ���^�Ƹ�
			case 7210073:
				craftlist = new int[] { 2652, 2653 };
				break;

			// ��������_��� �Ϸ�
			case 70027:
				craftlist = new int[] { 2779, 2780, 2781, 2782, 3420, 2739, 2792, 2731, 2732, 2733, 2734, 2735, 2736, 2737, 2738, 2793, 2790, 2791 };
				break;

			// ���̿� //�Ϸ�
			case 70676:
				craftlist = new int[] { 238, 241, 240, 239, 232, 233, 234, 237, 236, 235, 228, 230, 231, 229 };
				break;

			// ��ť���� //�Ϸ�
			case 87000:
				craftlist = new int[] { 2879, 2880 };
				break;

			/** ��Ʈ�� ����, �̺���(���� ��ȭ����) **/ // by.���ۻ�̳�
			case 70662:
				craftlist = new int[] { 5913, 5914, 5915, 5916, 5917, 5918, 5919, 5920, 5921, 5922, // �����ϻ���Ǽ��� //���Ľ� ���� ����
						5933, 5934, 5935, 5936, 5937, 5938, 5939, 5940, 5941, 5942, // �׶�ī���ǽ��� //���Ľ� ���� ����

						5893, 5894, 5895, 5896, 5897, 5898, 5899, 5900, 5901, 5902, // ����ǰ� //����
						3715, 3716, 3717, 3718, 3719, 3720, 3721, 3722, 3723, 3724, // ����Ȳ�� ����� //����
						3728, 3729, 3730, 3731, 3732, 3733, 3734, 3735, 3736, 3737, // �ٶ�Į���� �ܰ� //����
						3741, 3742, 3743, 3744, 3745, 3746, 3747, 3748, 3749, 3750, // ��������ü ������ //����
						3754, 3755, 3756, 3757, 3758, 3759, 3760, 3761, 3762, 3763, // ���� �׸����� �̵��� //����
						3767, 3768, 3769, 3770, 3771, 3772, 3773, 3774, 3775, 3776, // ���̾��� �ݳ� //����
						3780, 3781, 3782, 3783, 3784, 3785, 3786, 3787, 3788, 3789, // ũ�γ뽺�� ���� //����
						3793, 3794, 3795, 3796, 3797, 3798, 3799, 3800, 3801, 3802, // ���丮���� ���� //����
						3806, 3807, 3808, 3809, 3810, 3811, 3812, 3813, 3814, 3815, // Ÿ��ź�� �г� //����

						5903, 5904, 5905, 5906, 5907, 5908, 5909, 5910, 5911, 5912, // ����� �� //��ż���
						4429, 4430, 4431, 4432, 4433, 4434, 4435, 4436, 4437, 4438, // ����Ȳ�� ����� //��ż���
						4442, 4443, 4444, 4445, 4446, 4447, 4448, 4449, 4450, 4451, // �ٶ�Į���� �ܰ� //��ż���
						4455, 4456, 4457, 4458, 4459, 4460, 4461, 4462, 4463, 4464, // ��������ü ������ //��ż���
						4468, 4469, 4470, 4471, 4472, 4473, 4474, 4475, 4476, 4477, // ���� �׸����� �̵��� //��ż���
						4481, 4482, 4483, 4484, 4485, 4486, 4487, 4488, 4489, 4490, // ���̾��� �ݳ� //��ż���
						4494, 4495, 4496, 4497, 4498, 4499, 4500, 4501, 4502, 4503, // ũ�γ뽺�� ���� //��ż���
						4507, 4508, 4509, 4510, 4511, 4512, 4513, 4514, 4515, 4516, // ���丮���� ���� //��ż���
						4520, 4521, 4522, 4523, 4524, 4525, 4526, 4527, 4528, 4529, // Ÿ��ź�� �г� //��ż���

						2571, 2575, 2579, 2572, 2576, 2580, 2573, 2577, 2581, 2574, 2578, 2582, // ��Ÿ�Ϸ� 789 //��Ǫ�Ϸ� 789 //����Ϸ� 789 //�߶�Ϸ� 789
						2607, 2611, 2615, 2608, 2612, 2616, 2609, 2613, 2617, 2610, 2614, 2618, // ��Ÿ���� 789 //��Ǫ���� 789 //���忹�� 789 //�߶��� 789
						2595, 2599, 2603, 2596, 2600, 2604, 2597, 2601, 2605, 2598, 2602, 2606, // ��Ÿ�γ� 789 //��Ǫ�γ� 789 //�����γ� 789 //�߶��γ� 789
						2583, 2587, 2591, 2584, 2588, 2592, 2585, 2589, 2593, 2586, 2590, 2594, // ��Ÿ���� 789 //��Ǫ���� 789 //���帶�� 789 //�߶󸶷� 789

						6195, 6196, 6197, 6198, 6200, 6199, 6201, 6202, 6203, 6204, // �߶����(������), �߶����(��ũ����), �߶���(����), ��Ǫ���(�Ǹ�), ��Ǫ�̵���(��ȿ), ��Ǫ�̵���(����),
																					// ��Ÿ����(��ǳ), ��Ÿ������(���ν�), ����ü�μҵ�(��ü), ����Ű��ũ(��Ű)
						6214, 6215, 6216, 6217, 6219, 6218, 6220, 6221, 6222, 6223, // �Ӽ� 1��
						6224, 6225, 6226, 6227, 6229, 6228, 6230, 6231, 6232, 6233, // �Ӽ� 2��
						6234, 6235, 6236, 6237, 6239, 6238, 6240, 6241, 6242, 6243, // �Ӽ� 3��
						6244, 6245, 6246, 6247, 6249, 6248, 6250, 6251, 6252, 6253, // �Ӽ� 4��
						6254, 6255, 6256, 6257, 6259, 6258, 6260, 6261, 6262, 6263, // �Ӽ� 5��
				};

				break;

			/** ��Ʈ�� ����, ����(�巡�� ���ۻ���) **/
			case 70904:
				craftlist = new int[] { 203, 204, 205, 206, 207, 208, 209, 210, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 220, 226, 223,
						219, 225, 222, 221, 224, 216, 215, 217, 218, 70, 71, 72, 73, 74, 75, 76, 77, 78, 103, 104, 105, 106, 107, 108, 109, 6133, 6134 // ����,����,ȭ��,ǳ��,ź��,����,����,���,����
																																						// ����
						, 6135, 6136, 6137// ���Ľ� ���� ����
						, 6138, 6139, 6140, 6141, 6142, 6143, 6144, 6145, 6146/** ���Ľ��Ϸ� ��þ */
						, 6147, 6148, 6149, 6150, 6151, 6152, 6153, 6154, 6155/** ���Ľ���������þ */
						, 6156, 6157, 6158, 6159, 6160, 6161, 6162, 6163, 6164/** ���Ľ�������þ */
						, 6165, 6166, 6167, 6168, 6169, 6170, 6171, 6172, 6173, 6174/** ���Ľ��Ϸ±�ȯ */
						, 6175, 6176, 6177, 6178, 6179, 6180, 6181, 6182, 6183, 6184/** ���Ľ������±�ȯ */
						, 6185, 6186, 6187, 6188, 6189, 6190, 6191, 6192, 6193, 6194/** ���Ľ����±�ȯ */
				};
				break;
			// ��� �����׽�Ʈ
			case 709044:
				craftlist = new int[] { Config.CRAFT_TABLE_ONE, Config.CRAFT_TABLE_TWO, Config.CRAFT_TABLE_THREE, Config.CRAFT_TABLE_FOUR,
						Config.CRAFT_TABLE_FIVE, Config.CRAFT_TABLE_SIX, Config.CRAFT_TABLE_SEVEN, Config.CRAFT_TABLE_EIGHT, Config.CRAFT_TABLE_NINE,
						Config.CRAFT_TABLE_TEN };
				break;
			// �����ǹ���
			case 100625:
				craftlist = new int[] { 3879, 3880, 3881, 3882, 110, 111, 112, 113, 114, 115, 520 };
				break;

			// 3408,3409 ����� ������, ����Ǳݺ���

			case 460000128:
				craftlist = new int[] { Config.CRAFT_TABLE_ONE, Config.CRAFT_TABLE_TWO, Config.CRAFT_TABLE_THREE, Config.CRAFT_TABLE_FOUR,
						Config.CRAFT_TABLE_FIVE, Config.CRAFT_TABLE_SIX, Config.CRAFT_TABLE_SEVEN, Config.CRAFT_TABLE_EIGHT, Config.CRAFT_TABLE_NINE,
						Config.CRAFT_TABLE_TEN };
				break;

			case 870001:
				int t = Config.CRAFT_TABLE;
				craftlist = new int[] { t, t + 1, t + 2, t + 3, t + 4, t + 5, t + 6, t + 7, t + 8, t + 9 };
				break;
			case 870002:
				int tt = Config.CRAFT_TABLE + 10;
				craftlist = new int[] { tt, tt + 1, tt + 2, tt + 3, tt + 4, tt + 5, tt + 6, tt + 7, tt + 8, tt + 9 };
				break;
			case 870003:
				int ttt = Config.CRAFT_TABLE + 20;
				craftlist = new int[] { ttt, ttt + 1, ttt + 2, ttt + 3, ttt + 4, ttt + 5, ttt + 6, ttt + 7, ttt + 8, ttt + 9 };
				break;
			case 870004:
				int tttt = Config.CRAFT_TABLE + 30;
				craftlist = new int[] { tttt, tttt + 1, tttt + 2, tttt + 3, tttt + 4, tttt + 5, tttt + 6, tttt + 7, tttt + 8, tttt + 9 };
				break;
			case 870005:
				int ttttt = Config.CRAFT_TABLE + 40;
				craftlist = new int[] { ttttt, ttttt + 1, ttttt + 2, ttttt + 3, ttttt + 4, ttttt + 5, ttttt + 6, ttttt + 7, ttttt + 8, ttttt + 9 };
				break;
			case 870006:
				int tttttt = Config.CRAFT_TABLE + 50;
				craftlist = new int[] { tttttt, tttttt + 1, tttttt + 2, tttttt + 3, tttttt + 4, tttttt + 5, tttttt + 6, tttttt + 7, tttttt + 8, tttttt + 9 };
				break;
			case 870007:
				int ttttttt = Config.CRAFT_TABLE + 60;
				craftlist = new int[] { ttttttt, ttttttt + 1, ttttttt + 2, ttttttt + 3, ttttttt + 4, ttttttt + 5, ttttttt + 6, ttttttt + 7, ttttttt + 8,
						ttttttt + 9 };
				break;
			case 870008:
				int tttttttt = Config.CRAFT_TABLE + 70;
				craftlist = new int[] { tttttttt, tttttttt + 1, tttttttt + 2, tttttttt + 3, tttttttt + 4, tttttttt + 5, tttttttt + 6, tttttttt + 7,
						tttttttt + 8, tttttttt + 9 };
				break;
			case 870009:
				int ttttttttt = Config.CRAFT_TABLE + 80;
				craftlist = new int[] { ttttttttt, ttttttttt + 1, ttttttttt + 2, ttttttttt + 3, ttttttttt + 4, ttttttttt + 5, ttttttttt + 6, ttttttttt + 7,
						ttttttttt + 8, ttttttttt + 9 };
				break;
			case 870010:
				int tttttttttt = Config.CRAFT_TABLE + 90;
				craftlist = new int[] { tttttttttt, tttttttttt + 1, tttttttttt + 2, tttttttttt + 3, tttttttttt + 4, tttttttttt + 5, tttttttttt + 6,
						tttttttttt + 7, tttttttttt + 8, tttttttttt + 9 };
				break;
			// ���_������ �ų�
			case 450001831:
				craftlist = new int[] { 3870, 3871 };
				break;
			}

			int num;
			for (int i = 0; i < craftlist.length; i++) {
				writeC(0x12);
				num = craftlist[i];
				if (num > 127) {
					writeC(0x07);
				} else {
					writeC(0x06);
				}
				writeC(0x08);
				write4bit(num);
				writeH(0x10);
				writeH(0x18);
			}
			writeH(0x00);
		} catch (Exception e) {

		}
	}

	/**
	 * ���Ͱ���
	 */
	public S_ACTION_UI(String clanname, int rank) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0x19);
		writeC(0x02);
		writeC(0x0a);
		int length = 0;
		if (clanname != null)
			length = clanname.getBytes().length;
		if (length > 0) {
			writeC(length); // Ŭ���� SIZE
			writeByte(clanname.getBytes()); // Ŭ����
			writeC(0x10);
			writeC(rank); // Ŭ�� ��ũ
		} else {
			writeC(0x00);
		}
		writeH(0x00);
	}

	public S_ACTION_UI(int type, int value, boolean isPCRoom) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case ATTENDANCE_TIMEOVER:
			writeC(0x08);
			writeC(value);
			writeC(0x10);
			writeC(isPCRoom ? 0x01 : 0x00);
			writeC(0x18);
			writeC(2);
			break;
		case SPELL_PASSIVE_ONOFF_ACK:
			writeC(0x08);
			writeBit(1);
			writeC(0x10);
			writeBit(value);
			writeC(0x18);
			writeBit(isPCRoom ? 1 : 0);
			break;
		}
		writeH(0);
	}

	/**
	 * ���罺ų�� ����
	 */
	public S_ACTION_UI(int type, int skillnum) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case DOLL_READY:
			writeC(0x08);
			writeBit(skillnum);
			break;
		case Elixir:
			writeC(0x08);
			writeBit(skillnum);
			break;
		case stateProfile:
			writeC(0x0a); // ��
			if (skillnum == 45) {
				writeH(0x0808);
				writeC(skillnum);
				writeD(0x03180310);
				writeH(0x0120);
			} else {
				writeH(0x0806);
				writeC(skillnum);
				writeD(0x01180110);
			}

			writeC(0x12); // ��Ʈ
			if (skillnum == 45) {
				writeH(0x0808);
				writeC(skillnum);
				writeD(0x03180310);
				writeH(0x0120);
			} else {
				writeH(0x0806);
				writeC(skillnum);
				writeD(0x01180110);
			}

			writeC(0x1a);
			if (skillnum == 25) {
				writeH(0x0806);
				writeC(skillnum);
				writeD(0x01180110);
				writeH(0x3238);
			} else if (skillnum == 35) {
				writeH(0x0808);
				writeC(skillnum);
				writeD(0x01180110);
				writeH(0x6438);
			} else if (skillnum == 45) {
				writeH(0x0809);
				writeC(skillnum);
				writeD(0x03180310);
				writeC(0x38);
				writeH(0x0196);
			}

			writeC(0x22);
			if (skillnum == 45) {
				writeH(0x0808);
				writeC(skillnum);
				writeD(0x03180310);
				writeH(0x0120);
			} else {
				writeH(0x0806);
				writeC(skillnum);
				writeD(0x01180110);
			}

			writeC(0x2a);
			if (skillnum == 25) {
				writeH(0x0806);
				writeC(skillnum);
				writeD(0x32300110);
			} else if (skillnum == 35) {
				writeH(0x0808);
				writeC(skillnum);
				writeD(0x01180110);
				writeH(0x6430);
			} else if (skillnum == 45) {
				writeH(0x0809);
				writeC(skillnum);
				writeD(0x02180310);
				writeC(0x30);
				writeH(0x0196);
			}

			writeD(0xff080b32);
			writeD(0xffffffff);
			writeD(0xffffffff);
			writeC(0x01);
			break;
		case ATTENDANCE_ITEM:
			writeC(0x08);
			writeC(skillnum);

			AttendanceTable attend = AttendanceTable.getInstance();
			Map<Integer, ArrayList<AttendanceTable.AttendanceItem>> list = skillnum == 1 ? attend.attendancePcList() : attend.attendanceList();

			Set<Integer> keys = list.keySet();
			int index;
			ArrayList<AttendanceTable.AttendanceItem> items;
			for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
				index = iterator.next();
				items = list.get(index);

				byte[] attendance_item_info = sendAttendancePacketToRandom(items);
				int main_size = attendance_item_info.length + getBitSize(index) + 1;
				// System.out.println("���� : " + index + " / ���λ����� : " + main_size + " / �����ۻ����� :
				// " + items.size());

				writeC(0x12);
				writeBit(main_size);

				writeC(0x08);
				writeBit(index);

				writeByte(attendance_item_info);
			}
			break;
		case LOGIN_ADD_SKILL:
			writeC(0x0a);
			writeBit(skillnum == 5 ? 4L : 2L);
			writeC(0x08);
			writeBit(skillnum);
			if (skillnum != 5)
				break;
			writeC(0x10);
			writeC(0x0a);
			break;
		case ADD_SKILL:
			writeC(0x08);
			writeBit(skillnum);
			if (skillnum == 5) { // �ƸӰ���
				writeC(0x10);
				writeC(0x0a);
			}
			break;
		case TAM:
			writeC(0x08);
			write4bit(skillnum);
			break;
		case BMTYPE_DEL_CHECK_ACK:
		case CLAN_JOIN_MESSAGE:
		case PETBALL_CONTENTS_START_ACK:
			writeC(0x08);
			writeBit(skillnum);
			break;
		}
		writeH(0x00);
	}

	public S_ACTION_UI(int type, Account account, boolean isCheck) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case ATTENDANCE_COMPLETE:
			int normal_minute = 60 - account.getAttendanceHomeTime();
			int pc_minute = 60 - account.getAttendancePcHomeTime();
			int normal_size = getBitSize(normal_minute) + 7;
			int pc_size = getBitSize(pc_minute) + 7;
			writeC(0x0a);
			writeBit(normal_size);
			writeC(0x08);
			writeBit(0); // �Ϲ� 0 , �Ǿ��� 1
			writeC(0x10);
			writeBit(normal_minute); // �⼮ ���� �ð�
			writeC(0x18);
			writeBit(account.isAttendanceHome() ? 1 : 0); // �Ϸ��� �� Ƚ��
			writeC(0x20);
			writeBit(0x01); // �Ϸᰡ���� �� Ƚ��

			writeC(0x0a);
			writeBit(pc_size);
			writeC(0x08);
			writeBit(1); // �Ϲ� 0 , �Ǿ��� 1
			writeC(0x10);
			writeBit(isCheck ? pc_minute : 60); // �Ǿ��� �⼮ �����ð�
			writeC(0x18);
			writeBit(account.isAttendancePcHome() ? 1 : 0); // �Ϸ��� �� Ƚ��
			writeC(0x20);
			writeBit(0x01); // �Ϸᰡ���� �� Ƚ��

			for (int i = 0; i < account.getAttendanceHomeBytes().length; i++) {
				writeC(0x12);
				writeBit(0x08);
				writeC(0x08);
				writeBit(i + 1);
				writeC(0x10);
				writeBit(0);
				writeC(0x18);
				int state = account.getAttendanceHomeBytes()[i] == 2 ? 3 : account.getAttendanceHomeBytes()[i] == 1 ? 2 : 1;
				writeBit(state);
				writeC(0x20);
				int time = state == 3 || state == 2 ? 60 : 0;
				writeBit(time);

				if (state == 1)
					break;
			}

			for (int i = 0; i < account.getAttendancePcBytes().length; i++) {
				writeC(0x12);
				writeBit(0x08);
				writeC(0x08);
				writeBit(i + 1);
				writeC(0x10);
				writeBit(1);
				writeC(0x18);
				int state = account.getAttendancePcBytes()[i] == 2 ? 3 : account.getAttendancePcBytes()[i] == 1 ? 2 : 1;
				writeBit(state);
				writeC(0x20);
				int time = state == 3 || state == 2 ? 60 : 0;
				writeBit(time);

				if (state == 1)
					break;
			}
			break;
		}

		writeH(0);
	}

	public S_ACTION_UI(int type, int exp1, int exp2) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case CLAN_JOIN_SETTING:
			writeC(0x08);
			writeC(0x01);
			writeC(0x10);
			writeBit(exp1);
			writeC(0x18);
			writeBit(exp2);
			writeC(0x22);
			writeBit(20);
			writeD(0);
			writeD(0);
			writeD(0);
			writeD(0);
			writeD(0);
			break;
		case EMOTICON:
			writeC(0x08);
			writeBit(exp2);
			writeC(0x10);
			writeBit(0x02);
			writeC(0x18);
			writeBit(exp1);
			break;
		}
		writeH(0);
	}

	private byte[] sendAttendancePacket(L1Item item, L1ItemInstance dummy) {
		BinaryOutputStream os = new BinaryOutputStream();

		os.writeC(0x08);
		os.writeC(0x02); // type 1:��û�� , 2:����������

		os.writeC(0x10);
		os.writeBit(item.getItemDescId());

		os.writeC(0x18);
		os.writeBit(dummy.getCount());

		os.writeC(0x22);
		os.writeBit(item.getName().getBytes().length);
		os.writeByte(item.getName().getBytes());

		os.writeC(0x28);
		os.writeBit(0x00);

		os.writeC(0x30);
		os.writeBit(item.getGfxId()); //

		os.writeC(0x38);
		os.writeBit(item.getBless()); // ������ ����

		os.writeC(0x42);
		os.writeBit(item.getNameId().getBytes().length);
		os.writeByte(item.getNameId().getBytes());

		os.writeC(0x4a);
		byte[] status = dummy.getStatusBytes();
		os.writeBit(status.length);
		os.writeByte(status);

		os.writeC(0x50);
		os.writeBit(dummy.getItem().getType2() == 1 ? dummy.getAttrEnchantLevel() : -1); // �Ӽ���æ

		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return os.getBytes();
	}

	private byte[] sendAttendancePacketToRandom(ArrayList<AttendanceTable.AttendanceItem> list) {
		BinaryOutputStream os = new BinaryOutputStream();
		try {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				L1ItemInstance dummy = new L1ItemInstance();
				AttendanceItem _item = list.get(i);
				L1Item item = ItemTable.getInstance().getTemplate(_item._itemId);
				dummy.setItem(item);
				dummy.setCount(_item._count);

				byte[] stats = sendAttendancePacket(item, dummy);
				os.writeBit(0x12);
				os.writeBit(stats.length);
				os.writeByte(stats);
			}

			return os.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_ACTION_UI;
	}
}
