
-- 清除用户相关数据
TRUNCATE sys_permission;
TRUNCATE sys_role;
TRUNCATE sys_role_permission;
TRUNCATE sys_user;
TRUNCATE sys_user_role;
TRUNCATE sys_desktop;
TRUNCATE sys_desktop_set;

-- 系统权限数据
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (2, '流程审批', 'menu', NULL, 'user:flow', 0, '0/', '2', '1', 'fa fa-tasks');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (3, '流程管理', 'menu', '/activiti/ProcessManagement', 'sys:processManagement', 2, '0/2', '6', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (4, '我的待办', 'menu', '/activiti/dealwith', 'user:backlog', 2, '0/2', '1', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (5, '我的已办', 'menu', '/activiti/dealwithdo', 'user:backlogdo', 2, '0/2', '7', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (9, '启动流程', 'button', NULL, 'user:startFlow', 2, '0/2', '5', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (10, '系统管理', 'menu', NULL, 'sys:systemManage', 0, '0/', '1', '1', 'fa fa-user');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (11, '部门管理', 'menu', '/department/index', 'sys:deptManage', 10, '0/8/', '0', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (12, '用户管理', 'menu', '/user/index', 'sys:userManage', 10, '0/8/', '1', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (13, '角色管理', 'menu', '/role/index', 'sys:roleManage', 10, '0/8/', '1', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (14, '菜单管理', 'menu', '/permission/index', 'shop:commodityManage', 10, '0/8/', '1', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (16, '订单管理', 'menu', '', 'shop:orderManage', 0, '0/', '1', '1', 'fa fa-indent');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (17, '订单列表', 'menu', '/order/manage/list', 'shop:orderManage', 16, '0/14/', '1', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (18, '商城管理', 'menu', NULL, 'shop:page', 0, NULL, '1', '1', 'fa fa-shopping-cart');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (19, '商品管理', 'menu', '/shop/index', 'shop:commodityManage', 18, NULL, '1', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (20, '商品首页', 'menu', '/shop/shopPageMange', 'sys:shopPageMange', 18, NULL, '2', '1', NULL);
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (21, '商品分类', 'menu', '/commodity/index', 'sys:commodity', 18, NULL, '3', '1', NULL);

INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (22, '审批委托设置', 'menu', '/activiti/delegate/index', 'flow:delegate:index', 2, NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (23, '代理申请', 'menu', NULL, 'flow:agency', 2, NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (24, '代理申请设置', 'menu', NULL, 'flow:agency:set', 2, NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (26, '编辑', 'button', NULL, 'flow:delegate:edit', 22, NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (27, '删除', 'button', NULL, 'flow:delegate:del', 22, NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission` (`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES ('29', '我的申请', 'menu', '/activiti/apply', 'user:apply', '2', NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission` (`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES ('30', '文档中心', 'menu', '/docment/index', 'sys:docment', '0', NULL, '3', '1', NULL);
INSERT INTO `activiti`.`sys_permission` (`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES ('31', '知识管理', 'menu', '/docment/knowledge', 'sys:knowdge', '30', NULL, '3', '1', NULL);
INSERT INTO `activiti`.`sys_permission` (`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES ('32', '待我阅读', 'menu', '/activiti/read', 'user:read', '2', NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission` (`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES ('33', '规章制度', 'menu', '/docment/rule', 'sys:rule', '30', NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission` (`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES ('34', '下载中心', 'menu', '/docment/download', 'sys:download', '30', NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (35, '报表中心', 'menu', NULL, 'report:center', 0, NULL, '0', '1', 'fa fa-bar-chart');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (36, '流程报表', 'menu', '/report/process/index', 'report:process:index', 35, NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (37, '报销报表', 'menu', '/report/reimburse/index', 'report:reimburse:index', 35, NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (38, '测试', 'button', NULL, 'app:flow:test', 3, NULL, '0', '1', '');
INSERT INTO `activiti`.`sys_permission`(`PID`, `PNAME`, `TYPE`, `URL`, `PERCODE`, `PARENTID`, `PARENTIDS`, `SORTSRING`, `AVAILABLE`, `MENUICON`) VALUES (39, '已办代理', 'menu', '/activiti/hisAgency/index', 'flow:hisAgent:index', 2, NULL, '0', '1', '');



-- 系统角色
INSERT INTO `activiti`.`sys_role`(`RID`, `RNAME`, `AVAILABLE`, `PARENT_ID`, `ORDER_NUM`) VALUES (1, '超级管理员', '1', 0, 0);
INSERT INTO `activiti`.`sys_role`(`RID`, `RNAME`, `AVAILABLE`, `PARENT_ID`, `ORDER_NUM`) VALUES (2, '123', '1', 0, 0);
INSERT INTO `activiti`.`sys_role`(`RID`, `RNAME`, `AVAILABLE`, `PARENT_ID`, `ORDER_NUM`) VALUES (3, '用户管理员', '1', 2, 0);
INSERT INTO `activiti`.`sys_role`(`RID`, `RNAME`, `AVAILABLE`, `PARENT_ID`, `ORDER_NUM`) VALUES (12, 'test', '1', 2, 0);

-- 系统角色权限
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (37, 1, 2);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (38, 1, 3);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (39, 1, 4);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (40, 1, 5);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (41, 1, 10);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (42, 1, 11);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (43, 1, 12);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (44, 1, 13);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (45, 1, 14);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (46, 1, 16);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (47, 1, 17);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (48, 1, 18);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (49, 1, 19);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (50, 1, 20);
INSERT INTO `activiti`.`sys_role_permission`(`id`, `sys_role_id`, `sys_permission_id`) VALUES (51, 1, 21);


-- 系统用户
INSERT INTO `activiti`.`sys_user`(`UID`, `USER_ACCOUNT`, `USER_NAME`, `USER_PASSWORD`, `SALT`, `LOCKED`, `REGISTER_DATE`, `PHONE`, `EMAIL`, `ROLE_ID`, `DEPARTMENT_ID`, `ORDER_NUM`) VALUES (1, 'admin', 'admin', 'd896087c41260a1a5e7934829ff826e534a48f66', '38a9ea5f03fb6613537d0bca4c791b88', '0', '2018-12-09 11:46:25.000', '13148913794', '757027132@qq.com', '1', '43', NULL);
INSERT INTO `activiti`.`sys_user`(`UID`, `USER_ACCOUNT`, `USER_NAME`, `USER_PASSWORD`, `SALT`, `LOCKED`, `REGISTER_DATE`, `PHONE`, `EMAIL`, `ROLE_ID`, `DEPARTMENT_ID`, `ORDER_NUM`) VALUES (209, 'test11', 'test11', 'af634dfa1a77c1798088e58ee9963a54bbebdf8b', '584080c14aa2f5ea02deacbee6fb0a2d', '0', '2019-01-08 17:29:20.000', '12345679991', '757027132@qq.com', '0', NULL, 0);

-- 系统用户角色
INSERT INTO `activiti`.`sys_user_role`(`id`, `sys_user_id`, `sys_role_id`) VALUES (1, '1', '1');


-- 部门表
INSERT INTO `activiti`.`sys_department`(`DEPT_ID`, `PARENT_ID`, `NAME`, `ORDER_NUM`, `STATUS`) VALUES (42, 0, '服务中心', 0, 1);
INSERT INTO `activiti`.`sys_department`(`DEPT_ID`, `PARENT_ID`, `NAME`, `ORDER_NUM`, `STATUS`) VALUES (43, 42, 'IT部', 0, 1);

-- 桌面设置表
INSERT INTO `sys_desktop` VALUES (2, 1, 1, '项目测试进度');
INSERT INTO `sys_desktop` VALUES (20, 1, 2, ' 报告的新数据');
INSERT INTO `sys_desktop` VALUES (21, 1, 3, '阅读下面的评论');
INSERT INTO `sys_desktop` VALUES (22, 1, 4, '你的每日饲料');
INSERT INTO `sys_desktop` VALUES (23, 1, 5, '阿尔法项目');


-- 桌面设置divId表
INSERT INTO `sys_desktop_set` VALUES (71, 1, 1);
INSERT INTO `sys_desktop_set` VALUES (72, 1, 2);
INSERT INTO `sys_desktop_set` VALUES (73, 1, 3);
INSERT INTO `sys_desktop_set` VALUES (74, 1, 4);
INSERT INTO `sys_desktop_set` VALUES (75, 1, 5);
