package l1j.server.GameSystem.Boss;

import java.util.HashMap;

public class BossAlive {
	public static BossAlive ins;

	public static BossAlive getInstance(){
		if(ins==null)
			ins = new BossAlive();
		return ins;
	}
	//MapID , 1���� 2����
	HashMap<Integer,Integer> isAlive = new HashMap<Integer,Integer>();
	
	public boolean isBossAlive(int mapid){
		boolean alive = false;
			if(isAlive.containsKey(mapid)){
				alive = true;
			}
		
		return alive;		
	}
	
	public void BossSpawn(int mapid){
		isAlive.put(mapid, 1);
	}
	public void BossDeath(int mapid){
		isAlive.remove(mapid);
	}
	//���߿� �̰� HashMap���� �����
	public boolean is�����ں� = false;
	public long ezTime = -1;
	public void set�����ں�Ÿ��(long s){
		ezTime = s;
	}
	public boolean is�߷� = false;
	public long BLTime = -1;
	public void set�߷�Ÿ��(long s){
		BLTime = s;
	}
	public boolean is����� = false;
	public long sdTime = -1;
	public void set�����Ÿ��(long s){
		sdTime = s;
	}
}
