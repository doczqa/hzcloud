-- MySQL dump 10.13  Distrib 8.0.26, for macos11.3 (x86_64)
--
-- Host: db.cheercare.net    Database: ry_vue_test
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acs_alarm_info`
--

DROP TABLE IF EXISTS `acs_alarm_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_alarm_info` (
  `alarm_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `alarm_time` datetime NOT NULL COMMENT '报警时间',
  `alarm_type` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报警类型',
  `controller_id` bigint(20) DEFAULT NULL COMMENT '控制器Id',
  `controller_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '控制器',
  `controller_ip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '控制器IP',
  `door_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '门名称',
  `door_pin` int(11) DEFAULT NULL COMMENT '门端口',
  `alarm_detail` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '详情',
  `user_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户',
  `card_number` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡号',
  PRIMARY KEY (`alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_card`
--

DROP TABLE IF EXISTS `acs_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_card` (
  `card_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `card_number` varchar(225) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '卡号',
  `user_id` int(20) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '持卡人',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `id_type` char(4) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '证件类型',
  `id_number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '证件号',
  `sex` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '2' COMMENT '性别',
  `phonenumber` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `identity_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '身份类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建者',
  `cancelling_time` datetime DEFAULT NULL COMMENT '注销时间',
  `cancelling_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '注销者',
  `expiration_start_time` datetime NOT NULL COMMENT '有效期起',
  `expiration_end_time` datetime NOT NULL COMMENT '有效期止',
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB AUTO_INCREMENT=530 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_command_queue`
--

DROP TABLE IF EXISTS `acs_command_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_command_queue` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `controller_id` int(11) NOT NULL COMMENT '控制器ID',
  `ip` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '控制器IP',
  `door_pin` int(11) DEFAULT NULL COMMENT '门ID',
  `command` int(11) DEFAULT NULL COMMENT '命令',
  `data` longtext COLLATE utf8mb4_unicode_ci COMMENT '暂存数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `issue_time` datetime DEFAULT NULL COMMENT '下发时间',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `elapsed_time` int(11) DEFAULT NULL COMMENT '耗时',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态 0:已入队列 1:已生成 2：已执行',
  `result_code` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果 0：成功 1：失败',
  `result_msg` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果信息',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37651 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='命令任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_device_controller`
--

DROP TABLE IF EXISTS `acs_device_controller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_device_controller` (
  `controller_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `controller_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '控制器名',
  `ip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'IP地址',
  `port` int(11) NOT NULL COMMENT '端口',
  `type` char(4) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备类型',
  `capacity` int(4) DEFAULT NULL COMMENT '卡容量',
  `user_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登陆用户名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登陆密码',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `alarm_status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '布防',
  `identifier` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标识符',
  `controller_index` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '控制器索引',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`controller_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_device_door`
--

DROP TABLE IF EXISTS `acs_device_door`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_device_door` (
  `door_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `door_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '门名称',
  `controller_id` bigint(20) NOT NULL COMMENT '控制器ID',
  `controller_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pin` tinyint(4) NOT NULL COMMENT '端口',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态',
  `identifier` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标识符',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`door_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_device_zone`
--

DROP TABLE IF EXISTS `acs_device_zone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_device_zone` (
  `zone_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `zone_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '区域名称',
  `dept_id` bigint(20) NOT NULL COMMENT '所属部门',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`zone_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_device_zone_controller`
--

DROP TABLE IF EXISTS `acs_device_zone_controller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_device_zone_controller` (
  `zone_id` bigint(20) NOT NULL COMMENT '区域ID',
  `controller_id` bigint(20) NOT NULL COMMENT '控制器ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_holiday_group`
--

DROP TABLE IF EXISTS `acs_holiday_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_holiday_group` (
  `group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `group_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '假日组名称',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_holiday_group_plan`
--

DROP TABLE IF EXISTS `acs_holiday_group_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_holiday_group_plan` (
  `group_id` bigint(20) NOT NULL COMMENT '假日组ID',
  `plan_id` bigint(20) NOT NULL COMMENT '假日计划ID',
  PRIMARY KEY (`group_id`,`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_holiday_plan`
--

DROP TABLE IF EXISTS `acs_holiday_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_holiday_plan` (
  `plan_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `plan_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '假日计划名称',
  `start_date` date DEFAULT NULL COMMENT '起始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `start_time_1` time DEFAULT NULL COMMENT '时段一起',
  `end_time_1` time DEFAULT NULL COMMENT '时段一止',
  `start_time_2` time DEFAULT NULL COMMENT '时段二起',
  `end_time_2` time DEFAULT NULL COMMENT '时段二止',
  `start_time_3` time DEFAULT NULL COMMENT '时段三起',
  `end_time_3` time DEFAULT NULL COMMENT '时段三止',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_holiday_time_slice`
--

DROP TABLE IF EXISTS `acs_holiday_time_slice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_holiday_time_slice` (
  `id` bigint(20) NOT NULL,
  `start_time_1` time NOT NULL,
  `end_time_1` time NOT NULL,
  `start_time_2` time DEFAULT NULL,
  `end_time_2` time DEFAULT NULL,
  `start_time_3` time DEFAULT NULL,
  `end_time_3` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_v6_appgroup_holiday`
--

DROP TABLE IF EXISTS `acs_v6_appgroup_holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_v6_appgroup_holiday` (
  `id` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用群组',
  `pre_holiday_tz_1` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期前一天通行时区一',
  `pre_holiday_tz_1_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期前一天通行时区一直接外出',
  `pre_holiday_tz_2` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期前一天通行时区二',
  `pre_holiday_tz_2_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期前一天通行时区二直接外出',
  `pre_holiday_tz_3` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期前一天通行时区三',
  `pre_holiday_tz_3_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期前一天通行时区三直接外出',
  `pre_holiday_tz_4` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期前一天通行时区四',
  `pre_holiday_tz_4_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期前一天通行时区四直接外出',
  `pre_holiday_tz_5` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期前一天通行时区五',
  `pre_holiday_tz_5_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期前一天通行时区五直接外出',
  `pre_holiday_tz_6` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期前一天通行时区六',
  `pre_holiday_tz_6_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期前一天通行时区六直接外出',
  `pre_holiday_tz_7` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期前一天通行时区七',
  `pre_holiday_tz_7_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期前一天通行时区七直接外出',
  `pre_holiday_tz_8` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期前一天通行时区八',
  `pre_holiday_tz_8_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期前一天通行时区八直接外出',
  `holiday_tz_1` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期通行时区一',
  `holiday_tz_1_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期通行时区一直接外出',
  `holiday_tz_2` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期通行时区二',
  `holiday_tz_2_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期通行时区二直接外出',
  `holiday_tz_3` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期通行时区三',
  `holiday_tz_3_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期通行时区三直接外出',
  `holiday_tz_4` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期通行时区四',
  `holiday_tz_4_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期通行时区四直接外出',
  `holiday_tz_5` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期通行时区五',
  `holiday_tz_5_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期通行时区五直接外出',
  `holiday_tz_6` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期通行时区六',
  `holiday_tz_6_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期通行时区六直接外出',
  `holiday_tz_7` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期通行时区七',
  `holiday_tz_7_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期通行时区七直接外出',
  `holiday_tz_8` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期通行时区八',
  `holiday_tz_8_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期通行时区八直接外出',
  `post_holiday_tz_1` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期后一天通行时区一',
  `post_holiday_tz_1_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期后一天通行时区一直接外出',
  `post_holiday_tz_2` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期后一天通行时区二',
  `post_holiday_tz_2_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期后一天通行时区二直接外出',
  `post_holiday_tz_3` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期后一天通行时区三',
  `post_holiday_tz_3_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期后一天通行时区三直接外出',
  `post_holiday_tz_4` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期后一天通行时区四',
  `post_holiday_tz_4_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期后一天通行时区四直接外出',
  `post_holiday_tz_5` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期后一天通行时区五',
  `post_holiday_tz_5_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期后一天通行时区五直接外出',
  `post_holiday_tz_6` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期后一天通行时区六',
  `post_holiday_tz_6_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期后一天通行时区六直接外出',
  `post_holiday_tz_7` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期后一天通行时区七',
  `post_holiday_tz_7_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期后一天通行时区七直接外出',
  `post_holiday_tz_8` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '60' COMMENT '假期后一天通行时区八',
  `post_holiday_tz_8_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '假期后一天通行时区八直接外出'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_v6_appgroup_setting`
--

DROP TABLE IF EXISTS `acs_v6_appgroup_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_v6_appgroup_setting` (
  `id` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用群组',
  `door` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '控制门区',
  `door_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '直接外出',
  `sec_pin_tz_1` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安全密码检查时区门区一',
  `sec_pin_tz_2` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安全密码检查时区门区二',
  `sec_pin_tz_3` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安全密码检查时区门区三',
  `sec_pin_tz_4` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '安全密码检查时区门区四',
  `per_pin_tz_1` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个人密码检查时区门区一',
  `per_pin_tz_2` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个人密码检查时区门区二',
  `per_pin_tz_3` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个人密码检查时区门区三',
  `per_pin_tz_4` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个人密码检查时区门区四',
  `door_detail` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '门区'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_v6_appgroup_week`
--

DROP TABLE IF EXISTS `acs_v6_appgroup_week`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_v6_appgroup_week` (
  `id` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用群组',
  `sun_tz` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周日时区',
  `sun_tz_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周日时区直接外出',
  `mon_tz` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周一时区',
  `mon_tz_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周一时区直接外出',
  `tue_tz` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周二时区',
  `tue_tz_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周二时区直接外出',
  `wed_tz` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周三时区',
  `wed_tz_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周三时区直接外出',
  `thu_tz` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周四时区',
  `thu_tz_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周四时区直接外出',
  `fri_tz` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周五时区',
  `fri_tz_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周五时区直接外出',
  `sat_tz` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周六时区',
  `sat_tz_out` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '周六时区直接外出'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_v6_card_index`
--

DROP TABLE IF EXISTS `acs_v6_card_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_v6_card_index` (
  `controller_id` bigint(20) DEFAULT NULL COMMENT '控制器id',
  `card_id` bigint(20) DEFAULT NULL COMMENT '卡id',
  `card_index` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡片索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_v6_holiday`
--

DROP TABLE IF EXISTS `acs_v6_holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_v6_holiday` (
  `month` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '月份',
  `holidays` varchar(31) COLLATE utf8mb4_unicode_ci DEFAULT '0000000000000000000000000000000' COMMENT '假日'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_v6_interval`
--

DROP TABLE IF EXISTS `acs_v6_interval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_v6_interval` (
  `id` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时段',
  `start_time` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT '0000' COMMENT '开始时间',
  `end_time` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT '0000' COMMENT '结束时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_v6_timezone`
--

DROP TABLE IF EXISTS `acs_v6_timezone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_v6_timezone` (
  `id` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时区',
  `interval_1` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '31' COMMENT '时段一',
  `interval_2` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '31' COMMENT '时段二',
  `interval_3` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT '31' COMMENT '时段三'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acs_week_template`
--

DROP TABLE IF EXISTS `acs_week_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acs_week_template` (
  `template_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `template_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `holiday_group_id` bigint(20) DEFAULT NULL COMMENT '节日组',
  `mon_start_time_1` time DEFAULT NULL COMMENT '周一时段一起',
  `mon_end_time_1` time DEFAULT NULL COMMENT '周一时段一止',
  `mon_start_time_2` time DEFAULT NULL COMMENT '周一时段二起',
  `mon_end_time_2` time DEFAULT NULL COMMENT '周一时段二止',
  `mon_start_time_3` time DEFAULT NULL COMMENT '周一时段三起',
  `mon_end_time_3` time DEFAULT NULL COMMENT '周一时段三止',
  `tue_start_time_1` time DEFAULT NULL COMMENT '周二时段一起',
  `tue_end_time_1` time DEFAULT NULL COMMENT '周二时段一止',
  `tue_start_time_2` time DEFAULT NULL COMMENT '周二时段二起',
  `tue_end_time_2` time DEFAULT NULL COMMENT '周二时段二止',
  `tue_start_time_3` time DEFAULT NULL COMMENT '周二时段三起',
  `tue_end_time_3` time DEFAULT NULL COMMENT '周二时段三止',
  `wen_start_time_1` time DEFAULT NULL COMMENT '周三时段一起',
  `wen_end_time_1` time DEFAULT NULL COMMENT '周三时段一止',
  `wen_start_time_2` time DEFAULT NULL COMMENT '周三时段二起',
  `wen_end_time_2` time DEFAULT NULL COMMENT '周三时段二止',
  `wen_start_time_3` time DEFAULT NULL COMMENT '周三时段三起',
  `wen_end_time_3` time DEFAULT NULL COMMENT '周三时段三止',
  `tur_start_time_1` time DEFAULT NULL COMMENT '周四时段一起',
  `tur_end_time_1` time DEFAULT NULL COMMENT '周四时段一止',
  `tur_start_time_2` time DEFAULT NULL COMMENT '周四时段二起',
  `tur_end_time_2` time DEFAULT NULL COMMENT '周四时段二止',
  `tur_start_time_3` time DEFAULT NULL COMMENT '周四时段三起',
  `tur_end_time_3` time DEFAULT NULL COMMENT '周四时段三止',
  `fri_start_time_1` time DEFAULT NULL COMMENT '周五时段一起',
  `fri_end_time_1` time DEFAULT NULL COMMENT '周五时段一止',
  `fri_start_time_2` time DEFAULT NULL COMMENT '周五时段二起',
  `fri_end_time_2` time DEFAULT NULL COMMENT '周五时段二起',
  `fri_start_time_3` time DEFAULT NULL COMMENT '周五时段三起',
  `fri_end_time_3` time DEFAULT NULL COMMENT '周五时段三止',
  `sat_start_time_1` time DEFAULT NULL COMMENT '周六时段一起',
  `sat_end_time_1` time DEFAULT NULL COMMENT '周六时段一止',
  `sat_start_time_2` time DEFAULT NULL COMMENT '周六时段二起',
  `sat_end_time_2` time DEFAULT NULL COMMENT '周六时段二止',
  `sat_start_time_3` time DEFAULT NULL COMMENT '周六时段三起',
  `sat_end_time_3` time DEFAULT NULL COMMENT '周六时段三止',
  `sun_start_time_1` time DEFAULT NULL COMMENT '周日时段一起',
  `sun_end_time_1` time DEFAULT NULL COMMENT '周日时段一止',
  `sun_start_time_2` time DEFAULT NULL COMMENT '周日时段二起',
  `sun_end_time_2` time DEFAULT NULL COMMENT '周日时段二止',
  `sun_start_time_3` time DEFAULT NULL COMMENT '周日时段三起',
  `sun_end_time_3` time DEFAULT NULL COMMENT '周日时段三止',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `remake` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asc_door_group`
--

DROP TABLE IF EXISTS `asc_door_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asc_door_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '门禁组名称',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `week_template_id` int(11) DEFAULT NULL COMMENT '周计划模板',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='门禁组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asc_door_group_door`
--

DROP TABLE IF EXISTS `asc_door_group_door`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asc_door_group_door` (
  `door_group_id` int(11) NOT NULL COMMENT '门禁组ID,关联asc_device_door_group表group_id字段',
  `door_id` int(11) NOT NULL COMMENT '门ID,关联asc_device_door表door_id字段',
  PRIMARY KEY (`door_group_id`,`door_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='门禁组和门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asc_door_group_user_group`
--

DROP TABLE IF EXISTS `asc_door_group_user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asc_door_group_user_group` (
  `door_group_id` int(11) NOT NULL COMMENT '门禁组ID,关联asc_device_door_group表group_id字段,',
  `user_group_id` int(11) NOT NULL COMMENT '用户组ID,关联asc_user_group表group_id字段',
  PRIMARY KEY (`door_group_id`,`user_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组和用户关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asc_user_group`
--

DROP TABLE IF EXISTS `asc_user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asc_user_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户组名称',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `dept_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门名称',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asc_user_group_user`
--

DROP TABLE IF EXISTS `asc_user_group_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asc_user_group_user` (
  `user_group_id` int(11) NOT NULL COMMENT '用户组ID,关联asc_user_group表group_id字段',
  `user_id` int(11) NOT NULL COMMENT '用户ID,关联sys_user表user_id字段',
  PRIMARY KEY (`user_group_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组和用户关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `calendar_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cron_expression` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time_zone_id` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `entry_id` varchar(95) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `instance_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `job_group` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_nonconcurrent` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `requests_recovery` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `job_class_name` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_durable` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_nonconcurrent` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_update_data` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `requests_recovery` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lock_name` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `instance_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `str_prop_1` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `str_prop_2` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `str_prop_3` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bool_prop_2` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `job_group` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trigger_type` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `dept_num` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门编号',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=721001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) COLLATE utf8mb4_unicode_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '字典类型',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日志信息',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=360 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=904 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  `user_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `id_type` char(4) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '证件类型',
  `id_number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '证件号',
  `identity_id` bigint(20) NOT NULL COMMENT '身份类型',
  `face_info` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '人脸信息',
  `phonenumber` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `avatar` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '密码',
  `status` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=532 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-19 20:17:41
