package l1j.server.server.model.skill.skills;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1BookMark;

public class Teleport {

	public static void runSkill(L1Character cha, int _bookmarkId) {
		L1PcInstance pc = (L1PcInstance) cha;
		L1BookMark bookm = pc.getBookMark(_bookmarkId);
		if (bookm != null) {
			if (pc.getMap().isEscapable() || pc.isGm()) {
				int newX = bookm.getLocX();
				int newY = bookm.getLocY();
				short mapId = bookm.getMapId();

				L1Teleport.teleport(pc, newX, newY, mapId, pc.getMoveState()
						.getHeading(), true, true);

			} else {
				pc.sendPackets(new S_Paralysis(
						S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."));
			}
		} else {
			if ((pc.getMap().isTeleportable(pc.getX(), pc.getY()) && pc.getMap().isTeleportable()) || pc.isGm()
					|| ((pc.getMapId() == 101 || pc.getMapId() == 102 || pc.getMapId() == 103 || pc.getMapId() == 104 
					|| pc.getMapId() == 105 || pc.getMapId() == 106 || pc.getMapId() == 107 || pc.getMapId() == 108 || pc.getMapId() == 109 
					|| pc.getMapId() == 110 || pc.getMapId() == 12852 || pc.getMapId() == 12853 || pc.getMapId() == 12854 || pc.getMapId() == 12855
					|| pc.getMapId() == 12856 || pc.getMapId() == 12857 || pc.getMapId() == 12858 || pc.getMapId() == 12859 || pc.getMapId() == 12860
					|| pc.getMapId() == 12861) && pc.is������())
					|| ((pc.getMapId() == 15410 || pc.getMapId() == 15420 || pc.getMapId() == 15430 || pc.getMapId() == 15440) && pc.is�����̹���())
					|| (!GameTimeClock.getInstance().getGameTime().isNight() &&  pc.getMapId() == 54)) {
				L1Location newLocation = pc.getLocation().randomLocation(200, true);
				int newX = newLocation.getX();
				int newY = newLocation.getY();
				short mapId = (short) newLocation.getMapId();

				L1Teleport.teleport(pc, newX, newY, mapId, pc.getMoveState()
						.getHeading(), true);
			} else {
				pc.sendPackets(new S_Paralysis(
						S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
				pc.sendPackets(new S_ServerMessage(276));
			}
		}
	}

}
