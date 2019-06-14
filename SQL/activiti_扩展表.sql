
DROP TABLE IF EXISTS `act_ru_delegate`;
CREATE TABLE `act_ru_delegate` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ASSIGNEE` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '办理人',
  `ATTORNEY` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代理人',
  `TYPE` tinyint(2) DEFAULT NULL COMMENT '类型，0：申请代理，1：委托代理',
  `PROCESS_DEFINITION_ID` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程定义ID',
  `PROCESS_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程名称',
  `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `END_TIME` timestamp NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='委托表';

DROP TABLE IF EXISTS `act_hi_delegate`;
CREATE TABLE `act_hi_delegate` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ASSIGNEE` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '办理人',
  `ATTORNEY` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代理人',
  `TYPE` tinyint(2) DEFAULT NULL COMMENT '类型，0：申请代理，1：委托代理',
  `TASK_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务实例ID',
  `DELEGATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '委托时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='委托历史表';

DROP TABLE IF EXISTS `act_ext_copyto`;
CREATE TABLE `act_ext_copyto` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CC` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '抄送人',
  `ASSIGNEE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '办理人',
  `PROC_INST_ID` varchar(64) NOT NULL COMMENT '流程实例ID',
  `TASK_ID` varchar(64) NOT NULL COMMENT '任务ID',
  `PROC_NAME` varchar(255) DEFAULT NULL COMMENT '流程名称',
  `READ_TIME` timestamp NULL DEFAULT NULL COMMENT '查阅时间',
  `DURATION` bigint(20) DEFAULT NULL COMMENT '耗时，单位：毫秒',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务抄送表';


DROP TABLE IF EXISTS `shop_address`;
CREATE TABLE `shop_address` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(20) DEFAULT NULL,
  `addressee` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `deliveryAddr` varchar(2000) DEFAULT NULL,
  `createTime` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='地址表';


DROP TABLE IF EXISTS `shop_collect`;
CREATE TABLE `shop_collect` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `shopId` int(4) NOT NULL,
  `collectTime` datetime(6) DEFAULT NULL,
  `userId` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='收藏表';