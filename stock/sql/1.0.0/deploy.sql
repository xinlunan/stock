/*
SQLyog Enterprise v12.08 (64 bit)
MySQL - 5.6.24 : Database - deploy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`deploy` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `deploy`;

/*Table structure for table `app` */

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `env` varchar(50) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `group` varchar(50) DEFAULT NULL,
  `system` varchar(50) DEFAULT NULL,
  `appid` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `middleware_version` varchar(50) DEFAULT NULL,
  `middleware_dir` varchar(500) DEFAULT NULL,
  `middleware_log_dir` varchar(500) DEFAULT NULL,
  `app_log_dir` varchar(500) DEFAULT NULL,
  `backup_dir` varchar(500) DEFAULT NULL,
  `config_dir` varchar(500) DEFAULT NULL,
  `verify_config_dir` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ip` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `app` */

insert  into `app`(`id`,`env`,`ip`,`group`,`system`,`appid`,`type`,`middleware_version`,`middleware_dir`,`middleware_log_dir`,`app_log_dir`,`backup_dir`,`config_dir`,`verify_config_dir`,`url`) values (1,'idc_gz','10.32.0.188','A','cwm','CNSZX.CWM.CN.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/winit_tomcat/logs','/usr/local/winit_tomcat/logs','','/config/OPS/ops.gz/cwm/src/main/resources','/config/OPS/ops.gz.verify/cwm/src/main/resources','http://10.32.0.188:8081/cwm/\r'),(2,'idc_gz','10.32.0.208','B','cwm','CNSZX.CWM.CN.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps','/usr/local/winit_tomcat/logs','/usr/local/winit_tomcat/logs','','/config/OPS/ops.gz/cwm/src/main/resources','/config/OPS/ops.gz.verify/cwm/src/main/resources','http://10.32.0.208:8081/cwm/\r'),(3,'idc_gz','10.32.0.209','A','cwm','CNSHA.CWM.CN.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps','/usr/local/winit_tomcat/logs','/usr/local/winit_tomcat/logs','','/config/OPS/ops.gz/cwm/src/main/resources','/config/OPS/ops.gz.verify/cwm/src/main/resources','http://10.32.0.209:8081/cwm/\r'),(4,'idc_gz','10.32.0.189','B','cwm','CNSHA.CWM.CN.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps','/usr/local/winit_tomcat/logs','/usr/local/winit_tomcat/logs','','/config/OPS/ops.gz/cwm/src/main/resources','/config/OPS/ops.gz.verify/cwm/src/main/resources','http://10.32.0.189:8081/cwm/\r'),(5,'idc_gz','10.32.0.108','A','ims','IMS1.IMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_ims_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/ims/src/main/resources','/config/OPS/ops.gz.verify/ims/src/main/resources','http://10.32.0.108:8082/ims/\r'),(6,'idc_gz','10.32.0.140','B','ims','IMS1.IMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/ims/src/main/resources','/config/OPS/ops.gz.verify/ims/src/main/resources','http://10.32.0.140:8081/ims/\r'),(7,'idc_gz','10.32.0.112','A','oms','OMS1.OMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/oms/src/main/resources','/config/OPS/ops.gz.verify/oms/src/main/resources','http://10.32.0.112:8081/oms/\r'),(8,'idc_gz','10.32.0.122','B','oms','OMS1.OMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/oms/src/main/resources','/config/OPS/ops.gz.verify/oms/src/main/resources','http://10.32.0.122:8081/oms/\r'),(9,'idc_gz','10.32.0.142','B','oms','OMS1.OMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/oms/src/main/resources','/config/OPS/ops.gz.verify/oms/src/main/resources','http://10.32.0.142:8081/oms/\r'),(10,'idc_gz','10.32.0.108','A','openapi','OPENAPI1.OPENAPI.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/openapi/src/main/resources','/config/OPS/ops.gz.verify/openapi/src/main/resources','http://10.32.0.108:8081/openapi/\r'),(11,'idc_gz','10.32.0.118','B','openapi','OPENAPI1.OPENAPI.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/openapi/src/main/resources','/config/OPS/ops.gz.verify/openapi/src/main/resources','http://10.32.0.118:8081/openapi/\r'),(12,'idc_gz','10.32.0.144','B','openapi','OPENAPI1.OPENAPI.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/openapi/src/main/resources','/config/OPS/ops.gz.verify/openapi/src/main/resources','http://10.32.0.144:8081/openapi/\r'),(13,'idc_gz','10.32.0.110','A','pms','PMS1.PMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/pms/src/main/resources','/config/OPS/ops.gz.verify/pms/src/main/resources','http://10.32.0.110:8081/pms/\r'),(14,'idc_gz','10.32.0.120','B','pms','PMS1.PMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/pms/src/main/resources','/config/OPS/ops.gz.verify/pms/src/main/resources','http://10.32.0.120:8081/pms/\r'),(15,'idc_gz','10.32.0.141','B','pms','PMS1.PMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/pms/src/main/resources','/config/OPS/ops.gz.verify/pms/src/main/resources','http://10.32.0.141:8081/pms/\r'),(16,'idc_gz','10.32.0.32','A','seller-portal','OPC1.OPC.CN.CNR','php','Apache/2.2.27','/usr/local/winit_newman/','/usr/local/apachelog/','/usr/local/apachelog/','','/config/OPS/ops.gz/seller-portal/src/main/resources','/config/OPS/ops.gz.verify/seller-portal/src/main/resources','http://10.32.0.32/Manage/\r'),(17,'idc_gz','10.32.0.42','A','seller-portal','OPC1.OPC.CN.CNR','php','Apache/2.2.27','/usr/local/winit_newman/','/usr/local/apachelog/','/usr/local/apachelog/','','/config/OPS/ops.gz/seller-portal/src/main/resources','/config/OPS/ops.gz.verify/seller-portal/src/main/resources','http://10.32.0.42/Manage/\r'),(18,'idc_gz','10.32.0.25','B','seller-portal','OPC1.OPC.CN.CNR','php','Apache/2.2.27','/usr/local/winit_newman/','/usr/local/apachelog/','/usr/local/apachelog/','','/config/OPS/ops.gz/seller-portal/src/main/resources','/config/OPS/ops.gz.verify/seller-portal/src/main/resources','http://10.32.0.25/Manage/\r'),(19,'idc_gz','10.32.0.35','B','seller-portal','OPC1.OPC.CN.CNR','php','Apache/2.2.27','/usr/local/winit_newman/','/usr/local/apachelog/','/usr/local/apachelog/','','/config/OPS/ops.gz/seller-portal/src/main/resources','/config/OPS/ops.gz.verify/seller-portal/src/main/resources','http://10.32.0.35/Manage/\r'),(20,'idc_gz','10.32.0.185','A','service-gate','SG1.SG.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/winit_tomcat/logs','/usr/local/winit_tomcat/logs','','/config/OPS/ops.gz/service-gate/src/main/resources','/config/OPS/ops.gz.verify/service-gate/src/main/resources','http://10.32.0.185:8081/service-gate/\r'),(21,'idc_gz','10.32.0.205','B','service-gate','SG1.SG.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps','/usr/local/winit_tomcat/logs','/usr/local/winit_tomcat/logs','','/config/OPS/ops.gz/service-gate/src/main/resources','/config/OPS/ops.gz.verify/service-gate/src/main/resources','http://10.32.0.205:8081/service-gate/\r'),(22,'idc_gz','10.32.0.135','A','tms','TMS1.TMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/tms/src/main/resources','/config/OPS/ops.gz.verify/tms/src/main/resources','http://10.32.0.135:8081/tms/\r'),(23,'idc_gz','10.32.0.183','A','tom','TOM1.TOM.ALL.CNR','php','Apache/2.2.27','/usr/local/winit_tom/Apps','/usr/local/apachelog','/usr/local/apachelog','','/config/OPS/ops.gz/tom/src/main/resources','/config/OPS/ops.gz.verify/tom/src/main/resources','http://10.32.0.183\r'),(24,'idc_gz','10.32.0.184','B','tom','TOM1.TOM.ALL.CNR','php','Apache/2.2.27','/usr/local/winit_tom/Apps','/usr/local/apachelog','/usr/local/apachelog','','/config/OPS/ops.gz/tom/src/main/resources','/config/OPS/ops.gz.verify/tom/src/main/resources','http://10.32.0.184\r'),(25,'idc_gz','10.32.0.112','A','ums','UMS1.UMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_ums_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/ums/src/main/resources','/config/OPS/ops.gz.verify/ums/src/main/resources','http://10.32.0.112:8082/ums/\r'),(26,'idc_gz','10.32.0.139','B','ums','UMS1.UMS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_ums_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/ums/src/main/resources','/config/OPS/ops.gz.verify/ums/src/main/resources','http://10.32.0.139:8082/ums/\r'),(27,'idc_gz','10.32.0.128','A','ups','UPS1.UPS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/ups/src/main/resources','/config/OPS/ops.gz.verify/ups/src/main/resources','http://10.32.0.128:8081/ups/\r'),(28,'idc_gz','10.32.0.124','B','ups','UPS1.UPS.ALL.CNR','java','Apache Tomcat/7.0.54','/usr/local/winit_tomcat/webapps/','/usr/local/applogs/','/usr/local/applogs/','','/config/OPS/ops.gz/ups/src/main/resources','/config/OPS/ops.gz.verify/ups/src/main/resources','http://10.32.0.124:8081/ups/');

/*Table structure for table `deploy_guide` */

DROP TABLE IF EXISTS `deploy_guide`;

CREATE TABLE `deploy_guide` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` varchar(500) DEFAULT NULL,
  `target` varchar(500) DEFAULT NULL,
  `args` varchar(500) DEFAULT NULL,
  `result` varchar(5000) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4998 DEFAULT CHARSET=utf8;

