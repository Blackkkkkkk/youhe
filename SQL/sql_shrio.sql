DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `mname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module 权限表
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'add');
INSERT INTO `sys_permission` VALUES ('2', 'delete');
INSERT INTO `sys_permission` VALUES ('3', 'query');
INSERT INTO `sys_permission` VALUES ('4', 'update');

-- ----------------------------
-- Table structure for module_role  权限-角色
-- ----------------------------
DROP TABLE IF EXISTS `shiro_module_role`;
CREATE TABLE `shiro_module_role` (
  `rid` int(11) DEFAULT NULL,
  `mid` int(11) DEFAULT NULL,
  KEY `rid` (`rid`),
  KEY `mid` (`mid`),
  CONSTRAINT `mid` FOREIGN KEY (`mid`) REFERENCES `shiro_module` (`mid`),
  CONSTRAINT `rid` FOREIGN KEY (`rid`) REFERENCES `shiro_role` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module_role
-- ----------------------------
INSERT INTO `shiro_module_role` VALUES ('1', '1');
INSERT INTO `shiro_module_role` VALUES ('1', '2');
INSERT INTO `shiro_module_role` VALUES ('1', '3');
INSERT INTO `shiro_module_role` VALUES ('1', '4');
INSERT INTO `shiro_module_role` VALUES ('2', '1');
INSERT INTO `shiro_module_role` VALUES ('2', '3');

-- ----------------------------
-- Table structure for role 角色表
-- ----------------------------
DROP TABLE IF EXISTS `shiro_role`;
# noinspection SqlNoDataSourceInspection
CREATE TABLE `shiro_role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `rname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `shiro_role` VALUES ('1', 'admin');
INSERT INTO `shiro_role` VALUES ('2', 'customer');

-- ----------------------------
-- Table structure for user 用户表
-- ----------------------------
DROP TABLE IF EXISTS `shiro_user`;
CREATE TABLE `shiro_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `shiro_user` VALUES ('1', 'admin', '123');
INSERT INTO `shiro_user` VALUES ('2', 'customer', '1992');

-- ----------------------------
-- Table structure for user_role 用户-角色表
-- ----------------------------
DROP TABLE IF EXISTS `shiro_user_role`;
CREATE TABLE `shiro_user_role` (
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  KEY `u_fk` (`uid`),
  KEY `r_fk` (`rid`),
  CONSTRAINT `r_fk` FOREIGN KEY (`rid`) REFERENCES `shiro_role` (`rid`),
  CONSTRAINT `u_fk` FOREIGN KEY (`uid`) REFERENCES `shiro_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `shiro_user_role` VALUES ('1', '1');
INSERT INTO `shiro_user_role` VALUES ('2', '2');