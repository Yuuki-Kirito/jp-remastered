package l1j.server.server.TimeController;

import java.util.Calendar;

import l1j.server.Config;
import l1j.server.server.GiftBoxController;
import l1j.server.server.datatables.NpcShopSpawnTable;
import l1j.server.server.datatables.NpcShopTable;
import l1j.server.server.model.L1World;

public class ���ڻ���Controller extends Thread {
	
		private static ���ڻ���Controller _instance;

		private boolean _���ڻ���Start;
		public boolean get���ڻ���Start() {
			return _���ڻ���Start;
		}
		public void set���ڻ���Start(boolean ���ڻ���) {
			_���ڻ���Start = ���ڻ���;
		}

		public boolean isGmOpen = false;
		
		public static ���ڻ���Controller getInstance() {
			if(_instance == null) {
				_instance = new ���ڻ���Controller();
			}
			return _instance;
		}
		
		@Override
			public void run() {
		
					while (true) {
						try	{
						if(isOpen()){
						Thread.sleep(1000);
						Spawn1(); 
						Thread.sleep(120000);
						End();
					}
				} catch(Exception e){
					e.printStackTrace();
				} finally {
					try{
						Thread.sleep(1000L);
					}catch(Exception e){
						e.printStackTrace();
				}
				}
				}
			}
			
			 private void Spawn1(){
				 try{
					 NpcShopTable.reloding();
					 NpcShopSpawnTable.getInstance().shopNameReload();
					 NpcShopTable.getInstance().relodingac();
					 L1World.getInstance().broadcastServerMessage("\\aD[���� ���� �˸�]: ��� ���� ��ǰ�� ���� �Ǿ����ϴ�");
					 if(Config.Big_Praseant == true) {
						 GiftBoxController.getInstance().isGmOpen = true;
					 }
				 }catch(Exception e2){
						e2.printStackTrace();
				 }
				}
			 
			 private boolean isOpen() {
				  Calendar calender = Calendar.getInstance();
				  int hour, minute;
				  hour = calender.get(Calendar.HOUR_OF_DAY);
				  minute = calender.get(Calendar.MINUTE);	 
			   if ((hour == 1 && minute == 1)
			    || (hour == 2 && minute == 1)
				|| (hour == 3 && minute == 1)
				|| (hour == 4 && minute == 1)
				|| (hour == 5 && minute == 1)
				|| (hour == 6 && minute == 1)
				|| (hour == 7 && minute == 1)
				|| (hour == 8 && minute == 1)
				|| (hour == 9 && minute == 1)
				|| (hour == 10 && minute == 1)
				|| (hour == 11 && minute == 1)
				|| (hour == 12 && minute == 1)
				|| (hour == 13 && minute == 1)
				|| (hour == 14 && minute == 1)
				|| (hour == 15 && minute == 1)
				|| (hour == 16 && minute == 1)
				|| (hour == 17 && minute == 1)
				|| (hour == 18 && minute == 1)
				|| (hour == 19 && minute == 1)
				|| (hour == 20 && minute == 1)
				|| (hour == 21 && minute == 1)
				|| (hour == 22 && minute == 1)
				|| (hour == 23 && minute == 1)
				|| (hour == 0 && minute == 1)
				|| (hour == 1 && minute == 31)
			    || (hour == 2 && minute == 31)
				|| (hour == 3 && minute == 31)
				|| (hour == 4 && minute == 31)
				|| (hour == 5 && minute == 31)
				|| (hour == 6 && minute == 31)
				|| (hour == 7 && minute == 31)
				|| (hour == 8 && minute == 31)
				|| (hour == 9 && minute == 31)
				|| (hour == 10 && minute == 31)
				|| (hour == 11 && minute == 31)
				|| (hour == 12 && minute == 31)
				|| (hour == 13 && minute == 31)
				|| (hour == 14 && minute == 31)
				|| (hour == 15 && minute == 31)
				|| (hour == 16 && minute == 31)
				|| (hour == 17 && minute == 31)
				|| (hour == 18 && minute == 31)
				|| (hour == 19 && minute == 31)
				|| (hour == 20 && minute == 31)
				|| (hour == 21 && minute == 31)
				|| (hour == 22 && minute == 31)
				|| (hour == 23 && minute == 31)
				|| (hour == 0 && minute == 31)) {
				  return true;
				  }
				  return false;
				 }
			 
			 /** ���� **/
			 public void End() {
				 set���ڻ���Start(false);
			 }
}