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

package l1j.server.server.model.Instance;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;

import l1j.server.GameSystem.CrockSystem;
import l1j.server.MJCTSystem.MJCTObject;
import l1j.server.MJCTSystem.Loader.MJCTLoadManager;
import l1j.server.MJCTSystem.Loader.MJCTSystemLoader;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.ArmorSetTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1EquipmentTimer;
import l1j.server.server.model.L1ItemOwnerTimer;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.templates.L1Armor;
import l1j.server.server.templates.L1ArmorSets;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Pet;
import l1j.server.server.utils.BinaryOutputStream;

//Referenced classes of package l1j.server.server.model:
//L1Object, L1PcInstance

public class L1ItemInstance extends L1Object {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.KOREA);

	/** ��Ű������ **/
	private boolean _isPackage = false;

	public boolean isPackage() {
		return _isPackage;
	}

	public void setPackage(boolean _isPackage) {
		this._isPackage = _isPackage;
	}

	private long _itemdelay3;

	public long getItemdelay3() {
		return _itemdelay3;
	}

	public void setItemdelay3(long itemdelay3) {
		_itemdelay3 = itemdelay3;
	}

	private int _count;
	private int _tempcount;
	private int _class = -1;

	public boolean _isSecond = false;
	private int _itemId;
	private L1Item _item;
	private boolean _isEquipped = false;
	private int _enchantLevel;
	private int _attrenchantLevel;
	private int _stepenchantLevel; // �ܰ谭ȭ�ֹ���
	private boolean _isIdentified = false;
	private int _durability;
	private int _chargeCount;
	private int _remainingTime;
	private Timestamp _lastUsed = null;

	private String _encobjid = null;

	private int bless;
	private int _lastWeight;
	private final LastStatus _lastStatus = new LastStatus();
	private L1PcInstance _pc;
	public boolean _isRunning = false;
	private EnchantTimer _timer;
	private Timestamp _buyTime = null;
	private Timestamp _endTime = null;
	private boolean _demon_bongin = false;
	private int RingSlotNum = 13;
	private int Emblem = 30;
	private int shoulder = 29;
	private int Pendant = 31;
	private int _Key = 0;

	public L1ItemInstance() {
		_count = 1;
		_enchantLevel = 0;
	}

	public L1ItemInstance(L1Item item, int count) {
		this();
		setItem(item);
		setCount(count);
	}

	public L1ItemInstance(L1Item item) {
		this(item, 1);
	}

	public void clickItem(L1Character cha, ClientBasePacket packet) {
	}

	public int getRSN() {
		return RingSlotNum;
	}

	public void setRSN(int num) {
		RingSlotNum = num;
	}

	public int getEmblem() {
		return Emblem;
	}

	public void setEmblem(int num) {
		Emblem = num;
	}

	public int getshoulder() {
		return shoulder;
	}

	public void setshoulder(int num) {
		shoulder = num;
	}
	
	public int getPendant() {
		return Pendant;
	}

	public void setPendant(int num) {
		Pendant = num;
	}

	public String getEncobjid() {
		return _encobjid;
	}

	public void setEncobjid(String s) {
		_encobjid = s;
	}

	public void setEnchantWA(L1PcInstance p) {
		_pc = p;
	}

	public boolean isIdentified() {
		return _isIdentified;
	}

	public void setIdentified(boolean identified) {
		_isIdentified = identified;
	}

	public String getName() {
		return _item.getName();
	}

	
	public int getTempCount() {
		return _tempcount;
	}

	public void setTempCount(int count) {
		_tempcount = count;
	}
	
	public int getCount() {
		return _count;
	}

	public void setCount(int count) {
		_count = count;
	}

	public int getClassType() {
		return _class;
	}

	public void setClassType(int count) {
		_class = count;
	}

	public boolean isEquipped() {
		return _isEquipped;
	}

	public void setEquipped(boolean equipped) {
		_isEquipped = equipped;
	}

	public boolean isDemonBongin() {
		return _demon_bongin;
	}

	public void setDemonBongin(boolean ck) {
		_demon_bongin = ck;
	}

	public L1Item getItem() {
		return _item;
	}

	public void setItem(L1Item item) {
		_item = item;
		_itemId = item.getItemId();
	}

	public int getItemId() {
		return _itemId;
	}

	public void setItemId(int itemId) {
		_itemId = itemId;
	}

	public boolean isStackable() {
		return _item.isStackable();
	}

	@Override
	public void onAction(L1PcInstance player) {
	}

	public int getEnchantLevel() {
		return _enchantLevel;
	}

	public void setEnchantLevel(int enchantLevel) {
		_enchantLevel = enchantLevel;
	}

	public int getAttrEnchantLevel() {
		return _attrenchantLevel;
	}

	public void setAttrEnchantLevel(int attrenchantLevel) {
		_attrenchantLevel = attrenchantLevel;
	}

	public int getStepEnchantLevel() {
		return _stepenchantLevel;
	}

	public void setStepEnchantLevel(int stepenchantLevel) {
		_stepenchantLevel = stepenchantLevel;
	}

	private int temp_gfx = 0;

	public int get_gfxid() {
		return temp_gfx == 0 ? _item.getGfxId() : temp_gfx;
	}

	public void set_tempGfx(int i) {
		temp_gfx = i;
	}

	public int get_tempGfx() {
		return temp_gfx;
	}

	public int get_durability() {
		return _durability;
	}

	public int getChargeCount() {
		return _chargeCount;
	}

	public void setChargeCount(int i) {
		_chargeCount = i;
	}

	public int getRemainingTime() {
		return _remainingTime;
	}

	public void setRemainingTime(int i) {
		_remainingTime = i;
	}

	public void setLastUsed(Timestamp t) {
		_lastUsed = t;
	}

	public Timestamp getLastUsed() {
		return _lastUsed;
	}

	public int getBless() {
		return bless;
	}

	public void setBless(int i) {
		bless = i;
	}

	public int getLastWeight() {
		return _lastWeight;
	}

	public void setLastWeight(int weight) {
		_lastWeight = weight;
	}

	public Timestamp getBuyTime() {
		return _buyTime;
	}

	public void setBuyTime(Timestamp t) {
		_buyTime = t;
	}

	public int getKey() {
		return _Key;
	}

	public void setKey(int t) {
		_Key = t;
	}

	private String _CreaterName;

	public String getCreaterName() {
		return _CreaterName;
	}

	public void setCreaterName(String name) {
		_CreaterName = name;
	}

	public Timestamp getEndTime() {
		return _endTime;
	}

	public void setEndTime(Timestamp t) {
		_endTime = t;
	}

	private int _registlevel;

	public int getRegistLevel() {
		return _registlevel;
	}

	public void setRegistLevel(int level) {
		_registlevel = level;
	}

	/** ��Ƽ�� Ǫ�����Ͱ��� ����ȿ��ǥ�� **//*
								 * private String RoomtisHealingPotion11() { int lvl = getEnchantLevel();
								 * BinaryOutputStream os = new BinaryOutputStream(); switch (lvl) { case 0:
								 * os.writeC(96); os.writeC(2); //ȸ�� ��ȭ ��� 1���� break; case 1: os.writeC(96);
								 * os.writeC(6); //ȸ�� ��ȭ ��� 1���� break; case 2: os.writeC(96); os.writeC(8); //ȸ��
								 * ��ȭ ��� 1���� break; case 3: os.writeC(96); os.writeC(10); //ȸ�� ��ȭ ��� 1���� break;
								 * case 4: os.writeC(96); os.writeC(12); //ȸ�� ��ȭ ��� 1���� break; case 5:
								 * os.writeC(96); os.writeC(14); //ȸ�� ��ȭ ��� 1���� break; case 6: os.writeC(96);
								 * os.writeC(16); //ȸ�� ��ȭ ��� 1���� break; case 7: os.writeC(96); os.writeC(18);
								 * //ȸ�� ��ȭ ��� 1���� break; case 8: os.writeC(96); os.writeC(20); //ȸ�� ��ȭ ��� 1����
								 * break; default: break; } }
								 */

	/** ��Ƽ�� Ǫ�����Ͱ��� ����ȿ��ǥ�� **/
	private String RoomtisHealingPotion12() {
		int lvl = getEnchantLevel();
		String in = "";
		switch (lvl) {
		case 0:
			in = "���� ȸ������ ȿ�� ��� +2%";
			break;
		case 1:
			in = "���� ȸ������ ȿ�� ��� +6%";
			break;
		case 2:
			in = "���� ȸ������ ȿ�� ��� +8%";
			break;
		case 3:
			in = "���� ȸ������ ȿ�� ��� +12%";
			break;
		case 4:
			in = "���� ȸ������ ȿ�� ��� +14%";
			break;
		case 5:
			in = "���� ȸ������ ȿ�� ��� +16%";
			break;
		case 6:
			in = "���� ȸ������ ȿ�� ��� +18%";
			break;
		case 7:
			in = "���� ȸ������ ȿ�� ��� +20%";
			break;
		case 8:
			in = "���� ȸ������ ȿ�� ��� +22%";
			break;
		default:
			break;
		}
		return in;
	}

	/** �ϹݱͰ���,����� ���� ȸ�� ���� ȿ�� ���ǥ�� **/
	private String RoomtisHealingPotion14() {
		int lvl = getEnchantLevel();
		String in = "";
		switch (lvl) {
		case 5:
			in = "���� ȸ������ ȿ�� ��� +2%";
			break;
		case 6:
			in = "���� ȸ������ ȿ�� ��� +4%";
			break;
		case 7:
			in = "���� ȸ������ ȿ�� ��� +6%";
			break;
		case 8:
			in = "���� ȸ������ ȿ�� ��� +8%";
			break;
		case 9:
			in = "���� ȸ������ ȿ�� ��� +9%";
			break;
		default:
			break;
		}
		return in;
	}

	public int getsp() {
		int sp = _item.get_addsp();
		return sp;
	}

	public int getMr() { //����
		int mr = _item.get_mdef();
		int itemid = getItemId();
		if (itemid == 20011 || itemid == 20110 || itemid == 120011 || itemid == 9091
		 || itemid == 21166 || itemid == 490008 || itemid == 490017 || itemid == 120194 
		 || itemid == 1020110|| (itemid >= 21169 && itemid <= 21172)) {
			mr += getEnchantLevel();
		}

		if (itemid == 500214 || itemid == 7247 || itemid == 9083 || itemid == 20117|| itemid == 5000007) { // �����ں� �հ� ����
			mr += getEnchantLevel();
		}
		if (itemid == 900019) {//������ Ƽ���� 6���� ���� 1�� ����
			mr += getEnchantLevel() > 5 ? getEnchantLevel() - 5: 0;
		}
		if (itemid == 20056 || itemid == 120056 || itemid == 9092 || itemid == 220056 || itemid == 425108
				|| itemid == 9084 || itemid == 30014) {
			mr += getEnchantLevel() * 2;
		}
		if (itemid == 20078 || itemid == 20079 || itemid == 120079 || itemid == 21137 || itemid == 20074
				|| itemid == 120074 || itemid == 2020049 || itemid == 20049 || itemid == 20050) {
			mr += getEnchantLevel() * 3;
		}
		/**�Ҵ�Ʈ ����*/
		if (itemid >= 30035 && itemid <= 30037) {
			mr += (getEnchantLevel() >= 2 &&  getEnchantLevel() <= 4) ? (getEnchantLevel() * 2) - 2 : (getEnchantLevel() * 2);
		}
		/**�Ҵ�Ʈ ����*/
		/**�ູ���� �Ҵ�Ʈ ����*/
		if (itemid >= 30039 && itemid <= 30041) {
			mr += (getEnchantLevel() >= 2 &&  getEnchantLevel() <= 4) ? (getEnchantLevel() * 2) - 2 : (getEnchantLevel() * 2);
		}
		if (itemid == 30038) {
			switch (getEnchantLevel()) {
			case 5:
				mr += 11;
				break;
			case 6:
				mr += 13;
				break;
			case 7:
				mr += 15;
				break;
			case 8:
				mr += 17;
				break;
		}
	}
		/**�Ҵ�Ʈ ����*/
		
		if (getItem().getGrade() != 3 && getItem().getType2() == 2
				&& (getItem().getType() == 8 || getItem().getType() == 12) && getEnchantLevel() > 0) {
			switch (getEnchantLevel()) {
			case 5:
				mr += 1;
				break;
			case 6:
				mr += 3;
				break;
			case 7:
				mr += 5;
				break;
			case 8:
				mr += 7;
				break;
			case 9:
				mr += 10;
				break;
			}
		}
		if (itemid == 900020) {
			switch (getEnchantLevel()) {
			case 5:
				mr += getEnchantLevel() - 3;
				break;
			case 6:
				mr += getEnchantLevel() - 3;
				break;
			case 7:
				mr += getEnchantLevel() - 3;
				break;
			case 8:
				mr += getEnchantLevel() - 3;
				break;
			case 9:
				mr += getEnchantLevel() - 3;
				break;
			case 10:
				mr += getEnchantLevel() - 3;
				break;
			}
		}
		if (itemid == 231007 || itemid == 231008 || itemid == 231009) {
			switch (getEnchantLevel()) {
			case 6:
				mr += getEnchantLevel() - 3;
				break;
			case 7:
				mr += getEnchantLevel() - 2;
				break;
			case 8:
				mr += getEnchantLevel() - 1;
				break;
			}
		}
		if (itemid == 220011 || itemid == 1220011)
			mr += getEnchantLevel() * 2;
		if (itemid == 222342 || itemid == 1222342 || itemid == 222344) {
		  if (getEnchantLevel() >= 5) {
			mr += (getEnchantLevel() - 4) * 4;
			
		}
	}
		// �彽���� �����̾� ����  5���� ��þ�� ���� x2 �ö� 
		if (itemid == 5000003 || itemid == 21122) {// ���ְ��� ���� �߰�
		  if (getEnchantLevel() >= 5) {
			mr += (getEnchantLevel() - 4) * 2;
			
		}
	}
		// �ӹ̷ε�հ�   ��þ��5����  x3 �ö� 
		if (itemid == 20017) {
		  if (getEnchantLevel() >= 5) {
			mr += (getEnchantLevel() - 4) * 3;
			
		}
	}
		// **��Ƽ���� ������ �Ͱ��� ����**//
		if (itemid == 500009) {
			// mr += (getEnchantLevel() * 1);
			if (getEnchantLevel() == 7)
				mr += 10;
			else if (getEnchantLevel() == 8)
				mr += 13;
			else if (getEnchantLevel() > 0)
				mr += 2 + getEnchantLevel();
		}
		if (itemid == 502009) {
			if (getEnchantLevel() == 7)
				mr += 13;
			else if (getEnchantLevel() == 8)
				mr += 18;
			else if (getEnchantLevel() > 0)
				mr += 3 + getEnchantLevel();
		}
		if (itemid == 21251) {// �����۸������׹��� �ູ
			if (getEnchantLevel() >= 6) {
				mr += getEnchantLevel() - 5;
			}
		}
		// **��Ƽ���� ������ �Ͱ��� ����**//

		/*
		 * if(getItem().getGrade() == 2 ){//��ű� �߰� mr = mr + getEnchantLevel() * 1; }
		 * if(getItem().getGrade() == 3 ){//��ű� �߰� mr = mr + getEnchantLevel() * 2; }
		 */
		if (mr < 0)
			mr = 0; // << -������� �Ƚ�
		return mr;
	}

	public void set_durability(int i) {
		if (i < 0) {
			i = 0;
		}

		if (i > 127) {
			i = 127;
		}
		_durability = i;
	}

	public int getWeight() {
		if (getItem().getWeight() == 0) {
			return 0;
		} else {
			return Math.max(getCount() * getItem().getWeight() / 1000, 1);
		}
	}

	public class LastStatus {
		public int registLevel;
		public int count;
		public int itemId;
		public boolean isEquipped = false;
		public int enchantLevel;
		public boolean isIdentified = true;
		public int durability;
		public int chargeCount;
		public int remainingTime;
		public Timestamp lastUsed = null;
		public int bless;
		public int attrenchantLevel;
		public int stepenchantLevel;
		public int specialEnchant;
		public Timestamp endTime = null;
		public boolean demon_bongin;

		public void updateAll() {
			count = getCount();
			itemId = getItemId();
			isEquipped = isEquipped();
			isIdentified = isIdentified();
			enchantLevel = getEnchantLevel();
			durability = get_durability();
			chargeCount = getChargeCount();
			remainingTime = getRemainingTime();
			lastUsed = getLastUsed();
			bless = getBless();
			attrenchantLevel = getAttrEnchantLevel();
			stepenchantLevel = getStepEnchantLevel();
			specialEnchant = getSpecialEnchant();
			registLevel = getRegistLevel();
			endTime = getEndTime();
			demon_bongin = isDemonBongin();

		}

		public void updateCount() {
			count = getCount();
		}

		public void updateItemId() {
			itemId = getItemId();
		}

		public void updateEquipped() {
			isEquipped = isEquipped();
		}

		public void updateIdentified() {
			isIdentified = isIdentified();
		}

		public void updateEnchantLevel() {
			enchantLevel = getEnchantLevel();
		}

		public void updateDuraility() {
			durability = get_durability();
		}

		public void updateChargeCount() {
			chargeCount = getChargeCount();
		}

		public void updateRemainingTime() {
			remainingTime = getRemainingTime();
		}

		public void updateLastUsed() {
			lastUsed = getLastUsed();
		}

		public void updateBless() {
			bless = getBless();
		}

		public void updateAttrEnchantLevel() {
			attrenchantLevel = getAttrEnchantLevel();
		}

		public void updateRegistLevel() {
			registLevel = getRegistLevel();
		}

		public void updateEndTIme() {
			endTime = getEndTime();
		}

		public void updateDemonBongin() {
			demon_bongin = isDemonBongin();
		}

		public void updateStepEnchantLevel() {
			stepenchantLevel = getStepEnchantLevel();
		}
	}

	public LastStatus getLastStatus() {
		return _lastStatus;
	}

	public int getRecordingColumns() {
		int column = 0;

		if (getCount() != _lastStatus.count) {
			column += L1PcInventory.COL_COUNT;
		}
		if (getItemId() != _lastStatus.itemId) {
			column += L1PcInventory.COL_ITEMID;
		}
		if (isEquipped() != _lastStatus.isEquipped) {
			column += L1PcInventory.COL_EQUIPPED;
		}
		if (getEnchantLevel() != _lastStatus.enchantLevel) {
			column += L1PcInventory.COL_ENCHANTLVL;
		}
		if (get_durability() != _lastStatus.durability) {
			column += L1PcInventory.COL_DURABILITY;
		}
		if (getChargeCount() != _lastStatus.chargeCount) {
			column += L1PcInventory.COL_CHARGE_COUNT;
		}
		if (getLastUsed() != _lastStatus.lastUsed) {
			column += L1PcInventory.COL_DELAY_EFFECT;
		}
		if (isIdentified() != _lastStatus.isIdentified) {
			column += L1PcInventory.COL_IS_ID;
		}
		if (getRemainingTime() != _lastStatus.remainingTime) {
			column += L1PcInventory.COL_REMAINING_TIME;
		}
		if (getBless() != _lastStatus.bless) {
			column += L1PcInventory.COL_BLESS;
		}
		if (getAttrEnchantLevel() != _lastStatus.attrenchantLevel) {
			column += L1PcInventory.COL_ATTRENCHANTLVL;
		}
		if (getRegistLevel() != _lastStatus.registLevel) {
			column += L1PcInventory.COL_regist;
		}
		if (getEndTime() != _lastStatus.endTime) {
			column += L1PcInventory.COL_ENDTIME;
		}
		if (isDemonBongin() != _lastStatus.demon_bongin) {
			column += L1PcInventory.COL_DEMONBONGIN;
		}
		return column;
	}

	public String getNumberedViewName(int count) {
		return getNumberedViewName(count, false);
	}

	public String getNumberedViewName(int count, boolean privateShop) {
		StringBuilder name = new StringBuilder(getNumberedName(count, privateShop));
		int itemType2 = getItem().getType2();
		int itemId = getItem().getItemId();

		/** �� ����� �Ͻÿ� üũ */
		if (itemId == 40314 || itemId == 40316) {
			L1Pet pet = PetTable.getTemplate(getId());
			if (pet != null) {
				L1Npc Npc = NpcTable.getInstance().getTemplate(pet.getNpcId());
				name.append("("+Npc.get_name()+" Lv " + pet.getLevel() + ")");
			}
		}

		if (getItem().getType2() == 0 && getItem().getType() == 2) { // light
			if (isNowLighting()) {
				name.append(" ($10)");
			}
			if (itemId == 40001 || itemId == 40002 || itemId == 60154) {
				if (getRemainingTime() <= 0) {
					name.append(" ($11)");
				}
			}
		}

		if (getItem().getItemId() == L1ItemId.TEBEOSIRIS_KEY || getItem().getItemId() == L1ItemId.TIKAL_KEY) {
			name.append(" [" + CrockSystem.getInstance().OpenTime() + "]");
		}

		if (getItem().getItemId() == L1ItemId.DRAGON_KEY
				|| (getItemId() >= 60350 && getItemId() <= 60352 || getItemId() == 61000) || getItemId() == 490015) {// �巡��Ű

			name.append(" [" + sdf.format(getEndTime().getTime()) + "]");
		}

		if (getItem().getItemId() == 60285) {// �Ʒü� ����
			if (getEndTime() != null) {
				int mapid = getKey() - 1399;
				String date = " (" + (mapid > 9 ? mapid : "0" + mapid) + ") [" + sdf.format(getEndTime().getTime())
						+ "]";
				date = date.replace("[0", "[");
				name.append(date);
			}
		}

		if (getItem().getItemId() == 40312 || getItem().getItemId() == 49312) {// ��������
			if (getEndTime() != null)
				name.append(" [" + sdf.format(getEndTime().getTime()) + "] CHECK : " + getKey());
		}

		if (getItem().getItemId() == 20344 || getItem().getItemId() == 21092 || itemId == 60061 || // �䳢����,
																									// �丮����,
																									// ��������
				(getItem().getItemId() >= 60009 && getItem().getItemId() <= 60016) || // ��༭
				(itemId >= 425000 && itemId <= 425002) || // ����� ��
				(itemId >= 450000 && itemId <= 450007) || // ����� ����
				itemId == 21094 || // ������ ���� ����
				itemId == 21157 || itemId == 121216 || itemId == 221216 || (itemId >= 60173 && itemId <= 60176)
				|| (itemId >= 21113 && itemId <= 21120) || itemId == 430003 || itemId == 430505 || itemId == 430506
				|| itemId == 41915 || itemId == 5000034// ���� ���� ����
				|| (itemId >= 21125 && itemId <= 21136) || (itemId >= 9075 && itemId <= 9093)
				|| (itemId >= 21139 && itemId <= 21156) || (itemId >= 427113 && itemId <= 427207)
				|| (itemId >= 427120 && itemId <= 427122) || (itemId >= 21158 && itemId <= 21165)
				|| (itemId >= 267 && itemId <= 274) || itemId == 600234 || itemId == 9097 || itemId == 9098
				|| itemId == 450028 // ������
				// ����
				|| itemId == 450029 || itemId == 450030 || itemId == 450031 || itemId == 450032 || itemId == 450033
				|| itemId == 450034 || itemId == 450035 || itemId == 450036 || itemId == 21005 || itemId == 500215
				|| itemId == 21032 || itemId == 110111 || itemId == 500216
				// ���ž�۵�

				/*
				 * || itemId == 7 || itemId == 35 || itemId == 48 || itemId == 73 || itemId ==
				 * 105 || itemId == 120 || itemId == 147 || itemId == 156 || itemId == 174 ||
				 * itemId == 175 || itemId == 224 || itemId == 20028 || itemId == 20082 ||
				 * itemId == 20126 || itemId == 20173 || itemId == 20206 || itemId == 20232 ||
				 * itemId == 20282 || itemId == 201261 || itemId == 21098 || (itemId == 21102 &&
				 * itemId <= 21112) || itemId == 21254
				 */

				|| (itemId >= 21099 && itemId <= 21112) || itemId == 21254 || itemId == 20082 || itemId == 7
				|| itemId == 35 || itemId == 48 || itemId == 73 || itemId == 105 || itemId == 120 || itemId == 147
				|| itemId == 7232 || itemId == 156 || itemId == 174 || itemId == 175 || itemId == 224 || itemId == 9056// �׷���
				|| itemId == 141915 || itemId == 141916 || itemId == 1419161 || itemId == 60319 || itemId == 21271
				|| itemId == 21272 || itemId == 30014 || itemId == 600263 || itemId == 600264 || itemId == 600265
				|| itemId == 600266 || itemId == 421217 || itemId == 421218
				|| itemId == 421219 || itemId == 421216 || itemId == 600369 || itemId == 421220 || itemId == 421221) {
			if (getEndTime() != null) {
				String date = " [" + sdf.format(getEndTime().getTime()) + "]";
				date = date.replace("[0", "[");
				name.append(date);
			}
		}

		if (getItem().getItemId() == 40309 || getItem().getItemId() == 40316) {
			String Name = getCreaterName();
			if(Name != null) name.append(" "+ getRoundId() + "-" + (getTicketId() + 1) + " " + Name);	
		}

		if (isEquipped()) {
			if (itemType2 == 1) {
				name.append(" ($9)");
			} else if (itemType2 == 2 && !getItem().isUseHighPet()) {
				name.append(" ($117)");
			}
		}
		return name.toString();
	}

	public String getViewName() {
		return getNumberedViewName(_count);
	}

	public String getLogName() {
		return getNumberedName(_count);
	}

	public String getNumberedName(int count) {
		return getNumberedName(count, false);
	}

	public String getNumberedName(int count, boolean privateShop) {
		StringBuilder name = new StringBuilder();
		if (getItemId() == 600240) {
			if (getEnchantLevel() >= 0) {
				name.append("+" + getEnchantLevel() + " ");
			} else if (getEnchantLevel() < 0) {
				name.append(String.valueOf(getEnchantLevel()) + " ");
			}
		}
		if (isIdentified()) {
			if (getItem().getType2() == 1 || getItem().getType2() == 2) {
				switch (getAttrEnchantLevel()) {
				case 1:
					name.append("$6115");
					break; // ȭ��1��
				case 2:
					name.append("$6116");
					break; // ȭ��2��
				case 3:
					name.append("$6117");
					break; // ȭ��3��
				case 4:
					name.append("$14361");
					break; // ȭ��4��
				case 5:
					name.append("$14365");
					break; // ȭ��5��

				case 6:
					name.append("$6118");
					break; // ����1��
				case 7:
					name.append("$6119");
					break; // ����2��
				case 8:
					name.append("$6120");
					break; // ����3��
				case 9:
					name.append("$14362");
					break; // ����4��
				case 10:
					name.append("$14366");
					break; // ����5��

				case 11:
					name.append("$6121");
					break; // ǳ��1��
				case 12:
					name.append("$6122");
					break; // ǳ��2��
				case 13:
					name.append("$6123");
					break; // ǳ��3��
				case 14:
					name.append("$14363");
					break; // ǳ��4��
				case 15:
					name.append("$14367");
					break; // ǳ��5��

				case 16:
					name.append("$6124");
					break; // ����1��
				case 17:
					name.append("$6125");
					break; // ����2��
				case 18:
					name.append("$6126");
					break; // ����3��
				case 19:
					name.append("$14364");
					break; // ����4��
				case 20:
					name.append("$14368");
					break; // ����5��
				default:
					break;
				}
				/*switch (getAttrEnchantLevel()) {
				case 1:
					name.append("ȭ��:1�� ");
					break;
				case 2:
					name.append("ȭ��:2�� ");
					break;
				case 3:
					name.append("ȭ��:3�� ");
					break;
				case 4:
					name.append("����:1�� ");
					break;
				case 5:
					name.append("����:2�� ");
					break;
				case 6:
					name.append("����:3�� ");
					break;
				case 7:
					name.append("ǳ��:1�� ");
					break;
				case 8:
					name.append("ǳ��:2�� ");
					break;
				case 9:
					name.append("ǳ��:3�� ");
					break;
				case 10:
					name.append("����:1�� ");
					break;
				case 11:
					name.append("����:2�� ");
					break;
				case 12:
					name.append("����:3�� ");
					break;

				 * case 1: name.append("$6115"); break; case 2: name.append("$6116"); break;
				 * case 3: name.append("$6117"); break; case 4: name.append("$6118"); break;
				 * case 5: name.append("$6119"); break; case 6: name.append("$6120"); break;
				 * case 7: name.append("$6121"); break; case 8: name.append("$6122"); break;
				 * case 9: name.append("$6123"); break; case 10: name.append("$6124"); break;
				 * case 11: name.append("$6125"); break; case 12: name.append("$6126"); break;

				case 33:
					name.append("ȭ��:4�� ");
					break;
				case 34:
					name.append("ȭ��:5�� ");
					break;
				case 35:
					name.append("����:4�� ");
					break;
				case 36:
					name.append("����:5�� ");
					break;
				case 37:
					name.append("ǳ��:4�� ");
					break;
				case 38:
					name.append("ǳ��:5�� ");
					break;
				case 39:
					name.append("����:4�� ");
					break;
				case 40:
					name.append("����:5�� ");
					break;

				case 41:
					name.append("[������ +1]");
					break;
				case 42:
					name.append("[������ +2]");
					break;
				case 43:
					name.append("[������ +3]");
					break;
				case 44:
					name.append("[������ +4]");
					break;
				case 45:
					name.append("[������ +5]");
					break;
				case 46:
					name.append("[������ +6]");
					break;
				case 47:
					name.append("[������ +7]");
					break;
				case 48:
					name.append("[������ +8]");
					break;
				case 49:
					name.append("[������ +9]");
					break;
				case 50:
					name.append("[������ +10]");
					break;
				case 51:
					name.append("[������ +11]");
					break;
				case 52:
					name.append("[������ +12]");
					break;
				case 53:
					name.append("[������ +13]");
					break;
				case 54:
					name.append("[������ +14]");
					break;
				case 55:
					name.append("[������ +15]");
					break;
				case 56:
					name.append("[������ +16]");
					break;
				case 57:
					name.append("[������ +17]");
					break;
				case 58:
					name.append("[������ +18]");
					break;
				case 59:
					name.append("[������ +19]");
					break;
				case 60:
					name.append("[������ +20]");
					break;
				case 61:
					name.append("[������ +21]");
					break;
				case 62:
					name.append("[������ +22]");
					break;
				case 63:
					name.append("[������ +23]");
					break;
				case 64:
					name.append("[������ +24]");
					break;
				case 65:
					name.append("[������ +25]");
					break;
				case 66:
					name.append("[������ +26]");
					break;
				case 67:
					name.append("[������ +27]");
					break;
				case 68:
					name.append("[������ +28]");
					break;
				case 69:
					name.append("[������ +29]");
					break;
				case 70:
					name.append("[������ +30]");
					break;
				default:
					break;*/
				if (getEnchantLevel() >= 0) {
					name.append("+" + getEnchantLevel() + " ");
				} else if (getEnchantLevel() < 0) {
					name.append(String.valueOf(getEnchantLevel()) + " ");
				}
			}
		}

		/** MJCTSystem **/
		if (getItem().getItemId() == MJCTLoadManager.CTSYSTEM_LOAD_ID) {
			MJCTObject obj = MJCTSystemLoader.getInstance().get(getId());
				if (obj == null) {
					name.append(_item.getNameId());
				} else {
					name.append("[").append(obj.name).append("]���� ����");
				}
		} else {
			name.append(_item.getNameId());
		}

		if (isIdentified()) {
			if (getItem().getType2() == 1 || getItem().getType2() == 2) {
				if (getStepEnchantLevel() > 0) {
					name.append(" [" + getStepEnchantLevel() + "��]");
				}
			}
		}

		if (getItem().getItemId() >= 600251 && getItem().getItemId() <= 600254) {
			if (getItem().getMaxChargeCount() > 0)
				name.append(" (" + getChargeCount() + ")");
			/** ����Ʈ �����ۿ� ���� �ƽ� ī���� ���� */
		}else if (getItem().getItemId() == 500016) {
			if (getItem().getMaxChargeCount() > 0) 
				name.append(" (" + getChargeCount() + ")");
		}

		if (isIdentified()) {
			if (getItem().getItemId() < 600251 || getItem().getItemId() > 600254) {
				if (getItem().getMaxChargeCount() > 0) {
					name.append(" (" + getChargeCount() + ")");
				}
			}
			if (getItem().getMaxUseTime() > 0 && getItem().getType2() != 0) {
				name.append(" [" + getRemainingTime() + "]");
			} else if (getItemId() >= 60173 && getItemId() <= 60176 && getRemainingTime() > 0)
				name.append(" [" + getRemainingTime() + "]");
		}

		if (!privateShop && count > 1) {
			name.append(" (" + count + ")");
		}

		if (isIdentified()) {
			if (getItem().getItemId() == 220281) {
				return "$25460";
			}
		}
		return name.toString();
	}
	/** ���� �ѿ��� �ۿ�.. 
	 * ������ ���·κ��� ���� ��Ŷ���� �̿��ϴ� ������ ����Ʈ���� ������, �����ش�. 
	 * 1: ū��/������ ����� , 2: ����, 3: �ջ�, 4: ����, 5: ���� ����, 6: �߰� Ÿ�� , 7: Ŭ���� , 
	 * 8: Str, 9: Dex, 10: Con, 11: Wiz, 12: Int, 13: Cha, 14: �ִ�Hp,Mp 15: Mr, 
	 * 16: �������, 17: �ּ���, 18: ���̽�Ʈȿ��, 19: Ac, 20: ���, 21: ����, 22: ���, 23: ����, 24: ���Ÿ� ����, 25: ����[writeH], 
	 * 26: ����[writeH], 27: �ҼӼ� 28: ���Ӽ�, 29: �ٶ��Ӽ�, 30: ���Ӽ�, 
	 * 31: �ִ�Hp, 32: �ִ�Mp, 33: ����, 34: �������, 35: ���Ÿ� �����, 36: EXP����ġ, 37: ü��ȸ����, 38: ����ȸ����, 39: `,
	 * 40: ��������, 41: ����ġ����, 42: ����, 43: ����, 44: ����, 45:�ǵ����ǹ���,
	 * 46: ����, 47: �ٰŸ� �����, 48: �ٰŸ� ����, 49: ����, 50: ����ġ��Ÿ, 51: ����, 52: ����, 53: ����, 54: ����,
	 * 55: ���̽�Ʈ, 56: �߰�����, 57: ����, 58: ��(�ð�), 59: PVP�����, 60: PVP���������, 61: �ڵ�����,
	 * 62: ��Ŭ����, 63: ���������, 64: Ȯ�������������, 65: ����ȸ����, 66: ����, 67: Ư��, 68: ��������������,
	 * 69: ��Ʈ���ʽ�, 70: ����ȣ��/��, 71: ����, 72:����������, 73: �ߵ�, 74: �ߵ�, 75: ����, 76: �ܰ�, 77: �ܰ�,
	 * 78: �Ӽ�, 79: ��뷹��, 80: ����, 81: ����, 82: ����, 83: ����, 84: ����, 85: ����, 86: ����,
	 * 87: HP����ȸ��, 88: MP����ȸ��, 89: Ȯ������ȸ��, 90: ������������, 91: ��, 82: Ȯ�����߰������, 93: ����,
	 * 94: ����ȿ��, 95: Ȯ�����߰������, 96: ȸ����ȭ���, 97: ����������ǹ���, 98: ����,
	 * 99: ���Ÿ�ġ��Ÿ, 100: �ٰŸ�ġ��Ÿ, 101: ����ܰ躰�����, 102: Ÿ��ź�迭����, 103: Ȯ�����ٰŸ������, 
	 * 104: ����, 105: ����, 106: ����, 107: ����, 108: ����, 109: ����Ӽ������, 110: ����, 111: ����~����,
	 * 112: ���ѽð�, 113: ����, 114: �𵥵�, 115: ����, 116: �ູ�Ҹ���, 
	 * 117: �������, 118: ��������, 119: �������, 120: ��������, 121: ������ü,
	 * 122: ���߱��, 123: ��������, 124: ���߿��, 125: ���߰���, 126: ������ü,
	 * 130: �Ұ�â��, 131: ��ջ�
	 * @param armor
	 */
	
	public byte[] getStatusBytes() {
		int itemType2 = getItem().getType2();
		int itemId = getItemId();
		BinaryOutputStream os = new BinaryOutputStream();
		
		/** ���� ���� Ÿ���̶�� */
		/** ��� ���� ���� ǥ�� �κ� */
		if (getItem().getMinLevel() > 0) {
			os.writeC(111);
			os.writeC(getItem().getMinLevel());
			os.writeH(99);
		}
		
		/** 90�� �ɷ�ġ ǥ�� **/
		if (getItemId() >= 10124 && getItemId() <= 10163 || (getItemId() >= 20647 && getItemId() <= 20651)) { // ���׺���
			os.writeC(135); //���� �Ӽ������ +1
			os.writeC(3);
		}
		
		/** 85�� �ɷ�ġ ǥ�� **/
		if (getItemId() >= 10084 && getItemId() <= 10123 || (getItemId() >= 20642 && getItemId() <= 20646)) { // ���׺���
			os.writeC(135); //���� �Ӽ������ +1
			os.writeC(2);
		}
		
		/** 80�� �ɷ�ġ ǥ�� **/
		if (getItemId() >= 10044 && getItemId() <= 10083 || (getItemId() >= 20637 && getItemId() <= 20641)) { // ���׺���
			os.writeC(135); //���� �Ӽ������ +1
			os.writeC(1);
		}
		
		
		/** ���� �ɷ�ġ ǥ�� **/
		if (itemId == 41248) { // ���׺���
			os.writeC(90);
			os.writeC(500); // ������������+1
		}
		if (itemId == 41249) { // ��ť����
			os.writeC(88);
			os.writeC(15); // mp����ȸ��
		}
		if (itemId == 41250) { // �����ΰ�
			os.writeC(103); // Ȯ���� �ٰŸ������ +1
			os.writeC(15);
		}
		if (itemId == 430000) { // ����
			os.writeC(63); // ����� ����
			os.writeC(1);
		}
		if (itemId == 430001) { // ���
			os.writeC(88);
			os.writeC(15); // mp����ȸ��
		}
		if (itemId == 430002) { // ũ����Ʈ�þ�
			os.writeC(103); // Ȯ���� �ٰŸ������ +1
			os.writeC(15);
		}
		if (itemId == 430004) { // ��Ƽ
			os.writeC(_add_ac); // ��
			os.writeC(-3);
			os.writeC(33); // ����
			os.writeC(1);
			os.writeC(7);

		}
		if (itemId == 430500) { // ��īƮ����
			os.writeC(24); // ���Ÿ� ����
			os.writeC(1);
			os.writeC(35); // ���Ÿ� �����
			os.writeC(1);
		}
		if (itemId == 500202) { // ����Ŭ�ӽ�
			os.writeC(47); // �ٰŸ� �����
			os.writeC(2);
			os.writeC(5); // �ٰŸ� ����
			os.writeC(2);
			os.writeC(117); // ���ϳ���
			os.writeC(12);
		}
		if (itemId == 600315) { // �����Ŭ�ӽ�
			os.writeC(47); // �ٰŸ� �����
			os.writeC(2);
			os.writeC(5); // �ٰŸ� ����
			os.writeC(2);
			os.writeC(117); // ���ϳ���
			os.writeC(12);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
		}
		if (itemId == 500203) { // ���̾�Ʈ
			os.writeC(36);// ����ġ1����
			os.writeC(10);
			os.writeC(63); // ����� ����
			os.writeC(1);
		}
		if (itemId == 600309) { // �����̾�Ʈ
			os.writeC(36);// ����ġ1����
			os.writeC(10);
			os.writeC(63); // ����� ����
			os.writeC(1);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
		}
		if (itemId == 500204) { // �����
			os.writeC(88);
			os.writeC(15); // mp����ȸ��
			os.writeC(73); // �ߵ�
			os.writeS("�� ����Ʈ��");
		}
		if (itemId == 600308) { // �������
			os.writeC(88);
			os.writeC(15); // mp����ȸ��
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(73); // �ߵ�
			os.writeS("�� ����Ʈ��");
		}
		if (itemId == 500205) { // ��ť���� ��
			os.writeC(17); // ����
			os.writeC(1);
			os.writeC(88);
			os.writeC(15); // mp����ȸ��
		}
		if (itemId == 600310) { // �༭ť���� ��
			os.writeC(17); // ����
			os.writeC(1);
			os.writeC(88);
			os.writeC(15); // mp����ȸ��
			os.writeC(_add_ac); // ��
			os.writeC(+2);
		}
		
		if (itemId == 60324) { // �巹��ũ
			os.writeC(35); // ���Ÿ� �����
			os.writeC(2);
			os.writeC(88);
			os.writeC(6); // mp����ȸ��

		}
		
		if (itemId == 600311) { // ��巹��ũ
			os.writeC(35); // ���Ÿ� �����
			os.writeC(2);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(88);
			os.writeC(6); // mp����ȸ��

		}
		
		if (itemId == 5000035) { // ��ġ
			os.writeC(17); // ����
			os.writeC(2);
			os.writeC(14); // HP
			os.writeH(80);
		}
		if (itemId == 600314) { // �ฮġ
			os.writeC(17); // ����
			os.writeC(2);
			os.writeC(14); // HP
			os.writeH(80);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
		}
		if (itemId == 500110) { // ŷ���׺���
			os.writeC(88);
			os.writeC(10); // mp����ȸ��
			os.writeC(117);
			os.writeC(8);
		}
		if (itemId == 600312) { // ��ŷ���׺���
			os.writeC(88);
			os.writeC(10); // mp����ȸ��
			os.writeC(117);
			os.writeC(8);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
		}
		if (itemId == 600241) { // ��
			os.writeC(14); // HP
			os.writeH(50);
		}
		if (itemId == 600242) { // ��� ��
			os.writeC(47); // �ٰŸ� �����
			os.writeC(1);
			os.writeC(63); // ����� ����
			os.writeC(1);
		}
		if (itemId == 600243) { // ���̾Ƹ�� ��
			os.writeC(63); // ����� ����
			os.writeC(2);
		}
		if (itemId == 600313) { // ����̾Ƹ�� ��
			os.writeC(63); // ����� ����
			os.writeC(2);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
		}
		if (itemId == 600244) { // �þ�
			os.writeC(35); // ���Ÿ� �����
			os.writeC(5);
			os.writeC(88);
			os.writeC(30); // mp����ȸ��

		}
		if (itemId == 600317) { // ��þ�
			os.writeC(35); // ���Ÿ� �����
			os.writeC(5);
			os.writeC(88);
			os.writeC(30); // mp����ȸ��
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������

		}
		if (itemId == 600245) { // ����Ʈ�ߵ�
			os.writeC(47); // �ٰŸ� �����
			os.writeC(2);
			os.writeC(5); // �ٰŸ� ����
			os.writeC(2);
			os.writeC(122); // ��� ����1
			os.writeC(5);
		}
		if (itemId == 600316) { // �೪��Ʈ�ߵ�
			os.writeC(47); // �ٰŸ� �����
			os.writeC(2);
			os.writeC(5); // �ٰŸ� ����
			os.writeC(2);
			os.writeC(122); // ��� ����1
			os.writeC(5);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
		}
		if (itemId == 600246) { // ����
			os.writeC(122); // ��� ����1
			os.writeC(10);
			os.writeC(117);
			os.writeC(12);
		}
		if (itemId == 600321) { // �൥��
			os.writeC(122); // ��� ����1
			os.writeC(10);
			os.writeC(117);
			os.writeC(12);
			os.writeC(_add_ac); // ��
			os.writeC(+3);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
			os.writeC(60);
			os.writeC(4); // pvp����� ������
		}
		if (itemId == 600247) { // ��������Ʈ
			os.writeC(63); // ����� ����
			os.writeC(5);
			os.writeC(36);// ����ġ1����
			os.writeC(20);
			os.writeC(73); // �ߵ�
			os.writeS("�����̾�");
			os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
			os.writeH(10);
		}
		if (itemId == 5000045) { // ���������
			os.writeC(63); // ����� ����
			os.writeC(5);
			os.writeC(36);// ����ġ1����
			os.writeC(25);
			os.writeC(73); // �ߵ�
			os.writeS("���̾� ��");
			os.writeC(88);
			os.writeC(10); // mp����ȸ��
			os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
			os.writeH(8);
		}
		if (itemId == 600322) { // �൥������Ʈ
			os.writeC(63); // ����� ����
			os.writeC(5);
			os.writeC(36);// ����ġ1����
			os.writeC(20);
			os.writeC(73); // �ߵ�
			os.writeS("�����̾�");
			os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
			os.writeH(10);
			os.writeC(_add_ac); // ��
			os.writeC(+3);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
			os.writeC(60);
			os.writeC(4); // pvp����� ������
		}
		if (itemId == 142921) { // �����̾�
			os.writeC(47); // �ٰŸ� �����
			os.writeC(2);
			os.writeC(5); // �ٰŸ� ����
			os.writeC(2);
			os.writeC(117);
			os.writeC(5);
			os.writeC(125); // ��������1
			os.writeC(3);
			/*os.writeC(102); // Ÿ��ź �迭 ���� +1%
			os.writeC(5);*/
		}
		if (itemId == 600319) { // ������̾�
			os.writeC(47); // �ٰŸ� �����
			os.writeC(2);
			os.writeC(5); // �ٰŸ� ����
			os.writeC(2);
			os.writeC(117);
			os.writeC(5);
			os.writeC(125); // ��������1
			os.writeC(3);
			/*os.writeC(102); // Ÿ��ź �迭 ���� +1%
			os.writeC(5);*/
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
		}
		if (itemId == 142920) { // ���̸���
			os.writeC(63); // ����� ����
			os.writeC(3);
			os.writeC(101); // ���콽���̾� ������+1
			os.writeC(10);
		}
		if (itemId == 600318) { // ����̸���
			os.writeC(63); // ����� ����
			os.writeC(3);
			os.writeC(101); // ���콽���̾� ������+1
			os.writeC(10);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
		}
		if (itemId == 600259) { //��Ÿ��
			os.writeC(63); // ����� ����
			os.writeC(6);
			os.writeC(88);
			os.writeC(15); // mp����ȸ��
			os.writeC(36);// ����ġ1����
			os.writeC(25);
			os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
			os.writeH(10);
			os.writeC(59);
			os.writeC(4); // pvp�߰� ������
			os.writeC(60);
			os.writeC(2); // pvp����� ������
			os.writeC(_add_ac); // ��
			os.writeC(+3);
		}
		if (itemId == 600260) { // ��Ǫ����
			os.writeC(121); // ��系�� 1
			os.writeC(8);
			os.writeC(126); // �������1
			os.writeC(3);
			os.writeC(40);
			os.writeC(8); // ����1
			os.writeC(17); // sp
			os.writeC(4);
			os.writeC(88);
			os.writeC(5); // mp����ȸ��
			os.writeC(59);
			os.writeC(4); // pvp�߰� ������
			os.writeC(60);
			os.writeC(2); // pvp����� ������
			os.writeC(_add_ac); // ��
			os.writeC(+3);
		}
		if (itemId == 600261) { // ��������
			os.writeC(121); // ��系�� 1
			os.writeC(8);
			os.writeC(126); // �������1
			os.writeC(3);
			os.writeC(24);
			os.writeC(8);
			os.writeC(35); // ���Ÿ� �����
			os.writeC(4);
			os.writeC(88);
			os.writeC(5); // mp����ȸ��
			os.writeC(59);
			os.writeC(4); // pvp�߰� ������
			os.writeC(60);
			os.writeC(2); // pvp����� ������
			os.writeC(_add_ac); // ��
			os.writeC(+3);
		}
		if (itemId == 600262) { // �߶�ī��
			os.writeC(121); // ��系�� 1
			os.writeC(8);
			os.writeC(126); // �������1
			os.writeC(3);
			os.writeC(5); // �ٰŸ� ����
			os.writeC(8);
			os.writeC(47); // �ٰŸ� �����
			os.writeC(4);
			os.writeC(88);
			os.writeC(5); // mp����ȸ��
			os.writeC(59);
			os.writeC(4); // pvp�߰� ������
			os.writeC(60);
			os.writeC(2); // pvp����� ������
			os.writeC(_add_ac); // ��
			os.writeC(+3);
		}
		if (itemId == 142922) { // �ٶ�ī
			os.writeC(123); // ���� ����1
			os.writeC(10);
			os.writeC(117);
			os.writeC(12);
		}
		if (itemId == 600323) { // ��ٶ�ī
			os.writeC(123); // ���� ����1
			os.writeC(10);
			os.writeC(117);
			os.writeC(12);
			os.writeC(_add_ac); // ��
			os.writeC(+3);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
			os.writeC(60);
			os.writeC(4); // pvp����� ������
		}
		if (itemId == 751) { // �ӹ̷ε�
			os.writeC(36);// ����ġ1����
			os.writeC(10);
			os.writeC(63); // ����� ����
			os.writeC(2);
			os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
			os.writeH(8);
		}
		if (itemId == 600320) { // ��ӹ̷ε�
			os.writeC(36);// ����ġ1����
			os.writeC(10);
			os.writeC(63); // ����� ����
			os.writeC(2);
			os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
			os.writeH(8);
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
		}
		if (itemId == 752) { // Ÿ��
			os.writeC(17); // ����
			os.writeC(3);
			os.writeC(117);
			os.writeC(10);
			os.writeC(40);
			os.writeC(5);
			os.writeC(124); // ��� ����1
			os.writeC(5);
		}
		if (itemId == 600324) { // ��Ÿ��
			os.writeC(17); // ����
			os.writeC(3);
			os.writeC(117);
			os.writeC(10);
			os.writeC(40);
			os.writeC(5);
			os.writeC(124); // ��� ����1
			os.writeC(5);
			os.writeC(_add_ac); // ��
			os.writeC(+3);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
			os.writeC(60);
			os.writeC(4); // pvp����� ������
		}
		if (itemId == 753) { // ������Ʈ
			os.writeC(117);
			os.writeC(10);
			os.writeC(125); // ��������1
			os.writeC(5);
		}
		if (itemId == 600325) { // �������Ʈ
			os.writeC(117);
			os.writeC(10);
			os.writeC(125); // ��������1
			os.writeC(5);
			os.writeC(_add_ac); // ��
			os.writeC(+3);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
			os.writeC(60);
			os.writeC(4); // pvp����� ������
		}
		if (itemId == 754) { // ��������
			os.writeC(24); // ���Ÿ� ����
			os.writeC(5);
			os.writeC(35); // ���Ÿ� �����
			os.writeC(5);
			os.writeC(117);
			os.writeC(10);
			os.writeC(123); // ���� ����1
			os.writeC(7);
		}
		if (itemId == 600326) { // ���������
			os.writeC(24); // ���Ÿ� ����
			os.writeC(5);
			os.writeC(35); // ���Ÿ� �����
			os.writeC(5);
			os.writeC(117);
			os.writeC(10);
			os.writeC(123); // ���� ����1
			os.writeC(7);
			os.writeC(_add_ac); // ��
			os.writeC(+3);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
			os.writeC(60);
			os.writeC(4); // pvp����� ������
		}
		
		if (itemId == 755) { // Ŀ��
			os.writeC(_add_ac); // ��
			os.writeC(+2);
			os.writeC(63); // ����� ����
			os.writeC(3);
			os.writeC(117);
			os.writeC(10);
			os.writeC(101); // ���콽���̾� ������+1
			os.writeC(10);
			os.writeC(124); // ��� ����1
			os.writeC(5);
		}
		if (itemId == 600327) { // ��Ŀ��
			os.writeC(_add_ac); // ��
			os.writeC(+5);
			os.writeC(63); // ����� ����
			os.writeC(3);
			os.writeC(117);
			os.writeC(10);
			os.writeC(101); // ���콽���̾� ������+1
			os.writeC(10);
			os.writeC(124); // ��� ����1
			os.writeC(5);
			os.writeC(59);
			os.writeC(2); // pvp�߰� ������
		}
		if (itemId == 42015) { //��� �Ұ�
			os.writeC(39);
			os.writeS("\\fG�Ƶ���: \\aA+50%");
		}
		if (itemId == 43224) { //��������Ʈ
			os.writeC(39);
			os.writeS("\\fG��������Ʈ: \\aA+100");
		}
		if (itemId == 30112) { 
			os.writeC(73); // �ߵ�
			os.writeS("�����ϻ�� �ϰ�");
		}
		if (itemId == 30111) { 
			os.writeC(73); // �ߵ�
			os.writeS("�׶�ī�� ����");
		}
		if (itemType2 == 0) { // etcitem
			switch (getItem().getType()) {
			case 2: // light
				os.writeC(22);
				os.writeH(getItem().getLightRange());
				break;
			case 7: // food
				os.writeC(21);
				os.writeH(getItem().getFoodVolume());
				break;
			case 0: // arrow
				if (itemId == 40744) {
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 3);
				} else if (itemId == 40743) {
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
				} else if (itemId == 40745) {
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 3);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 3);
					os.writeC(109); // ���� �Ӽ������ +1
					os.writeC(3);
				}
			case 15: // sting
				os.writeC(1);
				os.writeC(getItem().getDmgSmall());
				os.writeC(getItem().getDmgLarge());
				break;
			default:
				os.writeC(23);
				break;
			}

			os.writeC(getItem().getMaterial());
			os.writeD(getWeight());

			/*
			 * 0e 17 05 07 00 00 00 4d 03 07 0c 4b 01 4c 19 //���� �ܰ�4 ���Ǯ
			 * 
			 * 0e 17 05 07 00 00 00 4d 03 07 0c 4b 00 4c 1f //�޵� �ܰ�4 ��Ʈ�� 0e 17 05 07 00 00
			 * 00 4d 04 07 0c 4b 01 4c 22 //���� �ܰ�5 ���Ǯ 0e 17 05 07 00 00 00 4d 04 07 0c 4b
			 * 01 4c 24//������ �ܰ�5 ���Ǯ 0e 17 05 07 00 00 00 4d 05 07 0c 4b 00 4c 29//�� �ܰ�6 ��Ʈ��
			 * 0e 17 05 07 00 00 00 4d 05 07 0c 4b 00 4c 2a//���� �ܰ�6 ��Ʈ�� 0e 17 05 07 00 00 00
			 * 4d 06 07 08 4b 01 4c 30//���� �ܰ�7 ���Ǯ 0c 17 05 07 00 00 00 4f 3c 07 01 4c
			 * 78//�극�̺�ƹ�Ÿ ����60
			 */
			/*
			 * if ((itemId >= 90085 && itemId <= 90092) || itemId == 160423 || itemId ==
			 * 435000 || itemId == 160510 || itemId == 160511 || itemId == 21123) {
			 * os.writeC(61); os.writeD(3442346400L); } if (itemId == 21269 &&
			 * getEnchantLevel() < 6) { os.writeC(61); os.writeD(3442346400L); }
			 * 
			 * if (itemId == 500206 || itemId == 500207 || itemId == 121216 || itemId ==
			 * 221216 || itemId == 500208) { os.writeC(61); os.writeD(3501426400L); }
			 */

			if (getItem().getType() == 10) {
				if ((getItem().getItemId() >= 40170 && getItem().getItemId() <= 40225)
						|| (getItem().getItemId() >= 45000 && getItem().getItemId() <= 45022)
						|| getItem().getItemId() == 140186 || getItem().getItemId() == 140196
						|| getItem().getItemId() == 140198 || getItem().getItemId() == 140204
						|| getItem().getItemId() == 140205 || getItem().getItemId() == 140210
						|| getItem().getItemId() == 140219) {
					if (getItem().getskilllv() != 0) {// ���� �ܰ�
						os.writeC(77);// 4D
						os.writeC(getItem().getskilllv() - 1);
					}
					if (getItem().getskilllv() >= 4 && getItem().getskilllv() <= 6) {
						os.writeC(7);
						os.writeD(12);
					} else {
						os.writeC(7);
						os.writeD(8);
					}

				} else {
					if ((getItem().getItemId() >= 40232 && getItem().getItemId() <= 40264)
							|| (getItem().getItemId() >= 41149 && getItem().getItemId() <= 41153)
							|| (getItem().getItemId() >= 60530 && getItem().getItemId() <= 60532)) {
						if (getItem().getskilllv() != 0) {// ���� �ܰ�
							os.writeC(77);// 4D
							os.writeC(getItem().getskilllv() - 1);
						}
						os.writeC(7);
						os.writeD(20);

					} else {
						if (getItem().getskilllv() != 0) {// ��뷹��
							os.writeC(79);
							os.writeC(getItem().getskilllv());
						}
					}

					if ((getItem().getItemId() >= 40226 && getItem().getItemId() <= 40231)
							|| getItem().getItemId() == 60348) {// ����
						os.writeC(7);
						os.writeD(0x01);
					}

					if ((getItem().getItemId() >= 40164 && getItem().getItemId() <= 40166)
							|| getItem().getItemId() == 41147 || getItem().getItemId() == 41148) {// ���
						os.writeC(7);
						os.writeD(0x02);
					}

					if ((getItem().getItemId() >= 40265 && getItem().getItemId() <= 40279)
							|| getItem().getItemId() == 60199) {// �ٿ�
						os.writeC(7);
						os.writeD(16);
					}
					if ((getItem().getItemId() >= 439100 && getItem().getItemId() <= 439114)) {// ����
						os.writeC(7);
						os.writeD(32);
					}
					if ((getItem().getItemId() >= 439000 && getItem().getItemId() <= 439019)) {// ȯ��
						os.writeC(7);
						os.writeD(64);
					}
					if ((getItem().getItemId() >= 7300 && getItem().getItemId() <= 7311)) {// ����
						os.writeC(7);
						os.writeD(128);
					}

					/*
					 * int bit = 0; bit |= getItem().isUseRoyal() ? 1 : 0; bit |=
					 * getItem().isUseKnight() ? 2 : 0; bit |= getItem().isUseElf() ? 4 : 0; bit |=
					 * getItem().isUseMage() ? 8 : 0;
					 * 
					 * 
					 * bit |= getItem().isUseDarkelf() ? 16 : 0; bit |=
					 * getItem().isUseDragonKnight() ? 32 : 0; bit |= getItem().isUseBlackwizard() ?
					 * 64 : 0; bit |= getItem().isUseWarrior() ? 128 : 0; //bit |=
					 * getItem().isUseHighPet() ? 128 : 0; if(itemType2 != 2 || getItem().getType()
					 * != 12 || bit != 127){ os.writeC(7); os.writeC(bit); }
					 */
				}

				if ((getItem().getItemId() >= 40232 && getItem().getItemId() <= 40264)
						|| (getItem().getItemId() >= 41149 && getItem().getItemId() <= 41153)
						|| (getItem().getItemId() >= 60530 && getItem().getItemId() <= 60532)) {
					if (getItem().getskillattr() != 0) {// �Ӽ�
						os.writeC(78);// 4b
						os.writeC(getItem().getskillattr() - 1);
					}
				} else {
					if (getItem().getskillattr() != 0) {// �Ӽ�
						os.writeC(75);// 4b
						os.writeC(getItem().getskillattr() - 1);
					}
				}

				if (getItem().getskillnum() != 0) {// ��ų��ȣ
					os.writeC(76);// 4c
					os.writeC(getItem().getskillnum() - 1);
				}

			}

