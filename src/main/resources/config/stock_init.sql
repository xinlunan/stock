/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.54-community : Database - xu_stock
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `t_stock` */

DROP TABLE IF EXISTS `t_stock`;

CREATE TABLE `t_stock` (
  `stock_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '股票Id',
  `exchange` char(2) DEFAULT NULL COMMENT '交易所',
  `stock_code` char(6) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(10) DEFAULT NULL COMMENT '股票名称',
  `asset` bigint(20) DEFAULT NULL COMMENT '总资产',
  `last_date` date DEFAULT NULL COMMENT '最后获取指数日期',
  `last_close` int(11) DEFAULT NULL COMMENT '最后收盘价',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`stock_id`),
  UNIQUE KEY `index_stock_code` (`stock_code`),
  KEY `index_stock_name` (`stock_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2769 DEFAULT CHARSET=utf8;

/*Table structure for table `t_stock_index` */

DROP TABLE IF EXISTS `t_stock_index`;

CREATE TABLE `t_stock_index` (
  `index_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '股票每日指数Id',
  `stock_id` int(11) DEFAULT NULL COMMENT '股票Id',
  `stock_code` char(6) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(10) DEFAULT NULL COMMENT '股票名称',
  `date` date DEFAULT NULL COMMENT '日期',
  `last_close` int(11) DEFAULT NULL COMMENT '昨天收盘价',
  `open` int(11) DEFAULT NULL COMMENT '开盘价',
  `close` int(11) DEFAULT NULL COMMENT '收盘价',
  `close_gap` int(11) DEFAULT NULL COMMENT '涨跌额',
  `close_gap_rate` float DEFAULT NULL COMMENT '涨跌幅',
  `high` int(11) DEFAULT NULL COMMENT '最高价',
  `low` int(11) DEFAULT NULL COMMENT '最低价',
  `high_gap` int(11) DEFAULT NULL COMMENT '最高价与昨收差价 high-last_close',
  `high_gap_rate` float DEFAULT NULL COMMENT '最高价与昨收差价比例 (high-last_close)/last_close',
  `low_gap` int(11) DEFAULT NULL COMMENT '最低价与昨收差价 low-last_close',
  `low_gap_rate` float DEFAULT NULL COMMENT '最低价与昨收差价比例 (low-last_close)/last_close',
  `amount` bigint(20) DEFAULT NULL COMMENT '成交额',
  `volume` bigint(20) DEFAULT NULL COMMENT '成交量',
  `asset` bigint(20) DEFAULT NULL COMMENT '总资产',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`index_id`),
  KEY `NewIndex1` (`stock_id`),
  KEY `NewIndex2` (`date`),
  KEY `index_stock_code` (`stock_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4568352 DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_config` */

DROP TABLE IF EXISTS `t_sys_config`;

CREATE TABLE `t_sys_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ip` varchar(15) DEFAULT NULL COMMENT '当前配置对应的IP',
  `port` int(11) DEFAULT NULL COMMENT '当前配置对应的端口',
  `name` varchar(500) DEFAULT NULL COMMENT '配置名',
  `value` varchar(1000) DEFAULT NULL COMMENT '值',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `enable` bit(1) DEFAULT NULL COMMENT '是否生效',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
