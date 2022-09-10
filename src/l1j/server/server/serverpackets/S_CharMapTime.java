package l1j.server.server.serverpackets;

import l1j.server.Config;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_CharMapTime extends ServerBasePacket {

	private static final String S_MAPTIMER = "[S] S_MapTimer";
	private byte[] _byte = null;
	private static final int RESTART_MAPUI = 803;
	private static int[] ������ = new int[] { 1, 2, 6, 7, 15, 17, 18, 23, 99, 100 };
	private static int[] totaltime = new int[] { Config.������������ð�,
			Config.���ž�߷������ð�, 7200, 10800,
			7200, 7200, Config.PC�����ð�, Config.PC�����ð�, Config.PC�����ð�, 7200, Config.PC�����ð� };

	public S_CharMapTime(L1PcInstance pc) {
		int[] usetime = new int[] { Config.������������ð� - pc.getgirantime(),
				Config.���ž�߷������ð� - pc.getivorytime(),
				7200 - pc.get����time(), 10800 - pc.get������time(),
				7200 - pc.get����time(), //����
				7200- pc.get��time(),
				Config.PC�����ð� - pc.get��time() //�̼��Ǿ��� ����������
				,Config.PC�����ð� - pc.get��time() //�̼��Ǿ��� ����������pc
				,Config.PC�����ð� - pc.get��time() //��Ÿ�¿��� �̺�Ʈ��
				,7200 - pc.get��������time()
				,Config.PC�����ð� - pc.get��time()};//��������
		String[] name = new String[] { "$12125", "$6081", "$30247", "$14667",
			"$30983", "$19375", "$30733", "$30734", "$28760", "$27281", "$23478" };
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(RESTART_MAPUI);
		for (int i = 0; i < ������.length; i++) {
			writeC(0x0a);
			int size = 4 + writeLenght(������[i])
					+ writeLenght(name[i].getBytes().length)
					+ name[i].getBytes().length + writeLenght(usetime[i])
					+ writeLenght(totaltime[i]);
			writeBit(size);
			writeC(0x08);
			writeBit(������[i]);
			writeC(0x12);
			writeC(name[i].getBytes().length);
			writeByte(name[i].getBytes());
			writeC(0x18); // �����̿�ð�
			writeBit(usetime[i]);
			writeC(0x20);
			writeBit(totaltime[i]);

		}
		writeH(0);
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
		return S_MAPTIMER;
	}
}