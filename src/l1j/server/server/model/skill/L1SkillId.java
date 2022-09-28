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
package l1j.server.server.model.skill;

public class L1SkillId {
	public static final int SKILLS_BEGIN = 1;

	/*
	 * Regular Magic Lv1-10
	 */
	// 1단계 일반마법
	public static final int HEAL = 1; // E: LESSER_HEAL
	public static final int LIGHT = 2;
	public static final int SHIELD = 3;
	public static final int ENERGY_BOLT = 4;
	public static final int TELEPORT = 5;
	public static final int ICE_DAGGER = 6;
	public static final int WIND_CUTTER = 7; // E: WIND_SHURIKEN
	public static final int HOLY_WEAPON = 8;

	// 2단계 일반마법
	public static final int CURE_POISON = 9;
	public static final int CHILL_TOUCH = 10;
	public static final int CURSE_POISON = 11;
	public static final int ENCHANT_WEAPON = 12;
	public static final int DETECTION = 13;
	public static final int DECREASE_WEIGHT = 14;
	public static final int FIRE_ARROW = 15;
	public static final int STALAC = 16;

	// 3단계 일반마법
	public static final int LIGHTNING = 17;
	public static final int TURN_UNDEAD = 18;
	public static final int EXTRA_HEAL = 19; // E: HEAL
	public static final int CURSE_BLIND = 20;
	public static final int BLESSED_ARMOR = 21;
	public static final int FROZEN_CLOUD = 22;
	public static final int WEAK_ELEMENTAL = 23; // E: REVEAL_WEAKNESS

	// 4단계 일반마법 // none = 24
	public static final int FIREBALL = 25;
	public static final int PHYSICAL_ENCHANT_DEX = 26; // E: ENCHANT_DEXTERITY
	public static final int WEAPON_BREAK = 27;
	public static final int VAMPIRIC_TOUCH = 28;
	public static final int SLOW = 29;
	public static final int EARTH_JAIL = 30;
	public static final int COUNTER_MAGIC = 31;
	public static final int MEDITATION = 32;

	// 5단계 일반마법
	public static final int CURSE_PARALYZE = 33;
	public static final int CALL_LIGHTNING = 34;
	public static final int GREATER_HEAL = 35;
	public static final int TAMING_MONSTER = 36; // E: TAME_MONSTER
	public static final int REMOVE_CURSE = 37;
	public static final int CONE_OF_COLD = 38;
	public static final int MANA_DRAIN = 39;
	public static final int DARKNESS = 40;

	// 6단계 일반마법
	public static final int CREATE_ZOMBIE = 41;
	public static final int PHYSICAL_ENCHANT_STR = 42; // E: ENCHANT_MIGHTY
	public static final int HASTE = 43;
	public static final int CANCELLATION = 44; // E: CANCEL MAGIC
	public static final int ERUPTION = 45;
	public static final int SUNBURST = 46;
	public static final int WEAKNESS = 47;
	public static final int BLESS_WEAPON = 48;

	// 7단계 일반마법
	public static final int HEAL_ALL = 49; // E: HEAL_PLEDGE
	public static final int Freeze_armor = 50;
	public static final int SUMMON_MONSTER = 51;
	public static final int HOLY_WALK = 52;
	public static final int TORNADO = 53;
	public static final int GREATER_HASTE = 54;
	public static final int BERSERKERS = 55;
	public static final int DISEASE = 56;

	// 8단계 일반마법
	public static final int FULL_HEAL = 57;
	public static final int FIRE_WALL = 58;
	public static final int BLIZZARD = 59;
	public static final int INVISIBILITY = 60;
	public static final int RESURRECTION = 61;
	public static final int EARTHQUAKE = 62;
	public static final int LIFE_STREAM = 63;
	public static final int SILENCE = 64;

	// 9단계 일반마법
	public static final int LIGHTNING_STORM = 65;
	public static final int FOG_OF_SLEEPING = 66;
	public static final int SHAPE_CHANGE = 67; // E: POLYMORPH
	public static final int IMMUNE_TO_HARM = 68;
	public static final int MASS_TELEPORT = 69;
	public static final int FIRE_STORM = 70;
	public static final int DECAY_POTION = 71;
	public static final int COUNTER_DETECTION = 72;

