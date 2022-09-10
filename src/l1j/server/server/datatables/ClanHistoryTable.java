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
package l1j.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Ranking;
import l1j.server.server.utils.SQLUtil;

// Referenced classes of package l1j.server.server:
// IdFactory

public class ClanHistoryTable {
	private static Logger _log = Logger.getLogger(ClanHistoryTable.class
			.getName());
	private static ClanHistoryTable _instance;

	public static ClanHistoryTable getInstance() {
		if (_instance == null) {
			_instance = new ClanHistoryTable();
		}
		return _instance;
	}

	private ClanHistoryTable() {
	}

	public void add(L1Clan clan, int ck, String charname, String itemname, int itemcount) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO clan_history SET clan_id=?, ckck=?, char_name=?, item_name=?, item_count=?, time=?");
			pstm.setInt(1, clan.getClanId());
			pstm.setInt(2, ck);
			pstm.setString(3, charname.toString());
			pstm.setString(4, itemname.toString());
			pstm.setInt(5, itemcount);
			Calendar cal = Calendar.getInstance();
			pstm.setTimestamp(6, new Timestamp(cal.getTimeInMillis()));
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void delete(int clanid) {// Ŭ�����̵�� üũ�� 3������ ��� ����
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM clan_history WHERE clan_id=?");
			pstm.setInt(1, clanid);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void dateCheckDelete() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM clan_history");
			rs = pstm.executeQuery();
			while (rs.next()) {
				boolean ck = System.currentTimeMillis() - 3600000 * 24 * 3 > rs
						.getTimestamp("time").getTime();
				// System.out.println(System.currentTimeMillis() - 3600000 * 24
				// * 3 +" > "+rs.getTimestamp("time").getTime());
				int num = rs.getInt("num");
				if (ck) {
					Connection con2 = null;
					PreparedStatement pstm2 = null;
					try {
						con2 = L1DatabaseFactory.getInstance().getConnection();
						pstm2 = con2
								.prepareStatement("DELETE FROM clan_history WHERE num=?");
						pstm2.setInt(1, num);
						pstm2.executeUpdate();
					} catch (SQLException e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					} finally {
						SQLUtil.close(pstm2);
						SQLUtil.close(con2);
					}
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs, pstm, con);
		}

	}

	public void history(L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM clan_history WHERE clan_id =? ORDER BY num DESC");
			pstm.setInt(1, pc.getClanid());
			rs = pstm.executeQuery();
			int count = 0;
			while (rs.next() && count++ < 7) {
				sb.append(rs.getString("text"));
				SimpleDateFormat dateFormat = new SimpleDateFormat("mm��");
				String time = dateFormat.format(new Timestamp((System
						.currentTimeMillis() - rs.getTimestamp("time")
						.getTime())
						+ (60 * 1000 * 60 * 15)));
				sb.append("(��� �ð� : " + time + ")\r\n\r\n");
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs, pstm, con);
		}
		pc.sendPackets(new S_Ranking(sb.toString()));

	}

}
