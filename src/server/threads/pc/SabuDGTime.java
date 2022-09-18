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
		player.sethalloweentime(player.gethalloweentime() + 1);
		int usetime = player.gethalloweentime();
		int outtime = 60 * 60 * 1;
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			String s1 = isPC���尡�ɿ���(player.gethalloweenday(), outtime, usetime);
			if (s1.equals("���尡��")) {// ���尡��
			} else if (s1.equals("�Ұ���")) {// ����Ұ���
				player.sendPackets(new S_ServerMessage(1522, "3"));// 5�ð� ���
				// ����ߴ�.
				L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
				return;
			} else if (s1.equals("�ʱ�ȭ")) {// �ʱ�ȭ
				player.sethalloweentime(1);
				player.sethalloweenday(nowday);
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
		player.setrubbertime(player.getrubbertime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getrubbertime();
			String s = isPC���尡�ɿ���(player.getrubberday(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.setrubbertime(1);
					player.setrubberday(nowday);
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
		player.setmongesomtime(player.getmongseomtime() + 1);
		player.setpc����time(player.getpctime1() + 1);
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		if (player.getpcday1() == null || player.getmongseonday() == null) {
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
				int usetime = player.getmongseomtime();
				String s = isAccount���尡�ɿ���(player.getmongseonday(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_SystemMessage("���� �ð� : ���� ���� �ð� 3�ð� 30�� ��� ���"), true);
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.setmongesomtime(1);
					player.set����day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_SystemMessage("���� �ð� : ���� ���� �ð� 3�ð� 30�� ����"), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int outtime = 60 * 60;
				int usetime = player.getpctime1();
				String s = isPC���尡�ɿ���(player.getpcday1(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_ServerMessage(1522, "30"));// �г��Ҵ�
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.setpc����time(1);
					player.setpcday1(nowday);
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
		player.setsolotowntime(player.getsolotowntime() + 1);
		if (player.getsolotownday() == null) {
			L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
			return;
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				int outtime = 60 * 40;
				int usetime = player.getsolotowntime();
				String s = isPC���尡�ɿ���(player.getsolotownday(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_ServerMessage(1527, "40"));// �г��Ҵ�
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.setsolotowntime(1);
					player.setsolotownday(nowday);
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
		player.setwateragarvalleytime(player.getsuspiciousagarvalleytime() + 1);

		if (player.getsuspiciousagarvalleyday() == null) {
			player.sendPackets(sm);
			L1Teleport.teleport(player, 32779, 32832, (short) 622, 5, true);
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				int outtime = 60 * 60;
				int usetime = player.getsuspiciousagarvalleytime();
				String s = isPC���尡�ɿ���(player.getsuspiciousagarvalleyday(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					// player.sendPackets(new S_SystemMessage(player,
					// "���� ü�� ���� �ð� 1�ð��� ��� ����ϼ̽��ϴ�."), true);
					player.sendPackets(new S_ServerMessage(1522, "1"));// 5�ð� ���
					// ����ߴ�.
					L1Teleport.teleport(player, 32779, 32832, (short) 622, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.setwateragarvalleytime(1);
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
		player.sethuntingeventtime(player.gethuntingeventtime() + 1);

		if (player.gethuntigeventday() == null) {
			player.sendPackets(sm);
			L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				int outtime = 60 * 60;
				int usetime = player.gethuntingeventtime();
				String s = isPC���尡�ɿ���(player.gethuntigeventday(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_ServerMessage(1522, "1"));// 5�ð� ���
					// ����ߴ�.
					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.sethuntingeventtime(1);
					player.sethuntingeventday(nowday);
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
		player.setsuspiciousprisontime(player.getsuspiciousprisontime() + 1);

		if (player.getsuspiciousprisonday() == null) {
			player.sendPackets(sm);
			L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
		} else {
			Timestamp nowday = new Timestamp(System.currentTimeMillis());
			try {
				int outtime = 60 * 60 * 2;
				int usetime = player.getsuspiciousprisontime();
				String s = isPC���尡�ɿ���(player.getsuspiciousprisonday(), outtime, usetime);
				if (s.equals("���尡��")) {// ���尡��
				} else if (s.equals("�Ұ���")) {// ����Ұ���
					player.sendPackets(new S_ServerMessage(1522, "2"));// 5�ð� ���
					// ����ߴ�.
					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
					return;
				} else if (s.equals("�ʱ�ȭ")) {// �ʱ�ȭ
					player.setsuspiciousprisontime(1);
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
		player.setforgetmetime(player.getforgetislandtime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getforgetislandtime();
			String s = isPC���尡�ɿ���(player.getforgetislanday(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.setforgetmetime(1);
					player.setforgetislandday(nowday);
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
		player.settrainingtime(player.gettrainingtime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 1;
			int usetime = player.gettrainingtime();
			String s = isPC���尡�ɿ���(player.getwaterlilyday(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "1"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.settrainingtime(1);
					player.settrainingday(nowday);
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
		player.setatubatime(player.getatubatime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 3;
			int usetime = player.getatubatime();
			String s = isPC���尡�ɿ���(player.getatubaday(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "3"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.setatubatime(1);
					player.setatoubaday(nowday);
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
		player.set_time(player.gettime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.gettime();
			String s = isPC���尡�ɿ���(player.getbudangday(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.set_time(1);
					player.set_day(nowday);
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
		player.setevatime(player.getevatime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getevatime();
			String s = isPC���尡�ɿ���(player.getevaday(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.setevatime(1);
					player.setevaday(nowday);
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
		player.setblackbattleshiptime(player.getblackbattleshiptime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getblackbattleshiptime();
			String s = isPC���尡�ɿ���(player.getblackbattleshipday(), outtime, usetime);

			if (!s.equals("���尡��")) {
				if (s.equals("�Ұ���")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("�ʱ�ȭ")) {
					player.setblackbattleshiptime(1);
					player.setblackbattleshipday(nowday);
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