	// 10단계 일반마법
	//public static final int CREATE_MAGICAL_WEAPON = 73;
	public static final int METEOR_STRIKE = 74;
	public static final int GREATER_RESURRECTION = 75;
	public static final int IceMETEOR_STRIKE = 76;
	public static final int DISINTEGRATE = 77; // E: DESTROY
	public static final int ABSOLUTE_BARRIER = 78;
	public static final int ADVANCE_SPIRIT = 79;
	public static final int ICE_SPIKE = 80;

	// none = 81 - 86
	/*
	 * Knight skills
	 */
	public static final int SHOCK_STUN = 87; // E: STUN_SHOCK
	public static final int REDUCTION_ARMOR = 88;
	public static final int BOUNCE_ATTACK = 89;
	public static final int SOLID_CARRIAGE = 90;
	public static final int COUNTER_BARRIER = 91;

	// none = 92-96
	/*
	 * Dark Spirit Magic
	 */
	public static final int BLIND_HIDING = 97;
	public static final int ENCHANT_VENOM = 98;
	public static final int SHADOW_ARMOR = 99;
	public static final int BRING_STONE = 100;
	public static final int MOVING_ACCELERATION = 101; // E: PURIFY_STONE
	public static final int BURNING_SPIRIT = 566;
	public static final int DARK_BLIND = 103;
	public static final int VENOM_RESIST = 104;
	public static final int DOUBLE_BRAKE = 105;
	public static final int UNCANNY_DODGE = 106;
	public static final int SHADOW_FANG = 107;
	public static final int FINAL_BURN = 108;
	public static final int DRESS_MIGHTY = 109;
	public static final int DRESS_DEXTERITY = 110;
	public static final int DRESS_EVASION = 565;
	public static final int ARMOR_BREAK = 112;
	public static final int LUCIFER = 234;
	public static final int ASSASSIN = 233;

	public static final int 출석체크 = 6000;
	public static final int 출석체크ディレイ = 5000;

	/** 4대용 마안 버프 */
	public static final int ANTA_MAAN = 7671; // 지룡의 마안
	public static final int FAFU_MAAN = 7672; // 수룡의 마안
	public static final int VALA_MAAN = 7673; // 화룡의 마안
	public static final int LIND_MAAN = 7674; // 풍룡의 마안
	public static final int BIRTH_MAAN = 7675; // 탄생의 마안
	public static final int SHAPE_MAAN = 7676; // 형상의 마안
	public static final int LIFE_MAAN = 7677; // 생명의 마안
	public static final int HALPAS_MAAN = 7678; // 흑룡의 마안
	public static final int NEVER_MAAN = 7679; // 절대의 마안

	/** 4대용 마안 버프 */

	/** 혈맹버프 */
	public static final int CLAN_BUFF1 = 505; //혈맹버프
	public static final int CLAN_BUFF2 = 506;
	public static final int CLAN_BUFF3 = 507;
	public static final int CLAN_BUFF4 = 508;
	// none = 112
	/*
	 * Royal Magic
	 */
	public static final int TRUE_TARGET = 113;
	public static final int GLOWING_AURA = 114;
	public static final int SHINING_AURA = 115;
	public static final int SHINING_ARMOR = 116; // E: CALL_PLEDGE_MEMBER
	public static final int BRAVE_AURA = 117;
	public static final int 마제스티 = 118;

	// unknown = 119 - 120
	// none = 121 - 128
	/*
	 * Spirit Magic
	 */
	public static final int RESIST_MAGIC = 129;
	public static final int BODY_TO_MIND = 130;
	public static final int TELEPORT_TO_MOTHER = 131;
	public static final int TRIPLE_ARROW = 132;
	public static final int ELEMENTAL_FALL_DOWN = 133;
	public static final int COUNTER_MIRROR = 134;

	// none = 135 - 136
	public static final int CLEAR_MIND = 137;
	public static final int RESIST_ELEMENTAL = 138;

