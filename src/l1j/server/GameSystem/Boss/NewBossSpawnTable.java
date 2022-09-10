package l1j.server.GameSystem.Boss;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.utils.SQLUtil;


public class NewBossSpawnTable {
	private static NewBossSpawnTable _instance;

	public static NewBossSpawnTable getInstance() {
		if (_instance == null) {
			_instance = new NewBossSpawnTable();
		}
		return _instance;
	}

	private static Logger _log = Logger.getLogger(NewBossSpawnTable.class.getName());

	private ArrayList<BossTemp> bosslist = new ArrayList<BossTemp>();

	private Random rnd = new Random(System.nanoTime());

	public ArrayList<BossTemp> getlist() {
		return bosslist;
	}
	
	public static void reload() {
		NewBossSpawnTable oldInstance = _instance;
		_instance = new NewBossSpawnTable();
		oldInstance.bosslist.clear();
	}
	
	

	private NewBossSpawnTable() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		FileOutputStream fos;
		BufferedOutputStream bos;
		
		try {
			
			
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM spawnlist_boss_new");
			rs = pstm.executeQuery();

			while (rs.next()) {
				BossTemp temp = new BossTemp();
				temp.npcid = rs.getInt("npcid");
				String text = rs.getString("info");
				temp.rndLoc = rs.getInt("rndXY");
				temp.Groupid = rs.getInt("groupid");
				temp.isYn = rs.getInt("is_yn") == 0 ? false : true;
				temp.isMent = rs.getInt("is_ment") == 0 ? false : true;
				temp.Ment = rs.getString("ment");
				temp.RMent = rs.getString("Rment");

				StringTokenizer s = new StringTokenizer(text, "\r\n");
				int number = 0;
				while (s.hasMoreElements()) {
					String temp2 = "";
					StringTokenizer values = new StringTokenizer(s.nextToken(), "��������: Ÿ�ӷ���������ǥ�����׷�YN�޼�����Ʈ");
					while (values.hasMoreElements()) {// ��������
						temp2 += values.nextToken();
					}
					// System.out.println("temp2 = " + temp2);
					if (number == 0) { // ���� ��ȭ����~~
						StringTokenizer Day = new StringTokenizer(temp2, ",");
						ArrayList<Integer> list = new ArrayList<Integer>();
						while (Day.hasMoreElements()) {
							String day = Day.nextToken();
							if (day.equalsIgnoreCase("��")) {
								list.add(new Integer(0));
							} else if (day.equalsIgnoreCase("��")) {
								list.add(new Integer(1));
							} else if (day.equalsIgnoreCase("ȭ")) {
								list.add(new Integer(2));
							} else if (day.equalsIgnoreCase("��")) {
								list.add(new Integer(3));
							} else if (day.equalsIgnoreCase("��")) {
								list.add(new Integer(4));
							} else if (day.equalsIgnoreCase("��")) {
								list.add(new Integer(5));
							} else if (day.equalsIgnoreCase("��")) {
								list.add(new Integer(6));
							}
						}
						temp.Day = new int[list.size()];
						for (int i = 0; i < list.size(); i++) {
							temp.Day[i] = list.get(i);
						}
					} else if (number == 1) { // �����ð�
						StringTokenizer mdata = new StringTokenizer(temp2, ",");
						ArrayList<Integer> Hourlist = new ArrayList<Integer>();
						ArrayList<Integer> Minutelist = new ArrayList<Integer>();
						while (mdata.hasMoreElements()) {
							String Times = mdata.nextToken();
							StringTokenizer Hours = new StringTokenizer(Times, "��");
							String Hour = Hours.nextToken();
							StringTokenizer Minutes = new StringTokenizer(Hours.nextToken(), "��");
							String Minute = Minutes.nextToken();
							Hourlist.add(Integer.parseInt(Hour.trim()));
							Minutelist.add(Integer.parseInt(Minute.trim()));
						}

						temp.SpawnHour = new int[Hourlist.size()];
						temp.SpawnMinute = new int[Hourlist.size()];
						for (int i = 0; i < Hourlist.size(); i++) {
							int Hour = Hourlist.get(i);
							int Minute = Minutelist.get(i);
							temp.SpawnHour[i] = Hour;
							temp.SpawnMinute[i] = Minute;
							 //System.out.println("Hour = " + Hour);
							 //System.out.println("Minute = " + Minute);
						}
						
					} else if (number == 2) { // ����Ÿ�� ��
						StringTokenizer mdata = new StringTokenizer(temp2, "��");
						temp.rndTime = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("�����ð� = " + temp.rndTime);
					} else if (number == 3) { // �����ð� ��
						StringTokenizer mdata = new StringTokenizer(temp2, "��");
						temp.DeleteTime = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("�����ð� �� = " + temp.DeleteTime);
					} else if (number == 4) { // ������ǥ
						StringTokenizer mdata = new StringTokenizer(temp2, ",");
						temp.SpawnLoc = new int[3];
						temp.SpawnLoc[0] = Integer.parseInt(mdata.nextToken().trim());
						temp.SpawnLoc[1] = Integer.parseInt(mdata.nextToken().trim());
						temp.SpawnLoc[2] = Integer.parseInt(mdata.nextToken().trim());
						// System.out.println("������ǥx = " + temp.SpawnLoc[0]);
						// System.out.println("������ǥy = " + temp.SpawnLoc[1]);
						// System.out.println("������ǥm = " + temp.SpawnLoc[2]);
					}

					number++;
				}
				bosslist.add(temp);
			}

			
			
			/*for (BossTemp temp : bosslist) {
				temp.NearTime = new int[temp.SpawnHour.length];
				for (int i = 0; i < temp.SpawnHour.length; i++) {
					if (temp.rndTime != 0) {
						int Hour = temp.SpawnHour[i];
						int rndtime = rnd.nextInt(temp.rndTime) + 1;
						int Minute = temp.SpawnMinute[i] + rndtime;
						if (Minute >= 120) {
							Hour += 2;
							Minute -= 120;
						} else if (Minute >= 60) {
							Hour++;
							Minute -= 60;
						}
						StringBuffer NewText = new StringBuffer();
						NewText.append(Hour);
						NewText.append(Minute < 10 ? "0" + Minute : Minute);
						temp.NearTime[i] = Integer.parseInt(NewText.toString().trim());
						// System.out.println("temp.NearTime[i] = " + temp.NearTime[i]);
					} else {
						StringBuffer NewText = new StringBuffer();
						NewText.append(temp.SpawnHour[i]);
						NewText.append(temp.SpawnMinute[i] < 10 ? "0" + temp.SpawnMinute[i] : temp.SpawnMinute[i]);
						temp.NearTime[i] = Integer.parseInt(NewText.toString().trim());
						// System.out.println("temp.NearTime[i] = " + temp.NearTime[i]);
						
					}
				}
			}*/
		
		} catch (SQLException e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/*
	 * 1~6 0 
	 * ��������: ��,ȭ,��,��,��,��,�� 
	 * ����Ÿ��: 2�� 00��, 5�� 30�� 
	 * ����Ÿ��: 0�� 
	 * ����Ÿ��: 3600�� 
	 * ������ǥ: 32726, 32832, 603 
	 * ��������: 0 
	 * �׷콺��: 0 
	 * YN�޼���: 1 
	 * ������Ʈ: 1
	 */
	public static class BossTemp {
		public int npcid;
		public boolean isSpawn;
		public int[] Day;
		public int[] SpawnHour;
		public int[] SpawnMinute;
		public int[] NearTime;
		public int rndTime;
		public int DeleteTime;
		public int[] SpawnLoc;
		public int rndLoc;
		public int Groupid;
		public boolean isYn;
		public boolean isMent;
		public String Ment;
		public String RMent;
		public int timeM;
	}
}