package l1j.server.server.command.executor;

import java.util.StringTokenizer;

import l1j.server.server.datatables.AttendanceTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;

public class L1AttendanceModify implements L1CommandExecutor {

	private L1AttendanceModify() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1AttendanceModify();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {

			StringTokenizer str = new StringTokenizer(arg);
			String target_name = str.nextToken();
			int modify_order = Integer.parseInt(str.nextToken());
			int modify_number = Integer.parseInt(str.nextToken());
			int modify_result = Integer.parseInt(str.nextToken());

			if (target_name.equalsIgnoreCase("��ü")) {
				for(L1PcInstance target : L1World.getInstance().getAllPlayers()) {
					if(target == null || target._robot || target.isPrivateShop() || target.noPlayerCK)
						continue;
					
					if (modify_order == 0) {
						if (modify_number == 0) {
							byte[] reset = new byte[AttendanceTable.getInstance().getHomeSize()];
							target.getAccount().setAttendanceHomeBytes(reset);
						} else {
							target.getAccount().getAttendanceHomeBytes()[modify_number - 1] = (byte) modify_result;
						}
					} else {
						if (modify_number == 0) {
							byte[] reset = new byte[AttendanceTable.getInstance().getPcSize()];
							target.getAccount().setAttendancePcBytes(reset);
						} else {
							target.getAccount().getAttendancePcBytes()[modify_number - 1] = (byte) modify_result;
						}
					}
					target.getAccount().storeAttendBytes();
				}
			} else {
				L1PcInstance target = L1World.getInstance().getPlayer(target_name);
				if (target == null)
					pc.sendPackets(new S_SystemMessage("���忡 �������� �ʴ� �����Դϴ�."));

				if (modify_order == 0) {
					if (modify_number == 0) {
						byte[] reset = new byte[AttendanceTable.getInstance().getHomeSize()];
						target.getAccount().setAttendanceHomeBytes(reset);
					} else {
						target.getAccount().getAttendanceHomeBytes()[modify_number - 1] = (byte) modify_result;
					}
				} else {
					if (modify_number == 0) {
						byte[] reset = new byte[AttendanceTable.getInstance().getPcSize()];
						target.getAccount().setAttendancePcBytes(reset);
					} else {
						target.getAccount().getAttendancePcBytes()[modify_number - 1] = (byte) modify_result;
					}
				}
				target.getAccount().storeAttendBytes();
			}
			pc.sendPackets(new S_SystemMessage(target_name + " �� " + 
					(modify_order == 0 ? "�Ϲ��⼮ " : "�Ǿ����⼮ ") + modify_number + "������ �⼮������ �����Ǿ����ϴ�."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage((new StringBuilder())
					.append(".�⼮���� [�����̸�or��ü] [�Ϲ�(0),�Ǿ�(1)] [����(0-��ü)] [0,1,2]�� �Է��� �ּ���. ").toString()));
		}
	}
}
