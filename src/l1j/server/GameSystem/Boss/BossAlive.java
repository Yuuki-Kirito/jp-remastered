package l1j.server.GameSystem.Boss;

import java.util.HashMap;

public class BossAlive {
	public static BossAlive ins;

	public static BossAlive getInstance(){
		if(ins==null)
			ins = new BossAlive();
		return ins;
	}
	//MapID , 1 Survive 2 Death
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
	//later make this a HashMap
	public boolean _is_ERZABE = false;
	public long ezTime = -1;
	public void set_erzabe_thyme(long s){
		ezTime = s;
	}
	public boolean is_BALROG = false;
	public long BLTime = -1;
	public void set_release_time(long s){
		BLTime = s;
	}
	public boolean is_SAND_WORM = false;
	public long sdTime = -1;
	public void set_sand_warm_time(long s){
		sdTime = s;
	}
}