	// none = 139 - 144
	public static final int RETURN_TO_NATURE = 145;
	public static final int BLOODY_SOUL = 146; // E: BLOOD_TO_SOUL
	public static final int ELEMENTAL_PROTECTION = 147; // E:PROTECTION_FROM_ELEMENTAL
	public static final int FIRE_WEAPON = 148;
	public static final int WIND_SHOT = 149;
	public static final int WIND_WALK = 150;
	public static final int FIRE_SHIELD = 151;
	public static final int ENTANGLE = 152; // E:퀘이크 만득이
	public static final int ERASE_MAGIC = 153;
	public static final int LESSER_ELEMENTAL = 154; // E:SUMMON_LESSER_ELEMENTAL
	public static final int FIRE_BLESS = 155; // E: BLESS_OF_FIRE
	public static final int STORM_EYE = 156; // E: EYE_OF_STORM
	public static final int EARTH_BIND = 157;
	public static final int NATURES_TOUCH = 158;
	public static final int EARTH_BLESS = 159; // E: BLESS_OF_EARTH
	public static final int AQUA_PROTECTER = 160;
	public static final int AREA_OF_SILENCE = 161;
	public static final int GREATER_ELEMENTAL = 162; // E:SUMMON_GREATER_ELEMENTAL
	public static final int BURNING_WEAPON = 163;
	public static final int NATURES_BLESSING = 164;
	public static final int CALL_OF_NATURE = 165; // E: NATURES_MIRACLE
	public static final int STORM_SHOT = 166;
	public static final int cyclone = 167;
	public static final int IRON_SKIN = 168;
	public static final int EXOTIC_VITALIZE = 169;
	public static final int WATER_LIFE = 170;
	public static final int ELEMENTAL_FIRE = 171;
	public static final int STORM_WALK = 172;
	public static final int POLLUTE_WATER = 173;
	public static final int STRIKER_GALE = 174;
	public static final int SOUL_OF_FLAME = 175;
	public static final int ADDITIONAL_FIRE = 176;
	public static final int HURRICANE = 178;
	public static final int FOCUS_WAVE = 177;
	public static final int SAND_STORM = 179;


	// 용기사
	public static final int DRAGON_SKIN = 181;
	public static final int BURNING_SLASH = 182;
	public static final int DESTROY = 183;
	public static final int MAGMA_BREATH = 184;
	public static final int SCALES_EARTH_DRAGON = 185;
	public static final int BLOOD_LUST = 186;
	public static final int FOU_SLAYER = 187;
	public static final int FEAR = 188;
	public static final int SHOCK_SKIN = 189;
	public static final int SCALES_WATER_DRAGON = 190;
	public static final int MORTAL_BODY = 191;
	public static final int THUNDER_GRAB = 192;
	public static final int HORROR_OF_DEATH = 193;
	public static final int FREEZING_BREATH = 194;
	public static final int SCALES_FIRE_DRAGON = 195;
	public static final int SCALES_Lind_DRAGON = 197;

	// 환술사
	public static final int MIRROR_IMAGE = 201;
	public static final int CONFUSION = 202;
	public static final int SMASH = 203;
	public static final int IllUSION_OGRE = 204;
	public static final int CUBE_IGNITION = 205;
	public static final int CONCENTRATION = 206;
	public static final int MIND_BREAK = 207;
	public static final int BONE_BREAK = 208;
	public static final int IllUSION_LICH = 209;
	public static final int CUBE_QUAKE = 210;
	public static final int PATIENCE = 211;
	public static final int PHANTASM = 212;
	public static final int AM_BREAK = 213;
	public static final int IllUSION_DIAMONDGOLEM = 214;
	public static final int CUBE_SHOCK = 215;
	public static final int INSIGHT = 216;
	public static final int PANIC = 217;
	public static final int JOY_OF_PAIN = 218;
	public static final int IllUSION_AVATAR = 219;
	public static final int CUBE_BALANCE = 220;
	public static final int SKILLS_END = 220;


	/**검사 액티브 스킬*/
	public static final int JUDGEMENT = 235;
	public static final int PHANTOM = 236;
	public static final int PANTERA = 237;
	public static final int HELLFIRE = 238;
	public static final int BLADE = 239;
	/**검사 액티브 스킬*/

