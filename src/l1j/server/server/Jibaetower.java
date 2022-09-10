package l1j.server.server;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.RotationNotice.RotationNoticeTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_PacketBox;

public class Jibaetower extends Thread {

	private static Jibaetower _instance;

	private boolean _JBStart;
	private static Random _random = new Random(System.nanoTime());

	public boolean getOBStart() {
		return _JBStart;
	}

	public void setOBStart(boolean OB) {
		_JBStart = OB;
	}
	
	public boolean isGmOpen = false;

	public boolean isGmClose = false;

	public static Jibaetower getInstance() {
		if (_instance == null) {
			_instance = new Jibaetower();
		}
		return _instance;
	}

	@Override
	public void run() {
		while (true) {
			try {
//				if (isOpen() || isGmOpen) {
//					isGmOpen = false;
//					Spawn1();
//					Thread.sleep(120000);
//				}
//				if (isClose() || isGmClose) {
//					isGmClose = false;
//					End();
//					Thread.sleep(120000);
//				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(1000L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void Spawn1() {
		try {
			L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "\\������ ž ����."));
			L1World.getInstance().broadcastServerMessage("\\aH������ ž : ���� �ð� ���� ����� �����մϴ�.");
			L1World.getInstance().broadcastServerMessage("\\aH������ ž ���� �ð� : ���� ���� 8�ÿ��� �����ϴ�.");
			/** �����̼� ��Ʈ�ѷ� ���� �ð����� �������� �������� üũ �� */
			long Time = System.currentTimeMillis();
			/** 1�ð� 15�� ���� ���� */
			long EndTime = System.currentTimeMillis() + (1000 * 3600 * 5);
			RotationNoticeTable.getInstance().UpdateNotice(16, new Timestamp(Time), new Timestamp(EndTime));
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc instanceof L1RobotInstance)
					continue;
				pc.�˸�����(pc, true);
			}
			setOBStart(true);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	
	private boolean isOpen() {
		Calendar calender = Calendar.getInstance();
		int hour, minute;
		hour = calender.get(Calendar.HOUR_OF_DAY);
		minute = calender.get(Calendar.MINUTE);
		if ((hour == 7 && minute == 59) || (hour == 19 && minute == 59)) {
			return true;
		}
		return false;
	}

	private boolean isClose() {
		Calendar calender = Calendar.getInstance();
		int hour, minute;
		hour = calender.get(Calendar.HOUR_OF_DAY);
		minute = calender.get(Calendar.MINUTE);
		if ((hour == 13 && minute == 15) || (hour == 1 && minute == 15)) {
			return true;
		}
		return false;
	}

	/** ���� **/
	public void End() {
		L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "\\aH������ ž : ������ ž�� �������ϴ� ."));
		L1World.getInstance().broadcastServerMessage("\\aH������ ž : ������ ž�� �������ϴ�.");
		setOBStart(false);
	}
}