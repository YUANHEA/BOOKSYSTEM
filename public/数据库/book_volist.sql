/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : php

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2020-01-23 22:28:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book_volist
-- ----------------------------
DROP TABLE IF EXISTS `book_volist`;
CREATE TABLE `book_volist` (
  `bookId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `bookName` varchar(255) NOT NULL,
  `bookSubtitle` varchar(255) NOT NULL,
  `bookMainImage` varchar(255) DEFAULT NULL,
  `bookPrice` decimal(10,2) NOT NULL,
  `bookStatus` varchar(255) NOT NULL,
  `bookTotalPrice` decimal(10,2) NOT NULL,
  `bookStock` varchar(255) NOT NULL,
  `bookSelected` tinyint(1) NOT NULL,
  PRIMARY KEY (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_volist
-- ----------------------------
INSERT INTO `book_volist` VALUES ('1', '1', '书本叫什么', '备注', '图片地址', '11.11', '1', '888.22', '86', '0');
INSERT INTO `book_volist` VALUES ('2', '1', '图书模拟1', '元旦促销', 'https://images-cn.ssl-images-amazon.com/images/I/51Tcbtedn%2BL._SY346_.jpg', '99.66', '1', '100.11', '86', '1');
INSERT INTO `book_volist` VALUES ('3', '2', '引爆社群', '十一特惠', '//imgservice.suning.cn/uimg1/b2c/image/fuQrTuDR5vNyQNwJdDmzFQ==.jpg_800w_800h_4e', '88.20', '1', '99.33', '86', '0');
INSERT INTO `book_volist` VALUES ('4', '2', '拼图游戏', '6.1打折', 'http://imgservice.suning.cn/uimg1/b2c/image/WZoKkQlYhFN6xM7vDA012Q==.jpg_800w_800h_4e', '10.00', '1', '22.01', '86', '1');
