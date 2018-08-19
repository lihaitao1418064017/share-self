/*
Navicat MySQL Data Transfer

Source Server         : home
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : share_self

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2018-08-07 15:52:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL COMMENT '涓婚敭ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `title` varchar(255) DEFAULT NULL COMMENT '鏍囬',
  `content` text COMMENT '鍐呭',
  `date` date DEFAULT NULL COMMENT '鍙戣〃鏃ユ湡',
  `article_type` int(11) DEFAULT NULL COMMENT '鏂囩珷绫诲瀷',
  `recommend` int(11) DEFAULT NULL COMMENT '鎺ㄨ崘',
  `user_id` int(11) DEFAULT NULL COMMENT '鍙戣〃鐢ㄦ埛',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for `comments`
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `content` text COMMENT '评论内容',
  `user_id` int(11) DEFAULT NULL COMMENT '用户',
  `article_id` int(11) DEFAULT NULL COMMENT '文章',
  `date` date DEFAULT NULL COMMENT '评论日期',
  `praise` int(11) DEFAULT NULL COMMENT '赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comments
-- ----------------------------

-- ----------------------------
-- Table structure for `photos`
-- ----------------------------
DROP TABLE IF EXISTS `photos`;
CREATE TABLE `photos` (
  `id` bigint(20) NOT NULL COMMENT '涓婚敭ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `url` VARCHAR (255) DEFAULT NULL COMMENT 'uri',
  `article_id` int(11) DEFAULT NULL COMMENT '鏂囩珷',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of photos
-- ----------------------------

-- ----------------------------


-- ----------------------------
-- Records of reply
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `age` varchar(255) DEFAULT NULL COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `headshot` varchar(255) DEFAULT NULL COMMENT '头像',
  `signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `role` int(11) DEFAULT NULL COMMENT '角色',
  `love` int(11) DEFAULT NULL COMMENT '喜欢',
  `focus` int(11) DEFAULT NULL COMMENT '关注',
  `article_sum` int(11) DEFAULT NULL COMMENT '文章数量',
  `address` varchar(255) DEFAULT NULL COMMENT '籍贯',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `video`
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` bigint(20) NOT NULL COMMENT '涓婚敭ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `url` VARCHAR(255) DEFAULT NULL COMMENT 'uri',
  `article_id` int(11) DEFAULT NULL COMMENT '鏂囩珷',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `focus_user` (
  `id` bigint(20) NOT NULL COMMENT '',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关注用户',
  `focus_user_id` bigint(20) DEFAULT NULL COMMENT '被关注用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `love_article` (
  `id` bigint(20) NOT NULL COMMENT '',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关注用户',
  `article_id` bigint(20) DEFAULT NULL COMMENT '关注的文章',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;