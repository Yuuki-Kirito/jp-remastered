/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.command.executor;

import java.util.StringTokenizer;
//import java.util.logging.Logger;

import l1j.server.server.datatables.ExpTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class L1Describe implements L1CommandExecutor {
	// private static Logger _log =
	// Logger.getLogger(L1Describe.class.getName());

	private L1Describe() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1Describe();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			StringTokenizer st = new StringTokenizer(arg);
			String name = st.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(name);
			if (target == null) {
				pc.sendPackets(new S_ServerMessage(73, name)); // \f1%0은 게임을 하고
																// 있지 않습니다.
				return;
			}

			int lv = target.getLevel();
			int currentLvExp = ExpTable.getExpByLevel(lv);
			int nextLvExp = ExpTable.getExpByLevel(lv + 1);
			double neededExp = nextLvExp - currentLvExp;
			double currentExp = target.getExp() - currentLvExp;
			int per = (int) ((currentExp / neededExp) * 100.0);

			pc.sendPackets(new S_SystemMessage("(정보) 케릭터 : " + target.getName()
					+ " / 혈맹 : " + target.getClanname()), true);
			pc.sendPackets(new S_SystemMessage(
					"----------------------------------------------------"));
			pc.sendPackets(new S_SystemMessage("* Lv: " + lv + "( " + per
					+ "% )" + " (Ac: " + target.getAC().getAc() + " / Mr: "
					+ target.getResistance().getMr() + " / SP: "
					+ target.getAbility().getSp() + ')'), true);
			pc.sendPackets(
					new S_SystemMessage("* Lawful: " + target.getLawful()
							+ " / " + "PkCnt: " + target.get_PKcount() + ')'),
					true);

			int hpr = target.getHpr() + target.getInventory().hpRegenPerTick();
			int mpr = target.getMpr() + target.getInventory().mpRegenPerTick();

			pc.sendPackets(
					new S_SystemMessage("* HP: (" + target.getCurrentHp() + '/'
							+ target.getMaxHp() + ") (HPr: " + hpr + ')'
							+ " / MP: (" + target.getCurrentMp() + '/'
							+ target.getMaxMp() + ") (MPr: " + mpr + ')'), true);
			pc.sendPackets(new S_SystemMessage("* (S: "
					+ target.getAbility().getTotalStr() + " / " + "D: "
					+ target.getAbility().getTotalDex() + " / " + "C: "
					+ target.getAbility().getTotalCon() + " / " + "I: "
					+ target.getAbility().getTotalInt() + " / " + "W: "
					+ target.getAbility().getTotalWis() + " / " + "C: "
					+ target.getAbility().getTotalCha() + ')'), true);

			pc.sendPackets(new S_SystemMessage(
					"----------------------------------------------------"),
					true);
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".정보 [케릭터명] 으로 입력하세요."), true);
		}
	}
}
