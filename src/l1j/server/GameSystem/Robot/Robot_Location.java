package l1j.server.GameSystem.Robot;

import java.util.ArrayList;
import java.util.Random;

public class Robot_Location {

	private static Random _random = new Random(System.currentTimeMillis());

	private static ArrayList<Robot_Location_bean> Giran_setting = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Giran_setting2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Giran_setting3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Giran_setting4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Giran_setting5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oren_setting = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Text_setting = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Well_done_setting = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Tell_me = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Tell_me2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Tell_me3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Tell_me4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Tell_me5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Tell_me6 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Yongdun_entrance = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Bondon_entrance = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Entrance = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ivory_Tower_4th_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ivory_Tower_5th_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Dragon_World1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Dragon_World2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Dragon_World3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Dragon_World4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Dragon_World5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Giant_Field1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Giant_Field2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> East1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> East2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> East3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> East4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> East5 = new ArrayList<Robot_Location_bean>();
	
	private static ArrayList<Robot_Location_bean> Storm1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Storm2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Storm3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Storm4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Storm5 = new ArrayList<Robot_Location_bean>();
	
	private static ArrayList<Robot_Location_bean> Underground_invasion_road_1st = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Underground_invasion_road_2st = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Underground_invasion_road_2nd = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Underground_invasion_road_2nd_floor2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Underground_invasion_road_3rd = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Underground_invasion_road_3rd_floor2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Eva_1st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Eva_1st_Floor2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Eva_2nd_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Eva_2nd_Floor2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Eva_3rd_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Eva_3rd_Floor2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Eva_4th_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Eva_4th_Floor2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Heine_Grass_Field = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Heine_Grass_Field2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Heine_Grass_Field3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Heine_Grass_Field4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Heine_Grass_Field5 = new ArrayList<Robot_Location_bean>();
	
	private static ArrayList<Robot_Location_bean> Oman_10th_Floor1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_10th_Floor2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_10th_Floor3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_10th_Floor4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_10th_Floor5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_10th_Floor6 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_10th_Floor7 = new ArrayList<Robot_Location_bean>();
	
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor6 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor7 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor8 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor9 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_9th_Floor10 = new ArrayList<Robot_Location_bean>();
	
	private static ArrayList<Robot_Location_bean> Oman_8th_Floor1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_8th_Floor2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_8th_Floor3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_8th_Floor4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_8th_Floor5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_8th_Floor6 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_8th_Floor7 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Oman_8th_Floor8 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Underground = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Yongdun_1st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Yongdun_2st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Yougdun_3st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Yougdun_4st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Yougdun_5st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Yougdun_6st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Yougdun_7st_Floor = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Bondon_1st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Bondon_2st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Bondon_3st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Bondon_4st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Bondon_5st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Bondon_6st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Bondon_7st_Floor = new ArrayList<Robot_Location_bean>();


	private static ArrayList<Robot_Location_bean> Ship_Deep_Sea = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ship_Deep_Sea2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ship_Deep_Sea3 = new ArrayList<Robot_Location_bean>();
	
	private static ArrayList<Robot_Location_bean> Forget_Isiand2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Forget_Isiand3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Forget_Isiand4 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Ant_Den1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ant_Den2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ant_Den3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ant_Den4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ant_Den5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Ant_Den6 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> Gigam_1st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Gigam_2st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Gigam_3st_Floor = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> Gigam_4st_Floor = new ArrayList<Robot_Location_bean>();
	
	private static ArrayList<Robot_Location_bean> Barrier_1st_Floor = new ArrayList<Robot_Location_bean>();

	public static void location_registration(int x, int y, int m) {

	}

