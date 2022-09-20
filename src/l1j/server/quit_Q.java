package l1j.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.GameSystem.GhostHouse;
import l1j.server.GameSystem.PetRacing;
import l1j.server.GameSystem.Dungeon.DungeonSystem;
import l1j.server.GameSystem.MiniGame.DeathMatch;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.CharBuffTable;
import l1j.server.server.datatables.MonsterBookTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Trade;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1FollowerInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;
import manager.LinAllManager;
import server.LoginController;

public class quit_Q implements Runnable {
	private long time = 0;
	private final Queue<L1PcInstance> _queue;

	public quit_Q() {
		_queue = new ConcurrentLinkedQueue<L1PcInstance>();
		GeneralThreadPool.getInstance().execute(this);
	}

	public void requestWork(L1PcInstance name) {
		_queue.offer(name);
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(10L);
				synchronized (this){
					L1PcInstance pc = _queue.peek();
					if (_queue.size() > 100) {
						if (System.currentTimeMillis() > time) {
							System.out.println("More than 100 exit queues : " + _queue.size());
							time = System.currentTimeMillis() + (1000 * 10);
						}
					}

					if (pc == null) {
						continue;
					}
					
					if (pc.isPrivateShop()) {
						pc.delete_shop_item(pc.getId());
					}
					

					LinAllManager.getInstance().LogLogOutAppend(pc.getName(), pc.getNetConnection().getHostname());

					// System.out.println("123 :" +pc.getName());

					try {
						if (Config._connection_chat_monitor() > 0) {
							for (L1PcInstance gm : Config.toArray_connection_chat_monitor()) {
								if (gm.getNetConnection() == null) {
									Config.remove_connection(gm);
									continue;
								}
								
								if (gm == pc) {
									continue;
								}
								
								gm.sendPackets(new S_SystemMessage("\\fY[" + pc.getName() + "] (end) / account:" + pc.getAccountName()));
							}
						}

						if (pc.isGm()) Config.removeALL(pc);

						pc.set_delete(true); // air bug fix
						pc.setadFeature(1);
						pc.setDeathMatch(false);
						pc.setHaunted(false);
						pc.setPetRacing(false);

						// 死んでいると街に戻り、空腹状態にする
						if (pc.isDead()) {
							int[] loc = Getback.GetBack_Location(pc, true);
							pc.setX(loc[0]);
							pc.setY(loc[1]);
							pc.setMap((short) loc[2]);
							pc.setCurrentHp(pc.getLevel());
							pc.set_food(39); // 10%
							loc = null;
						}
						
						// 自分の城の近くで終了時に耐性で位置を設定
						if (pc.getClan() != null && pc.getClan().getCastleId() > 0) {
							if (L1CastleLocation.checkInWarArea(pc.getClan().getCastleId(), pc)) {
								int[] loc = L1CastleLocation.getCastleLoc(pc.getClan().getCastleId());
								pc.setX(loc[0]);
								pc.setY(loc[1]);
								pc.setMap((short) loc[2]);
								loc = null;
							}
						}

						if (pc.isGhost()) {
							pc.setX(pc._ghostSaveLocX);
							pc.setY(pc._ghostSaveLocY);
							pc.setMap((short) pc._ghostSaveMapId);
							pc.getMoveState().setHeading(pc._ghostSaveHeading);
						}

						// 血盟倉庫の使用中に勃起したり終了する場合、血盟倉庫の使用中解除（クウ）
						ClanWarehouse clanWarehouse = null;
						L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
						if (clan != null) {
							clanWarehouse = WarehouseManager.getInstance().getClanWarehouse(clan.getClanName());
							clan.removeOnlineClanMember(pc.getName());
						}
						
						if (clanWarehouse != null) {
							clanWarehouse.unlock(pc.getId());
						}

						// トレードを停止する
						if (pc.getTradeID() != 0) { // トレード中
							L1Trade trade = new L1Trade();
							trade.TradeCancel(pc);
						}

						// 決闘中
						if (pc.getFightId() != 0) {
							pc.setFightId(0);
							L1PcInstance fightPc = (L1PcInstance) L1World.getInstance().findObject(pc.getFightId());
							if (fightPc != null) {
								fightPc.setFightId(0);
								fightPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_DUEL, 0, 0));
							}
						}

						if (DeathMatch.getInstance().isEnterMember(pc)) {
							DeathMatch.getInstance().removeEnterMember(pc);
						}
						
						if (GhostHouse.getInstance().isEnterMember(pc)) {
							GhostHouse.getInstance().removeEnterMember(pc);
						}
						
						if (PetRacing.getInstance().isEnterMember(pc)) {
							PetRacing.getInstance().removeEnterMember(pc);
						}

						/** ペットが召喚されているかどうかをチェックし、クリーンアップペットが死んだ場合はそのまま放置します。 */
						if (pc.getPetList() != null && pc.getPetListSize() > 0) {
							for (Object petObject : pc.getPetList()) {
								if (petObject == null) continue;
								if (petObject instanceof L1PetInstance) {
									L1PetInstance pet = (L1PetInstance) petObject;
									PetTable.UpDatePet(pet);
									pc.removePet((L1NpcInstance) pet);
									if(!pet.isDead()) pet.deletePet();
								} else if (petObject instanceof L1SummonInstance) {
									L1SummonInstance sunm = (L1SummonInstance) petObject;
									sunm.dropItem();
									pc.removePet((L1NpcInstance) sunm);
									sunm.deleteMe();
								}
							}
						}
						
						// マジックドールをワールドマップ上から消す
						if (pc.getDollList() != null && pc.getDollListSize() > 0) {
							for (L1DollInstance doll : pc.getDollList()) {
								if (doll != null) {
									doll.deleteDoll();
								}
							}
						}

						if (pc.getFollowerList() != null && pc.getFollowerList().size() > 0) {
							L1FollowerInstance follower = null;
							for (Object followerObject : pc.getFollowerList().values()) {
								if (followerObject == null)continue;
								follower = (L1FollowerInstance) followerObject;
								follower.setParalyzed(true);
								follower.spawn(follower.getNpcTemplate().get_npcId(), follower.getX(), follower.getY(), follower.getMoveState().getHeading(), follower.getMapId());
								follower.deleteMe();
							}
						}
						
						// キャラクター適用中のバフリストDB削除
						CharBuffTable.DeleteBuff(pc);
						// ログアウト時に現在適用されているバフリストDBに保存
						CharBuffTable.SaveBuff(pc);
						MonsterBookTable.getInstace().saveMonsterBookList(pc.getId());
						pc.getSkillEffectTimerSet().clearRemoveSkillEffectTimer();

						for (L1ItemInstance item : pc.getInventory().getItems()) {
							if (item != null && item.getCount() <= 0) {
								pc.getInventory().deleteItem(item);
							}
						}
						
						pc.setLogOutTime();
						pc.setOnlineStatus(0);
				
						/** キャラクター情報の保存 */
						pc.save();
						/** キャラクターの在庫を保存 */
						pc.saveInventory();
						
						/** If character div connection is not a game */
						if(pc.getNetConnection() != null){
							/** Check if there is indung information and delete the indungeon */
							DungeonSystem.isDungeonInfoPcCheck(pc);
							/** If you lease from the interserver, set it to exit from the interserver. */
							if(!pc.getNetConnection().getInterServer()){
								if (pc.isInParty()) { // パーティー中
									pc.getParty().leaveMember(pc);
								}
								
								if (pc.isInChatParty()) {// チャットパーティー
									pc.getChatParty().leaveMember(pc);
								}
								
								/** ソケットだけが生きている場合は、ログアウトで最後のログアウト時間を保存します。 */
								LoginController.getInstance().logout(pc.getNetConnection());
							}
							
							/** DBデータ情報を保存できるように別に保管ソケットが開かれなければ保存されるため、別に保管 */
							pc.getNetConnection().getAccount().updateAttendanceTime();
							pc.getNetConnection().getAccount().updateDGTime();
							pc.getNetConnection().getAccount().updateTam();
							pc.getNetConnection().getAccount().updateNcoin();
							
							/** ソケットクローズチェック  */
							pc.setNetConnection(null);
						}
						
						/** オブジェクトの削除 */
						pc.logout();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					_queue.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}