/*
SQLyog Enterprise v12.08 (64 bit)
MySQL - 5.6.24 : Database - deploy_xu
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `env` */

DROP TABLE IF EXISTS `env`;

CREATE TABLE `env` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `system_depend` */

DROP TABLE IF EXISTS `system_depend`;

CREATE TABLE `system_depend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `system` varchar(50) DEFAULT NULL,
  `depend_system` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  -- --------------------------------------------------------------------------------
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
  
  -- --------------------------------------------------------------------------------
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
