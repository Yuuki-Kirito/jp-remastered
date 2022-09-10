package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

public class S_Unknown1 extends ServerBasePacket {

	private static final String S_Unknown1 = "[S] S_Unknown1";

	public S_Unknown1() {
		writeC(Opcodes.S_ENTER_WORLD_CHECK);
		writeC(0x03);
	}
	
	public S_Unknown1(int type) {
		writeC(Opcodes.S_EVENT);
		switch(type){
		case 0: /** �α��� ��Ŷ */
			writeC(0x3d);					
            writeD(0);// �����ð�  
            writeC(0);// ����  
            writeC(0x29); // unknown  
			break;					
		case 1:	/** ����ŸƮ ��Ŷ */
			writeC(0x2A);
			writeD(0);
			writeH(0);
			break;
		}
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_Unknown1;
	}
}
