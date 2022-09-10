package l1j.server.server.clientpackets;

import static l1j.server.server.model.skill.L1SkillId.RevengeTime;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import l1j.server.Config;
import l1j.server.IND;
import l1j.server.IND_Q;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Dungeon.DungeonInfo;
import l1j.server.GameSystem.Dungeon.DungeonSystem;
import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.GameSystem.InterServer.InterServer;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.RotationNotice.RotationNoticeTable;
import l1j.server.GameSystem.RotationNotice.RotationNoticeTable.EventNotice;
import l1j.server.GameSystem.SupportSystem.L1SupportMap;
import l1j.server.GameSystem.SupportSystem.SupportMapTable;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.server.Account;
import l1j.server.server.ActionCodes;
import l1j.server.server.BadNamesList;
import l1j.server.server.GMCommands;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.Opcodes;
import l1j.server.server.UserCommands;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.AttendanceTable;
import l1j.server.server.datatables.AttendanceTable.AttendanceItem;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.CharcterRevengeTable;
import l1j.server.server.datatables.ClanBlessHuntInfo;
import l1j.server.server.datatables.ClanBlessHuntInfo.HuntInfo;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.CraftInfoTable;
import l1j.server.server.datatables.CraftInfoTable.L1CraftInfo;
import l1j.server.server.datatables.CraftInfoTable.Material;
import l1j.server.server.datatables.ExcludeLetterTable;
import l1j.server.server.datatables.ExcludeTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.MonsterBookTable;
import l1j.server.server.datatables.NpcActionTable;
import l1j.server.server.datatables.NpcShopSpawnTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.datatables.PetsSkillsTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Cooking;
import l1j.server.server.model.L1ExcludingLetterList;
import l1j.server.server.model.L1ExcludingList;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Quest;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.npc.action.L1NpcAction;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ACTION_UI;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharMapTime;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_ClanBlessHuntUi;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_DoActionShop;
import l1j.server.server.serverpackets.S_EventNotice;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_MonsterUi;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_Notice;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_Party;
import l1j.server.server.serverpackets.S_PetWindow;
import l1j.server.server.serverpackets.S_QuestTalkIsland;
import l1j.server.server.serverpackets.S_Revenge;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_ServerVersion;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SupportSystem;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1LoginInfo;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1PetSkill;
import l1j.server.server.templates.L1PrivateShopBuyList;
import l1j.server.server.templates.L1PrivateShopSellList;
import l1j.server.server.templates.L1QuestInfo;
import l1j.server.server.templates.L1UserRanking;
import l1j.server.server.templates.eCharacterDeleteResult;
import l1j.server.server.utils.BinaryOutputStream;
import l1j.server.server.utils.CommonUtil;
import l1j.server.server.utils.SQLUtil;
import manager.LinAllManager;
import server.LineageClient;
import server.LoginController;
import server.controller.InvSwapController;

public class C_ActionUi extends ClientBasePacket {
	Random _Random = new Random(System.nanoTime());
	private static final String C_ACTION_UI = "[C] C_ActionUi";
	private static final String[] textFilter = { "�ù�" };

	private static final int CRAFT_ITEM = 54;
	private static final int CRAFT_ITEMLIST = 56;
	private static final int CRAFT_OK = 58;
	private static final int SIEGE_WAR_REQ = 69;
	private static final int ServerInter = 117;
	private static final int DOLL_START = 122;
	private static final int DOLL_RESULT = 124;
	private static final int TELEPORT_SKY_GARDEN = 132;
	private static final int ������ŷ = 135;
	private static final int EventNotice = 143;
	private static final int ACTIONKEY = 319;
	private static final int CLAN_JOIN = 322;
	private static final int CLAN_MEMBERS = 324;
	private static final int CLAN_OPTION_CHANGE = 326;
	private static final int CLAN_OPTION = 332;
	private static final int PARTY_SIGN = 338;
	private static final int SPELL_PASSIVE_ONOFF_REQ = 408;
	private static final int ACCOUNT_TAM = 460;
	private static final int ACCOUNT_TAM_CANCEL = 480;
	private static final int CHAR_STAT = 484;
	private static final int CHAT = 514;
	private static final int EXCLUDE = 543;
	private static final int MONSTERBOOK_CLEAR = 563;
	private static final int MONSTERBOOK_TEL = 565;
	private static final int SEAL = 569;
	private static final int Party_Assist = 599;
	private static final int BMTYPE_DEL_CHECK_REQ = 604;
	private static final int EQUIPMENT_CHANGE = 801;
	private static final int RESTART_UI = 802;
	private static final int BOOKWEEKQUEST_TEL = 815;
	private static final int BOOKWEEKQUEST_CLEAR = 811;
	private static final int ���λ��� = 817;
	private static final int ServerVersion = 820;
	private static final int Party_Invitation = 828;
	private static final int WorldMapTeleport = 829;
	private static final int MOVE_SERVER_AUTH_REQ = 830;
	private static final int NP_LOGIN_REQ = 997;
	private static final int ENVIRONMENT_SETTING = 1002;
	private static final int ATTENDANCE_CHECK_COMPLETE = 1006;
	private static final int CLAN_BLESS_HUNT_CHANGE = 1016;
	private static final int CLAN_BLESS_HUNT_ALL_CHANGE = 1017;
	private static final int ����â��ŷ = 1021;
	private static final int RevengeBoard = 1051;
	private static final int RevengeProvoke = 1054;
	private static final int RevengeChase = 1056;
	private static final int RevengeChase2 = 1058;
	private static final int PetName = 2001;
	private static final int Petstat = 2003;
	private static final int PetCommand = 2004;
	private static final int PetSkillLevel = 2007;
	private static final int PetSkill = 2008;
	private static final int START_PLAY_SUPPORT = 2101;
	private static final int FINISH_PLAY_SUPPORT = 2103;
	private static final int InDungeonList = 2211;
	private static final int InDungeonAccess = 2213;
	private static final int InDungeonType = 2215;
	private static final int InDungeonSlot = 2217;
	private static final int InDungeonStancet = 2220;
	private static final int InDungeonReady = 2223;
	private static final int InDungeStart = 2225;
	private static final int InDungeoninvite = 2227;
	private static final int InDungeon = 2229;
	private static final int InDungeExit = 2231;
	private static final int InDungeonOut = 2234;
	private static final int PETBALL_CONTENTS_START_REQ = 2376;
	private static final int PETBALL_CONTENTS_END_REQ = 2377;

	private static final String REMOVE = "08 01 1a 04 08 00 10 00 85 32";
	private static final String ADD = "08 00 12 29 08 d3 7a 10 01 18 ff ff ff ff ff ff ff ff ff 01 20 00 28 01 30 00 38 01 42 06 24 31 30 39 34 37 48 00 50 00 58 b0 2d 62 00 9d 80";
	private static final int[] bigSuccessItem = {};

	public C_ActionUi(byte abyte0[], LineageClient client) throws IOException {
		super(abyte0);
		int type = readH();

		L1PcInstance pc = client.getActiveChar();

		// System.out.println("�׼� > " + type);

		switch (type) {
		case CHAR_STAT: {
			try {
				int length = readH();// ����
				ArrayList<byte[]> arrb = new ArrayList<byte[]>();
				for (int i = 0; i < length / 2; i++) {
					arrb.add(readByte(2));
				}

				int level, classtype = 0, status = 0, unknown2, unknown3 = 0, str = 0, cha = 0, inte = 0, dex = 0, con = 0, wis = 0;
				for (byte[] b : arrb) {
					switch (b[0]) {
					case 0x08:
						level = b[1] & 0xff;
						break;// ��
					case 0x10:
						classtype = b[1] & 0xff;
						break;// Ŭ���� Ÿ��
					case 0x18:
						status = b[1] & 0xff;
						break;// �ʱ���� = 1 / ���Ⱥ������ = 8

					case 0x20:
						unknown2 = b[1] & 0xff;
						break;// ��
					case 0x28:
						unknown3 = b[1] & 0xff;
						break;// ��

					case 0x30:
						str = b[1] & 0xff;
						break;// ��
					case 0x38:
						inte = b[1] & 0xff;
						break;// ��Ʈ
					case 0x40:
						wis = b[1] & 0xff;
						break;// ����
					case 0x48:
						dex = b[1] & 0xff;
						break;// ��
					case 0x50:
						con = b[1] & 0xff;
						break;// ��
					case 0x58:
						cha = b[1] & 0xff;
						break;// ī��

					default:
						int i = 0;
						try {
							i = b[0] & 0xff;
						} catch (Exception e) {
						}
						System.out.println("[���Ȱ��� ���ǵ��� ���� ��Ŷ] op : " + i);
						break;
					}
				}

				if (str != 0 && unknown3 != 1) {
					client.sendPacket(new S_NewCreateItem(0x01e3, status, str, con, "��", classtype, null));
				}

				if (dex != 0) {
					client.sendPacket(new S_NewCreateItem(0x01e3, status, dex, 0, "��", classtype, null));
				}

				if (con != 0 && unknown3 != 16) {
					client.sendPacket(new S_NewCreateItem(0x01e3, status, con, str, "��", classtype, null));
				}

				if (inte != 0) {
					client.sendPacket(new S_NewCreateItem(0x01e3, status, inte, 0, "��Ʈ", classtype, null));
				}

				if (wis != 0) {
					client.sendPacket(new S_NewCreateItem(0x01e3, status, wis, 0, "����", classtype, null));
				}

				if (cha != 0) {
					client.sendPacket(new S_NewCreateItem(0x01e3, status, cha, 0, "ī��", classtype, null));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		case CLAN_OPTION: {
			if (pc.getClanid() == 0) {
				return;
			}
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.CLAN_JOIN_SETTING, pc.getClan().getJoinSetting(), pc.getClan().getJoinType()), true);
		}
			break;
		case CLAN_JOIN: {
			/** 2016.11.25 MJ �ۼ��� ���� **/
			int joinType = readC();
			readH();
			int length = readC();

			// �������� �ʴ� �����Դϴ�.
			if (pc.isCrown()) {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 4), true);
				return;
			}

			// �̹� ���Ϳ� ������ ���� �Դϴ�.
			if (pc.getClanid() != 0) {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 9), true);
				return;
			}

