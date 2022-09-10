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
 * http://www.gnu.org/copyleft/gpl.html1
 */
package l1j.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.datatables.ExpTable;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.templates.L1PrivateShopBuyList;
import l1j.server.server.templates.L1PrivateShopSellList;
import l1j.server.server.utils.IntRange;
import server.GameServer;

public final class Config {
	public static IND_Q _IND_Q = null;
	public static quit_Q _quit_Q = null;
	public static INN_Q _INN_Q = null;
	private static final Logger _log = Logger.getLogger(Config.class.getName());
	public static int craft = 1005;
	public static boolean mainenc = false;
	public static boolean STANDBY_SERVER = false;
	public static int test222 = 3;
	public static boolean ���ο���Ŷ���� = false;
	public static boolean ������Ŷ��� = false;
	public static boolean Ŭ����Ŷ��� = false;
	private static final String servername = "�׽�Ʈ";

	public static ArrayList<L1PrivateShopSellList> sellList = new ArrayList<L1PrivateShopSellList>();

	public static ArrayList<L1PrivateShopBuyList> buyList = new ArrayList<L1PrivateShopBuyList>();

	public static byte[] FIRST_PACKET = { (byte) 0x0b, (byte) 0x00, // size
			/** 190512 ���� by feel. **/
			(byte) 0xeb, (byte) 0xd6, (byte) 0x45, (byte) 0x9d, (byte) 0x02, (byte) 0xfe, (byte) 0xb0, (byte) 0xc8, (byte) 0x0a };

//	S_INITPACKET : 235 [0xeb] / Length : 11   (07:16)
//	eb f0 7c ab 50 d2 8a b3 	7a

	public static long SeedVal = 0x029d45d6L; // ����

	public static void addSellList(L1PrivateShopSellList sellitem) {
		sellList.add(sellitem);
	}

	public static void SellListClear(L1PcInstance pc) {
		for (L1PrivateShopSellList slist : pc.getSellList())
			if (sellList.contains(slist))
				sellList.remove(slist);
	}

	public static void addBuyList(L1PrivateShopBuyList buyitem) {
		buyList.add(buyitem);
	}

	public static void BuyListClear(L1PcInstance pc) {
		for (L1PrivateShopBuyList blist : pc.getBuyList())
			if (buyList.contains(blist))
				buyList.remove(blist);
	}

	public static int �̸�Ȯ��(char[] _values) {
		for (char value : _values) {
			if ((value >= 'A' && value <= 'z') || (value >= 'a' && value <= 'z')) {
				// System.out.println("���� : "+value);
			} else if (value >= '0' && value <= '9') {
				// System.out.println("���� : "+value);
			} else if (value >= '\uAC00' && value <= '\uD7A3') {
				// System.out.println("�ѱ� : "+value);
			} else {
				return -1;
			}
		}
		return 0;
		// .... ���� ����
	}

	public static ArrayList<Integer> spractionerr = new ArrayList<Integer>();

	public static int teste = 8700;
	public static boolean ������ = false;
	public static int test = 0;
	public static int aaaaa = 0;

	public synchronized static void addaa() {
		aaaaa++;
	}

	public static int iitest = 0;

	public static String getserver() {
		return servername;
	}

	/** Debug/release mode */
	public static final boolean DEBUG = false;
	// public static boolean ��Ŷ�α� = true;//�α�

	public static final int �����ð� = 60 * 90;
	public static final int PC�����ð� = 60 * 60 * 2;

	public static final int ����_���ǵ���_�ð� = 60 * 60 * 3;
	public static final int �������� = 60 * 60 * 2;
	public static final int PC_���ǵ���_�ð� = 60 * 60 * 3;

	public static int ������������ð� = 7200;
	public static int ���ž�߷������ð� = 7200;
	public static int ���ž���������ð� = 3600;
	public static int �������������ð� = 3600;
	public static int ������õ���ǰ��ð� = 3600;
	public static int ���������ð� = 3600;
	public static int ��ȯ�Ǽ��ð� = 3600;
	public static int ��Ÿ�ٵ�����ð� = 3600;
	public static int PROTECT_CLAN_ID;
	public static int AUTO_REMOVECLAN;
	public static boolean �ű����ͺ�ȣó��;
	public static boolean CLAN_BLESS_ALL_USE;
	public static int �űԷ���;
	public static int ����Ȯ��2;
	public static int ����Ȯ��3;
	public static int ����Ȯ��4;
	public static int ����Ȯ��5;
	/** ��ȣ �ֹ��� Ȯ�� ���� */
	public static int ��ȣ�ֹ���Ȯ��0;
	public static int ��ȣ�ֹ���Ȯ��1;
	public static int ��ȣ�ֹ���Ȯ��2;
	public static int ��ȣ�ֹ���Ȯ��3;
	public static int ��ȣ�ֹ���Ȯ��4;
	public static int ��ȣ�ֹ���Ȯ��5;
	public static int ��ȣ�ֹ���Ȯ��6;

	public static int �⼮�ʱ�ȭ�ð�;
	public static int AINHASADDPRESETTIME;

	// �������̺�
	public static int CRAFT_TABLE_ONE;
	public static int CRAFT_TABLE_TWO;
	public static int CRAFT_TABLE_THREE;
	public static int CRAFT_TABLE_FOUR;
	public static int CRAFT_TABLE_FIVE;
	public static int CRAFT_TABLE_SIX;
	public static int CRAFT_TABLE_SEVEN;
	public static int CRAFT_TABLE_EIGHT;
	public static int CRAFT_TABLE_NINE;
	public static int CRAFT_TABLE_TEN;
	public static int CRAFT_TABLE;

	protected static ArrayList<L1PcInstance> ����ä�ø���� = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> ��Ƽä�ø���� = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> �Ӹ�ä�ø���� = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> ��þä�ø���� = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> ����ä�ø���� = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> ����ä�ø���� = new ArrayList<L1PcInstance>();

	protected static ArrayList<L1PcInstance> ����ä�ø���� = new ArrayList<L1PcInstance>();

	public static int ����ä�ø����() {
		return ����ä�ø����.size();
	}

	public static int ��Ƽä�ø����() {
		return ��Ƽä�ø����.size();
	}

	public static int �Ӹ�ä�ø����() {
		return �Ӹ�ä�ø����.size();
	}

	public static int ��þä�ø����() {
		return ��þä�ø����.size();
	}

	public static int ����ä�ø����() {
		return ����ä�ø����.size();
	}

	public static int ����ä�ø����() {
		return ����ä�ø����.size();
	}

	public static int ����ä�ø����() {
		return ����ä�ø����.size();
	}

	public static L1PcInstance[] toArray����ä�ø����() {
		return ����ä�ø����.toArray(new L1PcInstance[����ä�ø����.size()]);
	}

	public static L1PcInstance[] toArray��Ƽä�ø����() {
		return ��Ƽä�ø����.toArray(new L1PcInstance[��Ƽä�ø����.size()]);
	}

	public static L1PcInstance[] toArray�Ӹ�ä�ø����() {
		return �Ӹ�ä�ø����.toArray(new L1PcInstance[�Ӹ�ä�ø����.size()]);
	}

	public static L1PcInstance[] toArray��þä�ø����() {
		return ��þä�ø����.toArray(new L1PcInstance[��þä�ø����.size()]);
	}

	public static L1PcInstance[] toArray����ä�ø����() {
		return ����ä�ø����.toArray(new L1PcInstance[����ä�ø����.size()]);
	}

	public static L1PcInstance[] toArray����ä�ø����() {
		return ����ä�ø����.toArray(new L1PcInstance[����ä�ø����.size()]);
	}

	public static L1PcInstance[] toArray����ä�ø����() {
		return ����ä�ø����.toArray(new L1PcInstance[����ä�ø����.size()]);
	}

	public static void add��ü(L1PcInstance pc) {
		if (!����ä�ø����.contains(pc)) {
			����ä�ø����.add(pc);
			;
		}
		if (!��Ƽä�ø����.contains(pc)) {
			��Ƽä�ø����.add(pc);
			;
		}
		if (!�Ӹ�ä�ø����.contains(pc)) {
			�Ӹ�ä�ø����.add(pc);
			;
		}
		if (!��þä�ø����.contains(pc)) {
			��þä�ø����.add(pc);
			;
		}
		if (!����ä�ø����.contains(pc)) {
			����ä�ø����.add(pc);
			;
		}
		if (!����ä�ø����.contains(pc)) {
			����ä�ø����.add(pc);
			;
		}
		if (!����ä�ø����.contains(pc)) {
			����ä�ø����.add(pc);
			;
		}
	}

	public static void remove��ü(L1PcInstance pc) {
		if (����ä�ø����.contains(pc)) {
			����ä�ø����.remove(pc);
			;
		}
		if (��Ƽä�ø����.contains(pc)) {
			��Ƽä�ø����.remove(pc);
			;
		}
		if (�Ӹ�ä�ø����.contains(pc)) {
			�Ӹ�ä�ø����.remove(pc);
			;
		}
		if (��þä�ø����.contains(pc)) {
			��þä�ø����.remove(pc);
			;
		}
		if (����ä�ø����.contains(pc)) {
			����ä�ø����.remove(pc);
			;
		}
		if (����ä�ø����.contains(pc)) {
			����ä�ø����.remove(pc);
			;
		}
		if (����ä�ø����.contains(pc)) {
			����ä�ø����.remove(pc);
			;
		}
	}

	public static void add����(L1PcInstance pc) {
		if (����ä�ø����.contains(pc)) {
			return;
		}
		����ä�ø����.add(pc);
	}

	public static void remove����(L1PcInstance pc) {
		if (!����ä�ø����.contains(pc)) {
			return;
		}
		����ä�ø����.remove(pc);
	}

	public static void add��Ƽ(L1PcInstance pc) {
		if (��Ƽä�ø����.contains(pc)) {
			return;
		}
		��Ƽä�ø����.add(pc);
	}

	public static void remove��Ƽ(L1PcInstance pc) {
		if (!��Ƽä�ø����.contains(pc)) {
			return;
		}
		��Ƽä�ø����.remove(pc);
	}

	public static void add�Ӹ�(L1PcInstance pc) {
		if (�Ӹ�ä�ø����.contains(pc)) {
			return;
		}
		�Ӹ�ä�ø����.add(pc);
	}

	public static void remove�Ӹ�(L1PcInstance pc) {
		if (!�Ӹ�ä�ø����.contains(pc)) {
			return;
		}
		�Ӹ�ä�ø����.remove(pc);
	}

	public static void add��þ(L1PcInstance pc) {
		if (��þä�ø����.contains(pc)) {
			return;
		}
		��þä�ø����.add(pc);
	}

	public static void remove��þ(L1PcInstance pc) {
		if (!��þä�ø����.contains(pc)) {
			return;
		}
		��þä�ø����.remove(pc);
	}

	public static void add����(L1PcInstance pc) {
		if (����ä�ø����.contains(pc)) {
			return;
		}
		����ä�ø����.add(pc);
	}

	public static void remove����(L1PcInstance pc) {
		if (!����ä�ø����.contains(pc)) {
			return;
		}
		����ä�ø����.remove(pc);
	}

	public static void add����(L1PcInstance pc) {
		if (����ä�ø����.contains(pc)) {
			return;
		}
		����ä�ø����.add(pc);
	}

	public static void remove����(L1PcInstance pc) {
		if (!����ä�ø����.contains(pc)) {
			return;
		}
		����ä�ø����.remove(pc);
	}

	public static void add����(L1PcInstance pc) {
		if (����ä�ø����.contains(pc)) {
			return;
		}
		����ä�ø����.add(pc);
	}

	public static void remove����(L1PcInstance pc) {
		if (!����ä�ø����.contains(pc)) {
			return;
		}
		����ä�ø����.remove(pc);
	}

	public static boolean �վ�����;
	public static boolean ��ü��Ż�����;

	/** Thread pools size */
	public static int THREAD_P_EFFECTS;
	public static int THREAD_P_GENERAL;
	public static int AI_MAX_THREAD;
	public static int THREAD_P_TYPE_GENERAL;
	public static int THREAD_P_SIZE_GENERAL;

	/** Server control */
	public static int GAME_SERVER_TYPE;
	public static String GAME_SERVER_HOST_NAME;
	public static int GAME_SERVER_PORT;
	public static String DB_DRIVER;
	public static String DB_URL;
	public static String DB_LOGIN;
	public static String DB_PASSWORD;
	public static String TIME_ZONE;
	public static int CLIENT_LANGUAGE;

