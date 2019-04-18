TRUNCATE sys_permission;

INSERT INTO `sys_permission` VALUES ('2', '流程审批', 'menu', null, 'user:flow', '0', '0/', '2', '1');
INSERT INTO `sys_permission` VALUES ('3', '流程管理', 'menu', '/activiti/ProcessManagement', 'sys:processManagement', '2', '0/2', '6', '1');
INSERT INTO `sys_permission` VALUES ('4', '我的待办', 'menu', '/activiti/dealwith', 'user:backlog', '2', '0/2', '1', '1');
INSERT INTO `sys_permission` VALUES ('5', '我的已办', 'menu', '/activiti/dealwithdo', 'user:backlogdo', '2', '0/2', '7', '1');
INSERT INTO `sys_permission` VALUES ('9', '启动流程', 'button', null, 'user:startFlow', '2', '0/2', '5', '1');
INSERT INTO `sys_permission` VALUES ('10', '系统管理', 'menu', null, 'sys:systemManage', '0', '0/', '1', '1');
INSERT INTO `sys_permission` VALUES ('11', '部门管理', 'menu', '/department/index', 'sys:deptManage', '10', '0/8/', '0', '1');
INSERT INTO `sys_permission` VALUES ('12', '用户管理', 'menu', '/user/index', 'sys:userManage', '10', '0/8/', '1', '1');
INSERT INTO `sys_permission` VALUES ('13', '角色管理', 'menu', '/role/index', 'sys:roleManage', '10', '0/8/', '1', '1');
INSERT INTO `sys_permission` VALUES ('14', '菜单管理', 'menu', '/permission/index', 'shop:commodityManage', '10', '0/8/', '1', '1');
INSERT INTO `sys_permission` VALUES ('15', '商品主页', 'menu', null, 'shop:page', '0', '0/', '1', '1');
INSERT INTO `sys_permission` VALUES ('16', '订单管理', 'menu', '', 'shop:orderManage', '0', '0/', '1', '1');
INSERT INTO `sys_permission` VALUES ('17', '订单列表', 'menu', '/order/list', 'shop:orderManage', '16', '0/14/', '1', '1');
INSERT INTO `sys_permission` VALUES ('18', '商城主页', 'menu', null, 'shop:page', '0', null, '1', '1');
INSERT INTO `sys_permission` VALUES ('19', '商品管理', 'menu', '/shop/index', 'shop:commodityManage', '18', null, '1', '1');
INSERT INTO `sys_permission` VALUES ('20', '商品首页', 'menu', '/shop/shopPageMange', 'sys:shopPageMange', '18', null, '2', '1');
INSERT INTO `sys_permission` VALUES ('21', '商品分类', 'menu', '/commodity/index', 'sys:commodity', '18', null, '3', '1');