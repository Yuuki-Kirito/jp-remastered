package l1j.server.server.serverpackets;

import java.util.ArrayList;

import l1j.server.MJBookQuestSystem.Compensator.WeekQuestCompensator;
import l1j.server.MJBookQuestSystem.Loader.MonsterBookCompensateLoader;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_WeekQuest extends ServerBasePacket{
	private byte[] _byte = null;
	
	private static final byte[] WQLIST_ANONYMOUSE = new byte[]{
		0x1A,
		0x0B,
		0x08,
		0x01,
		0x10,
		(byte) 0x84,
		(byte) 0xA0,
		(byte) 0xB8,
		0x03,
		0x18,
		(byte) 0xC0,
		(byte) 0x87,
		0x01,
		0x1A,
		0x0B,
		0x08,
		0x01,
		0x10,
		(byte) 0xB5,
		(byte) 0xBF,
		(byte) 0xF0,
		0x06,
		0x18,
		(byte) 0xD8,
		(byte) 0x87,
		0x01,
	};
	
	public S_WeekQuest(){
	}
	
	/** pc �ν��Ͻ��� �ְ� ����Ʈ ����Ʈ�� �����Ѵ�. **/
	public void writeWQList(L1PcInstance pc){
		MonsterBookCompensateLoader compensator = MonsterBookCompensateLoader.getInstance();
		ArrayList<WeekQuestCompensator> list = compensator.getWeekCompensators();
		try {
			byte[] comp1 = list.get(0).getSerialize();
			byte[] comp2 = list.get(1).getSerialize();
			byte[] comp3 = list.get(2).getSerialize();
			byte[] progress = pc.getWeekQuest().getSerialize();

			int size = 0;
			size += WQLIST_ANONYMOUSE.length;
			size += comp1.length + comp2.length + comp3.length;
			size += progress.length;
			size += 2;
			
			writeC(Opcodes.S_EXTENDED_PROTOBUF);
			writeH(0x032A);
			writeC(0x0A);
			writeC(size);
			writeC(0x01);
			writeC(0x0A);
			writeC(0x55);
			writeByte(comp1);
			writeByte(comp2);
			writeByte(comp3);
			writeByte(WQLIST_ANONYMOUSE);
			writeByte(progress);
			writeH(0x00);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/** ���� ���� ���� ����Ʈ�� ������ �����Ѵ�.
	 * difficulty 	: 0, 1, 2 ���̵�,
	 * section 		: 0, 1, 2 ����
	 * count : ���� ���� ��(max/max �Ǹ� �ڵ����� clear ������ ����)
	 *  **/
	public void writeWQUpdate(int difficulty, int section, int count){
		writeSignature(0x032D);
		writeByte(new byte[]{
				0x08, 
				(byte)(difficulty & 0xff), 
				0x10, 
				(byte)(section & 0xff), 
				0x18, 
				(byte)(count & 0xff), 
				0x00, 
				0x00
			}
		);
	}
	
	/** ���� Ŭ���� ���� ��Ŷ�� ������. **/
	/** 
	 * difficulty 	: ���̵�
	 * status		: ���°� (1=Ȱ��ȭ�� ��Ŵ, 3=Ŭ����޽���(�̺�Ʈ�˸���)�� ������ �����ư Ȱ��ȭ, 5=���� Ŭ��� ǥ���ϰ� �����ư ��Ȱ��ȭ)
	 * (4=���� ��Ȱ��ȭ... �ְ� �� ���Ŷ� ����ϸ� �ɵ�)
	 *  **/
	public void writeWQLineClear(int difficulty, int status){
		writeSignature(0x032E);
		writeByte(new byte[]{
				0x08,
				(byte)(difficulty & 0xff),
				0x10,
				(byte)(status & 0xff),
				0x00,
				0x00
			}
		);
	}
	
	private void writeSignature(int type){
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
	}
	
	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return "S_WeekQuest";
	}
}