	public static boolean HOSTNAME_LOOKUPS;
	public static int AUTOMATIC_KICK;
	public static boolean AUTO_CREATE_ACCOUNTS;
	public static short MAX_ONLINE_USERS;
	public static boolean CACHE_MAP_FILES;
	public static boolean LOAD_V2_MAP_FILES;
	public static boolean CHECK_MOVE_INTERVAL;
	public static boolean CHECK_ATTACK_INTERVAL;
	public static boolean CHECK_SPELL_INTERVAL;
	public static short INJUSTICE_COUNT;
	public static int JUSTICE_COUNT;
	public static int CHECK_STRICTNESS;
	public static byte LOGGING_WEAPON_ENCHANT;
	public static byte LOGGING_ARMOR_ENCHANT;
	public static int LOGGING_TIME;
	public static boolean LOGGING_CHAT_NORMAL;
	public static boolean LOGGING_CHAT_WHISPER;
	public static boolean LOGGING_CHAT_SHOUT;
	public static boolean LOGGING_CHAT_WORLD;
	public static boolean LOGGING_CHAT_CLAN;
	public static boolean LOGGING_CHAT_PARTY;
	public static boolean LOGGING_CHAT_COMBINED;
	public static boolean LOGGING_CHAT_CHAT_PARTY;
	public static int ENCHANT_CHANCE_ACCESSORY;
	public static int Ȱ����;
	public static int �����ǥ;

	public static boolean ��Ʋ���۵�����;
	public static int ��Ʋ�����巹��;
	public static String ��Ʋ��������;
	public static String ��Ʋ�������۰���;

	public static String ����1�ܾ�����;
	public static String ����1�ܾ����۰���;

	public static String ����2�ܾ�����;
	public static String ����2�ܾ����۰���;

	public static String ����3�ܾ�����;
	public static String ����3�ܾ����۰���;

	public static boolean Tam_Ok;
	public static int Tam_Time;
	public static int Tam_Count;

	public static int D_Reset_Time;

	public static boolean Event_Box;

	public static int AUTOSAVE_INTERVAL;
	public static int AUTOSAVE_INTERVAL_INVENTORY;
	public static int SKILLTIMER_IMPLTYPE;
	public static int NPCAI_IMPLTYPE;
	public static boolean TELNET_SERVER;
	public static int TELNET_SERVER_PORT;
	public static int PC_RECOGNIZE_RANGE;
	public static boolean CHARACTER_CONFIG_IN_SERVER_SIDE;
	public static boolean ALLOW_2PC;
	public static int LEVEL_DOWN_RANGE;
	public static boolean SEND_PACKET_BEFORE_TELEPORT;
	public static boolean DETECT_DB_RESOURCE_LEAKS;
	public static boolean AUTO_CHECK;

	public static boolean WAR_TIME_AUTO_SETTING;

	/** Rate control */
	public static int �������ִ���æ;
	public static int ��ȣ�ֹ����ִ���æ;
	public static int ����������;
	public static int �����ϻ�嵥�ϸ�����Ʈ;
	public static int ��Ƽ���ִ���æ;
	public static int ��ű��ִ���æ;
	public static double RATE_XP;
	public static double fishrate;
	public static double RATE_LAWFUL;
	public static double RATE_KARMA;
	public static double RATE_DROP_ADENA;
	public static double RATE_DROP_ITEMS;
	public static int RATE_ROBOT_TIME; // ����PC(���)
	public static int ENCHANT_CHANCE_WEAPON;
	public static int ENCHANT_CHANCE_WEAPON7;
	public static int ENCHANT_CHANCE_WEAPON8;
	public static int ENCHANT_CHANCE_WEAPON9;
	public static int ENCHANT_CHANCE_WEAPON10;
	public static int ENCHANT_CHANCE_ARMOR;
	public static double RATE_WEIGHT_LIMIT;
	public static double RATE_WEIGHT_LIMIT_PET;
	public static double RATE_SHOP_SELLING_PRICE;
	public static double RATE_SHOP_PURCHASING_PRICE;
	public static int RATE_DREAM; // ���������� �̺�Ʈ���
	public static int CREATE_CHANCE_DIARY;
	public static int CREATE_CHANCE_RECOLLECTION;
	public static int CREATE_CHANCE_MYSTERIOUS;
	public static int CREATE_CHANCE_PROCESSING;
	public static int CREATE_CHANCE_PROCESSING_DIAMOND;
	public static int CREATE_CHANCE_DANTES;
	public static int CREATE_CHANCE_ANCIENT_AMULET;
	public static int CREATE_CHANCE_HISTORY_BOOK;
	public static int MAX_WEAPON;
	public static int MAX_ARMOR;
	public static int MAX_WEAPON1; // �������� ��þ����
	public static int MAX_LEVEL; // �űԺ�ȣ����
	public static int FEATHER_TIME;
	public static int FEATHER_NUMBER;
	public static int CLAN_NUMBER;
	public static int CASTLE_NUMBER;
	public static double RATE_CLAN_XP;
	public static double RATE_CASTLE_XP;
	public static double RATE_7_DMG_RATE;// ��þ��Ÿ �ܺ�ȭ
	public static int RATE_7_DMG_PER;
	public static double RATE_8_DMG_RATE;
	public static int RATE_8_DMG_PER;
	public static double RATE_9_DMG_RATE;
	public static int RATE_9_DMG_PER;
	public static double RATE_10_DMG_RATE;
	public static int RATE_10_DMG_PER;
	public static double RATE_11_DMG_RATE;
	public static int RATE_11_DMG_PER;
	public static double RATE_12_DMG_RATE;
	public static int RATE_12_DMG_PER;
	public static double RATE_13_DMG_RATE;
	public static int RATE_13_DMG_PER;
	public static double RATE_14_DMG_RATE;
	public static int RATE_14_DMG_PER;
	public static double RATE_15_DMG_RATE;
	public static int RATE_15_DMG_PER;
	public static double RATE_16_DMG_RATE;
	public static int RATE_16_DMG_PER;
	public static double RATE_17_DMG_RATE;
	public static int RATE_17_DMG_PER;
	public static double RATE_18_DMG_RATE;
	public static int RATE_18_DMG_PER;
	public static int AC_170;
	public static int AC_160;
	public static int AC_150;
	public static int AC_140;
	public static int AC_130;
	public static int AC_120;
	public static int AC_110;
	public static int AC_100;
	public static int AC_90;
	public static int AC_80;
	public static int AC_70;
	public static int AC_60;
	public static int AC_50;
	public static int AC_40;
	public static int AC_30;
	public static int AC_20;
	public static int AC_10;

	//// �ڵ�����
	public static int systime;
	public static String sys1;
	public static String sys2;
	public static String sys3;
	public static String sys4;
	public static String sys5;
	public static String sys6;
	public static String sys7;
	public static String sys8;
	public static String sys9;
	public static String sys10;
	public static String sys11;
	public static String sys12;
	public static String sys13;
	public static String sys14;
	public static String sys15;
	public static String sys16;

	/** AltSettings control */
	public static short GLOBAL_CHAT_LEVEL;
	public static short WHISPER_CHAT_LEVEL;
	public static byte AUTO_LOOT;
	public static int LOOTING_RANGE;
	public static boolean ALT_NONPVP;
	public static boolean ALT_ATKMSG;
	public static boolean CHANGE_TITLE_BY_ONESELF;
	public static int MAX_CLAN_MEMBER;
	public static boolean CLAN_ALLIANCE;
	public static int MAX_PT;
	public static int MAX_CHAT_PT;
	public static boolean SIM_WAR_PENALTY;
	public static boolean GET_BACK;
	public static String ALT_ITEM_DELETION_TYPE;
	public static int ALT_ITEM_DELETION_TIME;
	public static int ALT_ITEM_DELETION_RANGE;
	public static int ALT_PRIVATE_WAREHOUSE_LEVEL;
	public static int ALT_PRIVATE_SHOP_LEVEL;
	public static int EVENT_DRAGON_DIAMOND_CHANCE;

	public static int EVENT_DRAGON_DIAMOND_CHANCE2;

	public static int �����_�巹��ũ_����;
	public static int �����_�Ƹ�ī_����;
	public static int �����_�ҷ���_����;
	public static int ���ø������_����;

	public static int ���۵��_����;
	public static int ���ݵ��_����;
	public static int ��Ƽ�����_����;
	public static int ��ȭ���ڵ��_����;
	public static int ������_����;

	public static int ����2���_����;

	public static int �����ڹ���뷱����ġ;
	public static int �����ڰ��ʹ뷱����ġ;
	public static int warmember;
	public static int ���ĵ��_�׷���_����;

	public static int EVENT_������������_����;
	public static int EVENT_�����ָ��ָӴ�_����;

	public static L1NpcInstance �Ƴ����� = null;

	public static int EVENT_SEHO_BOOMB_CHANCE;

	public static boolean DRAGON_2DAY_EVENT;
	public static boolean DANTES_2DAY_EVENT;

	public static boolean RuphyBrave_3DAY_EVENT;

	public static boolean Orim_EVENT;

	public static boolean Dragon_3DAY_Event;

	public static boolean Dragon_1DAY_Event;

	public static boolean ���ø���_Event;
	public static boolean ����_Event;
	public static boolean �Ƹ�ī_Event;
	public static boolean ���_Event;

	public static boolean ����_Event;
	public static boolean ��Ƽ��_Event;
	public static boolean ���ۼ���_Event;

	public static boolean ��ȭ����_Event;
	public static boolean �Ƴ���̺�Ʈ;

	public static int �����Ǻ�;
	public static int �����ǳ�����;
	public static int ����Ȯ��;
	public static int ����Ȯ��;
	public static int ��������;
	public static int �̷�����Ȯ��;
	public static int ����Ȯ��;
	public static int ���극��ũ;
	public static int �׸�Ȯ��;
	public static int ����Ȯ��;
	public static int ��������Ȯ��;
	public static int Ŭ����Ʒ���;
	public static int ���ͳ�ƼȮ��;
	public static int ���Ȯ��;
	public static int DEMOLITIONȮ��;
	public static int ���׶�Ȯ��;
	public static int �����콺��Ȯ��;
	public static int ������ƮȮ��;
	public static int ����Ȯ��;
	public static int �����̾�;
	public static int �˽���Ȯ��;
	public static int ���Ȯ��;
	public static double �õ�����;
	public static int �������;

	public static boolean Dragon_14_12_12_Event;

	public static boolean Chopa_Event;

	public static boolean ���1212���������;
	public static boolean �ҷ���_Event;

	public static boolean ��ٵ��������;
	public static boolean ��ٵ��������1;
	public static boolean ���ø������������;
	public static boolean ���۵��������;
	public static boolean �Ƹ�ī���������;
	public static boolean ������������;

	public static boolean ��Ƽ�����������;
	public static boolean ��ȭ���ڵ��������;
	public static boolean ���ݵ��������;

	public static boolean ����2���������;

	public static boolean ���ĵ��������;

	public static boolean �ҷ���������;

	public static L1NpcInstance ��ٿ��Ǿ�;
	public static L1NpcInstance ���ø������Ǿ�;

	public static L1NpcInstance ���ۿ��Ǿ�;

	public static L1NpcInstance �Ƹ�ī���Ǿ�;

	public static L1NpcInstance ���1212���Ǿ�;

	public static L1NpcInstance ���Ŀ��Ǿ�;

	public static L1NpcInstance �ҷ����Ʒ翣�Ǿ�;
	public static L1NpcInstance �ҷ����ٷ翣�Ǿ�;

	/****** �̺�Ʈ ******/
	public static boolean ALT_HALLOWEENEVENT; // �ҷ���
	public static boolean ALT_HALLOWEENEVENT2009; // �ҷ���(2009��)
	public static boolean ALT_FANTASYEVENT; // ȯ��
	public static boolean ALT_CHUSEOKEVENT; // �߼�(09.09.24)
	public static boolean Big_Praseant; // �߼�(09.09.24)
	public static boolean Robot_Gaho; // �߼�(09.09.24)
	public static boolean ALT_FEATURE;
	public static boolean �巡�Ｚ���ǹ����̺�Ʈ; // �߼�(09.09.24)

	public static boolean ALT_WHO_COMMAND;
	public static boolean ALT_REVIVAL_POTION;
	public static int ALT_WAR_TIME;
	public static int ALT_WAR_TIME_UNIT;
	public static int ALT_WAR_INTERVAL;
	public static int ALT_WAR_INTERVAL_UNIT;
	public static int ALT_RATE_OF_DUTY;
	public static boolean SPAWN_HOME_POINT;
	public static int SPAWN_HOME_POINT_RANGE;
	public static int SPAWN_HOME_POINT_COUNT;
	public static int SPAWN_HOME_POINT_DELAY;
	public static boolean INIT_BOSS_SPAWN;
	public static int ELEMENTAL_STONE_AMOUNT;
	public static int HOUSE_TAX_INTERVAL;
	public static int MAX_DOLL_COUNT;
	public static boolean RETURN_TO_NATURE;
	public static int MAX_NPC_ITEM;
	public static int MAX_PERSONAL_WAREHOUSE_ITEM;
	public static int MAX_CLAN_WAREHOUSE_ITEM;
	public static boolean DELETE_CHARACTER_AFTER_7DAYS;
	public static int GMCODE;
	public static int DELETE_DB_DAYS;

