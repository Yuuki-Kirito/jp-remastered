package server.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_InventorySwap;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Sound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;



public final class InvSwapController {
	
	private static Logger _log = Logger.getLogger(InvSwapController.class.getName());
	private static InvSwapController _instance;
	private static Map<Integer, Map<Integer, List<Integer>>> list;	// ��Ʈ�� ������ ��� ����.
	private static Map<Integer, Integer> code_list;				// ���� ������ ��Ʈ��ġ.
	public static InvSwapController getInstance() {
		if (_instance == null) {
			_instance = new InvSwapController();
		}
		return _instance;
	}
	
	private InvSwapController() {
		list = new HashMap<Integer, Map<Integer, List<Integer>>>();
		code_list = new HashMap<Integer, Integer>();
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			st = con.prepareStatement("SELECT * FROM characters_inventory_set");
			rs = st.executeQuery();
			while (rs.next()) {
				int key = rs.getInt("objectId");
				//
				Map<Integer, List<Integer>> db = list.get(key);
				if(db == null) {
					db = new HashMap<Integer, List<Integer>>();
					db.put(0, new ArrayList<Integer>());
					db.put(1, new ArrayList<Integer>());
					list.put(key, db);
				}
				//
				String set1 = rs.getString("set1");
				String set2 = rs.getString("set2");
				if(set1!=null && set1.length()>0) {
					List<Integer> dbs = db.get(0);
					for(String value : set1.split(","))
						dbs.add( Integer.valueOf(value) );
				}
				if(set2!=null && set2.length()>0) {
					List<Integer> dbs = db.get(1);
					for(String value : set2.split(","))
						dbs.add( Integer.valueOf(value) );
				}
				//
				code_list.put(key, rs.getInt("setCode"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(st);
			SQLUtil.close(rs);
			SQLUtil.close(con);
		}
	}
	
	
	public  void initDB(){
		Connection con = null;
		PreparedStatement st = null;
		synchronized (list) {
			for(int key : list.keySet()) {
				try {
					//
					con = L1DatabaseFactory.getInstance().getConnection();
					st = con.prepareStatement("DELETE FROM characters_inventory_set WHERE objectId=?");
					st.setInt(1, key);
					st.executeUpdate();
					st.close();
					//
					Map<Integer, List<Integer>> db = list.get(key);
					StringBuffer set1 = new StringBuffer();
					StringBuffer set2 = new StringBuffer();
					for(int value : db.get(0))
						set1.append(value).append(",");
					for(int value : db.get(1))
						set2.append(value).append(",");
					//
					int code = code_list.get(key);
					//
					st = con.prepareStatement("INSERT INTO characters_inventory_set SET objectId=?, setCode=?, set1=?, set2=?");
					st.setInt(1, key);
					st.setInt(2, code);
					st.setString(3, set1.toString());
					st.setString(4, set2.toString());
					st.executeUpdate();
				} catch (Exception e) {
					System.out.println(e);
				} finally {
					SQLUtil.close(st);
					SQLUtil.close(con);
				}
			}
		}						
	}	
	
	public void toWorldJoin(L1PcInstance pc) {	
		Map<Integer, List<Integer>> set = null;
		synchronized (list) {
			set = list.get(pc.getId());
		}
		//System.out.println(set);
		if(set == null) {
			set = new HashMap<Integer, List<Integer>>();
			set.put(0, new ArrayList<Integer>());
			set.put(1, new ArrayList<Integer>());
			synchronized (list) {
				list.put(pc.getId(), set);
			}
			synchronized (code_list) {
				code_list.put(pc.getId(), 0);
			}
		}
		pc.sendPackets(new S_InventorySwap(code_list.get(pc.getId()), set));
	}
	
	public void toChangeSet(L1PcInstance pc, int code) {
		if(code<0 && code>1)
			return;
		synchronized (code_list) {
			code_list.put(pc.getId(), code);
		}
		//
		Map<Integer, List<Integer>> set = null;
		synchronized (list) {
			set = list.get(pc.getId());
		}
		if(set == null)
			return;
		//
		List<Integer> set_list = set.get(code);
		for(L1ItemInstance item : pc.getInventory().getItems()) {
			if(set_list.contains(item.getId())){
				continue;
			}
		
			if(item.getItem().getType2() != 1 && item.getItem().getType2() != 2)
				continue;
			if(item.isEquipped()){
				if(item.getItem().getType2() == 1)
					UseWeapon(pc, item, code);
				else 	if(item.getItem().getType2() == 2)
					UseArmor(pc, item);
			}
		}
		for(int value : set_list) {
			L1ItemInstance item = pc.getInventory().findItemObjId(value);
			if(item == null)
				continue;
			if(item.getItem().getType2() != 1 && item.getItem().getType2() != 2)
				continue;
			if(item.isEquipped())
				continue;
			if(item.getItem().getType2() == 1)
				UseWeapon(pc, item, code);
			else 	if(item.getItem().getType2() == 2)
				UseArmor(pc, item);
				
		}
		pc.sendPackets(new S_OwnCharStatus(pc));
		pc.sendPackets(new S_SPMR(pc));
		pc.sendPackets(new S_InventorySwap(code));
	}
	
	/**
	 * ���� Ŭ���� ȣ���.
	 * @param pc
	 * @param code
	 */
	public void toSaveSet(L1PcInstance pc, int code) {
		//
		if(code<0 && code>1)
			return;
		//
		Map<Integer, List<Integer>> set = null;
		synchronized (list) {
			set = list.get(pc.getId());
		}
		if(set == null)
			return;
		//
		synchronized (set) {
			List<Integer> db = set.get(code);
			db.clear();
			for(L1ItemInstance item : pc.getInventory().getItems()) {
				if(item == null)
					continue;
				if(item.getItem().getType2() != 1 && item.getItem().getType2() != 2)
					continue;
				if(!item.isEquipped())
					continue;
				
				db.add(item.getId());
			}	
			if(db.size() >22){
				System.out.println("���밹�� ��Ż ���� " + pc.getName());
			}
		}
	}
	
	public void UseWeapon(L1PcInstance activeChar, L1ItemInstance weapon, int code) {
		L1PcInventory pcInventory = activeChar.getInventory();
		if (activeChar.getWeapon() == null
				|| !activeChar.getWeapon().equals(weapon)) { 
			int weapon_type = weapon.getItem().getType();
			int polyid = activeChar.getGfxId().getTempCharGfx();

			if (weapon.getItem().getItemId() != 7236) {
				if (!L1PolyMorph.isEquipableWeapon(polyid, weapon_type)) { 
					String n = ("+" + weapon.getEnchantLevel() + " " + weapon
							.getName());
					activeChar.sendPackets(new S_SystemMessage("���� ���� ���¿����� "
							+ n + "�� ���� �� �� �����ϴ�."), true);
					return;
				}
			}

			if (weapon.getItem().isTwohandedWeapon()
					&& pcInventory.getGarderEquipped(2, 7, 13) >= 1) { 
				activeChar.sendPackets(new S_ServerMessage(128), true); 
				return;
			}

		}

		activeChar.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
		boolean isdoubleweapon = false;
		if (activeChar.isWarrior()) {
			if (activeChar.isSlayer) {
				if (activeChar.getWeapon() != null && activeChar.getSecondWeapon() != null) {
					
					isdoubleweapon = true;
				}
				if (isdoubleweapon) {
					if (activeChar.getWeapon().equals(weapon)) {
						if (activeChar.getWeapon().getItem().getBless() == 2) {
							activeChar.sendPackets(new S_ServerMessage(150)); 
							return;
						}
						// ��� ��ȯ�� �ƴϰ� ������ ��
						pcInventory.setEquipped(activeChar.getWeapon(), false, false, false, false);
						return;
					}
					if (activeChar.getSecondWeapon().equals(weapon)) {
						// ��� ��ȯ�� �ƴϰ� ������ ��
						pcInventory.setEquipped(activeChar.getSecondWeapon(),
								false, false, false, true);
						return;
					}
					if (weapon.getItem().getType() == 6) {
						if (!���߳�(weapon, activeChar)){
							return;
						}
						
						pcInventory.setEquipped(activeChar.getSecondWeapon(),false, false, true, true);
						pcInventory.setEquipped(weapon, true, false, false,true);
						return;

					} else {
						if (!���߳�(weapon, activeChar)){
	
							return;
						}
						pcInventory.setEquipped(activeChar.getSecondWeapon(),false, false, false, true);
						pcInventory.setEquipped(activeChar.getWeapon(), false,false, false, false);
						pcInventory.setEquipped(weapon, true, false, false,false);
						return;
					}
				} else {
					if (activeChar.getWeapon() != null) {
						if (activeChar.getWeapon().equals(weapon)) {
							if (activeChar.getWeapon().getItem().getBless() == 2) { 
								activeChar.sendPackets(new S_ServerMessage(150));
								return;
							}
							// ��� ��ȯ�� �ƴϰ� ������ ��
							pcInventory.setEquipped(activeChar.getWeapon(),
									false, false, false, false);
							return;
						}
						if (activeChar.getWeapon().getItem().getType() == 6 && weapon.getItem().getType() == 6) {
							if (pcInventory.getGarderEquipped(2, 7, 13) >= 1) {
								activeChar.sendPackets(new S_ServerMessage(128), true); 
								return;
							}
							if (pcInventory.getGarderEquipped(2, 13, 13) >= 1) {
								activeChar.sendPackets(
										new S_ServerMessage(128), true); 
								return;
							}
							if (!���߳�(weapon, activeChar)) {
								return;
							}
							int polyid = activeChar.getGfxId().getTempCharGfx();
							if (!useweaponpoly(activeChar, weapon, polyid)) {
								return;
							}
							activeChar.sendPackets(new S_SkillSound(activeChar.getId(), 12534)); //�����̾� ����Ʈ
				        	Broadcaster.broadcastPacket(activeChar, new S_SkillSound(activeChar.getId(), 12534)); //�����̾� ����Ʈ
							pcInventory.setEquipped(weapon, true, false, false,true);
							return;
						}
					}

				}
			}
		}
		if (activeChar.getWeapon() != null) { // �̹� �����ΰ��� ��� �ϰ� �ִ� ���, ���� ��� ����
			if (activeChar.getWeapon().getItem().getBless() == 2) { // ���������� �־���
																	// ���
				activeChar.sendPackets(new S_ServerMessage(150), true); 
				return;
			}
			if (activeChar.getWeapon().equals(weapon)) {
				// ��� ��ȯ�� �ƴϰ� ������ ��
				pcInventory.setEquipped(activeChar.getWeapon(), false, false,
						false);
				if (weapon.getItemId() == 262)// ���弭Ŀ ���� ����
					activeChar.sendPackets(new S_Sound(2828), true);
				return;
			} else {
				if (!���߳�(weapon, activeChar))
					return;
				pcInventory.setEquipped(activeChar.getWeapon(), false, false,
						true);
			}
		}

		if (weapon.getItemId() == 200002) { // �������� ���̽��ٰ�
			activeChar.sendPackets(
					new S_ServerMessage(149, weapon.getLogName()), true); // \f1%0��
																			// �տ�
																			// �鷯�پ����ϴ�.
		}
		if (!���߳�(weapon, activeChar))
			return;
		pcInventory.setEquipped(weapon, true, false, false);
		if (weapon.getItemId() == 7236) {// ������ ���߸�Ʈ
			activeChar.sendPackets(
					new S_ServerMessage(149, weapon.getLogName()), true); // \f1%0��
																			// �տ�
																			// �鷯�پ����ϴ�.
		}
		if (weapon.getItemId() == 262)// ���弭Ŀ ���� ����
			activeChar.sendPackets(new S_Sound(2828), true);

	}
	
	public boolean ���߳�(L1ItemInstance weapon, L1PcInstance pc) {
		int min = weapon.getItem().getMinLevel();
		int max = weapon.getItem().getMaxLevel();
		if (min != 0 && min > pc.getLevel()) {
			pc.sendPackets(new S_ServerMessage(318, String.valueOf(min)), true);
			return false;
		} else if (max != 0 && max < pc.getLevel()) {
			if (max < 50) {
				pc.sendPackets(
						new S_PacketBox(S_PacketBox.MSG_LEVEL_OVER, max), true);
			} else {
				pc.sendPackets(new S_SystemMessage("�� ��������" + max
						+ "���� ���ϸ� ����� �� �ֽ��ϴ�. "), true);
			}
			return false;
		}
		return true;
	}
	
	public boolean useweaponpoly(L1PcInstance pc, L1ItemInstance weapon,
			int polyid) {
		if (!L1PolyMorph.isEquipableWeapon(polyid, 19)) { // �� ���ſ����� ��� �Ұ�
			String n = ("+" + weapon.getEnchantLevel() + " " + weapon.getName());
			pc.sendPackets(
					new S_SystemMessage("���� ���� ���¿����� �ּ� ������ ������ �� �����ϴ�."), true);
			return false;
		}
		return true;
	}
	
	private static final int[] cashRingList = { 20297, 20301, 20428, 20429,
		20430, 20431, 20432, 20433, 423011, 425100, 425101, 425102, 425103,
		425104, 425105, 425109, 425110, 425111, 425112, 425113, 525109,
		525110, 525111, 525112, 525113, 625109, 625110, 625111, 625112,
		625113, 21113, 21114, 21246, 21247, 21248, 21249, 21250, 21251,
		21252, 21253 };

private void UseArmor(L1PcInstance activeChar, L1ItemInstance armor) {
	int type = armor.getItem().getType();
	L1PcInventory pcInventory = activeChar.getInventory();

	/** 2011.05.19 ������ ��Ʋ�� */
	if ((activeChar.getMapId() == 5302 || activeChar.getMapId() == 5153)
			&& !armor.isEquipped()) {
		if ((armor.getItemId() >= 20452 && armor.getItemId() <= 20455)
				|| (armor.getItemId() >= 42401 && armor.getItemId() <= 42421)
				|| (armor.getItemId() >= 421000 && armor.getItemId() <= 421023)) {
			// activeChar.sendPackets(new
			// S_SystemMessage("\\fY��Ʋ�������� �͹��� ����Ͻ� �� �����ϴ�."), true);
			activeChar.sendPackets(
					new S_ServerMessage(74, armor.getLogName()), true); // \f1%0��
																		// �����
																		// ��
																		// �����ϴ�.
			return;
		} else if (armor.getItemId() == 20077 || armor.getItemId() == 20062
				|| armor.getItemId() == 120077
				|| armor.getItemId() == 20343 || armor.getItemId() == 20344) {
			activeChar.sendPackets(
					new S_ServerMessage(74, armor.getLogName()), true); // \f1%0��
																		// �����
																		// ��
																		// �����ϴ�.
			return;
		}
	}
	boolean equipeSpace; // ��� �ϴ� ���Ұ� ��� ������
	if (type == 9) { // ���� ���
		equipeSpace = pcInventory.getTypeEquipped(2, 9) <= (1 + activeChar
				.getRingSlotLevel());
		try {
			if (equipeSpace) {
				boolean cashring = false;
				;
				for (int i : cashRingList) {
					if (i == armor.getItemId()) {
						cashring = true;
						break;
					}
				}
				L1ItemInstance[] ringlist = pcInventory.getRingEquipped();
				if (ringlist != null && ringlist.length > 0) {
					int count = 0;

					for (L1ItemInstance i : ringlist) {
						if (i == null)
							continue;
						if (cashring) {
							for (int a : cashRingList) {
								if (a == i.getItemId()) {
									count++;
									break;
								}
							}
						} else {
							if (i.getItemId() == armor.getItemId())
								count++;
						}
						if (count >= 2) {
							equipeSpace = false;
							break;
						}
					}
					/*
					 * for(L1ItemInstance i : ringlist){ if(i == null)
					 * continue; boolean ck = false; for(int a :
					 * cashRingList){ if(a == i.getItemId()){ ck = true;
					 * break; } } if(ck == cashring) count++; if(count >=
					 * 2){ equipeSpace = false; break; } }
					 */
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	} else if (type == 12) { // �Ͱ���
		equipeSpace = pcInventory.getTypeEquipped(2, 12) < (1 + activeChar
				.getEarringSlotLevel());
		int armorid = 0;
		int earid = 0;
		try {
			if (equipeSpace) {
				L1ItemInstance[] earringlist = pcInventory
						.getEarringEquipped();
				if (earringlist != null && earringlist.length > 0) {
					for (L1ItemInstance i : earringlist) {
						if (i == null)
							continue;
						armorid = armor.getItemId();
						earid = i.getItemId();
						if (armorid >= 502007 && armorid <= 502010) {
							armorid -= 2000;
						}
						if (earid >= 502007 && earid <= 502010) {
							earid -= 2000;
						}
						if (earid == armorid)
							equipeSpace = false;

						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} else {
		equipeSpace = pcInventory.getTypeEquipped(2, type) <= 0;
	}

	if (equipeSpace && !armor.isEquipped()) { // ����� ���� �ⱸ�� ��� �ϰ� ���� �ʾ�, ��
												// ��� ���Ұ� ��� �ִ� ���(������ �õ��Ѵ�)
		int polyid = activeChar.getGfxId().getTempCharGfx();

		if (!L1PolyMorph.isEquipableArmor(polyid, type)) { // �� ���ſ����� ��� �Ұ�
			activeChar.sendPackets(new S_SystemMessage("���� ���� ���¿����� "
					+ armor.getLogName() + "�� ���� �� �� �����ϴ�."), true);
			return;
		}
		if (type == 7 && pcInventory.getTypeEquipped(2, 13) >= 1
				|| type == 13 && pcInventory.getTypeEquipped(2, 7) >= 1) {
			activeChar.sendPackets(new S_ServerMessage(124), true); // \f1
																	// ����
																	// �����ΰ���
																	// ��� �ϰ�
																	// �ֽ��ϴ�.
			return;
		}

		if (type == 7) {// ����
			if (activeChar.getWeapon() != null
					&& activeChar.getWeapon().getItem().isTwohandedWeapon()
					&& armor.getItem().getUseType() != 13) { // ��� ����
				activeChar.sendPackets(new S_ServerMessage(129), true); // \f1�����
																		// ���⸦
																		// ������
																		// ä��
																		// ����(shield)��
																		// ������
																		// ��
																		// �����ϴ�.
				return;
			}
			if (activeChar.getWeapon() != null
					&& activeChar.getSecondWeapon() != null) {
				activeChar.sendPackets(new S_ServerMessage(129), true); // \f1�����
																		// ���⸦
																		// ������
																		// ä��
																		// ����(shield)��
																		// ������
																		// ��
																		// �����ϴ�.
				return;
			}
		}

		if (type == 13) {// ����
			if (activeChar.getWeapon() != null
					&& activeChar.getSecondWeapon() != null) {
				activeChar.sendPackets(new S_ServerMessage(129), true); // \f1�����
																		// ���⸦
																		// ������
																		// ä��
																		// ����(shield)��
																		// ������
																		// ��
																		// �����ϴ�.
				return;
			}
		}

		/*
		 * if (type == 3 && pcInventory.getTypeEquipped(2, 4) >= 1) { // ������
		 * ���, ���並 ���� ������ Ȯ�� activeChar.sendPackets(new S_ServerMessage(126,
		 * "$224", "$225"), true); // \f1%1��%0�� ���� �� �����ϴ�. return; } else
		 * if ((type == 3) && pcInventory.getTypeEquipped(2, 2) >= 1) { //
		 * ������ ���, ������ ���� ������ Ȯ�� activeChar.sendPackets(new
		 * S_ServerMessage(126, "$224", "$226"), true); // \f1%1��%0�� ���� ��
		 * �����ϴ�. return; } else if ((type == 2) &&
		 * pcInventory.getTypeEquipped(2, 4) >= 1) { // ������ ���, ���並 ���� ������
		 * Ȯ�� activeChar.sendPackets(new S_ServerMessage(126, "$226",
		 * "$225"), true); // \f1%1��%0�� ���� �� �����ϴ�. return; }
		 */

		activeChar.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����

		pcInventory.setEquipped(armor, true);
	} else if (armor.isEquipped()) { // ����� ���� �ⱸ�� ��� �ϰ� �־��� ���(Ż���� �õ��Ѵ�)
		if (armor.getItem().getBless() == 2) { // ���������� �־��� ���
			activeChar.sendPackets(new S_ServerMessage(150), true); // \f1 ��
																	// ����
																	// �����ϴ�.
																	// ���ָ�
																	// ��ĥ ��
																	// �ְ� �ִ�
																	// ��
																	// �����ϴ�.
			return;
		}
		/*
		 * if (type == 3 && pcInventory.getTypeEquipped(2, 2) >= 1) { // ������
		 * ���, ������ ���� ������ Ȯ�� activeChar.sendPackets(new
		 * S_ServerMessage(127), true); // \f1�װ��� ���� ���� �����ϴ�. return; } else
		 * if ((type == 2 || type == 3) && pcInventory.getTypeEquipped(2, 4)
		 * >= 1) { // ������ ������ ���, ���並 ���� ������ Ȯ�� activeChar.sendPackets(new
		 * S_ServerMessage(127), true); // \f1�װ��� ���� ���� �����ϴ�. return; }
		 */
		/*
		 * if (type == 7) { if
		 * (activeChar.getSkillEffectTimerSet().hasSkillEffect
		 * (L1SkillId.SOLID_CARRIAGE)) {
		 * activeChar.getSkillEffectTimerSet().
		 * removeSkillEffect(L1SkillId.SOLID_CARRIAGE); } }
		 */

		if (type == 7) {// ����
			if (activeChar.getWeapon() != null
					&& activeChar.getWeapon().getItem().isTwohandedWeapon()
					&& armor.getItem().getUseType() != 13) { // ��� ����
				activeChar.sendPackets(new S_ServerMessage(129), true); // \f1�����
																		// ���⸦
																		// ������
																		// ä��
																		// ����(shield)��
																		// ������
																		// ��
																		// �����ϴ�.
				return;
			}
			if (activeChar.getWeapon() != null
					&& activeChar.getSecondWeapon() != null) {
				activeChar.sendPackets(new S_ServerMessage(129), true); // \f1�����
																		// ���⸦
																		// ������
																		// ä��
																		// ����(shield)��
																		// ������
																		// ��
																		// �����ϴ�.
				return;
			}
		}
		if (type == 13) {// ����
			if (activeChar.getWeapon() != null
					&& activeChar.getSecondWeapon() != null) {
				activeChar.sendPackets(new S_ServerMessage(129), true); // \f1�����
																		// ���⸦
																		// ������
																		// ä��
																		// ����(shield)��
																		// ������
																		// ��
																		// �����ϴ�.
				return;
			}
		}
		pcInventory.setEquipped(armor, false);
	} else {
		activeChar.sendPackets(new S_ServerMessage(124), true); // \f1 ����
																// �����ΰ��� ���
																// �ϰ� �ֽ��ϴ�.
	}
	activeChar.setCurrentHp(activeChar.getCurrentHp());
	activeChar.setCurrentMp(activeChar.getCurrentMp());
	activeChar.sendPackets(new S_OwnCharAttrDef(activeChar), true);
	activeChar.sendPackets(new S_OwnCharStatus(activeChar), true);
	activeChar.sendPackets(
			new S_PacketBox(S_PacketBox.char_ER, activeChar.get_PlusEr()),
			true);
	activeChar.sendPackets(new S_SPMR(activeChar), true);
	L1ItemDelay.onItemUse(activeChar, armor); // ������ ���� ����
}


}
