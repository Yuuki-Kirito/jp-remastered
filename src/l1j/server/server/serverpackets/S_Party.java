package l1j.server.server.serverpackets;
import java.io.IOException;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.utils.BinaryOutputStream;

public class S_Party extends ServerBasePacket {

	private static final String _S_Party = "[S] S_Party";

	private byte[] _byte = null;
	private static final int[] BuffIconSkill = new int[] {8,26,42,48,55,68,133,29,40,43,47,64,66,71,73,87,161,
			173,174,183,188,192,212,228,230,153,157,193,217,208,235,236,242,243,247,559};

	public static final int PartyMember = 0x337;
	/** ��Ƽ �ʱ� ���� */
	public static final int PartyMemberUse = 0x338;
	/** ������������ */
	public static final int PartyMemberHp = 0x339;
	/** ���� hp ¡ǥ���� */
	public static final int PartyMemberMap = 0x33b;

	/** ���� �� ���� */

	public S_Party(int type, L1PcInstance pc) {
		switch (type) {
		case 0x68:
			Member(pc, PartyMember);
			break;
		case 0x69:
			Member(pc, PartyMemberUse);
			break;
		case 0x6e:
			Member(pc, PartyMemberHp);
			break;
		case 0x6f:
			Member(pc, PartyMemberMap);
			break;
		case 0x6a:
			ChangeLeader(pc);
			break;
		case 0x6b:
			RemoveMember(pc);
			break;
		case 0x6c1:
			NameColor(pc, 1);
			break;
		case 0x6c2:
			NameColor(pc, 2);
			break;

		default:
			break;
		}
	}

	public static final int REFRESH = 0x033b; // 59

	public S_Party(int type, L1PcInstance[] memberArray) {
		if (memberArray != null && memberArray.length > 0) {
			writeC(Opcodes.S_EXTENDED_PROTOBUF);
			writeH(type);

			for (L1PcInstance member : memberArray) {
				writeC(0x0a);
				byte[] _partyLocByteInfo = partyLocByteInfo(member);
				writeC(_partyLocByteInfo.length);
				writeByte(_partyLocByteInfo);
			}
			writeH(0x00);
		}
	}

	public S_Party(String htmlid, int objid) {
		buildPacket(htmlid, objid, "", "", 0);
	}

	public S_Party(String htmlid, int objid, String partyname, String partymembers) {
		buildPacket(htmlid, objid, partyname, partymembers, 1);
	}

	private void buildPacket(String htmlid, int objid, String partyname, String partymembers, int type) {
		writeC(Opcodes.S_HYPERTEXT);
		writeD(objid);
		writeS(htmlid);
		writeH(type);
		writeH(0x02);
		writeS(partyname);
		writeS(partymembers);
	}

	/** ��Ƽ�� ������ ��Ƽ�� �۾� */
	private void Member(L1PcInstance pc, int PartyType) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(PartyType);
		/** ���� ���� */
		String Name = null;
		int Hp = 0, Mp = 0;

		L1PcInstance leader = pc.getParty().getLeader();

