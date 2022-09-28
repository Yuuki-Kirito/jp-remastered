package l1j.server.GameSystem.Robot;

import static l1j.server.server.model.skill.L1SkillId.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

import javolution.util.FastMap;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ClanJoinLeaveStatus;
import l1j.server.server.serverpackets.S_DRAGONPERL;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.SQLUtil;

public class Robot {

	private static Random _random = new Random(System.currentTimeMillis());
	public static boolean is_DOLL = false;

	public static void poly(L1RobotInstance bot) {
		/*
		 * int rr = 0;
		 * 
		 * if (bot.리스봇 || bot.사냥봇) rr = _random.nextInt(3); else rr =
		 * _random.nextInt(2);
		 */

		if (/* rr == 0 || */(bot.lisbot_spawn_witch == 1 && _random.nextInt(10) < 8)) {
			polyNormal(bot);
		} else {
				polyNormal(bot);
		}
	}

	private static void polyNormal(L1RobotInstance bot) {
		if (bot.is_HUNTING_BOT) {
			if (bot.isElf() && bot.getCurrentWeapon() == 20) {
				int f = _random.nextInt(5);
				if (f == 1) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_FAIRY_BOW_1); //fairy man
					bot.setCurrentSprite(Config.ROBOT_FAIRY_BOW_1);
				} else if (f == 2) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_FAIRY_BOW_2); //phonos archer
					bot.setCurrentSprite(Config.ROBOT_FAIRY_BOW_2);
				} else if (f == 3) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_FAIRY_BOW_3); // burning archer
					bot.setCurrentSprite(Config.ROBOT_FAIRY_BOW_3);
				} else if (f == 4) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_FAIRY_BOW_4); // burning archer
					bot.setCurrentSprite(Config.ROBOT_FAIRY_BOW_4);
				} else {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_FAIRY_BOW_5); // burning archer
					bot.setCurrentSprite(Config.ROBOT_FAIRY_BOW_5);
				}
			} else if (bot.getCurrentWeapon() == 50 || bot.getCurrentWeapon() == 4 || bot.getCurrentWeapon() == 88 || bot.getCurrentWeapon() == 58){
				int f1 = _random.nextInt(3);
				if (f1 == 1) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_1); // Sharna 75 Knight M
					bot.setCurrentSprite(Config.ROBOT_KNIFE_1);
				} else if(f1 == 2) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_2); // Sharna 75 Fairy M
					bot.setCurrentSprite(Config.ROBOT_KNIFE_2);
				} else {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_3); // Sharna 75 Fairy M
					bot.setCurrentSprite(Config.ROBOT_KNIFE_3);
				}
			} else if (bot.getCurrentWeapon() == 54){
				int f2 = _random.nextInt(2);
				if (f2 == 1) { 
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_4); // Sharna 75 Dragon Knight
					bot.setCurrentSprite(Config.ROBOT_KNIFE_4);
				} else {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_5); // Sharna 75 Phantom Magic Male
					bot.setCurrentSprite(Config.ROBOT_KNIFE_5);
				}

			}else if(bot.getCurrentWeapon() == 24) {
				int f3 = _random.nextInt(2);
				if (f3 == 1) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_6); // Sharna 75 Phantom W
					bot.setCurrentSprite(Config.ROBOT_KNIFE_6);
				} else {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_7); // Sharna 75 Phantom W
					bot.setCurrentSprite(Config.ROBOT_KNIFE_7);
				}
			}else if(bot.getCurrentWeapon() == 40) {
				int f4 = _random.nextInt(2);
				if(f4 == 1) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_8); // Sharna 75 Phantom W
					bot.setCurrentSprite(Config.ROBOT_KNIFE_8);
				} else {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_9); // Sharna 75 Phantom W
					bot.setCurrentSprite(Config.ROBOT_KNIFE_9);
				}
			}
		} else {
			// int f = _random.nextInt(100);
			// buggy bots
			/*
			 * if(bot.리스봇 && (bot.리스봇_스폰위치 == 0 || bot.리스봇_스폰위치 == 3 ||
			 * (bot.리스봇_스폰위치 >= 16 && bot.리스봇_스폰위치 <= 19))) f =
			 * _random.nextInt(100);
			 */
			bot.getGfxId().setTempCharGfx(bot.getGfxId().getGfxId());
			bot.setCurrentSprite(bot.getGfxId().getGfxId());

			/*
			 * if(f < 15){
			 * bot.getGfxId().setTempCharGfx(bot.getGfxId().getGfxId());
			 * if(bot.버경봇 && bot.isCrown()){ if(_random.nextInt(2) == 0){
			 * if(bot.get_sex() == 0) bot.getGfxId().setTempCharGfx(6094); else
			 * bot.getGfxId().setTempCharGfx(6080); } } }else if(f < 50){
			 * bot.getGfxId().setTempCharGfx(11375); }else if(f < 75){
			 * bot.getGfxId().setTempCharGfx(11328+_random.nextInt(3)); //else
			 * if(f == 3) // bot.getGfxId().setTempCharGfx(11341); }else{
			 * bot.getGfxId().setTempCharGfx(11370); } if(bot.isElf() && f != 0
			 * && bot.getCurrentWeapon() == 20)
			 * bot.getGfxId().setTempCharGfx(bot.getGfxId().getGfxId());
			 */
		}
	}
	
	
	public static void polyWar(L1RobotInstance bot) {
		if (bot.is_HUNTING_BOT) {
			if(bot.isWizard()) {
				int f4 = _random.nextInt(2);
				if(f4 == 1) {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_1); // Sharna 75 Phantom W
					bot.setCurrentSprite(Config.ROBOT_KNIFE_1);
				} else {
					bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_2); // Sharna 75 Phantom W
					bot.setCurrentSprite(Config.ROBOT_KNIFE_2);
				}
			}
		}
	}

	public static boolean is_SPEED_BUFF(L1RobotInstance bot) {
		// TODO Auto-generated method stubs
		// When not Decay
		if (bot.getMap().isUnderwater()) {
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					STATUS_UNDERWATER_BREATH)) {
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(),
						190), true);
				bot.getSkillEffectTimerSet().setSkillEffect(
						STATUS_UNDERWATER_BREATH, 1800 * 1000);
			}
		}
		if (bot.getMoveState().getMoveSpeed() == 0
				&& !bot.getSkillEffectTimerSet().hasSkillEffect(HASTE)
				&& !bot.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.DECAY_POTION)) {
			bot.getMoveState().setMoveSpeed(1);
			bot.getSkillEffectTimerSet().setSkillEffect(HASTE,
					(_random.nextInt(400) + 1700) * 1000);
			Broadcaster.broadcastPacket(bot,
					new S_SkillSound(bot.getId(), 191), true);
		}
		if (_random.nextInt(100) > 10)
			return false;
		if (bot.isKnight() || bot.isCrown() || bot.isWarrior()) {
			// When it's not Decay
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_BRAVE)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.DECAY_POTION)) {
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.STATUS_BRAVE,
						(_random.nextInt(600) + 400) * 1000);
				bot.getMoveState().setBraveSpeed(1);
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(),
						751), true);
				Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(),
						1, 0), true);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_DRAGONPERL)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.DECAY_POTION)) {
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.STATUS_DRAGONPERL,
						(_random.nextInt(600) + 400) * 1000);
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 197), true);
				Broadcaster.broadcastPacket(bot, new S_DRAGONPERL(bot.getId(), 8), true);//
				bot.set_pearl_speed(1);
				return true;
			}
		} else if (bot.isElf()) {
			if (bot.getCurrentWeapon() != 20) {
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.FIRE_BLESS)
						&& !bot.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.SILENCE)) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.FIRE_BLESS,
							(_random.nextInt(50) + 250) * 1000);
					bot.getMoveState().setBraveSpeed(1);
					Broadcaster.broadcastPacket(bot,
							new S_SkillSound(bot.getId(), 11775), true);
					Broadcaster.broadcastPacket(bot,
							new S_SkillBrave(bot.getId(), 1, 0), true);
					return true;
				}
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_DRAGONPERL)
						&& !bot.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DECAY_POTION)) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.STATUS_DRAGONPERL,
							(_random.nextInt(600) + 400) * 1000);
					Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 197), true);
					Broadcaster.broadcastPacket(bot, new S_DRAGONPERL(bot.getId(), 8), true);//
					bot.set_pearl_speed(1);
					return true;
				}
			} else {
				int rnd = _random.nextInt(100) + 1;
				if(rnd >= 50){
					if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_ELFBRAVE) && !bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DECAY_POTION) &&
							!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FOCUS_WAVE)) {
						   bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_ELFBRAVE,(_random.nextInt(600) + 400) * 1000);
						   bot.getMoveState().setBraveSpeed(3);
						   Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 751), true);
						   Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(), 3, 0), true);
						   return true;
					  }
					if (!bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.STATUS_DRAGONPERL)
							&& !bot.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.DECAY_POTION)) {
						bot.getSkillEffectTimerSet().setSkillEffect(
								L1SkillId.STATUS_DRAGONPERL,
								(_random.nextInt(600) + 400) * 1000);
						Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 197), true);
						Broadcaster.broadcastPacket(bot, new S_DRAGONPERL(bot.getId(), 8), true);//
						bot.set_pearl_speed(1);
						return true;
					}
				} else {
					if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_ELFBRAVE) && !bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FOCUS_WAVE) 
							&& !bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)) {
						bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.FOCUS_WAVE,(_random.nextInt(600) + 400) * 1000);
						bot.getMoveState().setBraveSpeed(1);
						Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 16531), true);
						Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(), 10, 0), true);
						return true;
					}
					if (!bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.STATUS_DRAGONPERL)
							&& !bot.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.DECAY_POTION)) {
						bot.getSkillEffectTimerSet().setSkillEffect(
								L1SkillId.STATUS_DRAGONPERL,
								(_random.nextInt(600) + 400) * 1000);
						Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 197), true);
						Broadcaster.broadcastPacket(bot, new S_DRAGONPERL(bot.getId(), 8), true);//
						bot.set_pearl_speed(1);
						return true;
					}
				}
			}
		} else if (bot.isDragonknight()) {
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.BLOOD_LUST)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.SILENCE)) {
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.BLOOD_LUST,
						(_random.nextInt(300) + 200) * 1000);
				bot.getMoveState().setBraveSpeed(1);
				Broadcaster.broadcastPacket(bot, new S_DoActionGFX(bot.getId(),
						19));
				Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(),
						1, 0), true);
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(),
						6523), true);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_DRAGONPERL)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.DECAY_POTION)) {
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.STATUS_DRAGONPERL,
						(_random.nextInt(600) + 400) * 1000);
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 197), true);
				Broadcaster.broadcastPacket(bot, new S_DRAGONPERL(bot.getId(), 8), true);//
				bot.set_pearl_speed(1);
				return true;
			}
		} else if (bot.isDarkelf()) {
			int percent = (int) Math.round((double) bot.getCurrentMp()
					/ (double) bot.getMaxMp() * 100);
			if (percent < 20)
				return false;
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.MOVING_ACCELERATION)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.SILENCE)) {
				new L1SkillUse().handleCommands(bot,
						L1SkillId.MOVING_ACCELERATION, bot.getId(), bot.getX(),
						bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				/*
				 * bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.
				 * MOVING_ACCELERATION, (_random.nextInt(600)+400) *1000);
				 * bot.getMoveState().setBraveSpeed(4);
				 * Broadcaster.broadcastPacket(bot, new
				 * S_DoActionGFX(bot.getId(), 19));
				 * Broadcaster.broadcastPacket(bot, new
				 * S_SkillBrave(bot.getId(), 4, 0), true);
				 * Broadcaster.broadcastPacket(bot, new
				 * S_SkillSound(bot.getId(), 2945), true);
				 */
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_DRAGONPERL)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.DECAY_POTION)) {
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.STATUS_DRAGONPERL,
						(_random.nextInt(600) + 400) * 1000);
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 197), true);
				Broadcaster.broadcastPacket(bot, new S_DRAGONPERL(bot.getId(), 8), true);//
				bot.set_pearl_speed(1);
				return true;
			}
		} else if (bot.isIllusionist()) {
			int percent = (int) Math.round((double) bot.getCurrentMp()
					/ (double) bot.getMaxMp() * 100);
			if (percent < 20)
				return false;
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_BRAVE)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.DECAY_POTION)) {
				new L1SkillUse().handleCommands(bot,
						L1SkillId.STATUS_BRAVE, bot.getId(), bot.getX(),
						bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				   bot.getMoveState().setBraveSpeed(4);
				   Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 751), true);
				   Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(), 4, 0), true);
				
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_DRAGONPERL)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.DECAY_POTION)) {
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.STATUS_DRAGONPERL,
						(_random.nextInt(600) + 400) * 1000);
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 197), true);
				Broadcaster.broadcastPacket(bot, new S_DRAGONPERL(bot.getId(), 8), true);//
				bot.set_pearl_speed(1);
				return true;
			}
		} else if (bot.isWizard()) {
			int percent = (int) Math.round((double) bot.getCurrentMp()
					/ (double) bot.getMaxMp() * 100);
			if (percent < 20)
				return false;
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HOLY_WALK)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)) {
				bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.HOLY_WALK, (_random.nextInt(14) + 50) * 1000);
				bot.getMoveState().setBraveSpeed(4);
				Broadcaster.broadcastPacket(bot, new S_DoActionGFX(bot.getId(),19));
				Broadcaster.broadcastPacket(bot, new S_SkillBrave(bot.getId(),4, 0), true);
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(),3936), true);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_DRAGONPERL)
					&& !bot.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.DECAY_POTION)) {
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.STATUS_DRAGONPERL,
						(_random.nextInt(600) + 400) * 1000);
				Broadcaster.broadcastPacket(bot, new S_SkillSound(bot.getId(), 197), true);
				Broadcaster.broadcastPacket(bot, new S_DRAGONPERL(bot.getId(), 8), true);//
				bot.set_pearl_speed(1);
				return true;
			}
		}
		return false;
	}

	public static boolean is_CLASS_BUFF(L1RobotInstance bot) {
		// TODO Auto-generated method stubs
		if (bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE))
			return false;
		if (!bot.isDragonknight()) {
			int percent = (int) Math.round((double) bot.getCurrentMp()
					/ (double) bot.getMaxMp() * 100);
			if (percent < 30)
				return false;
		}
		if (bot.isKnight()) {
			bot._REDUGTION_ARMOR_VETERAN = true;
			bot._RAGING_FORCE = true;
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.REDUCTION_ARMOR)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.REDUCTION_ARMOR, bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BOUNCE_ATTACK)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.BOUNCE_ATTACK, bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COUNTER_BARRIER)) {
				bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.COUNTER_BARRIER,128000);
				bot.broadcastPacket(new S_SkillSound(bot.getId(), 10709));
				return true;
			}
		} else if (bot.isCrown()) {
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BRAVE_AURA)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.BRAVE_AURA, bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.GLOWING_AURA)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.GLOWING_AURA, bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.PRIME)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.PRIME, bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHINING_ARMOR)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.SHINING_ARMOR, bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHINING_AURA)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.SHINING_AURA, bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
		} else if (bot.isElf()) {
			bot._GLORIOUS = true;
			if (bot.getCurrentWeapon() == 20 && bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_ELFBRAVE)) {
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STORM_SHOT)) {
					new L1SkillUse().handleCommands(bot, L1SkillId.STORM_SHOT,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
					return true;
				}
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WIND_WALK)) {
					new L1SkillUse().handleCommands(bot, L1SkillId.WIND_WALK,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
					return true;
				}
			} else if (bot.getCurrentWeapon() == 20 && bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FOCUS_WAVE)) {
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WIND_SHOT)) {
					new L1SkillUse().handleCommands(bot, L1SkillId.WIND_SHOT,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
					return true;
				}
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.AQUA_PROTECTER)) {
					new L1SkillUse().handleCommands(bot, L1SkillId.AQUA_PROTECTER,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
					return true;
				}
			} else {
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BURNING_WEAPON)) {
					new L1SkillUse().handleCommands(bot,L1SkillId.BURNING_WEAPON, bot.getId(), bot.getX(),bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
					return true;
				}
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ELEMENTAL_FIRE)) {
					new L1SkillUse().handleCommands(bot,L1SkillId.ELEMENTAL_FIRE, bot.getId(), bot.getX(),bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
					return true;
				}
				if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ADDITIONAL_FIRE)) {
					new L1SkillUse().handleCommands(bot,L1SkillId.ADDITIONAL_FIRE, bot.getId(), bot.getX(),bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
					return true;
				}
			}
		} else if (bot.isDragonknight()) {
			bot._DRAGON_SKIN = true;
			bot._AURACHIA = true;
			bot.setSkillMastery(L1SkillId.FEAR);
			bot.setSkillMastery(L1SkillId.HORROR_OF_DEATH);
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SCALES_FIRE_DRAGON)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.SCALES_FIRE_DRAGON,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SCALES_Lind_DRAGON)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.SCALES_Lind_DRAGON,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HALPHAS)) {
				bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.HALPHAS,128000);
				bot.broadcastPacket(new S_SkillSound(bot.getId(), 17723));
				return true;
			}
		} else if (bot.isIllusionist()) {
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.MIRROR_IMAGE)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.MIRROR_IMAGE,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CONCENTRATION)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.CONCENTRATION,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				bot.setCurrentMp(bot.getMaxMp());
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.PATIENCE)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.PATIENCE,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.INSIGHT)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.INSIGHT,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.JOY_OF_PAIN)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.JOY_OF_PAIN,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.POTENTIAL)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.POTENTIAL,bot.getId(), bot.getX(), bot.getY(), null, 0, L1SkillUse.TYPE_NORMAL);
				return true;
			}
		} else if (bot.isDarkelf()) {
			bot._FINAL_BURN = true;
			bot._BURNING_SPIRITS = true;
			bot._DRESS_EVASION = true;
			bot._LUCIFER_DESTINY = true;
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHADOW_ARMOR)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.SHADOW_ARMOR,bot.getId(), bot.getX(), bot.getY(), null, 960, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DOUBLE_BRAKE)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.DOUBLE_BRAKE,bot.getId(), bot.getX(), bot.getY(), null, 192, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.UNCANNY_DODGE)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.UNCANNY_DODGE,bot.getId(), bot.getX(), bot.getY(), null, 960, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHADOW_FANG)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.SHADOW_FANG,bot.getId(), bot.getX(), bot.getY(), null, 192, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.LUCIFER)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.LUCIFER,bot.getId(), bot.getX(), bot.getY(), null, 192, L1SkillUse.TYPE_NORMAL);
				return true;
			}
		} else if (bot.isWizard()) {
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.IMMUNE_TO_HARM) && !bot.isPinkName()) {
				new L1SkillUse().handleCommands(bot, L1SkillId.IMMUNE_TO_HARM,bot.getId(), bot.getX(), bot.getY(), null, 60, L1SkillUse.TYPE_NORMAL);
				bot.getSkillEffectTimerSet().setSkillEffect(L1SkillId.SHAPE_CHANGE, 1800 * 1000);
				bot.getGfxId().setTempCharGfx(Config.ROBOT_KNIFE_8);
				Broadcaster.broadcastPacket(bot, new S_ChangeShape(bot.getId(), bot.getGfxId().getTempCharGfx()));
				Broadcaster.broadcastPacket(bot, new S_CharVisualUpdate(bot, bot.getCurrentWeapon()));
				bot.setCurrentMp(bot.getMaxMp());
				return true;
			}
			int rand =  _random.nextInt(100);
			if (bot.getCurrentHp() < 1300) {
				if (rand < 60) { 
				new L1SkillUse().handleCommands(bot, L1SkillId.IMMUNE_TO_HARM,bot.getId(), bot.getX(), bot.getY(), null, 60, L1SkillUse.TYPE_NORMAL);
				}
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.Freeze_armor)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.Freeze_armor,bot.getId(), bot.getX(), bot.getY(), null, 960, L1SkillUse.TYPE_NORMAL);
				return true;
			}
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ADVANCE_SPIRIT)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.ADVANCE_SPIRIT,bot.getId(), bot.getX(), bot.getY(), null, 960, L1SkillUse.TYPE_NORMAL);
				return true;
			}
		} else if (bot.isWarrior()) {
			if (!bot.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.PERIOD_TICK)) {
				new L1SkillUse().handleCommands(bot, L1SkillId.PERIOD_TICK,bot.getId(), bot.getX(), bot.getY(), null, 60, L1SkillUse.TYPE_NORMAL);
				return true;
			}
		}
		return false;
	}

	private static long joinTime = 0;

	public static void clan_join(L1RobotInstance bot) {
		// TODO Auto-generated method stubs
		if (bot.getClanid() != 0 || bot.isCrown())
			return;
		if (_random.nextInt(10) == 0)
			return;
		if (joinTime == 0) {
			joinTime = System.currentTimeMillis()
					+ (60000 * (20 + _random.nextInt(21)));
			return;
		} else {
			if (joinTime > System.currentTimeMillis())
				return;
			joinTime = System.currentTimeMillis()
					+ (60000 * (20 + _random.nextInt(21)));
		}
		// 로봇중 가입 되어있는 케릭이 650케릭 이상인지
		// 가입하려는혈 총혈 다 받아와서 유저 비교 제일적은혈
		String clanname = robot_clan_count();
		if (clanname == null)
			return;
		L1Clan clan = L1World.getInstance().getClan(clanname);
		if (clan == null)
			return;
		L1PcInstance pc = L1World.getInstance().getPlayer(clan.getLeaderName());
		if (pc == null)
			return;
		// 군주근처에 혈원이 있는지
		for (L1PcInstance pp : L1World.getInstance().getVisiblePlayer(pc)) {
			if (!(pp instanceof L1RobotInstance)
					&& pc.getClanid() == pp.getClanid())
				return;
		}
		// 내 근처에 같은혈 있는지
		for (L1PcInstance pp : L1World.getInstance().getVisiblePlayer(bot)) {
			if (!(pp instanceof L1RobotInstance)
					&& pc.getClanid() == pp.getClanid())
				return;
		}
		// 가입
		for (L1PcInstance clanMembers : clan.getOnlineClanMember()) {
			clanMembers.sendPackets(new S_ServerMessage(94, bot.getName())); // \f1%0이
																				// 혈맹의
																				// 일원으로서
																				// 받아들여졌습니다.
		}
		bot.setClanid(clan.getClanId());
		bot.setClanname(clan.getClanName());
		bot.setClanRank(L1Clan.CLAN_RANK_PROBATION);
		bot.setTitle("");
		bot.setClanJoinDate(new Timestamp(System.currentTimeMillis()));
		Broadcaster.broadcastPacket(bot, new S_CharTitle(bot.getId(), ""));
		clan.addClanMember(bot.getName(), bot.getClanRank(), bot.getLevel(),
				bot.getType(), bot.getMemo(), 1, bot);
		Broadcaster.broadcastPacket(bot, new S_ClanJoinLeaveStatus(bot));
		Broadcaster.broadcastPacket(bot, new S_ReturnedStat(bot,
				S_ReturnedStat.CLAN_JOIN_LEAVE));
		GeneralThreadPool.getInstance().schedule(
				new title((L1RobotInstance) pc, bot),
				3000 + _random.nextInt(2000));
		try {
			Robot_Bugbear.getInstance().clanSetting(bot);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Robot_ConnectAndRestart.getInstance().clanSetting(bot);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Robot_Fish.getInstance().clanSetting(bot);
		} catch (Exception e) {
			// TODO: handle exception
		}
		bot.updateclan(bot.getClanname(), bot.getClanid(), bot.getTitle(), true);
	}

	private static String robot_clan_count() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String clan = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM robots_crown");
			rs = pstm.executeQuery();
			while (rs.next()) {
				String clanname = rs.getString("clanname");
				int clanid = rs.getInt("clanid");
				if (clanid == 0)
					continue;
				Connection con2 = null;
				PreparedStatement pstm2 = null;
				ResultSet rs2 = null;
				try {
					con2 = L1DatabaseFactory.getInstance().getConnection();
					pstm2 = con2
							.prepareStatement("SELECT * FROM robots WHERE clanid=?");
					pstm2.setInt(1, clanid);
					rs2 = pstm2.executeQuery();
					if (!rs2.next()) {
						clan = clanname;
						break;
					}
				} catch (SQLException e) {

				} finally {
					SQLUtil.close(rs2);
					SQLUtil.close(pstm2);
					SQLUtil.close(con2);
				}
			}
		} catch (SQLException e) {

		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		if (clan != null)
			return clan;

		int count = 0;
		FastMap<String, Integer> list = new FastMap<String, Integer>();
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM robots");
			rs = pstm.executeQuery();
			while (rs.next()) {
				String clanname = rs.getString("clanname");
				int clanid = rs.getInt("clanid");
				if (clanid == 0)
					continue;
				count++;
				if (count > 650)
					break;
				try {
					int cc = list.get(clanname);
					list.put(clanname, cc + 1);
				} catch (Exception e) {
					list.put(clanname, 0);
				}
			}
		} catch (SQLException e) {

		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		if (count > 650)
			return null;

		int ci = 1000;
		for (FastMap.Entry<String, Integer> e = list.head(), mapEnd = list
				.tail(); (e = e.getNext()) != mapEnd;) {
			int cu = e.getValue();
			if (ci >= cu) {
				ci = cu;
				clan = e.getKey();
			}
		}
		return clan;
	}

	static class title implements Runnable {

		private L1RobotInstance crown;
		private L1RobotInstance joinchar;

		public title(L1RobotInstance _crown, L1RobotInstance _joinchar) {
			crown = _crown;
			joinchar = _joinchar;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stubs
			try {

				if (crown._userTitle == null
						|| crown._userTitle.equalsIgnoreCase(""))
					return;
				if (L1World.getInstance().getPlayer(crown.getName()) == null
						|| L1World.getInstance().getPlayer(joinchar.getName()) == null)
					return;

				joinchar.setTitle(crown._userTitle);
				S_CharTitle ct = new S_CharTitle(joinchar.getId(),
						joinchar.getTitle());
				joinchar.sendPackets(ct);
				Broadcaster.broadcastPacket(joinchar, ct, true);
				try {
					if (joinchar instanceof L1RobotInstance)
						joinchar.updateclan(joinchar.getClanname(),
								joinchar.getClanid(), crown._userTitle, true);
					else
						joinchar.save(); // DB에 캐릭터 정보를 써 우
				} catch (Exception e) {
				}

				L1Clan clan = L1World.getInstance()
						.getClan(crown.getClanname());
				if (clan != null) {
					for (L1PcInstance clanPc : clan.getOnlineClanMember()) {
						// \f1%0이%1에 「%2라고 하는 호칭을 주었습니다.
						S_ServerMessage sm = new S_ServerMessage(203,
								crown.getName(), joinchar.getName(),
								joinchar.getTitle());
						clanPc.sendPackets(sm, true);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void Doll_Delete(L1RobotInstance bot) {
		// TODO Auto-generated method stubs
		Doll_Delete(bot, false);
	}

	public static void Doll_Delete(L1RobotInstance bot, boolean effect) {
		// TODO Auto-generated method stubs
		L1DollInstance doll = null;
		for (Object dollObject : bot.getDollList()) {
			doll = (L1DollInstance) dollObject;
		}
		if (doll != null) {
			if (effect)
				Broadcaster.broadcastPacket(doll, new S_SkillSound(
						doll.getId(), 5936), true);
			doll.deleteDoll();
		}
	}

	public static void Doll_Spawn(L1RobotInstance bot) {
		// TODO Auto-generated method stubs
		if (!is_DOLL)
			return;
		if (bot.is_DOLL_SPAWN)
			return;
		if (bot.getDollList().size() > 0)
			return;
		if (!bot.is_HUNTING_BOT && _random.nextInt(100) < 70)
			return;
		int time = 2000 + _random.nextInt(8000);
		if (bot.is_BERKYUNG_BOT)
			time = 1;
		GeneralThreadPool.getInstance().schedule(new DollSpawn(bot, time != 1),
				time);
		bot.is_DOLL_SPAWN = true;
	}

	static class DollSpawn implements Runnable {
		private L1RobotInstance bot;
		private boolean effect;

		public DollSpawn(L1RobotInstance _bot, boolean _effect) {
			bot = _bot;
			effect = _effect;
		}

		@Override
		public void run() {
			try {

				// TODO Auto-generated method stubs
				if (bot.isDead()
						|| bot.is_THREAD_EXIT
						|| L1World.getInstance().getPlayer(bot.getName()) == null)
					return;
				int npcId = 0;
				int dollType = 0;
				int dollTime = 0;
					if (bot.getCurrentWeapon() == 20) {
						npcId = Config.DOLL_TYPE_BOW_NPC_ID;
						//npcId = 1600242;
						dollType = Config.DOLL_TYPE_BOW;
					} else {
					if (bot.isKnight() || bot.isCrown()) {
						npcId = Config.DOLL_TYPE_KALNPC_ID_1; //スパルトイ
						//npcId = 1600247; //데나
						dollType = Config.DOLL_TYPE_KNIFE_1;
				    } else if (bot.isWarrior()) {
						npcId = Config.DOLL_TYPE_KALNPC_ID_2; //スパルトイ
						//npcId = 1600247; //데나
						dollType = Config.DOLL_TYPE_KNIFE_2;
					} else if (bot.isDarkelf()) {
						npcId = Config.DOLL_TYPE_KALNPC_ID_3; //スパルトイ
						//npcId = 1600247; //데나
						dollType = Config.DOLL_TYPE_KNIFE_3;
					} else if (bot.isDragonknight()) {
						npcId = Config.DOLL_TYPE_KALNPC_ID_4; //スパルトイ
						//npcId = 1600247; //데나
						dollType = Config.DOLL_TYPE_KNIFE_4;
					} else if (bot.getCurrentWeapon() != 20 && bot.isElf()) {
						npcId = Config.DOLL_TYPE_KALNPC_ID_5; //スパルトイ
						//npcId = 1600247; //데나
						dollType = Config.DOLL_TYPE_KNIFE_5;
					} else if (bot.isWizard() || bot.isIllusionist()) {
						npcId = Config.DOLL_TYPE_KALNPC_ID_6; //スパルトイ
						//npcId = 1600247; //데나
						dollType = Config.DOLL_TYPE_KNIFE_6;
					}
					if (_random.nextInt(3) == 0) {
						npcId = Config.EXPERIENCE_DOLL_NPC_ID;
						//npcId = 1500204;
						dollType = Config.DOLL_TYPE_EXPERIENCE;
					}
				 }
					dollTime = 1800;
				if (dollType == 0)
					return;
				L1Npc template = NpcTable.getInstance().getTemplate(npcId);
				L1DollInstance doll = new L1DollInstance(template, bot,
						dollType, ObjectIdFactory.getInstance().nextId(),
						dollTime * 1000);
				if (effect)
					Broadcaster.broadcastPacket(bot,
							new S_SkillSound(doll.getId(), 5935), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	static class poly implements Runnable {

		L1RobotInstance bot;

		public poly(L1RobotInstance _bot) {
			bot = _bot;
		}

		@Override
		public void run() {
			try {
				// TODO Auto-generated method stubs
				if (bot.isDead()
						|| bot.is_THREAD_EXIT
						|| L1World.getInstance().getPlayer(bot.getName()) == null)
					return;
				poly(bot);
				Broadcaster.broadcastPacket(bot, new S_ChangeShape(bot.getId(),
						bot.getGfxId().getTempCharGfx()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void robot_shutdown(L1RobotInstance bot) {
		if (bot.is_BERKYUNG_BOT) {
			bot.is_THREAD_EXIT = true;
			bot.getNearObjects().removeAllKnownObjects();
			bot.stopHalloweenRegeneration();
			bot.stopPapuBlessing();
			bot.stopLindBlessing();
			bot.stopHalloweenArmorBlessing();
			bot.stopAHRegeneration();
			bot.stopHpRegenerationByDoll();
			bot.stopMpRegenerationByDoll();
			bot.stopSHRegeneration();
			bot.stopMpDecreaseByScales();
			bot.stopEtcMonitor();
			bot.berkyung_bot_type = 0;
		}
	}

}
