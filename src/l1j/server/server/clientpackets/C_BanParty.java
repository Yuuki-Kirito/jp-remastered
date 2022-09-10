/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package l1j.server.server.clientpackets;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_BanParty extends ClientBasePacket {
	

	private static final String C_BAN_PARTY = "[C] C_BanParty";

	public C_BanParty(byte decrypt[], LineageClient client) throws Exception {
		super(decrypt);
		try {
			String s = readS();
			L1PcInstance pc = client.getActiveChar();
			if (!pc.getParty().isLeader(pc)) {
				S_ServerMessage sm = new S_ServerMessage(427);
				pc.sendPackets(sm); // ��Ƽ�� �������� �߹��� �� �ֽ��ϴ�.
				sm = null;
				return;
			}
		
			for (L1PcInstance member : pc.getParty().getMembers()) {
				if (member.getName().toLowerCase().equals(s.toLowerCase())) {
					pc.getParty().leaveMember(member);
					return;
				}
			}
			// �߰ߵ��� �ʾҴ�
			S_ServerMessage sm = new S_ServerMessage(426, s);
			pc.sendPackets(sm); // %0�� ��Ƽ ����� �ƴմϴ�.
			sm = null;
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_BAN_PARTY;
	}

}