		switch (PartyType) {
		/** ��Ƽâ���� �ű԰� �����ϸ� �Ƹ� �̰� �ٽ� ������ ������ŭ�ؼ� */
		case PartyMember:
			/** ���� �ɸ� �̸� */
			writeC(0x0a);
			Name = leader.getName();
			if (Name.length() > 0) {
				writeC(Name.getBytes().length);
				writeByte(Name.getBytes());
			}

			for (L1PcInstance member : pc.getParty().getMembers()) { // ��� ������
				writeC(0x12);
				byte[] _PartyMember = memberByteInfo2(member);
				writeC(_PartyMember.length);
				writeByte(_PartyMember);
			}
			break;

			/** ���� ���� ������Ʈ */
		case PartyMemberUse:
			/** ��ü ������ */
			writeC(0x22);
			byte[] _memberByteInfo = memberByteInfo(pc);
			writeC(_memberByteInfo.length);
			writeByte(_memberByteInfo);
			break;

			/** ��Ƽ�� �ǿ��� ���� ������Ʈ */
		case PartyMemberHp:
			writeC(0x0a);
			Name = pc.getName();
			if (Name.length() > 0) {
				writeC(Name.getBytes().length);
				writeByte(Name.getBytes());
			}

			/** ���� HP �ۼ������� */
			writeC(0x18);
			Hp = (int) Math.round(((double) pc.getCurrentHp() / pc.getMaxHp() * 100));
			writeC(Hp);

			/** ���� MP �ۼ������� */
			writeC(0x20);
			Mp = (int) Math.round(((double) pc.getCurrentMp() / pc.getMaxMp() * 100));
			writeC(Mp);
			for (int skillId : BuffIconSkill) {
				int timeSec = pc.getSkillEffectTimerSet().getSkillEffectTimeSec(skillId);
				if(pc.getSkillEffectTimerSet().hasSkillEffect(skillId)) {
					writeC(0x4a);
					writeC(0x0a);
					writeC(0x08);
					switch (skillId) {	
					case L1SkillId.HOLY_WEAPON: 
						writeBit(777);
						break;
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
						writeBit(482);
						break;
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
						writeBit(483);
						break;
					case L1SkillId.BLESS_WEAPON: 
						writeBit(728);
						break;
					case L1SkillId.BERSERKERS: 
						writeBit(1556);
						break;
					case L1SkillId.IMMUNE_TO_HARM: 
						writeBit(1562);
						break;
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
						writeBit(1740);
						break;					
					case L1SkillId.SLOW:  
						writeBit(480);
						break;
					case L1SkillId.DARKNESS:  
						writeBit(751);
						break;
					case L1SkillId.HASTE:  
						writeBit(5397);
						break;
					case L1SkillId.WEAKNESS:  
						writeBit(852);
						break;
					case L1SkillId.SILENCE:  
						writeBit(824);
						break;
					case L1SkillId.FOG_OF_SLEEPING:  
						writeBit(1560);
						break;
					case L1SkillId.DECAY_POTION:  
						writeBit(754);
						break;
					case L1SkillId.������:  
						writeBit(7439);
						break;
					case L1SkillId.SHOCK_STUN:  
						writeBit(1626);
						break;
					case L1SkillId.�����̾�:  
						writeBit(1626);
						break;
					case L1SkillId.AREA_OF_SILENCE:  
						writeBit(722);
						break;
					case L1SkillId.POLLUTE_WATER:  
						writeBit(2348);
						break;
					case L1SkillId.STRIKER_GALE:  
						writeBit(2357);
						break;
					case L1SkillId.THUNDER_GRAB:  
						writeBit(1620);
						break;
					case L1SkillId.PHANTASM:  
						writeBit(3129);
						break;
					case L1SkillId.�Ŀ��׸�:  
						writeBit(6176);
						break;
					case L1SkillId.�������:  
						writeBit(6499);
						break;																								
					case L1SkillId.ERASE_MAGIC:  
						writeBit(1834);
						break;
					case L1SkillId.EARTH_BIND:  
						writeBit(893);
						break;
					case L1SkillId.PANIC:  
						writeBit(3125);
						break;
					case L1SkillId.BONE_BREAK:  
						writeBit(1626);
						break;
					case L1SkillId.PHANTOM:
						if(pc.isPhantomDeathed()) {
							writeBit(9701);
						} else if (pc.isPhantomRippered()) {
							writeBit(9702);
						} else {
							writeBit(9700);
						}
						break;
					case L1SkillId.JUDGEMENT:  
						writeBit(9711);
						break;
					case L1SkillId.FORCE_STUN:  
						writeBit(9609);
						break;
					case L1SkillId.ETERNITY:  
						writeBit(9608);
						break;
					case L1SkillId.DEMOLITION:  
						writeBit(9606);
						break;
					case L1SkillId.FLAME:  
						writeBit(9703);
						break;
					}
					/** ��ų������ �ѹ� */
					writeC(0x10);
					switch (skillId) {	
					case L1SkillId.HOLY_WEAPON:
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
					case L1SkillId.BLESS_WEAPON: 
					case L1SkillId.BERSERKERS: 
					case L1SkillId.HASTE:  
					case L1SkillId.IMMUNE_TO_HARM: 
						writeC(0x01);
						break;
					case L1SkillId.FLAME:
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
					case L1SkillId.SLOW:  
					case L1SkillId.DARKNESS:  
					case L1SkillId.WEAKNESS:  
					case L1SkillId.SILENCE:  
					case L1SkillId.FOG_OF_SLEEPING:  
					case L1SkillId.DECAY_POTION:  
					case L1SkillId.������:  
					case L1SkillId.SHOCK_STUN:  
					case L1SkillId.�����̾�:  
					case L1SkillId.AREA_OF_SILENCE:  
					case L1SkillId.POLLUTE_WATER:  
					case L1SkillId.STRIKER_GALE:  
					case L1SkillId.THUNDER_GRAB:  
					case L1SkillId.PHANTASM:  
					case L1SkillId.�Ŀ��׸�:  
					case L1SkillId.�������:  
					case L1SkillId.ERASE_MAGIC:  
					case L1SkillId.EARTH_BIND:  
					case L1SkillId.PANIC:  
					case L1SkillId.BONE_BREAK:  
					case L1SkillId.PHANTOM:  
					case L1SkillId.JUDGEMENT:  
					case L1SkillId.FORCE_STUN:  
					case L1SkillId.ETERNITY:  
					case L1SkillId.DEMOLITION:  
						writeC(0x00);
						break;
					}
					writeC(0x18);
					/** ��ų���̵� -1�� */
					writeBit(skillId - 1);
					/** ��ų���̵� -1�� */
					writeC(0x20);
					switch (skillId) {
					case L1SkillId.FLAME:
					case L1SkillId.HOLY_WEAPON:
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
					case L1SkillId.BLESS_WEAPON: 
					case L1SkillId.BERSERKERS: 
					case L1SkillId.HASTE:  
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
					case L1SkillId.SLOW:  
					case L1SkillId.AREA_OF_SILENCE:  
					case L1SkillId.EARTH_BIND:  
					case L1SkillId.PANIC:  
					case L1SkillId.BONE_BREAK:  
					case L1SkillId.PHANTOM:  
					case L1SkillId.JUDGEMENT:  
					case L1SkillId.FORCE_STUN:  
					case L1SkillId.ETERNITY:  
					case L1SkillId.DEMOLITION:  
					case L1SkillId.STRIKER_GALE:  
					case L1SkillId.ERASE_MAGIC:  
						writeBit(timeSec);
						break;
					case L1SkillId.WEAKNESS:  
					case L1SkillId.IMMUNE_TO_HARM: 
					case L1SkillId.������:  
					case L1SkillId.POLLUTE_WATER:  
					case L1SkillId.THUNDER_GRAB:  
					case L1SkillId.PHANTASM:  
					case L1SkillId.�Ŀ��׸�:  
					case L1SkillId.�������:  
						writeBit(timeSec * 10);
						break;
					case L1SkillId.DARKNESS:  
					case L1SkillId.SILENCE:  
					case L1SkillId.FOG_OF_SLEEPING:  
					case L1SkillId.DECAY_POTION:  
					case L1SkillId.SHOCK_STUN:  
					case L1SkillId.�����̾�:  
						writeBit(timeSec * 300);
						break;
					}
				}
			}
			//writeC(0x40);
			//writeC(pc.getǥ��());

			/** ¡ǥ ��Ŷ */
			if (pc.getǥ��() != 0) {
				writeC(0x40);
				writeC(pc.getǥ��());
			}
			break;
		}
		writeH(0x00);
	}

	private byte[] memberByteInfo2(L1PcInstance member) {
		BinaryOutputStream os = new BinaryOutputStream();
		try {
			os.writeC(0x0a);
			os.writeS2(member.getName());

			os.writeC(0x10);
			os.writeC(0x00);

			os.writeC(0x18);
			os.writeBit(member.getId());

			os.writeC(0x20);
			os.writeC(member.getType());

			os.writeC(0x28);
			os.writeC(member.get_sex());

			os.writeC(0x30);
			os.writeBit(100 * member.getCurrentHp() / member.getMaxHp());

			os.writeC(0x38);
			os.writeBit(100 * member.getCurrentMp() / member.getMaxMp());

			os.writeC(0x40);
			os.writeBit(member.getMapId());

			// 48 b2 84 ba f6 07
			os.writeC(0x48);
			os.writeBit(member.getX(), member.getY());

			os.writeC(0x50);
			os.writeC(member.getǥ��());

			os.writeC(0x60);
			os.writeC(0x64);
			for (int skillId : BuffIconSkill) {
				int timeSec = member.getSkillEffectTimerSet().getSkillEffectTimeSec(skillId);
				if(member.getSkillEffectTimerSet().hasSkillEffect(skillId)) {
					os.writeC(0x6a);
					os.writeC(0x0a);
					os.writeC(0x08);
					switch (skillId) {	
					case L1SkillId.HOLY_WEAPON: 
						os.writeBit(777);
						break;
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
						os.writeBit(482);
						break;
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
						os.writeBit(483);
						break;
					case L1SkillId.BLESS_WEAPON: 
						os.writeBit(728);
						break;
					case L1SkillId.BERSERKERS: 
						os.writeBit(1556);
						break;
					case L1SkillId.IMMUNE_TO_HARM: 
						os.writeBit(1562);
						break;
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
						os.writeBit(1740);
						break;					
					case L1SkillId.SLOW:  
						os.writeBit(480);
						break;
					case L1SkillId.DARKNESS:  
						os.writeBit(751);
						break;
					case L1SkillId.HASTE:  
						os.writeBit(5397);
						break;
					case L1SkillId.WEAKNESS:  
						os.writeBit(852);
						break;
					case L1SkillId.SILENCE:  
						os.writeBit(824);
						break;
					case L1SkillId.FOG_OF_SLEEPING:  
						os.writeBit(1560);
						break;
					case L1SkillId.DECAY_POTION:  
						os.writeBit(754);
						break;
					case L1SkillId.������:  
						os.writeBit(7439);
						break;
					case L1SkillId.SHOCK_STUN:  
						os.writeBit(1626);
						break;
					case L1SkillId.�����̾�:  
						os.writeBit(1626);
						break;
					case L1SkillId.AREA_OF_SILENCE:  
						os.writeBit(722);
						break;
					case L1SkillId.POLLUTE_WATER:  
						os.writeBit(2348);
						break;
					case L1SkillId.STRIKER_GALE:  
						os.writeBit(2357);
						break;
					case L1SkillId.THUNDER_GRAB:  
						os.writeBit(1620);
						break;
					case L1SkillId.PHANTASM:  
						os.writeBit(3129);
						break;
					case L1SkillId.�Ŀ��׸�:  
						os.writeBit(6176);
						break;
					case L1SkillId.�������:  
						os.writeBit(6499);
						break;																								
					case L1SkillId.ERASE_MAGIC:  
						os.writeBit(1834);
						break;
					case L1SkillId.EARTH_BIND:  
						os.writeBit(893);
						break;
					case L1SkillId.PANIC:  
						os.writeBit(3125);
						break;
					case L1SkillId.BONE_BREAK:  
						os.writeBit(1626);
						break;
					case L1SkillId.PHANTOM:  
						if(member.isPhantomDeathed()) {
							os.writeBit(9701);
						} else if (member.isPhantomRippered()) {
							os.writeBit(9702);
						} else {
							os.writeBit(9700);
						}
						break;
					case L1SkillId.JUDGEMENT:  
						os.writeBit(9711);
						break;
					case L1SkillId.FORCE_STUN:  
						os.writeBit(9609);
						break;
					case L1SkillId.ETERNITY:  
						os.writeBit(9608);
						break;
					case L1SkillId.DEMOLITION:  
						os.writeBit(9606);
						break;
					case L1SkillId.FLAME:  
						os.writeBit(9703);
						break;
					}
					/** ��ų������ �ѹ� */
					os.writeC(0x10);
					switch (skillId) {	
					case L1SkillId.HOLY_WEAPON:
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
					case L1SkillId.BLESS_WEAPON: 
					case L1SkillId.BERSERKERS: 
					case L1SkillId.HASTE:  
					case L1SkillId.IMMUNE_TO_HARM: 
						os.writeC(0x01);
						break;
					case L1SkillId.FLAME:
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
					case L1SkillId.SLOW:  
					case L1SkillId.DARKNESS:  
					case L1SkillId.WEAKNESS:  
					case L1SkillId.SILENCE:  
					case L1SkillId.FOG_OF_SLEEPING:  
					case L1SkillId.DECAY_POTION:  
					case L1SkillId.������:  
					case L1SkillId.SHOCK_STUN:  
					case L1SkillId.�����̾�:  
					case L1SkillId.AREA_OF_SILENCE:  
					case L1SkillId.POLLUTE_WATER:  
					case L1SkillId.STRIKER_GALE:  
					case L1SkillId.THUNDER_GRAB:  
					case L1SkillId.PHANTASM:  
					case L1SkillId.�Ŀ��׸�:  
					case L1SkillId.�������:  
					case L1SkillId.ERASE_MAGIC:  
					case L1SkillId.EARTH_BIND:  
					case L1SkillId.PANIC:  
					case L1SkillId.BONE_BREAK:  
					case L1SkillId.PHANTOM:  
					case L1SkillId.JUDGEMENT:  
					case L1SkillId.FORCE_STUN:  
					case L1SkillId.ETERNITY:  
					case L1SkillId.DEMOLITION:  
						os.writeC(0x00);
						break;
					}
					os.writeC(0x18);
					/** ��ų���̵� -1�� */
					os.writeBit(skillId - 1);
					/** ��ų���̵� -1�� */
					os.writeC(0x20);
					switch (skillId) {	
					case L1SkillId.FLAME:
					case L1SkillId.HOLY_WEAPON:
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
					case L1SkillId.BLESS_WEAPON: 
					case L1SkillId.BERSERKERS: 
					case L1SkillId.HASTE:  
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
					case L1SkillId.SLOW:  
					case L1SkillId.AREA_OF_SILENCE:  
					case L1SkillId.EARTH_BIND:  
					case L1SkillId.PANIC:  
					case L1SkillId.BONE_BREAK:  
					case L1SkillId.PHANTOM:  
					case L1SkillId.JUDGEMENT:  
					case L1SkillId.FORCE_STUN:  
					case L1SkillId.ETERNITY:  
					case L1SkillId.DEMOLITION:  
					case L1SkillId.STRIKER_GALE:  
					case L1SkillId.ERASE_MAGIC:  
						os.writeBit(timeSec);
						break;
					case L1SkillId.WEAKNESS:  
					case L1SkillId.IMMUNE_TO_HARM: 
					case L1SkillId.������:  
					case L1SkillId.POLLUTE_WATER:  
					case L1SkillId.THUNDER_GRAB:  
					case L1SkillId.PHANTASM:  
					case L1SkillId.�Ŀ��׸�:  
					case L1SkillId.�������:  
						os.writeBit(timeSec * 10);
						break;
					case L1SkillId.DARKNESS:  
					case L1SkillId.SILENCE:  
					case L1SkillId.FOG_OF_SLEEPING:  
					case L1SkillId.DECAY_POTION:  
					case L1SkillId.SHOCK_STUN:   
					case L1SkillId.�����̾�:  
						os.writeBit(timeSec * 300);
						break;
					}
				}
			}
			return os.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}


	private byte[] memberByteInfo(L1PcInstance member) {
		BinaryOutputStream os = new BinaryOutputStream();
		try {
			os.writeC(0x0a);
			os.writeS2(member.getName());

			os.writeC(0x10);
			os.writeC(0x00);

			os.writeC(0x18);
			os.writeBit(member.getId());

			os.writeC(0x20);
			os.writeC(member.getType());

			os.writeC(0x28);
			os.writeC(member.get_sex());

			os.writeC(0x30);
			os.writeBit(100 * member.getCurrentHp() / member.getMaxHp());

			os.writeC(0x38);
			os.writeBit(100 * member.getCurrentMp() / member.getMaxMp());

			os.writeC(0x40);
			os.writeBit(member.getMapId());

			// 48 b2 84 ba f6 07
			os.writeC(0x48);
			os.writeBit(member.getX(), member.getY());

			os.writeC(0x50);
			os.writeC(member.getǥ��());

			os.writeC(0x60);
			os.writeC(0x64);
			for (int skillId : BuffIconSkill) {
				int timeSec = member.getSkillEffectTimerSet().getSkillEffectTimeSec(skillId);
				if(member.getSkillEffectTimerSet().hasSkillEffect(skillId)) {
					os.writeC(0x6a);
					os.writeC(0x0a);
					os.writeC(0x08);
					switch (skillId) {	
					case L1SkillId.HOLY_WEAPON: 
						os.writeBit(777);
						break;
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
						os.writeBit(482);
						break;
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
						os.writeBit(483);
						break;
					case L1SkillId.BLESS_WEAPON: 
						os.writeBit(728);
						break;
					case L1SkillId.BERSERKERS: 
						os.writeBit(1556);
						break;
					case L1SkillId.IMMUNE_TO_HARM: 
						os.writeBit(1562);
						break;
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
						os.writeBit(1740);
						break;					
					case L1SkillId.SLOW:  
						os.writeBit(480);
						break;
					case L1SkillId.DARKNESS:  
						os.writeBit(751);
						break;
					case L1SkillId.HASTE:  
						os.writeBit(5397);
						break;
					case L1SkillId.WEAKNESS:  
						os.writeBit(852);
						break;
					case L1SkillId.SILENCE:  
						os.writeBit(824);
						break;
					case L1SkillId.FOG_OF_SLEEPING:  
						os.writeBit(1560);
						break;
					case L1SkillId.DECAY_POTION:  
						os.writeBit(754);
						break;
					case L1SkillId.������:  
						os.writeBit(7439);
						break;
					case L1SkillId.SHOCK_STUN:  
						os.writeBit(1626);
						break;
					case L1SkillId.�����̾�:  
						os.writeBit(1626);
						break;
					case L1SkillId.AREA_OF_SILENCE:  
						os.writeBit(722);
						break;
					case L1SkillId.POLLUTE_WATER:  
						os.writeBit(2348);
						break;
					case L1SkillId.STRIKER_GALE:  
						os.writeBit(2357);
						break;
					case L1SkillId.THUNDER_GRAB:  
						os.writeBit(1620);
						break;
					case L1SkillId.PHANTASM:  
						os.writeBit(3129);
						break;
					case L1SkillId.�Ŀ��׸�:  
						os.writeBit(6176);
						break;
					case L1SkillId.�������:  
						os.writeBit(6499);
						break;																								
					case L1SkillId.ERASE_MAGIC:  
						os.writeBit(1834);
						break;
					case L1SkillId.EARTH_BIND:  
						os.writeBit(893);
						break;
					case L1SkillId.PANIC:  
						os.writeBit(3125);
						break;
					case L1SkillId.BONE_BREAK:  
						os.writeBit(1626);
						break;
					case L1SkillId.PHANTOM:  
						if(member.isPhantomDeathed()) {
							os.writeBit(9701);
						} else if (member.isPhantomRippered()) {
							os.writeBit(9702);
						} else {
							os.writeBit(9700);
						}
						break;
					case L1SkillId.JUDGEMENT:  
						os.writeBit(9711);
						break;
					case L1SkillId.FORCE_STUN:  
						os.writeBit(9609);
						break;
					case L1SkillId.ETERNITY:  
						os.writeBit(9608);
						break;
					case L1SkillId.DEMOLITION:  
						os.writeBit(9606);
						break;
					case L1SkillId.FLAME:  
						os.writeBit(9703);
						break;
					}
					/** ��ų������ �ѹ� */
					os.writeC(0x10);
					switch (skillId) {	
					case L1SkillId.HOLY_WEAPON:
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
					case L1SkillId.BLESS_WEAPON: 
					case L1SkillId.BERSERKERS: 
					case L1SkillId.HASTE:  
					case L1SkillId.IMMUNE_TO_HARM: 
						os.writeC(0x01);
						break;
					case L1SkillId.FLAME:
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
					case L1SkillId.SLOW:  
					case L1SkillId.DARKNESS:  
					case L1SkillId.WEAKNESS:  
					case L1SkillId.SILENCE:  
					case L1SkillId.FOG_OF_SLEEPING:  
					case L1SkillId.DECAY_POTION:  
					case L1SkillId.������:  
					case L1SkillId.SHOCK_STUN:  
					case L1SkillId.�����̾�:  
					case L1SkillId.AREA_OF_SILENCE:  
					case L1SkillId.POLLUTE_WATER:  
					case L1SkillId.STRIKER_GALE:  
					case L1SkillId.THUNDER_GRAB:  
					case L1SkillId.PHANTASM:  
					case L1SkillId.�Ŀ��׸�:  
					case L1SkillId.�������:  
					case L1SkillId.ERASE_MAGIC:  
					case L1SkillId.EARTH_BIND:  
					case L1SkillId.PANIC:  
					case L1SkillId.BONE_BREAK:  
					case L1SkillId.PHANTOM:  
					case L1SkillId.JUDGEMENT:  
					case L1SkillId.FORCE_STUN:  
					case L1SkillId.ETERNITY:  
					case L1SkillId.DEMOLITION:  
						os.writeC(0x00);
						break;
					}
					os.writeC(0x18);
					/** ��ų���̵� -1�� */
					os.writeBit(skillId - 1);
					/** ��ų���̵� -1�� */
					os.writeC(0x20);
					switch (skillId) {
					case L1SkillId.FLAME:
					case L1SkillId.HOLY_WEAPON:
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
					case L1SkillId.BLESS_WEAPON: 
					case L1SkillId.BERSERKERS: 
					case L1SkillId.HASTE:  
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
					case L1SkillId.SLOW:  
					case L1SkillId.AREA_OF_SILENCE:  
					case L1SkillId.EARTH_BIND:  
					case L1SkillId.PANIC:  
					case L1SkillId.BONE_BREAK:  
					case L1SkillId.PHANTOM:  
					case L1SkillId.JUDGEMENT:  
					case L1SkillId.FORCE_STUN:  
					case L1SkillId.ETERNITY:  
					case L1SkillId.DEMOLITION:  
					case L1SkillId.STRIKER_GALE:  
					case L1SkillId.ERASE_MAGIC:  
						os.writeBit(timeSec);
						break;
					case L1SkillId.WEAKNESS:  
					case L1SkillId.IMMUNE_TO_HARM: 
					case L1SkillId.������:  
					case L1SkillId.POLLUTE_WATER:  
					case L1SkillId.THUNDER_GRAB:  
					case L1SkillId.PHANTASM:  
					case L1SkillId.�Ŀ��׸�:  
					case L1SkillId.�������:  
						os.writeBit(timeSec * 10);
						break;
					case L1SkillId.DARKNESS:  
					case L1SkillId.SILENCE:  
					case L1SkillId.FOG_OF_SLEEPING:  
					case L1SkillId.DECAY_POTION:  
					case L1SkillId.SHOCK_STUN:  
					case L1SkillId.�����̾�:  
						os.writeBit(timeSec * 300);
						break;
					}
				}
			}
			return os.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private byte[] partyLocByteInfo(L1PcInstance member) {
		BinaryOutputStream os = new BinaryOutputStream();
		try {
			os.writeC(0x0a);
			os.writeS2(member.getName());

			os.writeC(0x30);
			os.writeBit(member.getMapId());

			os.writeC(0x38);
			os.writeBit(member.getX(), member.getY());

			for (int skillId : BuffIconSkill) {
				int timeSec = member.getSkillEffectTimerSet().getSkillEffectTimeSec(skillId);
				if(member.getSkillEffectTimerSet().hasSkillEffect(skillId)) {
					os.writeC(0x4a);
					os.writeC(0x0a);
					os.writeC(0x08);
					switch (skillId) {	
					case L1SkillId.HOLY_WEAPON: 
						os.writeBit(777);
						break;
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
						os.writeBit(482);
						break;
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
						os.writeBit(483);
						break;
					case L1SkillId.BLESS_WEAPON: 
						os.writeBit(728);
						break;
					case L1SkillId.BERSERKERS: 
						os.writeBit(1556);
						break;
					case L1SkillId.IMMUNE_TO_HARM: 
						os.writeBit(1562);
						break;
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
						os.writeBit(1740);
						break;					
					case L1SkillId.SLOW:  
						os.writeBit(480);
						break;
					case L1SkillId.DARKNESS:  
						os.writeBit(751);
						break;
					case L1SkillId.HASTE:  
						os.writeBit(5397);
						break;
					case L1SkillId.WEAKNESS:  
						os.writeBit(852);
						break;
					case L1SkillId.SILENCE:  
						os.writeBit(824);
						break;
					case L1SkillId.FOG_OF_SLEEPING:  
						os.writeBit(1560);
						break;
					case L1SkillId.DECAY_POTION:  
						os.writeBit(754);
						break;
					case L1SkillId.������:  
						os.writeBit(7439);
						break;
					case L1SkillId.SHOCK_STUN:  
						os.writeBit(1626);
						break;
					case L1SkillId.�����̾�:  
						os.writeBit(1626);
						break;
					case L1SkillId.AREA_OF_SILENCE:  
						os.writeBit(722);
						break;
					case L1SkillId.POLLUTE_WATER:  
						os.writeBit(2348);
						break;
					case L1SkillId.STRIKER_GALE:  
						os.writeBit(2357);
						break;
					case L1SkillId.THUNDER_GRAB:  
						os.writeBit(1620);
						break;
					case L1SkillId.PHANTASM:  
						os.writeBit(3129);
						break;
					case L1SkillId.�Ŀ��׸�:  
						os.writeBit(6176);
						break;
					case L1SkillId.�������:  
						os.writeBit(6499);
						break;																								
					case L1SkillId.ERASE_MAGIC:  
						os.writeBit(1834);
						break;
					case L1SkillId.EARTH_BIND:  
						os.writeBit(893);
						break;
					case L1SkillId.PANIC:  
						os.writeBit(3125);
						break;
					case L1SkillId.BONE_BREAK:  
						os.writeBit(1626);
						break;
					
					case L1SkillId.PHANTOM:  
						if(member.isPhantomDeathed()) {
							os.writeBit(9701);
						} else if (member.isPhantomRippered()) {
							os.writeBit(9702);
						} else {
							os.writeBit(9700);
						}
						break;
					case L1SkillId.JUDGEMENT:  
						os.writeBit(9711);
						break;
					case L1SkillId.FORCE_STUN:  
						os.writeBit(9609);
						break;
					case L1SkillId.ETERNITY:  
						os.writeBit(9608);
						break;
					case L1SkillId.DEMOLITION:  
						os.writeBit(9606);
						break;
					case L1SkillId.FLAME:  
						os.writeBit(9703);
						break;
					}
					/** ��ų������ �ѹ� */
					os.writeC(0x10);
					switch (skillId) {	
					case L1SkillId.HOLY_WEAPON:
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
					case L1SkillId.PHYSICAL_ENCHANT_DEX: 
					case L1SkillId.BLESS_WEAPON: 
					case L1SkillId.BERSERKERS:
					case L1SkillId.HASTE:  
					case L1SkillId.IMMUNE_TO_HARM: 
						os.writeC(0x01);
						break;
					case L1SkillId.FLAME:
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
					case L1SkillId.SLOW:  
					case L1SkillId.DARKNESS:  
					case L1SkillId.WEAKNESS:  
					case L1SkillId.SILENCE:  
					case L1SkillId.FOG_OF_SLEEPING:  
					case L1SkillId.DECAY_POTION:  
					case L1SkillId.������:  
					case L1SkillId.SHOCK_STUN:  
					case L1SkillId.�����̾�:  
					case L1SkillId.AREA_OF_SILENCE:  
					case L1SkillId.POLLUTE_WATER:  
					case L1SkillId.STRIKER_GALE:  
					case L1SkillId.THUNDER_GRAB:  
					case L1SkillId.PHANTASM:  
					case L1SkillId.�Ŀ��׸�:  
					case L1SkillId.�������:  
					case L1SkillId.ERASE_MAGIC:  
					case L1SkillId.EARTH_BIND:  
					case L1SkillId.PANIC:  
					case L1SkillId.BONE_BREAK:  
					case L1SkillId.PHANTOM:  
					case L1SkillId.JUDGEMENT:  
					case L1SkillId.FORCE_STUN:  
					case L1SkillId.ETERNITY:  
					case L1SkillId.DEMOLITION:  
						os.writeC(0x00);
						break;
					}
					os.writeC(0x18);
					/** ��ų���̵� -1�� */
					os.writeBit(skillId - 1);
					/** ��ų���̵� -1�� */
					os.writeC(0x20);
					switch (skillId) {
					case L1SkillId.FLAME:
					case L1SkillId.HOLY_WEAPON:
					case L1SkillId.PHYSICAL_ENCHANT_STR: 
					case L1SkillId.PHYSICAL_ENCHANT_DEX:
					case L1SkillId.BLESS_WEAPON: 
					case L1SkillId.BERSERKERS: 
					case L1SkillId.HASTE:  
					case L1SkillId.ELEMENTAL_FALL_DOWN:  
					case L1SkillId.SLOW:  
					case L1SkillId.AREA_OF_SILENCE:  
					case L1SkillId.EARTH_BIND:  
					case L1SkillId.PANIC:  
					case L1SkillId.BONE_BREAK:
					case L1SkillId.PHANTOM:  
					case L1SkillId.JUDGEMENT:  
					case L1SkillId.FORCE_STUN:  
					case L1SkillId.ETERNITY:  
					case L1SkillId.DEMOLITION:  
					case L1SkillId.STRIKER_GALE:  
					case L1SkillId.ERASE_MAGIC:  
						os.writeBit(timeSec);
						break;
					case L1SkillId.WEAKNESS:  
					case L1SkillId.IMMUNE_TO_HARM: 
					case L1SkillId.������:  
					case L1SkillId.POLLUTE_WATER:  
					case L1SkillId.THUNDER_GRAB:  
					case L1SkillId.PHANTASM:  
					case L1SkillId.�Ŀ��׸�:  
					case L1SkillId.�������:  
						os.writeBit(timeSec * 10);
						break;
					case L1SkillId.DARKNESS:  
					case L1SkillId.SILENCE:  
					case L1SkillId.FOG_OF_SLEEPING:  
					case L1SkillId.DECAY_POTION:  
					case L1SkillId.SHOCK_STUN:  
					case L1SkillId.�����̾�:  
						os.writeBit(timeSec * 300);
						break;
					}
				}
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

	private void ChangeLeader(L1PcInstance pc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(PartyMemberUse);

		writeC(0x0a);
		String Name = pc.getName();
		if (Name.length() > 0) {
			writeC(Name.getBytes().length);
			writeByte(Name.getBytes());
		}
		writeH(0x00);
	}

	private void RemoveMember(L1PcInstance pc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(PartyMemberUse);

		writeC(0x12);
		String Name = pc.getName();
		if (Name.length() > 0) {
			writeC(Name.getBytes().length);
			writeByte(Name.getBytes());
		}
		writeH(0x00);
	}

	public void NameColor(L1PcInstance pc, int type) {
		writeC(Opcodes.S_EVENT);
		writeC(0x6C);
		writeD(pc.getId());
		writeH(type);
	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c, 0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e, 0x9f, 0xa0, 0xa1,
			0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0, 0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8,
			0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef,
			0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };


	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = _bao.toByteArray();
		}

		return _byte;
	}

	@Override
	public String getType() {
		return _S_Party;
	}

}