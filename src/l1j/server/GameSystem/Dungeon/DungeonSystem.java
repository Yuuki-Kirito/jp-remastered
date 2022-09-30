package l1j.server.GameSystem.Dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_EventNotice;

public class DungeonSystem {
	private final static Map<Integer, DungeonInfo> _List = new ConcurrentHashMap<Integer, DungeonInfo>();
	
	private static int _Counter = 0;
	
	private static DungeonSystem _instance;
	
	public static DungeonSystem getInstance() {
		if (_instance == null) {
			_instance = new DungeonSystem();
		}
		return _instance;
	}
	
	/** Finding empty spaces derived from the map  */
	public static int getDungeonInfo() {
		_Counter += 1;
		if(_Counter >= 1000)
			_Counter = 0;
		return _Counter;
	}
	
	/** Check the status of the current Infinity War entry status */
	public static void getDungeonInfo(int RoomNumber, DungeonInfo DungeonInfo) {
		if(!_List.containsKey(RoomNumber))
			_List.put(RoomNumber, DungeonInfo);
	}
	
	/** Check the status of the current Infinity War entry status */
	public static DungeonInfo isDungeonInfo(int RoomNumber) {
		if(_List.containsKey(RoomNumber))
			return _List.get(RoomNumber);
		return null;
	}
	
	public static boolean isDungeonInfoCheck(L1PcInstance Pc) {
        Iterator<Integer> it = new ArrayList<Integer>(_List.keySet()).iterator();
        DungeonInfo DungeonInfo;
        boolean DungeonInfoCheck = false;
        while (it.hasNext()) {
        	DungeonInfo = _List.get(it.next());
        	if(DungeonInfo.DungeonList.contains(DungeonInfo.isUser(Pc)))
        		DungeonInfoCheck = true;
        }
        return DungeonInfoCheck;
	}
	
	public static void isDungeonInfoPcCheck(L1PcInstance Pc) {
        Iterator<Integer> it = new ArrayList<Integer>(_List.keySet()).iterator();
        DungeonInfo DungeonInfo = null;
        while (it.hasNext()) {
        	DungeonInfo = _List.get(it.next());
        	if(DungeonInfo.DungeonList.contains(DungeonInfo.isUser(Pc))){
        		/** If it is a dungeon chapter, check it, delete the dungeon information, and let it all exit */
        		if(DungeonInfo.isDungeonLeadt() == Pc.getId() && !DungeonInfo.InPlaygame){
					for(L1PcInstance ListPc : DungeonInfo.isDungeonInfoUset()){
						ListPc.isDungeonTeleport(false);
						ListPc.sendPackets(new S_EventNotice(S_EventNotice.InDungeExit, 1, DungeonInfo.RoomNumber), true);
					}
					DungeonSystem.DungeonInfoRemove(DungeonInfo.RoomNumber);
        		}else if(!DungeonInfo.InPlaygame){
        			DungeonInfo.setUser(Pc);
        			Pc.isDungeonTeleport(false);
					Pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeExit, 1, DungeonInfo.RoomNumber), true);
					for(L1PcInstance ListPc : DungeonInfo.isDungeonInfoUset())
						ListPc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOpen, DungeonInfo, null), true);
        		}
        	}
        }
	}
	
	/** Check list information */
	public static Map<Integer, DungeonInfo> DungeonInfoList() {
		return _List;
	}

	/** Delete room information */
	public static void DungeonInfoRemove(int RoomNumber) {
		_List.remove(RoomNumber);
	}
}