//			if (itemId == 60354) { // �ÿ��� ���� ����
//				os.writeC(161); //���� �Ӽ������ +1
//				os.writeC(1);
//			} else if (itemId == 60355) { // �ÿ��� ���� ����
//				os.writeC(162); //���� �Ӽ������ +1
//				os.writeC(1);
//			} else if (itemId == 60356) { // �ÿ��� ���� ����
//				os.writeC(163); //���� �Ӽ������ +1
//				os.writeC(1);
//			} else if (itemId == 60357) { // �ÿ��� ���� ����
//				os.writeC(164); //���� �Ӽ������ +1
//				os.writeC(1);
//			} else if (itemId == 60358) { // �ÿ��� ���� ����
//				os.writeC(165); //���� �Ӽ������ +1 ������ �ƴϿ��� ��
//				os.writeC(1);
//			} else if (itemId == 60359) { // �ÿ��� ���� ����
//				os.writeC(166);
//				os.writeC(1);
//			} else if (itemId == 60360) { // �ÿ��� ���� ����
//				os.writeC(167);
//				os.writeC(1);
//			} else if (itemId == 60361) { // �ÿ��� ���� ����
//				os.writeC(168);
//				os.writeC(1);
//			} else if (itemId == 60362) { // �ÿ��� ���� ����
//				os.writeC(169);
//				os.writeC(1);
//			} else if (itemId == 60363) { // �ÿ��� ���� ����
//				os.writeC(170);
//				os.writeC(1);
//			} else if (itemId == 60364) { // �ÿ��� ���� ����
//				os.writeC(171);
//				os.writeC(1);
//			} else if (itemId == 60365) { // �ÿ��� ���� ����
//				os.writeC(172);
//				os.writeC(1);
//			} else if (itemId == 60366) { // �ÿ��� ���� ����
//				os.writeC(173);
//				os.writeC(1);
//			} else if (itemId == 60367) { // �ÿ��� ���� ����
//				os.writeC(174);
//				os.writeC(1);
//			} else if (itemId == 60368) { // �ÿ��� ���� ����
//				os.writeC(175);
//				os.writeC(1);
//			} else if (itemId == 60369) { // �ÿ��� ���� ����
//				os.writeC(176);
//				os.writeC(1);
//			} else if (itemId == 60370) { // �ÿ��� ���� ����
//				os.writeC(177);
//				os.writeC(1);
//			} else if (itemId == 60371) { // �ÿ��� ���� ����
//				os.writeC(178);
//				os.writeC(1);
//			} else if (itemId == 60372) { // �ÿ��� ���� ����
//				os.writeC(179);
//				os.writeC(1);
//			} else if (itemId == 60373) { // �ÿ��� ���� ����
//				os.writeC(180);
//				os.writeC(1);
			if (itemId == 60080 || itemId == 60082 || itemId == 60084) {
				if (getCreaterName() != null) {
					os.writeC(39);
					os.writeS("������:" + getCreaterName());
				}
			}
		} else if (itemType2 == 1 || itemType2 == 2) { // weapon | armor
			/** ������ ������æ ǥ�� �߰� **/
			/*
			 * int SafeEnchant = getItem().get_safeenchant(); os.writeC(39); if (SafeEnchant
			 * < 0) { SafeEnchant = 0; } os.writeS("[������æ : +" + SafeEnchant + "]");
			 */

			if (itemType2 == 1) {
				if (getItem().getBless() == 0) {// �ູ��Ÿ
					os.writeC(39);
					os.writeS("\\f2(�ູ)����� +1");
				}
			}
			if (itemType2 == 1) { // weapon
				os.writeC(1);
				os.writeC(getItem().getDmgSmall());
				os.writeC(getItem().getDmgLarge());
				os.writeC(getItem().getMaterial());
				os.writeD(getWeight());

			} else if (itemType2 == 2) { // armor

				// AC
				os.writeC(19);
				int ac = ((L1Armor) getItem()).get_ac();
				if (getRegistLevel() == 14)// �ǵ��� ��ö����
					ac -= 1;
				if (ac < 0) {
					ac = ac - ac - ac;
				} else {
					ac = ac - ac - ac;
				}
				os.writeC(ac);
				os.writeC(getItem().getMaterial());
				if (getItem().getType() == 8 || getItem().getType() == 12) {
					os.writeC(0x43);
					os.writeC(0x2b);// �ټ�
				} else if (getItem().getType() == 9 || getItem().getType() == 11) {
					os.writeC(0x43);
					os.writeC(0x2c);// ����
				} else if (getItem().getType() == 10) {
					os.writeC(0x43);
					os.writeC(0x2d);// ����
				} else {
					os.writeH(-1);
				}
				/*
				 * if(itemType2 != 2 || getItem().getType() != 12)
				 * os.writeC(getItem().getGrade());// else
				 */
				os.writeD(getWeight());
			}
			if (getItemId() == 500010 || getItemId() == 502010) {
				int ac = getEnchantLevel();

				if (getBless() == 0 && getEnchantLevel() >= 3) {
					ac += 1;
				}

				if (ac > 0) {
					os.writeC(2);
					os.writeC(ac);
				}
			} else if (getItemId() == 500007) {// �ӱͿ��̾�
				int ac = getEnchantLevel() + 1;
				if (ac > 6) {
					os.writeC(2);
					os.writeC(ac);
				}
			} else if (getItemId() == 502007) { // �ӱ� ���̾�
				int ac = getEnchantLevel() + 2;
				if (ac > 6) {
					os.writeC(2);
					os.writeC(ac);
				}
			} else if (getItemId() == 500009) { // ���� ���̾�
				int ac = getEnchantLevel() - 5;
				if (ac > 0) {
					os.writeC(2);
					os.writeC(ac);
				}
			} else if (getItemId() == 502009) { // ���� ���̾�
				int ac = getEnchantLevel() - 4;
				if (ac > 0) {
					os.writeC(2);
					os.writeC(ac);
				}
			} else if (getItem().getGrade() != 3 && itemType2 == 2
					&& (getItem().getType() == 8 || getItem().getType() == 12)) { //
				int ac = getEnchantLevel() - 4;
				if (ac > 0) {
					os.writeC(2);
					os.writeC(ac);
				} // 6���� �Ͱ��� ����� ac
			} else if (getItemId() == 231006 || getItemId() == 231004 || getItemId() == 231005) { // Ŀ�� ���� ��� ���� ����
				int ac = getEnchantLevel() - 3;
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 5:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 6:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 7:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 8:
					os.writeC(2);
					os.writeC(ac - 2);
					break;
				}
			} else if (getItemId() == 231007 || getItemId() == 231008 || getItemId() == 231009) { // Ŀ�� ��ȣ����
				int ac = getEnchantLevel() - 2;
				switch (getEnchantLevel()) {
				case 3:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 4:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 5:
					os.writeC(2);
					os.writeC(ac);
					os.writeC(60);
					os.writeC(1); // pvp����� ������
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(5);
					break;
				case 6:
					os.writeC(2);
					os.writeC(ac + 1);
					os.writeC(60);
					os.writeC(2); // pvp����� ������
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(10);
					break;
				case 7:
					os.writeC(2);
					os.writeC(ac + 1);
					os.writeC(60);
					os.writeC(3); // pvp����� ������
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(15);
					break;
				case 8:
					os.writeC(2);
					os.writeC(ac + 1);
					os.writeC(60);
					os.writeC(5); // pvp����� ������
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(20);
					break;
				}
			} else if (getItemId() == 231003) {
				int ac = getEnchantLevel() - 2;
				switch (getEnchantLevel()) {
				case 3:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 4:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 5:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 6:
					os.writeC(2);
					os.writeC(ac + 1);
					break;
				case 7:
					os.writeC(2);
					os.writeC(ac + 1);
					break;
				case 8:
					os.writeC(2);
					os.writeC(ac + 1);
					break;
			    }
			} else if (itemId == 500008) { // ��Ƽ��
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(2);
					os.writeC(1);
					break;
				case 6:
				case 7:
					os.writeC(2);
					os.writeC(2);
					break;
				case 8:
					os.writeC(2);
					os.writeC(3);
					break;
				}
			} else if (itemId == 502008) { // ��Ƽ��
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(2);
					os.writeC(1);
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(2);
					break;
				case 7:
					os.writeC(2);
					os.writeC(3);
					break;
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}
			} else if ((getItem().getItemId() >= 425109 && getItem().getItemId() <= 425113)
					|| (getItem().getItemId() >= 525109 && getItem().getItemId() <= 525113)
					|| (getItem().getItemId() >= 625109 && getItem().getItemId() <= 625113)) {
				switch (getEnchantLevel()) {
				case 0:
				case 1:
					break;
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}

			} else if ((getItem().getItemId() == 525115)) { // ���
				switch (getEnchantLevel()) {
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel());
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}
			} else if ((getItem().getItemId() == 525109 // ȸ��
					|| getItem().getItemId() == 525110 // ����
					|| getItem().getItemId() == 525111 // ü��
					|| getItem().getItemId() == 525112 // ����
					|| getItem().getItemId() == 525113 // ����
					|| getItem().getItemId() == 525114)// ����
			) {
				switch (getEnchantLevel()) {
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(3);
					break;
				}
			} else if ((getItem().getItemId() == 625115)) { // ���
				switch (getEnchantLevel()) {
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel());
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(4);
					break;
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(5);
					break;
				}
			} else if ((getItem().getItemId() == 625109 || getItem().getItemId() == 625110
					|| getItem().getItemId() == 625111 || getItem().getItemId() == 625112
					|| getItem().getItemId() == 625113 || getItem().getItemId() == 625114)) {
				switch (getEnchantLevel()) {
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(3);
					break;
				case 7:
				case 8:
					if (getItem().getItemId() == 625111 || getItem().getItemId() == 625113
							|| getItem().getItemId() == 625114) {
						os.writeC(2);
						os.writeC(4);
						break;
					} else {
						os.writeC(2);
						os.writeC(3);
						break;
					}
				}

			} else if ((getItemId() == 21247 || getItemId() == 21248)) { // ������ ü��,��������ac
				int ac = getEnchantLevel() - 1;
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 2:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 3:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 4:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 5:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 6:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 7:
					os.writeC(2);
					os.writeC(ac - 2);
					break;
				case 8:
					os.writeC(2);
					os.writeC(ac - 3);
					break;
				}
			} else if ((getItemId() == 21246)) { // ������ ���� ac
				int ac = getEnchantLevel() - 1;
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 2:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 3:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 4:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 5:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 6:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 7:
					os.writeC(2);
					os.writeC(ac - 2);
					break;
				case 8:
					os.writeC(2);
					os.writeC(ac - 3);
					break;
				}
			} else if ((getItemId() == 21251)) { // ������ �ູ��������ac
				int ac = getEnchantLevel() - 1;
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 2:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 3:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 4:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 5:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 6:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 7:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 8:
					os.writeC(2);
					os.writeC(ac - 2);
					break;
				}
			} else if ((getItemId() == 21252)) { // ������ �ູü��ac
				int ac = getEnchantLevel() - 1;
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 2:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 3:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 4:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 5:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 6:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 7:
					os.writeC(2);
					os.writeC(ac - 2);
					break;
				case 8:
					os.writeC(2);
					os.writeC(ac - 3);
					break;
				}
			} else if ((getItemId() == 21250)) { // ������ �ູ���� ac
				int ac = getEnchantLevel() - 1;
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 2:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 3:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 4:
					os.writeC(2);
					os.writeC(ac);
					break;
				case 5:
					os.writeC(2);
					os.writeC(ac - 1);
					break;
				case 6:
					os.writeC(2);
					os.writeC(ac - 2);
					break;
				case 7:
					os.writeC(2);
					os.writeC(ac - 2);
					break;
				case 8:
					os.writeC(2);
					os.writeC(ac - 3);
					break;
				}
			} else if ((getItemId() >= 21250 && getItemId() <= 21252)
					|| (getItemId() >= 21273 && getItemId() <= 21275)) { // ������
																			// �ູ����
				switch (getEnchantLevel()) {
				case 0:
				case 1:
					break;
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(3);
					break;
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}
			} else if (getItemId() == 21249) { // ������ ��� ����
				switch (getEnchantLevel()) {
				case 0:
					break;
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel());
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}
			} else

			if (getItemId() == 21253) { // ������ ��� �ູ ����
				switch (getEnchantLevel()) {
				case 0:
					break;
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel());
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(4);
					break;
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(5);
					break;
				}
			} else if (getEnchantLevel() != 0
					&& !(itemType2 == 2 && (getItem().getType() >= 8 && getItem().getType() <= 12
							|| getItem().getType() == 16 || getItem().getType() == 20))) { // ����, ���� �߰�
				if(getItemId() == 61 || getItemId() == 134 || getItemId() == 12 || getItemId() == 86 || getItemId() == 30080
						|| getItemId() == 30081 || getItemId() == 30082 || getItemId() == 30083 || getItemId() == 450044
						|| getItemId() == 450045 || getItemId() == 450046 || getItemId() == 450047 || getItemId() == 450048
						|| getItemId() == 450049 || getItemId() == 450050 || getItemId() == 450051 || getItemId() == 30110 || getItemId() == 30111 || getItemId() == 30112) { //���� 1��þ ��Ÿ2
					os.writeC(2);
					os.writeC(getEnchantLevel());
					os.writeC(107);
					os.writeC(getEnchantLevel() * 2);
					os.writeC(getEnchantLevel() * 2);
				} else {
					os.writeC(2);
					os.writeC(getEnchantLevel());
					os.writeC(107);
					os.writeC(getEnchantLevel() + (getEnchantLevel() >= 10 ? 1 : 0));
					os.writeC(getEnchantLevel() + (getEnchantLevel() >= 10 ? 1 : 0));
				}
			
			} 
			/*if (getAttrEnchantLevel() != 0 && !(itemType2 == 2)) { // �Ӽ� ��Ÿ ǥ��κ�
				switch (getAttrEnchantLevel()) {
				case 1:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(1);
					break;
				case 2:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(3);
					break;
				case 3:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(5);
					break;
				case 4:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(1);
					break;
				case 5:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(3);
					break;
				case 6:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(5);
					break;
				case 7:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(1);
					break;
				case 8:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(3);
					break;
				case 9:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(5);
					break;
				case 10:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(1);
					break;
				case 11:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(3);
					break;
				case 12:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(5);
					break;
				case 33:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(7);
					break;
				case 34:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(9);
					break;
				case 35:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(7);
					break;
				case 36:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(9);
					break;
				case 37:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(7);
					break;
				case 38:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(9);
					break;
				case 39:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(7);
					break;
				case 40:
					os.writeC(109); //���� �Ӽ������ +1
					os.writeC(9);
					break;
				}
			}*/

			if (get_durability() != 0) {
				os.writeC(3);
				os.writeC(get_durability());
			}
			if (getItem().isTwohandedWeapon()) {
				os.writeC(4);
			}

			if (getItem().getItemId() == 21269) {
				os.writeC(0x28);
				os.writeC(3);
			}

			// ���� ����
			if (getItemId() == 525115) { // ������ ��� ����
				switch (getEnchantLevel()) {
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(5);
					os.writeC(getEnchantLevel() - 4);
					break;
				}
			} else if (getItemId() == 625115) { // ������ ��� ����
				switch (getEnchantLevel()) {
				case 4:
				case 5:
				case 6:
				case 7:
					os.writeC(5);
					os.writeC(getEnchantLevel() - 4);
					break;
				case 8:
					os.writeC(5);
					os.writeC(5);
					break;
				}
			} else if (getItem().getHitModifier() != 0) {
				if (getItem().getType2() == 1 && (getItem().getType1() == 20 || getItem().getType1() == 62)) {
					os.writeC(24);
					os.writeC(getItem().getHitModifier());
				} else {
					os.writeC(48);
					os.writeC(getItem().getHitModifier());
				}
			}
			// ��Ÿ
			if (getItem().getType2() == 1 && getItem().getType() != 7 && getItem().getType() != 17
					&& getStepEnchantLevel() != 0) {// ����Ÿ�ֹ����̿���...�ù�
				os.writeC(6);
				os.writeC(getItem().getDmgModifier() + (getStepEnchantLevel() * 2));
			} else if (getItem().getGrade() != 3 && itemType2 == 2
					&& (getItem().getType() == 9 || getItem().getType() == 11)) { // ����~
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgModifier() + 1);
					break;
				case 6:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgModifier() + 2);
					break;
				case 7:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgModifier() + 3);
					break;
				case 8:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgModifier() + 4);
					break;
				case 9:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgModifier() + 5);
					break;
				default:
					break;
				}
			} else if (getItemId() == 500010 || getItemId() == 502010) { // ��Ƽ������
				if (getEnchantLevel() >= 3) {
					os.writeC(6);
					int dm = getEnchantLevel() - 2;
					if (getBless() != 0 && getEnchantLevel() >= 4)
						dm -= 1;
					os.writeC(getItem().getDmgModifier() + dm);
				}
			} else if ((getItemId() >= 525109 && getItemId() <= 525115) && getItemId() != 525114) { // ������
																									// ���
																									// ����
				switch (getEnchantLevel()) {
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getEnchantLevel() - 4);
					break;
				}
			} else if ((getItemId() >= 625109 && getItemId() <= 625115) && getItemId() != 625114) { // ������
																									// ���
																									// ����
				switch (getEnchantLevel()) {
				case 4:
				case 5:
				case 6:
				case 7:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getEnchantLevel() - 3);
					break;
				case 8:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(5);
					break;
				}

			} else if (getItem().getGrade() == 3) { // ���� ����
				if ((getItem().getItemId() >= 425109 && getItem().getItemId() <= 425113)
						// ||(getItem().getItemId() >=
						// 525109&&getItem().getItemId() <= 525113)
						// ||(getItem().getItemId() >=
						// 625109&&getItem().getItemId() <= 625113)
						|| (getItemId() >= 21247 && getItemId() <= 21249)) {
					switch (getEnchantLevel()) {
					case 5:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 1);
						break;
					case 6:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 2);
						break;
					case 7:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 3);
						break;
					case 8:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 4);
						break;
					default:
						break;
					}
				} else if ((getItemId() >= 21251 && getItemId() <= 21253)
						|| (getItemId() >= 21273 && getItemId() <= 21275)) {
					switch (getEnchantLevel()) {
					case 4:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 1);
						break;
					case 5:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 2);
						break;
					case 6:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 3);
						break;
					case 7:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 4);
						break;
					case 8:
						os.writeC(47);
						os.writeC(getItem().getDmgModifier() + 5);
						break;
					default:
						break;
					}
				}
			} 
				if (getItem().getType2() == 1 && (getItem().getType1() == 20 || getItem().getType1() == 62)) {
					os.writeC(35);
					os.writeC(getItem().getDmgModifier());
				} else {
					os.writeC(47);
					os.writeC(getItem().getDmgModifier());
				}

			if (getItemId() == 21249 || getItemId() == 21253) { // ������ ��� ����
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
					break;
				case 4:
					if (getItemId() == 21253) { // ����ó��
						os.writeC(5);
						os.writeC(1);
					}
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(5);
					os.writeC((getEnchantLevel() - 4) + (getItemId() == 21253 ? 1 : 0));
					break;
				}

			} else if (getItemId() == 130220 && getEnchantLevel() >= 7) { // �ݺ���
																			// �尩
				os.writeC(5);
				os.writeC(getEnchantLevel() - 3);
			} else if (getItem().getHitup() != 0) {
				os.writeC(5);
				os.writeC(getItem().getHitup());
			}

			if (getItem().getDmgup() != 0) {
				os.writeC(6);
				os.writeC(getItem().getDmgup());
			}

			if (getItemId() == 21249) { // ������ ��� ����
				switch (getEnchantLevel()) {
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(24);
					os.writeC(getEnchantLevel() - 4);
					break;
				}
			} else if (getItemId() == 21253) { // ������ ��� ����
				switch (getEnchantLevel()) {
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(24);
					os.writeC(getEnchantLevel() - 3);
					break;
				}
			} else if (getItemId() == 900022 || getItemId() == 10168) { // ǳ���� ����
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(24);
					os.writeC(1);
					break;
				case 6:
					os.writeC(24);
					os.writeC(2);
					break;
				case 7:
					os.writeC(24);
					os.writeC(3);
					break;
				case 8:
					os.writeC(24);
					os.writeC(4);
					break;
				case 9:
					os.writeC(24);
					os.writeC(5);
					break;
				case 10:
					os.writeC(24);
					os.writeC(6);
					break;
				}
			} else if (getItem().getBowHitup() != 0) {
				os.writeC(24);
				os.writeC(getItem().getBowHitup());
			}

			// ��Ÿ111
			if (getItem().getType1() == 20 && getItem().getType1() == 62 && getStepEnchantLevel() != 0) {// ����Ÿ�ֹ����̿���...�ù�
				os.writeC(35);
				os.writeC(getItem().getBowDmgup() + (getStepEnchantLevel() * 2));
			} else if (getItem().getGrade() != 3 && itemType2 == 2
					&& (getItem().getType() == 9 || getItem().getType() == 11)) { // ����~
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 6:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 2);
					break;
				case 7:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 3);
					break;
				case 8:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 4);
					break;
				case 9:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 5);
					break;
				default:
					break;
				}
			} else if (getItemId() == 900022) { // ǳ���ǰ���
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 1:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 2:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 3:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 4:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 5:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 6:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 7:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 2);
					break;
				case 8:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 3);
					break;
				case 9:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 4);
					break;
				case 10:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 5);
					break;
				default:
					break;
				}
			} else if (getItemId() == 500010 || getItemId() == 502010) { // ��Ƽ������
				if (getEnchantLevel() >= 3) {
					os.writeC(35);
					int dm = getEnchantLevel() - 2;
					if (getBless() != 0 && getEnchantLevel() >= 4)
						dm -= 1;
					os.writeC(dm);
				}
			} else if ((getItemId() >= 21247 && getItemId() <= 21249)) { // ������
																			// ���
																			// ����
				switch (getEnchantLevel()) {
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(35);
					os.writeC(getEnchantLevel() - 4);
					break;
				}
			} else if ((getItemId() >= 21251 && getItemId() <= 21253)
					|| (getItemId() >= 21273 && getItemId() <= 21275)) { // ������
																			// ���
																			// ����
				switch (getEnchantLevel()) {
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(35);
					os.writeC(getEnchantLevel() - 3);
					break;
				}
			} else if (getItem().getGrade() == 3) { // ���� ����
				if ((getItem().getItemId() >= 425109 && getItem().getItemId() <= 425113)
				// ||(getItem().getItemId() >= 525109&&getItem().getItemId() <=
				// 525113)
				// ||(getItem().getItemId() >= 625109&&getItem().getItemId() <=
				// 625113)
				) {
					switch (getEnchantLevel()) {
					case 5:
						os.writeC(35);
						os.writeC(getItem().getBowDmgup() + 1);
						break;
					case 6:
						os.writeC(35);
						os.writeC(getItem().getBowDmgup() + 2);
						break;
					case 7:
						os.writeC(35);
						os.writeC(getItem().getBowDmgup() + 3);
						break;
					case 8:
						os.writeC(35);
						os.writeC(getItem().getBowDmgup() + 4);
						break;
					default:
						break;
					}
				}
			} else if (getItem().getBowDmgup() != 0) {
				os.writeC(35);
				os.writeC(getItem().getBowDmgup());
			}

			if (itemId == 126 || itemId == 127 || itemId == 450012 || itemId == 100035 || itemId == 412002
					|| itemId == 450011 || itemId == 450023 || itemId == 450025 || itemId == 450013
					|| itemId == 413103) {
				os.writeC(16);
			}
			if (itemId == 412001 || itemId == 450009 || itemId == 450008 || itemId == 450010 || itemId == 262
					|| itemId == 12 || itemId == 30080 || itemId == 30089 || itemId == 31080 || itemId == 1412001
					|| itemId == 100032 || itemId == 450032 || itemId == 30088 || itemId == 222202
					|| itemId == 222205) {
				os.writeC(34);
			}

			// STR~CHA
			if (getItem().get_addstr() != 0) {
				os.writeC(8);
				os.writeC(getItem().get_addstr());
			}
			if (getItem().get_adddex() != 0) {
				os.writeC(9);
				os.writeC(getItem().get_adddex());
			}
			if (getItem().get_addcon() != 0) {
				os.writeC(10);
				os.writeC(getItem().get_addcon());
			}
			if (getItem().get_addwis() != 0) {
				os.writeC(11);
				os.writeC(getItem().get_addwis());
			}
			if (getStepEnchantLevel() != 0 && getItem().getType2() == 1 && getItem().getType() == 17) {
				os.writeC(12);
				os.writeC(getItem().get_addint() + getStepEnchantLevel());
			} else if (getItem().get_addint() != 0) {
				os.writeC(12);
				os.writeC(getItem().get_addint());
			}
			if (getItem().get_addcha() != 0) {
				os.writeC(13);
				os.writeC(getItem().get_addcha());
			}

			if (itemId == 427205) { // ����
				os.writeC(40);
				os.writeC(2); // ����1

			}

			if (itemId == 30083 || itemId == 31083 || itemId == 30092 || itemId == 222208) { // ����
					os.writeC(102); // Ÿ��ź �迭 ���� +1%
					os.writeC(5);
			}

			if (itemId == 501214) { // ������ ����
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(14);
					os.writeH(10);
					break;
				case 1:
					os.writeC(14);
					os.writeH(15);
					break;
				case 2:
					os.writeC(14);
					os.writeH(20);
					break;
				case 3:
					os.writeC(14);
					os.writeH(25);
					break;
				case 4:
					os.writeC(14);
					os.writeH(30);
					break;
				case 5:
					os.writeC(14);
					os.writeH(35);
					break;
				case 6:
					os.writeC(14);
					os.writeH(40);
					break;
				case 7:
					os.writeC(14);
					os.writeH(45);
					break;
				case 8:
					os.writeC(14);
					os.writeH(50);
					break;
				case 9:
					os.writeC(14);
					os.writeH(55);
					break;
				case 10:
					os.writeC(14);
					os.writeH(60);
					break;

				}
			}
			if (itemId == 5000003) { //�巡�� �����̾� ���� mp,hp ǥ��κ� 
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 1:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 2:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 3:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 4:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 5:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 6:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 7:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(150);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 8:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(200);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 9:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(250);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 10:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(300);
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;

				}
			}
			
			if (itemId == 501211) { // ������ ����
				switch (getEnchantLevel()) {
				case 9:
					os.writeC(17); // sp
					os.writeC(1);
					break;
				case 10:
					os.writeC(17); // sp
					os.writeC(2);
					break;

				}
			}
			if (itemId == 501212) { // ������ ����
				switch (getEnchantLevel()) {
				case 9:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					break;
				case 10:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(2);
					break;

				}
			}
			if (itemId == 501213) { // ������ ����
				switch (getEnchantLevel()) {
				case 9:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 10:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					break;

				}
			}

			if (itemId == 420000) { // ��� ����� ����
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 1:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 2:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 3:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 4:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 5:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					break;
				case 6:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					break;
				case 7:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(3);
					break;
				case 8:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(3);
					break;
				case 9:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(4);
					break;
				case 10:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(4);
					break;
				}
			}
			/** �����ǱͰ��� **/
			if (itemId == 30016) {
				os.writeC(40);
				os.writeC(2); // ����2
			}
			/** �ӹ̷ε�հ� ���Ÿ� ����� ǥ�� **/
			if (itemId == 20017 && getEnchantLevel() > 4) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 6:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					break;
				case 7:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(3);
					break;
				case 8:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(4);
					break;
				case 9:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(5);
					break;
				case 10:
					os.writeC(35); // ���Ÿ� �����
					os.writeC(6);
					break;
				default:
					break;
				}
			}
			/** ��ȣ���� Ȱ �� ���Ÿ� ���� **/
			if (itemType2 == 2 && itemId == 222343 && getEnchantLevel() > 4) {
				os.writeC(24);
				os.writeC(getItem().getBowHitup() + getEnchantLevel() - 4);
			}
			/**(����/��ø/����/�Ϸ�)�� ���� * : +7���� �ִ� HP +20/+40/+60 ���� : +9�� ����� ����+1 �߰�  **/
			if (itemId == 21259 || itemId == 21265 || itemId == 21266 || itemId == 30218 || itemId == 1021259
					|| itemId == 1021265 || itemId == 1021266 || itemId == 1030218 && getEnchantLevel() > 6) {
				switch (getEnchantLevel()) {
				case 7:
					os.writeC(14);
					os.writeH(20);
					break;
				case 8:
					os.writeC(14);
					os.writeH(40);
					break;
				case 9:
					os.writeC(14);
					os.writeH(60);
					os.writeC(63); // ����� ����
					os.writeC(1);
					break;
				case 10:
					os.writeC(14);
					os.writeH(60);
					os.writeC(63); // ����� ����
					os.writeC(2);
					break;
				default:
					break;
				}
			}
			if (itemId == 61 || itemId == 30083 || itemId == 30080 || itemId == 450044 || itemId ==450051 || itemId ==450049 || itemId ==30110) { //���� �ٰŸ� ġ��Ÿ
					os.writeC(100); //�ٰŸ� ġ��Ÿ+1
					os.writeC(getEnchantLevel() + 1);
			}
			
			if (itemId == 30111 || itemId == 30112) { //�����ϻ���� ���� �׶�ī���ǽ���
				os.writeC(100); //�ٰŸ� ġ��Ÿ+1
				os.writeC(getEnchantLevel() > 0 ? (getEnchantLevel() * 2) + 7 : 7);
			}
			
			/** Ŀ�� ���� ���� **/
			if (itemId == 231006) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(14);
					os.writeH(5);
					break;
				case 1:
					os.writeC(14);
					os.writeH(10);
					break;
				case 2:
					os.writeC(14);
					os.writeH(15);
					break;
				case 3:
					os.writeC(14);
					os.writeH(20);
					break;
				case 4:
					os.writeC(14);
					os.writeH(25);
					break;
				case 5:
					os.writeC(14);
					os.writeH(30);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					break;
				case 6:
					os.writeC(14);
					os.writeH(35);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(2);
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(1);
					break;
				case 7:
					os.writeC(14);
					os.writeH(40);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(3);
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(3);
					break;
				case 8:
					os.writeC(14);
					os.writeH(50);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(4);
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					break;
				}
			}
			/** Ŀ���� ��� ���� **/
			if (itemId == 231004) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(14);
					os.writeH(5);
					break;
				case 1:
					os.writeC(14);
					os.writeH(10);
					break;
				case 2:
					os.writeC(14);
					os.writeH(15);
					break;
				case 3:
					os.writeC(14);
					os.writeH(20);
					break;
				case 4:
					os.writeC(14);
					os.writeH(25);
					break;
				case 5:
					os.writeC(14);
					os.writeH(30);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 6:
					os.writeC(14);
					os.writeH(35);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(1);
					break;
				case 7:
					os.writeC(14);
					os.writeH(40);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(3);
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(2);
					break;
				case 8:
					os.writeC(14);
					os.writeH(50);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(4);
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(3);
					break;
				}
			}
			/** Ŀ���� ���� ���� **/
			if (itemId == 231005) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(14);
					os.writeH(5);
					break;
				case 1:
					os.writeC(14);
					os.writeH(10);
					break;
				case 2:
					os.writeC(14);
					os.writeH(15);
					break;
				case 3:
					os.writeC(14);
					os.writeH(20);
					break;
				case 4:
					os.writeC(14);
					os.writeH(25);
					break;
				case 5:
					os.writeC(14);
					os.writeH(30);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(1);
					break;
				case 6:
					os.writeC(14);
					os.writeH(35);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(2);
					os.writeC(50);
					os.writeD(1); // ��ġ1����
					break;
				case 7:
					os.writeC(14);
					os.writeH(40);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(3);
					os.writeC(50);
					os.writeD(2); // ��ġ1����
					break;
				case 8:
					os.writeC(14);
					os.writeH(50);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(4);
					os.writeC(50);
					os.writeD(4); // ��ġ1����
					break;
				}
			}
			/** ��ȣ�� ���� ���� **/
			if (itemId == 231007) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(14);
					os.writeH(30);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					os.writeC(63); // ����� ����
					os.writeC(1);
					break;
				case 6:
					os.writeC(14);
					os.writeH(35);
					os.writeC(63); // ����� ����
					os.writeC(2);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(2);
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(1);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(1);
					break;
				case 7:
					os.writeC(14);
					os.writeH(40);
					os.writeC(63); // ����� ����
					os.writeC(3);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(3);
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(3);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(3);
					break;
				case 8:
					os.writeC(14);
					os.writeH(50);
					os.writeC(63); // ����� ����
					os.writeC(4);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(4);
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(5);
					break;
				}
			}
			/** ��ȣ�� ��� ���� **/
			if (itemId == 231008) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(14);
					os.writeH(30);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					os.writeC(63); // ����� ����
					os.writeC(1);
					break;
				case 6:
					os.writeC(14);
					os.writeH(35);
					os.writeC(63); // ����� ����
					os.writeC(2);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(1);
					os.writeC(24);
					os.writeC(1);
					break;
				case 7:
					os.writeC(14);
					os.writeH(40);
					os.writeC(63); // ����� ����
					os.writeC(3);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(3);
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(3);
					os.writeC(24);
					os.writeC(3);
					break;
				case 8:
					os.writeC(14);
					os.writeH(50);
					os.writeC(63); // ����� ����
					os.writeC(4);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(4);
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(24);
					os.writeC(5);
					break;
				}
			}
			/** ��ȣ�� ���� ���� **/
			if (itemId == 231009) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(14);
					os.writeH(30);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(1);
					os.writeC(63); // ����� ����
					os.writeC(1);
					break;
				case 6:
					os.writeC(14);
					os.writeH(35);
					os.writeC(63); // ����� ����
					os.writeC(2);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(2);
					os.writeC(50);
					os.writeD(1); // ��ġ1����
					os.writeC(40);
					os.writeC(1); // ����1
					break;
				case 7:
					os.writeC(14);
					os.writeH(40);
					os.writeC(63); // ����� ����
					os.writeC(3);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(3);
					os.writeC(50);
					os.writeD(3); // ��ġ1����
					os.writeC(40);
					os.writeC(3); // ����1
					break;
				case 8:
					os.writeC(14);
					os.writeH(50);
					os.writeC(63); // ����� ����
					os.writeC(4);
					os.writeC(5); // �ٰŸ� ����
					os.writeC(4);
					os.writeC(50);
					os.writeD(5); // ��ġ1����
					os.writeC(40);
					os.writeC(5); // ����1
					break;
				}
			}
			/** Ŀ�� ��ȣ ���� **/
			if (itemId == 231003) {
				switch (getEnchantLevel()) {
				case 6:
					os.writeC(15);
					os.writeH(getMr() + 3);
					break;
				case 7:
					os.writeC(15);
					os.writeH(getMr() + 5);
					break;
				case 8:
					os.writeC(15);
					os.writeH(getMr() + 7);
					break;
				default:
					break;
				}
			}
			/** Ŀ�� ��ȣ ���� **/
			if (itemId == 231003) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(14);
					os.writeH(5);
					break;
				case 1:
					os.writeC(14);
					os.writeH(10);
					break;
				case 2:
					os.writeC(14);
					os.writeH(15);
					break;
				case 3:
					os.writeC(14);
					os.writeH(20);
					break;
				case 4:
					os.writeC(14);
					os.writeH(25);
					break;
				case 5:
					os.writeC(14);
					os.writeH(30);
					os.writeC(63); // ����� ����
					os.writeC(1);
					break;
				case 6:
					os.writeC(14);
					os.writeH(35);
					os.writeC(63); // ����� ����
					os.writeC(2);
					break;
				case 7:
					os.writeC(14);
					os.writeH(40);
					os.writeC(63); // ����� ����
					os.writeC(3);
					break;
				case 8:
					os.writeC(14);
					os.writeH(50);
					os.writeC(63); // ����� ����
					os.writeC(4);
					break;
				}
			}//���� ������������ Ȥ�� ������ ��Ű��� �ɼǺκ� 
			/** ���Ľ��ǿϷ� **/
			if (itemId == 27528) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 1:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 2:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 3:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 4:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 5:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 6:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 7:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 8:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 9:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 10:
					os.writeC(100); // �ٰŸ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);//�Ʊ�ó���� �Ͻø�ɲ����� ��ü������ ��� �̹��������� ���?�׷� ��
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				}
			}/**���Ľ��ǿ�����*/
			if (itemId == 27529) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 1:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 2:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 3:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 4:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 5:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 6:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 7:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 8:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 9:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				case 10:
					os.writeC(99); // ���Ÿ� ġ��Ÿ+1
					os.writeC(5);
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");///�ؿ��� �Ȱ��� �� �۾����ֽø� �Ǳ���
					break;
				}
			}
			/** ���Ľ��� ���� **/ // �߰����� by.���ۻ�̳�
			if (itemId == 27530) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 200); //MP +200
					os.writeC(97);//�����ǹ���
					os.writeC(5);
					os.writeC(63);
					os.writeC(���Ľ�DamageDown());
					os.writeC(70);
					os.writeC(2);// ������
					os.writeC(73);
					os.writeS("���Ľ��� ��ȣ, ���Ľ��� ����");
					break;
				}
			}
			/** �߶�ī���� �Ϸ�, �ߵ�, ����� ������ ����, �ٰŸ� �����, �ٰŸ� ġ��Ÿ, ǥ�� **/ //by.���ۻ�̳�
			if (itemId == 420115) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ġ��Ÿ +3
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 1:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ġ��Ÿ +3
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 2:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ġ��Ÿ +3
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 3:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ġ��Ÿ +3
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 4:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ġ��Ÿ +3
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 5:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ġ��Ÿ +3
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 6:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ġ��Ÿ +3
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 7:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(4);  //�ٰŸ� ġ��Ÿ +4
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(4);  //����� ������ ���� +4
					break;
				case 8:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(5);  //�ٰŸ� ġ��Ÿ +5
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(5);  //����� ������ ���� +5
					break;
				case 9:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(6);  //�ٰŸ� ġ��Ÿ +6
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(6);  //����� ������ ���� +6
					break;
				case 10:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(7);  //�ٰŸ� ġ��Ÿ +7
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(7);  //����� ������ ���� +7
					break;
				}
			}
			/** �߶�ī���� ������, �ߵ�, ����� ������ ����, �ٰŸ� �����, �ٰŸ� ġ��Ÿ, ǥ�� **/ //by.���ۻ�̳�
			if (itemId == 420113) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ġ��Ÿ +2
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 1:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ġ��Ÿ +2
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 2:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ġ��Ÿ +2
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 3:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ġ��Ÿ +2
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 4:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ġ��Ÿ +2
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 5:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ġ��Ÿ +2
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 6:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ġ��Ÿ +2
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 7:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ġ��Ÿ +3
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(4);  //����� ������ ���� +4
					break;
				case 8:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(4);  //�ٰŸ� ġ��Ÿ +4
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(5);  //����� ������ ���� +5
					break;
				case 9:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(5);  //�ٰŸ� ġ��Ÿ +5
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(6);  //����� ������ ���� +6
					break;
				case 10:
					os.writeC(100);//�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(6);  //�ٰŸ� ġ��Ÿ +6
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);  //�ٰŸ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: �ٰŸ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(7);  //����� ������ ���� +7
					break;
				}
			}
			/** �߶�ī���� �γ���, �ߵ�, ���Ÿ� ġ��Ÿ, ���Ÿ� �����, MP +30 ǥ�� **/ //by.���ۻ�̳�
			if (itemId == 420112) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ġ��Ÿ +2
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ġ��Ÿ +2
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ġ��Ÿ +2
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ġ��Ÿ +2
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ġ��Ÿ +2
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ġ��Ÿ +2
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ġ��Ÿ +2
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ġ��Ÿ +3
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(4);  //����� ������ ���� +4
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(4);  //���Ÿ� ġ��Ÿ +4
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(5);  //����� ������ ���� +5
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(5);  //���Ÿ� ġ��Ÿ +5
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(6);  //����� ������ ���� +6
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
					os.writeC(6);  //���Ÿ� ġ��Ÿ +6
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ����� +3
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ���Ÿ�");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(7);  //����� ������ ���� +7
					break;
				}
			}
			/** �߶�ī���� ����, MP +50, �ߵ� ǥ�� **/ //by.���ۻ�̳�
			if (itemId == 420114) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); ///�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); ///�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(4);  //����� ������ ���� +4
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(5);  //����� ������ ���� +5
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(6);  //����� ������ ���� +6
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					os.writeC(73); //�ߵ� �ڵ�
					os.writeS("�߶�ī���� ��ȣ: ����");
					os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
					os.writeC(7);  //����� ������ ���� +7
					break;
				}
			}
			if (itemId == 420003) { // ��� ������ ����
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					break;
				case 1:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					break;
				case 2:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					break;
				case 3:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					break;
				case 4:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					break;
				case 5:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(2);
					break;
				case 6:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(2);
					break;
				case 7:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(3);
					break;
				case 8:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(3);
					break;
				case 9:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(4);
					break;
				case 10:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(4);
					break;
				}
			}
			
			/** ���� ���� **/
			if (itemId == 10167) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 1);
					break;
				case 6:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 2);
					break;
				case 7:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 3);
					break;
				case 8:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 4);
					break;
				case 9:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					break;
				case 10:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 6);
					break;
				}
			}
			
			/** ����� ���� **/
			if (itemId == 10165) {
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 3);
					break;
				case 5:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 4);
					break;
				case 6:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					break;
				case 7:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					break;
				case 8:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					break;
				case 9:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					break;
				case 10:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					break;
				}
			}
			
			/** ��״����� ���� **/
			if (itemId == 10166) {
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 3);
					break;
				case 5:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 4);
					break;
				case 6:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 5);
					break;
				case 7:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 5);
					break;
				case 8:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 5);
					break;
				case 9:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 5);
					break;
				case 10:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 5);
					break;
				}
			}
			/** ȭ���ǰ��� **/
			if (itemId == 900023) {
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgup() + 1);
					break;
				case 5:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 1);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgup() + 1);
					break;
				case 6:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgup() + 1);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 2);
					break;
				case 7:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgup() + 2);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 3);
					break;
				case 8:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgup() + 3);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 4);
					break;
				case 9:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgup() + 4);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					break;
				case 10:
					os.writeC(47); // �ٰŸ� �����
					os.writeC(getItem().getDmgup() + 5);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 6);
					break;
				}
			}
			
			/** �巡���� ���� ���� **/
			if (itemId == 421219 || itemId == 431219) {
					os.writeC(40);
					os.writeC(6); // ����1
			}
			
			/** ���� 90�� **/
			if (itemId == 10139 || itemId == 10140 || itemId == 10141 || itemId == 10142 || itemId == 10143) {
					os.writeC(40);
					os.writeC(10); // ����1
			}
			
			/** ���� 90�� **/
			if (itemId == 10099 || itemId == 10100 || itemId == 10101 || itemId == 10102 || itemId == 10103) {
					os.writeC(40);
					os.writeC(5); // ����1
			}
			
			/** ĳ�� ������ 7�̻� â�� �̵� ���� **/
			if ((itemId >= 21246 && itemId <= 21253) || (itemId >= 500007 && itemId <= 500010) 
					|| (itemId >= 502007 && itemId <= 502010)) {
				os.writeC(148);
				os.writeC(7);
			}
			
			/** �����ǰ��� **/
			if (itemId == 900021 || itemId == 10169) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(40);
					os.writeC(1); // ����1
					break;
				case 6:
					os.writeC(40);
					os.writeC(2); // ����1
					break;
				case 7:
					os.writeC(40);
					os.writeC(3); // ����1
					break;
				case 8:
					os.writeC(40);
					os.writeC(4); // ����1
					break;
				case 9:
					os.writeC(40);
					os.writeC(5); // ����1
					break;
				case 10:
					os.writeC(40);
					os.writeC(6); // ����1
					break;
				}
			}
			
			/** �����ǰ��� **/
			if (itemId == 900020) {
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					os.writeC(63);
					os.writeC(1);
					break;
				case 7:
					os.writeC(63);
					os.writeC(2);
					break;
				case 8:
					os.writeC(63);
					os.writeC(3);
					break;
				case 9:
					os.writeC(63);
					os.writeC(4);
					break;
				case 10:
					os.writeC(63);
					os.writeC(5);
					break;
				}
			}
			/** �Ϸ� ȸ���� ���� **/
			if (itemId == 30010) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(6);
					os.writeC(6);
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 1);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 2);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 2);
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 3);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 3);
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 4);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 4);
					os.writeC(65); // ����ȸ����2����
					os.writeC(20);
					os.writeC(20);
					os.writeC(96);
					os.writeC(20); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 5);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					os.writeC(65); // ����ȸ����2����
					os.writeC(22);
					os.writeC(22);
					os.writeC(96);
					os.writeC(22); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** ��ø ȸ���� ���� **/
			if (itemId == 30011) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(6);
					os.writeC(6);
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 2);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 2);
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 3);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 3);
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 4);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 4);
					os.writeC(65); // ����ȸ����2����
					os.writeC(20);
					os.writeC(20);
					os.writeC(96);
					os.writeC(20); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 5);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 5);
					os.writeC(65); // ����ȸ����2����
					os.writeC(22);
					os.writeC(22);
					os.writeC(96);
					os.writeC(22); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** ���� ȸ���� ���� **/
			if (itemId == 30012) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(6);
					os.writeC(6);
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 1);
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 2);
					os.writeC(40);
					os.writeC(2); // ����1
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					os.writeC(40);
					os.writeC(3); // ����1
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 4);
					os.writeC(40);
					os.writeC(4); // ����1
					os.writeC(65); // ����ȸ����2����
					os.writeC(20);
					os.writeC(20);
					os.writeC(96);
					os.writeC(20); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 5);
					os.writeC(40);
					os.writeC(5); // ����1
					os.writeC(65); // ����ȸ����2����
					os.writeC(22);
					os.writeC(22);
					os.writeC(96);
					os.writeC(22); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** ȸ���� ���� **/
			if (itemId == 490022) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(6);
					os.writeC(6);
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(65); // ����ȸ����2����
					os.writeC(20);
					os.writeC(20);
					os.writeC(96);
					os.writeC(20); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(65); // ����ȸ����2����
					os.writeC(22);
					os.writeC(22);
					os.writeC(96);
					os.writeC(22); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** ȸ�������� ���� **/
			if (itemId == 30013) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(36);// ����ġ1����
					os.writeC(1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 1:
					os.writeC(36);// ����ġ1����
					os.writeC(2);
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(36);// ����ġ1����
					os.writeC(3);
					os.writeC(65); // ����ȸ����2����
					os.writeC(6);
					os.writeC(6);
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(36);// ����ġ1����
					os.writeC(4);
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(36);// ����ġ1����
					os.writeC(5);
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(36);// ����ġ1����
					os.writeC(6);
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(36);// ����ġ1����
					os.writeC(7);
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(36);// ����ġ1����
					os.writeC(9);
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(36);// ����ġ1����
					os.writeC(11);
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(36);// ����ġ1����
					os.writeC(13);
					os.writeC(65); // ����ȸ����2����
					os.writeC(20);
					os.writeC(20);
					os.writeC(96);
					os.writeC(20); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(36);// ����ġ1����
					os.writeC(15);
					os.writeC(65); // ����ȸ����2����
					os.writeC(22);
					os.writeC(22);
					os.writeC(96);
					os.writeC(22); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** ������ �Ҵ�Ʈ **/
			if (itemId == 30036) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(47);//�ٰŸ� ����� + 1
					os.writeC(1);
					break;
				case 1:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(4);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(2);
					break;
				case 2:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(6);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(3);
					os.writeC(15);
					os.writeH(getMr() + 2);
					break;
				case 3:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(8);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(4);
					os.writeC(15);
					os.writeH(getMr() + 4);
					break;
				case 4:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(10);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(5);
					os.writeC(47);//�ٰŸ� ����� + 1
					os.writeC(1);
					break;
				case 5:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(12);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(6);
					os.writeC(47);//�ٰŸ� ����� + 1
					os.writeC(2);
					break;
				case 6:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(14);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(7);
					os.writeC(47);//�ٰŸ� ����� + 1
					os.writeC(3);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(2);
					os.writeC(93); 
					os.writeC(2);
					os.writeC(89); //Me+16353
					os.writeD(2);
					os.writeC(60);
					os.writeC(2); // pvp����� ������
					break;
				case 7:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(17);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(8);
					os.writeC(47);//�ٰŸ� ����� + 1
					os.writeC(4);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(4);
					os.writeC(93); //er +4 
					os.writeC(4);
					os.writeC(89); //Me+16353
					os.writeD(4);
					os.writeC(60);
					os.writeC(4); // pvp����� ������
					break;
				case 8:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(20);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(10);
					os.writeC(47);//�ٰŸ� ����� + 1
					os.writeC(5);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(6);
					os.writeC(93); //er +4 
					os.writeC(6);
					os.writeC(89); //Me+16353
					os.writeD(6);
					os.writeC(60);
					os.writeC(6); // pvp����� ������
					break;
				}
			}
			/** �ູ���� ��Ƽ���� ���� ���Ʈ **/ //by.���ۻ�̳�
			if (itemId == 30040) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(6);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(3);
					os.writeC(15);
					os.writeH(getMr() + 2);
					break;
				case 2:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(8);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(4);
					os.writeC(15);
					os.writeH(getMr() + 4);
					break;
				case 3:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(10);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(5);
					os.writeC(47);//�ٰŸ� ����� + 1
					os.writeC(1);
					break;
				case 4:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(12);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(6);
					os.writeC(47);//�ٰŸ� ����� + 1
					os.writeC(2);
					break;
				case 5:
					os.writeC(149); //�ִ� HP % ǥ�� �ڵ�
					os.writeC(12);  //�ִ� HP + 12%
					os.writeC(150); //�ִ� MP % ǥ�� �ڵ�
					os.writeC(6);   //�ִ� MP + 6%
