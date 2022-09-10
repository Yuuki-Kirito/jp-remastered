package server.threads.pc;

import java.sql.Timestamp;

import l1j.server.Config;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class SabuDGTime implements Runnable {

	private static SabuDGTime _instance;

	public static SabuDGTime getInstance() {
		if (_instance == null) {
			_instance = new SabuDGTime();
		}
		return _instance;
	}

	public SabuDGTime() {
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	public void run() {
		try {

			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc == null || pc.getNetConnection() == null) {
					continue;
				}
				if (pc instanceof L1RobotInstance) {
					continue;
				}

				/*
				 * if (pc.getMapId() >= 451 && pc.getMapId() <= 536 && pc.getMapId() != 480 &&
				 * pc.getMapId() != 481 && pc.getMapId() != 482 && pc.getMapId() != 483 &&
				 * pc.getMapId() != 484 && pc.getMapId() != 521 && pc.getMapId() != 522 &&
				 * pc.getMapId() != 523 && pc.getMapId() != 524) {// ��Ÿ�ٵ� try {
				 * ravaTimeCheck(pc); } catch (Exception e) { } }
				 */

				if (pc.getMapId() >= 653 && pc.getMapId() <= 656) {
					try {
						�����Ѱ���TimeCheck(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() >= 1911 && pc.getMapId() <= 1913) {
					try {
						������õ����TimeCheck(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() >= 282 && pc.getMapId() <= 285) {// ���ž �߷� ����
					try {
						ivorytime(pc);
					} catch (Exception e) {
					}
				}
//				if ((pc.getMapId() >= 285 && pc.getMapId() <= 289))
//					try {
//						ivoryYaheetime(pc);
//					} catch (Exception localException4)
//				{	
//				}
				if (pc.getMapId() == 54 || pc.getMapId() == 15403 || pc.getMapId() == 15404) {// �������&������ ���
					try {

						GungeonTimeCheck(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() == 785 || pc.getMapId() == 788 || pc.getMapId() == 789) { // �����̺�Ʈ
					try {
						�����̺�ƮTimeCheck(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() == 1931) {
					try {
						����(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 1700 || pc.getMapId() == 1703) {
					try {
						�ؼ�(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() == 10101) {
					try {
						����(pc);
					} catch (Exception e) {
					}
				}
				/*
				 * if (pc.getMapId() == 5490) { try { ����(pc); } catch (Exception e) { } }
				 */
//				if (pc.getMapId() == 452 || pc.getMapId() == 453 || pc.getMapId() == 461 || pc.getMapId() == 462
//						|| pc.getMapId() == 471 || pc.getMapId() == 475 || pc.getMapId() == 495 || pc.getMapId() == 492
//						|| pc.getMapId() == 479) {
//					try {
//						���(pc);
//					} catch (Exception e) {
//					}
//				}
				if (pc.getMapId() == 491 || pc.getMapId() == 492 || pc.getMapId() == 493) {
					try {
						�����ٿ�ũ(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 777) {
					try {
						����(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 59 || pc.getMapId() == 60 || pc.getMapId() == 61 || pc.getMapId() == 63) {
					try {
						����(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 10 || pc.getMapId() == 11 || pc.getMapId() == 12) {
					try {
						��������(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 430 || pc.getMapId() == 624) {
					try {
						��(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 5501) {
					try {
						�ҷ���(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 820) {
					try {
						�ַ�Ÿ��(pc);
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	private void �ҷ���(L1PcInstance player) {
		player.addchecktime();
		player.set�ҷ���time(player.get�ҷ���time() + 1);
		int usetime = player.get�ҷ���time();
		int outtime = 60 * 60 * 1;
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			String s1 = isPC���尡�ɿ���(player.get�ҷ���day(), outtime, usetime);
			if (s1.equals("���尡��")) {// ���尡��
			} else if (s1.equals("�Ұ���")) {// ����Ұ���
				player.sendPackets(new S_ServerMessage(1522, "3"));// 5�ð� ���
				// ����ߴ�.
				L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
				return;
			} else if (s1.equals("�ʱ�ȭ")) {// �ʱ�ȭ
				player.set�ҷ���time(1);
				player.set�ҷ���day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
				player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);

				player.sendPackets(new S_ServerMessage(1526, "1"));// �ð� ���Ҵ�.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void ��(L1PcInstance player) {
		player.addchecktime();
		player.set��time(player.get��time() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.get��time();
			String s = isPC���尡�ɿ���(player.get��day(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.set��time(1);
					player.set��day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(153, outtime - 1), true);

					player.sendPackets(new S_ServerMessage(1526, "2"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ����(L1PcInstance player) {
		player.addchecktime();
		player.set����time(player.get����time() + 1);
		player.setpc����time(player.getpc����time() + 1);
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		if (player.getpc����day() == null || player.get����day() == null) {
			player.sendPackets(sm);
			L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
		} else {

			int ���� = player.getInventory().countItems(60499);
			int ���� = player.getInventory().countItems(60500);
			int ���� = player.getInventory().countItems(60501);

			// int maxcount = Math.max(����, ����)*5;
			// int maxcount = Math.max(����, ����)*5;
			int maxcount = (���� + ����) * 5;
			if (maxcount > 50)
				maxcount = 50;

			if (maxcount <= ����) {
				if (player.�����ڴ��ð� <= 0) {
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else {
					player.�����ڴ��ð�--;
				}
			}

			try {
				int outtime = 60 * 60;
				int usetime = player.get����time();
				String s = isAccount���尡�ɿ���(player.get����day(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_SystemMessage("���� �ð� : ���� ���� �ð� 3�ð� 30�� ��� ���"), true);
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.set����time(1);
					player.set����day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_SystemMessage("���� �ð� : ���� ���� �ð� 3�ð� 30�� ����"), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int outtime = 60 * 60;
				int usetime = player.getpc����time();
				String s = isPC���尡�ɿ���(player.getpc����day(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_ServerMessage(1522, "30"));// �г��Ҵ�
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.setpc����time(1);
					player.setpc����day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1527, "30"));// �г��Ҵ�
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private void �ַ�Ÿ��(L1PcInstance player) {
		player.addchecktime();
		player.set�ַ�Ÿ��time(player.get�ַ�Ÿ��time() + 1);
		if (player.get�ַ�Ÿ��day() == null) {
			L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
			return;
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				int outtime = 60 * 40;
				int usetime = player.get�ַ�Ÿ��time();
				String s = isPC���尡�ɿ���(player.get�ַ�Ÿ��day(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_ServerMessage(1527, "40"));// �г��Ҵ�
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.set�ַ�Ÿ��time(1);
					player.set�ַ�Ÿ��day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1527, "40"));// �г��Ҵ�
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private S_SystemMessage sm = new S_SystemMessage("������ �÷��̾�� ���� ����� �˴ϴ�.");

	/*
	 * private void ravaTimeCheck(L1PcInstance player) { player.addchecktime();
	 * player.setravatime(player.getravatime() + 1); if (player.getgiranday() ==
	 * null) { player.sendPackets(sm); int[] loc = Getback.GetBack_Location(player,
	 * true); L1Teleport .teleport(player, loc[0], loc[1], (short) loc[2], 5, true);
	 * } else { Timestamp nowday = new Timestamp(System.currentTimeMillis()); try {
	 * int outtime = 60 * 60 * 2; int usetime = player.getravatime(); String s =
	 * isPC���尡�ɿ���(player.getgiranday(), outtime, usetime); if (s.equals("���尡��")) {//
	 * ���尡�� } else if (s.equals("�Ұ���")) {// ����Ұ��� player.sendPackets(new
	 * S_ServerMessage(1522, "2"));// 2�ð� ��λ���ߴ�. int[] loc =
	 * Getback.GetBack_Location(player, true); L1Teleport.teleport(player, loc[0],
	 * loc[1], (short) loc[2], 5, true); return; } else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
	 * player.setravatime(1); player.setravaday(nowday);
	 * player.getNetConnection().getAccount().updateDGTime(); player.sendPackets(new
	 * S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
	 * player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1),
	 * true); // player.sendPackets(new S_SystemMessage(player, //
	 * "���/�۷��� ���� ���� �ð��� �ʱ�ȭ �Ǿ����ϴ�."), true); // player.sendPackets(new
	 * S_SystemMessage(player, // "���� ü�� �ð��� 3�ð� ���ҽ��ϴ�."), true);
	 * player.sendPackets(new S_ServerMessage(1526, "2"));// �ð� // ���Ҵ�. } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 * 
	 * }
	 */

	private void GungeonTimeCheck(L1PcInstance player) {
		player.addchecktime();
		player.setgirantime(player.getgirantime() + 1);
		if (player.getgiranday() == null) {
			player.sendPackets(sm);
			int[] loc = Getback.GetBack_Location(player, true);
			L1Teleport.teleport(player, loc[0], loc[1], (short) loc[2], 5, true);
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				if (player.getLevel() <= 89) {
					int outtime = 60 * 60 * 2;
					int usetime = player.getgirantime();
					String s = isPC���尡�ɿ���(player.getgiranday(), outtime, usetime);
					if (s.equals("���尡��")) {// ���尡��
					} else if (s.equals("�Ұ���")) {// ����Ұ���
						player.sendPackets(new S_ServerMessage(1522, "2"));// 2�ð� ��λ���ߴ�.
						int[] loc = Getback.GetBack_Location(player, true);
						L1Teleport.teleport(player, loc[0], loc[1], (short) loc[2], 5, true);
						return;
					} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
						player.setgirantime(1);
						player.setgiranday(nowday);
						player.getNetConnection().getAccount().updateDGTime();
						player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
						player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
						// player.sendPackets(new S_SystemMessage(player,
						// "���/�۷��� ���� ���� �ð��� �ʱ�ȭ �Ǿ����ϴ�."), true);
						// player.sendPackets(new S_SystemMessage(player,
						// "���� ü�� �ð��� 3�ð� ���ҽ��ϴ�."), true);
						player.sendPackets(new S_ServerMessage(1526, "2"));// �ð�
						// ���Ҵ�.
					}
				} else if (player.getLevel() >= 90) {
					int outtime = 60 * 60 * 3;
					int usetime = player.getgirantime();
					String s = isPC���尡�ɿ���(player.getgiranday(), outtime, usetime);
					if (s.equals("���尡��")) {// ���尡��
					} else if (s.equals("�Ұ���")) {// ����Ұ���
						player.sendPackets(new S_ServerMessage(1522, "3"));// 2�ð� ��λ���ߴ�.
						int[] loc = Getback.GetBack_Location(player, true);
						L1Teleport.teleport(player, loc[0], loc[1], (short) loc[2], 5, true);
						return;
					} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
						player.setgirantime(3);
						player.setgiranday(nowday);
						player.getNetConnection().getAccount().updateDGTime();
						player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
						player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
						// player.sendPackets(new S_SystemMessage(player,
						// "���/�۷��� ���� ���� �ð��� �ʱ�ȭ �Ǿ����ϴ�."), true);
						// player.sendPackets(new S_SystemMessage(player,
						// "���� ü�� �ð��� 3�ð� ���ҽ��ϴ�."), true);
						player.sendPackets(new S_ServerMessage(1526, "3"));// �ð�
						// ���Ҵ�.
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("deprecation")
	private String isAccount���尡�ɿ���(Timestamp accountday, int outtime, int usetime) {
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		String end = "�Ұ���";
		String ok = "���尡��";
		String start = "�ʱ�ȭ";
		if (accountday != null) {
			long clac = nowday.getTime() - accountday.getTime();

			int hours = nowday.getHours();
			int lasthours = accountday.getHours();

			if (accountday.getDate() != nowday.getDate()) {
				if (clac > 86400000 || hours >= Config.D_Reset_Time || lasthours < Config.D_Reset_Time) {// 24�ð��� �����ų�
					// ����9�����Ķ��
					return start;
				}
			} else {
				if (lasthours < Config.D_Reset_Time && hours >= Config.D_Reset_Time) {// ������ 9�������� ����üũ
					return start;
				}
			}
			if (outtime <= usetime) {
				return end;// ��λ��
			} else {
				return ok;
			}
		} else {
			return start;
		}
	}

	@SuppressWarnings("deprecation")
	private String isPC���尡�ɿ���(Timestamp accountday, int outtime, int usetime) {
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		String end = "�Ұ���";
		String ok = "���尡��";
		String start = "�ʱ�ȭ";
		if (accountday != null) {
			long clac = nowday.getTime() - accountday.getTime();

			int hours = nowday.getHours();
			int lasthours = accountday.getHours();

			if (accountday.getDate() != nowday.getDate()) {
				// System.out.println(nowday.getHours());
				if (clac > 86400000 || hours >= Config.D_Reset_Time || lasthours < Config.D_Reset_Time) {// 24�ð��� �����ų�
					// ����9�����Ķ��
					return start;
				}
			} else {
				if (lasthours < Config.D_Reset_Time && hours >= Config.D_Reset_Time) {// ������ 9�������� ����üũ
					return start;
				}
			}
			if (outtime <= usetime) {
				return end;// ��λ��
			} else {
				return ok;
			}
		} else {
			return start;
		}
	}

	private void ������õ����TimeCheck(L1PcInstance player) {
		player.addchecktime();
		player.set������õ����time(player.get������õ����time() + 1);

		if (player.get������õ����day() == null) {
			player.sendPackets(sm);
			L1Teleport.teleport(player, 32779, 32832, (short) 622, 5, true);
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				int outtime = 60 * 60;
				int usetime = player.get������õ����time();
				String s = isPC���尡�ɿ���(player.get������õ����day(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					// player.sendPackets(new S_SystemMessage(player,
					// "���� ü�� ���� �ð� 1�ð��� ��� ����ϼ̽��ϴ�."), true);
					player.sendPackets(new S_ServerMessage(1522, "1"));// 5�ð� ���
					// ����ߴ�.
					L1Teleport.teleport(player, 32779, 32832, (short) 622, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.set������õ����time(1);
					player.set������õ����day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					// player.sendPackets(new S_SystemMessage(player,
					// "������ õ���� ��� ���� �ð��� �ʱ�ȭ �Ǿ����ϴ�."), true);
					// player.sendPackets(new S_SystemMessage(player,
					// "���� ü�� �ð��� 1�ð� ���ҽ��ϴ�."), true);
					player.sendPackets(new S_ServerMessage(1526, "1"));// �ð�
					// ���Ҵ�.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void �����̺�ƮTimeCheck(L1PcInstance player) {
		player.addchecktime();
		player.set�����̺�Ʈtime(player.get�����̺�Ʈtime() + 1);

		if (player.get�����̺�Ʈday() == null) {
			player.sendPackets(sm);
			L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				int outtime = 60 * 60;
				int usetime = player.get�����̺�Ʈtime();
				String s = isPC���尡�ɿ���(player.get�����̺�Ʈday(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_ServerMessage(1522, "1"));// 5�ð� ���
					// ����ߴ�.
					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.set�����̺�Ʈtime(1);
					player.set�����̺�Ʈday(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1526, "1"));// �ð�
					// ���Ҵ�.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void �����Ѱ���TimeCheck(L1PcInstance player) {
		player.addchecktime();
		player.set�����Ѱ���time(player.get�����Ѱ���time() + 1);

		if (player.get�����Ѱ���day() == null) {
			player.sendPackets(sm);
			L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				int outtime = 60 * 60 * 2;
				int usetime = player.get�����Ѱ���time();
				String s = isPC���尡�ɿ���(player.get�����Ѱ���day(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_ServerMessage(1522, "2"));// 5�ð� ���
					// ����ߴ�.
					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.set�����Ѱ���time(1);
					player.set�����Ѱ���day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1526, "2"));// �ð�
					// ���Ҵ�.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void ivorytime(L1PcInstance player) {
		player.addchecktime();
		player.setivorytime(player.getivorytime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getivorytime();
			String s = isPC���尡�ɿ���(player.getivoryday(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.setivorytime(1);
					player.setivoryday(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(153, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1526, "2"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void �ؼ�(L1PcInstance player) {
		player.addchecktime();
		player.set�ؼ�time(player.get�ؼ�time() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.get�ؼ�time();
			String s = isPC���尡�ɿ���(player.get�ؼ�day(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.set�ؼ�time(1);
					player.set�ؼ�day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(153, outtime - 1), true);

					player.sendPackets(new S_ServerMessage(1526, "2"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ����(L1PcInstance player) {
		player.addchecktime();
		player.set����time(player.get����time() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 1;
			int usetime = player.get����time();
			String s = isPC���尡�ɿ���(player.get����day(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "1"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.set����time(1);
					player.set����day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(153, outtime - 1), true);

					player.sendPackets(new S_ServerMessage(1526, "1"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * private void ����(L1PcInstance player) { player.addchecktime();
	 * player.set����time(player.get����time() + 1);
	 * 
	 * Timestamp nowday = new Timestamp(System.currentTimeMillis()); try { int
	 * outtime = 60 * 60 * 8; int usetime = player.get����time(); String s =
	 * isPC���尡�ɿ���(player.get����day(), outtime, usetime);
	 * 
	 * if (!s.equals("���尡��")) { if (s.equals("�Ұ���")) { player.sendPackets(new
	 * S_ServerMessage(1522, "8"));
	 * 
	 * L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
	 * 
	 * return; }if (s.equals("�ʱ�ȭ")) { player.set����time(1); player.set����day(nowday);
	 * player.getNetConnection().getAccount().updateDGTime(); player.sendPackets(new
	 * S_PacketBox(153, outtime - 1), true);
	 * 
	 * player.sendPackets(new S_ServerMessage(1526, "8")); } } } catch (Exception e)
	 * { e.printStackTrace(); } }
	 */
	private void �����ٿ�ũ(L1PcInstance player) {
		player.addchecktime();
		player.set������time(player.get������time() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 3;
			int usetime = player.get������time();
			String s = isPC���尡�ɿ���(player.get������day(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "3"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.set������time(1);
					player.set������day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(153, outtime - 1), true);

					player.sendPackets(new S_ServerMessage(1526, "3"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ����(L1PcInstance player) {
		player.addchecktime();
		player.set����time(player.get����time() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.get����time();
			String s = isPC���尡�ɿ���(player.get����day(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.set����time(1);
					player.set����day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(153, outtime - 1), true);

					player.sendPackets(new S_ServerMessage(1526, "2"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ����(L1PcInstance player) {
		player.addchecktime();
		player.set����time(player.get����time() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.get����time();
			String s = isPC���尡�ɿ���(player.get����day(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.set����time(1);
					player.set����day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(153, outtime - 1), true);

					player.sendPackets(new S_ServerMessage(1526, "2"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ��������(L1PcInstance player) {
		player.addchecktime();
		player.set��������time(player.get��������time() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.get��������time();
			String s = isPC���尡�ɿ���(player.get��������day(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.set��������time(1);
					player.set��������day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(153, outtime - 1), true);

					player.sendPackets(new S_ServerMessage(1526, "2"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
