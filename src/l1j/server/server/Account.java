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
package l1j.server.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Base64;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.AttendanceTable;
import l1j.server.server.utils.SQLUtil;
import manager.LinAllManagerInfoThread;
import server.LineageClient;

public class Account {
	/** account name */
	private String _name;
	/** Accessor IP address */
	private String _ip;
	/** Password (encrypted) */
	private String _password;

	/** Kirkbyburn */
	private String _CharPassword;
	private byte[] _waitpacket = null;
	private boolean _iscpwok = false;
	
	
	private boolean _is_changed_slot = false;

	/** Last access date */
	private Timestamp _lastActive;

	public Timestamp _lastQuit;
	/** Access level (is it GM?) */
	private int _accessLevel;
	/** connecter host name */
	private String _host;
	/** Van presence (True == forbidden) */
	private boolean _banned;
	/** Account Valid or Not (True == Valid) */
	private boolean _isValid = false;
	/** Character Slot (Primordial Jade Chain) */
	private int _charslot;
	/** warehouse password */
	private int _GamePassword;
	/** account time */
	private int _AccountTime;
	/** Account Time Reservation Value */
	private int _AccountTimeRead;
	/** Automatic prevention non-input count **/
	public int _account_auto_check_count;
	/** Dragon Raid Buff Time **/
	public Timestamp _dragon_raid_buff;

	public Timestamp _Buff_HPMP;
	public Timestamp _Buff_DMG;
	public Timestamp _Buff_REDUC;
	public Timestamp _Buff_MAGIC;
	public Timestamp _Buff_STUN;
	public Timestamp _Buff_STR;
	public Timestamp _Buff_DEX;
	public Timestamp _Buff_INT;
	public Timestamp _Buff_WIS;
	public Timestamp _Buff_HOLD;
	public Timestamp _Buff_PCroom;

	/** Did you receive the Red Knights supply event? **/
	public boolean RedKnightEventItem = false;
	/** for message log */
	private static Logger _log = Logger.getLogger(Account.class.getName());

	public Account() {
	}