	/**검사 패시브 스킬*/
	public static final int INFINITY_A = 550;
	public static final int INFINITY_B = 551;
	public static final int INFINITY_D = 552;
	public static final int DAMASCUS = 553;
	public static final int PARADOX = 554;
	public static final int GROUS = 555;
	public static final int RAGE = 556;
	public static final int PHANTOM_R = 557;
	public static final int PHANTOM_D = 558;
	public static final int FLAME = 559;
	public static final int INFINITY_BL = 560;
	public static final int SURVIVE = 561;
	public static final int PANTERA_S = 562;
	/**검사 패시브 스킬*/

	/*
	 * Status
	 */
	public static final int STATUS_DRAGONPERL = 999;
	public static final int STATUS_BEGIN = 1000;
	public static final int STATUS_BRAVE = 1000;

	public static final int STATUS_RanKing = 6420;//

	public static final int STATUS_진귀한식량 = 1030;
	public static final int STATUS_마녀마력회복제 = 1031;
	public static final int STATUS_안전모드 = 1032;
	public static final int STATUS_힘업6 = 1033;
	public static final int STATUS_힘업7 = 1034;
	public static final int STATUS_덱업6 = 1035;
	public static final int STATUS_덱업7 = 1036;

	public static final int STATUS_AUTOROOT = 76261;// by사부 오토루팅 명령어를위한
	public static final int STATUS_MENT = 7626; // 추가 by사부 멘트명령어
	public static final int STATUS_HASTE = 1001;
	public static final int STATUS_BLUE_POTION = 1002;
	public static final int STATUS_UNDERWATER_BREATH = 1003;
	public static final int STATUS_WISDOM_POTION = 1004;
	public static final int 사엘 = 5959; // 사엘버프
	public static final int 크레이 = 5858; // 사엘버프
	public static final int 군터의조언 = 6060;
	public static final int DRAGON_GROWTH_BUFF = 6072;
	public static final int CLAN_BLESS_BUFF = 6073;
	public static final int BUFF_IN_THE_HALFWAY_CIRCLE = 6062;

	/** 안타라스 / 파푸리온 마안 버프 */
	public static final int DRAGONBLOOD_A = 55001;
	public static final int DRAGONBLOOD_P = 55002;
	public static final int DRAGONBLOOD_L = 55003;
	public static final int DRAGONRAID_BUFF = 55005;

	public static final int 피씨방_버프 = 777888;

	public static final int 강화버프_활력 = 56000;
	public static final int 강화버프_공격 = 56001;
	public static final int 강화버프_방어 = 56002;
	public static final int 강화버프_마법 = 56003;
	public static final int 강화버프_스턴 = 56004;
	public static final int 강화버프_홀드 = 56005;

	public static final int 강화버프_완력 = 56006;
	public static final int 강화버프_민첩 = 56007;
	public static final int 강화버프_지식 = 56008;
	public static final int 강화버프_지혜 = 56009;


