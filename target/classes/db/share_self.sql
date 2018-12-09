/*
 Navicat Premium Data Transfer

 Source Server         : 47.99.216.57（阿里云）
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : 47.99.216.57:3306
 Source Schema         : share_self

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 08/12/2018 23:45:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `date` date DEFAULT NULL COMMENT '发表日期',
  `article_type` int(11) DEFAULT NULL COMMENT '文章类型',
  `recommend` int(11) DEFAULT NULL COMMENT '推荐',
  `user_id` int(11) DEFAULT NULL COMMENT '发表用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comments
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
-- Table structure for focus_user
-- ----------------------------
DROP TABLE IF EXISTS `focus_user`;
CREATE TABLE `focus_user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关注用户',
  `focus_user_id` bigint(20) DEFAULT NULL COMMENT '被关注用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for friend_group
-- ----------------------------
DROP TABLE IF EXISTS `friend_group`;
CREATE TABLE `friend_group` (
  `id` varchar(255) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `group_name` varchar(255) DEFAULT NULL COMMENT '群名称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '群主',
  `group_introduce` varchar(255) DEFAULT NULL COMMENT '群介绍',
  `group_avatar` varchar(255) DEFAULT NULL COMMENT '群头像',
  `code` varchar(255) DEFAULT NULL COMMENT '群标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for im_friend
-- ----------------------------
DROP TABLE IF EXISTS `im_friend`;
CREATE TABLE `im_friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `friend_id` varchar(255) DEFAULT NULL COMMENT '朋友id',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_friend
-- ----------------------------
BEGIN;
INSERT INTO `im_friend` VALUES (1, 1, '2', '1');
INSERT INTO `im_friend` VALUES (2, 1, '1', '2');
INSERT INTO `im_friend` VALUES (3, 1, '1', '3');
INSERT INTO `im_friend` VALUES (4, 1, '3', '1');
INSERT INTO `im_friend` VALUES (5, 1, '2', '3');
INSERT INTO `im_friend` VALUES (6, 1, '3', '2');
COMMIT;

-- ----------------------------
-- Table structure for im_group
-- ----------------------------
DROP TABLE IF EXISTS `im_group`;
CREATE TABLE `im_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(255) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '用户id',
  `online` int(10) DEFAULT NULL,
  `avatar` varchar(20) DEFAULT NULL COMMENT '头像',
  `group_owner` varchar(20) DEFAULT NULL COMMENT '群主',
  `cmd` int(255) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_group
-- ----------------------------
BEGIN;
INSERT INTO `im_group` VALUES (1, '1', 'we are family', 1, 'head', '1', 1, 1023412341, 1);
INSERT INTO `im_group` VALUES (2, '2', '拼命三猿', 1, '111', '2', 1, 23452345, 1);
COMMIT;

-- ----------------------------
-- Table structure for im_group_user
-- ----------------------------
DROP TABLE IF EXISTS `im_group_user`;
CREATE TABLE `im_group_user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `group_id` varchar(255) DEFAULT NULL COMMENT '群id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '群成员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_group_user
-- ----------------------------
BEGIN;
INSERT INTO `im_group_user` VALUES (1, 1, '1', 1);
INSERT INTO `im_group_user` VALUES (2, 1, '1', 2);
INSERT INTO `im_group_user` VALUES (3, 1, '2', 1);
INSERT INTO `im_group_user` VALUES (4, 1, '2', 2);
INSERT INTO `im_group_user` VALUES (5, 1, '2', 3);
COMMIT;

-- ----------------------------
-- Table structure for im_user
-- ----------------------------
DROP TABLE IF EXISTS `im_user`;
CREATE TABLE `im_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `nick` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `sign` varchar(255) DEFAULT NULL,
  `terminal` varchar(255) DEFAULT NULL,
  `token` varchar(20) DEFAULT NULL COMMENT '用户id',
  `password` varchar(20) DEFAULT NULL COMMENT '头像',
  `loginname` varchar(20) DEFAULT NULL COMMENT '群主',
  `tenant_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_user
-- ----------------------------
BEGIN;
INSERT INTO `im_user` VALUES (1, '李海涛', '李海涛', '1', '123', '1', '1', '123456', 'lihaitao', 1);
INSERT INTO `im_user` VALUES (2, '刘浩', '刘浩', '1', '123', '1', '1', '123456', 'liuhao', 1);
INSERT INTO `im_user` VALUES (3, '刘宇鹏', '刘宇鹏', '1', '123', '1', '1', '123456', 'liuyupeng', 1);
COMMIT;

-- ----------------------------
-- Table structure for love_article
-- ----------------------------
DROP TABLE IF EXISTS `love_article`;
CREATE TABLE `love_article` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关注用户',
  `article_id` bigint(20) DEFAULT NULL COMMENT '关注的文章',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `p_id` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `url` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `perm_code` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `modify_time` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for photos
-- ----------------------------
DROP TABLE IF EXISTS `photos`;
CREATE TABLE `photos` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `url` varchar(255) DEFAULT NULL COMMENT 'uri',
  `article_id` int(11) DEFAULT NULL COMMENT '文章id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `description` varchar(255) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `modify_time` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `modify_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
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
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `role` int(11) DEFAULT NULL COMMENT '角色',
  `love` int(11) DEFAULT NULL COMMENT '喜欢',
  `focus` int(11) DEFAULT NULL COMMENT '关注',
  `article_sum` int(11) DEFAULT NULL COMMENT '文章数量',
  `address` varchar(255) DEFAULT NULL COMMENT '籍贯',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `modify_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `url` varchar(255) DEFAULT NULL COMMENT 'uri',
  `article_id` int(11) DEFAULT NULL COMMENT '鏂囩珷',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
