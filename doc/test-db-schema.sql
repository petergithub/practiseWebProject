
CREATE SCHEMA `test` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `test`;

DROP TABLE if EXISTS test.user;
CREATE TABLE test.`user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` VARCHAR(20) DEFAULT NULL COMMENT '登录名',
  `password` VARCHAR(20) DEFAULT NULL COMMENT '密码',
  `first_name` VARCHAR(100) NOT NULL COMMENT '登录名',
  `last_name` VARCHAR(100) NOT NULL COMMENT '登录名',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '电子邮箱',
  `status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '会员状态 :0,激活;1,禁用',
  `source` INT(11) NOT NULL DEFAULT '1' COMMENT '来源:0本地，1，云平台',
  `cost` DECIMAL(5,2) NOT NULL DEFAULT '0' COMMENT '交易成本: 给通道',
  `creation_date` DATETIME DEFAULT now() COMMENT '创建时间UTC 0000-00-00 00:00:00',
  `creation_user` VARCHAR(64) NOT NULL COMMENT '创建的用户',
  `modify_date` DATETIME DEFAULT now() COMMENT 'UTC记录最后一次修改 0000-00-00 00:00:00',
  `modify_user` VARCHAR(64) NOT NULL COMMENT '记录最后一次修改',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `ak_login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统一用户表';

-- select a.id,a.username as operater,DATE_FORMAT(a.date,	'%Y%m%d  %H:%m:%s');
-- insert into test_sql_type (name,cost,status,date) values ('name',12.3,1,'2015-09-02 14:25:30');