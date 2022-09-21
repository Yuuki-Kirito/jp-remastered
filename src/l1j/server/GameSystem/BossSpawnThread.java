package l1j.server.GameSystem;

import l1j.server.Config;
import l1j.server.server.model.L1MobGroupSpawn;
import l1j.server.server.model.L1NpcDeleteTimer;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.L1SpawnUtil;

public class BossSpawnThread implements Runnable {

	L1NpcInstance npc = null;
	int tempx = 0;
	int tempy = 0;
	short tempm = 0;
	int timeMillisToDelete = 0;
	long _sleep = 1;
	int randomRange = 0;
	int sec = 0;

	public BossSpawnThread(L1NpcInstance _npc, int x, int y, short map,
			int _timeMillisToDelete, long sleep, int _randomRange) {
		npc = _npc;
		tempx = x;
		tempy = y;
		tempm = map;
		timeMillisToDelete = _timeMillisToDelete;
		_sleep = sleep;
		randomRange = _randomRange;
	}

	public void run() {
		try {
			if (npc == null)
				return;
			sec = (int) _sleep / 1000;
		//	System.out.println(npc.getName() + " この " + sec + "秒後にスポーン");
	//		eva.EventLogAppend("[お知らせ]-["+ sec +" 秒]後 "+ npc.getName() + " 스폰" );イベントログエラー
			Thread.sleep(_sleep);
			
			npc.setMap(tempm);
			if (randomRange == 0) {
				npc.getLocation().set(tempx, tempy, tempm);
			} else {
				int tryCount = 0;
				do {
					tryCount++;
					npc.setX(tempx + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					npc.setY(tempx + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					if ((npc.getX() >= 32936 && npc.getY() <= 32945)
							&& (npc.getY() >= 32861 && npc.getY() <= 32870)
							&& npc.getMapId() == 410)
						continue;
					if (npc.getMap().isInMap(npc.getLocation())
							&& npc.getMap().isPassable(npc.getLocation())) {
						break;
					}
					Thread.sleep(1);
				} while (tryCount < 50);

				if (tryCount >= 50) {
					npc.getLocation().set(tempx, tempy, tempm);
				}
			}

			npc.getLocation().set(tempx, tempy, tempm);
			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			npc.getMoveState().setHeading(5);

			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);

			L1SpawnUtil.logofbossTime(npc);

			npc.getLight().turnOnOffLight();

			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // チャット開始
			if (0 < timeMillisToDelete) {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc,
						timeMillisToDelete);
				timer.begin();
			}

			if (npc.getNpcId() == 100338) {// エルザベ
				L1MobGroupSpawn.getInstance().doSpawn(npc, 106, false, false);
			}

			if (npc.getNpcId() == 100824) {// スパイス
				L1MobGroupSpawn.getInstance().doSpawn(npc, 150, false, false);
			}

			if (npc.getNpcId() == 45684) {
				L1World.getInstance().broadcastPacketToAll(
						new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
								"火竜の巣の最上部にヴァラカスが出現しました."), true);
			}

	//		L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"ボスがスポーンしました " + npc.getName()), true);
			
			try {
				if (Config._connection_chat_monitor() > 0) {
					S_SystemMessage sm = new S_SystemMessage("BOSSが出現しました。 "
							+ npc.getName() + " X " + npc.getX() + " / y "
							+ npc.getY() + " / id " + npc.getMapId());
					for (L1PcInstance gm : Config.toArray_connection_chat_monitor()) {
						L1PcInstance ck = L1World.getInstance().getPlayer(
								gm.getName());
						if (ck == null) {
							Config.remove_connection(gm);
							continue;
						}
						if (gm.getNetConnection() == null) {
							Config.remove_connection(gm);
							continue;
						}
						gm.sendPackets(sm);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
