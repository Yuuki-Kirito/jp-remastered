/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb_remastered
Target Host: localhost
Target Database: l1jdb_remastered
Date: 2022/09/12 21:00:29
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for weapon_skill
-- ----------------------------
DROP TABLE IF EXISTS `weapon_skill`;
CREATE TABLE `weapon_skill` (
  `weapon_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `note` varchar(255) DEFAULT NULL,
  `probability` int(11) unsigned NOT NULL DEFAULT '0',
  `fix_damage` int(11) unsigned NOT NULL DEFAULT '0',
  `random_damage` int(11) unsigned NOT NULL DEFAULT '0',
  `area` int(11) NOT NULL DEFAULT '0',
  `skill_id` int(11) unsigned NOT NULL DEFAULT '0',
  `skill_time` int(11) unsigned NOT NULL DEFAULT '0',
  `effect_id` int(11) unsigned NOT NULL DEFAULT '0',
  `effect_target` int(11) unsigned NOT NULL DEFAULT '0',
  `arrow_type` int(11) unsigned NOT NULL DEFAULT '0',
  `attr` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`weapon_id`)
) ENGINE=MyISAM AUTO_INCREMENT=450014 DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 10240 kB';

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `weapon_skill` VALUES ('44', '古代のダークエルフソード', '0', '0', '0', '0', '11', '30', '745', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('47', 'サイレンス ソード', '15', '0', '0', '0', '64', '16', '2177', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('54', 'カーツ ソード', '10', '40', '35', '0', '0', '0', '10', '0', '0', '8');
INSERT INTO `weapon_skill` VALUES ('58', 'デスナイト フレイムブレード', '9', '40', '40', '0', '0', '0', '1811', '0', '0', '2');
INSERT INTO `weapon_skill` VALUES ('76', 'ロンドゥ デュアルブレード', '15', '35', '35', '0', '0', '0', '1805', '0', '0', '1');
INSERT INTO `weapon_skill` VALUES ('114', '군주검', '20', '50', '50', '0', '0', '0', '4842', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('121', 'アイスクイーン スタッフ', '30', '45', '45', '0', '0', '0', '1810', '0', '0', '4');
INSERT INTO `weapon_skill` VALUES ('203', 'バルログのツーハンド ソード', '10', '80', '90', '2', '0', '0', '762', '0', '0', '2');
INSERT INTO `weapon_skill` VALUES ('205', 'ルナ ロング ボウ', '12', '80', '80', '0', '0', '0', '6288', '0', '1', '0');
INSERT INTO `weapon_skill` VALUES ('256', 'ハロウィン パンプキン ロングソード', '15', '20', '15', '0', '0', '0', '2750', '0', '0', '1');
INSERT INTO `weapon_skill` VALUES ('257', 'ハロウィン ロングソード', '8', '20', '15', '0', '0', '0', '2750', '0', '0', '1');
INSERT INTO `weapon_skill` VALUES ('258', 'アルティメット ハロウィン ソード', '8', '20', '15', '0', '0', '0', '2750', '0', '0', '1');
INSERT INTO `weapon_skill` VALUES ('30114', '그랑카인의 심판', '100', '50', '50', '0', '0', '0', '2177', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('412000', '뇌신검', '7', '20', '20', '0', '0', '0', '10', '0', '0', '8');
INSERT INTO `weapon_skill` VALUES ('412003', '아크메이지의 지팡이', '7', '0', '0', '0', '56', '64', '2230', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('412004', '혹한의 창', '7', '20', '15', '3', '0', '0', '1804', '0', '0', '4');
INSERT INTO `weapon_skill` VALUES ('412005', '광풍의 도끼', '7', '30', '20', '4', '0', '0', '758', '0', '0', '8');
INSERT INTO `weapon_skill` VALUES ('413101', '악마왕의 양손검', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('413102', '악마왕의 이도류', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('413103', '악마왕의 지팡이', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('413104', '악마왕의 창', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0');
INSERT INTO `weapon_skill` VALUES ('413105', '악마왕의 활', '7', '0', '0', '0', '56', '20', '2230', '0', '1', '0');
INSERT INTO `weapon_skill` VALUES ('413106', '할로윈 지팡이', '15', '20', '15', '0', '0', '4', '2750', '0', '0', '0');