	public static final int STATUS_POISON = 1006;
	public static final int STATUS_POISON_SILENCE = 1007;
	public static final int STATUS_POISON_PARALYZING = 1008;
	public static final int STATUS_POISON_PARALYZED = 1009;
	public static final int STATUS_CURSE_PARALYZING = 1010;
	public static final int STATUS_CURSE_PARALYZED = 1011;
	public static final int STATUS_FLOATING_EYE = 1012;
	public static final int STATUS_HOLY_WATER = 1013;
	public static final int STATUS_HOLY_MITHRIL_POWDER = 1014;
	public static final int STATUS_HOLY_WATER_OF_EVA = 1015;
	public static final int STATUS_ELFBRAVE = 1016;
	public static final int STATUS_CANCLEEND = 1016;
	public static final int STATUS_CURSE_BARLOG = 1017;
	public static final int STATUS_CURSE_YAHEE = 1018;
	public static final int STATUS_PET_FOOD = 1019;
	public static final int STATUS_PINK_NAME = 1020;
	public static final int STATUS_TIKAL_BOSSJOIN = 1021;
	public static final int STATUS_TIKAL_BOSSDIE = 1022;
	public static final int STATUS_CHAT_PROHIBITED = 1023;
	public static final int STATUS_COMA_3 = 1024;
	public static final int STATUS_COMA_5 = 1025;
	public static final int FEATHER_BUFF_A = 1026;
	public static final int FEATHER_BUFF_B = 1027;
	public static final int FEATHER_BUFF_C = 1028;
	public static final int FEATHER_BUFF_D = 1029;
	// public static final int STATUS_END = 1023;
	public static final int GMSTATUS_BEGIN = 2000;
	public static final int GMSTATUS_INVISIBLE = 2000;
	public static final int GMSTATUS_HPBAR = 2001;
	public static final int GMSTATUS_SHOWTRAPS = 2002;
	public static final int GMSTATUS_END = 2002;
	public static final int COOKING_NOW = 2999;
	public static final int COOKING_BEGIN = 3000;
	/** 1차요리 효과 (노멀) */
	public static final int COOKING_1_0_N = 3000;
	public static final int COOKING_1_1_N = 3001;
	public static final int COOKING_1_2_N = 3002;
	public static final int COOKING_1_3_N = 3003;
	public static final int COOKING_1_4_N = 3004;
	public static final int COOKING_1_5_N = 3005;
	public static final int COOKING_1_6_N = 3006;
	public static final int COOKING_1_7_N = 3007;

	/** 2차요리 효과 (노멀) */
	public static final int COOKING_1_8_N = 3008;
	public static final int COOKING_1_9_N = 3009;
	public static final int COOKING_1_10_N = 3010;
	public static final int COOKING_1_11_N = 3011;
	public static final int COOKING_1_12_N = 3012;
	public static final int COOKING_1_13_N = 3013;
	public static final int COOKING_1_14_N = 3014;
	public static final int COOKING_1_15_N = 3015;

	/** 3차요리 효과 (노멀) */
	public static final int COOKING_1_16_N = 3016;
	public static final int COOKING_1_17_N = 3017;
	public static final int COOKING_1_18_N = 3018;
	public static final int COOKING_1_19_N = 3019;
	public static final int COOKING_1_20_N = 3020;
	public static final int COOKING_1_21_N = 3021;
	public static final int COOKING_1_22_N = 3022;
	public static final int COOKING_1_23_N = 3023;

	/** 1차요리 효과 (환상) */
	public static final int COOKING_1_0_S = 3050;
	public static final int COOKING_1_1_S = 3051;
	public static final int COOKING_1_2_S = 3052;
	public static final int COOKING_1_3_S = 3053;
	public static final int COOKING_1_4_S = 3054;
	public static final int COOKING_1_5_S = 3055;
	public static final int COOKING_1_6_S = 3056;
	public static final int COOKING_1_7_S = 3057;

	/** 2차요리 효과 (환상) */
	public static final int COOKING_1_8_S = 3058;
	public static final int COOKING_1_9_S = 3059;
	public static final int COOKING_1_10_S = 3060;
	public static final int COOKING_1_11_S = 3061;
	public static final int COOKING_1_12_S = 3062;
	public static final int COOKING_1_13_S = 3063;
	public static final int COOKING_1_14_S = 3064;
	public static final int COOKING_1_15_S = 3065;

	/** 3차요리 효과 (환상) */
	public static final int COOKING_1_16_S = 3066;
	public static final int COOKING_1_17_S = 3067;
	public static final int COOKING_1_18_S = 3068;
	public static final int COOKING_1_19_S = 3069;
	public static final int COOKING_1_20_S = 3070;
	public static final int COOKING_1_21_S = 3071;
	public static final int COOKING_1_22_S = 3072;
	public static final int COOKING_1_23_S = 3073;

	public static final int SPECIAL_COOKING = 3074;
	public static final int COOKING_END = 3075;
	public static final int SPECIAL_COOKING2 = 3076;

