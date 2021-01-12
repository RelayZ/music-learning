/*
Navicat MySQL Data Transfer

Source Server         : Riley
Source Server Version : 80018
Source Host           : 192.168.56.1:3306
Source Database       : music

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2021-01-12 13:55:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', '123');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `type` tinyint(1) DEFAULT NULL COMMENT '收藏类型(0歌曲1歌单)',
  `song_id` int(11) DEFAULT NULL COMMENT '歌曲id',
  `song_list_id` int(11) DEFAULT NULL COMMENT '歌单id',
  `create_time` datetime NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏';

-- ----------------------------
-- Records of collect
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `type` tinyint(1) DEFAULT NULL COMMENT '收藏类型(0歌曲1歌单)',
  `song_id` int(11) DEFAULT NULL COMMENT '歌曲id',
  `song_list_id` int(11) DEFAULT NULL COMMENT '歌单id',
  `create_time` datetime DEFAULT NULL COMMENT '收藏时间',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `up` int(11) DEFAULT '0' COMMENT '评论点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论';

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for consumer
-- ----------------------------
DROP TABLE IF EXISTS `consumer`;
CREATE TABLE `consumer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别（1男0女)',
  `phone_num` char(15) DEFAULT NULL COMMENT '手机号',
  `email` char(30) DEFAULT NULL COMMENT '电子邮箱',
  `birth` datetime DEFAULT NULL COMMENT '生日',
  `Introduction` varchar(255) DEFAULT NULL COMMENT '签名',
  `location` varchar(255) DEFAULT NULL COMMENT '地区',
  `avator` varchar(255) DEFAULT NULL COMMENT '头像（图片地址)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='前端用户表';

-- ----------------------------
-- Records of consumer
-- ----------------------------
INSERT INTO `consumer` VALUES ('1', '曾国田', '123456', '1', '18475035359', '1724980715@qq.com', '2021-01-24 00:00:00', '帅比', '汕头市', '/avatorImages/161034547636817计科四班-曾国田2.jpg', '2021-01-11 13:35:44', '2021-01-11 14:11:16');
INSERT INTO `consumer` VALUES ('5', '测试的人', '123456', '1', '123', '1724980715@qq.com', '2021-01-05 00:00:00', '123', '213', '/img/user.jpg', '2021-01-11 14:11:39', '2021-01-11 14:11:39');
INSERT INTO `consumer` VALUES ('6', '曾国田粉丝', '123456', '0', '444', '111@qq.com', '2021-01-12 00:00:00', '小学生', '网吧', '/img/user.jpg', '2021-01-11 14:38:02', '2021-01-11 14:38:02');

-- ----------------------------
-- Table structure for list_song
-- ----------------------------
DROP TABLE IF EXISTS `list_song`;
CREATE TABLE `list_song` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键  ',
  `song_id` int(11) DEFAULT NULL COMMENT '歌曲id  多对多的关系',
  `song_list_id` int(11) DEFAULT NULL COMMENT '歌单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='歌单包含歌曲列表';

-- ----------------------------
-- Records of list_song
-- ----------------------------

-- ----------------------------
-- Table structure for rank
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank` (
  `id` int(11) NOT NULL,
  `song_list_id` int(11) DEFAULT NULL COMMENT '歌单id',
  `consumer_id` int(11) DEFAULT NULL COMMENT '用户id',
  `score` int(11) DEFAULT NULL COMMENT '评分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评价';

-- ----------------------------
-- Records of rank
-- ----------------------------

-- ----------------------------
-- Table structure for singer
-- ----------------------------
DROP TABLE IF EXISTS `singer`;
CREATE TABLE `singer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '歌手姓名',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别（1男0女2组合3不明)',
  `pic` varchar(255) DEFAULT NULL COMMENT '头像',
  `birth` datetime DEFAULT NULL COMMENT '生日',
  `location` varchar(255) DEFAULT NULL COMMENT '所属地区',
  `introduction` varchar(255) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='歌手';

-- ----------------------------
-- Records of singer
-- ----------------------------
INSERT INTO `singer` VALUES ('37', '曾国田', '1', 'img/singerPic/hhh.jpg', '1999-09-25 00:00:00', '中国', '帅逼');
INSERT INTO `singer` VALUES ('38', '曾国田的小弟', '1', 'img/singerPic/hhh.jpg', '2001-02-12 00:00:00', '美国', '就是他的小弟');
INSERT INTO `singer` VALUES ('39', '曾国田的小妹', '0', 'img/singerPic/hhh.jpg', '1990-01-09 00:00:00', '日本', '小妹');
INSERT INTO `singer` VALUES ('41', '曾国田的二表弟', '1', 'img/singerPic/hhh.jpg', '1997-08-15 00:00:00', '朝鲜', '人狠话不多');
INSERT INTO `singer` VALUES ('42', '曾国田的二表妹', '0', 'img/singerPic/hhh.jpg', '1996-06-10 00:00:00', '新加坡', '没有');
INSERT INTO `singer` VALUES ('43', '不明玩家', '3', 'img/singerPic/hhh.jpg', '2021-01-12 00:00:00', '东厂', '·');

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `singer_id` int(11) DEFAULT NULL COMMENT '歌手id',
  `name` varchar(255) DEFAULT NULL COMMENT '歌名',
  `introduction` varchar(255) DEFAULT NULL COMMENT '简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `pic` varchar(255) DEFAULT NULL COMMENT '歌曲图片',
  `lyric` text COMMENT '歌词',
  `url` varchar(255) DEFAULT NULL COMMENT '歌曲地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='歌曲';

-- ----------------------------
-- Records of song
-- ----------------------------
INSERT INTO `song` VALUES ('25', '37', '曾国田-醒着醉', '第一次', '2021-01-10 17:03:20', '2021-01-10 17:03:20', '/img/songPic/tubiao.jpg', '[00:00.18]林启得 - 醒着醉\r\n[00:00.84]作曲：马良\r\n[00:00.99]作词：马良\r\n[00:01.25]站在公交车里\r\n[00:04.28]抓着摇曳的手环\r\n[00:07.62]我的命运啊\r\n[00:10.03]像他一样摇摆\r\n[00:15.25]抬头看见那\r\n[00:17.33]天边的晚霞\r\n[00:22.92]林深时见鹿\r\n[00:26.62]老树陪古屋\r\n[00:30.07]我遇见你\r\n[00:31.69]却没能让你留步\r\n[00:37.59]清晨时见雾\r\n[00:41.25]青草沾雨露\r\n[00:44.86]我爱上你\r\n[00:46.73]却没能把你留住\r\n[00:52.27]你别出现在我黎明的梦里\r\n[00:55.46]我怕我醒来就抱不到你\r\n[00:59.41]谁能给我麻木的酒\r\n[01:02.56]醒着醉\r\n[01:06.82]你别出现在我醉酒的夜里\r\n[01:10.12]我怕我狼狈的把你挽回\r\n[01:14.23]谁能给我麻木的酒\r\n[01:17.47]醒着醉\r\n[01:51.87]林深时见鹿\r\n[01:55.12]老树陪古屋\r\n[01:58.68]我遇见你\r\n[02:00.30]却没能让你留步\r\n[02:06.08]清晨时见雾\r\n[02:09.73]青草沾雨露\r\n[02:13.39]我爱上你\r\n[02:15.11]却没能把你留住\r\n[02:20.80]你别出现在我黎明的梦里\r\n[02:24.15]我怕我醒来就抱不到你\r\n[02:28.06]谁能给我麻木的酒\r\n[02:31.41]醒着醉\r\n[02:35.31]你别出现在我醉酒的夜里\r\n[02:38.80]我怕我狼狈的把你挽回\r\n[02:42.83]谁能给我麻木的酒\r\n[02:46.03]醒着醉', '/song/1610269400772醒着醉.mp3');
INSERT INTO `song` VALUES ('26', '37', '曾国田-醒着醉', '第一次', '2021-01-10 17:03:58', '2021-01-10 17:03:58', '/img/songPic/tubiao.jpg', '[00:00.18]林启得 - 醒着醉\r\n[00:00.84]作曲：马良\r\n[00:00.99]作词：马良\r\n[00:01.25]站在公交车里\r\n[00:04.28]抓着摇曳的手环\r\n[00:07.62]我的命运啊\r\n[00:10.03]像他一样摇摆\r\n[00:15.25]抬头看见那\r\n[00:17.33]天边的晚霞\r\n[00:22.92]林深时见鹿\r\n[00:26.62]老树陪古屋\r\n[00:30.07]我遇见你\r\n[00:31.69]却没能让你留步\r\n[00:37.59]清晨时见雾\r\n[00:41.25]青草沾雨露\r\n[00:44.86]我爱上你\r\n[00:46.73]却没能把你留住\r\n[00:52.27]你别出现在我黎明的梦里\r\n[00:55.46]我怕我醒来就抱不到你\r\n[00:59.41]谁能给我麻木的酒\r\n[01:02.56]醒着醉\r\n[01:06.82]你别出现在我醉酒的夜里\r\n[01:10.12]我怕我狼狈的把你挽回\r\n[01:14.23]谁能给我麻木的酒\r\n[01:17.47]醒着醉\r\n[01:51.87]林深时见鹿\r\n[01:55.12]老树陪古屋\r\n[01:58.68]我遇见你\r\n[02:00.30]却没能让你留步\r\n[02:06.08]清晨时见雾\r\n[02:09.73]青草沾雨露\r\n[02:13.39]我爱上你\r\n[02:15.11]却没能把你留住\r\n[02:20.80]你别出现在我黎明的梦里\r\n[02:24.15]我怕我醒来就抱不到你\r\n[02:28.06]谁能给我麻木的酒\r\n[02:31.41]醒着醉\r\n[02:35.31]你别出现在我醉酒的夜里\r\n[02:38.80]我怕我狼狈的把你挽回\r\n[02:42.83]谁能给我麻木的酒\r\n[02:46.03]醒着醉', '/song/1610269438809醒着醉.mp3');
INSERT INTO `song` VALUES ('27', '37', '曾国田-茫', '第二张', '2021-01-10 17:04:19', '2021-01-10 17:04:19', '/img/songPic/tubiao.jpg', '[00:00.18]林启得 - 醒着醉\r\n[00:00.84]作曲：马良\r\n[00:00.99]作词：马良\r\n[00:01.25]站在公交车里\r\n[00:04.28]抓着摇曳的手环\r\n[00:07.62]我的命运啊\r\n[00:10.03]像他一样摇摆\r\n[00:15.25]抬头看见那\r\n[00:17.33]天边的晚霞\r\n[00:22.92]林深时见鹿\r\n[00:26.62]老树陪古屋\r\n[00:30.07]我遇见你\r\n[00:31.69]却没能让你留步\r\n[00:37.59]清晨时见雾\r\n[00:41.25]青草沾雨露\r\n[00:44.86]我爱上你\r\n[00:46.73]却没能把你留住\r\n[00:52.27]你别出现在我黎明的梦里\r\n[00:55.46]我怕我醒来就抱不到你\r\n[00:59.41]谁能给我麻木的酒\r\n[01:02.56]醒着醉\r\n[01:06.82]你别出现在我醉酒的夜里\r\n[01:10.12]我怕我狼狈的把你挽回\r\n[01:14.23]谁能给我麻木的酒\r\n[01:17.47]醒着醉\r\n[01:51.87]林深时见鹿\r\n[01:55.12]老树陪古屋\r\n[01:58.68]我遇见你\r\n[02:00.30]却没能让你留步\r\n[02:06.08]清晨时见雾\r\n[02:09.73]青草沾雨露\r\n[02:13.39]我爱上你\r\n[02:15.11]却没能把你留住\r\n[02:20.80]你别出现在我黎明的梦里\r\n[02:24.15]我怕我醒来就抱不到你\r\n[02:28.06]谁能给我麻木的酒\r\n[02:31.41]醒着醉\r\n[02:35.31]你别出现在我醉酒的夜里\r\n[02:38.80]我怕我狼狈的把你挽回\r\n[02:42.83]谁能给我麻木的酒\r\n[02:46.03]醒着醉', '/song/1610269459876茫.mp3');

-- ----------------------------
-- Table structure for song_list
-- ----------------------------
DROP TABLE IF EXISTS `song_list`;
CREATE TABLE `song_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `pic` varchar(255) DEFAULT NULL COMMENT '图片',
  `introduction` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '简介',
  `style` varchar(255) DEFAULT NULL COMMENT '风格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='歌单';

-- ----------------------------
-- Records of song_list
-- ----------------------------
INSERT INTO `song_list` VALUES ('11', '我喜欢', '/img/songListPic/123.jpg', '我喜欢简介', '华语');
INSERT INTO `song_list` VALUES ('12', '轻音乐', '/img/songListPic/123.jpg', 'Bandari', '轻音乐');
INSERT INTO `song_list` VALUES ('13', '曾国田', '/img/songListPic/123.jpg', '主唱男神', '粤语');
