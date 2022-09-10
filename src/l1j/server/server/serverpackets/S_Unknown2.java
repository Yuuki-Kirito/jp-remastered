package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

public class S_Unknown2 extends ServerBasePacket {
	public S_Unknown2(int type) {
		writeC(Opcodes.S_EVENT);
		switch(type){
		case 0: // �α��ν� ó��, ���Ʈ ��
			writeC(0x3d);
			writeD(0);// �����ð�
			writeC(0);// ����
			writeC(0x29); // unknown
			break;					
		case 1:	// ����
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
}
