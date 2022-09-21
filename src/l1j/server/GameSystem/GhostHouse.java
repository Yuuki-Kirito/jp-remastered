/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.GameSystem;

import java.util.ArrayList;
import java.util.Random;

import l1j.server.server.ActionCodes;
import l1j.server.server.datatables.DoorSpawnTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_DRAGONPERL;
import l1j.server.server.serverpackets.S_Game_GhostHouse;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class GhostHouse implements Runnable {

	enum Message {
		ENTER, WAIT_START, NOT_ENOUGH_STARTMEMBERS, GAMEEND
	};

	enum Status {
		ENTER, READY, PLAY, END, REST
	};

	private static final int GHOSTHOUSE_MAPID = 5140;
	private static final int LIMIT_MIN_PLAYER_COUNT = 2; // ゲーム開始に必要な人数 (数 :
															// 2人)
	private static final int LIMIT_MIN_ENTER_PLAYER_COUNT = 2; // ゲーム入場に必要な人数
																// (数：5人)

	private L1PcInstance[] rankList;
	private L1PcInstance[] finishMember;
	private L1DoorInstance openDoorId;

	private int winnerCount = 0;
	private int finishMemberCount;
	private boolean isTimeOver;

	public static Status GhostHouseStatus;
	private static GhostHouse instance;
	private L1Location GOAL_LINE = new L1Location(32871, 32830,
			GHOSTHOUSE_MAPID);

	private final ArrayList<L1PcInstance> entermember = new ArrayList<L1PcInstance>();
	private final ArrayList<L1PcInstance> playmember = new ArrayList<L1PcInstance>();
	private static final Random _random = new Random(System.nanoTime());

	public static GhostHouse getInstance() {
		if (instance == null) {
			instance = new GhostHouse();
		}
		return instance;
	}

	private int msgCount = 10;

	@Override
	public void run() {
		try {
			setStatus(Status.PLAY);
			/*
			 * setStatus(Status.REST); while(true){ switch (GhostHouseStatus) {
			 * case ENTER: npcChat("幽霊の家の試合入場の2分前です."); Thread.sleep(60000L);
			 * npcChat("幽霊の家の試合入場の1分前です."); Thread.sleep(30000L); // 2分精度mu8oe
			 * 入場ユーザーを受け取り、待つ 120000L npcChat("幽霊の家の試合の入場30秒前です.");
			 * enterMsg("幽霊の家競技入場30秒前です。"); Thread.sleep(10000L);
			 * npcChat("幽霊の家競技入場20秒前です。");
			 * enterMsg("幽霊の家競技入場20秒前です。"); Thread.sleep(10000L);
			 * npcChat("幽霊の家競技入場10秒前です。");
			 * enterMsg("幽霊の家競技入場10秒前です。"); Thread.sleep(5000L);
			 * npcChat("幽霊の家競技入場5秒前です。"); enterMsg("幽霊の家競技入場5秒前です。");
			 * Thread.sleep(1000L); npcChat("幽霊の家競技入場4秒前です。");
			 * enterMsg("幽霊の家競技入場4秒前です。"); Thread.sleep(1000L);
			 * npcChat("幽霊の家競技入場3秒前です。"); enterMsg("幽霊の家競技入場3秒前です。");
			 * Thread.sleep(1000L); npcChat("유령의집 경기 입장 2초 전입니다.");
			 * enterMsg("幽霊の家競技入場2秒前です。"); Thread.sleep(1000L);
			 * npcChat("幽霊の家競技入場1秒前です。"); enterMsg("幽霊の家競技入場1秒前です。");
			 * Thread.sleep(1000L); sendMessage(Message.ENTER);
			 * setStatus(Status.READY); break; case READY:
			 * npcChat("幽霊の家 試合開始待っています。"); Thread.sleep(10000L);
			 * npcChat("幽霊の家 試合開始待っています。"); Thread.sleep(5000L);
			 * finalPlayMemberCheck(); if(isGotEnoughStartMembers()){
			 * sendMessage(Message.WAIT_START); setStatus(Status.PLAY); } else {
			 * sendMessage(Message.NOT_ENOUGH_STARTMEMBERS); getOutGhostHouse();
			 * setStatus(Status.REST); npcChat("人員不足で幽霊の家競技が終了しました。"); }
			 * break; case PLAY: isTimeOver = false; Thread.sleep(3000L);
			 * clearEnterMember(); // 入場待機の削除 doPolyPlayGameMember(); // ゲーム人員変身
			 * Thread.sleep(5000L); countDownStartGame(); // 5,4,3,2,1 カウントダウン
			 * Thread.sleep(5000L); npcChat("幽霊の家の試合が始まりました。");
			 * checkWinnerCount(); // 勝者チェック startPlayGameMemberGameTime(); // ゲーム
			 * 参加者 00:00 時間の開始 GhostHouseStartDoorOpen(); // 5分チェック開始 int j =
			 * 0; while (j <= 300){ if(getStatus() == Status.END){ break; }
			 * Thread.sleep(1000L); playmember_check(); sortRankList();
			 * refreshRankList(); ++j; } // 5分チェック終了if (notWinnerGame())
			 * isTimeOver = true; setStatus(Status.END); break; case END:
			 * sendMessage(Message.GAMEEND); Thread.sleep(10000L);
			 * playGameMembersDisplayPacketClear(); getOutGhostHouse(); if
			 * (finalCheckFinishMember()){ giveItemToWinnerMember(); }
			 * GhostHouseDoorClose(); allClear(); clearFinishMember();
			 * npcChat("幽霊の家の試合が終了しました."); break; case REST: if(msgCount-- <
			 * 1){ npcChat("幽霊を一度見てください〜"); msgCount = 10; }
			 * Thread.sleep(1000L); break; default: Thread.sleep(1000L); break;
			 * } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final S_PacketBox pb_end = new S_PacketBox(
			S_PacketBox.MINIGAME_END);
	private static final S_PacketBox pb_clear = new S_PacketBox(
			S_PacketBox.MINIGAME_TIME_CLEAR);

	private void playmember_check() {
		L1PcInstance[] pcl = getPlayMemberArray();
		for (L1PcInstance pc : pcl) {
			if (pc == null)
				continue;
			if (pc.getMapId() != 5140) {
				pc.sendPackets(pb_end);
				pc.sendPackets(pb_clear);
				pc.setHaunted(false);
				removePlayMember(pc);
			}
		}
		pcl = null;
	}

	private void allClear() {
		winnerCount = 0;
		finishMemberCount = 0;
		clearEnterMember();
		clearPlayMember();
		setStatus(Status.REST);
	}

	private static final S_Message_YN myn1256 = new S_Message_YN(1256, "");
	private static final S_ServerMessage sm1264 = new S_ServerMessage(1264);
	private static final S_ServerMessage sm1257 = new S_ServerMessage(1257);
	private static final S_ServerMessage sm1263 = new S_ServerMessage(1263);
	private static final S_PacketBox pb_10second = new S_PacketBox(
			S_PacketBox.MINIGAME_10SECOND_COUNT);

	private void sendMessage(Message msg) {
		switch (msg) {
		case ENTER:
			L1PcInstance[] list = getEnterMemberArray();
			for (L1PcInstance pc : list) {
				if (pc != null) {
					pc.sendPackets(myn1256);
				}
			}
			list = null;
			break;
		case NOT_ENOUGH_STARTMEMBERS:
			L1PcInstance[] list2 = getPlayMemberArray();
			for (L1PcInstance pc : list2)
				if (pc != null) {
					pc.sendPackets(sm1264);
				}
			list2 = null;
			break;
		case WAIT_START:
			L1PcInstance[] list3 = getPlayMemberArray();
			for (L1PcInstance pc : list3)
				if (pc != null) {
					pc.sendPackets(sm1257);
				}
			list3 = null;
			break;
		case GAMEEND:
			L1PcInstance[] list4 = getPlayMemberArray();
			for (L1PcInstance pc : list4) {
				if (pc != null) {
					if (isTimeOver)
						pc.sendPackets(sm1263);
					else
						pc.sendPackets(pb_10second);
				}
			}
			list4 = null;
			break;
		default:
			break;
		}
	}

	public void finalPlayMemberCheck() {
		L1PcInstance[] list = getPlayMemberArray();
		for (L1PcInstance pc : list) {
			if (pc != null) {
				if (pc.getMapId() != GHOSTHOUSE_MAPID) {
					removePlayMember(pc);
					pc.setHaunted(false);
				}
			}
		}
		list = null;
	}

	private L1MerchantInstance keeper = null;

	private void npcChat(String chat) {
		if (keeper == null) {
			for (L1MerchantInstance npc : L1World.getInstance()
					.getAllMerchant()) {
				if (npc.getNpcId() == 80085) {
					keeper = npc;
					break;
				}
			}
		}
		if (keeper != null) {
			Broadcaster.broadcastPacket(keeper, new S_NpcChatPacket(keeper,
					chat, 0), true);
		}
	}

	private void enterMsg(String msg) {
		S_SystemMessage sm_msg = new S_SystemMessage(msg);
		L1PcInstance[] list = getEnterMemberArray();
		for (L1PcInstance pc : list) {
			if (pc != null)
				pc.sendPackets(sm_msg);
		}
	}

	private boolean isGotEnoughStartMembers() {
		return (getPlayMembersCount() >= LIMIT_MIN_PLAYER_COUNT) ? true : false;
	}

	private void doPolyPlayGameMember() {
		L1PcInstance[] list = getPlayMemberArray();
		for (L1PcInstance pc : list) {
			L1PolyMorph.doPoly(pc, 6284, 600, L1PolyMorph.MORPH_BY_LOGIN);
			pc.setHaunted(true);
		}
		list = null;
	}

	private static final S_PacketBox pb_count = new S_PacketBox(
			S_PacketBox.MINIGAME_START_COUNT);

	private void countDownStartGame() {
		sortRankList();
		refreshRankList();
		L1PcInstance[] list = getPlayMemberArray();
		for (L1PcInstance pc : list) {
			pc.sendPackets(pb_count);
		}
		list = null;
	}

	private void checkWinnerCount() {
		int PlayerCount = getPlayMembersCount();
		if (PlayerCount <= 4)
			winnerCount = 1;
		else if (5 >= PlayerCount && PlayerCount <= 7)
			winnerCount = 2;
		else if (8 >= PlayerCount && PlayerCount <= 10)
			winnerCount = 3;
		finishMember = new L1PcInstance[winnerCount];
	}

	private static final S_PacketBox pb_start = new S_PacketBox(
			S_PacketBox.MINIGAME_START_TIME);

	private void startPlayGameMemberGameTime() {
		L1PcInstance[] list = getPlayMemberArray();
		for (L1PcInstance pc : list) {
			pc.sendPackets(pb_start);
		}
		list = null;
	}

	private void GhostHouseStartDoorOpen() {
		L1DoorInstance door = DoorSpawnTable.getInstance().getDoor(3001);
		if (door != null) {
			if (door.getOpenStatus() == ActionCodes.ACTION_Close) {
				door.open();
			}
		}
	}

	private void GhostHouseDoorClose() {
		L1DoorInstance door = null;
		for (L1Object object : L1World.getInstance().getVisibleObjects(5140)
				.values()) {
			if (object instanceof L1DoorInstance) {
				door = (L1DoorInstance) object;
				if (door.getMapId() == GHOSTHOUSE_MAPID
						&& door.getOpenStatus() == ActionCodes.ACTION_Open) {
					door.close();
				}
			}
		}
	}

	private void sortRankList() {
		rankList = getPlayMemberArray();
		int c = rankList.length - 1;
		L1PcInstance data = null;
		for (int i = 0; i < c; i++) {
			for (int j = 0; j < (c - i); j++) {
				if (rankList[j].getLocation().getLineDistance(GOAL_LINE) > rankList[j + 1]
						.getLocation().getLineDistance(GOAL_LINE)) {
					data = rankList[j];
					rankList[j] = rankList[j + 1];
					rankList[j + 1] = data;
				}
			}
		}
	}

	private void refreshRankList() {
		int i = 0;
		for (L1PcInstance pc : rankList) {
			S_Game_GhostHouse gg = new S_Game_GhostHouse(i++);
			pc.sendPackets(gg, true);
		}
	}

	private boolean notWinnerGame() {
		return isPlayingNow() && getPlayMembersCount() != 0 ? true : false;
	}

	private void playGameMembersDisplayPacketClear() {
		L1PcInstance[] list = getPlayMemberArray();
		for (L1PcInstance pc : list) {
			pc.sendPackets(pb_end);
			pc.sendPackets(pb_clear);
			pc.setHaunted(false);
		}
		list = null;
	}

	public void pushOpenDoorTrap(int doorid) {
		openDoorId = DoorSpawnTable.getInstance().getDoor(doorid);
		if (openDoorId != null) {
			if (openDoorId.getOpenStatus() == ActionCodes.ACTION_Close)
				openDoorId.open();
		}
	}

	public void pushFinishLineTrap(L1PcInstance pc) {
		if (isPlayingNow())
			setStatus(Status.END);
		if (finishMemberCount >= winnerCount)
			return;
		if (finishMember[finishMemberCount] != pc)
			finishMember[finishMemberCount++] = pc;
	}

	private boolean finalCheckFinishMember() {
		return winnerCount <= finishMemberCount ? true : false;
	}

	private static final S_ServerMessage sm403 = new S_ServerMessage(403,
			"勇気ある者のカボチャのポケット");

	private void giveItemToWinnerMember() {
		for (L1PcInstance pc : finishMember) {
			if (_random.nextInt(10) <= 2) {
				pc.getInventory()
						.storeItem(L1ItemId.GHOSTHOUSE_WINNER_PIECE, 1);
			}
			pc.getInventory().storeItem(L1ItemId.MINIGAME_PRESENT, 1);
			pc.sendPackets(sm403);
		}
	}

	private static final S_PacketBox pb_dragonperl = new S_PacketBox(
			S_PacketBox.DRAGONPERL, 0, 0);

	private void getOutGhostHouse() {
		L1SkillUse l1skilluse = new L1SkillUse();
		L1PcInstance[] list = getPlayMemberArray();
		for (L1PcInstance pc : list) {
			if (pc != null) {
				if (getStatus() == Status.READY) {
					pc.getInventory().storeItem(40308, 1000); // 1000 アデナ支給
				}
				l1skilluse.handleCommands(pc, L1SkillId.CANCELLATION,
						pc.getId(), pc.getX(), pc.getY(), null, 0,
						L1SkillUse.TYPE_LOGIN);
				if (pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_DRAGONPERL)) {
					pc.getSkillEffectTimerSet().killSkillEffectTimer(
							L1SkillId.STATUS_DRAGONPERL);
					pc.sendPackets(pb_dragonperl);
					S_DRAGONPERL sdp = new S_DRAGONPERL(pc.getId(), 0);
					Broadcaster.broadcastPacket(pc, sdp);
					pc.sendPackets(sdp);
					pc.set_pearl_speed(0);
					sdp = null;
				}
				int[] loc = Getback.GetBack_Location(pc, true);
				L1Teleport
						.teleport(pc, loc[0], loc[1], (short) loc[2], 5, true);
				loc = null;
			}
		}
		list = null;
		l1skilluse = null;
	}

	public void addEnterMember(L1PcInstance pc) {
		if (isReadyNow()) {
			pc.sendPackets(myn1256);
			return;
		}
		if (!isEnterMember(pc)) {
			entermember.add(pc);
			S_ServerMessage sm1253 = new S_ServerMessage(1253,
					Integer.toString(getEnterMemberCount()));
			pc.sendPackets(sm1253);
			sm1253 = null;
			if (getStatus() == Status.REST
					&& getEnterMemberCount() >= LIMIT_MIN_ENTER_PLAYER_COUNT) {
				setStatus(Status.ENTER);
			}
		}
	}

	public boolean isReadyNow() {
		return getStatus() == Status.READY ? true : false;
	}

	public boolean isPlayingNow() {
		return getStatus() == Status.PLAY ? true : false;
	}

	private void clearFinishMember() {
		if (finishMember == null)
			return;
		for (int i = 0, c = finishMember.length; i < c; i++) {
			finishMember[i] = null;
		}
	}

	public void addPlayMember(L1PcInstance pc) {
		playmember.add(pc);
	}

	public int getPlayMembersCount() {
		return playmember.size();
	}

	public void removePlayMember(L1PcInstance pc) {
		playmember.remove(pc);
	}

	public void clearPlayMember() {
		playmember.clear();
	}

	public boolean isPlayMember(L1PcInstance pc) {
		return playmember.contains(pc);
	}

	public L1PcInstance[] getPlayMemberArray() {
		return playmember.toArray(new L1PcInstance[getPlayMembersCount()]);
	}

	public int getEnterMemberCount() {
		return entermember.size();
	}

	public void removeEnterMember(L1PcInstance pc) {
		entermember.remove(pc);
	}

	public void clearEnterMember() {
		entermember.clear();
	}

	public boolean isEnterMember(L1PcInstance pc) {
		return entermember.contains(pc);
	}

	public L1PcInstance[] getEnterMemberArray() {
		return entermember.toArray(new L1PcInstance[getEnterMemberCount()]);
	}

	public L1PcInstance[] getRank() {
		return rankList;
	}

	private void setStatus(Status i) {
		GhostHouseStatus = i;
	}

	private Status getStatus() {
		return GhostHouseStatus;
	}
}