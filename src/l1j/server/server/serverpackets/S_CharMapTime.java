package l1j.server.server.serverpackets;

import l1j.server.Config;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_CharMapTime extends ServerBasePacket {

	private static final String S_MAPTIMER = "[S] S_MapTimer";
	private byte[] _byte = null;
	private static final int RESTART_MAPUI = 803;
	private static int[] 고정값 = new int[] { 1, 2, 6, 7, 15, 17, 18, 23, 99, 100 };
	private static int[] totaltime = new int[] { Config.GIRAN_PRISON_DUNGEON_TIME,
			Config.IVORY_TOWER_BALROG_CAMP_TIME, 7200, 10800,
			7200, 7200, Config.PC_TIME, Config.PC_TIME, Config.PC_TIME, 7200, Config.PC_TIME };

	public S_CharMapTime(L1PcInstance pc) {
		int[] usetime = new int[] { Config.GIRAN_PRISON_DUNGEON_TIME - pc.getgirantime(),
				Config.IVORY_TOWER_BALROG_CAMP_TIME - pc.getivorytime(),
				7200 - pc.gettime(), 10800 - pc.getatubatime(),
				7200 - pc.getevatime(), //더미
				7200- pc.getrubbertime(),
				Config.PC_TIME - pc.getrubbertime() //미소피아의 숨겨진마을
				,Config.PC_TIME - pc.getrubbertime() //미소피아의 숨겨진마을pc
				,Config.PC_TIME - pc.getrubbertime() //불타는여정 이벤트맵
				,7200 - pc.getblackbattleshiptime()
				,Config.PC_TIME - pc.getrubbertime()};//말섬던전
		String[] name = new String[] { "$12125", "$6081", "$30247", "$14667",
			"$30983", "$19375", "$30733", "$30734", "$28760", "$27281", "$23478" };
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(RESTART_MAPUI);
		for (int i = 0; i < 고정값.length; i++) {
			writeC(0x0a);
			int size = 4 + writeLenght(고정값[i])
					+ writeLenght(name[i].getBytes().length)
					+ name[i].getBytes().length + writeLenght(usetime[i])
					+ writeLenght(totaltime[i]);
			writeBit(size);
			writeC(0x08);
			writeBit(고정값[i]);
			writeC(0x12);
			writeC(name[i].getBytes().length);
			writeByte(name[i].getBytes());
			writeC(0x18); // 남은이용시간
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