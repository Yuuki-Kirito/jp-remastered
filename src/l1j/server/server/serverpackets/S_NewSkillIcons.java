package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.skill.L1SkillId;

public class S_NewSkillIcons extends ServerBasePacket {

	private static final String S_NEWSKILLICONS = "[S] S_NewSkillIcon";

	public S_NewSkillIcons(int skillId, boolean on, long time) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(0x6E);
		writeC(0x08);
		if(skillId == L1SkillId.PHANTOM || skillId == L1SkillId.PHANTOM_R || skillId == L1SkillId.PHANTOM_D || skillId == L1SkillId.FLAME || skillId == L1SkillId.JUDGEMENT
				|| skillId == L1SkillId.FORCE_STUN || skillId == L1SkillId.DEMOLITION || skillId == L1SkillId.ETERNITY || skillId == L1SkillId.������ || skillId == L1SkillId.�������) {
			writeC(on ? 1 : 3);	
		} else {
			writeC(on ? 2 : 3);
		}
		writeC(0x10);
		if(skillId == L1SkillId.PHANTOM) {
			byteWrite(skillId + 4772);
		} else {
			byteWrite(skillId - 1);
		}
		if (on) {
			writeC(0x18);
			if (time < 0) {
				byte[] minus = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x01 };
				writeByte(minus);
			} else
				byteWrite(time);
			writeC(0x20);
			if (skillId == L1SkillId.�巡�����ູ1�ܰ� || skillId == L1SkillId.�巡�����ູ2�ܰ� || skillId == L1SkillId.�巡�����ູ3�ܰ�
					|| skillId == L1SkillId.PC��  || skillId == L1SkillId.FLAME
					|| skillId == L1SkillId.FORCE_STUN || skillId == L1SkillId.DEMOLITION || skillId == L1SkillId.ETERNITY || skillId == L1SkillId.�������){
				writeC(0x0a); // 10
			} else if (skillId == L1SkillId.PHANTOM) {
				writeC(0x00);
			} else {
				writeC(0x08); // 8
			}
			writeC(0x28);
			if (skillId == L1SkillId.WIND_WALK) {// �����̵� �������
				byteWrite(8490);// �κ� �̹���.
			} else if (skillId == L1SkillId.FIRE_SHIELD) {// ���̾��
				byteWrite(763);
			} else if (skillId == L1SkillId.�����̵�����) {// �����̵� �������
				byteWrite(8463);// �κ� �̹���.
			} else if (skillId == L1SkillId.��Ŀ�����̺�) {// ��ؽ�
				byteWrite(8480);
			} else if (skillId == L1SkillId.STATUS_Ŀ������) {// ����¡ ���Ǹ���
				byteWrite(2430);
			} else if (skillId == L1SkillId.STATUS_Ŀ������) {// �׷��̽� �ƹ�Ÿ
				byteWrite(2430);
			} else if (skillId == L1SkillId.STATUS_Ŀ�����) {// �ҿ� �踮��
				byteWrite(2430);
				/*} else if (skillId == L1SkillId.DESTROY) {// ��Ʈ����
				byteWrite(7451);*/
			} else if (skillId == L1SkillId.����Ʈ) {// ����Ʈ
				byteWrite(7456);
			} else if (skillId == L1SkillId.��Ŀ�����Ǹ���) {// ����Ʈ
				byteWrite(4832);
			} else if (skillId == L1SkillId.��ο����) {// �ҿ� �踮��
				byteWrite(8843);
			} else if (skillId == L1SkillId.�����) {// �ҿ� �踮��
				byteWrite(4503);
			} else if (skillId == L1SkillId.IMMUNE_TO_HARM) {// �ҿ� �踮��
				byteWrite(1562);
			} else if (skillId == L1SkillId.����¡) {// Ÿ��ź: ����¡
				byteWrite(7460);
			} else if (skillId == L1SkillId.�ҿ�踮��) {// �ҿ� �踮��
				byteWrite(7435);
			} else if (skillId == L1SkillId.IllUSION_OGRE) {// �Ϸ�� �����
				byteWrite(3117);
			} else if (skillId == L1SkillId.CUBE_IGNITION) {// ť������
				byteWrite(3104);
			} else if (skillId == L1SkillId.IllUSION_LICH) {// �Ϸ�� ��ġ
				byteWrite(3115);
			} else if (skillId == L1SkillId.CUBE_QUAKE) {// ť�� ��
				byteWrite(3106);
			} else if (skillId == L1SkillId.IllUSION_DIAMONDGOLEM) {// 
				byteWrite(3113);
			} else if (skillId == L1SkillId.CUBE_SHOCK) {// ť�긮ġ
				byteWrite(3109);
			} else if (skillId == L1SkillId.IllUSION_AVATAR) {// �Ϸ�� �ƹ�Ÿ
				byteWrite(3111);
			} else if (skillId == L1SkillId.CUBE_BALANCE) {// ť������
				byteWrite(5402);
			} else if (skillId == L1SkillId.�׷��̽��ƹ�Ÿ) {// �׷��̽� �ƹ�Ÿ
				byteWrite(7427);
			} else if (skillId == L1SkillId.�ۼַ�Ʈ���̵�) {// �ۼַ�Ʈ ���̵�
				byteWrite(7432);// �κ� �̹���.
			} else if (skillId == L1SkillId.������) {// ������
				byteWrite(7439);
			} else if (skillId == L1SkillId.cyclone) {// ����Ŭ��
				byteWrite(9190);
			} else if (skillId == L1SkillId.STRIKER_GALE) {// ����Ŭ��
				byteWrite(2357);
			} else if (skillId == L1SkillId.������Ƽ) {// 
				byteWrite(9518);
			} else if (skillId == L1SkillId.���̴׾Ƹ�) {// 
				byteWrite(9483);
			} else if (skillId == L1SkillId.����콺) {// 
				byteWrite(9443);
			} else if (skillId == L1SkillId.SCALES_EARTH_DRAGON) {// 
				byteWrite(3182);
			} else if (skillId == L1SkillId.SCALES_WATER_DRAGON) {// 
				byteWrite(3184);
			} else if (skillId == L1SkillId.SCALES_FIRE_DRAGON) {// 
				byteWrite(3078);
			} else if (skillId == L1SkillId.SCALES_Lind_DRAGON) {// 
				byteWrite(8887);
			} else if (skillId == L1SkillId.DISEASE) {// 
				byteWrite(9487);
			} else if (skillId == L1SkillId.Freeze_armor) {// 
				byteWrite(9490);
			} else if (skillId == L1SkillId.CLEAR_MIND) {// 
				byteWrite(745);
			} else if (skillId == L1SkillId.REDUCTION_ARMOR) {// 
				byteWrite(1889);
			} else if (skillId == L1SkillId.SOLID_CARRIAGE) {// 
				byteWrite(2351);
			} else if (skillId == L1SkillId.DRESS_EVASION) {// 
				byteWrite(1608);
			} else if (skillId == L1SkillId.SHADOW_FANG) {// 
				byteWrite(1128);
			} else if (skillId == L1SkillId.ADVANCE_SPIRIT) {// 
				byteWrite(1607);
			} else if (skillId == L1SkillId.�Ⱓƽ) {// 
				byteWrite(6168);
			} else if (skillId == L1SkillId.�����̵�) {// 
				byteWrite(8854);
			} else if (skillId == L1SkillId.BLESSED_ARMOR) {// 
				byteWrite(5316);
			} else if (skillId == L1SkillId.HOLY_WEAPON) {// 
				byteWrite(777);
			} else if (skillId == L1SkillId.ENCHANT_WEAPON) {// 
				byteWrite(5265);
			} else if (skillId == L1SkillId.AQUA_PROTECTER) {// 
				byteWrite(2342);
			} else if (skillId == L1SkillId.�巡�����ູ1�ܰ�) {
				byteWrite(9596);
			} else if (skillId == L1SkillId.�巡�����ູ2�ܰ�) {
				byteWrite(9597);
			} else if (skillId == L1SkillId.�巡�����ູ3�ܰ�) {
				byteWrite(9598);
			} else if (skillId == L1SkillId.KYULJUN_CASHSCROLL1) {
				byteWrite(9654);
			} else if (skillId == L1SkillId.PC��) {
				byteWrite(9653);
			} else if (skillId == L1SkillId.FORCE_STUN) {
				byteWrite(9609);
			} else if (skillId == L1SkillId.HALPHAS) {
				byteWrite(9612);
			} else if (skillId == L1SkillId.POTENTIAL) {
				byteWrite(9610);
			} else if (skillId == L1SkillId.PRIME) {
				byteWrite(9611);
			} else if (skillId == L1SkillId.PHANTOM) {
				byteWrite(9700);
			} else if (skillId == L1SkillId.ETERNITY) {
				byteWrite(9608);
			} else if (skillId == L1SkillId.DEMOLITION) {
				byteWrite(9606);
			} else if (skillId == L1SkillId.PHANTOM_R) {
				byteWrite(9702);
			} else if (skillId == L1SkillId.PHANTOM_D) {
				byteWrite(9701);
			} else if (skillId == L1SkillId.FLAME) {
				byteWrite(9703);
			} else if (skillId == L1SkillId.JUDGEMENT) {
				byteWrite(9711);
			} else if (skillId == L1SkillId.SHINING_AURA) {
				byteWrite(1567);
			} else if (skillId == L1SkillId.CONFUSION) {
				byteWrite(3211);
			} else if (skillId == L1SkillId.�������) {
				byteWrite(6499);
			} else if (skillId == L1SkillId.BLESS_WEAPON) {
				byteWrite(728);
			} else if (skillId == L1SkillId.BRAVE_AURA) {
				byteWrite(890);
			} else if (skillId == L1SkillId.BURNING_SPIRIT) {
				byteWrite(1113);
			} else if (skillId == L1SkillId.ELEMENTAL_FIRE) {
				byteWrite(1736);
			} else if (skillId == L1SkillId.ENTANGLE) {
				byteWrite(8493);
			}
		} 
		writeH(0x0030);
		if (on) {
			writeC(0x38);
			if (skillId == L1SkillId.PHANTOM || skillId == L1SkillId.PHANTOM_R || skillId == L1SkillId.PHANTOM_D || skillId == L1SkillId.FLAME || skillId == L1SkillId.JUDGEMENT
				|| skillId == L1SkillId.FORCE_STUN || skillId == L1SkillId.DEMOLITION || skillId == L1SkillId.ETERNITY || skillId == L1SkillId.������ || skillId == L1SkillId.�������) {
				writeC(0x07);
			} else {
				writeC(0x03);
			}
			writeC(0x40);
			int msgNum = 0;
			if (skillId == L1SkillId.WIND_WALK)// �̱۾�
				msgNum = 5157;// �޼���.
			else if (skillId == L1SkillId.�����̵�����)// �ۼַ�Ʈ �踮��
				msgNum = 5119;// �޼���.
			else if (skillId == L1SkillId.FIRE_SHIELD)// 
				msgNum = 5165;
			else if (skillId == L1SkillId.��Ŀ�����̺�)// ��ؽ�
				msgNum = 5155;
			else if (skillId == L1SkillId.STATUS_Ŀ������)// ����¡ ���Ǹ���
				msgNum = 5180;
			else if (skillId == L1SkillId.STATUS_Ŀ������)// �׷��̽� �ƹ�Ÿ
				msgNum = 5182;
			else if (skillId == L1SkillId.STATUS_Ŀ�����)// �ҿ� �踮��
				msgNum = 5181;
			/*else if (skillId == L1SkillId.DESTROY)// ��Ʈ����
				msgNum = 4739;*/
			else if (skillId == L1SkillId.����Ʈ)// ����Ʈ
				msgNum = 4761;
			else if (skillId == L1SkillId.��ο����)// ����Ʈ
				msgNum = 5266;
			else if (skillId == L1SkillId.��Ŀ�����Ǹ���)// ����Ʈ
				msgNum = 5272;
			else if (skillId == L1SkillId.�����)// ����Ʈ
				msgNum = 5268;
			else if (skillId == L1SkillId.IMMUNE_TO_HARM)// ����Ʈ
				msgNum = 314;
			else if(skillId == L1SkillId.����¡)
				msgNum = 4740;
			else if (skillId == L1SkillId.�ҿ�踮��)// �ҿ� �踮��
				msgNum = 4736;
			else if (skillId == L1SkillId.IllUSION_OGRE)
				msgNum = 1340;
			else if (skillId == L1SkillId.CUBE_IGNITION)
				msgNum = 3074;
			else if (skillId == L1SkillId.IllUSION_LICH)
				msgNum = 1343;
			else if (skillId == L1SkillId.CUBE_QUAKE)
				msgNum = 3075;
			else if (skillId == L1SkillId.IllUSION_DIAMONDGOLEM)
				msgNum = 1347;
			else if (skillId == L1SkillId.CUBE_SHOCK) //ť�긮ġ
				msgNum = 1348;
			else if (skillId == L1SkillId.IllUSION_AVATAR) //�Ϸ�Ǿƹ�Ÿ
				msgNum = 1351;
			else if (skillId == L1SkillId.CUBE_BALANCE) //ť��ƹ�Ÿ
				msgNum = 3073;
			else if (skillId == L1SkillId.�׷��̽��ƹ�Ÿ)// �׷��̽� �ƹ�Ÿ
				msgNum = 4734;
			else if (skillId == L1SkillId.�ۼַ�Ʈ���̵�)// �ۼַ�Ʈ ���̵�
				msgNum = 4735;// �޼���.
			else if (skillId == L1SkillId.������)// ������
				msgNum = 4737;
			else if (skillId == L1SkillId.cyclone)// ����Ŭ��
				msgNum = 5446;
			else if (skillId == L1SkillId.STRIKER_GALE)// ����Ŭ��
				msgNum = 1084;
			else if (skillId == L1SkillId.���̴׾Ƹ�)// 
				msgNum = 5892;
			else if (skillId == L1SkillId.������Ƽ)// 
				msgNum = 5893;
			else if (skillId == L1SkillId.����콺)// 
				msgNum = 5550;
			else if (skillId == L1SkillId.SCALES_EARTH_DRAGON)// 
				msgNum = 2267;
			else if (skillId == L1SkillId.SCALES_WATER_DRAGON)// 
				msgNum = 2271;
			else if (skillId == L1SkillId.SCALES_FIRE_DRAGON)// 
				msgNum = 2274;
			else if (skillId == L1SkillId.SCALES_Lind_DRAGON)// 
				msgNum = 5270;
			else if (skillId == L1SkillId.DISEASE)// 
				msgNum = 5888;
			else if (skillId == L1SkillId.Freeze_armor)// 
				msgNum = 5889;
			else if (skillId == L1SkillId.CLEAR_MIND)// 
				msgNum = 861;
			else if (skillId == L1SkillId.REDUCTION_ARMOR)// 
				msgNum = 1043;
			else if (skillId == L1SkillId.SOLID_CARRIAGE)// 
				msgNum = 1087;
			else if (skillId == L1SkillId.DRESS_EVASION)// 
				msgNum = 970;
			else if (skillId == L1SkillId.SHADOW_FANG)// 
				msgNum = 1339;
			else if (skillId == L1SkillId.ADVANCE_SPIRIT)// 
				msgNum = 982;
			else if (skillId == L1SkillId.�Ⱓƽ)// 
				msgNum = 3918;
			else if (skillId == L1SkillId.�����̵�)// 
				msgNum = 5264;
			else if (skillId == L1SkillId.BLESSED_ARMOR)// 
				msgNum = 3060;
			else if (skillId == L1SkillId.HOLY_WEAPON)// 
				msgNum = 685;
			else if (skillId == L1SkillId.ENCHANT_WEAPON)// 
				msgNum = 3064;
			else if (skillId == L1SkillId.AQUA_PROTECTER)// 
				msgNum = 1085;
			else if (skillId == L1SkillId.�巡�����ູ1�ܰ�)
				msgNum = 6963;
			else if (skillId == L1SkillId.�巡�����ູ2�ܰ�)
				msgNum = 6964;
			else if (skillId == L1SkillId.�巡�����ູ3�ܰ�)
				msgNum = 6965;
			else if (skillId == L1SkillId.KYULJUN_CASHSCROLL1)
				msgNum = 7021;
			else if (skillId == L1SkillId.PC��)
				msgNum = 2156;
			else if (skillId == L1SkillId.FORCE_STUN)
				msgNum = 7007;
			else if (skillId == L1SkillId.HALPHAS)
				msgNum = 7009;
			else if (skillId == L1SkillId.POTENTIAL)
				msgNum = 7010;
			else if (skillId == L1SkillId.PRIME)
				msgNum = 7006;
			else if (skillId == L1SkillId.PHANTOM)
				msgNum = 7063;
			else if (skillId == L1SkillId.ETERNITY) 
				msgNum = 7008;
			else if (skillId == L1SkillId.DEMOLITION)
				msgNum = 7011;
			else if (skillId == L1SkillId.PHANTOM_R) 
				msgNum = 7064;
			else if (skillId == L1SkillId.PHANTOM_D) 
				msgNum = 7065;
			else if (skillId == L1SkillId.FLAME) 
				msgNum = 7062;
			else if (skillId == L1SkillId.JUDGEMENT) 
				msgNum = 7066;
			else if (skillId == L1SkillId.SHINING_AURA) 
				msgNum = 865;
			else if (skillId == L1SkillId.CONFUSION) 
				msgNum = 285;
			else if (skillId == L1SkillId.�������) 
				msgNum = 4119;
			else if (skillId == L1SkillId.BLESS_WEAPON) 
				msgNum = 693;
			else if (skillId == L1SkillId.BRAVE_AURA) 
				msgNum = 866;
			else if (skillId == L1SkillId.BURNING_SPIRIT)
				msgNum = 818;
			else if (skillId == L1SkillId.ELEMENTAL_FIRE)
				msgNum = 1014;
			else if (skillId == L1SkillId.ENTANGLE)
				msgNum = 5151;
			byteWrite(msgNum);
			writeC(0x48);
			writeC(0x00);
		}
		writeH(0x0050);
		if (on) {
			writeC(0x58);
			if (skillId == L1SkillId.PHANTOM || skillId == L1SkillId.PHANTOM_R || skillId == L1SkillId.PHANTOM_D || skillId == L1SkillId.FLAME || skillId == L1SkillId.JUDGEMENT 
					|| skillId == L1SkillId.FORCE_STUN || skillId == L1SkillId.DEMOLITION || skillId == L1SkillId.ETERNITY || skillId == L1SkillId.������ || skillId == L1SkillId.�������) {
				writeC(0x00);
			} else {
				writeC(0x01);
			}
			writeC(0x60);
			writeC(0x00);
			writeC(0x68);
			writeC(0x00);
			writeC(0x70);
			writeC(0x00);
		}
		writeH(0x00);
	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c, 0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e, 0x9f, 0xa0, 0xa1,
			0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0, 0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8,
			0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef,
			0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };

	private void byteWrite(long value) {
		long temp = value / 128;
		if (temp > 0) {
			writeC(hextable[(int) value % 128]);
			while (temp >= 128) {
				writeC(hextable[(int) temp % 128]);
				temp = temp / 128;
			}
			if (temp > 0)
				writeC((int) temp);
		} else {
			if (value == 0) {
				writeC(0);
			} else {
				writeC(hextable[(int) value]);
				writeC(0);
			}
		}
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_NEWSKILLICONS;
	}
}