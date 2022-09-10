package l1j.server.GameSystem.Boss;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.GameSystem.Boss.NewBossSpawnTable.BossTemp;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.RotationNotice.RotationNoticeTable;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.IdFactory;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1MobGroupSpawn;
import l1j.server.server.model.L1NpcDeleteTimer;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.templates.L1Npc;
import manager.LinAllManager;

public class BossTimeController implements Runnable {
	private static Logger _log = Logger.getLogger(BossTimeController.class.getName());

	private static BossTimeController _instance;

	private Random rnd = new Random(System.nanoTime());
	private int _time = 0;
	private int _timeM = 0;
	private Date day = new Date(System.currentTimeMillis());

	public static BossTimeController getInstance() {
		if (_instance == null)
			_instance = new BossTimeController();
		return _instance;
	}

	ArrayList<BossTemp> bosslist = null;

	private boolean isNow = false;

	public BossTimeController() {
		GeneralThreadPool.getInstance().execute(this);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				BossChack();
			} catch (Exception e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
	}

	private void BossChack() {

		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);
		
		bosslist = NewBossSpawnTable.getInstance().getlist();
		for (BossTemp temp : bosslist) {
			boolean isDay = false;
			for (int i : temp.Day) {
				if (i == day.getDay()) {
					isDay = true;
					break;
				}
			}
			if (!isDay) {
				continue;
			}
			
			if(temp.isSpawn){
				boolean ck = false;
				for (int Minute_temp : temp.SpawnMinute) {
					if(minute == Minute_temp){
						ck = true;
					}
				}
				if(ck)continue;
				else temp.isSpawn = false;
			}
			
			
			for (int i = 0; i < temp.SpawnHour.length; i++) {
				if(hour == temp.SpawnHour[i]-1 && minute==temp.SpawnMinute[i]){
						
					/**
					switch(temp.npcid){
						case 100338:
							if(!BossAlive.getInstance().is�����ں�){
								BossAlive.getInstance().is�����ں� = true;
								BossAlive.getInstance().set�����ں�Ÿ��(RealTimeClock.getInstance().getRealTime().getSeconds()+3600);
								int time = (int)(BossAlive.getInstance().ezTime - RealTimeClock.getInstance().getRealTime().getSeconds());
								L1World.getInstance().broadcastPacketToAll(new S_EventNotice(1, time, 3600, true));

							}
						break;
						case 100420:
							if(!BossAlive.getInstance().is�����){
								BossAlive.getInstance().is����� = true;
								BossAlive.getInstance().set�����Ÿ��(RealTimeClock.getInstance().getRealTime().getSeconds()+3600);
								int time = (int)(BossAlive.getInstance().sdTime - RealTimeClock.getInstance().getRealTime().getSeconds());
								L1World.getInstance().broadcastPacketToAll(new S_EventNotice(2, time, 3600, true));
							}
							break;
					}
					*/
				}
				if(hour == temp.SpawnHour[i] && minute == temp.SpawnMinute[i]){
					temp.isSpawn = true;
					GeneralThreadPool.getInstance().execute(new BossThread(temp));
				}	
			}
		}
	}
	
	
	/**���� ���� ó�� */
	class BossThread implements Runnable {
		BossTemp temp;
		public BossThread(BossTemp _temp){
			temp = _temp;
		}
		
		public void run(){
			try{
				int rndtime = rnd.nextInt(temp.rndTime) + 1;
				if(rndtime > 0)Thread.sleep(rndtime * 60 * 1000);
				StoreBoss(temp.npcid, temp.SpawnLoc, temp.rndLoc, temp.Groupid, temp.isYn, temp.isMent, temp.Ment, temp.RMent,temp.DeleteTime);
			}catch(Exception e){}
		}
	}
	
	

	public void StoreBoss(int npcid, int[] Loc, int rndXY, int groupid, boolean isYN, boolean isMent, String ment, String Rment, int deleteTime) {
		try {
			L1Npc template = NpcTable.getInstance().getTemplate(npcid);
			if (template == null) {
				_log.warning("Boss mob data for id:" + npcid + " missing in npc table");
				System.out.println("�������� ��Ʈ�ѷ� ���� npcid " + npcid + "�� �������� �ʽ��ϴ�.");
				return;
			}
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(npcid);
			
			npc.setId(IdFactory.getInstance().nextId());

			L1Location loc = new L1Location(Loc[0], Loc[1], Loc[2]);
			 if (rndXY != 0) {
				loc.randomLocation(rndXY, false);
			}
			 
			/** ���Ǿ� ��ġ ���� ���� */
			npc.setLocation(loc);
			npc.getLocation().forward(5);

			/** Ư�� ���Ǿ� ������Ʈ �߰� �κ� *//*
			/** ����� 4���׼� �߰��ؼ� ������ ����������� �ѷ��ش� *//*
			/** Appear �׼� ������ Chat ��Ʈ �� */
			boolean Appear = false; String Chat = null;
			switch (npcid){	
				/** �ְ��� ���Ͻ� �� ���� */
				case 910021: Appear = true; Chat = "\\fY$27422"; break;
				/** �ҽ��� �þ� ���� */
				case 910028: Appear = true; Chat = "\\fY$27432"; break;
				/** ������ �����̾� ���� */
				case 910036: Appear = true; Chat = "\\fY$27442"; break;
				/** ������ ���� �ε� ���� */
				case 910042: Appear = true; Chat = "\\fY$27452"; break;
				/** ������ ��� ���� */
				case 910050: Appear = true; Chat = "\\fY$27462"; break;
				/** �һ��� �ӹ� �ε� ���� */
				case 910056: Appear = true; Chat = "\\fY$27472"; break;
				/** ��Ȥ�� ���̸��� ���� */
				case 910062: Appear = true; Chat = "\\fY$27482"; break;
				/** ����� ����Ʈ �ߵ� ���� */
				case 910069: Appear = true; Chat = "\\fY$27649"; break;
				/** �Ҹ��� ��ġ ���� */
				case 910075: Appear = true; Chat = "\\fY$27659"; break;
				/** ������ ��״��� ���� */
				case 910014: Appear = true; Chat = "\\fY$27669"; break;
				/** ������ ����� ��ȥ */
				//case 7310078: Appear = true; Chat = "\\fY$27679"; break;
				/** ������ ����� ��ȥ */
				//case 7310079: Appear = true; Chat = "\\fY$27689"; break;
				/** ��� �׸� ���� */
				case 910088: Appear = true; Chat = "\\fY$27699"; break;
			}
			
			/** ��Ʈ�� �ִ� �׼Ǹ� ���� �׼� ó�� �ϵ��� ���� */
			for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(npc)) {
				npc.onPerceive(pc);
				pc.sendPackets(new S_NPCPack(npc), true);
				if(Appear) pc.sendPackets(new S_DoActionGFX(npc.getId(), ActionCodes.ACTION_Appear), true);
				if(Chat != null) pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, Chat), true);
			}
			
			/** �˶� �����̼Ǹ��� �� �����̼� ���� ���� */
			switch(npcid){
				case 100338:
					/** �����̼� ��Ʈ�ѷ� ���� �ð����� �������� �������� üũ �� */
					long Time = System.currentTimeMillis();
					/** 30�� ���� ���� */
					long EndTime = System.currentTimeMillis() + (1000 * 1800);
					RotationNoticeTable.getInstance().UpdateNotice(1, new Timestamp(Time), new Timestamp(EndTime));
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						if (pc instanceof L1RobotInstance) continue;
						pc.�˸�����(pc, true);
					}
					break;
					
				case 100420:{
					long Time2 = System.currentTimeMillis();
					/** 30�� ���� ���� */
					long EndTime2 = System.currentTimeMillis() + (1000 * 1800);
					RotationNoticeTable.getInstance().UpdateNotice(2, new Timestamp(Time2), new Timestamp(EndTime2));
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						if (pc instanceof L1RobotInstance) continue;
						pc.�˸�����(pc, true);
					}
					break;
				}
				case 45752:
					long Time3 = System.currentTimeMillis();
					/** 30�� ���� ���� */
					long EndTime3 = System.currentTimeMillis() + (1000 * 1800);
					RotationNoticeTable.getInstance().UpdateNotice(11, new Timestamp(Time3), new Timestamp(EndTime3));
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						if (pc instanceof L1RobotInstance) continue;
						pc.�˸�����(pc, true);
					}
					break;
			}
			
			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			if (groupid > 0)
				L1MobGroupSpawn.getInstance().doSpawn(npc, groupid, true, false);
			
			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);
			
			BossAlive.getInstance().BossSpawn(loc.getMapId());
			
			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE);

			if (isMent) {
				L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "\\aH[�����˸�] :"+ ment.toString()));
				L1World.getInstance().broadcastServerMessage("\\aH[���� �˸�] :"+ ment.toString());
				L1World.getInstance().broadcastServerMessage("\\aJ[�ֿ� ������] :"+ Rment.toString());
			}
			if (isYN) {

			}

			if (0 < deleteTime) {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, deleteTime * 1000);
				timer.begin();
			} else {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, 3600 * 1000);
				timer.begin();
			}
			
			LinAllManager.getInstance().BossAppend(npc.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}