	public static int �κ�����Ȱ1;
	public static int �κ�����Ȱ2;
	public static int �κ�����Ȱ3;
	public static int �κ�����Ȱ4;
	public static int �κ�����Ȱ5;
	public static int �κ�Į1;
	public static int �κ�Į2;
	public static int �κ�Į3;
	public static int �κ�Į4;
	public static int �κ�Į5;
	public static int �κ�Į6;
	public static int �κ�Į7;
	public static int �κ�Į8;
	public static int �κ�Į9;
	public static int ����Ÿ��Ȱ;
	public static int ����Ÿ��Į1;
	public static int ����Ÿ��Į2;
	public static int ����Ÿ��Į3;
	public static int ����Ÿ��Į4;
	public static int ����Ÿ��Į5;
	public static int ����Ÿ��Į6;
	public static int ����Ÿ�԰���ġ;
	public static int ����Ÿ��Ȱ���Ǿ����̵�;
	public static int ����Ÿ��Į���Ǿ����̵�1;
	public static int ����Ÿ��Į���Ǿ����̵�2;
	public static int ����Ÿ��Į���Ǿ����̵�3;
	public static int ����Ÿ��Į���Ǿ����̵�4;
	public static int ����Ÿ��Į���Ǿ����̵�5;
	public static int ����Ÿ��Į���Ǿ����̵�6;
	public static int ����ġ�������Ǿ����̵�;
	public static int �����۷���;
	public static int ���������;
	public static int ����������;
	public static int ���������;
	public static int ����������;
	public static int ���������;
	public static int �����ɳ���;
	public static int ����𳻼�;
	public static int ����������;
	public static int ����Ȱ�⺻Ÿ��ġPC;
	public static int ����Ȱ����Ÿ��ġPC;
	public static int ���⺻Ÿ��ġPC;
	public static int ��緣��Ÿ��ġPC;
	public static int ����⺻Ÿ��ġPC;
	public static int ���緣��Ÿ��ġPC;
	public static int �ٿ��⺻Ÿ��ġPC;
	public static int �ٿ�����Ÿ��ġPC;
	public static int ����⺻Ÿ��ġPC;
	public static int ���緣��Ÿ��ġPC;
	public static int ���˿�⺻Ÿ��ġPC;
	public static int ���˿䷣��Ÿ��ġPC;
	public static int ����Ȱ�⺻Ÿ��ġNPC;
	public static int ����Ȱ����Ÿ��ġNPC;
	public static int ���⺻Ÿ��ġNPC;
	public static int ��緣��Ÿ��ġNPC;
	public static int ����⺻Ÿ��ġNPC;
	public static int ���緣��Ÿ��ġNPC;
	public static int �ٿ��⺻Ÿ��ġNPC;
	public static int �ٿ�����Ÿ��ġNPC;
	public static int ����⺻Ÿ��ġNPC;
	public static int ���緣��Ÿ��ġNPC;
	public static int ���˿�⺻Ÿ��ġNPC;
	public static int ���˿䷣��Ÿ��ġNPC;
	public static int ������⺻ȸ����;
	public static int �����෣��ȸ����;
	public static boolean ����������Ŭ��;
	public static boolean ����������;
	public static boolean ��������ǳ;
	public static boolean �����ֿ���;
	public static boolean ������;
	public static boolean ���˹�;
	public static boolean �κ�����;

	public static boolean isGmchat = true; // by�ǵ��� ����ä�� �����Ŷ
	public static byte MAX_����_DUNGEON_LEVEL;

	public static byte MAX_�Ŀ���_DUNGEON_LEVEL;

	public static byte �뷹�̵巹��;

	public static byte �߶�ī�����̵巹��;

	public static byte MIN_���ž_DUNGEON_LEVEL;
	public static String �ڵ�����_����;

	public static boolean ĳ���ͺ����뿩�� = true;

	public static String ������üũ����;

	public static String ��������Ʈ;

	public static String �ڵ�����_��;

	public static boolean ACCOUNT_PASSWORD;// �н����� ��ȣȭ ���� �ҽ� 0813 �߰�

	/** CharSettings control */
	public static int PRINCE_MAX_HP;

	public static int MAXLEVEL;

	public static int PRINCE_MAX_MP;
	public static int KNIGHT_MAX_HP;
	public static int KNIGHT_MAX_MP;
	public static int ELF_MAX_HP;
	public static int ELF_MAX_MP;
	public static int WIZARD_MAX_HP;
	public static int WIZARD_MAX_MP;
	public static int DARKELF_MAX_HP;
	public static int DARKELF_MAX_MP;
	public static int DRAGONKNIGHT_MAX_HP;
	public static int DRAGONKNIGHT_MAX_MP;
	public static int BLACKWIZARD_MAX_HP;
	public static int BLACKWIZARD_MAX_MP;

	public static int LV50_EXP;
	public static int LV51_EXP;
	public static int LV52_EXP;
	public static int LV53_EXP;
	public static int LV54_EXP;
	public static int LV55_EXP;
	public static int LV56_EXP;
	public static int LV57_EXP;
	public static int LV58_EXP;
	public static int LV59_EXP;
	public static int LV60_EXP;
	public static int LV61_EXP;
	public static int LV62_EXP;
	public static int LV63_EXP;
	public static int LV64_EXP;
	public static int LV65_EXP;
	public static int LV66_EXP;
	public static int LV67_EXP;
	public static int LV68_EXP;
	public static int LV69_EXP;
	public static int LV70_EXP;
	public static int LV71_EXP;
	public static int LV72_EXP;
	public static int LV73_EXP;
	public static int LV74_EXP;
	public static int LV75_EXP;
	public static int LV76_EXP;
	public static int LV77_EXP;
	public static int LV78_EXP;
	public static int LV79_EXP;
	public static int LV80_EXP;
	public static int LV81_EXP;
	public static int LV82_EXP;
	public static int LV83_EXP;
	public static int LV84_EXP;
	public static int LV85_EXP;
	public static int LV86_EXP;
	public static int LV87_EXP;
	public static int LV88_EXP;
	public static int LV89_EXP;
	public static int LV90_EXP;
	public static int LV91_EXP;
	public static int LV92_EXP;
	public static int LV93_EXP;
	public static int LV94_EXP;
	public static int LV95_EXP;
	public static int LV96_EXP;
	public static int LV97_EXP;
	public static int LV98_EXP;
	public static int LV99_EXP;
	public static int EXP_GIVE;

	public static int ���̵�ð�;
	public static int �Ƴ���̺�Ʈ�ð�;

	/** �����ͺ��̽� Ǯ ���� */
	public static int min;
	public static int max;
	public static boolean run;

	/** Configuration files */
	public static final String SERVER_CONFIG_FILE = "./config/server.properties";
	public static final String RATES_CONFIG_FILE = "./config/rates.properties";
	public static final String ALT_SETTINGS_FILE = "./config/altsettings.properties";
	public static final String BOT_SETTINGS_FILE = "./config/botsettings.properties";
	public static final String CHAR_SETTINGS_CONFIG_FILE = "./config/charsettings.properties";
	public static final String CHOLONG_SETTINGS_CONFIG_FILE = "./config/Eventlink.properties";
	public static boolean shutdown = false;
	// �α� ǥ���Ұ�����
	public static boolean LOGGER = true;
	// ��Ŷ ǥ�� �Ұ�����
	public static boolean PACKET = false;
	/** �� ���� ���� */

	// NPC�κ��� ���̸��� �� �ִ� MP�Ѱ�
	public static final int MANA_DRAIN_LIMIT_PER_NPC = 40;

	// 1ȸ�� �������� ���̸��� �� �ִ� MP�Ѱ�(SOM, ��ö SOM)
	public static final int MANA_DRAIN_LIMIT_PER_SOM_ATTACK = 9;
	public static String _name = ".clftjdtkdlek";

	public static void load() {
		_log.info("loading gameserver config");
		// server.properties
		try {
			Properties serverSettings = new Properties();
			InputStream is = new FileInputStream(new File(SERVER_CONFIG_FILE));
			serverSettings.load(is);
			is.close();

			/** ������ ���̽� Ǯ */
			min = Integer.parseInt(serverSettings.getProperty("min"));
			max = Integer.parseInt(serverSettings.getProperty("max"));
			run = Boolean.parseBoolean(serverSettings.getProperty("run"));

			ĳ���ͺ����뿩�� = Boolean.parseBoolean(serverSettings.getProperty("Charpass"));

			GAME_SERVER_TYPE = Integer.parseInt(serverSettings.getProperty("ServerType", "0"));

			GAME_SERVER_HOST_NAME = serverSettings.getProperty("GameserverHostname", "*");
			if ("*".equals(GAME_SERVER_HOST_NAME))
				GAME_SERVER_HOST_NAME = InetAddress.getLocalHost().getHostAddress();

			GAME_SERVER_PORT = Integer.parseInt(serverSettings.getProperty("GameserverPort", "2000"));

			DB_DRIVER = serverSettings.getProperty("Driver", "com.mysql.jdbc.Driver");

			DB_URL = serverSettings.getProperty("URL", "jdbc:mysql://localhost/l1jdb?useUnicode=true&characterEncoding=euckr");

			DB_LOGIN = serverSettings.getProperty("Login", "root");

			DB_PASSWORD = serverSettings.getProperty("Password", "");

			THREAD_P_TYPE_GENERAL = Integer.parseInt(serverSettings.getProperty("GeneralThreadPoolType", "0"), 10);

			THREAD_P_SIZE_GENERAL = Integer.parseInt(serverSettings.getProperty("GeneralThreadPoolSize", "0"), 10);

			CLIENT_LANGUAGE = Integer.parseInt(serverSettings.getProperty("ClientLanguage", "4"));

			TIME_ZONE = serverSettings.getProperty("TimeZone", "KST");

			HOSTNAME_LOOKUPS = Boolean.parseBoolean(serverSettings.getProperty("HostnameLookups", "false"));

			�վ����� = Boolean.parseBoolean(serverSettings.getProperty("dduldef", "true"));
			��ü��Ż����� = Boolean.parseBoolean(serverSettings.getProperty("ucedef", "true"));

			AUTOMATIC_KICK = Integer.parseInt(serverSettings.getProperty("AutomaticKick", "10"));

			AUTO_CREATE_ACCOUNTS = Boolean.parseBoolean(serverSettings.getProperty("AutoCreateAccounts", "true"));

			MAX_ONLINE_USERS = Short.parseShort(serverSettings.getProperty("MaximumOnlineUsers", "30"));

			CACHE_MAP_FILES = Boolean.parseBoolean(serverSettings.getProperty("CacheMapFiles", "false"));

			LOAD_V2_MAP_FILES = Boolean.parseBoolean(serverSettings.getProperty("LoadV2MapFiles", "false"));

			CHECK_MOVE_INTERVAL = Boolean.parseBoolean(serverSettings.getProperty("CheckMoveInterval", "false"));

			CHECK_ATTACK_INTERVAL = Boolean.parseBoolean(serverSettings.getProperty("CheckAttackInterval", "false"));

			CHECK_SPELL_INTERVAL = Boolean.parseBoolean(serverSettings.getProperty("CheckSpellInterval", "false"));

			INJUSTICE_COUNT = Short.parseShort(serverSettings.getProperty("InjusticeCount", "10"));

			JUSTICE_COUNT = Integer.parseInt(serverSettings.getProperty("JusticeCount", "4"));

			CHECK_STRICTNESS = Integer.parseInt(serverSettings.getProperty("CheckStrictness", "102"));

			ACCOUNT_PASSWORD = Boolean.parseBoolean(serverSettings.getProperty("AccountPassword", "false"));// �н����� ��ȣȭ ���� �ҽ� 0813 �߰�

			LOGGING_WEAPON_ENCHANT = Byte.parseByte(serverSettings.getProperty("LoggingWeaponEnchant", "0"));

			LOGGING_ARMOR_ENCHANT = Byte.parseByte(serverSettings.getProperty("LoggingArmorEnchant", "0"));

			LOGGING_TIME = Integer.parseInt(serverSettings.getProperty("LoggingTime", "120"));

			LOGGING_CHAT_NORMAL = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatNormal", "false"));

			LOGGING_CHAT_WHISPER = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatWhisper", "false"));

			LOGGING_CHAT_SHOUT = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatShout", "false"));

			LOGGING_CHAT_WORLD = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatWorld", "false"));

			LOGGING_CHAT_CLAN = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatClan", "false"));

			LOGGING_CHAT_PARTY = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatParty", "false"));

			LOGGING_CHAT_COMBINED = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatCombined", "false"));

			LOGGING_CHAT_CHAT_PARTY = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatChatParty", "false"));

			AUTOSAVE_INTERVAL = Integer.parseInt(serverSettings.getProperty("AutosaveInterval", "1200"), 10);

			AUTOSAVE_INTERVAL_INVENTORY = Integer.parseInt(serverSettings.getProperty("AutosaveIntervalOfInventory", "300"), 10);

			SKILLTIMER_IMPLTYPE = Integer.parseInt(serverSettings.getProperty("SkillTimerImplType", "1"));

			NPCAI_IMPLTYPE = Integer.parseInt(serverSettings.getProperty("NpcAIImplType", "1"));

			TELNET_SERVER = Boolean.parseBoolean(serverSettings.getProperty("TelnetServer", "false"));

			TELNET_SERVER_PORT = Integer.parseInt(serverSettings.getProperty("TelnetServerPort", "23"));

			PC_RECOGNIZE_RANGE = Integer.parseInt(serverSettings.getProperty("PcRecognizeRange", "20"));

			CHARACTER_CONFIG_IN_SERVER_SIDE = Boolean.parseBoolean(serverSettings.getProperty("CharacterConfigInServerSide", "true"));

			ALLOW_2PC = Boolean.parseBoolean(serverSettings.getProperty("Allow2PC", "true"));

			LEVEL_DOWN_RANGE = Integer.parseInt(serverSettings.getProperty("LevelDownRange", "0"));

			SEND_PACKET_BEFORE_TELEPORT = Boolean.parseBoolean(serverSettings.getProperty("SendPacketBeforeTeleport", "false"));
			DETECT_DB_RESOURCE_LEAKS = Boolean.parseBoolean(serverSettings.getProperty("EnableDatabaseResourceLeaksDetection", "false"));
			// 123123123

			AUTO_CHECK = Boolean.parseBoolean(serverSettings.getProperty("AutoCheck", "false"));

			WAR_TIME_AUTO_SETTING = Boolean.parseBoolean(serverSettings.getProperty("WarTimeAutoSetting", "false"));

			GameServer.�ű�����_����ġ���޴� = Boolean.parseBoolean(serverSettings.getProperty("NewvieBonus", "false"));
			// DETECT_DB_RESOURCE_LEAKS =
			// Boolean.parseBoolean(serverSettings.getProperty("EnableDatabaseResourceLeaksDetection",
			// "false"));
			// DETECT_DB_RESOURCE_LEAKS =
			// Boolean.parseBoolean(serverSettings.getProperty("EnableDatabaseResourceLeaksDetection",
			// "false"));
			// =========== IP Check[#Config] ===========
			// AUTH_CONNECT =
			// Boolean.parseBoolean(serverSettings.getProperty("AuthConnect",
			// "true"));
			// =========== IP Check[#Config] ===========
			// =========== IP Check[#Config] ===========
			// CHECK_CONNECT =
			// Boolean.parseBoolean(serverSettings.getProperty("CheckConnect",
			// "true"));
			// =========== IP Check[#Config] ===========

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + SERVER_CONFIG_FILE + " File.");
		}

