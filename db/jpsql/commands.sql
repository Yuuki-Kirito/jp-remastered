/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb_remastered
Target Host: localhost
Target Database: l1jdb_remastered
Date: 2022/09/12 20:39:13
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for commands
-- ----------------------------
DROP TABLE IF EXISTS `commands`;
CREATE TABLE `commands` (
  `name` varchar(255) NOT NULL,
  `access_level` int(10) NOT NULL DEFAULT '9999',
  `class_name` varchar(255) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `commands` VALUES ('echo', '8888', 'L1Echo');
INSERT INTO `commands` VALUES ('setting', '8888', 'L1Status');
INSERT INTO `commands` VALUES ('summon', '8888', 'L1Summon');
INSERT INTO `commands` VALUES ('cleaning', '8888', 'L1DeleteGroundItem');
INSERT INTO `commands` VALUES ('addskill', '8888', 'L1AddSkill');
INSERT INTO `commands` VALUES ('level', '7777', 'L1Level');
INSERT INTO `commands` VALUES ('loc', '8888', 'L1Loc');
INSERT INTO `commands` VALUES ('desc', '8888', 'L1Describe');
INSERT INTO `commands` VALUES ('who', '7777', 'L1Who');
INSERT INTO `commands` VALUES ('allbuff', '8888', 'L1AllBuff');
INSERT INTO `commands` VALUES ('speed', '7777', 'L1Speed');
INSERT INTO `commands` VALUES ('adena', '8888', 'L1Adena');
INSERT INTO `commands` VALUES ('resettrap', '8888', 'L1ResetTrap');
INSERT INTO `commands` VALUES ('reloadtrap', '8888', 'L1ReloadTrap');
INSERT INTO `commands` VALUES ('showtrap', '8888', 'L1ShowTrap');
INSERT INTO `commands` VALUES ('gfxid', '8888', 'L1GfxId');
INSERT INTO `commands` VALUES ('invgfxid', '8888', 'L1InvGfxId');
INSERT INTO `commands` VALUES ('hpbar', '8888', 'L1HpBar');
INSERT INTO `commands` VALUES ('gm', '8888', 'L1GM');
INSERT INTO `commands` VALUES ('hometown', '8888', 'L1HomeTown');
INSERT INTO `commands` VALUES ('lvpresent', '8888', 'L1LevelPresent');
INSERT INTO `commands` VALUES ('present', '8888', 'L1Present');
INSERT INTO `commands` VALUES ('shutdown', '8888', 'L1Shutdown');
INSERT INTO `commands` VALUES ('item', '8888', 'L1CreateItem');
INSERT INTO `commands` VALUES ('itemset', '8888', 'L1CreateItemSet');
INSERT INTO `commands` VALUES ('buff', '8888', 'L1Buff');
INSERT INTO `commands` VALUES ('스킬', '8888', 'L1Burf');
INSERT INTO `commands` VALUES ('patrol', '8888', 'L1Patrol');
INSERT INTO `commands` VALUES ('banip', '8888', 'L1BanIp');
INSERT INTO `commands` VALUES ('chat', '8888', 'L1Chat');
INSERT INTO `commands` VALUES ('chatng', '7777', 'L1ChatNG');
INSERT INTO `commands` VALUES ('skick', '8888', 'L1SKick');
INSERT INTO `commands` VALUES ('kick', '8888', 'L1Kick');
INSERT INTO `commands` VALUES ('powerkick', '8888', 'L1PowerKick');
INSERT INTO `commands` VALUES ('accbankick', '7777', 'L1AccountBanKick');
INSERT INTO `commands` VALUES ('poly', '7777', 'L1Poly');
INSERT INTO `commands` VALUES ('ress', '8888', 'L1Ress');
INSERT INTO `commands` VALUES ('death', '8888', 'L1Kill');
INSERT INTO `commands` VALUES ('gmroom', '7777', 'L1GMRoom');
INSERT INTO `commands` VALUES ('topc', '7777', 'L1ToPC');
INSERT INTO `commands` VALUES ('move', '8888', 'L1Move');
INSERT INTO `commands` VALUES ('weather', '8888', 'L1ChangeWeather');
INSERT INTO `commands` VALUES ('tospawn', '8888', 'L1ToSpawn');
INSERT INTO `commands` VALUES ('f', '8888', 'L1Favorite');
INSERT INTO `commands` VALUES ('recall', '7777', 'L1Recall');
INSERT INTO `commands` VALUES ('partyrecall', '8888', 'L1PartyRecall');
INSERT INTO `commands` VALUES ('visible', '8888', 'L1Visible');
INSERT INTO `commands` VALUES ('invisible', '7777', 'L1Invisible');
INSERT INTO `commands` VALUES ('spawn', '8888', 'L1SpawnCmd');
INSERT INTO `commands` VALUES ('insert', '8888', 'L1InsertSpawn');
INSERT INTO `commands` VALUES ('설문', '8888', 'L1GMQuestion');
INSERT INTO `commands` VALUES ('action', '8888', 'L1Action');
INSERT INTO `commands` VALUES ('reload', '8888', 'L1Reload');
INSERT INTO `commands` VALUES ('tile', '8888', 'L1Tile');
INSERT INTO `commands` VALUES ('뻥', '8888', 'L1UserCalc');
INSERT INTO `commands` VALUES ('검사', '7777', 'L1CheckCharacter');
INSERT INTO `commands` VALUES ('무인추방', '8888', 'L1AutoKick');
INSERT INTO `commands` VALUES ('영자상점', '8888', 'L1NpcShopSwitch');
INSERT INTO `commands` VALUES ('찾기', '8888', 'L1Search');
INSERT INTO `commands` VALUES ('허상', '8888', 'L1Robot3');
INSERT INTO `commands` VALUES ('창조', '8888', 'SabuSpwan');
INSERT INTO `commands` VALUES ('부활', '8888', 'L1Ress');
INSERT INTO `commands` VALUES ('오토상점', '8888', 'L1AutoPcShop');
INSERT INTO `commands` VALUES ('데', '8888', 'L1DescId');
