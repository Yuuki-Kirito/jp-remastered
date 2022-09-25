package l1j.server.GameSystem.Valakas;

import static l1j.server.server.model.skill.L1SkillId.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1EffectSpawn;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1TownLocation;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.utils.L1SpawnUtil;

public class ValaRaid implements Runnable {

	private static Logger _log = Logger.getLogger(ValaRaid.class.getName());
	
	private int _map;
	
	private static Random _random = new Random(System.nanoTime());
	
	public int stage = 1;
	
	public static final int StageOne = 1;
	private static final int StageTwo = 2;
	private static final int StageThree = 3;
	private static final int StageFour = 4;
	private static final int StageFive = 5;
	private static final int StageSix = 6;
	private static final int StageStop = 7;
	
	
	private boolean Running = false;
	
	private boolean one_die = false;
	private boolean two_die = false;
	private boolean three_die = false;
	private L1MonsterInstance vala = null;
	//private int RealId = 0;
	
	private int sleep = 60;
	
	private int Time = 7200;

	public ValaRaid(int id){
		_map = id;
	}
	
	public void setReady(boolean flag){
		Running = flag;
	}
	
	public boolean isReady(){
		return Running;
	}


	
	public void AllShockStun(){
		int[] stunTimeArray = { 4500, 5000, 5500 };
		int rnd = _random.nextInt(stunTimeArray.length);
		int _shockStunDuration = stunTimeArray[rnd];
		for (L1PcInstance pc : PcStageCK()) {
		 if((pc.getX() >= 32753 && pc.getX() <= 32787) && (pc.getY() >= 32875 && pc.getY() <= 32910)) {
			L1EffectSpawn.getInstance().spawnEffect(81162, _shockStunDuration, pc.getX(), pc.getY(), pc.getMapId());
			pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_STUN, true));
			pc.getSkillEffectTimerSet().setSkillEffect(SHOCK_STUN, _shockStunDuration);
			pc.sendPackets(new S_SkillSound(pc.getId(), 4434)); // スタン
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 4434));
			}
		}
	}
	
	@Override
	public void run() {
		while(Running){
			try {
				TimeCheck();
				switch(stage){
				case StageOne:
					Thread.sleep(sleep * 1000);
					stage = StageTwo;
			        break;
				case StageTwo:
					if(Valakas_IsDie()) {
					//health();
						giveItem(5000063);
						stage = StageSix;
						break;
					}
					Thread.sleep(sleep * 1000);
					for (L1PcInstance pc : PcStageCK()){
						pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "オリム：みんな気をつけて、奴はすでに眠りから目覚めました。"));
					}
					Thread.sleep(5 * 1000);
					for (L1PcInstance pc : PcStageCK()){
						pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "オリム：周囲の炎…あなたを閉じ込めるために罠を置いたようです。"));
					}
					Thread.sleep(5 * 1000);
					for (L1PcInstance pc : PcStageCK()){
						pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,S_PacketBox.RED_MESSAGE, "ヴァラカス：クルルル…面倒な虫がやってきたな…", true));

					}
					Thread.sleep(5 * 1000);
					for (L1PcInstance pc : PcStageCK()){
						pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,S_PacketBox.RED_MESSAGE, "ヴァラカス：お前らもハルパスの拳属なのか…？ ", true));

					}
					// 黒い画面の雨の効果
					L1SpawnUtil.spawn2(32773, 32889, (short) _map, 3310030, 0, 1 * 1000, 3310030);
					L1SpawnUtil.spawn2(32773, 32889, (short) _map, 3310031, 0, 1 * 1000, 3310031);

					
					Thread.sleep(10 * 1000);
					for (L1PcInstance pc : PcStageCK()){
						pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,S_PacketBox.RED_MESSAGE, "ヴァラカス：気にしないだろう。"), true);

					}
					Thread.sleep(5 * 1000);
					//  目に見えヴァラカス出現
		
					L1SpawnUtil.spawn2(32773, 32889, (short) _map, 3310032, 0, 1 * 1000, 3310032);
					
					Thread.sleep(7 * 1000);
					for (L1PcInstance pc : PcStageCK()){
						pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,S_PacketBox.RED_MESSAGE, "ヴァラカス：監視神聖な場所に汚れた足を入れたことを後悔させてはいけない…！", true));
						pc.sendPackets(new S_SkillSound (pc.getId(), 15930));	
					}
					Thread.sleep(2 * 1000);
					spawn(32769, 32893, (short) _map, _random.nextInt(3), 145684, 0); 
					
					Thread.sleep(10 * 1000);

					stage = StageThree;
					break;
				case StageThree:
					if(Valakas_IsDie()) {
						health();
						giveItem(5000063);
						stage = StageSix;
						break;
					}
					vala = getValakas();

					
					int rand = _random.nextInt(100)+1;
					if(rand < 40) {
						// Zercuo Samkeronu...
						valakas_talk(vala, 0);
						AllShockStun();
						
						Thread.sleep(3000);
						
						for (L1PcInstance pc : PcStageCK()) {
							pc.sendPackets(new S_SkillSound (pc.getId(), 15959));
							Thread.sleep(500);
							pc.sendPackets(new S_SkillSound (pc.getId(), 15961));
							pc.setValakaseDmgDouble = true;
						}
						Thread.sleep(2000);
						for (L1PcInstance pc : PcStageCK()) {
							pc.setValakaseDmgDouble = false;
						}

					}
					Thread.sleep(5000);
					int max = vala.getMaxHp();
					int cur = vala.getCurrentHp();
					// ヴァラカス血 70% 時ハルパス召喚
					if(cur <= max * 3/4)
						stage = StageFour;
					break;
				case StageFour:
					if(Valakas_IsDie()) {
						health();
						giveItem(5000063);
						stage = StageSix;
						break;
					}
					// ハルパスの出現段階
					valakas_talk(vala, 4);
					for (L1PcInstance pc : PcStageCK()) {
						pc.sendPackets(new S_SkillSound (pc.getId(), 15837));
						pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.BUFF_IN_THE_HALFWAY_CIRCLE,60);
					}
					Thread.sleep(2000);
					spawn(32772, 32889, (short) _map, _random.nextInt(8), 145686, 0); 
					spawn(32772, 32889, (short) _map, _random.nextInt(8), 145685, 0); 
					spawn(32772, 32889, (short) _map, _random.nextInt(8), 145686, 0); 
					spawn(32772, 32889, (short) _map, _random.nextInt(8), 145685, 0); 
					spawn(32772, 32889, (short) _map, _random.nextInt(8), 145686, 0); 
					spawn(32772, 32889, (short) _map, _random.nextInt(8), 145685, 0); 
					spawn(32772, 32889, (short) _map, _random.nextInt(8), 145686, 0); 
					spawn(32772, 32889, (short) _map, _random.nextInt(8), 145685, 0); 
					Thread.sleep(5 *1000);

					stage = StageFive;
					
					break;
				case StageFive:
					if(Valakas_IsDie()) {
						health();
						giveItem(5000063);
						stage = StageSix;
						break;
					}
					Thread.sleep(1000);
					
					//最後のステップ
					rand = _random.nextInt(100)+1;
					
					if(rand < 15) {
						// Zercuo Samkeronu...
						valakas_talk(vala, 0);
						AllShockStun();
						
						Thread.sleep(3000);
		
						for (L1PcInstance pc : PcStageCK()) {
							pc.sendPackets(new S_SkillSound (pc.getId(), 15959));
							Thread.sleep(500);
							pc.sendPackets(new S_SkillSound (pc.getId(), 15961));
							pc.setValakaseDmgDouble = true;
						}
						Thread.sleep(2000);
						for (L1PcInstance pc : PcStageCK()) {
							pc.setValakaseDmgDouble = false;
						}
					}
					rand = _random.nextInt(100)+1;
					// global stunman
					if(rand < 25) {
						valakas_talk(vala, 3);
						AllShockStun();
					}
					Thread.sleep(10000);
					
					break;
				case StageSix:
					for (L1PcInstance pc : PcStageCK()){ // ドラゴンバフ
						pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGONRAID_BUFF, (86400 * 2) * 1000);
						Timestamp deleteTime = new Timestamp(System.currentTimeMillis()+ (86400000 * Config.RAID_TIME));// 7日
						pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGIB_RAID_BUFF, 86400 * 2),true);
						pc.sendPackets(new S_SkillSound(pc.getId(), 7783));
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7783));
						pc.getNetConnection().getAccount().setDragonRaid(deleteTime);
						pc.getNetConnection().getAccount().updateDragonRaidBuff();
					}
					L1World.getInstance().broadcastServerMessage("ドワーフの叫び：ヴァラカスの翼を破った勇者たちが誕生しました。");
					Thread.sleep(2000);

					for (L1PcInstance pc : PcStageCK()) {
						pc.sendPackets(new S_ServerMessage(1476)); // 30秒後にテレポート
						pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,S_PacketBox.RED_MESSAGE, "ヴァラカス：今日は一歩退く……しかし、卑劣な束縛が解けて力を取り戻すようになる日…。", true));
					}
					Thread.sleep(30000);
					stage = StageStop;
					break;
				case StageStop:
					RETURN_TEL();
					Thread.sleep(2000);
					Vala_Delete();
					break;
					
				default:
					break;
				}
			}catch(Exception e){
			}finally{
				try{
					Thread.sleep(1000);
				} catch (Exception e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				}
			}
		}

		
	}

	public static String []talkStr = { "ヴァラカス：ジェルキューオ・サンケローヌ..", "ヴァラカス：ジェルキューオカオフ..", "ヴァラカス：クルドゥームクラスハルパウム..", "ヴァラカス：クルドゥム・リラスカム..",
	"ヴァラカス：クルドゥームクラスハルパウム.." };
	
	public void valakas_talk(L1MonsterInstance vala, int talkNum){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Broadcaster.broadcastPacket(vala, new S_NpcChatPacket(vala,talkStr[talkNum]));
		
	}
	public void Start(){
		GeneralThreadPool.getInstance().schedule(this, 5000);
	}

	public L1MonsterInstance getValakas(){
		L1MonsterInstance mob = null;
		for(L1Object object : L1World.getInstance().getVisibleObjects(_map).values()){
			if(object instanceof L1MonsterInstance){
				mob = (L1MonsterInstance)object;
				int mobid = mob.getNpcId();
				if(mobid == 145684)
					return mob;
			}
		}
		return null;
	}
	private void RETURN_TEL(){
		int[] loc = L1TownLocation.getGetBackLoc(L1TownLocation.TOWNID_WERLDAN);
		for(L1PcInstance pc : PcStageCK()){
			if (pc.getMapId() == _map) {
				L1Teleport.teleport(pc, loc[0], loc[1], (short) loc[2], pc.getMoveState().getHeading(), true);
			}
		}
		Running = false;
	}
	
	private void TimeCheck(){
		if (Time > 0) {
			Time--;
		}
		if (Time == 0) {
			RETURN_TEL();
			Running = false;
		}
	}
	
	private boolean Valakas_IsDie(){
		L1MonsterInstance mob = null;
		for(L1Object object : L1World.getInstance().getVisibleObjects(_map).values()){
			if(object instanceof L1MonsterInstance){
				mob = (L1MonsterInstance)object;
				int npc = mob.getNpcTemplate().get_npcId();
				switch(npc){
				case 145684: // ヴァラカス
					if (mob != null && mob.isDead()){
						return true;
					}
					break;
				default:
					break;
				}
			}
		}
		return false;

	}
	
	private void Vala_Delete(){
		Collection<L1Object> cklist = L1World.getInstance().getVisibleObjects(_map).values();
		for(L1Object ob : cklist){
			if(ob == null)
				continue;
			if(ob instanceof L1ItemInstance){
				L1ItemInstance obj = (L1ItemInstance)ob;
				L1Inventory groundInventory = L1World.getInstance().getInventory(obj.getX(), obj.getY(), obj.getMapId());
				groundInventory.removeItem(obj);
			}else if(ob instanceof L1NpcInstance){
				L1NpcInstance npc = (L1NpcInstance)ob;
				npc.deleteMe();
			}
		}
		ValaRaidSystem.getInstance().removeVala(_map);
	}
	
	public ArrayList<L1PcInstance> PcStageCK() {
		ArrayList<L1PcInstance> _pc = new ArrayList<L1PcInstance>();
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if (pc.getMapId() == _map)
				_pc.add(pc);
		}
		return _pc;
	}
	/** 血痕＆アイテム **/
	private void health() { // L1SkillUse l1skilluse;
		L1World.getInstance().broadcastServerMessage("ドワーフの叫び：ヴァラカスの翼を破った勇者たちが誕生しました。");
	}

	private void giveItem(int id) {
		for (L1PcInstance pc : PcStageCK()) {
			if (pc == null)
				continue;
			createNewItem(pc, id, 1);
		}
	}

	private boolean createNewItem(L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // 持てない場合は地面に落とす処理のキャンセルはしない（否定防止）
				L1World.getInstance().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
			}
			for (L1PcInstance pc1 : PcStageCK()) {
				pc1.sendPackets(new S_ServerMessage(813, "ヴァラカス", item.getName(), pc1.getName()));
			}
			return true;
		} else {
			return false;
		}
	}
	
	private L1DoorInstance DoorSpawn(int X, int Y, short MapId, int Heading, int npcId) {
		try {
			L1DoorInstance Door = (L1DoorInstance) NpcTable.getInstance().newNpcInstance(npcId);
			Door.setId(ObjectIdFactory.getInstance().nextId());
			Door.setDoorId(npcId);
			Door.setMap(MapId);
			Door.getLocation().set(X, Y, MapId);
			Door.setHomeX(Door.getX());
			Door.setHomeY(Door.getY());
			Door.setDirection(Heading);
			Door.setLeftEdgeLocation(Heading == 0 ? Door.getX() : Door.getY());
			Door.setRightEdgeLocation(Heading == 0 ? Door.getX() : Door.getY());
			Door.setMaxHp(0);
			Door.setCurrentHp(0);
			Door.setKeeperId(0);
			Door.setOpenStatus(ActionCodes.ACTION_Close);
			for (L1PcInstance Pc : L1World.getInstance().getVisiblePlayer(Door)){
				Door.onPerceive(Pc);
				Pc.sendPackets(new S_DoActionGFX(Door.getId(), ActionCodes.ACTION_Close), true);
			}
			L1World.getInstance().storeObject(Door);
			L1World.getInstance().addVisibleObject(Door);
			Door.isPassibleDoor(false);
			return Door;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void spawn(int x, int y, short MapId, int Heading, int npcId, int randomRange) {
		try {
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(npcId);
			npc.setId(ObjectIdFactory.getInstance().nextId());
			npc.setMap(MapId);
			if (randomRange == 0) {
				npc.getLocation().set(x, y, MapId);
				npc.getLocation().forward(Heading);
			} else {
				int tryCount = 0;
				do {
					tryCount++;
					npc.setX(x + (int) (Math.random() * randomRange) - (int) (Math.random() * randomRange));
					npc.setY(y + (int) (Math.random() * randomRange) - (int) (Math.random() * randomRange));
					if (npc.getMap().isInMap(npc.getLocation()) && npc.getMap().isPassable(npc.getLocation())) {
						break;
					}
					Thread.sleep(1);
				} while (tryCount < 50);
				if (tryCount >= 50) {
					npc.getLocation().forward(Heading);
				}
			}
			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			npc.getMoveState().setHeading(Heading);

			for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(npc)) {
				npc.onPerceive(pc);
				S_DoActionGFX gfx = new S_DoActionGFX(npc.getId(), ActionCodes.ACTION_AxeWalk);
				pc.sendPackets(gfx);
			}

			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);

			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // チャット開始

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
}
