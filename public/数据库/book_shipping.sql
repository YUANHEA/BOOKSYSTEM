/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : php

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2020-02-08 18:55:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book_shipping`
-- ----------------------------
DROP TABLE IF EXISTS `book_shipping`;
CREATE TABLE `book_shipping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_shipping
-- ----------------------------
INSERT INTO `book_shipping` VALUES ('4', '1', '廖师兄', '010', '18688888888', '北京', '北京市', '海淀区', '中关村慕课网大楼', '100000', '2000-01-22 14:26:25', '2000-01-22 14:26:25');
INSERT INTO `book_shipping` VALUES ('7', '20', 'gee11rrr11ly', '010', '186888888881', '北京', '北京市', '海淀1区', '中关村', '100000', '2020-02-06 15:29:30', '2020-02-06 15:29:30');
INSERT INTO `book_shipping` VALUES ('9', '20', 'gee11rrr11ly22', '010', '186888888881', '北京', '北京市', '海淀1区', '中关村', '100000', '2020-02-06 15:30:36', '2020-02-06 15:30:36');
INSERT INTO `book_shipping` VALUES ('11', '3', 'gee1111l222y', '010', '18688888888', '北京', '北京市', '海淀区', '中关村', '100000', '2020-02-08 17:05:24', '2020-02-08 17:05:24');
INSERT INTO `book_shipping` VALUES ('12', '16', 'gee1111l222y67', '010', '18688888888', '北京', '北京市', '海淀区', '中关村', '100000', '2020-02-08 17:34:12', '2020-02-08 17:34:12');