//					os.writeC(15);  //���� ��� ǥ�� �ڵ�
//					os.writeH(10);  //���� ��� + 10
					os.writeC(47);  //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(2);   //�ٰŸ� ����� +2
					os.writeC(51);  //�ٰŸ� ȸ��(DG) ǥ�� �ڵ�
					os.writeC(1);   //�ٰŸ� ȸ��(DG) +1
					os.writeC(93);  //���Ÿ� ȸ��(ER) ǥ�� �ڵ�
					os.writeC(1);   //���⸮ ȸ��(ER) +1
					os.writeC(89);  //Ȯ�� ���� ȸ��(ME) ǥ�� �ڵ�
					os.writeD(1);   //Ȯ�� ���� ȸ��(ME) +1
					os.writeC(60);  //PVP ����� ���� ǥ�� �ڵ�
					os.writeC(1);   //PVP ����� ���� +1
					break;
				case 6:
					os.writeC(149); //�ִ� HP % ǥ�� �ڵ�
					os.writeC(14);  //�ִ� HP + 14%
					os.writeC(150); //�ִ� MP % ǥ�� �ڵ�
					os.writeC(7);   //�ִ� MP + 7%
//					os.writeC(15);  //���� ��� ǥ�� �ڵ�
//					os.writeH(12);  //���� ��� + 12
					os.writeC(47);  //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(3);   //�ٰŸ� ����� +3
					os.writeC(51);  //�ٰŸ� ȸ��(DG) ǥ�� �ڵ�
					os.writeC(3);   //�ٰŸ� ȸ��(DG) +3
					os.writeC(93);  //���Ÿ� ȸ��(ER) ǥ�� �ڵ�
					os.writeC(3);   //���⸮ ȸ��(ER) +3
					os.writeC(89);  //Ȯ�� ���� ȸ��(ME) ǥ�� �ڵ�
					os.writeD(3);   //Ȯ�� ���� ȸ��(ME) +3
					os.writeC(60);  //PVP ����� ���� ǥ�� �ڵ�
					os.writeC(3);   //PVP ����� ���� +3
					break;
				case 7:
					os.writeC(149); //�ִ� HP % ǥ�� �ڵ�
					os.writeC(17);  //�ִ� HP + 17%
					os.writeC(150); //�ִ� MP % ǥ�� �ڵ�
					os.writeC(8);   //�ִ� MP + 8%