	public static final int COOKING_NEW_POWERFUL_WAGYU_STEAK = 3077;
	public static final int COOKING_NEW_QUICK_BOILED_SALMON = 3078;
	public static final int COOKING_NEW_CLEVER_TURKEY_ROAST = 3079;
	public static final int COOKING_NEW_ORDEAL_CHICKEN_SOUP = 3080;
	public static final int 메티스축복주문서 = 3081;
	public static final int 메티스정성요리 = 3082;
	public static final int 메티스정성스프 = 3083;
	public static final int COOKING_SMALL_NOODLE_DISHES = 3084;
	public static final int COOKING_SMALL_PORTABLE_BEVERAGE = 3085;
	public static final int WIND_SHACKLE = 3086; //몹셰클
	public static final int COOKING_NEW_TAM_POWERFUL_WAGYU_STEAK = 3087;
	public static final int COOKING_NEW_TAM_QUICK_BOILED_SALMON = 3088;
	public static final int COOKING_NEW_TAM_CLEVER_TURKEY_ROAST = 3089;
	public static final int COOKING_NEW_TAM_ORDEAL_CHICKEN_SOUP = 3090;
	public static final int ICE_LANCE = 3091;
	public static final int STATUS_FREEZE = 10071;
	public static final int CURSE_PARALYZE2 = 10101;

	public static final int STATUS_SPOT1 = 20072;
	public static final int STATUS_SPOT2 = 20073;
	public static final int STATUS_SPOT3 = 20074;
	public static final int STATUS_SPOT4 = 20174;

	public static final int STATUS_IGNITION = 20075;
	public static final int STATUS_QUAKE = 20076;
	public static final int STATUS_SHOCK = 20077;
	public static final int STATUS_BALANCE = 20078;
	public static final int 린드가호ディレイ = 8178;
	public static final int 할파스가호lv1 = 100000;
	public static final int STATUS_FRUIT = 20079;
	public static final int STATUS_OVERLAP = 20080;
	public static final int EXP_POTION = 20081;
	public static final int EXP_POTION_cash = 30080;
	public static final int EXP_POTION2 = 9278;
	public static final int EXP_POTION3 = 9279;
	public static final int DRAGON_EME_1 = 7785;
	public static final int DRAGON_EME_2 = 7786;
	public static final int DRAGON_PUPLE = 7787;
	public static final int DRAGON_TOPAZ = 7788;
	public static final int Tam_Fruit1 = 7791;
	public static final int Tam_Fruit2 = 7792;
	public static final int Tam_Fruit3 = 7793;
    public static final int Tam_Fruit4 = 7794;
	public static final int Tam_Fruit5 = 7795;
	public static final int STATUS_BLUE_POTION2 = 20082;
	public static final int STATUS_DESHOCK = 20083;
	public static final int STATUS_CUBE = 20084;
	public static final int STATUS_CASHSCROLL = 7893;
	public static final int STATUS_CASHSCROLL2 = 7894;
	public static final int STATUS_CASHSCROLL3 = 7895;
	public static final int STATUS_KURTZ_FIGHTER = 16551;
	public static final int STATUS_KURTZ_SAGE = 16553;
	public static final int STATUS_KURTZ_BOWMASTER = 16552;
	public static final int STATUS_COOL_ICE_SCULPTURE = 84850;

	public static final int STATUS_BLUE_POTION3 = 22004;

	public static final int MOB_SLOW_18 = 30000; // 슬로우 18번모션
	public static final int MOB_SLOW_1 = 30001; // 슬로우 1번모션
	public static final int MOB_CURSEPARALYZ_19 = 30002; // 커스 19번모션
	public static final int MOB_COCA = 30003; // 코카트리스 얼리기공격
	public static final int MOB_BASILL = 30004; // 바실리스크 얼리기에볼
	public static final int MOB_RANGESTUN_19 = 30005; // 범위스턴 19번모션
	public static final int MOB_RANGESTUN_18 = 30006; // 범위스턴 18번모션
	public static final int MOB_CURSEPARALYZ_18 = 30007; // 커스 18번모션
	public static final int MOB_DISEASE_30 = 30008; // 디지즈 30번모션
	public static final int MOB_WEAKNESS_1 = 30009; // 위크니스 1번모션
	public static final int MOB_DISEASE_1 = 30079; // 디지즈 1번모션
	public static final int MOB_SHOCKSTUN_30 = 30081; // 쇼크스턴 30번모션
	public static final int MOB_WINDSHACKLE_1 = 30084; // 윈드셰클 1번모션

