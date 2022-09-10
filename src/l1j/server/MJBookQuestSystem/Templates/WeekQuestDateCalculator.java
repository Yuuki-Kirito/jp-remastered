package l1j.server.MJBookQuestSystem.Templates;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.MJBookQuestSystem.Loader.WeekQuestLoader;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.SQLUtil;

/** �ְ� ����Ʈ �ð��� ����� Ŭ���� **/
public class WeekQuestDateCalculator {
	private static Logger _log = Logger.getLogger(WeekQuestDateCalculator.class.getName());
	
	private static WeekUpdator _updator = null;
	private static WeekQuestDateCalculator _instance;
	public static WeekQuestDateCalculator getInstance(){
		if(_instance == null)
			_instance = new WeekQuestDateCalculator();
		return _instance;
	}
	
	private Timestamp			_updateStamp;
	private ScheduledFuture<?> 	_future;
	private void setLastTime(){
		Connection con							= null;
		PreparedStatement pstm 					= null;
		try {
			con 	= L1DatabaseFactory.getInstance().getConnection();
			pstm 	= con.prepareStatement("insert into tb_weekquest_updateInfo set id=1, lastTime=? on duplicate key update lastTime=?");
			pstm.setTimestamp(1, _updateStamp);
			pstm.setTimestamp(2, _updateStamp);
			pstm.executeUpdate();
		}catch (Exception e){
			StringBuilder sb = new StringBuilder();
			sb.append("[ERROR - WeekQuestDateCalculator] setLastTime()...").append(_updateStamp).append(" write error. \r\n").append(e.getLocalizedMessage());
			_log.log(Level.SEVERE, sb.toString(), e);
			System.out.println(sb.toString());
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	private Date getLastTime(){
		Connection con						= null;
		PreparedStatement pstm 				= null;
		ResultSet rs 						= null;
		String column						= "";
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select * from tb_weekquest_updateInfo");
			rs = pstm.executeQuery();
			if(rs.next()){
				Timestamp ts = rs.getTimestamp("lastTime");
				return new Date(ts.getTime());
			}
		}catch (Exception e){
			StringBuilder sb = new StringBuilder();
			sb.append("[ERROR - WeekQuestDateCalculator] getLastTime()...").append(column).append(" read error. \r\n").append(e.getLocalizedMessage());
			_log.log(Level.SEVERE, sb.toString(), e);
			System.out.println(sb.toString());
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return null;
	}
	
	public void run(){
		long sleepTime = 0;
		long nowMillis = System.currentTimeMillis();
		
		// ���� ������Ʈ ������ �ҷ��´�.
		Date oldDate = getLastTime();
		
		// ���� ������Ʈ ������ ����.
		if(oldDate == null){												
			
			// ����� ������Ʈ�� �Ѵ�.
			setUpdate(nowMillis);
		}else{
			// ���� ������Ʈ �����κ��� ���� ������Ʈ ������ �ҷ��´�.
			Calendar cal = getNextWeekCalendar(oldDate.getTime());
			
			// ���� �ð��� ���� ������Ʈ �ð����� �� ũ�ٸ� ������Ʈ�� ���� �ʾҴ�.
			if(nowMillis >= cal.getTimeInMillis()){ 	

				// ���� ������Ʈ �ð����� �����Ѵ�.
				setUpdate(nowMillis);
		
				
			// ���� ������Ʈ �� ���� �ƴϴ�.
			}else{									
				// ���� ������Ʈ ������ �����Ѵ�.
				_updateStamp = new Timestamp(oldDate.getTime());
				
				// ���� ������Ʈ���� �� �ð��� ���ϰ� 
				sleepTime = cal.getTimeInMillis() - nowMillis;
				
				// ����.
				setNextUpdate(sleepTime);
			}
		}
	}
	
	private WeekQuestDateCalculator(){
		
	}
	
	/** ���� calendar�� ���� ������ ������ �ʿ� �ϼ��� ��ȯ�Ѵ�. **/
	private int getDayToNextWeek(Calendar cal){
		int week 		= cal.get(Calendar.DAY_OF_WEEK);// ������ ���Ѵ�.
		int nextWeek 	= 0;
		if(Config.WQ_UPDATE_TYPE == 0)	// ���� ���� Ÿ���̶��, 1�Ͼ� ��ȯ�Ѵ�.
			nextWeek = 1;
		else if(week >= Config.WQ_UPDATE_WEEK)
			nextWeek = (Config.WQ_UPDATE_WEEK + 7) - week;
		else
			nextWeek = Config.WQ_UPDATE_WEEK - week;
		return nextWeek;
	}
	
	/** �־��� �ð����κ��� ������ ������ calendar�� ��ȯ�Ѵ�. **/
	private Calendar getNextWeekCalendar(long sysmillis){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(new Date(sysmillis));
		
		Calendar nextCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		nextCal.setTime(cal.getTime());
		nextCal.add(Calendar.DATE, getDayToNextWeek(cal));
		nextCal.set(Calendar.HOUR_OF_DAY, Config.WQ_UPDATE_TIME);
		nextCal.set(Calendar.MINUTE, 0);
		nextCal.set(Calendar.SECOND, 0);
		return nextCal;
	}
	
	/** �ְ� ����Ʈ�� ���ŵǾ������� ��ȯ�Ѵ�. **/
	public boolean isUpdateWeekQuest(Timestamp ts){
		if(ts == null)
			return true;
		
		// timezone ���ÿ� ���� �Ҽ����� ms Ʋ������ ���� �ȵǴ� ���� ��Ȥ�߻�.. 1�� �̳� ������ ��� ���� ������.
		long time = Math.abs(ts.getTime() - _updateStamp.getTime());
		if(time <= 1000)
			return false;
		return true;
	}
	
	public Timestamp getUpdateStamp(){
		return _updateStamp;
	}
	
	/** ���� �ð����κ��� ������Ʈ�� �ǽ��Ѵ�. **/
	public void setUpdate(){
		setUpdate(System.currentTimeMillis());
	}
	
	/** �־��� �ð����κ��� ������Ʈ�� �ǽ��Ѵ�. **/
	public void setUpdate(long nowMillis){
		_updateStamp = new Timestamp(nowMillis);	// ���� �ð��� �����ϰ�
		setLastTime();								// DB�� �����Ѵ�.
		// ������ ���� ����.
		long sleepTime = getNextWeekCalendar(_updateStamp.getTime()).getTimeInMillis() - _updateStamp.getTime();
		setNextUpdate(sleepTime);		
	}
	
	/** ���� ������Ʈ�� ����ǵ��� �Ѵ�. **/
	private void setNextUpdate(long sleepTime){
		WeekQuestLoader.reload();
		// � ���������� ���ε� �� ���ɼ��� �ִٸ�, �ѹ��� ó���ϵ���,
		if(_updator == null)
			_updator = new WeekUpdator();
		
		_future = GeneralThreadPool.getInstance().schedule(_updator, sleepTime);
	}
	
	public synchronized void reloadTime(){
		if(_future != null){
			_future.cancel(true);
		}
		
		setUpdate(System.currentTimeMillis());
		L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "�ְ� ����Ʈ �� �⼮����Ʈ�� ���ŵǾ����ϴ�."), true);
	}
	
	/** �ֱ��� ������Ʈ�� ������ ���� **/
	class WeekUpdator implements Runnable{
		
		@Override
		public void run() {
			try{
				long now = System.currentTimeMillis();
				if(_updateStamp.getTime() > now){
					GeneralThreadPool.getInstance().schedule(this, _updateStamp.getTime() - now);
					return;
				}
				String s = "Week Quest Updates now time is " + now;
				System.out.println(s);
				_log.log(Level.SEVERE, s);
				
				WeekQuestDateCalculator.getInstance().setUpdate(now);
				Thread.sleep(500);
				L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "�ְ� ����Ʈ�� ���ŵǾ����ϴ�. �ɸ��͸� ������ϸ� �� ����Ʈ ����� �޾ƺ� �� �ֽ��ϴ�."), true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
