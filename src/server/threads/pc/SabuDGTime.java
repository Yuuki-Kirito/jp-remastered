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
				 * pc.getMapId() != 523 && pc.getMapId() != 524) {// 라스타바드 try {
				 * ravaTimeCheck(pc); } catch (Exception e) { } }
				 */

				if (pc.getMapId() >= 653 && pc.getMapId() <= 656) {
					try {
						수상한감옥TimeCheck(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() >= 1911 && pc.getMapId() <= 1913) {
					try {
						수상한천상계곡TimeCheck(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() >= 282 && pc.getMapId() <= 285) {// 상아탑 발록 진영
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
				if (pc.getMapId() == 54 || pc.getMapId() == 15403 || pc.getMapId() == 15404) {// 기란던전&지배자 결계
					try {

						GungeonTimeCheck(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() == 785 || pc.getMapId() == 788 || pc.getMapId() == 789) { // 수렵이벤트
					try {
						수렵이벤트TimeCheck(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() == 1931) {
					try {
						몽섬(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 1700 || pc.getMapId() == 1703) {
					try {
						잊섬(pc);
					} catch (Exception e) {
					}
				}

				if (pc.getMapId() == 10101) {
					try {
						수련(pc);
					} catch (Exception e) {
					}
				}
				/*
				 * if (pc.getMapId() == 5490) { try { 낚시(pc); } catch (Exception e) { } }
				 */
//				if (pc.getMapId() == 452 || pc.getMapId() == 453 || pc.getMapId() == 461 || pc.getMapId() == 462
//						|| pc.getMapId() == 471 || pc.getMapId() == 475 || pc.getMapId() == 495 || pc.getMapId() == 492
//						|| pc.getMapId() == 479) {
//					try {
//						라던(pc);
//					} catch (Exception e) {
//					}
//				}
				if (pc.getMapId() == 491 || pc.getMapId() == 492 || pc.getMapId() == 493) {
					try {
						아투바오크(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 777) {
					try {
						버땅(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 59 || pc.getMapId() == 60 || pc.getMapId() == 61 || pc.getMapId() == 63) {
					try {
						에바(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 10 || pc.getMapId() == 11 || pc.getMapId() == 12) {
					try {
						검은전함(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 430 || pc.getMapId() == 624) {
					try {
						고무(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 5501) {
					try {
						할로윈(pc);
					} catch (Exception e) {
					}
				}
				if (pc.getMapId() == 820) {
					try {
						솔로타운(pc);
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

	private void 할로윈(L1PcInstance player) {
		player.addchecktime();
		player.sethalloweentime(player.gethalloweentime() + 1);
		int usetime = player.gethalloweentime();
		int outtime = 60 * 60 * 1;
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			String s1 = isPC입장가능여부(player.gethalloweenday(), outtime, usetime);
			if (s1.equals("입장가능")) {// 입장가능
			} else if (s1.equals("불가능")) {// 입장불가능
				player.sendPackets(new S_ServerMessage(1522, "3"));// 5시간 모두
				// 사용했다.
				L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
				return;
			} else if (s1.equals("초기화")) {// 초기화
				player.sethalloweentime(1);
				player.sethalloweenday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
				player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);

				player.sendPackets(new S_ServerMessage(1526, "1"));// 시간 남았다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void 고무(L1PcInstance player) {
		player.addchecktime();
		player.setrubbertime(player.getrubbertime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getrubbertime();
			String s = isPC입장가능여부(player.getrubberday(), outtime, usetime);

			if (!s.equals("입장가능")) {
				if (s.equals("불가능")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("초기화")) {
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

	private void 몽섬(L1PcInstance player) {
		player.addchecktime();
		player.setmongesomtime(player.getmongseomtime() + 1);
		player.setpc몽섬time(player.getpctime1() + 1);
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		if (player.getpcday1() == null || player.getmongseonday() == null) {
			player.sendPackets(sm);
			L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
		} else {

			int 조각 = player.getInventory().countItems(60499);
			int 구슬 = player.getInventory().countItems(60500);
			int 씨앗 = player.getInventory().countItems(60501);

			// int maxcount = Math.max(구슬, 조각)*5;
			// int maxcount = Math.max(구슬, 조각)*5;
			int maxcount = (구슬 + 조각) * 5;
			if (maxcount > 50)
				maxcount = 50;

			if (maxcount <= 씨앗) {
				if (player.mongsumtel_waiting_time <= 0) {
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else {
					player.mongsumtel_waiting_time--;
				}
			}

			try {
				int outtime = 60 * 60;
				int usetime = player.getmongseomtime();
				String s = isAccount입장가능여부(player.getmongseonday(), outtime, usetime);
				if (s.equals("입장가능")) {// 입장가능
				} else if (s.equals("불가능")) {// 입장불가능
					player.sendPackets(new S_SystemMessage("입장 시간 : 계정 입장 시간 3시간 30분 모두 사용"), true);
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("초기화")) {// 초기화
					player.setmongesomtime(1);
					player.set몽섬day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_SystemMessage("입장 시간 : 계정 입장 시간 3시간 30분 남음"), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				int outtime = 60 * 60;
				int usetime = player.getpctime1();
				String s = isPC입장가능여부(player.getpcday1(), outtime, usetime);
				if (s.equals("입장가능")) {// 입장가능
				} else if (s.equals("불가능")) {// 입장불가능
					player.sendPackets(new S_ServerMessage(1522, "30"));// 분남았다
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("초기화")) {// 초기화
					player.setpc몽섬time(1);
					player.setpcday1(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1527, "30"));// 분남았다
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private void 솔로타운(L1PcInstance player) {
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
				String s = isPC입장가능여부(player.getsolotownday(), outtime, usetime);
				if (s.equals("입장가능")) {// 입장가능
				} else if (s.equals("불가능")) {// 입장불가능
					player.sendPackets(new S_ServerMessage(1527, "40"));// 분남았다
					L1Teleport.teleport(player, 33443, 32798, (short) 4, 5, true);
					return;
				} else if (s.equals("초기화")) {// 초기화
					player.setsolotowntime(1);
					player.setsolotownday(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1527, "40"));// 분남았다
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private S_SystemMessage sm = new S_SystemMessage("비정상 플레이어는 제제 대상이 됩니다.");

	/*
	 * private void ravaTimeCheck(L1PcInstance player) { player.addchecktime();
	 * player.setravatime(player.getravatime() + 1); if (player.getgiranday() ==
	 * null) { player.sendPackets(sm); int[] loc = Getback.GetBack_Location(player,
	 * true); L1Teleport .teleport(player, loc[0], loc[1], (short) loc[2], 5, true);
	 * } else { Timestamp nowday = new Timestamp(System.currentTimeMillis()); try {
	 * int outtime = 60 * 60 * 2; int usetime = player.getravatime(); String s =
	 * isPC입장가능여부(player.getgiranday(), outtime, usetime); if (s.equals("입장가능")) {//
	 * 입장가능 } else if (s.equals("불가능")) {// 입장불가능 player.sendPackets(new
	 * S_ServerMessage(1522, "2"));// 2시간 모두사용했다. int[] loc =
	 * Getback.GetBack_Location(player, true); L1Teleport.teleport(player, loc[0],
	 * loc[1], (short) loc[2], 5, true); return; } else if (s.equals("초기화")) {// 초기화
	 * player.setravatime(1); player.setravaday(nowday);
	 * player.getNetConnection().getAccount().updateDGTime(); player.sendPackets(new
	 * S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
	 * player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1),
	 * true); // player.sendPackets(new S_SystemMessage(player, //
	 * "기란/글루디오 던전 입장 시간이 초기화 되었습니다."), true); // player.sendPackets(new
	 * S_SystemMessage(player, // "던전 체류 시간이 3시간 남았습니다."), true);
	 * player.sendPackets(new S_ServerMessage(1526, "2"));// 시간 // 남았다. } } catch
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
					String s = isPC입장가능여부(player.getgiranday(), outtime, usetime);
					if (s.equals("입장가능")) {// 입장가능
					} else if (s.equals("불가능")) {// 입장불가능
						player.sendPackets(new S_ServerMessage(1522, "2"));// 2시간 모두사용했다.
						int[] loc = Getback.GetBack_Location(player, true);
						L1Teleport.teleport(player, loc[0], loc[1], (short) loc[2], 5, true);
						return;
					} else if (s.equals("초기화")) {// 초기화
						player.setgirantime(1);
						player.setgiranday(nowday);
						player.getNetConnection().getAccount().updateDGTime();
						player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
						player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
						// player.sendPackets(new S_SystemMessage(player,
						// "기란/글루디오 던전 입장 시간이 초기화 되었습니다."), true);
						// player.sendPackets(new S_SystemMessage(player,
						// "던전 체류 시간이 3시간 남았습니다."), true);
						player.sendPackets(new S_ServerMessage(1526, "2"));// 시간
						// 남았다.
					}
				} else if (player.getLevel() >= 90) {
					int outtime = 60 * 60 * 3;
					int usetime = player.getgirantime();
					String s = isPC입장가능여부(player.getgiranday(), outtime, usetime);
					if (s.equals("입장가능")) {// 입장가능
					} else if (s.equals("불가능")) {// 입장불가능
						player.sendPackets(new S_ServerMessage(1522, "3"));// 2시간 모두사용했다.
						int[] loc = Getback.GetBack_Location(player, true);
						L1Teleport.teleport(player, loc[0], loc[1], (short) loc[2], 5, true);
						return;
					} else if (s.equals("초기화")) {// 초기화
						player.setgirantime(3);
						player.setgiranday(nowday);
						player.getNetConnection().getAccount().updateDGTime();
						player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
						player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
						// player.sendPackets(new S_SystemMessage(player,
						// "기란/글루디오 던전 입장 시간이 초기화 되었습니다."), true);
						// player.sendPackets(new S_SystemMessage(player,
						// "던전 체류 시간이 3시간 남았습니다."), true);
						player.sendPackets(new S_ServerMessage(1526, "3"));// 시간
						// 남았다.
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("deprecation")
	private String isAccount입장가능여부(Timestamp accountday, int outtime, int usetime) {
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		String end = "불가능";
		String ok = "입장가능";
		String start = "초기화";
		if (accountday != null) {
			long clac = nowday.getTime() - accountday.getTime();

			int hours = nowday.getHours();
			int lasthours = accountday.getHours();

			if (accountday.getDate() != nowday.getDate()) {
				if (clac > 86400000 || hours >= Config.D_Reset_Time || lasthours < Config.D_Reset_Time) {// 24시간이 지낫거나
					// 오전9시이후라면
					return start;
				}
			} else {
				if (lasthours < Config.D_Reset_Time && hours >= Config.D_Reset_Time) {// 같은날 9시이전에 들어간거체크
					return start;
				}
			}
			if (outtime <= usetime) {
				return end;// 모두사용
			} else {
				return ok;
			}
		} else {
			return start;
		}
	}

	@SuppressWarnings("deprecation")
	private String isPC입장가능여부(Timestamp accountday, int outtime, int usetime) {
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		String end = "불가능";
		String ok = "입장가능";
		String start = "초기화";
		if (accountday != null) {
			long clac = nowday.getTime() - accountday.getTime();

			int hours = nowday.getHours();
			int lasthours = accountday.getHours();

			if (accountday.getDate() != nowday.getDate()) {
				// System.out.println(nowday.getHours());
				if (clac > 86400000 || hours >= Config.D_Reset_Time || lasthours < Config.D_Reset_Time) {// 24시간이 지낫거나
					// 오전9시이후라면
					return start;
				}
			} else {
				if (lasthours < Config.D_Reset_Time && hours >= Config.D_Reset_Time) {// 같은날 9시이전에 들어간거체크
					return start;
				}
			}
			if (outtime <= usetime) {
				return end;// 모두사용
			} else {
				return ok;
			}
		} else {
			return start;
		}
	}

	private void 수상한천상계곡TimeCheck(L1PcInstance player) {
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
				String s = isPC입장가능여부(player.getsuspiciousagarvalleyday(), outtime, usetime);
				if (s.equals("입장가능")) {// 입장가능
				} else if (s.equals("불가능")) {// 입장불가능
					// player.sendPackets(new S_SystemMessage(player,
					// "던전 체류 가능 시간 1시간을 모두 사용하셨습니다."), true);
					player.sendPackets(new S_ServerMessage(1522, "1"));// 5시간 모두
					// 사용했다.
					L1Teleport.teleport(player, 32779, 32832, (short) 622, 5, true);
					return;
				} else if (s.equals("초기화")) {// 초기화
					player.setwateragarvalleytime(1);
					player.set수상한천상계곡day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					// player.sendPackets(new S_SystemMessage(player,
					// "수상한 천상의 계곡 입장 시간이 초기화 되었습니다."), true);
					// player.sendPackets(new S_SystemMessage(player,
					// "던전 체류 시간이 1시간 남았습니다."), true);
					player.sendPackets(new S_ServerMessage(1526, "1"));// 시간
					// 남았다.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void 수렵이벤트TimeCheck(L1PcInstance player) {
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
				String s = isPC입장가능여부(player.gethuntigeventday(), outtime, usetime);
				if (s.equals("입장가능")) {// 입장가능
				} else if (s.equals("불가능")) {// 입장불가능
					player.sendPackets(new S_ServerMessage(1522, "1"));// 5시간 모두
					// 사용했다.
					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
					return;
				} else if (s.equals("초기화")) {// 초기화
					player.sethuntingeventtime(1);
					player.sethuntingeventday(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1526, "1"));// 시간
					// 남았다.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void 수상한감옥TimeCheck(L1PcInstance player) {
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
				String s = isPC입장가능여부(player.getsuspiciousprisonday(), outtime, usetime);
				if (s.equals("입장가능")) {// 입장가능
				} else if (s.equals("불가능")) {// 입장불가능
					player.sendPackets(new S_ServerMessage(1522, "2"));// 5시간 모두
					// 사용했다.
					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
					return;
				} else if (s.equals("초기화")) {// 초기화
					player.setsuspiciousprisontime(1);
					player.set수상한감옥day(nowday);
					player.getNetConnection().getAccount().updateDGTime();
					player.sendPackets(new S_PacketBox(player, S_PacketBox.DG_TIME_RESTART), true);
					player.sendPackets(new S_PacketBox(S_PacketBox.TIME_COUNT, outtime - 1), true);
					player.sendPackets(new S_ServerMessage(1526, "2"));// 시간
					// 남았다.
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
			String s = isPC입장가능여부(player.getivoryday(), outtime, usetime);

			if (!s.equals("입장가능")) {
				if (s.equals("불가능")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("초기화")) {
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

	private void 잊섬(L1PcInstance player) {
		player.addchecktime();
		player.setforgetmetime(player.getforgetislandtime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getforgetislandtime();
			String s = isPC입장가능여부(player.getforgetislanday(), outtime, usetime);

			if (!s.equals("입장가능")) {
				if (s.equals("불가능")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("초기화")) {
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

	private void 수련(L1PcInstance player) {
		player.addchecktime();
		player.settrainingtime(player.gettrainingtime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 1;
			int usetime = player.gettrainingtime();
			String s = isPC입장가능여부(player.getwaterlilyday(), outtime, usetime);

			if (!s.equals("입장가능")) {
				if (s.equals("불가능")) {
					player.sendPackets(new S_ServerMessage(1522, "1"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("초기화")) {
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
	 * private void 낚시(L1PcInstance player) { player.addchecktime();
	 * player.set낚시time(player.get낚시time() + 1);
	 * 
	 * Timestamp nowday = new Timestamp(System.currentTimeMillis()); try { int
	 * outtime = 60 * 60 * 8; int usetime = player.get낚시time(); String s =
	 * isPC입장가능여부(player.get낚시day(), outtime, usetime);
	 * 
	 * if (!s.equals("입장가능")) { if (s.equals("불가능")) { player.sendPackets(new
	 * S_ServerMessage(1522, "8"));
	 * 
	 * L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);
	 * 
	 * return; }if (s.equals("초기화")) { player.set낚시time(1); player.set낚시day(nowday);
	 * player.getNetConnection().getAccount().updateDGTime(); player.sendPackets(new
	 * S_PacketBox(153, outtime - 1), true);
	 * 
	 * player.sendPackets(new S_ServerMessage(1526, "8")); } } } catch (Exception e)
	 * { e.printStackTrace(); } }
	 */
	private void 아투바오크(L1PcInstance player) {
		player.addchecktime();
		player.setatubatime(player.getatubatime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 3;
			int usetime = player.getatubatime();
			String s = isPC입장가능여부(player.getatubaday(), outtime, usetime);

			if (!s.equals("입장가능")) {
				if (s.equals("불가능")) {
					player.sendPackets(new S_ServerMessage(1522, "3"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("초기화")) {
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

	private void 버땅(L1PcInstance player) {
		player.addchecktime();
		player.set_time(player.gettime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.gettime();
			String s = isPC입장가능여부(player.getbudangday(), outtime, usetime);

			if (!s.equals("입장가능")) {
				if (s.equals("불가능")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("초기화")) {
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

	private void 에바(L1PcInstance player) {
		player.addchecktime();
		player.setevatime(player.getevatime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getevatime();
			String s = isPC입장가능여부(player.getevaday(), outtime, usetime);

			if (!s.equals("입장가능")) {
				if (s.equals("불가능")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("초기화")) {
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

	private void 검은전함(L1PcInstance player) {
		player.addchecktime();
		player.setblackbattleshiptime(player.getblackbattleshiptime() + 1);

		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		try {
			int outtime = 60 * 60 * 2;
			int usetime = player.getblackbattleshiptime();
			String s = isPC입장가능여부(player.getblackbattleshipday(), outtime, usetime);

			if (!s.equals("입장가능")) {
				if (s.equals("불가능")) {
					player.sendPackets(new S_ServerMessage(1522, "2"));

					L1Teleport.teleport(player, 33432, 32796, (short) 4, 5, true);

					return;
				}
				if (s.equals("초기화")) {
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
