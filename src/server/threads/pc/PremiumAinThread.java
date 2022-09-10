package server.threads.pc;

import static l1j.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.BLESS_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.DRAGONBLOOD_A;
import static l1j.server.server.model.skill.L1SkillId.DRAGONBLOOD_P;
import static l1j.server.server.model.skill.L1SkillId.FIRE_SHIELD;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.PhoneCheck;
import l1j.server.server.datatables.RobotTable;
import l1j.server.server.datatables.RobotTable.RobotTeleport;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ACTION_UI;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_NewSkillIcons;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.CommonUtil;
import l1j.server.server.utils.SQLUtil;

public class PremiumAinThread extends Thread {
	private static final int[] loc = { -2, -1, 0, 1, 2 };
	private static PremiumAinThread _instance;
	private static Logger _log = Logger.getLogger(PremiumAinThread.class.getName());

	public static PremiumAinThread getInstance() {
		if (_instance == null) {
			_instance = new PremiumAinThread();
			_instance.start();
		}
		return _instance;
	}

	public PremiumAinThread() {
		super("server.threads.pc.PremiumAinThread");
	}

	public void run() {
		// System.out.println(PremiumAinThread.class.getName() + " ����");
		while (true) {
			try {

				for (L1Clan c : L1World.getInstance().getAllClans()) {
					ClanTable.getInstance().updateClan(c);
				}

				for (L1PcInstance _client : L1World.getInstance().getAllPlayers()) {
					if (_client instanceof L1RobotInstance) {
						continue;
					}
					if (_client == null || _client.getNetConnection() == null) {
						if (_client.noPlayerCK) {
							// �ڷ���Ʈ
							if (_client.getTeleportTime() != 0 && _client.getCurrentTeleportCount() >= _client.getTeleportTime()) {
								RobotTeleport robotTeleport = RobotTable.getRobotTeleportList()
										.get(CommonUtil.random(RobotTable.getRobotTeleportList().size()));
								L1Teleport.teleport(_client, robotTeleport.x + loc[CommonUtil.random(5)], robotTeleport.y + loc[CommonUtil.random(5)],
										(short) robotTeleport.mapid, robotTeleport.heading);
								_client.setCurrentTeleportCount(0);
							}
							// ��ų���
							if (_client.getSkillTime() != 0 && _client.getCurrentSkillCount() >= _client.getSkillTime()) {
								BuffStart buff = new BuffStart();
								buff.player = _client;
								GeneralThreadPool.getInstance().execute(buff);
								_client.setCurrentSkillCount(0);
							}
							// �̵�
							if (_client.getMoveTime() != 0 && _client.getCurrentMoveCount() >= _client.getMoveTime()) {

							}
							_client.setCurrentTeleportCount(_client.getCurrentTeleportCount() + 1);
							_client.setCurrentSkillCount(_client.getCurrentSkillCount() + 1);

						}
						continue;
					} else {
						try {

							if (_client.���͹��� && _client.getLevel() < 99) {
								_client.sendPackets(new S_PacketBox(S_PacketBox.���͹���, 1), true);
							} else if (_client.���͹���) {
								_client.sendPackets(new S_PacketBox(S_PacketBox.���͹���, 0), true);
								_client.���͹��� = false;
							}

							int deadtime = _client.getDeadTimeCount();
							int tc = _client.getTimeCount();
							int ttc = _client.gettamtimecount();
							int dtc = _client.getdtimecount();
							int ctc = _client.get����ī��Ʈ();
							int FT = Config.FEATHER_TIME;
							int tamt = Config.Tam_Time;

							if (Config.Tam_Ok) {
								if (ttc >= tamt) {
									_client.settamtimecount(0);// 6(��)���� + 1����
																// �����ش�.
									int tamcount = _client.tamcount();
									if (tamcount > 0) {
										int addtam = Config.Tam_Count * tamcount;
										_client.getNetConnection().getAccount().tam_point += addtam;
										try {
											_client.getNetConnection().getAccount().updateTam();
										} catch (Exception e) {
										}

										_client.sendPackets(new S_SystemMessage("������ �� " + tamcount + "�ܰ� ���� : Tam����Ʈ (" + addtam + ")�� ����!"));
										try {
											_client.sendPackets(new S_NewCreateItem(S_NewCreateItem.TAM_POINT, _client.getNetConnection()), true);
										} catch (Exception e) {
										}
									}
								} else {
									_client.settamtimecount(ttc + 1);// 6(��)���� +
																		// 1����
																		// �����ش�.
								}
							}

							if (Config.Chopa_Event && Config.���ĵ��������) {
								if (ctc >= 60) {
									_client.set����ī��Ʈ(0);// 6(��)���� + 1���� �����ش�.
									_client.getInventory().storeItem(50757, 1); // ���
									_client.sendPackets(new S_SystemMessage("���� �̺�Ʈ : 1�ð� ���� ���� [������ ��] ȹ��."), true);
								} else {
									_client.set����ī��Ʈ(ctc + 1);// 6(��)���� + 1����
																// �����ش�.
								}
							}

							if (Config.Dragon_14_12_12_Event && Config.���1212���������) {
								if (dtc >= 60) {
									_client.setdtimecount(0);
									_client.getInventory().storeItem(437010, 1); // ���
									_client.sendPackets(new S_SystemMessage("ưư�� ��� �̺�Ʈ : 1�ð� ���� ���� [�巡���� ���̾Ƹ��] ȹ��."), true);
								} else {
									_client.setdtimecount(dtc + 1);// 6(��)���� +1���� �����ش�.
								}
							}

							if (Config.Dragon_3DAY_Event && Config.��ٵ��������) {
								if (dtc >= 300) {
									_client.setdtimecount(0);// 6(��)���� + 1����
																// �����ش�.
									_client.getInventory().storeItem(437010, 1); // ���
									_client.sendPackets(new S_SystemMessage("��� �̺�Ʈ : 5�ð� ���� ���� [�巡���� ���̾Ƹ��] ȹ��."), true);
								} else {
									_client.setdtimecount(dtc + 1);// 6(��)���� +
																	// 1���� �����ش�.
								}
							}

							if (tc >= FT) {
								giveFeather(_client);// �ź��ѳ��� ���� ���޽ð� (6��)
							} else {
								_client.setTimeCount(tc + 1);// 6(��)���� + 1���� �����ش�.
							}
							/// ���� ���� �ּ�

							if (Config.�ҷ���_Event && Config.�ҷ���������) {
								if (_client.getInventory().checkEquipped(21123)) { // �ҷ���
																					// ����
																					// ����
																					// 2012
									�ҷ�������(_client);
								}
							}

							if (Config.Event_Box) {
								�����̺�Ʈ(_client);
							}

							if (_client.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2)) {
								DragonEME(_client);
							}
							if (_client.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_1)) {
								DragonEME2(_client);
							}
							// if(_client.getLevel() >= 49){
							int sc = _client.getSafeCount();
							if (CharPosUtil.getZoneType(_client) == 1 && !_client.isPrivateShop()) {
								if (sc >= 14) {// 141
									if (_client.getAinHasad() <= 1999999) {
										_client.calAinHasad(10000);
										_client.sendPackets(new S_ACTION_UI(S_ACTION_UI.AINHASAD, _client), true);
									}
									_client.setSafeCount(0);
								} else {
									_client.setSafeCount(sc + 1);
								}
							} else {
								if (sc > 0)
									_client.setSafeCount(0);
							}
							// }

							int keycount = _client.getInventory().countItems(L1ItemId.DRAGON_KEY);
							if (keycount > 0)
								DragonkeyTimeCheck(_client, keycount);

							int castle_id = L1CastleLocation.getCastleIdByArea(_client);
							if (castle_id != 0) {
								if (WarTimeController.getInstance().isNowWar(castle_id)) {
									_client.setCastleZoneTime(_client.getCastleZoneTime() + 1);
								}
							}

							/*
							 * if(_client.get_food() >= 225) { long time = 1800L -
							 * (System.currentTimeMillis() - _client.getSurvivalCry()) / 1000L; long minute
							 * = time / 60L; if (minute < 29L) { L1ItemInstance[] itemlist =
							 * _client.getInventory().findItemsId(60059); if(itemlist != null &&
							 * itemlist.length > 0){ for(L1ItemInstance tempitem : itemlist){
							 * if(tempitem.get_tempGfx() != 0) continue; tempitem.set_tempGfx(5355);
							 * _client.sendPackets(new S_DeleteInventoryItem(tempitem));
							 * _client.sendPackets(new S_AddItem(tempitem)); } } itemlist = null; } }
							 */

							if (_client.getDollList().size() > 0) {
								for (L1DollInstance doll : _client.getDollList()) {
									/*
									 * if(doll.getDollType() == L1DollInstance.DOLLTYPE_����_����){
									 * if(_client.��������_���ڿ���_Count >= 15){ _client.getInventory().storeItem(60472,
									 * 1); // �ҷ��� ȣ�ھ� ���� _client.sendPackets(new S_ServerMessage(403,
									 * "ũ�������� ��Ű �ָӴ�"), true); _client.��������_���ڿ���_Count = 0; }else
									 * _client.��������_���ڿ���_Count++; }
									 */
									if (doll.getDollType() == L1DollInstance.DOLLTYPE_HW_HUSUABI) {
										if (_client.��������_�ҷ��������_Count >= 60) {
											_client.getInventory().storeItem(140722, 1); // �ٷ��� ��������
											_client.sendPackets(new S_SystemMessage("�ҷ��� ����ƺ� �������� ��ȯ ���� : �ٷ��� ���� ���� ȹ��."));
											// _client.sendPackets(new
											// S_ServerMessage(403,
											// "�׷����� ���� ����"), true);
											_client.��������_�ҷ��������_Count = 0;
										} else
											_client.��������_�ҷ��������_Count++;
									}

									if (doll.getDollType() == L1DollInstance.DOLLTYPE_�׷���) {
										if (_client.��������_�׷���_Count >= 15) {
											_client.getInventory().storeItem(9057, 1); // �ҷ��� ȣ�ھ� ����
											_client.sendPackets(new S_SystemMessage("������īƮ���� �������� ��ȯ ���� : ������īƮ���� ���� ���� ȹ��."));
											// _client.sendPackets(new
											// S_ServerMessage(403,
											// "�׷����� ���� ����"), true);
											_client.��������_�׷���_Count = 0;
										} else
											_client.��������_�׷���_Count++;
									}
								}
							}

							if (_client.isDead()) {
								if (deadtime >= 5) {
									_client.logout();
									_client.getNetConnection().kick();
								} else {
									_client.setDeadTimeCount(deadtime + 1);// 6(��)����
																			// +
																			// 1����
																			// �����ش�.
								}
							} else {
								_client.setDeadTimeCount(0);
							}

						} catch (Exception e) {
							_log.warning("Primeum give failure.");
							_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
							throw e;
						}
					}

					/*
					 * if(_client.Auto_check){ ����üũ(_client); }
					 */

					/*
					 * if(PhoneCheck.getnocheck(_client.getAccountName())){
					 * if(_client.getMapId()!=6202){ L1Teleport.teleport(_client, 32928, 32864,
					 * (short) 6202, 5, true); } }
					 */

					if (Config.������) {
						if (PhoneCheck.getnocheck(_client.getAccountName())) {
							if (_client.getMapId() != 6202) {
								L1Teleport.teleport(_client, 32928, 32864, (short) 6202, 5, true);
							}
						}
					}

					if (_client.PC��_����������) {
						_client.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[PC�� ��ǰ ���� �ȳ�] PC�� �̿� �ð��� ����˴ϴ�. "));
						_client.sendPackets(new S_SystemMessage("[PC�� ��ǰ ���� �ȳ�] ����ŸƮ�� �������� �ʾƵ� ������ ������ �����ϴ�."));
						/*
						 * _client.sendPackets(new S_Restart(_client.getId(), 1), true);
						 */
					}

					long sysTime = System.currentTimeMillis();
					if (_client.PC��_����) {
						if (_client.getNetConnection().getAccount().getBuff_PC��() != null) {
							if (sysTime <= _client.getNetConnection().getAccount().getBuff_PC��().getTime()) {
								long �Ǿ�Ÿ�� = _client.getNetConnection().getAccount().getBuff_PC��().getTime() - sysTime;
								TimeZone seoul = TimeZone.getTimeZone("UTC");
								Calendar calendar = Calendar.getInstance(seoul);
								calendar.setTimeInMillis(�Ǿ�Ÿ��);
								int d = calendar.get(Calendar.DATE) - 1;
								int h = calendar.get(Calendar.HOUR_OF_DAY);
								int m = calendar.get(Calendar.MINUTE);
								int sc = calendar.get(Calendar.SECOND);
								if (d == 0) {
									if (h > 0) {
										if (h == 1 && m == 0) {
											_client.sendPackets(
													new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[PC�� �̿� �ð�] " + h + "�ð� " + m + "�� " + sc + "�� ���ҽ��ϴ�."));
										}
									} else {
										if (m == 30) {
											_client.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[PC�� �̿� �ð�] " + m + "�� " + sc + "�� ���ҽ��ϴ�."));
											_client.sendPackets(new S_SystemMessage("[PC�� ��ǰ ���� �ȳ�] �̿� �ð� ������ ���� ����ŸƮ�� ���� �˴ϴ�."));
										} else if (m == 20) {
											_client.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[PC�� �̿� �ð�] " + m + "�� " + sc + "�� ���ҽ��ϴ�."));
											_client.sendPackets(new S_SystemMessage("[PC�� ��ǰ ���� �ȳ�] �̿� �ð� ������ ���� ����ŸƮ�� ���� �˴ϴ�."));
										} else if (m <= 10) {
											_client.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[PC�� �̿� �ð�] " + m + "�� " + sc + "�� ���ҽ��ϴ�."));
											_client.sendPackets(new S_SystemMessage("[PC�� ��ǰ ���� �ȳ�] ������ ������ �����־ ������ ������ �����ϴ�. ����� �ڵ� ����ŸƮ�� ����˴ϴ�."));
										}

									}
								}
							} else {
								_client.PC��_���� = false;
								_client.PC��_���������� = true;
								_client.sendPackets(new S_NewSkillIcons(L1SkillId.PC��, false, -1));
								String s = "08 00 e7 6d";// �Ǿ���..
								_client.sendPackets(new S_NewCreateItem(126, s));
								/* _client.sendPackets(new S_Restart(_client.getId(), 1), true); */
								_client.getAccount().setAttendancePcHomeTime(0);
								_client.getAccount().saveAttendanceTime(_client.getAccount());
								_client.sendPackets(new S_ACTION_UI(S_ACTION_UI.ATTENDANCE_COMPLETE, _client.getAccount(), _client.PC��_����));
							}
						}
					}

				}

				����üũ();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(60000);
					// Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean ������(int npcid, int itemid) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT COUNT(*) FROM npc_shop_sell WHERE npc_id = ? AND item_id = ?");
			pstm.setInt(1, npcid);
			pstm.setInt(2, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	private boolean ��������(int npcid, int itemid) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM shop_npc WHERE npc_id = ? AND item_id = ?");
			pstm.setInt(1, npcid);
			pstm.setInt(2, itemid);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	public void ����üũ(L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM _Auto_Check WHERE Name=?");
			pstm.setString(1, pc.getName());
			rs = pstm.executeQuery();
			if (!rs.next()) {
				pc.Auto_check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void ����üũ() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean ok = false;
		int npcid, itemid;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM shop_npc");
			rs = pstm.executeQuery();
			while (rs.next()) {
				npcid = rs.getInt("npc_id");
				itemid = rs.getInt("item_id");
				if (npcid >= 8000001 && npcid <= 8010002)
					continue;
				ok = ������(npcid, itemid);
				if (!ok) {
					��������(npcid, itemid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}

	private void �����̺�Ʈ(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance)
			return;
		if (pc.���ڻ���Time >= 60) {
			pc.getInventory().storeItem(60517, 1); // ���ɻ��� ����
			pc.sendPackets(new S_ServerMessage(403, "1�ð�Ÿ�� ���� ����"), true);
			pc.���ڻ���Time = 0;
		} else
			pc.���ڻ���Time++;
	}

	private void �����ָ��̺�Ʈ(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance)
			return;
		if (pc.�����ָ��̺�ƮTime >= 60) {
			pc.getInventory().storeItem(9096, 1); // ������ָ�
			pc.sendPackets(new S_SystemMessage("����� �ָ� �̺�Ʈ ���� ���� : ������ ����� �ָ� �ָӴ� ȹ��."));
			// pc.sendPackets(new S_ServerMessage(403, "�׷����� ���� ����"), true);
			pc.�����ָ��̺�ƮTime = 0;
		} else
			pc.�����ָ��̺�ƮTime++;
	}

	private void �׷����̺�Ʈ(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance)
			return;
		if (pc.�׷����̺�ƮTime >= 60) {
			pc.getInventory().storeItem(9057, 1); // �׷����� ��������
			pc.sendPackets(new S_SystemMessage("�׷��� �̺�Ʈ ���� ���� : �׷����� ���� ���� ȹ��."));
			// pc.sendPackets(new S_ServerMessage(403, "�׷����� ���� ����"), true);
			pc.�׷����̺�ƮTime = 0;
		} else
			pc.�׷����̺�ƮTime++;
	}

	private void �����̺�Ʈ(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance)
			return;
		if (pc.�����̺�ƮTime >= 60) {
			pc.getInventory().storeItem(60517, 1); // ���ɻ��� ����
			pc.sendPackets(new S_ServerMessage(403, "�輼ȣ�� ���ö� ��ź"), true);
			pc.�����̺�ƮTime = 0;
		} else
			pc.�����̺�ƮTime++;
	}

	private void �ҷ�������(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		if (pc.�ҷ���ȣ�ھ�Time >= 15) {
			pc.getInventory().storeItem(160423, 1); // �ҷ��� ȣ�ھ� ����
			pc.sendPackets(new S_ServerMessage(403, "ȣ�ڼ�"), true);
			// pc.getInventory().storeItem(60198, 1); // �ҷ��� ȣ�ھ� ����
			// pc.sendPackets(new S_ServerMessage(403, "�ҷ��� ȣ�ھ�"), true);
			pc.�ҷ���ȣ�ھ�Time = 0;
		} else
			pc.�ҷ���ȣ�ھ�Time++;
	}

	private void DragonEME(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2)) {
			if (pc.getDETime() != null) {
				if (System.currentTimeMillis() > pc.getDETime().getTime()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, 0x02, 0), true);
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_EME_2);
				} else {
					long DETIME = pc.getDETime().getTime() - System.currentTimeMillis();
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_EME_2);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGON_EME_2, (int) DETIME);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, 0x02, (int) DETIME / 1000), true);
					try {
						pc.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void DragonEME2(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_1)) {
			if (pc.getDETime2() != null) {
				if (System.currentTimeMillis() > pc.getDETime2().getTime()) {
					S_PacketBox pb1 = new S_PacketBox(S_PacketBox.DRAGON_EME, 0x01, 0);
					pc.sendPackets(pb1, true);
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_EME_1);
				} else {
					long DETIME = pc.getDETime2().getTime() - System.currentTimeMillis();
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_EME_1);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGON_EME_1, (int) DETIME);
					S_PacketBox pb2 = new S_PacketBox(S_PacketBox.DRAGON_EME, 0x01, (int) DETIME / 1000);
					pc.sendPackets(pb2, true);
					try {
						pc.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void antablood(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(DRAGONBLOOD_A)) {
			if (pc.getAnTime() != null) {
				if (System.currentTimeMillis() > pc.getAnTime().getTime()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 82, 0), true);
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_A);
				} else {
					long BloodTime = pc.getAnTime().getTime() - System.currentTimeMillis();
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_A);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGONBLOOD_A, (int) BloodTime);
					pc.getResistance().addWater(50);
					pc.getAC().addAc(-2);
					pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 82, (int) BloodTime / 60000), true);
					try {
						pc.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_A);
				pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 82, 0), true);
			}
		}
	}

	private void papooblood(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(DRAGONBLOOD_P)) {
			if (pc.getpaTime() != null) {
				if (System.currentTimeMillis() > pc.getpaTime().getTime()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 85, 0), true);
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_P);
				} else {
					long BloodTime = pc.getpaTime().getTime() - System.currentTimeMillis();
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_P);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGONBLOOD_P, (int) BloodTime);
					pc.getResistance().addWind(50);
					pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 85, (int) BloodTime / 60000), true);
					try {
						pc.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_P);
				pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 85, 0), true);
			}
		}
	}

	private void lindblood(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONBLOOD_L)) {
			if (pc.getlindTime() != null) {
				if (System.currentTimeMillis() > pc.getlindTime().getTime()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 88, 0), true);
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_L);
				} else {
					long BloodTime = pc.getlindTime().getTime() - System.currentTimeMillis();
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_L);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGONBLOOD_L, (int) BloodTime / 1000);
					pc.getAbility().addSp(1);
					pc.sendPackets(new S_SPMR(pc), true);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 88, (int) BloodTime / 60000), true);
					try {
						pc.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_L);
				pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 88, 0), true);
			}
		}
	}

	private Date day = new Date(System.currentTimeMillis());

	private void giveFeather(L1PcInstance pc) {

		UserRankingController.isRenewal = true;
		// UserRankingController.reload();

		if (pc.isPrivateShop()) {
			return;
		}
		if (pc instanceof L1RobotInstance) {
			return;
		}

		if (pc.getInventory().calcWeightpercent() >= 90) {
			pc.sendPackets(new S_ServerMessage(1414)); // ���� �������� ����á���ϴ�.
			return;
		}

		pc.setTimeCount(0);

		int FN = Config.FEATHER_NUMBER;
		int CLN = Config.CLAN_NUMBER;
		int CAN = Config.CASTLE_NUMBER;
		boolean eve = false;
		day.setTime(System.currentTimeMillis());
		// 18��~24�� ���� �ι� ����
		/*
		 * if(day.getHours() >= 18 && day.getHours() <= 23){ eve = true; //FN *= 2;
		 * //CLN *= 2; //CAN *= 2; }
		 */
		if (pc.isPrivateShop()) {
			// pc.getInventory().storeItem(41159, 1); // �ź��� �������� ����
			// pc.sendPackets(new S_ServerMessage(403, "$5116 (1)"));
		} else {
			int total = eve ? FN * 2 : FN;
			pc.sendPackets(new S_ServerMessage(403, "�Ƚ��� ���� (" + (eve ? FN * 2 : FN) + ")"), true);
			// S_SystemMessage sm = new
			// S_SystemMessage("�Ƚ��� ���� ("+FN+")�� ������ϴ�.");
			// pc.sendPackets(sm); sm.clear(); sm = null;
			L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
			if (clan != null) {
				if (clan.getCastleId() == 0 && pc.getClanid() != 0) { // ������ �ƴϰ�
																		// ����
																		// ������
					total += eve ? CLN * 2 : CLN;
					pc.sendPackets(new S_SystemMessage("\\fY���Ϳ� �߰� ����: ���� (" + (eve ? CLN * 2 : CLN) + ") ȹ��"), true);
				}
				if (clan.getCastleId() != 0) { // �����Ͻ�
					total += eve ? CAN * 2 : CAN;
					pc.sendPackets(new S_SystemMessage("\\fY������ �߰� ����: ���� (" + (eve ? CAN * 2 : CAN) + ") ȹ��"), true);
				}
			}
			pc.getInventory().storeItem(41159, total); // �ź��� �������� ����
//			pc.getInventory().storeItem(600267, 8);
//			pc.getInventory().storeItem(600272, 8);
		}
	}

	private void DragonkeyTimeCheck(L1PcInstance pc, int count) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		long nowtime = System.currentTimeMillis();
		if (count == 1) {
			L1ItemInstance item = pc.getInventory().findItemId(L1ItemId.DRAGON_KEY);
			if (nowtime > item.getEndTime().getTime())
				pc.getInventory().removeItem(item);
		} else {
			L1ItemInstance[] itemList = pc.getInventory().findItemsId(L1ItemId.DRAGON_KEY);
			for (int i = 0; i < itemList.length; i++) {
				if (nowtime > itemList[i].getEndTime().getTime())
					pc.getInventory().removeItem(itemList[i]);
			}
			itemList = null;
		}
	}

	private class BuffStart implements Runnable {
		L1PcInstance player;
		L1SkillUse skilluse = new L1SkillUse();

		private void buff(L1PcInstance pc) {
			if (pc.isDead())
				return;

			long curtime = System.currentTimeMillis() / 1000;

			int[] allBuffSkill = { PHYSICAL_ENCHANT_DEX, PHYSICAL_ENCHANT_STR, BLESS_WEAPON, ADVANCE_SPIRIT, FIRE_SHIELD };
			if (pc.getLevel() <= 65) {
				try {
					for (int i = 0; i < allBuffSkill.length; i++) {
						skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
						pc.setQuizTime(curtime);
					}
				} catch (Exception e) {
				}
			}
		}

		public void run() {
			try {
				Thread.sleep(5000);
				if (player != null) {
					if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.PHYSICAL_ENCHANT_STR)
							|| !player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.PHYSICAL_ENCHANT_DEX)) {
						buff(player);
						Thread.sleep(3000);
					}
					S_DoActionGFX da = new S_DoActionGFX(player.getId(), ActionCodes.ACTION_SkillBuff);
					if (player.isCrown()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.GLOWING_AURA)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.GLOWING_AURA, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHINING_AURA) && player.getLevel() >= 55) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.SHINING_AURA, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					} else if (player.isKnight()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.REDUCTION_ARMOR)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.REDUCTION_ARMOR, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							player.getSkillEffectTimerSet().setSkillEffect(L1SkillId.REDUCTION_ARMOR, 100000);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BOUNCE_ATTACK)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.BOUNCE_ATTACK, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							player.getSkillEffectTimerSet().setSkillEffect(L1SkillId.BOUNCE_ATTACK, 60000);
							Thread.sleep(7000);
						}
					} else if (player.isDarkelf()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.MOVING_ACCELERATION)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.MOVING_ACCELERATION, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.UNCANNY_DODGE)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.UNCANNY_DODGE, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BURNING_SPIRIT)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.BURNING_SPIRIT, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DOUBLE_BRAKE)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.DOUBLE_BRAKE, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHADOW_FANG)) {
							player.getSkillEffectTimerSet().setSkillEffect(L1SkillId.SHADOW_FANG, 300000);
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.SHADOW_FANG, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					} else if (player.isElf()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLOODY_SOUL)) {
							player.getSkillEffectTimerSet().setSkillEffect(L1SkillId.BLOODY_SOUL, 15000);
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.BLOODY_SOUL, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
							player.getSkillEffectTimerSet().setSkillEffect(L1SkillId.BLOODY_SOUL, 15000);
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.BLOODY_SOUL, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
							player.getSkillEffectTimerSet().setSkillEffect(L1SkillId.BLOODY_SOUL, 15000);
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.BLOODY_SOUL, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					} else if (player.isDragonknight()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLOOD_LUST)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.BLOOD_LUST, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.MORTAL_BODY)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.MORTAL_BODY, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					} else if (player.isIllusionist()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CONCENTRATION)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.CONCENTRATION, player.getId(), player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.PATIENCE)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.PATIENCE, player.getId(), player.getX(), player.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.INSIGHT)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.INSIGHT, player.getId(), player.getX(), player.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					}
					RobotTeleport robotTeleport = RobotTable.getRobotTeleportList().get(CommonUtil.random(RobotTable.getRobotTeleportList().size()));
					L1Teleport.teleport(player, robotTeleport.x + loc[CommonUtil.random(5)], robotTeleport.y + loc[CommonUtil.random(5)],
							(short) robotTeleport.mapid, robotTeleport.heading);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
