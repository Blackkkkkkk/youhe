
-- 清除用户相关数据
TRUNCATE sys_permission;
TRUNCATE sys_role;
TRUNCATE sys_role_permission;
TRUNCATE sys_user;
TRUNCATE sys_user_role;

-- 系统权限数据
INSERT INTO `activiti`.`sys_permission` VALUES ('2', '流程审批', 'menu', null, 'user:flow', '0', '0/', '2', '1', 'fa fa-tasks');
INSERT INTO `activiti`.`sys_permission` VALUES ('3', '流程管理', 'menu', '/activiti/ProcessManagement', 'sys:processManagement', '2', '0/2', '6', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('4', '我的待办', 'menu', '/activiti/dealwith', 'user:backlog', '2', '0/2', '1', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('5', '我的已办', 'menu', '/activiti/dealwithdo', 'user:backlogdo', '2', '0/2', '7', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('6', '流程在线设计', 'menu', '/create', 'sys:flowDesigner', '2', '0/2', '2', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('7', '流程部署模块', 'menu', null, 'sys:deploy', '2', '0/2', '3', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('8', '流程部署', 'button', '/activiti/deploy', 'sys:flowDeploy', '2', '0/2', '4', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('9', '启动流程', 'button', null, 'user:startFlow', '2', '0/2', '5', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('10', '系统管理', 'menu', null, 'sys:systemManage', '0', '0/', '1', '1', 'fa fa-user');
INSERT INTO `activiti`.`sys_permission` VALUES ('11', '部门管理', 'menu', '/department/index', 'sys:deptManage', '10', '0/8/', '0', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('12', '用户管理', 'menu', '/user/index', 'sys:userManage', '10', '0/8/', '1', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('13', '角色管理', 'menu', '/role/index', 'sys:roleManage', '10', '0/8/', '1', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('14', '菜单管理', 'menu', '/permission/index', 'shop:commodityManage', '10', '0/8/', '1', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('15', '商品主页', 'menu', null, 'shop:page', '0', '0/', '1', '1', 'fa fa-shopping-bag');
INSERT INTO `activiti`.`sys_permission` VALUES ('16', '订单管理', 'menu', '', 'shop:orderManage', '0', '0/', '1', '1', 'fa fa-indent');
INSERT INTO `activiti`.`sys_permission` VALUES ('17', '订单列表', 'menu', '/order/list', 'shop:orderManage', '16', '0/14/', '1', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('18', '商城主页', 'menu', null, 'shop:page', '0', null, '1', '1', 'fa fa-shopping-cart');
INSERT INTO `activiti`.`sys_permission` VALUES ('19', '商品管理', 'menu', '/shop/index', 'shop:commodityManage', '18', null, '1', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('20', '商品首页', 'menu', '/shop/shopPageMange', 'sys:shopPageMange', '18', null, '2', '1', null);
INSERT INTO `activiti`.`sys_permission` VALUES ('21', '商品分类', 'menu', '/commodity/index', 'sys:commodity', '18', null, '3', '1', null);





-- 系统角色
INSERT INTO `activiti`.`sys_role`(`RID`, `RNAME`, `AVAILABLE`, `PARENT_ID`, `ORDER_NUM`) VALUES (1, '超级管理员', '1', 0, 0);
INSERT INTO `activiti`.`sys_role`(`RID`, `RNAME`, `AVAILABLE`, `PARENT_ID`, `ORDER_NUM`) VALUES (2, '123', '1', 0, 0);
INSERT INTO `activiti`.`sys_role`(`RID`, `RNAME`, `AVAILABLE`, `PARENT_ID`, `ORDER_NUM`) VALUES (3, '用户管理员', '1', 2, 0);
INSERT INTO `activiti`.`sys_role`(`RID`, `RNAME`, `AVAILABLE`, `PARENT_ID`, `ORDER_NUM`) VALUES (12, 'test', '1', 2, 0);

-- 系统角色权限
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (20, 1, 2);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (21, 1, 3);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (22, 1, 4);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (23, 1, 5);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (24, 1, 6);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (25, 1, 7);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (26, 1, 8);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (27, 1, 9);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (28, 1, 10);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (29, 1, 11);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (30, 1, 12);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (31, 1, 13);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (32, 1, 15);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (33, 1, 16);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (34, 1, 17);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (35, 1, 18);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (36, 1, 19);

-- 系统用户
INSERT INTO `activiti`.`sys_user`(`UID`, `USER_ACCOUNT`, `USER_NAME`, `USER_PASSWORD`, `SALT`, `LOCKED`, `REGISTER_DATE`, `PHONE`, `EMAIL`, `ROLE_ID`, `DEPARTMENT_ID`, `ORDER_NUM`) VALUES (1, 'admin', 'admin', 'd896087c41260a1a5e7934829ff826e534a48f66', '38a9ea5f03fb6613537d0bca4c791b88', '0', '2018-12-09 11:46:25.000', '13148913794', '757027132@qq.com', '1', '0', NULL);
INSERT INTO `activiti`.`sys_user`(`UID`, `USER_ACCOUNT`, `USER_NAME`, `USER_PASSWORD`, `SALT`, `LOCKED`, `REGISTER_DATE`, `PHONE`, `EMAIL`, `ROLE_ID`, `DEPARTMENT_ID`, `ORDER_NUM`) VALUES (209, 'test11', 'test11', 'af634dfa1a77c1798088e58ee9963a54bbebdf8b', '584080c14aa2f5ea02deacbee6fb0a2d', '0', '2019-01-08 17:29:20.000', '12345679991', '757027132@qq.com', '0', NULL, 0);

-- 系统用户角色
INSERT INTO `activiti`.`sys_user_role`(`id`, `sys_user_id`, `sys_role_id`) VALUES (1, '1', '1');
