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

package l1j.server.server.templates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Bookmarks;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;

public class L1BookMark {
	private static Logger _log = Logger.getLogger(L1BookMark.class.getName());

	private int _charId;

	private int _id;

	private String _name;

	private int _locX;

	private int _locY;

	private short _mapId;

	private int _randomX;

	private int _randomY;

	private int _fast;

	public int get_fast() {
		return _fast;
	}

	public void set_fast(int _fast) {
		this._fast = _fast;
	}

	public L1BookMark() {
	}

	public static void deleteCharBookmark(L1PcInstance player) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM character_teleport WHERE char_id=?");
			pstm.setInt(1, player.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void deleteBookmark(L1PcInstance player, String s) {
		L1BookMark book = player.getBookMark(s);
		if (book != null) {
			Connection con = null;
			PreparedStatement pstm = null;
			try {

				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con
						.prepareStatement("DELETE FROM character_teleport WHERE id=?");
				pstm.setInt(1, book.getId());
				pstm.executeUpdate();
				player.removeBookMark(book);
			} catch (SQLException e) {
				_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
			} finally {
				SQLUtil.close(pstm);
				SQLUtil.close(con);
			}
		}
	}

	public static void addBookmark(L1PcInstance pc, int x, int y, int m,
			String s) {
		int size = pc.getBookMarkSize();
		if (size >= pc.getBookmarkMax()) {
			pc.sendPackets(new S_SystemMessage("これ以上記憶ウィンドウに追加できません。"));
			return;
		}

		if (pc.getBookMark(s) == null) {
			L1BookMark bookmark = new L1BookMark();
			bookmark.setId(ObjectIdFactory.getInstance().nextId());
			bookmark.setCharId(pc.getId());
			bookmark.setName(s);
			bookmark.setLocX(x);
			bookmark.setLocY(y);
			bookmark.setMapId((short) m);

			insertBookmark(bookmark);

			pc.addBookMark(bookmark);
			pc.sendPackets(new S_Bookmarks(s, bookmark.getLocX(), bookmark
					.getLocY(), bookmark.getMapId(), bookmark.getId()));
		} else {
			pc.sendPackets(new S_SystemMessage("同じ名前がすでに存在します。"));
		}
	}

	public static void addBookmark(L1PcInstance pc, String s) {
		if (!pc.getMap().isMarkable()) {
			pc.sendPackets(new S_SystemMessage("ここを記憶することはできません。"));
			return;
		}

		int size = pc.getBookMarkSize();
		if (size >= pc.getBookmarkMax()) {
			return;
		}

		if (pc.getBookMark(s) == null) {
			L1BookMark bookmark = new L1BookMark();
			bookmark.setId(ObjectIdFactory.getInstance().nextId());
			bookmark.setCharId(pc.getId());
			bookmark.setName(s);
			bookmark.setLocX(pc.getX());
			bookmark.setLocY(pc.getY());
			bookmark.setMapId(pc.getMapId());

			insertBookmark(bookmark);

			pc.addBookMark(bookmark);
			pc.sendPackets(new S_Bookmarks(s, bookmark.getLocX(), bookmark
					.getLocY(), bookmark.getMapId(), bookmark.getId()));
		} else {
			pc.sendPackets(new S_SystemMessage("同じ名前がすでに存在します。"));
		}
	}

	public static void insertBookmark(L1BookMark bookmark) {
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO character_teleport SET id = ?, char_id = ?, name = ?, locx = ?, locy = ?, mapid = ?, randomX = ?, randomY = ?");
			pstm.setInt(1, bookmark.getId());
			pstm.setInt(2, bookmark.getCharId());
			pstm.setString(3, bookmark.getName());
			pstm.setInt(4, bookmark.getLocX());
			pstm.setInt(5, bookmark.getLocY());
			pstm.setInt(6, bookmark.getMapId());
			pstm.setInt(7, 0);
			pstm.setInt(8, 0);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void insertResetIDBookmark(L1BookMark bookmark) {
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO character_teleport SET id = ?, char_id = ?, name = ?, locx = ?, locy = ?, mapid = ?, randomX = ?, randomY = ?, fast = ?");
			pstm.setInt(1, ObjectIdFactory.getInstance().nextId());
			pstm.setInt(2, bookmark.getCharId());
			pstm.setString(3, bookmark.getName());
			pstm.setInt(4, bookmark.getLocX());
			pstm.setInt(5, bookmark.getLocY());
			pstm.setInt(6, bookmark.getMapId());
			pstm.setInt(7, 0);
			pstm.setInt(8, 0);
			pstm.setInt(9, bookmark.get_fast());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static final String[] BALL_OF_FAINT_MEMORY = { "\\fS[制作] \\fT ルームティススナッパー",
			"\\fS[制作] \\fTディオ(宝石)", "\\fS[制作] \\fTギラン製作村", "\\fS[制作] \\fTケント製作村",
			"\\fS[制作] \\fTエリクサー,カーガム", "\\fS[制作] \\fT蘇生の村", "\\fR[精霊オブ] \\fUPC象牙,オマーンPC",
			"\\fR[ペット村] \\fUペット雑貨、分譲士", "\\fR[ペット村] \\fU初歩ペット狩り場", "\\fR[ペット村] \\fUペット経験治癒復旧",
			"\\fR[ギラン倉庫] \\fUノーディム","\\fR[ギラン掲示板] \\fUギゲ", "\\fR[雑貨賞] \\fUメイヤー", "\\fR[料理商人] \\fUラポンズ",
			"\\fR[火気ダンジョン] \\fUブレッド", "\\fR[アクセ開放] \\fUスナップパー", "\\fR[パンドラ] \\fUパンドラ証書",
			"\\fR[魔法商人] \\fUエルリオン,インセンス", "\\fR[ラダン入場] \\fUファイラ",
			"\\fR[受賞した商人] \\fU羽商人", "\\fY[ボス] \\fVカルゴ", "\\fY[ボス] \\fV大黒長老",
			"\\fY[ボス] \\fVドレイク", "\\fY[ボス] \\fV風竜の守護者", "\\fY[ボス] \\fV水竜の守護者",
			"\\fY[ボス] \\fV画、フェニックス、イフ", "\\fY[ボス] \\fV大きな足のマヨ", "\\fY[ボス] \\fVデスナイト",
			"\\fY[ボス] \\fVデーモン"};
	public static final int[] BOFM_X = {33451,33454,33466,33066,33396,32867,32780,32759,32761,32757,33430,33415,33452,33431
			,34069,34050,32638,33457,33448,32755,34035,33367,33396,34261,32740,33742,34258,32745,32763};
	public static final int[] BOFM_Y = {32804,32778,32778,32752,32836,32806,32826,32444,32455,32468,32817,32808,32821,32789
			,32270,32261,32951,32810,32818,32818,32874,32257,32343,32838,32821,32282,32424,32736,32842};
	// 32637 33327
	public static final String[] BALL_OF_MEMORY = { "\\fV[MYSTERY] \\fW氷湖の中央",
			"\\fV[MYSTERY] \\fW氷湖の外殻", "\\fV[MYSTERY] \\fW氷の崖",
			"\\fV[MYSTERY] \\fWアデン小船", "\\fV[MYSTERY] \\fWハイネ境界タワー",
			"\\fV[MYSTERY] \\fW用の谷の崖", "\\fV[MYSTERY] \\fW隠されたカメ島",
			"\\fV[MYSTERY]", "\\fW精神と時間の島"};
	public static final int[] MEMORY_X = { 34002, 33994, 34212, 34194, 33503, 33466,
			33343, 32555 };
	public static final int[] MEMORY_Y = { 32329, 32332, 32343, 33135, 33505, 32312,
			33163, 32979 };

	public static synchronized boolean 아이템기억(L1PcInstance pc, int type) {
		for (int i = 0; i < type; i++) {
			String name = "";
			if (type == 8) {
				name = BALL_OF_MEMORY[i];
			} else {
				name = BALL_OF_FAINT_MEMORY[i];
			}
			if (pc.getBookMark(name) != null) {
				pc.sendPackets(new S_SystemMessage(
						"追加する記憶の中に、同名の記憶があります。 > " + name),
						true);
				return false;
			}
		}
		for (int i = 0; i < type; i++) {
			L1BookMark bookmark = new L1BookMark();
			bookmark.setId(ObjectIdFactory.getInstance().nextId());
			bookmark.setCharId(pc.getId());
			String name = "";
			if (type == 8) {
				name = BALL_OF_MEMORY[i];
				bookmark.setName(name);
				bookmark.setLocX(MEMORY_X[i]);
				bookmark.setLocY(MEMORY_Y[i]);
			} else {
				name = BALL_OF_FAINT_MEMORY[i];
				bookmark.setName(name);
				bookmark.setLocX(BOFM_X[i]);
				bookmark.setLocY(BOFM_Y[i]);
			}
			// 말하는섬
			if (type != 8 && (i == 6))
				bookmark.setMapId((short) 622);
			else if (type != 8 && (i == 16))
				bookmark.setMapId((short) 0);
			else if (type != 8 && i == 19)
				bookmark.setMapId((short) 622);
			else if (type != 8 && (i == 21 || i == 22))
				bookmark.setMapId((short) 15430);
			else if (type != 8 && i == 23)
				bookmark.setMapId((short) 15410);
			else if (type != 8 && i == 24)
				bookmark.setMapId((short) 63);
			else if (type != 8 && i == 25)
				bookmark.setMapId((short) 15440);
			else if (type != 8 && i == 26)
				bookmark.setMapId((short) 15420);
			else if (type != 8 &&(i == 27))
				bookmark.setMapId((short) 812);
			else if (type != 8 &&(i == 28))
				bookmark.setMapId((short) 77);
			else
				bookmark.setMapId((short) 4);

			Connection con = null;
			PreparedStatement pstm = null;
			try {
				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con
						.prepareStatement("INSERT INTO character_teleport SET id = ?, char_id = ?, name = ?, locx = ?, locy = ?, mapid = ?, randomX = ?, randomY = ?");
				pstm.setInt(1, bookmark.getId());
				pstm.setInt(2, bookmark.getCharId());
				pstm.setString(3, bookmark.getName());
				pstm.setInt(4, bookmark.getLocX());
				pstm.setInt(5, bookmark.getLocY());
				pstm.setInt(6, bookmark.getMapId());
				pstm.setInt(7, 0);
				pstm.setInt(8, 0);
				pstm.executeUpdate();
			} catch (SQLException e) {
				_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
			} finally {
				SQLUtil.close(pstm);
				SQLUtil.close(con);
			}

			pc.addBookMark(bookmark);
			pc.sendPackets(new S_Bookmarks(name, bookmark.getLocX(), bookmark
					.getLocY(), bookmark.getMapId(), bookmark.getId()));
		}
		return true;
	}

	/*
	 * public static synchronized void ItemaddBookmark(L1PcInstance pc, int
	 * itemid) { int size = pc.getBookMarkSize(); if (size <= 0) { return; }
	 *
	 * int count = 0; for(L1BookMark book : pc.getBookMark()){ if(count >= 60)
	 * continue; Connection con = null; PreparedStatement pstm = null;
	 *
	 * try { con = L1DatabaseFactory.getInstance().getConnection(); pstm =
	 * con.prepareStatement(
	 * "INSERT INTO item_bookmark SET book_id = ?, item_id=?, name = ?, locx = ?, locy = ?, mapid = ?"
	 * ); pstm.setInt(1, book.getId()); pstm.setInt(2, itemid);
	 * pstm.setString(3, book.getName()); pstm.setInt(4, book.getLocX());
	 * pstm.setInt(5, book.getLocY()); pstm.setInt(6, book.getMapId());
	 * pstm.executeUpdate(); count++; } catch (SQLException e) {
	 * _log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e); } finally {
	 * SQLUtil.close(pstm); SQLUtil.close(con); } } }
	 */

	public static synchronized void ItemaddBookmarkByTeleport(L1PcInstance pc, int itemid) {
		int size = pc.getBookMarkSize();
		if (size <= 0) {
			return;
		}

		FastTable<L1BookMark> list = new FastTable<L1BookMark>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_teleport WHERE char_id=? ORDER BY id ASC");
			pstm.setInt(1, pc.getId());
			rs = pstm.executeQuery();

			L1BookMark bookmark = null;
			while (rs.next()) {
				bookmark = new L1BookMark();
				bookmark.setId(rs.getInt("id"));
				bookmark.setCharId(rs.getInt("char_id"));
				bookmark.setName(rs.getString("name"));
				bookmark.setLocX(rs.getInt("locx"));
				bookmark.setLocY(rs.getInt("locy"));
				bookmark.setMapId(rs.getShort("mapid"));
				bookmark.setRandomX(rs.getShort("randomX"));
				bookmark.setRandomY(rs.getShort("randomY"));
				list.add(bookmark);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		int count = 0;
		for (L1BookMark book : list) {
			if (count >= pc.getBookmarkMax())
				continue;
			Connection con2 = null;
			PreparedStatement pstm2 = null;

			try {
				con2 = L1DatabaseFactory.getInstance().getConnection();
				pstm2 = con2.prepareStatement("INSERT INTO item_bookmark SET book_id = ?, item_id=?, name = ?, locx = ?, locy = ?, mapid = ?");
				pstm2.setInt(1, book.getId());
				pstm2.setInt(2, itemid);
				pstm2.setString(3, book.getName());
				pstm2.setInt(4, book.getLocX());
				pstm2.setInt(5, book.getLocY());
				pstm2.setInt(6, book.getMapId());
				pstm2.executeUpdate();
				count++;
			} catch (SQLException e) {
				_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
			} finally {
				SQLUtil.close(pstm2);
				SQLUtil.close(con2);
			}
		}
		list.clear();
		list = null;
	}

	@SuppressWarnings("finally")
	public static synchronized int ItemBookmarkChehck(int itemid) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select * from item_bookmark WHERE item_id = ?");
			pstm.setInt(1, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
			return count;
		}
	}

	public static synchronized boolean ItemBookmarkLoad(L1PcInstance pc,
			int itemid) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select * from item_bookmark WHERE item_id = ?");
			pstm.setInt(1, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				try {
					String name = rs.getString("name");
					if (pc.getBookMark(name) != null) {
						pc.sendPackets(new S_SystemMessage("追加する記憶の中に、同名の記憶があります。 > " + name), true);
						return false;
					}
				} catch (Exception e) {
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select * from item_bookmark WHERE item_id = ? ORDER BY id ASC");
			pstm.setInt(1, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				try {
					int id = ObjectIdFactory.getInstance().nextId();
					L1BookMark bookmark = new L1BookMark();
					bookmark.setId(id);
					bookmark.setCharId(pc.getId());
					bookmark.setName(rs.getString("name"));
					bookmark.setLocX(rs.getInt("locx"));
					bookmark.setLocY(rs.getInt("locy"));
					bookmark.setMapId(rs.getShort("mapid"));
					insertBookmark(bookmark);
					pc.addBookMark(bookmark);
					pc.sendPackets(new S_Bookmarks(bookmark.getName(), bookmark
							.getLocX(), bookmark.getLocY(),
							bookmark.getMapId(), bookmark.getId()));
				} catch (Exception e) {
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM item_bookmark WHERE item_id=?");
			pstm.setInt(1, itemid);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, "ブックマークの削除でエラーが発生しました。", e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return true;
	}

	public int getId() {
		return _id;
	}

	public void setId(int i) {
		_id = i;
	}

	public int getCharId() {
		return _charId;
	}

	public void setCharId(int i) {
		_charId = i;
	}

	public String getName() {
		return _name;
	}

	public void setName(String s) {
		_name = s;
	}

	public int getLocX() {
		return _locX;
	}

	public void setLocX(int i) {
		_locX = i;
	}

	public int getLocY() {
		return _locY;
	}

	public void setLocY(int i) {
		_locY = i;
	}

	public short getMapId() {
		return _mapId;
	}

	public void setMapId(short i) {
		_mapId = i;
	}

	public int getRandomX() {
		return _randomX;
	}

	public void setRandomX(int i) {
		_randomX = i;
	}

	public int getRandomY() {
		return _randomY;
	}

	public void setRandomY(int i) {
		_randomY = i;
	}
}