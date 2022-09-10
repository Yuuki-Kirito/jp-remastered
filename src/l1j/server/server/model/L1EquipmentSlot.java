/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.model;

import static l1j.server.server.model.skill.L1SkillId.DETECTION;
import static l1j.server.server.model.skill.L1SkillId.ENCHANT_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.EXTRA_HEAL;
import static l1j.server.server.model.skill.L1SkillId.GREATER_HASTE;
import static l1j.server.server.model.skill.L1SkillId.HASTE;
import static l1j.server.server.model.skill.L1SkillId.HEAL;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;

import java.util.ArrayList;

import l1j.server.Config;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ACTION_UI;
import l1j.server.server.serverpackets.S_Ability;
import l1j.server.server.serverpackets.S_AddSkill;
import l1j.server.server.serverpackets.S_DelSkill;
import l1j.server.server.serverpackets.S_Invis;
import l1j.server.server.serverpackets.S_ItemStatus;
import l1j.server.server.serverpackets.S_MaanIcons;
import l1j.server.server.serverpackets.S_NewAddSkill;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_OtherCharPacks;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_QuestTalkIsland;
import l1j.server.server.serverpackets.S_SMPacketBox;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_SabuBox;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillIconBlessOfEva;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1QuestInfo;

public class L1EquipmentSlot {

	private L1PcInstance _owner;

	private ArrayList<L1ArmorSet> _currentArmorSet;

	private L1ItemInstance _weapon;

	private ArrayList<L1ItemInstance> _armors;

	public L1EquipmentSlot(L1PcInstance owner) {
		_owner = owner;

		_armors = new ArrayList<L1ItemInstance>();
		_currentArmorSet = new ArrayList<L1ArmorSet>();
	}

	private void setWeapon(L1ItemInstance weapon, boolean doubleweapon) {
		if (doubleweapon) {
			_owner.setSecondWeapon(weapon);
			_owner.setCurrentWeapon(88);
			weapon._isSecond = true;
		} else {
			_owner.setWeapon(weapon);
			_owner.setCurrentWeapon(weapon.getItem().getType1());
		}

		weapon.startEquipmentTimer(_owner);
		_weapon = weapon;

		int itemId = weapon.getItem().getItemId();
		if (itemId >= 11011 && itemId <= 11013) {
			L1PolyMorph.doPoly(_owner, 8768, 0, L1PolyMorph.MORPH_BY_LOGIN);
		} // �߰�

		if (weapon.getItemId() == 261) { // ����� ������
			_owner.addMpr(weapon.getEnchantLevel());
		}
		/** Ŭ����� ����� ������ ����Ʈ �Ϸ� �κ� */
		if ((weapon.getItemId() == 29 || weapon.getItemId() == 142) && _owner.getLevel() > 1 && _owner.getLevel() <= Config.Ŭ����Ʒ���) {
			L1QuestInfo info = _owner.getQuestList(277);
			if (info != null && info.end_time == 0) {
				info.ck[0] = 1;
				_owner.sendPackets(new S_QuestTalkIsland(_owner, 277, info));
			}
		}

		if (weapon.getItemId() == 134 || weapon.getItemId() == 30081) {
			if (weapon.getEnchantLevel() >= 1) {
				_owner.getAbility().addSp((weapon.getEnchantLevel()));
				_owner.sendPackets(new S_SPMR(_owner));
			}
		}

		if (weapon.getItemId() == 30082) {
			if (weapon.getEnchantLevel() >= 0) {
				_owner.addBowDmgup((weapon.getEnchantLevel() + 9));
			}
		}

		if (weapon.getItem().getType2() == 1 && (weapon.getItem().getType() == 7 || weapon.getItem().getType() == 17) && weapon.getStepEnchantLevel() != 0) {
			_owner.getAbility().addSp(weapon.getStepEnchantLevel());
			_owner.sendPackets(new S_SPMR(_owner));
			// _owner.getAbility().addAddedInt(weapon.getEnchantLevel());
			_owner.sendPackets(new S_OwnCharStatus(_owner));

		} else

		if (weapon.getItem().getType2() == 1 && weapon.getItem().getType() != 7 && weapon.getItem().getType() != 17 && weapon.getStepEnchantLevel() != 0) {
			_owner.addDmgup(weapon.getStepEnchantLevel());
		}

		if (weapon.get_durability() > 0)
			_owner.sendPackets(new S_PacketBox(S_PacketBox.����ջ󸶿콺, weapon.get_durability()), true);

		if (weapon.getSkill() != null && weapon.getSkill().getSkillId() != 0) {
			switch (weapon.getSkill().getSkillId()) {
			case L1SkillId.HOLY_WEAPON:
				_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2165, weapon.getSkill().getTime(), false, false));
				break;
			case L1SkillId.ENCHANT_WEAPON:
				_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, weapon.getSkill().getTime(), doubleweapon, false));
				break;
			case L1SkillId.SHADOW_FANG:
				_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951, weapon.getSkill().getTime(), false, false));
				break;
			default:
				break;
			}
		}
	}

	public L1ItemInstance getWeapon() {
		return _weapon;
	}

	private void setArmor(L1ItemInstance armor, boolean loaded) {
		L1Item item = armor.getItem();
		int itemlvl = armor.getEnchantLevel();
		int itemtype = armor.getItem().getType();
		int itemId = armor.getItem().getItemId();
		int itemgrade = armor.getItem().getGrade();
		int RegistLevel = armor.getRegistLevel();

		if ((itemtype >= 8 && itemtype <= 12) || itemtype == 16 || itemtype == 20 || itemtype == 21) { // ����, ���� �߰�
			if (itemId == 20016 || itemId == 20294 || itemId == 120016 || itemId == 220294) {
				_owner.getAC().addAc(armor.getAcByMagic());
				_owner.getAC().addAc(item.get_ac());
			} else {
				_owner.getAC().addAc(item.get_ac() - armor.getAcByMagic());
			}
		} else {
			if (itemId == 20016 || itemId == 20294 || itemId == 120016 || itemId == 220294) {
				_owner.getAC().addAc(item.get_ac());
				_owner.getAC().addAc(armor.getEnchantLevel());
				_owner.getAC().addAc(armor.getAcByMagic());
			} else {
				_owner.getAC().addAc(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() + armor.get_durability());
			}
		}
		if (itemId == 420104 || itemId == 420105 || itemId == 420106 || itemId == 420107) {
			_owner.startPapuBlessing();
		}
		
		if (itemId >= 27528 && itemId <= 27530) {
			_owner.set_halpas_armor(true);
			_owner.set_halpas_armor_enchant(armor.getEnchantLevel());
			if (!_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HALPAS_FAITH_DELAY)) {
			_owner.sendPackets(new S_MaanIcons(L1SkillId.HALPAS_FAITH_STANDBY, true, -1));
			}
		}
		
		if (itemId == 27528) {
			_owner.addDmgCritical(5);
		}
		
		if (itemId == 27529) {
			_owner.addBowDmgCritical(5);
		}
		
		if (itemId == 27530) {
			_owner.addMagicCritical(5);
		}
		
		if (itemId >= 420108 && itemId <= 420111) {
			_owner.startLindBlessing();
		}
		if (itemId == 21255)
			_owner.startHalloweenArmorBlessing();

		_owner.addDamageReductionByArmor(item.getDamageReduction());
		_owner.addWeightReduction(item.getWeightReduction());

		if (item.getWeightReduction() != 0) {
			_owner.sendPackets(new S_NewCreateItem("����", _owner));
		}
		if (itemId == 7246) {
			if (itemlvl > 5) {
				int en = itemlvl - 5;
				_owner.addWeightReduction(en * 60);
			}
		}
		if (itemId == 130220) { // �ݺ����尩
			switch (itemlvl) {
			case 7:
				_owner.addHitup(4);
				break;
			case 8:
				_owner.addHitup(5);
				break;
			case 9:
				_owner.addHitup(6);
				break;
			case 10:
				_owner.addHitup(7);
				break;
			case 11:
				_owner.addHitup(8);
				break;
			default:
				break;
			}
		}

		if (itemgrade == 3 && itemId == 30036) {// �������Ҵ�Ʈ
			double perhp = itemlvl < 7 ? ((2.000 + ((double) itemlvl * 2.000))) / 100 : ((((double) itemlvl * 3.000) - 4.000)) / 100;
			double permp = itemlvl < 8 ? ((1.000 + ((double) itemlvl))) / 100 : ((2.000 + ((double) itemlvl))) / 100;
			int addhp = (int) ((double) _owner.getBaseMaxHp() * perhp);
			int addmp = (int) ((double) _owner.getBaseMaxMp() * permp);
			switch (itemlvl) {
			case 0:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 1:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 2:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 3:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 4:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addDmgup(1);
				break;
			case 5:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addDmgup(2);
				break;
			case 6:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addDmgup(3);
				_owner.getResistance().addcalcPcDefense(2);
				_owner.addDg(2);
				break;
			case 7:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addDmgup(4);
				_owner.getResistance().addcalcPcDefense(4);
				_owner.addDg(4);
				break;
			case 8:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addDmgup(5);
				_owner.getResistance().addcalcPcDefense(6);
				_owner.addDg(6);
				break;
			default:
				break;
			}
		}

		//�ູ���������Ҵ�Ʈ�߰�
				if (itemgrade == 3 && itemId == 30040) {// �������Ҵ�Ʈ(�ູ)
					double perhp = itemlvl < 5 ? ((4.000 + ((double) itemlvl * 2.000))) / 100 : ((((double) itemlvl * 3.000) - 1.000)) / 100 ;
					double permp = itemlvl < 8 ? ((1.000 + ((double) itemlvl))) / 100 : ((6.000 + ((double) itemlvl))) / 100 ;
					int addhp = (int) ((double) _owner.getBaseMaxHp() * perhp);
					int addmp = (int) ((double) _owner.getBaseMaxMp() * permp);
					switch (itemlvl) {
					case 1:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						break;
					case 2:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						break;
					case 3:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDmgup(1);
						break;
					case 4:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDmgup(2);
						break;
					case 5:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDmgup(3);
						_owner.getResistance().addcalcPcDefense(2);
						_owner.addDg(2);
						break;
					case 6:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDmgup(4);
						_owner.getResistance().addcalcPcDefense(4);
						_owner.addDg(4);
						break;
					case 7:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDmgup(5);
						_owner.getResistance().addcalcPcDefense(6);
						_owner.addDg(6);
						break;
					case 8:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDmgup(8);
						_owner.getResistance().addcalcPcDefense(8);
						_owner.addDg(8);
						break;
					default:
						break;
					}
				}
		//�߰���
		
		if (itemgrade == 3 && itemId == 30034) {// ������Ҵ�Ʈ
			double perhp = itemlvl < 3 ? ((1.000 + ((double) itemlvl))) / 100 : ((((double) itemlvl * 2.000) - 1.000)) / 100;
			double permp = itemlvl < 3 ? ((1.000 + ((double) itemlvl))) / 100 : ((((double) itemlvl * 2.000) - 1.000)) / 100;
			int addhp = (int) ((double) _owner.getBaseMaxHp() * perhp);
			int addmp = (int) ((double) _owner.getBaseMaxMp() * permp);
			switch (itemlvl) {
			case 0:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 1:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 2:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 3:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 4:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(1);
				break;
			case 5:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(2);
				break;
			case 6:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(3);
				_owner.getResistance().addcalcPcDefense(2);
				_owner.addDg(2);
				break;
			case 7:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(4);
				_owner.getResistance().addcalcPcDefense(4);
				_owner.addDg(4);
				break;
			case 8:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(5);
				_owner.getResistance().addcalcPcDefense(6);
				_owner.addDg(6);
				break;
			default:
				break;
			}
		}
		
		if (itemgrade == 3 && itemId == 30039) {// ������Ҵ�Ʈ(�ູ)
			double perhp = itemlvl < 8 ? ((((double) itemlvl * 2.000) + 1.000)) / 100 : ((((double) itemlvl * 2.000) + 2.000)) / 100;
			double permp = itemlvl < 8 ? ((((double) itemlvl * 2.000) + 1.000)) / 100 : ((((double) itemlvl * 2.000) + 2.000)) / 100;
			int addhp = (int) ((double) _owner.getBaseMaxHp() * perhp);
			int addmp = (int) ((double) _owner.getBaseMaxMp() * permp);
			switch (itemlvl) {
			case 1:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 2:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 3:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(1);
				break;
			case 4:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(2);
				break;
			case 5:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(3);
				_owner.getResistance().addcalcPcDefense(2);
				_owner.addDg(2);
				break;
			case 6:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(4);
				_owner.getResistance().addcalcPcDefense(4);
				_owner.addDg(4);
				break;
			case 7:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(5);
				_owner.getResistance().addcalcPcDefense(6);
				_owner.addDg(6);
				break;
			case 8:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addBowDmgup(8);
				_owner.getResistance().addcalcPcDefense(8);
				_owner.addDg(8);
				break;
			default:
				break;
			}
		}
//�߰���		

		if (itemgrade == 3 && itemId == 30037) {// ������ �Ҵ�Ʈ
			double perhp = itemlvl < 8 ? ((1.000 + ((double) itemlvl))) / 100 : ((2.000 + ((double) itemlvl))) / 100;
			double permp = itemlvl < 7 ? ((2.000 + ((double) itemlvl * 2.000))) / 100 : ((((double) itemlvl * 3.000) - 4.000)) / 100;
			int addhp = (int) ((double) _owner.getBaseMaxHp() * perhp);
			int addmp = (int) ((double) _owner.getBaseMaxMp() * permp);
			switch (itemlvl) {
			case 0:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 1:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 2:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 3:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 4:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(1);
				break;
			case 5:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(2);
				break;
			case 6:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(3);
				_owner.getResistance().addcalcPcDefense(2);
				_owner.addDg(2);
				break;
			case 7:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(4);
				_owner.getResistance().addcalcPcDefense(4);
				_owner.addDg(4);
				break;
			case 8:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(5);
				_owner.getResistance().addcalcPcDefense(6);
				_owner.addDg(6);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_SPMR(_owner));
		}

		if (itemgrade == 3 && itemId == 30041) {// ������ �Ҵ�Ʈ(�ູ)
			double perhp = itemlvl < 8 ? ((1.000 + ((double) itemlvl))) / 100 : ((6.000 + ((double) itemlvl))) / 100 ;
			double permp = itemlvl < 5 ? ((4.000 + ((double) itemlvl * 2.000))) / 100 : ((((double) itemlvl * 3.000) - 1.000)) / 100 ;
			int addhp = (int) ((double) _owner.getBaseMaxHp() * perhp);
			int addmp = (int) ((double) _owner.getBaseMaxMp() * permp);
			switch (itemlvl) {
			case 1:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 2:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 3:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(1);
				break;
			case 4:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(2);
				break;
			case 5:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(3);
				_owner.getResistance().addcalcPcDefense(2);
				_owner.addDg(2);
				break;
			case 6:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(4);
				_owner.getResistance().addcalcPcDefense(4);
				_owner.addDg(4);
				break;
			case 7:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(5);
				_owner.getResistance().addcalcPcDefense(6);
				_owner.addDg(6);
				break;
			case 8:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.getAbility().addSp(8);
				_owner.getResistance().addcalcPcDefense(8);
				_owner.addDg(8);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_SPMR(_owner));
		}		