	public static final int ANTA_SKILL_1 = 10188; // 세이 리라프
	public static final int ANTA_SKILL_2 = 10189; // 티 세토르
	public static final int ANTA_SKILL_3 = 10190; // 뮤즈 삼
	public static final int ANTA_SKILL_4 = 10191;// 너츠 삼
	public static final int ANTA_SKILL_5 = 10192; // 티프 삼

	public static final int ANTA_SKILL_6 = 10193; // 리라프

	public static final int ANTA_SKILL_7 = 10194; // 켄 로우

	public static final int ANTA_SKILL_8 = 10195; // 티기르
	public static final int ANTA_SKILL_9 = 10196; // 켄 티기르

	public static final int ANTA_SKILL_10 = 10197; // 루오 타

	public static final int ANTA_SKILL_11 = 10198; // 케 네시

	public static final int ANTA_SKILL_12 = 10199; // 뮤즈 심

	public static final int ANTA_SKILL_13 = 10200; // 너츠 심

	public static final int ANTA_SKILL_14 = 10201; // 티프 심

	public static final int PAPOO_SKILL = 10502; // 리오타! 누스건 카푸
	public static final int PAPOO_SKILL1 = 10507; // 리오타! 피로이나
	public static final int PAPOO_SKILL2 = 10508; // 리오타! 라나 폰폰
	public static final int PAPOO_SKILL3 = 10509; // 리오나! 레오 폰폰

	public static final int PAPOO_SKILL4 = 10510; // 리오타!테나 론디르
	public static final int PAPOO_SKILL5 = 10512; // 리오타!네나 론디르
	public static final int PAPOO_SKILL6 = 10514; // 리오타!라나 오이므
	public static final int PAPOO_SKILL7 = 10515; // 리오타! 레포 오이므 //7870한테만적용
	public static final int PAPOO_SKILL8 = 10516; // 리오타! 테나 웨인라크
	public static final int PAPOO_SKILL9 = 10519; // 리오타! 네나 우누스
	public static final int PAPOO_SKILL10 = 10520; // 리오타! 오니즈 웨인라크
	public static final int PAPOO_SKILL11 = 10521; // 리오타! 오니즈 쿠스온 웨인라크

	public static final int 초보자도우미클릭ディレイ = 10522;
	public static final int 얼녀성인던ディレイ = 10523;
	public static final int 완력빙수 = 10524;
	public static final int 민첩빙수 = 10525;
	public static final int 지식빙수 = 10526;

	public static final int 단테스버프 = 10527;

	public static final int SPIRIT_OF_BLACK_DEATH = 10528;

	public static final int STATUS_LEVEL_UP_BONUS = 10529;

	public static final int 제로스리덕 = 10535;

	public static final int 토마호크 = 229;
	public static final int PERIOD_TICK = 226;
	public static final int POWER_GRIP = 228;
	public static final int DESPERADO = 230;
	public static final int 하울 = 225;

	public static final int PRIME = 241;
	public static final int FORCE_STUN = 242;
	public static final int ETERNITY = 243;
	public static final int AVENGER = 244;
	public static final int HALPHAS = 245;
	public static final int POTENTIAL = 246;
	public static final int DEMOLITION = 247;
	public static final int HEUKSA = 248;

	public static final int STATUS_WISDOM_POTION2 = 10530;
	public static final int BRAVE_AVATAR = 10531;
	public static final int 주군의버프 = 10534;

	public static final int PAPOO_구슬_ディレイ = 90000;
	public static final int 안식처스킬준비 = 4065;

	public static final int 가입신청ディレイ = 76271;
	public static final int 채팅파티버프 = 76272;

	public static final int 신고ディレイ = 76273;
	public static final int 파티초대ディレイ = 76274;