//					os.writeC(15);  //���� ��� ǥ�� �ڵ�
//					os.writeH(14);  //���� ��� + 14
					os.writeC(47);  //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(4);   //�ٰŸ� ����� +4
					os.writeC(51);  //�ٰŸ� ȸ��(DG) ǥ�� �ڵ�
					os.writeC(5);   //�ٰŸ� ȸ��(DG) +5
					os.writeC(93);  //���Ÿ� ȸ��(ER) ǥ�� �ڵ�
					os.writeC(5);   //���⸮ ȸ��(ER) +5
					os.writeC(89);  //Ȯ�� ���� ȸ��(ME) ǥ�� �ڵ�
					os.writeD(5);   //Ȯ�� ���� ȸ��(ME) +5
					os.writeC(60);  //PVP ����� ���� ǥ�� �ڵ�
					os.writeC(5);   //PVP ����� ���� +5
					break;
				case 8:
					os.writeC(149); //�ִ� HP % ǥ�� �ڵ�
					os.writeC(20);  //�ִ� HP + 20%
					os.writeC(150); //�ִ� MP % ǥ�� �ڵ�
					os.writeC(10);  //�ִ� MP + 10%
//					os.writeC(15);  //���� ��� ǥ�� �ڵ�
//					os.writeH(16);  //���� ��� + 16
					os.writeC(47);  //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(5);   //�ٰŸ� ����� +5
					os.writeC(51);  //�ٰŸ� ȸ��(DG) ǥ�� �ڵ�
					os.writeC(7);   //�ٰŸ� ȸ��(DG) +7
					os.writeC(93);  //���Ÿ� ȸ��(ER) ǥ�� �ڵ�
					os.writeC(7);   //���⸮ ȸ��(ER) +7
					os.writeC(89);  //Ȯ�� ���� ȸ��(ME) ǥ�� �ڵ�
					os.writeD(7);   //Ȯ�� ���� ȸ��(ME) +7
					os.writeC(60);  //PVP ����� ���� ǥ�� �ڵ�
					os.writeC(7);   //PVP ����� ���� +7
					break;
				}
			}
			/** �ູ���� ��Ƽ���� ��� ���Ʈ **/ //by.���ۻ�̳�
			if (itemId == 30039) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(3);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(3);
					break;
				case 2:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(5);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(5);
					break;
				case 3:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(7);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(7);
					os.writeC(35);//���Ÿ������+1
					os.writeC(1);
					break;
				case 4:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(9);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(9);
					os.writeC(35);//���Ÿ������+1
					os.writeC(2);
					break;
				case 5:
					os.writeC(149); //�ִ� HP % ǥ�� �ڵ�
					os.writeC(9);   //�ִ� HP + 9%
					os.writeC(150); //�ִ� MP % ǥ�� �ڵ�
					os.writeC(9);   //�ִ� MP + 9%
//					os.writeC(15);  //���� ��� ǥ�� �ڵ�
//					os.writeH(10);  //���� ��� + 10
					os.writeC(35);  //���Ÿ� ����� ǥ�� �ڵ�
 					os.writeC(2);   //���Ÿ� ����� +2
					os.writeC(51);  //�ٰŸ� ȸ��(DG) ǥ�� �ڵ�
					os.writeC(1);   //�ٰŸ� ȸ��(DG) +1
					os.writeC(93);  //���Ÿ� ȸ��(ER) ǥ�� �ڵ�
					os.writeC(1);   //���⸮ ȸ��(ER) +1
					os.writeC(89);  //Ȯ�� ���� ȸ��(ME) ǥ�� �ڵ�
					os.writeD(1);   //Ȯ�� ���� ȸ��(ME) +1
					os.writeC(60);  //PVP ����� ���� ǥ�� �ڵ�
					os.writeC(1);   //PVP ����� ���� +1
					break;
				case 6:
					os.writeC(149); //�ִ� HP % ǥ�� �ڵ�
					os.writeC(11);  //�ִ� HP + 11%
					os.writeC(150); //�ִ� MP % ǥ�� �ڵ�
					os.writeC(11);  //�ִ� MP + 11%
//					os.writeC(15);  //���� ��� ǥ�� �ڵ�
//					os.writeH(12);  //���� ��� + 12
					os.writeC(35);  //���Ÿ� ����� ǥ�� �ڵ�
 					os.writeC(3);   //���Ÿ� ����� +3
					os.writeC(51);  //�ٰŸ� ȸ��(DG) ǥ�� �ڵ�
					os.writeC(3);   //�ٰŸ� ȸ��(DG) +3
					os.writeC(93);  //���Ÿ� ȸ��(ER) ǥ�� �ڵ�
					os.writeC(3);   //���⸮ ȸ��(ER) +3
					os.writeC(89);  //Ȯ�� ���� ȸ��(ME) ǥ�� �ڵ�
					os.writeD(3);   //Ȯ�� ���� ȸ��(ME) +3
					os.writeC(60);  //PVP ����� ���� ǥ�� �ڵ�
					os.writeC(3);   //PVP ����� ���� +3
					break;
				case 7:
					os.writeC(149); //�ִ� HP % ǥ�� �ڵ�
					os.writeC(13);  //�ִ� HP + 13%
					os.writeC(150); //�ִ� MP % ǥ�� �ڵ�
					os.writeC(13);  //�ִ� MP + 13%
//					os.writeC(15);  //���� ��� ǥ�� �ڵ�
//					os.writeH(14);  //���� ��� + 14
					os.writeC(35);  //���Ÿ� ����� ǥ�� �ڵ�
 					os.writeC(4);   //���Ÿ� ����� +4
					os.writeC(51);  //�ٰŸ� ȸ��(DG) ǥ�� �ڵ�
					os.writeC(5);   //�ٰŸ� ȸ��(DG) +5
					os.writeC(93);  //���Ÿ� ȸ��(ER) ǥ�� �ڵ�
					os.writeC(5);   //���⸮ ȸ��(ER) +5
					os.writeC(89);  //Ȯ�� ���� ȸ��(ME) ǥ�� �ڵ�
					os.writeD(5);   //Ȯ�� ���� ȸ��(ME) +5
					os.writeC(60);  //PVP ����� ���� ǥ�� �ڵ�
					os.writeC(5);   //PVP ����� ���� +5
					break;
				case 8:
					os.writeC(149); //�ִ� HP % ǥ�� �ڵ�
					os.writeC(15);  //�ִ� HP + 15%
					os.writeC(150); //�ִ� MP % ǥ�� �ڵ�
					os.writeC(15);  //�ִ� MP + 15%
