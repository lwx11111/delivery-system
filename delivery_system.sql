/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : delivery_system

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 14/05/2024 03:33:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `longitude` decimal(10, 6) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10, 6) NULL DEFAULT NULL COMMENT '纬度',
  `province_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省名',
  `province_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省ID',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市名',
  `city_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市ID',
  `county_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '县名',
  `county_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '县ID',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `contacts` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对应用户ID',
  `house_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '门牌号',
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('1768251549991170049', 87.568000, 43.854000, '新疆维吾尔自治区', '', '乌鲁木齐市', '0991', '新市区', '650104', '新疆维吾尔自治区乌鲁木齐市新市区高新街街道交通银行(乌鲁木齐北京路支行)盈科国际中心', 'admin', '', '1', '', '1767755634570178562');
INSERT INTO `address` VALUES ('1768251549991170050', 87.570000, 43.860000, '新疆维吾尔自治区', '', '乌鲁木齐市', '0991', '新市区', '650104', '新疆维吾尔自治区乌鲁木齐市新市区高新街街道交通银行(乌鲁木齐北京路支行)盈科国际中心', 'admin', '', '1', '', '1767757356310999042');
INSERT INTO `address` VALUES ('1768251549991170051', 87.569296, 43.855817, '新疆维吾尔自治区', '', '乌鲁木齐市', '0991', '新市区', '650104', '新疆维吾尔自治区乌鲁木齐市新市区高新街街道交通银行(乌鲁木齐北京路支行)盈科国际中心', 'admin', '', '1', '', '1767758845037260802');
INSERT INTO `address` VALUES ('1768251549991170052', 87.569296, 43.855817, '新疆维吾尔自治区', '', '乌鲁木齐市', '0991', '新市区', '650104', '新疆维吾尔自治区乌鲁木齐市新市区高新街街道交通银行(乌鲁木齐北京路支行)盈科国际中心', 'admin', '', '1', '', '1767767585803218946');
INSERT INTO `address` VALUES ('1783064286042562561', 87.583008, 43.822351, '新疆维吾尔自治区', '', '乌鲁木齐市', '0991', '沙依巴克区', '650103', '新疆维吾尔自治区乌鲁木齐市沙依巴克区八一街道西北路新疆大学', '刘文轩', '13806436774', '1767759610543902721', '新疆大学北校区', NULL);
INSERT INTO `address` VALUES ('1783114704672038913', 87.583043, 43.822336, '新疆维吾尔自治区', '', '乌鲁木齐市', '0991', '沙依巴克区', '650103', '新疆维吾尔自治区乌鲁木齐市沙依巴克区八一街道西北路新疆大学友好校区', '刘文轩', '13806436774', '1767759610543902721', '新疆大学', NULL);
INSERT INTO `address` VALUES ('1784265336304979970', 87.583012, 43.822344, '新疆维吾尔自治区', '', '乌鲁木齐市', '0991', '沙依巴克区', '650103', '新疆维吾尔自治区乌鲁木齐市沙依巴克区八一街道西北路新疆大学友好校区', 'lwxshop1', '', '1783449746459910145', '', '1784265335956852737');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `shop_item_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `amount` int NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_item_id`(`shop_item_id` ASC) USING BTREE,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`shop_item_id`) REFERENCES `shop_item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES ('1783063759716102145', '1767758133746216962', 1, '1767759610543902721');
INSERT INTO `cart` VALUES ('1783063759716102146', '1767758357617192961', 1, '1767759610543902721');
INSERT INTO `cart` VALUES ('1783064075572359169', '1767758133746216962', 1, '1767759610543902721');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '大类ID',
  `picture` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `index_name`(`id` ASC, `name` ASC, `parent_id` ASC) USING BTREE,
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商铺分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '美食', NULL, 'http://127.0.0.1:9000/sys/1.png');
INSERT INTO `category` VALUES ('2', '甜点饮品', NULL, 'http://127.0.0.1:9000/sys/2.png');
INSERT INTO `category` VALUES ('3', '饺子馄饨', '1', 'http://127.0.0.1:9000/sys/3.png');
INSERT INTO `category` VALUES ('4', '汉堡薯条', '1', 'http://127.0.0.1:9000/sys/4.png');
INSERT INTO `category` VALUES ('5', '奶茶果汁', '2', 'http://127.0.0.1:9000/sys/5.png');
INSERT INTO `category` VALUES ('6', '醒脑咖啡', '2', 'http://127.0.0.1:9000/sys/6.png');

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  CONSTRAINT `collection_ibfk_1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户收藏' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collection
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户ID',
  `shop_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品ID',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单ID',
  `rider_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '骑手ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `release_time` datetime NULL DEFAULT NULL COMMENT '时间',
  `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回复id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  INDEX `order_id`(`order_id` ASC) USING BTREE,
  INDEX `parent_id`(`parent_id` ASC) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1783090275590098945', '1767759610543902721', '1767767585803218946', '1783067278523277313', NULL, NULL, '321', '2024-04-24 19:07:04', NULL);
INSERT INTO `comment` VALUES ('1783090404934045697', '1767759610543902721', '1767767585803218946', '1783067278523277313', NULL, NULL, '321', '2024-04-24 19:07:35', NULL);
INSERT INTO `comment` VALUES ('1783093597650833410', '1767759610543902721', '1767767585803218946', '1783067278523277313', NULL, NULL, '321', '2024-04-24 19:20:17', NULL);
INSERT INTO `comment` VALUES ('1783114779645202433', '1767759610543902721', '1767767585803218946', '1783114428137340930', NULL, NULL, '好', '2024-04-24 20:44:27', NULL);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺ID',
  `rider_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配送骑手',
  `rider_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '骑手名',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户ID',
  `shop_item` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物品JSON（废弃，需要做检索，采用中间表）',
  `packing_charges` double NULL DEFAULT NULL COMMENT '打包费',
  `delivery_charge` double NULL DEFAULT NULL COMMENT '配送费',
  `total_charge` double NULL DEFAULT NULL COMMENT '总金额',
  `expected_time` datetime NULL DEFAULT NULL COMMENT '期望时间',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配送地址',
  `delivery_service` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配送服务',
  `order_time` datetime NULL DEFAULT NULL COMMENT '下单时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `payment_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付方式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `tableware` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '餐具数量',
  `status_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态名',
  `shop_address_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商家地址ID',
  `user_address_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户地址ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  CONSTRAINT `order_info_ibfk_1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('1783067278523277313', '1767767585803218946', '1767763920098988034', NULL, '1767759610543902721', NULL, NULL, 1, 12, '2024-04-24 17:58:40', '1', '1', '2024-04-24 17:35:42', 51, '微信支付', '', '1', '消费者评论', '1768251549991170052', '1783064286042562561');
INSERT INTO `order_info` VALUES ('1783114428137340930', '1767767585803218946', '1767763920098988034', NULL, '1767759610543902721', NULL, NULL, 1, 12, '2024-04-24 21:05:59', '1', '1', '2024-04-24 20:43:03', 51, '微信支付', '', '1', '消费者评论', '1768251549991170052', '1783064286042562561');
INSERT INTO `order_info` VALUES ('1783182098828361729', '1767757356310999042', NULL, NULL, '1767759610543902721', NULL, NULL, 1, 82, '2024-04-25 01:37:54', '1', '1', '2024-04-25 01:11:57', 2, '支付宝支付', '', '1', '消费者支付', '1768251549991170050', '1783064286042562561');
INSERT INTO `order_info` VALUES ('1783183164470984706', '1767767585803218946', '1767763920098988034', 'lwxrider', '1767759610543902721', NULL, NULL, 1, 3, '2024-04-25 01:39:09', '1', '1', '2024-04-25 01:16:11', 3, '支付宝支付', '', '1', '商家接单', '1768251549991170052', '1783064286042562561');

