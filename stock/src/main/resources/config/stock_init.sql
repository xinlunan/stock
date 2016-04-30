/*
SQLyog Enterprise v12.08 (64 bit)
MySQL - 5.6.24 : Database - xu_stock
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`xu_stock` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `xu_stock`;

/*Table structure for table `t_stock` */

DROP TABLE IF EXISTS `t_stock`;

CREATE TABLE `t_stock` (
  `stock_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '股票Id',
  `exchange` char(2) DEFAULT NULL COMMENT '交易所',
  `stock_code` char(6) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(10) DEFAULT NULL COMMENT '股票名称',
  `asset` decimal(10,2) DEFAULT NULL COMMENT '总资产',
  `last_date` date DEFAULT NULL COMMENT '最后获取指数日期',
  `last_close` decimal(10,2) DEFAULT NULL COMMENT '最后收盘价',
  `exrights` decimal(15,10) DEFAULT '1.0000000000' COMMENT '除权系数',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`stock_id`),
  UNIQUE KEY `index_stock_code` (`stock_code`),
  KEY `index_stock_name` (`stock_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2872 DEFAULT CHARSET=utf8;

/*Table structure for table `t_stock_analyse_strategy` */

DROP TABLE IF EXISTS `t_stock_analyse_strategy`;

CREATE TABLE `t_stock_analyse_strategy` (
  `strategy_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '策略ID',
  `strategy_type` varchar(50) DEFAULT NULL COMMENT '策略类型',
  `version` int(11) DEFAULT NULL COMMENT '参数版本',
  `parameters` varchar(500) DEFAULT NULL COMMENT '策略参数',
  `enable` int(11) DEFAULT '1' COMMENT '是否启用',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`strategy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5018607 DEFAULT CHARSET=utf8;

/*Table structure for table `t_stock_trade_buy_trade` */

DROP TABLE IF EXISTS `t_stock_trade_buy_trade`;

CREATE TABLE `t_stock_trade_buy_trade` (
  `trade_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '股票交易Id',
  `stock_code` char(6) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(10) DEFAULT NULL COMMENT '股票名称',
  `buy_date` date DEFAULT NULL COMMENT '购买日期',
  `buy_trade_price` decimal(10,2) DEFAULT NULL COMMENT '购买价',
  `sell_date` date DEFAULT NULL COMMENT '卖出日',
  `sell_trade_price` decimal(10,2) DEFAULT NULL COMMENT '卖出价',
  `profit` decimal(10,2) DEFAULT NULL COMMENT '利润',
  `profit_rate` decimal(10,2) DEFAULT NULL COMMENT '利润率',
  `close_profit_rate` decimal(10,2) DEFAULT NULL COMMENT '收盘价收益比例',
  `buy_hour` int(11) DEFAULT NULL COMMENT '购买小时',
  `buy_minute` int(11) DEFAULT NULL COMMENT '购买分钟',
  `buy_high_price` decimal(10,2) DEFAULT NULL COMMENT '购买日最高价',
  `high_profit_rate` decimal(10,2) DEFAULT NULL COMMENT '最高价收益比例',
  `buy_close_price` decimal(10,2) DEFAULT NULL COMMENT '购买日收盘价',
  `sell_hour` int(11) DEFAULT NULL COMMENT '卖出小时',
  `sell_minute` int(11) DEFAULT NULL COMMENT '卖出分钟',
  `sell_high_price` decimal(10,2) DEFAULT NULL COMMENT '卖出日最高价',
  `sell_close_price` decimal(10,2) DEFAULT NULL COMMENT '卖出日收盘价',
  `trade_type` varchar(10) DEFAULT NULL COMMENT '交易类型：BUY,SELL',
  `trade_nature` varchar(10) DEFAULT NULL COMMENT '交易性质：REAL,VIRTUAL',
  `strategy` varchar(50) DEFAULT NULL COMMENT '策略',
  `version` int(11) DEFAULT NULL COMMENT '策略版本',
  `parameters` varchar(500) DEFAULT NULL COMMENT '策略参数',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`trade_id`),
  KEY `index_stock_code` (`stock_code`,`buy_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_stock_daily` */

DROP TABLE IF EXISTS `t_stock_daily`;

CREATE TABLE `t_stock_daily` (
  `daily_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '股票每日指数Id',
  `stock_code` char(6) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(10) DEFAULT NULL COMMENT '股票名称',
  `date` date DEFAULT NULL COMMENT '日期',
  `last_close` decimal(10,2) DEFAULT NULL COMMENT '昨天收盘价',
  `open` decimal(10,2) DEFAULT NULL COMMENT '开盘价',
  `close` decimal(10,2) DEFAULT NULL COMMENT '收盘价',
  `close_gap` decimal(10,2) DEFAULT NULL COMMENT '涨跌额',
  `close_gap_rate` decimal(10,2) DEFAULT NULL COMMENT '涨跌幅',
  `high` decimal(10,2) DEFAULT NULL COMMENT '最高价',
  `low` decimal(10,2) DEFAULT NULL COMMENT '最低价',
  `high_gap` decimal(10,2) DEFAULT NULL COMMENT '最高价与昨收差价 high-last_close',
  `high_gap_rate` decimal(10,2) DEFAULT NULL COMMENT '最高价与昨收差价比例 (high-last_close)/last_close',
  `low_gap` decimal(10,2) DEFAULT NULL COMMENT '最低价与昨收差价 low-last_close',
  `low_gap_rate` decimal(10,2) DEFAULT NULL COMMENT '最低价与昨收差价比例 (low-last_close)/last_close',
  `exrights` decimal(15,10) DEFAULT NULL COMMENT '除权比例',
  `this_exrights` decimal(10,4) DEFAULT NULL COMMENT '本次除权比例',
  `amount` bigint(20) DEFAULT NULL COMMENT '成交额',
  `volume` bigint(20) DEFAULT NULL COMMENT '成交量',
  `asset` bigint(20) DEFAULT NULL COMMENT '总资产',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`daily_id`),
  UNIQUE KEY `index_stock_code` (`stock_code`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=5035599 DEFAULT CHARSET=utf8;

/*Table structure for table `t_stock_history_highest` */

DROP TABLE IF EXISTS `t_stock_history_highest`;

CREATE TABLE `t_stock_history_highest` (
  `history_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '股票每日指数Id',
  `stock_code` char(6) DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(10) DEFAULT NULL COMMENT '股票名称',
  `date` date DEFAULT NULL COMMENT '日期',
  `hour` decimal(10,2) DEFAULT NULL COMMENT '最高时间-小时',
  `minute` decimal(10,2) DEFAULT NULL COMMENT '最高时间-分钟',
  `open` decimal(10,2) DEFAULT NULL COMMENT '开盘价',
  `high` decimal(10,2) DEFAULT NULL COMMENT '最高价',
  `low` decimal(10,2) DEFAULT NULL COMMENT '最低价',
  `close` decimal(10,2) DEFAULT NULL COMMENT '收盘价',
  `exrights` decimal(15,10) DEFAULT NULL COMMENT '除权系数',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`history_id`),
  KEY `index_stock_code` (`stock_code`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_stock_minute` */

DROP TABLE IF EXISTS `t_stock_minute`;

CREATE TABLE `t_stock_minute` (
  `minute_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '股票分时指数Id',
  `stock_code` char(6) DEFAULT NULL COMMENT '股票代码',
  `date` date DEFAULT NULL COMMENT '日期',
  `hour` int(11) DEFAULT NULL COMMENT '小时',
  `minute` int(11) DEFAULT NULL COMMENT '分钟',
  `price` decimal(10,2) DEFAULT NULL COMMENT '当前价',
  `exrights` decimal(15,10) DEFAULT NULL COMMENT '除权系数',
  `volume` decimal(10,2) DEFAULT NULL COMMENT '成交量',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '成交额',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`minute_id`),
  KEY `index_stock_code` (`stock_code`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_sys_config` */

DROP TABLE IF EXISTS `t_sys_config`;

CREATE TABLE `t_sys_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ip` varchar(15) DEFAULT NULL COMMENT '当前配置对应的IP',
  `port` int(11) DEFAULT NULL COMMENT '当前配置对应的端口',
  `name` varchar(500) DEFAULT NULL COMMENT '配置名',
  `value` varchar(1000) DEFAULT NULL COMMENT '值',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `enable` int(1) DEFAULT NULL COMMENT '是否生效',
  `created` datetime DEFAULT NULL COMMENT '创建日期',
  `updated` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