//					os.writeC(15);  //���� ��� ǥ�� �ڵ�
//					os.writeH(16);  //���� ��� + 16
					os.writeC(35);  //���Ÿ� ����� ǥ�� �ڵ�
 					os.writeC(5);   //���Ÿ� ����� +5
					os.writeC(51);  //�ٰŸ� ȸ��(DG) ǥ�� �ڵ�
					os.writeC(7);   //�ٰŸ� ȸ��(DG) +7
					os.writeC(93);  //���Ÿ� ȸ��(ER) ǥ�� �ڵ�
					os.writeC(7);   //���⸮ ȸ��(ER) +7
					os.writeC(89);  //Ȯ�� ���� ȸ��(ME) ǥ�� �ڵ�
					os.writeD(7);   //Ȯ�� ���� ȸ��(ME) +7
					os.writeC(60);  //PVP ����� ���� ǥ�� �ڵ�
					os.writeC(7);   //PVP ����� ���� +7
					break;
				}
			}
			/** ������ �Ҵ�Ʈ(�ູ) **/
			if (itemId == 30041) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(3);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(6);
					break;
				case 2:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(4);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(8);
					break;
				case 3:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(5);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(10);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 1);
					break;
				case 4:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(6);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(12);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 2);
					break;
				case 5:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(7);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(14);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(2);
					os.writeC(93); 
					os.writeC(2);
					os.writeC(89); //Me+16353
					os.writeD(2);
					os.writeC(60);
					os.writeC(2); // pvp����� ������
					break;
				case 6:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(8);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(17);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 4);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(4);
					os.writeC(93); //er +4 
					os.writeC(4);
					os.writeC(89); //Me+16353
					os.writeD(4);
					os.writeC(60);
					os.writeC(4); // pvp����� ������
					break;
				case 7:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(10);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(20);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 5);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(6);
					os.writeC(93); //er +4 
					os.writeC(6);
					os.writeC(89); //Me+16353
					os.writeD(6);
					os.writeC(60);
					os.writeC(6); // pvp����� ������
					break;
				case 8:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(14);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(24);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 5);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(8);
					os.writeC(93); //er +4 
					os.writeC(8);
					os.writeC(89); //Me+16353
					os.writeD(8);
					os.writeC(60);
					os.writeC(8); // pvp����� ������
					break;
				}
			}
			/** ��ɲ��� �Ҵ�Ʈ(�ູ) **/
			if (itemId == 30038) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(3);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(3);
					break;
				case 2:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(4);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(4);
					break;
				case 3:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(5);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(5);
					os.writeC(36);// ����ġ1����
					os.writeC(4);
					break;
				case 4:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(6);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(6);
					os.writeC(36);// ����ġ1����
					os.writeC(8);
					break;
				case 5:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(7);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(7);
					os.writeC(36);// ����ġ1����
					os.writeC(12);
					break;
				case 6:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(8);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(8);
					os.writeC(36);// ����ġ1����
					os.writeC(16);
					break;
				case 7:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(10);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(10);
					os.writeC(36);// ����ġ1����
					os.writeC(20);
					break;
				case 8:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(14);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(14);
					os.writeC(36);// ����ġ1����
					os.writeC(24);
					break;
				}
			}
			/** �巡�� �����̾� ����  **/
			if (itemId == 5000003) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(60);
					os.writeC(2); //PVP ����� ������ +2
					break;
				case 1:
					os.writeC(60);
					os.writeC(2); //PVP ����� ������ +2
					break;
				case 2:
					os.writeC(60);
					os.writeC(2); //PVP ����� ������ +2
					break;
				case 3:
					os.writeC(60);
					os.writeC(2); //PVP ����� ������ +2
					break;
				case 4:
					os.writeC(60);
					os.writeC(2); //PVP ����� ������ +2
					break;
				case 5:
					os.writeC(60);
					os.writeC(3); //PVP ����� ������ +3
					break;
				case 6:
					os.writeC(60);
					os.writeC(4); //PVP ����� ������ +4
					break;
				case 7:
					os.writeC(60);
					os.writeC(5); //PVP ����� ������ +5
					break;
				case 8:
					os.writeC(60);
					os.writeC(6); //PVP ����� ������ +6
					break;
				case 9:
					os.writeC(60);
					os.writeC(7); //PVP ����� ������ +7
					break;
				case 10:
					os.writeC(60);
					os.writeC(8); //PVP ����� ������ +8
					break;
				}
			}
			/** ���ְ��� �߰�, MP +70, �ٰŸ� ���� +2, ��æƮ +5���� �ִ� HP +20/+40/+60/+80/+100/+120 ����, PVP ����� ���� ǥ��  **/ //by.���ۻ�̳�
			if (itemId == 5000004) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;					
				case 5:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(20); //HP +20
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(1);  //PVP ����� ������ +1
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 6:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(40); //HP +40
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(2);  //PVP ����� ������ +2
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 7:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(60); //HP +60
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(3);  //PVP ����� ������ +3
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 8:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(80); //HP +80
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(4);  //PVP ����� ������ +4
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 9:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(60);  //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(5);   //PVP ����� ������ +5
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 10:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(120); //HP120
					os.writeC(60);  //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(6);   //PVP ����� ������ +6
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				}
			}	
			/** �븶������ �߰�, MP +70, ��æƮ +5���� �ִ� HP +20/+40/+60/+80/+100/+120 ����, PVP����� ���� ǥ��  **/ //by.���ۻ�̳�
			if (itemId == 5000005) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 1:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 2:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 3:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 4:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 5:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(20); //HP +20
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(1);  //PVP ����� ������ +1
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 6:
					os.writeC(14); //HPǥ�� �ڵ�
					os.writeH(40); //HP +40
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(2);  //PVP ����� ������ +2
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 7:
					os.writeC(14); //HPǥ�� �ڵ�
					os.writeH(60); //HP +60
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(3);  //PVP ����� ������ +3
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 8:
					os.writeC(14); //HPǥ�� �ڵ�
					os.writeH(80); //HP +80
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(4);  //PVP ����� ������ +4
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 9:
					os.writeC(14);  //HPǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(60);  //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(5);   //PVP ����� ������ +5
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 10:
					os.writeC(14);  //HPǥ�� �ڵ�
					os.writeH(120); //HP +120
					os.writeC(60);  //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(6);   //PVP ����� ������ +6
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				}
			}		
			/** �븶������ �߰�, ���� ���� ǥ��  **/ //by.���ۻ�̳�
			if (itemId == 5000005) {
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(2);  //���� ���� +2				
			
		}
			/** �������� �߰�, MP +70, ��æƮ +5���� �ִ� HP +20/+40/+60/+80/+100/+120 ����, PVP����� ���� ǥ��  **/ //by.���ۻ�̳�
			if (itemId == 5000006) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 1:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 2:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 3:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 4:
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 5:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(20); //HP +20
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(1);  //PVP ����� ������ +1
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 6:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(40); //HP +40
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(2);  //PVP ����� ������ +2
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 7:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(60); //HP +60
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(3);  //PVP ����� ������ +3
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 8:
					os.writeC(14); //HP ǥ�� �ڵ�
					os.writeH(80); //HP +80
					os.writeC(60); //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(4);  //PVP ����� ������ +4
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 9:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(60);  //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(5);   //PVP ����� ������ +5
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 10:
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(120); //HP +120
					os.writeC(60);  //PVP ����� ������ ǥ�� �ڵ�
					os.writeC(6);   //PVP ����� ������ +6
					os.writeC(32);  //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				}
			}
			         
			/** ����� �Ҵ�Ʈ **/
			if (itemId == 30034) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(1);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(1);
					break;
				case 1:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(2);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(2);
					break;
				case 2:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(3);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(3);
					break;
				case 3:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(5);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(5);
					break;
				case 4:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(7);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(7);
					os.writeC(35);//���Ÿ������+1
					os.writeC(1);
					break;
				case 5:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(9);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(9);
					os.writeC(35);//���Ÿ������+1
					os.writeC(2);
					break;
				case 6:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(11);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(11);
					os.writeC(35);//���Ÿ������+1
					os.writeC(3);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(2);
					os.writeC(93); 
					os.writeC(2);
					os.writeC(89); //Me+16353
					os.writeD(2);
					os.writeC(60);
					os.writeC(2); // pvp����� ������
					break;
				case 7:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(13);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(13);
					os.writeC(35);//���Ÿ������+1
					os.writeC(4);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(4);
					os.writeC(93); //er +4 
					os.writeC(4);
					os.writeC(89); //Me+16353
					os.writeD(4);
					os.writeC(60);
					os.writeC(4); // pvp����� ������
					break;
				case 8:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(15);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(15);
					os.writeC(35);//���Ÿ������+1
					os.writeC(5);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(6);
					os.writeC(93); //er +4 
					os.writeC(6);
					os.writeC(89); //Me+16353
					os.writeD(6);
					os.writeC(60);
					os.writeC(6); // pvp����� ������
					break;
				}
			}
			/** ������ �Ҵ�Ʈ **/
			if (itemId == 30037) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(1);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(1);
					break;
				case 1:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(2);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(4);
					break;
				case 2:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(3);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(6);
					break;
				case 3:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(4);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(8);
					break;
				case 4:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(5);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(10);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 1);
					break;
				case 5:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(6);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(12);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 2);
					break;
				case 6:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(7);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(14);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(2);
					os.writeC(93); 
					os.writeC(2);
					os.writeC(89); //Me+16353
					os.writeD(2);
					os.writeC(60);
					os.writeC(2); // pvp����� ������
					break;
				case 7:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(8);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(17);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 4);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(4);
					os.writeC(93); //er +4 
					os.writeC(4);
					os.writeC(89); //Me+16353
					os.writeD(4);
					os.writeC(60);
					os.writeC(4); // pvp����� ������
					break;
				case 8:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(10);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(20);
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 5);
					os.writeC(51); //ȸ��:DG+1
					os.writeC(6);
					os.writeC(93); //er +4 
					os.writeC(6);
					os.writeC(89); //Me+16353
					os.writeD(6);
					os.writeC(60);
					os.writeC(6); // pvp����� ������
					break;
				}
			}
			/** ��ɲ��� �Ҵ�Ʈ **/
			if (itemId == 30035) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(1);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(1);
					break;
				case 1:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(2);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(2);
					break;
				case 2:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(3);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(3);
					break;
				case 3:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(4);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(4);
					break;
				case 4:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(5);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(5);
					os.writeC(36);// ����ġ1����
					os.writeC(4);
					break;
				case 5:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(6);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(6);
					os.writeC(36);// ����ġ1����
					os.writeC(8);
					break;
				case 6:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(7);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(7);
					os.writeC(36);// ����ġ1����
					os.writeC(12);
					break;
				case 7:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(8);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(8);
					os.writeC(36);// ����ġ1����
					os.writeC(16);
					break;
				case 8:
					os.writeC(149);//�ִ�HP+1%
					os.writeC(10);
					os.writeC(150);//�ִ�MP+1%
					os.writeC(10);
					os.writeC(36);// ����ġ1����
					os.writeC(20);
					break;
				}
			}
			/** �Ϸ� ������ ���� **/
			if (itemId == 30007) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(36);// ����ġ1����
					os.writeC(1);
					break;
				case 1:
					os.writeC(36);// ����ġ1����
					os.writeC(2);
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(36);// ����ġ1����
					os.writeC(3);
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(36);// ����ġ1����
					os.writeC(4);
					os.writeC(65); // ����ȸ����2����
					os.writeC(6);
					os.writeC(6);
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(36);// ����ġ1����
					os.writeC(5);
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(5);
					os.writeC(getItem().getHitup() + 1);
					os.writeC(36);// ����ġ1����
					os.writeC(6);
					os.writeC(65); // ����ȸ����2����
					os.writeC(9);
					os.writeC(9);
					os.writeC(96);
					os.writeC(9); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 1);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 1);
					os.writeC(36);// ����ġ1����
					os.writeC(7);
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 2);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 2);
					os.writeC(36);// ����ġ1����
					os.writeC(9);
					os.writeC(65); // ����ȸ����2����
					os.writeC(11);
					os.writeC(11);
					os.writeC(96);
					os.writeC(11); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 3);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 3);
					os.writeC(36);// ����ġ1����
					os.writeC(11);
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 4);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 4);
					os.writeC(36);// ����ġ1����
					os.writeC(13);
					os.writeC(65); // ����ȸ����2����
					os.writeC(13);
					os.writeC(13);
					os.writeC(96);
					os.writeC(13); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(6);
					os.writeC(getItem().getDmgup() + 5);
					os.writeC(5);
					os.writeC(getItem().getHitup() + 5);
					os.writeC(36);// ����ġ1����
					os.writeC(15);
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}

			/** ���丮���� ����, ���� ġ��Ÿ ǥ�� �κ� **/ // by.���ۻ�̳�
			if (itemId == 30081) {
				int ����ġ��Ÿ = getEnchantLevel() + 1;
				os.writeC(50); os.writeH(getItem().get_magic_critical() + ����ġ��Ÿ);
			}
			/** �����Ű��ũ, ����ġ��Ÿ ǥ�� �κ� **/ // by.���ۻ�̳�
			else if (itemId == 7238) {
				if (getEnchantLevel() <= 6) {
					os.writeC(50); os.writeH(getItem().get_magic_critical() + 1);
				} else if (getEnchantLevel() >= 7) {
					os.writeC(50); os.writeH(getItem().get_magic_critical() + getEnchantLevel() - 5);
				}
			}
			/** ��ġ�� ����, ���� ġ��Ÿ **/ // by.���ۻ�̳�
			else if (itemId == 10172){
				os.writeC(50); os.writeH(getItem().get_magic_critical() + 3);
			}
			/** ���Ľ��� ����, ���� ġ��Ÿ ǥ�� �κ� **/ // by.���ۻ�̳�
			else if (itemId == 5000002){
				os.writeC(50); os.writeH(getItem().get_magic_critical() + 5);
			}
			/** �߶�ī���� ����, ����ġ��Ÿ ǥ�� �κ� **/ // by.���ۻ�̳�
			else if (itemId == 420114) {
				if (getEnchantLevel() <= 6) {
					os.writeC(50); os.writeH(getItem().get_magic_critical() + 2);
				} else if (getEnchantLevel() >= 7) {
					os.writeC(50); os.writeH(getItem().get_magic_critical() + getEnchantLevel() - 4);
				}
			}
			/** ��ø ������ ���� **/
			if (itemId == 30008) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(36);// ����ġ1����
					os.writeC(1);
					break;
				case 1:
					os.writeC(36);// ����ġ1����
					os.writeC(2);
					os.writeC(39);
					os.writeS("���� ȸ���� 2% +2 %");
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(36);// ����ġ1����
					os.writeC(3);
					os.writeC(39);
					os.writeS("���� ȸ���� 4% +4 %");
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(36);// ����ġ1����
					os.writeC(4);
					os.writeC(39);
					os.writeS("���� ȸ���� 6% +6 %");
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(36);// ����ġ1����
					os.writeC(5);
					os.writeC(39);
					os.writeS("���� ȸ���� 8% +8 %");
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 1);
					os.writeC(36);// ����ġ1����
					os.writeC(6);
					os.writeC(39);
					os.writeS("���� ȸ���� 9% +9 %");
					os.writeC(96);
					os.writeC(9); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 1);
					os.writeC(36);// ����ġ1����
					os.writeC(7);
					os.writeC(39);
					os.writeS("���� ȸ���� 10% +10 %");
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 2);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 2);
					os.writeC(36);// ����ġ1����
					os.writeC(9);
					os.writeC(39);
					os.writeS("���� ȸ���� 11% +11 %");
					os.writeC(96);
					os.writeC(11); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 3);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 3);
					os.writeC(36);// ����ġ1����
					os.writeC(11);
					os.writeC(39);
					os.writeS("���� ȸ���� 12% +12 %");
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 4);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 4);
					os.writeC(36);// ����ġ1����
					os.writeC(13);
					os.writeC(39);
					os.writeS("���� ȸ���� 13% +13 %");
					os.writeC(96);
					os.writeC(13); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 5);
					os.writeC(24);
					os.writeC(getItem().getBowHitup() + 5);
					os.writeC(36);// ����ġ1����
					os.writeC(15);
					os.writeC(39);
					os.writeS("���� ȸ���� 14% +14 %");
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			
			/** ���� ������ ���� **/
			if (itemId == 30009) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(36);// ����ġ1����
					os.writeC(1);
					break;
				case 1:
					os.writeC(36);// ����ġ1����
					os.writeC(2);
					os.writeC(39);
					os.writeS("���� ȸ���� 2% +2 %");
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(36);// ����ġ1����
					os.writeC(3);
					os.writeC(39);
					os.writeS("���� ȸ���� 4% +4 %");
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(36);// ����ġ1����
					os.writeC(4);
					os.writeC(39);
					os.writeS("���� ȸ���� 6% +6 %");
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(36);// ����ġ1����
					os.writeC(5);
					os.writeC(39);
					os.writeS("���� ȸ���� 8% +8 %");
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(36);// ����ġ1����
					os.writeC(6);
					os.writeC(39);
					os.writeS("���� ȸ���� 9% +9 %");
					os.writeC(96);
					os.writeC(9); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 1);
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(36);// ����ġ1����
					os.writeC(7);
					os.writeC(39);
					os.writeS("���� ȸ���� 10% +10 %");
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 2);
					os.writeC(40);
					os.writeC(2); // ����1
					os.writeC(36);// ����ġ1����
					os.writeC(9);
					os.writeC(39);
					os.writeS("���� ȸ���� 11% +11 %");
					os.writeC(96);
					os.writeC(11); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					os.writeC(40);
					os.writeC(3); // ����1
					os.writeC(36);// ����ġ1����
					os.writeC(11);
					os.writeC(39);
					os.writeS("���� ȸ���� 12% +12 %");
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 4);
					os.writeC(40);
					os.writeC(4); // ����1
					os.writeC(36);// ����ġ1����
					os.writeC(13);
					os.writeC(39);
					os.writeS("���� ȸ���� 13% +13 %");
					os.writeC(96);
					os.writeC(13); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 5);
					os.writeC(40);
					os.writeC(5); // ����1
					os.writeC(36);// ����ġ1����
					os.writeC(15);
					os.writeC(39);
					os.writeS("���� ȸ���� 14% +14 %");
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}

			/** ������ ���� **/
			if (itemId == 490020) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(36);// ����ġ1����
					os.writeC(1);
					break;
				case 1:
					os.writeC(36);// ����ġ1����
					os.writeC(2);
					os.writeC(39);
					os.writeS("���� ȸ���� 2% +2 %");
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(36);// ����ġ1����
					os.writeC(3);
					os.writeC(39);
					os.writeS("���� ȸ���� 4% +4 %");
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(36);// ����ġ1����
					os.writeC(4);
					os.writeC(39);
					os.writeS("���� ȸ���� 6% +6 %");
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(36);// ����ġ1����
					os.writeC(5);
					os.writeC(39);
					os.writeS("���� ȸ���� 8% +8 %");
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(36);// ����ġ1����
					os.writeC(6);
					os.writeC(39);
					os.writeS("���� ȸ���� 9% +9 %");
					os.writeC(96);
					os.writeC(9); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(36);// ����ġ1����
					os.writeC(7);
					os.writeC(39);
					os.writeS("���� ȸ���� 10% +10 %");
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(36);// ����ġ1����
					os.writeC(9);
					os.writeC(39);
					os.writeS("���� ȸ���� 11% +11 %");
					os.writeC(96);
					os.writeC(11); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(36);// ����ġ1����
					os.writeC(11);
					os.writeC(39);
					os.writeS("���� ȸ���� 12% +12 %");
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(36);// ����ġ1����
					os.writeC(13);
					os.writeC(39);
					os.writeS("���� ȸ���� 13% +13 %");
					os.writeC(96);
					os.writeC(13); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(36);// ����ġ1����
					os.writeC(15);
					os.writeC(39);
					os.writeS("���� ȸ���� 14% +14 %");
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** ��ȣ�� ���� **/
			if (itemId == 30022) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(36);// ����ġ1����
					os.writeC(1);
					break;
				case 2:
					os.writeC(36);// ����ġ1����
					os.writeC(2);
					break;
				case 3:
					os.writeC(36);// ����ġ1����
					os.writeC(3);
					break;
				case 4:
					os.writeC(36);// ����ġ1����
					os.writeC(4);
					os.writeC(15);
					os.writeH(getMr() + 1);
					break;
				case 5:
					os.writeC(36);// ����ġ1����
					os.writeC(5);
					os.writeC(15);
					os.writeH(getMr() + 2);
					break;
				case 6:
					os.writeC(36);// ����ġ1����
					os.writeC(6);
					os.writeC(15);
					os.writeH(getMr() + 3);
					break;
				case 7:
					os.writeC(36);// ����ġ1����
					os.writeC(7);
					os.writeC(15);
					os.writeH(getMr() + 4);
					break;
				case 8:
					os.writeC(36);// ����ġ1����
					os.writeC(8);
					os.writeC(15);
					os.writeH(getMr() + 5);
					break;
				}
			}
			/** ������ ���� **/
			if (itemId == 30021) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(17); // sp
					os.writeC(1);
					break;
				case 6:
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					os.writeC(40);
					os.writeC(2); // ����1
					os.writeC(17); // sp
					os.writeC(2);
					break;
				case 7:
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					os.writeC(40);
					os.writeC(3); // ����1
					os.writeC(17); // sp
					os.writeC(3);
					break;
				case 8:
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					os.writeC(40);
					os.writeC(4); // ����1
					os.writeC(17); // sp
					os.writeC(4);
					break;
				}
			}
			/** ����� ���� **/
			if (itemId == 30020) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					os.writeC(24);
					os.writeC(1);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					break;
				case 6:
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					os.writeC(24);
					os.writeC(2);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					break;
				case 7:
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					os.writeC(24);
					os.writeC(3);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(3);
					break;
				case 8:
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					os.writeC(24);
					os.writeC(4);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(4);
					break;
				}
			}
			/** ������ ���� **/
			if (itemId == 30019) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					os.writeC(5); // �ٰŸ� ����
					os.writeC(1);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					break;
				case 6:
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					os.writeC(5); // �ٰŸ� ����
					os.writeC(2);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(2);
					break;
				case 7:
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					os.writeC(5); // �ٰŸ� ����
					os.writeC(3);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(3);
					break;
				case 8:
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					os.writeC(5); // �ٰŸ� ����
					os.writeC(4);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(4);
					break;
				}
			}
			/** ��ȣ�� ���� ���� **/
			if (itemId == 30025) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					os.writeC(40);
					os.writeC(2); // ����1
					os.writeC(17); // sp
					os.writeC(1);
					os.writeC(15); // mr����
					os.writeH(getMr() + 4);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(5);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(1);
					break;
				case 6:
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					os.writeC(40);
					os.writeC(3); // ����1
					os.writeC(17); // sp
					os.writeC(2);
					os.writeC(15); // mr����
					os.writeH(getMr() + 6);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(10);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(2);
					break;
				case 7:
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					os.writeC(40);
					os.writeC(4); // ����1
					os.writeC(17); // sp
					os.writeC(3);
					os.writeC(15); // mr����
					os.writeH(getMr() + 8);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(15);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(3);
					break;
				case 8:
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					os.writeC(40);
					os.writeC(5); // ����1
					os.writeC(17); // sp
					os.writeC(4);
					os.writeC(15); // mr����
					os.writeH(getMr() + 10);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(20);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(5);
					break;
				}
			}
			/** ��ȣ�� ��� ���� **/
			if (itemId == 30024) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					os.writeC(24);
					os.writeC(2);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					os.writeC(15); // mr����
					os.writeH(getMr() + 4);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(5);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(1);
					break;
				case 6:
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					os.writeC(24);
					os.writeC(3);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					os.writeC(15); // mr����
					os.writeH(getMr() + 6);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(10);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(2);
					break;
				case 7:
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					os.writeC(24);
					os.writeC(4);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(3);
					os.writeC(15); // mr����
					os.writeH(getMr() + 8);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(15);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(3);
					break;
				case 8:
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					os.writeC(24);
					os.writeC(5);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(4);
					os.writeC(15); // mr����
					os.writeH(getMr() + 10);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(20);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(5);
					break;
				}
			}
			/** ��ȣ�� ���� ���� **/
			if (itemId == 30023) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					os.writeC(5); // �ٰŸ� ����
					os.writeC(2);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(1);
					os.writeC(15); // mr����
					os.writeH(getMr() + 4);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(5);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(1);
					break;
				case 6:
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					os.writeC(5); // �ٰŸ� ����
					os.writeC(3);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(2);
					os.writeC(15); // mr����
					os.writeH(getMr() + 6);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(10);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(2);
					break;
				case 7:
					os.writeC(65); // ����ȸ����2����
					os.writeC(16);
					os.writeC(16);
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					os.writeC(5); // �ٰŸ� ����
					os.writeC(4);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(3);
					os.writeC(15); // mr����
					os.writeH(getMr() + 8);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(15);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(3);
					break;
				case 8:
					os.writeC(65); // ����ȸ����2����
					os.writeC(18);
					os.writeC(18);
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					os.writeC(5); // �ٰŸ� ����
					os.writeC(5);
					os.writeC(47); // �ٰŸ� �����
					os.writeC(4);
					os.writeC(15); // mr����
					os.writeH(getMr() + 10);
					os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
					os.writeH(20);
					os.writeC(135); //���� �Ӽ������ +1
					os.writeC(5);
					break;
				}
			}
			/** �Ϸ��� ���� **/
			if (itemId == 490023) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(6);
					os.writeC(6);
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(9);
					os.writeC(9);
					os.writeC(96);
					os.writeC(9); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(1);
					os.writeC(6); // �ٰŸ� �����
					os.writeC(1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(2);
					os.writeC(6); // �ٰŸ� �����
					os.writeC(2);
					os.writeC(65); // ����ȸ����2����
					os.writeC(11);
					os.writeC(11);
					os.writeC(96);
					os.writeC(11); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(3);
					os.writeC(6); // �ٰŸ� �����
					os.writeC(3);
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(4);
					os.writeC(6); // �ٰŸ� �����
					os.writeC(4);
					os.writeC(65); // ����ȸ����2����
					os.writeC(13);
					os.writeC(13);
					os.writeC(96);
					os.writeC(13); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(5);
					os.writeC(6); // �ٰŸ� �����
					os.writeC(5);
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** ��ø�� ���� **/
			if (itemId == 490024) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(65); // ����ȸ����2����
					os.writeC(2);
					os.writeC(2);
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(65); // ����ȸ����2����
					os.writeC(4);
					os.writeC(4);
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(65); // ����ȸ����2����
					os.writeC(6);
					os.writeC(6);
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(65); // ����ȸ����2����
					os.writeC(8);
					os.writeC(8);
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(24);
					os.writeC(1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(9);
					os.writeC(9);
					os.writeC(96);
					os.writeC(9); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(24);
					os.writeC(1);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(1);
					os.writeC(65); // ����ȸ����2����
					os.writeC(10);
					os.writeC(10);
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(24);// ���Ÿ�����
					os.writeC(2);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(2);
					os.writeC(65); // ����ȸ����2����
					os.writeC(11);
					os.writeC(11);
					os.writeC(96);
					os.writeC(11); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(24);// ���Ÿ�����
					os.writeC(3);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(3);
					os.writeC(65); // ����ȸ����2����
					os.writeC(12);
					os.writeC(12);
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(24);// ���Ÿ�����
					os.writeC(4);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(4);
					os.writeC(65); // ����ȸ����2����
					os.writeC(13);
					os.writeC(13);
					os.writeC(96);
					os.writeC(13); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(24);// ���Ÿ�����
					os.writeC(5);
					os.writeC(35); // ���Ÿ� �����
					os.writeC(5);
					os.writeC(65); // ����ȸ����2����
					os.writeC(14);
					os.writeC(14);
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** ������ ���� **/
			if (itemId == 490025) {
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(39);
					os.writeS("���� ȸ���� 2% +2 %");
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(39);
					os.writeS("���� ȸ���� 4% +4 %");
					os.writeC(96);
					os.writeC(4); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(39);
					os.writeS("���� ȸ���� 6% +6 %");
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(39);
					os.writeS("���� ȸ���� 8% +8 %");
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(39);
					os.writeS("���� ȸ���� 9% +9 %");
					os.writeC(96);
					os.writeC(9); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(17); // sp
					os.writeC(1);
					os.writeC(39);
					os.writeS("���� ȸ���� 10% +10 %");
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(40);
					os.writeC(2); // ����1
					os.writeC(17); // sp
					os.writeC(2);
					os.writeC(39);
					os.writeS("���� ȸ���� 11% +11 %");
					os.writeC(96);
					os.writeC(11); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(40);
					os.writeC(3); // ����1
					os.writeC(17); // sp
					os.writeC(3);
					os.writeC(39);
					os.writeS("���� ȸ���� 12% +12 %");
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(40);
					os.writeC(4); // ����1
					os.writeC(17); // sp
					os.writeC(4);
					os.writeC(39);
					os.writeS("���� ȸ���� 13% +13 %");
					os.writeC(96);
					os.writeC(13); // ȸ�� ��ȭ ��� 1����
					break;
				case 10:
					os.writeC(40);
					os.writeC(5); // ����1
					os.writeC(17); // sp
					os.writeC(5);
					os.writeC(39);
					os.writeS("���� ȸ���� 14% +14 %");
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				}
			}
			/** �巡�� �����̾� **/
			if (itemId == 100034) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(39);
					os.writeS("���� ����+5");
					break;
				case 1:
					os.writeC(39);
					os.writeS("���� ����+6");
					break;
				case 2:
					os.writeC(39);
					os.writeS("���� ����+7");
					break;
				case 3:
					os.writeC(39);
					os.writeS("���� ����+8");
					break;
				case 4:
					os.writeC(39);
					os.writeS("���� ����+9");
					break;
				case 5:
					os.writeC(39);
					os.writeS("���� ����+10");
					break;
				case 6:
					os.writeC(39);
					os.writeS("���� ����+11");
					break;
				case 7:
					os.writeC(39);
					os.writeS("���� ����+12");
					break;
				case 8:
					os.writeC(39);
					os.writeS("���� ����+13");
					break;
				case 9:
					os.writeC(39);
					os.writeS("���� ����+14");
					break;
				case 10:
					os.writeC(39);
					os.writeS("���� ����+15");
					os.writeC(39);
					os.writeS("�巡���� �ϰ�");
					break;
				}
			}
			/*
			 * if(itemId == 20010 || itemId == 20100 || itemId == 20166 || itemId == 20198)
			 * { //������ os.writeC(39); os.writeS(
			 * "��Ʈ ���� ���ʽ�:AC-10,STR+2,�ٰŸ� �����+2,���� :������������Ʈ");
			 * 
			 * }
			 * 
			 * if(itemId == 20041 || itemId == 20150 || itemId == 20184 || itemId == 20214)
			 * { //Ŀ���� os.writeC(39); os.writeS(
			 * "��Ʈ ���� ���ʽ�:AC-10,CON+2,�ִ� HP+100,����� ����+2,���� : Ŀ��");
			 * 
			 * }
			 */
			/** ������� �߰� �ٰŸ� ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 230000) { 
				os.writeC(5); //�ٰŸ� ���� ǥ�� �ڵ�
				os.writeC(1); //�ٰŸ� ���� +1
			}
			/** �������� �߰� ���� ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 230002) { 
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1
			}
			/** �ż��� ������ ����� ���� ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 222348) {
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(2);  //���� ���� +2
			}
			/** �������� �� �ٰŸ� ġ��Ÿ ǥ�� **/ //.by���ۻ�̳�
			if (itemId == 100033) { 
				os.writeC(100); //�ٰŸ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(10);  //�ٰŸ� ġ��Ÿ +10

			}
			
			if (getTechniqueTolerance() != 0) {
				os.writeC(117);
				os.writeC(getTechniqueTolerance());
			}

			if (getSpiritTolerance() != 0) {
				os.writeC(118);
				os.writeC(getSpiritTolerance());
			}

			if (getDragonLangTolerance() != 0) {
				os.writeC(119);
				os.writeC(getDragonLangTolerance());
			}

			if (getFearTolerance() != 0) {
				os.writeC(120);
				os.writeC(getFearTolerance());
			}

			if (getAllTolerance() != 0) {
				os.writeC(121);
				os.writeC(getAllTolerance());
			}

			if (getTechniqueHit() != 0) {
				os.writeC(122);
				os.writeC(getTechniqueHit());
			}

			if (getSpiritHit() != 0) {
				os.writeC(123);
				os.writeC(getSpiritHit());
			}

			if (getDragonLangHit() != 0) {
				os.writeC(124);
				os.writeC(getDragonLangHit());
			}

			if (getFearHit() != 0) {
				os.writeC(125);
				os.writeC(getFearHit());
			}

			if (getAllHit() != 0) {
				os.writeC(126);
				os.writeC(getAllHit());
			}
			/** �巡�� �����̾� ����  ����� ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 5000003) { 
				os.writeC(63); // ����� ���� ǥ�� �ڵ�
				os.writeC(1);  //����� ���� +1
			}
			/** ���̾��� �ݳ�  ����� ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 30082) { 
				os.writeC(63); //����� ���� ǥ�� �ڵ�
				os.writeC(2);  //����� ���� +2
			}
			/** ���̾��� �ݳ�  ���Ÿ� ġ��Ÿ, ����� ������ ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 30082) { 
			switch (getEnchantLevel()) {
			case 0:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(1);  //���Ÿ� ġ��Ÿ +1
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(9);  //����� ������ ���� +9
				break;
			case 1:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(2);  //���Ÿ� ġ��Ÿ +2
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(10); //����� ������ ���� +10
				break;
			case 2:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ� 
				os.writeC(3);  //���Ÿ� ġ��Ÿ +3
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(11); //����� ������ ���� +11
				break;
			case 3:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ� 
				os.writeC(4);  //���Ÿ� ġ��Ÿ +4
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(12); //����� ������ ���� +12
				break;
			case 4:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(5);  //���Ÿ� ġ��Ÿ +5
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(13); //����� ������ ���� +13
				break;
			case 5:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(6);  //���Ÿ� ġ��Ÿ +6
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(14); //����� ������ ���� +14
				break;
			case 6:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(7);  //���Ÿ� ġ��Ÿ +7
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(15); //����� ������ ���� +15
				break;
			case 7:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(8);  //���Ÿ� ġ��Ÿ +8
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(16); //����� ������ ���� +16
				break;
			case 8:
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(9);  //���Ÿ� ġ��Ÿ +9
				os.writeC(97); //����� ������ ���� ǥ�� �ڵ�
				os.writeC(17); //����� ������ ���� +17
				break;
			case 9:
				os.writeC(99);  //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(10);  //���Ÿ� ġ��Ÿ +10
				os.writeC(97);  //����� ������ ���� ǥ�� �ڵ�
				os.writeC(18);  //����� ������ ���� +18
				break;
			default:
				break;
				
			}
		}
			/** ��Ǫ������ ��� ������ ������ ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 30121) { 
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(2);  //����� ������ ���� +2
					break;
				case 1:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(2);  //����� ������ ���� +2
					break;
				case 2:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(2);  //����� ������ ���� +2
					break;
				case 3:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(2);  //����� ������ ���� +2
					break;
				case 4:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(2);  //����� ������ ���� +2
					break;
				case 5:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(2);  //����� ������ ���� +2
					break;
				case 6:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(2);  //����� ������ ���� +2
					break;
				case 7:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(2);  //����� ������ ���� +2
					break;
				case 8: 
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(3);  //����� ������ ���� +3
					break;
				case 9:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(4);  //����� ������ ���� +4
					break;
				case 10:
					os.writeC(97); //����� ������ ǥ�� �ڵ�
					os.writeC(5);  //����� ������ ���� +5
					break;					
				default:
					break;
				}
			}
			/** �ı��� ��� ���Ÿ� ġ��Ÿ ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 30220) {
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(2);  //���Ÿ� ġ��Ÿ +2
			}
			/** �Ǹ����� Ȱ ���Ÿ� ġ��Ÿ ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 413105) {
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(1);  //���Ÿ� ġ��Ÿ +1
			}
			/** �Ϲ� & �ູ ��ձ�,�Ǹ��� ��� ���Ÿ� ġ��Ÿ ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 189 || itemId == 100189 || itemId == 293) { 
				os.writeC(99); //���Ÿ� ġ��Ÿ ǥ�� �ڵ�
				os.writeC(3);  //���Ÿ� ġ��Ÿ +3
			}
			/** ���� ����ü ������ ���� ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 134) { 
			switch (getEnchantLevel()) {
			case 0:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(2);  //���� ���� +2
				break;
			case 1:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(3);  //���� ���� +3
				break;
			case 2:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(4);  //���� ���� +4
				break;
			case 3:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(5);  //���� ���� +5
				break;
			case 4:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(6);  //���� ���� +6
				break;
			case 5:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(7);  //���� ���� +7
				break;
			case 6:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(8);  //���� ���� +8
				break;
			case 7:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(9);  //���� ���� +9
				break;
			case 8:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(10); //���� ���� +10
				break;
			case 9:
				os.writeC(40); //�������� ǥ�� �ڵ�
				os.writeC(11); //���� ���� +11
				break;
			default:
				break;
				
			}
		}
			/** ��Ÿ���� ������ ���� ǥ��  **/ //.by���ۻ�̳�
			if (itemId == 30116) { 
			switch (getEnchantLevel()) {
			case 0:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1
				break;
			case 1:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1
				break;
			case 2:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1
				break;
			case 3:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1
				break;
			case 4:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1
				break;
			case 5:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1
				break;
			case 6:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1 
				break;
			case 7:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(1);  //���� ���� +1 
				break;
			case 8:
				os.writeC(40); //���� ���� ǥ�� �ڵ�
				os.writeC(2);  //���� ���� +2
				break;
			case 9:
				os.writeC(40);  //���� ���� ǥ�� �ڵ�
				os.writeC(3);   //���� ���� +3
				break;
			case 10:
				os.writeC(40);  //���� ���� ǥ�� �ڵ�
				os.writeC(4);   //���� ���� +4
				break;
			default:
				break;
				
			}
		}
			/** ��Ƽ���� Ǫ���� �Ͱ��� ����ȿ�� ǥ�� **/
			if (itemType2 == 2 && itemId == 500008 && getEnchantLevel() >= 0) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 1:
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(96);
					os.writeC(20); // ȸ�� ��ȭ ��� 1����
					break;
				default:
					break;
				}
			}
			
			 /*if (getItemId() == 61) {
					os.writeC(107);
					int dmg = 0;
					if (getEnchantLevel() == 1)
						dmg = 3;
					else if (getEnchantLevel() == 2)
						dmg = 5;
					else if (getEnchantLevel() == 3)
						dmg = 7;
					else if (getEnchantLevel() == 4)
						dmg = 10;
					os.writeC(dmg);
				}*/

			/** �ູ���� ��Ƽ���� Ǫ���� �Ͱ��� ����ȿ�� ǥ�� **/
			if (itemType2 == 2 && itemId == 502008 && getEnchantLevel() >= 0) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(96);
					os.writeC(2); // ȸ�� ��ȭ ��� 1����
					break;
				case 1:
					os.writeC(96);
					os.writeC(6); // ȸ�� ��ȭ ��� 1����
					break;
				case 2:
					os.writeC(96);
					os.writeC(8); // ȸ�� ��ȭ ��� 1����
					break;
				case 3:
					os.writeC(96);
					os.writeC(12); // ȸ�� ��ȭ ��� 1����
					break;
				case 4:
					os.writeC(96);
					os.writeC(14); // ȸ�� ��ȭ ��� 1����
					break;
				case 5:
					os.writeC(96);
					os.writeC(16); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(96);
					os.writeC(18); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(96);
					os.writeC(20); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(96);
					os.writeC(22); // ȸ�� ��ȭ ��� 1����
					break;
				default:
					break;
				}
			}
			/** ��Ƽ�� ����ȸ��ǥ�� **/
			/*
			 * if (itemId == 500008 && getEnchantLevel() >= 0){ os.writeC(39);
			 * os.writeS(RootisHealingPotion()); }
			 */
			// exp ������� ��Ŷ��ȣ�������̶� �ϴ� ..
			if (itemId == 490020) {
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					os.writeC(39);
					os.writeS("EXP+ " + (getEnchantLevel() + 1) + "%");
					break;
				case 7:
					os.writeC(39);
					os.writeS("EXP+ 9%");
					break;
				case 8:
					os.writeC(39);
					os.writeS("EXP+ 11%");
					break;
				case 9:
					os.writeC(39);
					os.writeS("EXP+ 13%");
					break;
				case 10:
					os.writeC(39);
					os.writeS("EXP+ 15%");
					break;
				}
			}
			/** �Ϲ� ����� �Ͱ��� ��ǥ ȸ�� ���� ����ȿ��ǥ�� **/
			if (getItem().getGrade() != 3 && itemType2 == 2
					&& (getItem().getType() == 8 || getItem().getType() == 12)) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(96);
					os.writeC(3); // ȸ�� ��ȭ ��� 1����
					break;
				case 6:
					os.writeC(96);
					os.writeC(5); // ȸ�� ��ȭ ��� 1����
					break;
				case 7:
					os.writeC(96);
					os.writeC(7); // ȸ�� ��ȭ ��� 1����
					break;
				case 8:
					os.writeC(96);
					os.writeC(9); // ȸ�� ��ȭ ��� 1����
					break;
				case 9:
					os.writeC(96);
					os.writeC(10); // ȸ�� ��ȭ ��� 1����
					break;
				default:
					break;
				}
			}
			if (getItem().getGrade() != 3 && getItem().getType2() == 2
					&& (getItem().getType() == 8 || getItem().getType() == 12) && getEnchantLevel() > 0) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(65);
					os.writeC(3);
					os.writeC(0);
					break;
				case 6:
					os.writeC(65);
					os.writeC(5);
					os.writeC(3);
					break;
				case 7:
					os.writeC(65);
					os.writeC(7);
					os.writeC(5);
					break;
				case 8:
					os.writeC(65);
					os.writeC(9);
					os.writeC(7);
					break;
				case 9:
					os.writeC(65);
					os.writeC(10);
					os.writeC(8);
					break;
				default:
					break;
				}
			} else if (itemId == 500008) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(65);
					os.writeC(2);
					os.writeC(2);
					break;
				case 1:
					os.writeC(65);
					os.writeC(6);
					os.writeC(6);
					break;
				case 2:
					os.writeC(65);
					os.writeC(8);
					os.writeC(8);
					break;
				case 3:
					os.writeC(65);
					os.writeC(10);
					os.writeC(10);
					break;
				case 4:
					os.writeC(65);
					os.writeC(12);
					os.writeC(12);
					break;
				case 5:
					os.writeC(65);
					os.writeC(14);
					os.writeC(14);
					break;
				case 6:
					os.writeC(65);
					os.writeC(16);
					os.writeC(16);
					break;
				case 7:
					os.writeC(65);
					os.writeC(18);
					os.writeC(18);
					break;
				case 8:
					os.writeC(65);
					os.writeC(20);
					os.writeC(20);
					break;
				default:
					break;
				}
			} else if (itemId == 502008) {
				switch (getEnchantLevel()) {
				case 3:
					os.writeC(65);
					os.writeC(12);
					os.writeC(12);
					break;
				case 4:
					os.writeC(65);
					os.writeC(14);
					os.writeC(14);
					break;
				case 5:
					os.writeC(65);
					os.writeC(16);
					os.writeC(16);
					break;
				case 6:
					os.writeC(65);
					os.writeC(18);
					os.writeC(18);
					break;
				case 7:
					os.writeC(65);
					os.writeC(20);
					os.writeC(20);
					break;
				case 8:
					os.writeC(65);
					os.writeC(22);
					os.writeC(22);
					break;
				default:
					break;
				}
			}

			/** ��Ƽ�� ����ȸ��ǥ�� **/

			// ��ƽ��ƽ ���ڸ����� �Ʒ��� �̵�

			/** ��Ƽ�� ����������ǥ�� **/
			if (itemId == 500007 && getEnchantLevel() >= 3) {
				os.writeC(63);
				os.writeC(�ӱ͸���());
			}

			if (itemId == 502007 && getEnchantLevel() >= 3) {
				os.writeC(63);
				os.writeC(��ӱ͸���());
			}
			if (itemId == 21248 && getEnchantLevel() >= 7) { // ������ ü�� ����
				os.writeC(63);
				os.writeC(��ӱ͸���() - 4);
			}
			if (itemId == 21252 && getEnchantLevel() >= 6) { // ��������ü�� ����
				os.writeC(63);
				os.writeC(��ӱ͸���() - 3);
			}
			/*if (getItem().get_regist_dragon() != 0) {
				os.writeC(119); // ��𳻼� 1
				os.writeC(getItem().get_regist_dragon());
			}
			if (getItem().get_regist_stun() != 0) {
				os.writeC(117); // ������� 1
				os.writeC(getItem().get_regist_stun());
			}
			if (getItem().get_regist_soul() != 0) {
				os.writeC(118); // ���ɳ��� 1
				os.writeC(getItem().get_regist_soul());
			}
			if (getItem().get_regist_all() != 0) {
				os.writeC(121); // ��系�� 1
				os.writeC(getItem().get_regist_all());
			}
			if (getItem().get_regist_horror() != 0) {
				os.writeC(120); // �������� 1
				os.writeC(getItem().get_regist_horror());
			}
*/
			if (itemId == 90101) {// ���̾ƹ��� ��������
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:

					os.writeC(39);
					os.writeS("�𵥵� �߰� �����");

					break;
				}
			}
			/** 80�� ǥ�� **/
			if ((getItem().getItemId() >= 10044 && getItem().getItemId() <= 10083) || (getItem().getItemId() >= 20637 && getItem().getItemId() <= 20641)) {
				os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
				os.writeH(5);
			}
			/** 85�� ǥ�� **/
			if ((getItem().getItemId() >= 10084 && getItem().getItemId() <= 10123) || (getItem().getItemId() >= 20642 && getItem().getItemId() <= 20646)) {
				os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
				os.writeH(5);
			}
			/** 90�� ǥ�� **/
			if ((getItem().getItemId() >= 10124 && getItem().getItemId() <= 10163) || (getItem().getItemId() >= 20647 && getItem().getItemId() <= 20651)) {
				os.writeC(116); // �ູ�Ҹ�ȿ�� 1����
				os.writeH(5);
			}
			/** ��ȣ���� �Ŀ��۷κ� **/
			if (getItem().getItemId() == 222345) {// ��ȣ��
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(1);
					break;
				case 6:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(2);
					break;
				case 7:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(3);
					break;
				case 8:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(4);
					break;
				case 9:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(5);
					break;
				case 10:
					os.writeC(5); // �ٰŸ� ����
					os.writeC(5);
					break;
				default:
					break;
				}
			}
			/** ������ �������׹��� ���� ȸ�� **/
			if (getItem().getItemId() == 21247) {
				switch (getEnchantLevel()) {
				case 7:
					os.writeC(89);
					os.writeD(1); // Ȯ������ȸ��
					break;
				case 8:
					os.writeC(89);
					os.writeD(3); // Ȯ������ȸ��
					break;
				default:
					break;
				}
			}
			/** �� ������ �������׹��� ���� ȸ�� **/
			if (getItem().getItemId() == 21251) {
				switch (getEnchantLevel()) {
				case 6:
					os.writeC(89);
					os.writeD(1); // Ȯ������ȸ��
					break;
				case 7:
					os.writeC(89);
					os.writeD(3); // Ȯ������ȸ��
					break;
				case 8:
					os.writeC(89);
					os.writeD(5); // Ȯ������ȸ��
					break;
				default:
					break;
				}
			}
			/** ���� 6���� ����ǥ�� **/
			if (getItem().getGrade() != 3 && itemType2 == 2
					&& (getItem().getType() == 9 || getItem().getType() == 11)) { //
				switch (getEnchantLevel()) {
				case 6:
					os.writeC(15);
					os.writeH(getMr() + 1);
					break;
				case 7:
					os.writeC(15);
					os.writeH(getMr() + 3);
					break;
				case 8:
					os.writeC(15);
					os.writeH(getMr() + 5);
					break;
				case 9:
					os.writeC(15);
					os.writeH(getMr() + 7);
					break;
				default:
					break;
				}
			}
            /** �Ǽ��縮 Ư�� ǥ�� **/ //.by���ۻ�̳�
			if (itemType2 == 2 && getItem().getType() == 8) {// �����
				os.writeC(67); os.writeH(76); // ����
			} else if (itemType2 == 2 && getItem().getType() == 12 && getItem().getGrade() != 4) {// �Ͱ���
				os.writeC(67); os.writeH(43); // �ټ�
			} else if (itemType2 == 2 && (getItem().getType() == 9 || getItem().getType() == 11) && getItem().getGrade() != 3) {// ����
				os.writeC(67); os.writeH(44); // ���� 
			} else if (itemType2 == 2 && getItem().getType() == 10) {// ��Ʈ
				os.writeC(67); os.writeH(45); // ����
			}
			
			/** �Ϲ� ������ Ƽ���� ����� ����, MR, ����ġ ���ʽ�, HP100 ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 200851) {// ������ Ƽ����
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(63); //����� ���� ǥ�� �ڵ�
					os.writeC(1);  //����� ���� +1
					break;
				case 1:
					os.writeC(63); //����� ���� ǥ�� �ڵ�
					os.writeC(1);  //����� ���� +1
					break;
				case 2:
					os.writeC(63); //����� ���� ǥ�� �ڵ�
					os.writeC(1);  //����� ���� +1
					break;
				case 3:
					os.writeC(63); //����� ���� ǥ�� �ڵ�
					os.writeC(1);  //����� ���� +1
					break;
				case 4:
					os.writeC(63); //����� ���� ǥ�� �ڵ�
					os.writeC(1);  //����� ���� +1
					break;
				case 5:
					os.writeC(15); //MR ǥ�� �ڵ�
					os.writeH(getMr() + 4); //MR +4
					os.writeC(63); //����� ���� ǥ�� �ڵ�
					os.writeC(1);  //����� ���� +1
					break;
				case 6:
					os.writeC(15);; //MR ǥ�� �ڵ�
					os.writeH(getMr() + 5); //MR +5
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 7:
					os.writeC(15);; //MR ǥ�� �ڵ�
					os.writeH(getMr() + 6); //MR +6
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 8:
					os.writeC(15);; //MR ǥ�� �ڵ�
					os.writeH(getMr() + 8); //MR +8
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					os.writeC(36);  //����ġ �ۼ�Ʈ ǥ�� �ڵ� 
					os.writeC(2);   //����ġ �ۼ�Ʈ +2
					break;
				case 9:
					os.writeC(15);; //MR ǥ�� �ڵ�
					os.writeH(getMr() + 11); //MR +11
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(2);   //����� ���� +1
					os.writeC(36);  //����ġ �ۼ�Ʈ ǥ�� �ڵ�
					os.writeC(4);   //����ġ �ۼ�Ʈ +4
					break;
				case 10:
					os.writeC(15);; //MR ǥ�� �ڵ�
					os.writeH(getMr() + 14); //MR +14
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(2);   //����� ���� +1
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(36);  //����ġ �ۼ�Ʈ ǥ�� �ڵ�
					os.writeC(6);   //����ġ �ۼ�Ʈ +6
					break;
				}
			}
			/** �ູ���� ������ Ƽ���� ����� ����, MR, ����ġ ���ʽ�, HP100 ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 30030) {//�������� Ƽ����
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 1:
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 2:
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 3:
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 4:
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 5:
					os.writeC(15);  //MR ǥ�� �ڵ�
					os.writeH(getMr() + 4); //MR +4
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 6:
					os.writeC(15);  //MR ǥ�� �ڵ�
					os.writeH(getMr() + 5); //MR +5
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					break;
				case 7:
					os.writeC(15);  //MR ǥ�� �ڵ�
					os.writeH(getMr() + 6); //MR +6
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					os.writeC(36);  //����ġ �ۼ�Ʈ ǥ�� �ڵ�
					os.writeC(2);   //����ġ �ۼ�Ʈ +2
					break;
				case 8:
					os.writeC(15);  //MR ǥ�� �ڵ�
					os.writeH(getMr() + 8); //MR +8
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(1);   //����� ���� +1
					os.writeC(36);  //����ġ �ۼ�Ʈ ǥ�� �ڵ�
					os.writeC(4);   //����ġ �ۼ�Ʈ +4
					break;
				case 9:
					os.writeC(15);  //MR ǥ�� �ڵ�
					os.writeH(getMr() + 11); //MR +11
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(2);   //����� ���� +2
					os.writeC(36);  //����ġ �ۼ�Ʈ ǥ�� �ڵ�
					os.writeC(6);   //����ġ �ۼ�Ʈ +6
					break;
				case 10:
					os.writeC(15);  //MR ǥ�� �ڵ�
					os.writeH(getMr() + 14); //MR +14
					os.writeC(63);  //����� ���� ǥ�� �ڵ�
					os.writeC(2);   //����� ���� +2
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(36);  //����ġ �ۼ�Ʈ ǥ�� �ڵ�
					os.writeC(8);   //����ġ �ۼ�Ʈ +8
					break;
				}
			}
			/** ������ Ƽ����,  ����� ���� ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 900019) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(63); //����� ���� ǥ�� �ڵ�
					os.writeC(1);  //����� ���� +1
					break;
				case 1:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(1); //����� ���� +1
					break;
				case 2:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(1); //����� ���� +1
					break;
				case 3:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(1); //����� ���� +1
					break;
				case 4:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(1); //����� ���� +1
					break;
				case 5:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(1); //����� ���� +1
					break;
				case 6:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(2); //����� ���� +2
					break;
				case 7:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(3); //����� ���� +3
					break;
				case 8:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(4); //����� ���� +4
					break;
				case 9:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(5); //����� ���� +5
					break;
				case 10:
					os.writeC(63);//����� ���� ǥ�� �ڵ�
					os.writeC(6); //����� ���� +6
					break;
				}
			}
			/** �ູ���� ȭ���� Ƽ����,  �ٰŸ� �����, �ٰŸ� ����, HP100 ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 30031) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 1:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ����� +1
					break;
				case 2:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ����� +1
					break;
				case 3:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ����� +1
					break;
				case 4:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ����� +1
					break;
				case 5:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ����� +1
					break;
				case 6:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ����� +1
					break;
				case 7:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ����� +1
					os.writeC(5); //�ٰŸ� ���� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ���� +1
					break;
				case 8:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1); //�ٰŸ� ����� +1
					os.writeC(5); //�ٰŸ� ���� ǥ�� �ڵ�
					os.writeC(3); //�ٰŸ� ���� +3
					break;
				case 9:
					os.writeC(47);//�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(2); //�ٰŸ� ����� +2
					os.writeC(5); //�ٰŸ� ���� ǥ�� �ڵ� 
					os.writeC(5); //�ٰŸ� ���� +5
					break;
				case 10:
					os.writeC(47);  //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(2);   //�ٰŸ� ����� +2
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(5);   //�ٰŸ� ���� ǥ�� �ڵ�
					os.writeC(7);   //�ٰŸ� ���� +7
					break;
				}
			}
			/** �ູ���� ǳ���� Ƽ����, ���Ÿ� �����, ���ܸ� ����, HP100  ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 30032) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 1:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 2:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ� 
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 3:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ� 
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 4:
					os.writeC(35);//���Ÿ� ����� ǥ�� �ڵ� 
					os.writeC(1); //���Ÿ� ����� +1
					break;
				case 5:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ� 
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 6:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ� 
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 7:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ� 
					os.writeC(1);  //���Ÿ� ����� +1
					os.writeC(24); //���Ÿ� ���� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ���� +1
					break;
				case 8:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ� 
					os.writeC(1);  //���Ÿ� ����� +1
					os.writeC(24); //���Ÿ� ���� ǥ�� �ڵ�
					os.writeC(3);  //���Ÿ� ���� +3
					break;
				case 9:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ� 
					os.writeC(2);  //���Ÿ� ����� +2
					os.writeC(24); //���Ÿ� ���� ǥ�� �ڵ�
					os.writeC(5);  //���Ÿ� ���� +5
					break;
				case 10:
					os.writeC(35);  //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(2);   //���Ÿ� ����� +2
					os.writeC(24);  //���Ÿ� ���� ǥ�� �ڵ�
					os.writeC(7);   //���Ÿ� ���� +7
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					break;
				}
			}
			/** �ູ���� ������ Ƽ����,  SP, ��������, HP100 ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 30033) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(17); //SP ǥ�� �ڵ�  
					os.writeC(1);  //SP +1
					break;
				case 1:
					os.writeC(17); //SP ǥ�� �ڵ� 
					os.writeC(1);  //SP +1
					break;
				case 2:
					os.writeC(17); //SP ǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 3:
					os.writeC(17); //SP ǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 4:
					os.writeC(17); //SP ǥ�� �ڵ�
					os.writeC(1);  //SP +1
					;
					break;
				case 5:
					os.writeC(17); //SP ǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 6:
					os.writeC(17); //SP ǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 7:
					os.writeC(17); //SP ǥ�� �ڵ�
					os.writeC(2);  //SP +2
					break;
				case 8:
					os.writeC(17); //SP ǥ�� �ڵ�
					os.writeC(2);  //SP +2
					os.writeC(40); //���� ���� ǥ�� �ڵ�
					os.writeC(2);  //���� ���� +2
					break;
				case 9:
					os.writeC(17); //SP ǥ�� �ڵ�
					os.writeC(2);  //SP +2
					os.writeC(40); //���� ���� ǥ�� �ڵ�
					os.writeC(4);  //���� ���� +4
					break;
				case 10:
					os.writeC(17);  //SP ǥ�� �ڵ�
					os.writeC(3);   //SP +3
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(40);  //���� ���� ǥ�� �ڵ�
					os.writeC(5);   //���� ���� +5
					break;
				}
			}
			/** �Ϲ� ȭ���� Ƽ����,  �ٰŸ� �����, �ٰŸ� ����, HP100 ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 200852) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 1:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 2:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 3:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 4:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 5:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 6:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 7:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					break;
				case 8:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(1);  //�ٰŸ� ����� +1
					os.writeC(5);  //�ٰŸ� ���� ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ���� +2
					break;
				case 9:
					os.writeC(47); //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(2);  //�ٰŸ� ����� +2
					os.writeC(5);  //�ٰŸ� ���� ǥ�� �ڵ�
					os.writeC(4);  //�ٰŸ� ���� +4
					break;
				case 10:
					os.writeC(47);  //�ٰŸ� ����� ǥ�� �ڵ�
					os.writeC(2);   //�ٰŸ� ����� +2
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(5);   //�ٰŸ� ���� ǥ�� �ڵ�
					os.writeC(6);   // �ٰŸ� ���� +6
					break;
				}
			}
			/** �Ϲ� ǳ���� Ƽ����,  ���Ÿ� �����, ���Ÿ� ����, HP100 ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 200853) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 1:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 2:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 3:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 4:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 5:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 6:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 7:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					break;
				case 8:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(1);  //���Ÿ� ����� +1
					os.writeC(24); //���Ÿ� ���� ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ���� +2
					break;
				case 9:
					os.writeC(35); //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(2);  //���Ÿ� ����� +2
					os.writeC(24); //���Ÿ� ���� ǥ�� �ڵ�
					os.writeC(4);  //���Ÿ� ���� +4
					break;
				case 10:
					os.writeC(35);  //���Ÿ� ����� ǥ�� �ڵ�
					os.writeC(2);   //���Ÿ� ����� +2
					os.writeC(24);  //���Ÿ� ���� ǥ�� �ڵ�
					os.writeC(6);   //���Ÿ� ���� +6
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100	
					break;
				}
			}
			/** �Ϲ� ������ Ƽ����,  SP, ���� ����, HP100 ǥ��κ� **/ //.by���ۻ�̳�
			if (itemId == 200854) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 1:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 2:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 3:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 4:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 5:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 6:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 7:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(1);  //SP +1
					break;
				case 8:
					os.writeC(17); //SPǥ�� �ڵ�
					os.writeC(2);  //SP +2
					os.writeC(40); //���� ���� ǥ�� �ڵ�
					os.writeC(1);  //���� ���� +1
					break;
				case 9:
					os.writeC(17); //SPǥ�� �ڵ� 
					os.writeC(2);  //SP +3
					os.writeC(40); //���� ���� ǥ�� �ڵ�
					os.writeC(3);  //���� ���� +3
					break;
				case 10:
					os.writeC(17);  //SPǥ�� �ڵ�
					os.writeC(3);   //SP +3
					os.writeC(14);  //HP ǥ�� �ڵ�
					os.writeH(100); //HP +100
					os.writeC(40);  //���� ���� ǥ�� �ڵ�
					os.writeC(4);   //���� ���� +4
					break;
				}
			}
			/** �ݿ����� ����, Ȯ���� ����� ���� ǥ�� **/ //by.���ۻ�̳�
			else if (itemId == 21093) {
				os.writeC(64); os.writeC(getEnchantLevel() * 2); os.writeC(50);
			}	
			/** �ݿ����� ����, Ȯ���� ����� ���� ǥ�� **/ //by.���ۻ�̳�
			else if (itemId == 30014) {
				os.writeC(64); os.writeC(getEnchantLevel()); os.writeC(20);
			}
			/** �ż��� ������ ����, Ȯ�������� ǥ�� **/ //by.���ۻ�̳�
			if (itemId == 222355) {
				os.writeC(64); os.writeC(getEnchantLevel()); os.writeC(10);
			}
			/** �Ϲ� & �ູ ����, ȭ��, ǳ��, Ƽ����, 10��þ�� PVP����� �߰�, PVP����� ������ ǥ��  **/ //.by���ۻ�̳�
			if ((itemId == 200851 || itemId == 200852 || itemId == 200853 || itemId == 200854 || itemId == 30030 || itemId == 30031 || itemId == 30032 || itemId == 30033)
					&& getEnchantLevel() >= 10) { // ���� ȭ�� ǳ�� pvp������
				os.writeC(59); // PVP �߰� ����� ǥ�� �ڵ�
				os.writeC(1);  // PVP �߰� ����� +1
				os.writeC(60); // PVP ����� ������ ǥ�� �ڵ� 
				os.writeC(1);  // PVP����� ������ +1
			}
			if (itemId == 421217 || itemId == 421218 || itemId == 421219 || itemId == 431217 || itemId == 431218
					|| itemId == 431219) { // �巡���� ����
				os.writeC(36);// ����ġ1����
				os.writeC(10);
			}
			/** ������ ���� ǥ��(����)  **/
			if (itemId == 421220 ) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+4%");
					os.writeC(36);// ����ġ1����
					os.writeC(10);
					break;
				case 1:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+8%");
					os.writeC(36);// ����ġ1����
					os.writeC(12);
					break;
				case 2:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+12%");
					os.writeC(36);// ����ġ1����
					os.writeC(14);
					break;
				case 3:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+16%");
					os.writeC(36);// ����ġ1����
					os.writeC(16);
					break;
				case 4:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+20%");
					os.writeC(36);// ����ġ1����
					os.writeC(18);
					break;
				case 5:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+24%");
					os.writeC(36);// ����ġ1����
					os.writeC(20);
					break;
				case 6:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+28%");
					os.writeC(36);// ����ġ1����
					os.writeC(22);
					break;
				case 7:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+32%");
					os.writeC(36);// ����ġ1����
					os.writeC(25);
					break;
				case 8:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+36%");
					os.writeC(39);
					os.writeS("\\fG����ȿ��: \\aA��������");
					os.writeC(36);// ����ġ1����
					os.writeC(30);
					break;
				default:
					break;
				}
			}
			/** ������ ���� ǥ��(�����̵�����)  **/ 
			if (itemId == 421221) { 
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+4%");
					os.writeC(36);// ����ġ1����
					os.writeC(10);
					break;
				case 1:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+8%");
					os.writeC(36);// ����ġ1����
					os.writeC(12);
					break;
				case 2:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+12%");
					os.writeC(36);// ����ġ1����
					os.writeC(14);
					break;
				case 3:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+16%");
					os.writeC(36);// ����ġ1����
					os.writeC(16);
					break;
				case 4:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+20%");
					os.writeC(36);// ����ġ1����
					os.writeC(18);
					break;
				case 5:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+24%");
					os.writeC(36);// ����ġ1����
					os.writeC(20);
					break;
				case 6:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+28%");
					os.writeC(36);// ����ġ1����
					os.writeC(22);
					break;
				case 7:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+32%");
					os.writeC(36);// ����ġ1����
					os.writeC(25);
					break;
				case 8:
					os.writeC(39);
					os.writeS("\\fG�Ƶ���: \\aA+36%");
					os.writeC(39);
					os.writeS("\\fG����ȿ��: \\aA�����̵�����");
					os.writeC(36);// ����ġ1����
					os.writeC(30);
					break;
				default:
					break;
				}
			}
			/** �巡���� ���� ǥ��  **/ 
			if (itemId == 421216) {
				os.writeC(36);//����ġ % ǥ�� �ڵ�
				os.writeC(2); //����ġ  2%
			}
			/** �Ƹ�ī�ǰ�ȭ������ **/
			if (itemId == 21271) {
				os.writeC(40);
				os.writeC(2); // ����1
				os.writeC(120); // �������� 1
				os.writeC(5);
				os.writeC(36);// ����ġ1����
				os.writeC(5);
			}
			/** �Ƹ�ī������ **/
			if (itemId == 21272) {
				os.writeC(40);
				os.writeC(2); // ����1
				os.writeC(36);// ����ġ1����
				os.writeC(2);
			}

			/** ���� pvp������ **/
			if (getItem().getGrade() != 3 && itemType2 == 2
					&& (getItem().getType() == 9 || getItem().getType() == 11)) { //
				switch (getEnchantLevel()) {
				case 6:
					os.writeC(59);// PVP �߰� ������ ǥ�� �ڵ�
					os.writeC(1); // PVP �߰� ������ +1
					break;
				case 7:
					os.writeC(59);
					os.writeC(2); // pvp�߰� ������
					break;
				case 8:
					os.writeC(59);
					os.writeC(3); // pvp�߰� ������
					break;
				case 9:
					os.writeC(59);
					os.writeC(5); // pvp�߰� ������
					break;
				default:
					break;
				}
			}

			/** �巡�� �����̾� ���� �� ���� ���� **/
			if (itemId == 66) {
				os.writeC(39);
				os.writeS("���� ���� +" + (5 + getEnchantLevel()));

				if (getEnchantLevel() >= 10) {
					getItem().setMagicName("�巡���� �ϰ�");
				}

			}

			if (itemId == 21097) {// �������� ����
				switch (getEnchantLevel()) {
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(1);
					break;
				case 7:
				case 8:
					os.writeC(17);
					os.writeC(2);
					break;
				default:
					if (getEnchantLevel() >= 9) {
						os.writeC(17);
						os.writeC(3);
					}
					break;
				}
			/** ��ġ �κ� SPǥ�� **/ //.by���ۻ�̳�
			} else if (itemType2 == 2 && itemId == 20107 && getEnchantLevel() >= 3) { 
				os.writeC(17);
				os.writeC(getItem().get_addsp() + getEnchantLevel() - 2);
				
			/** �ӹ̷ε��� �尩 SPǥ�� **/ //.by���ۻ�̳�
			} else if (itemType2 == 2 && itemId == 10170 && getEnchantLevel() >= 7) { 
				os.writeC(17);
				os.writeC(getItem().get_addsp() + getEnchantLevel() - 6);
				
			/** ���������� Ű��ũ 8���� sp1�� ���� SPǥ�� **/ //.by���ۻ�̳�
			} else if (itemId == 30120 && getEnchantLevel() >= 8) { 
				os.writeC(17);
				os.writeC(getItem().get_addsp() + getEnchantLevel() - 7);
				
			/** ���丮���� ���� **/ //.by���ۻ�̳�
			} else if (itemId == 134 || itemId == 30081) {
				os.writeC(17);
				os.writeC(getItem().get_addsp() + (getEnchantLevel()));
				
			/** ���� 7�̻� �ɼ� �����Ŀ� ������ **/
			} else if (getItem().getGrade() != 3 && itemType2 == 2 && (getItem().getType() == 9 || getItem().getType() == 11)) { // ����~
			       if (getEnchantLevel() >= 7) {
				   os.writeC(17);
				   os.writeC(getItem().get_addsp() + (getEnchantLevel() - 6));
			}
			/** ���� SP ǥ�� **/ //.by���ۻ�̳� �̰� �����ϴµ� �Ӹ��� ��������..����
			  else if (getItem().get_addsp() != 0) {
				   os.writeC(17); os.writeC(getItem().get_addsp());
		    }
		    } 
			  else if (itemId == 900021) { // ������ ����
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(1);
					break;
				case 7:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + getEnchantLevel() - 5);
					break;
				case 8:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + getEnchantLevel() - 5);
					break;
				case 9:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + getEnchantLevel() - 5);
					break;
				case 10:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + getEnchantLevel() - 5);
					break;
				}
			} else if (getsp() != 0) {
				os.writeC(17);
				os.writeC(getsp());
			}
			// �����Ŀ� ������

			// ���.��ű� �� ���� SP
			// ��Ÿ111

			if (getItem().getType2() == 1 && (getItem().getType() == 7 || getItem().getType1() == 17)
					&& getStepEnchantLevel() == 1) {// ����Ÿ�ֹ����̿���...�ù�
				os.writeC(39);
				os.writeS("�߰� SP +1");
			} else if (getItem().getType2() == 1 && (getItem().getType() == 7 || getItem().getType1() == 17)
					&& getStepEnchantLevel() == 2) {// ����Ÿ�ֹ����̿���...�ù�
				os.writeC(39);
				os.writeS("�߰� SP +2");
			} else if (getItem().getType2() == 1 && (getItem().getType() == 7 || getItem().getType1() == 17)
					&& getStepEnchantLevel() == 3) {// ����Ÿ�ֹ����̿���...�ù�
				os.writeC(39);
				os.writeS("�߰� SP +3");
			} else

			if (getItemId() == 21246) { // ������ ���� ����
				switch (getEnchantLevel()) {
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 4);
					break;
				case 7:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 4);
					os.writeC(40);
					os.writeC(1);
					break;
				case 8:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 4);
					os.writeC(40);
					os.writeC(2);
					break;
				default:
					break;
				}
			} else

			if (getItemId() == 21250) { // ������ ���� ����
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(17);
					os.writeC(1);
					break;
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 3);
					os.writeC(40);
					os.writeC(1);
					break;
				case 7:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 3);
					os.writeC(40);
					os.writeC(2);
					break;
				case 8:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 3);
					os.writeC(40);
					os.writeC(3);
					break;
				default:
					break;
				}
				/*
				 * }else if (getItem().getGrade() == 2) {//���� �� if(getEnchantLevel() < 6){ if
				 * (getItem().get_addsp() != 0) { os.writeC(17);
				 * os.writeC(getItem().get_addsp()); } }else if(getEnchantLevel() >= 6){
				 * os.writeC(17); os.writeC(getItem().get_addsp() + (getEnchantLevel()-5)); }
				 */
			} else if (itemId == 500009) { // ��Ƽ�� ������
				switch (getEnchantLevel()) {
				case 3:
				case 4:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 1);
					break;
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 2);
					break;
				case 7:
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					break;
				case 8:
					os.writeC(40);
					os.writeC(3); // ����1
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					break;
				}
			} else if (itemId == 502009) { // ��Ƽ�� ������
				switch (getEnchantLevel()) {
				case 3:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 1);
					break;
				case 4:
				case 5:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 2);
					break;
				case 6:
					os.writeC(40);
					os.writeC(1); // ����1
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					break;
				case 7:
					os.writeC(40);
					os.writeC(3); // ����1
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					break;
				case 8:
					os.writeC(40);
					os.writeC(5); // ����1
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 4);
					break;
				}
			} else if (itemId == 525114) { // ��Ƽ�� ������
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(17);
					os.writeC(1);
					break;
				case 6:
				case 7:
					os.writeC(17);
					os.writeC(2);
					break;
				case 8:
					os.writeC(17);
					os.writeC(3);
					break;
				}
			} else if (itemId == 625114) { // ��Ƽ�� ������
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(17);
					os.writeC(1);
					break;
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(2);
					break;
				case 7:
					os.writeC(17);
					os.writeC(3);
					break;
				case 8:
					os.writeC(17);
					os.writeC(4);
					break;
				}
			}
			/*
			 * else if (getItem().get_addsp() != 0) { os.writeC(17);
			 * os.writeC(getItem().get_addsp()); }
			 */

			if (getItem().isHasteItem()) {
				os.writeC(18);
			}

			// Ŭ����

			// ////////////////////////////////////////////////////////////////////////////////////////////////
			// ���.��ű��� ���� HP
			if (itemId == 21095) { // ü���� ����
				os.writeC(14);
				switch (getEnchantLevel()) {
				case 7:
					os.writeH(getItem().get_addhp() + 25);
					break;
				case 8:
					os.writeH(getItem().get_addhp() + 50);
					break;
				default:
					if (getEnchantLevel() >= 9) {
						os.writeH(getItem().get_addhp() + 75);
					} else {
						os.writeH(getItem().get_addhp());
					}
					break;
				}
			}
			
			    else if (getItem().getGrade() != 3 && itemType2 == 2 && (getItem().getType() == 9
					|| getItem().getType() == 11 || getItem().getType() == 8 || getItem().getType() == 12)) { // ����~
				switch (getEnchantLevel()) {
				case 0:
					if (getItem().get_addhp() != 0) {
						os.writeC(14);
						os.writeH(getItem().get_addhp());
					}
					break;
				case 1:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 5);
					break;
				case 2:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 10);
					break;
				case 3:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 20);
					break;
				case 4:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 30);
					break;
				case 5:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 40);
					break;
				case 6:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 40);
					break;
				case 7:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 50);
					break;
				case 8:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 50);
					break;
				case 9:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 60);
					break;
				default:
					break;
				}
			} else if (getItem().getGrade() == 3) {// �������
				if (itemId == 500007) { // ��Ƽ�� ������
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;
					case 1:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 20);
						break;
					case 2:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 30);
						break;
					case 3:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 40);
						break;
					case 4:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 50);
						break;
					case 5:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 60);
						break;
					case 6:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 70);
						break;
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 80);
						os.writeC(24);
						os.writeC(getItem().getBowHitup() + 1);
						os.writeC(5);
						os.writeC(getItem().getHitup() + 1);
						break;
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 90);
						os.writeC(24);
						os.writeC(getItem().getBowHitup() + 3);
						os.writeC(5);
						os.writeC(getItem().getHitup() + 3);
						break;
					}
				} else if (itemId == 502007) { // ��Ƽ�� ������
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;
					case 3:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 50);
						break;
					case 4:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 60);
						break;
					case 5:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 70);
						break;
					case 6:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 80);
						os.writeC(24);
						os.writeC(getItem().getBowHitup() + 1);
						os.writeC(5);
						os.writeC(getItem().getHitup() + 1);
						break;
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 90);
						os.writeC(24);
						os.writeC(getItem().getBowHitup() + 3);
						os.writeC(5);
						os.writeC(getItem().getHitup() + 3);
						break;
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 140);
						os.writeC(24);
						os.writeC(getItem().getBowHitup() + 5);
						os.writeC(5);
						os.writeC(getItem().getHitup() + 5);
						break;
					}

				} else if ((getItem().getItemId() == 525109 || getItem().getItemId() == 525110
						|| getItem().getItemId() == 525111 || getItem().getItemId() == 525112
						|| getItem().getItemId() == 525113 || getItem().getItemId() == 625109
						|| getItem().getItemId() == 625110 || getItem().getItemId() == 625111
						|| getItem().getItemId() == 625112 || getItem().getItemId() == 625113)) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) + 10);
						break;
					case 6:
					case 7:
					case 8:
						if (getItem().getItemId() == 625111) {
							os.writeC(14);
							os.writeH((getItem().get_addhp() + (getEnchantLevel() * 5) + 10)
									+ ((getEnchantLevel() - 5) * 5));
							break;
						} else {
							os.writeC(14);
							os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) + 10);
							break;
						}
					}

				} else if (getItem().getItemId() == 525115) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) - 10);
						break;
					}
				} else if (getItem().getItemId() == 625115) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) - 5);
						break;
					case 8:
						os.writeC(14);
						os.writeH(30);
						break;
					}
				} else if (getItem().getItemId() == 525114) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5));
						break;
					}
				} else if (getItem().getItemId() == 625114) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 3:
						os.writeC(14);
						os.writeH(20);
						break;
					case 1:
					case 2:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5));
						break;
					case 4:
					case 5:
					case 6:
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) + 5);
						break;
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) + 10);
						break;
					}
				} else if ((getItem().getItemId() >= 425109 && getItem().getItemId() <= 425113)
						|| (getItem().getItemId() >= 525109 && getItem().getItemId() <= 525113)
						|| (getItem().getItemId() >= 625109 && getItem().getItemId() <= 625113) || getItemId() == 21247
						|| getItemId() == 21248 || getItemId() == 21251 || getItemId() == 21252) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 1:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 15);
						break;
					case 2:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 20);
						break;
					case 3:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 25);
						break;
					case 4:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 30);
						break;
					case 5:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 35);
						break;
					case 6:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 40 + (getItemId() == 21252 ? 5 : 0));
						break;
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 45 + (getItemId() == 21252 ? 10 : 0));
						break;
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 50 + (getItemId() == 21252 ? 15 : 0));
						break;
					}
				} else if (getItemId() == 21246 || getItemId() == 21250) { // ������
																			// ����
					if (getEnchantLevel() > 0) {
						os.writeC(14);
						os.writeH(getEnchantLevel() * 5);
					}
				} else if (getItemId() == 21249 || getItemId() == 21253) { // �����ۿ��
					if (getEnchantLevel() >= 3) {
						os.writeC(14);
						os.writeH((getEnchantLevel() - 2) * 5);
					}
				}
				/** ��Ʈ 6���� HP ǥ�� **/
			} else if (itemType2 == 2 && (getItem().getType() == 10 && getEnchantLevel() > 5)) {// ��Ʈ
				switch (getEnchantLevel()) {
				case 6:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 20);
					break;
				case 7:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 30);
					break;
				case 8:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 40);
					break;
				case 9:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 50);
					break;
				default:
					break;
				}
				/**/
			} else if (getItem().get_addhp() != 0) {
				os.writeC(14);
				os.writeH(getItem().get_addhp());
			}
			// ////////////////////////////////////////////////////////////////////////////////////////////////

			// ��ƽ
			/*
			 * if (getItem().getGrade() == 0){// ���� : �� if(getItem().get_addhpr() != 0){
			 * os.writeC(37); os.writeC(getItem().get_addhpr()); }else{
			 * switch(getEnchantLevel()){ case 6: os.writeC(37);
			 * os.writeC(getItem().get_addhpr() + 1); break; case 7: os.writeC(37);
			 * os.writeC(getItem().get_addhpr() + 2); break; case 8: os.writeC(37);
			 * os.writeC(getItem().get_addhpr() + 3); break; } } } else
			 */if (getItem().get_addhpr() != 0) {
				os.writeC(37);
				os.writeC(getItem().get_addhpr());
			}

			// ���.��ű��� ���� MP
			if (getItem().getGrade() != 3 && itemType2 == 2 && (getItem().getType() == 10)) { // ��Ʈ
																								// ~
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 5);
					break;
				case 2:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 10);
					break;
				case 3:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 20);
					break;
				case 4:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 30);
					break;
				case 5:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 40);
					break;
				case 6:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 40);
					os.writeC(60);
					os.writeC(1); // pvp�߰� ������
					break;
				case 7:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					os.writeC(60);
					os.writeC(3); // pvp�߰� ������
					break;
				case 8:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					os.writeC(60);
					os.writeC(5); // pvp�߰� ������
					break;
				case 9:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 60);
					os.writeC(60);
					os.writeC(7); // pvp�߰� ������
					break;
				default:
					break;
				}
			} else if (getItem().getItemId() == 500009) {// �����
				switch (getEnchantLevel()) {
				case 0:
					if (getItem().get_addmp() != 0) {
						os.writeC(32);
						os.writeH(getItem().get_addmp());
					}
					break;
				case 1:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 10);
					break;
				case 2:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 15);
					break;
				case 3:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 30);
					break;
				case 4:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 35);
					break;
				case 5:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 6:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 55);
					break;
				case 7:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 70);
					break;
				case 8:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 95);
					break;
				}
			} else if (getItem().getItemId() == 502009) {// �����
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 10);
					break;
				case 2:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 15);
					break;
				case 3:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 35);
					break;
				case 4:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 5:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 55);
					break;
				case 6:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 70);
					break;
				case 7:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 95);
					break;
				case 8:
					os.writeC(39);
					os.writeS("�ִ� MP +130");
					break;
				default:
					break;
				}
			} else if (getItemId() == 625114) {// ��������
												// ���� �ູ
				os.writeC(32);
				if (getEnchantLevel() >= 7) {
					if (getEnchantLevel() == 7) {
						os.writeC(30);
					} else {
						os.writeC(35);
					}
				} else {
					os.writeC(getItem().get_addmp());
				}
				
			  /** ���������� ������, MP +10 ǥ��  **/  //by.���ۻ�̳�
			} else if (getItem().getItemId() == 420109) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 10); //MP +10
					break;
				}
				
			  /** ��Ǫ������ ������,���������� �γ���, ������ �����, ������ �����, ������ �������� �����, MP+20 ǥ��   **/  //by.���ۻ�̳�
			} else if (getItem().getItemId() == 420105 || itemId == 420110 || itemId == 21258 || itemId == 21260 || itemId == 21268) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 20); //MP +20
					break;
				}
				
			  /** ��Ǫ������ �γ���, MP +30 ǥ��  **/  //by.���ۻ�̳�
			} else if (getItem().getItemId() == 420106) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 30); //MP +30
					break;
				}
				
			  /** �������� ���� mp+40 ǥ��  */  //by.���ۻ�̳�
			} else if (getItem().getItemId() == 420111) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 40); //MP +40
					break;
				}
				
			  /** ��Ǫ������ ����, ������� �߰�, ��ɲ��� �߰�, �������� �߰�, MP +50 ǥ��  **/  //by.���ۻ�̳�
			} else if (getItem().getItemId() == 420107 || itemId == 230000 || itemId == 230001 || itemId == 230002) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 50); //MP +50
					break;
				}
			  /** �Ŵ� ���� ������ ���� ����, mp+70 ǥ��  */  //by.���ۻ�̳�
			} else if (getItem().getItemId() == 20050) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 70); //MP +70
					break;
				}
				
				/** �þ��� �ɾ�,�븶������ ����, MP +100 ǥ��  **/  //by.���ۻ�̳�
			} else if (getItem().getItemId() == 22009 || itemId == 21166) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 1:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 2:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 3:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 4:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 5:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 6:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 7:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 8:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 9:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				case 10:
					os.writeC(32); //MP ǥ�� �ڵ�
					os.writeH(getItem().get_addmp() + 100); //MP +100
					break;
				}
			//} else if (getItem().get_addmp() != 0) {
			//	os.writeC(32);
			//	if (getItemId() == 21166)
			//		os.writeH(getItem().get_addmp() + (getEnchantLevel() * 10));
			//	else
			//		os.writeH(getItem().get_addmp());
			}

			// ��ƽ
			/*
			 * if (getItem().getGrade() == 0){//���� : �� if(getItem().get_addmpr() != 0){
			 * os.writeC(38); os.writeC(getItem().get_addmpr()); }else{
			 * switch(getEnchantLevel()){ case 6: os.writeC(38);
			 * os.writeC(getItem().get_addmpr() + 1); break; case 7: os.writeC(38);
			 * os.writeC(getItem().get_addmpr() + 2); break; case 8: os.writeC(38);
			 * os.writeC(getItem().get_addmpr() + 3); break; } } } else
			 */if (getItem().get_addmpr() != 0) {
				os.writeC(38);
				int mprr = getItem().get_addmpr();
				if (getItem().getItemId() == 261)
					mprr += getEnchantLevel();
				os.writeC(mprr);
			}

			// ����

			// PVP ������ ������
			if (getItemId() >= 21242 && getItemId() <= 21245) {
				os.writeC(60);
				os.writeC(getEnchantLevel() + 1);
			}
			/** �ؼ������۸����� by.soju **/
			if (getItem().get_regist_calcPcDefense() != 0) {
				os.writeC(60);
				os.writeC(getItem().get_regist_calcPcDefense());
			}

			// MR
			/*
			 * if (getItem().getGrade() == 1){//����: �� if(getEnchantLevel() < 6){ if (getMr()
			 * != 0) { os.writeC(15); os.writeH(getMr()); } }else if(getEnchantLevel()>=6){
			 * os.writeC(15); os.writeH(getMr()+(getEnchantLevel()-5)); }
			 *//**
				 * } else if(getItem().getItemId() == 500009){//����� os.writeC(15);
				 * os.writeH(getMr());
				 **/
			/*
			 * } else
			 */if (getMr() != 0) {
				os.writeC(15);
				os.writeH(getMr());
			}

			// ���.��ű� ���� �ӹ�ǥ��
			if (getItem().get_defense_fire() != 0) {
				os.writeC(27);
				os.writeC(getItem().get_defense_fire());
			}
			if (getItem().get_defense_water() != 0) {
				os.writeC(28);
				os.writeC(getItem().get_defense_water());
			}
			if (getItem().get_defense_wind() != 0) {
				os.writeC(29);
				os.writeC(getItem().get_defense_wind());
			}
			if (getItem().get_defense_earth() != 0) {
				os.writeC(30);
				os.writeC(getItem().get_defense_earth());
			}

			/**
			 * getMr() �߰�.���. ����� �ߺ�ǥ�� ���� ���� ������ ǥ�úκ� ���� ������
			 */

			if (getRegistLevel() != 0 && itemId >= 490000 && itemId <= 490017) {
				os.writeC(39);
				os.writeS(spirit());
			}

			if (getRegistLevel() == 10) {// �ǵ��� ���ɹ���
				os.writeC(27);
				os.writeC(10);
				os.writeC(28);
				os.writeC(10);
				os.writeC(29);
				os.writeC(10);
				os.writeC(30);
				os.writeC(10);
			} else if (getRegistLevel() == 11) {// �ǵ��� ��������
				os.writeC(32);
				os.writeC(30);
			} else if (getRegistLevel() == 12) {// �ǵ��� ü�¹���
				os.writeC(14);
				os.writeH(30);
			} else if (getRegistLevel() == 13) {// �ǵ��� �긶����
				os.writeC(15);
				os.writeH(10);
			} else if (getRegistLevel() == 15) {// �ǵ��� ȸ������
				os.writeC(37);
				os.writeC(1);
				os.writeC(38);
				os.writeC(1);
			} else if (getRegistLevel() == 16) {// �ǵ��� ��ȭ����
				os.writeC(33);
				os.writeC(2);
				os.writeC(10);
			} else if (getRegistLevel() == 17) {// �ǵ��� Ȧ�幮��
				os.writeC(33);
				os.writeC(6);
				os.writeC(10);
			} else if (getRegistLevel() == 18) {// �ǵ��� ���Ϲ���
				os.writeC(117);
				os.writeC(10);
			}
			
			/** �⺻ ������ ���� ���� ���� �κ� �ε� ������ ���� 
			if (getItem().getMinLevel() > 0) {
				os.writeC(42);
				os.writeC(getItem().getMinLevel());
			}
			*/
			// 45/45 ������ ü�²��� ������ Ƽ

			// PVP �߰� ������

			if ((getItemId() >= 277 && getItemId() <= 283) || (getItemId() >= 90085 && getItemId() <= 90092)) {
				os.writeC(59);
				os.writeC(getEnchantLevel());

			}
			/** �ؼ������۸����� by.soju **/
			if (getItem().get_regist_PVPweaponTotalDamage() != 0) {
				os.writeC(59);
				os.writeC(getItem().get_regist_PVPweaponTotalDamage());
			}

			else

			if ((getItemId() >= 21246 && getItemId() <= 21253)) {

				int dmg = 0;
				if (getEnchantLevel() == 7)
					dmg = 1;
				else if (getEnchantLevel() == 8)
					dmg = 2;
				if (dmg != 0) {
					os.writeC(59);
					os.writeC(dmg);
				}
			} else

			if ((getItemId() >= 90093 && getItemId() <= 90100)) {

				int dmg = 0;
				if (getEnchantLevel() == 7)
					dmg = 3;
				else if (getEnchantLevel() == 8)
					dmg = 5;
				else if (getEnchantLevel() == 9)
					dmg = 7;
				else if (getEnchantLevel() == 10)
					dmg = 10;
				if (dmg != 0) {
					os.writeC(59);
					os.writeC(dmg);
				}
			} else

			if ((getItemId() >= 110051 && getItemId() <= 110058)) // ��Ÿ�� �����
			{

				int dmg = 0;
				if (getEnchantLevel() == 7)
					dmg = 3;
				else if (getEnchantLevel() == 8)
					dmg = 5;
				else if (getEnchantLevel() == 9)
					dmg = 7;
				else if (getEnchantLevel() == 10)
					dmg = 10;
				if (dmg != 0) {
					os.writeC(59);
					os.writeC(dmg);
				}
			} else

			if ((getItemId() == 121216)
			/* || (getItemId() >= 625109 && getItemId() <= 625115) */) {
				os.writeC(59);
				os.writeC(1);
			} else if ((getItemId() == 221216)
			/* || (getItemId() >= 625109 && getItemId() <= 625115) */) {
				os.writeC(59);
				os.writeC(3);
			} else if (getItemId() >= 284 && getItemId() <= 290) {
				os.writeC(59);
				int dmg = 0;
				if (getEnchantLevel() == 7)
					dmg = 3;
				else if (getEnchantLevel() == 8)
					dmg = 5;
				else if (getEnchantLevel() == 9)
					dmg = 7;
				else if (getEnchantLevel() == 10)
					dmg = 10;
				os.writeC(dmg);
			} else if (getItemId() >= 900015 && getItemId() <= 900018) {
				os.writeC(59);
				int dmg = 0;
				if (getEnchantLevel() == 0)
					dmg = 2;
				else if (getEnchantLevel() == 1)
					dmg = 2;
				else if (getEnchantLevel() == 2)
					dmg = 2;
				else if (getEnchantLevel() == 3)
					dmg = 2;
				else if (getEnchantLevel() == 4)
					dmg = 2;
				else if (getEnchantLevel() == 5)
					dmg = 2;
				else if (getEnchantLevel() == 6)
					dmg = 2;
				else if (getEnchantLevel() == 7)
					dmg = 2;
				else if (getEnchantLevel() == 8)
					dmg = 2;
				else if (getEnchantLevel() == 9)
					dmg = 2;
				else if (getEnchantLevel() == 10)
					dmg = 2;

				os.writeC(dmg);
			} else

			/*
			 * if ((itemId >= 90085 && itemId <= 90092) || itemId == 160423 || itemId ==
			 * 435000 || itemId == 160510 || itemId == 160511 || itemId == 21123) {
			 * os.writeC(61); os.writeD(3442346400L); } if (itemId == 21269 &&
			 * getEnchantLevel() < 6) { os.writeC(61); os.writeD(3442346400L); }
			 * 
			 * if (itemId == 500206 || itemId == 500207 || itemId == 121216 || itemId ==
			 * 221216 || itemId == 500208) { os.writeC(61); os.writeD(3501426400L); }
			 */
			/*
			 * if (itemId >= 9075 && itemId <= 9093) { os.writeC(61); long dd = 3374546400L;
			 * Calendar cal = (Calendar) Calendar.getInstance().clone();
			 * cal.setTimeInMillis(dd); // cal.add(Calendar.YEAR, -1997); //
			 * cal.add(Calendar.HOUR_OF_DAY, 7); // cal.add(Calendar.DAY_OF_MONTH, 7); /*
			 * long ddd = calendar.getTimeInMillis();
			 * 
			 * calendar = Calendar.getInstance(); calendar.add(Calendar.DAY_OF_MONTH, 7);
			 */
			// long dddd = cal.getTimeInMillis()/1000/60/60;

			/*
			 * // 1970��1��1�� long time = (((System.currentTimeMillis()/1000)/60
			 * )/60);//(((((System.currentTimeMillis ()+851533200000)/1000)/60)/60) +
			 * (24*7)) + 236537; long temp = time / 128; if(temp > 0){
			 * os.writeC(hextable[(int)time%128]); while (temp > 128) {
			 * os.writeC(hextable[(int)temp%128]); temp = temp / 128; }
			 * os.writeC((int)temp); }else{ if(time==0){ os.writeC(0); }else{
			 * os.writeC(hextable[(int)time]); os.writeC(0); } }
			 */
			// os.writeD(dddd*24*1000);
			// os.writeD(Config.test + 3374546400L);
			// }

			if (itemId == 21096) {// ��ȣ�� ����
				if (��ȣ�ǰ���DamageDown() > 0) {
					os.writeC(63);

					os.writeC(��ȣ�ǰ���DamageDown());
				}
			} else if (getItem().getGrade() != 3 && itemType2 == 2 && (getItem().getType() == 10)) { // ��Ʈ
																										// ~
				if (��Ʈ����������() > 0) {
					os.writeC(63);

					os.writeC(��Ʈ����������());
				}
			} else if (getItem().getDamageReduction() != 0) {
				int reduc = getItem().getDamageReduction();
				if (itemId >= 420100 && itemId <= 420103) {
					if (getEnchantLevel() >= 7) {
						reduc++;
					}
					if (getEnchantLevel() >= 8) {
						reduc++;
					}
					if (getEnchantLevel() >= 9) {
						reduc++;
					}
				}
				os.writeC(63);
				os.writeC(reduc);
			}

			if (itemId == 502007) {
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(64);
					os.writeC(2);
					os.writeC(20);
					break;
				case 5:
					os.writeC(64);
					os.writeC(3);
					os.writeC(20);
					break;
				case 6:
					os.writeC(64);
					os.writeC(4);
					os.writeC(20);
					break;
				case 7:
					os.writeC(64);
					os.writeC(5);
					os.writeC(20);
					break;
				case 8:
					os.writeC(64);
					os.writeC(6);
					os.writeC(20);
					break;
				}
			} else if (itemId == 500007) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(64);
					os.writeC(2);
					os.writeC(20);
					break;
				case 6:
					os.writeC(64);
					os.writeC(3);
					os.writeC(20);
					break;
				case 7:
					os.writeC(64);
					os.writeC(4);
					os.writeC(20);
					break;
				case 8:
					os.writeC(64);
					os.writeC(5);
					os.writeC(20);
					break;
				}
			} else if (itemId == 21248) { // ������ ü�� Ȯ�� ����
				switch (getEnchantLevel()) {
				case 7:
					os.writeC(64);
					os.writeC(1);
					os.writeC(20);
					break;
				case 8:
					os.writeC(64);
					os.writeC(2);
					os.writeC(20);
					break;
				}
			} else if (itemId == 21252) { //// �� ������ ü�� Ȯ�� ����
				switch (getEnchantLevel()) {
				case 6:
					os.writeC(64);
					os.writeC(1);
					os.writeC(20);
					break;
				case 7:
					os.writeC(64);
					os.writeC(2);
					os.writeC(20);
					break;
				case 8:
					os.writeC(64);
					os.writeC(3);
					os.writeC(20);
					break;
				}
			}

			if (getItem().getWeightReduction() != 0) {
				// os.writeC(68);
				int reduc = getItem().getWeightReduction();
				if (itemId == 7246) {
					if (getEnchantLevel() > 5) {
						int en = getEnchantLevel() - 5;
						reduc += en * 60;
					}
				}

				os.writeC(0x5a);
				os.writeH(reduc);
			}

			if (getItem().getItemId() == 20298 // ���Ͻ��ǹ���
					|| getItem().getItemId() == 120298 // �����Ͻ�����
					|| getItem().getItemId() == 20117 // ������Ʈ�ǰ���
					|| getItem().getItemId() == 420100 || getItem().getItemId() == 420101
					|| getItem().getItemId() == 420102 || getItem().getItemId() == 420103) {
				os.writeC(70);
				os.writeC(2);// ������
			}

			/** �ߵ� ǥ�� **/ //.by���ۻ�̳�
			if ((itemId == 203025 || itemId == 203026) && getEnchantLevel() >= 10){ //�� �ο�ƺ� ���
				os.writeC(74); os.writeS("�ο� ȥ");
			} else if (itemId == 293 && getEnchantLevel() >= 10){ //�Ǹ��� ���
				os.writeC(74); os.writeS("�Ǹ�");
			} else if (itemId == 7227 && getEnchantLevel() >= 10){ //��ǳ�� ����
				os.writeC(74); os.writeS("����");
			} else if (itemId == 90084 && getEnchantLevel() >= 10){ //�������� ü�μҵ�
				os.writeC(74); os.writeS("����");
			} else if (itemId == 66 && getEnchantLevel() >= 10){ //�巡�� �����̾�
				os.writeC(74); os.writeS("�巡���� �ϰ�");
			} else if (itemId == 30117 && getEnchantLevel() >= 10){ //�߶�ī���� ���
				os.writeC(74); os.writeS("�߶�ī���� ȥ:���");
			} else if (itemId == 30118 && getEnchantLevel() >= 10){ //�߶�ī���� ��հ�
				os.writeC(74); os.writeS("�߶�ī���� ȥ:��հ�");
			} else if (itemId == 30119 && getEnchantLevel() >= 10){ //���������� ü�μҵ�
				os.writeC(74); os.writeS("���������� ȥ:ü�μҵ�");
			} else if (itemId == 30121 && getEnchantLevel() >= 10){ //��Ǫ������ ���
				os.writeC(74); os.writeS("��Ǫ������ ȥ:���");
			} else if (itemId == 30122 && getEnchantLevel() >= 10){ //��Ǫ������ �̵���
				os.writeC(74); os.writeS("��Ǫ������ ȥ:�̵���");
			} else if (itemId == 30115 && getEnchantLevel() >= 10){ //��Ÿ���� ����
				os.writeC(74); os.writeS("��Ÿ���� ȥ:����");
			} else if (itemId == 30112 && getEnchantLevel() >= 0){ //�����ϻ���� ����
				os.writeC(74); os.writeS("�����ϻ���� ����");
			} else if (itemId == 30111 && getEnchantLevel() >= 0){ //�׶�ī���� ����
				os.writeC(74); os.writeS("�׶�ī�� ��ȿ");
			} else if (getItem().isHasteItem()) {
				os.writeC(74); os.writeS("���̽�Ʈ");
			} else if (getItem().getMagicName() != null && !getItem().getMagicName().equals("")) {
				os.writeC(74); os.writeS(getItem().getMagicName());
			}

			/** ��Ƽ�� ���� �Ͱ��� �ߵ��� ���� ǥ��κ� */
			if (getItemId() == 500010 || getItemId() == 502010) {
				int chance = 0;
				if (getBless() == 0 && getEnchantLevel() >= 4) {
					chance = 2 + getEnchantLevel() - 4;
				} else if (getEnchantLevel() >= 5) {
					chance = 2 + getEnchantLevel() - 5;
				}
				if (chance > 0) {
					os.writeC(95);
					os.writeC(chance);
					os.writeC(20);
				}
			}
			
			/** ����Ÿ���̸� �� ���� üũ�ؼ� ��� ���� ���� */
			if (itemType2 == 1) {
				/** ���� �Ӽ� Ÿ�Կ� ���ؼ� �ߵ� ǥ�� */
				if(getAttrEnchantLevel() != 0){
					os.writeC(110);
					os.writeC(getAttrEnchantBit(getAttrEnchantLevel()));
				}
				
				/** ���� Ÿ���̰� ������ ������ �𵥵� ȿ���� �մ� �����̶�� */
				if (getItem().getMaterial() == 14 || getItem().getMaterial() == 17 || getItem().getMaterial() == 22){
					os.writeC(114);
					os.writeD(1);
				}
				
				if(getItem().getType1() != 20 && getItem().getType1() != 62){
					/** ���� Ÿ���̰� �������� ������� ����ǥ�� �ο� */
					if (getItem().getBless() == 0){
						os.writeC(115);
						os.writeD(1);
					}
					
					/** ���� �ջ� üũ ȿ�� Ȱ�迭�� ��ջ� ǥ����� */
					if (getItem().get_canbedmg() == 0){
						os.writeC(131);
						os.writeD(1);
					}
				}	
			}
		}
		
		/** �������ۿ� üũ�ؼ� �ŷ��Ұ����̸� â�� �Ұ�ȿ�� */
		/** �ŷ��Ұ� â�� ǥ�� */
		os.writeC(130); 
		os.writeD(getItem().isTradable() ? 7 : 2);
		
		int bit = 0;
		bit |= getItem().isUseRoyal() ? 1 : 0;
		bit |= getItem().isUseKnight() ? 2 : 0;
		bit |= getItem().isUseElf() ? 4 : 0;
		bit |= getItem().isUseMage() ? 8 : 0;
		bit |= getItem().isUseDarkelf() ? 16 : 0;
		bit |= getItem().isUseDragonKnight() ? 32 : 0;
		bit |= getItem().isUseBlackwizard() ? 64 : 0;
		bit |= getItem().isUseWarrior() ? 128 : 0;
		bit |= getItem().isUseFencer() ? 256 : 0;
		// bit |= getItem().isUseHighPet() ? 128 : 0;
		if (itemType2 != 0) {
			os.writeC(7);
			os.writeD(bit);
		}
		
		
		
		try {
			os.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return os.getBytes();
	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c,
			0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e,
			0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0,
			0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2,
			0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4,
			0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6,
			0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
			0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };

	private int ��Ʈ����������() {
		int lvl = getEnchantLevel();
		int reduc = 0;
		switch (lvl) {
		case 5:
			reduc = getItem().getDamageReduction() + 1;
			break;
		case 6:
			reduc = getItem().getDamageReduction() + 2;
			break;
		case 7:
			reduc = getItem().getDamageReduction() + 3;
			break;
		case 8:
			reduc = getItem().getDamageReduction() + 4;
			break;
		case 9:
			reduc = getItem().getDamageReduction() + 5;
			break;
		default:
			if (lvl >= 10) {
				reduc = getItem().getDamageReduction() + 5;
			} else {
				reduc = getItem().getDamageReduction() + 0;
			}
			break;
		}
		return reduc;
	}

	private int ��ȣ�ǰ���DamageDown() {
		int lvl = getEnchantLevel();
		int reduc = 0;
		switch (lvl) {
		case 5:
		case 6:
			reduc = 2;
			break;
		case 7:
		case 8:
			reduc = 3;
			break;
		default:
			if (lvl >= 9) {
				reduc = 4;
			} else {
				reduc = 1;
			}
			break;
		}
		return reduc;
	}

	private static final int _hit = 0x05;
	private static final int _dmg = 0x06;
	private static final int _bowhit = 0x18;
	private static final int _bowdmg = 0x23;
	private static final int _str = 0x08;
	private static final int _dex = 0x09;
	private static final int _con = 0x0a;
	private static final int _wis = 0x0b;
	private static final int _int = 0x0c;
	private static final int _cha = 0x0d;

	private static final int _mr = 0x0f;
	private static final int _sp = 0x11;

	private static final int _fire = 0x1B;
	private static final int _water = 0x1C;
	private static final int _wind = 0x1D;
	private static final int _earth = 0x1E;

	private static final int _maxhp = 0x0e;
	private static final int _maxmp = 0x20;
	private static final int _hpr = 0x25;
	private static final int _mpr = 0x26;
	private static final int _add_ac = 0x38;
	private static final int _poly = 0x47;

	public byte[] getStatusBytes(L1PcInstance pc, boolean check) {
		byte[] data = getStatusBytes();
		@SuppressWarnings("resource")
		BinaryOutputStream os = new BinaryOutputStream();
		
		try {
			os.write(data);

			os.writeC(0x45);

			if (check) {
				os.writeC(1);
			} else {
				os.writeC(2);
			}
			L1ArmorSets set = ArmorSetTable.getInstance().getArmorSets(getItem().getSetId());

			if (set.getAc() != 0) {
				os.writeC(_add_ac);
				os.writeC(set.getAc());
			}

			if (getItem().getItemId() == 20099) {
				os.writeC(_poly);
				os.writeH(1175);// ����
			} else if (getItem().getItemId() == 20100) {
				os.writeC(_poly);
				os.writeH(18692);// ������
			} else if (getItem().getItemId() == 20151) {
				os.writeC(_poly);
				os.writeH(2118);// �ɷ��Ͻ�
			} else if (getItem().getItemId() == 20118) {
				os.writeC(_poly);
				os.writeH(2117);// �˶����
			}

			if (set.getShortHitup() != 0) {
				os.writeC(_hit);
				os.writeC(set.getShortHitup());
			}
			if (set.getShortDmgup() != 0) {
				os.writeC(_dmg);
				os.writeC(set.getShortDmgup());
			}

			if (set.getLongHitup() != 0) {
				os.writeC(_bowhit);
				os.writeC(set.getLongHitup());
			}
			if (set.getLongDmgup() != 0) {
				os.writeC(_bowdmg);
				os.writeC(set.getLongDmgup());
			}

			if (set.getHpr() != 0) {
				os.writeC(_hpr);
				os.writeC(set.getHpr());
			}
			if (set.getMpr() != 0) {
				os.writeC(_mpr);
				os.writeC(set.getMpr());
			}

			if (set.getHp() != 0) {
				os.writeC(_maxhp);
				os.writeH(set.getHp());
			}
			if (set.getMp() != 0) {
				os.writeC(_maxmp);
				os.writeC(set.getMp());
			}

			if (set.getMr() != 0) {
				os.writeC(_mr);
				os.writeH(set.getMr());
			}

			if (set.getSp() != 0) {
				os.writeC(_sp);
				os.writeC(set.getSp());
			}

			if (set.getfire() != 0) {
				os.writeC(_fire);
				os.writeC(set.getfire());
			}
			if (set.getwater() != 0) {
				os.writeC(_water);
				os.writeC(set.getwater());
			}
			if (set.getwind() != 0) {
				os.writeC(_wind);
				os.writeC(set.getwind());
			}
			if (set.getearth() != 0) {
				os.writeC(_earth);
				os.writeC(set.getearth());
			}

			if (set.getStr() != 0) {
				os.writeC(_str);
				os.writeC(set.getStr());
			}
			if (set.getDex() != 0) {
				os.writeC(_dex);
				os.writeC(set.getDex());
			}
			if (set.getCon() != 0) {
				os.writeC(_con);
				os.writeC(set.getCon());
			}
			if (set.getWis() != 0) {
				os.writeC(_wis);
				os.writeC(set.getWis());
			}
			if (set.getIntl() != 0) {
				os.writeC(_int);
				os.writeC(set.getIntl());
			}
			if (set.getCha() != 0) {
				os.writeC(_cha);
				os.writeC(set.getCha());
			}
			
			os.writeC(0x45);
			os.writeC(0);

			if (getItem().getType2() == 2) {
				if (getItem().getType() == 8 || getItem().getType() == 12) {
					os.writeC(0x43);
					os.writeC(0x2b);// �ټ�
				} else if (getItem().getType() == 9 || getItem().getType() == 11) {
					os.writeC(0x43);
					os.writeC(0x2c);// ����
				} else if (getItem().getType() == 10) {
					os.writeC(0x43);
					os.writeC(0x2d);// ����
				} else {
					os.writeC(0);
					os.writeC(-1);
				}
			} else {
				os.writeC(0);
				os.writeC(0);
			}

		} catch (Exception e) {
		}
		return os.getBytes();
	}
	
	
	public int getWarehouseType() {
		int Type = 7;
		/** �ŷ��Ұ� ������ üũ */
		if (!getItem().isTradable() || getBless() >= 128) Type -= 2;
		/** â�� �̿� �������� üũ */
		if (getItemId() >= 66757 && getItemId() <= 66788) Type = 3;
		return Type;
	}
	
	/** Ÿ���� ������ ��Ŷ ���� */
	public int getStatusType() {
		int Type = 0;
		if (getItem().getType2() == 0) Type = -128;
		/** Ȯ�� ��Ȯ�� */
		if (isIdentified()) Type += 1;
		/** ��ȯ �Ұ� */
		if (!getItem().isTradable() || getBless() >= 128) Type += 2;
		/** ���� �Ұ� */
		if (getItem().isCantDelete()) Type += 4;
		/** ��þ �Ұ� */
		if (getItem().get_safeenchant() == -1 || getBless() >= 128) Type += 8;
		return Type;
	}
	
	
	public int getShowItemListBit(){
		/**7ǥ�� 2����*/
		int b = 7;
		if (!getItem().isRetrieve()) {
			b = 2;
		}
		return b;
	}

	public byte[] getStatusBytes(L1PcInstance pc) {
		byte[] data = getStatusBytes();
		@SuppressWarnings("resource")
		BinaryOutputStream os = new BinaryOutputStream();
		try {
			os.write(data);
			L1ArmorSets set = ArmorSetTable.getInstance().getArmorSets(getItem().getSetId());

			if (set != null && getItem().getMainId() == getItem().getItemId()) {
				os.writeC(0x45);
				os.writeC(2);
				if (set.getAc() != 0) {
					os.writeC(_add_ac);
					os.writeC(set.getAc());
				}
				if (getItem().getItemId() == 20099) {
					os.writeC(_poly);
					os.writeH(1175);// ����
				} else if (getItem().getItemId() == 20100) {
					os.writeC(_poly);
					os.writeH(18692);// ������
				} else if (getItem().getItemId() == 20151) {
					os.writeC(_poly);
					os.writeH(2118);// �ɷ��Ͻ�
				} else if (getItem().getItemId() == 20118) {
					os.writeC(_poly);
					os.writeH(2117);// �˶����
				}

				if (set.getShortHitup() != 0) {
					os.writeC(_hit);
					os.writeC(set.getShortHitup());
				}
				if (set.getShortDmgup() != 0) {
					os.writeC(_dmg);
					os.writeC(set.getShortDmgup());
				}

				if (set.getLongHitup() != 0) {
					os.writeC(_bowhit);
					os.writeC(set.getLongHitup());
				}
				if (set.getLongDmgup() != 0) {
					os.writeC(_bowdmg);
					os.writeC(set.getLongDmgup());
				}

				if (set.getHpr() != 0) {
					os.writeC(_hpr);
					os.writeC(set.getHpr());
				}
				if (set.getMpr() != 0) {
					os.writeC(_mpr);
					os.writeC(set.getMpr());
				}

				if (set.getHp() != 0) {
					os.writeC(_maxhp);
					os.writeH(set.getHp());
				}
				if (set.getMp() != 0) {
					os.writeC(_maxmp);
					os.writeC(set.getMp());
				}

				if (set.getMr() != 0) {
					os.writeC(_mr);
					os.writeH(set.getMr());
				}

				if (set.getSp() != 0) {
					os.writeC(_sp);
					os.writeC(set.getSp());
				}

				if (set.getfire() != 0) {
					os.writeC(_fire);
					os.writeC(set.getfire());
				}
				if (set.getwater() != 0) {
					os.writeC(_water);
					os.writeC(set.getwater());
				}
				if (set.getwind() != 0) {
					os.writeC(_wind);
					os.writeC(set.getwind());
				}
				if (set.getearth() != 0) {
					os.writeC(_earth);
					os.writeC(set.getearth());
				}

				if (set.getStr() != 0) {
					os.writeC(_str);
					os.writeC(set.getStr());
				}
				if (set.getDex() != 0) {
					os.writeC(_dex);
					os.writeC(set.getDex());
				}
				if (set.getCon() != 0) {
					os.writeC(_con);
					os.writeC(set.getCon());
				}
				if (set.getWis() != 0) {
					os.writeC(_wis);
					os.writeC(set.getWis());
				}
				if (set.getIntl() != 0) {
					os.writeC(_int);
					os.writeC(set.getIntl());
				}
				if (set.getCha() != 0) {
					os.writeC(_cha);
					os.writeC(set.getCha());
				}
				os.writeC(0x45);
				os.writeC(0);
			}

			if (getItem().getType2() == 2) {
				if (getItem().getType() == 8 || getItem().getType() == 12) {
					os.writeC(0x43);
					os.writeC(0x2b);// �ټ�
				} else if (getItem().getType() == 9 || getItem().getType() == 11) {
					os.writeC(0x43);
					os.writeC(0x2c);// ����
				} else if (getItem().getType() == 10) {
					os.writeC(0x43);
					os.writeC(0x2d);// ����
				} else {
					os.writeC(0);
					os.writeC(-1);
				}
			} else {
				os.writeC(0);
				os.writeC(0);
			}

		} catch (Exception e) {
		}
		return os.getBytes();
	}

	private String spirit() {
		int lvl = getRegistLevel();
		String in = "";
		switch (lvl) {
		case 1:
			in = "������ ��(I)";
			break;
		case 2:
			in = "������ ��(II)";
			break;
		case 3:
			in = "������ ��(III)";
			break;
		case 4:
			in = "������ ��(IV)";
			break;
		case 5:
			in = "������ ��(V)";
			break;
		default:
			break;
		}
		return in;
	}

	public EnchantTimer getSkill() {
		return _timer;
	}

	public void getSkillExit() {
		_timer.cancel();
		_isRunning = false;
		_timer = null;
		setAcByMagic(0);
		setDmgByMagic(0);
		setHolyDmgByMagic(0);
		setHitByMagic(0);
	}

	public class EnchantTimer implements Runnable {

		private int skillId = 0;
		private int time = 0;
		private boolean cancel = false;

		public EnchantTimer() {
		}

		public EnchantTimer(int skillid, int _time) {
			skillId = skillid;
			time = _time;
		}

		@Override
		public void run() {
			try {
				if (cancel)
					return;
				time--;
				if (time > 0) {
					GeneralThreadPool.getInstance().schedule(this, 1000);
					return;
				}

				int type = getItem().getType();
				int type2 = getItem().getType2();
				int itemId = getItem().getItemId();
				if (_pc != null && _pc.getInventory().checkItem(itemId)) {
					if (type == 2 && type2 == 2 && isEquipped()) {
						_pc.getAC().addAc(3);
						_pc.sendPackets(new S_OwnCharStatus(_pc), true);
						switch (skillId) {
						case L1SkillId.BLESSED_ARMOR:
							if (_pc != null && _pc.getInventory().checkItem(getItemId()) && isEquipped()) {
								_pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 748, 0, false, false));
							}
							break;
						default:
							break;
						}
					}
				}
				// _pc.sendPackets(new S_ServerMessage(308, getLogName()));
				switch (skillId) {
				case L1SkillId.ENCHANT_WEAPON:
					if (_pc != null && _pc.getInventory().checkItem(itemId) && isEquipped()) {
						_pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0, _isSecond, false));
					}
					break;
				case L1SkillId.SHADOW_FANG:
					if (_pc != null && _pc.getInventory().checkItem(itemId) && isEquipped()) {
						_pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951, 0, false, false));
					}
					break;
				default:
					break;
				}
				setAcByMagic(0);
				setDmgByMagic(0);
				setHolyDmgByMagic(0);
				setHitByMagic(0);
				_isRunning = false;
				_timer = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public int getSkillId() {
			return skillId;
		}

		public int getTime() {
			return time;
		}

		public void cancel() {
			cancel = true;
		}

	}

	private int _acByMagic = 0;
	private int _hitByMagic = 0;
	private int _holyDmgByMagic = 0;
	private int _dmgByMagic = 0;

	public int getAcByMagic() {
		return _acByMagic;
	}

	public void setAcByMagic(int i) {
		_acByMagic = i;
	}

	public int getDmgByMagic() {
		return _dmgByMagic;
	}

	public void setDmgByMagic(int i) {
		_dmgByMagic = i;
	}

	public int getHolyDmgByMagic() {
		return _holyDmgByMagic;
	}

	public void setHolyDmgByMagic(int i) {
		_holyDmgByMagic = i;
	}

	public int getHitByMagic() {
		return _hitByMagic;
	}

	public void setHitByMagic(int i) {
		_hitByMagic = i;
	}

	public void setSkillArmorEnchant(L1PcInstance pc, int skillId, int skillTime) {
		int type = getItem().getType();
		int type2 = getItem().getType2();
		if (_isRunning) {
			_timer.cancel();
			int itemId = getItem().getItemId();
			if (pc != null && pc.getInventory().checkItem(itemId)) {
				if (type == 2 && type2 == 2 && isEquipped()) {
					pc.getAC().addAc(3);
					pc.sendPackets(new S_OwnCharStatus(pc), true);
				}
			}
			setAcByMagic(0);
			_isRunning = false;
			_timer = null;
			switch (skillId) {
			case L1SkillId.BLESSED_ARMOR:
				if (pc != null && pc.getInventory().checkItem(getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 748, 0, false, false));
				}
				break;
			default:
				break;
			}
		}
		if (type == 2 && type2 == 2 && isEquipped()) {
			pc.getAC().addAc(-3);
			pc.sendPackets(new S_OwnCharStatus(pc));
			switch (skillId) {
			case L1SkillId.BLESSED_ARMOR:
				if (pc != null && pc.getInventory().checkItem(getItem().getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 748, skillTime / 1000, false, false));
				}
				break;
			default:
				break;
			}
		}
		setAcByMagic(3);
		_pc = pc;
		_timer = new EnchantTimer(skillId, skillTime / 1000);
		GeneralThreadPool.getInstance().schedule(_timer, 1000);
		// (new Timer()).schedule(_timer, skillTime);
		_isRunning = true;
	}

	public void setSkillWeaponEnchant(L1PcInstance pc, int skillId, int skillTime) {
		if (getItem().getType2() != 1) {
			return;
		}
		if (_isRunning) {
			_timer.cancel();
			setDmgByMagic(0);
			setHolyDmgByMagic(0);
			setHitByMagic(0);
			_isRunning = false;
			_timer = null;
			switch (skillId) {
			case L1SkillId.ENCHANT_WEAPON:
				if (pc != null && pc.getInventory().checkItem(getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0, _isSecond, false));
				}
				break;
			case L1SkillId.SHADOW_FANG:
				if (pc != null && pc.getInventory().checkItem(getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951, 0, false, false));
				}
				break;
			default:
				break;
			}
		}

		switch (skillId) {
		case L1SkillId.HOLY_WEAPON:
			setHolyDmgByMagic(1);
			setHitByMagic(1);
			break;

		case L1SkillId.ENCHANT_WEAPON:
			if (pc != null && pc.getInventory().checkItem(getItem().getItemId()) && isEquipped()) {
				pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, skillTime / 1000, _isSecond, false));
			}
			setDmgByMagic(2);
			break;

		case L1SkillId.SHADOW_FANG:
			if (pc != null && pc.getInventory().checkItem(getItem().getItemId()) && isEquipped()) {
				pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951, skillTime / 1000, false, false));
			}
			setDmgByMagic(5);
			break;

		default:
			break;
		}

		_pc = pc;
		_timer = new EnchantTimer(skillId, skillTime / 1000);
		GeneralThreadPool.getInstance().schedule(_timer, 1000);
		// (new Timer()).schedule(_timer, skillTime);
		_isRunning = true;
	}

	private int �ӱ͸���() {
		int lvl = getEnchantLevel();
		int i = 0;
		switch (lvl) {
		case 3:
			i = 1;
			break;
		case 4:
			i = 1;
			break;
		case 5:
			i = 2;
			break;
		case 6:
			i = 3;
			break;
		case 7:
			i = 4;
			break;
		case 8:
			i = 5;
			break;
		default:
			break;
		}
		return i;
	}

	private int ��ӱ͸���() {
		int lvl = getEnchantLevel();
		int i = 0;
		switch (lvl) {
		case 3:
			i = 1;
			break;
		case 4:
			i = 2;
			break;
		case 5:
			i = 3;
			break;
		case 6:
			i = 4;
			break;
		case 7:
			i = 5;
			break;
		case 8:
			i = 6;
			break;
		default:
			break;
		}
		return i;
	}

	// ** ��Ƽ�� �Ǽ� ���� �߰��κ� **/
	public void startItemOwnerTimer(L1PcInstance pc) {
		setItemOwner(pc);
		L1ItemOwnerTimer timer = new L1ItemOwnerTimer(this, 10000);
		timer.begin();
	}

	private L1EquipmentTimer _equipmentTimer;

	public void startEquipmentTimer(L1PcInstance pc) {
		if (getRemainingTime() > 0) {
			_equipmentTimer = new L1EquipmentTimer(pc, this);
			Timer timer = new Timer(true);
			timer.scheduleAtFixedRate(_equipmentTimer, 1000, 1000);
		}
	}

	public void stopEquipmentTimer() {
		try {
			if (getRemainingTime() > 0 && _equipmentTimer != null) {
				_equipmentTimer.cancel();
				_equipmentTimer = null;
			}
		} catch (Exception e) {
		}
	}

	private L1PcInstance _itemOwner;

	public L1PcInstance getItemOwner() {
		return _itemOwner;
	}

	public void setItemOwner(L1PcInstance pc) {
		_itemOwner = pc;
	}

	private boolean _isNowLighting = false;

	public boolean isNowLighting() {
		return _isNowLighting;
	}

	public void setNowLighting(boolean flag) {
		_isNowLighting = flag;
	}

	private int _secondId;

	public int getSecondId() {
		return _secondId;
	}

	public void setSecondId(int i) {
		_secondId = i;
	}

	private int _roundId;

	public int getRoundId() {
		return _roundId;
	}

	public void setRoundId(int i) {
		_roundId = i;
	}

	private int _ticketId = -1; // Ƽ�� ��ȣ

	public int getTicketId() {
		return _ticketId;
	}

	public void setTicketId(int i) {
		_ticketId = i;
	}

	private int _DropMobId = 0;

	public int isDropMobId() {
		return _DropMobId;
	}

	public void setDropMobId(int i) {
		_DropMobId = i;
	}

	private boolean _isWorking = false;

	public boolean isWorking() {
		return _isWorking;
	}

	public void setWorking(boolean flag) {
		_isWorking = flag;
	}

	// �������� �д�üũ�ؼ� �����ϱ� ���ؼ� �߰�!!
	private int _deleteItemTime = 0;

	public int get_DeleteItemTime() {
		return _deleteItemTime;
	}

	public void add_DeleteItemTime() {
		_deleteItemTime++;
	}

	public void init_DeleteItemTime() {
		_deleteItemTime = 0;
	}

	private int _changeType = -1;

	public void setChangeType(int i) {
		this._changeType = i;
	}

	public int getChangeType() {
		return this._changeType;
	}

	private int _specialEnchant;

	public boolean isSpecialEnchantable() {
		return (_specialEnchant & 0xFF) == 1;
	}

	public void setSpecialEnchantable() {
		_specialEnchant = 1;
	}

	public int getSpecialEnchant() {
		return _specialEnchant;
	}

	public int getSpecialEnchant(int index) {
		return ((_specialEnchant >> (8 * index)) & 0xFF);
	}

	public void setSpecialEnchant(int enchant) {
		_specialEnchant = enchant;
	}

	public void setSpecialEnchant(int index, int enchant) {
		_specialEnchant |= enchant << (8 * index);
	}

	public int getAttrEnchantBit(int attr){
		int attr_bit = 0;
		int result_bit = 0;
		
		switch(attr){
		case 1:
			attr_bit = 1;
			attr = 1;
			break;
		case 2:
			attr_bit = 1;
			attr = 2;
			break;
		case 3:
			attr_bit = 1;
			attr = 3;
			break;
		case 4:
			attr_bit = 1;
			attr = 4;
			break;
		case 5:
			attr_bit = 1;
			attr = 5;
			break;
		case 6:
			attr_bit = 2;
			attr = 1;
			break;
		case 7:
			attr_bit = 2;
			attr = 2;
		case 8:
			attr_bit = 2;
			attr = 3;
			break;
		case 9:
			attr_bit = 2;
			attr = 4;
			break;
		case 10:
			attr_bit = 2;
			attr = 5;
			break;
		case 11:
			attr_bit = 3;
			attr = 1;
			break;
		case 12:
			attr_bit = 3;
			attr = 2;
			break;
		case 13:
			attr_bit = 3;
			attr = 3;
			break;
		case 14:
			attr_bit = 3;
			attr = 4;
			break;
		case 15:
			attr_bit = 3;
			attr = 5;
			break;
		case 16:
			attr_bit = 4;
			attr = 1;
			break;
		case 17:
			attr_bit = 4;
			attr = 2;
			break;
		case 18:
			attr_bit = 4;
			attr = 3;
			break;
		case 19:
			attr_bit = 4;
			attr = 4;
		case 20:
			attr_bit = 4;
			attr = 5;
			break;
		default:
			break;
		}
		
		if(attr > 0){
			result_bit = attr_bit + (16 * attr);
		}
		return result_bit;
	}
	
	public int[] getAttrEnchant(int attr) {
		int attr_bit = 0;
		if (attr >= 1 && attr <= 5) {
			attr_bit = 1;
		}else if (attr >= 6 && attr <= 10) {
			attr_bit = 2;
			attr = attr - 5;
		}else if (attr >= 11 && attr <= 15) {
			attr_bit = 3;
			attr = attr - 10;
		}else if (attr >= 16 && attr <= 20) {
			attr_bit = 4;
			attr = attr - 15;
		}
		return new int[] {attr_bit, attr};
	}
	
        /** ��� ���� **/ //by.���ۻ�̳�
	    public int getTechniqueTolerance() {
		    int result = getItem().get_regist_stun();
	     	int enchantLevel = getEnchantLevel();

		/** ������ ������ ��� ���� **/ //by.���ۻ�̳�
		if ((getItemId() >= 22224 && getItemId() <= 22228) || getItemId() == 222290 || getItemId() == 222291
				|| (getItemId() >= 222330 && getItemId() <= 222336)) {
			result += enchantLevel > 5 ? 5 + (getEnchantLevel() - 6) * 2 : 0;
    	}
		
	    /** (�Ϲ�)ȭ���� Ƽ����, (�Ϲ�)ǳ���� Ƽ����, (�Ϲ�)������ Ƽ����, ��� ���� **/ //by.���ۻ�̳�
		 else if ((getItemId() >= 200852 && getItemId() <= 200854)) {
			result += enchantLevel > 4 ? enchantLevel + 3 : 0;
			if (enchantLevel > 7)
				result += enchantLevel == 8 ? 1 : enchantLevel == 9 ? 3 : enchantLevel == 10 ? 5 : 0;
		}
	    /** �ູ���� ȭ���� Ƽ����, �ູ���� ǳ���� Ƽ����, �ູ���� ������ Ƽ����, ��� ���� **/ //by.���ۻ�̳�
		 else if ((getItemId() >= 30031 && getItemId() <= 30033)) {
			result += enchantLevel > 4 ? enchantLevel + 3 : 0;
			if (enchantLevel > 6)
				result += enchantLevel == 7 ? -1 :enchantLevel == 8 ? 0 : enchantLevel == 9 ? 2 : enchantLevel == 10 ? 4 : 0;
		} 
		/** ������ Ÿ�� 2,8 ����, ��� ���� **/ //by.���ۻ�̳�
		if (getItem().getGrade() != 3 && getItem().getType2() == 2 && (getItem().getType() == 8 || getItem().getType() == 12)) {
			result += enchantLevel > 6 ? enchantLevel - 5 : 0;
		}
	    /** �����ں��� �հ�, ���ְ��� ����, ��� ���� **/ //by.���ۻ�̳�
		if ((getItemId() == 5000007 || getItemId() == 21122)) { 
			result += enchantLevel > 4 ? enchantLevel - 4 : 0;

		}
	    /** �����۷� �Ϲ�&��, ��� ���� **/ //by.���ۻ�̳�
		if (((getItem().getItemId() >= 21246 && getItem().getItemId() <= 21253)) && getEnchantLevel() > 5) {
				result += enchantLevel == 6 ? 5 : enchantLevel == 7 ? 7 : enchantLevel == 8 ? 9 : 0;

		}

		return result;
	}
        /** ���� ���� **/ //by.���ۻ�̳�
	    public int getSpiritTolerance() { 
	    	int result = getItem().get_regist_soul();
	    	int enchantLevel = getEnchantLevel();

	    /** �Ŵ� ���� ������ �ݺ� ����, �Ŵ� ���� ������ ���� ����, �þ��� �ɾ�, �븶������ ���� ���� ���� **/ //by.���ۻ�̳�
		if (getItemId() == 20049 || getItemId() == 20050 || getItemId() == 22009 || getItemId() == 21166 || getItemId() == 2020049) {
			result += enchantLevel > 4 ? enchantLevel - 4 : 0;
			if (result > 8) {
				result = 8;
			}
		}


		return result;
	}
        /** ��� ���� **/ //by.���ۻ�̳�
	    public int getDragonLangTolerance() { 
	    	int result = getItem().get_regist_dragon();
		    int enchantLevel = getEnchantLevel();
		    
	    /**������ ����, (��Ÿ���� �Ϸ�,������,�γ���,����), (��Ǫ������ �Ϸ�,������,�γ���,����), (���������� �Ϸ�,������,�γ���,����), (�߶�ī���� �Ϸ�,������,�γ���,����)  ��� ���� **/ //by.���ۻ�̳�
		if (getItemId() == 20235 || (getItemId() >= 420100 && getItemId() <= 420115)) {
			result += enchantLevel > 4 ? enchantLevel - 4 : 0;
			if (result > 7) {
				result = 7;
			}
		}

	

		return result;
	}
        /** ���� ���� **/ //by.���ۻ�̳�
	    public int getFearTolerance() { 
		   int result = getItem().get_regist_horror();
		   int enchantLevel = getEnchantLevel();

	    /** �����̾��� ����, ���� ���� **/ //by.���ۻ�̳�
		if (getItemId() == 20079) {
			result += enchantLevel > 6 ? enchantLevel - 6 : 0;
			if (result > 7) {
				result = 10;
			}
		}
	    /** ���ְ��� �߰�,�������� �߰�, �븶������ �߰�, �����ں��� �հ�,  ���� ���� **/ //by.���ۻ�̳�
		if ((getItemId() == 5000004 || getItemId() == 5000005 || getItemId() == 5000006 || getItemId() == 5000007)) { 
			result += enchantLevel > 4 ? enchantLevel - 4 : 0;

		}


		return result;
	}
	    /** ��ü ����**/ //by.���ۻ�̳�
	    public int getAllTolerance() {
	    	int result = getItem().get_regist_all();
		    int enchantLevel = getEnchantLevel();
		    
	    /** ���̸����� ����, ����� ����, ��״����� ����, ��ü ���� **/ //by.���ۻ�̳�
		if (getItemId() >= 10164 && getItemId() <= 10166) {
			result += enchantLevel > 4 ? enchantLevel - 4 : 0;
			if (result > 6) {
				result = 6;
			}
		}
	    /** ������ ����, ǳ���� ����, ȭ���� ����, ��ü ���� **/ //by.���ۻ�̳�
		if (getItemId() >= 900021 && getItemId() <= 900023) {
			result += enchantLevel > 5 ? enchantLevel - 5 : 0;
			if (result > 6) {
				result = 6;
			}
		}

		/** �� ���� Ƽ���� ��ü ���� **/
		 else if (getItemId() >= 30031 && getItemId() <= 30033 && getEnchantLevel() >= 7) {
			result += enchantLevel > 7 ? ((enchantLevel - 7) * 2) : 0;
			if (getEnchantLevel() == 6){ 
		  }  else if (getEnchantLevel() >= 7 ){ 
			result += enchantLevel == 7 ? 1 :enchantLevel == 8 ? 0 : enchantLevel == 9 ? 0 : enchantLevel == 10 ? 0 : 0;
			}
		}

	    /** ������ Ƽ����, ��ü ���� **/ //by.���ۻ�̳�
		if (getItemId() == 900019) {
			result += enchantLevel > 5 ? enchantLevel - 5 : 0;
			if (result > 6) {
				result = 6;
			}
		}
	    /** ��� �Ҵ�Ʈ, ���� �Ҵ�Ʈ, ���� �Ҵ�Ʈ, ��ü ���� **/ //by.���ۻ�̳�
		if (getItemId() == 30034 || getItemId() == 30036 || getItemId() == 30037) {
			result += enchantLevel > 5 ? ((enchantLevel - 5) * 2) : 0;
			if (result > 6) {
				result = 6;
			}
		}
		
	    /** ��� �Ҵ�Ʈ, ���� �Ҵ�Ʈ, ���� �Ҵ�Ʈ, ��ü ���� **/ //by.���ۻ�̳�
		if (getItemId() == 30039 || getItemId() == 30040 || getItemId() == 30041) {
			result += enchantLevel > 5 ? ((enchantLevel - 5) * 2 + 1) : 0;
			if (result > 7) {
				result = 7;
			}
		}

		return result;
	}
        /** ��� ���� **/ //by.���ۻ�̳�
	    public int getTechniqueHit() { 
	    	int result = getItem().getTechniqueHit();
		    int enchantLevel = getEnchantLevel();
		    
	    /** ����Ȳ�� �����, ����� ��, �׶�ī���� ����, �����ϻ���� ����, ��� ���� **/ //by.���ۻ�̳�
		if (getItemId() == 61 || getItemId() == 30110 || getItemId() == 30111 || getItemId() == 30112) {
			result += enchantLevel > 0 ? enchantLevel : 0;
		} 
	    /** �߶�ī���� ���, �߶�ī���� ��հ�, ��� ���� **/ //by.���ۻ�̳�
		if (getItemId() == 30117 || getItemId() == 30118) { 
			result += enchantLevel > 7 ? enchantLevel - 7 : 0;
			if (result > 10) {
				result = 10;
			}
		}

		return result;
	}
        /** ���� ���� **/ //by.���ۻ�̳�
     	public int getSpiritHit() { 
		    int result = getItem().getSpiritHit();
	     	int enchantLevel = getEnchantLevel();
	     	
	    /** �����׸����� �̵���, ���̾��� �ݳ�, ����� ��, �����ϻ���� ����,  ���� ���� **/ //by.���ۻ�̳�
		if (getItem().getItemId() == 86 || getItem().getItemId() ==30082 || getItemId() == 30110 || getItemId() == 30112) {
			result += enchantLevel > 0 ? enchantLevel : 0;
		}
	    /** ������ ũ�ο�, ��Ǫ������ ���, �߶�ī���� ���, ��Ǫ������ �̵���,  ���� ���� **/ //by.���ۻ�̳�
		if (getItemId() == 292 || getItemId() == 30121 || getItemId() == 30117 || getItemId() == 30122) { 
			result += enchantLevel > 7 ? enchantLevel - 7 : 0;
			if (result > 10) {
				result = 10;
			}
		}

		return result;
	}
	    /** ��� ���� **/ //by.���ۻ�̳�
	    public int getDragonLangHit() { 
	     	int result = getItem().getDragonLangHit();
	    	int enchantLevel = getEnchantLevel();
		
		/** ���������� ü�μҵ�, ������� **/ //by.���ۻ�̳�
		if (getItemId() == 30119) { //  
			result += enchantLevel > 7 ? enchantLevel - 7 : 0;	
		} 
		
		/** ũ�γ뽺�� ����, ���丮���� ����, ������� **/ //by.���ۻ�̳�
		if (getItemId() == 30080 || getItemId() == 30081) { 
			result += enchantLevel > 0 ? enchantLevel : 0;
		}
		
		/** ����� Ű��ũ, �������� ü�μҵ�, ������� **/ //by.���ۻ�̳�
		if (getItemId() == 7238 || getItemId() == 90084) { 
			result += enchantLevel > 6 ? enchantLevel - 6 : 0;
		}

		return result;
	}
		/** ���� ���� **/ //by.���ۻ�̳�   
	    public int getFearHit() { 
	    	int result = getItem().getFearHit();
		    int enchantLevel = getEnchantLevel();

		/** ��ǳ�� ����, ��ũ������ ��, �߶�ī���� ���, ��Ÿ���� ����, �������� **/ //by.���ۻ�̳�
		if (getItem().getItemId() == 7227 || getItem().getItemId() == 30109 
		 || getItem().getItemId() == 30117 || getItem().getItemId() == 30115) {
			result += enchantLevel > 7 ? enchantLevel - 7 : 0;
		} 
		/** Ÿ��ź�� �г�, ����� ��, �����ϻ���� ����, �������� **/ //by.���ۻ�̳�
		if (getItem().getItemId() == 30083 || getItemId() == 30110 || getItemId() == 30112) {
			result += enchantLevel > 0 ? enchantLevel : 0;
		}

		return result;
	}
		/** ��ü ���� **/ //by.���ۻ�̳� 
	    public int getAllHit() {
	    	int result = getItem().getAllHit();
		    int enchantLevel = getEnchantLevel();

		return result;
	}
	private int ���Ľ�DamageDown() {
		int lvl = getEnchantLevel();
		int reduc = 0;
		switch (lvl) {
		case 0:
			reduc = 7;
			break;
		case 1:
			reduc = 9;
			break;
		case 2:
			reduc = 11;
			break;
		case 3:
			reduc = 13;
			break;
		case 4:
			reduc = 15;
			break;
		case 5:
			reduc = 17;
			break;
		case 6:
			reduc = 19;
			break;
		case 7:
			reduc = 21;
			break;
		case 8:
			reduc = 23;
			break;
		case 9:
			reduc = 25;
			break;
		default:
			if (lvl >= 10) {
				reduc = 27;
			} else {
				reduc = 7;
			}
			break;
		}
		return reduc;
	}
	/** ��ũ ��Ŭ���� üũ */
	public boolean isDogNecklace() {
		L1Pet L1pet = PetTable.getTemplate(getId());
		if(L1pet != null && !L1pet.isProduct()){
			return true;
		}
		return false;
	}
}
