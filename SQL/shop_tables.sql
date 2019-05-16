/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : activiti

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 22/04/2019 13:35:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;



-- ----------------------------
-- Table structure for shop_commodity
-- ----------------------------
DROP TABLE IF EXISTS `shop_commodity`;
CREATE TABLE `shop_commodity`  (
  `ID` bigint(50) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名字',
  `PRICE` int(100) NULL DEFAULT 0 COMMENT '价格\r\n',
  `NUM` int(255) NULL DEFAULT 0 COMMENT '数量',
  `DETAIL_PICTURE` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详情图片路径',
  `THUMBNAIL` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '缩略图',
  `TOP` int(4) NULL DEFAULT 0 COMMENT '1 置顶 0非置顶',
  `STATUS` int(4) NULL DEFAULT NULL COMMENT '1 上架 0下架',
  `REGISTER` datetime(4) NULL DEFAULT NULL COMMENT '注册时间',
  `ORDER_NUM` int(100) NULL DEFAULT NULL COMMENT '排序号',
  `HOT_SALE` int(4) NULL DEFAULT NULL COMMENT '是否热卖 1 热卖  0 非热卖',
  `IS_NEW_PRODUCT_ORDER_NUM` int(20) NULL DEFAULT NULL COMMENT '是否新品排序号',
  `COMMODITY_ID` bigint(50) NULL DEFAULT NULL COMMENT '商品分类',
  `DESCRIBE` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `AVAILABLE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',

  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_commodity_role
-- ----------------------------
DROP TABLE IF EXISTS `shop_commodity_role`;
CREATE TABLE `shop_commodity_role`  (
  `CID` int(36) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `CNAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `AVAILABLE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
  `PARENT_ID` int(20) NULL DEFAULT NULL COMMENT '上级角色ID',
  `ORDER_NUM` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`CID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_commodity_type
-- ----------------------------
DROP TABLE IF EXISTS `shop_commodity_type`;
CREATE TABLE `shop_commodity_type`  (
  `TID` bigint(50) NOT NULL COMMENT '类型编号',
  `COLOR` varchar(68) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '颜色',
  `SIZE` varchar(68) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '尺寸大小',
  `COMMODITY_TYPE_ID` int(11) NULL DEFAULT NULL COMMENT '商品编号',
  `PARENTID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级角色ID',
  `sort` bigint(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`TID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_index_carousel_text
-- ----------------------------
DROP TABLE IF EXISTS `shop_index_carousel_text`;
CREATE TABLE `shop_index_carousel_text`  (
  `ID` int(100) NOT NULL AUTO_INCREMENT,
  `TEXT_1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TEXT_2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TEXT_3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TEXT_4` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TEXT_5` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TEXT_6` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE` int(5) NULL DEFAULT NULL COMMENT '区分是第几张轮播图  value:1 2 3 ',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_order
-- ----------------------------
DROP TABLE IF EXISTS `shop_order`;
CREATE TABLE `shop_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户UID',
  `b_order_num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大订单号',
  `total_price` int(11) NOT NULL DEFAULT 0 COMMENT '订单总金额：单位分',
  `status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '订单状态：0-已取消；30-待支付；60-已支付待发货；90-已发货',
  `delivery_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_detail`;
CREATE TABLE `shop_order_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `b_order_num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大订单号',
  `commodity_id` bigint(20) NOT NULL COMMENT '商品ID',
  `s_order_num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小订单号',
  `commodity_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` int(11) NOT NULL DEFAULT 0 COMMENT '订单总金额：单位分',
  `num` int(6) NOT NULL DEFAULT 0 COMMENT '商品数量',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_picture
-- ----------------------------
DROP TABLE IF EXISTS `shop_picture`;
CREATE TABLE `shop_picture`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `REGISTER_DATE` datetime(3) NULL DEFAULT NULL,
  `TYPE` int(4) NULL DEFAULT NULL COMMENT '1.详情图，2缩略图',
  `FILE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `FILE_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `SAVE_FILE_NAME` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保存到服务器目录的文件名称',
  `REPORTADDR` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保存到服务器目录的文件全路径',
  `PREVIEW_ID` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片ID',
  `SHOP_ID` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品的ID',
  `PICTURE_SIZE` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片大小',
  `PAGEADDR` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
