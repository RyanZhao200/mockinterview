/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : mockinterview

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-12-08 23:03:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for c_admin
-- ----------------------------
DROP TABLE IF EXISTS `c_admin`;
CREATE TABLE `c_admin` (
  `aid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `head_url` varchar(255) DEFAULT NULL COMMENT '头像链接',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='管理员';

-- ----------------------------
-- Table structure for c_article
-- ----------------------------
DROP TABLE IF EXISTS `c_article`;
CREATE TABLE `c_article` (
  `aid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `uid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章内容';

-- ----------------------------
-- Table structure for c_url
-- ----------------------------
DROP TABLE IF EXISTS `c_url`;
CREATE TABLE `c_url` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `url_name` varchar(255) DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片链接';

-- ----------------------------
-- Table structure for f_comment
-- ----------------------------
DROP TABLE IF EXISTS `f_comment`;
CREATE TABLE `f_comment` (
  `cid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `comment` text COMMENT '评论内容',
  `pid` int(20) DEFAULT NULL COMMENT '帖子ID',
  `uid` int(20) DEFAULT NULL COMMENT '用户ID',
  `comment_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  `parent_id` int(20) DEFAULT NULL COMMENT '父ID',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='帖子评论';

-- ----------------------------
-- Table structure for f_forum
-- ----------------------------
DROP TABLE IF EXISTS `f_forum`;
CREATE TABLE `f_forum` (
  `pid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `tid` bigint(20) DEFAULT NULL COMMENT '类别ID',
  `uid` int(20) DEFAULT NULL COMMENT '创建人ID',
  `scan_count` bigint(20) DEFAULT NULL COMMENT '浏览量',
  `comment_count` varchar(255) DEFAULT NULL COMMENT '评论数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '跟新时间',
  `reply_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近回复时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='帖子';

-- ----------------------------
-- Table structure for f_type
-- ----------------------------
DROP TABLE IF EXISTS `f_type`;
CREATE TABLE `f_type` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '帖子类别ID',
  `type_name` varchar(255) DEFAULT NULL COMMENT '类别名称',
  `order_no` int(20) DEFAULT NULL COMMENT '帖子顺序',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='帖子类别';

-- ----------------------------
-- Table structure for j_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `j_evaluation`;
CREATE TABLE `j_evaluation` (
  `eid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `comment` text COMMENT '内容',
  `grade` int(20) DEFAULT NULL COMMENT '评星',
  `iid` bigint(20) DEFAULT NULL COMMENT '面试官ID',
  `fid` int(20) DEFAULT NULL COMMENT '评价者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='求职者对面试官评价';

-- ----------------------------
-- Table structure for j_finder
-- ----------------------------
DROP TABLE IF EXISTS `j_finder`;
CREATE TABLE `j_finder` (
  `fid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '求职者ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `head_url` varchar(255) DEFAULT NULL COMMENT '头像链接',
  `signature` text COMMENT '签名',
  `sex` int(2) DEFAULT NULL COMMENT '性别(1:女,0:男)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_activate` int(2) DEFAULT NULL COMMENT '是否激活（1：yes）',
  `activate_code` varchar(255) DEFAULT NULL COMMENT '激活码',
  `qq` varchar(255) DEFAULT NULL COMMENT 'QQ',
  `weixin` varchar(255) DEFAULT NULL COMMENT '微信',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称（已经丢弃）',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='求职者';

-- ----------------------------
-- Table structure for j_interviewer
-- ----------------------------
DROP TABLE IF EXISTS `j_interviewer`;
CREATE TABLE `j_interviewer` (
  `iid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '面试官ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `head_url` varchar(255) DEFAULT NULL COMMENT '头像链接',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别(1:女,0:男)',
  `description` text COMMENT '个人介绍',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `work_year` int(11) DEFAULT NULL COMMENT '工作年限',
  `is_activate` int(2) DEFAULT '0' COMMENT '是否激活（1：激活，0：未激活）',
  `activate_code` varchar(255) DEFAULT NULL COMMENT '激活码',
  `is_certification` int(2) DEFAULT '0' COMMENT '是否认证（1：yes,0:no）',
  `grade` int(11) DEFAULT NULL COMMENT '评分等级',
  `cost` varchar(255) DEFAULT '50' COMMENT '收费',
  `qq` varchar(255) DEFAULT NULL COMMENT 'QQ',
  `weixin` varchar(255) DEFAULT NULL COMMENT '微信',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `blog_url` varchar(255) DEFAULT NULL COMMENT '博客地址',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称（已弃用）',
  PRIMARY KEY (`iid`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COMMENT='面试官';

-- ----------------------------
-- Table structure for j_interviewer_type
-- ----------------------------
DROP TABLE IF EXISTS `j_interviewer_type`;
CREATE TABLE `j_interviewer_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '中间表ID',
  `iid` bigint(20) DEFAULT NULL COMMENT '面试官ID',
  `tid` bigint(20) DEFAULT NULL COMMENT '类别ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='面试官和类别中间表';

-- ----------------------------
-- Table structure for j_type
-- ----------------------------
DROP TABLE IF EXISTS `j_type`;
CREATE TABLE `j_type` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `type_name` varchar(255) NOT NULL COMMENT '栏目名称',
  `order_no` int(11) DEFAULT NULL COMMENT '栏目顺序',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父栏目ID',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='IT类别';
