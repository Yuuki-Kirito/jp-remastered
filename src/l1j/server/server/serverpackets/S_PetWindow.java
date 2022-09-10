package l1j.server.server.serverpackets;

import java.util.ArrayList;

import l1j.server.server.Opcodes;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.templates.L1Pet;
import l1j.server.server.templates.L1PetSkill;

import l1j.server.server.utils.BinaryOutputStream;

public class S_PetWindow extends ServerBasePacket {

	private static final String S_PetWindow = "[S] S_PetWindow";
	
	private byte[] _byte = null;
	
	public S_PetWindow(int cnt, L1NpcInstance pet, boolean show) {
		buildPacket(cnt, pet, show);
	}

	private void buildPacket(int cnt, L1NpcInstance pet, boolean show) {
		writeC(Opcodes.S_VOICE_CHAT);
		writeC(0x0c);
		if (show) {
			writeH(cnt);// ����
			writeD(0);
			writeD(pet.getId());// ��id
			writeD(pet.getMapId());// ??
			writeH(pet.getX());// x
			writeH(pet.getY());// y
			if (pet instanceof L1PetInstance)
				writeC(1);
			else if (pet instanceof L1SummonInstance)
				writeC(0);
			writeS(pet.getName());// name
		} else {
			writeH(cnt);// ����
			writeD(1);
			writeD(pet.getId());// id
		}
	}
	
	public S_PetWindow(L1PetInstance Pet) {
		buildPacket(Pet);
	}
	
	public static final int PatHp		= 1; 	/** ����   Hp */ 
	public static final int PatExp 		= 2; 	/** ����ġ */ 
	public static final int PatLevel 	= 3; 	/** ������ ���� */ 
	public static final int PatName 	= 4; 	/** ����� */ 
	
	public static final int PatBonusPoint 	= 5; 	/** ������ ���� */ 
	public static final int PatStatUpDate  	= 6; 	/** Hp & Ac && Mr */
	
	public static final int PatElixir 	= 8; 	/** �������� */ 
	public static final int DeadExp		= 9; 	/** ���� ����ġ & �ۼ������� */
	
	public static final int PatPoint 	= 10; 	/** ���� & ��������Ʈ ��Ŷ */ 
	public static final int Friendship 	= 11; 	/** ��������Ʈ ��Ŷ */ 
	public static final int PatFight 	= 12; 	/** ������ ���� */ 
	
	public static final int DogBlood    = 14;   /** ������ �� */
	
	public S_PetWindow(int Op, L1PetInstance Pet) {
		buildPacket(Op, Pet);
	}
	
