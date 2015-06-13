drop table if exists test.user;
CREATE TABLE test.`user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(20) DEFAULT NULL COMMENT '登录名',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `first_name` varchar(100) NOT NULL COMMENT '登录名',
  `last_name` varchar(100) NOT NULL COMMENT '登录名',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '会员状态 :0,激活;1,禁用',
  `source` int(11) NOT NULL DEFAULT '1' COMMENT '来源:0本地，1，云平台',
  `creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `ak_login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统一用户表';