/*Data for the table `deploy_guide` */

insert  into `deploy_guide`(`id`,`action`,`target`,`args`,`result`,`created`) values (4887,'|-发布A组到验证环境','---------------','-------------------------------------','','2016-04-22 09:13:02'),(4888,'  |-去除haproxy代理','haproxy服务器','seller-portal,service-gate','','2016-04-22 09:13:02'),(4889,'    |-修改haproxy配置','XXXX文件','seller-portal,service-gate','','2016-04-22 09:13:02'),(4890,'    |-重启haproxy','restar.sh','','','2016-04-22 09:13:02'),(4891,'    |-检验流量是否正常','','','','2016-04-22 09:13:02'),(4892,'  |-停止应用服务器','','','','2016-04-22 09:13:02'),(4893,'    |-停止应用','10.32.0.32 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4894,'    |-停止应用','10.32.0.42 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4895,'    |-停止应用','10.32.0.185 : SG1.SG.ALL.CNR','','','2016-04-22 09:13:02'),(4896,'    |-停止应用','10.32.0.112 : OMS1.OMS.ALL.CNR','','','2016-04-22 09:13:02'),(4897,'    |-停止应用','10.32.0.188 : CNSZX.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4898,'    |-停止应用','10.32.0.209 : CNSHA.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4899,'    |-停止应用','10.32.0.108 : IMS1.IMS.ALL.CNR','','','2016-04-22 09:13:02'),(4900,'    |-停止应用','10.32.0.110 : PMS1.PMS.ALL.CNR','','','2016-04-22 09:13:02'),(4901,'    |-停止应用','10.32.0.128 : UPS1.UPS.ALL.CNR','','','2016-04-22 09:13:02'),(4902,'  |-发布应用服务','','','','2016-04-22 09:13:02'),(4903,'    |-发布应用','10.32.0.32 : OPC1.OPC.CN.CNR','/config/OPS/ops.gz.verify/seller-portal/src/main/resources','','2016-04-22 09:13:02'),(4904,'    |-发布应用','10.32.0.42 : OPC1.OPC.CN.CNR','/config/OPS/ops.gz.verify/seller-portal/src/main/resources','','2016-04-22 09:13:02'),(4905,'    |-发布应用','10.32.0.185 : SG1.SG.ALL.CNR','/config/OPS/ops.gz.verify/service-gate/src/main/resources','','2016-04-22 09:13:02'),(4906,'    |-发布应用','10.32.0.112 : OMS1.OMS.ALL.CNR','/config/OPS/ops.gz.verify/oms/src/main/resources','','2016-04-22 09:13:02'),(4907,'    |-发布应用','10.32.0.188 : CNSZX.CWM.CN.CNR','/config/OPS/ops.gz.verify/cwm/src/main/resources','','2016-04-22 09:13:02'),(4908,'    |-发布应用','10.32.0.209 : CNSHA.CWM.CN.CNR','/config/OPS/ops.gz.verify/cwm/src/main/resources','','2016-04-22 09:13:02'),(4909,'    |-发布应用','10.32.0.108 : IMS1.IMS.ALL.CNR','/config/OPS/ops.gz.verify/ims/src/main/resources','','2016-04-22 09:13:02'),(4910,'    |-发布应用','10.32.0.110 : PMS1.PMS.ALL.CNR','/config/OPS/ops.gz.verify/pms/src/main/resources','','2016-04-22 09:13:02'),(4911,'    |-发布应用','10.32.0.128 : UPS1.UPS.ALL.CNR','/config/OPS/ops.gz.verify/ups/src/main/resources','','2016-04-22 09:13:02'),(4912,'  |-启动应用服务器','','','','2016-04-22 09:13:02'),(4913,'    |-启动应用','10.32.0.128 : UPS1.UPS.ALL.CNR','','','2016-04-22 09:13:02'),(4914,'    |-启动应用','10.32.0.110 : PMS1.PMS.ALL.CNR','','','2016-04-22 09:13:02'),(4915,'    |-启动应用','10.32.0.108 : IMS1.IMS.ALL.CNR','','','2016-04-22 09:13:02'),(4916,'    |-启动应用','10.32.0.188 : CNSZX.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4917,'    |-启动应用','10.32.0.209 : CNSHA.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4918,'    |-启动应用','10.32.0.112 : OMS1.OMS.ALL.CNR','','','2016-04-22 09:13:02'),(4919,'    |-启动应用','10.32.0.185 : SG1.SG.ALL.CNR','','','2016-04-22 09:13:02'),(4920,'    |-启动应用','10.32.0.32 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4921,'    |-启动应用','10.32.0.42 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4922,'|-发布A组到生产环境','---------------','-------------------------------------','','2016-04-22 09:13:02'),(4923,'  |-去除haproxy代理','haproxy服务器','seller-portal,service-gate','','2016-04-22 09:13:02'),(4924,'    |-修改haproxy配置','XXXX文件','seller-portal,service-gate','','2016-04-22 09:13:02'),(4925,'    |-重启haproxy','restar.sh','','','2016-04-22 09:13:02'),(4926,'    |-检验流量是否正常','','','','2016-04-22 09:13:02'),(4927,'  |-停止应用服务器','','','','2016-04-22 09:13:02'),(4928,'    |-停止应用','10.32.0.32 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4929,'    |-停止应用','10.32.0.42 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4930,'    |-停止应用','10.32.0.185 : SG1.SG.ALL.CNR','','','2016-04-22 09:13:02'),(4931,'    |-停止应用','10.32.0.112 : OMS1.OMS.ALL.CNR','','','2016-04-22 09:13:02'),(4932,'    |-停止应用','10.32.0.188 : CNSZX.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4933,'    |-停止应用','10.32.0.209 : CNSHA.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4934,'    |-停止应用','10.32.0.108 : IMS1.IMS.ALL.CNR','','','2016-04-22 09:13:02'),(4935,'    |-停止应用','10.32.0.110 : PMS1.PMS.ALL.CNR','','','2016-04-22 09:13:02'),(4936,'    |-停止应用','10.32.0.128 : UPS1.UPS.ALL.CNR','','','2016-04-22 09:13:02'),(4937,'  |-发布应用服务','','','','2016-04-22 09:13:02'),(4938,'    |-发布应用','10.32.0.32 : OPC1.OPC.CN.CNR','/config/OPS/ops.gz/seller-portal/src/main/resources','','2016-04-22 09:13:02'),(4939,'    |-发布应用','10.32.0.42 : OPC1.OPC.CN.CNR','/config/OPS/ops.gz/seller-portal/src/main/resources','','2016-04-22 09:13:02'),(4940,'    |-发布应用','10.32.0.185 : SG1.SG.ALL.CNR','/config/OPS/ops.gz/service-gate/src/main/resources','','2016-04-22 09:13:02'),(4941,'    |-发布应用','10.32.0.112 : OMS1.OMS.ALL.CNR','/config/OPS/ops.gz/oms/src/main/resources','','2016-04-22 09:13:02'),(4942,'    |-发布应用','10.32.0.188 : CNSZX.CWM.CN.CNR','/config/OPS/ops.gz/cwm/src/main/resources','','2016-04-22 09:13:02'),(4943,'    |-发布应用','10.32.0.209 : CNSHA.CWM.CN.CNR','/config/OPS/ops.gz/cwm/src/main/resources','','2016-04-22 09:13:02'),(4944,'    |-发布应用','10.32.0.108 : IMS1.IMS.ALL.CNR','/config/OPS/ops.gz/ims/src/main/resources','','2016-04-22 09:13:02'),(4945,'    |-发布应用','10.32.0.110 : PMS1.PMS.ALL.CNR','/config/OPS/ops.gz/pms/src/main/resources','','2016-04-22 09:13:02'),(4946,'    |-发布应用','10.32.0.128 : UPS1.UPS.ALL.CNR','/config/OPS/ops.gz/ups/src/main/resources','','2016-04-22 09:13:02'),(4947,'  |-启动应用服务器','','','','2016-04-22 09:13:02'),(4948,'    |-启动应用','10.32.0.128 : UPS1.UPS.ALL.CNR','','','2016-04-22 09:13:02'),(4949,'    |-启动应用','10.32.0.110 : PMS1.PMS.ALL.CNR','','','2016-04-22 09:13:02'),(4950,'    |-启动应用','10.32.0.108 : IMS1.IMS.ALL.CNR','','','2016-04-22 09:13:02'),(4951,'    |-启动应用','10.32.0.188 : CNSZX.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4952,'    |-启动应用','10.32.0.209 : CNSHA.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4953,'    |-启动应用','10.32.0.112 : OMS1.OMS.ALL.CNR','','','2016-04-22 09:13:02'),(4954,'    |-启动应用','10.32.0.185 : SG1.SG.ALL.CNR','','','2016-04-22 09:13:02'),(4955,'    |-启动应用','10.32.0.32 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4956,'    |-启动应用','10.32.0.42 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4957,'|-发布B组到生产环境','---------------','-------------------------------------','','2016-04-22 09:13:02'),(4958,'  |-去除haproxy代理','haproxy服务器','seller-portal,service-gate','','2016-04-22 09:13:02'),(4959,'    |-修改haproxy配置','XXXX文件','seller-portal,service-gate','','2016-04-22 09:13:02'),(4960,'    |-重启haproxy','restar.sh','','','2016-04-22 09:13:02'),(4961,'    |-检验流量是否正常','','','','2016-04-22 09:13:02'),(4962,'  |-停止应用服务器','','','','2016-04-22 09:13:02'),(4963,'    |-停止应用','10.32.0.25 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4964,'    |-停止应用','10.32.0.35 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4965,'    |-停止应用','10.32.0.205 : SG1.SG.ALL.CNR','','','2016-04-22 09:13:02'),(4966,'    |-停止应用','10.32.0.122 : OMS1.OMS.ALL.CNR','','','2016-04-22 09:13:02'),(4967,'    |-停止应用','10.32.0.142 : OMS1.OMS.ALL.CNR','','','2016-04-22 09:13:02'),(4968,'    |-停止应用','10.32.0.208 : CNSZX.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4969,'    |-停止应用','10.32.0.189 : CNSHA.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4970,'    |-停止应用','10.32.0.140 : IMS1.IMS.ALL.CNR','','','2016-04-22 09:13:02'),(4971,'    |-停止应用','10.32.0.120 : PMS1.PMS.ALL.CNR','','','2016-04-22 09:13:02'),(4972,'    |-停止应用','10.32.0.141 : PMS1.PMS.ALL.CNR','','','2016-04-22 09:13:02'),(4973,'    |-停止应用','10.32.0.124 : UPS1.UPS.ALL.CNR','','','2016-04-22 09:13:02'),(4974,'  |-发布应用服务','','','','2016-04-22 09:13:02'),(4975,'    |-发布应用','10.32.0.25 : OPC1.OPC.CN.CNR','/config/OPS/ops.gz/seller-portal/src/main/resources','','2016-04-22 09:13:02'),(4976,'    |-发布应用','10.32.0.35 : OPC1.OPC.CN.CNR','/config/OPS/ops.gz/seller-portal/src/main/resources','','2016-04-22 09:13:02'),(4977,'    |-发布应用','10.32.0.205 : SG1.SG.ALL.CNR','/config/OPS/ops.gz/service-gate/src/main/resources','','2016-04-22 09:13:02'),(4978,'    |-发布应用','10.32.0.122 : OMS1.OMS.ALL.CNR','/config/OPS/ops.gz/oms/src/main/resources','','2016-04-22 09:13:02'),(4979,'    |-发布应用','10.32.0.142 : OMS1.OMS.ALL.CNR','/config/OPS/ops.gz/oms/src/main/resources','','2016-04-22 09:13:02'),(4980,'    |-发布应用','10.32.0.208 : CNSZX.CWM.CN.CNR','/config/OPS/ops.gz/cwm/src/main/resources','','2016-04-22 09:13:02'),(4981,'    |-发布应用','10.32.0.189 : CNSHA.CWM.CN.CNR','/config/OPS/ops.gz/cwm/src/main/resources','','2016-04-22 09:13:02'),(4982,'    |-发布应用','10.32.0.140 : IMS1.IMS.ALL.CNR','/config/OPS/ops.gz/ims/src/main/resources','','2016-04-22 09:13:02'),(4983,'    |-发布应用','10.32.0.120 : PMS1.PMS.ALL.CNR','/config/OPS/ops.gz/pms/src/main/resources','','2016-04-22 09:13:02'),(4984,'    |-发布应用','10.32.0.141 : PMS1.PMS.ALL.CNR','/config/OPS/ops.gz/pms/src/main/resources','','2016-04-22 09:13:02'),(4985,'    |-发布应用','10.32.0.124 : UPS1.UPS.ALL.CNR','/config/OPS/ops.gz/ups/src/main/resources','','2016-04-22 09:13:02'),(4986,'  |-启动应用服务器','','','','2016-04-22 09:13:02'),(4987,'    |-启动应用','10.32.0.124 : UPS1.UPS.ALL.CNR','','','2016-04-22 09:13:02'),(4988,'    |-启动应用','10.32.0.120 : PMS1.PMS.ALL.CNR','','','2016-04-22 09:13:02'),(4989,'    |-启动应用','10.32.0.141 : PMS1.PMS.ALL.CNR','','','2016-04-22 09:13:02'),(4990,'    |-启动应用','10.32.0.140 : IMS1.IMS.ALL.CNR','','','2016-04-22 09:13:02'),(4991,'    |-启动应用','10.32.0.208 : CNSZX.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4992,'    |-启动应用','10.32.0.189 : CNSHA.CWM.CN.CNR','','','2016-04-22 09:13:02'),(4993,'    |-启动应用','10.32.0.122 : OMS1.OMS.ALL.CNR','','','2016-04-22 09:13:02'),(4994,'    |-启动应用','10.32.0.142 : OMS1.OMS.ALL.CNR','','','2016-04-22 09:13:02'),(4995,'    |-启动应用','10.32.0.205 : SG1.SG.ALL.CNR','','','2016-04-22 09:13:02'),(4996,'    |-启动应用','10.32.0.25 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02'),(4997,'    |-启动应用','10.32.0.35 : OPC1.OPC.CN.CNR','','','2016-04-22 09:13:02');

/*Table structure for table `env` */

DROP TABLE IF EXISTS `env`;

CREATE TABLE `env` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `env` */

insert  into `env`(`id`,`name`) values (3,'idc_au'),(2,'idc_eu'),(1,'idc_gz'),(4,'idc_us'),(5,'staging');

/*Table structure for table `machine` */

DROP TABLE IF EXISTS `machine`;

CREATE TABLE `machine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `env` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `os` varchar(50) DEFAULT NULL,
  `os_version` varchar(50) DEFAULT NULL,
  `target` varchar(50) DEFAULT NULL,
  `fun_version` varchar(50) DEFAULT NULL,
  `ssh_port` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ip` (`ip`),
  KEY `env` (`env`),
  CONSTRAINT `env` FOREIGN KEY (`env`) REFERENCES `env` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `machine` */

insert  into `machine`(`id`,`env`,`name`,`ip`,`os`,`os_version`,`target`,`fun_version`,`ssh_port`) values (1,'idc_gz',NULL,'10.32.0.32',NULL,NULL,'app','PHP 5.4.30',NULL),(2,'idc_gz',NULL,'10.32.0.42',NULL,NULL,'app','PHP 5.4.30',NULL),(3,'idc_gz',NULL,'10.32.0.25',NULL,NULL,'app','PHP 5.4.30',NULL),(4,'idc_gz',NULL,'10.32.0.35',NULL,NULL,'app','PHP 5.4.30',NULL),(5,'idc_gz',NULL,'10.32.0.108',NULL,NULL,'app','jdk1.7.0_80',NULL),(6,'idc_gz',NULL,'10.32.0.118',NULL,NULL,'app','jdk1.7.0_80',NULL),(7,'idc_gz',NULL,'10.32.0.144',NULL,NULL,'app','jdk1.7.0_80',NULL),(8,'idc_gz',NULL,'10.32.0.140',NULL,NULL,'app','jdk1.7.0_80',NULL),(9,'idc_gz',NULL,'10.32.0.110',NULL,NULL,'app','jdk1.7.0_80',NULL),(10,'idc_gz',NULL,'10.32.0.120',NULL,NULL,'app','jdk1.7.0_80',NULL),(11,'idc_gz',NULL,'10.32.0.141',NULL,NULL,'app','jdk1.7.0_80',NULL),(12,'idc_gz',NULL,'10.32.0.112',NULL,NULL,'app','jdk1.7.0_80',NULL),(13,'idc_gz',NULL,'10.32.0.122',NULL,NULL,'app','jdk1.7.0_80',NULL),(14,'idc_gz',NULL,'10.32.0.142',NULL,NULL,'app','jdk1.7.0_80',NULL),(15,'idc_gz',NULL,'10.32.0.139',NULL,NULL,'app','jdk1.7.0_80',NULL),(16,'idc_gz',NULL,'10.32.0.128',NULL,NULL,'app','jdk1.7.0_80',NULL),(17,'idc_gz',NULL,'10.32.0.124',NULL,NULL,'app','jdk1.7.0_80',NULL),(18,'idc_gz',NULL,'10.32.0.135',NULL,NULL,'app','jdk1.7.0_80',NULL),(19,'idc_gz',NULL,'10.32.0.183',NULL,NULL,'app','PHP 5.4.30',NULL),(20,'idc_gz',NULL,'10.32.0.184',NULL,NULL,'app','PHP 5.4.30',NULL),(21,'idc_gz',NULL,'10.32.0.185',NULL,NULL,'app','jdk1.7.0_80',NULL),(22,'idc_gz',NULL,'10.32.0.205',NULL,NULL,'app','jdk1.7.0_80',NULL),(23,'idc_gz',NULL,'10.32.0.188',NULL,NULL,'app','jdk1.7.0_80',NULL),(24,'idc_gz',NULL,'10.32.0.208',NULL,NULL,'app','jdk1.7.0_80',NULL),(25,'idc_gz',NULL,'10.32.0.209',NULL,NULL,'app','jdk1.7.0_80',NULL),(26,'idc_gz',NULL,'10.32.0.189',NULL,NULL,'app','jdk1.7.0_80',NULL);

/*Table structure for table `system` */

DROP TABLE IF EXISTS `system`;

CREATE TABLE `system` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `load_type` varchar(50) DEFAULT NULL,
  `level` bigint(20) DEFAULT NULL,
  `full_depend` varchar(20000) DEFAULT NULL,
  `is_loop_depend` bit(1) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `system` */

insert  into `system`(`id`,`name`,`type`,`load_type`,`level`,`full_depend`,`is_loop_depend`,`updated`) values (1,'seller-portal','php','haproxy',5,'openapi,cwm,oms,pms,ims,ups',NULL,'2016-04-22 09:13:02'),(2,'tom','php','haproxy',5,'service-gate,cwm,oms,pms,ims,ups',NULL,'2016-04-22 09:13:02'),(3,'openapi','java','haproxy',4,'cwm,oms,pms,ims,ups',NULL,'2016-04-22 09:13:02'),(4,'service-gate','java','haproxy',4,'cwm,oms,pms,ims,ups',NULL,'2016-04-22 09:13:02'),(5,'oms','java','zookeeper',3,'pms,ims,ups',NULL,'2016-04-22 09:13:02'),(6,'cwm','java','zookeeper',2,'ups',NULL,'2016-04-22 09:13:02'),(7,'pms','java','zookeeper',2,'ups',NULL,'2016-04-22 09:13:02'),(8,'ups','java','zookeeper',1,'',NULL,'2016-04-22 09:13:02'),(9,'ims','java','zookeeper',1,'',NULL,'2016-04-22 09:13:02'),(10,'tms','java','zookeeper',1,'',NULL,'2016-04-22 09:13:02');

/*Table structure for table `system_depend` */

DROP TABLE IF EXISTS `system_depend`;

CREATE TABLE `system_depend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `system` varchar(50) DEFAULT NULL,
  `depend_system` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `system_depend` */

insert  into `system_depend`(`id`,`system`,`depend_system`) values (1,'seller-portal','openapi'),(2,'openapi','oms'),(3,'openapi','cwm'),(4,'openapi','pms'),(5,'openapi','ups'),(6,'tom','service-gate'),(7,'service-gate','oms'),(8,'service-gate','cwm'),(9,'service-gate','pms'),(10,'service-gate','ups'),(11,'oms','pms'),(12,'oms','ups'),(13,'pms','ups'),(14,'cwm','ups'),(15,'oms','ims'),(16,'service-gate\r\n','ims'),(17,'openapi','ims'),(18,'openapi','tms'),(19,'service-gate','tms');

/* Function  structure for function  `get_systems_sort` */

/*!50003 DROP FUNCTION IF EXISTS `get_systems_sort` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `get_systems_sort`(systems VARCHAR (4000)) RETURNS varchar(4000) CHARSET utf8
BEGIN
  DECLARE system1 VARCHAR (50) ;
  DECLARE system2 VARCHAR (50) ;
  declare system_count int ;
  declare i int ;
  DECLARE j INT ;
  declare is_high_level int ;
  SELECT (LENGTH(systems) - LENGTH( REPLACE(systems,',','') ) +1) into system_count;
  CALL generate_system_full_depend () ;
  set i = 1;
  WHILE i <= system_count DO
    set j = i+1;
    WHILE j <= system_count DO
      SELECT splitString(systems,',',i) into system1;
      SELECT splitString(systems,',',j) INTO system2;
      select count(1) into is_high_level from system where name=system1 and full_depend like CONCAT('%',system2,'%');
      IF is_high_level>0 THEN -- 当前系统依赖不存在
        set systems = REPLACE(systems,system1,concat(system2,'##'));
        SET systems = REPLACE(systems,system2,system1);
        SET systems = REPLACE(systems,CONCAT(system1,'##'),system2);
      END IF;
      SET j = j +1; 
    END WHILE; 
    SET i = i +1; 
  END WHILE; 
  RETURN systems ;
END */$$
DELIMITER ;

/* Function  structure for function  `splitString` */

/*!50003 DROP FUNCTION IF EXISTS `splitString` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `splitString`( f_string varchar(20000),f_delimiter varchar(5),f_order int) RETURNS varchar(255) CHARSET utf8
BEGIN  
    declare result varchar(255) default '';  
    set result = reverse(substring_index(reverse(substring_index(f_string,f_delimiter,f_order)),f_delimiter,1));  
    return result;  
END */$$
DELIMITER ;

/* Procedure structure for procedure `generate_deploy_group_guide` */

/*!50003 DROP PROCEDURE IF EXISTS  `generate_deploy_group_guide` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `generate_deploy_group_guide`(IN systems VARCHAR(4000),in app_group varchar(10),in purpose varchar(50))
BEGIN
  declare system_name varchar(5000);
  DECLARE haproxy_system_name VARCHAR(5000);
  DECLARE cfg_dir VARCHAR(5000);
  DECLARE machine_ip VARCHAR(50);
  DECLARE sys_app_id VARCHAR(50);
  DECLARE system_cont INT;
  DECLARE haproxy_cont INT;
  declare app_machine int;
  DECLARE i INT;
  DECLARE j INT;
  SET system_cont = (LENGTH(systems) - LENGTH( REPLACE(systems,',','') ) +1) ;
 
  if app_group='A' and purpose='verify' then
    insert into deploy_guide (action, target, args, result,created) values ( '|-发布A组到验证环境', '---------------', '-------------------------------------', '',sysdate()) ;
  end if;
  if app_group='A' AND purpose='produce' then
    INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '|-发布A组到生产环境', '---------------', '-------------------------------------', '',SYSDATE()) ;
  END IF;
  IF app_group='B' AND purpose='produce' THEN
    INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '|-发布B组到生产环境', '---------------', '-------------------------------------', '',SYSDATE()) ;
  end if;
  -- 去haproxy
  set system_name = '';
  set haproxy_system_name = '';
  set i = system_cont;
  while i >=1 do 
    set system_name = splitString( systems,',',i);
    select count(1) into haproxy_cont from system where load_type = 'haproxy' and name=system_name;
    if haproxy_cont > 0 then
       set haproxy_system_name = concat((CASE WHEN haproxy_system_name='' THEN '' ELSE CONCAT(haproxy_system_name,',') END ) , system_name);
    end if;
    set i = i-1;
  end while;
  INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '  |-去除haproxy代理', 'haproxy服务器', haproxy_system_name, '',SYSDATE()) ;
  INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '    |-修改haproxy配置', 'XXXX文件', haproxy_system_name, '',SYSDATE()) ;
  INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '    |-重启haproxy', 'restar.sh', '', '',SYSDATE()) ;
  INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '    |-检验流量是否正常', '', '', '',SYSDATE()) ;
  
  -- 停服务
  INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '  |-停止应用服务器', '', '', '',SYSDATE()) ;
  SET system_name = '';
  SET i = system_cont;
  WHILE i >=1 DO 
    SET system_name = splitString( systems,',',i);
    SELECT COUNT(1) INTO app_machine FROM app a WHERE a.group=app_group AND system = system_name; 
    SET j=0;
    WHILE j < app_machine DO
      SELECT verify_config_dir,ip,appid INTO cfg_dir,machine_ip,sys_app_id FROM app WHERE `group`=app_group AND system = system_name LIMIT j,1;
      INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '    |-停止应用',CONCAT(machine_ip,' : ',sys_app_id), '', '',SYSDATE()) ;
      SET j = j + 1;
    END WHILE;
    SET i = i-1;
  END WHILE;
  
  -- 发版本
  INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '  |-发布应用服务', '', '', '',SYSDATE()) ;
  SET system_name = '';
  SET i = system_cont;
  WHILE i >=1 DO 
    SET system_name = splitString( systems,',',i);
    IF app_group='A' AND purpose='verify' THEN
      select count(1) into app_machine FROM app a WHERE a.group=app_group AND system = system_name; 
      set j=0;
      WHILE j < app_machine DO
        SELECT verify_config_dir,ip,appid into cfg_dir,machine_ip,sys_app_id FROM app WHERE `group`=app_group AND system = system_name limit j,1;
        INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ('    |-发布应用',CONCAT(machine_ip,' : ',sys_app_id), cfg_dir, '',SYSDATE()) ;
        set j = j + 1;
      END WHILE;
    else
      SELECT COUNT(1) INTO app_machine FROM app a WHERE a.group=app_group AND system = system_name; 
      SET j=0;
      WHILE j < app_machine DO
        SELECT config_dir,ip,appid INTO cfg_dir,machine_ip,sys_app_id FROM app WHERE `group`=app_group AND system = system_name LIMIT j,1;
        INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ('    |-发布应用',CONCAT(machine_ip,' : ',sys_app_id), cfg_dir, '',SYSDATE()) ;
        SET j = j + 1;
      END WHILE;
    END IF;
    SET i = i-1;
  END WHILE;
   
  -- 启动服务
  INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '  |-启动应用服务器', '', '', '',SYSDATE()) ;
  SET system_name = '';
  SET i = 1;
  WHILE i <= system_cont DO 
    SET system_name = splitString( systems,',',i);
    SELECT COUNT(1) INTO app_machine FROM app a WHERE a.group=app_group AND system = system_name; 
    SET j=0;
    WHILE j < app_machine DO
      SELECT verify_config_dir,ip,appid INTO cfg_dir,machine_ip,sys_app_id FROM app WHERE `group`=app_group AND system = system_name LIMIT j,1;
      INSERT INTO deploy_guide (ACTION, target, args, result,created) VALUES ( '    |-启动应用',CONCAT(machine_ip,' : ',sys_app_id), '', '',SYSDATE()) ;
      SET j = j + 1;
    END WHILE;
    SET i = i + 1;
  END WHILE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `generate_deploy_guide` */

/*!50003 DROP PROCEDURE IF EXISTS  `generate_deploy_guide` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `generate_deploy_guide`(in systems varchar(4000))
BEGIN
  DECLARE systems_sort VARCHAR(4000);-- 排序后的工程
  -- 生成工程依赖关系
  select get_systems_sort(systems) into systems_sort;
  
  -- 删除已存在的指导
  delete from deploy_guide;
  -- 发布A组到验证环境
  call generate_deploy_group_guide(systems_sort,'A','verify');
  -- 发布A组到生产环境
  CALL generate_deploy_group_guide(systems_sort,'A','produce');
  -- 发布B组到生产环境
  CALL generate_deploy_group_guide(systems_sort,'B','produce');
  select ACTION, target, args, result,created from `deploy_guide` order by id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `generate_system_full_depend` */

/*!50003 DROP PROCEDURE IF EXISTS  `generate_system_full_depend` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `generate_system_full_depend`()
BEGIN
  DECLARE i INT;
  DECLARE depend_lenght INT;
  DECLARE results INT ;
  declare current_sys varchar(50);
  DECLARE depend_sys VARCHAR(50);
  DECLARE temp_sys VARCHAR(50);
  DECLARE full_depend_sys VARCHAR(20000);
  DECLARE parent_full_depend_sys VARCHAR(20000);
  DECLARE depend_csr1 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=1 ;
  DECLARE depend_csr2 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=2 ;
  DECLARE depend_csr3 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=3 ;
  DECLARE depend_csr4 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=4 ;
  DECLARE depend_csr5 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=5 ;
  DECLARE depend_csr6 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=6 ;
  DECLARE depend_csr7 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=7 ;
  DECLARE depend_csr8 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=8 ;
  DECLARE depend_csr9 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=9 ;
  DECLARE depend_csr10 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=10 ;
  DECLARE depend_csr11 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=11 ;
  DECLARE depend_csr12 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=12 ;
  DECLARE depend_csr13 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=13 ;
  DECLARE depend_csr14 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=14 ;
  DECLARE depend_csr15 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=15 ;
  DECLARE depend_csr16 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=16 ;
  DECLARE depend_csr17 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=17 ;
  DECLARE depend_csr18 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=18 ;
  DECLARE depend_csr19 CURSOR FOR SELECT d.system ,d.`depend_system` FROM system_depend d  JOIN system s2 ON d.`depend_system` = s2.`name` WHERE s2.`level`=19 ;
  DECLARE depend_csr CURSOR FOR SELECT s2.name ,s2.`full_depend` FROM system s2;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET results=1;
  SET results=0;
  SET parent_full_depend_sys = '';
  UPDATE system s SET LEVEL=0 ,s.`full_depend` = '' ,s.updated=SYSDATE() ;
  UPDATE system s SET LEVEL=1 ,s.updated=SYSDATE() WHERE s.name IN (SELECT d1.depend_system FROM `system_depend` d1 WHERE d1.depend_system NOT IN (SELECT d2.system FROM system_depend d2));
  
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr1; 
    REPEAT FETCH depend_csr1 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=2 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr1;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr2; 
    REPEAT FETCH depend_csr2 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=3 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr2;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr3; 
    REPEAT FETCH depend_csr3 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=4 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr3;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr4; 
    REPEAT FETCH depend_csr4 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=5 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr4;
  SET results=0; 
  set current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr5; 
    REPEAT FETCH depend_csr5 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=6 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr5;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr6; 
    REPEAT FETCH depend_csr6 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=7 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr6;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr7; 
    REPEAT FETCH depend_csr7 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=8 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr7;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr8; 
    REPEAT FETCH depend_csr8 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=9 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr8;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr9; 
    REPEAT FETCH depend_csr9 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=10 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr9;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr10; 
    REPEAT FETCH depend_csr10 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=11 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr10;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr11; 
    REPEAT FETCH depend_csr11 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=12 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr11;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr12; 
    REPEAT FETCH depend_csr12 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=13 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr12;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr13; 
    REPEAT FETCH depend_csr13 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=14 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr13;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr14; 
    REPEAT FETCH depend_csr14 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=15 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr14;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr15; 
    REPEAT FETCH depend_csr15 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=16 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr15;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr16; 
    REPEAT FETCH depend_csr16 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=17 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr16;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr17; 
    REPEAT FETCH depend_csr17 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=18 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr17;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr18; 
    REPEAT FETCH depend_csr18 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=19 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr18;
  SET results=0; 
  SET current_sys = '';
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr19; 
    REPEAT FETCH depend_csr19 INTO current_sys,depend_sys;  -- 获得结果
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO parent_full_depend_sys FROM system s WHERE s.name = depend_sys;
      SELECT (CASE WHEN s.full_depend='' THEN '' ELSE CONCAT(',',s.full_depend) END ) INTO full_depend_sys FROM system s WHERE s.name = current_sys;
      SET full_depend_sys = CONCAT(depend_sys,full_depend_sys,parent_full_depend_sys);
      UPDATE system s SET LEVEL=20 ,s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;
    UNTIL results END REPEAT;
  CLOSE depend_csr19;
  SET results=0; 
  SET current_sys = '';
  
  /* ---------------------------------------------------------------------------------- */
  OPEN depend_csr; 
    REPEAT FETCH depend_csr INTO current_sys,parent_full_depend_sys;  -- 获得结果
      SET full_depend_sys = '';
      SELECT (LENGTH(parent_full_depend_sys) - LENGTH( REPLACE(parent_full_depend_sys,',','')) +1 ) INTO i;
      while i >=1 do 
        set temp_sys = splitString(parent_full_depend_sys,',',i);
        if LOCATE(temp_sys,full_depend_sys)=0 then
          set full_depend_sys = concat(temp_sys,(CASE WHEN full_depend_sys='' THEN '' ELSE CONCAT(',',full_depend_sys) END ) );
        end if;
        set i = i - 1; 
      end while;
      UPDATE system s SET s.`full_depend`=full_depend_sys,s.updated=SYSDATE() WHERE s.name = current_sys;     
    UNTIL results END REPEAT;
  CLOSE depend_csr; 
  SET results=0; 
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
