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
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.utils.SQLUtil;
import server.Server;

// Referenced classes of package l1j.server.server:
// IdFactory

public class LetterTable {
	private static Logger _log = Logger.getLogger(LetterTable.class.getName());
	private volatile static LetterTable uniqueInstance = null;

	public LetterTable() {
	}

	public static LetterTable getInstance() {
		if (uniqueInstance == null) {
			synchronized (Server.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new LetterTable();
				}
			}
		}
		return uniqueInstance;
	}

	//テンプレートID一覧
	// 16:キャラクターが存在しない
	// 32:荷物が多すぎる
	// 48:血盟が存在しない
	// 64:※内容が表示されない（白文字）
	// 80：※内容が表示されない（黒字）
	// 96:※内容が表示されない（黒字）
	// 112:おめでとうございます。 ％nあなたが参加したオークションは最終価格％0アデナの価格で落札されました。
	// 128：あなたが提示された金額よりも少し高額を提示した方が現れたため、残念ながら入札に失敗しました。
	// 144:あなたが参加したオークションは成功しましたが、現在家を所有できる状態にありません。
	// 160：あなたが所有していた家が最終価格％1アデナで落札されました。
	// 176:あなたが申し込んだオークションは、オークション期間内に提示した金額以上での支払を表明することが現れなかったため、結局
	//削除されました。
	// 192:あなたが申し込んだオークションは、オークション期間内に提示した金額以上での支払を表明することが現れなかったため、結局
	//削除されました。
	// 208:あなたの血盟が所有している家は、本霊主義領地に帰属しているため、今後利用したいならこちらに税金を払う
	//しなければなりません。
	// 224:あなたは、あなたの家に課された税%0アデナをまだ納入していません。
	// 240:あなたは、最終的にあなたの家に課せられた税%0を支払わなかったので、警告通りにあなたの家の所有権を奪い取る。

	public synchronized int getLetterCount(String name, int type) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int cnt = 0;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT count(*) as cnt FROM letter WHERE receiver=? AND template_id = ? order by date");
			pstm.setString(1, name);
			pstm.setInt(2, type);
			rs = pstm.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return cnt;
	}

	public synchronized String getLetterSubject(int letterobj) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String String = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT subject FROM letter WHERE item_object_id=?");
			pstm.setInt(1, letterobj);
			rs = pstm.executeQuery();
			if (rs.next()) {
				String = rs.getString(1);
			}

		} catch (SQLException e) {
			_log.warning("could not check existing getLetterSubject:" + e.getMessage());
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return String;
	}

	public synchronized int writeLetter(int code, String dTime, String sender,
			String receiver, int templateId, String subject, String content) {
		Connection con = null;
		PreparedStatement pstm1 = null;
		ResultSet rs = null;
		PreparedStatement pstm2 = null;
		int itemObjectId = 0;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();

			pstm1 = con
					.prepareStatement(" SELECT Max(item_object_id)+1 as cnt FROM letter ORDER BY item_object_id ");
			rs = pstm1.executeQuery();
			if (rs.next()) {
				itemObjectId = rs.getInt("cnt");
			}

			pstm2 = con
					.prepareStatement("INSERT INTO letter SET item_object_id=?, code=?, sender=?, receiver=?, date=?, template_id=?, subject=?, content=?, isCheck=?");
			pstm2.setInt(1, itemObjectId);
			pstm2.setInt(2, code);
			pstm2.setString(3, sender);
			pstm2.setString(4, receiver);
			pstm2.setString(5, dTime);
			pstm2.setInt(6, templateId);
			pstm2.setString(7, subject);
			pstm2.setString(8, content);

			if (sender.equalsIgnoreCase(receiver)) {
				pstm2.setInt(9, 1);
			} else {
				pstm2.setInt(9, 0);
			}

			pstm2.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm1);
			SQLUtil.close(pstm2);
			SQLUtil.close(con);
		}
		return itemObjectId;
	}

	public synchronized void writeLetter(int itemObjectId, int code,
			String sender, String receiver, String date, int templateId,
			byte[] subject, byte[] content) {
		Connection con = null;
		PreparedStatement pstm1 = null;
		ResultSet rs = null;
		PreparedStatement pstm2 = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm1 = con
					.prepareStatement("SELECT * FROM letter ORDER BY item_object_id");
			rs = pstm1.executeQuery();
			pstm2 = con
					.prepareStatement("INSERT INTO letter SET item_object_id=?, code=?, sender=?, receiver=?, date=?, template_id=?, subject=?, content=?");
			pstm2.setInt(1, itemObjectId);
			pstm2.setInt(2, code);
			pstm2.setString(3, sender);
			pstm2.setString(4, receiver);
			pstm2.setString(5, date);
			pstm2.setInt(6, templateId);
			pstm2.setBytes(7, subject);
			pstm2.setBytes(8, content);
			pstm2.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm1);
			SQLUtil.close(pstm2);
			SQLUtil.close(con);
		}
	}

	public synchronized void deleteLetter(int itemObjectId) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM letter WHERE item_object_id=?");
			pstm.setInt(1, itemObjectId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public synchronized void SaveLetter(int id, int letterType) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE letter SET template_id = ? WHERE item_object_id=?");
			pstm.setInt(1, letterType);
			pstm.setInt(2, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public synchronized void CheckLetter(int id) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE letter SET isCheck = 1 WHERE item_object_id=?");
			pstm.setInt(1, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}


	public synchronized boolean CheckNoReadLetter(String name) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM letter WHERE isCheck=0 AND receiver=?");
			pstm.setString(1, name);
			rs = pstm.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}
}
