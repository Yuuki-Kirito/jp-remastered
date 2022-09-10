package l1j.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import l1j.server.server.utils.LeakCheckedConnection;

/**
 * DB���� �׼����ϱ� ���� ���� �������̽��� ����
 */
public class L1DatabaseFactory {
	private static L1DatabaseFactory _instance = null;

	/** DB���� ������ ������ ��?  */
	private ComboPooledDataSource _source;

	/** �޼��� �α׿�.  */
	private static Logger _log = Logger.getLogger(L1DatabaseFactory.class.getName());

	/* DB �׼����� �ʿ��� ������ */
	/** DB���� ����̹�.  */
	private static String _driver;
	/** DB������ URL.  */
	private static String _url;     
	/** DB������ �����ϴ� ������.  */
	private static String _user;    
	/** DB������ �����ϴ� �н�����.  */
	private static String _password;

	/**
	 * @return L1DatabaseFactory
	 * @throws SQLException
	 */
	public static L1DatabaseFactory getInstance() throws SQLException {
		if(_instance == null) {
			synchronized (L1DatabaseFactory.class) {
				if(_instance  == null) {
					_instance = new L1DatabaseFactory();
				}
			}
		}
		
		return _instance ;
	}
	
	/**
	 * DB���� �׼����� �ʿ��� ���� ����
	 * 
	 * @param driver
	 *            DB���� ����̹�
	 * @param url
	 *            DB���� URL
	 * @param user
	 *            DB������ �����ϴ� ������
	 * @param password
	 *            DB������ �����ϴ� �н�����
	 */
	public static void setDatabaseSettings(final String driver,
			final String url, final String user, final String password) {
		_driver = driver;
		_url = url;
		_user = user;
		_password = password;
	}

	private L1DatabaseFactory() throws SQLException {
		try {
			// DatabaseFactory�� L2J�κ��� �Ϻθ� ������ ����
			_source = new ComboPooledDataSource();
			_source.setDriverClass(_driver);
			_source.setJdbcUrl(_url);
			_source.setUser(_user);
			_source.setPassword(_password);
			_source.setInitialPoolSize(10);
			_source.setMinPoolSize(10);
			_source.setMaxPoolSize(100);
			_source.setAcquireIncrement(5);
			_source.setAcquireRetryAttempts(30);
			_source.setAcquireRetryDelay(1000);
			_source.setIdleConnectionTestPeriod(60);
			_source.setPreferredTestQuery("SELECT 1");
			_source.setTestConnectionOnCheckin(true);
			_source.setTestConnectionOnCheckout(false);
			
			/* Test the connection */
			_source.getConnection(). close();
		} catch (SQLException x) {
			_log.fine("Database Connection FAILED");
			// rethrow the exception
			throw x;
		} catch (Exception e) {
			_log.fine("Database Connection FAILED");
			throw new SQLException("could not init DB connection:" + e);
		}
	}

	public void shutdown() {
		try {
			_source.close();
		} catch (Exception e) {
			_log.log(Level.INFO, "", e);
		}
		try {
			_source = null;
		} catch (Exception e) {
			_log.log(Level.INFO, "", e);
		}
	}

	/**
	 * DB������ ��, connection ������Ʈ�� �����ش�.
	 * 
	 * @return Connection connection ������Ʈ
	 * @throws SQLException
	 */
	public Connection getConnection() {
		Connection con = null;

		while (con == null) {
			try {
				con = _source.getConnection();
			} catch (SQLException e) {
				_log.warning("L1DatabaseFactory: getConnection() failed, trying again " + e);
			}
		}
		return Config.DETECT_DB_RESOURCE_LEAKS ?  LeakCheckedConnection.create(con) : con;
	}
}
