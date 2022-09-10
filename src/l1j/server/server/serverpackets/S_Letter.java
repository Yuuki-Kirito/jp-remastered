package l1j.server.server.serverpackets;

import java.util.StringTokenizer;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_Letter extends ServerBasePacket {

	private static final String S_LETTER = "[S] S_Letter";
	private byte[] _byte = null;

	public S_Letter(L1PcInstance pc, int LetterType, int RecieveType, int objid, String subject) {
		buildPacket(pc, LetterType, RecieveType, objid, subject);
	}
	
//	public S_Letter(L1PcInstance pc, int objid, String subject) {
//		buildPacket(pc, objid, subject);
//	}
	
	public S_Letter(String s) {
		buildPacket(s);
	}

//	private void buildPacket(L1PcInstance pc, int type, int iden, int objid,
//			String ����) {
//		writeC(Opcodes.S_MAIL_INFO);
//		writeC(type); // 0:������ 1:���͸����� 2:������
//		writeH(1);
//		writeD(objid);
//		writeC(iden); // Ȯ�ο��� 0:��Ȯ�� 1:Ȯ��
//		writeD((int) (System.currentTimeMillis() / 1000));
//		writeC(0x00);
//		writeS(pc.getName());
//		writeSS(����);
//	}
	
	private void buildPacket(L1PcInstance pc, int LetterType, int RecieveType, int objid, String subject) {
		writeC(Opcodes.S_MAIL_INFO);
		/**�������� �Ϲ����� Ÿ�� �Ϲ�50, ���� 51*/
		writeC(LetterType);
		/**�������� �Ϲ����� Ÿ�� �Ϲ�50, ���� 51*/
		/**���� ������Ʈ?*/
		writeD(objid);
		/**���� ������Ʈ?*/
		/**���� ������ Ÿ�� 1:������� ������ 0:�޴� ��� ������ �� ���͸��� 2:������*/
		writeC(RecieveType); 
		/**���� ������ Ÿ�� 1:������ 0:���͸����� 2:������*/
		/**���� �޴»�� �̸�*/
		writeS(pc.getName());
		/**���� �޴»�� �̸�*/
		writeSS(subject);
	}
//	
	public void buildPacket(String s) {
		writeC(Opcodes.S_MAIL_INFO);
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreTokens()) {
			writeC(Integer.parseInt(st.nextToken(), 16));
		}
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
		return S_LETTER;
	}
}