	/**
	 * encrypt the password.
	 * 
	 * @param rawPassword
	 *            password
	 * @return String
	 * @throws NoSuchAlgorithmException
	 *             When encryption algorithms are not available
	 * @throws UnsupportedEncodingException
	 *             When encoding is not supported
	 */
	private static String encodePassword(final String rawPassword)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] buf = rawPassword.getBytes("UTF-8");
		buf = MessageDigest.getInstance("SHA").digest(buf);

		return Base64.encodeBytes(buf);
	}

	/**
	 * Create a new account
	 * 
	 * @param name
	 *            account name
	 * @param rawPassword
	 *            password
	 * @param ip
	 *            Accessor IP address
	 * @param host
	 *            connecter host name
	 * @return Account
	 */
	public static Account create(final String name, final String rawPassword, final String ip, final String host) {
		Calendar cal = Calendar.getInstance();
		int hour = Calendar.HOUR;
		int minute = Calendar.MINUTE;
		/** 0 morning , 1 afternoon * */
		String morning_afternoon = "afternoon";
		if (cal.get(Calendar.AM_PM) == 0) {
			morning_afternoon = "afternoon";
		}
		Connection con = null;
		PreparedStatement pstm = null;
		try {

			Account account = new Account();
			account._name = name;
			account._password = rawPassword;
			account._ip = ip;
			account._host = host;
			account._banned = false;
			account._lastActive = new Timestamp(System.currentTimeMillis());
			account._quize = null;
			account._attendanceHome = new byte[AttendanceTable.getInstance().getHomeSize()];
			account._attendancePcHome = new byte[AttendanceTable.getInstance().getPcSize()];
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr;
			if (Config.ACCOUNT_PASSWORD) {
				sqlstr = "INSERT INTO accounts SET login=?,password=password(?),lastactive=?,access_level=?,ip=?,host=?,banned=?,charslot=?,quize=?,gamepassword=?,point_time=?,Point_time_ready=?,CharPassword=?, data_home=?, data_pc=?";
			} else {
				sqlstr = "INSERT INTO accounts SET login=?,password=?,lastactive=?,access_level=?,ip=?,host=?,banned=?,charslot=?,quize=?,gamepassword=?,point_time=?,Point_time_ready=?,CharPassword=?, data_home=?, data_pc=?";
			}
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, account._name);
			pstm.setString(2, account._password);
			pstm.setTimestamp(3, account._lastActive);
			pstm.setInt(4, 0);
			pstm.setString(5, account._ip);
			pstm.setString(6, account._host);
			pstm.setInt(7, account._banned ? 1 : 0);
			pstm.setInt(8, 6);
			pstm.setString(9, account._quize);

			pstm.setInt(10, 0);
			pstm.setInt(11, 0);
			pstm.setInt(12, 0);
			pstm.setString(13, null);
			pstm.setBytes(14, account._attendanceHome);
			pstm.setBytes(15, account._attendancePcHome);
			pstm.executeUpdate();
			
			System.out.println(""+ morning_afternoon + " " + cal.get(hour) + "시" + cal.get(minute) + "분" + "   ■ 신규 계정: ["+name+"] 생성완료■");
			 LinAllManagerInfoThread.AccountCount += 1;
			
			return account;
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return null;
	}

	/**
	 * DBGet account information from
	 * 
	 * @param name
	 *            account name
	 * @return Account
	 */

	public static Account load(final String name) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		Account account = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "SELECT * FROM accounts WHERE login=? LIMIT 1";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, name);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				return null;
			}
			account = new Account();
			account._name = rs.getString("login");
			account._password = rs.getString("password");
			account._lastActive = rs.getTimestamp("lastactive");
			account._accessLevel = rs.getInt("access_level");
			account._ip = rs.getString("ip");
			account._host = rs.getString("host");
			account._banned = rs.getInt("banned") == 0 ? false : true;
			account._charslot = rs.getInt("charslot");
			account._quize = rs.getString("quize");
			account._GamePassword = (rs.getInt("gamepassword"));
			
			account._attendanceHomeTime = rs.getInt("attendanceHomeTime");
			account._isAttendanceHome = rs.getBoolean("attendanceHome");
			account._attendancePcHomeTime = rs.getInt("attendancePcHomeTime");
			account._isAttendancePcHome = rs.getBoolean("attendancePcHome");
			account._attendanceHome = rs.getBytes("data_home");
			account._attendancePcHome = rs.getBytes("data_pc");
			account._attendanceDate = rs.getDate("attendanceDate");
			account._attendancePcDate = rs.getDate("attendancePcDate");
			
			int pt = rs.getInt("point_time");
			int ptr = rs.getInt("point_time_ready");
			if (pt <= 0 && ptr > 0) {
				account._AccountTime = ptr;
				account._AccountTimeRead = 0;
				updatePointAccountReady(name, ptr, 0);
			} else {
				account._AccountTime = pt;
				account._AccountTimeRead = ptr;
			}
			account.girantime = (rs.getInt("GDGTime"));
			account.giranday = (rs.getTimestamp("GDGDay"));
			account.ivorytime = (rs.getInt("IDGTime"));
			account.ivoryday = (rs.getTimestamp("IDGDay"));
			account.ravatime = (rs.getInt("RDGTime"));
			account.ravaday = (rs.getTimestamp("RDGDay"));
			account.dragontime = (rs.getInt("DDGTime"));
			account.yongdongday = (rs.getTimestamp("DDGDay"));

			account.suspiciousprisontime = (rs.getInt("SDGTime"));
			account.suspiciousprisonday = (rs.getTimestamp("SDGDay"));
			account.huntingeventtime = (rs.getInt("SETime"));
			account.huntingeventday = (rs.getTimestamp("SEDay"));
			account.ivoryyaheetime = (rs.getInt("IYDGTime"));
			account.ivoryyaheeday = (rs.getTimestamp("IYDGDay"));
			account.suspiciousagarvalleytime = (rs.getInt("CDGTime"));
			account.suspiciousagarvalleyday = (rs.getTimestamp("CDGDay"));
			account.tam_point = (rs.getInt("Tam_Point"));
			account._dragon_raid_buff = (rs.getTimestamp("DragonRaid_Buff"));
			account.RedKnightEventItem = rs.getInt("RedKnight_event") == 0 ? false : true;
			account.halloweentime = (rs.getInt("HWTime"));
			account.hallowindday = (rs.getTimestamp("HWDay"));
			account.solotowntime = (rs.getInt("STTime"));
			account.solotownday = (rs.getTimestamp("STDay"));
			account.Shop_open_count = (rs.getInt("Shop_open_count"));

			account.mongseomtime = (rs.getInt("MSTime"));
			account.mongseonday = (rs.getTimestamp("MSDay"));

			account.rubbertime = (rs.getInt("GOTime"));
			account.rubberday = (rs.getTimestamp("GODay"));
			account.randomtime = (rs.getInt("RBTime"));
			account.ladenday = (rs.getTimestamp("RBDay"));
			account.fishingtime = (rs.getInt("FSTime"));
			account.fishingday = (rs.getTimestamp("FSDay"));
			account.forgetislandtime = (rs.getInt("ISTime"));
			account.forgetislandday = (rs.getTimestamp("ISDay"));
			account.blaclbattleshiptime = (rs.getInt("BSTime"));
			account.blackbattleshipday = (rs.getTimestamp("BSDay"));
			account.trainingtime = (rs.getInt("SRTime"));
			account.waterlilyday = (rs.getTimestamp("SRDay"));
			account.rubberpctime = (rs.getInt("GPTime"));
			account.rubberpcday = (rs.getTimestamp("GPDay"));
			account.time = (rs.getInt("BLTime"));
			account.budangday = (rs.getTimestamp("BLDay"));
			account.atubatime = (rs.getInt("AOTime"));
			account.atoubaday = (rs.getTimestamp("AODay"));
			account.evatime = (rs.getInt("EKTime"));
			account.evaday = (rs.getTimestamp("EKDay"));

			// account.ainhasad = (rs.getInt("Ainhasad"));

			account._CharPassword = (rs.getString("CharPassword"));

			account.Ncoin_point = (rs.getInt("Ncoin_Point"));

			account._Buff_HPMP = (rs.getTimestamp("BUFF_HPMP_Time"));
			account._Buff_DMG = (rs.getTimestamp("BUFF_DMG_Time"));
			account._Buff_REDUC = (rs.getTimestamp("BUFF_REDUC_Time"));
			account._Buff_MAGIC = (rs.getTimestamp("BUFF_MAGIC_Time"));
			account._Buff_STUN = (rs.getTimestamp("BUFF_STUN_Time"));
			account._Buff_HOLD = (rs.getTimestamp("BUFF_HOLD_Time"));
			account._Buff_PCroom = (rs.getTimestamp("BUFF_PCROOM_Time"));
			account._Buff_STR = (rs.getTimestamp("BUFF_STR_Time"));
			account._Buff_DEX = (rs.getTimestamp("BUFF_DEX_Time"));
			account._Buff_INT = (rs.getTimestamp("BUFF_INT_Time"));
			account._Buff_WIS = (rs.getTimestamp("BUFF_WIS_Time"));
			account._lastQuit = (rs.getTimestamp("LastQuit"));

			_log.fine("account exists");

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		return account;
	}

	/**
	 * DB에 Last Access Date Update
	 * 
	 * @param account
	 *            account name
	 */
	public static void updateLastActive(final Account account, String sip) {
		Connection con = null;
		PreparedStatement pstm = null;
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET lastactive=?, ip=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setTimestamp(1, ts);
			pstm.setString(2, sip);
			pstm.setString(3, account.getName());
			pstm.executeUpdate();
			account._lastActive = ts;
			_log.fine("update lastactive for " + account.getName());
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/*
	 * public void final Account account { Timestamp account end date = _lastQuit;
* Timestamp current date = new Timestamp(System.currentTimeMillis());
*
* long account last time = 0; long Current DateTime = Current Date.getTime(); long time difference = 0; if (account end date !=
* null) { last account end time = account end date.getTime(); } else { return; } time difference = current date time -
* Account last closing time; int number of probe additions = (int) (time difference / (60000 * 12)); if (Number of additions < 1) { return; }
*
* Apply the number of explorations (account, account last time, number of additions); // System.out.println("Number of search additions: "+ number of search additions);
* //disconnection detection level }
	 */

	public void applying_the_search_value(final Account account, long end_date, int number_of_additions) {
		Connection con = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		PreparedStatement pstm2 = null;
		Timestamp tamtime = null;
		long sysTime = System.currentTimeMillis();
		int tamcount = Config.Tam_Count;

		int char_objid = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM `characters` WHERE account_name = ?"); // キャラクター
																								// テーブルから
																								// 君主のみ
																								// 選んで
			pstm.setString(1, account.getName());
			rs = pstm.executeQuery();
			while (rs.next()) {
				tamtime = rs.getTimestamp("TamEndTime");
				char_objid = rs.getInt("objid");
				if (tamtime != null) {
					if (sysTime <= tamtime.getTime()) {
						// 今までも適用されている場合。
						int additional_times = number_of_additions;
						tam_point += additional_times * tamcount;
						updateTam();
						// System.out.println("現在も適用されている乗車に追加回数：+探偵回数);
					} else {
						// if(Tam_wait_count(char_objid)!=0){
						int day = Nexttam(char_objid);
						if (day != 0) {
							Timestamp deleteTime = null;
							deleteTime = new Timestamp(sysTime + (86400000 * (long) day) + 10000);// 7일
							con2 = L1DatabaseFactory.getInstance().getConnection();
							pstm2 = con2.prepareStatement(
									"UPDATE `characters` SET TamEndTime=? WHERE account_name = ? AND objid = ?"); // キャラクター
																													// テーブルから
																													// 君主のみ
																													// 選んで
							pstm2.setTimestamp(1, deleteTime);
							pstm2.setString(2, account.getName());
							pstm2.setInt(3, char_objid);
							pstm2.executeUpdate();
							tamdel(char_objid);
							tamtime = deleteTime;
						}
						// }
						if (end_date <= tamtime.getTime()) {
							// 現在ではありませんが、終了後に適用される場合。
							int additional_times = (int) ((tamtime.getTime() - end_date) / (60000 * 12));
							tam_point += additional_times * tamcount;
							updateTam();
						} else {
							// System.out.println("終了日以前に乗車時間も終了しました。");
						}

						/**/
					}
				} else {
					// System.out.println("no tom time");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int Nexttam(int objectId) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int day = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT day FROM `tam` WHERE objid = ? order by id asc limit 1"); 
																											
																											
																											
			pstm.setInt(1, objectId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				day = rs.getInt("Day");
			}
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return day;
	}

	public void tamdel(int objectId) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("delete from Tam where objid = ? order by id asc limit 1");
			pstm.setInt(1, objectId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateLastQuit(final Account account) {
		Connection con = null;
		PreparedStatement pstm = null;
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET lastQuit=?  WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setTimestamp(1, ts);
			pstm.setString(2, account.getName());
			pstm.executeUpdate();

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * Web password related;
	 * 
	 * @param account
	 *            account name
	 */
	public static void updateWebPwd(String AccountName, String pwd) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr;
			if (Config.ACCOUNT_PASSWORD) {
				sqlstr = "UPDATE accounts SET password=password(?) WHERE login = ?";
			} else {
				sqlstr = "UPDATE accounts SET password=? WHERE login = ?";
			}
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, pwd);
			pstm.setString(2, AccountName);
			pstm.executeUpdate();
			_log.fine("update lastactive for " + AccountName);
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * 2차비번 저관련임;
	 * 
	 * @param account
	 *            계정명
	 */
	public void UpdateCharPassword(String pwd) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET CharPassword=? WHERE login=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, pwd);
			pstm.setString(2, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * Count the number of characters in the account
	 * 
	 * @return result number of characters
	 */
	public int countCharacters() {
		int result = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "SELECT count(*) as cnt FROM characters WHERE account_name=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, _name);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return result;
	}

	public static void ban(final String account) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET banned=1 WHERE login=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, account);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void updateQuize(final Account account) {
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET quize=? WHERE login=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, account.getquize());
			pstm.setString(2, account.getName());
			pstm.executeUpdate();
			account._quize = account.getquize();
			_log.fine("update quize for " + account.getName());
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * Compare the entered password with the password stored in the DB
	 * 
	 * @param rawPassword password
	 * @return boolean
	 */
	public boolean validatePassword(String accountName, final String rawPassword) {
		try {
			if (Config.ACCOUNT_PASSWORD) {
				_isValid = (_password.equals(encodePassword(rawPassword)) || checkPassword(accountName, _password, rawPassword));
			} else {
				_isValid = (_password.equals(rawPassword) || checkPassword(accountName, _password, rawPassword));
			}
			if (_isValid) {
				_password = null; // 認証が成功した場合、パスワードを破棄する。
			}
			return _isValid;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		return false;
	}

	/**
	 * Is it a valid account
	 * 
	 * @return boolean
	 */
	public boolean isValid() {
		return _isValid;
	}

	/**
	 * GM Is it an account
	 * 
	 * @return boolean
	 */
	public boolean isGameMaster() {
		return 0 < _accessLevel;
	}

	public String getName() {
		return _name;
	}
	
	public String getPassword() {
		return _password;
	}

	public String getIp() {
		return _ip;
	}

	public Timestamp getLastActive() {
		return _lastActive;
	}

	public int getAccessLevel() {
		return _accessLevel;
	}

	public String getHost() {
		return _host;
	}

	public boolean isBanned() {
		return _banned;
	}

	/**
	 * take the quiz.
	 * 
	 * @return String
	 */
	private String _quize;

	public String getquize() {
		return _quize;
	}

	public void setquize(String s) {
		_quize = s;
	}

	public void setcpwok(boolean f) {
		_iscpwok = f;
	}

	public boolean iscpwok() {
		return _iscpwok;
	}

	public byte[] getwaitpacket() {
		return _waitpacket;
	}

	public void setwaitpacket(byte[] s) {
		_waitpacket = s;
	}

	public String getCPW() {
		return _CharPassword;
	}

	public void setCPW(String s) {
		_CharPassword = s;
	}

	public int getCharSlot() {
		return _charslot;
	}

	public Timestamp getBuff_HPMP() {
		return _Buff_HPMP;
	}

	public void setBuff_HPMP(Timestamp ts) {
		_Buff_HPMP = ts;
	}

	public Timestamp getBuff_DMG() {
		return _Buff_DMG;
	}

	public void setBuff_DMG(Timestamp ts) {
		_Buff_DMG = ts;
	}

	public Timestamp getBuff_REDUC() {
		return _Buff_REDUC;
	}

	public void setBuff_REDUC(Timestamp ts) {
		_Buff_REDUC = ts;
	}

	public Timestamp getBuff_MAGIC() {
		return _Buff_MAGIC;
	}

	public void setBuff_MAGIC(Timestamp ts) {
		_Buff_MAGIC = ts;
	}

	public Timestamp getBuff_STUN() {
		return _Buff_STUN;
	}

	public void setBuff_STUN(Timestamp ts) {
		_Buff_STUN = ts;
	}

	public Timestamp getBuff_HOLD() {
		return _Buff_HOLD;
	}

	public void setBuff_HOLD(Timestamp ts) {
		_Buff_HOLD = ts;
	}

	public Timestamp getBuff_PCroom() {
		return _Buff_PCroom;
	}

	public void setBuff_PCroom(Timestamp ts) {
		_Buff_PCroom = ts;
	}
	
	public Timestamp getBuff_STR() {
		return _Buff_STR;
	}

	public void setBuff_STR(Timestamp ts) {
		_Buff_STR = ts;
	}
	
	public Timestamp getBuff_DEX() {
		return _Buff_DEX;
	}

	public void setBuff_DEX(Timestamp ts) {
		_Buff_DEX = ts;
	}
	
	public Timestamp getBuff_INT() {
		return _Buff_INT;
	}

	public void setBuff_INT(Timestamp ts) {
		_Buff_INT = ts;
	}
	
	public Timestamp getBuff_WIS() {
		return _Buff_WIS;
	}

	public void setBuff_WIS(Timestamp ts) {
		_Buff_WIS = ts;
	}

	public Timestamp getDragonRaid() {
		return _dragon_raid_buff;
	}

	public void setDragonRaid(Timestamp ts) {
		_dragon_raid_buff = ts;
	}

	/**
	 * character slot settings
	 * 
	 * @return boolean
	 */
	public void setCharSlot(LineageClient client, int i) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET charslot=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, i);
			pstm.setString(2, client.getAccount().getName());
			pstm.executeUpdate();
			client.getAccount()._charslot = i;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static boolean checkLoginIP(String ip) {
		int num = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT count(ip) as cnt FROM accounts WHERE ip=? ");

			pstm.setString(1, ip);
			rs = pstm.executeQuery();

			if (rs.next())
				num = rs.getInt("cnt");

			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);

			if (num < 2) {
				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con.prepareStatement("SELECT count(host) as cnt FROM accounts WHERE host=? ");

				pstm.setString(1, ip);
				rs = pstm.executeQuery();

				if (rs.next())
					num = rs.getInt("cnt");
			}

			// 同じIPで作成されたアカウントが5つ未満の場合
			if (num < 5)
				return false;
			else
				return true;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	// Web連携のためのメソッドを追加 - By わんわん
	public static boolean checkPassword(String accountName, String _pwd, String rawPassword) {
		String _inputPwd = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			if (Config.ACCOUNT_PASSWORD) {
				pstm = con.prepareStatement("SELECT password(?) as pwd ");
			} else {
				pstm = con.prepareStatement("SELECT ? as pwd ");
			}
			pstm.setString(1, rawPassword);// これはエラーです。
			rs = pstm.executeQuery();
			if (rs.next()) {
				_inputPwd = rs.getString("pwd");
			}
			/*
			 * System.out.println(_inputPwd); System.out.println(rawPassword);
			 * System.out.println(_pwd);
			 */
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
			if (_pwd.equals(_inputPwd)) { // 同じなら
				return true;
			} else
				return false;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	/**
	 * warehouse password
	 * 
	 * @return boolean
	 */
	public static void setGamePassword(LineageClient client, int pass) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET gamepassword=? WHERE login =?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, pass);
			pstm.setString(2, client.getAccount().getName());
			pstm.executeUpdate();
			client.getAccount()._GamePassword = pass;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int getGamePassword() {
		return _GamePassword;
	}

	/**
	 * Stores the remaining points payment time when logging out;
	 * 
	 * @param account
	 *            account name
	 */
	public static void updatePointAccount(String AccountName, long time) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET point_time=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setLong(1, time);
			pstm.setString(2, AccountName);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void updatePointAccountReady(String AccountName, int time, int ready) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET point_time=?, point_time_ready=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, time);
			pstm.setInt(2, ready);
			pstm.setString(3, AccountName);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/*
	 * public void Ainupdate() { Connection con = null; PreparedStatement pstm =
	 * null; try { con = L1DatabaseFactory.getInstance().getConnection(); String
	 * sqlstr = "UPDATE accounts SET  Ainhasad=? WHERE login = ?"; pstm =
	 * con.prepareStatement(sqlstr); pstm.setInt(1, ainhasad); pstm.setString(2,
	 * _name); pstm.executeUpdate(); } catch (Exception e) { _log.log(Level.SEVERE,
	 * e.getLocalizedMessage(), e); } finally { SQLUtil.close(pstm);
	 * SQLUtil.close(con); } }
	 */

	public void updateDGTime() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET GDGTime=?, GDGDay=?,IDGTime=?,IDGDay=?, RDGTime=?,RDGDay=?, DDGTime=?, DDGDay =?, SDGTime=?, SDGDay=?, SETime=?, SEDay=?, IYDGTime=?, IYDGDay=?, CDGTime=?, CDGDay=?, HWTime=?, HWDay=?, STTime=?, STDay=?, MSTime=?, MSDay=? , GOTime=?, GODay=?, RBTime=?, RBDay=?, FSTime=?, FSDay=?, ISTime=?, ISDay=?, BSTime=?, BSDay=?, SRTime=?, SRDay=?, GPTime=?, GPDay=?, BLTime=?, BLDay=?, AOTime=?, AODay=?, EKTime=?, EKDay=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, girantime);
			pstm.setTimestamp(2, giranday);
			pstm.setInt(3, ivorytime);
			pstm.setTimestamp(4, ivoryday);
			pstm.setInt(5, ravatime);
			pstm.setTimestamp(6, ravaday);
			pstm.setInt(7, dragontime);
			pstm.setTimestamp(8, yongdongday);
			pstm.setInt(9, suspiciousprisontime);
			pstm.setTimestamp(10, suspiciousprisonday);
			pstm.setInt(11, huntingeventtime);
			pstm.setTimestamp(12, huntingeventday);
			pstm.setInt(13, ivoryyaheetime);
			pstm.setTimestamp(14, ivoryyaheeday);
			pstm.setInt(15, suspiciousagarvalleytime);
			pstm.setTimestamp(16, suspiciousagarvalleyday);
			pstm.setInt(17, halloweentime);
			pstm.setTimestamp(18, hallowindday);
			pstm.setInt(19, solotowntime);
			pstm.setTimestamp(20, solotownday);

			pstm.setInt(21, mongseomtime);
			pstm.setTimestamp(22, mongseonday);

			pstm.setInt(23, rubbertime);
			pstm.setTimestamp(24, rubberday);

			pstm.setInt(25, randomtime);
			pstm.setTimestamp(26, ladenday);

			pstm.setInt(27, fishingtime);
			pstm.setTimestamp(28, fishingday);

			pstm.setInt(29, forgetislandtime);
			pstm.setTimestamp(30, forgetislandday);

			pstm.setInt(31, blaclbattleshiptime);
			pstm.setTimestamp(32, blackbattleshipday);
			
			pstm.setInt(33, trainingtime);
			pstm.setTimestamp(34, waterlilyday);
			
			pstm.setInt(35, rubberpctime);
			pstm.setTimestamp(36, rubberpcday);
			
			pstm.setInt(37, time);
			pstm.setTimestamp(38, budangday);
			
			pstm.setInt(39, atubatime);
			pstm.setTimestamp(40, atoubaday);
			
			pstm.setInt(41, evatime);
			pstm.setTimestamp(42, evaday);

			pstm.setString(43, _name);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateTam() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET Tam_Point=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, tam_point);
			pstm.setString(2, _name);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateNcoin() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET Ncoin_Point=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, Ncoin_point);
			pstm.setString(2, _name);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	public void updateAttendanceTime() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE accounts SET attendanceHomeTime = ?, attendancePcHomeTime = ? WHERE login = ?");
			pstm.setInt(1, getAttendanceHomeTime());
			pstm.setInt(2, getAttendancePcHomeTime());
			pstm.setString(3, _name);
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateShopOpenCount() {
		Shop_open_count++;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET Shop_open_count=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, Shop_open_count);
			pstm.setString(2, _name);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void resetShopOpenCount() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET Shop_open_count=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, 0);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateDragonRaidBuff() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET DragonRaid_Buff=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setTimestamp(1, _dragon_raid_buff);
			pstm.setString(2, _name);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updatePC_room() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET BUFF_PCROOM_Time=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setTimestamp(1, _Buff_PCroom);
			pstm.setString(2, _name);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateBUFF() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET Buff_HPMP_Time=?,Buff_DMG_Time=?,Buff_Reduc_Time=?,Buff_Magic_Time=?,Buff_Stun_Time=?,Buff_Hold_Time=?,Buff_Str_Time=?,Buff_dex_Time=?,Buff_int_Time=?,Buff_wis_Time=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setTimestamp(1, _Buff_HPMP);
			pstm.setTimestamp(2, _Buff_DMG);
			pstm.setTimestamp(3, _Buff_REDUC);
			pstm.setTimestamp(4, _Buff_MAGIC);
			pstm.setTimestamp(5, _Buff_STUN);
			pstm.setTimestamp(6, _Buff_HOLD);
			pstm.setTimestamp(7, _Buff_STR);
			pstm.setTimestamp(8, _Buff_DEX);
			pstm.setTimestamp(9, _Buff_INT);
			pstm.setTimestamp(10, _Buff_WIS);
			pstm.setString(11, _name);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateRedKnightEvent() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			RedKnightEventItem = true;
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET RedKnight_event=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, 1);
			pstm.setString(2, _name);
			pstm.executeUpdate();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int getAccountTime() {
		return _AccountTime;
	}

	public int getAccountTimeReady() {
		return _AccountTimeRead;
	}

	public int girantime;
	public int ivorytime;
	public int ivoryyaheetime;
	public int ravatime;
	public int dctime;
	public int suspiciousprisontime;
	public int huntingeventtime;
	public int suspiciousagarvalleytime;
	public int halloweentime;
	public int solotowntime;
	public int mongseomtime;
	public int rubbertime;
	public int dragontime;
	public int randomtime;
	public int fishingtime;
	public int forgetislandtime;
	public int trainingtime;
	public int blaclbattleshiptime;
	public int rubberpctime;

	public Timestamp giranday;
	public Timestamp ivoryday;
	public Timestamp ivoryyaheeday;
	public Timestamp ravaday;
	public Timestamp dcday;
	public Timestamp suspiciousprisonday;
	public Timestamp huntingeventday;
	public Timestamp suspiciousagarvalleyday;
	public Timestamp hallowindday;
	public Timestamp solotownday;
	public Timestamp rubberday;
	public Timestamp yongdongday;

	public Timestamp mongseonday;
	public Timestamp ladenday;
	public Timestamp fishingday;
	public Timestamp forgetislandday;
	public Timestamp waterlilyday;
	public Timestamp blackbattleshipday;
	public Timestamp rubberpcday;
	/**Remastered Dungeon Time Addition*/
	public int time;
	public Timestamp budangday;
	public int evatime;
	public Timestamp evaday;
	public int atubatime;
	public Timestamp atoubaday;
	/**Remastered Dungeon Time Addition*/

	// public int ainhasad;

	public int tam_point;
	public int Ncoin_point;
	public int Shop_open_count;

	/**
	 * Get attendance check.
	 * 
	 * @return
	 */
	/** Save attendance check time */
	public void saveAttendanceTime(Account account) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE accounts SET attendanceHomeTime = ?,attendancePcHomeTime = ? WHERE login = ?");
			pstm.setInt(1, account.getAttendanceHomeTime());
			pstm.setInt(2, account.getAttendancePcHomeTime());
			pstm.setString(3, account.getName());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/** Status of whether general attendance check has been completed */
	private boolean _isAttendanceHome;

	public void setAttendanceHome(boolean flag) {
		_isAttendanceHome = flag;
	}

	public boolean isAttendanceHome() {
		return _isAttendanceHome;
	}

	private int _attendanceHomeTime;

	public void addAttendanceHomeTime(int time) {
		_attendanceHomeTime += time;
		if (_attendanceHomeTime >= 60) {
			_attendanceHomeTime = 60;
		}
	}

	public void setAttendanceHomeTime(int time) {
		_attendanceHomeTime = time;
	}

	public int getAttendanceHomeTime() {
		return _attendanceHomeTime;
	}

	public byte[] _attendanceHome;

	public byte[] getAttendanceHomeBytes() {
		return _attendanceHome;
	}

	public void setAttendanceHomeBytes(byte[] b) {
		_attendanceHome = b;
	}

	/** PC room attendance check **/
	private boolean _isAttendancePcHome;

	public void setAttendancePcHome(boolean flag) {
		_isAttendancePcHome = flag;
	}

	public boolean isAttendancePcHome() {
		return _isAttendancePcHome;
	}

	private int _attendancePcHomeTime;

	public void addAttendancePcHomeTime(int time) {
		_attendancePcHomeTime += time;
		if (_attendancePcHomeTime >= 60) {
			_attendancePcHomeTime = 60;
		}
	}

	public void setAttendancePcHomeTime(int time) {
		_attendancePcHomeTime = time;
	}

	public int getAttendancePcHomeTime() {
		return _attendancePcHomeTime;
	}

	public byte[] _attendancePcHome;

	public byte[] getAttendancePcBytes() {
		return _attendancePcHome;
	}

	public void setAttendancePcBytes(byte[] b) {
		_attendancePcHome = b;
	}

	public void storeAttendBytes() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET data_home=?, data_pc=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setBytes(1, this._attendanceHome);
			pstm.setBytes(2, this._attendancePcHome);
			pstm.setString(3, this.getName());
			pstm.execute();
			_log.fine("update lastactive for " + this.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void storeAttendCheck() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET attendanceHome=?, attendancePcHome=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, this._isAttendanceHome ? 1 : 0);
			pstm.setInt(2, this._isAttendancePcHome ? 1 : 0);
			pstm.setString(3, this.getName());
			pstm.execute();
			_log.fine("update lastactive for " + this.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public boolean isAllHomeAttendCheck() {
		for (byte b : _attendanceHome) {
			if (b != 0x02) {
				return false;
			}
		}

		return true;
	}

	public boolean isAllPcAttendCheck() {
		for (byte b : _attendancePcHome) {
			if (b != 0x02) {
				return false;
			}
		}

		return true;
	}
	
	private Date _attendanceDate;

	public void setAttendanceDate(Date time) {
		_attendanceDate = time;
	}

	public Date getAttendanceDate() {
		return _attendanceDate;
	}

	private Date _attendancePcDate;

	public void setAttendancePcDate(Date time) {
		_attendancePcDate = time;
	}

	public Date getAttendancePcDate() {
		return _attendancePcDate;
	}

	public void updateAttendaceDate(){
		Connection con = null;
		PreparedStatement pstm = null;
		Calendar cal = Calendar.getInstance(Locale.JAPAN);	
		cal.set(Calendar.YEAR, 2016);
		long tim = cal.getTimeInMillis();
		java.sql.Date JAVANowtime = new java.sql.Date(tim);
		Date date = new Date(tim);
		JAVANowtime = new java.sql.Date(date.getTime());
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET attendanceDate=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setDate(1, JAVANowtime);
			pstm.setString(2, getName());
			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	public void updateAttendacePcDate(){
		Connection con = null;
		PreparedStatement pstm = null;
		Calendar cal = Calendar.getInstance(Locale.JAPAN);	
		cal.set(Calendar.YEAR, 2016);
		long tim = cal.getTimeInMillis();
		java.sql.Date JAVANowtime = new java.sql.Date(tim);
		Date date = new Date(tim);
		JAVANowtime = new java.sql.Date(date.getTime());
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE accounts SET attendancePcDate=? WHERE login = ?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setDate(1, JAVANowtime);
			pstm.setString(2, getName());
			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	
	public void is_changed_slot(boolean is_changed){
		_is_changed_slot = is_changed;
	}
	public boolean is_changed_slot(){
		return _is_changed_slot;
	}
}