-- ----------------------------
-- Table structure for order_shop_item
-- ----------------------------
DROP TABLE IF EXISTS `order_shop_item`;
CREATE TABLE `order_shop_item`  (
  `id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `shop_item_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `amount` int NULL DEFAULT NULL COMMENT '数量',
  `total` double NULL DEFAULT NULL COMMENT '总价',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id` ASC) USING BTREE,
  INDEX `shop_item_id`(`shop_item_id` ASC) USING BTREE,
  CONSTRAINT `order_shop_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_shop_item_ibfk_2` FOREIGN KEY (`shop_item_id`) REFERENCES `shop_item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_shop_item
-- ----------------------------
INSERT INTO `order_shop_item` VALUES ('1783067278569414657', '1783067278523277313', '1767767663548837889', 5, 5);
INSERT INTO `order_shop_item` VALUES ('1783067278590386177', '1783067278523277313', '1767767726580838402', 6, 6);
INSERT INTO `order_shop_item` VALUES ('1783114428175089665', '1783114428137340930', '1767767663548837889', 6, 6);
INSERT INTO `order_shop_item` VALUES ('1783114428191866881', '1783114428137340930', '1767767726580838402', 5, 5);
INSERT INTO `order_shop_item` VALUES ('1783182098891276290', '1783182098828361729', '1767758357617192961', 1, 81);
INSERT INTO `order_shop_item` VALUES ('1783183164496150530', '1783183164470984706', '1767767663548837889', 1, 1);
INSERT INTO `order_shop_item` VALUES ('1783183164504539138', '1783183164470984706', '1767767726580838402', 1, 1);

-- ----------------------------
-- Table structure for order_status
-- ----------------------------
DROP TABLE IF EXISTS `order_status`;
CREATE TABLE `order_status`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态名',
  `status_time` datetime NULL DEFAULT NULL COMMENT '状态切换时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id` ASC) USING BTREE,
  CONSTRAINT `order_status_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点单状态流程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_status
-- ----------------------------
INSERT INTO `order_status` VALUES ('1783067289885646849', '1783067278523277313', 2, '消费者支付', '2024-04-24 17:35:44');
INSERT INTO `order_status` VALUES ('1783067314292301825', '1783067278523277313', 3, '商家接单', '2024-04-24 17:35:50');
INSERT INTO `order_status` VALUES ('1783068081870905345', '1783067278523277313', 4, '骑手配送', '2024-04-24 17:38:53');
INSERT INTO `order_status` VALUES ('1783068088711815169', '1783067278523277313', 5, '消费者收货', '2024-04-24 17:38:55');
INSERT INTO `order_status` VALUES ('1783093598103797762', '1783067278523277313', 51, '消费者评论', '2024-04-24 19:20:17');
INSERT INTO `order_status` VALUES ('1783114441743663106', '1783114428137340930', 2, '消费者支付', '2024-04-24 20:43:06');
INSERT INTO `order_status` VALUES ('1783114498459041794', '1783114428137340930', 3, '商家接单', '2024-04-24 20:43:20');
INSERT INTO `order_status` VALUES ('1783114565324636162', '1783114428137340930', 4, '骑手配送', '2024-04-24 20:43:36');
INSERT INTO `order_status` VALUES ('1783114569791569921', '1783114428137340930', 5, '消费者收货', '2024-04-24 20:43:37');
INSERT INTO `order_status` VALUES ('1783114779938783233', '1783114428137340930', 51, '消费者评论', '2024-04-24 20:44:27');
INSERT INTO `order_status` VALUES ('1783182116498964481', '1783182098828361729', 2, '消费者支付', '2024-04-25 01:12:01');
INSERT INTO `order_status` VALUES ('1783183173992054785', '1783183164470984706', 2, '消费者支付', '2024-04-25 01:16:13');
INSERT INTO `order_status` VALUES ('1783183751098925058', '1783183164470984706', 3, '商家接单', '2024-04-25 01:18:31');

-- ----------------------------
-- Table structure for rider_order
-- ----------------------------
DROP TABLE IF EXISTS `rider_order`;
CREATE TABLE `rider_order`  (
  `account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `num` int NULL DEFAULT NULL COMMENT '接单数量',
  `status` int NULL DEFAULT NULL COMMENT '骑手状态',
  `earnings` double NULL DEFAULT NULL COMMENT '骑手工资',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '骑手名称',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rider_order
-- ----------------------------
INSERT INTO `rider_order` VALUES ('1767763920098988034', 5, 0, NULL, 'lwxrider');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺名',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省',
  `county` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '县',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '具体地址',
  `score` double NULL DEFAULT NULL COMMENT '店铺评分(sum_score/sum_people)',
  `sales_volume` int NULL DEFAULT NULL COMMENT '销量',
  `min_price` double NULL DEFAULT NULL COMMENT '起送价格',
  `delivery_charge` double NULL DEFAULT NULL COMMENT '配送费',
  `safety_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '安全档案图片',
  `open_time` time NULL DEFAULT NULL COMMENT '开门时间',
  `close_time` time NULL DEFAULT NULL COMMENT '关门时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺描述',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺图片路径',
  `sum_score` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '总分1-5 整数',
  `sum_people` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '打分总人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '店铺信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('1767755634570178562', '1', '蜜雪冰城', NULL, NULL, '新疆大学北校区', 4, 2, 1, 1, 'http://127.0.0.1:9000/test/038915195d79a495ab8ed3db4aa7f8c5_.jpg', NULL, NULL, 1, '这是一家蜜雪冰城', 'http://127.0.0.1:9000/test/5e9a26dc5f05099ffdd2f33e338ca771_.jpg', 0000000000, 0000000000);
INSERT INTO `shop` VALUES ('1767757356310999042', '1', '福斯汀', NULL, NULL, '新疆大学北校区', 4, 3, 1, 1, 'http://127.0.0.1:9000/test/ef51ff30e273aab8ce96eb0952dfd6e5_.jpg', NULL, NULL, 1, '福斯汀', 'http://127.0.0.1:9000/test/76d7d7be151f751c93161d4bc59c88f4_.jpg', 0000000000, 0000000000);
INSERT INTO `shop` VALUES ('1767758845037260802', '1', '美斯汀', NULL, NULL, '新疆大学北校区', 4, 4, 1, 1, 'http://127.0.0.1:9000/test/e9345e580a4f8fd5a6f10cd1fe2e9af1_.jpg', NULL, NULL, 1, '美斯汀', 'http://127.0.0.1:9000/test/4623e1b772f65d6e3650e4600b4f0eff_.jpg', 0000000000, 0000000000);
INSERT INTO `shop` VALUES ('1767767585803218946', '1767767452898332674', '淄博烧烤', NULL, NULL, '新疆大学北校区', 4, 7, 1, 1, 'http://127.0.0.1:9000/test/fd456e7263ab2880a9365b38ac9f4472_.jpg', NULL, NULL, 1, '淄博烧烤', 'http://127.0.0.1:9000/test/3edf3088297fe306c84f7952bbdd5aff_.jpg', 0000000000, 0000000000);
INSERT INTO `shop` VALUES ('1767767585803218947', '1767767452898332674', '淄博烧烤', NULL, NULL, '新疆大学北校区', 4, 5, 1, 1, 'http://127.0.0.1:9000/test/fd456e7263ab2880a9365b38ac9f4472_.jpg', NULL, NULL, 0, '淄博烧烤', 'http://127.0.0.1:9000/test/3edf3088297fe306c84f7952bbdd5aff_.jpg', 0000000000, 0000000000);
INSERT INTO `shop` VALUES ('1784265335956852737', '1783449746459910145', '我的店铺1号', '', NULL, '新疆维吾尔自治区乌鲁木齐市沙依巴克区八一街道西北路新疆大学友好校区', 4, 0, 2, 2, 'http://127.0.0.1:9000/test/1b3ba765fd1b282d717464cd4518b824_.jpg', NULL, NULL, 1, '我的店铺1号', 'http://127.0.0.1:9000/test/3893c8831e36c059013ea5ff4f2f333a_.jpg', NULL, NULL);

-- ----------------------------
-- Table structure for shop_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  CONSTRAINT `shop_category_ibfk_1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `shop_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商铺分中间表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop_category
-- ----------------------------
INSERT INTO `shop_category` VALUES ('1767755634570178563', '1767755634570178562', '5');
INSERT INTO `shop_category` VALUES ('1767757356310999043', '1767757356310999042', '4');
INSERT INTO `shop_category` VALUES ('1767758845037260803', '1767758845037260802', '4');
INSERT INTO `shop_category` VALUES ('1767767585803218947', '1767767585803218946', '3');
INSERT INTO `shop_category` VALUES ('1767767585882910721', '1767767585803218946', '4');
INSERT INTO `shop_category` VALUES ('1784265335982018561', '1784265335956852737', '3');
INSERT INTO `shop_category` VALUES ('1784265336044933121', '1784265335956852737', '4');

-- ----------------------------
-- Table structure for shop_comment
-- ----------------------------
DROP TABLE IF EXISTS `shop_comment`;
CREATE TABLE `shop_comment`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `shop_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `release_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `mark` int NULL DEFAULT NULL COMMENT '评分',
  `recover_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商家回复ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  INDEX `recover_id`(`recover_id` ASC) USING BTREE,
  CONSTRAINT `shop_comment_ibfk_1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `shop_comment_ibfk_2` FOREIGN KEY (`recover_id`) REFERENCES `shop_comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商铺评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop_comment
-- ----------------------------
INSERT INTO `shop_comment` VALUES ('1', '1767755634570178562', '1', '122', '2024-03-25 12:45:20', 3, NULL);

-- ----------------------------
-- Table structure for shop_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_item`;
CREATE TABLE `shop_item`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `shop_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物品名',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物品图片',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物品介绍',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
  `main_ingredient` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主料',
  `vice_ingredient` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '辅料',
  `status` int NULL DEFAULT 1 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shop_id`(`shop_id` ASC) USING BTREE,
  CONSTRAINT `shop_item_ibfk_1` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '店铺物品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop_item
-- ----------------------------
INSERT INTO `shop_item` VALUES ('1767756962210000898', '1767755634570178562', '棒打鲜橙', 'http://127.0.0.1:9000/test/af1d7782a0fafac50abd7cd2798fefe1_.jpg', '棒打鲜橙', 7.00, '', '橙子', '水', 1);
INSERT INTO `shop_item` VALUES ('1767757082255175681', '1767755634570178562', '草莓椰奶露', 'http://127.0.0.1:9000/test/7475b972d43a971f701d93e99dfca9ad_.jpg', '草莓椰奶露', 8.00, '', '草莓', '水', 1);
INSERT INTO `shop_item` VALUES ('1767758133746216962', '1767757356310999042', '中国汉堡单人餐', 'http://127.0.0.1:9000/test/06ed69fa70e5a2f7d0cd43a400ec2b35_.jpg', '中国汉堡单人餐', 30.00, '', '鸡肉', '土豆', 1);
INSERT INTO `shop_item` VALUES ('1767758357617192961', '1767757356310999042', '中国汉堡双人餐', 'http://127.0.0.1:9000/test/dbb80537c879b7c1cdf27bb08a0e2ada_.jpg', '中国汉堡双人餐', 81.00, '', '鸡肉', '土豆', 1);
INSERT INTO `shop_item` VALUES ('1767759440888475650', '1767758845037260802', '单人餐', 'http://127.0.0.1:9000/test/5f3f494ae160152960f6f2dabb63da19_.jpg', '单人餐', 28.00, '', '鸡肉', '土豆', 1);
INSERT INTO `shop_item` VALUES ('1767759529598005249', '1767758845037260802', '牛肉单人餐', 'http://127.0.0.1:9000/test/fc6b7238932446d49fd799f881dfeb02_.jpg', '牛肉单人餐', 29.00, '', '牛肉', '土豆', 1);
INSERT INTO `shop_item` VALUES ('1767767663548837889', '1767767585803218946', '烤串', 'http://127.0.0.1:9000/test/802f664774f7fb36e658dfd238749d68_.jpg', '烤串', 1.00, '', '牛肉', '', 1);
INSERT INTO `shop_item` VALUES ('1767767726580838402', '1767767585803218946', '鸡肉串', 'http://127.0.0.1:9000/test/e48dabb762382be6c447406cf70b8cf1_.jpg', '鸡肉串', 1.00, '', '鸡肉', '', 1);
INSERT INTO `shop_item` VALUES ('1784265421138972673', '1784265335956852737', '物品1号', 'http://127.0.0.1:9000/test/9b0177aa5ef8e0c925661fd8b7eec19f_.jpg', '物品1号', 1.00, '', '', '', 1);
INSERT INTO `shop_item` VALUES ('1784265471927799809', '1784265335956852737', '物品2号', 'http://127.0.0.1:9000/test/bf030a3662821eeed91c1598acd49eec_.jpg', '物品2号', 2.00, '', '', '', 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `parent_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '订单状态', '', NULL);
INSERT INTO `sys_dict` VALUES (2, '逻辑删除', '0', 1);
INSERT INTO `sys_dict` VALUES (3, '待支付', '1', 1);
INSERT INTO `sys_dict` VALUES (4, '待接单', '2', 1);
INSERT INTO `sys_dict` VALUES (5, '待发货', '3', 1);
INSERT INTO `sys_dict` VALUES (6, '待收货', '4', 1);
INSERT INTO `sys_dict` VALUES (7, '订单结束', '5', 1);
INSERT INTO `sys_dict` VALUES (8, '消费者取消', '6', 1);
INSERT INTO `sys_dict` VALUES (9, '商家取消', '7', 1);
INSERT INTO `sys_dict` VALUES (10, '退款', '8', 1);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `key_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作id，各实体主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作名称',
  `result` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作结果',
  `time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `parameter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作入参',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1778380135033044994', NULL, 'lwx', 'invoke class: org.example.controller.TestController, method:lwx', NULL, NULL);
INSERT INTO `sys_log` VALUES ('1778381181394460673', NULL, 'lwx', 'invoke class: org.example.controller.TestController, method:lwx', NULL, NULL);
INSERT INTO `sys_log` VALUES ('1778381231478644737', NULL, 'lwx', 'invoke class: org.example.controller.TestController, method:lwx', NULL, NULL);
INSERT INTO `sys_log` VALUES ('1778401200379641858', NULL, 'test', '{\"code\":200,\"message\":\"成功\"}', NULL, '[Ljava.lang.Object;@7a96ef82');
INSERT INTO `sys_log` VALUES ('1778405436681203713', NULL, 'test', '{\"code\":200,\"message\":\"成功\"}', '2024-04-11 20:51:12', NULL);
INSERT INTO `sys_log` VALUES ('1778405741959417857', NULL, 'test', '{\"code\":200,\"message\":\"成功\"}', '2024-04-11 20:52:25', 'null{\r\n    \"test\":\"123\"\r\n}');
INSERT INTO `sys_log` VALUES ('1778406670725148674', NULL, 'test', '{\"code\":200,\"message\":\"成功\"}', '2024-04-11 20:56:06', '{\r\n    \"test\":\"123\"\r\n}');
INSERT INTO `sys_log` VALUES ('1778409950872088577', NULL, 'lwx', '{\"code\":200,\"message\":\"成功\"}', '2024-04-11 21:09:08', '{\r\n    \"test\":\"123\"\r\n}');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `group_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件组ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件原始名称',
  `storage_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件存储名称',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  `created_by_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `status` int NULL DEFAULT NULL COMMENT '状态（0删除，1有效）',
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件详细ID',
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '拍照项名称',
  `percentage` int NULL DEFAULT NULL COMMENT '进度',
  `bucket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '存储桶',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss
-- ----------------------------
INSERT INTO `sys_oss` VALUES ('0240f473b66fd9c79036c860d46fadfd', '3b150039a2cca87748aacee8fed4977d', '8568097.jpg', '0240f473b66fd9c79036c860d46fadfd_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('030dd36d4bd13f8c47fb7d79b7a96ac1', 'e3b7fa064cf59f1cfd1669cb23d7cec5', '8578047.jpg', '030dd36d4bd13f8c47fb7d79b7a96ac1_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('038915195d79a495ab8ed3db4aa7f8c5', '62d643724bbc8561244d0267a5fccabb', '2.jpg', '038915195d79a495ab8ed3db4aa7f8c5_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('043e8e392256f851f3793e167bd8be98', '549c102b60ab59e71d113ea6d430e070', '8568097.jpg', '043e8e392256f851f3793e167bd8be98_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('0500da97fa2e1631681ef607eaf30e57', '69fd576348ce37bc3afc5f9e722d6c3f', '3.jpg', '0500da97fa2e1631681ef607eaf30e57_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('06ed69fa70e5a2f7d0cd43a400ec2b35', 'ec8c47dacdd5015256fa02dde3835bd6', 'shop22.jpg', '06ed69fa70e5a2f7d0cd43a400ec2b35_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('06f80215bf76fa8dcb5da70e3b1139aa', '168fc6eb957c1a183af3a879792d6f7c', '8578047.jpg', '06f80215bf76fa8dcb5da70e3b1139aa_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('07278f4dfe994f60fa0c59226db73cbe', 'e19f593558bb3932c63b1204f568aaf6', 'shop1.jpg', '07278f4dfe994f60fa0c59226db73cbe_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('089d7588d2996c50103468b1e69e9230', '6765a6a4a520a76b505373d907f9da79', '8578047.jpg', '089d7588d2996c50103468b1e69e9230_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('09e81cac3b384601979ea454abee77db', 'bdfb1017ee497006989fe19cadf31862', '4.jpg', '09e81cac3b384601979ea454abee77db_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('0baaa6a6a4ab56ea355676e50929ed6e', 'd3d218a301369ca806bf6d46c0391914', '8578047.jpg', '0baaa6a6a4ab56ea355676e50929ed6e_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('0c32c92add117f0f6174db1cf382f826', '69abb2f0c8fa3df86423e21f75bdd66b', '8578047.jpg', '0c32c92add117f0f6174db1cf382f826_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('0f2f16a8daed4227c84044b91b6cfb35', '5036918139bbaf910f5c6ca018866c96', '8578047.jpg', '0f2f16a8daed4227c84044b91b6cfb35_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('0fd2b304f255fac0741e5d7b57e5e376', '9a4d8ee1f5440e771f12f2338942b061', '3.jpg', '0fd2b304f255fac0741e5d7b57e5e376_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('11c5fb0058a2f00fca7ca39556b9359e', '013fa3bb1e62958e78ca7b7d21c4df1d', '8578047.jpg', '11c5fb0058a2f00fca7ca39556b9359e_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('1334fc36b98c0b0580ffa8ff651cc0ec', '79bfae6c2cce24594b683dcafd24f6ef', '8568097.jpg', '1334fc36b98c0b0580ffa8ff651cc0ec_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('13aeac4bb337f6024a4bd1eb74932616', '3913e01698e644dd2b634ed0203f469b', '3.jpg', '13aeac4bb337f6024a4bd1eb74932616_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('1574ddf469ea19c4ad717b3202551e6c', '0f0587e5a8a83618a6ae2476785bb482', '8578047.jpg', '1574ddf469ea19c4ad717b3202551e6c_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('17320555fb6a975ae011e45396414514', 'bc6ed4866a509fc254e1a366006850b5', '8578047.jpg', '17320555fb6a975ae011e45396414514_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('196224d6ac0e7bcc27fa73b4965f7957', '809d320e0221a6bc76da81001f2dfba2', '8578047.jpg', '196224d6ac0e7bcc27fa73b4965f7957_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('1b3ba765fd1b282d717464cd4518b824', 'fa48b76a338eb3199aa10af2170f13fb', '8568097.jpg', '1b3ba765fd1b282d717464cd4518b824_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('1b58fd1d93bb9ea052b2bdfe7bf0648f', '54b5d7c8a0c8a2c31f9734df8095639d', '8578047.jpg', '1b58fd1d93bb9ea052b2bdfe7bf0648f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('1c1153f6b3565b93cb1877a2172c944d', '9f31452e51f0d21dea9b3df75b176c9c', '8568097.jpg', '1c1153f6b3565b93cb1877a2172c944d_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('1c5fbb5de10eec56308a6ce4f0f3cdc2', 'f1892cad8cd7b409fdb3e6b342b337a8', '8578047.jpg', '1c5fbb5de10eec56308a6ce4f0f3cdc2_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('1e3da5153c3d6af750c4bff914309a7a', '9c13bb3cff69dcc7151343cb32164ac2', 'shop1.jpg', '1e3da5153c3d6af750c4bff914309a7a_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('2050d76a2baebd30f69453c4bf3fd32f', '9a3f9fb1a7c59ed23986a49cf827819d', '8568097.jpg', '2050d76a2baebd30f69453c4bf3fd32f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('20fda8f6fcf96432d6f162bbddb62a95', '84b50dcf532489a71122280905967984', '8578047.jpg', '20fda8f6fcf96432d6f162bbddb62a95_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('21121f39fe36c64693a9e77e6d7f5610', 'd999b9bed0b700701905689572295486', '8578047.jpg', '21121f39fe36c64693a9e77e6d7f5610_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('2274469862432b37e6cf8865a010e563', 'a71d918ea211e336e277ded4e530c70f', '2.jpg', '2274469862432b37e6cf8865a010e563_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('22cde4c89e5fa64f421fb959ac1222b9', 'b5c4432e6f286ba966deb35f01a80fc1', '3.jpg', '22cde4c89e5fa64f421fb959ac1222b9_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('2663d8a78db0d3378f210ef0ef4257c5', '58e4a69a284a32b6bcd0c0aec4a3a131', '8578047.jpg', '2663d8a78db0d3378f210ef0ef4257c5_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('279b91ff03d895b3dda85ca07173ada5', '5a571a68a90e3f039c0d7fd0b1ad5b02', '8578047.jpg', '279b91ff03d895b3dda85ca07173ada5_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('2b2d7b430c6d241b120e21da8eac78da', '70910632d7c4e0a169ab578a0eaacbf6', '8568097.jpg', '2b2d7b430c6d241b120e21da8eac78da_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('2c7f9bfab0095b0c36846aedd2124187', '0586b421e45abedd7896a6e56f29d065', '8568097.jpg', '2c7f9bfab0095b0c36846aedd2124187_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('2e009159fa1e9f9cf8427c3559537155', 'dcce265baa56b73c7fc3cbcd1b21b779', '8568097.jpg', '2e009159fa1e9f9cf8427c3559537155_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('2efa5e321425c4e62b19a9b7e0b76cb3', '83c5caf7cbcb087cc50e822087c93a80', '8568097.jpg', '2efa5e321425c4e62b19a9b7e0b76cb3_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('2f4847cf656ba4659c3f83128bd51aec', '066392fe5d3bc9a368ab550951d51995', '8568097.jpg', '2f4847cf656ba4659c3f83128bd51aec_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('2f706e5105066a2d7fdbb9c78a63bc94', 'c3f26668fb050fb650f0b51298273ca2', '8578047.jpg', '2f706e5105066a2d7fdbb9c78a63bc94_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('31c249e6f8a3872d0a233c20ce650888', '749cc97c6b8697d91ca519f01926fff4', '3.jpg', '31c249e6f8a3872d0a233c20ce650888_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('32c67497ae239880f44a2ebf077366c6', 'c256aae88c9e5be071ec3749bcb31c91', '8578047.jpg', '32c67497ae239880f44a2ebf077366c6_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('33c1dac0e81bdc802aa18052307820cd', '552d0bbaf274955e7a72f9b30a8074b3', '2.jpg', '33c1dac0e81bdc802aa18052307820cd_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('366112b97fee826e978105d352e5c9df', 'c27e4b5f50565f333a268a3b45a411ec', '8568097.jpg', '366112b97fee826e978105d352e5c9df_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('3893c8831e36c059013ea5ff4f2f333a', '850137c2dbe8cc635d352ee22dcb7f8d', '8578047.jpg', '3893c8831e36c059013ea5ff4f2f333a_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('39b68e775da633f9096b974805956ec8', '6fa5991ee942f9b58870d1cf67a9d76a', '8568097.jpg', '39b68e775da633f9096b974805956ec8_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('3edf3088297fe306c84f7952bbdd5aff', '90edd8d52147c09f96e89775c3db9041', '1.jpg', '3edf3088297fe306c84f7952bbdd5aff_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('42bc6d420f5f9c178f284ed6fcdb3b55', '4b8ab17573cd8ac2311924b02a3b0530', '3.jpg', '42bc6d420f5f9c178f284ed6fcdb3b55_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('4423ffa17938d528202113247dd3794b', '605f5b0001b0d12badba30f330aad3cc', '3.jpg', '4423ffa17938d528202113247dd3794b_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('4623e1b772f65d6e3650e4600b4f0eff', 'ece4214864c7beb5e03d4e348a44fced', 'shop3.jpg', '4623e1b772f65d6e3650e4600b4f0eff_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('4839e84c77f90bf4c3d4d47af8cce071', 'd3ae7bad4c74ce27259ddcc0b26e11b0', '8578047.jpg', '4839e84c77f90bf4c3d4d47af8cce071_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('49ca9730443c8fcc0438f74ac2cae44a', '20ff89cc992f1398fd6a01af23856041', '3.jpg', '49ca9730443c8fcc0438f74ac2cae44a_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('49d682566752e67a6ef5fd0f1a4bef73', '96f024993cd03239c643fcbd1ff7ef1c', '8578047.jpg', '49d682566752e67a6ef5fd0f1a4bef73_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('4c144b843ff2e0a2b60566acf4d7f6b4', 'd3bfa50ee3f709c1a0de3cf81305993e', '8578047.jpg', '4c144b843ff2e0a2b60566acf4d7f6b4_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('4fc53f6d989586f5096a9de1eea637c8', '296bdb70f5ba055cd42dce55be85816c', '3.jpg', '4fc53f6d989586f5096a9de1eea637c8_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('4ff74c15c25c3198db4e189239af5e0f', '71baa99ee466e1dd9d2b4c0239e97af7', '3.jpg', '4ff74c15c25c3198db4e189239af5e0f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('51d14adb70ecc52f2b0e9372a55211c3', 'cdf963463ee29f03fff89a3092873fa8', '8578047.jpg', '51d14adb70ecc52f2b0e9372a55211c3_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('52874fb0c495c14f8cf31fc549fcd3da', '557e4f8d96e668b311441d9ae6335160', '8578047.jpg', '52874fb0c495c14f8cf31fc549fcd3da_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('52b71f422845f15943640e06070cf913', '6905bdc413eea5f950f6cc95ca81111c', '8578047.jpg', '52b71f422845f15943640e06070cf913_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('5331663aaf63bb2522236616be84c058', '8273ca0e99c10f57b602e0f3cf56ae9a', '2.jpg', '5331663aaf63bb2522236616be84c058_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('53bdf2da981c9554991a7726a3f67647', '3a0fcb2f6d78dcd5f741e8db6084b717', '2.jpg', '53bdf2da981c9554991a7726a3f67647_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('544a3035ef1c58bb6e9fbfa2abb8105b', 'f9219a26daaa1fc9b430fd7a5c738b9b', '8568097.jpg', '544a3035ef1c58bb6e9fbfa2abb8105b_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('5b242897cd393649415e90dc857ccee4', 'e95e04838828d21eba5d801e01bc4f72', '3.jpg', '5b242897cd393649415e90dc857ccee4_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('5e9168d9f6c64195c0bafafe17871b1e', 'e37e25c48179be24b50c22ccf93f699f', '8578047.jpg', '5e9168d9f6c64195c0bafafe17871b1e_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('5e9a26dc5f05099ffdd2f33e338ca771', '1e416556752f29e123f29f580ce03b05', 'shop1.jpg', '5e9a26dc5f05099ffdd2f33e338ca771_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('5ea6674faf8f0d74c106348af21bda65', 'fc7a297d2e86462b48b6eb59c0c4fcca', '8578047.jpg', '5ea6674faf8f0d74c106348af21bda65_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('5f3f494ae160152960f6f2dabb63da19', '90ce9ba0787b05ccb35ea72dcdb670d8', 'shop31.jpg', '5f3f494ae160152960f6f2dabb63da19_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('61f68da7e47cbef8fc6171f1b370e6db', '81f3f87784656e131dffbbc38131690c', '8578047.jpg', '61f68da7e47cbef8fc6171f1b370e6db_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('621c7b45fe5138fe2893bc2857419e2f', '2703f0284a61380a2aa85c6ee341f785', '3.jpg', '621c7b45fe5138fe2893bc2857419e2f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('65cca11aa235eb6b9ca803860e19ba25', 'f8c4cbdeb733964f821ed986bab97ecf', '8568097.jpg', '65cca11aa235eb6b9ca803860e19ba25_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('68274933ddf358256afaf52ad7e71a72', '5132b6a22d03af01ba471a6c4b3bcbbc', '3.jpg', '68274933ddf358256afaf52ad7e71a72_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('68c96a45a9bf220b758f3aaaff8b6a02', '89fd37abef850db94dbe70e56f0031ea', '3.jpg', '68c96a45a9bf220b758f3aaaff8b6a02_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('68d801f05b4c82bd3b1ea8cf428d439b', 'cee5a8e32da8fb55dad10ecf9d319160', '3.jpg', '68d801f05b4c82bd3b1ea8cf428d439b_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('69006a96c3d0968f6152f5cbd6820fbe', '00f1990573131c6fe266a1b1a9629b86', '3.jpg', '69006a96c3d0968f6152f5cbd6820fbe_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('6ab222181a87b205191979d8f6943463', 'dd0bd7aa340d6cba303ff95a025ce6d7', '3.jpg', '6ab222181a87b205191979d8f6943463_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('6bd33fff1034c8f46d1dbf088265fddc', '5ad461e3714ea0bbbf4113557cac8632', '8578047.jpg', '6bd33fff1034c8f46d1dbf088265fddc_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('6eaec90a76a3ad7c08386f95fe1f0a2b', '7786e6cb2038023726180d425d8cc113', '2.jpg', '6eaec90a76a3ad7c08386f95fe1f0a2b_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('7148e5ca760691feab9d3dbbf261a43a', '393775b72d87d8d769e51bf75c5c3cf5', '8578047.jpg', '7148e5ca760691feab9d3dbbf261a43a_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('7475b972d43a971f701d93e99dfca9ad', 'edb1a388451cafc5c57faaf54deffd22', 'shop12.jpg', '7475b972d43a971f701d93e99dfca9ad_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('76d7d7be151f751c93161d4bc59c88f4', 'a38dc89c1a03a6ae0b9545e98c30613e', 'shop2.jpg', '76d7d7be151f751c93161d4bc59c88f4_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('76ec4df8bfb832ae0d7c428df4f603d9', 'd47bde1eb540eda9e4cd17bc0a6db7cd', '8578047.jpg', '76ec4df8bfb832ae0d7c428df4f603d9_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('76f7921ff77c1c72cc81b49ffbff7397', '4ffcb6b05b9c7d41dce781e4b31d7665', '8568097.jpg', '76f7921ff77c1c72cc81b49ffbff7397_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('78b85c8a868e815102993962b0632634', 'ce3b7e789ba5fc23e6cddd24557bf765', '8578047.jpg', '78b85c8a868e815102993962b0632634_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('78dde51046996b4dd4d69acb2aec5bfa', 'c1db58294885490d5287557465787ae2', '3.jpg', '78dde51046996b4dd4d69acb2aec5bfa_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('7948b7fe65d1d469bb5096ab8d43cd04', '96d65ae6fe79802bca8b21d71a918344', '8568097.jpg', '7948b7fe65d1d469bb5096ab8d43cd04_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('7a26fba46e2664193e23b9f26111ad0e', '051d359fbbc03230a24ae6cba5b6b504', '8568097.jpg', '7a26fba46e2664193e23b9f26111ad0e_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('7d3d0c9a3b3c738861465a669bf4a9a8', '2920718f1a0413ec0eb9c9deafe3b989', '2.jpg', '7d3d0c9a3b3c738861465a669bf4a9a8_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('802f664774f7fb36e658dfd238749d68', '54555a80315f3bed0ba79aa2a6b9d698', '3.jpg', '802f664774f7fb36e658dfd238749d68_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('80ff71a1e9ab7264bfaec51234332256', '9f9836e687f4d24c6b17f0e11b319066', '3.jpg', '80ff71a1e9ab7264bfaec51234332256_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('83d0b738797efaea8335608c6a0c3ec7', 'f23c4772cf8578c3e6b00a3d7683f87e', '8568097.jpg', '83d0b738797efaea8335608c6a0c3ec7_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('861f479584071060c8e8efccc2770a89', '58ae477903ad99925eba701e5f5b229d', '8578047.jpg', '861f479584071060c8e8efccc2770a89_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('870cfd9ff2661776ccd058a7efbd1543', '5ae8cf1f5f22a27c7e115e76c46cda6e', '8578047.jpg', '870cfd9ff2661776ccd058a7efbd1543_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('87189bc099fe606a40e00394ab842347', '2414fd8ba0c8f1a45ea3feeda0f02664', '1.jpg', '87189bc099fe606a40e00394ab842347_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('8b1c95e2fa10884324ba595f60a92e4d', 'e61ef65541df21af70da3a425d89fa6b', '8578047.jpg', '8b1c95e2fa10884324ba595f60a92e4d_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('8b4dd0d7bbaa83ddcfa76b1f542ca7d9', '1827a5aad0c04c65565a30355566a0da', '8578047.jpg', '8b4dd0d7bbaa83ddcfa76b1f542ca7d9_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('8bf26a1a34eefa1fc10f90f4a21a1a30', '6334c7203a930716205c74f70719b551', '3.jpg', '8bf26a1a34eefa1fc10f90f4a21a1a30_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('8d8d6c2cddc2516eb0b736cbe763adda', 'f5bc70f9751b6c029253b0843adc2474', '8578047.jpg', '8d8d6c2cddc2516eb0b736cbe763adda_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('9075cbd4f5f0032e68d3a90849637181', '577b35a548714724dc50d4ac940de341', '8578047.jpg', '9075cbd4f5f0032e68d3a90849637181_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('90ffb2b0bee4a55a67a1fb4b4875898f', '80ae1e523ed14fdd0fea7f4b8f7620f7', '8578047.jpg', '90ffb2b0bee4a55a67a1fb4b4875898f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('911f34a3cf4f4e11ac49a8297221dd6d', '252ae7630f5bc823310c42a82f7be50d', '8578047.jpg', '911f34a3cf4f4e11ac49a8297221dd6d_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('94d5b44e3c68844ed335d6ac2ac404af', 'f0d3f40ccc8c7b2dc236ccb41ba46fcf', '8578047.jpg', '94d5b44e3c68844ed335d6ac2ac404af_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('977e6003a6b145c819e9006ec3d24073', 'd98a5c3eaac1eaee400d4fb96e72b5c3', '3.jpg', '977e6003a6b145c819e9006ec3d24073_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('97c7b2e0cd7097c7f1016bbefbde1965', 'eb86fcfc1de1ae92996cfe0f807e7293', '8578047.jpg', '97c7b2e0cd7097c7f1016bbefbde1965_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('99a969168d935b25cd2bcb20482b9a9f', '280f699379eccb85a5b272feb6709f2c', '8568097.jpg', '99a969168d935b25cd2bcb20482b9a9f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('9b0177aa5ef8e0c925661fd8b7eec19f', '5e7a2fff72e1b5dfe687e87e5a7a044c', '8568097.jpg', '9b0177aa5ef8e0c925661fd8b7eec19f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('9ba90c6f86d45dcef9d340fc98afe393', '4a7e50f744a6ab0d09be9679f0b78929', '8578047.jpg', '9ba90c6f86d45dcef9d340fc98afe393_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('9bbd698641940a787a38c96a1ed0ac2b', '3c6d164ca741df158af84c6903d4cfda', '8568097.jpg', '9bbd698641940a787a38c96a1ed0ac2b_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('9e4436bfe8d7c0ac2d443c04ff1c1e62', 'd5d3c6077332701c21473e57d9429fd3', '3.jpg', '9e4436bfe8d7c0ac2d443c04ff1c1e62_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('a280c49bb795333b07ee6ddae617c6f4', 'b562af10288637c8559312c5f7285083', '8568097.jpg', 'a280c49bb795333b07ee6ddae617c6f4_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('a3d7f3b9e7e4602d555fca0bc0046c0b', 'a6bcb06f220cd135e69d6095f501ee42', '8578047.jpg', 'a3d7f3b9e7e4602d555fca0bc0046c0b_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('a4a650ac579e06223d6a449573f9a157', '03a4ab668a14360d18f7383c43d9a895', '3.jpg', 'a4a650ac579e06223d6a449573f9a157_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('a4c889c427d26a45d81ea73af8e0c5db', '134a0dacf18d3e81fc5b9b5938c3448d', '2.jpg', 'a4c889c427d26a45d81ea73af8e0c5db_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('a5b7ba952c9678257a985e9509e251a0', 'f8bbcc6c9a1273610d6c607bab43305a', '4.jpg', 'a5b7ba952c9678257a985e9509e251a0_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('a5dadd2bf35a3066306aa46f53b6c929', '06eca1eeea7c67ac6f63ca690e7175e0', '8578047.jpg', 'a5dadd2bf35a3066306aa46f53b6c929_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('a835a7da5fb34ccd5904843da08e468d', 'c6b2142c12b85fbf1020c20fc1bbb184', '8578047.jpg', 'a835a7da5fb34ccd5904843da08e468d_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('a87cb670e7d4345470dc4cd1a105df2a', 'ece1dc15a88f802397da3be577187867', '8568097.jpg', 'a87cb670e7d4345470dc4cd1a105df2a_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('a8dae8cf7afc702ecbae8c8bbd70fd58', 'a8e33a50e0cb1e7e6f3b3189a968baef', '8578047.jpg', 'a8dae8cf7afc702ecbae8c8bbd70fd58_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('ad3d65bc74f39c9543bd28a43ef86a47', 'f5096718b6fb75a6d99d0223e21b5d0e', '2.jpg', 'ad3d65bc74f39c9543bd28a43ef86a47_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('ae1368904088ed43ee058a6d2572cbef', '00203821896f0ce332238921532b3e1c', '8568097.jpg', 'ae1368904088ed43ee058a6d2572cbef_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('af1d7782a0fafac50abd7cd2798fefe1', 'aebe60bece5e4c3354147820edea4130', 'shop11.jpg', 'af1d7782a0fafac50abd7cd2798fefe1_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('af202677b3cf0d9e94061f4501f150b1', '6e5b20654f79cab54d9503a3a2f44c13', '3.jpg', 'af202677b3cf0d9e94061f4501f150b1_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('af20d9112bcf84451f7deaf7c899ff24', 'aafeba56529a0aebe3e9f5aca1f135bc', '8578047.jpg', 'af20d9112bcf84451f7deaf7c899ff24_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('b136511a4a41c1a45e78a5a83e483948', 'abc42e5edce9f51a6093c68cb2c6aef6', '8568097.jpg', 'b136511a4a41c1a45e78a5a83e483948_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('b460716a5f89eaec6db877fa183b7bc7', 'c60eacab77cb04eb60492e82fc5c424e', '3.jpg', 'b460716a5f89eaec6db877fa183b7bc7_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('b541600ad83556b108f620e4a6d768e0', '6754fc2c460d86f8051d77e8ea24b82c', '8578047.jpg', 'b541600ad83556b108f620e4a6d768e0_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('bbab26ce22975e0ecdd0f1e1aa8a2678', '28e0e289eb41de90361be8f0ecbc04cf', '3.jpg', 'bbab26ce22975e0ecdd0f1e1aa8a2678_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('bd25d14df06f2964507c127362ae000c', '102ac40a5803d87c90f0239bd68d25f0', '1.jpg', 'bd25d14df06f2964507c127362ae000c_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('bd3f90456025276fefd21863145bfcee', 'f43ee5519a0af2c3cd4bf50d9859f004', '8568097.jpg', 'bd3f90456025276fefd21863145bfcee_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('bf030a3662821eeed91c1598acd49eec', '1eec02249160fd758303f378b7607cde', '8578047.jpg', 'bf030a3662821eeed91c1598acd49eec_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('c00015c76ba5986e180fb7d20da0682b', 'f82a387989303c59fe879e238e4ca86c', '3.jpg', 'c00015c76ba5986e180fb7d20da0682b_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('c0a8cf6764f2f75a9bcf8632e7329538', 'a48b7ebba5c643f8bcc05fda96be513d', '8578047.jpg', 'c0a8cf6764f2f75a9bcf8632e7329538_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('c126bf86dbb14b56f3cc9b45a401a8d5', '47d96506c2cd286c8f38c0af21e4e14f', '8578047.jpg', 'c126bf86dbb14b56f3cc9b45a401a8d5_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('c159830a033b4baa5fcd69c77d2dc375', 'e368d7efcedbad10a521ead3def5fa46', '8568097.jpg', 'c159830a033b4baa5fcd69c77d2dc375_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('c2a12f95303c83b93afeca9e8bb7f524', '3f1c83146d947faa21e98b3a5df26dad', '1.jpg', 'c2a12f95303c83b93afeca9e8bb7f524_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('c3b5c7adc50ae82f9a81cb7c9319b4d2', '3b27f9b6120340c276a2f832ec5c78aa', '8568097.jpg', 'c3b5c7adc50ae82f9a81cb7c9319b4d2_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('c823bdbd45b166237a9831d063ef34ec', '8645346be90c31f2447bfcda9a7494c2', 'shop1.jpg', 'c823bdbd45b166237a9831d063ef34ec_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('c8b2c70288e3525e5792df5cd48ffdd1', '857a1d09afe8093f04e3855b81bc55e9', '8578047.jpg', 'c8b2c70288e3525e5792df5cd48ffdd1_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('caf3c5a78e8c9bb0797878b365b83530', 'ff863a37f9de2fd007b86c0affa1a397', '4.jpg', 'caf3c5a78e8c9bb0797878b365b83530_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('cb1be99b2bd904bb8a176a04f7295a32', 'fabae54c2c79bb3b5e6de9c297748fab', '8578047.jpg', 'cb1be99b2bd904bb8a176a04f7295a32_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('cbef2df4134190cbc4703471f8762e39', '668c73406213afb9a1d519bab5928a5e', '3.jpg', 'cbef2df4134190cbc4703471f8762e39_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('d034c5799f04fefc367082030a6a422e', '718f048540571e3556ef41c745a6b172', '3.jpg', 'd034c5799f04fefc367082030a6a422e_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('d967c59d7a6b4e091aa96a0124e9c84f', '2a3ce26dbbbb68b44d420607e256f921', '8578047.jpg', 'd967c59d7a6b4e091aa96a0124e9c84f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('db54080e374d756ae1ec1c2f17161145', '97589186fd7b1b9254a566a01be5da8e', '8568097.jpg', 'db54080e374d756ae1ec1c2f17161145_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('dbb80537c879b7c1cdf27bb08a0e2ada', 'ee5d3a9596cdc3bef9d2ed694a414e43', 'shop21.jpg', 'dbb80537c879b7c1cdf27bb08a0e2ada_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('de91a09dacd42395e6345f85308db1b8', '82ce38027a7fd7922b5db47d9c766c6d', '2.jpg', 'de91a09dacd42395e6345f85308db1b8_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('e0157655c42ceb637c38d73a5293c6cb', '988f36f976dc742865950d3594a706b9', '8578047.jpg', 'e0157655c42ceb637c38d73a5293c6cb_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('e07825d03c9bfe3175551ec0becbf4fd', '11e761fa98833b4ba8c01b31df4cd8ee', '3.jpg', 'e07825d03c9bfe3175551ec0becbf4fd_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('e0c7aac91de8ae090f8eca5d1539bac8', '6b46706d1f4cde6217eac95eab90b064', '2.jpg', 'e0c7aac91de8ae090f8eca5d1539bac8_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('e0cd5a5ba0adbdafa479bb46df1f801f', '0dab8152ffe583826e259e076262af15', '8578047.jpg', 'e0cd5a5ba0adbdafa479bb46df1f801f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('e218dd91fb4edfcb562a98e441f29574', 'fd1375a02156433e57f66bd92fe16a63', '8578047.jpg', 'e218dd91fb4edfcb562a98e441f29574_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('e23547bf0dffe026dae8e50a33788d84', '169c1e6f09a7dd925ecc939b87787c37', '8578047.jpg', 'e23547bf0dffe026dae8e50a33788d84_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('e3e8a0912836bf1e0ba1e4d107265db6', 'f51abdfc51baccc1dca72513eff2b6e1', '8578047.jpg', 'e3e8a0912836bf1e0ba1e4d107265db6_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('e48dabb762382be6c447406cf70b8cf1', 'dbb3a87732a0cade5d5b828d41d84747', '4.jpg', 'e48dabb762382be6c447406cf70b8cf1_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('e8757616018c65bb40640dcb41fd26e1', 'f25a4be4d5847cc89187d86fc5f7dba3', '8568097.jpg', 'e8757616018c65bb40640dcb41fd26e1_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('e8819c773958c31bb0547ea98722e151', 'f201777e11fabdee40c1d5494cc0eae2', '8568097.jpg', 'e8819c773958c31bb0547ea98722e151_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('e9345e580a4f8fd5a6f10cd1fe2e9af1', 'c65d4601bebe0d940feed5e08940999c', '2.jpg', 'e9345e580a4f8fd5a6f10cd1fe2e9af1_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('ecda3e04f17c58a97739f3ed749b8efa', '8c1e5b822c21077264de3995263fed2b', '8578047.jpg', 'ecda3e04f17c58a97739f3ed749b8efa_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('eeb290a6013facb7c26b4d12073daa1e', '76f3e9e55335829eeb2f2be62d45d850', '3.jpg', 'eeb290a6013facb7c26b4d12073daa1e_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('ef51ff30e273aab8ce96eb0952dfd6e5', '2be6897ebe92da2dc65ae4b3ebb882d0', '2.jpg', 'ef51ff30e273aab8ce96eb0952dfd6e5_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f07284cab4b5ebdc25912984e86838c9', 'c883ca9f49682252ae8ebc7922e52e52', '8578047.jpg', 'f07284cab4b5ebdc25912984e86838c9_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('f167ec8069f5e205e22d5a0464185453', '20df6cd569398db3724389d3777d754a', '8578047.jpg', 'f167ec8069f5e205e22d5a0464185453_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f1b3f5945b641b598199e3534d48c469', '21500f88f3ef2417779e737218a6aafe', '8578047.jpg', 'f1b3f5945b641b598199e3534d48c469_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f21458b5dc2c7cccec1f3344f2934af4', 'dab0360e73a907123329535d4101bd2f', '8578047.jpg', 'f21458b5dc2c7cccec1f3344f2934af4_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('f276b8f3b52f8514aabccfa26fdd9e7f', '9a689f0734a52117bef833e79252ff18', '8578047.jpg', 'f276b8f3b52f8514aabccfa26fdd9e7f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f46e9fb87ac870e01c9a44b0ae2df541', '789818261ee09b070993bf8db3227877', '3.jpg', 'f46e9fb87ac870e01c9a44b0ae2df541_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f76b989428e507b4cdc67c1d974ca641', 'd76233f789dad659a893715a967393f9', '8568097.jpg', 'f76b989428e507b4cdc67c1d974ca641_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('f8cce997b9c17acb965ee9ed4f6f1145', 'fd3f6cb18298c05e1653ef429ffe120e', '8568097.jpg', 'f8cce997b9c17acb965ee9ed4f6f1145_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f8e88716475463fd310ff678077bc4eb', '2fdb351fa1f34ccb77d7ffe6fe6c43ae', '2.jpg', 'f8e88716475463fd310ff678077bc4eb_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f8edac32327a3ac91ab5bd6b05135b96', '447127a99f66756b3b6e1eb54533d3b2', '8578047.jpg', 'f8edac32327a3ac91ab5bd6b05135b96_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f905ca0dde8668b6f49fa6715e08b346', '6dc67e81640d7943668e236e7599bbef', '3.jpg', 'f905ca0dde8668b6f49fa6715e08b346_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f97a483d6c08f5050cd79cee212c194c', '1f1b53a5b31e8d2b33a10a89dc409f5a', '8578047.jpg', 'f97a483d6c08f5050cd79cee212c194c_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('f9df6102f1c6ae4f1524d7ed74646a9f', 'fa3e80992cec395f6ec7e3667c7deb85', '8578047.jpg', 'f9df6102f1c6ae4f1524d7ed74646a9f_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('fb5f6066039534f695e08da3ca149328', 'c64043ad971c7c6bc5f33b70d30f76a0', '8578047.jpg', 'fb5f6066039534f695e08da3ca149328_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', '');
INSERT INTO `sys_oss` VALUES ('fc6b7238932446d49fd799f881dfeb02', '776379b32e045a90681aa01682c88e91', 'shop32.jpg', 'fc6b7238932446d49fd799f881dfeb02_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('fd2a05b47d6023434bba0a50316499cc', '616be81b7374fc08c8f94322b47258b6', '8578047.jpg', 'fd2a05b47d6023434bba0a50316499cc_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('fd456e7263ab2880a9365b38ac9f4472', '58c40b5feb0361d82047d9258be0486a', '2.jpg', 'fd456e7263ab2880a9365b38ac9f4472_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);
INSERT INTO `sys_oss` VALUES ('ff783dbf6fa650065957617922c65cbb', 'd04a4f8966ccf8229da40022d079a4ed', '8568097.jpg', 'ff783dbf6fa650065957617922c65cbb_.jpg', NULL, NULL, '1', 1, NULL, NULL, NULL, 'test', NULL);

SET FOREIGN_KEY_CHECKS = 1;
