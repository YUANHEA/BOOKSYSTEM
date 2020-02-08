/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : php

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2020-02-08 18:55:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book_order_item`
-- ----------------------------
DROP TABLE IF EXISTS `book_order_item`;
CREATE TABLE `book_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` int(11) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL COMMENT '商品id',
  `book_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `book_image` varchar(500) DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `quantity` int(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_order_item
-- ----------------------------
INSERT INTO `book_order_item` VALUES ('5', '20', '1581152269387', '2', '悲惨世界 ', '/upload/2.jpg', '27.00', '1', '27.00', '2020-02-08 16:57:48', '2020-02-08 16:57:48');
INSERT INTO `book_order_item` VALUES ('6', '3', '1581152782594', '3', '边城 ', '/upload/3.jpg', '15.00', '1', '15.00', '2020-02-08 17:06:22', '2020-02-08 17:06:22');
INSERT INTO `book_order_item` VALUES ('7', '16', '1581155300148', '6', '不能承受的生命之轻', '/upload/6.jpg', '29.00', '1', '29.00', '2020-02-08 17:48:19', '2020-02-08 17:48:19');