	public static final int RANKING_BUFF_1 = 80000;
	public static final int RANKING_BUFF_2 = 80001;
	public static final int RANKING_BUFF_3 = 80002;
	public static final int RANKING_BUFF_4 = 80003;
	public static final int RANKING_BUFF_5 = 80007;
	public static final int RANKING_BUFF_6 = 80008;
	public static final int RANKING_BUFF_7 = 80009;
	public static final int RANKING_BUFF_8 = 80010;
	public static final int RANKING_BUFF_9 = 80011;
	public static final int RANKING_BUFF_10 = 80012;
	public static final int RANKING_BUFF_11 = 80013;
	public static final int SEAL_BUFF = 80014;
	public static final int 드래곤의축복1단계 = 80015;
	public static final int 드래곤의축복2단계 = 80016;
	public static final int 드래곤의축복3단계 = 80017;
	public static final int KYULJUN_CASHSCROLL1 = 80018; //결전의 주문
	public static final int PC방 = 80019;
	public static final int RevengeTime = 80020;
	public static final int AINHASAD_GAHO = 80021;

	public static final int 정상의가호 = 80004;
	public static final int COMBO_BUFF = 80006; //콤보

	public static final int DEATH_HILL = 73;
	public static final int 앱솔루트블레이드 = 92;
	public static final int GRACE = 122;
	public static final int EMPIRE = 123;
	public static final int SOUL_BARRIER = 135;
	public static final int INFERNO = 136;
	//public static final int 디스트로이 = 196;
	public static final int IMPACT = 222;
	public static final int 포커스스피릿츠 = 223;
	public static final int MOBIUS = 224;
	public static final int RISING = 231;
	public static final int 프라이드 = 93;
	public static final int 블로우어택 = 94;
	public static final int ARMOR_BREAK_DISTINY = 510; // アーマーブレイク デスティニー
	public static final int DOUBLE_BREAK_DISTINY = 511;
	public static final int COUNTER_BARRIER_OLDHAND = 512;
	public static final int THUNDER_GRAP_BRAVE = 513;
	public static final int FOU_SLAYER_BRAVE = 514;
	public static final int DEATH_PERADO_ABSOLUTE = 515;
	public static final int DARK_HOSE = 516;
	public static final int FINAL_BURN_PASSIVE = 517;
	public static final int AURA_KIA = 518;
	public static final int LUCIFER_DISTINY = 567;
	public static final int REDUCTION_ARMOR_OLDHAND = 568;
	public static final int RAGING_FORCE = 569;
	public static final int SHADOW_TAB = 199;
	// SAFE POLY앤 dodo팩
	public static final int POLY_SAFE = 15846;
	public static final int TENKACHOUJI_BUFF = 15410;
	public static final int 순간이동지배 = 15847;
	//public static final int POLY_SAFE = 15846;
	public static final int VALA_BUFF = 50000; // 발라카스 혈흔 버프
	public static final int CHAINSWORD1 = 50002;
	public static final int CHAINSWORD2 = 50003;
	public static final int CHAINSWORD3 = 50004;
	public static final int 기란감옥시간 = 50005;

	/** 펫 관련 스킬 */
	public static final int Fighting = 5000;

	/** 성장의 나뭇잎 */
	/** 3919 30% 3920 50% 3921 100% */
	public static final int GrowthFoliage = 3919;

	/** 핏불의 예거밤(투지) */
	public static final int YeagerNight = 3922;

	public static final int DogBlood = 4001;

	/** 인던형 디버프 */
	public static final int DungeonPoison = 8200;
	public static final int DungeonFlare = 8201;

	/** 용무기 시리즈 */
	public static final int 발라카스의장검 = 18982;
	public static final int 발라카스의양손검 = 18986;
	public static final int 파푸리온의활 = 18998;
	public static final int 파푸리온의이도류 = 19002;
	public static final int 안타라스의지팡이 = 19010;
	public static final int 안타라스의도끼 = 19006;
	public static final int 린드비오르의키링크 = 18994;
	public static final int 린드비오르의체인소드 = 18990;

	public static final int HALPAS_FAITH_DELAY = 4454;
	public static final int HALPAS_FAITH_PVP_REDUC = 4453;
	public static final int HALPAS_FAITH_STANDBY = 4452;

	public static final int miso1 = 8135;//미소피아 공격축복
	public static final int miso2 = 8136;//미소피아 방어축복
	public static final int miso3 = 8137;//미소피아 성장축복

}