//�߰���
		
		if (itemgrade == 3 && itemId == 30035) {// ��ɲ��� �Ҵ�Ʈ
			double perhp = itemlvl < 8 ? ((1.000 + ((double) itemlvl))) / 100 : ((2.000 + ((double) itemlvl))) / 100;
			double permp = itemlvl < 8 ? ((1.000 + ((double) itemlvl))) / 100 : ((2.000 + ((double) itemlvl))) / 100;
			int addhp = (int) ((double) _owner.getBaseMaxHp() * perhp);
			int addmp = (int) ((double) _owner.getBaseMaxMp() * permp);
			switch (itemlvl) {
			case 0:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 1:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 2:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 3:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 4:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 5:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				break;
			case 6:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addDamageReductionByArmor(2);
				break;
			case 7:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addDamageReductionByArmor(4);
				break;
			case 8:
				_owner.setPendentHp(addhp);
				_owner.addMaxHp(addhp);
				_owner.setPendentMp(addmp);
				_owner.addMaxMp(addmp);
				_owner.addDamageReductionByArmor(6);
				break;
			default:
				break;
			}
		}

		//�ູ���� ��ɲ��� �Ҵ�Ʈ �߰�
				if (itemgrade == 3 && itemId == 30038) {// ��ɲ��� �Ҵ�Ʈ(�ູ)
					double perhp = itemlvl < 8 ? ((1.000 + ((double) itemlvl))) / 100 : ((2.000 + ((double) itemlvl))) / 100 ;
					double permp = itemlvl < 8 ? ((1.000 + ((double) itemlvl))) / 100 : ((2.000 + ((double) itemlvl))) / 100 ;
					int addhp = (int) ((double) _owner.getBaseMaxHp() * perhp);
					int addmp = (int) ((double) _owner.getBaseMaxMp() * permp);
					switch (itemlvl) {
					case 1:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						break;
					case 2:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						break;
					case 3:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						break;
					case 4:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						break;
					case 5:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDamageReductionByArmor(2);
						break;
					case 6:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDamageReductionByArmor(4);
						break;
					case 7:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDamageReductionByArmor(6);
						break;
					case 8:
						_owner.setPendentHp(addhp);
						_owner.addMaxHp(addhp);
						_owner.setPendentMp(addmp);
						_owner.addMaxMp(addmp);
						_owner.addDamageReductionByArmor(6);
						break;
					default:
						break;
					}
				}
		//�߰���
		
		_owner.addDmgupByArmor(item.getDmgup());
		_owner.addBowHitupByArmor(item.getBowHitup());
		_owner.addBowDmgupByArmor(item.getBowDmgup());
		_owner.getResistance().addEarth(item.get_defense_earth());
		_owner.getResistance().addWind(item.get_defense_wind());
		_owner.getResistance().addWater(item.get_defense_water());
		_owner.getResistance().addFire(item.get_defense_fire());

		// �ؼ������۸�����
		_owner.getResistance().addcalcPcDefense(item.get_regist_calcPcDefense());
		_owner.getResistance().addPVPweaponTotalDamage(item.get_regist_PVPweaponTotalDamage());

		_armors.add(armor);

		for (L1ArmorSet armorSet : L1ArmorSet.getAllSet()) {
			if (armorSet.isPartOfSet(itemId) && armorSet.isValid(_owner)) {
				if (armor.getItem().getType2() == 2 && (armor.getItem().getType() == 9 || armor.getItem().getType() == 11)) {
					if (!armorSet.isEquippedRingOfArmorSet(_owner)) {
						armorSet.giveEffect(_owner);
						_currentArmorSet.add(armorSet);
						if (item.getMainId() != 0) {
							L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId());
							if (main != null) {
								if (main.isEquipped())
									_owner.sendPackets(new S_ItemStatus(main, _owner, true, true));
							}
						}
						if (item.getMainId2() != 0) {
							L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId2());
							if (main != null) {
								if (main.isEquipped())
									_owner.sendPackets(new S_ItemStatus(main, _owner, true, true));
							}
						}
						if (item.getMainId3() != 0) {
							L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId3());
							if (main != null) {
								if (main.isEquipped())
									_owner.sendPackets(new S_ItemStatus(main, _owner, true, true));
							}
						}

					}
				} else {
					armorSet.giveEffect(_owner);
					_currentArmorSet.add(armorSet);
					if (item.getMainId() != 0) {
						L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId());
						if (main != null) {
							if (main.isEquipped())
								_owner.sendPackets(new S_ItemStatus(main, _owner, true, true));
						}
					}
					if (item.getMainId2() != 0) {
						L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId2());
						if (main != null) {
							if (main.isEquipped())
								_owner.sendPackets(new S_ItemStatus(main, _owner, true, true));
						}
					}
					if (item.getMainId3() != 0) {
						L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId3());
						if (main != null) {
							if (main.isEquipped())
								_owner.sendPackets(new S_ItemStatus(main, _owner, true, true));
						}
					}
				}
			}
		}
		if (itemId >= 490000 && itemId <= 490017) {
			_owner.getResistance().addFire(RegistLevel * 2);
			_owner.getResistance().addWind(RegistLevel * 2);
			_owner.getResistance().addEarth(RegistLevel * 2);
			_owner.getResistance().addWater(RegistLevel * 2);
		}

		if (itemId == 420100 || itemId == 420100 || itemId == 420100 || itemId == 420100) {
			if (itemlvl == 7) {
				_owner.addDamageReductionByArmor(1);
			}
			if (itemlvl == 8) {
				_owner.addDamageReductionByArmor(2);
			}
			if (itemlvl == 9) {
				_owner.addDamageReductionByArmor(3);
			}
		}
		if (itemId == 27528 || itemId == 27529 || itemId == 27530) {// ���Ľ����ʸ�����
			if (itemlvl == 0) {
				_owner.addDamageReductionByArmor(7);
			}
			if (itemlvl == 1) {
				_owner.addDamageReductionByArmor(9);
			}
			if (itemlvl == 2) {
				_owner.addDamageReductionByArmor(11);
			}
			if (itemlvl == 3) {
				_owner.addDamageReductionByArmor(13);
			}
			if (itemlvl == 4) {
				_owner.addDamageReductionByArmor(15);
			}
			if (itemlvl == 5) {
				_owner.addDamageReductionByArmor(17);
			}
			if (itemlvl == 6) {
				_owner.addDamageReductionByArmor(19);
			}
			if (itemlvl == 7) {
				_owner.addDamageReductionByArmor(21);
			}
			if (itemlvl == 8) {
				_owner.addDamageReductionByArmor(23);
			}
			if (itemlvl == 9) {
				_owner.addDamageReductionByArmor(25);
			}
			if (itemlvl == 10) {
				_owner.addDamageReductionByArmor(27);
			}
		}

		if (armor.getItemId() == 20107 || armor.getItemId() == 120107) {
			if (armor.getEnchantLevel() >= 3) {
				_owner.getAbility().addSp((armor.getEnchantLevel() - 2));
				_owner.sendPackets(new S_SPMR(_owner));
			}
		}
		if (armor.getItemId() == 10170) {
			if (armor.getEnchantLevel() >= 7) {
				_owner.getAbility().addSp((armor.getEnchantLevel() - 6));
				_owner.sendPackets(new S_SPMR(_owner));
			}
		}
		if (RegistLevel == 10) {// �ǵ��� ���ɹ���
			_owner.getResistance().addFire(10);
			_owner.getResistance().addWind(10);
			_owner.getResistance().addEarth(10);
			_owner.getResistance().addWater(10);
		} else if (RegistLevel == 11) {// �ǵ��� ��������
			_owner.addMaxMp(30);
		} else if (RegistLevel == 12) {// �ǵ��� ü�¹���
			_owner.addMaxHp(50);
		} else if (RegistLevel == 13) {// �ǵ��� �긶����
			_owner.getResistance().addMr(10);
		} else if (RegistLevel == 14) {// �ǵ��� ��ö����
			_owner.getAC().addAc(-1);
		} else if (RegistLevel == 15) {// �ǵ��� ȸ������
			_owner.addHpr(1);
			_owner.addMpr(1);
		}

		if (itemId == 423014) {
			_owner.startAHRegeneration();
		}
		if (itemId == 423015) {
			_owner.startSHRegeneration();
		}
		if (itemId == 20380) {
			_owner.startHalloweenRegeneration();
		}

		if (armor.getAttrEnchantLevel() > 0) { // ������
			switch (armor.getAttrEnchantLevel()) {
			case 41:
				_owner.addDamageReductionByArmor(1);
				break;
			case 42:
				_owner.addDamageReductionByArmor(2);
				break;
			case 43:
				_owner.addDamageReductionByArmor(3);
				break;
			case 44:
				_owner.addDamageReductionByArmor(4);
				break;
			case 45:
				_owner.addDamageReductionByArmor(5);
				break;
			case 46:
				_owner.addDamageReductionByArmor(6);
				break;
			case 47:
				_owner.addDamageReductionByArmor(7);
				break;
			case 48:
				_owner.addDamageReductionByArmor(8);
				break;
			case 49:
				_owner.addDamageReductionByArmor(9);
				break;
			case 50:
				_owner.addDamageReductionByArmor(10);
				break;
			case 51:
				_owner.addDamageReductionByArmor(11);
				break;
			case 52:
				_owner.addDamageReductionByArmor(12);
				break;
			case 53:
				_owner.addDamageReductionByArmor(13);
				break;
			case 54:
				_owner.addDamageReductionByArmor(14);
				break;
			case 55:
				_owner.addDamageReductionByArmor(15);
				break;
			case 56:
				_owner.addDamageReductionByArmor(16);
				break;
			case 57:
				_owner.addDamageReductionByArmor(17);
				break;
			case 58:
				_owner.addDamageReductionByArmor(18);
				break;
			case 59:
				_owner.addDamageReductionByArmor(19);
				break;
			case 60:
				_owner.addDamageReductionByArmor(20);
				break;
			case 61:
				_owner.addDamageReductionByArmor(21);
				break;
			case 62:
				_owner.addDamageReductionByArmor(22);
				break;
			case 63:
				_owner.addDamageReductionByArmor(23);
				break;
			case 64:
				_owner.addDamageReductionByArmor(24);
				break;
			case 65:
				_owner.addDamageReductionByArmor(25);
				break;
			case 66:
				_owner.addDamageReductionByArmor(26);
				break;
			case 67:
				_owner.addDamageReductionByArmor(27);
				break;
			case 68:
				_owner.addDamageReductionByArmor(28);
				break;
			case 69:
				_owner.addDamageReductionByArmor(29);
				break;
			case 70:
				_owner.addDamageReductionByArmor(30);
				break;
			default:
				break;
			}
		}

		if (itemId == 20077 || itemId == 20062 || itemId == 120077) {
			if (!_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.INVISIBILITY)) {
				for (L1DollInstance doll : _owner.getDollList()) {
					doll.deleteDoll();
					_owner.sendPackets(new S_SkillIconGFX(56, 0));
					_owner.sendPackets(new S_OwnCharStatus(_owner));
				}
				_owner.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.BLIND_HIDING);
				_owner.getSkillEffectTimerSet().setSkillEffect(L1SkillId.INVISIBILITY, 0);
				_owner.sendPackets(new S_Invis(_owner.getId(), 1));
				Broadcaster.broadcastPacket(_owner, new S_Invis(_owner.getId(), 1));

				if (_owner.isInParty()) {
					for (L1PcInstance tar : L1World.getInstance().getVisiblePlayer(_owner, -1)) {
						if (_owner.getParty().isMember(tar)) {
							tar.sendPackets(new S_OtherCharPacks(_owner, tar, true));
						}
					}
				}

				// S_RemoveObject sremove = new S_RemoveObject(_owner.getId());

				for (L1PcInstance pc2 : L1World.getInstance().getVisiblePlayer(_owner)) {
					// pc2.sendPackets(sremove);
					if (pc2.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_FLOATING_EYE)
							&& pc2.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CURSE_BLIND)) {
						pc2.sendPackets(new S_OtherCharPacks(_owner, pc2, true));
					}
				}

			}
		}

		if (itemId == 20288) {
			_owner.sendPackets(new S_Ability(1, true));
		}
		if (itemId == 5001130) {
			_owner.sendPackets(new S_Ability(1, true));
		}
		if (itemId == 20281 || itemId == 120281) {
			_owner.sendPackets(new S_Ability(2, true));
		}
		if (itemId == 20284 || itemId == 120284) {
			_owner.sendPackets(new S_Ability(5, true));
		}
		if (itemId == 20036) {
			_owner.sendPackets(new S_Ability(3, true));
		}
		if (itemId == 20207) {
			_owner.sendPackets(new S_SkillIconBlessOfEva(_owner.getId(), -1));
		}
		if (itemId == 21097) {// �������� ����
			switch (itemlvl) {
			case 5:
			case 6:
				_owner.getAbility().addSp(1);
				break;
			case 7:
			case 8:
				_owner.getAbility().addSp(2);
				break;
			default:
				if (itemlvl >= 9)
					_owner.getAbility().addSp(3);
				break;
			}
		}
		if (itemId == 21095) { // ü���� ����
			switch (itemlvl) {
			case 5:
			case 6:
				_owner.addMaxHp(25);
				break;
			case 7:
			case 8:
				_owner.addMaxHp(50);
				break;
			default:
				if (itemlvl >= 9)
					_owner.addMaxHp(75);
				break;
			}
		}
		if (itemId == 21096) {// ��ȣ�� ����
			switch (itemlvl) {
			case 5:
			case 6:
				_owner.addDamageReductionByArmor(1);
				break;
			case 7:
			case 8:
				_owner.addDamageReductionByArmor(2);
				break;
			default:
				if (itemlvl >= 9)
					_owner.addDamageReductionByArmor(3);
				break;
			}
		}

		if (itemId == 420000) {// ��� ����� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addBowDmgup(1);
				break;
			case 5:
			case 6:
				_owner.addBowDmgup(2);
				break;
			case 7:
			case 8:
				_owner.addBowDmgup(3);
				break;
			default:
				if (itemlvl >= 9)
					_owner.addBowDmgup(4);
				break;
			}
		}
		if (itemId == 900022) {// ǳ���ǰ���
			switch (itemlvl) {
			case 0:
				_owner.addBowDmgup(1);
			case 1:
				_owner.addBowDmgup(1);
			case 2:
				_owner.addBowDmgup(1);
			case 3:
				_owner.addBowDmgup(1);
			case 4:
				_owner.addBowDmgup(1);
				break;
			case 5:
				_owner.addBowDmgup(1);
				_owner.addBowHitup(1);
				break;
			case 6:
				_owner.addBowDmgup(1);
				_owner.addBowHitup(2);
				break;
			case 7:
				_owner.addBowDmgup(2);
				_owner.addBowHitup(3);
				break;
			case 8:
				_owner.addBowDmgup(3);
				_owner.addBowHitup(4);
				break;
			case 9:
				_owner.addBowDmgup(4);
				_owner.addBowHitup(5);
				break;
			case 10:
				_owner.addBowDmgup(5);
				_owner.addBowHitup(6);
				break;
			default:
				break;
			}
		}
		if (itemId == 900021) {// �����ǰ���
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 7:
				_owner.getAbility().addSp(2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 8:
				_owner.getAbility().addSp(3);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 9:
				_owner.getAbility().addSp(4);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 10:
				_owner.getAbility().addSp(5);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			default:
				break;
			}
		}
		if (itemId == 900020) {// �����ǰ���
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				_owner.addDamageReductionByArmor(1);
				break;
			case 7:
				_owner.addDamageReductionByArmor(2);
				break;
			case 8:
				_owner.addDamageReductionByArmor(3);
				break;
			case 9:
				_owner.addDamageReductionByArmor(4);
				break;
			case 10:
				_owner.addDamageReductionByArmor(5);
				break;
			default:
				break;
			}
		}
		if (itemId == 900023) {// ȭ���ǰ���
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addDmgup(1);
				break;
			case 5:
				_owner.addDmgup(1);
				_owner.addHitup(1);
				break;
			case 6:
				_owner.addDmgup(1);
				_owner.addHitup(2);
				break;
			case 7:
				_owner.addDmgup(2);
				_owner.addHitup(3);
				break;
			case 8:
				_owner.addDmgup(3);
				_owner.addHitup(4);
				break;
			case 9:
				_owner.addDmgup(4);
				_owner.addHitup(5);
				break;
			case 10:
				_owner.addDmgup(5);
				_owner.addHitup(6);
				break;
			default:
				break;
			}
		}
		if (itemId == 10167) {// ����Ʈ�ߵ��� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				_owner.addHitup(1);
				break;
			case 6:
				_owner.addHitup(2);
				break;
			case 7:
				_owner.addHitup(3);
				break;
			case 8:
				_owner.addHitup(4);
				break;
			case 9:
				_owner.addHitup(5);
				break;
			case 10:
				_owner.addHitup(6);
				break;
			default:
				break;
			}
		}

		if (itemId == 10165) {// ����� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addHitup(3);
				break;
			case 5:
				_owner.addHitup(4);
				break;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				_owner.addHitup(5);
				break;
			default:
				break;
			}
		}

		if (itemId == 10166) {// ��״����� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addBowHitup(3);
				break;
			case 5:
				_owner.addBowHitup(4);
				break;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				_owner.addBowHitup(5);
				break;
			default:
				break;
			}
		}

		if (itemId == 10168) {// ��״����� ����, ���̸����� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				_owner.addBowHitup(1);
				break;
			case 6:
				_owner.addBowHitup(2);
				break;
			case 7:
				_owner.addBowHitup(3);
				break;
			case 8:
				_owner.addBowHitup(4);
				break;
			case 9:
				_owner.addBowHitup(5);
				break;
			case 10:
				_owner.addBowHitup(6);
				break;
			default:
				break;
			}
		}

		if (itemId == 420003) {// ��� ������ ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addDmgup(1);
				break;
			case 5:
			case 6:
				_owner.addDmgup(2);
				break;
			case 7:
			case 8:
				_owner.addDmgup(3);
				break;
			default:
				if (itemlvl >= 9)
					_owner.addDmgup(4);
				break;
			}
		}

		if (itemId == 200851) {// ������ Ƽ����
			switch (itemlvl) {
			case 5:
				_owner.getResistance().addMr(4);
				break;
			case 6:
				_owner.getResistance().addMr(5);

				break;
			case 7:
				_owner.getResistance().addMr(6);

				break;
			case 8:
				_owner.getResistance().addMr(8);

				break;
			case 9:
				_owner.getResistance().addMr(11);

				break;
			case 10:
				_owner.getResistance().addMr(14);
				_owner.addMaxHp(100);
				_owner.getResistance().addPVPweaponTotalDamage(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;

			}
		}

		if (itemId == 900019) {
			switch (itemlvl) {
			case 1:
				_owner.addDamageReductionByArmor(1);
			case 2:
				_owner.addDamageReductionByArmor(1);
			case 3:
				_owner.addDamageReductionByArmor(1);
			case 4:
				_owner.addDamageReductionByArmor(1);
			case 5:
				_owner.addDamageReductionByArmor(1);
				break;
			case 6:
				_owner.addDamageReductionByArmor(2);
				break;
			case 7:
				_owner.addDamageReductionByArmor(3);
				break;
			case 8:
				_owner.addDamageReductionByArmor(4);
				break;
			case 9:
				_owner.addDamageReductionByArmor(5);
				break;
			case 10:
				_owner.addDamageReductionByArmor(6);
				break;

			}
		}
		if (itemId == 200852) {// ȭ���� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.addDmgup(1);
				break;
			case 2:
				_owner.addDmgup(1);
				break;
			case 3:
				_owner.addDmgup(1);
				break;
			case 4:
				_owner.addDmgup(1);
				break;
			case 5:
				_owner.addDmgup(1);
				break;
			case 6:
				_owner.addDmgup(1);
				break;
			case 7:
				_owner.addDmgup(1);
				break;
			case 8:
				_owner.addDmgup(1);
				break;
			case 9:
				_owner.addDmgup(2);
				break;
			case 10:
				_owner.addDmgup(2);
				_owner.addMaxHp(100);
				_owner.getResistance().addPVPweaponTotalDamage(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;

			}
		}
		if (itemId == 200853) {// ǳ���� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.addBowDmgup(1);
				break;
			case 2:
				_owner.addBowDmgup(1);
				break;
			case 3:
				_owner.addBowDmgup(1);
				break;
			case 4:
				_owner.addBowDmgup(1);
				break;
			case 5:
				_owner.addBowDmgup(1);
				break;
			case 6:
				_owner.addBowDmgup(1);
				break;
			case 7:
				_owner.addBowDmgup(1);
				break;
			case 8:
				_owner.addBowDmgup(1);
				break;
			case 9:
				_owner.addBowDmgup(2);
				break;
			case 10:
				_owner.addBowDmgup(2);
				_owner.addMaxHp(100);
				_owner.getResistance().addPVPweaponTotalDamage(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;

			}
		}
		if (itemId == 200854) {// ������ Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 2:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 3:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 4:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 5:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 6:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 7:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 8:
				_owner.getAbility().addSp(2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 9:
				_owner.getAbility().addSp(2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 10:
				_owner.getAbility().addSp(3);
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.addMaxHp(100);
				_owner.getResistance().addPVPweaponTotalDamage(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;

			}
		}

		if (itemId == 30030) {// �������� Ƽ����
			switch (itemlvl) {
			case 5:
				_owner.getResistance().addMr(4);
				break;
			case 6:
				_owner.getResistance().addMr(5);

				break;
			case 7:
				_owner.getResistance().addMr(6);

				break;
			case 8:
				_owner.getResistance().addMr(8);

				break;
			case 9:
				_owner.getResistance().addMr(11);

				break;
			case 10:
				_owner.getResistance().addMr(14);
				_owner.addMaxHp(100);
				_owner.getResistance().addPVPweaponTotalDamage(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;

			}
		}
		if (itemId == 30031) {// ��ȭ���� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.addDmgup(1);
				break;
			case 2:
				_owner.addDmgup(1);
				break;
			case 3:
				_owner.addDmgup(1);
				break;
			case 4:
				_owner.addDmgup(1);
				break;
			case 5:
				_owner.addDmgup(1);
				break;
			case 6:
				_owner.addDmgup(1);
				break;
			case 7:
				_owner.addDmgup(1);
				break;
			case 8:
				_owner.addDmgup(1);
				break;
			case 9:
				_owner.addDmgup(2);
				break;
			case 10:
				_owner.addDmgup(2);
				_owner.addMaxHp(100);
				_owner.getResistance().addPVPweaponTotalDamage(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;

			}
		}
		if (itemId == 30032) {// ����ǳ���� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.addBowDmgup(1);
				break;
			case 2:
				_owner.addBowDmgup(1);
				break;
			case 3:
				_owner.addBowDmgup(1);
				break;
			case 4:
				_owner.addBowDmgup(1);
				break;
			case 5:
				_owner.addBowDmgup(1);
				break;
			case 6:
				_owner.addBowDmgup(1);
				break;
			case 7:
				_owner.addBowDmgup(1);
				break;
			case 8:
				_owner.addBowDmgup(1);
				break;
			case 9:
				_owner.addBowDmgup(2);
				break;
			case 10:
				_owner.addBowDmgup(2);
				_owner.addMaxHp(100);
				_owner.getResistance().addPVPweaponTotalDamage(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;

			}
		}
		if (itemId == 30033) {// ������� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 2:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 3:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 4:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 5:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 6:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 7:
				_owner.getAbility().addSp(2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 8:
				_owner.getAbility().addSp(2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 9:
				_owner.getAbility().addSp(2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 10:
				_owner.getAbility().addSp(3);
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.addMaxHp(100);
				_owner.getResistance().addPVPweaponTotalDamage(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;

			}
		}
		if (itemId == 501214) {// ü�� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(10);
				break;
			case 1:
				_owner.addMaxHp(15);
				break;
			case 2:
				_owner.addMaxHp(20);
				break;
			case 3:
				_owner.addMaxHp(25);
				break;
			case 4:
				_owner.addMaxHp(30);
				break;
			case 5:
				_owner.addMaxHp(35);
				break;
			case 6:
				_owner.addMaxHp(40);
				break;
			case 7:
				_owner.addMaxHp(45);
				break;
			case 8:
				_owner.addMaxHp(50);
				break;
			case 9:
				_owner.addMaxHp(55);
				break;
			case 10:
				_owner.addMaxHp(60);
				break;

			}
		}
		/** �ӹ̷ε�հ� **/
		if (itemId == 20017) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(1);
				break;
			case 6:
				_owner.addBowHitup(2);
				break;
			case 7:
				_owner.addBowHitup(3);
				break;
			case 8:
				_owner.addBowHitup(4);
				break;
			case 9:
				_owner.addBowHitup(5);
				break;
			case 10:
				_owner.addBowHitup(6);
				break;
			default:
				break;
			}
		}
		if (itemId == 501211) {// ������ ����
			switch (itemlvl) {

			case 9:
				_owner.getAbility().addSp(1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 10:
				_owner.getAbility().addSp(2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;

			}
		}
		if (itemId == 501212) {// ������ ����
			switch (itemlvl) {
			case 9:
				_owner.addDmgup(1);
				break;
			case 10:
				_owner.addDmgup(2);
				break;

			}
		}
		if (itemId == 501213) {// ������ ����
			switch (itemlvl) {
			case 9:
				_owner.addBowDmgup(1);
				break;
			case 10:
				_owner.addBowDmgup(2);
				break;

			}
		}
		if (itemId == 30083) {
			_owner.set���������(5);
		}
		/** ��ȣ���� Ȱ �� ���Ÿ� ���� **/
		if (itemId == 222343) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(1);
				break;
			case 6:
				_owner.addBowHitup(2);
				break;
			case 7:
				_owner.addBowHitup(3);
				break;
			case 8:
				_owner.addBowHitup(4);
				break;
			case 9:
				_owner.addBowHitup(5);
				break;
			default:
				break;
			}
		}
		/** ��ȣ���� �Ŀ� �۷κ� �ٰŸ� ���� **/
		if (itemId == 222345) {
			switch (itemlvl) {
			case 5:
				_owner.addHitup(1);
				break;
			case 6:
				_owner.addHitup(2);
				break;
			case 7:
				_owner.addHitup(3);
				break;
			case 8:
				_owner.addHitup(4);
				break;
			case 9:
				_owner.addHitup(5);
				break;
			default:
				break;
			}
		}
		/**
		 * ���ְ��� �߰�, MP +70, �ٰŸ� ���� +2, ��æƮ +5���� �ִ� HP +20/+40/+60/+80/+100/+120 ����,
		 * PVP����� ���� �߰�
		 **/ // by.���ۻ�̳�
		if (itemId == 5000004) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(70); // MP +70
				_owner.addHitup(2); // �ٰŸ� ���� +2
				break;
			case 1:
				_owner.addMaxMp(70); // MP +70
				_owner.addHitup(2); // �ٰŸ� ���� +2
				break;
			case 2:
				_owner.addMaxMp(70); // MP +70
				_owner.addHitup(2); // �ٰŸ� ���� +2
				break;
			case 3:
				_owner.addMaxMp(70); // MP +70
				_owner.addHitup(2); // �ٰŸ� ���� +2
				break;
			case 4:
				_owner.addMaxMp(70); // MP +70
				_owner.addHitup(2); // �ٰŸ� ���� +2
				break;
			case 5:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(20); // HP +20
				_owner.addHitup(2); // �ٰŸ� ���� +2
				_owner.getResistance().addcalcPcDefense(1); // PVP����� ���� +1
				break;
			case 6:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(40); // HP +40
				_owner.addHitup(2); // �ٰŸ� ���� +2
				_owner.getResistance().addcalcPcDefense(2); // PVP����� ���� +2
				break;
			case 7:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(60); // HP +60
				_owner.addHitup(2); // �ٰŸ� ���� +2
				_owner.getResistance().addcalcPcDefense(3); // PVP����� ���� +3
				break;
			case 8:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(80); // HP +80
				_owner.addHitup(2); // �ٰŸ� ���� +2
				_owner.getResistance().addcalcPcDefense(4); // PVP����� ���� +4
				break;
			case 9:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(100); // HP +100
				_owner.addHitup(2); // �ٰŸ� ���� +2
				_owner.getResistance().addcalcPcDefense(5); // PVP����� ���� +5
				break;
			case 10:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(120); // HP +120
				_owner.addHitup(2); // �ٰŸ� ���� +2
				_owner.getResistance().addcalcPcDefense(6); // PVP����� ���� +6
				break;
			default:
				break;
			}
		}
		/**
		 * �븶������ �߰�, MP +70, ��æƮ +5���� �ִ� HP +20/+40/+60/+80/+100/+120 ����, ���� ���� +2, PVP
		 * ����� ���� �߰�
		 **/ // by.���ۻ�̳�
		if (itemId == 5000005) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(70); // MP +70
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 1:
				_owner.addMaxMp(70); // MP +70
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 2:
				_owner.addMaxMp(70); // MP +70
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 3:
				_owner.addMaxMp(70); // MP +70
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 4:
				_owner.addMaxMp(70); // MP +70
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 5:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(20); // HP +20
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(1); // PVP����� ���� +1
				break;
			case 6:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(40); // HP +40
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(2); // PVP����� ���� +2
				break;
			case 7:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(60); // HP +60
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(3); // PVP����� ���� +3
				break;
			case 8:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(80); // HP +80
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(4); // PVP����� ���� +4
				break;
			case 9:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(100); // HP +100
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(5); // PVP����� ���� +5
				break;
			case 10:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(120); // HP +120
				_owner.getAbility().addSp(2);// ���� ���� +2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(6); // PVP����� ���� +6
				break;
			default:
				break;
			}
		}
		/**
		 * �������� �߰�, MP +70, +5���� �ִ� HP +20/+40/+60/+80/+100/+120 ����, ���Ÿ� ���� +2, PVP �����
		 * ���� �߰�
		 **/ // by.���ۻ�̳�
		if (itemId == 5000006) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(70); // MP +70
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				break;
			case 1:
				_owner.addMaxMp(70); // MP +70
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				break;
			case 2:
				_owner.addMaxMp(70); // MP +70
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				break;
			case 3:
				_owner.addMaxMp(70); // MP +70
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				break;
			case 4:
				_owner.addMaxMp(70); // MP +70
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				break;
			case 5:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(20); // HP +20
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				_owner.getResistance().addcalcPcDefense(1); // PVP ����� ���� +1
				break;
			case 6:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(40); // HP +40
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				_owner.getResistance().addcalcPcDefense(2); // PVP ����� ���� +2
				break;
			case 7:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(60); // HP +60
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 8:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(80); // HP +80
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				_owner.getResistance().addcalcPcDefense(4); // PVP ����� ���� +4
				break;
			case 9:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(100); // HP +100
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				_owner.getResistance().addcalcPcDefense(5); // PVP ����� ���� +5
				break;
			case 10:
				_owner.addMaxMp(70); // MP +70
				_owner.addMaxHp(120); // HP +120
				_owner.addBowHitup(2); // ���Ÿ� ���� +2
				_owner.getResistance().addcalcPcDefense(6); // PVP ����� ���� +6
				break;
			default:
				break;
			}
		}
		/**
		 * �巡�� �����̾� ����, +7���� �ִ� HP +50/+100/+150/+200 ����, ����� ���� +1 �߰�, PVP ����� ���� +2,
		 * MP +50
		 **/ // by.���ۻ�̳�
		if (itemId == 5000003) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(100); // HP +100
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(2); // PVP ����� ���� +2
				break;
			case 1:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(100); // HP +100
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(2); // PVP ����� ���� +2
				break;
			case 2:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(100); // HP +100
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(2); // PVP ����� ���� +2
				break;
			case 3:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(100); // HP +100
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(2); // PVP ����� ���� +2
				break;
			case 4:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(100); // HP +100
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(2); // PVP ����� ���� +2
				break;
			case 5:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(100); // HP +100
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 6:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(100); // HP +100
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(4); // PVP ����� ���� +4
				break;
			case 7:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(150); // HP +150
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(5); // PVP ����� ���� +5
				break;
			case 8:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(200); // HP +200
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(6); // PVP ����� ���� +6
				break;
			case 9:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(250); // HP +250
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(7); // PVP ����� ���� +7
				break;
			case 10:
				_owner.addMaxMp(50); // MP +50
				_owner.addMaxHp(300); // HP +300
				_owner.addDamageReductionByArmor(1); // ����� ���� +1
				_owner.getResistance().addcalcPcDefense(8); // PVP ����� ���� +8
				break;
			default:
				break;
			}
		}
		/** �븶������ ����, MP +100 **/ // by.���ۻ�̳�
		if (itemId == 21166) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(100); // MP +100
				break;
			case 1:
				_owner.addMaxMp(100); // MP +100
				break;
			case 2:
				_owner.addMaxMp(100); // MP +100
				break;
			case 3:
				_owner.addMaxMp(100); // MP +100
				break;
			case 4:
				_owner.addMaxMp(100); // MP +100
				break;
			case 5:
				_owner.addMaxMp(100); // MP +100
				break;
			case 6:
				_owner.addMaxMp(100); // MP +100
				break;
			case 7:
				_owner.addMaxMp(100); // MP +100
				break;
			case 8:
				_owner.addMaxMp(100); // MP +100
				break;
			case 9:
				_owner.addMaxMp(100); // MP +100
				break;
			case 10:
				_owner.addMaxMp(100); // MP +100
				break;
			default:
				break;
			}
		}

		/** ���������� ������, MP +10 **/ // by.���ۻ�̳�
		if (itemId == 420109) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(10); // MP +10
				break;
			case 1:
				_owner.addMaxMp(10); // MP +10
				break;
			case 2:
				_owner.addMaxMp(10); // MP +10
				break;
			case 3:
				_owner.addMaxMp(10); // MP +10
				break;
			case 4:
				_owner.addMaxMp(10); // MP +10
				break;
			case 5:
				_owner.addMaxMp(10); // MP +10
				break;
			case 6:
				_owner.addMaxMp(10); // MP +10
				break;
			case 7:
				_owner.addMaxMp(10); // MP +10
				break;
			case 8:
				_owner.addMaxMp(10); // MP +10
				break;
			case 9:
				_owner.addMaxMp(10); // MP +10
				break;
			case 10:
				_owner.addMaxMp(10); // MP +10
				break;
			default:
				break;
			}
		}
		/** ��Ǫ������ ������, ���������� �γ���, ������ �����, ������ �����, ������ �������� �����, MP +20 **/ // by.���ۻ�̳�
		if (itemId == 420105 || itemId == 420110 || itemId == 21258 || itemId == 21260 || itemId == 21268) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(20); // MP +20
				break;
			case 1:
				_owner.addMaxMp(20); // MP +20
				break;
			case 2:
				_owner.addMaxMp(20); // MP +20
				break;
			case 3:
				_owner.addMaxMp(20); // MP +20
				break;
			case 4:
				_owner.addMaxMp(20); // MP +20
				break;
			case 5:
				_owner.addMaxMp(20); // MP +20
				break;
			case 6:
				_owner.addMaxMp(20); // MP +20
				break;
			case 7:
				_owner.addMaxMp(20); // MP +20
				break;
			case 8:
				_owner.addMaxMp(20); // MP +20
				break;
			case 9:
				_owner.addMaxMp(20); // MP +20
				break;
			case 10:
				_owner.addMaxMp(20); // MP +20
				break;
			default:
				break;
			}
		}

		/** ��Ǫ������ �γ���, MP +30 **/ // by.���ۻ�̳�
		if (itemId == 420106) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(30); // MP +30
				break;
			case 1:
				_owner.addMaxMp(30); // MP +30
				break;
			case 2:
				_owner.addMaxMp(30); // MP +30
				break;
			case 3:
				_owner.addMaxMp(30); // MP +30
				break;
			case 4:
				_owner.addMaxMp(30); // MP +30
				break;
			case 5:
				_owner.addMaxMp(30); // MP +30
				break;
			case 6:
				_owner.addMaxMp(30); // MP +30
				break;
			case 7:
				_owner.addMaxMp(30); // MP +30
				break;
			case 8:
				_owner.addMaxMp(30); // MP +30
				break;
			case 9:
				_owner.addMaxMp(30); // MP +30
				break;
			case 10:
				_owner.addMaxMp(30); // MP +30
				break;
			default:
				break;
			}
		}
		/** �������� ����, MP +40 **/ // by.���ۻ�̳�
		if (itemId == 420111) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(40); // MP +40
				break;
			case 1:
				_owner.addMaxMp(40); // MP +40
				break;
			case 2:
				_owner.addMaxMp(40); // MP +40
				break;
			case 3:
				_owner.addMaxMp(40); // MP +40
				break;
			case 4:
				_owner.addMaxMp(40); // MP +40
				break;
			case 5:
				_owner.addMaxMp(40); // MP +40
				break;
			case 6:
				_owner.addMaxMp(40); // MP +40
				break;
			case 7:
				_owner.addMaxMp(40); // MP +40
				break;
			case 8:
				_owner.addMaxMp(40); // MP +40
				break;
			case 9:
				_owner.addMaxMp(40); // MP +40
				break;
			case 10:
				_owner.addMaxMp(40); // MP +40
				break;
			default:
				break;
			}
		}
		/** ��Ǫ������ �ķ�, ������� �߰�, ��ɲ��� �߰�, �������� �߰�, MP +50 **/ // by.���ۻ�̳�
		if (itemId == 420107 || itemId == 230000 || itemId == 230001 || itemId == 230002) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(50); // MP +50
				break;
			case 1:
				_owner.addMaxMp(50); // MP +50
				break;
			case 2:
				_owner.addMaxMp(50); // MP +50
				break;
			case 3:
				_owner.addMaxMp(50); // MP +50
				break;
			case 4:
				_owner.addMaxMp(50); // MP +50
				break;
			case 5:
				_owner.addMaxMp(50); // MP +50
				break;
			case 6:
				_owner.addMaxMp(50); // MP +50
				break;
			case 7:
				_owner.addMaxMp(50); // MP +50
				break;
			case 8:
				_owner.addMaxMp(50); // MP +50
				break;
			case 9:
				_owner.addMaxMp(50); // MP +50
				break;
			case 10:
				_owner.addMaxMp(50); // MP +50
				break;
			default:
				break;
			}
		}
		/** �Ŵ� ���� ������ ���� ����, MP +70 **/ // by.���ۻ�̳�
		if (itemId == 20050) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(70); // MP +70
				break;
			case 1:
				_owner.addMaxMp(70); // MP +70
				break;
			case 2:
				_owner.addMaxMp(70); // MP +70
				break;
			case 3:
				_owner.addMaxMp(70); // MP +70
				break;
			case 4:
				_owner.addMaxMp(70); // MP +70
				break;
			case 5:
				_owner.addMaxMp(70); // MP +70
				break;
			case 6:
				_owner.addMaxMp(70); // MP +70
				break;
			case 7:
				_owner.addMaxMp(70); // MP +70
				break;
			case 8:
				_owner.addMaxMp(70); // MP +70
				break;
			case 9:
				_owner.addMaxMp(70); // MP +70
				break;
			case 10:
				_owner.addMaxMp(70); // MP +70
				break;
			default:
				break;
			}
		}
		/** �þ��� �ɾ�, MP +100 **/ // by.���ۻ�̳�
		if (itemId == 22009) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 1:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 2:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 3:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 4:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 5:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 6:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 7:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 8:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 9:
				_owner.addMaxMp(100); // MP + 100
				break;
			case 10:
				_owner.addMaxMp(100); // MP + 100
				break;
			default:
				break;
			}
		}
		/** �����ں��� �հ�, PVP ����� ���� +3 **/ // by.���ۻ�̳�
		if (itemId == 5000007) {
			switch (itemlvl) {
			case 0:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 1:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 2:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 3:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 4:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 5:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 6:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 7:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 8:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 9:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			case 10:
				_owner.getResistance().addcalcPcDefense(3); // PVP ����� ���� +3
				break;
			default:
				break;
			}
		}
		/** �����ں��� �����, �ٰŸ� ���� +4, ���Ÿ� ���� +4, PVP ����� �߰� +3, �߰� */ // by.���ۻ�̳�
		if (itemId == 101010) {
			switch (itemlvl) {
			case 0:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 1:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 2:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 3:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 4:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 5:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 6:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 7:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 8:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 9:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			case 10:
				_owner.addHitup(4); // �ٰŸ� ���� +4
				_owner.addBowHitup(4); // ���Ÿ� ���� +4
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� +3
				break;
			default:
				break;
			}
		}
		/** ���Ľ��� ���� MP +200 **/ // by.���ۻ�̳�
		if (itemId == 27530) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(200); // HP +200
				break;
			case 1:
				_owner.addMaxMp(200); // HP +200
				break;
			case 2:
				_owner.addMaxMp(200); // HP +200
				break;
			case 3:
				_owner.addMaxMp(200); // HP +200
				break;
			case 4:
				_owner.addMaxMp(200); // HP +200
				break;
			case 5:
				_owner.addMaxMp(200); // HP +200
				break;
			case 6:
				_owner.addMaxMp(200); // HP +200
				break;
			case 7:
				_owner.addMaxMp(200); // HP +200
				break;
			case 8:
				_owner.addMaxMp(200); // HP +200
				break;
			case 9:
				_owner.addMaxMp(200); // HP +200
				break;
			case 10:
				_owner.addMaxMp(200); // HP +200
				break;
			default:
				break;
			}
		}
		/** (����/��ø/����/�Ϸ�)�� ���� * : +7���� �ִ� HP +20/+40/+60 ���� : +9�� ����� ����+1 �߰� */
		if (itemId == 21259 || itemId == 1021259 || itemId == 21265 || itemId == 1021265 || itemId == 21266 || itemId == 1021266 || itemId == 30218) {
			switch (itemlvl) {
			case 7:
				_owner.addMaxHp(20);
				break;
			case 8:
				_owner.addMaxHp(40);
				break;
			case 9:
				_owner.addMaxHp(60);
				_owner.addDamageReductionByArmor(1);
				break;
			case 10:
				_owner.addMaxHp(60);
				_owner.addDamageReductionByArmor(1);
				break;
			default:
				break;
			}
		}
		if (itemId == 230000) { // ������� �߰�
			switch (itemlvl) {
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
				_owner.addHitup(1);
				break;

			}
		}

		if (itemId == 231006) { // Ŀ���� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(5);
				break;
			case 1:
				_owner.addMaxHp(10);
				break;
			case 2:
				_owner.addMaxHp(15);
				break;
			case 3:
				_owner.addMaxHp(20);
				break;
			case 4:
				_owner.addMaxHp(25);
				_owner.getAC().addAc(-1);
				break;
			case 5:
				_owner.addMaxHp(30);
				_owner.getAC().addAc(-2);
				_owner.addDmgup(1);
				break;
			case 6:
				_owner.addMaxHp(35);
				_owner.getAC().addAc(-3);
				_owner.addDmgup(2);
				break;
			case 7:
				_owner.addMaxHp(40);
				_owner.getAC().addAc(-3);
				_owner.addDmgup(3);
				break;
			case 8:
				_owner.addMaxHp(50);
				_owner.getAC().addAc(-3);
				_owner.addDmgup(4);
				break;
			default:
			}
		}
		if (itemId == 231004) { // Ŀ���� ��� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(5);
				break;
			case 1:
				_owner.addMaxHp(10);
				break;
			case 2:
				_owner.addMaxHp(15);
				break;
			case 3:
				_owner.addMaxHp(20);
				break;
			case 4:
				_owner.addMaxHp(25);
				_owner.getAC().addAc(-1);
				break;
			case 5:
				_owner.addMaxHp(30);
				_owner.getAC().addAc(-2);
				_owner.addBowDmgup(1);
				break;
			case 6:
				_owner.addMaxHp(35);
				_owner.getAC().addAc(-3);
				_owner.addBowDmgup(2);
				break;
			case 7:
				_owner.addMaxHp(40);
				_owner.getAC().addAc(-3);
				_owner.addBowDmgup(3);
				break;
			case 8:
				_owner.addMaxHp(50);
				_owner.getAC().addAc(-3);
				_owner.addBowDmgup(4);
				break;
			default:
			}
		}
		if (itemId == 231005) { // Ŀ���� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(5);
				break;
			case 1:
				_owner.addMaxHp(10);
				break;
			case 2:
				_owner.addMaxHp(15);
				break;
			case 3:
				_owner.addMaxHp(20);
				break;
			case 4:
				_owner.addMaxHp(25);
				_owner.getAC().addAc(-1);
				break;
			case 5:
				_owner.addMaxHp(30);
				_owner.getAC().addAc(-2);
				_owner.addHitup(1);
				break;
			case 6:
				_owner.addMaxHp(35);
				_owner.getAC().addAc(-3);
				_owner.addHitup(2);
				break;
			case 7:
				_owner.addMaxHp(40);
				_owner.getAC().addAc(-3);
				_owner.addHitup(3);
				break;
			case 8:
				_owner.addMaxHp(50);
				_owner.getAC().addAc(-3);
				_owner.addHitup(4);
				break;
			default:
			}
		}
		if (itemId == 231003) { // Ŀ���� ��ȣ ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(5);
				break;
			case 1:
				_owner.addMaxHp(10);
				break;
			case 2:
				_owner.addMaxHp(15);
				break;
			case 3:
				_owner.addMaxHp(20);
				_owner.getAC().addAc(-1);
				break;
			case 4:
				_owner.addMaxHp(25);
				_owner.getAC().addAc(-2);
				break;
			case 5:
				_owner.addMaxHp(30);
				_owner.getAC().addAc(-3);
				_owner.addDamageReductionByArmor(1);
				break;
			case 6:
				_owner.addMaxHp(35);
				_owner.getAC().addAc(-5);
				_owner.addDamageReductionByArmor(2);
				_owner.getResistance().addMr(3);
				break;
			case 7:
				_owner.addMaxHp(40);
				_owner.getAC().addAc(-6);
				_owner.addDamageReductionByArmor(3);
				_owner.getResistance().addMr(5);
				break;
			case 8:
				_owner.addMaxHp(50);
				_owner.getAC().addAc(-7);
				_owner.addDamageReductionByArmor(4);
				_owner.getResistance().addMr(7);
				break;
			default:
			}
		}
		if (itemId == 231007) { // ��ȣ�� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(5);
				break;
			case 1:
				_owner.addMaxHp(10);
				break;
			case 2:
				_owner.addMaxHp(15);
				break;
			case 3:
				_owner.addMaxHp(20);
				_owner.getAC().addAc(-1);
				break;
			case 4:
				_owner.addMaxHp(25);
				_owner.getAC().addAc(-2);
				break;
			case 5:
				_owner.addMaxHp(30);
				_owner.addDmgup(1);
				_owner.getAC().addAc(-3);
				_owner.addDamageReductionByArmor(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;
			case 6:
				_owner.addMaxHp(35);
				_owner.addDmgup(2);
				_owner.addHitup(1);
				_owner.getAC().addAc(-5);
				_owner.addDamageReductionByArmor(2);
				_owner.getResistance().addMr(3);
				_owner.getResistance().addcalcPcDefense(2);
				break;
			case 7:
				_owner.addMaxHp(40);
				_owner.addDmgup(3);
				_owner.getAC().addAc(-6);
				_owner.addHitup(3);
				_owner.addDamageReductionByArmor(3);
				_owner.getResistance().addMr(5);
				_owner.getResistance().addcalcPcDefense(3);
				break;
			case 8:
				_owner.addMaxHp(50);
				_owner.addDmgup(4);
				_owner.getAC().addAc(-7);
				_owner.addHitup(5);
				_owner.addDamageReductionByArmor(4);
				_owner.getResistance().addMr(7);
				_owner.getResistance().addcalcPcDefense(5);
				break;
			default:
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		if (itemId == 231008) { // ��ȣ�� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(5);
				break;
			case 1:
				_owner.addMaxHp(10);
				break;
			case 2:
				_owner.addMaxHp(15);
				break;
			case 3:
				_owner.addMaxHp(20);
				_owner.getAC().addAc(-1);
				break;
			case 4:
				_owner.addMaxHp(25);
				_owner.getAC().addAc(-2);
				break;
			case 5:
				_owner.addMaxHp(30);
				_owner.addBowDmgup(1);
				_owner.getAC().addAc(-3);
				_owner.addDamageReductionByArmor(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;
			case 6:
				_owner.addMaxHp(35);
				_owner.addBowDmgup(2);
				_owner.addBowHitup(1);
				_owner.getAC().addAc(-5);
				_owner.addDamageReductionByArmor(2);
				_owner.getResistance().addMr(3);
				_owner.getResistance().addcalcPcDefense(2);
				break;
			case 7:
				_owner.addMaxHp(40);
				_owner.addBowDmgup(3);
				_owner.getAC().addAc(-6);
				_owner.addBowHitup(3);
				_owner.addDamageReductionByArmor(3);
				_owner.getResistance().addMr(5);
				_owner.getResistance().addcalcPcDefense(3);
				break;
			case 8:
				_owner.addMaxHp(50);
				_owner.addBowDmgup(4);
				_owner.getAC().addAc(-7);
				_owner.addBowHitup(5);
				_owner.addDamageReductionByArmor(4);
				_owner.getResistance().addMr(7);
				_owner.getResistance().addcalcPcDefense(5);
				break;
			default:
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		if (itemId == 231009) { // ��ȣ�� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(5);
				break;
			case 1:
				_owner.addMaxHp(10);
				break;
			case 2:
				_owner.addMaxHp(15);
				break;
			case 3:
				_owner.addMaxHp(20);
				_owner.getAC().addAc(-1);
				break;
			case 4:
				_owner.addMaxHp(25);
				_owner.getAC().addAc(-2);
				break;
			case 5:
				_owner.addMaxHp(30);
				_owner.addHitup(1);
				_owner.getAC().addAc(-3);
				_owner.addDamageReductionByArmor(1);
				_owner.getResistance().addcalcPcDefense(1);
				break;
			case 6:
				_owner.addMaxHp(35);
				_owner.addHitup(2);
				_owner.getAC().addAc(-5);
				_owner.addDamageReductionByArmor(2);
				_owner.getResistance().addMr(3);
				_owner.getResistance().addcalcPcDefense(2);
				break;
			case 7:
				_owner.addMaxHp(40);
				_owner.getAC().addAc(-6);
				_owner.addHitup(3);
				_owner.addDamageReductionByArmor(3);
				_owner.getResistance().addMr(5);
				_owner.getResistance().addcalcPcDefense(3);
				break;
			case 8:
				_owner.addMaxHp(50);
				_owner.getAC().addAc(-7);
				_owner.addHitup(4);
				_owner.addDamageReductionByArmor(4);
				_owner.getResistance().addMr(7);
				_owner.getResistance().addcalcPcDefense(5);
				break;
			default:
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		/** �������� ��, �ٰŸ� ġ��Ÿ +10 **/
		if (itemId == 100033) {
			switch (itemlvl) {
			case 0:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 1:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 2:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 3:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 4:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 5:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 6:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 7:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 8:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 9:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			case 10:
				_owner.addDmgCritical(10); // �ٰŸ� ġ��Ÿ +10
				break;
			default:
				break;
			}
		}
		/** �׶�ī���� ����, �����ϻ���� ����, ġ��Ÿ + ó�� **/
		if (itemId == 30111 || itemId == 30112){ 
			switch (itemlvl) {
			case 0:
				_owner.addDmgCritical(7);
				break;
			case 1:
				_owner.addDmgCritical(9);
				_owner.addDmgup(2);
				break;
			case 2:
				_owner.addDmgCritical(11);
				_owner.addDmgup(4);
				break;
			case 3:
				_owner.addDmgCritical(13);
				_owner.addDmgup(6);
				break;
			case 4:
				_owner.addDmgCritical(15);
				_owner.addDmgup(8);
				break;
			case 5:
				_owner.addDmgCritical(17);
				_owner.addDmgup(10);
				break;
			case 6:
				_owner.addDmgCritical(19);
				_owner.addDmgup(12);
				break;
			case 7:
				_owner.addDmgCritical(21);
				_owner.addDmgup(14);
				break;
			case 8:
				_owner.addDmgCritical(23);
				_owner.addDmgup(16);
				break;
			case 9:
				_owner.addDmgCritical(25);
				_owner.addDmgup(18);
				break;
			case 10:
				_owner.addDmgCritical(27);
				_owner.addDmgup(20);
				break;	
			default:
				break;
			}
		}
		/** �߶�ī���� �Ϸ�, �ٰŸ� �����+3, �ٰŸ� ġ��Ÿ, PVP ����� ������ ���� **/ // by.���ۻ�̳�
		if (itemId == 420115) {
			switch (itemlvl) {
			case 0:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(3); // �ٰŸ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 1:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(3); // �ٰŸ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 2:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(3); // �ٰŸ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 3:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(3); // �ٰŸ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 4:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(3); // �ٰŸ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 5:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(3); // �ٰŸ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 6:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(3); // �ٰŸ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 7:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(4); // �ٰŸ� ġ��Ÿ +4
				_owner.getResistance().addPVPweaponTotalDamage(4); // PVP ����� ������ ���� +4
				break;
			case 8:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(5); // �ٰŸ� ġ��Ÿ +5
				_owner.getResistance().addPVPweaponTotalDamage(5); // PVP ����� ������ ���� +5
				break;
			case 9:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(6); // �ٰŸ� ġ��Ÿ +6
				_owner.getResistance().addPVPweaponTotalDamage(6); // PVP ����� ������ ���� +6
				break;
			case 10:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(7); // �ٰŸ� ġ��Ÿ +7
				_owner.getResistance().addPVPweaponTotalDamage(7); // PVP ����� ������ ���� +7
				break;
			default:
				break;
			}
		}
		/** �߶�ī���� ������, �ٰŸ� �����+3, �ٰŸ� ġ��Ÿ, PVP ����� ������ ���� **/ // by.���ۻ�̳�
		if (itemId == 420113) {
			switch (itemlvl) {
			case 0:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(2); // �ٰŸ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 1:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(2); // �ٰŸ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 2:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(2); // �ٰŸ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 3:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(2); // �ٰŸ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 4:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(2); // �ٰŸ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 5:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(2); // �ٰŸ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 6:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(2); // �ٰŸ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 7:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(3); // �ٰŸ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(4); // PVP ����� ������ ���� +4
				break;
			case 8:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(4); // �ٰŸ� ġ��Ÿ +4
				_owner.getResistance().addPVPweaponTotalDamage(5); // PVP ����� ������ ���� +5
				break;
			case 9:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(5); // �ٰŸ� ġ��Ÿ +5
				_owner.getResistance().addPVPweaponTotalDamage(6); // PVP ����� ������ ���� +6
				break;
			case 10:
				_owner.addDmgup(3); // �ٰŸ� ����� +3
				_owner.addDmgCritical(6); // �ٰŸ� ġ��Ÿ +6
				_owner.getResistance().addPVPweaponTotalDamage(7); // PVP ����� ������ ���� +7
				break;
			default:
				break;
			}
		}
		/** �߶�ī���� �γ���, MP +30, ���Ÿ� ����� +3, ���Ÿ� ġ��Ÿ, ����� ������ ���� **/ // by.���ۻ�̳�
		if (itemId == 420112) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(2); // ���Ÿ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 1:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(2); // ���Ÿ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 2:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(2); // ���Ÿ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 3:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(2); // ���Ÿ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 4:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(2); // ���Ÿ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 5:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(2); // ���Ÿ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 6:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(2); // ���Ÿ� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 7:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(3); // ���Ÿ� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(4); // PVP ����� ������ ���� +4
				break;
			case 8:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(4); // ���Ÿ� ġ��Ÿ +4
				_owner.getResistance().addPVPweaponTotalDamage(5); // PVP ����� ������ ���� +5
				break;
			case 9:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(5); // ���Ÿ� ġ��Ÿ +5
				_owner.getResistance().addPVPweaponTotalDamage(6); // PVP ����� ������ ���� +6
				break;
			case 10:
				_owner.addMaxMp(30); // MP +30
				_owner.addBowDmgup(3); // ���Ÿ� ����� +3
				_owner.addBowDmgCritical(6); // ���Ÿ� ġ��Ÿ +6
				_owner.getResistance().addPVPweaponTotalDamage(7); // PVP ����� ������ ���� +7
				break;
			default:
				break;
			}
		}
		/** �߶�ī���� ����, MP +50, ���� ġ��Ÿ +2, ����� ������ ���� **/ // by.���ۻ�̳�
		if (itemId == 420114) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(2); // ���� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 1:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(2); // ���� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 2:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(2); // ���� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 3:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(2); // ���� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 4:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(2); // ���� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 5:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(2); // ���� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 6:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(2); // ���� ġ��Ÿ +2
				_owner.getResistance().addPVPweaponTotalDamage(3); // PVP ����� ������ ���� +3
				break;
			case 7:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(3); // ���� ġ��Ÿ +3
				_owner.getResistance().addPVPweaponTotalDamage(4); // PVP ����� ������ ���� +4
				break;
			case 8:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(4); // ���� ġ��Ÿ +4
				_owner.getResistance().addPVPweaponTotalDamage(5); // PVP ����� ������ ���� +5
				break;
			case 9:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(5); // ���� ġ��Ÿ +5
				_owner.getResistance().addPVPweaponTotalDamage(6); // PVP ����� ������ ���� +6
				break;
			case 10:
				_owner.addMaxMp(50); // MP +50
				_owner.addMagicCritical(6); // ���� ġ��Ÿ +6
				_owner.getResistance().addPVPweaponTotalDamage(7); // PVP ����� ������ ���� +7
				break;
			default:
				break;
			}
		}
		/** �Ϸ��� ���� **/
		if (itemId == 490023 || itemId == 30007 || itemId == 30010) {
			switch (itemlvl) {
			case 5:
				_owner.addHitup(1);
				break;
			case 6:
				_owner.addHitup(1);
				_owner.addDmgup(1);
				break;
			case 7:
				_owner.addHitup(2);
				_owner.addDmgup(2);
				break;
			case 8:
				_owner.addHitup(3);
				_owner.addDmgup(3);
				break;
			case 9:
				_owner.addHitup(4);
				_owner.addDmgup(4);
				break;
			case 10:
				_owner.addHitup(5);
				_owner.addDmgup(5);
				break;
			default:
				break;
			}
		}
		/** ������ ���� **/
		if (itemId == 30019) {
			switch (itemlvl) {
			case 5:
				_owner.addHitup(1);
				_owner.addDmgup(1);
				break;
			case 6:
				_owner.addHitup(2);
				_owner.addDmgup(2);
				break;
			case 7:
				_owner.addHitup(3);
				_owner.addDmgup(3);
				break;
			case 8:
				_owner.addHitup(4);
				_owner.addDmgup(4);
				break;
			default:
				break;
			}
		}
		/** ����� ���� **/
		if (itemId == 30020) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(1);
				_owner.addBowDmgup(1);
				break;
			case 6:
				_owner.addBowHitup(2);
				_owner.addBowDmgup(2);
				break;
			case 7:
				_owner.addBowHitup(3);
				_owner.addBowDmgup(3);
				break;
			case 8:
				_owner.addBowHitup(4);
				_owner.addBowDmgup(4);
				break;
			default:
				break;
			}
		}
		/** ������ ���� **/
		if (itemId == 30021) {
			switch (itemlvl) {
			case 5:
				_owner.getAbility().addSp(1);
				break;
			case 6:
				_owner.getAbility().addSp(2);
				break;
			case 7:
				_owner.getAbility().addSp(3);
				break;
			case 8:
				_owner.getAbility().addSp(4);
				break;
			default:
				break;
			}
		}
		/** ��ȣ ������ ���� **/
		if (itemId == 30023) {
			switch (itemlvl) {
			case 5:
				_owner.addHitup(1);
				_owner.addDmgup(1);
				_owner.getResistance().addMr(4);
				break;
			case 6:
				_owner.addHitup(2);
				_owner.addDmgup(2);
				_owner.getResistance().addMr(6);
				break;
			case 7:
				_owner.addHitup(3);
				_owner.addDmgup(3);
				_owner.getResistance().addMr(8);
				break;
			case 8:
				_owner.addHitup(4);
				_owner.addDmgup(4);
				_owner.getResistance().addMr(10);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		/** ��ȣ ����� ���� **/
		if (itemId == 30024) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(1);
				_owner.addBowDmgup(1);
				_owner.getResistance().addMr(4);
				break;
			case 6:
				_owner.addBowHitup(2);
				_owner.addBowDmgup(2);
				_owner.getResistance().addMr(6);
				break;
			case 7:
				_owner.addBowHitup(3);
				_owner.addBowDmgup(3);
				_owner.getResistance().addMr(8);
				break;
			case 8:
				_owner.addBowHitup(4);
				_owner.addBowDmgup(4);
				_owner.getResistance().addMr(10);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		/** ��ȣ ������ ���� **/
		if (itemId == 30025) {
			switch (itemlvl) {
			case 5:
				_owner.getAbility().addSp(1);
				_owner.getResistance().addMr(4);
				break;
			case 6:
				_owner.getAbility().addSp(2);
				_owner.getResistance().addMr(6);
				break;
			case 7:
				_owner.getAbility().addSp(3);
				_owner.getResistance().addMr(8);
				break;
			case 8:
				_owner.getAbility().addSp(4);
				_owner.getResistance().addMr(10);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		/** ��ȣ ���� **/
		if (itemId == 30022) {
			switch (itemlvl) {
			case 4:
				_owner.getResistance().addMr(1);
				break;
			case 5:
				_owner.getResistance().addMr(2);
				break;
			case 6:
				_owner.getResistance().addMr(3);
				break;
			case 7:
				_owner.getResistance().addMr(4);
				break;
			case 8:
				_owner.getResistance().addMr(5);
				break;
			default:
				break;
			}
		}
		/** Ȱ�� **/
		if (itemId == 30006 || itemId == 20191 || itemId == 120191 || itemId == 720191) {
			switch (itemlvl) {
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
				_owner.addBowHitup(2);
				break;

			}
		}
		/** ��ø�� ���� **/
		if (itemId == 490024 || itemId == 30008 || itemId == 30011) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(1);
				break;
			case 6:
				_owner.addBowHitup(1);
				_owner.addBowDmgup(1);
				break;
			case 7:
				_owner.addBowHitup(2);
				_owner.addBowDmgup(2);
				break;
			case 8:
				_owner.addBowHitup(3);
				_owner.addBowDmgup(3);
				break;
			case 9:
				_owner.addBowHitup(4);
				_owner.addBowDmgup(4);
				break;
			case 10:
				_owner.addBowHitup(5);
				_owner.addBowDmgup(5);
				break;
			default:
				break;
			}
		}
		/** ������ ���� **/
		if (itemId == 490025 || itemId == 30009 || itemId == 30012) {
			switch (itemlvl) {
			case 6:
				_owner.getAbility().addSp(1);
				break;
			case 7:
				_owner.getAbility().addSp(2);
				break;
			case 8:
				_owner.getAbility().addSp(3);
				break;
			case 9:
				_owner.getAbility().addSp(4);
				break;
			case 10:
				_owner.getAbility().addSp(5);
				break;
			default:
				break;
			}
		}
		if (itemId >= 900011 && itemId <= 900014) {// ��� �ϼ� ��
			switch (itemlvl) {
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
				_owner.addDamageReductionByArmor(2);
				break;

			}
		}
		if (itemId >= 900015 && itemId <= 900018) {// ��� ���� ��
			switch (itemlvl) {
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
				_owner.addDmgup(2);
				break;

			}
		}
		if (itemId == 20100) {// ���� ��
			switch (itemlvl) {
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
				_owner.addDmgup(2);
				break;

			}
		}
		if (itemId == 20150) {// Ŀ�� ��
			switch (itemlvl) {
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
				_owner.addDamageReductionByArmor(2);
				break;

			}
		}
		if (itemtype >= 8 && itemtype <= 12) {
			if (itemlvl > 0) {
				if (itemgrade != 3) {
					if (itemtype == 8 || itemtype == 12) {// �Ͱ��� �����
						switch (itemlvl) {
						case 1:
							_owner.addMaxHp(5);
							break;
						case 2:
							_owner.addMaxHp(10);
							break;
						case 3:
							_owner.addMaxHp(20);
							break;
						case 4:
							_owner.addMaxHp(30);
							break;
						case 5:
							_owner.addPotionPlus(9);
							_owner.addMaxHp(40);
							_owner.getAC().addAc(-1);
							break;
						case 6:
							_owner.addPotionPlus(9);
							_owner.addMaxHp(40);
							_owner.getAC().addAc(-2);
							break;
						case 7:
							_owner.addPotionPlus(9);
							_owner.addMaxHp(50);
							_owner.getAC().addAc(-3);
							break;
						case 8:
							_owner.addPotionPlus(9);
							_owner.addMaxHp(50);
							_owner.getAC().addAc(-4);
							break;
						case 9:
							_owner.addPotionPlus(9);
							_owner.addMaxHp(60);
							_owner.getAC().addAc(-5);
							break;
						default:
						}
					} else if ((itemgrade >= 0 && itemgrade <= 2) && (itemtype == 9 || itemtype == 11)) { // ����
						switch (itemlvl) {
						case 1:
							_owner.addMaxHp(5);
							break;
						case 2:
							_owner.addMaxHp(10);
							break;
						case 3:
							_owner.addMaxHp(20);
							break;
						case 4:
							_owner.addMaxHp(30);
							break;
						case 5:
							_owner.addDmgup(1);
							_owner.addBowDmgup(1);
							_owner.addMaxHp(40);
							break;
						case 6:
							_owner.addDmgup(2);
							_owner.addBowDmgup(2);
							_owner.addMaxHp(40);
							_owner.getResistance().addMr(1);
							_owner.getResistance().addPVPweaponTotalDamage(1);
							break;
						case 7:
							_owner.addDmgup(3);
							_owner.addBowDmgup(3);
							_owner.addMaxHp(50);
							_owner.getAbility().addSp(1);
							_owner.getResistance().addPVPweaponTotalDamage(2);
							_owner.getResistance().addMr(3);
							break;
						case 8:
							_owner.addDmgup(4);
							_owner.addBowDmgup(4);
							_owner.addMaxHp(50);
							_owner.getAbility().addSp(2);
							_owner.getResistance().addPVPweaponTotalDamage(3);
							_owner.getResistance().addMr(5);
							break;
						case 9:
							_owner.addDmgup(5);
							_owner.addBowDmgup(5);
							_owner.addMaxHp(60);
							_owner.getAbility().addSp(3);
							_owner.getResistance().addPVPweaponTotalDamage(5);
							_owner.getResistance().addMr(7);
							break;
						default:
						}
					} else if (itemtype == 10) {// ��Ʈ
						switch (itemlvl) {
						case 1:
							_owner.addMaxMp(5);
							break;
						case 2:
							_owner.addMaxMp(10);
							break;
						case 3:
							_owner.addMaxMp(20);
							break;
						case 4:
							_owner.addMaxMp(30);
							break;
						case 5:
							_owner.addDamageReductionByArmor(1);
							_owner.addMaxMp(40);
							break;
						case 6:
							_owner.addDamageReductionByArmor(2);
							_owner.addMaxMp(40);
							_owner.addMaxHp(20);
							_owner.getResistance().addcalcPcDefense(1);
							break;
						case 7:
							_owner.addDamageReductionByArmor(3);
							_owner.addMaxMp(50);
							_owner.addMaxHp(30);
							_owner.getResistance().addcalcPcDefense(3);
							break;
						case 8:
							_owner.addDamageReductionByArmor(4);
							_owner.addMaxMp(50);
							_owner.addMaxHp(40);
							_owner.getResistance().addcalcPcDefense(5);
							break;
						case 9:
							_owner.addDamageReductionByArmor(5);
							_owner.addMaxMp(60);
							_owner.addMaxHp(50);
							_owner.getResistance().addcalcPcDefense(7);
							break;
						default:
						}
					}

					/** ����/ȸ���� ���� **/
				} else if (itemId == 490020 || itemId == 490022) {
					switch (itemlvl) {
					case 0:
						_owner.getAC().addAc(-0);
						break;
					case 1:
						_owner.getAC().addAc(-1);
						break;
					case 2:
						_owner.getAC().addAc(-2);
						break;
					case 3:
						_owner.getAC().addAc(-3);
						break;
					case 4:
						_owner.getAC().addAc(-4);
						break;
					case 5:
						_owner.getAC().addAc(-5);
						break;
					case 6:
						_owner.getAC().addAc(-6);
						break;
					case 7:
						_owner.getAC().addAc(-7);
						break;
					case 8:
						_owner.getAC().addAc(-8);
						break;
					case 9:
						_owner.getAC().addAc(-9);
						break;
					case 10:
						_owner.getAC().addAc(-10);
						break;
					default:
						break;
					}

				} else if (itemId == 500007) { // ��Ƽ���� �������Ͱ���
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(20);
						break;
					case 2:
						_owner.addMaxHp(30);
						break;
					case 3:
						_owner.addMaxHp(40);
						_owner.addDamageReductionByArmor(1);
						break;
					case 4:
						_owner.addMaxHp(50);
						_owner.addDamageReductionByArmor(1);
						break;
					case 5:
						_owner.addMaxHp(60);
						_owner.addDamageReductionByArmor(2);
						break;
					case 6:
						_owner.addMaxHp(70);
						_owner.addDamageReductionByArmor(3);
						_owner.getAC().addAc(-7);
						break;
					case 7:
						_owner.addMaxHp(80);
						_owner.addDamageReductionByArmor(4);
						_owner.getAC().addAc(-8);
						_owner.addHitup(1);
						_owner.addBowHitup(1);
						break;
					case 8:
						_owner.addMaxHp(90);
						_owner.addDamageReductionByArmor(5);
						_owner.getAC().addAc(-9);
						_owner.addHitup(3);
						_owner.addBowHitup(3);
						break;
					default:
					}
				} else if (itemId == 502007) { // ��Ƽ���� �������Ͱ���
					switch (itemlvl) {
					case 3:
						_owner.addMaxHp(50);
						_owner.addDamageReductionByArmor(1);
						break;
					case 4:
						_owner.addMaxHp(60);
						_owner.addDamageReductionByArmor(2);
						break;
					case 5:
						_owner.addMaxHp(70);
						_owner.addDamageReductionByArmor(3);
						_owner.getAC().addAc(-7);
						break;
					case 6:
						_owner.addMaxHp(80);
						_owner.addDamageReductionByArmor(4);
						_owner.getAC().addAc(-8);
						_owner.addHitup(1);
						_owner.addBowHitup(1);
						break;
					case 7:
						_owner.addMaxHp(90);
						_owner.addDamageReductionByArmor(5);
						_owner.getAC().addAc(-9);
						_owner.addHitup(3);
						_owner.addBowHitup(3);
						break;
					case 8:
						_owner.addMaxHp(140);
						_owner.addDamageReductionByArmor(6);
						_owner.getAC().addAc(-10);
						_owner.addHitup(5);
						_owner.addBowHitup(5);
						break;
					default:
					}
				} else if (itemId == 500008) { // ��Ƽ���� Ǫ�����Ͱ���
					switch (itemlvl) {
					case 5:
						_owner.getAC().addAc(-1);
						break;
					case 6:
						_owner.getAC().addAc(-2);
						break;
					case 7:
						_owner.getAC().addAc(-2);
						break;
					case 8:
						_owner.getAC().addAc(-3);
						break;
					default:
					}
				} else if (itemId == 502008) { // ��Ƽ���� Ǫ�����Ͱ���
					switch (itemlvl) {
					case 4:
						_owner.getAC().addAc(-1);
						break;
					case 5:
						_owner.getAC().addAc(-2);
						break;
					case 6:
						_owner.getAC().addAc(-2);
						break;
					case 7:
						_owner.getAC().addAc(-3);
						break;
					case 8:
						_owner.getAC().addAc(-4);
						break;
					default:
					}
				} else if (itemId == 500009) { // ��Ƽ���� �������Ͱ���
					switch (itemlvl) {
					case 1:
						_owner.addMaxMp(10);
						break;
					case 2:
						_owner.addMaxMp(15);
						break;
					case 3:
						_owner.addMaxMp(30);
						_owner.getAbility().addSp(1);
						break;
					case 4:
						_owner.addMaxMp(35);
						_owner.getAbility().addSp(1);
						break;
					case 5:
						_owner.addMaxMp(50);
						_owner.getAbility().addSp(2);
						break;
					case 6:
						_owner.addMaxMp(55);
						_owner.getAbility().addSp(2);
						_owner.getAC().addAc(-1);
						break;
					case 7:
						_owner.addMaxMp(70);
						_owner.getAbility().addSp(3);
						_owner.getAC().addAc(-2);
						break;
					case 8:
						_owner.addMaxMp(95);
						_owner.getAbility().addSp(3);
						_owner.getAC().addAc(-3);
						break;
					default:
					}
				} else if (itemId == 502009) { // ��Ƽ���� �������Ͱ���
					switch (itemlvl) {
					case 3:
						_owner.addMaxMp(35);
						_owner.getAbility().addSp(1);
						break;
					case 4:
						_owner.addMaxMp(50);
						_owner.getAbility().addSp(2);
						break;
					case 5:
						_owner.addMaxMp(55);
						_owner.getAbility().addSp(2);
						_owner.getAC().addAc(-1);
						break;
					case 6:
						_owner.addMaxMp(70);
						_owner.getAbility().addSp(3);
						_owner.getAC().addAc(-2);
						break;
					case 7:
						_owner.addMaxMp(95);
						_owner.getAbility().addSp(3);
						_owner.getAC().addAc(-3);
						break;
					case 8:
						_owner.addMaxMp(125);
						_owner.getAbility().addSp(4);
						_owner.getAC().addAc(-4);
						break;
					default:
					}
				} else if ((itemId >= 425109 && itemId <= 425113) || (itemId >= 525109 && itemId <= 525113) || (itemId >= 625109 && itemId <= 625113)) {
					if (itemlvl > 0) {
						_owner.addMaxHp(((itemlvl * 5) + 10));
					}
					switch (itemlvl) {
					case 2:
					case 3:
					case 4:
						_owner.getAC().addAc(-(itemlvl - 1));
						break;
					case 5:
					case 6:
					case 7:
					case 8:
						_owner.getAC().addAc(-4);
						_owner.addDmgup(itemlvl - 4);
						_owner.addBowDmgup(itemlvl - 4);
						break;
					}
				} else if (itemgrade == 3 && itemId == 21247) {// ��������
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(15);
						break;
					case 2:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-1);
						break;
					case 3:
						_owner.addMaxHp(25);
						_owner.getAC().addAc(-2);
						break;
					case 4:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-3);
						break;
					case 5:
						_owner.addMaxHp(35);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(1);
						_owner.addBowDmgup(1);
						break;
					case 6:
						_owner.addMaxHp(40);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(2);
						_owner.addBowDmgup(2);
						break;
					case 7:
						_owner.addMaxHp(45);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(3);
						_owner.addBowDmgup(3);
						break;
					case 8:
						_owner.addMaxHp(50);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(4);
						_owner.addBowDmgup(4);
						break;
					default:
						break;
					}
				} else if (itemgrade == 3 && itemId == 21248) {// ü��
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(15);
						break;
					case 2:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-1);
						break;
					case 3:
						_owner.addMaxHp(25);
						_owner.getAC().addAc(-2);
						break;
					case 4:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-3);
						break;
					case 5:
						_owner.addMaxHp(35);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(1);
						_owner.addBowDmgup(1);
						break;
					case 6:
						_owner.addMaxHp(40);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(2);
						_owner.addBowDmgup(2);
						break;
					case 7:
						_owner.addMaxHp(45);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(3);
						_owner.addBowDmgup(3);
						_owner.addDamageReductionByArmor(1);
						break;
					case 8:
						_owner.addMaxHp(50);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(4);
						_owner.addBowDmgup(4);
						_owner.addDamageReductionByArmor(2);
						break;
					default:
						break;
					}
				} else

				if (itemgrade == 3 && itemId == 21246) { // �����ǹ���
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(5);
						break;
					case 2:
						_owner.addMaxHp(10);
						_owner.getAC().addAc(-1);
						break;
					case 3:
						_owner.addMaxHp(15);
						_owner.getAC().addAc(-2);
						break;
					case 4:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-3);
						break;
					case 5:
						_owner.addMaxHp(25);
						_owner.getAC().addAc(-3);
						_owner.getAbility().addSp(1);
						break;
					case 6:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-4);
						_owner.getAbility().addSp(2);
						break;
					case 7:
						_owner.addMaxHp(35);
						_owner.getAC().addAc(-4);
						_owner.getAbility().addSp(3);
						_owner.getResistance().addPVPweaponTotalDamage(1);
						break;
					case 8:
						_owner.addMaxHp(40);
						_owner.addMaxMp(30);
						_owner.getAC().addAc(-5);
						_owner.getAbility().addSp(4);
						_owner.getResistance().addPVPweaponTotalDamage(2);
						break;
					default:
						break;
					}
				} else if (itemgrade == 3 && itemId == 21249) {// ����ǹ���
					switch (itemlvl) {
					case 1:
						_owner.getAC().addAc(-1);
						break;
					case 2:
						_owner.getAC().addAc(-2);
						break;
					case 3:
						_owner.addMaxHp(5);
						_owner.getAC().addAc(-3);
						break;
					case 4:
						_owner.addMaxHp(10);
						_owner.getAC().addAc(-4);
						break;
					case 5:
						_owner.addMaxHp(15);
						_owner.getAC().addAc(-4);
						_owner.addHitup(1);
						_owner.addBowHitup(1);
						_owner.addDmgup(1);
						_owner.addBowDmgup(1);
						break;
					case 6:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-4);
						_owner.addHitup(2);
						_owner.addBowHitup(2);
						_owner.addDmgup(2);
						_owner.addBowDmgup(2);
						break;
					case 7:
						_owner.addMaxHp(25);
						_owner.getAC().addAc(-4);
						_owner.addHitup(3);
						_owner.addBowHitup(3);
						_owner.addDmgup(3);
						_owner.addBowDmgup(3);
						_owner.getResistance().addPVPweaponTotalDamage(1);
						break;
					case 8:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-4);
						_owner.addHitup(4);
						_owner.addBowHitup(4);
						_owner.addDmgup(4);
						_owner.addBowDmgup(4);
						_owner.getResistance().addPVPweaponTotalDamage(2);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ü�� ���� **/
				} else if (itemgrade == 3 && itemId == 21252) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(15);
						break;
					case 2:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-1);
						break;
					case 3:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-2);
						break;
					case 4:
						_owner.addMaxHp(35);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(1);
						_owner.addBowDmgup(1);
						break;
					case 5:
						_owner.addMaxHp(40);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(2);
						_owner.addBowDmgup(2);
						break;
					case 6:
						_owner.addMaxHp(45);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(3);
						_owner.addBowDmgup(3);
						_owner.addDamageReductionByArmor(1);
						break;
					case 7:
						_owner.addMaxHp(55);
						_owner.getAC().addAc(-5);
						_owner.addDmgup(4);
						_owner.addBowDmgup(4);
						_owner.getResistance().addPVPweaponTotalDamage(1);
						_owner.addDamageReductionByArmor(2);
						break;
					case 8:
						_owner.addMaxHp(65);
						_owner.getAC().addAc(-5);
						_owner.addDmgup(5);
						_owner.addBowDmgup(5);
						_owner.getResistance().addPVPweaponTotalDamage(2);
						_owner.addDamageReductionByArmor(3);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ȸ��,����,���� ���� **/
				} else if (itemgrade == 3 && itemId == 21273 || itemId == 21274 || itemId == 21275) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(15);
						break;
					case 2:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-1);
						break;
					case 3:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-2);
						break;
					case 4:
						_owner.addMaxHp(35);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(1);
						_owner.addBowDmgup(1);
						break;
					case 5:
						_owner.addMaxHp(40);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(2);
						_owner.addBowDmgup(2);
						break;
					case 6:
						_owner.addMaxHp(45);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(3);
						_owner.addBowDmgup(3);
						break;
					case 7:
						_owner.addMaxHp(50);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(4);
						_owner.addBowDmgup(4);
						_owner.getResistance().addPVPweaponTotalDamage(1);
						break;
					case 8:
						_owner.addMaxHp(50);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(5);
						_owner.addBowDmgup(5);
						_owner.getResistance().addPVPweaponTotalDamage(2);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ���� ���� ���� **/
				} else if (itemgrade == 3 && itemId == 21251) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(15);
						break;
					case 2:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-1);
						break;
					case 3:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-2);
						break;
					case 4:
						_owner.addMaxHp(35);
						_owner.getAC().addAc(-3);
						_owner.addDmgup(1);
						_owner.addBowDmgup(1);
						break;
					case 5:
						_owner.addMaxHp(40);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(2);
						_owner.addBowDmgup(2);
						break;
					case 6:
						_owner.addMaxHp(45);
						_owner.getAC().addAc(-4);
						_owner.addDmgup(3);
						_owner.addBowDmgup(3);
						_owner.getResistance().addMr(1);
						break;
					case 7:
						_owner.addMaxHp(50);
						_owner.getAC().addAc(-5);
						_owner.addDmgup(4);
						_owner.addBowDmgup(4);
						_owner.getResistance().addMr(2);
						_owner.getResistance().addPVPweaponTotalDamage(1);
						break;
					case 8:
						_owner.addMaxHp(50);
						_owner.getAC().addAc(-5);
						_owner.addDmgup(5);
						_owner.addBowDmgup(5);
						_owner.getResistance().addMr(3);
						_owner.getResistance().addPVPweaponTotalDamage(2);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ���� ���� **/
				} else if (itemgrade == 3 && itemId == 21250) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(5);
						break;
					case 2:
						_owner.addMaxHp(10);
						_owner.getAC().addAc(-1);
						break;
					case 3:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-2);
						break;
					case 4:
						_owner.addMaxHp(25);
						_owner.getAC().addAc(-3);
						_owner.getAbility().addSp(1);
						break;
					case 5:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-3);
						_owner.getAbility().addSp(2);
						break;
					case 6:
						_owner.addMaxHp(35);
						_owner.getAC().addAc(-3);
						_owner.getAbility().addSp(3);
						break;
					case 7:
						_owner.addMaxHp(40);
						_owner.addMaxMp(15);
						_owner.getAC().addAc(-4);
						_owner.getAbility().addSp(4);
						_owner.getResistance().addPVPweaponTotalDamage(1);
						break;
					case 8:
						_owner.addMaxHp(50);
						_owner.addMaxMp(20);
						_owner.getAC().addAc(-4);
						_owner.getAbility().addSp(5);
						_owner.getResistance().addPVPweaponTotalDamage(2);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ��� ���� **/
				} else
				// �Ƴ�� �ø���
				/*
				 * if (itemId == 90093 ||itemId == 90094 ||itemId == 90095 ||itemId == 90096
				 * ||itemId == 90097 ||itemId == 90098 ||itemId == 90099 ||itemId == 90100) {
				 * switch (itemlvl) {
				 * 
				 * case 7: _owner.addDmgup(3); break; case 8: _owner.addDmgup(5); break; case 9:
				 * _owner.addDmgup(7); break; case 10: _owner.addDmgup(10); break; default:
				 * break; }
				 * 
				 * } else
				 */

				if (itemId == 110051 || itemId == 110052 || itemId == 110053 // ��Ÿ�¹����
						|| itemId == 110054 || itemId == 110055 || itemId == 110056 || itemId == 110057 || itemId == 110058) {
					switch (itemlvl) {

					case 7:
						_owner.addDmgup(3);
						break;
					case 8:
						_owner.addDmgup(5);
						break;
					case 9:
						_owner.addDmgup(7);
						break;
					case 10:
						_owner.addDmgup(10);
						break;
					default:
						break;
					}

				} else

				if (itemgrade == 3 && itemId == 21253) {
					switch (itemlvl) {
					case 1:
						_owner.getAC().addAc(-1);
						break;
					case 2:
						_owner.getAC().addAc(-2);
						break;
					case 3:
						_owner.addMaxHp(10);
						_owner.getAC().addAc(-3);
						break;
					case 4:
						_owner.addMaxHp(15);
						_owner.getAC().addAc(-4);
						_owner.addHitup(1);
						_owner.addBowHitup(1);
						_owner.addDmgup(1);
						_owner.addBowDmgup(1);
						break;
					case 5:
						_owner.addMaxHp(20);
						_owner.getAC().addAc(-4);
						_owner.addHitup(2);
						_owner.addBowHitup(2);
						_owner.addDmgup(2);
						_owner.addBowDmgup(2);
						break;
					case 6:
						_owner.addMaxHp(25);
						_owner.getAC().addAc(-4);
						_owner.addHitup(3);
						_owner.addBowHitup(3);
						_owner.addDmgup(3);
						_owner.addBowDmgup(3);
						break;
					case 7:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-5);
						_owner.addHitup(4);
						_owner.addBowHitup(4);
						_owner.addDmgup(4);
						_owner.addBowDmgup(4);
						_owner.getResistance().addPVPweaponTotalDamage(1);
						break;
					case 8:
						_owner.addMaxHp(30);
						_owner.getAC().addAc(-5);
						_owner.addHitup(5);
						_owner.addBowHitup(5);
						_owner.addDmgup(5);
						_owner.addBowDmgup(5);
						_owner.getResistance().addPVPweaponTotalDamage(2);
						break;
					default:
						break;
					}
				} else if (itemId == 500010 || itemId == 502010) {
					int ac = itemlvl;

					if (item.getBless() == 0 && itemlvl >= 3) {
						ac += 1;
					}
					_owner.getAC().addAc(-ac);
					int dm = itemlvl - 2;
					if (item.getBless() != 0 && itemlvl >= 4)
						dm -= 1;
					_owner.addDmgup(dm);
					_owner.addBowDmgup(dm);
				}
			}
		}
		_owner.set���巹��(���巹��(itemId, itemlvl));
		armor.startEquipmentTimer(_owner);
		/*
		 * if (armor.getSkill() != null && armor.getSkill().getSkillId() != 0) { switch
		 * (armor.getSkill().getSkillId()) { case L1SkillId.BLESSED_ARMOR:
		 * _owner.sendPackets( new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 748,
		 * armor.getSkill().getTime(), false, false)); break; default: break; } }
		 */
	}

	public ArrayList<L1ItemInstance> getArmors() {
		return _armors;
	}

	private void removeWeapon(L1ItemInstance weapon, boolean doubleweapon) {
		if (doubleweapon) {
			_owner.setSecondWeapon(null);
			_owner.setCurrentWeapon(_owner.getWeapon().getItem().getType1());
			_owner.getWeapon()._isSecond = false;
			weapon._isSecond = false;
		} else {
			_owner.setWeapon(null);
			if (_owner.getSecondWeapon() != null) {
				_owner.setWeapon(_owner.getSecondWeapon());
				_owner.sendPackets(new S_SabuBox(S_SabuBox.�������������԰���, _owner.getSecondWeapon().getId(), 8, false));
				_owner.sendPackets(new S_SabuBox(S_SabuBox.�������������԰���, _owner.getSecondWeapon().getId(), 9, true));
				_owner.setSecondWeapon(null);
				_owner.setCurrentWeapon(_owner.getWeapon().getItem().getType1());
			} else {
				_owner.setCurrentWeapon(0);
			}
		}

		weapon.stopEquipmentTimer();
		_weapon = null;
		int itemId = weapon.getItem().getItemId();
		if (itemId >= 11011 && itemId <= 11013) {
			L1PolyMorph.undoPoly(_owner);
		} // �߰�

		if (weapon.getItem().getType2() == 1 && (weapon.getItem().getType() == 7 || weapon.getItem().getType() == 17) && weapon.getStepEnchantLevel() != 0) {
			_owner.getAbility().addSp(-(weapon.getStepEnchantLevel()));
			_owner.sendPackets(new S_SPMR(_owner));
			_owner.sendPackets(new S_OwnCharStatus(_owner));

		} else

		if (weapon.getItemId() == 261) { // ����� ������
			_owner.addMpr(-weapon.getEnchantLevel());
		}

		if (weapon.getItemId() == 134 || weapon.getItemId() == 30081) {
			if (weapon.getEnchantLevel() >= 1) {
				_owner.getAbility().addSp(-(weapon.getEnchantLevel()));
				_owner.sendPackets(new S_SPMR(_owner));
			}
		}
		if (weapon.getItemId() == 30082) {
			if (weapon.getEnchantLevel() >= 0) {
				_owner.addBowDmgup(-(weapon.getEnchantLevel() + 9));
			}
		}

		if (_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COUNTER_BARRIER)) {
			_owner.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.COUNTER_BARRIER);
			_owner.sendPackets(new S_SMPacketBox(S_SMPacketBox.RMSkillIconAura, L1SkillId.COUNTER_BARRIER, 0), true);// ī��
			// �����
		} else if (_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FIRE_BLESS)) {
			_owner.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.FIRE_BLESS);

		} else if (_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HALPHAS)) {
			_owner.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.HALPHAS);

		} else if (_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���丣��)) {
			_owner.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.���丣��);
		} else if (_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���彺��)) {
			_owner.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.���彺��);
		} else if (_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��Ŀ�����̺�)) {
			_owner.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��Ŀ�����̺�);
		} else if (_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�㸮����)) {
			_owner.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.�㸮����);
		}

		if (weapon.getItem().getType2() == 1 && weapon.getItem().getType() != 7 && weapon.getItem().getType() != 17 && weapon.getStepEnchantLevel() != 0) {
			_owner.addDmgup(-weapon.getStepEnchantLevel());
		}

		/*
		 * if (weapon.getBless() >= 128 && (weapon.getItem().getType2() == 1 ||
		 * weapon.getItem().getType2() == 2 || weapon.getItem().getType2() == 3 ||
		 * weapon.getItem().getType2() == 4 || weapon.getItem().getType2() == 5 ||
		 * weapon.getItem().getType2() == 6 || weapon.getItem().getType2() == 7 ||
		 * weapon.getItem().getType2() == 8 || weapon.getItem().getType2() == 9 ||
		 * weapon.getItem().getType2() == 10 || weapon.getItem().getType2() == 11 ||
		 * weapon.getItem().getType2() == 12 || weapon.getItem().getType2() == 13 ||
		 * weapon.getItem().getType2() == 14 || weapon.getItem().getType2() == 15 ||
		 * weapon.getItem().getType2() == 16 || weapon.getItem().getType2() == 17 ||
		 * weapon.getItem().getType2() == 18)) {// ���ι��� _owner.addBowDmgup(-2);
		 * _owner.addDmgup(-2); _owner.getAbility().addSp(-2); _owner.sendPackets(new
		 * S_SPMR(_owner), true); _owner.sendPackets(new
		 * S_NewSkillIcon(L1SkillId.SEAL_BUFF, false)); }
		 */

		if (weapon.get_durability() > 0)
			_owner.sendPackets(new S_PacketBox(S_PacketBox.����ջ󸶿콺, 0), true);

		/*
		 * if(_owner.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLESS_WEAPON )){
		 * _owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2176, 0,
		 * false, false)); }
		 */

		/*
		 * if(_owner.getWeapon() != null){ _owner.sendPackets(new
		 * S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0, false, false));
		 * _owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0,
		 * true, false)); if(_owner.getWeapon().getSkill() != null &&
		 * _owner.getWeapon().getSkill().getSkillId() == L1SkillId.ENCHANT_WEAPON){
		 * _owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747,
		 * _owner.getWeapon().getSkill().getTime(), false, false)); } }else{
		 * _owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0,
		 * false, false)); }
		 */
		try {
			if (weapon.getSkill() != null && weapon.getSkill().getSkillId() != 0) {
				switch (weapon.getSkill().getSkillId()) {
				case L1SkillId.SHADOW_FANG:
					_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951, 0, false, false));
					break;
				case L1SkillId.ENCHANT_WEAPON:
					if (_owner.getWeapon() != null) {
						_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0, false, false));
						_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0, true, false));
						if (_owner.getWeapon().getSkill() != null && _owner.getWeapon().getSkill().getSkillId() == L1SkillId.ENCHANT_WEAPON) {
							_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, _owner.getWeapon().getSkill().getTime(), false, false));
						}
					} else {
						_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0, false, false));
					}
					break;

				/*
				 * if(_owner.getWeapon() != null){ _owner.sendPackets(new
				 * S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2176, 0 , false, false));
				 * if(_owner.getWeapon().getSkill() != null &&
				 * _owner.getWeapon().getSkill().getSkillId() == L1SkillId.BLESS_WEAPON){
				 * _owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2176,
				 * _owner.getWeapon().getSkill().getTime() , false, false)); } }else{
				 * _owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2176, 0 ,
				 * false, false)); } break;
				 */
				case L1SkillId.HOLY_WEAPON:
					if (_owner.getWeapon() != null) {
						_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2165, 0, false, false));
						if (_owner.getWeapon().getSkill() != null && _owner.getWeapon().getSkill().getSkillId() == L1SkillId.HOLY_WEAPON) {
							_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2165, _owner.getWeapon().getSkill().getTime(), false, false));
						}
					} else {
						_owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2165, 0, false, false));
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

	private void removeArmor(L1ItemInstance armor) {
		L1Item item = armor.getItem();
		int itemId = armor.getItem().getItemId();
		int itemlvl = armor.getEnchantLevel();
		int itemtype = armor.getItem().getType();
		int itemgrade = armor.getItem().getGrade();
		int RegistLevel = armor.getRegistLevel();

		if ((itemtype >= 8 && itemtype <= 12) || itemtype == 16 || itemtype == 20 || itemtype == 21) { // ����, ���� �߰�
			if (itemId == 20016 || itemId == 20294 || itemId == 120016 || itemId == 220294) {
				_owner.getAC().addAc(-armor.getAcByMagic());
				_owner.getAC().addAc(-item.get_ac());
			} else {
				_owner.getAC().addAc(-(item.get_ac() - armor.getAcByMagic()));
			}
		} else {
			if (itemId == 20016 || itemId == 20294 || itemId == 120016 || itemId == 220294) {
				_owner.getAC().addAc(-item.get_ac());
				_owner.getAC().addAc(-armor.getEnchantLevel());
				_owner.getAC().addAc(-armor.getAcByMagic());
			} else {
				_owner.getAC().addAc(-(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() + armor.get_durability()));
			}
		}
		if (itemId == 420104 || itemId == 420105 || itemId == 420106 || itemId == 420107) {
			_owner.stopPapuBlessing();
		}
		
		if (itemId >= 27528 && itemId <= 27530) {
			_owner.set_halpas_armor(false);
			_owner.set_halpas_armor_enchant(0);
			_owner.sendPackets(new S_MaanIcons(L1SkillId.HALPAS_FAITH_STANDBY, false, 0));
		}
		
		if (itemId == 27528) {
			_owner.addDmgCritical(-5);
		}
		
		if (itemId == 27529) {
			_owner.addBowDmgCritical(-5);
		}
		
		if (itemId == 27530) {
			_owner.addMagicCritical(-5);
		}
		
		if (itemId >= 420108 && itemId <= 420111) {
			_owner.stopLindBlessing();
		}
		if (itemId == 21255)
			_owner.stopHalloweenArmorBlessing();

		_owner.addDamageReductionByArmor(-item.getDamageReduction());
		_owner.addWeightReduction(-item.getWeightReduction());

		if (itemId == 7246) {
			if (itemlvl > 5) {
				int en = itemlvl - 5;
				_owner.addWeightReduction(-(en * 60));
			}
		}

		if (item.getWeightReduction() != 0) {
			_owner.sendPackets(new S_NewCreateItem("����", _owner));
		}

		if (itemId == 130220) { // �ݺ����尩
			switch (itemlvl) {
			case 7:
				_owner.addHitup(-4);
				break;
			case 8:
				_owner.addHitup(-5);
				break;
			case 9:
				_owner.addHitup(-6);
				break;
			case 10:
				_owner.addHitup(-7);
				break;
			case 11:
				_owner.addHitup(-8);
				break;
			default:
				break;
			}
		}

		if (itemgrade == 3 && itemId == 30036) {// �������Ҵ�Ʈ
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 1:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 2:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 3:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 4:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-1);
				break;
			case 5:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-2);
				break;
			case 6:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-3);
				_owner.getResistance().addcalcPcDefense(-2);
				_owner.addDg(-2);
				break;
			case 7:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-4);
				_owner.getResistance().addcalcPcDefense(-4);
				_owner.addDg(-4);
				break;
			case 8:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-5);
				_owner.getResistance().addcalcPcDefense(-6);
				_owner.addDg(-6);
				break;
			default:
				break;
			}
		}

		if (itemgrade == 3 && itemId == 30034) {// ������Ҵ�Ʈ
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 1:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 2:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 3:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 4:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-1);
				break;
			case 5:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-2);
				break;
			case 6:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-3);
				_owner.getResistance().addcalcPcDefense(-2);
				_owner.addDg(-2);
				break;
			case 7:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-4);
				_owner.getResistance().addcalcPcDefense(-4);
				_owner.addDg(-4);
				break;
			case 8:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-5);
				_owner.getResistance().addcalcPcDefense(-6);
				_owner.addDg(-6);
				break;
			default:
				break;
			}
		}

		if (itemgrade == 3 && itemId == 30037) {// �������Ҵ�Ʈ
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 1:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 2:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 3:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 4:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getAbility().addSp(-1);
				break;
			case 5:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getAbility().addSp(-2);
				break;
			case 6:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getResistance().addcalcPcDefense(-2);
				_owner.addDg(-2);
				_owner.getAbility().addSp(-3);
				break;
			case 7:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getResistance().addcalcPcDefense(-4);
				_owner.addDg(-4);
				_owner.getAbility().addSp(-4);
				break;
			case 8:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getResistance().addcalcPcDefense(-6);
				_owner.addDg(-6);
				_owner.getAbility().addSp(-5);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_SPMR(_owner));
		}

		if (itemgrade == 3 && itemId == 30035) {// ��ɲ��� �Ҵ�Ʈ
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 1:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 2:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 3:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 4:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 5:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 6:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDamageReductionByArmor(-2);
				break;
			case 7:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDamageReductionByArmor(-4);
				break;
			case 8:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDamageReductionByArmor(-6);
				break;
			default:
				break;
			}
		}
		
		if (itemgrade == 3 && itemId == 30040) {// �������Ҵ�Ʈ(�ູ)
			switch (itemlvl) {
			case 1:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 2:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 3:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 4:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-1);
				break;
			case 5:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-2);
				break;
			case 6:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-3);
				_owner.getResistance().addcalcPcDefense(-2);
				_owner.addDg(-2);
				break;
			case 7:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-4);
				_owner.getResistance().addcalcPcDefense(-4);
				_owner.addDg(-4);
				break;
			case 8:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDmgup(-8);
				_owner.getResistance().addcalcPcDefense(-8);
				_owner.addDg(-8);
				break;
			default:
				break;
			}
		}
		if (itemgrade == 3 && itemId == 30039) {// ������Ҵ�Ʈ(�ູ)
			switch (itemlvl) {
			case 1:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 2:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 3:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 4:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-1);
				break;
			case 5:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-2);
				break;
			case 6:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-3);
				_owner.getResistance().addcalcPcDefense(-2);
				_owner.addDg(-2);
				break;
			case 7:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-4);
				_owner.getResistance().addcalcPcDefense(-4);
				_owner.addDg(-4);
				break;
			case 8:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addBowDmgup(-8);
				_owner.getResistance().addcalcPcDefense(-8);
				_owner.addDg(-8);
				break;
			default:
				break;
			}
		}
		
		
		if (itemgrade == 3 && itemId == 30041) {// �������Ҵ�Ʈ
			switch (itemlvl) {
			case 1:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 2:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 3:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 4:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getAbility().addSp(-1);
				break;
			case 5:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getAbility().addSp(-2);
				break;
			case 6:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getResistance().addcalcPcDefense(-2);
				_owner.addDg(-2);
				_owner.getAbility().addSp(-3);
				break;
			case 7:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getResistance().addcalcPcDefense(-4);
				_owner.addDg(-4);
				_owner.getAbility().addSp(-4);
				break;
			case 8:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.getResistance().addcalcPcDefense(-8);
				_owner.addDg(-8);
				_owner.getAbility().addSp(-8);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_SPMR(_owner));
		}
		
		if (itemgrade == 3 && itemId == 30038) {// ��ɲ��� �Ҵ�Ʈ(�ູ)
			switch (itemlvl) {
			case 1:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 2:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 3:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 4:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 5:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				break;
			case 6:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDamageReductionByArmor(-2);
				break;
			case 7:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDamageReductionByArmor(-4);
				break;
			case 8:
				_owner.addMaxHp(-_owner.getPendentHp());
				_owner.setPendentHp(0);
				_owner.addMaxMp(-_owner.getPendentMp());
				_owner.setPendentMp(0);
				_owner.addDamageReductionByArmor(-6);
				break;
			default:
				break;
			}
		}
		_owner.addDmgupByArmor(-item.getDmgup());
		_owner.addBowHitupByArmor(-item.getBowHitup());
		_owner.addBowDmgupByArmor(-item.getBowDmgup());
		_owner.getResistance().addEarth(-item.get_defense_earth());
		_owner.getResistance().addWind(-item.get_defense_wind());
		_owner.getResistance().addWater(-item.get_defense_water());
		_owner.getResistance().addFire(-item.get_defense_fire());

		// �ؼ������۸�����
		_owner.getResistance().addcalcPcDefense(-item.get_regist_calcPcDefense());
		_owner.getResistance().addPVPweaponTotalDamage(-item.get_regist_PVPweaponTotalDamage());

		for (L1ArmorSet armorSet : L1ArmorSet.getAllSet()) {
			if (armorSet.isPartOfSet(itemId) && _currentArmorSet.contains(armorSet) && !armorSet.isValid(_owner)) {
				armorSet.cancelEffect(_owner);
				_currentArmorSet.remove(armorSet);
				if (item.getMainId() != 0) {
					L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId());
					if (main != null) {
						_owner.sendPackets(new S_ItemStatus(main, _owner, true, false));
					}
				}
				if (item.getMainId2() != 0) {
					L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId2());
					if (main != null) {
						_owner.sendPackets(new S_ItemStatus(main, _owner, true, false));
					}
				}
				if (item.getMainId3() != 0) {
					L1ItemInstance main = _owner.getInventory().findItemId(item.getMainId3());
					if (main != null) {
						_owner.sendPackets(new S_ItemStatus(main, _owner, true, false));
					}
				}

			}
		}

		if (itemId >= 490000 && itemId <= 490017) {
			_owner.getResistance().addFire(-RegistLevel * 2);
			_owner.getResistance().addWind(-RegistLevel * 2);
			_owner.getResistance().addEarth(-RegistLevel * 2);
			_owner.getResistance().addWater(-RegistLevel * 2);
		}

		if (armor.getItemId() == 20107 || armor.getItemId() == 120107) {
			if (armor.getEnchantLevel() >= 3) {
				_owner.getAbility().addSp(-(armor.getEnchantLevel() - 2));
				_owner.sendPackets(new S_SPMR(_owner));
			}
		}

		if (armor.getItemId() == 10170) {
			if (armor.getEnchantLevel() >= 7) {
				_owner.getAbility().addSp(-(armor.getEnchantLevel() - 6));
				_owner.sendPackets(new S_SPMR(_owner));
			}
		}

		if (RegistLevel == 10) {// �ǵ��� ���ɹ���
			_owner.getResistance().addFire(-10);
			_owner.getResistance().addWind(-10);
			_owner.getResistance().addEarth(-10);
			_owner.getResistance().addWater(-10);
		} else if (RegistLevel == 11) {// �ǵ��� ��������
			_owner.addMaxMp(-30);
		} else if (RegistLevel == 12) {// �ǵ��� ü�¹���
			_owner.addMaxHp(-50);
		} else if (RegistLevel == 13) {// �ǵ��� �긶����
			_owner.getResistance().addMr(-10);
		} else if (RegistLevel == 14) {// �ǵ��� ��ö����
			_owner.getAC().addAc(1);
		} else if (RegistLevel == 15) {// �ǵ��� ȸ������
			_owner.addHpr(-1);
			_owner.addMpr(-1);
		}

		if (itemId == 423014) {
			_owner.stopAHRegeneration();
		}
		if (itemId == 423015) {
			_owner.stopSHRegeneration();
		}
		if (itemId == 20380) {
			_owner.stopHalloweenRegeneration();
		}
		if (itemId == 420100 || itemId == 420100 || itemId == 420100 || itemId == 420100) {
			if (itemlvl == 7) {
				_owner.addDamageReductionByArmor(-1);
			}
			if (itemlvl == 8) {
				_owner.addDamageReductionByArmor(-2);
			}
			if (itemlvl == 9) {
				_owner.addDamageReductionByArmor(-3);
			}
		}
		if (itemId == 27528 || itemId == 27529 || itemId == 27530) {// ���Ľ����� ������
			if (itemlvl == 0) {
				_owner.addDamageReductionByArmor(-7);
			}
			if (itemlvl == 1) {
				_owner.addDamageReductionByArmor(-9);
			}
			if (itemlvl == 2) {
				_owner.addDamageReductionByArmor(-11);
			}
			if (itemlvl == 3) {
				_owner.addDamageReductionByArmor(-13);
			}
			if (itemlvl == 4) {
				_owner.addDamageReductionByArmor(-15);
			}
			if (itemlvl == 5) {
				_owner.addDamageReductionByArmor(-17);
			}
			if (itemlvl == 6) {
				_owner.addDamageReductionByArmor(-19);
			}
			if (itemlvl == 7) {
				_owner.addDamageReductionByArmor(-21);
			}
			if (itemlvl == 8) {
				_owner.addDamageReductionByArmor(-23);
			}
			if (itemlvl == 9) {
				_owner.addDamageReductionByArmor(-25);
			}
			if (itemlvl == 10) {
				_owner.addDamageReductionByArmor(-27);
			}
		}
		if (itemId == 20077 || itemId == 20062 || itemId == 120077) {
			_owner.delInvis();
		}
		if (itemId == 20288) {
			_owner.sendPackets(new S_Ability(1, false));
		}
		if (itemId == 5001130) {
			_owner.sendPackets(new S_Ability(1, false));
		}
		if (itemId == 20036) {
			_owner.sendPackets(new S_Ability(3, false));
		}
		if (itemId == 20281 || itemId == 120281) {
			_owner.sendPackets(new S_Ability(2, false));
		}
		if (itemId == 20284 || itemId == 120284) {
			_owner.sendPackets(new S_Ability(5, false));
		}
		if (itemId == 20207) {
			_owner.sendPackets(new S_SkillIconBlessOfEva(_owner.getId(), 0));
		}

		if (itemId == 21097) {// �������� ����
			switch (itemlvl) {
			case 5:
			case 6:
				_owner.getAbility().addSp(-1);
				break;
			case 7:
			case 8:
				_owner.getAbility().addSp(-2);
				break;
			default:
				if (itemlvl >= 9)
					_owner.getAbility().addSp(-3);
				break;
			}
		}
		if (itemId == 21095) { // ü���� ����
			switch (itemlvl) {
			case 5:
			case 6:
				_owner.addMaxHp(-25);
				break;
			case 7:
			case 8:
				_owner.addMaxHp(-50);
				break;
			default:
				if (itemlvl >= 9)
					_owner.addMaxHp(-75);
				break;
			}
		}
		if (itemId == 21096) {// ��ȣ�� ����
			switch (itemlvl) {
			case 5:
			case 6:
				_owner.addDamageReductionByArmor(-1);
				break;
			case 7:
			case 8:
				_owner.addDamageReductionByArmor(-2);
				break;
			default:
				if (itemlvl >= 9)
					_owner.addDamageReductionByArmor(-3);
				break;
			}
		}
		if (armor.getAttrEnchantLevel() > 0) { // ������
			switch (armor.getAttrEnchantLevel()) {
			case 41:
				_owner.addDamageReductionByArmor(-1);
				break;
			case 42:
				_owner.addDamageReductionByArmor(-2);
				break;
			case 43:
				_owner.addDamageReductionByArmor(-3);
				break;
			case 44:
				_owner.addDamageReductionByArmor(-4);
				break;
			case 45:
				_owner.addDamageReductionByArmor(-5);
				break;
			case 46:
				_owner.addDamageReductionByArmor(-6);
				break;
			case 47:
				_owner.addDamageReductionByArmor(-7);
				break;
			case 48:
				_owner.addDamageReductionByArmor(-8);
				break;
			case 49:
				_owner.addDamageReductionByArmor(-9);
				break;
			case 50:
				_owner.addDamageReductionByArmor(-10);
				break;
			case 51:
				_owner.addDamageReductionByArmor(-11);
				break;
			case 52:
				_owner.addDamageReductionByArmor(-12);
				break;
			case 53:
				_owner.addDamageReductionByArmor(-13);
				break;
			case 54:
				_owner.addDamageReductionByArmor(-14);
				break;
			case 55:
				_owner.addDamageReductionByArmor(-15);
				break;
			case 56:
				_owner.addDamageReductionByArmor(-16);
				break;
			case 57:
				_owner.addDamageReductionByArmor(-17);
				break;
			case 58:
				_owner.addDamageReductionByArmor(-18);
				break;
			case 59:
				_owner.addDamageReductionByArmor(-19);
				break;
			case 60:
				_owner.addDamageReductionByArmor(-20);
				break;
			case 61:
				_owner.addDamageReductionByArmor(-21);
				break;
			case 62:
				_owner.addDamageReductionByArmor(-22);
				break;
			case 63:
				_owner.addDamageReductionByArmor(-23);
				break;
			case 64:
				_owner.addDamageReductionByArmor(-24);
				break;
			case 65:
				_owner.addDamageReductionByArmor(-25);
				break;
			case 66:
				_owner.addDamageReductionByArmor(-26);
				break;
			case 67:
				_owner.addDamageReductionByArmor(-27);
				break;
			case 68:
				_owner.addDamageReductionByArmor(-28);
				break;
			case 69:
				_owner.addDamageReductionByArmor(-29);
				break;
			case 70:
				_owner.addDamageReductionByArmor(-30);
				break;
			default:
				break;
			}
		}
		if (itemId == 20100) {// ���� ��
			switch (itemlvl) {
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
				_owner.addDmgup(-2);
				break;

			}
		}
		if (itemId == 20150) {// Ŀ�� ��
			switch (itemlvl) {
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
				_owner.addDamageReductionByArmor(-2);
				break;

			}
		}
		/** �ӹ̷ε�հ� **/
		if (itemId == 20017) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(-1);
				break;
			case 6:
				_owner.addBowHitup(-2);
				break;
			case 7:
				_owner.addBowHitup(-3);
				break;
			case 8:
				_owner.addBowHitup(-4);
				break;
			case 9:
				_owner.addBowHitup(-5);
				break;
			case 10:
				_owner.addBowHitup(-6);
				break;
			default:
				break;
			}
		}
		if (itemId == 501211) {// ������ ����
			switch (itemlvl) {
			case 9:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;

			case 10:
				_owner.getAbility().addSp(-2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;

			}
		}
		if (itemId == 501212) {// ������ ����
			switch (itemlvl) {
			case 9:
				_owner.addDmgup(-1);
				break;
			case 10:
				_owner.addDmgup(-2);
				break;

			}
		}
		if (itemId == 501213) {// ������ ����
			switch (itemlvl) {
			case 9:
				_owner.addBowDmgup(-1);
				break;
			case 10:
				_owner.addBowDmgup(-2);
				break;

			}
		}

		if (itemId == 420000) {// ��� ����� ����
			switch (itemlvl) {
			case 0:
			case 2:
			case 3:
			case 4:
				_owner.addBowDmgup(-1);
				break;
			case 5:
			case 6:
				_owner.addBowDmgup(-2);
				break;
			case 7:
			case 8:
				_owner.addBowDmgup(-3);
				break;
			default:
				if (itemlvl >= 9)
					_owner.addBowDmgup(-4);
				break;
			}
		}
		if (itemId == 900022) {// ǳ���ǰ���
			switch (itemlvl) {
			case 0:
				_owner.addBowDmgup(-1);
			case 1:
				_owner.addBowDmgup(-1);
			case 2:
				_owner.addBowDmgup(-1);
			case 3:
				_owner.addBowDmgup(-1);
			case 4:
				_owner.addBowDmgup(-1);
				break;
			case 5:
				_owner.addBowDmgup(-1);
				_owner.addBowHitup(-1);
				break;
			case 6:
				_owner.addBowDmgup(-1);
				_owner.addBowHitup(-2);
				break;
			case 7:
				_owner.addBowDmgup(-2);
				_owner.addBowHitup(-3);
				break;
			case 8:
				_owner.addBowDmgup(-3);
				_owner.addBowHitup(-4);
				break;
			case 9:
				_owner.addBowDmgup(-4);
				_owner.addBowHitup(-5);
				break;
			case 10:
				_owner.addBowDmgup(-5);
				_owner.addBowHitup(-6);
				break;
			default:
				break;
			}
		}
		if (itemId == 900021) {// �����ǰ���
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 7:
				_owner.getAbility().addSp(-2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 8:
				_owner.getAbility().addSp(-3);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 9:
				_owner.getAbility().addSp(-4);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 10:
				_owner.getAbility().addSp(-5);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			default:
				break;
			}
		}
		if (itemId == 900020) {// �����ǰ���
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				_owner.addDamageReductionByArmor(-1);
				break;
			case 7:
				_owner.addDamageReductionByArmor(-2);
				break;
			case 8:
				_owner.addDamageReductionByArmor(-3);
				break;
			case 9:
				_owner.addDamageReductionByArmor(-4);
				break;
			case 10:
				_owner.addDamageReductionByArmor(-5);
				break;
			default:
				break;
			}
		}
		if (itemId == 900023) {// ȭ���ǰ���
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addDmgup(-1);
				break;
			case 5:
				_owner.addDmgup(-1);
				_owner.addHitup(-1);
				break;
			case 6:
				_owner.addDmgup(-1);
				_owner.addHitup(-2);
				break;
			case 7:
				_owner.addDmgup(-2);
				_owner.addHitup(-3);
				break;
			case 8:
				_owner.addDmgup(-3);
				_owner.addHitup(-4);
				break;
			case 9:
				_owner.addDmgup(-4);
				_owner.addHitup(-5);
				break;
			case 10:
				_owner.addDmgup(-5);
				_owner.addHitup(-6);
				break;
			default:
				break;
			}
		}

		if (itemId == 10167) {// ����Ʈ�ߵ��� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				_owner.addHitup(-1);
				break;
			case 6:
				_owner.addHitup(-2);
				break;
			case 7:
				_owner.addHitup(-3);
				break;
			case 8:
				_owner.addHitup(-4);
				break;
			case 9:
				_owner.addHitup(-5);
				break;
			case 10:
				_owner.addHitup(-6);
				break;
			default:
				break;
			}
		}

		if (itemId == 10165) {// ����� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addHitup(-3);
				break;
			case 5:
				_owner.addHitup(-4);
				break;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				_owner.addHitup(-5);
				break;
			default:
				break;
			}
		}

		if (itemId == 10166) {// ��״����� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addBowHitup(-3);
				break;
			case 5:
				_owner.addBowHitup(-4);
				break;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				_owner.addBowHitup(-5);
				break;
			default:
				break;
			}
		}

		if (itemId == 10168) {// ��״����� ����, ���̸����� ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				_owner.addBowHitup(-1);
				break;
			case 6:
				_owner.addBowHitup(-2);
				break;
			case 7:
				_owner.addBowHitup(-3);
				break;
			case 8:
				_owner.addBowHitup(-4);
				break;
			case 9:
				_owner.addBowHitup(-5);
				break;
			case 10:
				_owner.addBowHitup(-6);
				break;
			default:
				break;
			}
		}

		if (itemId == 420003) {// ��� ������ ����
			switch (itemlvl) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				_owner.addDmgup(-1);
				break;
			case 5:
			case 6:
				_owner.addDmgup(-2);
				break;
			case 7:
			case 8:
				_owner.addDmgup(-3);
				break;
			default:
				if (itemlvl >= 9)
					_owner.addDmgup(-4);
				break;
			}
		}

		if (itemId == 501214) {// ü�� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-10);
				break;
			case 1:
				_owner.addMaxHp(-15);
				break;
			case 2:
				_owner.addMaxHp(-20);
				break;
			case 3:
				_owner.addMaxHp(-25);
				break;
			case 4:
				_owner.addMaxHp(-30);
				break;
			case 5:
				_owner.addMaxHp(-35);
				break;
			case 6:
				_owner.addMaxHp(-40);
				break;
			case 7:
				_owner.addMaxHp(-45);
				break;
			case 8:
				_owner.addMaxHp(-50);
				break;
			case 9:
				_owner.addMaxHp(-55);
				break;
			case 10:
				_owner.addMaxHp(-60);
				break;

			}
		}
		if (itemId >= 900015 && itemId <= 900018) {// ��� ���� ��
			switch (itemlvl) {
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
				_owner.addDmgup(-2);
				break;

			}
		}

		if (itemId == 200851) {// ������ Ƽ����
			switch (itemlvl) {

			case 5:
				_owner.getResistance().addMr(-4);

				break;
			case 6:
				_owner.getResistance().addMr(-5);

				break;
			case 7:
				_owner.getResistance().addMr(-6);

				break;
			case 8:
				_owner.getResistance().addMr(-8);

				break;
			case 9:
				_owner.getResistance().addMr(-11);

				break;
			case 10:
				_owner.getResistance().addMr(-14);
				_owner.addMaxHp(-100);
				_owner.getResistance().addPVPweaponTotalDamage(-1);
				_owner.getResistance().addcalcPcDefense(-1);

				break;

			}
		}
		if (itemId == 900019) {
			switch (itemlvl) {
			case 1:
				_owner.addDamageReductionByArmor(-1);
			case 2:
				_owner.addDamageReductionByArmor(-1);
			case 3:
				_owner.addDamageReductionByArmor(-1);
			case 4:
				_owner.addDamageReductionByArmor(-1);
			case 5:
				_owner.addDamageReductionByArmor(-1);
				break;
			case 6:
				_owner.addDamageReductionByArmor(-2);
				break;
			case 7:
				_owner.addDamageReductionByArmor(-3);
				break;
			case 8:
				_owner.addDamageReductionByArmor(-4);
				break;
			case 9:
				_owner.addDamageReductionByArmor(-5);
				break;
			case 10:
				_owner.addDamageReductionByArmor(-6);
				break;

			}
		}
		if (itemId == 200852) {// ȭ���� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.addDmgup(-1);
				break;
			case 2:
				_owner.addDmgup(-1);
				break;
			case 3:
				_owner.addDmgup(-1);
				break;
			case 4:
				_owner.addDmgup(-1);
				break;
			case 5:
				_owner.addDmgup(-1);
				break;
			case 6:
				_owner.addDmgup(-1);
				break;
			case 7:
				_owner.addDmgup(-1);
				break;
			case 8:
				_owner.addDmgup(-1);
				break;
			case 9:
				_owner.addDmgup(-2);
				break;
			case 10:
				_owner.addDmgup(-2);
				_owner.addMaxHp(-100);
				_owner.getResistance().addPVPweaponTotalDamage(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;

			}
		}
		if (itemId == 200853) {// ǳ���� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.addBowDmgup(-1);
				break;
			case 2:
				_owner.addBowDmgup(-1);
				break;
			case 3:
				_owner.addBowDmgup(-1);
				break;
			case 4:
				_owner.addBowDmgup(-1);
				break;
			case 5:
				_owner.addBowDmgup(-1);
				break;
			case 6:
				_owner.addBowDmgup(-1);
				break;
			case 7:
				_owner.addBowDmgup(-1);
				break;
			case 8:
				_owner.addBowDmgup(-1);
				break;
			case 9:
				_owner.addBowDmgup(-2);
				break;
			case 10:
				_owner.addBowDmgup(-2);
				_owner.addMaxHp(-100);
				_owner.getResistance().addPVPweaponTotalDamage(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;

			}
		}
		if (itemId == 200854) {// ������ Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 2:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 3:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 4:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 5:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 6:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 7:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 8:
				_owner.getAbility().addSp(-2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 9:
				_owner.getAbility().addSp(-2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 10:
				_owner.getAbility().addSp(-3);
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.addMaxHp(-100);
				_owner.getResistance().addPVPweaponTotalDamage(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;

			}
		}

		if (itemId == 30030) {// ������ Ƽ����
			switch (itemlvl) {

			case 5:
				_owner.getResistance().addMr(-4);

				break;
			case 6:
				_owner.getResistance().addMr(-5);

				break;
			case 7:
				_owner.getResistance().addMr(-6);

				break;
			case 8:
				_owner.getResistance().addMr(-8);

				break;
			case 9:
				_owner.getResistance().addMr(-11);

				break;
			case 10:
				_owner.getResistance().addMr(-14);
				_owner.addMaxHp(-100);
				_owner.getResistance().addPVPweaponTotalDamage(-1);
				_owner.getResistance().addcalcPcDefense(-1);

				break;

			}
		}
		if (itemId == 30031) {// ȭ���� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.addDmgup(-1);
				break;
			case 2:
				_owner.addDmgup(-1);
				break;
			case 3:
				_owner.addDmgup(-1);
				break;
			case 4:
				_owner.addDmgup(-1);
				break;
			case 5:
				_owner.addDmgup(-1);
				break;
			case 6:
				_owner.addDmgup(-1);
				break;
			case 7:
				_owner.addDmgup(-1);
				break;
			case 8:
				_owner.addDmgup(-1);
				break;
			case 9:
				_owner.addDmgup(-2);
				break;
			case 10:
				_owner.addDmgup(-2);
				_owner.addMaxHp(-100);
				_owner.getResistance().addPVPweaponTotalDamage(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;

			}
		}
		if (itemId == 30032) {// ǳ���� Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.addBowDmgup(-1);
				break;
			case 2:
				_owner.addBowDmgup(-1);
				break;
			case 3:
				_owner.addBowDmgup(-1);
				break;
			case 4:
				_owner.addBowDmgup(-1);
				break;
			case 5:
				_owner.addBowDmgup(-1);
				break;
			case 6:
				_owner.addBowDmgup(-1);
				break;
			case 7:
				_owner.addBowDmgup(-1);
				break;
			case 8:
				_owner.addBowDmgup(-1);
				break;
			case 9:
				_owner.addBowDmgup(-2);
				break;
			case 10:
				_owner.addBowDmgup(-2);
				_owner.addMaxHp(-100);
				_owner.getResistance().addPVPweaponTotalDamage(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;

			}
		}
		if (itemId == 30033) {// ������ Ƽ����
			switch (itemlvl) {
			case 1:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 2:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 3:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 4:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 5:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 6:
				_owner.getAbility().addSp(-1);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 7:
				_owner.getAbility().addSp(-2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 8:
				_owner.getAbility().addSp(-2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 9:
				_owner.getAbility().addSp(-2);
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 10:
				_owner.getAbility().addSp(-3);
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.addMaxHp(-100);
				_owner.getResistance().addPVPweaponTotalDamage(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;

			}
		}
		/** ��ȣ���� Ȱ �� ���Ÿ� ���� **/
		if (itemId == 222343) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(-1);
				break;
			case 6:
				_owner.addBowHitup(-2);
				break;
			case 7:
				_owner.addBowHitup(-3);
				break;
			case 8:
				_owner.addBowHitup(-4);
				break;
			case 9:
				_owner.addBowHitup(-5);
				break;
			default:
				break;
			}
		}
		if (itemId == 30083) {
			_owner.set���������(-5);
		}
		/** ��ȣ���� �Ŀ� �۷κ� �ٰŸ� ���� **/
		if (itemId == 222345) {
			switch (itemlvl) {
			case 5:
				_owner.addHitup(-1);
				break;
			case 6:
				_owner.addHitup(-2);
				break;
			case 7:
				_owner.addHitup(-3);
				break;
			case 8:
				_owner.addHitup(-4);
				break;
			case 9:
				_owner.addHitup(-5);
				break;
			default:
				break;
			}
		}
		/**
		 * ���ְ��� �߰�, MP -70, �ٰŸ� ���� -2, ��æƮ +5���� �ִ� HP -20/-40/-60/-80/-100/-120 ����,
		 * PVP����� ���� ����
		 **/ // by.���ۻ�̳�
		if (itemId == 5000004) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-70); // MP -70
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				break;
			case 1:
				_owner.addMaxMp(-70); // MP -70
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				break;
			case 2:
				_owner.addMaxMp(-70); // MP -70
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				break;
			case 3:
				_owner.addMaxMp(-70); // MP -70
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				break;
			case 4:
				_owner.addMaxMp(-70); // MP -70
				_owner.addHitup(2); // �ٰŸ� ���� -2
				break;
			case 5:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-20); // HP -20
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				_owner.getResistance().addcalcPcDefense(-1); // PVP����� ���� -1
				break;
			case 6:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-40); // HP -40
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				_owner.getResistance().addcalcPcDefense(-2); // PVP����� ���� -2
				break;
			case 7:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-60); // HP -60
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				_owner.getResistance().addcalcPcDefense(-3); // PVP����� ���� -3
				break;
			case 8:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-80); // HP -80
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				_owner.getResistance().addcalcPcDefense(-4); // PVP����� ���� -4
				break;
			case 9:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-100); // HP -100
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				_owner.getResistance().addcalcPcDefense(-5); // PVP����� ���� -5
				break;
			case 10:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-120); // HP -120
				_owner.addHitup(-2); // �ٰŸ� ���� -2
				_owner.getResistance().addcalcPcDefense(-6); // PVP����� ����-6
				break;
			default:
				break;
			}
		}
		/**
		 * �븶������ �߰�, MP -70, ��æƮ +5���� �ִ� HP -20/-40/-60/-80/-100/-120 ����, ���� ���� -2, PVP
		 * ����� ���� ����
		 **/ // by.���ۻ�̳�
		if (itemId == 5000005) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-70); // MP -70
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 1:
				_owner.addMaxMp(-70); // MP -70
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 2:
				_owner.addMaxMp(-70); // MP -70
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 3:
				_owner.addMaxMp(-70); // MP -70
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 4:
				_owner.addMaxMp(-70); // MP -70
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				break;
			case 5:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-20); // HP -20
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(-1); // PVP ����� ���� -1
				break;
			case 6:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-40); // HP -40
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(-2); // PVP ����� ���� -2
				break;
			case 7:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-60); // HP -60
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 8:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-80); // HP -80
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(-4); // PVP ����� ���� -4
				break;
			case 9:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-100); // HP -100
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(-5); // PVP ����� ���� -5
				break;
			case 10:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-120); // HP -120
				_owner.getAbility().addSp(-2); // ���� ���� -2
				_owner.sendPackets(new S_SPMR(_owner));
				_owner.getResistance().addcalcPcDefense(-6); // PVP ����� ���� -6
				break;
			default:
				break;
			}
		}
		/**
		 * �������� �߰�, MP -70, +5���� �ִ� HP -20/-40/-60/-80/-100/-120 ����, ���Ÿ� ���� -2, PVP �����
		 * ���� ����
		 **/ // by.���ۻ�̳�
		if (itemId == 5000006) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-70); // MP -70
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				break;
			case 1:
				_owner.addMaxMp(-70); // MP -70
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				break;
			case 2:
				_owner.addMaxMp(-70); // MP -70
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				break;
			case 3:
				_owner.addMaxMp(-70); // MP -70
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				break;
			case 4:
				_owner.addMaxMp(-70); // MP -70
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				break;
			case 5:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-20); // HP -20
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				_owner.getResistance().addcalcPcDefense(-1); // PVP ����� ���� -1
				break;
			case 6:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-40); // HP -40
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				_owner.getResistance().addcalcPcDefense(-2); // PVP ����� ���� -2
				break;
			case 7:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-60); // HP -60
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 8:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-80); // HP -80
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				_owner.getResistance().addcalcPcDefense(-4); // PVP ����� ���� -4
				break;
			case 9:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-100); // HP -100
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				_owner.getResistance().addcalcPcDefense(-5); // PVP ����� ���� -5
				break;
			case 10:
				_owner.addMaxMp(-70); // MP -70
				_owner.addMaxHp(-120); // HP -120
				_owner.addBowHitup(-2); // ���Ÿ� ���� -2
				_owner.getResistance().addcalcPcDefense(-6); // PVP ����� ���� -6
				break;
			default:
				break;
			}
		}
		/**
		 * �巡�� �����̾� ����, +7���� �ִ� HP -50/-100/-150/-200 ����, ����� ����-1 ����, PVP ����� ���� -2, MP
		 * -50
		 **/ // by.���ۻ�̳�
		if (itemId == 5000003) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-100); // HP -100
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-2); // PVP ����� ���� -2
				break;
			case 1:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-100); // HP -100
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-2); // PVP ����� ���� -2
				break;
			case 2:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-100); // HP -100
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-2); // PVP ����� ���� -2
				break;
			case 3:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-100); // HP -100
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-2); // PVP ����� ���� -2
				break;
			case 4:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-100); // HP -100
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-2); // PVP ����� ���� -2
				break;
			case 5:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-100); // HP -100
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 6:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-100); // HP -100
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-4); // PVP ����� ���� -4
				break;
			case 7:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-150); // HP -150
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-5); // PVP ����� ���� -5
				break;
			case 8:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-200); // HP -200
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-6); // PVP ����� ���� -6
				break;
			case 9:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-250); // HP -250
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-7); // PVP ����� ���� -7
				break;
			case 10:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMaxHp(-300); // HP -300
				_owner.addDamageReductionByArmor(-1); // ����� ���� -1
				_owner.getResistance().addcalcPcDefense(-8); // PVP ����� ���� -8
				break;
			default:
				break;
			}
		}
		/** �븶������ ���� MP-100 **/ // by.���ۻ�̳�
		if (itemId == 21166) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 1:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 2:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 3:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 4:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 5:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 6:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 7:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 8:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 9:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 10:
				_owner.addMaxMp(-100); // MP -100
				break;
			default:
				break;
			}
		}
		/** �����ں��� �հ�, PVP ����� ���� -3 **/ // by.���ۻ�̳�
		if (itemId == 5000007) {
			switch (itemlvl) {
			case 0:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 1:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 2:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 3:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 4:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 5:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 6:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 7:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 8:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 9:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			case 10:
				_owner.getResistance().addcalcPcDefense(-3); // PVP ����� ���� -3
				break;
			default:
				break;
			}
		}
		/** ���������� ������ MP-10 **/ // by.���ۻ�̳�
		if (itemId == 420109) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 1:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 2:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 3:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 4:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 5:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 6:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 7:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 8:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 9:
				_owner.addMaxMp(-10); // MP -10
				break;
			case 10:
				_owner.addMaxMp(-10); // MP -10
				break;
			default:
				break;
			}
		}
		/** ��Ǫ������ ������, ���������� �γ���, ������ �����, ������ �����, ������ �������� �����, MP -20 **/ // by.���ۻ�̳�
		if (itemId == 420105 || itemId == 420110 || itemId == 21258 || itemId == 21260 || itemId == 21268) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 1:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 2:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 3:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 4:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 5:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 6:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 7:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 8:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 9:
				_owner.addMaxMp(-20); // MP -20
				break;
			case 10:
				_owner.addMaxMp(-20); // MP -20
				break;
			default:
				break;
			}
		}
		/** ��Ǫ������ �γ���, MP-30 **/ // by.���ۻ�̳�
		if (itemId == 420106) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 1:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 2:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 3:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 4:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 5:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 6:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 7:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 8:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 9:
				_owner.addMaxMp(-30); // MP -30
				break;
			case 10:
				_owner.addMaxMp(-30); // MP -30
				break;
			default:
				break;
			}
		}

		/** �������� ����, MP -40 **/ // by.���ۻ�̳�
		if (itemId == 420111) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 1:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 2:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 3:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 4:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 5:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 6:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 7:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 8:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 9:
				_owner.addMaxMp(-40); // MP -40
				break;
			case 10:
				_owner.addMaxMp(-40); // MP -40
				break;
			default:
				break;
			}
		}

		/** ��Ǫ������ �ķ�, ������� �߰�, ��ɲ��� �߰�, �������� �߰�, MP -50 **/ // by.���ۻ�̳�
		if (itemId == 420107 || itemId == 230000 || itemId == 230001 || itemId == 230002) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 1:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 2:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 3:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 4:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 5:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 6:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 7:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 8:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 9:
				_owner.addMaxMp(-50); // MP -50
				break;
			case 10:
				_owner.addMaxMp(-50); // MP -50
				break;
			default:
				break;
			}
		}
		/** �Ŵ� ���� ������ ���� ����, MP -70 **/ // by.���ۻ�̳�
		if (itemId == 20050) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 1:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 2:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 3:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 4:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 5:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 6:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 7:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 8:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 9:
				_owner.addMaxMp(-70); // MP -70
				break;
			case 10:
				_owner.addMaxMp(-70); // MP -70
				break;
			default:
				break;
			}
		}
		/** �þ��� �ɾ� MP -100 **/ // by.���ۻ�̳�
		if (itemId == 22009) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 1:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 2:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 3:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 4:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 5:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 6:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 7:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 8:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 9:
				_owner.addMaxMp(-100); // MP -100
				break;
			case 10:
				_owner.addMaxMp(-100); // MP -100
				break;
			default:
				break;
			}
		}
		/** �����ں��� �����, �ٰŸ� ���� -4, ���Ÿ� ���� -4, PVP ����� �߰�-3 **/ // by.���ۻ�̳�
		if (itemId == 101010) {
			switch (itemlvl) {
			case 0:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 1:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 2:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 3:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 4:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 5:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 6:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 7:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 8:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 9:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			case 10:
				_owner.addHitup(-4); // �ٰŸ� ���� -4
				_owner.addBowHitup(-4); // ���Ÿ� ���� -4
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� -3
				break;
			default:
				break;
			}
		}
		/** ���Ľ��� ���� MP +200 **/ // by.���ۻ�̳�
		if (itemId == 27530) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 1:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 2:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 3:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 4:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 5:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 6:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 7:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 8:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 9:
				_owner.addMaxMp(-200); // HP -200
				break;
			case 10:
				_owner.addMaxMp(-200); // HP -200
				break;
			default:
				break;
			}
		}
		/** �������� ��, �ٰŸ� ġ��Ÿ -10 **/
		if (itemId == 100033) {
			switch (itemlvl) {
			case 0:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 1:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 2:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 3:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 4:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 5:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 6:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 7:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 8:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 9:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			case 10:
				_owner.addDmgCritical(-10); // �ٰŸ� ġ��Ÿ -10
				break;
			default:
				break;
			}
		}
		/** �����ϻ���� ����, ġ��Ÿ - ó�� **/
		if (itemId == 30111 || itemId == 30112){ 
			switch (itemlvl) {
			case 0:
				_owner.addDmgCritical(-7);
				break;
			case 1:
				_owner.addDmgCritical(-9);
				_owner.addDmgup(-2);
				break;
			case 2:
				_owner.addDmgCritical(-11);
				_owner.addDmgup(-4);
				break;
			case 3:
				_owner.addDmgCritical(-13);
				_owner.addDmgup(-6);
				break;
			case 4:
				_owner.addDmgCritical(-15);
				_owner.addDmgup(-8);
				break;
			case 5:
				_owner.addDmgCritical(-17);
				_owner.addDmgup(-10);
				break;
			case 6:
				_owner.addDmgCritical(-19);
				_owner.addDmgup(-12);
				break;
			case 7:
				_owner.addDmgCritical(-21);
				_owner.addDmgup(-14);
				break;
			case 8:
				_owner.addDmgCritical(-23);
				_owner.addDmgup(-16);
				break;
			case 9:
				_owner.addDmgCritical(-25);
				_owner.addDmgup(-18);
				break;
			case 10:
				_owner.addDmgCritical(-27);
				_owner.addDmgup(-20);
				break;	
			default:
				break;
			}
		}
		/** �߶�ī���� �Ϸ�, �ٰŸ� ����� -3, �ٰŸ� ġ��Ÿ, PVP ����� ������ ���� **/ // by.���ۻ�̳�
		if (itemId == 420115) {
			switch (itemlvl) {
			case 0:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-3); // �ٰŸ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 1:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-3); // �ٰŸ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 2:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-3); // �ٰŸ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 3:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-3); // �ٰŸ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 4:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-3); // �ٰŸ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 5:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-3); // �ٰŸ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 6:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-3); // �ٰŸ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 7:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-4); // �ٰŸ� ġ��Ÿ -4
				_owner.getResistance().addPVPweaponTotalDamage(-4); // PVP ����� ������ ���� -4
				break;
			case 8:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-5); // �ٰŸ� ġ��Ÿ -5
				_owner.getResistance().addPVPweaponTotalDamage(-5); // PVP ����� ������ ���� -5
				break;
			case 9:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-6); // �ٰŸ� ġ��Ÿ -6
				_owner.getResistance().addPVPweaponTotalDamage(-6); // PVP ����� ������ ���� -6
				break;
			case 10:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-7); // �ٰŸ� ġ��Ÿ -7
				_owner.getResistance().addPVPweaponTotalDamage(-7); // PVP ����� ������ ���� -7
				break;
			default:
				break;
			}
		}
		/** �߶�ī���� ������, �ٰŸ� �����-3, �ٰŸ� ġ��Ÿ, PVP ����� ������ ���� **/ // by.���ۻ�̳�
		if (itemId == 420113) {
			switch (itemlvl) {
			case 0:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-2); // �ٰŸ� ġ��Ÿ-+2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 1:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-2); // �ٰŸ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 2:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-2); // �ٰŸ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 3:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-2); // �ٰŸ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 4:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-2); // �ٰŸ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 5:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-2); // �ٰŸ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 6:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-2); // �ٰŸ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 7:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-3); // �ٰŸ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-4); // PVP ����� ������ ���� -4
				break;
			case 8:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-4); // �ٰŸ� ġ��Ÿ -4
				_owner.getResistance().addPVPweaponTotalDamage(-5); // PVP ����� ������ ���� -5
				break;
			case 9:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-5); // �ٰŸ� ġ��Ÿ -5
				_owner.getResistance().addPVPweaponTotalDamage(-6); // PVP ����� ������ ���� -6
				break;
			case 10:
				_owner.addDmgup(-3); // �ٰŸ� ����� -3
				_owner.addDmgCritical(-6); // �ٰŸ� ġ��Ÿ -6
				_owner.getResistance().addPVPweaponTotalDamage(-7); // PVP ����� ������ ���� -7
				break;
			default:
				break;
			}
		}
		/** �߶�ī���� �γ���, MP -30, ���Ÿ� ����� -3, ���Ÿ� ġ��Ÿ, ����� ������ ���� **/ // by.���ۻ�̳�
		if (itemId == 420112) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-2); // ���Ÿ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 1:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-2); // ���Ÿ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 2:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-2); // ���Ÿ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 3:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-2); // ���Ÿ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 4:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-2); // ���Ÿ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 5:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-2); // ���Ÿ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 6:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-2); // ���Ÿ� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 7:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-3); // ���Ÿ� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-4); // PVP ����� ������ ���� -4
				break;
			case 8:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-4); // ���Ÿ� ġ��Ÿ -4
				_owner.getResistance().addPVPweaponTotalDamage(-5); // PVP ����� ������ ���� -5
				break;
			case 9:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-5); // ���Ÿ� ġ��Ÿ -5
				_owner.getResistance().addPVPweaponTotalDamage(-6); // PVP ����� ������ ���� -6
				break;
			case 10:
				_owner.addMaxMp(-30); // MP -30
				_owner.addBowDmgup(-3); // ���Ÿ� ����� -3
				_owner.addBowDmgCritical(-6); // ���Ÿ� ġ��Ÿ -6
				_owner.getResistance().addPVPweaponTotalDamage(-7); // PVP ����� ������ ���� -7
				break;
			default:
				break;
			}
		}
		/** �߶�ī���� ����, MP -50, ���� ġ��Ÿ -2, ����� ������ ���� **/ // by.���ۻ�̳�
		if (itemId == 420114) {
			switch (itemlvl) {
			case 0:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-2); // ���� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 1:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-2); // ���� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 2:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-2); // ���� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 3:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-2); // ���� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 4:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-2); // ���� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 5:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-2); // ���� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 6:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-2); // ���� ġ��Ÿ -2
				_owner.getResistance().addPVPweaponTotalDamage(-3); // PVP ����� ������ ���� -3
				break;
			case 7:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-3); // ���� ġ��Ÿ -3
				_owner.getResistance().addPVPweaponTotalDamage(-4); // PVP ����� ������ ���� -4
				break;
			case 8:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-4); // ���� ġ��Ÿ -4
				_owner.getResistance().addPVPweaponTotalDamage(-5); // PVP ����� ������ ���� -5
				break;
			case 9:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-5); // ���� ġ��Ÿ -5
				_owner.getResistance().addPVPweaponTotalDamage(-6); // PVP ����� ������ ���� -6
				break;
			case 10:
				_owner.addMaxMp(-50); // MP -50
				_owner.addMagicCritical(-6); // ���� ġ��Ÿ -6
				_owner.getResistance().addPVPweaponTotalDamage(-7); // PVP ����� ������ ���� -7
				break;
			default:
				break;
			}
		}
		if (itemId == 21259 || itemId == 1021259 || itemId == 21265 || itemId == 1021265 || itemId == 21266 || itemId == 1021266 || itemId == 30218) {
			switch (itemlvl) {
			case 7:
				_owner.addMaxHp(-20);
				break;
			case 8:
				_owner.addMaxHp(-40);
				break;
			case 9:
				_owner.addMaxHp(-60);
				_owner.addDamageReductionByArmor(-1);
				break;
			case 10:
				_owner.addMaxHp(-60);
				_owner.addDamageReductionByArmor(-1);
				break;
			default:
				break;
			}
		}
		/** Ȱ�� **/
		if (itemId == 30006 || itemId == 20191 || itemId == 120191 || itemId == 720191) {
			switch (itemlvl) {
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
				_owner.addBowHitup(-2);
				break;

			}
		}
		if (itemId == 230000) { // ������� �߰�
			switch (itemlvl) {
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
				_owner.addHitup(-1);
				break;

			}
		}
		if (itemId == 231006) { // ������ ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-5);
				break;
			case 1:
				_owner.addMaxHp(-10);
				break;
			case 2:
				_owner.addMaxHp(-15);
				break;
			case 3:
				_owner.addMaxHp(-20);
				break;
			case 4:
				_owner.addMaxHp(-25);
				_owner.getAC().addAc(1);
				break;
			case 5:
				_owner.addMaxHp(-30);
				_owner.getAC().addAc(2);
				_owner.addDmgup(-1);
				break;
			case 6:
				_owner.addMaxHp(-35);
				_owner.getAC().addAc(3);
				_owner.addDmgup(-2);
				break;
			case 7:
				_owner.addMaxHp(-40);
				_owner.getAC().addAc(3);
				_owner.addDmgup(-3);
				break;
			case 8:
				_owner.addMaxHp(-50);
				_owner.getAC().addAc(3);
				_owner.addDmgup(-4);
				break;
			default:
			}
		}
		if (itemId == 231004) { // Ŀ���� ��� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-5);
				break;
			case 1:
				_owner.addMaxHp(-10);
				break;
			case 2:
				_owner.addMaxHp(-15);
				break;
			case 3:
				_owner.addMaxHp(-20);
				break;
			case 4:
				_owner.addMaxHp(-25);
				_owner.getAC().addAc(1);
				break;
			case 5:
				_owner.addMaxHp(-30);
				_owner.getAC().addAc(2);
				_owner.addBowDmgup(-1);
				break;
			case 6:
				_owner.addMaxHp(-35);
				_owner.getAC().addAc(3);
				_owner.addBowDmgup(-2);
				break;
			case 7:
				_owner.addMaxHp(-40);
				_owner.getAC().addAc(3);
				_owner.addBowDmgup(-3);
				break;
			case 8:
				_owner.addMaxHp(-50);
				_owner.getAC().addAc(3);
				_owner.addBowDmgup(-4);
				break;
			default:
			}
		}
		if (itemId == 231005) { // Ŀ���� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-5);
				break;
			case 1:
				_owner.addMaxHp(-10);
				break;
			case 2:
				_owner.addMaxHp(-15);
				break;
			case 3:
				_owner.addMaxHp(-20);
				break;
			case 4:
				_owner.addMaxHp(-25);
				_owner.getAC().addAc(1);
				break;
			case 5:
				_owner.addMaxHp(-30);
				_owner.getAC().addAc(2);
				_owner.addHitup(-1);
				break;
			case 6:
				_owner.addMaxHp(35);
				_owner.getAC().addAc(3);
				_owner.addHitup(2);
				break;
			case 7:
				_owner.addMaxHp(-40);
				_owner.getAC().addAc(3);
				_owner.addHitup(-3);
				break;
			case 8:
				_owner.addMaxHp(-50);
				_owner.getAC().addAc(3);
				_owner.addHitup(-4);
				break;
			default:
			}
		}
		if (itemId == 231003) { // Ŀ���� ��ȣ ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-5);
				break;
			case 1:
				_owner.addMaxHp(-10);
				break;
			case 2:
				_owner.addMaxHp(-15);
				break;
			case 3:
				_owner.addMaxHp(-20);
				_owner.getAC().addAc(1);
				break;
			case 4:
				_owner.addMaxHp(-25);
				_owner.getAC().addAc(2);
				break;
			case 5:
				_owner.addMaxHp(-30);
				_owner.getAC().addAc(3);
				_owner.addDamageReductionByArmor(-1);
				break;
			case 6:
				_owner.addMaxHp(-35);
				_owner.getAC().addAc(5);
				_owner.addDamageReductionByArmor(-2);
				_owner.getResistance().addMr(-3);
				break;
			case 7:
				_owner.addMaxHp(-40);
				_owner.getAC().addAc(6);
				_owner.addDamageReductionByArmor(-3);
				_owner.getResistance().addMr(-5);
				break;
			case 8:
				_owner.addMaxHp(-50);
				_owner.getAC().addAc(7);
				_owner.addDamageReductionByArmor(-4);
				_owner.getResistance().addMr(-7);
				break;
			default:
			}
		}
		if (itemId == 231007) { // ��ȣ�� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-5);
				break;
			case 1:
				_owner.addMaxHp(-10);
				break;
			case 2:
				_owner.addMaxHp(-15);
				break;
			case 3:
				_owner.addMaxHp(-20);
				_owner.getAC().addAc(1);
				break;
			case 4:
				_owner.addMaxHp(-25);
				_owner.getAC().addAc(2);
				break;
			case 5:
				_owner.addMaxHp(-30);
				_owner.addDmgup(-1);
				_owner.getAC().addAc(3);
				_owner.addDamageReductionByArmor(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;
			case 6:
				_owner.addMaxHp(-35);
				_owner.addDmgup(-2);
				_owner.addHitup(-1);
				_owner.getAC().addAc(5);
				_owner.addDamageReductionByArmor(-2);
				_owner.getResistance().addMr(-3);
				_owner.getResistance().addcalcPcDefense(-2);
				break;
			case 7:
				_owner.addMaxHp(-40);
				_owner.addDmgup(-3);
				_owner.getAC().addAc(6);
				_owner.addHitup(-3);
				_owner.addDamageReductionByArmor(-3);
				_owner.getResistance().addMr(-5);
				_owner.getResistance().addcalcPcDefense(-3);
				break;
			case 8:
				_owner.addMaxHp(-50);
				_owner.addDmgup(-4);
				_owner.getAC().addAc(7);
				_owner.addHitup(-5);
				_owner.addDamageReductionByArmor(-4);
				_owner.getResistance().addMr(-7);
				_owner.getResistance().addcalcPcDefense(-5);
				break;
			default:
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		if (itemId == 231008) { // ��ȣ�� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-5);
				break;
			case 1:
				_owner.addMaxHp(-10);
				break;
			case 2:
				_owner.addMaxHp(-15);
				break;
			case 3:
				_owner.addMaxHp(-20);
				_owner.getAC().addAc(1);
				break;
			case 4:
				_owner.addMaxHp(-25);
				_owner.getAC().addAc(2);
				break;
			case 5:
				_owner.addMaxHp(-30);
				_owner.addBowDmgup(-1);
				_owner.getAC().addAc(3);
				_owner.addDamageReductionByArmor(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;
			case 6:
				_owner.addMaxHp(-35);
				_owner.addBowDmgup(-2);
				_owner.addBowHitup(-1);
				_owner.getAC().addAc(5);
				_owner.addDamageReductionByArmor(-2);
				_owner.getResistance().addMr(-3);
				_owner.getResistance().addcalcPcDefense(-2);
				break;
			case 7:
				_owner.addMaxHp(-40);
				_owner.addBowDmgup(-3);
				_owner.getAC().addAc(6);
				_owner.addBowHitup(-3);
				_owner.addDamageReductionByArmor(-3);
				_owner.getResistance().addMr(-5);
				_owner.getResistance().addcalcPcDefense(-3);
				break;
			case 8:
				_owner.addMaxHp(-50);
				_owner.addBowDmgup(-4);
				_owner.getAC().addAc(7);
				_owner.addBowHitup(-5);
				_owner.addDamageReductionByArmor(-4);
				_owner.getResistance().addMr(-7);
				_owner.getResistance().addcalcPcDefense(-5);
				break;
			default:
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		if (itemId == 231009) { // ��ȣ�� ���� ����
			switch (itemlvl) {
			case 0:
				_owner.addMaxHp(-5);
				break;
			case 1:
				_owner.addMaxHp(-10);
				break;
			case 2:
				_owner.addMaxHp(-15);
				break;
			case 3:
				_owner.addMaxHp(-20);
				_owner.getAC().addAc(1);
				break;
			case 4:
				_owner.addMaxHp(-25);
				_owner.getAC().addAc(2);
				break;
			case 5:
				_owner.addMaxHp(-30);
				_owner.addHitup(-1);
				_owner.getAC().addAc(3);
				_owner.addDamageReductionByArmor(-1);
				_owner.getResistance().addcalcPcDefense(-1);
				break;
			case 6:
				_owner.addMaxHp(-35);
				_owner.addHitup(-2);
				_owner.getAC().addAc(5);
				_owner.addDamageReductionByArmor(-2);
				_owner.getResistance().addMr(-3);
				_owner.getResistance().addcalcPcDefense(-2);
				break;
			case 7:
				_owner.addMaxHp(-40);
				_owner.getAC().addAc(6);
				_owner.addHitup(-3);
				_owner.addDamageReductionByArmor(-3);
				_owner.getResistance().addMr(-5);
				_owner.getResistance().addcalcPcDefense(-3);
				break;
			case 8:
				_owner.addMaxHp(-50);
				_owner.getAC().addAc(7);
				_owner.addHitup(-4);
				_owner.addDamageReductionByArmor(-4);
				_owner.getResistance().addMr(-7);
				_owner.getResistance().addcalcPcDefense(-5);
				break;
			default:
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}

		/** �Ϸ��� ���� **/
		if (itemId == 490023 || itemId == 30007 || itemId == 30010) {
			switch (itemlvl) {
			case 5:
				_owner.addHitup(-1);
				break;
			case 6:
				_owner.addHitup(-1);
				_owner.addDmgup(-1);
				break;
			case 7:
				_owner.addHitup(-2);
				_owner.addDmgup(-2);
				break;
			case 8:
				_owner.addHitup(-3);
				_owner.addDmgup(-3);
				break;
			case 9:
				_owner.addHitup(-4);
				_owner.addDmgup(-4);
				break;
			case 10:
				_owner.addHitup(-5);
				_owner.addDmgup(-5);
				break;
			default:
				break;
			}
		}
		/** ������ ���� **/
		if (itemId == 30019) {
			switch (itemlvl) {
			case 5:
				_owner.addHitup(-1);
				_owner.addDmgup(-1);
				break;
			case 6:
				_owner.addHitup(-2);
				_owner.addDmgup(-2);
				break;
			case 7:
				_owner.addHitup(-3);
				_owner.addDmgup(-3);
				break;
			case 8:
				_owner.addHitup(-4);
				_owner.addDmgup(-4);
				break;
			default:
				break;
			}
		}
		/** ����� ���� **/
		if (itemId == 30020) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(-1);
				_owner.addBowDmgup(-1);
				break;
			case 6:
				_owner.addBowHitup(-2);
				_owner.addBowDmgup(-2);
				break;
			case 7:
				_owner.addBowHitup(-3);
				_owner.addBowDmgup(-3);
				break;
			case 8:
				_owner.addBowHitup(-4);
				_owner.addBowDmgup(-4);
				break;
			default:
				break;
			}
		}
		/** ������ ���� **/
		if (itemId == 30021) {
			switch (itemlvl) {
			case 5:
				_owner.getAbility().addSp(-1);
				break;
			case 6:
				_owner.getAbility().addSp(-2);
				break;
			case 7:
				_owner.getAbility().addSp(-3);
				break;
			case 8:
				_owner.getAbility().addSp(-4);
				break;
			default:
				break;
			}
		}
		/** ��ȣ ������ ���� **/
		if (itemId == 30023) {
			switch (itemlvl) {
			case 5:
				_owner.addHitup(-1);
				_owner.addDmgup(-1);
				_owner.getResistance().addMr(-4);
				break;
			case 6:
				_owner.addHitup(-2);
				_owner.addDmgup(-2);
				_owner.getResistance().addMr(-6);
				break;
			case 7:
				_owner.addHitup(-3);
				_owner.addDmgup(-3);
				_owner.getResistance().addMr(-8);
				break;
			case 8:
				_owner.addHitup(-4);
				_owner.addDmgup(-4);
				_owner.getResistance().addMr(-10);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		/** ��ȣ ����� ���� **/
		if (itemId == 30024) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(-1);
				_owner.addBowDmgup(-1);
				_owner.getResistance().addMr(-4);
				break;
			case 6:
				_owner.addBowHitup(-2);
				_owner.addBowDmgup(-2);
				_owner.getResistance().addMr(-6);
				break;
			case 7:
				_owner.addBowHitup(-3);
				_owner.addBowDmgup(-3);
				_owner.getResistance().addMr(-8);
				break;
			case 8:
				_owner.addBowHitup(-4);
				_owner.addBowDmgup(-4);
				_owner.getResistance().addMr(-10);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		/** ��ȣ ������ ���� **/
		if (itemId == 30025) {
			switch (itemlvl) {
			case 5:
				_owner.getAbility().addSp(-1);
				_owner.getResistance().addMr(-4);
				break;
			case 6:
				_owner.getAbility().addSp(-2);
				_owner.getResistance().addMr(-6);
				break;
			case 7:
				_owner.getAbility().addSp(-3);
				_owner.getResistance().addMr(-8);
				break;
			case 8:
				_owner.getAbility().addSp(-4);
				_owner.getResistance().addMr(-10);
				break;
			default:
				break;
			}
			_owner.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _owner), true);
		}
		/** ��ȣ ���� **/
		if (itemId == 30022) {
			switch (itemlvl) {
			case 4:
				_owner.getResistance().addMr(-1);
				break;
			case 5:
				_owner.getResistance().addMr(-2);
				break;
			case 6:
				_owner.getResistance().addMr(-3);
				break;
			case 7:
				_owner.getResistance().addMr(-4);
				break;
			case 8:
				_owner.getResistance().addMr(-5);
				break;
			default:
				break;
			}
		}
		/** ��ø�� ���� **/
		if (itemId == 490024 || itemId == 30008 || itemId == 30011) {
			switch (itemlvl) {
			case 5:
				_owner.addBowHitup(-1);
				break;
			case 6:
				_owner.addBowHitup(-1);
				_owner.addBowDmgup(-1);
				break;
			case 7:
				_owner.addBowHitup(-2);
				_owner.addBowDmgup(-2);
				break;
			case 8:
				_owner.addBowHitup(-3);
				_owner.addBowDmgup(-3);
				break;
			case 9:
				_owner.addBowHitup(-4);
				_owner.addBowDmgup(-4);
				break;
			case 10:
				_owner.addBowHitup(-5);
				_owner.addBowDmgup(-5);
				break;
			default:
				break;
			}
		}
		/** ������ ���� **/
		if (itemId == 490025 || itemId == 30009 || itemId == 30012) {
			switch (itemlvl) {
			case 6:
				_owner.getAbility().addSp(-1);
				break;
			case 7:
				_owner.getAbility().addSp(-2);
				break;
			case 8:
				_owner.getAbility().addSp(-3);
				break;
			case 9:
				_owner.getAbility().addSp(-4);
				break;
			case 10:
				_owner.getAbility().addSp(-5);
				break;
			default:
				break;
			}
		}
		if (itemId >= 900011 && itemId <= 900014) {// ��� �ϼ� ��
			switch (itemlvl) {
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
				_owner.addDamageReductionByArmor(-2);
				break;

			}
		}
		if (itemtype >= 8 && itemtype <= 12) {
			if (itemlvl > 0) {
				if (itemgrade != 3) {
					if (itemtype == 8 || itemtype == 12) {// �Ͱ��� �����
						switch (itemlvl) {
						case 1:
							_owner.addMaxHp(-5);
							break;
						case 2:
							_owner.addMaxHp(-10);
							break;
						case 3:
							_owner.addMaxHp(-20);
							break;
						case 4:
							_owner.addMaxHp(-30);
							break;
						case 5:
							_owner.addPotionPlus(-9);
							_owner.addMaxHp(-40);
							_owner.getAC().addAc(1);
							break;
						case 6:
							_owner.addPotionPlus(-9);
							_owner.addMaxHp(-40);
							_owner.getAC().addAc(2);
							break;
						case 7:
							_owner.addPotionPlus(-9);
							_owner.addMaxHp(-50);
							_owner.getAC().addAc(3);
							break;
						case 8:
							_owner.addPotionPlus(-9);
							_owner.addMaxHp(-50);
							_owner.getAC().addAc(4);
							break;
						case 9:
							_owner.addPotionPlus(-9);
							_owner.addMaxHp(-60);
							_owner.getAC().addAc(5);
							break;
						default:
						}
					} else if ((itemgrade >= 0 && itemgrade <= 2) && (itemtype == 9 || itemtype == 11)) { // ����
						switch (itemlvl) {
						case 1:
							_owner.addMaxHp(-5);
							break;
						case 2:
							_owner.addMaxHp(-10);
							break;
						case 3:
							_owner.addMaxHp(-20);
							break;
						case 4:
							_owner.addMaxHp(-30);
							break;
						case 5:
							_owner.addDmgup(-1);
							_owner.addBowDmgup(-1);
							_owner.addMaxHp(-40);
							break;
						case 6:
							_owner.addDmgup(-2);
							_owner.addBowDmgup(-2);
							_owner.addMaxHp(-40);
							_owner.getResistance().addMr(-1);
							_owner.getResistance().addPVPweaponTotalDamage(-1);
							break;
						case 7:
							_owner.addDmgup(-3);
							_owner.addBowDmgup(-3);
							_owner.addMaxHp(-50);
							_owner.getAbility().addSp(-1);
							_owner.getResistance().addPVPweaponTotalDamage(-2);
							_owner.getResistance().addMr(-3);
							break;
						case 8:
							_owner.addDmgup(-4);
							_owner.addBowDmgup(-4);
							_owner.addMaxHp(-50);
							_owner.getAbility().addSp(-2);
							_owner.getResistance().addPVPweaponTotalDamage(-3);
							_owner.getResistance().addMr(-5);
							break;
						case 9:
							_owner.addDmgup(-5);
							_owner.addBowDmgup(-5);
							_owner.addMaxHp(-60);
							_owner.getAbility().addSp(-3);
							_owner.getResistance().addPVPweaponTotalDamage(-5);
							_owner.getResistance().addMr(-7);
							break;
						default:
						}
					} else if (itemtype == 10) {// ��Ʈ
						switch (itemlvl) {
						case 1:
							_owner.addMaxMp(-5);
							break;
						case 2:
							_owner.addMaxMp(-10);
							break;
						case 3:
							_owner.addMaxMp(-20);
							break;
						case 4:
							_owner.addMaxMp(-30);
							break;
						case 5:
							_owner.addDamageReductionByArmor(-1);
							_owner.addMaxMp(-40);
							break;
						case 6:
							_owner.addDamageReductionByArmor(-2);
							_owner.addMaxMp(-40);
							_owner.addMaxHp(-20);
							_owner.getResistance().addcalcPcDefense(-1);
							break;
						case 7:
							_owner.addDamageReductionByArmor(-3);
							_owner.addMaxMp(-50);
							_owner.addMaxHp(-30);
							_owner.getResistance().addcalcPcDefense(-3);
							break;
						case 8:
							_owner.addDamageReductionByArmor(-4);
							_owner.addMaxMp(-50);
							_owner.addMaxHp(-40);
							_owner.getResistance().addcalcPcDefense(-5);
							break;
						case 9:
							_owner.addDamageReductionByArmor(-5);
							_owner.addMaxMp(-60);
							_owner.addMaxHp(-50);
							_owner.getResistance().addcalcPcDefense(-7);
							break;
						default:
						}
					}

					/** ����/ȸ���� ���� **/
				} else if (itemId == 490020 || itemId == 490022) {
					switch (itemlvl) {
					case 0:
						_owner.getAC().addAc(0);
						break;
					case 1:
						_owner.getAC().addAc(1);
						break;
					case 2:
						_owner.getAC().addAc(2);
						break;
					case 3:
						_owner.getAC().addAc(3);
						break;
					case 4:
						_owner.getAC().addAc(4);
						break;
					case 5:
						_owner.getAC().addAc(5);
						break;
					case 6:
						_owner.getAC().addAc(6);
						break;
					case 7:
						_owner.getAC().addAc(7);
						break;
					case 8:
						_owner.getAC().addAc(8);
						break;
					case 9:
						_owner.getAC().addAc(9);
						break;
					case 10:
						_owner.getAC().addAc(10);
						break;
					default:
						break;
					}
				} else if (itemId == 500007) { // ��Ƽ���� �������Ͱ���
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(-20);
						break;
					case 2:
						_owner.addMaxHp(-30);
						break;
					case 3:
						_owner.addMaxHp(-40);
						_owner.addDamageReductionByArmor(-1);
						break;
					case 4:
						_owner.addMaxHp(-50);
						_owner.addDamageReductionByArmor(-1);
						break;
					case 5:
						_owner.addMaxHp(-60);
						_owner.addDamageReductionByArmor(-2);
						break;
					case 6:
						_owner.addMaxHp(-70);
						_owner.addDamageReductionByArmor(-3);
						_owner.getAC().addAc(7);
						break;
					case 7:
						_owner.addMaxHp(-80);
						_owner.addDamageReductionByArmor(-4);
						_owner.getAC().addAc(8);
						_owner.addHitup(-1);
						_owner.addBowHitup(-1);
						break;
					case 8:
						_owner.addMaxHp(-90);
						_owner.addDamageReductionByArmor(-5);
						_owner.getAC().addAc(9);
						_owner.addHitup(-3);
						_owner.addBowHitup(-3);
						break;
					default:
					}
				} else if (itemId == 502007) { // ��Ƽ���� �������Ͱ���
					switch (itemlvl) {
					case 3:
						_owner.addMaxHp(-50);
						_owner.addDamageReductionByArmor(-1);
						break;
					case 4:
						_owner.addMaxHp(-60);
						_owner.addDamageReductionByArmor(-2);
						break;
					case 5:
						_owner.addMaxHp(-70);
						_owner.addDamageReductionByArmor(-3);
						_owner.getAC().addAc(7);
						break;
					case 6:
						_owner.addMaxHp(-80);
						_owner.addDamageReductionByArmor(-4);
						_owner.getAC().addAc(8);
						_owner.addHitup(-1);
						_owner.addBowHitup(-1);
						break;
					case 7:
						_owner.addMaxHp(-90);
						_owner.addDamageReductionByArmor(-5);
						_owner.getAC().addAc(9);
						_owner.addHitup(-3);
						_owner.addBowHitup(-3);
						break;
					case 8:
						_owner.addMaxHp(-140);
						_owner.addDamageReductionByArmor(-6);
						_owner.getAC().addAc(10);
						_owner.addHitup(-5);
						_owner.addBowHitup(-5);
						break;
					default:
					}
				} else if (itemId == 500008) { // ��Ƽ���� Ǫ�����Ͱ���
					switch (itemlvl) {
					case 5:
						_owner.getAC().addAc(1);
						break;
					case 6:
						_owner.getAC().addAc(2);
						break;
					case 7:
						_owner.getAC().addAc(2);
						break;
					case 8:
						_owner.getAC().addAc(3);
						break;
					default:
					}
				} else if (itemId == 502008) { // ��Ƽ���� Ǫ�����Ͱ���
					switch (itemlvl) {
					case 4:
						_owner.getAC().addAc(1);
						break;
					case 5:
						_owner.getAC().addAc(2);
						break;
					case 6:
						_owner.getAC().addAc(2);
						break;
					case 7:
						_owner.getAC().addAc(3);
						break;
					case 8:
						_owner.getAC().addAc(4);
						break;
					default:
					}
				} else if (itemId == 500009) { // ��Ƽ���� �������Ͱ���
					switch (itemlvl) {
					case 1:
						_owner.addMaxMp(-10);
						break;
					case 2:
						_owner.addMaxMp(-15);
						break;
					case 3:
						_owner.addMaxMp(-30);
						_owner.getAbility().addSp(-1);
						break;
					case 4:
						_owner.addMaxMp(-35);
						_owner.getAbility().addSp(-1);
						break;
					case 5:
						_owner.addMaxMp(-50);
						_owner.getAbility().addSp(-2);
						break;
					case 6:
						_owner.addMaxMp(-55);
						_owner.getAbility().addSp(-2);
						_owner.getAC().addAc(1);
						break;
					case 7:
						_owner.addMaxMp(-70);
						_owner.getAbility().addSp(-3);
						_owner.getAC().addAc(2);
						break;
					case 8:
						_owner.addMaxMp(-95);
						_owner.getAbility().addSp(-3);
						_owner.getAC().addAc(3);
						break;
					default:
					}
				} else if (itemId == 502009) { // ��Ƽ���� �������Ͱ���
					switch (itemlvl) {
					case 3:
						_owner.addMaxMp(-35);
						_owner.getAbility().addSp(-1);
						break;
					case 4:
						_owner.addMaxMp(-50);
						_owner.getAbility().addSp(-2);
						break;
					case 5:
						_owner.addMaxMp(-55);
						_owner.getAbility().addSp(-2);
						_owner.getAC().addAc(1);
						break;
					case 6:
						_owner.addMaxMp(-70);
						_owner.getAbility().addSp(-3);
						_owner.getAC().addAc(2);
						break;
					case 7:
						_owner.addMaxMp(-95);
						_owner.getAbility().addSp(-3);
						_owner.getAC().addAc(3);
						break;
					case 8:
						_owner.addMaxMp(-125);
						_owner.getAbility().addSp(-4);
						_owner.getAC().addAc(4);
						break;
					default:
					}
				} else if ((itemId >= 425109 && itemId <= 425113) || (itemId >= 525109 && itemId <= 525113) || (itemId >= 625109 && itemId <= 625113)) {
					if (itemlvl > 0) {
						_owner.addMaxHp(-((itemlvl * 5) + 10));
					}
					switch (itemlvl) {
					case 2:
					case 3:
					case 4:
						_owner.getAC().addAc(itemlvl - 1);
						break;
					case 5:
					case 6:
					case 7:
					case 8:
						_owner.getAC().addAc(4);
						_owner.addDmgup(-(itemlvl - 4));
						_owner.addBowDmgup(-(itemlvl - 4));
						break;
					}
				} else if (itemgrade == 3 && itemId == 21247) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(-15);
						break;
					case 2:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(1);
						break;
					case 3:
						_owner.addMaxHp(-25);
						_owner.getAC().addAc(2);
						break;
					case 4:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(3);
						break;
					case 5:
						_owner.addMaxHp(-35);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-1);
						_owner.addBowDmgup(-1);
						break;
					case 6:
						_owner.addMaxHp(-40);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-2);
						_owner.addBowDmgup(-2);
						break;
					case 7:
						_owner.addMaxHp(-45);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-3);
						_owner.addBowDmgup(-3);
						break;
					case 8:
						_owner.addMaxHp(-50);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-4);
						_owner.addBowDmgup(-4);
						break;
					default:
						break;
					}
				} else if (itemgrade == 3 && itemId == 21248) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(-15);
						break;
					case 2:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(1);
						break;
					case 3:
						_owner.addMaxHp(-25);
						_owner.getAC().addAc(2);
						break;
					case 4:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(3);
						break;
					case 5:
						_owner.addMaxHp(-35);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-1);
						_owner.addBowDmgup(-1);
						break;
					case 6:
						_owner.addMaxHp(-40);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-2);
						_owner.addBowDmgup(-2);
						break;
					case 7:
						_owner.addMaxHp(-45);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-3);
						_owner.addBowDmgup(-3);
						_owner.addDamageReductionByArmor(-1);
						break;
					case 8:
						_owner.addMaxHp(-50);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-4);
						_owner.addBowDmgup(-4);
						_owner.addDamageReductionByArmor(-2);
						break;
					default:
						break;
					}
				} else

				if (itemgrade == 3 && itemId == 21246) { // �����ǹ���
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(-5);
						break;
					case 2:
						_owner.addMaxHp(-10);
						_owner.getAC().addAc(1);
						break;
					case 3:
						_owner.addMaxHp(-15);
						_owner.getAC().addAc(2);
						break;
					case 4:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(3);
						break;
					case 5:
						_owner.addMaxHp(-25);
						_owner.getAC().addAc(3);
						_owner.getAbility().addSp(-1);
						break;
					case 6:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(4);
						_owner.getAbility().addSp(-2);
						break;
					case 7:
						_owner.addMaxHp(-35);
						_owner.getAC().addAc(4);
						_owner.getAbility().addSp(-3);
						_owner.getResistance().addPVPweaponTotalDamage(-1);
						break;
					case 8:
						_owner.addMaxHp(-40);
						_owner.getAC().addAc(5);
						_owner.getAbility().addSp(-4);
						_owner.addMaxMp(-30);
						_owner.getResistance().addPVPweaponTotalDamage(-2);
						break;
					default:
						break;
					}
				} else if (itemgrade == 3 && itemId == 21249) {// ����ǹ���
					switch (itemlvl) {
					case 1:
						_owner.getAC().addAc(1);
						break;
					case 2:
						_owner.getAC().addAc(2);
						break;
					case 3:
						_owner.addMaxHp(-5);
						_owner.getAC().addAc(3);
						break;
					case 4:
						_owner.addMaxHp(-10);
						_owner.getAC().addAc(4);
						break;
					case 5:
						_owner.addMaxHp(-15);
						_owner.getAC().addAc(4);
						_owner.addHitup(-1);
						_owner.addBowHitup(-1);
						_owner.addDmgup(-1);
						_owner.addBowDmgup(-1);
						break;
					case 6:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(4);
						_owner.addHitup(-2);
						_owner.addBowHitup(-2);
						_owner.addDmgup(-2);
						_owner.addBowDmgup(-2);
						break;
					case 7:
						_owner.addMaxHp(-25);
						_owner.getAC().addAc(4);
						_owner.addHitup(-3);
						_owner.addBowHitup(-3);
						_owner.addDmgup(-3);
						_owner.addBowDmgup(-3);
						_owner.getResistance().addPVPweaponTotalDamage(-1);
						break;
					case 8:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(4);
						_owner.addHitup(-4);
						_owner.addBowHitup(-4);
						_owner.addDmgup(-4);
						_owner.addBowDmgup(-4);
						_owner.getResistance().addPVPweaponTotalDamage(-2);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ü�� ���� **/
				} else if (itemgrade == 3 && itemId == 21252) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(-15);
						break;
					case 2:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(1);
						break;
					case 3:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(2);
						break;
					case 4:
						_owner.addMaxHp(-35);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-1);
						_owner.addBowDmgup(-1);
						break;
					case 5:
						_owner.addMaxHp(-40);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-2);
						_owner.addBowDmgup(-2);
						break;
					case 6:
						_owner.addMaxHp(-45);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-3);
						_owner.addBowDmgup(-3);
						_owner.addDamageReductionByArmor(-1);
						break;
					case 7:
						_owner.addMaxHp(-55);
						_owner.getAC().addAc(5);
						_owner.addDmgup(-4);
						_owner.addBowDmgup(-4);
						_owner.getResistance().addPVPweaponTotalDamage(-1);
						_owner.addDamageReductionByArmor(-2);
						break;
					case 8:
						_owner.addMaxHp(-65);
						_owner.getAC().addAc(5);
						_owner.addDmgup(-5);
						_owner.addBowDmgup(-5);
						_owner.getResistance().addPVPweaponTotalDamage(-2);
						_owner.addDamageReductionByArmor(-3);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ȸ��,����,���� ���� **/
				} else if (itemgrade == 3 && itemId == 21273 || itemId == 21274 || itemId == 21275) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(-15);
						break;
					case 2:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(1);
						break;
					case 3:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(2);
						break;
					case 4:
						_owner.addMaxHp(-35);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-1);
						_owner.addBowDmgup(-1);
						break;
					case 5:
						_owner.addMaxHp(-40);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-2);
						_owner.addBowDmgup(-2);
						break;
					case 6:
						_owner.addMaxHp(-45);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-3);
						_owner.addBowDmgup(-3);
						break;
					case 7:
						_owner.addMaxHp(-50);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-4);
						_owner.addBowDmgup(-4);
						_owner.getResistance().addPVPweaponTotalDamage(-1);
						break;
					case 8:
						_owner.addMaxHp(-50);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-5);
						_owner.addBowDmgup(-5);
						_owner.getResistance().addPVPweaponTotalDamage(-2);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ���� ���� ���� **/
				} else if (itemgrade == 3 && itemId == 21251) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(-15);
						break;
					case 2:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(1);
						break;
					case 3:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(2);
						break;
					case 4:
						_owner.addMaxHp(-35);
						_owner.getAC().addAc(3);
						_owner.addDmgup(-1);
						_owner.addBowDmgup(-1);
						break;
					case 5:
						_owner.addMaxHp(-40);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-2);
						_owner.addBowDmgup(-2);
						break;
					case 6:
						_owner.addMaxHp(-45);
						_owner.getAC().addAc(4);
						_owner.addDmgup(-3);
						_owner.addBowDmgup(-3);
						_owner.getResistance().addMr(-1);
						break;
					case 7:
						_owner.addMaxHp(-50);
						_owner.getAC().addAc(5);
						_owner.addDmgup(-4);
						_owner.addBowDmgup(-4);
						_owner.getResistance().addMr(-2);
						_owner.getResistance().addPVPweaponTotalDamage(-1);
						break;
					case 8:
						_owner.addMaxHp(-50);
						_owner.getAC().addAc(5);
						_owner.addDmgup(-5);
						_owner.addBowDmgup(-5);
						_owner.getResistance().addMr(-3);
						_owner.getResistance().addPVPweaponTotalDamage(-2);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ���� ���� **/
				} else if (itemgrade == 3 && itemId == 21250) {
					switch (itemlvl) {
					case 1:
						_owner.addMaxHp(-5);
						break;
					case 2:
						_owner.addMaxHp(-10);
						_owner.getAC().addAc(1);
						break;
					case 3:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(2);
						break;
					case 4:
						_owner.addMaxHp(-25);
						_owner.getAC().addAc(3);
						_owner.getAbility().addSp(-1);
						break;
					case 5:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(3);
						_owner.getAbility().addSp(-2);
						break;
					case 6:
						_owner.addMaxHp(-35);
						_owner.getAC().addAc(3);
						_owner.getAbility().addSp(-3);
						break;
					case 7:
						_owner.addMaxHp(-40);
						_owner.getAC().addAc(4);
						_owner.getAbility().addSp(-4);
						_owner.getResistance().addPVPweaponTotalDamage(-1);
						_owner.addMaxMp(-15);
						break;
					case 8:
						_owner.addMaxHp(-50);
						_owner.getAC().addAc(4);
						_owner.getAbility().addSp(-5);
						_owner.getResistance().addPVPweaponTotalDamage(-2);
						_owner.addMaxMp(-20);
						break;
					default:
						break;
					}
					/** �ູ���� �������� ��� ���� **/
				} else

				if (itemgrade == 3 && itemId == 21253) {
					switch (itemlvl) {
					case 1:
						_owner.getAC().addAc(1);
						break;
					case 2:
						_owner.getAC().addAc(2);
						break;
					case 3:
						_owner.addMaxHp(-10);
						_owner.getAC().addAc(3);
						break;
					case 4:
						_owner.addMaxHp(-15);
						_owner.getAC().addAc(4);
						_owner.addHitup(-1);
						_owner.addBowHitup(-1);
						_owner.addDmgup(-1);
						_owner.addBowDmgup(-1);
						break;
					case 5:
						_owner.addMaxHp(-20);
						_owner.getAC().addAc(4);
						_owner.addHitup(-2);
						_owner.addBowHitup(-2);
						_owner.addDmgup(-2);
						_owner.addBowDmgup(-2);
						break;
					case 6:
						_owner.addMaxHp(-25);
						_owner.getAC().addAc(4);
						_owner.addHitup(-3);
						_owner.addBowHitup(-3);
						_owner.addDmgup(-3);
						_owner.addBowDmgup(-3);
						break;
					case 7:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(5);
						_owner.addHitup(-4);
						_owner.addBowHitup(-4);
						_owner.addDmgup(-4);
						_owner.addBowDmgup(-4);
						_owner.getResistance().addPVPweaponTotalDamage(-1);
						break;
					case 8:
						_owner.addMaxHp(-30);
						_owner.getAC().addAc(5);
						_owner.addHitup(-5);
						_owner.addBowHitup(-5);
						_owner.addDmgup(-5);
						_owner.addBowDmgup(-5);
						_owner.getResistance().addPVPweaponTotalDamage(-2);
						break;
					default:
						break;
					}
				} else

				// �Ƴ�� �ø���
				/*
				 * if (itemId == 90093 ||itemId == 90094 ||itemId == 90095 ||itemId == 90096
				 * ||itemId == 90097 ||itemId == 90098 ||itemId == 90099 ||itemId == 90100) {
				 * switch (itemlvl) {
				 * 
				 * case 7: _owner.addDmgup(-3); break; case 8: _owner.addDmgup(-5); break; case
				 * 9: _owner.addDmgup(-7); break; case 10: _owner.addDmgup(-10); break; default:
				 * break; }
				 * 
				 * } else
				 */

				if (itemId == 110051 || itemId == 110052 || itemId == 110053 // ��Ÿ�¹����
						|| itemId == 110054 || itemId == 110055 || itemId == 110056 || itemId == 110057 || itemId == 110058) {
					switch (itemlvl) {

					case 7:
						_owner.addDmgup(-3);
						break;
					case 8:
						_owner.addDmgup(-5);
						break;
					case 9:
						_owner.addDmgup(-7);
						break;
					case 10:
						_owner.addDmgup(-10);
						break;
					default:
						break;
					}

				} else

				if (itemId == 500010 || itemId == 502010) {
					int ac = itemlvl;

					if (item.getBless() == 0 && itemlvl >= 3) {
						ac += 1;
					}
					_owner.getAC().addAc(ac);

					int dm = itemlvl - 2;

					if (item.getBless() != 0 && itemlvl >= 4)
						dm -= 1;

					_owner.addDmgup(-dm);
					_owner.addBowDmgup(-dm);
				}
			}
		}
		armor.stopEquipmentTimer();
		// if (itemId == 490020)
		_owner.set���巹��(0);
		_armors.remove(armor);

		/*
		 * if (armor.getSkill() != null && armor.getSkill().getSkillId() != 0) { switch
		 * (armor.getSkill().getSkillId()) { case L1SkillId.BLESSED_ARMOR:
		 * _owner.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 748, 0,
		 * false, false)); break; default: break; } }
		 */
	}

	public void set(L1ItemInstance equipment, boolean doubleweapon, boolean loaded) {
		L1Item item = equipment.getItem();

		if (item.getType2() == 0) {
			return;
		}

		if (item.get_addhp() != 0) {
			_owner.addMaxHp(item.get_addhp());
		}
		if (item.get_addmp() != 0) {
			if (item.getItemId() == 20001) // ���� 10�� ����
				_owner.addMaxMp(item.get_addmp() + (equipment.getEnchantLevel() * 10));
			else
				_owner.addMaxMp(item.get_addmp());
		}
		_owner.getAbility().addAddedStr(item.get_addstr());
		_owner.getAbility().addAddedCon(item.get_addcon());
		_owner.getAbility().addAddedDex(item.get_adddex());
		_owner.getAbility().addAddedInt(item.get_addint());
		_owner.getAbility().addAddedWis(item.get_addwis());

		if (equipment.getTechniqueTolerance() != 0)
			_owner.addTechniqueTolerance(equipment.getTechniqueTolerance());
		if (equipment.getSpiritTolerance() != 0)
			_owner.addSpiritTolerance(equipment.getSpiritTolerance());
		if (equipment.getDragonLangTolerance() != 0)
			_owner.addDragonLangTolerance(equipment.getDragonLangTolerance());
		if (equipment.getFearTolerance() != 0)
			_owner.addFearTolerance(equipment.getFearTolerance());
		if (equipment.getAllTolerance() != 0)
			_owner.addAllTolerance(equipment.getAllTolerance());

		if (equipment.getTechniqueHit() != 0)
			_owner.addTechniqueHit(equipment.getTechniqueHit());
		if (equipment.getSpiritHit() != 0)
			_owner.addSpiritHit(equipment.getSpiritHit());
		if (equipment.getDragonLangHit() != 0)
			_owner.addDragonLangHit(equipment.getDragonLangHit());
		if (equipment.getFearHit() != 0)
			_owner.addFearHit(equipment.getFearHit());
		if (equipment.getAllHit() != 0)
			_owner.addAllHit(equipment.getAllHit());

		if (item.get_addwis() != 0) {
			_owner.resetBaseMr();
		}
		_owner.getAbility().addAddedCha(item.get_addcha());

		int addMr = 0;
		addMr += equipment.getMr();
		if (item.getItemId() == 20236 && _owner.isElf()) {
			addMr += 5;
		}
		if (addMr != 0) {
			_owner.getResistance().addMr(addMr);
			_owner.sendPackets(new S_SPMR(_owner));
		}
		if (item.get_addsp() != 0) {
			_owner.getAbility().addSp(item.get_addsp());
			_owner.sendPackets(new S_SPMR(_owner));
		}
		if (item.isHasteItem()) {
			_owner.addHasteItemEquipped(1);
			_owner.removeHasteSkillEffect();
			if (_owner.getMoveState().getMoveSpeed() != 1) {
				_owner.getMoveState().setMoveSpeed(1);
				_owner.sendPackets(new S_SkillHaste(_owner.getId(), 1, -1));
				Broadcaster.broadcastPacket(_owner, new S_SkillHaste(_owner.getId(), 1, 0));
			}
		}
		_owner.getEquipSlot().setMagicHelm(equipment);

		if (item.getType2() == 1) {
			setWeapon(equipment, doubleweapon);
		} else if (item.getType2() == 2) {
			setArmor(equipment, loaded);
			_owner.sendPackets(new S_SPMR(_owner));
		}
		_owner.sendPackets(new S_PacketBox(S_PacketBox.char_ER, _owner.get_PlusEr()), true);
	}

	public void remove(L1ItemInstance equipment, boolean doubleweapon) {
		L1Item item = equipment.getItem();
		if (item.getType2() == 0) {
			return;
		}

		if (item.get_addhp() != 0) {
			_owner.addMaxHp(-item.get_addhp());
		}
		if (item.get_addmp() != 0) {
			if (item.getItemId() == 20001) // ���� 10�� ����
				_owner.addMaxMp(-(item.get_addmp() + (equipment.getEnchantLevel() * 10)));
			else
				_owner.addMaxMp(-item.get_addmp());
		}
		_owner.getAbility().addAddedStr((byte) -item.get_addstr());
		_owner.getAbility().addAddedCon((byte) -item.get_addcon());
		_owner.getAbility().addAddedDex((byte) -item.get_adddex());
		_owner.getAbility().addAddedInt((byte) -item.get_addint());
		_owner.getAbility().addAddedWis((byte) -item.get_addwis());

		if (equipment.getTechniqueTolerance() != 0)
			_owner.addTechniqueTolerance(-equipment.getTechniqueTolerance());
		if (equipment.getSpiritTolerance() != 0)
			_owner.addSpiritTolerance(-equipment.getSpiritTolerance());
		if (equipment.getDragonLangTolerance() != 0)
			_owner.addDragonLangTolerance(-equipment.getDragonLangTolerance());
		if (equipment.getFearTolerance() != 0)
			_owner.addFearTolerance(-equipment.getFearTolerance());
		if (equipment.getAllTolerance() != 0)
			_owner.addAllTolerance(-equipment.getAllTolerance());

		if (equipment.getTechniqueHit() != 0)
			_owner.addTechniqueHit(-equipment.getTechniqueHit());
		if (equipment.getSpiritHit() != 0)
			_owner.addSpiritHit(-equipment.getSpiritHit());
		if (equipment.getDragonLangHit() != 0)
			_owner.addDragonLangHit(-equipment.getDragonLangHit());
		if (equipment.getFearHit() != 0)
			_owner.addFearHit(-equipment.getFearHit());
		if (equipment.getAllHit() != 0)
			_owner.addAllHit(-equipment.getAllHit());

		if (item.get_addwis() != 0) {
			_owner.resetBaseMr();
		}
		_owner.getAbility().addAddedCha((byte) -item.get_addcha());

		int addMr = 0;
		addMr -= equipment.getMr();
		if (item.getItemId() == 20236 && _owner.isElf()) {
			addMr -= 5;
		}
		if (addMr != 0) {
			_owner.getResistance().addMr(addMr);
			_owner.sendPackets(new S_SPMR(_owner));
		}
		if (item.get_addsp() != 0) {
			_owner.getAbility().addSp(-item.get_addsp());
			_owner.sendPackets(new S_SPMR(_owner));
		}
		if (item.isHasteItem()) {
			_owner.addHasteItemEquipped(-1);
			if (_owner.getHasteItemEquipped() == 0) {
				_owner.getMoveState().setMoveSpeed(0);
				_owner.sendPackets(new S_SkillHaste(_owner.getId(), 0, 0));
				Broadcaster.broadcastPacket(_owner, new S_SkillHaste(_owner.getId(), 0, 0));
			}
		}
		_owner.getEquipSlot().removeMagicHelm(_owner.getId(), equipment);

		if (item.getType2() == 1) {
			removeWeapon(equipment, doubleweapon);
		} else if (item.getType2() == 2) {
			removeArmor(equipment);
		}
		_owner.sendPackets(new S_PacketBox(S_PacketBox.char_ER, _owner.get_PlusEr()), true);
	}

	public void setMagicHelm(L1ItemInstance item) {
		switch (item.getItemId()) {
		case 20008:
			_owner.setSkillMastery(HASTE);
			_owner.sendPackets(
					new S_AddSkill(0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, _owner.getElfAttr()));
			break;
		case 20013:
		case 120013:
			_owner.setSkillMastery(PHYSICAL_ENCHANT_DEX);
			_owner.setSkillMastery(HASTE);
			_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, PHYSICAL_ENCHANT_DEX, true), true);
			_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, HASTE, true), true);
			break;
		case 20014:
		case 120014:
			_owner.setSkillMastery(HEAL);
			_owner.setSkillMastery(EXTRA_HEAL);
			_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, HEAL, true), true);
			_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, EXTRA_HEAL, true), true);
			break;
		case 20015:
		case 120015:
			_owner.setSkillMastery(ENCHANT_WEAPON);
			_owner.setSkillMastery(DETECTION);
			_owner.setSkillMastery(PHYSICAL_ENCHANT_STR);
			_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, PHYSICAL_ENCHANT_STR, true), true);
			_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, DETECTION, true), true);
			_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, ENCHANT_WEAPON, true), true);
			break;
		case 20023:
			_owner.setSkillMastery(GREATER_HASTE);
			_owner.sendPackets(
					new S_AddSkill(0, 0, 0, 0, 0, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, _owner.getElfAttr()));
			break;
		}
	}

	public void removeMagicHelm(int objectId, L1ItemInstance item) {
		switch (item.getItemId()) {
		case 20008:
			if (!SkillsTable.getInstance().spellCheck(objectId, HASTE)) {
				_owner.removeSkillMastery(HASTE);
				_owner.sendPackets(new S_DelSkill(0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
			}
			break;
		case 20013:
		case 120013:
			if (!SkillsTable.getInstance().spellCheck(objectId, PHYSICAL_ENCHANT_DEX)) {
				_owner.removeSkillMastery(PHYSICAL_ENCHANT_DEX);
				_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, PHYSICAL_ENCHANT_DEX, false), true);
			}
			if (!SkillsTable.getInstance().spellCheck(objectId, HASTE)) {
				_owner.removeSkillMastery(HASTE);
				_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, HASTE, false), true);
			}
			break;
		case 20014:
		case 120014:
			if (!SkillsTable.getInstance().spellCheck(objectId, HEAL)) {
				_owner.removeSkillMastery(HEAL);
				_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, HEAL, false), true);
			}
			if (!SkillsTable.getInstance().spellCheck(objectId, EXTRA_HEAL)) {
				_owner.removeSkillMastery(EXTRA_HEAL);
				_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, EXTRA_HEAL, false), true);
			}
			break;
		case 120015:
		case 20015:
			if (!SkillsTable.getInstance().spellCheck(objectId, ENCHANT_WEAPON)) {
				_owner.removeSkillMastery(ENCHANT_WEAPON);
				_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, ENCHANT_WEAPON, false), true);
			}
			if (!SkillsTable.getInstance().spellCheck(objectId, DETECTION)) {
				_owner.removeSkillMastery(DETECTION);
				_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, DETECTION, false), true);
			}
			if (!SkillsTable.getInstance().spellCheck(objectId, PHYSICAL_ENCHANT_STR)) {
				_owner.removeSkillMastery(PHYSICAL_ENCHANT_STR);
				_owner.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, _owner, PHYSICAL_ENCHANT_STR, false), true);
			}
			break;
		case 20023:
			if (!SkillsTable.getInstance().spellCheck(objectId, GREATER_HASTE)) {
				_owner.removeSkillMastery(GREATER_HASTE);
				_owner.sendPackets(new S_DelSkill(0, 0, 0, 0, 0, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
			}
			break;
		}
	}

	public L1ItemInstance gettypeArmor() {
		L1ItemInstance ar = null;
		for (L1ItemInstance list : _armors) {
			if (list.getItem().getType() == 2) {
				ar = list;
				break;
			}

		}
		return ar;
	}

	public int ���巹��(int itemid, int itemlvl) {
		if (itemid == 490020) {
			if (itemlvl <= 6)
				return itemlvl + 1;
			switch (itemlvl) {
			case 7:
				return 9;
			case 8:
				return 11;
			case 9:
				return 13;
			case 10:
				return 15;
			}
		}
		return 0;
	}

}