		// rates.properties
		/*
		 * try { Properties rateSettings = new Properties(); InputStream is = new
		 * FileInputStream(new File(RATES_CONFIG_FILE)); rateSettings.load(is);
		 * is.close();
		 */

		// rates.properties
		try {
			Properties rateSettings = new Properties();
			// InputStream is = new FileInputStream(new File(RATES_CONFIG_FILE));
			FileReader is = new FileReader(new File(RATES_CONFIG_FILE));
			rateSettings.load(is);
			is.close();

			systime = Integer.parseInt(rateSettings.getProperty("systime", "30"));
			sys1 = rateSettings.getProperty("sys1", "");
			sys2 = rateSettings.getProperty("sys2", "");
			sys3 = rateSettings.getProperty("sys3", "");
			sys4 = rateSettings.getProperty("sys4", "");
			sys5 = rateSettings.getProperty("sys5", "");
			sys6 = rateSettings.getProperty("sys6", "");
			sys7 = rateSettings.getProperty("sys7", "");
			sys8 = rateSettings.getProperty("sys8", "");
			sys9 = rateSettings.getProperty("sys9", "");
			sys10 = rateSettings.getProperty("sys10", "");
			sys11 = rateSettings.getProperty("sys11", "");
			sys12 = rateSettings.getProperty("sys12", "");
			sys13 = rateSettings.getProperty("sys13", "");
			sys14 = rateSettings.getProperty("sys14", "");
			sys15 = rateSettings.getProperty("sys15", "");
			sys16 = rateSettings.getProperty("sys16", "");
			RATE_XP = Double.parseDouble(rateSettings.getProperty("RateXp", "1.0"));

			fishrate = Double.parseDouble(rateSettings.getProperty("fishexp", "112500"));

			RATE_LAWFUL = Double.parseDouble(rateSettings.getProperty("RateLawful", "1.0"));

			RATE_KARMA = Double.parseDouble(rateSettings.getProperty("RateKarma", "1.0"));

			RATE_DROP_ADENA = Double.parseDouble(rateSettings.getProperty("RateDropAdena", "1.0"));

			RATE_DROP_ITEMS = Double.parseDouble(rateSettings.getProperty("RateDropItems", "1.0"));

			RATE_ROBOT_TIME = Integer.parseInt(rateSettings.getProperty("RateRobotTime", "12")); // ����PC(���)

			ENCHANT_CHANCE_WEAPON = Integer.parseInt(rateSettings.getProperty("EnchantChanceWeapon", "1"));
			ENCHANT_CHANCE_WEAPON7 = Integer.parseInt(rateSettings.getProperty("EnchantChanceWeapon7", "1"));
			ENCHANT_CHANCE_WEAPON8 = Integer.parseInt(rateSettings.getProperty("EnchantChanceWeapon8", "1"));
			ENCHANT_CHANCE_WEAPON9 = Integer.parseInt(rateSettings.getProperty("EnchantChanceWeapon9", "1"));
			ENCHANT_CHANCE_WEAPON10 = Integer.parseInt(rateSettings.getProperty("EnchantChanceWeapon10", "1"));

			ENCHANT_CHANCE_ARMOR = Integer.parseInt(rateSettings.getProperty("EnchantChanceArmor", "1"));

			ENCHANT_CHANCE_ACCESSORY = Integer.parseInt(rateSettings.getProperty("EnchantChanceAccessory", "5"));
			Ȱ���� = Integer.parseInt(rateSettings.getProperty("Bowhit", "6"));
			�����ǥ = Integer.parseInt(rateSettings.getProperty("cash", "10"));

			RATE_WEIGHT_LIMIT = Double.parseDouble(rateSettings.getProperty("RateWeightLimit", "1"));

			RATE_WEIGHT_LIMIT_PET = Double.parseDouble(rateSettings.getProperty("RateWeightLimitforPet", "1"));

			RATE_SHOP_SELLING_PRICE = Double.parseDouble(rateSettings.getProperty("RateShopSellingPrice", "1.0"));

			RATE_SHOP_PURCHASING_PRICE = Double.parseDouble(rateSettings.getProperty("RateShopPurchasingPrice", "1.0"));

			RATE_DREAM = Integer.parseInt(rateSettings.getProperty("Ratedream", "1")); // �����������̺�Ʈ

			CREATE_CHANCE_DIARY = Integer.parseInt(rateSettings.getProperty("CreateChanceDiary", "33"));

			CREATE_CHANCE_RECOLLECTION = Integer.parseInt(rateSettings.getProperty("CreateChanceRecollection", "90"));

			CREATE_CHANCE_MYSTERIOUS = Integer.parseInt(rateSettings.getProperty("CreateChanceMysterious", "90"));

			CREATE_CHANCE_PROCESSING = Integer.parseInt(rateSettings.getProperty("CreateChanceProcessing", "90"));

			CREATE_CHANCE_PROCESSING_DIAMOND = Integer.parseInt(rateSettings.getProperty("CreateChanceProcessingDiamond", "90"));

			CREATE_CHANCE_DANTES = Integer.parseInt(rateSettings.getProperty("CreateChanceDantes", "90"));

			CREATE_CHANCE_ANCIENT_AMULET = Integer.parseInt(rateSettings.getProperty("CreateChanceAncientAmulet", "90"));

			CREATE_CHANCE_HISTORY_BOOK = Integer.parseInt(rateSettings.getProperty("CreateChanceHistoryBook", "50"));

			MAX_WEAPON = Integer.parseInt(rateSettings.getProperty("MaxWeapon", "12"));

			MAX_ARMOR = Integer.parseInt(rateSettings.getProperty("MaxArmor", "10"));

			MAX_WEAPON1 = Integer.parseInt(rateSettings.getProperty("MaxWeapon1", "12"));// ����

			MAX_LEVEL = Integer.parseInt(rateSettings.getProperty("Maxlevel", "12"));// �űԺ�ȣ����
			��Ʋ�����巹�� = Integer.parseInt(rateSettings.getProperty("BattleLevel", "55"));
			��Ʋ���۵����� = Boolean.parseBoolean(rateSettings.getProperty("BattleZone", "true"));
			��Ʋ�������� = rateSettings.getProperty("BattleItem", "");
			��Ʋ�������۰��� = rateSettings.getProperty("BattleCount", "");

			����1�ܾ����� = rateSettings.getProperty("Dogamone", "");
			����1�ܾ����۰��� = rateSettings.getProperty("DogamoneCount", "");

			����2�ܾ����� = rateSettings.getProperty("Dogamto", "");
			����2�ܾ����۰��� = rateSettings.getProperty("DogamtoCount", "");

			����3�ܾ����� = rateSettings.getProperty("Dogamthr", "");
			����3�ܾ����۰��� = rateSettings.getProperty("DogamthrCount", "");

			FEATHER_TIME = Integer.parseInt(rateSettings.getProperty("FeatherTime", "10"));

			FEATHER_NUMBER = Integer.parseInt(rateSettings.getProperty("FeatherNumber", "10"));

			CLAN_NUMBER = Integer.parseInt(rateSettings.getProperty("ClanNumber", "10"));

			CASTLE_NUMBER = Integer.parseInt(rateSettings.getProperty("CastleNumber", "50"));

			�������ִ���æ = Integer.parseInt(rateSettings.getProperty("SnapperMaxEnchant", "8"));
			��Ƽ���ִ���æ = Integer.parseInt(rateSettings.getProperty("RoomteeceMaxEnchant", "8"));
			��ű��ִ���æ = Integer.parseInt(rateSettings.getProperty("acaccessoryMaxEnchant", "9"));
			��ȣ�ֹ����ִ���æ = Integer.parseInt(rateSettings.getProperty("Maximumboji", "7"));
			�Ƴ���̺�Ʈ�ð� = Integer.parseInt(rateSettings.getProperty("AnoldeTime", "2"));
			�����ǳ����� = Integer.parseInt(rateSettings.getProperty("Leafitem", "100"));
			�����Ǻ� = Integer.parseInt(rateSettings.getProperty("Eternalitem", "100"));

			// RATE_CLAN_XP =
			// Double.parseDouble(rateSettings.getProperty("RateClanXp",
			// "1.0"));

			// RATE_CASTLE_XP =
			// Double.parseDouble(rateSettings.getProperty("RateCastleXp",
			// "1.0"));

			RATE_7_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_7_Dmg_Rate", "1.5"));
			RATE_8_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_8_Dmg_Rate", "1.5"));
			RATE_9_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_9_Dmg_Rate", "2.0"));
			RATE_10_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_10_Dmg_Rate", "2.0"));
			RATE_11_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_11_Dmg_Rate", "2.0"));
			RATE_12_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_12_Dmg_Rate", "2.0"));
			RATE_13_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_13_Dmg_Rate", "2.0"));
			RATE_14_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_14_Dmg_Rate", "2.0"));
			RATE_15_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_15_Dmg_Rate", "2.0"));
			RATE_16_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_16_Dmg_Rate", "2.5"));
			RATE_17_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_17_Dmg_Rate", "2.5"));
			RATE_18_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_18_Dmg_Rate", "2.5"));

			RATE_7_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_7_Dmg_Per", "5"));
			RATE_8_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_8_Dmg_Per", "10"));
			RATE_9_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_9_Dmg_Per", "20"));
			RATE_10_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_10_Dmg_Per", "30"));
			RATE_11_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_11_Dmg_Per", "40"));
			RATE_12_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_12_Dmg_Per", "50"));
			RATE_13_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_13_Dmg_Per", "60"));
			RATE_14_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_14_Dmg_Per", "70"));
			RATE_15_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_15_Dmg_Per", "80"));
			RATE_16_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_16_Dmg_Per", "90"));
			RATE_17_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_17_Dmg_Per", "90"));
			RATE_18_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_18_Dmg_Per", "100"));

			�õ����� = Double.parseDouble(rateSettings.getProperty("Immune_to", "0.5"));

			AC_170 = Integer.parseInt(rateSettings.getProperty("AC_170", "0"));
			AC_160 = Integer.parseInt(rateSettings.getProperty("AC_160", "0"));
			AC_150 = Integer.parseInt(rateSettings.getProperty("AC_150", "0"));
			AC_140 = Integer.parseInt(rateSettings.getProperty("AC_140", "0"));
			AC_130 = Integer.parseInt(rateSettings.getProperty("AC_130", "0"));
			AC_120 = Integer.parseInt(rateSettings.getProperty("AC_120", "0"));
			AC_110 = Integer.parseInt(rateSettings.getProperty("AC_110", "0"));
			AC_100 = Integer.parseInt(rateSettings.getProperty("AC_100", "0"));
			AC_90 = Integer.parseInt(rateSettings.getProperty("AC_90", "0"));
			AC_80 = Integer.parseInt(rateSettings.getProperty("AC_80", "0"));
			AC_70 = Integer.parseInt(rateSettings.getProperty("AC_70", "0"));
			AC_60 = Integer.parseInt(rateSettings.getProperty("AC_60", "0"));
			AC_50 = Integer.parseInt(rateSettings.getProperty("AC_50", "0"));
			AC_40 = Integer.parseInt(rateSettings.getProperty("AC_40", "0"));
			AC_30 = Integer.parseInt(rateSettings.getProperty("AC_30", "0"));
			AC_20 = Integer.parseInt(rateSettings.getProperty("AC_20", "0"));
			AC_10 = Integer.parseInt(rateSettings.getProperty("AC_10", "0"));

			���̵�ð� = Integer.parseInt(rateSettings.getProperty("Raidtime", "1"));

			����Ȯ��2 = Integer.parseInt(rateSettings.getProperty("dollchance2", "1"));
			����Ȯ��3 = Integer.parseInt(rateSettings.getProperty("dollchance3", "1"));
			����Ȯ��4 = Integer.parseInt(rateSettings.getProperty("dollchance4", "1"));
			����Ȯ��5 = Integer.parseInt(rateSettings.getProperty("dollchance5", "1"));
			��ȣ�ֹ���Ȯ��0 = Integer.parseInt(rateSettings.getProperty("bojiscroll0", "1"));
			��ȣ�ֹ���Ȯ��1 = Integer.parseInt(rateSettings.getProperty("bojiscroll1", "1"));
			��ȣ�ֹ���Ȯ��2 = Integer.parseInt(rateSettings.getProperty("bojiscroll2", "1"));
			��ȣ�ֹ���Ȯ��3 = Integer.parseInt(rateSettings.getProperty("bojiscroll3", "1"));
			��ȣ�ֹ���Ȯ��4 = Integer.parseInt(rateSettings.getProperty("bojiscroll4", "1"));
			��ȣ�ֹ���Ȯ��5 = Integer.parseInt(rateSettings.getProperty("bojiscroll5", "1"));
			��ȣ�ֹ���Ȯ��6 = Integer.parseInt(rateSettings.getProperty("bojiscroll6", "1"));
			����Ȯ�� = Integer.parseInt(rateSettings.getProperty("janginchance", "1"));
			�⼮�ʱ�ȭ�ð� = Integer.parseInt(rateSettings.getProperty("chutime", "9"));
			AINHASADDPRESETTIME = Integer.parseInt(rateSettings.getProperty("ainhasaddpresettime", "18"));

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + RATES_CONFIG_FILE + " File.");
		}

		// altsettings.properties
		try {
			Properties altSettings = new Properties();
			InputStream is = new FileInputStream(new File(ALT_SETTINGS_FILE));
			altSettings.load(is);
			is.close();

			�űԷ��� = Short.parseShort(altSettings.getProperty("biginnerlev", "56"));

			���������� = Integer.parseInt(altSettings.getProperty("joyrune", "8"));

			�����ϻ�嵥�ϸ�����Ʈ = Integer.parseInt(altSettings.getProperty("ainhasadDP", "10000000"));

			GLOBAL_CHAT_LEVEL = Short.parseShort(altSettings.getProperty("GlobalChatLevel", "30"));

			WHISPER_CHAT_LEVEL = Short.parseShort(altSettings.getProperty("WhisperChatLevel", "7"));

			AUTO_LOOT = Byte.parseByte(altSettings.getProperty("AutoLoot", "2"));

			LOOTING_RANGE = Integer.parseInt(altSettings.getProperty("LootingRange", "3"));

			ALT_NONPVP = Boolean.parseBoolean(altSettings.getProperty("NonPvP", "true"));

			ALT_ATKMSG = Boolean.parseBoolean(altSettings.getProperty("AttackMessageOn", "true"));

			CHANGE_TITLE_BY_ONESELF = Boolean.parseBoolean(altSettings.getProperty("ChangeTitleByOneself", "false"));

			MAX_CLAN_MEMBER = Integer.parseInt(altSettings.getProperty("MaxClanMember", "0"));

			CLAN_ALLIANCE = Boolean.parseBoolean(altSettings.getProperty("ClanAlliance", "true"));

			MAX_PT = Integer.parseInt(altSettings.getProperty("MaxPT", "8"));

			EXP_GIVE = Integer.parseInt(altSettings.getProperty("ExpGive", "70"));

			MAX_CHAT_PT = Integer.parseInt(altSettings.getProperty("MaxChatPT", "8"));

			SIM_WAR_PENALTY = Boolean.parseBoolean(altSettings.getProperty("SimWarPenalty", "true"));

			GET_BACK = Boolean.parseBoolean(altSettings.getProperty("GetBack", "false"));

			ALT_ITEM_DELETION_TYPE = altSettings.getProperty("ItemDeletionType", "auto");

			ALT_ITEM_DELETION_TIME = Integer.parseInt(altSettings.getProperty("ItemDeletionTime", "10"));

			ALT_ITEM_DELETION_RANGE = Integer.parseInt(altSettings.getProperty("ItemDeletionRange", "5"));

			ALT_PRIVATE_WAREHOUSE_LEVEL = Integer.parseInt(altSettings.getProperty("PrivateWarehouseLevel", "5"));

			ALT_HALLOWEENEVENT = Boolean.parseBoolean(altSettings.getProperty("HalloweenEvent", "true"));

			ALT_HALLOWEENEVENT2009 = Boolean.parseBoolean(altSettings.getProperty("HalloweenEvent2009", "true"));

			ALT_FANTASYEVENT = Boolean.parseBoolean(altSettings.getProperty("FantasyEvent", "true"));

			ALT_CHUSEOKEVENT = Boolean.parseBoolean(altSettings.getProperty("ChuSeokEvent", "true"));

			Big_Praseant = Boolean.parseBoolean(altSettings.getProperty("Bigprasenat", "false"));

			�巡�Ｚ���ǹ����̺�Ʈ = Boolean.parseBoolean(altSettings.getProperty("dragonpotion", "false"));

			ALT_FEATURE = Boolean.parseBoolean(altSettings.getProperty("FeatureEvent", "true"));

			ALT_WHO_COMMAND = Boolean.parseBoolean(altSettings.getProperty("WhoCommand", "false"));

			ALT_REVIVAL_POTION = Boolean.parseBoolean(altSettings.getProperty("RevivalPotion", "false"));
			String strWar;
			strWar = altSettings.getProperty("WarTime", "1h");
			if (strWar.indexOf("d") >= 0) {
				ALT_WAR_TIME_UNIT = Calendar.DATE;
				strWar = strWar.replace("d", "");
			} else if (strWar.indexOf("h") >= 0) {
				ALT_WAR_TIME_UNIT = Calendar.HOUR_OF_DAY;
				strWar = strWar.replace("h", "");
			} else if (strWar.indexOf("m") >= 0) {
				ALT_WAR_TIME_UNIT = Calendar.MINUTE;
				strWar = strWar.replace("m", "");
			}
			ALT_WAR_TIME = Integer.parseInt(strWar);
			strWar = altSettings.getProperty("WarInterval", "2d");
			if (strWar.indexOf("d") >= 0) {
				ALT_WAR_INTERVAL_UNIT = Calendar.DATE;
				strWar = strWar.replace("d", "");
			} else if (strWar.indexOf("h") >= 0) {
				ALT_WAR_INTERVAL_UNIT = Calendar.HOUR_OF_DAY;
				strWar = strWar.replace("h", "");
			} else if (strWar.indexOf("m") >= 0) {
				ALT_WAR_INTERVAL_UNIT = Calendar.MINUTE;
				strWar = strWar.replace("m", "");
			}
			ALT_WAR_INTERVAL = Integer.parseInt(strWar);

			SPAWN_HOME_POINT = Boolean.parseBoolean(altSettings.getProperty("SpawnHomePoint", "true"));

			SPAWN_HOME_POINT_COUNT = Integer.parseInt(altSettings.getProperty("SpawnHomePointCount", "2"));

			SPAWN_HOME_POINT_DELAY = Integer.parseInt(altSettings.getProperty("SpawnHomePointDelay", "100"));

			SPAWN_HOME_POINT_RANGE = Integer.parseInt(altSettings.getProperty("SpawnHomePointRange", "8"));

			INIT_BOSS_SPAWN = Boolean.parseBoolean(altSettings.getProperty("InitBossSpawn", "true"));

			ELEMENTAL_STONE_AMOUNT = Integer.parseInt(altSettings.getProperty("ElementalStoneAmount", "300"));

			HOUSE_TAX_INTERVAL = Integer.parseInt(altSettings.getProperty("HouseTaxInterval", "10"));

			MAX_DOLL_COUNT = Integer.parseInt(altSettings.getProperty("MaxDollCount", "1"));

			RETURN_TO_NATURE = Boolean.parseBoolean(altSettings.getProperty("ReturnToNature", "false"));

			MAX_NPC_ITEM = Integer.parseInt(altSettings.getProperty("MaxNpcItem", "8"));

			MAX_PERSONAL_WAREHOUSE_ITEM = Integer.parseInt(altSettings.getProperty("MaxPersonalWarehouseItem", "100"));

			MAX_CLAN_WAREHOUSE_ITEM = Integer.parseInt(altSettings.getProperty("MaxClanWarehouseItem", "200"));

			DELETE_CHARACTER_AFTER_7DAYS = Boolean.parseBoolean(altSettings.getProperty("DeleteCharacterAfter7Days", "True"));

			GMCODE = Integer.parseInt(altSettings.getProperty("GMCODE", "9999"));

			DELETE_DB_DAYS = Integer.parseInt(altSettings.getProperty("DeleteDBDAY", "14"));

			MAX_����_DUNGEON_LEVEL = Byte.parseByte(altSettings.getProperty("MaxEarthDragonDungeon", "99"));

			MAX_�Ŀ���_DUNGEON_LEVEL = Byte.parseByte(altSettings.getProperty("MaxWaterDragonDungeon", "99"));

			�뷹�̵巹�� = Byte.parseByte(altSettings.getProperty("Dragonraid", "80"));

			�߶�ī�����̵巹�� = Byte.parseByte(altSettings.getProperty("DragonraidVala", "85"));

			MIN_���ž_DUNGEON_LEVEL = Byte.parseByte(altSettings.getProperty("MinIvoryTowerDungeon", "99"));

			�ڵ�����_���� = toKor(altSettings.getProperty("Anti_Auto_Quiz", "�̹������������մϴ�������ڵ�üũ���̴Ϻ����Ͻô����ڵ��ƴ����Է����ּ���"));
			// System.out.println("�ڵ����� ���� : "+�ڵ�����_����);
			������üũ���� = toKor(altSettings.getProperty("Phone_Auto_Quiz", "���� �Ǻ������� �ڵ��� ������ ���ּ���."));

			��������Ʈ = toKor(altSettings.getProperty("Phone_Ment", "�����ڵ����� �̿��� 010-1234-5678 �� ��ȭ�ֽø� ������ ���� �帳�ϴ�."));

			�ڵ�����_�� = toKor(altSettings.getProperty("Anti_Auto_Answer", "�ڵ��ƴ�"));
			// System.out.println("�ڵ����� �� : "+�ڵ�����_��);

			Event_Box = Boolean.parseBoolean(altSettings.getProperty("Event_box_ok", "false"));

			ALT_PRIVATE_SHOP_LEVEL = Integer.parseInt(altSettings.getProperty("PrivateShopLevel", "0"));

			EVENT_DRAGON_DIAMOND_CHANCE = Integer.parseInt(altSettings.getProperty("DragonDiamondChance", "0"));
			EVENT_DRAGON_DIAMOND_CHANCE2 = Integer.parseInt(altSettings.getProperty("DragonDiamondChance2", "0"));

			�����_�巹��ũ_���� = Integer.parseInt(altSettings.getProperty("DragonDiamondChance3", "0"));

			�Ƹ�ī_Event = Boolean.parseBoolean(altSettings.getProperty("150409Event", "false"));
			�����_�Ƹ�ī_���� = Integer.parseInt(altSettings.getProperty("150409ARKADropChance", "0"));
			�����_�ҷ���_���� = Integer.parseInt(altSettings.getProperty("HWDropChance", "0"));
			���ø������_���� = Integer.parseInt(altSettings.getProperty("1219EventDropChance", "0"));
			���ø���_Event = Boolean.parseBoolean(altSettings.getProperty("1219Event", "false"));

			�̷�����Ȯ�� = Integer.parseInt(altSettings.getProperty("EraseMagic", "40"));

			����Ȯ�� = Integer.parseInt(altSettings.getProperty("Desperado", "60"));

			���극��ũ = Integer.parseInt(altSettings.getProperty("BoneB", "40"));

			�׸�Ȯ�� = Integer.parseInt(altSettings.getProperty("PowerRip", "40"));

			����Ȯ�� = Integer.parseInt(altSettings.getProperty("Shock_Stun", "60"));

			���Ȯ�� = Integer.parseInt(altSettings.getProperty("avenger", "60"));

			��������Ȯ�� = Integer.parseInt(altSettings.getProperty("force_Stun", "60"));

			���ͳ�ƼȮ�� = Integer.parseInt(altSettings.getProperty("ETERNITY", "60"));

			DEMOLITIONȮ�� = Integer.parseInt(altSettings.getProperty("demolition", "60"));

			���׶�Ȯ�� = Integer.parseInt(altSettings.getProperty("PANTERA", "60"));
			
			�����콺��Ȯ�� = Integer.parseInt(altSettings.getProperty("Shadow_Step", "60"));

			������ƮȮ�� = Integer.parseInt(altSettings.getProperty("JUDGEMENT", "60"));

			����Ȯ�� = Integer.parseInt(altSettings.getProperty("PHANTOM", "60"));

			Ŭ����Ʒ��� = Integer.parseInt(altSettings.getProperty("claudialevel", "70"));

			�����̾� = Integer.parseInt(altSettings.getProperty("�����̾�", "60"));

			���Ȯ�� = Integer.parseInt(altSettings.getProperty("Earth_Bind", "40"));

			�˽���Ȯ�� = Integer.parseInt(altSettings.getProperty("weaponShock_Stun", "5"));

			���ݵ��_���� = Integer.parseInt(altSettings.getProperty("150101EventDropChance", "0"));
			����_Event = Boolean.parseBoolean(altSettings.getProperty("150101Event", "false"));

			��Ƽ�����_���� = Integer.parseInt(altSettings.getProperty("150310EventDropChance", "0"));
			��Ƽ��_Event = Boolean.parseBoolean(altSettings.getProperty("150310Event", "false"));

			��ȭ���ڵ��_���� = Integer.parseInt(altSettings.getProperty("150319EventDropChance", "0"));
			��ȭ����_Event = Boolean.parseBoolean(altSettings.getProperty("150319Event", "false"));

			������_���� = Integer.parseInt(altSettings.getProperty("150216EventDropChance", "0"));
			���_Event = Boolean.parseBoolean(altSettings.getProperty("150216Event", "false"));

			���ۼ���_Event = Boolean.parseBoolean(altSettings.getProperty("150108Event", "false"));
			����2���_���� = Integer.parseInt(altSettings.getProperty("150108EventDropChance", "0"));

			�����ڹ���뷱����ġ = Integer.parseInt(altSettings.getProperty("aWeaponBalance", "0"));
			�����ڰ��ʹ뷱����ġ = Integer.parseInt(altSettings.getProperty("aArmorBalance", "0"));
			warmember = Integer.parseInt(altSettings.getProperty("warmember", "0"));

			���۵��_���� = Integer.parseInt(altSettings.getProperty("1225EventDropChance", "0"));
			����_Event = Boolean.parseBoolean(altSettings.getProperty("1225Event", "false"));
			Dragon_3DAY_Event = Boolean.parseBoolean(altSettings.getProperty("Dragon3DayEvent", "false"));

			Dragon_1DAY_Event = Boolean.parseBoolean(altSettings.getProperty("Dragon1DayEvent", "false"));

			���ĵ��_�׷���_���� = Integer.parseInt(altSettings.getProperty("ChopaChance", "0"));
			Chopa_Event = Boolean.parseBoolean(altSettings.getProperty("ChopaEvent", "false"));

			Dragon_14_12_12_Event = Boolean.parseBoolean(altSettings.getProperty("Dragon141212event", "false"));

			�ҷ���_Event = Boolean.parseBoolean(altSettings.getProperty("HW_Event", "false"));

			EVENT_������������_���� = Integer.parseInt(altSettings.getProperty("GakinEventChance", "0"));

			EVENT_�����ָ��ָӴ�_���� = Integer.parseInt(altSettings.getProperty("RuphyBraveEventChance", "0"));

			EVENT_SEHO_BOOMB_CHANCE = Integer.parseInt(altSettings.getProperty("Event_box_chance", "0"));

			DRAGON_2DAY_EVENT = Boolean.parseBoolean(altSettings.getProperty("Dragon2DayEvent", "false"));

			DANTES_2DAY_EVENT = Boolean.parseBoolean(altSettings.getProperty("Dantes2DayEvent", "false"));

			Orim_EVENT = Boolean.parseBoolean(altSettings.getProperty("OrimEvent", "false"));

			RuphyBrave_3DAY_EVENT = Boolean.parseBoolean(altSettings.getProperty("RuphyBrave3DayEvent", "false"));

			Tam_Ok = Boolean.parseBoolean(altSettings.getProperty("TamOK", "false"));
			Tam_Time = Integer.parseInt(altSettings.getProperty("TamTime", "15"));
			Tam_Count = Integer.parseInt(altSettings.getProperty("TamCount", "600"));

			�������� = Integer.parseInt(altSettings.getProperty("ShopLevel", "5"));
			������� = Integer.parseInt(altSettings.getProperty("DropLevel", "5"));

			D_Reset_Time = Integer.parseInt(altSettings.getProperty("DungeonResetTime", "9"));

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + ALT_SETTINGS_FILE + " File.");
		}

		try {
			Properties botSettings = new Properties();
			InputStream is = new FileInputStream(new File(BOT_SETTINGS_FILE));
			botSettings.load(is);
			is.close();
			Robot_Gaho = Boolean.parseBoolean(botSettings.getProperty("Robotgaho", "false"));
			�κ�����Ȱ1 = Integer.parseInt(botSettings.getProperty("robotbow1", "12314"));
			�κ�����Ȱ2 = Integer.parseInt(botSettings.getProperty("robotbow2", "12314"));
			�κ�����Ȱ3 = Integer.parseInt(botSettings.getProperty("robotbow3", "12314"));
			�κ�����Ȱ4 = Integer.parseInt(botSettings.getProperty("robotbow4", "12314"));
			�κ�����Ȱ5 = Integer.parseInt(botSettings.getProperty("robotbow5", "12314"));
			�κ�Į1 = Integer.parseInt(botSettings.getProperty("robotsowrd1", "12283"));
			�κ�Į2 = Integer.parseInt(botSettings.getProperty("robotsowrd2", "12283"));
			�κ�Į3 = Integer.parseInt(botSettings.getProperty("robotsowrd3", "12283"));
			�κ�Į4 = Integer.parseInt(botSettings.getProperty("robotsowrd4", "12283"));
			�κ�Į5 = Integer.parseInt(botSettings.getProperty("robotsowrd5", "12283"));
			�κ�Į6 = Integer.parseInt(botSettings.getProperty("robotsowrd6", "12283"));
			�κ�Į7 = Integer.parseInt(botSettings.getProperty("robotsowrd7", "12283"));
			�κ�Į8 = Integer.parseInt(botSettings.getProperty("robotsowrd8", "12283"));
			�κ�Į9 = Integer.parseInt(botSettings.getProperty("robotsowrd9", "12283"));
			����Ÿ��Ȱ = Integer.parseInt(botSettings.getProperty("robotdollbowtype", "22"));
			����Ÿ��Į1 = Integer.parseInt(botSettings.getProperty("robotdollswordtype1", "21"));
			����Ÿ��Į2 = Integer.parseInt(botSettings.getProperty("robotdollswordtype2", "26"));
			����Ÿ��Į3 = Integer.parseInt(botSettings.getProperty("robotdollswordtype3", "26"));
			����Ÿ��Į4 = Integer.parseInt(botSettings.getProperty("robotdollswordtype4", "26"));
			����Ÿ��Į5 = Integer.parseInt(botSettings.getProperty("robotdollswordtype5", "26"));
			����Ÿ��Į6 = Integer.parseInt(botSettings.getProperty("robotdollswordtype6", "26"));
			����Ÿ�԰���ġ = Integer.parseInt(botSettings.getProperty("robotdolltypeexp", "26"));
			����Ÿ��Ȱ���Ǿ����̵� = Integer.parseInt(botSettings.getProperty("robotdollbownpcid", "41915"));
			����Ÿ��Į���Ǿ����̵�1 = Integer.parseInt(botSettings.getProperty("robotdollswordnpcid1", "4500161"));
			����Ÿ��Į���Ǿ����̵�2 = Integer.parseInt(botSettings.getProperty("robotdollswordnpcid2", "45000161"));
			����Ÿ��Į���Ǿ����̵�3 = Integer.parseInt(botSettings.getProperty("robotdollswordnpcid3", "4500161"));
			����Ÿ��Į���Ǿ����̵�4 = Integer.parseInt(botSettings.getProperty("robotdollswordnpcid4", "4500161"));
			����Ÿ��Į���Ǿ����̵�5 = Integer.parseInt(botSettings.getProperty("robotdollswordnpcid5", "4500161"));
			����Ÿ��Į���Ǿ����̵�6 = Integer.parseInt(botSettings.getProperty("robotdollswordnpcid6", "4500161"));
			����ġ�������Ǿ����̵� = Integer.parseInt(botSettings.getProperty("robotdollexpnpcid", "4500161"));
			�����۷��� = Integer.parseInt(botSettings.getProperty("botstartLev", "56"));
			��������� = Integer.parseInt(botSettings.getProperty("bottechnichit", "5"));
			���������� = Integer.parseInt(botSettings.getProperty("botspirithit", "0"));
			��������� = Integer.parseInt(botSettings.getProperty("botdragonhit", "0"));
			���������� = Integer.parseInt(botSettings.getProperty("botfearhit", "0"));
			��������� = Integer.parseInt(botSettings.getProperty("bottechnictollance", "30"));
			�����ɳ��� = Integer.parseInt(botSettings.getProperty("botspirittollance", "10"));
			����𳻼� = Integer.parseInt(botSettings.getProperty("botdragontollance", "10"));
			���������� = Integer.parseInt(botSettings.getProperty("botfeartollance", "10"));
			����Ȱ�⺻Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("bowbasepc", "10"));
			����Ȱ����Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("bowrndpc", "10"));
			���⺻Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("knightbasepc", "10"));
			��緣��Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("knightrndpc", "10"));
			����⺻Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("warriorbasepc", "10"));
			���緣��Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("warriorrndpc", "10"));
			�ٿ��⺻Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("darkelfbasepc", "10"));
			�ٿ�����Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("darkelfrndpc", "10"));
			����⺻Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("dragonebasepc", "10"));
			���緣��Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("dragonerndpc", "10"));
			���˿�⺻Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("crownbasepc", "10"));
			���˿䷣��Ÿ��ġPC = Integer.parseInt(botSettings.getProperty("crownrndpc", "10"));
			����Ȱ�⺻Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("bowbasenpc", "10"));
			����Ȱ����Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("bowrndnpc", "10"));
			���⺻Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("knightbasenpc", "10"));
			��緣��Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("knightrndnpc", "10"));
			����⺻Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("warriorbasenpc", "10"));
			���緣��Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("warriorrndnpc", "10"));
			�ٿ��⺻Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("darkelfbasenpc", "10"));
			�ٿ�����Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("darkelfrndnpc", "10"));
			����⺻Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("dragonebasenpc", "10"));
			���緣��Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("dragonerndnpc", "10"));
			���˿�⺻Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("crownbasenpc", "10"));
			���˿䷣��Ÿ��ġNPC = Integer.parseInt(botSettings.getProperty("crownrndnpc", "10"));
			������⺻ȸ���� = Integer.parseInt(botSettings.getProperty("botposionbase", "45"));
			�����෣��ȸ���� = Integer.parseInt(botSettings.getProperty("botposionrnd", "65"));
			����������Ŭ�� = Boolean.parseBoolean(botSettings.getProperty("botelfcyclone", "false"));
			���������� = Boolean.parseBoolean(botSettings.getProperty("botnightmare", "false"));
			��������ǳ = Boolean.parseBoolean(botSettings.getProperty("bottypoon", "false"));
			�����ֿ��� = Boolean.parseBoolean(botSettings.getProperty("botcrownempire", "false"));
			������ = Boolean.parseBoolean(botSettings.getProperty("botgalse", "false"));
			���˹� = Boolean.parseBoolean(botSettings.getProperty("botpolute", "false"));
			�κ����� = Boolean.parseBoolean(botSettings.getProperty("botattckpc", "false"));

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + BOT_SETTINGS_FILE + " File.");
		}

		// charsettings.properties
		try {
			Properties charSettings = new Properties();
			InputStream is = new FileInputStream(new File(CHAR_SETTINGS_CONFIG_FILE));
			charSettings.load(is);
			is.close();

			PRINCE_MAX_HP = Integer.parseInt(charSettings.getProperty("PrinceMaxHP", "1000"));

			PRINCE_MAX_MP = Integer.parseInt(charSettings.getProperty("PrinceMaxMP", "800"));

			KNIGHT_MAX_HP = Integer.parseInt(charSettings.getProperty("KnightMaxHP", "1400"));

			KNIGHT_MAX_MP = Integer.parseInt(charSettings.getProperty("KnightMaxMP", "600"));

			ELF_MAX_HP = Integer.parseInt(charSettings.getProperty("ElfMaxHP", "1000"));

			ELF_MAX_MP = Integer.parseInt(charSettings.getProperty("ElfMaxMP", "900"));

			WIZARD_MAX_HP = Integer.parseInt(charSettings.getProperty("WizardMaxHP", "800"));

			WIZARD_MAX_MP = Integer.parseInt(charSettings.getProperty("WizardMaxMP", "1200"));

			DARKELF_MAX_HP = Integer.parseInt(charSettings.getProperty("DarkelfMaxHP", "1000"));

			DARKELF_MAX_MP = Integer.parseInt(charSettings.getProperty("DarkelfMaxMP", "900"));

			DRAGONKNIGHT_MAX_HP = Integer.parseInt(charSettings.getProperty("DragonknightMaxHP", "1000"));

			DRAGONKNIGHT_MAX_MP = Integer.parseInt(charSettings.getProperty("DragonknightMaxMP", "900"));

			BLACKWIZARD_MAX_HP = Integer.parseInt(charSettings.getProperty("BlackwizardMaxHP", "900"));

			BLACKWIZARD_MAX_MP = Integer.parseInt(charSettings.getProperty("BlackwizardMaxMP", "1100"));

			MAXLEVEL = Integer.parseInt(charSettings.getProperty("LimitLevel", "99"));

			LV50_EXP = Integer.parseInt(charSettings.getProperty("Lv50Exp", "1"));
			LV51_EXP = Integer.parseInt(charSettings.getProperty("Lv51Exp", "1"));
			LV52_EXP = Integer.parseInt(charSettings.getProperty("Lv52Exp", "1"));
			LV53_EXP = Integer.parseInt(charSettings.getProperty("Lv53Exp", "1"));
			LV54_EXP = Integer.parseInt(charSettings.getProperty("Lv54Exp", "1"));
			LV55_EXP = Integer.parseInt(charSettings.getProperty("Lv55Exp", "1"));
			LV56_EXP = Integer.parseInt(charSettings.getProperty("Lv56Exp", "1"));
			LV57_EXP = Integer.parseInt(charSettings.getProperty("Lv57Exp", "1"));
			LV58_EXP = Integer.parseInt(charSettings.getProperty("Lv58Exp", "1"));
			LV59_EXP = Integer.parseInt(charSettings.getProperty("Lv59Exp", "1"));
			LV60_EXP = Integer.parseInt(charSettings.getProperty("Lv60Exp", "1"));
			LV61_EXP = Integer.parseInt(charSettings.getProperty("Lv61Exp", "1"));
			LV62_EXP = Integer.parseInt(charSettings.getProperty("Lv62Exp", "1"));
			LV63_EXP = Integer.parseInt(charSettings.getProperty("Lv63Exp", "1"));
			LV64_EXP = Integer.parseInt(charSettings.getProperty("Lv64Exp", "1"));
			LV65_EXP = Integer.parseInt(charSettings.getProperty("Lv65Exp", "2"));
			LV66_EXP = Integer.parseInt(charSettings.getProperty("Lv66Exp", "2"));
			LV67_EXP = Integer.parseInt(charSettings.getProperty("Lv67Exp", "2"));
			LV68_EXP = Integer.parseInt(charSettings.getProperty("Lv68Exp", "2"));
			LV69_EXP = Integer.parseInt(charSettings.getProperty("Lv69Exp", "2"));
			LV70_EXP = Integer.parseInt(charSettings.getProperty("Lv70Exp", "4"));
			LV71_EXP = Integer.parseInt(charSettings.getProperty("Lv71Exp", "4"));
			LV72_EXP = Integer.parseInt(charSettings.getProperty("Lv72Exp", "4"));
			LV73_EXP = Integer.parseInt(charSettings.getProperty("Lv73Exp", "4"));
			LV74_EXP = Integer.parseInt(charSettings.getProperty("Lv74Exp", "4"));
			LV75_EXP = Integer.parseInt(charSettings.getProperty("Lv75Exp", "8"));
			LV76_EXP = Integer.parseInt(charSettings.getProperty("Lv76Exp", "8"));
			LV77_EXP = Integer.parseInt(charSettings.getProperty("Lv77Exp", "8"));
			LV78_EXP = Integer.parseInt(charSettings.getProperty("Lv78Exp", "8"));
			LV79_EXP = Integer.parseInt(charSettings.getProperty("Lv79Exp", "16"));
			LV80_EXP = Integer.parseInt(charSettings.getProperty("Lv80Exp", "32"));
			LV81_EXP = Integer.parseInt(charSettings.getProperty("Lv81Exp", "64"));
			LV82_EXP = Integer.parseInt(charSettings.getProperty("Lv82Exp", "128"));
			LV83_EXP = Integer.parseInt(charSettings.getProperty("Lv83Exp", "256"));
			LV84_EXP = Integer.parseInt(charSettings.getProperty("Lv84Exp", "512"));
			LV85_EXP = Integer.parseInt(charSettings.getProperty("Lv85Exp", "1024"));
			LV86_EXP = Integer.parseInt(charSettings.getProperty("Lv86Exp", "2048"));
			LV87_EXP = Integer.parseInt(charSettings.getProperty("Lv87Exp", "4096"));
			LV88_EXP = Integer.parseInt(charSettings.getProperty("Lv88Exp", "8192"));
			LV89_EXP = Integer.parseInt(charSettings.getProperty("Lv89Exp", "16384"));
			LV90_EXP = Integer.parseInt(charSettings.getProperty("Lv90Exp", "32768"));
			LV91_EXP = Integer.parseInt(charSettings.getProperty("Lv91Exp", "65536"));
			LV92_EXP = Integer.parseInt(charSettings.getProperty("Lv92Exp", "131072"));
			LV93_EXP = Integer.parseInt(charSettings.getProperty("Lv93Exp", "262144"));
			LV94_EXP = Integer.parseInt(charSettings.getProperty("Lv94Exp", "524288"));
			LV95_EXP = Integer.parseInt(charSettings.getProperty("Lv95Exp", "1048576"));
			LV96_EXP = Integer.parseInt(charSettings.getProperty("Lv96Exp", "2097152"));
			LV97_EXP = Integer.parseInt(charSettings.getProperty("Lv97Exp", "4194304"));
			LV98_EXP = Integer.parseInt(charSettings.getProperty("Lv98Exp", "8388608"));
			LV99_EXP = Integer.parseInt(charSettings.getProperty("Lv99Exp", "16777216"));

			ExpTable._expPenalty[0] = LV50_EXP;
			ExpTable._expPenalty[1] = LV51_EXP;
			ExpTable._expPenalty[2] = LV52_EXP;
			ExpTable._expPenalty[3] = LV53_EXP;
			ExpTable._expPenalty[4] = LV54_EXP;
			ExpTable._expPenalty[5] = LV55_EXP;
			ExpTable._expPenalty[6] = LV56_EXP;
			ExpTable._expPenalty[7] = LV57_EXP;
			ExpTable._expPenalty[8] = LV58_EXP;
			ExpTable._expPenalty[9] = LV59_EXP;
			ExpTable._expPenalty[10] = LV60_EXP;
			ExpTable._expPenalty[11] = LV61_EXP;
			ExpTable._expPenalty[12] = LV62_EXP;
			ExpTable._expPenalty[13] = LV63_EXP;
			ExpTable._expPenalty[14] = LV64_EXP;
			ExpTable._expPenalty[15] = LV65_EXP;
			ExpTable._expPenalty[16] = LV66_EXP;
			ExpTable._expPenalty[17] = LV67_EXP;
			ExpTable._expPenalty[18] = LV68_EXP;
			ExpTable._expPenalty[19] = LV69_EXP;
			ExpTable._expPenalty[20] = LV70_EXP;
			ExpTable._expPenalty[21] = LV71_EXP;
			ExpTable._expPenalty[22] = LV72_EXP;
			ExpTable._expPenalty[23] = LV73_EXP;
			ExpTable._expPenalty[24] = LV74_EXP;
			ExpTable._expPenalty[25] = LV75_EXP;
			ExpTable._expPenalty[26] = LV76_EXP;
			ExpTable._expPenalty[27] = LV77_EXP;
			ExpTable._expPenalty[28] = LV78_EXP;
			ExpTable._expPenalty[29] = LV79_EXP;
			ExpTable._expPenalty[30] = LV80_EXP;
			ExpTable._expPenalty[31] = LV81_EXP;
			ExpTable._expPenalty[32] = LV82_EXP;
			ExpTable._expPenalty[33] = LV83_EXP;
			ExpTable._expPenalty[34] = LV84_EXP;
			ExpTable._expPenalty[35] = LV85_EXP;
			ExpTable._expPenalty[36] = LV86_EXP;
			ExpTable._expPenalty[37] = LV87_EXP;
			ExpTable._expPenalty[38] = LV88_EXP;
			ExpTable._expPenalty[39] = LV89_EXP;
			ExpTable._expPenalty[40] = LV90_EXP;
			ExpTable._expPenalty[41] = LV91_EXP;
			ExpTable._expPenalty[42] = LV92_EXP;
			ExpTable._expPenalty[43] = LV93_EXP;
			ExpTable._expPenalty[44] = LV94_EXP;
			ExpTable._expPenalty[45] = LV95_EXP;
			ExpTable._expPenalty[46] = LV96_EXP;
			ExpTable._expPenalty[47] = LV97_EXP;
			ExpTable._expPenalty[48] = LV98_EXP;
			ExpTable._expPenalty[49] = LV99_EXP;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + CHAR_SETTINGS_CONFIG_FILE + " File.");
		}
		try {
			Properties Eventlink = new Properties();
			InputStream is = new FileInputStream(new File(CHOLONG_SETTINGS_CONFIG_FILE));
			Eventlink.load(is);
			is.close();

			/** �̺�Ʈ���� �������� **/
			/** �̺�Ʈ�� **/
			CRAFT_TABLE_ONE = Integer.parseInt(Eventlink.getProperty("one", "0")); // �屺;
			CRAFT_TABLE_TWO = Integer.parseInt(Eventlink.getProperty("two", "0")); // �屺;;
			CRAFT_TABLE_THREE = Integer.parseInt(Eventlink.getProperty("three", "0")); // �屺;;
			CRAFT_TABLE_FOUR = Integer.parseInt(Eventlink.getProperty("four", "0")); // �屺;;
			CRAFT_TABLE_FIVE = Integer.parseInt(Eventlink.getProperty("five", "0")); // �屺;;
			CRAFT_TABLE_SIX = Integer.parseInt(Eventlink.getProperty("six", "0")); // �屺;;
			CRAFT_TABLE_SEVEN = Integer.parseInt(Eventlink.getProperty("seven", "0")); // �屺;;
			CRAFT_TABLE_EIGHT = Integer.parseInt(Eventlink.getProperty("eight", "0")); // �屺;;
			CRAFT_TABLE_NINE = Integer.parseInt(Eventlink.getProperty("nine", "0")); // �屺;
			CRAFT_TABLE_TEN = Integer.parseInt(Eventlink.getProperty("ten", "0")); // �屺;;
			CRAFT_TABLE = Integer.parseInt(Eventlink.getProperty("zero", "1")); // �屺;;
			����Ȯ�� = Integer.parseInt(Eventlink.getProperty("dollchance", "1"));
			CLAN_BLESS_ALL_USE = Boolean.parseBoolean(Eventlink.getProperty("ClanBlessAllUse", "false"));
		} catch (Exception e) {
			_log.log(Level.SEVERE, "Config.���� ������ �߻��߽��ϴ�.", e);
			throw new Error("Failed to Load " + CHOLONG_SETTINGS_CONFIG_FILE + " File.");
		}
		validate();
		loadWeekQuest();
	}

	public static final String WEEKQUEST_CONFIG_FILE = "./config/weekquest.properties";
	public static int WQ_UPDATE_TYPE;
	public static int WQ_UPDATE_WEEK;
	public static int WQ_UPDATE_TIME;

	public static void loadWeekQuest() {
		try {
			Properties wq = new Properties();
			InputStream is = new FileInputStream(new File(WEEKQUEST_CONFIG_FILE));
			wq.load(is);
			is.close();
			WQ_UPDATE_TYPE = Integer.parseInt(wq.getProperty("WeekQuest_UpdateType", "1"));
			WQ_UPDATE_WEEK = Integer.parseInt(wq.getProperty("WeekQuest_UpdateWeek", "4"));
			WQ_UPDATE_TIME = Integer.parseInt(wq.getProperty("WeekQuest_UpdateTime", "10"));
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + WEEKQUEST_CONFIG_FILE + " File.");
		}

	}

	public static String toKor(String src) {
		String str = null;
		try {
			byte[] b = src.getBytes("8859_1");
			str = new String(b, "MS932");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	private static void validate() {
		if (!IntRange.includes(Config.ALT_ITEM_DELETION_RANGE, 0, 5)) {
			throw new IllegalStateException("ItemDeletionRange�� ���� ���� ���� �������Դϴ�. ");
		}

		if (!IntRange.includes(Config.ALT_ITEM_DELETION_TIME, 1, 35791)) {
			throw new IllegalStateException("ItemDeletionTime�� ���� ���� ���� �������Դϴ�. ");
		}
	}

	public static boolean setParameterValue(String pName, String pValue) {
		// server.properties
		if (pName.equalsIgnoreCase("ServerType")) {
			GAME_SERVER_TYPE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("GameserverHostname")) {
			GAME_SERVER_HOST_NAME = pValue;
		} else if (pName.equalsIgnoreCase("GameserverPort")) {
			GAME_SERVER_PORT = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Driver")) {
			DB_DRIVER = pValue;
		} else if (pName.equalsIgnoreCase("URL")) {
			DB_URL = pValue;
		} else if (pName.equalsIgnoreCase("Login")) {
			DB_LOGIN = pValue;
		} else if (pName.equalsIgnoreCase("Password")) {
			DB_PASSWORD = pValue;
		} else if (pName.equalsIgnoreCase("ClientLanguage")) {
			CLIENT_LANGUAGE = Integer.parseInt(pValue);

		} else if (pName.equalsIgnoreCase("TimeZone")) {
			TIME_ZONE = pValue;
		} else if (pName.equalsIgnoreCase("AutomaticKick")) {
			AUTOMATIC_KICK = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AutoCreateAccounts")) {
			AUTO_CREATE_ACCOUNTS = Boolean.parseBoolean(pValue);
		} else if (pName.equalsIgnoreCase("MaximumOnlineUsers")) {
			MAX_ONLINE_USERS = Short.parseShort(pValue);
		} else if (pName.equalsIgnoreCase("LoggingWeaponEnchant")) {
			LOGGING_WEAPON_ENCHANT = Byte.parseByte(pValue);
		} else if (pName.equalsIgnoreCase("LoggingArmorEnchant")) {
			LOGGING_ARMOR_ENCHANT = Byte.parseByte(pValue);
		} else if (pName.equalsIgnoreCase("LoggingTime")) {
			LOGGING_TIME = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("CharacterConfigInServerSide")) {
			CHARACTER_CONFIG_IN_SERVER_SIDE = Boolean.parseBoolean(pValue);
		} else if (pName.equalsIgnoreCase("Charpass")) {
			ĳ���ͺ����뿩�� = Boolean.parseBoolean(pValue);
		} else if (pName.equalsIgnoreCase("Allow2PC")) {
			ALLOW_2PC = Boolean.parseBoolean(pValue);
		} else if (pName.equalsIgnoreCase("LevelDownRange")) {
			LEVEL_DOWN_RANGE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AccountPassword")) {
			ACCOUNT_PASSWORD = Boolean.parseBoolean(pValue);// �н����� ��ȣȭ ���� �ҽ� 0813 �߰�
		} else if (pName.equalsIgnoreCase("SendPacketBeforeTeleport")) {
			SEND_PACKET_BEFORE_TELEPORT = Boolean.parseBoolean(pValue);
		}
		// rates.properties
		else if (pName.equalsIgnoreCase("RateXp")) {
			RATE_XP = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("RateLawful")) {
			RATE_LAWFUL = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("RateKarma")) {
			RATE_KARMA = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("RateDropAdena")) {
			RATE_DROP_ADENA = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("RateDropItems")) {
			RATE_DROP_ITEMS = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceWeapon")) {
			ENCHANT_CHANCE_WEAPON = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceWeapon7")) {
			ENCHANT_CHANCE_WEAPON7 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceWeapon8")) {
			ENCHANT_CHANCE_WEAPON8 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceWeapon9")) {
			ENCHANT_CHANCE_WEAPON9 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceWeapon10")) {
			ENCHANT_CHANCE_WEAPON10 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("FeatherTime")) {
			FEATHER_TIME = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("FeatherNumber")) {
			FEATHER_NUMBER = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("BattleZone")) {
			��Ʋ���۵����� = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("BattleLevel")) {
			��Ʋ�����巹�� = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ClanNumber")) {
			CLAN_NUMBER = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("CastleNumber")) {
			CASTLE_NUMBER = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceArmor")) {
			ENCHANT_CHANCE_ARMOR = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceAccessory")) {
			ENCHANT_CHANCE_ACCESSORY = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Weightrate")) {
			RATE_WEIGHT_LIMIT = Byte.parseByte(pValue);
		}

		// altsettings.properties
		else if (pName.equalsIgnoreCase("GlobalChatLevel")) {
			GLOBAL_CHAT_LEVEL = Short.parseShort(pValue);
		} else if (pName.equalsIgnoreCase("WhisperChatLevel")) {
			WHISPER_CHAT_LEVEL = Short.parseShort(pValue);
		} else if (pName.equalsIgnoreCase("AutoLoot")) {
			AUTO_LOOT = Byte.parseByte(pValue);
		} else if (pName.equalsIgnoreCase("LOOTING_RANGE")) {
			LOOTING_RANGE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AltNonPvP")) {
			ALT_NONPVP = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("AttackMessageOn")) {
			ALT_ATKMSG = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("ChangeTitleByOneself")) {
			CHANGE_TITLE_BY_ONESELF = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxClanMember")) {
			MAX_CLAN_MEMBER = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ClanAlliance")) {
			CLAN_ALLIANCE = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxPT")) {
			MAX_PT = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("MaxChatPT")) {
			MAX_CHAT_PT = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("SimWarPenalty")) {
			SIM_WAR_PENALTY = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("GetBack")) {
			GET_BACK = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("AutomaticItemDeletionTime")) {
			ALT_ITEM_DELETION_TIME = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AutomaticItemDeletionRange")) {
			ALT_ITEM_DELETION_RANGE = Byte.parseByte(pValue);
		} else if (pName.equalsIgnoreCase("HalloweenEvent")) {
			ALT_HALLOWEENEVENT = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("HalloweenEvent2009")) {
			ALT_HALLOWEENEVENT2009 = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("FantasyEvent")) {
			ALT_FANTASYEVENT = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("ChuSeokEvent")) {
			ALT_CHUSEOKEVENT = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("Robotgaho")) {
			Robot_Gaho = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("HouseTaxInterval")) {
			HOUSE_TAX_INTERVAL = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxDollCount")) {
			MAX_DOLL_COUNT = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("ReturnToNature")) {
			RETURN_TO_NATURE = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxNpcItem")) {
			MAX_NPC_ITEM = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxPersonalWarehouseItem")) {
			MAX_PERSONAL_WAREHOUSE_ITEM = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxClanWarehouseItem")) {
			MAX_CLAN_WAREHOUSE_ITEM = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("DeleteCharacterAfter7Days")) {
			DELETE_CHARACTER_AFTER_7DAYS = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("GMCODE")) {
			GMCODE = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("DeleteDBDAY")) {
			DELETE_DB_DAYS = Integer.valueOf(pValue);
		}

		else if (pName.equalsIgnoreCase("ExpGive")) {
			EXP_GIVE = Integer.parseInt(pValue);
		}
		// charsettings.properties
		else if (pName.equalsIgnoreCase("PrinceMaxHP")) {
			PRINCE_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("LimitLevel")) {
			MAXLEVEL = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("PrinceMaxMP")) {
			PRINCE_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("KnightMaxHP")) {
			KNIGHT_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("KnightMaxMP")) {
			KNIGHT_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ElfMaxHP")) {
			ELF_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ElfMaxMP")) {
			ELF_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("WizardMaxHP")) {
			WIZARD_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("WizardMaxMP")) {
			WIZARD_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("DarkelfMaxHP")) {
			DARKELF_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("DarkelfMaxMP")) {
			DARKELF_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("DragonknightMaxHP")) {
			DRAGONKNIGHT_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("DragonknightMaxMP")) {
			DRAGONKNIGHT_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("BlackwizardMaxHP")) {
			BLACKWIZARD_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("BlackwizardMaxMP")) {
			BLACKWIZARD_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv50Exp")) {
			LV50_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv51Exp")) {
			LV51_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv52Exp")) {
			LV52_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv53Exp")) {
			LV53_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv54Exp")) {
			LV54_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv55Exp")) {
			LV55_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv56Exp")) {
			LV56_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv57Exp")) {
			LV57_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv58Exp")) {
			LV58_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv59Exp")) {
			LV59_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv60Exp")) {
			LV60_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv61Exp")) {
			LV61_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv62Exp")) {
			LV62_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv63Exp")) {
			LV63_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv64Exp")) {
			LV64_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv65Exp")) {
			LV65_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv66Exp")) {
			LV66_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv67Exp")) {
			LV67_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv68Exp")) {
			LV68_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv69Exp")) {
			LV69_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv70Exp")) {
			LV70_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv71Exp")) {
			LV71_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv72Exp")) {
			LV72_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv73Exp")) {
			LV73_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv74Exp")) {
			LV74_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv75Exp")) {
			LV75_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv76Exp")) {
			LV76_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv77Exp")) {
			LV77_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv78Exp")) {
			LV78_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv79Exp")) {
			LV79_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv80Exp")) {
			LV80_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv81Exp")) {
			LV81_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv82Exp")) {
			LV82_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv83Exp")) {
			LV83_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv84Exp")) {
			LV84_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv85Exp")) {
			LV85_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv86Exp")) {
			LV86_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv87Exp")) {
			LV87_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv88Exp")) {
			LV88_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv89Exp")) {
			LV89_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv90Exp")) {
			LV90_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv91Exp")) {
			LV91_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv92Exp")) {
			LV92_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv93Exp")) {
			LV93_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv94Exp")) {
			LV94_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv95Exp")) {
			LV95_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv96Exp")) {
			LV96_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv97Exp")) {
			LV97_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv98Exp")) {
			LV98_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv99Exp")) {
			LV99_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Raidtime")) {
			���̵�ð� = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("SnapperMaxEnchant")) {
			�������ִ���æ = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("RoomteeceMaxEnchant")) {
			��Ƽ���ִ���æ = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("acaccessoryMaxEnchant")) {
			��ű��ִ���æ = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AnoldeTime")) {
			�Ƴ���̺�Ʈ�ð� = Integer.parseInt(pValue);
			// Eventlink set file
		} else if (pName.equalsIgnoreCase("one")) {
			CRAFT_TABLE_ONE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("two")) {
			CRAFT_TABLE_TWO = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("three")) {
			CRAFT_TABLE_THREE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("four")) {
			CRAFT_TABLE_FOUR = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("five")) {
			CRAFT_TABLE_FIVE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("six")) {
			CRAFT_TABLE_SIX = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("seven")) {
			CRAFT_TABLE_SEVEN = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("eight")) {
			CRAFT_TABLE_EIGHT = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("nine")) {
			CRAFT_TABLE_NINE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ten")) {
			CRAFT_TABLE_TEN = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("zero")) {
			CRAFT_TABLE = Integer.parseInt(pValue);
		} else {
			return false;
		}
		return true;
	}

	private Config() {
	}

	public final static int etc_arrow = 0;
	public final static int etc_wand = 1;
	public final static int etc_light = 2;
	public final static int etc_gem = 3;
	public final static int etc_potion = 6;
	public final static int etc_firecracker = 5;
	public final static int etc_food = 7;
	public final static int etc_scroll = 8;
	public final static int etc_questitem = 9;
	public final static int etc_spellbook = 10;
	public final static int etc_other = 12;
	public final static int etc_material = 13;
	public final static int etc_sting = 15;
	public final static int etc_treasurebox = 16;
	public final static int etc_Doll = 17;
	public final static int etc_pet = 18;
	public final static int etc_petitem = 20;

	public static enum LOG {
		chat, error, system, badplayer, enchant, inventory, time
	}

	/*
	 * public static synchronized String YearMonthDate2(){ try{ int �� = Year();
	 * String ��2; if(�� < 10){ ��2 = "0"+��; }else{ ��2 = Integer.toString(��); } int �� =
	 * Month(); String ��2=null; if(�� < 10){ ��2 = "0"+��; }else{ ��2 =
	 * Integer.toString(��); } int �� = Date(); String ��2 = null; if(�� < 10){ ��2 =
	 * "0"+��; }else{ ��2 = Integer.toString(��); } return ��2+��2+��2; }catch (Exception
	 * e){}
	 *
	 * return "000000"; }
	 */
	public static synchronized String YearMonthDate2() {
		try {

			Date day = new Date(System.currentTimeMillis());
			int �� = day.getYear() - 100;
			String ��2;
			if (�� < 10) {
				��2 = "0" + ��;
			} else {
				��2 = Integer.toString(��);
			}
			int �� = (day.getMonth() + 1);
			String ��2 = null;
			if (�� < 10) {
				��2 = "0" + ��;
			} else {
				��2 = Integer.toString(��);
			}
			int �� = day.getDate();
			String ��2 = null;
			if (�� < 10) {
				��2 = "0" + ��;
			} else {
				��2 = Integer.toString(��);
			}
			return ��2 + ��2 + ��2;
		} catch (Exception e) {
		}

		return "000000";
	}

	public static synchronized int Year() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.YEAR) - 2000;
	}

	public static synchronized int Month() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.MONTH) + 1;
	}

	public static synchronized int Date() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.DATE);
	}

	public static synchronized int Hour() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static synchronized int Minute() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.MINUTE);
	}

	public static synchronized String Time() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		int h = cal.get(Calendar.HOUR);
		int m = cal.get(Calendar.MINUTE);
		StringBuffer sb = new StringBuffer();
		if (h < 10) {
			sb.append("0");
		}
		sb.append(h);
		sb.append(":");
		if (m < 10) {
			sb.append("0");
		}
		sb.append(m);
		return sb.toString();
	}
}