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

package l1j.server.server.model.item.function;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ACTION_UI;
import l1j.server.server.serverpackets.S_AddSkill;
import l1j.server.server.serverpackets.S_CreateItem;
import l1j.server.server.serverpackets.S_NewAddSkill;
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1EtcItem;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Skills;

@SuppressWarnings("serial")
public class Spellbook extends L1ItemInstance {

	public Spellbook(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		try {
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
				int itemId = useItem.getItemId();
				int delay_id = 0;
				if (useItem.getItem().getType2() == 0) { // �������� ���� ������
					delay_id = ((L1EtcItem) useItem.getItem()).get_delayid();
				}
				if (delay_id != 0) { // ���� ���� �־�
					if (pc.hasItemDelay(delay_id) == true) {
						return;
					}
				}
				if ((itemId == 40218 || itemId == 600485) && !pc.isWizard())
					return;
				if (itemId == 411488 && !pc.isKnight())
					return;
				if (itemId == 6002651 && !pc.isKnight())
					return;
				if (itemId == 6002652 && !pc.isKnight())
					return;
				if (itemId == 411533 && !pc.isElf())
					return;
				if (itemId == 40236 && !pc.isElf())
					return;
				if (itemId == 55594 && !pc.isDarkelf())
					return;
				if (itemId == 600361 && !pc.isDarkelf())
					return;
				if (itemId == 6002650 && !pc.isDarkelf())
					return;
				if (itemId == 40279 && !pc.isDarkelf())
					return;
				if (itemId == 40271 && !pc.isDarkelf())
					return;
				if (itemId == 55595 && !pc.isDarkelf())
					return;
				if (itemId == 55596 && !pc.isKnight())
					return;
				if (itemId == 55597 && !pc.isDragonknight())
					return;
				if (itemId == 55598 && !pc.isDragonknight())
					return;
				if (itemId == 600360 && !pc.isDragonknight())
					return;
				if (itemId == 439107 && !pc.isDragonknight())
					return;
				if (itemId == 439112 && !pc.isDragonknight())
					return;
				if (itemId == 439100 && !pc.isDragonknight())
					return;
				if (itemId == 55599 && !pc.isWarrior())
					return;
				if (itemId == 600358 && !pc.isIllusionist())
					return;

				if (itemId == 40218 || itemId == 600485 || itemId == 55594 || itemId == 55595 || itemId == 55596 || itemId == 55597 || itemId == 55598
						|| itemId == 600360 || itemId == 55599 || itemId == 411488 || itemId == 411533 || itemId == 600358 || itemId == 40271 
						|| itemId == 6002651 || itemId == 6002652 || itemId == 439107 || itemId == 439112 || itemId == 439100) {
					newSkills(pc, useItem, itemId);
					return;
				}
				if (itemId == 40236) {
					newSkills(pc, useItem, itemId);
					return;
				}
				if (itemId == 600361) {
					newSkills(pc, useItem, itemId);
					return;
				}
				if (itemId == 6002650) {
					newSkills(pc, useItem, itemId);
					return;
				}
				if (itemId == 40279) {
					newSkills(pc, useItem, itemId);
					return;
				}
				if (itemId > 40169 && itemId < 40226 || itemId >= 45000 && itemId <= 45022 || (itemId == 140186 || itemId == 140196 || itemId == 140198
						|| itemId == 140204 || itemId == 140205 || itemId == 140210 || itemId == 140219)) { // ������
					useSpellBook(pc, useItem, itemId);
				} else if ((itemId > 40225 && itemId < 40232) || itemId == 60348 || itemId == 402281 || itemId == 600328 || itemId == 600482) {
					if (pc.isCrown() || pc.isGm()) {
						if (itemId == 40226) {
							if (pc.getLevel() >= 50)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "50"), true);
						} else if (itemId == 40228) {
							if (pc.getLevel() >= 80)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "80"), true);
						} else if (itemId == 40227) {
							if (pc.getLevel() >= 60)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);
						} else if (itemId == 40231) {
							if (pc.getLevel() >= 80)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "80"), true);
						} else if (itemId == 40230) {
							if (pc.getLevel() >= 70)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "70"), true);
						} else if (itemId == 40229) {
							if (pc.getLevel() >= 75)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "75"), true);
						} else if (itemId == 60348 || itemId == 600328) {
							if (pc.getLevel() >= 80)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "80"), true);
						} else if (itemId == 402281) {
							if (pc.getLevel() >= 80)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "85"), true);
						} else if (itemId == 600482) { // ������
							if (pc.getLevel() >= 85)
								����������ų(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "85"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true); // LV�� ���Ƽ�
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					}
				} else if ((itemId >= 40232 && itemId <= 40235) // ������ ����
						|| (itemId >= 40237 && itemId <= 40264) || (itemId >= 41149 && itemId <= 41153) || itemId == 411533 || itemId == 600329
						|| (itemId >= 60530 && itemId <= 60532) || itemId == 600484) {
					useElfSpellBook(pc, useItem, itemId);
				} else if ((itemId > 40264 && itemId <= 40270) || (itemId >= 40272 && itemId <= 40278) || itemId == 60199 || itemId == 55593
						  || itemId == 600486 || itemId == 600518) {
					if (pc.isDarkelf() || pc.isGm()) {
						if (itemId >= 40265 && itemId <= 40269) { // ��� ������ ����
							if (pc.getLevel() >= 15)
								SpellBook1(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "15"), true);
						} else if (itemId == 40270 || itemId == 40272 || itemId == 40273 || itemId == 40274) { // / ��� ������ ����
							if (pc.getLevel() >= 30)
								SpellBook1(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "30"), true);
						} else if (itemId >= 40275 && itemId <= 40278) {
							if (pc.getLevel() >= 45)
								SpellBook1(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "45"), true);
						} else if (itemId == 60199) {
							if (pc.getLevel() >= 60) { // �ƸӺ극��ũ
								SpellBook1(pc, useItem);
							} else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);
						} else if (itemId == 55593) {
							if (pc.getLevel() >= 80) { // �����
								�����(pc, useItem);
							} else
								pc.sendPackets(new S_ServerMessage(3321, "80"), true);
						} else if (itemId == 600518) {
							if (pc.getLevel() >= 80) { // �����
								�����콺��(pc, useItem);
							} else
								pc.sendPackets(new S_ServerMessage(3321, "80"), true);
						} else if (itemId == 600486) {
							if (pc.getLevel() >= 85) { // ���
								�ٿ�������ų(pc, useItem);
							} else
								pc.sendPackets(new S_ServerMessage(3321, "85"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // (����:��� ������ ������ ��ũ ���������� ������ �� �ֽ��ϴ�. )
					}
				} else if ((itemId >= 600500 && itemId <= 600517)) {
					if (pc.isFencer() || pc.isGm()) {
						if ((itemId >= 600500 && itemId <= 600502) && pc.getLevel() >= 45) {
							�˻��Ǽ��нú�(pc, useItem);
						} else if ((itemId >= 600503 && itemId <= 600505) && pc.getLevel() >= 60) {
							�˻��Ǽ��нú�(pc, useItem);
						} else if ((itemId >= 600506 && itemId <= 600507) && pc.getLevel() >= 70) {
							�˻��Ǽ��нú�(pc, useItem);
						} else if ((itemId >= 600508 && itemId <= 600509) && pc.getLevel() >= 75) {
							�˻��Ǽ��нú�(pc, useItem);
						} else if ((itemId >= 600510 && itemId <= 600512) && pc.getLevel() >= 80) {
							�˻��Ǽ��нú�(pc, useItem);
						} else if (itemId == 600517 && pc.getLevel() >= 60) {
							�˻��Ǽ�(pc, useItem);
						} else if ((itemId == 600514 || itemId == 600516) && pc.getLevel() >= 70) {
							�˻��Ǽ�(pc, useItem);
						} else if ((itemId == 600515) && pc.getLevel() >= 75) {
							�˻��Ǽ�(pc, useItem);
						} else if ((itemId == 600513) && pc.getLevel() >= 80) {
							�˻��Ǽ�(pc, useItem);
						} else {
							pc.sendPackets(new S_ServerMessage(312));
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."));
					}
				} else if ((itemId >= 7300 && itemId <= 7311) || itemId == 210133 || itemId == 600489) {
					if (pc.isWarrior() || pc.isGm()) {
						if (itemId == 7302 && pc.getLevel() >= 15) {// �����̾� �нú�
							�����������нú�(pc, useItem);
						} else if (itemId == 7307 && pc.getLevel() >= 30) {// �Ͽ�
																			// ��ų
							����������(pc, useItem);
						} else if (itemId == 7308 && pc.getLevel() >= 45) {// �丶ȣũ
																			// ��ų
							����������(pc, useItem);
						} else if (itemId == 7300 && pc.getLevel() >= 45) {// ũ����
																			// �нú�
							�����������нú�(pc, useItem);
						} else if (itemId == 7303 && pc.getLevel() >= 60) {// �ƸӰ���
																			// �нú�
							�����������нú�(pc, useItem);
						} else if (itemId == 7309 && pc.getLevel() >= 60) {// �Ⱓƽ
																			// ��ų
							����������(pc, useItem);
						} else if (itemId == 7301 && pc.getLevel() >= 60) {// ǻ��
																			// �нú�
							�����������нú�(pc, useItem);
						} else if (itemId == 7304 && pc.getLevel() >= 75) {// Ÿ��ź��
																			// �нú�
							�����������нú�(pc, useItem);
						} else if (itemId == 7310 && pc.getLevel() >= 80) {// �������
																			// ��ų
							����������(pc, useItem);
						} else if (itemId == 7311 && pc.getLevel() >= 75) {// �Ŀ��׸�
																			// ��ų
							����������(pc, useItem);
						} else if (itemId == 210133 && pc.getLevel() >= 80) {// ����¡
							����������(pc, useItem);
						} else if (itemId == 7306 && pc.getLevel() >= 75) {// Ÿ��ź����
																			// ��ų
							�����������нú�(pc, useItem);
						} else if (itemId == 7305 && pc.getLevel() >= 80) {// Ÿ��ź��
																			// ��ų
							�����������нú�(pc, useItem);
						} else if (itemId == 73101 && pc.getLevel() >= 80) {// Ÿ��ź����¡
							// ��ų
							�����������нú�(pc, useItem);
						} else if (itemId == 600489 && pc.getLevel() >= 85) {// ��������
							����������ų(pc, useItem);
						} else {
							pc.sendPackets(new S_ServerMessage(312));
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."));
					}

				} else if (itemId >= 40164 && itemId <= 40166 // �����
						|| itemId >= 41147 && itemId <= 41148 || itemId == 411488 || itemId == 55560 || itemId == 55561 || itemId == 600483) {
					if (pc.isKnight() || pc.isGm()) {
						if (itemId == 40164) { // ��ũ ����
							if (pc.getLevel() >= 60)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);
						} else if (itemId == 40165) { // ������ �Ƹ�
							if (pc.getLevel() >= 50)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "50"), true);
						} else if (itemId == 41147) { // �ָ��� �ɸ���
							if (pc.getLevel() >= 55)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "55"), true);
						} else if (itemId == 41148) { // ī���� �踮��
							if (pc.getLevel() >= 80)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "80"), true);
						} else if (itemId == 40166) { // �ٿ ����
							if (pc.getLevel() >= 65)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "65"), true);
						} else if (itemId == 411488) { // �ۼַ�Ʈ ���̵�
							if (pc.getLevel() >= 85)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "85"), true);
						} else if (itemId == 55560) { // �����̵�
							if (pc.getLevel() >= 60)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);
						} else if (itemId == 55561) { // ��ο� ����
							if (pc.getLevel() >= 75)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "75"), true);
						} else if (itemId == 600483) { // ���� ����
							if (pc.getLevel() >= 85)
								���������ų(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "85"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					}
				} else if (itemId >= 439101 && itemId <= 439106 || itemId >= 439108 && itemId <= 439111 || itemId == 439113 || itemId == 439114 || itemId == 72018 || itemId == 72019
						|| itemId == 72020 || itemId == 55562 || itemId == 600487) {
					if (pc.isDragonknight() || pc.isGm()) {
						if (itemId >= 439101 && itemId <= 439104) {
							if (pc.getLevel() >= 20)
								SpellBook5(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "20"), true);
						} else if (itemId >= 439105 && itemId <= 439106 || itemId == 439108 || itemId == 439109) {
							if (pc.getLevel() >= 40)
								SpellBook5(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "40"), true);
						} else if (itemId >= 439110 && itemId <= 439111 || itemId == 439113 || itemId == 439114) {
							if (pc.getLevel() >= 60)
								SpellBook5(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);
						} else if (itemId == 55562) {
							if (pc.getLevel() >= 80)
								SpellBook5(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "80"), true);

						} else if (itemId == 600487) {
							if (pc.getLevel() >= 85)
								����������ų(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "85"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					}
				} else if (itemId >= L1ItemId.ILLUSIONIST_SPELLSTART && itemId <= L1ItemId.ILLUSIONIST_SPELLEND || itemId == 55564 || itemId == 2100199
						|| itemId == 600359 || itemId == 600488) {
					if (pc.isIllusionist() || pc.isGm()) {
						if (itemId >= L1ItemId.MEMORIALCRYSTAL_MIRRORIMAGE && itemId <= L1ItemId.MEMORIALCRYSTAL_CUBE_IGNITION) {
							if (pc.getLevel() >= 15)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "15"), true);
						} else if (itemId >= L1ItemId.MEMORIALCRYSTAL_CONSENTRATION && itemId <= L1ItemId.MEMORIALCRYSTAL_CUBE_QUAKE) {
							if (pc.getLevel() >= 30)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "30"), true);
						} else if (itemId >= L1ItemId.MEMORIALCRYSTAL_PATIENCE && itemId <= L1ItemId.MEMORIALCRYSTAL_CUBE_SHOCK) {
							if (pc.getLevel() >= 45)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "45"), true);
						} else if (itemId >= L1ItemId.MEMORIALCRYSTAL_INSITE && itemId <= L1ItemId.MEMORIALCRYSTAL_CUBE_BALANCE) {
							if (pc.getLevel() >= 60)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);
						} else if (itemId == 55564) {
							if (pc.getLevel() >= 75)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "75"), true);
						} else if (itemId == 2100199) {
							if (pc.getLevel() >= 80)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "80"), true);
						} else if (itemId == 600359) {
							if (pc.getLevel() >= 85)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "85"), true);
						} else if (itemId == 600488) {
							if (pc.getLevel() >= 85)
								ȯ����������ų(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "85"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					}
				}
				L1ItemDelay.onItemUse(pc, useItem); // ������ ���� ����
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ����������(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		L1Skills l1skills = null;
		for (int j6 = 225; j6 <= 231; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "������ ����(" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				if (pc.isSkillMastery(i)) {
					pc.sendPackets(new S_SystemMessage("�̹� ������ ��ų �Դϴ�."), true);
					return;
				}
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void �����������нú�(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		L1Skills l1skills = null;
		for (int i = 300; i < 307; i++) {
			l1skills = SkillsTable.getInstance().getTemplate(i);
			String s1 = "������ ����(" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int id = l1skills.getId();
				if (pc.isSkillMastery(i)) {
					pc.sendPackets(new S_SystemMessage("�̹� ������ �нú� �Դϴ�."), true);
					return;
				}
				switch (id) {
				case 1:
					pc.isCrash = true;
					break;
				case 2:
					pc.isPurry = true;
					break;
				case 3:
					pc.isSlayer = true;
					break;
				case 5:
					pc.isAmorGaurd = true;
					break;
				case 6:
					pc.isTaitanR = true;
					break;
				case 7:
					pc.isTaitanB = true;
					break;
				case 8:
					pc.isTaitanM = true;
					break;
				}
				pc.sendPackets(new S_NewUI(S_NewUI.�нú��߰�, id));
				S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
				pc.sendPackets(s_skillSound);
				Broadcaster.broadcastPacket(pc, s_skillSound);
				SkillsTable.getInstance().spellMastery(pc.getId(), i, l1skills.getName(), 0, 0);
				pc.getInventory().removeItem(l1iteminstance, 1);
			}
		}
	}

	private void �˻��Ǽ�(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		L1Skills l1skills = null;
		for (int j6 = 235; j6 <= 239; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "�˻��� �� (" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				if (pc.isSkillMastery(i)) {
					pc.sendPackets(new S_SystemMessage("�̹� ������ ��ų �Դϴ�."), true);
					return;
				}
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void �˻��Ǽ��нú�(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		L1Skills l1skills = null;
		for (int i = 550; i <= 562; i++) {
			l1skills = SkillsTable.getInstance().getTemplate(i);
			String s1 = "�˻��� �� (" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				if (pc.isSkillMastery(i)) {
					pc.sendPackets(new S_SystemMessage("�̹� ������ �нú� �Դϴ�."), true);
					return;
				}
				switch (i) {
				case 550:
					pc.infinity_A = true;
					break;
				case 551:
					pc.infinity_B = true;
					break;
				case 552:
					pc.infinity_D = true;
					break;
				case 553:
					pc.damascus = true;
					break;
				case 554:
					pc.paradox = true;
					break;
				case 555:
					pc.grous = true;
					break;
				case 556:
					pc.rage = true;
					break;
				case 557:
					pc.phantom_R = true;
					break;
				case 558:
					pc.phantom_D = true;
					break;
				case 559:
					pc.flame = true;
					break;
				case 560:
					pc.infinity_BL = true;
					break;
				case 561:
					pc.survive = true;
					break;
				case 562:
					pc.Pantera_S = true;
					break;
				}
				if (i == 562) {
					pc.sendPackets(new S_CreateItem(146, 36, 0));
				} else {
					pc.sendPackets(new S_CreateItem(146, i - 527, 0));
				}
				S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
				pc.sendPackets(s_skillSound);
				Broadcaster.broadcastPacket(pc, s_skillSound);
				SkillsTable.getInstance().spellMastery(pc.getId(), i, l1skills.getName(), 0, 0);
				pc.getInventory().removeItem(l1iteminstance, 1);
			}
		}
	}

	private void useSpellBook(L1PcInstance pc, L1ItemInstance item, int itemId) {
		int itemAttr = 0;
		int locAttr = 0; // 0:other 1:law 2:chaos
		boolean isLawful = true;
		int pcX = pc.getX();
		int pcY = pc.getY();
		int mapId = pc.getMapId();
		int level = pc.getLevel();
		if (itemId == 45000 || itemId == 45008 || itemId == 45018 || itemId == 45021 || itemId == 40171 || itemId == 40179 || itemId == 40180 || itemId == 40182
				|| itemId == 40194 || itemId == 40197 || itemId == 40202 || itemId == 40206 || itemId == 40213 || itemId == 40220 || itemId == 40222) {
			itemAttr = 1;
		}
		if (itemId == 45009 || itemId == 45010 || itemId == 45019 || itemId == 40172 || itemId == 40173 || itemId == 40178 || itemId == 40185 || itemId == 40186
				|| itemId == 40192 || itemId == 40196 || itemId == 40201 || itemId == 40204 || itemId == 40211 || itemId == 40221 || itemId == 40225
				|| itemId == 140186 || itemId == 140196 || itemId == 140204) {
			itemAttr = 2;
		}
		if (pcX > 33116 && pcX < 33128 && pcY > 32930 && pcY < 32942 && mapId == 4 || pcX > 33135 && pcX < 33147 && pcY > 32235 && pcY < 32247 && mapId == 4
				|| pcX >= 32783 && pcX <= 32803 && pcY >= 32831 && pcY <= 32851 && mapId == 77
				|| pcX >= 33189 && pcX <= 33198 && pcY >= 33446 && pcY <= 33456 && mapId == 4) {
			locAttr = 1;
			isLawful = true;
		}
		if (pcX > 32880 && pcX < 32892 && pcY > 32646 && pcY < 32658 && mapId == 4 || pcX > 32662 && pcX < 32674 && pcY > 32297 && pcY < 32309 && mapId == 4
				|| pcX >= 33075 && pcX <= 33082 && pcY >= 33212 && pcY <= 33220 && mapId == 4) {
			locAttr = 2;
			isLawful = false;
		}
		if (pc.isGm()) {
			SpellBook(pc, item, isLawful);
		} else {// if ((itemAttr == locAttr || itemAttr == 0) && locAttr != 0) {
			if (pc.isKnight()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 50)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "50"), true);
				} else if (itemId >= 45000 && itemId <= 45007) {
					pc.sendPackets(new S_ServerMessage(312), true);
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				}
			} else if (pc.isCrown() || pc.isDarkelf()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 15)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "15"), true);
				} else if (itemId >= 45008 && itemId <= 45015) {
					if (level >= 30)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "30"), true);
				} else if (itemId >= 45008 && itemId <= 45015 || itemId >= 45000 && itemId <= 45007) {
					pc.sendPackets(new S_ServerMessage(312), true);
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				}
			} else if (pc.isElf()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 10)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "10"), true);
				} else if (itemId >= 45008 && itemId <= 45015) {
					if (level >= 20)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "20"), true);
				} else if (itemId >= 45016 && itemId <= 45022) {
					if (level >= 30)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "30"), true);
				} else if (itemId >= 40170 && itemId <= 40177) {
					if (level >= 40)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "40"), true);
				} else if (itemId >= 40178 && itemId <= 40185) {
					if (level >= 50)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "50"), true);
				} else if (((itemId >= 40186 && itemId <= 40193) || itemId == 140186)) {
					if (level >= 60)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "60"), true);
				} else if (itemId >= 45000 && itemId <= 45022 || itemId >= 40170 && itemId <= 40193) {
					pc.sendPackets(new S_ServerMessage(312), true);
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				}
			} else if (pc.isFencer()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 15)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "15"), true);
				} else if (itemId >= 45008 && itemId <= 45015) {
					if (level >= 30)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "30"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				}
			} else if (pc.isWizard()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 8)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "8"), true);
				} else if (itemId >= 45008 && itemId <= 45015) {
					if (level >= 16)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "16"), true);
				} else if (itemId >= 45016 && itemId <= 45022) {
					if (level >= 24)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "24"), true);
				} else if (itemId >= 40170 && itemId <= 40177) {
					if (level >= 32)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "32"), true);
				} else if (itemId >= 40178 && itemId <= 40185) {
					if (level >= 40)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "40"), true);
				} else if (((itemId >= 40186 && itemId <= 40193) || itemId == 140186)) {
					if (level >= 48)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "48"), true);
				} else if (((itemId >= 40194 && itemId <= 40201) || itemId == 140196 || itemId == 140198)) {
					if (level >= 56)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "56"), true);
				} else if (((itemId >= 40202 && itemId <= 40209) || itemId == 140204 || itemId == 140205)) {
					if (level >= 64)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "64"), true);
				} else if (((itemId >= 40210 && itemId <= 40217) || itemId == 140210)) {
					if (level >= 72)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "72"), true);
				} else if (((itemId >= 40219 && itemId <= 40225) || itemId == 140219)) {
					if (level >= 80)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				} else {
					pc.sendPackets(new S_ServerMessage(312), true);
				}
			}
		}
	}

	private void useElfSpellBook(L1PcInstance pc, L1ItemInstance item, int itemId) {
		int level = pc.getLevel();
		if ((pc.isElf() || pc.isGm())) {
			if (itemId >= 40232 && itemId <= 40234) {
				if (level >= 15)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "15"), true);
			} else if (itemId >= 40235 && itemId <= 40235) {
				if (level >= 30)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "30"), true);
			} else if ((itemId >= 40237 && itemId <= 40240) && itemId != 40238) {
				if (level >= 45)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "45"), true);
			} else if (itemId >= 40241 && itemId <= 40243 || itemId == 60531 || itemId == 60530) {
				if (level >= 60)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "60"), true);
			} else if (itemId >= 40244 && itemId <= 40246) {
				if (level >= 75)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "75"), true);
			} else if (itemId >= 40247 && itemId <= 40248) {
				if (level >= 45)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "45"), true);
			} else if (itemId >= 40249 && itemId <= 40250) {
				if (level >= 60)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "60"), true);
			} else if (itemId >= 40251 && itemId <= 40252) {
				if (level >= 75)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "75"), true);
			} else if (itemId == 40253) {
				if (level >= 45)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "45"), true);
			} else if (itemId == 40254) {
				if (level >= 60)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "60"), true);
			} else if (itemId == 40255) {
				if (level >= 75)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "75"), true);
			} else if (itemId == 40256) {
				if (level >= 45)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "45"), true);
			} else if (itemId == 40257) {
				if (level >= 60)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "60"), true);
			} else if (itemId >= 40258 && itemId <= 40259) {
				if (level >= 75)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "75"), true);
			} else if (itemId >= 40260 && itemId <= 40261) {
				if (level >= 45)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "45"), true);
			} else if (itemId == 40262) {
				if (level >= 60)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "60"), true);
			} else if (itemId >= 40263 && itemId <= 40264) {
				if (level >= 75)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "75"), true);
			} else if (itemId >= 41149 && itemId <= 41150) {
				if (level >= 75)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "75"), true);
			} else if (itemId == 41151) {
				if (level >= 60)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "60"), true);
			} else if (itemId >= 41152 && itemId <= 41153) {
				if (level >= 75)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "75"), true);
			} else if (itemId == 60532) {
				if (level >= 75)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "75"), true);
			} else if (itemId == 411533 || itemId == 600329) {
				if (level >= 80)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "80"), true);
			} else if (itemId == 600484) {
				if (level >= 85)
					����������ų(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "85"), true);
			} else if (itemId == 40238) {
				�����ҿ�(pc, item);
			}
		} else {
			pc.sendPackets(new S_ServerMessage(312), true);
		}
	}

	private void SpellBook(L1PcInstance pc, L1ItemInstance item, boolean isLawful) {
		String s = "";
		int i = 0;
		L1Skills l1skills = null;
		for (int skillId = 1; skillId < 81; skillId++) {
			l1skills = SkillsTable.getInstance().getTemplate(skillId);
			String s1 = "������ (" + l1skills.getName() + ")";
			if (item.getItem().getName().equalsIgnoreCase(s1)) {
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int objid = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(objid, isLawful ? 224 : 231);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(objid, i, s, 0, 0);
		pc.getInventory().removeItem(item, 1);
	}

	private void SpellBook1(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		L1Skills l1skills = null;

		for (int j6 = 97; j6 < 113; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "�������� ���� (" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);// 231
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook2(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		L1Skills l1skills = null;
		for (int j6 = 129; j6 <= 179; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "������ ���� (" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				if (!pc.isGm() && l1skills.getAttr() != 0 && pc.getElfAttr() != l1skills.getAttr()) {
					if (pc.getElfAttr() == 0 || pc.getElfAttr() == 1 || pc.getElfAttr() == 2 || pc.getElfAttr() == 4 || pc.getElfAttr() == 8) { // �Ӽ�ġ�� �̻��� ����
																																				// ���Ӽ��� �����
																																				// �� �ֵ���(����) ��
																																				// �д�
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."));
						return;
					}
				}
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook3(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		L1Skills l1skills = null;
		for (int j6 = 87; j6 <= 94; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);

			String s1 = (new StringBuilder()).append("����� (").append(l1skills.getName()).append(")").toString();
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook4(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		L1Skills l1skills = null;
		for (int j6 = 113; j6 < 124; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "������ (" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook5(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		L1Skills l1skills = null;
		for (int j6 = 181; j6 < 200; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "������ ����(" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook6(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		for (int j6 = 201; j6 <= 224; j6++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "����� ����(" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				s = l1skills.getName();
				i = l1skills.getSkillId();
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, i), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void ����������ų(L1PcInstance pc, L1ItemInstance l1iteminstance, boolean isLawful) {
		String s = "";
		int j6 = 243;
		L1Skills l1skills = null;

		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "������ (" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, isLawful ? 224 : 231);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void �ٿ�������ų(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 244;
		L1Skills l1skills = null;

		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "�������� ���� (" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);// 231
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void �����(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 234;
		L1Skills l1skills = null;

		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "�������� ���� (" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);// 231
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}
	
	private void �����콺��(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 199;
		L1Skills l1skills = null;

		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "�������� ���� (" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);// 231
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void ����������ų(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 519;
		L1Skills l1skills = null;
		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "������ ���� (" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewUI(S_NewUI.�нú��߰�, 22));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.�۷θ�� = true;
		pc.getInventory().removeItem(l1iteminstance, 1);
	}
	
	private void �����ҿ�(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 564;
		L1Skills l1skills = null;
		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "������ ���� (" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewUI(S_NewUI.�нú��߰�, 38));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.�������ҿ� = true;
		pc.sendPackets(new S_ACTION_UI(S_ACTION_UI.SPELL_PASSIVE_ONOFF_ACK, 38, false));
		pc.getInventory().removeItem(l1iteminstance, 1);
	}


	private void ���������ų(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 242;
		L1Skills l1skills = null;
		l1skills = SkillsTable.getInstance().getTemplate(j6);

		String s1 = (new StringBuilder()).append("����� (").append(l1skills.getName()).append(")").toString();
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void ����������ų(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 241;
		L1Skills l1skills = null;
		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "������ (" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void ����������ų(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 245;
		L1Skills l1skills = null;
		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "������ ����(" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void ȯ����������ų(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int j6 = 246;
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "����� ����(" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}
		if (pc.isSkillMastery(j6)) {
			pc.sendPackets(new S_SystemMessage("�̹� ��� �����Դϴ�."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void ����������ų(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		L1Skills l1skills = null;
		int j6 = 247;
		l1skills = SkillsTable.getInstance().getTemplate(j6);
		String s1 = "������ ����(" + l1skills.getName() + ")";
		if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
			if (pc.isSkillMastery(j6)) {
				pc.sendPackets(new S_SystemMessage("�̹� ������ ��ų �Դϴ�."), true);
				return;
			}
			s = l1skills.getName();
			j6 = l1skills.getSkillId();
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_NewAddSkill(S_NewAddSkill.AddskillNew, pc, j6), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, j6, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	/** ���� ��ų */

	private void newSkills(L1PcInstance pc, L1ItemInstance item, int itemId) {
//		40218	������ (������)
//		55593	�������� ���� (��ؽ�)
//		55594	�������� ���� (����¡ ���Ǹ���)
//		210133	������ ����(Ÿ��ź ����¡)
//		402281	������ (�׷��̽� �ƹ�Ÿ)
//		411488	����� (�ۼַ�Ʈ ���̵�)
//		411533	������ ���� (�ҿ� �踮��)
//		2100199	����� ����(����Ʈ)
//		2100345	������ ����(��Ʈ����)
		switch (itemId) {
		case 40218:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isWizard()) {
				SpellBook(pc, item, true);
			}
			break;
		case 600485:
			if (pc.getLevel() < 85) {
				pc.sendPackets(new S_ServerMessage(3321, "85"), true);
				return;
			}
			if (pc.isWizard()) {
				����������ų(pc, item, true);
			}
			break;
		case 439107:
			if (pc.getLevel() < 30) {
				pc.sendPackets(new S_ServerMessage(3321, "30"), true);
				return;
			}
			if (pc.isDragonknight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(439107);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 40, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.��Ʈ�����Ǿ� = true;
				}
			}
			break;
		case 439112:
			if (pc.getLevel() < 45) {
				pc.sendPackets(new S_ServerMessage(3321, "45"), true);
				return;
			}
			if (pc.isDragonknight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(439112);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 41, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.��Ʈ����ȣ�� = true;
				}
			}
			break;
		case 439100:
			if (pc.getLevel() < 15) {
				pc.sendPackets(new S_ServerMessage(3321, "15"), true);
				return;
			}
			if (pc.isDragonknight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(439100);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 61, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.�巡�ｺŲ = true;
				}
			}
			break;
		case 55594:
			if (pc.getLevel() < 85) {
				pc.sendPackets(new S_ServerMessage(3321, "85"), true);
				return;
			}
			if (pc.isDarkelf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(55594);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 11, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.�ƸӺ극��ũ����Ƽ�� = true;
				}
			}
			break;
		case 55595:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isDarkelf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(55595);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 12, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.����극��ũ����Ƽ�� = true;
				}
			}
			break;
		case 55596:
			if (pc.getLevel() < 85) {
				pc.sendPackets(new S_ServerMessage(3321, "85"), true);
				return;
			}
			if (pc.isKnight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(55596);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 10, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.ī���͹踮��׶� = true;
				}
			}
			break;
		case 55597:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isDragonknight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(55597);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 14, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.����׷��극�̺� = true;
				}
			}
			break;
		case 600360:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isDragonknight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(600360);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 16, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.�ƿ��Ű�� = true;
				}
			}
			break;
		case 55598:
			if (pc.getLevel() < 85) {
				pc.sendPackets(new S_ServerMessage(3321, "85"), true);
				return;
			}
			if (pc.isDragonknight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(55598);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 15, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.���콽���̾�극�̺� = true;
				}
			}
			break;
		case 55599:
			if (pc.getLevel() < 85) {
				pc.sendPackets(new S_ServerMessage(3321, "85"), true);
				return;
			}
			if (pc.isWarrior()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(55599);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 13, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.������󵵾ۼַ�Ʈ = true;
				}
			}
			break;
		case 411488:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isKnight()) {
				SpellBook3(pc, item);
			}
			break;
		case 411533:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isElf()) {
				SpellBook2(pc, item);
			}
			break;
		case 60530:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isElf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(60530);
				if (skill != null) {
					int skillLevel = skill.getSkillLevel();
					int id = skill.getId();
					int[] arr = new int[23];
					arr[skillLevel - 1] = id;
					int skillId = skill.getSkillId();
					int objid = pc.getId();
					pc.sendPackets(new S_AddSkill(arr));
					S_SkillSound s_skillSound = new S_SkillSound(objid, 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(objid, skillId, skill.getName(), 0, 0);
				}
			}
			break;
		case 60531:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isElf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(60531);
				if (skill != null) {
					int skillLevel = skill.getSkillLevel();
					int id = skill.getId();
					int[] arr = new int[23];
					arr[skillLevel - 1] = id;
					int skillId = skill.getSkillId();
					int objid = pc.getId();
					pc.sendPackets(new S_AddSkill(arr));
					S_SkillSound s_skillSound = new S_SkillSound(objid, 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(objid, skillId, skill.getName(), 0, 0);
				}
			}
			break;
		case 60532:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isElf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(60532);
				if (skill != null) {
					int skillLevel = skill.getSkillLevel();
					int id = skill.getId();
					int[] arr = new int[23];
					arr[skillLevel - 1] = id;
					int skillId = skill.getSkillId();
					int objid = pc.getId();
					pc.sendPackets(new S_AddSkill(arr));
					S_SkillSound s_skillSound = new S_SkillSound(objid, 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(objid, skillId, skill.getName(), 0, 0);
				}
			}
			break;
		case 600358:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isIllusionist()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(600358);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 17, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.��ũȣ�� = true;
				}
			}
			break;
		case 40236:
			if (pc.isElf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(40236);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 21, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.������Ʈ������Ʈ = true;
					pc.getResistance().addMr(5);
					pc.sendPackets(new S_SPMR(pc), true);
					pc.getResistance().addAllNaturalResistance(5);
					pc.sendPackets(new S_OwnCharAttrDef(pc), true);
				}
			}
			break;
		case 40271:
			if (pc.getLevel() < 60) {
				pc.sendPackets(new S_ServerMessage(3321, "60"), true);
				return;
			}
			if (pc.isDarkelf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(40271);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 47, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					pc.���׽��Ǹ��� = true;
				}
			}
			break;
		case 40279:
			if (pc.getLevel() < 60) {
				pc.sendPackets(new S_ServerMessage(3321, "60"), true);
				return;
			}
			if (pc.isDarkelf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(40279);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 48, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.Add_Er(18);
					pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					pc.�巹���̺����� = true;
				}
			}
			break;
		case 600361:
			if (pc.getLevel() < 60) {
				pc.sendPackets(new S_ServerMessage(3321, "60"), true);
				return;
			}
			if (pc.isDarkelf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(600361);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 18, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.���̳ι� = true;
				}
			}
			break;
		case 6002650:
			if (pc.getLevel() < 85) {
				pc.sendPackets(new S_ServerMessage(3321, "85"), true);
				return;
			}
			if (pc.isDarkelf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(6002650);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 49, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.����۵���Ƽ�� = true;
				}
			}
			break;
		case 6002651:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isKnight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(6002651);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 43, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.�����ǾƸӺ��׶� = true;
				}
			}
			break;
		case 6002652:
			if (pc.getLevel() < 80) {
				pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				return;
			}
			if (pc.isKnight()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(6002652);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 44, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.����¡���� = true;
					if(pc.����¡���� && pc.getLevel() >= 80) {
						int pvpre = pc.getLevel() - 77;
						int i = (pvpre / 3) * 1;
						pc.addTechniqueHit(i);
					}
				}
			}
			break;
		}
		pc.getInventory().removeItem(item, 1);
	}

}