	private void buildPacket(int Op, L1PetInstance Pet) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd0);
		writeC(0x07);
		
		/** ������Ʈ */
		writeC(0x08);
		writeBit(Pet.getId());
		
		/** Ÿ���������� ���� */
		switch (Op) {	
			case DogBlood:
				writeC(0x90);
				writeC(0x02);
				if(Pet.SkillCheck(L1SkillId.DogBlood)){
					writeBit(0x64);
				}else writeBit(0x0c);
				break;
		
			case PatPoint:
				writeC(0xa0);
				writeC(0x01);
				writeBit(Pet.getFighting());
				
				writeC(0xa8);
				writeC(0x01);
				writeBit(Pet.getFriendship());
				break;
			
			case Friendship:
				writeC(0xa8);
				writeC(0x01);
				writeBit(Pet.getFriendship());
				break;
				
			case PatStatUpDate:
				writeC(0x30);
				writeBit(Pet.getBaseMaxHp());
				
				writeC(0x38);
				writeBit(Pet.getBonusHp());
				
				writeC(0x40);
				writeBit(Pet.getCurrentHp());
				
				writeC(0xf0);
				writeC(0x01);
				writeBit(Pet.getAC().getAc());
				
				writeC(0xf8);
				writeC(0x01);
				writeBit(Pet.getResistance().getMr());
				break;
				
			case DeadExp:
				writeC(0x28);
				writeBit(Pet.getExp());
				
				writeC(0xb0);
				writeC(0x01);
				writeBit(Pet.isDeadExp() ? 1 : 0);
				break;
				
			case PatHp:
				writeC(0x40);
				writeBit(Pet.getCurrentHp());
				break;
				
			case PatExp:
				writeC(0x28);
				writeBit(Pet.getExp());
				break;
				
			case PatName:
				writeC(0x12);
				writeC(Pet.getName().getBytes().length);
				writeByte(Pet.getName().getBytes());
				break;
				
			case PatElixir:
				writeC(0x78);
				writeBit(Pet.getElixir());
				
				writeC(0x88);
				writeC(0x01);
				writeBit(Pet.getElixirHunt());
				
				writeC(0x90);
				writeC(0x01);;
				writeBit(Pet.getElixirSurvival());
				
				writeC(0x98);
				writeC(0x01);
				writeBit(Pet.getElixirSacred());
				break;
				
			case PatBonusPoint:
				writeC(0x60);
				writeBit(Pet.getHunt());
				
				writeC(0x68);
				writeBit(Pet.getSurvival());
				
				writeC(0x70);
				writeBit(Pet.getSacred());
				
				writeC(0x80);
				writeC(0x01);
				writeBit(Pet.getBonusPoint());
				break;
				
			case PatLevel:
				writeC(0x20);
				writeBit(Pet.getLevel());
				
				writeC(0x28);
				writeBit(Pet.getExp());
				
				writeC(0x30);
				writeBit(Pet.getBaseMaxHp());
				
				writeC(0x38);
				writeBit(Pet.getBonusHp());
				
				writeC(0x40);
				writeBit(Pet.getCurrentHp());
				
				writeC(0x60);
				writeBit(Pet.getHunt());
				
				writeC(0x68);
				writeBit(Pet.getSurvival());
				
				writeC(0x70);
				writeBit(Pet.getSacred());
				
				writeC(0x78);
				writeBit(Pet.getElixir());
				
				if(Pet.getBonusPoint() != 0){
					writeC(0x80);
					writeC(0x01);
					writeBit(Pet.getBonusPoint());
				}
				break;	
		}
		writeH(0x00);
	}
	
	public S_PetWindow(L1PetInstance Pet, boolean Booster) {
		buildPacket(Pet, Booster);
	}
	
	private void buildPacket(L1PetInstance Pet, boolean Booster) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd0);
		writeC(0x07);
		
		/** ������Ʈ */
		writeC(0x08);
		writeBit(Pet.getId());
		
		writeC(0x98);
		writeC(0x02);
		writeBit(Booster ? 1 : 0);
			
		writeC(0x81);
		writeC(0x02);
		writeF(Pet.SpeedTime(0));
		
		writeC(0x89);
		writeC(0x02);
		writeF(Pet.SpeedTime(1));

		writeH(0x00);
	}

	private void buildPacket(L1PetInstance Pet) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd0);
		writeC(0x07);
		
		/** ������Ʈ */
		writeC(0x08);
		writeBit(Pet.getId());
		
		/** �̸� */
		writeC(0x12);
		writeC(Pet.getName().getBytes().length);
		writeByte(Pet.getName().getBytes());
		
		/** �̸� */
		writeC(0x18);
		writeBit(Pet.getPetInfo());
		
		/** �̸� */
		writeC(0x20);
		writeBit(Pet.getLevel());
		
		/** ����ġ */
		writeC(0x28);
		writeBit(Pet.getExp());
		
		/** �ִ� Hp */
		writeC(0x30);
		writeBit(Pet.getBaseMaxHp());
		
		/** ���ʽ� Hp �ε� */
		writeC(0x38);
		writeBit(Pet.getBonusHp());
		
		/** ���� Hp */
		writeC(0x40);
		writeBit(Pet.getCurrentHp());
		
		/** �⺻ ���� ���� 10*/
		writeC(0x48);
		writeBit(0x0a);
		
		/** �⺻ ���� ���� 10*/
		writeC(0x50);
		writeBit(0x0a);
		
		/** �⺻ ���� ���� 10*/
		writeC(0x58);
		writeBit(0x0a);
		
		/** ���ʽ� ���� ���� 10*/
		writeC(0x60);
		writeBit(Pet.getHunt());
		
		/** ���ʽ� ���� ���� 0*/
		writeC(0x68);
		writeBit(Pet.getSurvival());
		
		/** ���ʽ� ���� ���� 0*/
		writeC(0x70);
		writeBit(Pet.getSacred());
		
		/** ������ ���� */
		writeC(0x78);
		writeBit(Pet.getElixir());
		
		/** ������ ���� */
		writeC(0x80);
		writeC(0x01);
		writeBit(Pet.getBonusPoint());
		
		/** �� ������ �κ� üũ */
		writeC(0x88);
		writeC(0x01);
		writeBit(Pet.getElixirHunt());
		
		writeC(0x90);
		writeC(0x01);
		writeBit(Pet.getElixirSurvival());
		
		writeC(0x98);
		writeC(0x01);
		writeBit(Pet.getElixirSacred());
		
		/** ���� ��Ŷ 10000 �� ���� */
		writeC(0xa0);
		writeC(0x01);
		writeBit(Pet.getFighting());
		
		/** ��������Ʈ 9999 ���� ��� */
		writeC(0xa8);
		writeC(0x01);
		writeBit(Pet.getFriendship());
		
		/** ����ġ �ٿ� ��Ŷ */
		writeC(0xb0);
		writeC(0x01);
		writeBit(Pet.isDeadExp() ? 1 : 0);
		
		/** ac���� ��Ŷ */
		writeC(0xf0);
		writeC(0x01);
		writeBit(Pet.getAC().getAc());
		
		/** ac���� ��Ŷ */
		writeC(0xf8);
		writeC(0x01);
		writeBit(Pet.getResistance().getMr());
		
		/** Ȯ�� ��� */
		writeC(0x90);
		writeC(0x02);
		writeC(0x0c);
		
		
		
		writeH(0x00);
	}
	
	public S_PetWindow(L1ItemInstance items, L1PetInstance Pet, boolean On, boolean Die, boolean Name, boolean Elixir, boolean Product) {
		buildPacket(items, Pet, On, Die, Name, Elixir, Product);
	}
	
	/** ������ ���� ��Ŷ ó�� */
	private void buildPacket(L1ItemInstance items, L1PetInstance Pet, boolean On, boolean Die, boolean Name, boolean Elixir, boolean Product) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0x4d);
		writeC(0x02);
		
		/** ������ ���� */
		int Length = 0;
		Length += On ? 2 : 0; 
		Length += Die ? 2 : 0; 
		Length += Name ? Pet.getName().getBytes().length + 2 : 0; 
		Length += Elixir ? 2 : 0;  
		Length += Product ? 2 : 0;
		
		/** ��ü ������ */
		writeC(0x0a);
		writeC(Length + size7B(items.getId()) + 4);
		
		writeC(0x08);
		writeBit(items.getId());
		
		/** 2�� ������ */
		writeC(0xaa);
		writeC(0x01);
		writeC(Length);
		
		
		/** ������ ���� ��Ŷ �¿��� 0���� 1�� */
		if(Product){
			writeC(0x08); 
			writeC(Pet.isProduct() ? 1 : 0); 
		}
		
		if(Die){
			writeC(0x10); 
			writeC(Pet.isDead() ? 1 : 0); 
		}
		
		if(On){
			writeC(0x18); 
			writeC(Pet.isPetSummons() ? 1 : 0); 
		}
		
		if(Name){
			writeC(0x32); 
			writeC(Pet.getName().getBytes().length);
			writeByte(Pet.getName().getBytes()); 
		}
		
		if(Elixir){
			writeC(0x38); 
			writeC(Pet.getElixir());
		}
		writeH(0x00);
		
	}
	
	public S_PetWindow(int Number, int Time) {
		buildPacket(Number, Time);
	}
	
	/** �� ���� ��ų �κ� ó�� */
	private void buildPacket(int Number, int Time) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd5);
		writeC(0x07);
		
		/** ������ ��� */
		writeC(0x0a);
		writeBit(0x06);
		
		/** ��ų �ð� */
		writeC(0x08);
		writeBit(Number);
		
		/** ��ų �ѹ� */
		writeC(0x10);
		writeBit(Time);
		
		writeH(0x00);
	}

	/** ��ų ���� ���� ��Ŷ ó�� */
	 public S_PetWindow(int Number, boolean Hit) {
		buildPacket(Number, Hit);
	}
	 
	private void buildPacket(int Number, boolean Hit) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd9);
		writeC(0x07);
		
		writeC(0x08);
		writeC(Number);
		
		writeC(0x10);
		writeC(Hit ? 0 : 1);
		
		writeH(0x00);
	}
	
	public S_PetWindow(L1PetSkill Number) {
		buildPacket(Number);
	}
	
	/** �� ��ų �κ� ó�� */
	private void buildPacket(L1PetSkill Number) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd6);
		writeC(0x07);
		
		/** ������ ��� */
		writeC(0x0a);
		writeBit(0x04);
		
		/** ��ų �ѹ� */
		writeC(0x08);
		writeBit(Number.getSkillNumber());
		
		/** ��ų ���� */
		writeC(0x10);
		writeBit(Number.getSkillLevel());
	
		writeH(0x00);
	}
	
	/** �� ���� ��Ŷ ó�� */
	/**
	aa 01 15 
	08 00
	10 00 
	18 00 
	20 f2 75 28 13 //������Ʈ �ε��ϰ�
	32 06 24 32 38 33 38 32 //�̸�
	38 00 //������ ����
	b0 01 01 //��ɷ�
	b8 01 00 //������
	c0 01 00 //�ż���
	c8 01 05 //Ȯ�ξȵ�
	d0 01 01 //��������Ʈ
	�� ���� ���� ó�� �۾� �� */
	
	private static BinaryOutputStream os;
	
	/** ��Ŷ ó���Ҽ��մ� ������ ���� ���� */
	public static byte[] DogCollar(L1ItemInstance items) {
		if (items.getItem().getType2() == 0 && 
			(items.getItemId() == 40314 || items.getItemId() == 40316)) {
			L1Pet Pet = PetTable.getTemplate(items.getId());
			/** �꿡 ���� ������Ʈ �˻��� �� ������Ʈ �������� */
			if (Pet != null) {
				os = new BinaryOutputStream();
				/** ������ ��� */
				os.writeC(0xaa); 
				os.writeC(0x01); 
				os.writeC(15 + Pet.getName().getBytes().length); 
				
				/** ������ ���� ��Ŷ �¿��� 0���� 1�� */
				os.writeC(0x08); 
				os.writeC(Pet.isProduct() ? 1 : 0); 
				
				/** ���� ����  */
				os.writeC(0x10); 
				os.writeC(Pet.isPetDead() ? 1 : 0); 
				
				/** ���� �κ�  */
				os.writeC(0x18); 
				os.writeC(0x00); 
				
				/** �� �̹��� �� ���� ���� */
				os.writeC(0x20); 
				os.writeBit(Pet.getPetInfo()); 
				
				/** �� ���� üũ */
				os.writeC(0x28); 
				os.writeC(Pet.getLevel()); 
				
				/** �� �̸� */
				os.writeC(0x32); 
				os.writeC(Pet.getName().getBytes().length);
				os.writeByte(Pet.getName().getBytes());
				
				/** ������ �κ� */
				os.writeC(0x38); 
				os.writeC(Pet.getElixir()); 
				
				/** �Ʒ��� Ȯ�� ��� 
				os.writeC(0xb0); 
				os.writeC(0x01); 
				os.writeC(0x00); 
				
				os.writeC(0xb8); 
				os.writeC(0x01); 
				os.writeC(0x00); 
				
				os.writeC(0xc0); 
				os.writeC(0x01); 
				os.writeC(0x00);
				
				os.writeC(0xc8); 
				os.writeC(0x01); 
				os.writeC(0x05); 
				
				os.writeC(0xd0); 
				os.writeC(0x01); 
				os.writeC(0x01);
				*/
				
				return os.getBytes();
			}
		}
		return null;
	}
	
	/**
	 * b4 d6 07 
	 * 0a 04 
	 * 08 01 10 00 
	 * 0a 04 
	 * 08 07 10 00 
	 * 0a 04 
	 * 08 0f 10 00 
	 * 0a 04 
	 * 08 15 10 00
	 */
	/** �� ��ų ���� �۾��� */
	public S_PetWindow(L1PetInstance Pet, int Test) {
		buildPacket(Pet, Test);
	}
	
	/** �� ���� ��ų �ΰ� ���� �ƹ��� ���� */
	private void buildPacket(L1PetInstance Pet, int Test) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd0);
		writeC(0x07);
		
		/** ������Ʈ */
		writeC(0x08);
		writeBit(Pet.getId());
		
		writeC(0x81);
		writeC(0x02);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeBit(Test);
		writeC(0x40);
		
		writeC(0x89);
		writeC(0x02);
		
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeBit(Test);
		writeC(0x40);
		
		writeH(0x00);
	}	
	
	public S_PetWindow(ArrayList<L1PetSkill> Number) {
		buildPacket(Number);
	}
	
	/** �� ��ų �κ� ó�� */
	private void buildPacket(ArrayList<L1PetSkill> Number) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd6);
		writeC(0x07);
		
		for (L1PetSkill NumberSkii : Number) {
			/** ������ ��� */
			writeC(0x0a);
			writeBit(0x04);
			
			/** ��ų �ѹ� */
			writeC(0x08);
			writeBit(NumberSkii.getSkillNumber());
			
			/** ��ų ���� */
			writeC(0x10);
			writeBit(NumberSkii.getSkillLevel());
		}
		writeH(0x00);
	}
	
	/** �׽�Ʈ�� ��Ŷ */
	public S_PetWindow(int Test) {
		buildPacket(Test);
	}
	
	//b4 76 00 08 e5 78 10 64 18 00 ����20 01 28 00 30 00 70 c8

	private void buildPacket(int Test) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0xd6);
		writeC(0x07);
		
		writeC(0x0a);
		writeBit(0x04);
		
		writeC(0x08);
		writeBit(Test);
		
		writeC(0x10);
		writeBit(0x00);
	
		writeH(0x00);
	}

	@Override
	public String getType() {
		return S_PetWindow;
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}
}