			// ���ָ� ���� ������ �ּ���.
			try {
				String clanname = new String(readByteL(length), 0, length, "MS932");
				L1Clan clan = L1World.getInstance().getClan(clanname);
				//
				if (clan == null) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 13), true);
					return;
				}

				L1PcInstance crown = clan.getonline����();
				switch (clan.getJoinType()) {
				case 1:
					if (crown == null) {
						pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 11), true);
						return;
					}

					crown.setTempID(pc.getId()); // ����� ������Ʈ ID�� ������ �д�
					S_Message_YN myn = new S_Message_YN(97, pc.getName());
					crown.sendPackets(myn, true);
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 1), true);
					return;
				case 2:
					readD();
					readC();
					int size = readC();
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < size; i++)
						sb.append(String.format("%02X", readC()));
					if (clan.getJoinPassword() == null || !clan.getJoinPassword().equalsIgnoreCase(sb.toString())) {
						pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 3), true);
						return;
					}
				case 0:
					if (crown != null) {
						C_Attr.���Ͱ���(crown, pc, clan);
						pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 0), true);
					} else
						pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 1), true);
					break;
				default:
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 11), true);
					break;
				}
			} catch (Exception e) {
			}
		}
			break;
		case CLAN_OPTION_CHANGE: {
			if (pc.getClanid() == 0 || (!pc.isCrown() && pc.getClanRank() != L1Clan.CLAN_RANK_GUARDIAN)) {
				return;
			}

			readC();
			readH();
			int setting = readC();
			readC();
			int setting2 = readC();
			if (setting2 == 2) {
				pc.sendPackets(new S_SystemMessage("���� ��ȣ ���� �������� ������ �� �����ϴ�."), true);
				setting2 = 1;
			}

			pc.getClan().setJoinSetting(setting);
			pc.getClan().setJoinType(setting2);
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.CLAN_JOIN_SETTING, setting, setting2), true);
			ClanTable.getInstance().updateClan(pc.getClan());
			pc.sendPackets(new S_ServerMessage(3980), true);
		}
			break;
		case SPELL_PASSIVE_ONOFF_REQ: {
			try {
				readH();
				readC();
				int passive_id = readBit();
				readC();
				boolean on_off = readBit() == 1 ? true : false;
				if (passive_id == 38) {
					if (on_off) {
						pc.startBloodToSoul();
					} else {
						pc.stopBloodToSoul();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		case PARTY_SIGN: {
			try {

				int length = readH();

				byte[] BYTE = readByte();
				byte[] objid = new byte[length - 3];
				byte[] subtype = new byte[1];

				if (pc.getParty() == null) {
					return;
				}

				if (!pc.getParty().isLeader(pc)) {
					return;
				}

				System.arraycopy(BYTE, 1, objid, 0, objid.length);
				System.arraycopy(BYTE, length - 1, subtype, 0, 1);

				StringBuffer sb = new StringBuffer();

				for (byte zzz : objid) {
					sb.append(String.valueOf(zzz));
				}

				String s = sb.toString();

				L1PcInstance ǥ��pc = null;

				// System.out.println(s);

				for (L1PcInstance player : pc.getParty().getMembers()) {
					// System.out.println(player.encobjid);
					if (s.equals(player.encobjid)) {
						player.setǥ��(subtype[0]);
						ǥ��pc = player;
					}
				}

				if (ǥ��pc != null) {
					for (L1PcInstance player : pc.getParty().getMembers()) {
						player.sendPackets(new S_NewUI(0x53, ǥ��pc));
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		case ACCOUNT_TAM_CANCEL: {
			readC();
			readH();
			byte[] BYTE = readByte();
			byte[] temp = new byte[BYTE.length - 1];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = BYTE[i];
			}

			StringBuffer sb = new StringBuffer();
			for (byte zzz : temp) {
				sb.append(String.valueOf(zzz));
			}

			int day = Nexttam(sb.toString());
			int charobjid = TamCharid(sb.toString());
			if (charobjid != pc.getId()) {
				pc.sendPackets(new S_SystemMessage("�ش� �ɸ��͸� ��Ҹ� �� �� �ֽ��ϴ�."));
				return;
			}

			int itemid = 0;
			if (day != 0) {
				if (day == 7) {
					itemid = 5559;
				} else if (day == 30) {
					itemid = 5560;
				}
				L1ItemInstance item = pc.getInventory().storeItem(itemid, 1);
				if (item != null) {
					pc.sendPackets(new S_ServerMessage(403, item.getName() + " (1)"), true);
					tamcancle(sb.toString());
					pc.sendPackets(new S_NewCreateItem(pc.getAccountName(), 0xcd));
				}
			}
		}
			break;
		case ACCOUNT_TAM: {
			pc.sendPackets(new S_NewCreateItem(pc.getAccountName(), S_NewCreateItem.TamPage));
		}
			break;
		case TELEPORT_SKY_GARDEN: {
			if (!pc.PC��_����) {
				pc.sendPackets(new S_SystemMessage("PC�� �̿���� ����߿��� ��� ������ �ൿ�Դϴ�."), true);
				return;
			}

			if (pc.getMapId() == 99 || pc.getMapId() == 6202) {
				pc.sendPackets(new S_SystemMessage("������ ���¿����� �����̵��� ����� �� �����ϴ�."), true);
				return;
			}

			/*
			 * if (!pc.getMap().isTeleportable()) { pc.sendPackets(new
			 * S_SystemMessage("������ ���¿����� �����̵��� ����� �� �����ϴ�."), true); return; }
			 */

			int ran = _Random.nextInt(4);

			if (ran == 0) {
				L1Teleport.teleport(pc, 32779, 32825, (short) 622, pc.getMoveState().getHeading(), true);
			} else if (ran == 1) {
				L1Teleport.teleport(pc, 32761, 32819, (short) 622, pc.getMoveState().getHeading(), true);
			} else if (ran == 2) {
				L1Teleport.teleport(pc, 32756, 32837, (short) 622, pc.getMoveState().getHeading(), true);
			} else {
				L1Teleport.teleport(pc, 32770, 32839, (short) 622, pc.getMoveState().getHeading(), true);
			}
		}
			break;
		case CLAN_MEMBERS: {
			pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_WAIT, true), true);
		}
			break;
		case SIEGE_WAR_REQ: {
			try {
				readH();
				readC();
				int castleType = readC();
				// 1��Ʈ 2��� 4��ũ���
				String s = "";
				for (L1Clan cc : L1World.getInstance().getAllClans()) {
					if (castleType == cc.getCastleId()) {
						s = cc.getClanName();
						break;
					}
				}

				if (s.equalsIgnoreCase("")) {
					return;
				}

				L1PcInstance player = pc;
				String clanName = player.getClanname();
				int clanId = player.getClanid();

				if (!player.isCrown()) { // ���� �̿�
					S_ServerMessage sm = new S_ServerMessage(478);
					player.sendPackets(sm, true);
					return;
				}

				if (clanId == 0) { // ũ���̼Ҽ�
					S_ServerMessage sm = new S_ServerMessage(272);
					player.sendPackets(sm, true);
					return;
				}

				L1Clan clan = L1World.getInstance().getClan(clanName);
				if (clan == null) { // ��ũ���� �߰ߵ��� �ʴ´�
					S_SystemMessage sm = new S_SystemMessage("��� ������ �߰ߵ��� �ʾҽ��ϴ�.");
					player.sendPackets(sm, true);
					return;
				}

				if (player.getId() != clan.getLeaderId()) { // ������
					S_ServerMessage sm = new S_ServerMessage(478);
					player.sendPackets(sm, true);
					return;
				}

				if (clanName.toLowerCase().equals(s.toLowerCase())) { // ��ũ���� ����
					S_SystemMessage sm = new S_SystemMessage("�ڽ��� ���� ���� ������ �Ұ����մϴ�.");
					player.sendPackets(sm, true);
					return;
				}

				L1Clan enemyClan = null;
				String enemyClanName = null;
				for (L1Clan checkClan : L1World.getInstance().getAllClans()) { // ũ������ üũ
					if (checkClan.getClanName().toLowerCase().equals(s.toLowerCase())) {
						enemyClan = checkClan;
						enemyClanName = checkClan.getClanName();
						break;
					}
				}

				if (enemyClan == null) { // ��� ũ���� �߰ߵ��� �ʾҴ�
					S_SystemMessage sm = new S_SystemMessage("��� ������ �߰ߵ��� �ʾҽ��ϴ�.");
					player.sendPackets(sm, true);
					return;
				}

				if (clan.getAlliance(enemyClan.getClanId()) == enemyClan) {
					S_ServerMessage sm = new S_ServerMessage(1205);
					player.sendPackets(sm, true);
					return;
				}

				List<L1War> warList = L1World.getInstance().getWarList(); // ���� ����Ʈ�� ���
				if (clan.getCastleId() != 0) { // ��ũ���� ����
					S_ServerMessage sm = new S_ServerMessage(474);
					player.sendPackets(sm, true);
					return;
				}

				if (enemyClan.getCastleId() != 0 && // ��� ũ���� ���ַ�, ��ĳ���Ͱ�
													// Lv25 �̸�
						player.getLevel() < 25) {
					S_ServerMessage sm = new S_ServerMessage(475);
					player.sendPackets(sm, true); // �������� �����Ϸ��� ���� 25�� �̸���
													// ������ �ȵ˴ϴ�.
					return;
				}

				int onLineMemberSize = 0;
				for (L1PcInstance onlineMember : clan.getOnlineClanMember()) {
					if (onlineMember.isPrivateShop())
						continue;
					onLineMemberSize++;
				}

				if (onLineMemberSize < Config.warmember) {
					player.sendPackets(new S_SystemMessage("�������� ���� �������� " + Config.warmember + " �� �̻� �Ǿ�� ������ �����մϴ�."), true);
					return;
				}

				/*
				 * if (clan.getHouseId() > 0) { S_SystemMessage sm = new S_SystemMessage(
				 * "����Ʈ�� �ִ� ���¿����� ���� ���� �� �� �����ϴ�."); player.sendPackets(sm, true); return; }
				 */
				if (enemyClan.getCastleId() != 0) { // ��� ũ���� ����
					int castle_id = enemyClan.getCastleId();
					if (WarTimeController.getInstance().isNowWar(castle_id)) { // ���� �ð���
						L1PcInstance clanMember[] = clan.getOnlineClanMember();
						for (int k = 0; k < clanMember.length; k++) {
							if (L1CastleLocation.checkInWarArea(castle_id, clanMember[k])) {
								// S_ServerMessage sm = new
								// S_ServerMessage(477);
								// player.sendPackets(sm, true); // ����� ������
								// ��� ���Ϳ��� ���� �ۿ� ������ ������ �������� ������ �� �����ϴ�.
								int[] loc = new int[3];
								Random _rnd = new Random(System.nanoTime());
								loc = L1CastleLocation.getGetBackLoc(castle_id);
								int locx = loc[0] + (_rnd.nextInt(4) - 2);
								int locy = loc[1] + (_rnd.nextInt(4) - 2);
								short mapid = (short) loc[2];
								L1Teleport.teleport(clanMember[k], locx, locy, mapid, clanMember[k].getMoveState().getHeading(), true);
							}
						}

						boolean enemyInWar = false;
						for (L1War war : warList) {
							if (war.CheckClanInWar(enemyClanName)) { // ��� ũ���� �̹� ������
								war.DeclareWar(clanName, enemyClanName);
								war.AddAttackClan(clanName);
								enemyInWar = true;
								break;
							}
						}
						if (!enemyInWar) { // ��� ũ���� ������ �ܷ̿�, ��������
							L1War war = new L1War();
							war.handleCommands(1, clanName, enemyClanName); // ������ ����
						}
					} else { // ���� �ð���
						S_ServerMessage sm = new S_ServerMessage(476);
						player.sendPackets(sm, true); // ���� �������� �ð��� �ƴմϴ�.
					}
				} else { // ��� ũ���� ���ִ� �ƴϴ�
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		case ACTIONKEY: {
			readD();
			readC();
			int action = readC();
			if (action >= 1 && action <= 11) {
				pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.EMOTICON, action, pc.getId()), true);
				Broadcaster.broadcastPacket(pc, new S_ACTION_UI(S_ACTION_UI.EMOTICON, action, pc.getId()), true);
			}
		}
			break;
		case ATTENDANCE_CHECK_COMPLETE: {
			try {
				if (pc == null) {
					return;
				}

				if (pc.hasSkillEffect(L1SkillId.�⼮üũ�ǫ��쫤)) {
					return;
				}

				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.�⼮üũ�ǫ��쫤, 3000);
				readH();
				readC();

				int objectId = readBit();
				readC();
				int size = readBit();
				Account account = pc.getAccount();
				AttendanceTable attend = AttendanceTable.getInstance();

				boolean check_one = size == 0 ? account.getAttendanceHomeBytes()[objectId - 1] != 1 : account.getAttendancePcBytes()[objectId - 1] != 1;
				boolean check_two = size == 0 ? account.getAttendanceHomeBytes()[objectId - 1] == 2 : account.getAttendancePcBytes()[objectId - 1] == 2;

				if (check_one || check_two) {
					System.out.println("�ڡ١� �߰��(�⼮üũ) �ǽ� ���� : [" + account.getName() + "] �ڡ١�");
					return;
				}

				ArrayList<AttendanceItem> _item = size == 0 ? attend.getAttendHomeItem(objectId) : attend.getAttendPCItem(objectId);
				L1ItemInstance reward_item = null;
				AttendanceItem reward_info = null;
				int chance = CommonUtil.random(100);
				int reward_size = _item.size();

				for (int i = 0; i < reward_size; i++) {
					reward_info = _item.get(i);
					if (chance < reward_info._probability) {
						reward_item = ItemTable.getInstance().createItem(reward_info._itemId);
						if (reward_item != null) {
							if (reward_info._enchant != 0) {
								reward_item.setEnchantLevel(reward_info._enchant);
							}

							reward_item.setCount(reward_info._count);
							break;
						}
					}
				}

				if (size == 0) {
					account.getAttendanceHomeBytes()[objectId - 1] = 2;
					account.updateAttendaceDate();
					/** �⼮üũ ��� �Ϸ�� �ʱ�ȭ **/
					if (account.isAllHomeAttendCheck()) {
						byte[] b = new byte[42];
						account.setAttendanceHomeBytes(b);
					}

				} else {
					account.getAttendancePcBytes()[objectId - 1] = 2;
					account.updateAttendacePcDate();

					/** �⼮üũ ��� �Ϸ�� �ʱ�ȭ **/
					if (account.isAllPcAttendCheck()) {
						byte[] b = new byte[42];
						account.setAttendancePcBytes(b);
					}
				}

				pc.sendPackets(
						new S_ACTION_UI(S_ACTION_UI.ATTENDANCE_ITEM_COMPLETE, objectId, size == 0 ? false : true, reward_item, reward_info._broadcast_item));

				long delay_time = reward_size >= 2 ? 7000 : 0;
				/**
				 * �귿�ð� ������ ����
				 */
				AttendanceGiveItem agi = new AttendanceGiveItem(pc, reward_item, reward_info);
				GeneralThreadPool.getInstance().schedule(agi, delay_time);

				account.storeAttendBytes();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				clear();
			}
		}
			break;
		case ������ŷ: {
			readH();
			readC();
			int classId = readC();
			ArrayList<L1UserRanking> list = UserRankingController.getInstance().getList(classId);

			if (list.size() > 100) {
				List<L1UserRanking> cutlist = list.subList(0, 100);
				List<L1UserRanking> cutlist2 = list.subList(100, list.size() > 200 ? 200 : list.size());
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.������ŷ, cutlist, classId, 2, 1));
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.������ŷ, cutlist2, classId, 2, 2));
			} else {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.������ŷ, list, classId, 1, 1));
			}
		}
			break;
		case EXCLUDE: {
			readH();
			readC();
			int excludeType = readC();
			readC();
			int subType = readC();
			int nameFlag = readC();
			String name = "";
			if (nameFlag == 0x1A) {
				int nameLength = readC();
				name = readS(nameLength);
			}

			try {
				if (name.isEmpty()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.ADD_EXCLUDE2, pc.getExcludingList().getList(), 0), true);
					pc.sendPackets(new S_PacketBox(S_PacketBox.ADD_EXCLUDE2, pc.getExcludingLetterList().getList(), 1), true);
					return;
				}

				if (excludeType == 1) {// �߰�
					L1ExcludingList exList = pc.getExcludingList();
					L1ExcludingLetterList exletterList = pc.getExcludingLetterList();
					switch (subType) {// �Ϲ� 0 ���� 1
					case 0:
						if (exList.contains(name)) {
							/*
							 * String temp = exList.remove(name); S_PacketBox pb = new
							 * S_PacketBox(S_PacketBox.REM_EXCLUDE, temp, type); pc.sendPackets(pb, true);
							 * ExcludeTable.getInstance().delete(pc.getName( ), name);
							 */
						} else {
							if (exList.isFull()) {
								S_SystemMessage sm = new S_SystemMessage("���ܵ� ����ڰ� �ʹ� �����ϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							exList.add(name);
							S_PacketBox pb = new S_PacketBox(S_PacketBox.ADD_EXCLUDE, name, 0);
							pc.sendPackets(pb, true);
							ExcludeTable.getInstance().add(pc.getName(), name);
						}
						break;
					case 1:
						if (exletterList.contains(name)) {
						} else {
							if (exletterList.isFull()) {
								S_SystemMessage sm = new S_SystemMessage("���ܵ� ����ڰ� �ʹ� �����ϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							exletterList.add(name);
							S_PacketBox pb = new S_PacketBox(S_PacketBox.ADD_EXCLUDE, name, 1);
							pc.sendPackets(pb, true);
							ExcludeLetterTable.getInstance().add(pc.getName(), name);
						}
						break;
					default:
						break;
					}
				} else if (excludeType == 2) {// ����
					L1ExcludingList exList = pc.getExcludingList();
					L1ExcludingLetterList exletterList = pc.getExcludingLetterList();
					switch (subType) {// �Ϲ� 0 ���� 1
					case 0:
						if (exList.contains(name)) {
							String temp = exList.remove(name);
							S_PacketBox pb = new S_PacketBox(S_PacketBox.REM_EXCLUDE, temp, 0);
							pc.sendPackets(pb, true);
							ExcludeTable.getInstance().delete(pc.getName(), name);
						}
						break;
					case 1:
						if (exletterList.contains(name)) {
							String temp = exletterList.remove(name);
							S_PacketBox pb = new S_PacketBox(S_PacketBox.REM_EXCLUDE, temp, 1);
							pc.sendPackets(pb, true);
							ExcludeLetterTable.getInstance().delete(pc.getName(), name);
						}
						break;
					default:
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		case ����â��ŷ: {
			int Ÿ�� = readH();
			readC();
			long �ð� = read4(read_size());
			L1UserRanking classRank = UserRankingController.getInstance().getClassRank(pc.getType(), pc.getName());
			L1UserRanking rank = UserRankingController.getInstance().getTotalRank(pc.getName());
			if (classRank == null && rank == null) {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.����â��ŷ, classRank, rank, true));
			} else if (Ÿ�� == 2) {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.����â��ŷ, classRank, rank, true));
			} else if (Ÿ�� == 6 && UserRankingController.��ŷ���� >= �ð�) {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.����â��ŷ, classRank, rank, true));
			} else {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.����â��ŷ, classRank, rank, false));
			}
		}
			break;
		case FINISH_PLAY_SUPPORT: {
			pc.setAutoPlay(false);
			pc.sendPackets(new S_SupportSystem(pc, S_SupportSystem.SC_FINISH_PLAY_SUPPORT_ACK), true);
		}
			break;
		case START_PLAY_SUPPORT: {
			if (SupportMapTable.getInstance().isSupportMap(pc.getMapId())) {
				L1SupportMap SM = SupportMapTable.getInstance().getSupportMap(pc.getMapId());
				if (SM != null) {
					if (pc.getLevel() > 1 && pc.getLevel() <= Config.Ŭ����Ʒ���) {
						L1QuestInfo info = pc.getQuestList(368);
						if (info != null && info.end_time == 0) {
							info.ck[0] = 1;
							pc.sendPackets(new S_QuestTalkIsland(pc, 368, info));
						}
					}

					pc.setAutoPlay(true);
					pc.sendPackets(new S_SupportSystem(pc, S_SupportSystem.SC_START_PLAY_SUPPORT_ACK), true);
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�� �������� ����� �� �����ϴ�."), true);
			}
		}
			break;
		case WorldMapTeleport: {
			readC();
			readH();
			int chatlen = readBit(); // ����Ʈ ����Ʈ
			byte[] actionName = readByte(chatlen);
			String code = new String(actionName, "MS932");

			for (L1NpcInstance tel_map_npc : L1World.getInstance().getAllTeleporter()) {
				if (tel_map_npc.getNpcId() == 50036) {
					L1Object npc = L1World.getInstance().findObject(tel_map_npc.getId());
					L1NpcAction action = NpcActionTable.getInstance().get(code, pc, npc);
					if (pc.getInventory().checkItem(140100)) {
						pc.getInventory().consumeItem(140100, 1);
						action.execute(code, pc, npc, null);
						// System.out.println("message" + code);
					}
				}
			}
		}
			break;
		case EQUIPMENT_CHANGE: {
			readH();

			// readC();
			int index = readC();
			int code = readC();
			if (index == 0x08) {
				InvSwapController.getInstance().toSaveSet(pc, code);
			} else if (index == 0x10) {
				InvSwapController.getInstance().toChangeSet(pc, code);
			}
		}
			break;
		case CHAT: {
			int totallen = readH();// ��ü����
			��Ŷ��ġ����((byte) 0x10);// ��ġ�̵�
			int chattype = readC();// ä��Ÿ��
			��Ŷ��ġ����((byte) 0x1a);// ��ġ�̵�
			int chatlen = readC();// ä�ñ���
			BinaryOutputStream os = new BinaryOutputStream();

			for (int i = 0; i < chatlen; i++) {
				os.writeC(readC());
			}

			byte[] chat = os.getBytes();
			String chat2 = new String(chat, "EUC-KR");
			os.close();
			String name = "";

			if (chattype != 1) {
				Chat(pc, chattype, totallen, chat, chat2, client);
				return;
			}

			��Ŷ��ġ����((byte) 0x2a);// ��ġ�̵�
			int namelen = readC();// �̸�����
			if (namelen != 0) {
				name = readS(namelen);
			}

			ChatWhisper(pc, chattype, totallen, chat, chat2, name);
		}
			break;
		case ENVIRONMENT_SETTING: {
			if (pc == null) {
				return;
			}

			if (pc.isOneMinuteStart()) {
				resetAttendanceTime(pc.getAccount(), pc);
				getAttendanceHome(pc, pc.getAccount());
				getAttendancePCRoom(pc, pc.getAccount());
			}

			if (!pc.isOneMinuteStart()) {
				pc.setOneMinuteStart(true);
			}
		}
			break;
		case RESTART_UI: {
			pc.sendPackets(new S_CharMapTime(pc));
		}
			break;
		case ServerVersion: {
			client.sendPacket(new S_ServerVersion(S_ServerVersion.SERVER_VERSION, 2004281703, S_ServerVersion.RESULT_OK));
		}
			break;
		case ServerInter: {
			try {
				int ServerInterTotal = readH();
				String Name = null;

				for (int i = 0; i < ServerInterTotal; i++) {
					if (readLength() == 0) {
						break;
					}

					int Type = readC(), Length = 0;
					if (Type == 0x08) {
						read4(read_size());
					} else if (Type == 0x12) {
						Length = readC();
						Name = readS(Length);
					} else {
						break;
					}
				}

				L1PcInstance Player = L1World.getInstance().getPlayer(Name);
				client.setInterServer(Player.getNetConnection().getInterServer());
				client.setInterServerType(Player.getNetConnection().getInterServerType());
				client.setInterServerName(Player.getName());
				client.setInterServerParty(Player.getParty());
				client.setInterServerNotice(Player.getǥ��());

				client.setAccount(Player.getNetConnection().getAccount());
				LoginController.getInstance().login(client, client.getAccount());

				Player.getNetConnection().ServerInterKick();
				InterServer.RequestInterServer(client);

				return;
			} catch (Exception e) {
				e.printStackTrace();
				/** ���� �������� ���� ���� */
				client.kick();
			}
		}
			break;
		case ���λ���: {
			if (pc == null || pc.isGhost() || pc.isDead()) {
				return;
			}
			if (pc.isInvisble()) {
				pc.sendPackets(new S_ServerMessage(755), true);
				return;
			}
			if (pc.getMapId() != 800) {
				pc.sendPackets(new S_ServerMessage(3405)); // ���� �Ұ� ����
				return;
			}

			if (Config.STANDBY_SERVER) {
				pc.sendPackets(new S_SystemMessage("���´���߿��� ����� �� �����ϴ�."), true);
				pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
				Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
				break;
			}

			for (L1PcInstance target : L1World.getInstance().getAllPlayers()) {
				if (target.getId() != pc.getId() && target.getAccountName().toLowerCase().equals(pc.getAccountName().toLowerCase()) && target.isPrivateShop()) {
					pc.sendPackets(new S_SystemMessage("\\aH�˸�: �̹� ���λ����� ���� �Ǿ� �ֽ��ϴ� ."), true);
					return;
				}
			}

			ArrayList<L1PrivateShopSellList> sellList = pc.getSellList();
			ArrayList<L1PrivateShopBuyList> buyList = pc.getBuyList();
			L1ItemInstance checkItem;
			boolean tradable = true;

			readC();
			int length = readBit();

			readC();
			readC();
			int shoptype = readC(); // 0 or 1

			if (shoptype == 0) { // ����
				int sellTotalCount = 0;
				int buyTotalCount = 0;
				int sellObjectId = 0;
				int sellPrice = 0;
				int sellCount = 0;
				L1ItemInstance sellitem = null;
				Object[] petlist = null;
				L1ItemInstance buyitem = null;
				for (int i = 0; i < length; i++) {
					int code = readC();
					if (code == 0x12 || code == 0x1a) {
						readC();
						for (int i2 = 0; i2 < 3; i2++) {
							int code2 = readC();
							if (code2 == 0x08)
								sellObjectId = readBit();
							else if (code2 == 0x10)
								sellPrice = readBit();
							else if (code2 == 0x18)
								sellCount = readBit();
						}
						// �ŷ� ������ �������̳� üũ
						checkItem = pc.getInventory().getItem(sellObjectId);

						if (checkItem == null) {
							continue;
						}

						if (sellObjectId != checkItem.getId()) {
							tradable = false;
							pc.sendPackets(new S_SystemMessage("\\aH�˸�: ������ ������ �Դϴ�. �ٽ� �õ����ּ���."), true);
						}
						if (!checkItem.isStackable() && sellCount != 1) {
							tradable = false;
							pc.sendPackets(new S_SystemMessage("\\aH�˸�: ������ ������ �Դϴ�. �ٽ� �õ����ּ���."), true);
						}
						if (sellCount > checkItem.getCount()) {
							sellCount = checkItem.getCount();
						}
						if (checkItem.getCount() < sellCount || checkItem.getCount() <= 0 || sellCount <= 0) {
							tradable = false;
							pc.sendPackets(new S_SystemMessage("\\aH�˸�: ������ ������ �Դϴ�. �ٽ� �õ����ּ���."), true);
						}
						if (checkItem.getBless() >= 128) {
							tradable = false;
							pc.sendPackets(new S_ServerMessage(210, checkItem.getItem().getName())); // ���λ���
						}

						if (!checkItem.getItem().isTradable()) {
							tradable = false;
							pc.sendPackets(new S_ServerMessage(941), true); // �ŷ� �Ұ� �������Դϴ�.
						}

						L1DollInstance ���� = null;
						for (Object ����������Ʈ : pc.getDollList()) {
							if (����������Ʈ instanceof L1DollInstance) {
								���� = (L1DollInstance) ����������Ʈ;
								if (checkItem.getId() == ����.getItemObjId()) {
									tradable = false;
									pc.sendPackets(new S_ServerMessage(941), true); // �ŷ� �Ұ� �������Դϴ�.
								}
							}
						}

						petlist = pc.getPetList().toArray();
						for (Object petObject : petlist) {
							if (petObject instanceof L1PetInstance) {
								L1PetInstance pet = (L1PetInstance) petObject;
								if (checkItem.getId() == pet.getItemObjId()) {
									tradable = false;
									pc.sendPackets(new S_ServerMessage(166, checkItem.getItem().getName(), "\\aH�˸�: �ŷ��� �Ұ��� �մϴ�."));
									return;
								}
							}
						}

						if (code == 0x12) {
							if (sellTotalCount > 7) {
								pc.sendPackets(new S_SystemMessage("\\aH�˸�: ��ǰ�� 7�� ���� ��� �����մϴ�."));
								return;
							}

							L1PrivateShopSellList pssl = new L1PrivateShopSellList();
							pssl.setItemObjectId(sellObjectId);
							pssl.setSellPrice(sellPrice);
							pssl.setSellTotalCount(sellCount);
							sellList.add(pssl);
							sellTotalCount++;
						} else if (code == 0x1a) {
							if (buyTotalCount > 7) {
								pc.sendPackets(new S_SystemMessage("\\aH�˸�: ��ǰ�� 7�� ���� ��� �����մϴ�."));
								return;
							}
							L1PrivateShopBuyList psbl = new L1PrivateShopBuyList();
							psbl.setItemObjectId(sellObjectId);
							psbl.setBuyPrice(sellPrice);
							psbl.setBuyTotalCount(sellCount);
							buyList.add(psbl);
							buyTotalCount++;
						}
					} else {
						break;
					}
				}

				if (sellTotalCount == 0 && buyTotalCount == 0) {
					pc.sendPackets(new S_ServerMessage(908), true);
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle), true);
					Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle), true);
					return;
				}

				if (!tradable) { // �ŷ� �Ұ����� �������� ���ԵǾ� �ִ� ���, ���� ���� ����
					sellList.clear();
					buyList.clear();
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle), true);
					Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle), true);
					return;
				}

				/** ������ �ΰ� **/
				int shopOpenCount = pc.getNetConnection().getAccount().Shop_open_count;
				if (shopOpenCount >= 40) {
					int OpenAdena = 20000 + ((shopOpenCount - 40) * 1000);
					if (!pc.getInventory().consumeItem(40308, OpenAdena)) {
						sellList.clear();
						buyList.clear();
						pc.setPrivateShop(false);
						pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
						Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
						pc.sendPackets(new S_ServerMessage(189), true); // �Ƶ����� �����մϴ�
						return;
					} else {
						pc.getInventory().consumeItem(40308, OpenAdena);
					}
				} else {
					if (!pc.getInventory().consumeItem(40308, 1000)) {
						sellList.clear();
						buyList.clear();
						pc.setPrivateShop(false);
						pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
						Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
						pc.sendPackets(new S_ServerMessage(189), true); // �Ƶ����� �����մϴ�
						return;
					} else {
						pc.getInventory().consumeItem(40308, 1000);
					}
				}

				int l1 = readC();
				byte[] chat = readByte(l1);

				readC();
				int l2 = readC();
				String polynum = readS(l2);

				pc.setShopChat(chat);

				pc.setPrivateShop(true);

				pc.sendPackets(new S_DoActionShop(pc.getId(), ActionCodes.ACTION_Shop, chat), true);
				Broadcaster.broadcastPacket(pc, new S_DoActionShop(pc.getId(), ActionCodes.ACTION_Shop, chat), true);

				try {
					for (L1PrivateShopSellList pss : pc.getSellList()) {
						int sellp = pss.getSellPrice();
						int sellc = pss.getSellTotalCount();
						sellitem = pc.getInventory().getItem(pss.getItemObjectId());
						if (sellitem == null)
							continue;
						pc.�ӽ�SaveShop(pc, sellitem, sellp, sellc, 1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					for (L1PrivateShopBuyList psb : pc.getBuyList()) {
						int buyp = psb.getBuyPrice();
						int buyc = psb.getBuyTotalCount();
						buyitem = pc.getInventory().getItem(psb.getItemObjectId());
						if (buyitem == null)
							continue;
						pc.�ӽ�SaveShop(pc, buyitem, buyp, buyc, 0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					int polyId = 0;
					if (polynum.equalsIgnoreCase("tradezone1"))
						polyId = 11326;
					else if (polynum.equalsIgnoreCase("tradezone2"))
						polyId = 11427;
					else if (polynum.equalsIgnoreCase("tradezone3"))
						polyId = 10047;
					else if (polynum.equalsIgnoreCase("tradezone4"))
						polyId = 9688;
					else if (polynum.equalsIgnoreCase("tradezone5"))
						polyId = 11322;
					else if (polynum.equalsIgnoreCase("tradezone6"))
						polyId = 10069;
					else if (polynum.equalsIgnoreCase("tradezone7"))
						polyId = 10034;
					else if (polynum.equalsIgnoreCase("tradezone8"))
						polyId = 10032;

					if (polyId != 0) {
						pc.getSkillEffectTimerSet().killSkillEffectTimer(67);
						L1PolyMorph.undoPoly(pc);
						L1ItemInstance weapon = pc.getWeapon();
						if (weapon != null)
							pc.getInventory().setEquipped(weapon, false, false, false);
						pc.getGfxId().setTempCharGfx(polyId);
						pc.sendPackets(new S_ChangeShape(pc.getId(), polyId, pc.getCurrentWeapon()));
						if ((!pc.isGmInvis()) && (!pc.isInvisble())) {
							Broadcaster.broadcastPacket(pc, new S_ChangeShape(pc.getId(), polyId));
						}
						S_CharVisualUpdate charVisual = new S_CharVisualUpdate(pc, 0x46);
						pc.sendPackets(charVisual);
						Broadcaster.broadcastPacket(pc, charVisual);
					}
					pc.getNetConnection().getAccount().updateShopOpenCount();
					pc.sendPackets(new S_PacketBox(S_PacketBox.��������Ƚ��, pc.getNetConnection().getAccount().Shop_open_count), true);
					pc.sendPackets(new S_ChatPacket(pc, "\\aH�˸�: .���λ��� �Է½� ���λ��� ���� ."));
				} catch (Exception e) {
					pc.���������ۻ���(pc.getId());
					sellList.clear();
					buyList.clear();
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle), true);
					Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle), true);
					return;
				}
				petlist = null;
			} else if (shoptype == 1) { // ����
				if (isTwoLogin(pc)) {
					pc.sendPackets(new S_Disconnect());
				}
				sellList.clear();
				buyList.clear();
				pc.setPrivateShop(false);
				pc.sendPackets(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
				pc.broadcastPacket(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle));
				L1PolyMorph.undoPoly(pc);
				try {
					pc.���������ۻ���(pc.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			break;

		case Party_Assist: {
			readC();
			readH();
			int Object = read4(read_size());
			if (pc.isInParty()) {
				pc.getParty().updateAssist(pc, Object);
			}
		}
			break;
		case Party_Invitation: {
			readC();
			readH();
			int Type = readC();
			L1PcInstance Player = null;
			if (Type != 2 && Type != 4 && Type != 5 && Type != 7) {
				readC();
				L1Object target = L1World.getInstance().findObject(readBit());
				if (target != null)
					Player = (L1PcInstance) target;
			}
			readC();
			int NameLength = readC();
			if (Player == null) {
				Player = L1World.getInstance().getPlayer(readS(NameLength));
			} else
				readS(NameLength);
			if (Player == null)
				return;
			/** 0�� �Ϲ� ��Ƽ 1�� �й���Ƽ 2�� ä����Ƽ 5/�ʴ� �̸� 6�� ǥ�� 7���߹� 8����Ƽ���� */
			if (Type == 0 || Type == 1 || Type == 4 || Type == 5) {
				if (Player instanceof L1PcInstance) {
					if (pc.getId() == Player.getId())
						return;
					if (Player.isInParty()) {
						/** ���� �ٸ� ��Ƽ�� �Ҽ��� �ֱ� (����)������ �ʴ��� �� �����ϴ� */
						pc.sendPackets(new S_ServerMessage(415), true);
						return;
					}

					if (pc.isInParty()) {
						if (pc.getParty().isLeader(pc)) {
							Player.setPartyID(pc.getId());
							/** \f2%0\f>%s�κ��� \fU��Ƽ \f> �� �ʴ�Ǿ����ϴ�. ���մϱ�? (Y/N) */
							Player.sendPackets(new S_Message_YN(953, pc.getName()), true);
						} else {
							/** ��Ƽ�� �������� �ʴ��� �� �ֽ��ϴ�. */
							pc.sendPackets(new S_ServerMessage(416), true);
						}
					} else {
						Player.setPartyID(pc.getId());
						switch (Type) {
						case 4:
						case 0:
							pc.setPartyType(0);
							/** \f2%0\f>%s�κ��� \fU��Ƽ \f> �� �ʴ�Ǿ����ϴ�. ���մϱ�? (Y/N) */
							Player.sendPackets(new S_Message_YN(953, pc.getName()), true);
							break;
						case 5:
						case 1:
							pc.setPartyType(1);
							/** \f2%0\f>%s \fU�ڵ��й���Ƽ\f> �ʴ��Ͽ����ϴ�. ����Ͻðڽ��ϱ�? (Y/N) */
							Player.sendPackets(new S_Message_YN(954, pc.getName()), true);
							break;
						}
					}
				}
			} else if (Type == 2) { // ä�� ��Ƽ
				if (pc.getId() == Player.getId())
					return;
				if (Player.isInChatParty()) {
					/** ���� �ٸ� ��Ƽ�� �Ҽ��� �ֱ� (����)������ �ʴ��� �� �����ϴ� */
					pc.sendPackets(new S_ServerMessage(415), true);
					return;
				}

				if (pc.isInChatParty()) {
					if (pc.getChatParty().isLeader(pc)) {
						Player.setPartyID(pc.getId());
						/** \f2%0\f>%s�κ���\fUä�� ��Ƽ \f>�� �ʴ�Ǿ����ϴ�. ���մϱ�? (Y/N) */
						Player.sendPackets(new S_Message_YN(951, pc.getName()), true);
					} else {
						/** ��Ƽ�� �������� �ʴ��� �� �ֽ��ϴ�. */
						pc.sendPackets(new S_ServerMessage(416), true);
					}
				} else {
					Player.setPartyID(pc.getId());
					/** \f2%0\f>%s�κ���\fUä�� ��Ƽ \f>�� �ʴ�Ǿ����ϴ�. ���մϱ�? (Y/N) */
					Player.sendPackets(new S_Message_YN(951, pc.getName()), true);
				}
			} else if (Type == 3) { // ����
				/** ��Ƽ���� �ƴϰų� ��������̶�� �ȵǵ��� ���� */
				if (!pc.getParty().isLeader(pc) || pc.getId() == Player.getId()) {
					return;
				}
				/** ���� �ٸ��ų� 10���� ���϶�� ���� �Ұ��� �ϵ��� ���� */
				/*
				 * if (!isDistance(pc.getX(), pc.getY(), pc.getMapId(), Player.getX(),
				 * Player.getY(), Player.getMapId(), 10)){ pc.sendPackets(new
				 * S_ServerMessage(1695)); return; }
				 */
				pc.getParty().passLeader(Player);
			} else if (Type == 6) {
				readC();
				Player.setǥ��(readC());
				for (L1PcInstance player : pc.getParty().getMembers()) {
					player.sendPackets(new S_Party(0x6e, Player));
				}
			} else if (Type == 7) { // �߹�
				if (!pc.getParty().isLeader(pc)) {
					/** ��Ƽ�� �������� �߹��� �� �ֽ��ϴ�. */
					pc.sendPackets(new S_ServerMessage(427));
					return;
				}
				pc.getParty().leaveMember(Player);
			}
		}
			break;
		case SEAL:
			readC();
			readH();
			L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(read4(read_size()));
			if (l1iteminstance1.getItem().getType2() == 0) { // etc �������̶��
				pc.sendPackets(new S_ServerMessage(79)); // �ƹ��ϵ� �Ͼ�� �ʴ´� (��Ʈ)
				return;
			}
			if (l1iteminstance1.getBless() == 0 || l1iteminstance1.getBless() == 1 || l1iteminstance1.getBless() == 2 || l1iteminstance1.getBless() == 3) {
				int Bless = 0;
				switch (l1iteminstance1.getBless()) {
				case 0:
					Bless = 128;
					break; // ��
				case 1:
					Bless = 129;
					break; // ����
				case 2:
					Bless = 130;
					break; // ����
				case 3:
					Bless = 131;
					break; // ��Ȯ��
				}
				l1iteminstance1.setBless(Bless);
				int st = 0;
				if (l1iteminstance1.isIdentified())
					st += 1;
				if (!l1iteminstance1.getItem().isTradable())
					st += 2;
				if (l1iteminstance1.getItem().isCantDelete())
					st += 4;
				if (l1iteminstance1.getItem().get_safeenchant() < 0)
					st += 8;
				if (l1iteminstance1.getBless() >= 128) {
					st = 32;
					if (l1iteminstance1.isIdentified()) {
						st += 15;
					} else {
						st += 14;
					}
				}
				pc.sendPackets(new S_PacketBox(S_PacketBox.ITEM_, l1iteminstance1, st));
				pc.getInventory().updateItem(l1iteminstance1, L1PcInventory.COL_IS_ID);
				pc.getInventory().saveItem(l1iteminstance1, L1PcInventory.COL_IS_ID);
			} else
				pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
			break;
		case DOLL_START: {
			
			int doll_obj_id = 0;
			if(pc.getDollListSize() > 0) {
				for(L1DollInstance doll : pc.getDollList()) {
					doll_obj_id = doll.getId();
					break;
				}
			}
			
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_READY, doll_obj_id));
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_START));
			break;
		}
		case DOLL_RESULT: {
			int total = (readH() - 2) / 12;
			readC();
			int step = readC();
			int[] dollids = new int[total];
			L1ItemInstance item;
			for (int i = 0; i < total; i++) {
				readC();
				readD();
				read4(read_size());
				readC();
				int objid = read4(read_size());

				item = pc.getInventory().getItem(objid);
				if (item == null) {
					return;
				}
				dollids[i] = item.getItemId();
				pc.getInventory().removeItem(item);
			}
			int chance2 = ((total * Config.����Ȯ��2)); // �����ռ� Ȯ�� ����
			int chance3 = ((total * Config.����Ȯ��3)); // �����ռ� Ȯ�� ����
			int chance4 = ((total * Config.����Ȯ��4)); // �����ռ� Ȯ�� ����
			int chance5 = ((total * Config.����Ȯ��5)); // �����ռ� Ȯ�� ����
			switch (step) {
			case 1:
				if (CommonUtil.random(100) + 1 <= chance2) {
					dollids = new int[] { 430001, // ���
							41249, // ��ť����
							430500, // ��īƮ����
							500109, // �����(A) ?
							500108, // �ξ�
							600242 // ��ٰ�
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
					if (step >= 3) {
						try {
							Thread.sleep(10000);
							L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 4433, item), true);
						} catch (Exception e) {
						}
					}
				} else {
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, false, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
				}
				break;
			case 2:
				if (CommonUtil.random(100) + 1 <= chance3) {
					dollids = new int[] { 500205, // ��ť ��
							500204, // �����
							500203, // ���̾�Ʈ
							60324, // �巹��ũ
							500110, // ŷ ���׺���
							600243 // ���̾Ƹ���
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
					if (step >= 3) {
						try {
							Thread.sleep(10000);
							// L1World.getInstance().broadcastPacketToAll(new S_ServerMessage(4433,
							// item.getItem().getNameId(), pc.getName()));
							L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 4433, item), true);
						} catch (Exception e) {
						}
					}
				} else {
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, false, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
				}
				break;
			case 3:
				if (CommonUtil.random(100) + 1 <= chance4) {
					dollids = new int[] { 500202, // ����Ŭ�ӽ�
							5000035, // ��ġ
							600245, // ����Ʈ�ߵ�
							600244, // �þ�
							142920, // ���̸���
							142921, // �����̾�
							751 // �ӹ̷ε�
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
					if (step >= 3) {
						try {
							Thread.sleep(10000);
							L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 4433, item), true);
						} catch (Exception e) {
						}
					}
				} else {
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, false, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
				}
				break;
			case 4:// ������ ������ �ܰ�
				if (CommonUtil.random(100) + 1 <= chance5) {
					dollids = new int[] { 600246, // ����
							600247, // ��������Ʈ 746
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							754, // ���
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
					if (step >= 3) {
						try {
							Thread.sleep(10000);
							L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 4433, item), true);
						} catch (Exception e) {
						}
					}
				} else {
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, false, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
				}
				break;
			case 5:
				if (CommonUtil.random(100) + 1 <= 50) {
					dollids = new int[] { 600321, // ����
							600322, // ��������Ʈ 746
							600323, // �ٶ�ī
							600324, // Ÿ��
							600325, // ����
							600326, // ���
							600327, // Ŀ��
							142922, // �ٶ�ī
							600327, // Ŀ��
							142922, // �ٶ�ī
							600324, // Ÿ��
							600325, // ����
							600327, // Ŀ��
							142922, // �ٶ�ī
							600324, // Ÿ��
							600325, // ����
							600327, // Ŀ��
							142922, // �ٶ�ī
							600324, // Ÿ��
							600325, // ����
							600327, // Ŀ��
							142922, // �ٶ�ī
							600324, // Ÿ��
							600325, // ����
							600327, // Ŀ��
							142922, // �ٶ�ī
							600324, // Ÿ��
							600325, // ����
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), true));
					pc.getInventory().storeItem(item);
					if (step >= 3) {
						try {
							Thread.sleep(10000);
							L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 4433, item), true);
						} catch (Exception e) {
						}
					}
				} else {
					dollids = new int[] { 600246, // ����
							600247, // ��������Ʈ 746
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							142922, // �ٶ�ī
							752, // Ÿ��
							753, // ����
							755, // Ŀ��
							600259, // ��Ÿ
							600261, // ����
							600260, // ��Ǫ
							600262 // �߶�
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
					if (step >= 3) {
						try {
							Thread.sleep(10000);
							L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 4433, item), true);
						} catch (Exception e) {
						}
					}
				}
				break;
			case 6:
				if (CommonUtil.random(100) + 1 <= 50) {
					dollids = new int[] { 600308, // ������
							600309, // �����̾�Ʈ
							600310, // �༭ť
							600311, // ��巹��
							600312, // ��ŷ����
							600313 // ����̾Ƹ���
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), true));
					pc.getInventory().storeItem(item);
				} else {
					dollids = new int[] { 500205, // ��ť ��
							500204, // �����
							500203, // ���̾�Ʈ
							60324, // �巹��ũ
							500110, // ŷ ���׺���
							600243 // ���̾Ƹ���
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
				}
				break;
			case 7:
				if (CommonUtil.random(100) + 1 <= 40) {
					dollids = new int[] { 600314, // �ฮġ
							600315, // ��þ�
							600316, // �೪��
							600317, // ��þ�
							600318, // ����̸���
							600319, // �����
							600320 // ��ӹ�
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), true));
					pc.getInventory().storeItem(item);
					if (step >= 3) {
						try {
							Thread.sleep(10000);
							L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 4433, item), true);
						} catch (Exception e) {
						}
					}
				} else {
					dollids = new int[] { 500202, // ����Ŭ�ӽ�
							5000035, // ��ġ
							600245, // ����Ʈ�ߵ�
							600244, // �þ�
							142920, // ���̸���
							142921, // �����̾�
							751, // �ӹ̷ε�
					};
					item = ItemTable.getInstance().createItem(dollids[CommonUtil.random(dollids.length)]);
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.DOLL_RESULT, true, item.getId(), item.get_gfxid(), false));
					pc.getInventory().storeItem(item);
					if (step >= 3) {
						try {
							Thread.sleep(10000);
							L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 4433, item), true);
						} catch (Exception e) {
						}
					}
				}
				break;
			}
		}
			break;
		/** �� ���� ȣ�� */
		case InDungeon: {
			pc.getInventory().CheckItemSkill();
			pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeon), true);
			break;
		}

		case InDungeStart: {
			readH();
			readC();
			int RoomNumber = read4(read_size());
			DungeonInfo DungeonInfo = DungeonSystem.isDungeonInfo(RoomNumber);

			DungeonInfo.InPlaygame = true;
			pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeStart, 1, RoomNumber), true);
			IND_Q.requestWork(new IND(pc.getName(), DungeonInfo.TypeNumber == 201 ? 3 : 4, DungeonInfo));
			// ����ŸƮ ���°� �ƴϸ� �޼��� ������ ����
			/*
			 * if (DungeonInfo == null) { pc.sendPackets(new
			 * S_EventNotice(S_EventNotice.InDungeStart, 7, RoomNumber), true); return;
			 * }else if(DungeonInfo.isDungeonInfoCheck()){ pc.sendPackets(new
			 * S_EventNotice(S_EventNotice.InDungeStart, 8, RoomNumber), true); return;
			 * }else if (DungeonInfo.DungeonList.size() < DungeonInfo.MinSize) {
			 * pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeStart, 6, RoomNumber),
			 * true); return; }else{ for(L1PcInstance PcList :
			 * DungeonInfo.isDungeonInfoUset()){
			 * if(!DungeonInfo.isDungeonInfoCheck(PcList)){ pc.sendPackets(new
			 * S_EventNotice(S_EventNotice.InDungeStart, 8, RoomNumber), true); return; } }
			 * DungeonInfo.InPlaygame = true; pc.sendPackets(new
			 * S_EventNotice(S_EventNotice.InDungeStart, 1, RoomNumber), true);
			 * IND_Q.requestWork(new IND(pc.getName(), DungeonInfo.TypeNumber == 201 ? 3 :
			 * 4, DungeonInfo)); }
			 */
			break;
		}

		case InDungeonOut: {
			readH();
			readC();
			int RoomNumber = read4(read_size());
			DungeonInfo DungeonInfo = DungeonSystem.isDungeonInfo(RoomNumber);
			readC();
			L1PcInstance OutUse = DungeonInfo.isDungeonInfoCheckUset(read4(read_size()));
			OutUse.sendPackets(new S_EventNotice(S_EventNotice.InDungeExit, 1, RoomNumber), true);
			OutUse.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOut, OutUse.getId(), RoomNumber), true);
			OutUse.isDungeonTeleport(false);
			DungeonInfo.setUser(pc);

			for (L1PcInstance PcList : DungeonInfo.isDungeonInfoUset()) {
				PcList.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOutUse, DungeonInfo, OutUse), true);
				PcList.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOutExit, DungeonInfo, OutUse), true);
				PcList.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOpen, DungeonInfo, null), true);
			}

			break;
		}

		case InDungeoninvite: {
			int InDungeonTotal = readH();
			int RoomNumber = 0, Object = 0;
			String UseName = null;

			for (int i = 0; i < InDungeonTotal; i++) {
				if (readLength() == 0) {
					break;
				}

				int Type = readC(), Length = 0;

				if (Type == 0x08) {
					RoomNumber = read4(read_size());
				} else if (Type == 0x10) {
					Object = read4(read_size());
				} else if (Type == 0x1a) {
					Length = readC();
					UseName = readS(Length);
				} else {
					break;
				}
			}

			L1PcInstance Player = null, PlayerUse = null;
			L1Object PlayerObject = L1World.getInstance().findObject(Object);

			if (PlayerObject != null) {
				Player = (L1PcInstance) PlayerObject;
			}

			if (UseName != null) {
				PlayerUse = L1World.getInstance().getPlayer(UseName);
			}

			if (Player != null && PlayerUse != null) {
				PlayerUse.sendPackets(new S_EventNotice(S_EventNotice.InDungeoninvite, RoomNumber, Player.getName(), PlayerUse.getName()), true);
			}

			break;
		}

		/** �� ����Ʈ ���� */
		case InDungeonList: {
			int ListLength = readH();
			readH();
			readC();
			/** �� ���� ���� */
			if (ListLength == 2) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonList, 0, 0), true);
				/** �� Ŭ���� ���� ���� */
			} else {
				int InDungeonNumber = read4(read_size());
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonList, InDungeonNumber, 0), true);
			}

			break;
		}

		/** �� �輳 ���� */
		case InDungeonType: {
			/** ���谡 �ִ��� üũ �κ� ���谡 ���ٸ� �޼����� ��� */
			if (!pc.getInventory().checkItem(500021, 1)) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonType, 15, 0), true);
				return;
			}

			/** ���ó� ���� ���¶�� ���� ���� �ع����� */
			if (pc.isPrivateShop() && pc.isFishing()) {
				return;
			}

			readH();
			readC();
			/** ��ü ������ ��� */
			int InDungeonTotal = readC();
			DungeonInfo DungeonInfo = new DungeonInfo();
			DungeonInfo.RoomNumber = DungeonSystem.getDungeonInfo();

			for (int i = 0; i < InDungeonTotal; i++) {
				if (readLength() == 0) {
					break;
				}

				int Type = readC(), Length = 0;
				if (Type == 0x0a) {
					Length = readC();
					DungeonInfo.Title = readS(Length);
					Length = Length + 1;
				} else if (Type == 0x10) {
					DungeonInfo.Level = read4(read_size());
					Length = getBitSize(DungeonInfo.Level);
					/** �Һ� �Ƶ��� */
				} else if (Type == 0x18) {
					DungeonInfo.Adena = read4(read_size());
					Length = getBitSize(DungeonInfo.Adena);
					/** �̰� �׳� �⺻ 0 ���θ� �� */
				} else if (Type == 0x20) {
					DungeonInfo.Type = read4(read_size()) == 0 ? 1 : 2;
					Length = getBitSize(DungeonInfo.Type);
					/** �ڵ� �й� */
				} else if (Type == 0x28) {
					DungeonInfo.Division = read4(read_size());
					Length = getBitSize(DungeonInfo.Division);
					/** ���� ����� */
				} else if (Type == 0x30) {
					DungeonInfo.Open = read4(read_size());
					Length = getBitSize(DungeonInfo.Open);
					/** �� �ο��� ���� 6�� */
				} else if (Type == 0x38) {
					DungeonInfo.MaxSize = read4(read_size());
					DungeonInfo.MinSize = 4;
					Length = getBitSize(DungeonInfo.MaxSize);
					/** �� ��� ��ȣ ���ٸ� 0 */
				} else if (Type == 0x42) {
					Length = readC();
					DungeonInfo.OpenPassword = readS(Length);
					Length = Length + 1;
					/** �� Ÿ�� (���� �ѹ�) */
				} else if (Type == 0x48) {
					DungeonInfo.TypeNumber = read4(read_size());
					Length = getBitSize(DungeonInfo.TypeNumber);
				}

				i += Length;
			}

			/** �� ������ ���� ��Ŷ �� ���� ������ ���� ���� */
			DungeonInfo.setDungeonLeadt(pc.getId());
			DungeonInfo.setDungeonInfoCheck(pc, true);
			DungeonInfo.setUser(pc);
			pc.isDungeonTeleport(true);
			pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonType, 1, DungeonInfo.RoomNumber), true);

			DungeonSystem.getDungeonInfo(DungeonInfo.RoomNumber, DungeonInfo);
			pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOpen, DungeonInfo, null), true);
			pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonSetUp, DungeonInfo, null), true);

			break;
		}

		/** ���� ���� ��Ŷ �ε� */
		case InDungeonSlot: {
			/** ��ü ������ ��� */
			int InDungeonTotal = readH();
			DungeonInfo DungeonInfo = null;
			for (int i = 0; i < InDungeonTotal; i++) {

				if (readLength() == 0) {
					break;
				}

				int Type = readC(), Length = 0;

				if (Type == 0x08) {
					int RoomNumber = read4(read_size());
					DungeonInfo = DungeonSystem.isDungeonInfo(RoomNumber);
					Length = getBitSize(RoomNumber);
				} else if (Type == 0x12) {
					Length = readC();
					DungeonInfo.Title = readS(Length);
					Length = Length + 1;
				} else if (Type == 0x18) {
					DungeonInfo.Level = read4(read_size());
					Length = getBitSize(DungeonInfo.Level);
				} else if (Type == 0x20) {
					DungeonInfo.Open = read4(read_size());
					Length = getBitSize(DungeonInfo.Open);
				} else if (Type == 0x2a) {
					Length = readC();
					DungeonInfo.OpenPassword = readS(Length);
					Length = Length + 1;
				} else if (Type == 0x30) {
					DungeonInfo.Division = read4(read_size());
					Length = getBitSize(DungeonInfo.Division);
					/** �� �ο��� ���� 6�� */
				} else if (Type == 0x38) {
					DungeonInfo.MaxSize = read4(read_size());
					Length = getBitSize(DungeonInfo.MaxSize);
					/** �� Ÿ�� (���� �ѹ�) */
				} else if (Type == 0x40) {
					DungeonInfo.TypeNumber = read4(read_size());
					Length = getBitSize(DungeonInfo.TypeNumber);
				}

				i += Length;
			}

			/** ����ɸ��� �������� �н� */
			DungeonInfo.isDungeonReady();
			pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonList, 1, DungeonInfo.RoomNumber), true);

			for (L1PcInstance PcList : DungeonInfo.isDungeonInfoUset()) {
				PcList.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOpen, DungeonInfo, null), true);
			}

			break;
		}

		/** �� ���� ���� */
		/** 51 a5 08 02 00 08 0d d2 */
		case InDungeonAccess: {
			readH();
			readC();
			/** �� �ѹ� üũ */
			int RoomNumber = read4(read_size());
			DungeonInfo DungeonInfo = DungeonSystem.isDungeonInfo(RoomNumber);
			pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonSetUp, DungeonInfo, null), true);
		}
			break;
		case PETBALL_CONTENTS_START_REQ: {

			if (pc.getZoneType() != 1) {
				pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.PETBALL_CONTENTS_START_ACK, 1));
				return;
			}

			pc.setReturnX(pc.getX());
			pc.setReturnY(pc.getY());
			pc.setReturnMap(pc.getMapId());
			L1Teleport.teleport(pc, 32766, 32830, (short) 5167, pc.getHeading(), false);
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.PETBALL_CONTENTS_START_ACK, 0));
		}
			break;
		case PETBALL_CONTENTS_END_REQ: {
			L1Teleport.teleport(pc, pc.getReturnX(), pc.getReturnY(), (short) pc.getReturnMap(), pc.getHeading(), false);
		}
			break;
		case MOVE_SERVER_AUTH_REQ: {
			try {
				L1LoginInfo wli = client.getLoginInfo();
				BinaryOutputStream builder = new BinaryOutputStream();
				builder.writeC(Opcodes.C_LOGOUT);
				builder.writeC(0);
				builder.writeC(0);

				client.packetHandler.handlePacket(builder.getBytes(), null);

				builder.close();

				builder = new BinaryOutputStream();
				builder.writeC(Opcodes.C_LOGIN);
				builder.writeS(wli.getAccount());
				builder.writeS(wli.getPassword());

				client.packetHandler.handlePacket(builder.getBytes(), null);
				builder.close();
			} catch (Exception e) {
				e.printStackTrace();
				client.close();
			}
		}
			break;
		case NP_LOGIN_REQ: {
			try {
				readH(); // 30 00
				readC(); // 08
				readBit(); // c0 d1 e2 09
				readC(); // 10
				readBit(); // 00
				readC(); // 1a
				int size1 = readBit();
				readByte(size1);
				readC(); // 0x22
				int token_size = readBit();
				byte[] auth_token_byte = readByte(token_size); // set_auth_provider
				String auth_token = new String(auth_token_byte, "MS932");
				String _authtoken = auth_token.toUpperCase();

				L1LoginInfo wli = new L1LoginInfo();

				System.out.println("auth_token: " + auth_token);

				if (_authtoken.contains("SHOTGUN")) {
					// ������ ���� ���ӱ� �α��� ����
					//System.out.println("USER: " + auth_token.split("\\|")[0]);
					//System.out.println("PASSWORD: " + auth_token.split("\\|")[2]);
					wli.setAccount(auth_token.split("\\|")[0]);
					wli.setPassword(auth_token.split("\\|")[2]);
				} else {
					// ���ӵ��� �г� Baron ���� ���ӱ� �α��� ����
					//System.out.println(auth_token);
					//wli.setAccount(auth_token.split("	")[0]); // ���� ��â�� �α��� �߰� by feel.
					//wli.setPassword(auth_token.split("	")[1]); // �׽��� �ٽ� ����ϸ�.. 2007~2019
					wli.setAccount(auth_token.split("\\|")[0]);
					wli.setPassword(auth_token.split("\\|")[1]);
				}

				client.setLoginInfo(wli);

				BinaryOutputStream login_c_packet = new BinaryOutputStream();
				login_c_packet.writeC(Opcodes.C_LOGIN);
				login_c_packet.writeS(wli.getAccount());
				login_c_packet.writeS(wli.getPassword());

				client.packetHandler.handlePacket(login_c_packet.getBytes(), null);

				login_c_packet.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			break;
		/** 51 ac 08 07 00 08 d9 1b 12 00 18 00 */
		case InDungeonStancet: {
			/** ���ó� ���� ���¶�� ���� ���� �ع����� */
			if (pc.isPrivateShop() && pc.isFishing())
				return;

			/** ���ͼ������ �޼����� ���� �Ұ����ϰ� ���� */
			if (pc.getNetConnection().getInterServer()) {
				pc.sendPackets(new S_SystemMessage("���ͼ���(������ž,��������)�������� ������ �Ұ����մϴ�."), true);
				return;
				/** ���谡 ������ ���� �Ұ����ϰ� �Ϸ� 1ȸ������ ���� �����ϰ� ���� */
			} else if (!pc.getInventory().checkItem(500021, 1)) {
				pc.sendPackets(new S_SystemMessage("�δ� ���踦 �������� �ʾ� ���� �Ҽ� �����ϴ�."), true);
				return;
			}

			readH();
			readC();
			/** �� �ѹ� üũ */
			int RoomNumber = read4(read_size());
			readC();
			int PasswordLength = readC();
			String Password = readS(PasswordLength);

			DungeonInfo DungeonInfo = DungeonSystem.isDungeonInfo(RoomNumber);
			if (DungeonInfo == null) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 7, RoomNumber), true);
				return;
			} else if (DungeonInfo.InPlaygame) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 3, RoomNumber), true);
				return;
			} else if (!pc.getInventory().checkItem(L1ItemId.ADENA, DungeonInfo.Adena)) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 4, RoomNumber), true);
				return;
			} else if (DungeonInfo.Open != 0 && !Password.contentEquals(DungeonInfo.OpenPassword)) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 5, RoomNumber), true);
				return;
			} else if (DungeonInfo.DungeonList.size() >= DungeonInfo.MaxSize) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 8, RoomNumber), true);
				return;
			} else if (DungeonInfo.DungeonList.contains((Object) pc)) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 9, RoomNumber), true);
				return;
			} else if (DungeonSystem.isDungeonInfoCheck(pc)) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 10, RoomNumber), true);
				return;
			} else if (DungeonInfo.Level > pc.getLevel()) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 11, RoomNumber), true);
				return;
			} else if (CharPosUtil.getZoneType(pc) != 1) {
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 12, RoomNumber), true);
				return;
			}

			DungeonInfo.setUser(pc);
			DungeonInfo.setDungeonInfoCheck(pc, false);
			pc.isDungeonTeleport(true);
			pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonStancet, 1, RoomNumber), true);

			for (L1PcInstance PcList : DungeonInfo.isDungeonInfoUset()) {
				PcList.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOpen, DungeonInfo, null), true);
				PcList.sendPackets(new S_EventNotice(S_EventNotice.InDungeonRenewal, DungeonInfo, pc), true);
			}

			break;
		}

		case InDungeonReady: {
			readH();
			readC();
			/** �� �ѹ� üũ */
			int RoomNumber = read4(read_size());
			readC();
			DungeonInfo DungeonInfo = DungeonSystem.isDungeonInfo(RoomNumber);

			if (DungeonInfo.isDungeonInfoCheck(pc)) {
				DungeonInfo.setDungeonInfoCheck(pc, false);
			} else {
				DungeonInfo.setDungeonInfoCheck(pc, true);
			}

			for (L1PcInstance PcList : DungeonInfo.isDungeonInfoUset()) {
				PcList.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOpen, DungeonInfo, null), true);
			}

			break;
		}

		case InDungeExit: {
			readH();
			readC();
			int RoomNumber = read4(read_size());
			DungeonInfo DungeonInfo = DungeonSystem.isDungeonInfo(RoomNumber);

			if (DungeonInfo.isDungeonLeadt() == pc.getId()) {
				for (L1PcInstance ListPc : DungeonInfo.isDungeonInfoUset()) {
					ListPc.isDungeonTeleport(false);
					ListPc.sendPackets(new S_EventNotice(S_EventNotice.InDungeExit, 1, RoomNumber), true);
				}

				DungeonSystem.DungeonInfoRemove(RoomNumber);
			} else {
				DungeonInfo.setUser(pc);
				pc.isDungeonTeleport(false);
				pc.sendPackets(new S_EventNotice(S_EventNotice.InDungeExit, 1, RoomNumber), true);

				for (L1PcInstance ListPc : DungeonInfo.isDungeonInfoUset()) {
					ListPc.sendPackets(new S_EventNotice(S_EventNotice.InDungeonOpen, DungeonInfo, null), true);
				}
			}

			break;
		}

		/*
		 * case �ʺ��̵�: readD(); // ������ ó�� int a = readC(); if(a==1 && pc.cL==0){
		 * pc.sendPackets(new S_CreateItem(S_CreateItem.����Ʈ����, 1), true);
		 * pc.sendPackets(new S_CreateItem(S_CreateItem.����Ʈ����, 2), true);
		 * pc.sendPackets(new S_CreateItem(S_CreateItem.����Ʈ����, 3), true); } break;
		 * 
		 * case �ʺ��Ϸ�: readD(); // ������ ó�� int d = readC(); pc.sendPackets(new
		 * S_CreateItem(S_CreateItem.����Ʈ�Ϸ�, d), true); pc.sendPackets(new
		 * S_OwnCharStatus(pc)); break;
		 * 
		 * case �ʺ��ε�: readD(); // ������ ó�� int q = readC(); pc.sendPackets(new
		 * S_CreateItem(S_CreateItem.����Ʈ�ε�, q), true); break;
		 * 
		 * case �ʺ�����: readD(); // ������ ó�� int m = readC(); pc.sendPackets(new
		 * S_CreateItem(S_CreateItem.����Ʈ����, m), true); break;
		 */
		case RevengeBoard: {
			readH();
			readC();
			CharcterRevengeTable revenge = CharcterRevengeTable.getInstance();
			ArrayList<Integer> objid = revenge.GetRevengeObj(pc.getId());

			if (objid.size() < 1) {
				return;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(RevengeTime)) {
				int targetId = revenge.LoadChaserTarGet(pc.getId());
				L1Object target = L1World.getInstance().findObject(targetId);

				if (target != null && target instanceof L1PcInstance) {
					L1PcInstance Targetpc = (L1PcInstance) target;
					pc.sendPackets(new S_Revenge(S_Revenge.Revenge_Chase_Loc, Targetpc), true);
				} else {
					pc.sendPackets(new S_Revenge(S_Revenge.Revenge_List_Add, pc), true);
				}
			}

			pc.sendPackets(new S_Revenge(S_Revenge.Revenge_All_List, pc), true);
			break;
		}
		case RevengeProvoke: {
			readD();
			readC();
			int NameLength = readC();
			String Name = readS(NameLength);
			CharcterRevengeTable revenge = CharcterRevengeTable.getInstance();

			if (Name == null) {
				return;
			} else {
				revenge.UpdateRemainCount(pc.getId(), Name);
				L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, pc.getName() + "���� " + Name + "���� �����Ͽ����ϴ�"), true);
			}

			pc.sendPackets(new S_Revenge(S_Revenge.Revenge_List_Provoke, pc), true);

			break;
		}
		case RevengeChase: {
			readD();
			readH();
			readC();
			int NameLength = readC();
			String Name = readS(NameLength);
			CharcterRevengeTable revenge = CharcterRevengeTable.getInstance();
			String OBJT = revenge.TarObjFind(Name);
			int targetId = Integer.parseInt(OBJT);
			L1Object target = L1World.getInstance().findObject(targetId);

			if (target == null) {
				return;
			}

			if (!pc.getInventory().checkItem(40308, 10000)) {
				pc.sendPackets(new S_SystemMessage("�Ƶ�����  �����մϴ�."), true);
				return;
			}

			if (target != null && target instanceof L1PcInstance) {
				L1PcInstance Targetpc = (L1PcInstance) target;
				if (revenge.RemainCounter(pc.getId(), Targetpc.getId()) > 0) {
					pc.getInventory().removeItem(40308, 10000);
					revenge.UpdateRemainCount(pc.getId(), Targetpc.getId());
					pc.getSkillEffectTimerSet().setSkillEffect(RevengeTime, 600 * 1000);
					revenge.StoreChaserTime(pc.getId(), target.getId(), 600);
					pc.sendPackets(new S_Revenge(S_Revenge.Revenge_Chase_Loc, Targetpc), true);
				} else {
					return;
				}
			}

			pc.sendPackets(new S_Revenge(S_Revenge.Revenge_All_List, pc), true);

			break;
		}
		case RevengeChase2: {
			readD();
			readH();
			readC();
			int NameLength = readC();
			String Name = readS(NameLength);
			CharcterRevengeTable revenge = CharcterRevengeTable.getInstance();
			String OBJT = revenge.TarObjFind(Name);
			int targetId = Integer.parseInt(OBJT);
			L1Object target = L1World.getInstance().findObject(targetId);

			if (target == null) {
				return;
			}

			if (target != null && target instanceof L1PcInstance && pc.getSkillEffectTimerSet().hasSkillEffect(RevengeTime)) {
				L1PcInstance Targetpc = (L1PcInstance) target;
				pc.sendPackets(new S_Revenge(S_Revenge.Revenge_Chase_MinMap, Targetpc), true);
				revenge.StoreChaserTime(pc.getId(), target.getId(), pc.getSkillEffectTimerSet().getSkillEffectTimeSec(80020));
			}

			break;
		}
		case BMTYPE_DEL_CHECK_REQ: {
			int delete_result = eCharacterDeleteResult.Success.toInt();
			try {
				readH();
				readC();
				int name_size = readBit();
				byte[] name_byte = readByte(name_size); // set_auth_provider
				String name = new String(name_byte, "MS932");

				pc = CharacterTable.getInstance().restoreCharacter(name);

				if (pc == null) {
					client.sendPacket(new S_Notice("�������� �ʴ� ĳ���� �Դϴ�."));
					delete_result = eCharacterDeleteResult.BMTypeFail.toInt();
					client.sendPacket(new S_ACTION_UI(S_ACTION_UI.BMTYPE_DEL_CHECK_ACK, delete_result));
					return;
				}

				for (L1PcInstance target : L1World.getInstance().getAllPlayers3()) {
					if (target.getId() == pc.getId()) {
						client.sendPacket(new S_Notice("���� ���� ĳ���ʹ� ������ �� �����ϴ�."));
						delete_result = eCharacterDeleteResult.BMTypeFail.toInt();
						client.sendPacket(new S_ACTION_UI(S_ACTION_UI.BMTYPE_DEL_CHECK_ACK, delete_result));
						return;
					}
				}

				client.sendPacket(new S_ACTION_UI(S_ACTION_UI.BMTYPE_DEL_CHECK_ACK, delete_result));
			} catch (Exception e) {
				delete_result = eCharacterDeleteResult.InvalidPacket.toInt();
				client.sendPacket(new S_ACTION_UI(S_ACTION_UI.BMTYPE_DEL_CHECK_ACK, delete_result));
			}
		}
			break;
		case MONSTERBOOK_CLEAR:
			readH();
			readC();
			int monNum = read4(read_size());
			int value = 0;

			if (monNum == 1) {
				value = 1;
			} else if (monNum == 2) {
				value = 2;
			}

			if (monNum >= 3) {
				value = monNum % 3;
			}

			switch (value) {// ���� 1~3�ܰ躰�� ����������
			case 1:
				pc.addExp(50000);
				pc.getInventory().storeItem(60765, 10);
				L1Cooking.newEatCooking(pc, L1SkillId.õ��������, 1800);
				break;
			case 2:
				pc.getInventory().storeItem(60765, 20);
				L1Cooking.newEatCooking(pc, L1SkillId.õ��������, 1800);
				pc.addExp(500000);
				break;
			case 3:
				pc.getInventory().storeItem(60765, 30);
				L1Cooking.newEatCooking(pc, L1SkillId.õ��������, 1800);
				pc.addExp(5000000);
				break;
			}

			pc.sendPackets(new S_MonsterUi(S_MonsterUi.MONSTER_END, monNum));
			MonsterBookTable.getInstace().setMon_Quest(pc.getId(), monNum, 1);
			MonsterBookTable.getInstace().saveMonsterQuest(pc.getId());
			pc.sendPackets(new S_SkillSound(pc.getId(), 3944), true);
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 3944), true);

			break;

		case MONSTERBOOK_TEL:
			readH();
			readC();
			int monsternumber = read4(read_size()) / 3 + 1;
			MonsterBookTable Mui_gi = MonsterBookTable.getInstace();
			int mn = Mui_gi.getMonNum(monsternumber);

			if (mn != 0 && mn <= 557 && pc.getMap().isSafetyZone(pc.getX(), pc.getY())) {
				int itemId = Mui_gi.getMarterial(monsternumber);
				String itemName = ItemTable.getInstance().findItemIdByName(itemId);

				if (itemName != null) {
					int locx = Mui_gi.getLocX(monsternumber);
					int locy = Mui_gi.getLocY(monsternumber);
					int mapid = Mui_gi.getMapId(monsternumber);

					if (pc.getMap().isEscapable()) {
						if (pc.getInventory().consumeItem(itemId, 1)) {
							pc.sendPackets(new S_SystemMessage("5���� �ڷ���Ʈ�˴ϴ�."), true);
							L1Teleport.teleport(pc, locx, locy, (short) mapid, 5, true);
						} else {
							pc.sendPackets(new S_ServerMessage(4692, itemName));
							return;
						}
					} else {
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
						pc.sendPackets(new S_ServerMessage(4726));
					}
				}
			} else if (mn >= 558 && mn <= 609 && pc.getMap().isSafetyZone(pc.getX(), pc.getY())) {
				int itemId = Mui_gi.getMarterial(monsternumber - 10);
				String itemName = ItemTable.getInstance().findItemIdByName(itemId);

				if (itemName != null) {
					int locx = Mui_gi.getLocX(monsternumber - 10);
					int locy = Mui_gi.getLocY(monsternumber - 10);
					int mapid = Mui_gi.getMapId(monsternumber - 10);

					if (pc.getMap().isEscapable()) {
						if (pc.getInventory().consumeItem(itemId, 1)) {
							pc.sendPackets(new S_SystemMessage("5���� �ڷ���Ʈ�˴ϴ�."), true);
							L1Teleport.teleport(pc, locx, locy, (short) mapid, 5, true);
						} else {
							pc.sendPackets(new S_ServerMessage(4692, itemName));
							return;
						}
					} else {
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
						pc.sendPackets(new S_ServerMessage(4726));
					}
				}
			} else if (mn >= 610 && pc.getMap().isSafetyZone(pc.getX(), pc.getY())) {
				int itemId = Mui_gi.getMarterial(monsternumber - 11);
				String itemName = ItemTable.getInstance().findItemIdByName(itemId);

				if (itemName != null) {
					int locx = Mui_gi.getLocX(monsternumber - 11);
					int locy = Mui_gi.getLocY(monsternumber - 11);
					int mapid = Mui_gi.getMapId(monsternumber - 11);

					if (pc.getMap().isEscapable()) {
						if (pc.getInventory().consumeItem(itemId, 1)) {
							pc.sendPackets(new S_SystemMessage("5���� �ڷ���Ʈ�˴ϴ�."), true);
							L1Teleport.teleport(pc, locx, locy, (short) mapid, 5, true);
						} else {
							pc.sendPackets(new S_ServerMessage(4692, itemName));
							return;
						}
					} else {
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
						pc.sendPackets(new S_ServerMessage(4726));
					}
				}
			} else {
				pc.sendPackets(new S_SystemMessage("\\aH�˸�: SafetyZone������ �̿� �����մϴ� ."), true);
			}

			break;

		case BOOKWEEKQUEST_TEL:
			if (pc.getWeekQuest() != null) {
				readH(); // 0x04, 0x00
				readC(); // 0x08,
				int difficulty = readC();
				readC(); // 0x10,
				int section = readC();
				pc.getWeekQuest().teleport(difficulty, section);
			}

			break;

		case BOOKWEEKQUEST_CLEAR:
			if (pc.getWeekQuest() != null) {
				readH(); // 0x04, 0x00
				readC(); // 0x08,
				int difficulty = readC();
				readC(); // 0x10,
				int section = readC();
				pc.getWeekQuest().complete(difficulty, section);
			}

			break;

		case EventNotice: {
			readH(); // size;
			readC(); // dummy
			int Action = readC();

			/** ������ ������ �������� ���Ұ��ϵ��� ���� */
			if (pc.getMapId() == 99 || (pc.getMapId() >= 2236 && pc.getMapId() <= 2237)) {
				pc.sendPackets(new S_SystemMessage("�̰������� ����Ҽ� �����ϴ�."), true);
				pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
				return;
			}

			/** ��ȯ�� �Ұ����� ���������� ��ȯ�ȵǵ��� ���� �ϱ�! */
			if (!pc.getMap().isEscapable()) {
				pc.sendPackets(new S_SystemMessage("�̰������� ����Ҽ� �����ϴ�."), true);
				pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
				return;
			}

			EventNotice Notice = RotationNoticeTable.getInstance().getRotation(Action);
			int locx = Notice.Teleport[0], locy = Notice.Teleport[1], mapid = Notice.Teleport[2], Adena = Notice.Teleport[3];
			if (Action == 20) {
				if (!pc.getInventory().checkItem(60384) && pc.getQuest().get_step(L1Quest.QUEST_55_Roon) == 0) {
					locx = 32882;
					locy = 32783;
					mapid = 4;
					Adena = 10000;
				} else if (!pc.getInventory().checkItem(60391) && pc.getQuest().get_step(L1Quest.QUEST_70_Roon) == 0 && pc.getLevel() >= 70) {
					locx = 32869;
					locy = 32797;
					mapid = 4;
					Adena = 10000;
				} else if (!pc.getInventory().checkItem(60398) && pc.getQuest().get_step(L1Quest.QUEST_80_Roon) == 0 && pc.getLevel() >= 80) {
					locx = 32869;
					locy = 32797;
					mapid = 4;
					Adena = 10000;
				} else if (!pc.getInventory().checkItem(60405) && pc.getQuest().get_step(L1Quest.QUEST_85_Roon) == 0 && pc.getLevel() >= 85) {
					locx = 32869;
					locy = 32797;
					mapid = 4;
					Adena = 10000;
				} else if (!pc.getInventory().checkItem(60412) && pc.getQuest().get_step(L1Quest.QUEST_90_Roon) == 0 && pc.getLevel() >= 90) {
					locx = 32869;
					locy = 32797;
					mapid = 4;
					Adena = 10000;
				}
			}

			L1Location loc = new L1Location(locx, locy, (short) mapid).randomLocation(Action == 16 ? 10 : 5, true);
			/** �Ƶ��� üũ�Ŀ� �Ƶ������ִٸ� ���� �ð� üũ�ؼ� �ð��� ���� �� �����ϵ��� �Ѵ� */
			if (!pc.getInventory().checkItem(40308, Adena)) {
				pc.sendPackets(new S_SystemMessage("�Ƶ�����  �����մϴ�."), true);
				pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
				return;
			}

			pc.getInventory().consumeItem(40308, Adena);
			pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
			L1Teleport.teleport(pc, loc.getX(), loc.getY(), (short) loc.getMapId(), 5, true, true, 5000);
			break;
		}

		/** �� ��ų ���� �ϴ� �ӽ÷� �۾� */
		case PetSkill: {
			readH(); // size;
			readC(); // dummy
			/** ��ų �ѹ� �޾ƿ� �⺻������ */
			int NameNumber = readC();
			L1PetInstance Pet = (L1PetInstance) pc.getPet();
			/** Ȯ�� ������ ��� ���� ���ְ� ��������� �ϴµ� �ϴ� �н� */
			if (Pet != null) {
				/**
				 * ��ų ��� ������ ��Ŷ ó�� �ؾߵ� �ӽ÷� ����
				 */
				int SkillLevel = 0;
				int[] Amount = null;
				if (NameNumber == 1 || NameNumber == 7 || NameNumber == 15 || NameNumber == 18 || NameNumber == 19 || NameNumber == 20 || NameNumber == 21
						|| NameNumber == 22) {
					SkillLevel = 1;
					Amount = new int[] { 3, 4, 5, 7, 10, 15, 25, 50, 100, 200 };
				} else if (NameNumber == 2 || NameNumber == 8 || NameNumber == 16) {
					SkillLevel = 2;
					Amount = new int[] { 13, 24, 35, 47, 60, 75, 95, 130, 190, 300 };
				} else if (NameNumber == 24 || NameNumber == 14 || NameNumber == 12 || NameNumber == 23 || NameNumber == 10 || NameNumber == 17) {
					SkillLevel = 3;
					Amount = new int[] { 28, 54, 80, 107, 135, 165, 200, 250, 325, 450 };
				} else if (NameNumber == 4 || NameNumber == 9 || NameNumber == 11 || NameNumber == 5 || NameNumber == 13) {
					SkillLevel = 4;
					Amount = new int[] { 48, 94, 140, 187, 235, 285, 340, 410, 505, 650 };
				} else if (NameNumber == 3) {
					SkillLevel = 5;
					Amount = new int[] { 73, 144, 215, 287, 360, 435, 515, 610, 730, 900 };
				}

				L1PetSkill PetSkill = Pet.getPetSkills(NameNumber);
				if (PetSkill != null && Pet.getFriendship() >= Amount[PetSkill.getSkillLevel()] && pc.getInventory().consumeItem(43204, 1)) {
					Pet.setFriendship(Pet.getFriendship() - Amount[PetSkill.getSkillLevel()]);
					pc.sendPackets(new S_PetWindow(S_PetWindow.Friendship, Pet), true);
					/** ��ų ���� ���� Ȯ�� ó�� */
					int Chance = _Random.nextInt(100) + 1;
					/** ���Ƽ� �����Ͽ��ٸ� ��ų ���� ��Ŷ ó�� �ؼ� �Ϸ� �����ش� */
					if (50 - (PetSkill.getSkillLevel() * SkillLevel) >= Chance) {
						PetSkill.setSkillLevel(PetSkill.getSkillLevel() + 1);
						/** npc���� ��Ŷ ó���� ��ȯ�Ͽ� ���� �޼ҵ带 �ϳ� �� ���� ������ */
						Pet.SkillsUpdate(PetSkill.getSkillNumber(), PetSkill.getSkillLevel());
						pc.sendPackets(new S_PetWindow(PetSkill), true);
						pc.sendPackets(new S_PetWindow(NameNumber, true), true);
						/** ��� ���� �ý��� */
						PetsSkillsTable.SaveSkills(Pet, false);
					} else {
						pc.sendPackets(new S_PetWindow(NameNumber, false), true);
					}
				} else {
					pc.sendPackets(new S_PetWindow(NameNumber, false), true);
				}

				/** ������ �ʱ�ȭ ��ų�� ��� */
				Arrays.fill(Amount, 0);
			}

			break;
		}

		/** �� ���� ��ų ���� üũ */
		case PetSkillLevel: {
			/** ��ų �ܰ� ����ؼ� 1�ܰ����� 2�ܰ����� üũ�ؾߵ� */
			/** 1�ܰ谡 ��� �Ϸ�� ������ �´��� üũ�ؼ� 2�ܰ�� �Ѱ��ټ��յ��� ���� */
			L1PetInstance Pet = (L1PetInstance) pc.getPet();
			/** �� ��ų ������ �������� üũ */
			if (Pet != null) {
				if (Pet.getPetSkills(Pet.getPetType().getSkillOneStep())) {
					/** �޼��� ��ũ */
					pc.sendPackets(new S_ServerMessage(5314), true);
					/** �⺻ ��ų ��������� */
					Pet.addPetSkills(Pet.getPetType().getSkillOneStep());
					pc.sendPackets(new S_PetWindow(Pet.ArrayPetSkills()), true);
					/** ��� ����t �ý��� */
					PetsSkillsTable.SaveSkills(Pet, false);
				} else if (Pet.getPetSkills(Pet.getPetType().getSkillTwoStep())) {
					/** �Ƶ����� ���ٸ� ���� */
					if (!pc.getInventory().consumeItem(40308, 100000)) {
						pc.sendPackets(new S_SystemMessage("�Ƶ����� �����Ͽ� 2�ܰ� �߼������� �Ҽ������ϴ�."), true);
						return;
					}

					/** �޼��� ��ũ */
					pc.sendPackets(new S_ServerMessage(5314), true);
					/** �⺻ ��ų ��������� */
					Pet.addPetSkills(Pet.getPetType().getSkillTwoStep());
					pc.sendPackets(new S_PetWindow(Pet.ArrayPetSkills()), true);
					/** ��� ����t �ý��� */
					PetsSkillsTable.SaveSkills(Pet, false);
				} else if (Pet.getPetSkills(Pet.getPetType().getSkillThreeStep())) {
					/** �Ƶ����� ���ٸ� ���� */
					if (!pc.getInventory().consumeItem(40308, 500000)) {
						pc.sendPackets(new S_SystemMessage("�Ƶ����� �����Ͽ� 3�ܰ� �߼������� �Ҽ������ϴ�."), true);
						return;
					}

					/** �޼��� ��ũ */
					pc.sendPackets(new S_ServerMessage(5314), true);
					/** �⺻ ��ų ��������� */
					Pet.addPetSkills(Pet.getPetType().getSkillThreeStep());
					pc.sendPackets(new S_PetWindow(Pet.ArrayPetSkills()), true);
					/** ��� ����t �ý��� */
					PetsSkillsTable.SaveSkills(Pet, false);
				} else if (Pet.getPetSkills(Pet.getPetType().getSkillFourStep())) {
					/** �Ƶ����� ���ٸ� ���� */
					if (!pc.getInventory().consumeItem(40308, 1000000)) {
						pc.sendPackets(new S_SystemMessage("�Ƶ����� �����Ͽ� 4�ܰ� �߼������� �Ҽ������ϴ�."), true);
						return;
					}

					/** �޼��� ��ũ */
					pc.sendPackets(new S_ServerMessage(5314), true);
					/** �⺻ ��ų ��������� */
					Pet.addPetSkills(Pet.getPetType().getSkillFourStep());
					pc.sendPackets(new S_PetWindow(Pet.ArrayPetSkills()), true);
					/** ��� ����t �ý��� */
					PetsSkillsTable.SaveSkills(Pet, false);
				} else if (Pet.getPetSkills(Pet.getPetType().getSkillFiveStep())) {
					/** �Ƶ����� ���ٸ� ���� */
					if (!pc.getInventory().consumeItem(40308, 5000000)) {
						pc.sendPackets(new S_SystemMessage("�Ƶ����� �����Ͽ� 5�ܰ� �߼������� �Ҽ������ϴ�."), true);
						return;
					}

					/** �޼��� ��ũ */
					pc.sendPackets(new S_ServerMessage(5314), true);
					/** �⺻ ��ų ��������� */
					Pet.addPetSkills(Pet.getPetType().getSkillFiveStep());
					pc.sendPackets(new S_PetWindow(Pet.ArrayPetSkills()), true);
					/** ��� ����t �ý��� */
					PetsSkillsTable.SaveSkills(Pet, false);
				} else {
					pc.sendPackets(new S_SystemMessage("��ų�� ���� �����ϴ�."), true);
				}
			}

			break;
		}

		case PetName: {
			readH(); // size;
			readC(); // size;
			int NameLength = readC();
			String Name = readS(NameLength);
			L1PetInstance Pet = (L1PetInstance) pc.getPet();
			if (Pet != null) {
				if (Pet.isInvalidName(Name)) {
					pc.sendPackets(new S_SystemMessage("����Ҽ� ���� �̸� �Դϴ�."), true);
					return;
				}

				/** ���� �̸��� ���� ���Ұ� */
				if (PetTable.isNameExists(Name)) {
					pc.sendPackets(new S_ServerMessage(327), true);
					return;
				}

				Pet.PetName(Name);
			}

			break;
		}

		case Petstat: {
			readH();
			int Temp[] = new int[3];
			L1PetInstance Pet = (L1PetInstance) pc.getPet();

			if (Pet != null && Pet.getBonusPoint() > 0) {
				for (int i = 0; i < Temp.length; i++) {
					readC();
					Temp[i] = readC();
				}

				Pet.BonusPoint(Temp);
			}

			/** ������ �ʱ�ȭ ��ų�� ��� */
			Arrays.fill(Temp, 0);
			break;
		}

		case PetCommand: {
			readH();
			readC();
			int Command = readC();
			L1PetInstance Pet = (L1PetInstance) pc.getPet();
			if (Pet != null) {
				switch (Command) {
				case 2:
					/** ���� �¼� */
					Pet.onFinalAction(1, null);
					break;

				case 3:
					/** ��� �¼� */
					Pet.onFinalAction(2, null);
					break;

				case 6:
					/** ���� ���� */
					Pet.onFinalAction(8, null);
					break;

				case 7:
					/** ��� ���� */
					readC();
					L1Object obj = L1World.getInstance().findObject(read4(read_size()));
					Pet.onFinalAction(9, obj);
					break;

				case 9:
					/** �°� ������� ���� */
					Pet.onFinalAction(10, null);
					break;

				case 101:
					/** �� �׼� ���� ��Ŷ ó���ε� */
					Pet.onFinalAction(67, null);
					break;
				}

			}

			break;
		}

		case CLAN_BLESS_HUNT_ALL_CHANGE: {
			if (pc.getClan() == null) {
				return;
			}

			if (pc.getClanRank() != L1Clan.CLAN_RANK_PRINCE && pc.getClanRank() != L1Clan.CLAN_RANK_GUARDIAN) {
				pc.sendPackets(new S_SystemMessage("������ ���ֳ� ��ȣ��縸 ��밡���մϴ�."));
				return;
			}

			if (!pc.getInventory().consumeItem(L1ItemId.ADENA, 300000)) {
				pc.sendPackets(new S_ServerMessage(189));
				return;
			}

			ClanBlessHuntInfo.getInstance().settingClanBlessHuntMaps(pc.getClan());
			ClanTable.getInstance().updateClanBlessHunt(pc.getClan());
			pc.sendPackets(new S_ClanBlessHuntUi(S_ClanBlessHuntUi.CLAN_BLESS_HUNT_TELEPORT, pc.getClan()));

			break;
		}

		case CLAN_BLESS_HUNT_CHANGE: {
			if (pc.getClan() == null) {
				return;
			}

			/*
			 * if (pc.getClanRank() != L1Clan.CLAN_RANK_PRINCE && pc.getClanRank()
			 * !=L1Clan.CLAN_RANK_GUARDIAN) { pc.sendPackets(new
			 * S_SystemMessage("������ ���ֳ� ��ȣ��縸 ��밡���մϴ�.")); return; }
			 */
			if (pc.getClan().isHuntMapChoice()) {
				if (!pc.getInventory().consumeItem(L1ItemId.ADENA, 1000)) {
					pc.sendPackets(new S_ServerMessage(189));
					return;
				}
			}

			readH();
			readC();
			readBit();
			readC();
			int mapNumber = readBit();
			readC();
			int code = readBit();
			ArrayList<Integer> mapList = ClanBlessHuntInfo.getInstance().getMapList(mapNumber);

			if (code == 1 || code == 3) {
				for (int i = 0; i < pc.getClan().getBlessHuntMaps().size(); i++) {
					if (pc.getClan().getBlessHuntMaps().get(i) == mapNumber) {
						pc.getClan().getBlessHuntMapsType().set(i, 2);
						pc.getClan().setBlessHuntMapIds(mapList);
					} else {
						pc.getClan().getBlessHuntMapsType().set(i, 3);
					}
				}
				pc.sendPackets(new S_ClanBlessHuntUi(S_ClanBlessHuntUi.CLAN_BLESS_HUNT_TELEPORT, pc.getClan()));
				pc.getClan().setHuntMapChoice(true);
				ClanTable.getInstance().updateClanBlessHunt(pc.getClan());
			} else {
				HuntInfo hi = ClanBlessHuntInfo.getInstance().getClanBlessHuntInfo(mapNumber);
				if (hi != null) {
					L1Teleport.teleport(pc, hi.getTeleportLocX(), hi.getTeleportLocY(), (short) hi.getTeleportMapId(), pc.getHeading(), true);
				}
			}

			break;
		}

		case CRAFT_ITEM: // ���� �ý���
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.CRAFT_ITEM));
			break;

		case CRAFT_ITEMLIST: // 0x38
			readH(); // size;
			readC(); // dummy
			int objectId = read4(read_size());
			L1Object obj = L1World.getInstance().findObject(objectId);

			if (obj instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) obj;
				int[] craftList = CraftInfoTable.getIns().getCraftNpc(npc.getNpcId());
				if (craftList != null && craftList.length != 0) {
					pc.sendPackets(new S_ACTION_UI(craftList));
				} else {
					pc.sendPackets(new S_ACTION_UI(npc));
				}
			}

			break;

		case 92: {
			if (pc.getInventory().calcWeightpercent() >= 90) {
				pc.sendPackets(new S_SystemMessage("���� ����: ���� ������ 90% �̻� ���� �Ұ�."));
				return;
			}

			int len = readC() - 3;
			for (int i = 0; i < len; i++) {
				readC();
			}

			pc.sendPackets(new S_NewCreateItem(0X5D, "08 00 10 e3 03 56 ce"));// 1479

			break;
		}

		case CRAFT_OK: {// 0x3a
			try {

				if (pc.getInventory().calcWeightpercent() >= 90) {
					pc.sendPackets(new S_SystemMessage("���� ����: ���� ������ 90% �̻� ���� �Ұ�."));
					return;
				}

				// ���⼭ �����Ŷ�� �ѹ� �о�帰��. �׸��� �ʿ��� �ڷ� ���� ���������� �����Ѵ� �����´�.
				if (!readCraftInfo(pc)) {
					craftErrMsg(pc, craftId, "!readCraftInfo(pc)");
					return;
				}

				if (materialDescIdList == null) {
					craftErrMsg(pc, craftId, "materialDescIdList == null");
					return;
				}

				L1CraftInfo ci = CraftInfoTable.getIns().getCraftInfo(craftId);
				if (ci == null) {
					craftErrMsg(pc, craftId, "ci == null");
					return;
				}

				L1Item item = ItemTable.getInstance().findDescId(ci.descId, ci.bless);
				if (item == null) {
					craftErrMsg(pc, craftId, "temp == null");
					return;
				}

				ArrayList<Material> materialList = ci.materialList;
				if (materialList == null) {
					craftErrMsg(pc, craftId, "materialList == null");
					return;
				}

				if (materialList.size() != materialDescIdList.size() && craftId != 3667 && craftId != 3599 && craftId != 6134 && craftId != 5913
						&& craftId != 5914 && craftId != 5915 && craftId != 5916 && craftId != 5917 // �����ϻ���� ����
						&& craftId != 5918 && craftId != 5919 && craftId != 5920 && craftId != 5921 && craftId != 5922 // �����ϻ���� ����
						&& craftId != 5933 && craftId != 5934 && craftId != 5935 && craftId != 5936 && craftId != 5937 // �׶�ī���� �г�
						&& craftId != 5938 && craftId != 5939 && craftId != 5940 && craftId != 5941 && craftId != 5942 // �׶�ī���� �г�
						&& craftId != 79 && craftId != 80 && craftId != 81 && craftId != 82 && craftId != 83 // �߶�,��Ǫ,����,��Ÿ, ���ʷ�
						&& craftId != 84 && craftId != 85 && craftId != 86 && craftId != 87 && craftId != 88 // �߶�,��Ǫ,����,��Ÿ, ���ʷ�
						&& craftId != 89 && craftId != 90 && craftId != 91 && craftId != 92 && craftId != 93 && craftId != 94) { // �߶�,��Ǫ,����,��Ÿ, ���ʷ�
					craftErrMsg(pc, craftId, "materialList.size() != materialDescIdList.size()");
					return;
				}

				HashMap<L1ItemInstance, Integer> deleteItemIntArrayList = new HashMap<L1ItemInstance, Integer>(); // ������,�����Ҽ�
				L1Inventory pcIv = pc.getInventory();

				for (Material material : materialList) { // �ϳ��� ��������
					if (material == null) {
						craftErrMsg(pc, craftId, "material == null");
						return;
					}

					int[] descIds = material.descIds;
					if (descIds == null) {
						craftErrMsg(pc, craftId, "descIds == null");
						return;
					}

					if (material.enchant == null) {
						craftErrMsg(pc, craftId, "material.enchant == null");
						return;
					}

					if (material.count == null) {
						craftErrMsg(pc, craftId, "material.count == null");
						return;
					}

					if (material.bless == null) {
						craftErrMsg(pc, craftId, "material.bless == null");
						return;
					}

					boolean materialCk = false;
					for (int index = 0; index < descIds.length; index++) { // �ϳ��� �������� ��ü�����۱��� �˻�.
						int descId = descIds[index];

						if (materialDescIdList.get(descId) == null) {
							continue;
						}

						materialCk = true;
						int enchant = materialDescIdList.get(descId);

						if (enchant != material.enchant[index]) {
							craftErrMsg(pc, craftId, "enchant != material.enchant[index]");
							return;
						}

						int count = material.count[index] * createItemCount;
						// int bless = material.bless[index];
						L1Item item1 = ItemTable.getInstance().getTemplateByDescId(descId);

						if (item1.isStackable()) { // �������� ����Ŀ���ϰ��
							L1ItemInstance deletItemCk = pcIv.findCraftMaterialItem(descId, count);
							if (deletItemCk == null) {
								craftErrMsg(pc, craftId, "1deletItemCk == null");
								return;
							}

							if (deleteItemIntArrayList.containsKey(deletItemCk) && craftId != 3667 && craftId != 3599) {
								craftErrMsg(pc, craftId, "deleteItemIntArrayList.containsKey(deletItemCk)");
								return;
							}

							deleteItemIntArrayList.put(deletItemCk, count);
							break;
						} else { // �Ϲ� �������ϰ��
							int ckCount = 0;
							for (L1ItemInstance itemIns : pcIv.getItems()) {

								if (ckCount >= count) {
									break;
								}

								if (itemIns != null && !itemIns.isEquipped() && itemIns.getItem().getItemDescId() == descId
										&& itemIns.getEnchantLevel() == enchant) {
									if (deleteItemIntArrayList.containsKey(itemIns)) {
										craftErrMsg(pc, craftId, "deleteItemIntArrayList.containsKey(itemIns)");
										return;
									}

									deleteItemIntArrayList.put(itemIns, 1);
									ckCount++;
								}
							}

							if (ckCount < count) {
								craftErrMsg(pc, craftId, "ckCount < count");
								return;
							} else {
								break;
							}
						}
					}

					if (!materialCk) {
						craftErrMsg(pc, craftId, "!materialCk");
						return;
					}
				}

				int probability = ci.probability;
				boolean success = true;

				if (probability != 0) {
					if (_Random.nextInt(100) > probability) {
						success = false;
					}
				}

				int deleteItemId = 0;
				for (L1ItemInstance itemIns : deleteItemIntArrayList.keySet()) {
					if (itemIns == null) {
						craftErrMsg(pc, craftId, "itemIns == null");
						return;
					}
					if (!pcIv.consumeItem(itemIns, deleteItemIntArrayList.get(itemIns))) { // item, count
						craftErrMsg(pc, craftId, "!pcIv.consumeItem(itemIns, deleteItemIntArrayList.get(itemIns))");
						return;
					}
				}

				if (success) {
					L1ItemInstance createItem = null;

					for (int itemId : bigSuccessItem) {
						if (itemId == item.getItemId()) {
							if (_Random.nextInt(100) < 100) { // 10% Ȯ���� �뼺��������.
								createItem = pcIv.storeItem2(item.getItemId(), ci.makeCount * createItemCount, ci.enchant, ci.bless, ci.attr);
								createItem.setBless(0);
								L1World.getInstance().broadcastPacketToAll(new S_ACTION_UI(S_ACTION_UI.��������޽���, 3599, createItem), true);
								commit("�뼺�� ������ ����^�ݼ����̺� : " + item.getName(), "", 1);
								pc.sendPackets(new S_SkillSound(pc.getId(), 7976));
								pc.broadcastPacket(new S_SkillSound(pc.getId(), 7976));
								pc.sendPackets(new S_SkillSound(pc.getId(), 2047));
								Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 2047));
							}

							break;
						}
					}

					if (createItem == null) {
						createItem = pcIv.storeItem2(item.getItemId(), ci.makeCount * createItemCount, ci.enchant, ci.bless, ci.attr);
					}

					pc.sendPackets(new S_ServerMessage(403, createItem.getName()));
					pc.sendPackets(new S_NewCreateItem(0X3b, ADD)); // ���� ��Ʈ
					pc.sendPackets(new S_SkillSound(pc.getId(), 2047)); // ����

					LinAllManager.getInstance().CraftInfo(success, pc.getName(), ci.name, craftId);
				} else {
					/** ���� ���� **/

					/** ���� ���н� ���� ���� **/
					if (craftId >= 2652 && craftId <= 2653) { // ������ �ø���
						pc.getInventory().storeItem(deleteItemId, _Random.nextInt(2) + 1);
					} else if (craftId >= 1043 && craftId <= 1048) { // ������ �ø���
						pc.getInventory().storeItem(500209, 30);
					} else if (craftId == 5892) {
						pc.getInventory().storeItem(60765, 30);
					} else if (craftId == 2747) { // �ູ���� ������ ��ű� ���� �ֹ���
						pc.getInventory().storeItem(7323, 1);
					} else if (craftId == 3599) { // �����̹�
						pc.getInventory().storeItem(20288, 1);
					} else if (craftId == 108) { // ������ ����
						pc.getInventory().storeItem(430108, 1); // <- ź���Ǹ���
					} else if (craftId == 109) { // ������ ����
						pc.getInventory().storeItem(430109, 1); // <- �����Ǹ���

						/** �� ���ʷ� �방�� ���� **/
					} else if (craftId == 2595) { // 6��Ÿ�γ�
						��þƮ����(pc, 420102, 1, 6, 0);
					} else if (craftId == 2596) { // 6��Ǫ�γ�
						��þƮ����(pc, 420106, 1, 6, 0);
					} else if (craftId == 2597) { // 6�����γ�
						��þƮ����(pc, 420110, 1, 6, 0);
					} else if (craftId == 2598) { // 6�߶��γ�
						��þƮ����(pc, 420112, 1, 6, 0);
					} else if (craftId == 2599) { // 7��Ÿ�γ�
						��þƮ����(pc, 420102, 1, 7, 0);
					} else if (craftId == 2600) { // 7��Ǫ�γ�
						��þƮ����(pc, 420106, 1, 7, 0);
					} else if (craftId == 2601) { // 7�����γ�
						��þƮ����(pc, 420110, 1, 7, 0);
					} else if (craftId == 2602) { // 7�߶��γ�
						��þƮ����(pc, 420112, 1, 7, 0);
					} else if (craftId == 2603) { // 8��Ÿ�γ�
						��þƮ����(pc, 420102, 1, 8, 0);
					} else if (craftId == 2604) { // 8��Ǫ�γ�
						��þƮ����(pc, 420106, 1, 8, 0);
					} else if (craftId == 2605) { // 8�����γ�
						��þƮ����(pc, 420110, 1, 8, 0);
					} else if (craftId == 2606) { // 8�߶��γ�
						��þƮ����(pc, 420112, 1, 8, 0);
					} else if (craftId == 2607) { // 6��Ÿ����
						��þƮ����(pc, 420101, 1, 6, 0);
					} else if (craftId == 2608) { // 6��Ǫ����
						��þƮ����(pc, 420105, 1, 6, 0);
					} else if (craftId == 2609) { // 6���忹��
						��þƮ����(pc, 420109, 1, 6, 0);
					} else if (craftId == 2610) { // 6�߶���
						��þƮ����(pc, 420113, 1, 6, 0);
					} else if (craftId == 2611) { // 7��Ÿ����
						��þƮ����(pc, 420101, 1, 7, 0);
					} else if (craftId == 2612) { // 7��Ǫ����
						��þƮ����(pc, 420105, 1, 7, 0);
					} else if (craftId == 2613) { // 7���忹��
						��þƮ����(pc, 420109, 1, 7, 0);
					} else if (craftId == 2614) { // 7�߶���
						��þƮ����(pc, 420113, 1, 7, 0);
					} else if (craftId == 2615) { // 8��Ÿ����
						��þƮ����(pc, 420101, 1, 8, 0);
					} else if (craftId == 2616) { // 8��Ǫ����
						��þƮ����(pc, 420105, 1, 8, 0);
					} else if (craftId == 2617) { // 8���忹��
						��þƮ����(pc, 420109, 1, 8, 0);
					} else if (craftId == 2618) { // 8�߶���
						��þƮ����(pc, 420113, 1, 8, 0);
					} else if (craftId == 2571) { // 6��Ÿ�Ϸ�
						��þƮ����(pc, 420100, 1, 6, 0);
					} else if (craftId == 2572) { // 6��Ǫ�Ϸ�
						��þƮ����(pc, 420104, 1, 6, 0);
					} else if (craftId == 2573) { // 6����Ϸ�
						��þƮ����(pc, 420108, 1, 6, 0);
					} else if (craftId == 2574) { // 6�߶�Ϸ�
						��þƮ����(pc, 420115, 1, 6, 0);
					} else if (craftId == 2575) { // 7��Ÿ�Ϸ�
						��þƮ����(pc, 420100, 1, 7, 0);
					} else if (craftId == 2576) { // 7��Ǫ�Ϸ�
						��þƮ����(pc, 420104, 1, 7, 0);
					} else if (craftId == 2577) { // 7����Ϸ�
						��þƮ����(pc, 420108, 1, 7, 0);
					} else if (craftId == 2578) { // 7�߶�Ϸ�
						��þƮ����(pc, 420115, 1, 7, 0);
					} else if (craftId == 2579) { // 8��Ÿ�Ϸ�
						��þƮ����(pc, 420100, 1, 8, 0);
					} else if (craftId == 2580) { // 8��Ǫ�Ϸ�
						��þƮ����(pc, 420104, 1, 8, 0);
					} else if (craftId == 2581) { // 8����Ϸ�
						��þƮ����(pc, 420108, 1, 8, 0);
					} else if (craftId == 2582) { // 8�߶�Ϸ�
						��þƮ����(pc, 420115, 1, 8, 0);
					} else if (craftId == 2583) { // 6��Ÿ����
						��þƮ����(pc, 420103, 1, 6, 0);
					} else if (craftId == 2584) { // 6��Ǫ����
						��þƮ����(pc, 420107, 1, 6, 0);
					} else if (craftId == 2585) { // 6���帶��
						��þƮ����(pc, 420111, 1, 6, 0);
					} else if (craftId == 2586) { // 6�߶󸶷�
						��þƮ����(pc, 420114, 1, 6, 0);
					} else if (craftId == 2587) { // 7��Ÿ����
						��þƮ����(pc, 420103, 1, 7, 0);
					} else if (craftId == 2588) { // 7��Ǫ����
						��þƮ����(pc, 420107, 1, 7, 0);
					} else if (craftId == 2589) { // 7���帶��
						��þƮ����(pc, 420111, 1, 7, 0);
					} else if (craftId == 2590) { // 7�߶󸶷�
						��þƮ����(pc, 420114, 1, 7, 0);
					} else if (craftId == 2591) { // 8��Ÿ����
						��þƮ����(pc, 420103, 1, 8, 0);
					} else if (craftId == 2592) { // 8��Ǫ����
						��þƮ����(pc, 420107, 1, 8, 0);
					} else if (craftId == 2593) { // 8���帶��
						��þƮ����(pc, 420111, 1, 8, 0);
					} else if (craftId == 2594) { // 8�߶󸶷�
						��þƮ����(pc, 420114, 1, 8, 0);

						/** ���Ľ� ���� �ø��� ���Ľ� ���� **/
					} else if (craftId == 6138) { // +1 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 0, 0);
					} else if (craftId == 6139) { // +2 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 1, 0);
					} else if (craftId == 6140) { // +3 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 2, 0);
					} else if (craftId == 6141) { // +4 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 3, 0);
					} else if (craftId == 6142) { // +5 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 4, 0);
					} else if (craftId == 6143) { // +6 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 5, 0);
					} else if (craftId == 6144) { // +7 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 6, 0);
					} else if (craftId == 6145) { // +8 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 7, 0);
					} else if (craftId == 6146) { // +9 ���Ľ��� �Ϸ�
						��þƮ����(pc, 27528, 1, 8, 0);
					} else if (craftId == 6147) { // +1 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 0, 0);
					} else if (craftId == 6148) { // +2 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 1, 0);
					} else if (craftId == 6149) { // +3 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 2, 0);
					} else if (craftId == 6150) { // +4 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 3, 0);
					} else if (craftId == 6151) { // +5 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 4, 0);
					} else if (craftId == 6152) { // +6 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 5, 0);
					} else if (craftId == 6153) { // +7 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 6, 0);
					} else if (craftId == 6154) { // +8 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 7, 0);
					} else if (craftId == 6155) { // +9 ���Ľ��� ������
						��þƮ����(pc, 27529, 1, 8, 0);
					} else if (craftId == 6156) { // +0 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 0, 0);
					} else if (craftId == 6157) { // +1 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 1, 0);
					} else if (craftId == 6158) { // +2 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 2, 0);
					} else if (craftId == 6159) { // +3 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 3, 0);
					} else if (craftId == 6160) { // +4 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 4, 0);
					} else if (craftId == 6161) { // +5 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 5, 0);
					} else if (craftId == 6162) { // +6 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 6, 0);
					} else if (craftId == 6163) { // +7 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 7, 0);
					} else if (craftId == 6164) { // +8 ���Ľ��� ����
						��þƮ����(pc, 27530, 1, 8, 0);

						/** �빫�� �ø��� ��� ���� **/ // �߶�ī���� ���
					} else if (craftId == 6195) { // �߶�ī���� ���(�������� ��)
						��þƮ����(pc, 100033, 1, 10, 0); // �������� ��, 1�� , 10�� ,0 �Ӽ� ����
					} else if (craftId == 6214) { // �߶�ī���� ���(�������� ��)
						��þƮ����(pc, 100033, 1, 10, 1);
					} else if (craftId == 6224) {
						��þƮ����(pc, 100033, 1, 10, 2);
					} else if (craftId == 6234) {
						��þƮ����(pc, 100033, 1, 10, 3);
					} else if (craftId == 6244) {
						��þƮ����(pc, 100033, 1, 10, 4);
					} else if (craftId == 6254) {
						��þƮ����(pc, 100033, 1, 10, 5);
					} else if (craftId == 6196) { // �߶�ī���� ���(��ũ������ ��)
						��þƮ����(pc, 30109, 1, 10, 0);
					} else if (craftId == 6215) {
						��þƮ����(pc, 30109, 1, 10, 1);
					} else if (craftId == 6225) {
						��þƮ����(pc, 30109, 1, 10, 2);
					} else if (craftId == 6235) {
						��þƮ����(pc, 30109, 1, 10, 3);
					} else if (craftId == 6245) {
						��þƮ����(pc, 30109, 1, 10, 4);
					} else if (craftId == 6255) {
						��þƮ����(pc, 30109, 1, 10, 5);

						/** �빫�� �ø��� ��� ���� **/ // �߶�ī���� ��հ�
					} else if (craftId == 6197) {
						��þƮ����(pc, 59, 1, 10, 0);
					} else if (craftId == 6216) {
						��þƮ����(pc, 59, 1, 10, 1);
					} else if (craftId == 6226) {
						��þƮ����(pc, 59, 1, 10, 2);
					} else if (craftId == 6236) {
						��þƮ����(pc, 59, 1, 10, 3);
					} else if (craftId == 6246) {
						��þƮ����(pc, 59, 1, 10, 4);
					} else if (craftId == 6256) {
						��þƮ����(pc, 59, 1, 10, 5);

						/** �빫�� �ø��� ��� ���� **/ // ��Ǫ������ ���
					} else if (craftId == 6198) {
						��þƮ����(pc, 293, 1, 10, 0);
					} else if (craftId == 6217) {
						��þƮ����(pc, 293, 1, 10, 1);
					} else if (craftId == 6227) {
						��þƮ����(pc, 293, 1, 10, 2);
					} else if (craftId == 6237) {
						��þƮ����(pc, 293, 1, 10, 3);
					} else if (craftId == 6247) {
						��þƮ����(pc, 293, 1, 10, 4);
					} else if (craftId == 6257) {
						��þƮ����(pc, 293, 1, 10, 5);

						/** �빫�� �ø��� ��� ���� **/ // ��Ǫ������ �̵���
					} else if (craftId == 6199) {
						��þƮ����(pc, 292, 1, 10, 0);
					} else if (craftId == 6218) {
						��þƮ����(pc, 292, 1, 10, 1);
					} else if (craftId == 6228) {
						��þƮ����(pc, 292, 1, 10, 2);
					} else if (craftId == 6238) {
						��þƮ����(pc, 292, 1, 10, 3);
					} else if (craftId == 6248) {
						��þƮ����(pc, 292, 1, 10, 4);
					} else if (craftId == 6258) {
						��þƮ����(pc, 292, 1, 10, 5);
					} else if (craftId == 6200) {
						��þƮ����(pc, 90083, 1, 10, 0);
					} else if (craftId == 6219) {
						��þƮ����(pc, 90083, 1, 10, 1);
					} else if (craftId == 6229) {
						��þƮ����(pc, 90083, 1, 10, 2);
					} else if (craftId == 6239) {
						��þƮ����(pc, 90083, 1, 10, 3);
					} else if (craftId == 6249) {
						��þƮ����(pc, 90083, 1, 10, 4);
					} else if (craftId == 6259) {
						��þƮ����(pc, 90083, 1, 10, 5);

						/** �빫�� �ø��� ��� ���� **/ // ��Ÿ���� ����
					} else if (craftId == 6201) {
						��þƮ����(pc, 7227, 1, 10, 0);
					} else if (craftId == 6220) {
						��þƮ����(pc, 7227, 1, 10, 1);
					} else if (craftId == 6230) {
						��þƮ����(pc, 7227, 1, 10, 2);
					} else if (craftId == 6240) {
						��þƮ����(pc, 7227, 1, 10, 3);
					} else if (craftId == 6250) {
						��þƮ����(pc, 7227, 1, 10, 4);
					} else if (craftId == 6260) {
						��þƮ����(pc, 7227, 1, 10, 5);

						/** �빫�� �ø��� ��� ���� **/ // ��Ÿ���� ������
					} else if (craftId == 6202) {
						��þƮ����(pc, 291, 1, 10, 0);
					} else if (craftId == 6221) {
						��þƮ����(pc, 291, 1, 10, 1);
					} else if (craftId == 6231) {
						��þƮ����(pc, 291, 1, 10, 2);
					} else if (craftId == 6241) {
						��þƮ����(pc, 291, 1, 10, 3);
					} else if (craftId == 6251) {
						��þƮ����(pc, 291, 1, 10, 4);
					} else if (craftId == 6261) {
						��þƮ����(pc, 291, 1, 10, 5);

						/** �빫�� �ø��� ��� ���� **/ // ���������� ü�μҵ�
					} else if (craftId == 6203) {
						��þƮ����(pc, 90084, 1, 10, 0);
					} else if (craftId == 6222) {
						��þƮ����(pc, 90084, 1, 10, 1);
					} else if (craftId == 6232) {
						��þƮ����(pc, 90084, 1, 10, 2);
					} else if (craftId == 6242) {
						��þƮ����(pc, 90084, 1, 10, 3);
					} else if (craftId == 6252) {
						��þƮ����(pc, 90084, 1, 10, 4);
					} else if (craftId == 6262) {
						��þƮ����(pc, 90084, 1, 10, 5);

						/** �빫�� �ø��� ��� ���� **/ // ���������� Ű��ũ
					} else if (craftId == 6204) {
						��þƮ����(pc, 7238, 1, 10, 0);
					} else if (craftId == 6223) {
						��þƮ����(pc, 7238, 1, 10, 1);
					} else if (craftId == 6233) {
						��þƮ����(pc, 7238, 1, 10, 2);
					} else if (craftId == 6243) {
						��þƮ����(pc, 7238, 1, 10, 3);
					} else if (craftId == 6253) {
						��þƮ����(pc, 7238, 1, 10, 4);
					} else if (craftId == 6263) {
						��þƮ����(pc, 7238, 1, 10, 5);

						/** ����� ����� ��æƮ�� **/
					} else if (craftId >= 3715 && craftId <= 3724) { // ������þ��
						��þƮ����2(pc, 61, 1, craftId - 3715, 5);
					} else if (craftId >= 3728 && craftId <= 3737) { // ��Į
						��þƮ����2(pc, 12, 1, craftId - 3728, 5);
					} else if (craftId >= 3741 && craftId <= 3750) { // ������
						��þƮ����2(pc, 134, 1, craftId - 3741, 10);
					} else if (craftId >= 3754 && craftId <= 3763) { // ����
						��þƮ����2(pc, 86, 1, craftId - 3754, 5);
					} else if (craftId >= 3767 && craftId <= 3776) { // �ݳ�
						��þƮ����2(pc, 30082, 1, craftId - 3767, 15);
					} else if (craftId >= 3780 && craftId <= 3789) { // ũ��
						��þƮ����2(pc, 30080, 1, craftId - 3780, 5);
					} else if (craftId >= 3793 && craftId <= 3802) { // ����
						��þƮ����2(pc, 30081, 1, craftId - 3793, 5);
					} else if (craftId >= 3806 && craftId <= 3815) { // Ÿ��
						��þƮ����2(pc, 30083, 1, craftId - 3806, 5);
					} else if (craftId >= 5893 && craftId <= 5902) { // ����ǰ�
						��þƮ����2(pc, 30110, 1, craftId - 5893, 5);

						/** ���Ľ��� ����� ��æƮ�� **/
					} else if (craftId >= 5913 && craftId <= 5922) { // �����ϻ���Ǽ���
						��þƮ����2(pc, 30112, 1, craftId - 5913, 5);
					} else if (craftId >= 5933 && craftId <= 5942) { // �׶�ī���� ����
						��þƮ����2(pc, 30111, 1, craftId - 5933, 5);

						/** ����� ����� ��þƮ�� */
					} else if (craftId >= 4429 && craftId <= 4438) { // ������þ��
						��þƮ����2(pc, 61, 1, craftId - 4429, 5);
					} else if (craftId >= 4442 && craftId <= 4451) { // ��Į
						��þƮ����2(pc, 12, 1, craftId - 4442, 5);
					} else if (craftId >= 4455 && craftId <= 4464) { // ������
						��þƮ����2(pc, 134, 1, craftId - 4455, 10);
					} else if (craftId >= 4468 && craftId <= 4477) { // ����
						��þƮ����2(pc, 86, 1, craftId - 4468, 5);
					} else if (craftId >= 4481 && craftId <= 4490) { // �ݳ�
						��þƮ����2(pc, 30082, 1, craftId - 4481, 15);
					} else if (craftId >= 4494 && craftId <= 4503) { // ũ��
						��þƮ����2(pc, 30080, 1, craftId - 4494, 5);
					} else if (craftId >= 4507 && craftId <= 4516) { // ����
						��þƮ����2(pc, 30081, 1, craftId - 4507, 5);
					} else if (craftId >= 4520 && craftId <= 4529) { // Ÿ��
						��þƮ����2(pc, 30083, 1, craftId - 4520, 5);
					} else if (craftId >= 5903 && craftId <= 5912) { // ����ǰ�
						��þƮ����2(pc, 30110, 1, craftId - 5903, 5);
					}

					/** ����� ����� ��þƮ�� */
					pc.sendPackets(new S_NewCreateItem(0X3b, REMOVE)); // ���� ��Ʈ
					LinAllManager.getInstance().CraftInfo(success, pc.getName(), ci.name, craftId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}

		} // end switch

	}

	private boolean ��þƮ����(L1PcInstance pc, int item_id, int count, int EnchantLevel, int AttrEnchantLevel) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);

		if (item != null) {
			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			item.setAttrEnchantLevel(AttrEnchantLevel);

			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else {
				pc.sendPackets(new S_ServerMessage(82));// ���� �������� �����ϰų� �κ��丮�� ������ �� �� �� �����ϴ�.
				return false;
			}

			// pc.sendPackets(new S_SystemMessage("������ ���ۿ� �����߽��ϴ�."));
			pc.sendPackets(new S_ServerMessage(143, item.getLogName())); // %0�� �տ� �־����ϴ�.
			// pc.sendPackets(new S_SkillSound(pc.getId(), 2032));
			// pc.broadcastPacket(new S_SkillSound(pc.getId(), 2032));
			return true;
		} else {
			return false;
		}

	}

	private boolean ��þƮ����2(L1PcInstance pc, int item_id, int count, int EnchantLevel, int AttrEnchantLevel) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setAttrEnchantLevel(AttrEnchantLevel);
			item.setIdentified(true);

			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else {
				pc.sendPackets(new S_ServerMessage(82));
				// ���� �������� �����ϰų� �κ��丮�� ������ �� �� �� �����ϴ�.
				return false;
			}

			// pc.sendPackets(new S_SystemMessage("������ ���ۿ� �����߽��ϴ�."));
			pc.sendPackets(new S_ServerMessage(143, item.getLogName())); // %0�� �տ� �־����ϴ�.
			// pc.sendPackets(new S_SkillSound(pc.getId(), 2032));
			// pc.broadcastPacket(new S_SkillSound(pc.getId(), 2032));
			return true;
		} else {
			return false;
		}

	}

	private int craftId;
	private int createItemCount;
	private HashMap<Integer, Integer> materialDescIdList; // descId, encahnt

	private boolean readCraftInfo(L1PcInstance pc) {
		materialDescIdList = new HashMap<Integer, Integer>();

		readP(3); // dummy //��Ż������ ����
		L1Object obj = L1World.getInstance().findObject(readBit());

		if (obj == null) {
			craftErrMsg(pc, 0, "obj == null");
			return false;
		}

		if (obj instanceof L1NpcInstance) {
			L1NpcInstance npc = (L1NpcInstance) obj;
			int difflocx = Math.abs(pc.getX() - npc.getX());
			int difflocy = Math.abs(pc.getY() - npc.getY());
			if (difflocx > 15 || difflocy > 15 || (pc.getMapId() != npc.getMapId())) {
				pc.sendPackets(new S_ServerMessage(3575), true); // ���� NPC�� �ʹ� �ָ� �ֽ��ϴ�.
				return false;
			}
		}

		readP(1); // dummy
		craftId = readBit();

		if (GMCommands.����üũ) {
			System.out.println("���۾��̵� - craftId : " + craftId);
		}

		if (craftId == 0) {
			craftErrMsg(pc, craftId, "craftId == 0");
			return false;
		}

		readP(1); // dummy
		createItemCount = readBit();
		if (GMCommands.����üũ) {
			System.out.println("���۸��鰹�� - createItemCount : " + createItemCount);
		}

		if (createItemCount == 0) {
			craftErrMsg(pc, craftId, "createItemCount == 0");
			return false;
		}

		while (isRead(1)) {
			int dummyType = readC();

			if (dummyType != 0x22) {
				break;
			}

			readP(4); // ����� ����, dummy 0x08 , �������� ����. dummy 0x10
			int descId = readBit();

			if (materialDescIdList.containsKey(descId) && craftId != 3410 && craftId != 3411 && craftId != 3412 && craftId != 3667 && craftId != 3599
					&& craftId != 2044) {
				craftErrMsg(pc, craftId, "materialDescIdList.containsKey(descId)");
				return false;
			}

			int enchant = 0;
			int isEnchant = readC();

			if (isEnchant == 0x20) { // ��þƮ��.
				enchant = readC();
				readP(6); // dummy
			} else {
				readP(5); // dummy
			}

			if (GMCommands.����üũ) {
				System.out.println("��������� - descId : " + descId + " / enchant : " + enchant);
			}

			materialDescIdList.put(descId, enchant);
		}

		return true;
	}

	private void craftErrMsg(L1PcInstance pc, int craftId, String msg) {
		System.out.println("���� ����: " + msg + " / craftId : " + craftId + " / ����� : " + pc.getName());
	}

	public void commit(String com, String name, int count) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM request_log WHERE command=?");
			pstm.setString(1, com);
			rs = pstm.executeQuery();
			Connection con2 = null;
			PreparedStatement pstm2 = null;

			try {
				con2 = L1DatabaseFactory.getInstance().getConnection();
				if (rs.next()) {
					int amount = rs.getInt("count");
					pstm2 = con2.prepareStatement("UPDATE request_log SET count=? WHERE command=?");
					pstm2.setInt(1, amount + count);
					pstm2.setString(2, com);
				} else {
					pstm2 = con2.prepareStatement("INSERT INTO request_log SET command=?, count=?");
					pstm2.setString(1, com);
					pstm2.setInt(2, count);
				}
				pstm2.executeUpdate();
			} catch (SQLException e) {

			} finally {
				SQLUtil.close(pstm2);
				SQLUtil.close(con2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private boolean isTwoLogin(L1PcInstance c) {// �ߺ�üũ ����
		boolean bool = false;
		for (L1PcInstance target : L1World.getInstance().getAllPlayers()) {
			if (target.noPlayerCK || target.isRobot())
				continue;
			if (c.getId() != target.getId() && (!target.isPrivateShop())) {
				if (c.getNetConnection().getAccountName().equalsIgnoreCase(target.getNetConnection().getAccountName())) {
					bool = true;
					break;
				}
			}
		}
		return bool;
	}

	private void resetAttendanceTime(Account account, L1PcInstance pc) {
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0) {
			if (account.isAttendanceHome()) {
				account.setAttendanceHomeTime(0);
				account.setAttendanceHome(false);
			}
			if (account.isAttendancePcHome()) {
				account.setAttendancePcHomeTime(0);
				account.setAttendancePcHome(false);
			}
			account.updateAttendanceTime();
			account.storeAttendCheck();
			updateAttendanceTime();
			pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.ATTENDANCE_COMPLETE, account, pc.PC��_����));
		}
	}

	private void getAttendanceHome(L1PcInstance pc, Account account) {
		if (account.isAttendanceHome())
			return;
		account.addAttendanceHomeTime(1);
		if (account.getAttendanceHomeTime() >= 60) {
			for (int i = 0; i < account.getAttendanceHomeBytes().length; i++) {
				if (account.getAttendanceHomeBytes()[i] == 0) {
					account.getAttendanceHomeBytes()[i] = 1;
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.ATTENDANCE_TIMEOVER, i + 1, false));
					break;
				}
			}
			account.storeAttendBytes();
			account.setAttendanceHomeTime(0);
			account.setAttendanceHome(true);
			account.storeAttendCheck();
		}
	}

	private void getAttendancePCRoom(L1PcInstance pc, Account account) {
		if (!pc.PC��_����)
			return;
		if (account.isAttendancePcHome())
			return;
		account.addAttendancePcHomeTime(1);
		if (account.getAttendancePcHomeTime() >= 60) {
			for (int i = 0; i < account.getAttendancePcBytes().length; i++) {
				if (account.getAttendancePcBytes()[i] == 0) {
					/* System.out.println("�Ϸ���Ŷ�� ��Ŷ ��ȣ : " + (i + 1)); */
					account.getAttendancePcBytes()[i] = 1;
					pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.ATTENDANCE_TIMEOVER, i + 1, true));
					break;
				}
			}
			account.storeAttendBytes();
			account.setAttendancePcHomeTime(0);
			account.setAttendancePcHome(true);
			account.storeAttendCheck();
		}
	}

	private void updateAttendanceTime() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE accounts SET attendanceHomeTime=0, attendancePcHomeTime=0, attendanceHome=0,attendancePcHome=0");
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private class AttendanceGiveItem implements Runnable {
		private L1PcInstance _pc;
		private L1ItemInstance _reward_item;
		private AttendanceItem _reward_info;

		public AttendanceGiveItem(L1PcInstance pc, L1ItemInstance reward_item, AttendanceItem reward_info) {
			_pc = pc;
			_reward_item = reward_item;
			_reward_info = reward_info;
		}

		@Override
		public void run() {
			try {
				_pc.getInventory().storeItem(_reward_item);
				_pc.sendPackets(new S_ServerMessage(403, _reward_item.getName() + "(" + _reward_item.getCount() + ")"));

				if (_reward_info._broadcast_item) {
					L1World.getInstance().broadcastPacketToAll(
							new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "�⼮ �������� �Ƶ� ������ ��� ��簡 " + _reward_item.getName() + " ��(��) ȹ���Ͽ����ϴ�."));
				}

				L1SkillUse l1skilluse = new L1SkillUse();
				l1skilluse.handleCommands(_pc, L1SkillId.�⼮üũ, _pc.getId(), _pc.getX(), _pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void ChatWhisper(L1PcInstance whisperFrom, int chatType, int chatcount, byte[] chatdata, String text, String targetName) {
		try {
			// ä�� �������� ���
			if (whisperFrom.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) {
				S_ServerMessage sm = new S_ServerMessage(242);
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (whisperFrom.getLevel() < Config.WHISPER_CHAT_LEVEL) {
				S_ServerMessage sm = new S_ServerMessage(404, String.valueOf(Config.WHISPER_CHAT_LEVEL));
				whisperFrom.sendPackets(sm, true);
				return;
			}

			/*
			 * if (!whisperFrom.isGm() && (targetName.compareTo("��Ƽ��") == 0)) {
			 * S_SystemMessage sm = new S_SystemMessage( "��ڴԲ��� �ӼӸ��� �� �� �����ϴ�.");
			 * whisperFrom.sendPackets(sm, true); return; }
			 */

			if (targetName.equalsIgnoreCase("***")) {
				S_SystemMessage sm = new S_SystemMessage("-> (***) " + text);
				whisperFrom.sendPackets(sm, true);
				return;
			}

			L1PcInstance whisperTo = L1World.getInstance().getPlayer(targetName);

			// ���忡 ���� ���
			if (whisperTo == null) {
				L1NpcShopInstance npc = null;
				npc = L1World.getInstance().getNpcShop(targetName);
				if (npc != null) {
					// S_ChatPacket scp = new S_ChatPacket(npc,
					// text,Opcodes.S_MESSAGE, 9);
					S_NewCreateItem scp = new S_NewCreateItem(chatType, chatdata, chatcount, whisperFrom);
					whisperFrom.sendPackets(scp, true);
					// S_SystemMessage sm = new
					// S_SystemMessage("-> ("+targetName+") "+text);
					// whisperFrom.sendPackets(sm); sm.clear(); sm = null;
					return;
				}

				S_ServerMessage sm = new S_ServerMessage(73, targetName);
				whisperFrom.sendPackets(sm, true);
				return;
			}

			// �ڱ� �ڽſ� ���� wis�� ���
			if (whisperTo.equals(whisperFrom)) {
				return;
			}

			if (whisperTo.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) {
				S_SystemMessage sm = new S_SystemMessage("ä�ñ������� �������� �ӼӸ��� �Ҽ� �����ϴ�.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (text.length() > 45) {
				S_SystemMessage sm = new S_SystemMessage("�Ӹ��� ���� �� �ִ� ���ڼ��� �ʰ��Ͽ����ϴ�.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			// ���ܵǰ� �ִ� ���
			if (whisperTo.getExcludingList().contains(whisperFrom.getName())) {
				S_ServerMessage sm = new S_ServerMessage(117, whisperTo.getName());
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (!whisperTo.isCanWhisper()) {
				S_ServerMessage sm = new S_ServerMessage(205, whisperTo.getName());
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (whisperTo instanceof L1RobotInstance) {
				// S_ChatPacket scp = new S_ChatPacket(whisperTo,
				// text,Opcodes.S_MESSAGE, 9);
				whisperFrom.sendPackets(new S_NewCreateItem(chatType, chatdata, chatcount, whisperTo.getName(), whisperFrom));
				return;
			}

			whisperFrom.sendPackets(new S_NewCreateItem(whisperFrom, 3, chatType, text, whisperTo.getName()));
			whisperTo.sendPackets(new S_NewCreateItem(whisperFrom, 4, chatType, text, whisperTo.getName()));
			LinAllManager.getInstance().WisperChatAppend(whisperFrom.getName(), whisperTo.getName(), text);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clear();
		}
	}

	private void Chat(L1PcInstance pc, int chatType, int chatcount, byte[] chatdata, String chatText, LineageClient clientthread) {
		try {
			if (pc.ĳ������) {
				try {
					String chaName = chatText;
					if (pc.getClanid() > 0) {
						pc.sendPackets(new S_SystemMessage("����Ż���� ĳ������ �����Ҽ� �ֽ��ϴ�."));
						pc.ĳ������ = false;
						return;
					}

					if (!pc.getInventory().checkItem(467009, 1)) { // �ֳ� üũ
						pc.sendPackets(new S_SystemMessage("�ɸ��� ���� ������� �����ϼž� �����մϴ�."));
						pc.ĳ������ = false;
						return;
					}

					for (int i = 0; i < chaName.length(); i++) {
						if (chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������ ��.
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��.
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��.
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��.
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��.
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��.
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��.
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' // �ѹ���(char)������
																																				// ��.
								|| chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��') {
							pc.sendPackets(new S_SystemMessage("����Ҽ����� �ɸ����Դϴ�."));
							pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
							pc.ĳ������ = false;
							return;
						}
					}

					if (chaName.getBytes().length > 12) {
						pc.sendPackets(new S_SystemMessage("�̸��� �ʹ� ��ϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					if (chaName.length() == 0) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �Է��ϼ���."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					if (BadNamesList.getInstance().isBadName(chaName)) {
						pc.sendPackets(new S_SystemMessage("����� �� ���� �ɸ����Դϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					if (isInvalidName(chaName)) {
						pc.sendPackets(new S_SystemMessage("����� �� ���� �ɸ����Դϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					if (CharacterTable.doesCharNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					if (CharacterTable.RobotNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					if (CharacterTable.RobotCrownNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					if (NpcShopSpawnTable.getInstance().getNpc(chaName) || npcshopNameCk(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					if (CharacterTable.somakname(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					pc.getInventory().consumeItem(467009, 1); // �Ҹ�

					String oldname = pc.getName();

					chaname(chaName, oldname);

					long sysTime = System.currentTimeMillis();
					logchangename(chaName, oldname, new Timestamp(sysTime));

					pc.sendPackets(new S_SystemMessage(chaName + " ���̵�� ���� �ϼ̽��ϴ�."));
					pc.sendPackets(new S_SystemMessage("������  �̿��� ���� Ŭ���̾�Ʈ�� ������ ���� �˴ϴ�."));

					Thread.sleep(1000);
					clientthread.kick();
				} catch (Exception e) {
				}

				return;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE) || pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.AREA_OF_SILENCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_POISON_SILENCE)) {
				return;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) { // ä�� ������
				S_ServerMessage sm = new S_ServerMessage(242);
				pc.sendPackets(sm); // ���� ä�� �������Դϴ�.
				sm = null;
				return;
			}

			if (pc.isDeathMatch() && !pc.isGhost() && !pc.isGm()) {
				S_ServerMessage sm = new S_ServerMessage(912);
				pc.sendPackets(sm); // ä���� �� �� �����ϴ�.
				sm = null;
				return;
			}

			if (!pc.isGm()) {

				for (String tt : textFilter) {
					int indexof = chatText.indexOf(tt);

					if (indexof != -1) {
						int count = 100;

						while ((indexof = chatText.indexOf(tt)) != -1) {
							if (count-- <= 0) {
								break;
							}

							char[] dd = chatText.toCharArray();
							chatText = "";

							for (int i = 0; i < dd.length; i++) {
								if (i >= indexof && i <= (indexof + tt.length() - 1)) {
									chatText = chatText + "  ";
								} else
									chatText = chatText + dd[i];
							}
						}
					}
				}
			}

			switch (chatType) {
			case 0: {
				if (pc.isGhost() && !(pc.isGm() || pc.isMonitor())) {
					return;
				}

				if (chatText.startsWith(".�ð�")) {
					StringBuilder sb = null;
					sb = new StringBuilder();
					TimeZone kst = TimeZone.getTimeZone("GMT+9");
					Calendar cal = Calendar.getInstance(kst);
					sb.append("[Server Time]" + cal.get(Calendar.YEAR) + "�� " + (cal.get(Calendar.MONTH) + 1) + "�� " + cal.get(Calendar.DATE) + "�� "
							+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));

					S_SystemMessage sm = new S_SystemMessage(sb.toString());
					pc.sendPackets(sm, true);
					sb = null;
					return;
				}

				// GMĿ���
				if (chatText.startsWith(".") && (pc.getAccessLevel() == Config.GMCODE || pc.getAccessLevel() == 7777)) {
					String cmd = chatText.substring(1);
					GMCommands.getInstance().handleCommands(pc, cmd);
					return;
				}

				if (chatText.startsWith("$")) {
					if (pc.isGm()) {
						chatWorld(pc, chatdata, chatType, chatcount, chatText);
					} else {
						chatWorld(pc, chatdata, 12, chatcount, chatText);
					}

					if (!pc.isGm()) {
						pc.checkChatInterval();
					}

					return;
				}

				Gamble(pc, chatText);
				if (chatText.startsWith(".")) { // �����ڸ�Ʈ
					String cmd = chatText.substring(1);
					if (cmd == null) {
						return;
					}

					UserCommands.getInstance().handleCommands(pc, cmd);
					return;
				}

				if (chatText.startsWith("$")) { // ����ä��
					if (pc.isGm()) {
						chatWorld(pc, chatdata, chatType, chatcount, chatText);
					} else {
						chatWorld(pc, chatdata, 12, chatcount, chatText);
					}

					if (!pc.isGm()) {
						pc.checkChatInterval();
					}

					return;
				}

				pc.sendPackets(new S_NewCreateItem(pc, 3, chatType, chatText, ""));
				S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");

				if (!pc.getExcludingList().contains(pc.getName())) {
					if (pc.getMapId() != 2699) {
						pc.sendPackets(s_chatpacket);
					}
				}

				for (L1PcInstance listner : L1World.getInstance().getRecognizePlayer(pc)) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						if (listner.getMapId() == 2699) {
							continue;
						}

						listner.sendPackets(s_chatpacket);
					}
				}

				// ���� ó��
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, chatText, 0), true);
						}
					}
				}

				LinAllManager.getInstance().NomalchatAppend(pc.getName(), chatText);
			}
				break;
			case 2: {
				if (pc.isGhost()) {
					return;
				}

				// S_ChatPacket s_chatpacket = new S_ChatPacket(pc,
				// chatText,Opcodes.S_SAY, 2);
				// S_NewCreateItem chat5 = new S_NewCreateItem(chatType,
				// chatdata, chatcount, pc);
				S_NewCreateItem chat5 = new S_NewCreateItem(pc, 4, chatType, chatText, "");
				if (!pc.getExcludingList().contains(pc.getName())) {
					pc.sendPackets(chat5);
				}

				for (L1PcInstance listner : L1World.getInstance().getVisiblePlayer(pc, 50)) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(chat5);
					}
				}

				LinAllManager.getInstance().NomalchatAppend(pc.getName(), chatText);
				// ���� ó��
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							for (L1PcInstance listner : L1World.getInstance().getVisiblePlayer(mob, 30)) {
								listner.sendPackets(new S_NpcChatPacket(mob, chatText, 2), true);
							}
						}
					}
				}
			}
				break;
			case 3:
				chatWorld(pc, chatdata, chatType, chatcount, chatText);
				break;
			case 4: {
				if (pc.getClanid() != 0) { // ũ�� �Ҽ���
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					S_NewCreateItem chat4 = new S_NewCreateItem(pc, 4, chatType, chatText, "");
					LinAllManager.getInstance().ClanChatAppend(pc.getClanname(), pc.getName(), chatText);
					for (L1PcInstance listner : clan.getOnlineClanMember()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(chat4);
						}
					}
				}
			}
				break;
			case 11: {
				if (pc.isInParty()) { // ��Ƽ��
					// S_NewCreateItem s_chatpacket = new
					// S_NewCreateItem(chatType, chatdata, chatcount, pc);
					S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");
					for (L1PcInstance listner : pc.getParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
				LinAllManager.getInstance().PartyChatAppend(pc.getName(), chatText);
			}
				break;
			case 12:
				if (pc.isGm()) {
					chatWorld(pc, chatdata, chatType, chatcount, chatText);
				} else {
					chatWorld(pc, chatdata, 3, chatcount, chatText);
				}
				break;
			case 13: { // ��ȣ��� ä��
				if (pc.getClanid() != 0) { // ���� �Ҽ���
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					int rank = pc.getClanRank();
					if (clan != null && (rank == L1Clan.CLAN_RANK_GUARDIAN || rank == L1Clan.CLAN_RANK_SUBPRINCE || rank == L1Clan.CLAN_RANK_PRINCE)) {
						// S_NewCreateItem chat1 = new S_NewCreateItem(chatType,
						// chatdata, chatcount, pc);
						S_NewCreateItem chat1 = new S_NewCreateItem(pc, 4, chatType, chatText, "");

						for (L1PcInstance listner : clan.getOnlineClanMember()) {
							int listnerRank = listner.getClanRank();
							if (!listner.getExcludingList().contains(pc.getName()) && (listnerRank == L1Clan.CLAN_RANK_GUARDIAN
									|| rank == L1Clan.CLAN_RANK_SUBPRINCE || listnerRank == L1Clan.CLAN_RANK_PRINCE)) {
								listner.sendPackets(chat1);
							}
						}
					}
				}
				LinAllManager.getInstance().ClanChatAppend(pc.getClanname(), pc.getName(), chatText);
			}
				break;
			case 14: { // ä�� ��Ƽ
				if (pc.isInChatParty()) { // ä�� ��Ƽ��
					S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");
					LinAllManager.getInstance().PartyChatAppend(pc.getName(), chatText);
					for (L1PcInstance listner : pc.getChatParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
			}
				break;
			case 15: { // ����ä��
				if (pc.getClanid() != 0) { // ���� �Ҽ���
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());

					if (clan != null) {
						Integer allianceids[] = clan.Alliance();
						if (allianceids.length > 0) {
							String TargetClanName = null;
							L1Clan TargegClan = null;
							S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");
							for (L1PcInstance listner : clan.getOnlineClanMember()) {
								int AllianceClan = listner.getClanid();
								if (pc.getClanid() == AllianceClan) {
									listner.sendPackets(s_chatpacket);
								}
							} // �ڱ����� ���ۿ�

							for (int j = 0; j < allianceids.length; j++) {
								TargegClan = clan.getAlliance(allianceids[j]);
								if (TargegClan != null) {
									TargetClanName = TargegClan.getClanName();
									if (TargetClanName != null) {
										for (L1PcInstance alliancelistner : TargegClan.getOnlineClanMember()) {
											alliancelistner.sendPackets(s_chatpacket);
										} // �������� ���ۿ�
									}
								}

							}
						}

					}
				}
				break;
			}
			case 17:
				if (pc.getClanid() != 0) { // ���� �Ҽ���
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					if (clan != null && (pc.isCrown() && pc.getId() == clan.getLeaderId())) {
						S_NewCreateItem s_chatpacket5 = new S_NewCreateItem(pc, 4, chatType, chatText, "");
						LinAllManager.getInstance().ClanChatAppend(pc.getClan().getClanName(), pc.getName(), chatText);
						for (L1PcInstance listner : clan.getOnlineClanMember()) {
							if (!listner.getExcludingList().contains(pc.getName())) {
								listner.sendPackets(s_chatpacket5);
							}
						}
					}
				}

				break;

			} // end switch

			if (!pc.isGm()) {
				pc.checkChatInterval();
			}

		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	private void chatWorld(L1PcInstance pc, byte[] chatdata, int chatType, int chatcount, String text) {
		if (pc.getLevel() >= Config.GLOBAL_CHAT_LEVEL) {
			if (pc.isGm() || L1World.getInstance().isWorldChatElabled()) {
				if (pc.get_food() >= 12) { // 5%����?
					S_PacketBox pb = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
					pc.sendPackets(pb, true);
					if (chatType == 3) {
						if (pc.isGm()) {
//							L1World.getInstance().broadcastPacketToAll(new S_NewCreateItem(pc, 4, chatType, text, ""));
							L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[���] " + text));
						}
						LinAllManager.getInstance().AllChatAppend(pc.getName(), text);
					} else if (chatType == 12) {
						if (pc.isGm()) {
//							L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[���] " + text));
						}
						LinAllManager.getInstance().AllChatAppend("[���]" + pc.getName(), text);
					}

					for (L1PcInstance listner : L1World.getInstance().getAllPlayers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							if (listner.isShowTradeChat() && chatType == 12) {
								listner.sendPackets(new S_NewCreateItem(chatType, chatdata, chatcount, pc));
								listner.sendPackets(new S_NewCreateItem(pc, 4, chatType, text, ""));
							} else if (listner.isShowWorldChat() && chatType == 3) {
								listner.sendPackets(new S_NewCreateItem(pc, 4, chatType, text, ""));
							}
						}
					}
					if (chatType == 3) {
						LinAllManager.getInstance().AllChatAppend(pc.getName(), text);
					} else if (chatType == 12) {
						LinAllManager.getInstance().AllChatAppend("[���]" + pc.getName(), text);
					}
				} else {
					S_ServerMessage sm = new S_ServerMessage(462);
					pc.sendPackets(sm, true);
				}
			} else {
				S_ServerMessage sm = new S_ServerMessage(510);
				pc.sendPackets(sm, true);
			}
		} else {
			S_ServerMessage sm = new S_ServerMessage(195, String.valueOf(Config.GLOBAL_CHAT_LEVEL));
			pc.sendPackets(sm, true);
		}
	}

	private boolean npcshopNameCk(String name) {
		return NpcTable.getInstance().findNpcShopName(name);
	}

	private void logchangename(String chaName, String oldname, Timestamp datetime) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "INSERT INTO Log_Change_name SET Old_Name=?,New_Name=?, Time=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, oldname);
			pstm.setString(2, chaName);
			pstm.setTimestamp(3, datetime);
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static boolean isInvalidName(String name) {
		int numOfNameBytes = 0;
		try {
			numOfNameBytes = name.getBytes("EUC-KR").length;
		} catch (UnsupportedEncodingException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return false;
		}

		if (isAlphaNumeric(name)) {
			return false;
		}

		if (5 < (numOfNameBytes - name.length()) || 12 < numOfNameBytes) {
			return false;
		}

		if (BadNamesList.getInstance().isBadName(name)) {
			return false;
		}

		return true;
	}

	private static boolean isAlphaNumeric(String s) {
		boolean flag = true;
		char ac[] = s.toCharArray();
		int i = 0;
		do {
			if (i >= ac.length) {
				break;
			}

			if (!Character.isLetterOrDigit(ac[i])) {
				flag = false;
				break;
			}

			i++;
		} while (true);

		return flag;
	}

	private void chaname(String chaName, String oldname) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET char_name=? WHERE char_name=?");
			pstm.setString(1, chaName);
			pstm.setString(2, oldname);
			pstm.executeUpdate();
		} catch (Exception e) {

		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void Gamble(L1PcInstance pc, String chatText) {

		if (pc.Gamble_Somak) { // �Ҹ�
			for (int i : GambleInstance.mobArray) {
				L1Npc npck = NpcTable.getInstance().getTemplate(i);
				String name = npck.get_name().replace(" ", "");
				if (name.equalsIgnoreCase(chatText) || npck.get_name().equalsIgnoreCase(chatText)
				/*
				 * || chatText.startsWith(npck.get_name())|| chatText.startsWith(name)
				 */) {
					pc.Gamble_Text = npck.get_name();
				}
			}
		}
	}

	public int Nexttam(String encobj) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int day = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT day FROM `tam` WHERE encobjid = ? order by id asc limit 1"); // �ɸ��� ���̺��� ���ָ� ���ͼ�
			pstm.setString(1, encobj);
			rs = pstm.executeQuery();

			while (rs.next()) {
				day = rs.getInt("Day");
			}
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		return day;
	}

	public int TamCharid(String encobj) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int objid = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT objid FROM `tam` WHERE encobjid = ? order by id asc limit 1"); // �ɸ��� ���̺��� ���ָ� ���ͼ�
			pstm.setString(1, encobj);
			rs = pstm.executeQuery();

			while (rs.next()) {
				objid = rs.getInt("objid");
			}
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		return objid;
	}

	public void tamcancle(String objectId) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("delete from tam where encobjid = ? order by id asc limit 1");
			pstm.setString(1, objectId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	@Override
	public String getType() {
		return C_ACTION_UI;
	}

}
