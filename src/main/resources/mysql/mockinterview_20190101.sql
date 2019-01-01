/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : mockinterview

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2019-01-01 11:00:46
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
-- Table structure for c_follower
-- ----------------------------
DROP TABLE IF EXISTS `c_follower`;
CREATE TABLE `c_follower` (
  `fid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `followers_uid` int(11) DEFAULT NULL COMMENT '被关注者ID',
  `followers_type` varchar(4) DEFAULT NULL COMMENT '被关注者（1、求职者，2、面试官）',
  `following_uid` int(11) DEFAULT NULL COMMENT '关注者ID',
  `following_type` varchar(4) DEFAULT NULL COMMENT '关注者（1、求职者，2、面试官）',
  `follow_status` varchar(4) DEFAULT NULL COMMENT '关注的状态（1、关注，2、取消关注，3、拉黑）',
  `follow_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '关注时间',
  `unfollow_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '取消关注时间',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='关注表';

-- ----------------------------
-- Table structure for c_message
-- ----------------------------
DROP TABLE IF EXISTS `c_message`;
CREATE TABLE `c_message` (
  `mid` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `content` varchar(50) DEFAULT NULL COMMENT '消息内容',
  `message_url` varchar(30) DEFAULT NULL COMMENT '消息链接',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_type` varchar(4) DEFAULT NULL COMMENT '用户类别（1、求职者，2、面试官）',
  `message_type` varchar(4) DEFAULT NULL COMMENT '消息类别（1：论坛；2：面试）',
  `oid` int(11) DEFAULT NULL COMMENT '订单ID',
  `status_type` varchar(4) DEFAULT NULL COMMENT '状态类别（面试：1、待付款，2、待面试，3、待结单，4、待评价，5、面试结束；论坛：1、帖子，2、评论）',
  `message_status` varchar(4) DEFAULT NULL COMMENT '消息状态（1、未读，2、已读，3、已删）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最近更新时间',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='消息表（论坛、面试）';

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
  `user_type` varchar(4) DEFAULT NULL COMMENT '用户类别（1、求职者，2、面试官）',
  `comment_status` varchar(4) DEFAULT NULL COMMENT '评论状态（1、正常，2、删除）',
  `comment_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '评论时间',
  `parent_id` int(20) DEFAULT NULL COMMENT '父ID',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='帖子评论';

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
  `user_type` varchar(4) DEFAULT NULL COMMENT '用户类别（1、求职者，2、面试官）',
  `forum_status` varchar(4) DEFAULT NULL COMMENT '帖子状态（1、正常，2、删除）',
  `scan_count` bigint(20) DEFAULT NULL COMMENT '浏览量',
  `comment_count` varchar(255) DEFAULT NULL COMMENT '评论数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '跟新时间',
  `reply_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近回复时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='帖子';

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
-- Table structure for j_chat
-- ----------------------------
DROP TABLE IF EXISTS `j_chat`;
CREATE TABLE `j_chat` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '聊天消息ID',
  `message` varchar(255) DEFAULT NULL COMMENT '消息内容',
  `send_uid` int(11) DEFAULT NULL COMMENT '发送人ID',
  `send_type` varchar(255) DEFAULT NULL COMMENT '发送人类别（1求职者，2、面试官）',
  `accept_uid` int(11) DEFAULT NULL COMMENT '接受者ID',
  `accept_type` varchar(255) DEFAULT NULL COMMENT '接受者类别',
  `message_status` varchar(255) DEFAULT NULL COMMENT '消息状态（暂时不用）',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='聊天记录表';

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
  `oid` int(11) DEFAULT NULL COMMENT '订单ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='求职者对面试官评价';

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
-- Table structure for j_flow
-- ----------------------------
DROP TABLE IF EXISTS `j_flow`;
CREATE TABLE `j_flow` (
  `fid` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `flow_num` varchar(30) DEFAULT NULL COMMENT '流水号',
  `order_num` varchar(30) DEFAULT NULL COMMENT '订单号',
  `paid_amount` varchar(30) DEFAULT NULL COMMENT '支付金额',
  `finder_id` int(11) DEFAULT NULL COMMENT '求职者ID（付款人ID）',
  `interviewer_id` int(11) DEFAULT NULL COMMENT '面试官ID（准收款人ID）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='流水表';

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
  `grade` float(4,1) DEFAULT NULL COMMENT '评分等级',
  `cost` varchar(255) DEFAULT '50' COMMENT '收费',
  `qq` varchar(255) DEFAULT NULL COMMENT 'QQ',
  `weixin` varchar(255) DEFAULT NULL COMMENT '微信',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `blog_url` varchar(255) DEFAULT NULL COMMENT '博客地址',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称（已弃用）',
  PRIMARY KEY (`iid`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COMMENT='面试官';

-- ----------------------------
-- Table structure for j_interviewer_type
-- ----------------------------
DROP TABLE IF EXISTS `j_interviewer_type`;
CREATE TABLE `j_interviewer_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '中间表ID',
  `iid` bigint(20) DEFAULT NULL COMMENT '面试官ID',
  `tid` bigint(20) DEFAULT NULL COMMENT '类别ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='面试官和类别中间表';

-- ----------------------------
-- Table structure for j_order
-- ----------------------------
DROP TABLE IF EXISTS `j_order`;
CREATE TABLE `j_order` (
  `oid` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_num` varchar(30) DEFAULT NULL COMMENT '订单号',
  `order_status` varchar(30) DEFAULT NULL COMMENT '订单状态 1：待付款  2：已付款',
  `order_amount` varchar(30) DEFAULT NULL COMMENT '订单金额',
  `paid_amount` varchar(30) DEFAULT NULL COMMENT '实际支付金额',
  `introduction` text COMMENT '求职者个人介绍',
  `resume_url` varchar(255) DEFAULT NULL COMMENT '简历链接',
  `finder_id` int(11) DEFAULT NULL COMMENT '求职者ID',
  `interviewer_id` int(11) DEFAULT NULL COMMENT '面试官ID',
  `is_interviewed` varchar(10) DEFAULT NULL COMMENT '是否面试（1：是,2：否）（面试官跟新）',
  `is_ordered` varchar(10) DEFAULT NULL COMMENT '是否结单（1：是,2：否）(求职者跟新)',
  `is_evaluation` varchar(10) DEFAULT NULL COMMENT '是否已经评价（1：是,2：否）',
  `evaluation_id` int(11) DEFAULT NULL COMMENT '评论表单ID',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `paid_time` datetime DEFAULT NULL COMMENT '支付时间',
  `ordered_time` datetime DEFAULT NULL COMMENT '结单时间',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='订单表';

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
