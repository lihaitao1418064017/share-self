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
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `url` VARCHAR (255) DEFAULT NULL COMMENT 'uri',
  `article_id` int(11) DEFAULT NULL COMMENT '文章id',
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
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
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
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `url` VARCHAR(255) DEFAULT NULL COMMENT 'uri',
  `article_id` int(11) DEFAULT NULL COMMENT '鏂囩珷',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `focus_user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关注用户',
  `focus_user_id` bigint(20) DEFAULT NULL COMMENT '被关注用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `love_article` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关注用户',
  `article_id` bigint(20) DEFAULT NULL COMMENT '关注的文章',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

   /*权限表*/
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `p_id` bigint(20) DEFAULT NULL COMMENT '',
  `type` INT(11) DEFAULT NULL COMMENT '',
  `name` VARCHAR(255) DEFAULT NULL COMMENT '',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `url` VARCHAR(255) DEFAULT NULL COMMENT '',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '',
  `perm_code` VARCHAR(255) DEFAULT NULL COMMENT '',
  `icon` VARCHAR(255) DEFAULT NULL COMMENT '',
  `create_time` DATE DEFAULT NULL COMMENT '',
  `modify_time` DATE DEFAULT NULL COMMENT '',
  `status` INT(11) DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    /*role*/
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `name` VARCHAR(255) DEFAULT NULL COMMENT '',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '',
  `create_time` DATE DEFAULT NULL COMMENT '',
  `modify_time` DATE DEFAULT NULL COMMENT '',
  `status` INT(11) DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*RolePermission*/
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id`  bigint(20) DEFAULT NULL COMMENT '',
  `permission_id`  bigint(20) DEFAULT NULL COMMENT '',
  `create_time` DATE DEFAULT NULL COMMENT '',
  `modify_time` DATE DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*UserRole*/
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id`  bigint(20) DEFAULT NULL COMMENT '',
  `user_id`  bigint(20) DEFAULT NULL COMMENT '',
  `create_time` DATE DEFAULT NULL COMMENT '',
  `modify_time` DATE DEFAULT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `friend_group` (
  `id` VARCHAR (255) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `group_name`  VARCHAR (255) DEFAULT NULL COMMENT '群名称',
  `user_id`  bigint(20) DEFAULT NULL COMMENT '群主',
  `group_introduce` VARCHAR(255) DEFAULT NULL COMMENT '群介绍',
  `group_avatar` VARCHAR(255) DEFAULT NULL COMMENT '群头像',
  `code` VARCHAR(255) DEFAULT NULL COMMENT '群标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `group_user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `group_id`  VARCHAR (255) DEFAULT NULL COMMENT '群id',
  `user_id`  bigint(20) DEFAULT NULL COMMENT '群成员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------------------------------
CREATE TABLE `im_friend` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` bigint(20) NOT NULL COMMENT 'ID',
  `friend_id`  VARCHAR (255) DEFAULT NULL COMMENT '朋友id',
  `user_id`  VARCHAR (20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `im_group` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `group_id`  VARCHAR (255) DEFAULT NULL COMMENT '',
  `name`  VARCHAR (20) DEFAULT NULL COMMENT '用户id',
  `online`  INTEGER (10) DEFAULT NULL COMMENT '',
  `avatar`  VARCHAR (20) DEFAULT NULL COMMENT '头像',
  `group_owner`  VARCHAR (20) DEFAULT NULL COMMENT '群主',

    `cmd`  VARCHAR (20) DEFAULT NULL COMMENT '',
  `create_time`  bigint(20) DEFAULT NULL COMMENT '',



  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `im_user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `nick`  VARCHAR (255) DEFAULT NULL COMMENT '',
  `avatar`  VARCHAR (255) DEFAULT NULL COMMENT '',
  `status`  VARCHAR (255) DEFAULT NULL COMMENT '',
  `sign`  VARCHAR (255) DEFAULT NULL COMMENT '',
  `terminal`  VARCHAR (255) DEFAULT NULL COMMENT '',
  `token`  VARCHAR (20) DEFAULT NULL COMMENT '用户id',
  `password`  VARCHAR (20) DEFAULT NULL COMMENT '头像',
  `loginname`  VARCHAR (20) DEFAULT NULL COMMENT '群主',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;