	public static ArrayList<Robot_Location_bean> location(L1RobotInstance bot) {
		_random.setSeed(System.currentTimeMillis());
		if (bot.hunting_bot_type == L1RobotInstance.SETTING) {
			if (bot.hunting_bot_location.equalsIgnoreCase("Yongdun 1st floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 2nd floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 3rd floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 4th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdon 5th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 6th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 7th floor")) {
				return _random.nextInt(1000) > 500 ? Giran_setting : Giran_setting2;
			}
			int rr = _random.nextInt(16);
			if (rr == 15) {
				switch (_random.nextInt(2)) {
				case 0:
					rr = 0;
					break;
				case 1:
					rr = 7;
					break;
				}
			}
			switch (rr) {
			case 7:
			case 11:
			case 0:
				return _random.nextInt(1000) >= 500 ? Giran_setting2 : Giran_setting5;
			case 8:
			case 4:
			case 1:
				return Oren_setting;
			case 13:
			case 12:
				return Giran_setting4;
			case 10:
			case 9:
			case 5:
			case 2:
				return _random.nextInt(1000) >= 500 ? Giran_setting : Giran_setting3;
				//return テキスト設定; //元のテキスト設定
			case 14:
			case 6:
			case 3:
				return Text_setting;
				//return well-done setting;
			default:
				break;
			}
			return Giran_setting;
		} else if (bot.hunting_bot_type == L1RobotInstance.TEL_NPC_MOVE) {
			if (bot.hunting_bot_location.equalsIgnoreCase("Yongdun 1st floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 2nd floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 3rd floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 4th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdon 5th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 6th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Yongdun 7th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("ant den 1")) {
				return Tell_me4;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Bondon 1st floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Bondon 2nd floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Bondon 3rd floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Bondon 4th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Bondon 5th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Bondon 6th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("Bondon 7th floor")
					|| bot.hunting_bot_location.startsWith("forget island")  
				    || bot.hunting_bot_location.startsWith("Barrier")
				    || bot.hunting_bot_location.equalsIgnoreCase("ant den 2")) { 
				    return Tell_me; 
			} else if (bot.hunting_bot_location.startsWith("Barrier")
					|| bot.hunting_bot_location.equalsIgnoreCase("ant den 3")) { 
				    return Tell_me3; 
			} else if (bot.hunting_bot_location.startsWith("dragon") || bot.hunting_bot_location.startsWith("huadong")
					|| bot.hunting_bot_location.equalsIgnoreCase("ant den 4")) { 
			    return Tell_me5;
			} else if (bot.hunting_bot_location.startsWith("Oman 8th floor")
					|| bot.hunting_bot_location.startsWith("Oman 9th floor")
					|| bot.hunting_bot_location.startsWith("Oman 10th floor")
					|| bot.hunting_bot_location.equalsIgnoreCase("ant den 5")) {
				    return Tell_me2;
	         } else if (bot.hunting_bot_location.equalsIgnoreCase("Gigam 1st floor")
	        		 || bot.hunting_bot_location.equalsIgnoreCase("Gigam 2nd floor")
	        		 || bot.hunting_bot_location.equalsIgnoreCase("Gigam 3rd floor")
	        		 || bot.hunting_bot_location.equalsIgnoreCase("Gigam 4th floor")
	        		 || bot.hunting_bot_location.equalsIgnoreCase("ant den 6")) 
			   return Tell_me6;
			
	         else
			 return Tell_me;
			 
		  } else if (bot.hunting_bot_type == L1RobotInstance.HUNT_MOVE) {
			bot.is_TELL_HUNTING = false;
			if (bot.hunting_bot_location.equalsIgnoreCase("dragon")) {
				switch (_random.nextInt(5)) {
				case 0:
					return Dragon_World1;
				case 1:
					return Dragon_World2;
				case 2:
					return Dragon_World3;
				case 3:
					return Dragon_World4;
				case 4:
					return Dragon_World5;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("giant field")) {
				if (_random.nextInt(100) < 50)
					return Giant_Field1;
				else
					return Giant_Field2;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("huadong")) {
				switch (_random.nextInt(5)) {
				case 0:
					return East1;
				case 1:
					return East2;
				case 2:
					return East3;
				case 3:
					return East4;
				case 4:
					return East5;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("storm")) {
				switch (_random.nextInt(5)) {
				case 0:
					return Storm1;
				case 1:
					return Storm2;
				case 2:
					return Storm3;
				case 3:
					return Storm4;
				case 4:
					return Storm5;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("1st floor of the underground invasion road")) {
				bot.is_TELL_HUNTING = true;
				if (_random.nextInt(100) >= 50)
					return Underground_invasion_road_1st;
				else
					return Underground_invasion_road_2st;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("2nd floor of the underground invasion road")) {
				bot.is_TELL_HUNTING = true;
				if (_random.nextInt(100) >= 50)
					return Underground_invasion_road_2nd;
				else
					return Underground_invasion_road_2nd_floor2;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("3rd floor of the underground invasion road")) {
				bot.is_TELL_HUNTING = true;
				if (_random.nextInt(100) >= 50)
					return Underground_invasion_road_3rd;
				else
					return Underground_invasion_road_3rd_floor2;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Eva 1st floor")) {
				bot.is_TELL_HUNTING = true;
				if (_random.nextInt(100) >= 50)
					return Eva_1st_Floor;
				else
					return Eva_1st_Floor2;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Eva 2nd floor")) {
				bot.is_TELL_HUNTING = true;
				if (_random.nextInt(100) >= 50)
					return Eva_2nd_Floor;
				else
					return Eva_2nd_Floor2;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Eva 3rd floor")) {
				bot.is_TELL_HUNTING = true;
				if (_random.nextInt(100) >= 50)
					return Eva_3rd_Floor;
				else
					return Eva_3rd_Floor2;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Eva 4th floor")) {
				bot.is_TELL_HUNTING = true;
				if (_random.nextInt(100) >= 50)
					return Eva_4th_Floor;
				else
					return Eva_4th_Floor2;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("heine grass field")) {
				switch (_random.nextInt(9)) {
				case 5:
				case 0:
					return Heine_Grass_Field;
				case 6:
				case 1:
					return Heine_Grass_Field2;
				case 7:
				case 2:
					return Heine_Grass_Field3;
				case 8:
				case 3:
					return Heine_Grass_Field4;
				case 4:
					return Heine_Grass_Field5;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Oman 10th floor")) {
				switch (_random.nextInt(7)) {
				case 0:
					return Oman_10th_Floor1;
				case 1:
					return Oman_10th_Floor2;
				case 2:
					return Oman_10th_Floor3;
				case 3:
					return Oman_10th_Floor4;
				case 4:
					return Oman_10th_Floor5;
				case 5:
					return Oman_10th_Floor6;
				case 6:
					return Oman_10th_Floor7;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Oman 9th floor")) {
				switch (_random.nextInt(10)) {
				case 0:
					return Oman_9th_Floor1;
				case 1:
					return Oman_9th_Floor2;
				case 2:
					return Oman_9th_Floor3;
				case 3:
					return Oman_9th_Floor4;
				case 4:
					return Oman_9th_Floor5;
				case 5:
					return Oman_9th_Floor6;
				case 6:
					return Oman_9th_Floor7;
				case 7:
					return Oman_9th_Floor8;
				case 8:
					return Oman_9th_Floor9;
				case 9:
					return Oman_9th_Floor10;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Oman 8th floor")) {
				switch (_random.nextInt(8)) {
				case 0:
					return Oman_8th_Floor1;
				case 1:
					return Oman_8th_Floor2;
				case 2:
					return Oman_8th_Floor3;
				case 3:
					return Oman_8th_Floor4;
				case 4:
					return Oman_8th_Floor5;
				case 5:
					return Oman_8th_Floor6;
				case 6:
					return Oman_8th_Floor7;
				case 7:
					return Oman_8th_Floor8;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("ship deep sea")) {
				bot.is_TELL_HUNTING = true;
				switch (_random.nextInt(3)) {
				case 0:
					return Ship_Deep_Sea;
				case 1:
					return Ship_Deep_Sea2;
				case 2:
					return Ship_Deep_Sea3;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("forget island")) {
				//bot.텔사냥 = false;
				switch (_random.nextInt(3)) {
				case 0:
					return Forget_Isiand2;
				case 1:
					return Forget_Isiand3;
				case 2:
					return Forget_Isiand4;
				default:
					break;
				}
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Barrier")) {
				bot.is_TELL_HUNTING = true;
				return Barrier_1st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("ant den 1")) {
				bot.is_TELL_HUNTING = true;
				return Ant_Den1;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("ant den 2")) {
				bot.is_TELL_HUNTING = true;
				return Ant_Den2;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("ant den 3")) {
				bot.is_TELL_HUNTING = true;
				return Ant_Den3;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("ant den 4")) {
				bot.is_TELL_HUNTING = true;
				return Ant_Den4;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("ant den 5")) {
				bot.is_TELL_HUNTING = true;
				return Ant_Den5;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("ant den 6")) {
				bot.is_TELL_HUNTING = true;
				return Ant_Den6;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Ivory Tower 4th Floor")) {
				bot.is_TELL_HUNTING = true;
				return Ivory_Tower_4th_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Ivory Tower 5th Floor")) {
				bot.is_TELL_HUNTING = true;
				return Ivory_Tower_5th_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("underground")) {
				bot.is_TELL_HUNTING = true;
				return Underground;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Yongdun 1st floor")) {
				bot.is_TELL_HUNTING = true;
				return Yongdun_1st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Yongdun 2nd floor")) {
				bot.is_TELL_HUNTING = true;
				return Yongdun_2st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Yongdun 3rd floor")) {
				bot.is_TELL_HUNTING = true;
				return Yougdun_3st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Yongdun 4th floor")) {
				bot.is_TELL_HUNTING = true;
				return Yougdun_4st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Yongdon 5th floor")) {
				bot.is_TELL_HUNTING = true;
				return Yougdun_5st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Yongdun 6th floor")) {
				bot.is_TELL_HUNTING = true;
				return Yougdun_6st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Yongdun 7th floor")) {
				bot.is_TELL_HUNTING = true;
				return Yougdun_7st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Bondon 1st floor")) {
				bot.is_TELL_HUNTING = true;
				return Bondon_1st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Bondon 2nd floor")) {
				bot.is_TELL_HUNTING = true;
				return Bondon_2st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Bondon 3rd floor")) {
				bot.is_TELL_HUNTING = true;
				return Bondon_3st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Bondon 4th floor")) {
				bot.is_TELL_HUNTING = true;
				return Bondon_4st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Bondon 5th floor")) {
				bot.is_TELL_HUNTING = true;
				return Bondon_5st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Bondon 6th floor")) {
				bot.is_TELL_HUNTING = true;
				return Bondon_6st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Bondon 7th floor")) {
				bot.is_TELL_HUNTING = true;
				return Bondon_7st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Gigam 1st floor")) {
				bot.is_TELL_HUNTING = true;
				return Gigam_1st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Gigam 2nd floor")) {
				bot.is_TELL_HUNTING = true;
				return Gigam_2st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Gigam 3rd floor")) {
				bot.is_TELL_HUNTING = true;
				return Gigam_3st_Floor;
			} else if (bot.hunting_bot_location.equalsIgnoreCase("Gigam 4th floor")) {
				bot.is_TELL_HUNTING = true;
				return Gigam_4st_Floor;
			}
		}
		return null;
	}

	public static void setRLOC() {
		// Potions, Warehouses, Buffsポーション、倉庫、バフ
		Giran_setting.add(new Robot_Location_bean(33457, 32819, 4));
		Giran_setting.add(new Robot_Location_bean(33431, 32816, 4));
		Giran_setting.add(new Robot_Location_bean(33437, 32804, 4));
		Giran_setting2.add(new Robot_Location_bean(33432, 32815, 4));
		Giran_setting2.add(new Robot_Location_bean(33457, 32820, 4));
		Giran_setting2.add(new Robot_Location_bean(33437, 32804, 4));
		Giran_setting3.add(new Robot_Location_bean(33428, 32806, 4));
		Giran_setting3.add(new Robot_Location_bean(33422, 32813, 4));
		Giran_setting3.add(new Robot_Location_bean(33437, 32803, 4));
		Giran_setting4.add(new Robot_Location_bean(33437, 32803, 4));
		Giran_setting5.add(new Robot_Location_bean(33428, 32806, 4));
		Giran_setting5.add(new Robot_Location_bean(33440, 32801, 4));

		Oren_setting.add(new Robot_Location_bean(34065, 32287, 4));
		Oren_setting.add(new Robot_Location_bean(34053, 32287, 4));
		Oren_setting.add(new Robot_Location_bean(34064, 32279, 4));
		Text_setting.add(new Robot_Location_bean(32596, 32741, 4));
		Text_setting.add(new Robot_Location_bean(32609, 32735, 4));
		Well_done_setting.add(new Robot_Location_bean(33738, 32494, 4));
		Well_done_setting.add(new Robot_Location_bean(33723, 32488, 4));
		Well_done_setting.add(new Robot_Location_bean(33714, 32498, 4));
		// Giran Telnyeo Go
		Tell_me.add(new Robot_Location_bean(33437, 32795, 4));
		Tell_me2.add(new Robot_Location_bean(33449, 32803, 4));
		Tell_me3.add(new Robot_Location_bean(33448, 32812, 4));
		Tell_me4.add(new Robot_Location_bean(33440, 32808, 4));
		Tell_me5.add(new Robot_Location_bean(33419, 32804, 4));
		Tell_me6.add(new Robot_Location_bean(33429, 32826, 4));
		// Yongdon entrance
		Yongdun_entrance.add(new Robot_Location_bean(33446, 32828, 4));
		// giraffe entrance
		Entrance.add(new Robot_Location_bean(33428, 32820, 4));
		// bondon entrance
		Bondon_entrance.add(new Robot_Location_bean(32727, 32929, 4));
		
		// dragon
		Dragon_World1.add(new Robot_Location_bean(33323, 32432, 15430));
		Dragon_World1.add(new Robot_Location_bean(33315, 32432, 15430));
		Dragon_World1.add(new Robot_Location_bean(33304, 32429, 15430));
		Dragon_World1.add(new Robot_Location_bean(33292, 32431, 15430));
		Dragon_World1.add(new Robot_Location_bean(33276, 32429, 15430));
		Dragon_World1.add(new Robot_Location_bean(33266, 32424, 15430));
		Dragon_World1.add(new Robot_Location_bean(33262, 32412, 15430));
		Dragon_World1.add(new Robot_Location_bean(33275, 32400, 15430));
		Dragon_World1.add(new Robot_Location_bean(33287, 32400, 15430));
		Dragon_World1.add(new Robot_Location_bean(33306, 32394, 15430));
		Dragon_World1.add(new Robot_Location_bean(33318, 32391, 15430));
		Dragon_World1.add(new Robot_Location_bean(33318, 32383, 15430));
		Dragon_World1.add(new Robot_Location_bean(33308, 32379, 15430));
		Dragon_World1.add(new Robot_Location_bean(33300, 32378, 15430));
		Dragon_World1.add(new Robot_Location_bean(33298, 32366, 15430));
		Dragon_World1.add(new Robot_Location_bean(33306, 32360, 15430));
		Dragon_World1.add(new Robot_Location_bean(33322, 32352, 15430));
		Dragon_World1.add(new Robot_Location_bean(33337, 32350, 15430));
		Dragon_World1.add(new Robot_Location_bean(33352, 32353, 15430));
		Dragon_World1.add(new Robot_Location_bean(33368, 32351, 15430));
		Dragon_World1.add(new Robot_Location_bean(33381, 32353, 15430));
		Dragon_World1.add(new Robot_Location_bean(33389, 32336, 15430));
		Dragon_World1.add(new Robot_Location_bean(33384, 32324, 15430));
		Dragon_World1.add(new Robot_Location_bean(33381, 32315, 15430));
		Dragon_World1.add(new Robot_Location_bean(33373, 32305, 15430));
		Dragon_World1.add(new Robot_Location_bean(33361, 32309, 15430));
		Dragon_World1.add(new Robot_Location_bean(33344, 32320, 15430));
		Dragon_World1.add(new Robot_Location_bean(33326, 32303, 15430));
		Dragon_World1.add(new Robot_Location_bean(33345, 32283, 15430));
		
		
		Dragon_World5.add(new Robot_Location_bean(33358, 32278, 15430));
		Dragon_World5.add(new Robot_Location_bean(33359, 32278, 15430));
		Dragon_World5.add(new Robot_Location_bean(33374, 32276, 15430));
		Dragon_World5.add(new Robot_Location_bean(33383, 32276, 15430));
		Dragon_World5.add(new Robot_Location_bean(33393, 32279, 15430));
		Dragon_World5.add(new Robot_Location_bean(33413, 32296, 15430));
		Dragon_World5.add(new Robot_Location_bean(33427, 32305, 15430));
		Dragon_World5.add(new Robot_Location_bean(33428, 32316, 15430));
		Dragon_World5.add(new Robot_Location_bean(33419, 32326, 15430));
		Dragon_World5.add(new Robot_Location_bean(33422, 32341, 15430));
		Dragon_World5.add(new Robot_Location_bean(33413, 32356, 15430));
		Dragon_World5.add(new Robot_Location_bean(33411, 32378, 15430));
		Dragon_World5.add(new Robot_Location_bean(33400, 32389, 15430));
		Dragon_World5.add(new Robot_Location_bean(33404, 32404, 15430));
		Dragon_World5.add(new Robot_Location_bean(33398, 32414, 15430));
		Dragon_World5.add(new Robot_Location_bean(33385, 32423, 15430));
		Dragon_World5.add(new Robot_Location_bean(33382, 32440, 15430));
		Dragon_World5.add(new Robot_Location_bean(33373, 32446, 15430));
		Dragon_World5.add(new Robot_Location_bean(33357, 32439, 15430));
		Dragon_World5.add(new Robot_Location_bean(33342, 32438, 15430));
		
		
		Dragon_World2.add(new Robot_Location_bean(33275, 32400, 15430));
		Dragon_World2.add(new Robot_Location_bean(33287, 32400, 15430));
		Dragon_World2.add(new Robot_Location_bean(33306, 32394, 15430));
		Dragon_World2.add(new Robot_Location_bean(33318, 32391, 15430));
		Dragon_World2.add(new Robot_Location_bean(33328, 32386, 15430));
		Dragon_World2.add(new Robot_Location_bean(33342, 32387, 15430));
		Dragon_World2.add(new Robot_Location_bean(33356, 32402, 15430));
		Dragon_World2.add(new Robot_Location_bean(33362, 32384, 15430));
		Dragon_World2.add(new Robot_Location_bean(33359, 32373, 15430));
		Dragon_World2.add(new Robot_Location_bean(33367, 32367, 15430));
		Dragon_World2.add(new Robot_Location_bean(33388, 32373, 15430));
		Dragon_World2.add(new Robot_Location_bean(33395, 32358, 15430));
		Dragon_World2.add(new Robot_Location_bean(33385, 32350, 15430));
		Dragon_World2.add(new Robot_Location_bean(33388, 32335, 15430));
		Dragon_World2.add(new Robot_Location_bean(33382, 32315, 15430));
		Dragon_World2.add(new Robot_Location_bean(33370, 32302, 15430));
		Dragon_World2.add(new Robot_Location_bean(33358, 32296, 15430));
		Dragon_World2.add(new Robot_Location_bean(33347, 32298, 15430));
		Dragon_World2.add(new Robot_Location_bean(33339, 32288, 15430));
		Dragon_World2.add(new Robot_Location_bean(33345, 32283, 15430));
		Dragon_World2.add(new Robot_Location_bean(33358, 32278, 15430));
		Dragon_World2.add(new Robot_Location_bean(33359, 32278, 15430));
		Dragon_World2.add(new Robot_Location_bean(33374, 32276, 15430));
		Dragon_World2.add(new Robot_Location_bean(33383, 32276, 15430));
		Dragon_World2.add(new Robot_Location_bean(33393, 32279, 15430));
		Dragon_World2.add(new Robot_Location_bean(33413, 32296, 15430));
		Dragon_World2.add(new Robot_Location_bean(33427, 32305, 15430));
		Dragon_World2.add(new Robot_Location_bean(33428, 32316, 15430));
		Dragon_World2.add(new Robot_Location_bean(33419, 32326, 15430));
		Dragon_World2.add(new Robot_Location_bean(33422, 32341, 15430));
		Dragon_World2.add(new Robot_Location_bean(33413, 32356, 15430));
		Dragon_World2.add(new Robot_Location_bean(33411, 32378, 15430));
		Dragon_World2.add(new Robot_Location_bean(33400, 32389, 15430));
		Dragon_World2.add(new Robot_Location_bean(33404, 32404, 15430));
		Dragon_World2.add(new Robot_Location_bean(33398, 32414, 15430));
		Dragon_World2.add(new Robot_Location_bean(33385, 32423, 15430));
		Dragon_World2.add(new Robot_Location_bean(33382, 32440, 15430));
		Dragon_World2.add(new Robot_Location_bean(33373, 32446, 15430));
		Dragon_World2.add(new Robot_Location_bean(33357, 32439, 15430));
		Dragon_World2.add(new Robot_Location_bean(33342, 32438, 15430));
		
		
		Dragon_World3.add(new Robot_Location_bean(33352, 32438, 15430));
		Dragon_World3.add(new Robot_Location_bean(33370, 32444, 15430));
		Dragon_World3.add(new Robot_Location_bean(33379, 32445, 15430));
		Dragon_World3.add(new Robot_Location_bean(33381, 32432, 15430));
		Dragon_World3.add(new Robot_Location_bean(33386, 32419, 15430));
		Dragon_World3.add(new Robot_Location_bean(33405, 32411, 15430));
		Dragon_World3.add(new Robot_Location_bean(33402, 32392, 15430));
		Dragon_World3.add(new Robot_Location_bean(33386, 32391, 15430));
		Dragon_World3.add(new Robot_Location_bean(33400, 32387, 15430));
		Dragon_World3.add(new Robot_Location_bean(33409, 32378, 15430));
		Dragon_World3.add(new Robot_Location_bean(33411, 32357, 15430));
		Dragon_World3.add(new Robot_Location_bean(33422, 32341, 15430));
		Dragon_World3.add(new Robot_Location_bean(33420, 32323, 15430));
		Dragon_World3.add(new Robot_Location_bean(33429, 32314, 15430));
		Dragon_World3.add(new Robot_Location_bean(33427, 32305, 15430));
		Dragon_World3.add(new Robot_Location_bean(33414, 32296, 15430));
		Dragon_World3.add(new Robot_Location_bean(33400, 32284, 15430));
		Dragon_World3.add(new Robot_Location_bean(33391, 32275, 15430));
		Dragon_World3.add(new Robot_Location_bean(33379, 32275, 15430));
		Dragon_World3.add(new Robot_Location_bean(33356, 32277, 15430));
		Dragon_World3.add(new Robot_Location_bean(33349, 32280, 15430));
		Dragon_World3.add(new Robot_Location_bean(33338, 32290, 15430));
		Dragon_World3.add(new Robot_Location_bean(33323, 32303, 15430));
		Dragon_World3.add(new Robot_Location_bean(33317, 32320, 15430));
		Dragon_World3.add(new Robot_Location_bean(33308, 32333, 15430));
		Dragon_World3.add(new Robot_Location_bean(33286, 32334, 15430));
		Dragon_World3.add(new Robot_Location_bean(33277, 32348, 15430));
		Dragon_World3.add(new Robot_Location_bean(33275, 32366, 15430));
		Dragon_World3.add(new Robot_Location_bean(33269, 32381, 15430));
		Dragon_World3.add(new Robot_Location_bean(33259, 32396, 15430));
		Dragon_World3.add(new Robot_Location_bean(33259, 32407, 15430));
		Dragon_World3.add(new Robot_Location_bean(33266, 32424, 15430));
		Dragon_World3.add(new Robot_Location_bean(33288, 32430, 15430));
		Dragon_World3.add(new Robot_Location_bean(33305, 32431, 15430));
		Dragon_World3.add(new Robot_Location_bean(33325, 32431, 15430));
		
		
		
		Dragon_World4.add(new Robot_Location_bean(33420, 32323, 15430));
		Dragon_World4.add(new Robot_Location_bean(33429, 32314, 15430));
		Dragon_World4.add(new Robot_Location_bean(33427, 32305, 15430));
		Dragon_World4.add(new Robot_Location_bean(33414, 32296, 15430));
		Dragon_World4.add(new Robot_Location_bean(33400, 32284, 15430));
		Dragon_World4.add(new Robot_Location_bean(33379, 32275, 15430));
		Dragon_World4.add(new Robot_Location_bean(33356, 32277, 15430));
		Dragon_World4.add(new Robot_Location_bean(33338, 32290, 15430));
		Dragon_World4.add(new Robot_Location_bean(33324, 32304, 15430));
		Dragon_World4.add(new Robot_Location_bean(33340, 32314, 15430));
		Dragon_World4.add(new Robot_Location_bean(33356, 32315, 15430));
		Dragon_World4.add(new Robot_Location_bean(33372, 32305, 15430));
		Dragon_World4.add(new Robot_Location_bean(33380, 32313, 15430));
		Dragon_World4.add(new Robot_Location_bean(33385, 32324, 15430));
		Dragon_World4.add(new Robot_Location_bean(33388, 32336, 15430));
		Dragon_World4.add(new Robot_Location_bean(33384, 32352, 15430));
		Dragon_World4.add(new Robot_Location_bean(33395, 32359, 15430));
		Dragon_World4.add(new Robot_Location_bean(33389, 32370, 15430));
		Dragon_World4.add(new Robot_Location_bean(33376, 32371, 15430));
		Dragon_World4.add(new Robot_Location_bean(33364, 32368, 15430));
		Dragon_World4.add(new Robot_Location_bean(33358, 32376, 15430));
		Dragon_World4.add(new Robot_Location_bean(33361, 32389, 15430));
		Dragon_World4.add(new Robot_Location_bean(33355, 32402, 15430));
		Dragon_World4.add(new Robot_Location_bean(33343, 32388, 15430));
		Dragon_World4.add(new Robot_Location_bean(33324, 32386, 15430));
		Dragon_World4.add(new Robot_Location_bean(33312, 32395, 15430));
		Dragon_World4.add(new Robot_Location_bean(33295, 32396, 15430));
		Dragon_World4.add(new Robot_Location_bean(33282, 32401, 15430));
		Dragon_World4.add(new Robot_Location_bean(33269, 32406, 15430));
		Dragon_World4.add(new Robot_Location_bean(33260, 32418, 15430));
		Dragon_World4.add(new Robot_Location_bean(33274, 32426, 15430));
		Dragon_World4.add(new Robot_Location_bean(33292, 32430, 15430));
		Dragon_World4.add(new Robot_Location_bean(33306, 32432, 15430));
		Dragon_World4.add(new Robot_Location_bean(33320, 32432, 15430));
		Dragon_World4.add(new Robot_Location_bean(33336, 32433, 15430));
		
		
		
		Giant_Field1.add(new Robot_Location_bean(34278, 33214, 4));
		Giant_Field1.add(new Robot_Location_bean(34279, 33232, 4));
		Giant_Field1.add(new Robot_Location_bean(34277, 33248, 4));
		Giant_Field1.add(new Robot_Location_bean(34276, 33262, 4));
		Giant_Field1.add(new Robot_Location_bean(34278, 33275, 4));
		Giant_Field1.add(new Robot_Location_bean(34276, 33299, 4));
		Giant_Field1.add(new Robot_Location_bean(34274, 33325, 4));
		Giant_Field1.add(new Robot_Location_bean(34272, 33342, 4));
		Giant_Field1.add(new Robot_Location_bean(34277, 33355, 4));
		Giant_Field1.add(new Robot_Location_bean(34278, 33370, 4));
		Giant_Field1.add(new Robot_Location_bean(34272, 33384, 4));
		Giant_Field1.add(new Robot_Location_bean(34255, 33387, 4));
		Giant_Field1.add(new Robot_Location_bean(34247, 33400, 4));
		Giant_Field1.add(new Robot_Location_bean(34238, 33412, 4));
		Giant_Field1.add(new Robot_Location_bean(34226, 33431, 4));
		Giant_Field1.add(new Robot_Location_bean(34227, 33455, 4));
		Giant_Field1.add(new Robot_Location_bean(34230, 33430, 4));
		Giant_Field1.add(new Robot_Location_bean(34227, 33422, 4));
		Giant_Field1.add(new Robot_Location_bean(34240, 33410, 4));
		Giant_Field1.add(new Robot_Location_bean(34252, 33397, 4));
		Giant_Field1.add(new Robot_Location_bean(34266, 33387, 4));
		Giant_Field1.add(new Robot_Location_bean(34276, 33377, 4));
		Giant_Field1.add(new Robot_Location_bean(34275, 33364, 4));
		Giant_Field1.add(new Robot_Location_bean(34263, 33364, 4));
		Giant_Field1.add(new Robot_Location_bean(34250, 33363, 4));
		Giant_Field1.add(new Robot_Location_bean(34235, 33358, 4));
		Giant_Field1.add(new Robot_Location_bean(34238, 33340, 4));
		Giant_Field1.add(new Robot_Location_bean(34225, 33329, 4));
		Giant_Field1.add(new Robot_Location_bean(34224, 33315, 4));
		Giant_Field1.add(new Robot_Location_bean(34227, 33300, 4));
		Giant_Field1.add(new Robot_Location_bean(34240, 33288, 4));
		Giant_Field1.add(new Robot_Location_bean(34248, 33279, 4));
		Giant_Field1.add(new Robot_Location_bean(34256, 33258, 4));
		Giant_Field1.add(new Robot_Location_bean(34262, 33247, 4));
		Giant_Field1.add(new Robot_Location_bean(34263, 33238, 4));
		Giant_Field1.add(new Robot_Location_bean(34262, 33228, 4));
		Giant_Field1.add(new Robot_Location_bean(34260, 33219, 4));
		Giant_Field1.add(new Robot_Location_bean(34269, 33218, 4));
		Giant_Field2.add(new Robot_Location_bean(34278, 33214, 4));
		Giant_Field2.add(new Robot_Location_bean(34265, 33220, 4));
		Giant_Field2.add(new Robot_Location_bean(34262, 33232, 4));
		Giant_Field2.add(new Robot_Location_bean(34263, 33245, 4));
		Giant_Field2.add(new Robot_Location_bean(34257, 33258, 4));
		Giant_Field2.add(new Robot_Location_bean(34249, 33273, 4));
		Giant_Field2.add(new Robot_Location_bean(34246, 33283, 4));
		Giant_Field2.add(new Robot_Location_bean(34232, 33291, 4));
		Giant_Field2.add(new Robot_Location_bean(34226, 33304, 4));
		Giant_Field2.add(new Robot_Location_bean(34225, 33315, 4));
		Giant_Field2.add(new Robot_Location_bean(34223, 33327, 4));
		Giant_Field2.add(new Robot_Location_bean(34236, 33337, 4));
		Giant_Field2.add(new Robot_Location_bean(34237, 33354, 4));
		Giant_Field2.add(new Robot_Location_bean(34245, 33363, 4));
		Giant_Field2.add(new Robot_Location_bean(34255, 33357, 4));
		Giant_Field2.add(new Robot_Location_bean(34254, 33342, 4));
		Giant_Field2.add(new Robot_Location_bean(34255, 33329, 4));
		Giant_Field2.add(new Robot_Location_bean(34245, 33316, 4));
		Giant_Field2.add(new Robot_Location_bean(34242, 33300, 4));
		Giant_Field2.add(new Robot_Location_bean(34245, 33284, 4));
		Giant_Field2.add(new Robot_Location_bean(34261, 33276, 4));
		Giant_Field2.add(new Robot_Location_bean(34273, 33280, 4));
		Giant_Field2.add(new Robot_Location_bean(34279, 33271, 4));
		Giant_Field2.add(new Robot_Location_bean(34276, 33261, 4));
		Giant_Field2.add(new Robot_Location_bean(34276, 33248, 4));
		Giant_Field2.add(new Robot_Location_bean(34279, 33236, 4));
		Giant_Field2.add(new Robot_Location_bean(34278, 33222, 4));
		Giant_Field2.add(new Robot_Location_bean(34265, 33218, 4));
		Giant_Field2.add(new Robot_Location_bean(34258, 33209, 4));
		Giant_Field2.add(new Robot_Location_bean(34258, 33195, 4));
		Giant_Field2.add(new Robot_Location_bean(34265, 33177, 4));
		Giant_Field2.add(new Robot_Location_bean(34279, 33166, 4));
		Giant_Field2.add(new Robot_Location_bean(34272, 33152, 4));
		Giant_Field2.add(new Robot_Location_bean(34279, 33139, 4));
		Giant_Field2.add(new Robot_Location_bean(34278, 33128, 4));
		Giant_Field2.add(new Robot_Location_bean(34265, 33118, 4));
		Giant_Field2.add(new Robot_Location_bean(34256, 33131, 4));
		Giant_Field2.add(new Robot_Location_bean(34254, 33149, 4));
		Giant_Field2.add(new Robot_Location_bean(34259, 33165, 4));
		Giant_Field2.add(new Robot_Location_bean(34263, 33179, 4));
		Giant_Field2.add(new Robot_Location_bean(34259, 33191, 4));
		Giant_Field2.add(new Robot_Location_bean(34258, 33206, 4));
		Giant_Field2.add(new Robot_Location_bean(34261, 33217, 4));
		Giant_Field2.add(new Robot_Location_bean(34270, 33218, 4));
		
		Storm1.add(new Robot_Location_bean(34148, 32794, 15410));
		Storm1.add(new Robot_Location_bean(34153, 32798, 15410));
		Storm1.add(new Robot_Location_bean(34166, 32793, 15410));
		Storm1.add(new Robot_Location_bean(34176, 32792, 15410));
		Storm1.add(new Robot_Location_bean(34183, 32797, 15410));
		Storm1.add(new Robot_Location_bean(34183, 32797, 15410));
		Storm1.add(new Robot_Location_bean(34184, 32807, 15410));
		Storm1.add(new Robot_Location_bean(34175, 32807, 15410));
		Storm1.add(new Robot_Location_bean(34166, 32814, 15410));
		Storm1.add(new Robot_Location_bean(34157, 32824, 15410));
		Storm1.add(new Robot_Location_bean(34157, 32836, 15410));
		Storm1.add(new Robot_Location_bean(34151, 32847, 15410));
		Storm1.add(new Robot_Location_bean(34142, 32856, 15410));
		Storm1.add(new Robot_Location_bean(34142, 32868, 15410));
		Storm1.add(new Robot_Location_bean(34144, 32879, 15410));
		Storm1.add(new Robot_Location_bean(34144, 32879, 15410));
		Storm1.add(new Robot_Location_bean(34141, 32892, 15410));
		Storm1.add(new Robot_Location_bean(34131, 32889, 15410));
		Storm1.add(new Robot_Location_bean(34125, 32880, 15410));
		Storm1.add(new Robot_Location_bean(34117, 32867, 15410));
		Storm1.add(new Robot_Location_bean(34120, 32855, 15410));
		Storm1.add(new Robot_Location_bean(34126, 32847, 15410));
		Storm1.add(new Robot_Location_bean(34135, 32839, 15410));
		Storm1.add(new Robot_Location_bean(34136, 32826, 15410));
		Storm1.add(new Robot_Location_bean(34144, 32817, 15410));
		Storm1.add(new Robot_Location_bean(34160, 32820, 15410));
		
		
		Storm2.add(new Robot_Location_bean(34135, 32932, 15410));
		Storm2.add(new Robot_Location_bean(34143, 32926, 15410));
		Storm2.add(new Robot_Location_bean(34143, 32926, 15410));
		Storm2.add(new Robot_Location_bean(34152, 32922, 15410));
		Storm2.add(new Robot_Location_bean(34153, 32911, 15410));
		Storm2.add(new Robot_Location_bean(34161, 32906, 15410));
		Storm2.add(new Robot_Location_bean(34172, 32910, 15410));
		Storm2.add(new Robot_Location_bean(34175, 32920, 15410));
		Storm2.add(new Robot_Location_bean(34189, 32921, 15410));
		Storm2.add(new Robot_Location_bean(34185, 32934, 15410));
		Storm2.add(new Robot_Location_bean(34174, 32934, 15410));
		Storm2.add(new Robot_Location_bean(34170, 32928, 15410));
		Storm2.add(new Robot_Location_bean(34165, 32921, 15410));
		Storm2.add(new Robot_Location_bean(34156, 32926, 15410));
		Storm2.add(new Robot_Location_bean(34148, 32934, 15410));
		Storm2.add(new Robot_Location_bean(34139, 32941, 15410));
		
		
		Storm3.add(new Robot_Location_bean(34273, 32937, 15410));
		Storm3.add(new Robot_Location_bean(34272, 32928, 15410));
		Storm3.add(new Robot_Location_bean(34270, 32919, 15410));
		Storm3.add(new Robot_Location_bean(34268, 32910, 15410));
		Storm3.add(new Robot_Location_bean(34270, 32897, 15410));
		Storm3.add(new Robot_Location_bean(34270, 32889, 15410));
		Storm3.add(new Robot_Location_bean(34260, 32888, 15410));
		Storm3.add(new Robot_Location_bean(34250, 32901, 15410));
		Storm3.add(new Robot_Location_bean(34241, 32908, 15410));
		Storm3.add(new Robot_Location_bean(34239, 32917, 15410));
		Storm3.add(new Robot_Location_bean(34245, 32916, 15410));
		Storm3.add(new Robot_Location_bean(34249, 32925, 15410));
		Storm3.add(new Robot_Location_bean(34257, 32931, 15410));
		Storm3.add(new Robot_Location_bean(34266, 32933, 15410));
		
		
		Storm4.add(new Robot_Location_bean(34260, 32853, 15410));
		Storm4.add(new Robot_Location_bean(32429, 32862, 15410));
		Storm4.add(new Robot_Location_bean(34244, 32874, 15410));
		Storm4.add(new Robot_Location_bean(34250, 32885, 15410));
		Storm4.add(new Robot_Location_bean(34258, 32888, 15410));
		Storm4.add(new Robot_Location_bean(34268, 32885, 15410));
		Storm4.add(new Robot_Location_bean(34273, 32878, 15410));
		Storm4.add(new Robot_Location_bean(34271, 32871, 15410));
		Storm4.add(new Robot_Location_bean(34268, 32862, 15410));
		Storm4.add(new Robot_Location_bean(34261, 32858, 15410));
		Storm4.add(new Robot_Location_bean(34252, 32861, 15410));
		
		Storm5.add(new Robot_Location_bean(34274, 32786, 15410));
		Storm5.add(new Robot_Location_bean(34264, 32792, 15410));
		Storm5.add(new Robot_Location_bean(34256, 32793, 15410));
		Storm5.add(new Robot_Location_bean(34247, 32793, 15410));
		Storm5.add(new Robot_Location_bean(34238, 32794, 15410));
		Storm5.add(new Robot_Location_bean(34228, 32800, 15410));
		Storm5.add(new Robot_Location_bean(34228, 32811, 15410));
		Storm5.add(new Robot_Location_bean(34233, 32818, 15410));
		Storm5.add(new Robot_Location_bean(34239, 32823, 15410));
		Storm5.add(new Robot_Location_bean(34247, 32828, 15410));
		Storm5.add(new Robot_Location_bean(34257, 32831, 15410));
		Storm5.add(new Robot_Location_bean(34265, 32823, 15410));
		Storm5.add(new Robot_Location_bean(34269, 32803, 15410));
		Storm5.add(new Robot_Location_bean(34271, 32792, 15410));

		East1.add(new Robot_Location_bean(33587, 32367, 15440));
		East1.add(new Robot_Location_bean(33615, 32381, 15440));
		East1.add(new Robot_Location_bean(33628, 32378, 15440));
		East1.add(new Robot_Location_bean(33640, 32382, 15440));
		East1.add(new Robot_Location_bean(33650, 32381, 15440));
		East1.add(new Robot_Location_bean(33661, 32377, 15440));
		East1.add(new Robot_Location_bean(33659, 32364, 15440));
		East1.add(new Robot_Location_bean(33647, 32363, 15440));
		East1.add(new Robot_Location_bean(33641, 32352, 15440));
		East1.add(new Robot_Location_bean(33653, 32345, 15440));
		East1.add(new Robot_Location_bean(33665, 32333, 15440));
		East1.add(new Robot_Location_bean(33674, 32335, 15440));
		East1.add(new Robot_Location_bean(33682, 32343, 15440));
		East1.add(new Robot_Location_bean(33687, 32349, 15440));
		East1.add(new Robot_Location_bean(33687, 32358, 15440));
		East1.add(new Robot_Location_bean(33684, 32368, 15440));
		East1.add(new Robot_Location_bean(33679, 32378, 15440));
		East1.add(new Robot_Location_bean(33672, 32387, 15440));
		East1.add(new Robot_Location_bean(33663, 32395, 15440));
		East1.add(new Robot_Location_bean(33663, 32395, 15440));
		
		

		East2.add(new Robot_Location_bean(33738, 32396, 15440));
		East2.add(new Robot_Location_bean(33748, 32401, 15440));
		East2.add(new Robot_Location_bean(33760, 32387, 15440));
		East2.add(new Robot_Location_bean(33767, 32378, 15440));
		East2.add(new Robot_Location_bean(33770, 32365, 15440));
		East2.add(new Robot_Location_bean(33762, 32351, 15440));
		East2.add(new Robot_Location_bean(33767, 32332, 15440));
		East2.add(new Robot_Location_bean(33754, 32327, 15440));
		East2.add(new Robot_Location_bean(33744, 32313, 15440));
		East2.add(new Robot_Location_bean(33734, 32305, 15440));
		East2.add(new Robot_Location_bean(33743, 32290, 15440));
		East2.add(new Robot_Location_bean(33730, 32307, 15440));
		East2.add(new Robot_Location_bean(33726, 32322, 15440));
		East2.add(new Robot_Location_bean(33728, 32344, 15440));
		East2.add(new Robot_Location_bean(33724, 32361, 15440));
		East2.add(new Robot_Location_bean(33723, 32376, 15440));
		East2.add(new Robot_Location_bean(33714, 32394, 15440));
		East2.add(new Robot_Location_bean(33701, 32389, 15440));
		East2.add(new Robot_Location_bean(33690, 32392, 15440));

		
		
		East3.add(new Robot_Location_bean(33662, 32340, 15440));
		East3.add(new Robot_Location_bean(33672, 32333, 15440));
		East3.add(new Robot_Location_bean(33684, 32324, 15440));
		East3.add(new Robot_Location_bean(33695, 32331, 15440));
		East3.add(new Robot_Location_bean(33705, 32326, 15440));
		East3.add(new Robot_Location_bean(33709, 32309, 15440));
		East3.add(new Robot_Location_bean(33714, 32300, 15440));
		East3.add(new Robot_Location_bean(33716, 32286, 15440));
		East3.add(new Robot_Location_bean(33730, 32281, 15440));
		East3.add(new Robot_Location_bean(33731, 32266, 15440));
		East3.add(new Robot_Location_bean(33726, 32248, 15440));
		East3.add(new Robot_Location_bean(33729, 32263, 15440));
		East3.add(new Robot_Location_bean(33729, 32280, 15440));
		East3.add(new Robot_Location_bean(33719, 32281, 15440));
		East3.add(new Robot_Location_bean(33715, 32296, 15440));
		East3.add(new Robot_Location_bean(33703, 32300, 15440));
		East3.add(new Robot_Location_bean(33698, 32288, 15440));
		East3.add(new Robot_Location_bean(33690, 32284, 15440));
		East3.add(new Robot_Location_bean(33686, 32274, 15440));
		East3.add(new Robot_Location_bean(33684, 32262, 15440));
		East3.add(new Robot_Location_bean(33684, 32247, 15440));
		East3.add(new Robot_Location_bean(33679, 32239, 15440));
		East3.add(new Robot_Location_bean(33671, 32228, 15440));
		East3.add(new Robot_Location_bean(33652, 32223, 15440));
		East3.add(new Robot_Location_bean(33637, 32235, 15440));
		East3.add(new Robot_Location_bean(33625, 32226, 15440));
		East3.add(new Robot_Location_bean(33616, 32235, 15440));
		East3.add(new Robot_Location_bean(33611, 32248, 15440));
		East3.add(new Robot_Location_bean(33618, 32261, 15440));
		East3.add(new Robot_Location_bean(33619, 32275, 15440));
		East3.add(new Robot_Location_bean(33611, 32284, 15440));
		East3.add(new Robot_Location_bean(33597, 32276, 15440));
		East3.add(new Robot_Location_bean(33584, 32269, 15440));
		East3.add(new Robot_Location_bean(33578, 32259, 15440));
		East3.add(new Robot_Location_bean(33568, 32255, 15440));
		East3.add(new Robot_Location_bean(33560, 32264, 15440));
		East3.add(new Robot_Location_bean(33562, 32277, 15440));
		East3.add(new Robot_Location_bean(33559, 32287, 15440));
		East3.add(new Robot_Location_bean(33562, 32302, 15440));
		East3.add(new Robot_Location_bean(33573, 32315, 15440));
		East3.add(new Robot_Location_bean(33580, 32320, 15440));
		East3.add(new Robot_Location_bean(33580, 32334, 15440));
		East3.add(new Robot_Location_bean(33585, 32349, 15440));
		
		

		East4.add(new Robot_Location_bean(33662, 32340, 15440));
		East4.add(new Robot_Location_bean(33650, 32346, 15440));
		East4.add(new Robot_Location_bean(33641, 32351, 15440));
		East4.add(new Robot_Location_bean(33651, 32365, 15440));
		East4.add(new Robot_Location_bean(33660, 32369, 15440));
		East4.add(new Robot_Location_bean(33659, 32380, 15440));
		East4.add(new Robot_Location_bean(33645, 32383, 15440));
		East4.add(new Robot_Location_bean(33636, 32382, 15440));
		East4.add(new Robot_Location_bean(33625, 32379, 15440));
		East4.add(new Robot_Location_bean(33614, 32385, 15440));
		East4.add(new Robot_Location_bean(33612, 32398, 15440));
		East4.add(new Robot_Location_bean(33598, 32407, 15440));
		East4.add(new Robot_Location_bean(33587, 32400, 15440));
		East4.add(new Robot_Location_bean(33577, 32393, 15440));
		East4.add(new Robot_Location_bean(33581, 32376, 15440));
		East4.add(new Robot_Location_bean(33587, 32358, 15440));
		East4.add(new Robot_Location_bean(33582, 32347, 15440));
		East4.add(new Robot_Location_bean(33574, 32341, 15440));
		East4.add(new Robot_Location_bean(33562, 32331, 15440));
		East4.add(new Robot_Location_bean(33554, 32322, 15440));
		East4.add(new Robot_Location_bean(33549, 32312, 15440));
		East4.add(new Robot_Location_bean(33561, 32300, 15440));
		East4.add(new Robot_Location_bean(33560, 32286, 15440));
		East4.add(new Robot_Location_bean(33562, 32273, 15440));
		East4.add(new Robot_Location_bean(33560, 32261, 15440));
		East4.add(new Robot_Location_bean(33575, 32255, 15440));
		East4.add(new Robot_Location_bean(33585, 32270, 15440));
		East4.add(new Robot_Location_bean(33596, 32274, 15440));
		East4.add(new Robot_Location_bean(33607, 32278, 15440));
		East4.add(new Robot_Location_bean(33616, 32280, 15440));
		East4.add(new Robot_Location_bean(33621, 32267, 15440));
		East4.add(new Robot_Location_bean(33618, 32256, 15440));
		East4.add(new Robot_Location_bean(33611, 32248, 15440));
		East4.add(new Robot_Location_bean(33615, 32236, 15440));
		East4.add(new Robot_Location_bean(33625, 32226, 15440));
		East4.add(new Robot_Location_bean(33635, 32231, 15440));
		East4.add(new Robot_Location_bean(33638, 32242, 15440));
		East4.add(new Robot_Location_bean(33638, 32254, 15440));
		East4.add(new Robot_Location_bean(33646, 32266, 15440));
		East4.add(new Robot_Location_bean(33659, 32265, 15440));
		East4.add(new Robot_Location_bean(33652, 32243, 15440));
		East4.add(new Robot_Location_bean(33656, 32235, 15440));
		East4.add(new Robot_Location_bean(33668, 32229, 15440));
		East4.add(new Robot_Location_bean(33675, 32237, 15440));
		East4.add(new Robot_Location_bean(33683, 32247, 15440));
		East4.add(new Robot_Location_bean(33684, 32263, 15440));
		East4.add(new Robot_Location_bean(33687, 32272, 15440));
		East4.add(new Robot_Location_bean(33687, 32282, 15440));
		East4.add(new Robot_Location_bean(33695, 32287, 15440));
		East4.add(new Robot_Location_bean(33704, 32295, 15440));
		East4.add(new Robot_Location_bean(33705, 32301, 15440));
		East4.add(new Robot_Location_bean(33715, 32299, 15440));
		East4.add(new Robot_Location_bean(33718, 32281, 15440));
		East4.add(new Robot_Location_bean(33715, 32302, 15440));
		East4.add(new Robot_Location_bean(33708, 32313, 15440));
		East4.add(new Robot_Location_bean(33703, 32330, 15440));
		East4.add(new Robot_Location_bean(33694, 32331, 15440));
		East4.add(new Robot_Location_bean(33684, 32322, 15440));
		East4.add(new Robot_Location_bean(33674, 32333, 15440));
		East4.add(new Robot_Location_bean(33664, 32336, 15440));

		East5.add(new Robot_Location_bean(33738, 32396, 15440));
		East5.add(new Robot_Location_bean(33731, 32406, 15440));
		East5.add(new Robot_Location_bean(33722, 32416, 15440));
		East5.add(new Robot_Location_bean(33710, 32418, 15440));
		East5.add(new Robot_Location_bean(33700, 32415, 15440));
		East5.add(new Robot_Location_bean(33686, 32423, 15440));
		East5.add(new Robot_Location_bean(33676, 32423, 15440));
		East5.add(new Robot_Location_bean(33663, 32418, 15440));
		East5.add(new Robot_Location_bean(33651, 32414, 15440));
		East5.add(new Robot_Location_bean(33637, 32414, 15440));
		East5.add(new Robot_Location_bean(33627, 32406, 15440));
		East5.add(new Robot_Location_bean(33619, 32397, 15440));
		East5.add(new Robot_Location_bean(33613, 32387, 15440));
		East5.add(new Robot_Location_bean(33614, 32380, 15440));
		East5.add(new Robot_Location_bean(33625, 32379, 15440));
		East5.add(new Robot_Location_bean(33634, 32380, 15440));
		East5.add(new Robot_Location_bean(33642, 32381, 15440));
		East5.add(new Robot_Location_bean(33654, 32382, 15440));
		East5.add(new Robot_Location_bean(33661, 32373, 15440));
		East5.add(new Robot_Location_bean(33655, 32364, 15440));
		East5.add(new Robot_Location_bean(33644, 32360, 15440));
		East5.add(new Robot_Location_bean(33641, 32352, 15440));
		East5.add(new Robot_Location_bean(33652, 32345, 15440));
		East5.add(new Robot_Location_bean(33664, 32335, 15440));
		East5.add(new Robot_Location_bean(33661, 32328, 15440));
		East5.add(new Robot_Location_bean(33659, 32321, 15440));
		East5.add(new Robot_Location_bean(33655, 32311, 15440));
		East5.add(new Robot_Location_bean(33646, 32303, 15440));
		East5.add(new Robot_Location_bean(33656, 32296, 15440));
		East5.add(new Robot_Location_bean(33666, 32299, 15440));
		East5.add(new Robot_Location_bean(33672, 32303, 15440));
		East5.add(new Robot_Location_bean(33678, 32309, 15440));
		East5.add(new Robot_Location_bean(33682, 32317, 15440));
		East5.add(new Robot_Location_bean(33689, 32319, 15440));
		East5.add(new Robot_Location_bean(33692, 32329, 15440));
		East5.add(new Robot_Location_bean(33705, 32329, 15440));
		East5.add(new Robot_Location_bean(33706, 32316, 15440));
		East5.add(new Robot_Location_bean(33712, 32305, 15440));
		East5.add(new Robot_Location_bean(33716, 32290, 15440));
		East5.add(new Robot_Location_bean(33720, 32282, 15440));
		East5.add(new Robot_Location_bean(33732, 32281, 15440));
		East5.add(new Robot_Location_bean(33743, 32286, 15440));
		East5.add(new Robot_Location_bean(33750, 32292, 15440));
		East5.add(new Robot_Location_bean(33760, 32292, 15440));
		East5.add(new Robot_Location_bean(33763, 32302, 15440));
		East5.add(new Robot_Location_bean(33765, 32313, 15440));
		East5.add(new Robot_Location_bean(33768, 32322, 15440));
		East5.add(new Robot_Location_bean(33766, 32335, 15440));
		East5.add(new Robot_Location_bean(33763, 32350, 15440));
		East5.add(new Robot_Location_bean(33760, 32361, 15440));
		East5.add(new Robot_Location_bean(33755, 32374, 15440));
		East5.add(new Robot_Location_bean(33758, 32387, 15440));
		East5.add(new Robot_Location_bean(33752, 32396, 15440));
		East5.add(new Robot_Location_bean(33742, 32401, 15440));

		Underground_invasion_road_1st.add(new Robot_Location_bean(32761, 32825, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32765, 32833, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32766, 32845, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32771, 32856, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32785, 32854, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32799, 32844, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32814, 32839, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32821, 32824, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32835, 32815, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32850, 32813, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32862, 32803, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32877, 32805, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32893, 32805, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32907, 32811, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32918, 32824, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32925, 32840, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32925, 32856, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32922, 32872, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32921, 32888, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32922, 32904, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32913, 32913, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32900, 32916, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32891, 32916, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32877, 32918, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32868, 32915, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32868, 32905, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32859, 32899, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32852, 32894, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32843, 32893, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32834, 32891, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32825, 32892, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32817, 32891, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32810, 32891, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32800, 32883, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32798, 32875, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32796, 32868, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32786, 32869, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32776, 32872, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32766, 32867, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32758, 32860, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32752, 32858, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32743, 32858, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32748, 32847, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32756, 32839, 307));
		Underground_invasion_road_1st.add(new Robot_Location_bean(32759, 32831, 307));

		Underground_invasion_road_2st.add(new Robot_Location_bean(32761, 32825, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32761, 32835, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32757, 32845, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32762, 32854, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32767, 32861, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32769, 32872, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32779, 32872, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32794, 32868, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32800, 32869, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32815, 32865, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32827, 32867, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32837, 32868, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32848, 32862, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32854, 32862, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32862, 32861, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32867, 32863, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32880, 32862, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32890, 32858, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32900, 32851, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32911, 32847, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32914, 32857, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32921, 32860, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32932, 32856, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32925, 32844, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32925, 32834, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32925, 32826, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32917, 32822, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32906, 32819, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32908, 32812, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32904, 32804, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32896, 32804, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32889, 32806, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32881, 32806, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32870, 32802, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32857, 32805, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32847, 32806, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32837, 32811, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32833, 32818, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32822, 32824, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32816, 32833, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32809, 32841, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32797, 32846, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32787, 32852, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32778, 32858, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32771, 32856, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32765, 32846, 307));
		Underground_invasion_road_2st.add(new Robot_Location_bean(32763, 32835, 307));

		Underground_invasion_road_2nd.add(new Robot_Location_bean(32748, 32827, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32748, 32833, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32756, 32840, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32752, 32851, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32763, 32856, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32778, 32854, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32786, 32848, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32790, 32836, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32795, 32822, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32806, 32815, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32818, 32808, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32825, 32810, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32835, 32809, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32845, 32806, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32855, 32798, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32864, 32803, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32875, 32804, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32876, 32813, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32879, 32816, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32891, 32813, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32900, 32812, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32911, 32809, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32919, 32809, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32925, 32809, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32935, 32809, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32942, 32807, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32950, 32810, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32961, 32806, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32968, 32808, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32973, 32815, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32982, 32818, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32987, 32825, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32992, 32831, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32986, 32842, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32988, 32851, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32988, 32859, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32984, 32866, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32990, 32873, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32986, 32881, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32987, 32889, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32980, 32898, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32975, 32907, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32965, 32914, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32957, 32918, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32947, 32924, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32938, 32926, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32928, 32922, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32922, 32912, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32912, 32914, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32902, 32919, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32892, 32928, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32880, 32933, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32872, 32927, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32859, 32928, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32848, 32929, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32838, 32922, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32827, 32918, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32814, 32920, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32804, 32917, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32796, 32911, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32787, 32894, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32779, 32886, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32778, 32874, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32781, 32863, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32764, 32865, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32757, 32870, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32748, 32874, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32739, 32869, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32739, 32859, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32738, 32852, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32739, 32842, 308));
		Underground_invasion_road_2nd.add(new Robot_Location_bean(32746, 32835, 308));

		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32748, 32827, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32748, 32838, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32739, 32842, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32734, 32851, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32739, 32859, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32741, 32869, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32752, 32867, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32761, 32866, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32776, 32863, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32782, 32863, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32792, 32853, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32807, 32848, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32817, 32846, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32827, 32851, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32838, 32851, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32846, 32851, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32854, 32851, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32866, 32849, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32871, 32850, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32870, 32860, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32872, 32866, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32874, 32875, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32871, 32884, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32868, 32896, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32878, 32898, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32888, 32890, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32895, 32879, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32904, 32869, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32916, 32868, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32924, 32859, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32919, 32848, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32919, 32835, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32930, 32832, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32939, 32832, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32946, 32838, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32957, 32844, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32964, 32850, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32967, 32840, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32974, 32831, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32986, 32826, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32992, 32833, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32990, 32839, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32992, 32846, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32990, 32856, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32981, 32859, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32972, 32866, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32971, 32881, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32966, 32868, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32957, 32860, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32948, 32863, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32943, 32871, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32943, 32881, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32939, 32893, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32934, 32901, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32931, 32890, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32925, 32884, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32922, 32874, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32914, 32867, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32908, 32867, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32900, 32874, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32892, 32882, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32886, 32891, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32875, 32897, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32868, 32899, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32869, 32890, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32872, 32880, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32875, 32871, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32872, 32862, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32866, 32857, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32857, 32858, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32850, 32859, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32841, 32859, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32829, 32859, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32819, 32866, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32811, 32875, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32807, 32888, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32797, 32896, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32785, 32892, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32777, 32884, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32780, 32869, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32782, 32856, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32774, 32855, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32764, 32857, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32756, 32851, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32757, 32838, 308));
		Underground_invasion_road_2nd_floor2.add(new Robot_Location_bean(32749, 32835, 308));

		Underground_invasion_road_3rd.add(new Robot_Location_bean(32758, 32840, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32767, 32846, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32776, 32842, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32783, 32845, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32794, 32840, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32797, 32827, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32798, 32816, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32796, 32807, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32806, 32805, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32812, 32809, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32818, 32813, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32827, 32818, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32836, 32811, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32844, 32806, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32855, 32803, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32855, 32816, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32858, 32826, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32868, 32831, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32880, 32824, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32890, 32820, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32898, 32827, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32907, 32825, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32917, 32814, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32923, 32806, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32933, 32809, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32944, 32807, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32955, 32804, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32969, 32804, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32978, 32805, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32988, 32802, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33000, 32801, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33007, 32805, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33018, 32808, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33031, 32804, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33038, 32812, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33043, 32820, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33043, 32830, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33043, 32842, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33046, 32850, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33049, 32859, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33051, 32869, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33048, 32881, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33046, 32893, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33035, 32899, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33026, 32906, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33015, 32908, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(33001, 32908, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32991, 32902, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32991, 32890, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32986, 32882, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32978, 32877, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32968, 32884, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32958, 32886, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32949, 32883, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32953, 32873, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32957, 32864, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32951, 32855, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32953, 32842, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32942, 32846, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32934, 32857, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32929, 32866, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32920, 32860, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32915, 32851, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32902, 32850, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32891, 32852, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32881, 32849, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32877, 32860, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32869, 32870, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32857, 32875, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32846, 32872, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32836, 32873, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32829, 32879, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32821, 32885, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32815, 32889, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32809, 32899, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32799, 32907, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32790, 32910, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32786, 32903, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32777, 32911, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32770, 32915, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32759, 32916, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32752, 32911, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32753, 32898, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32752, 32885, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32751, 32877, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32741, 32871, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32736, 32864, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32741, 32852, 309));
		Underground_invasion_road_3rd.add(new Robot_Location_bean(32749, 32845, 309));

		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32758, 32840, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32750, 32843, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32744, 32853, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32748, 32859, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32744, 32869, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32746, 32879, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32752, 32885, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32757, 32890, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32754, 32896, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32752, 32908, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32756, 32915, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32769, 32916, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32781, 32914, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32792, 32909, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32801, 32906, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32811, 32899, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32824, 32900, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32823, 32908, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32821, 32921, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32827, 32927, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32834, 32922, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32845, 32916, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32852, 32917, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32857, 32924, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32866, 32925, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32875, 32918, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32883, 32911, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32889, 32913, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32895, 32919, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32902, 32924, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32912, 32922, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32923, 32913, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32933, 32912, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32945, 32909, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32951, 32910, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32957, 32915, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32960, 32924, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32960, 32932, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32963, 32938, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32972, 32936, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32983, 32934, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32994, 32931, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33006, 32930, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33017, 32926, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33028, 32927, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33032, 32923, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33028, 32914, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33031, 32901, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33029, 32892, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33025, 32884, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33018, 32875, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33020, 32860, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33010, 32856, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(33001, 32850, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32997, 32865, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32989, 32869, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32979, 32875, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32970, 32882, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32961, 32887, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32952, 32887, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32951, 32879, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32955, 32866, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32953, 32855, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32954, 32842, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32942, 32846, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32935, 32855, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32929, 32865, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32922, 32862, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32916, 32854, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32915, 32847, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32920, 32835, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32913, 32829, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32902, 32825, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32894, 32831, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32888, 32841, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32881, 32846, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32878, 32856, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32875, 32864, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32867, 32872, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32857, 32872, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32847, 32872, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32833, 32875, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32823, 32871, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32812, 32868, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32801, 32874, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32793, 32872, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32784, 32878, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32778, 32885, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32773, 32890, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32762, 32887, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32752, 32887, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32746, 32879, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32756, 32871, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32764, 32862, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32765, 32852, 309));
		Underground_invasion_road_3rd_floor2.add(new Robot_Location_bean(32762, 32844, 309));

		Eva_1st_Floor.add(new Robot_Location_bean(32710, 32788, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32716, 32793, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32721, 32796, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32731, 32796, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32727, 32810, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32725, 32818, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32722, 32825, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32726, 32831, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32724, 32838, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32724, 32844, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32721, 32852, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32715, 32859, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32702, 32863, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32703, 32873, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32695, 32870, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32689, 32869, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32683, 32868, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32679, 32864, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32673, 32864, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32668, 32868, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32664, 32859, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32664, 32854, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32663, 32847, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32667, 32841, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32674, 32844, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32675, 32834, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32668, 32834, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32663, 32832, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32668, 32827, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32676, 32826, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32680, 32816, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32679, 32806, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32684, 32802, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32695, 32801, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32705, 32800, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32709, 32800, 59));
		Eva_1st_Floor.add(new Robot_Location_bean(32710, 32793, 59));

		Eva_1st_Floor2.add(new Robot_Location_bean(32710, 32788, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32710, 32793, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32709, 32800, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32703, 32802, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32695, 32802, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32688, 32802, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32679, 32803, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32678, 32810, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32679, 32817, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32667, 32828, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32664, 32832, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32669, 32834, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32675, 32835, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32674, 32844, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32669, 32850, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32676, 32850, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32677, 32859, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32680, 32866, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32684, 32868, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32690, 32869, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32696, 32870, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32703, 32872, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32705, 32863, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32713, 32860, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32721, 32851, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32729, 32850, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32735, 32851, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32741, 32843, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32747, 32839, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32747, 32828, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32745, 32818, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32739, 32815, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32733, 32818, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32732, 32825, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32726, 32825, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32722, 32823, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32724, 32811, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32728, 32805, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32731, 32797, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32723, 32796, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32719, 32792, 59));
		Eva_1st_Floor2.add(new Robot_Location_bean(32711, 32792, 59));

		Eva_2nd_Floor.add(new Robot_Location_bean(32746, 32861, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32734, 32862, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32724, 32859, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32715, 32852, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32708, 32853, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32702, 32855, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32699, 32849, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32691, 32846, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32689, 32838, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32680, 32837, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32672, 32832, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32677, 32824, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32678, 32813, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32684, 32805, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32691, 32804, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32697, 32808, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32703, 32803, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32706, 32794, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32717, 32793, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32729, 32793, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32740, 32792, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32740, 32799, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32736, 32808, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32737, 32818, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32734, 32829, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32738, 32819, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32735, 32810, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32724, 32809, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32717, 32812, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32709, 32820, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32704, 32823, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32696, 32832, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32693, 32835, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32686, 32837, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32686, 32846, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32696, 32846, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32702, 32854, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32712, 32853, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32722, 32855, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32730, 32858, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32735, 32862, 60));
		Eva_2nd_Floor.add(new Robot_Location_bean(32742, 32862, 60));

		Eva_2nd_Floor2.add(new Robot_Location_bean(32746, 32861, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32738, 32863, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32729, 32862, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32729, 32855, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32723, 32856, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32716, 32852, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32701, 32850, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32696, 32846, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32691, 32838, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32698, 32826, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32708, 32821, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32718, 32813, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32716, 32805, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32705, 32800, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32698, 32809, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32688, 32804, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32680, 32808, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32677, 32812, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32677, 32824, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32671, 32833, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32674, 32842, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32685, 32837, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32693, 32835, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32700, 32838, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32701, 32843, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32702, 32850, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32710, 32853, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32717, 32852, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32724, 32857, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32729, 32857, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32735, 32862, 60));
		Eva_2nd_Floor2.add(new Robot_Location_bean(32742, 32862, 60));

		Eva_3rd_Floor.add(new Robot_Location_bean(32727, 32808, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32719, 32804, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32714, 32796, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32708, 32791, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32698, 32789, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32690, 32795, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32678, 32790, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32676, 32801, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32674, 32809, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32678, 32817, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32674, 32831, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32677, 32847, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32681, 32854, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32677, 32863, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32668, 32873, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32679, 32873, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32691, 32861, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32700, 32869, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32714, 32866, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32724, 32871, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32732, 32863, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32741, 32854, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32746, 32841, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32741, 32833, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32739, 32825, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32739, 32820, 61));
		Eva_3rd_Floor.add(new Robot_Location_bean(32731, 32817, 61));

		Eva_3rd_Floor2.add(new Robot_Location_bean(32727, 32808, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32730, 32818, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32739, 32822, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32734, 32831, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32729, 32842, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32724, 32852, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32728, 32863, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32731, 32870, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32722, 32872, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32716, 32864, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32709, 32852, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32699, 32847, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32696, 32837, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32691, 32835, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32690, 32828, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32686, 32819, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32685, 32806, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32680, 32796, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32676, 32790, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32686, 32790, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32695, 32795, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32708, 32792, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32717, 32798, 61));
		Eva_3rd_Floor2.add(new Robot_Location_bean(32721, 32807, 61));

		Eva_4th_Floor.add(new Robot_Location_bean(32805, 32870, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32796, 32875, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32789, 32871, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32771, 32868, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32773, 32858, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32784, 32852, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32789, 32839, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32799, 32828, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32795, 32819, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32782, 32815, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32774, 32816, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32775, 32808, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32771, 32800, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32783, 32797, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32795, 32794, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32801, 32783, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32804, 32770, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32810, 32754, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32810, 32738, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32823, 32726, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32818, 32712, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32804, 32717, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32794, 32707, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32780, 32710, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32763, 32717, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32748, 32712, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32735, 32703, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32721, 32713, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32706, 32707, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32693, 32708, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32682, 32717, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32669, 32716, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32660, 32721, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32652, 32732, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32647, 32744, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32648, 32757, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32651, 32768, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32646, 32778, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32644, 32790, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32648, 32797, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32648, 32809, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32648, 32821, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32649, 32834, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32648, 32845, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32648, 32861, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32647, 32871, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32657, 32878, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32660, 32889, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32673, 32888, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32681, 32885, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32691, 32881, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32705, 32882, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32715, 32880, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32725, 32883, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32736, 32877, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32743, 32886, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32753, 32888, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32762, 32895, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32772, 32889, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32784, 32882, 63));
		Eva_4th_Floor.add(new Robot_Location_bean(32797, 32878, 63));

		Eva_4th_Floor2.add(new Robot_Location_bean(32805, 32870, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32795, 32878, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32783, 32881, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32775, 32887, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32759, 32894, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32750, 32888, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32742, 32885, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32733, 32877, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32732, 32864, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32719, 32870, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32713, 32860, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32715, 32848, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32709, 32841, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32697, 32839, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32695, 32828, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32691, 32818, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32688, 32808, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32692, 32798, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32694, 32787, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32686, 32783, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32687, 32775, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32702, 32769, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32714, 32757, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32725, 32750, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32737, 32746, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32740, 32736, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32739, 32728, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32742, 32718, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32741, 32708, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32742, 32697, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32754, 32705, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32758, 32713, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32770, 32713, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32780, 32708, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32790, 32700, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32797, 32690, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32803, 32701, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32805, 32708, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32808, 32718, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32822, 32714, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32833, 32710, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32836, 32722, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32835, 32737, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32834, 32746, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32838, 32757, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32840, 32767, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32845, 32779, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32836, 32792, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32820, 32795, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32817, 32806, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32810, 32817, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32802, 32826, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32800, 32836, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32807, 32843, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32825, 32845, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32833, 32854, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32834, 32867, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32826, 32877, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32828, 32886, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32824, 32895, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32817, 32896, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32807, 32896, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32801, 32889, 63));
		Eva_4th_Floor2.add(new Robot_Location_bean(32796, 32881, 63));

		Heine_Grass_Field.add(new Robot_Location_bean(33403, 33415, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33409, 33406, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33414, 33394, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33423, 33387, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33428, 33380, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33430, 33372, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33431, 33362, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33438, 33354, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33442, 33344, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33439, 33338, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33436, 33329, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33441, 33321, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33444, 33313, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33446, 33305, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33452, 33297, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33461, 33291, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33467, 33283, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33474, 33287, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33477, 33290, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33485, 33287, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33492, 33284, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33500, 33283, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33509, 33283, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33514, 33275, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33517, 33265, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33525, 33263, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33532, 33260, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33538, 33257, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33544, 33254, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33545, 33244, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33546, 33236, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33552, 33233, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33562, 33231, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33561, 33222, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33559, 33214, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33561, 33205, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33567, 33198, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33567, 33190, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33562, 33185, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33568, 33179, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33572, 33171, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33575, 33165, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33579, 33159, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33575, 33156, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33572, 33151, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33569, 33147, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33564, 33138, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33557, 33131, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33556, 33121, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33559, 33110, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33553, 33104, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33553, 33095, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33549, 33089, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33540, 33087, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33531, 33092, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33520, 33092, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33513, 33101, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33504, 33104, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33495, 33111, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33487, 33114, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33479, 33109, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33472, 33107, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33468, 33101, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33457, 33102, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33453, 33110, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33452, 33118, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33452, 33130, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33443, 33135, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33442, 33141, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33435, 33147, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33426, 33146, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33423, 33150, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33421, 33160, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33416, 33168, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33411, 33177, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33411, 33188, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33411, 33196, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33412, 33203, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33414, 33213, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33414, 33220, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33406, 33223, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33402, 33232, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33395, 33239, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33392, 33247, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33388, 33254, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33386, 33261, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33387, 33271, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33386, 33275, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33384, 33282, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33382, 33288, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33386, 33291, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33390, 33296, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33392, 33300, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33398, 33302, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33398, 33307, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33396, 33314, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33397, 33323, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33397, 33331, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33401, 33337, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33400, 33345, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33396, 33352, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33397, 33359, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33401, 33364, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33402, 33372, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33401, 33379, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33402, 33386, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33398, 33394, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33401, 33402, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33402, 33407, 4));
		Heine_Grass_Field.add(new Robot_Location_bean(33401, 33413, 4));

		Heine_Grass_Field2.add(new Robot_Location_bean(33403, 33415, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33397, 33409, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33393, 33402, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33394, 33395, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33395, 33388, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33395, 33381, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33398, 33375, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33400, 33367, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33399, 33360, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33396, 33353, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33397, 33345, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33403, 33338, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33410, 33334, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33415, 33327, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33422, 33323, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33428, 33321, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33432, 33314, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33438, 33305, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33441, 33297, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33438, 33290, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33442, 33280, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33447, 33273, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33451, 33266, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33449, 33258, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33442, 33250, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33445, 33241, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33449, 33234, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33441, 33229, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33431, 33230, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33423, 33228, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33415, 33226, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33408, 33218, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33400, 33216, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33391, 33214, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33384, 33208, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33386, 33200, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33388, 33186, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33400, 33178, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33410, 33166, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33414, 33156, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33422, 33148, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33430, 33139, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33436, 33126, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33445, 33119, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33451, 33109, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33458, 33099, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33469, 33094, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33474, 33097, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33482, 33101, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33494, 33104, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33503, 33113, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33509, 33119, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33515, 33125, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33515, 33133, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33517, 33137, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33524, 33141, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33535, 33146, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33545, 33137, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33554, 33127, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33565, 33126, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33573, 33133, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33580, 33140, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33591, 33141, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33596, 33149, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33600, 33159, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33606, 33166, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33599, 33175, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33587, 33182, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33581, 33190, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33573, 33200, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33561, 33209, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33556, 33222, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33549, 33236, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33552, 33245, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33547, 33255, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33543, 33267, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33529, 33280, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33521, 33288, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33509, 33293, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33504, 33303, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33505, 33311, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33499, 33324, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33489, 33332, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33481, 33339, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33469, 33344, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33462, 33351, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33461, 33360, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33461, 33370, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33452, 33377, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33440, 33376, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33434, 33385, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33426, 33389, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33415, 33393, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33404, 33399, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33396, 33405, 4));
		Heine_Grass_Field2.add(new Robot_Location_bean(33394, 33415, 4));

		Heine_Grass_Field3.add(new Robot_Location_bean(33437, 33209, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33442, 33223, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33449, 33232, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33458, 33239, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33466, 33247, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33474, 33254, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33483, 33261, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33492, 33268, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33501, 33280, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33498, 33291, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33486, 33300, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33479, 33307, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33473, 33306, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33463, 33310, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33449, 33311, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33439, 33307, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33432, 33295, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33418, 33293, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33413, 33287, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33413, 33278, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33416, 33268, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33417, 33257, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33413, 33248, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33409, 33238, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33403, 33230, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33403, 33218, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33402, 33208, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33408, 33195, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33415, 33185, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33423, 33175, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33430, 33163, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33439, 33153, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33448, 33144, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33458, 33139, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33467, 33130, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33479, 33119, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33490, 33113, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33500, 33104, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33510, 33102, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33520, 33104, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33528, 33096, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33539, 33090, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33550, 33091, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33559, 33096, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33563, 33105, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33574, 33106, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33584, 33114, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33591, 33120, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33592, 33130, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33589, 33141, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33588, 33150, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33581, 33161, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33572, 33166, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33557, 33169, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33543, 33172, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33534, 33163, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33525, 33157, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33509, 33156, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33499, 33158, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33489, 33162, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33480, 33166, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33470, 33173, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33459, 33181, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33451, 33189, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33449, 33198, 4));
		Heine_Grass_Field3.add(new Robot_Location_bean(33445, 33208, 4));

		Heine_Grass_Field4.add(new Robot_Location_bean(33495, 33103, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33484, 33100, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33475, 33097, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33470, 33090, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33461, 33084, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33453, 33088, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33444, 33093, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33435, 33099, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33428, 33109, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33417, 33115, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33415, 33123, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33402, 33126, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33393, 33130, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33386, 33135, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33379, 33142, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33371, 33140, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33363, 33137, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33353, 33136, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33345, 33131, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33335, 33135, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33327, 33142, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33320, 33152, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33315, 33161, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33310, 33171, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33314, 33182, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33322, 33185, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33326, 33195, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33335, 33190, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33343, 33195, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33343, 33204, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33342, 33209, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33336, 33220, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33336, 33234, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33339, 33243, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33348, 33248, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33358, 33250, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33354, 33259, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33346, 33269, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33335, 33274, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33331, 33282, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33321, 33287, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33312, 33285, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33308, 33276, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33301, 33269, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33292, 33264, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33295, 33257, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33288, 33255, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33279, 33255, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33275, 33266, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33280, 33271, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33283, 33279, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33286, 33286, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33291, 33292, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33297, 33299, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33300, 33307, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33303, 33312, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33311, 33319, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33316, 33326, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33322, 33331, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33325, 33340, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33324, 33349, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33325, 33357, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33332, 33359, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33340, 33360, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33348, 33363, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33354, 33369, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33364, 33366, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33370, 33357, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33372, 33345, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33379, 33336, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33382, 33327, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33382, 33315, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33383, 33307, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33382, 33298, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33382, 33289, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33379, 33283, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33383, 33275, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33384, 33267, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33377, 33262, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33377, 33254, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33376, 33246, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33383, 33237, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33383, 33226, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33388, 33216, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33395, 33209, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33395, 33203, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33395, 33194, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33396, 33184, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33400, 33177, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33410, 33169, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33418, 33161, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33428, 33154, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33442, 33146, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33450, 33141, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33459, 33137, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33468, 33132, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33478, 33122, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33488, 33117, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33499, 33115, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33507, 33109, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33516, 33106, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33527, 33107, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33537, 33106, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33545, 33107, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33552, 33104, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33559, 33103, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33567, 33104, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33575, 33099, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33582, 33090, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33589, 33081, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33596, 33071, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33598, 33062, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33600, 33051, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33595, 33039, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33584, 33033, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33575, 33037, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33566, 33034, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33554, 33033, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33543, 33037, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33534, 33036, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33524, 33038, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33516, 33039, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33508, 33047, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33499, 33054, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33497, 33061, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33491, 33071, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33482, 33074, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33473, 33079, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33463, 33084, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33455, 33087, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33451, 33097, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33454, 33106, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33454, 33115, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33454, 33126, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33456, 33136, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33466, 33131, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33471, 33124, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33479, 33115, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33488, 33112, 4));
		Heine_Grass_Field4.add(new Robot_Location_bean(33498, 33103, 4));

		Heine_Grass_Field5.add(new Robot_Location_bean(33347, 33219, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33354, 33214, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33362, 33211, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33372, 33210, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33380, 33212, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33387, 33212, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33395, 33220, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33403, 33225, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33412, 33221, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33420, 33218, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33427, 33211, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33435, 33210, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33442, 33212, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33446, 33205, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33450, 33194, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33454, 33183, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33461, 33175, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33467, 33167, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33475, 33159, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33474, 33150, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33468, 33142, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33460, 33139, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33457, 33132, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33450, 33131, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33443, 33134, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33434, 33134, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33424, 33137, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33418, 33131, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33412, 33127, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33403, 33132, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33394, 33133, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33385, 33135, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33376, 33142, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33371, 33152, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33369, 33165, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33365, 33177, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33359, 33183, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33361, 33193, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33369, 33194, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33376, 33193, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33382, 33195, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33387, 33187, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33391, 33190, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33394, 33193, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33391, 33201, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33396, 33207, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33394, 33213, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33382, 33220, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33366, 33219, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33356, 33227, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33352, 33236, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33352, 33243, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33343, 33246, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33338, 33237, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33333, 33231, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33331, 33224, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33328, 33218, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33330, 33208, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33340, 33199, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33348, 33191, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33357, 33188, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33364, 33192, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33368, 33203, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33364, 33216, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33357, 33226, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33354, 33233, 4));
		Heine_Grass_Field5.add(new Robot_Location_bean(33349, 33226, 4));
		
		
		Oman_10th_Floor1.add(new Robot_Location_bean(32757, 32781, 110));
		Oman_10th_Floor1.add(new Robot_Location_bean(32751, 32776, 110));
		Oman_10th_Floor1.add(new Robot_Location_bean(32737, 32775, 110));		
		Oman_10th_Floor1.add(new Robot_Location_bean(32756, 32764, 110));
		Oman_10th_Floor1.add(new Robot_Location_bean(32765, 32763, 110));
		Oman_10th_Floor1.add(new Robot_Location_bean(32764, 32788, 110));
		Oman_10th_Floor1.add(new Robot_Location_bean(32756, 32788, 110));
		
		Oman_10th_Floor2.add(new Robot_Location_bean(32757, 32822, 110));
		Oman_10th_Floor2.add(new Robot_Location_bean(32749, 32814, 110));
		Oman_10th_Floor2.add(new Robot_Location_bean(32731, 32816, 110));		
		Oman_10th_Floor2.add(new Robot_Location_bean(32744, 32839, 110));
		Oman_10th_Floor2.add(new Robot_Location_bean(32760, 32840, 110));
		Oman_10th_Floor2.add(new Robot_Location_bean(32764, 32816, 110));
		Oman_10th_Floor2.add(new Robot_Location_bean(32758, 32810, 110));
		
		Oman_10th_Floor3.add(new Robot_Location_bean(32775, 32810, 110));
		Oman_10th_Floor3.add(new Robot_Location_bean(32789, 32804, 110));
		Oman_10th_Floor3.add(new Robot_Location_bean(32791, 32796, 110));		
		Oman_10th_Floor3.add(new Robot_Location_bean(32800, 32788, 110));
		Oman_10th_Floor3.add(new Robot_Location_bean(32811, 32793, 110));
		Oman_10th_Floor3.add(new Robot_Location_bean(32805, 32807, 110));
		Oman_10th_Floor3.add(new Robot_Location_bean(32788, 32815, 110));
		
		
		Oman_10th_Floor4.add(new Robot_Location_bean(32747, 32851, 110));
		Oman_10th_Floor4.add(new Robot_Location_bean(32759, 32853, 110));
		Oman_10th_Floor4.add(new Robot_Location_bean(32769, 32858, 110));		
		Oman_10th_Floor4.add(new Robot_Location_bean(32784, 32861, 110));
		Oman_10th_Floor4.add(new Robot_Location_bean(32781, 32844, 110));
		Oman_10th_Floor4.add(new Robot_Location_bean(32757, 32848, 110));
		Oman_10th_Floor4.add(new Robot_Location_bean(32748, 32842, 110));
		
		Oman_10th_Floor5.add(new Robot_Location_bean(32798, 32849, 110));
		Oman_10th_Floor5.add(new Robot_Location_bean(32800, 32862, 110));
		Oman_10th_Floor5.add(new Robot_Location_bean(32809, 32871, 110));		
		Oman_10th_Floor5.add(new Robot_Location_bean(32815, 32863, 110));
		Oman_10th_Floor5.add(new Robot_Location_bean(32814, 32850, 110));
		Oman_10th_Floor5.add(new Robot_Location_bean(32828, 32844, 110));
		Oman_10th_Floor5.add(new Robot_Location_bean(32811, 32873, 110));
		
		Oman_10th_Floor6.add(new Robot_Location_bean(32864, 32861, 110));
		Oman_10th_Floor6.add(new Robot_Location_bean(32852, 32850, 110));
		Oman_10th_Floor6.add(new Robot_Location_bean(32852, 32838, 110));		
		Oman_10th_Floor6.add(new Robot_Location_bean(32838, 32830, 110));
		Oman_10th_Floor6.add(new Robot_Location_bean(32841, 32847, 110));
		
		Oman_10th_Floor7.add(new Robot_Location_bean(32835, 32744, 110));
		Oman_10th_Floor7.add(new Robot_Location_bean(32812, 32744, 110));
		Oman_10th_Floor7.add(new Robot_Location_bean(32816, 32758, 110));		
		Oman_10th_Floor7.add(new Robot_Location_bean(32820, 32771, 110));
		Oman_10th_Floor7.add(new Robot_Location_bean(32842, 32759, 110));
		Oman_10th_Floor7.add(new Robot_Location_bean(32837, 32742, 110));
		
		
		Oman_9th_Floor1.add(new Robot_Location_bean(32613, 32866, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32622, 32854, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32622, 32841, 109));	
		Oman_9th_Floor1.add(new Robot_Location_bean(32612, 32835, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32615, 32820, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32617, 32810, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32630, 32810, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32634, 32831, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32646, 32835, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32637, 32845, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32630, 32858, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32624, 32869, 109));
		Oman_9th_Floor1.add(new Robot_Location_bean(32620, 32864, 109));
		
		Oman_9th_Floor2.add(new Robot_Location_bean(32655, 32796, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32671, 32799, 109));	
		Oman_9th_Floor2.add(new Robot_Location_bean(32686, 32799, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32698, 32798, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32698, 32813, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32690, 32837, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32688, 32849, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32688, 32849, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32685, 32868, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32675, 32869, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32665, 32869, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32658, 32849, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32657, 32838, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32654, 32827, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32654, 32812, 109));
		Oman_9th_Floor2.add(new Robot_Location_bean(32655, 32798, 109));
		
		Oman_9th_Floor3.add(new Robot_Location_bean(32730, 32826, 109));	
		Oman_9th_Floor3.add(new Robot_Location_bean(32739, 32831, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32744, 32836, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32749, 32842, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32743, 32850, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32730, 32859, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32730, 32870, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32727, 32890, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32715, 32892, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32702, 32881, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32706, 32861, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32703, 32842, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32701, 32827, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32714, 32824, 109));
		Oman_9th_Floor3.add(new Robot_Location_bean(32727, 32837, 109));
		
		
		
		Oman_9th_Floor4.add(new Robot_Location_bean(32653, 32931, 109));
		Oman_9th_Floor4.add(new Robot_Location_bean(32667, 32920, 109));
		Oman_9th_Floor4.add(new Robot_Location_bean(32669, 32912, 109));
		Oman_9th_Floor4.add(new Robot_Location_bean(32658, 32906, 109));
		Oman_9th_Floor4.add(new Robot_Location_bean(32658, 32892, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32651, 32890, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32639, 32879, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32640, 32873, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32650, 32873, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32657, 32875, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32657, 32892, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32670, 32891, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32671, 32902, 109));	
		Oman_9th_Floor4.add(new Robot_Location_bean(32674, 32921, 109));
		Oman_9th_Floor4.add(new Robot_Location_bean(32684, 32922, 109));
		Oman_9th_Floor4.add(new Robot_Location_bean(32667, 32921, 109));
		
		
		Oman_9th_Floor5.add(new Robot_Location_bean(32636, 32932, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32632, 32923, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32629, 32916, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32616, 32917, 109));	
		Oman_9th_Floor5.add(new Robot_Location_bean(32610, 32914, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32609, 32898, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32603, 32893, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32619, 32892, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32622, 32881, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32621, 32869, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32624, 32886, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32624, 32886, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32627, 32902, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32639, 32903, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32651, 32902, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32663, 32909, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32655, 32916, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32646, 32916, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32630, 32917, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32631, 32924, 109));
		Oman_9th_Floor5.add(new Robot_Location_bean(32636, 32930, 109));
		
		
		
		
		Oman_9th_Floor6.add(new Robot_Location_bean(32637, 32845, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32630, 32858, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32624, 32869, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32620, 32864, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32613, 32866, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32622, 32854, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32622, 32841, 109));	
		Oman_9th_Floor6.add(new Robot_Location_bean(32612, 32835, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32615, 32820, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32617, 32810, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32630, 32810, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32634, 32831, 109));
		Oman_9th_Floor6.add(new Robot_Location_bean(32646, 32835, 109));
		
		
		Oman_9th_Floor7.add(new Robot_Location_bean(32665, 32869, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32658, 32849, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32657, 32838, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32654, 32827, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32654, 32812, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32655, 32798, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32655, 32796, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32671, 32799, 109));	
		Oman_9th_Floor7.add(new Robot_Location_bean(32686, 32799, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32698, 32798, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32698, 32813, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32690, 32837, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32688, 32849, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32688, 32849, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32685, 32868, 109));
		Oman_9th_Floor7.add(new Robot_Location_bean(32675, 32869, 109));
		
		
		Oman_9th_Floor8.add(new Robot_Location_bean(32703, 32842, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32701, 32827, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32714, 32824, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32727, 32837, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32730, 32826, 109));	
		Oman_9th_Floor8.add(new Robot_Location_bean(32739, 32831, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32744, 32836, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32749, 32842, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32743, 32850, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32730, 32859, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32730, 32870, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32727, 32890, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32715, 32892, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32702, 32881, 109));
		Oman_9th_Floor8.add(new Robot_Location_bean(32706, 32861, 109));
		
		
		Oman_9th_Floor9.add(new Robot_Location_bean(32671, 32902, 109));	
		Oman_9th_Floor9.add(new Robot_Location_bean(32674, 32921, 109));
		Oman_9th_Floor9.add(new Robot_Location_bean(32684, 32922, 109));
		Oman_9th_Floor9.add(new Robot_Location_bean(32667, 32921, 109));
		Oman_9th_Floor9.add(new Robot_Location_bean(32653, 32931, 109));
		Oman_9th_Floor9.add(new Robot_Location_bean(32667, 32920, 109));
		Oman_9th_Floor9.add(new Robot_Location_bean(32669, 32912, 109));
		Oman_9th_Floor9.add(new Robot_Location_bean(32658, 32906, 109));
		Oman_9th_Floor9.add(new Robot_Location_bean(32658, 32892, 109));	
		Oman_9th_Floor9.add(new Robot_Location_bean(32651, 32890, 109));	
		Oman_9th_Floor9.add(new Robot_Location_bean(32639, 32879, 109));	
		Oman_9th_Floor9.add(new Robot_Location_bean(32640, 32873, 109));	
		Oman_9th_Floor9.add(new Robot_Location_bean(32650, 32873, 109));	
		Oman_9th_Floor9.add(new Robot_Location_bean(32657, 32875, 109));	
		Oman_9th_Floor9.add(new Robot_Location_bean(32657, 32892, 109));	
		Oman_9th_Floor9.add(new Robot_Location_bean(32670, 32891, 109));	
		
		
		Oman_9th_Floor10.add(new Robot_Location_bean(32655, 32916, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32646, 32916, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32630, 32917, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32631, 32924, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32636, 32930, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32636, 32932, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32632, 32923, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32629, 32916, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32616, 32917, 109));	
		Oman_9th_Floor10.add(new Robot_Location_bean(32610, 32914, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32609, 32898, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32603, 32893, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32619, 32892, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32622, 32881, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32621, 32869, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32624, 32886, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32624, 32886, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32627, 32902, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32639, 32903, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32651, 32902, 109));
		Oman_9th_Floor10.add(new Robot_Location_bean(32663, 32909, 109));
		
		Oman_8th_Floor1.add(new Robot_Location_bean(32614, 32836, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32606, 32830, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32601, 32822, 108));	
		Oman_8th_Floor1.add(new Robot_Location_bean(32605, 32806, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32606, 32793, 108));	
		Oman_8th_Floor1.add(new Robot_Location_bean(32616, 32802, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32619, 32815, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32627, 32824, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32637, 32833, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32639, 32819, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32652, 32816, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32652, 32829, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32650, 32841, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32643, 32853, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32632, 32857, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32622, 32867, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32610, 32866, 108));
		Oman_8th_Floor1.add(new Robot_Location_bean(32608, 32851, 108));
		
		Oman_8th_Floor2.add(new Robot_Location_bean(32657, 32792, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32666, 32794, 108));	
		Oman_8th_Floor2.add(new Robot_Location_bean(32674, 32798, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32687, 32793, 108));	
		Oman_8th_Floor2.add(new Robot_Location_bean(32697, 32793, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32693, 32806, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32686, 32815, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32686, 32826, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32685, 32843, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32675, 32847, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32684, 32853, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32687, 32862, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32679, 32874, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32659, 32876, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32660, 32862, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32670, 32853, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32675, 32846, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32665, 32837, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32665, 32825, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32665, 32814, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32659, 32806, 108));
		Oman_8th_Floor2.add(new Robot_Location_bean(32654, 32799, 108));
		
		Oman_8th_Floor3.add(new Robot_Location_bean(32744, 32791, 108));	
		Oman_8th_Floor3.add(new Robot_Location_bean(32736, 32799, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32724, 32811, 108));	
		Oman_8th_Floor3.add(new Robot_Location_bean(32722, 32801, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32721, 32786, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32636, 32794, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32713, 32787, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32712, 32809, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32710, 32814, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32719, 32814, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32720, 32819, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32729, 32820, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32722, 32826, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32706, 32827, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32706, 32838, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32726, 32839, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32724, 32859, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32709, 32860, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32696, 32865, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32697, 32872, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32714, 32875, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32719, 32867, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32723, 32877, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32730, 32875, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32737, 32868, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32738, 32854, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32724, 32854, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32733, 32835, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32735, 32824, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32735, 32814, 108));
		Oman_8th_Floor3.add(new Robot_Location_bean(32723, 32813, 108));
		
		Oman_8th_Floor4.add(new Robot_Location_bean(32670, 32929, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32674, 32911, 108));	
		Oman_8th_Floor4.add(new Robot_Location_bean(32658, 32913, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32645, 32913, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32638, 32916, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32629, 32925, 108));	
		Oman_8th_Floor4.add(new Robot_Location_bean(32618, 32930, 108));	
		Oman_8th_Floor4.add(new Robot_Location_bean(32609, 32932, 108));	
		Oman_8th_Floor4.add(new Robot_Location_bean(32604, 32936, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32603, 32925, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32605, 32914, 108));		
		Oman_8th_Floor4.add(new Robot_Location_bean(32615, 32912, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32615, 32905, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32605, 32902, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32602, 32896, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32615, 32896, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32632, 32895, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32644, 32888, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32658, 32874, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32676, 32878, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32672, 32899, 108));
		Oman_8th_Floor4.add(new Robot_Location_bean(32673, 32914, 108));
		
		
		Oman_8th_Floor5.add(new Robot_Location_bean(32615, 32912, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32615, 32905, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32605, 32902, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32602, 32896, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32615, 32896, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32632, 32895, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32644, 32888, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32658, 32874, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32676, 32878, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32672, 32899, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32673, 32914, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32670, 32929, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32674, 32911, 108));	
		Oman_8th_Floor5.add(new Robot_Location_bean(32658, 32913, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32645, 32913, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32638, 32916, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32629, 32925, 108));	
		Oman_8th_Floor5.add(new Robot_Location_bean(32618, 32930, 108));	
		Oman_8th_Floor5.add(new Robot_Location_bean(32609, 32932, 108));	
		Oman_8th_Floor5.add(new Robot_Location_bean(32604, 32936, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32603, 32925, 108));
		Oman_8th_Floor5.add(new Robot_Location_bean(32605, 32914, 108));
		
		
		Oman_8th_Floor6.add(new Robot_Location_bean(32706, 32838, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32726, 32839, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32724, 32859, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32709, 32860, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32696, 32865, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32697, 32872, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32714, 32875, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32719, 32867, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32723, 32877, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32730, 32875, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32737, 32868, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32738, 32854, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32724, 32854, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32733, 32835, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32735, 32824, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32735, 32814, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32723, 32813, 108));		
		Oman_8th_Floor6.add(new Robot_Location_bean(32744, 32791, 108));	
		Oman_8th_Floor6.add(new Robot_Location_bean(32736, 32799, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32724, 32811, 108));	
		Oman_8th_Floor6.add(new Robot_Location_bean(32722, 32801, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32721, 32786, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32636, 32794, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32713, 32787, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32712, 32809, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32710, 32814, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32719, 32814, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32720, 32819, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32729, 32820, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32722, 32826, 108));
		Oman_8th_Floor6.add(new Robot_Location_bean(32706, 32827, 108));
		
		
		Oman_8th_Floor7.add(new Robot_Location_bean(32637, 32833, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32639, 32819, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32652, 32816, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32652, 32829, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32650, 32841, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32643, 32853, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32632, 32857, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32622, 32867, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32610, 32866, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32608, 32851, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32614, 32836, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32606, 32830, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32601, 32822, 108));	
		Oman_8th_Floor7.add(new Robot_Location_bean(32605, 32806, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32606, 32793, 108));	
		Oman_8th_Floor7.add(new Robot_Location_bean(32616, 32802, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32619, 32815, 108));
		Oman_8th_Floor7.add(new Robot_Location_bean(32627, 32824, 108));
		
		
	
		Oman_8th_Floor8.add(new Robot_Location_bean(32670, 32853, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32675, 32846, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32665, 32837, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32665, 32825, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32665, 32814, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32659, 32806, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32654, 32799, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32657, 32792, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32666, 32794, 108));	
		Oman_8th_Floor8.add(new Robot_Location_bean(32674, 32798, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32687, 32793, 108));	
		Oman_8th_Floor8.add(new Robot_Location_bean(32697, 32793, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32693, 32806, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32686, 32815, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32686, 32826, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32685, 32843, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32675, 32847, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32684, 32853, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32687, 32862, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32679, 32874, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32659, 32876, 108));
		Oman_8th_Floor8.add(new Robot_Location_bean(32660, 32862, 108));
		
		
        
		
		Underground.add(new Robot_Location_bean(32800, 33051, 420));
		
		Forget_Isiand2.add(new Robot_Location_bean(32754, 32942, 1701)); //南ゴーレム研究所入口
		Forget_Isiand3.add(new Robot_Location_bean(32694, 32716, 1701)); //西ゴーレム入口
		Forget_Isiand4.add(new Robot_Location_bean(32926, 32890, 1701)); //東ゴーレム入口

		Ship_Deep_Sea.add(new Robot_Location_bean(33011, 33011, 558));
		Ship_Deep_Sea2.add(new Robot_Location_bean(33011, 33012, 558));
		Ship_Deep_Sea3.add(new Robot_Location_bean(33011, 33013, 558));

		Yongdun_1st_Floor.add(new Robot_Location_bean(32799, 32742, 30));
		Yongdun_2st_Floor.add(new Robot_Location_bean(32761, 32788, 31));
		Yougdun_3st_Floor.add(new Robot_Location_bean(32703, 32833, 32));
		Yougdun_4st_Floor.add(new Robot_Location_bean(32677, 32860, 33));
		Yougdun_5st_Floor.add(new Robot_Location_bean(32742, 32794, 35));
		Yougdun_6st_Floor.add(new Robot_Location_bean(32666, 32862, 36));
		Yougdun_7st_Floor.add(new Robot_Location_bean(32664, 32838, 37));

		Bondon_1st_Floor.add(new Robot_Location_bean(32812, 32726, 807));
		Bondon_2st_Floor.add(new Robot_Location_bean(32750, 32798, 808));
		Bondon_3st_Floor.add(new Robot_Location_bean(32801, 32754, 809));
		Bondon_4st_Floor.add(new Robot_Location_bean(32763, 32773, 810));
		Bondon_5st_Floor.add(new Robot_Location_bean(32728, 32723, 811));
		Bondon_6st_Floor.add(new Robot_Location_bean(32804, 32725, 812));
		Bondon_7st_Floor.add(new Robot_Location_bean(32727, 32725, 813));

		Ivory_Tower_4th_Floor.add(new Robot_Location_bean(32901, 32765, 280));
		Ivory_Tower_5th_Floor.add(new Robot_Location_bean(32810, 32865, 281));

		Ant_Den1.add(new Robot_Location_bean(32784, 32751, 43));
		Ant_Den2.add(new Robot_Location_bean(32798, 32754, 44));
		Ant_Den3.add(new Robot_Location_bean(32759, 32742, 45));
		Ant_Den4.add(new Robot_Location_bean(32750, 32764, 46));
		Ant_Den5.add(new Robot_Location_bean(32795, 32746, 47));
		Ant_Den6.add(new Robot_Location_bean(32768, 32805, 50));
		
		Gigam_1st_Floor.add(new Robot_Location_bean(32805, 32738, 53));
		Gigam_2st_Floor.add(new Robot_Location_bean(32808, 32796, 54));
		Gigam_3st_Floor.add(new Robot_Location_bean(32736, 32729, 55));
		Gigam_4st_Floor.add(new Robot_Location_bean(32768, 32820, 56));
		
		Barrier_1st_Floor.add(new Robot_Location_bean(32785, 32883, 15403));
		

	}

	

	

}