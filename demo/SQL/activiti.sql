SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
-- DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log` (
`LOG_NR_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`TYPE_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TIME_STAMP_`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
`USER_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DATA_`  longblob NULL ,
`LOCK_OWNER_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`LOCK_TIME_`  timestamp NULL DEFAULT NULL ,
`IS_PROCESSED_`  tinyint(4) NULL DEFAULT 0 ,
PRIMARY KEY (`LOG_NR_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of act_evt_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
-- DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`REV_`  int(11) NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DEPLOYMENT_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`BYTES_`  longblob NULL ,
`GENERATED_`  tinyint(4) NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_ge_bytearray
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
-- DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property` (
`NAME_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`VALUE_`  varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`NAME_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_ge_property
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
-- DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`ACT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CALL_PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`ACT_NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`ACT_TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`ASSIGNEE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`START_TIME_`  datetime NOT NULL ,
`END_TIME_`  datetime NULL DEFAULT NULL ,
`DURATION_`  bigint(20) NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
PRIMARY KEY (`ID_`),
INDEX `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`) USING BTREE ,
INDEX `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`) USING BTREE ,
INDEX `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`, `ACT_ID_`) USING BTREE ,
INDEX `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`, `ACT_ID_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_hi_actinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
-- DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`USER_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DESCRIPTION_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`URL_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CONTENT_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TIME_`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_hi_attachment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
-- DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TIME_`  datetime NOT NULL ,
`USER_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`ACTION_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`MESSAGE_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`FULL_MSG_`  longblob NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_hi_comment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
-- DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`ACT_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`VAR_TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`TIME_`  datetime NOT NULL ,
`BYTEARRAY_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DOUBLE_`  double NULL DEFAULT NULL ,
`LONG_`  bigint(20) NULL DEFAULT NULL ,
`TEXT_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TEXT2_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`),
INDEX `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`) USING BTREE ,
INDEX `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`) USING BTREE ,
INDEX `ACT_IDX_HI_DETAIL_TIME` (`TIME_`) USING BTREE ,
INDEX `ACT_IDX_HI_DETAIL_NAME` (`NAME_`) USING BTREE ,
INDEX `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_hi_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
-- DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`GROUP_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`USER_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`),
INDEX `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`) USING BTREE ,
INDEX `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`) USING BTREE ,
INDEX `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_hi_identitylink
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
-- DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`BUSINESS_KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`START_TIME_`  datetime NOT NULL ,
`END_TIME_`  datetime NULL DEFAULT NULL ,
`DURATION_`  bigint(20) NULL DEFAULT NULL ,
`START_USER_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`START_ACT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`END_ACT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`SUPER_PROCESS_INSTANCE_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DELETE_REASON_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`),
UNIQUE INDEX `PROC_INST_ID_` (`PROC_INST_ID_`) USING BTREE ,
INDEX `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`) USING BTREE ,
INDEX `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_hi_procinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
-- DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_DEF_KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PARENT_TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DESCRIPTION_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`OWNER_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`ASSIGNEE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`START_TIME_`  datetime NOT NULL ,
`CLAIM_TIME_`  datetime NULL DEFAULT NULL ,
`END_TIME_`  datetime NULL DEFAULT NULL ,
`DURATION_`  bigint(20) NULL DEFAULT NULL ,
`DELETE_REASON_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PRIORITY_`  int(11) NULL DEFAULT NULL ,
`DUE_DATE_`  datetime NULL DEFAULT NULL ,
`FORM_KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CATEGORY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
PRIMARY KEY (`ID_`),
INDEX `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_hi_taskinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
-- DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`VAR_TYPE_`  varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`BYTEARRAY_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DOUBLE_`  double NULL DEFAULT NULL ,
`LONG_`  bigint(20) NULL DEFAULT NULL ,
`TEXT_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TEXT2_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CREATE_TIME_`  datetime NULL DEFAULT NULL ,
`LAST_UPDATED_TIME_`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`),
INDEX `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`) USING BTREE ,
INDEX `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`, `VAR_TYPE_`) USING BTREE ,
INDEX `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_hi_varinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_id_group
-- ----------------------------
-- DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`REV_`  int(11) NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_id_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_id_info
-- ----------------------------
-- DROP TABLE IF EXISTS `act_id_info`;
CREATE TABLE `act_id_info` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`REV_`  int(11) NULL DEFAULT NULL ,
`USER_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TYPE_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`VALUE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PASSWORD_`  longblob NULL ,
`PARENT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_id_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_id_membership
-- ----------------------------
-- DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership` (
`USER_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`GROUP_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
PRIMARY KEY (`USER_ID_`, `GROUP_ID_`),
FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `ACT_FK_MEMB_GROUP` (`GROUP_ID_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_id_membership
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_id_user
-- ----------------------------
-- DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`REV_`  int(11) NULL DEFAULT NULL ,
`FIRST_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`LAST_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EMAIL_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PWD_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PICTURE_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_id_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_procdef_info
-- ----------------------------
-- DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`INFO_JSON_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_procdef_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
-- DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CATEGORY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
`DEPLOY_TIME_`  timestamp NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_re_deployment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
-- DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CATEGORY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CREATE_TIME_`  timestamp NULL DEFAULT NULL ,
`LAST_UPDATE_TIME_`  timestamp NULL DEFAULT NULL ,
`VERSION_`  int(11) NULL DEFAULT NULL ,
`META_INFO_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DEPLOYMENT_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EDITOR_SOURCE_VALUE_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EDITOR_SOURCE_EXTRA_VALUE_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_re_model
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
-- DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`CATEGORY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`VERSION_`  int(11) NOT NULL ,
`DEPLOYMENT_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`RESOURCE_NAME_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DGRM_RESOURCE_NAME_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DESCRIPTION_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`HAS_START_FORM_KEY_`  tinyint(4) NULL DEFAULT NULL ,
`HAS_GRAPHICAL_NOTATION_`  tinyint(4) NULL DEFAULT NULL ,
`SUSPENSION_STATE_`  int(11) NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_re_procdef
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
-- DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`EVENT_TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`EVENT_NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`ACTIVITY_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CONFIGURATION_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`CREATED_`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_ru_event_subscr
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
-- DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`REV_`  int(11) NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`BUSINESS_KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PARENT_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`SUPER_EXEC_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`ACT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`IS_ACTIVE_`  tinyint(4) NULL DEFAULT NULL ,
`IS_CONCURRENT_`  tinyint(4) NULL DEFAULT NULL ,
`IS_SCOPE_`  tinyint(4) NULL DEFAULT NULL ,
`IS_EVENT_SCOPE_`  tinyint(4) NULL DEFAULT NULL ,
`SUSPENSION_STATE_`  int(11) NULL DEFAULT NULL ,
`CACHED_ENT_STATE_`  int(11) NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`LOCK_TIME_`  timestamp NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`),
INDEX `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_ru_execution
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
-- DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`REV_`  int(11) NULL DEFAULT NULL ,
`GROUP_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`USER_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_ru_identitylink
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
-- DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`LOCK_EXP_TIME_`  timestamp NULL DEFAULT NULL ,
`LOCK_OWNER_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EXCLUSIVE_`  tinyint(1) NULL DEFAULT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROCESS_INSTANCE_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`RETRIES_`  int(11) NULL DEFAULT NULL ,
`EXCEPTION_STACK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`EXCEPTION_MSG_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DUEDATE_`  timestamp NULL DEFAULT NULL ,
`REPEAT_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`HANDLER_TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`HANDLER_CFG_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_ru_job
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
-- DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' ,
`REV_`  int(11) NULL DEFAULT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_DEF_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PARENT_TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DESCRIPTION_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_DEF_KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`OWNER_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`ASSIGNEE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DELEGATION_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PRIORITY_`  int(11) NULL DEFAULT NULL ,
`CREATE_TIME_`  timestamp NULL DEFAULT NULL ,
`DUE_DATE_`  datetime NULL DEFAULT NULL ,
`CATEGORY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`SUSPENSION_STATE_`  int(11) NULL DEFAULT NULL ,
`TENANT_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' ,
`FORM_KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`),
INDEX `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_ru_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
-- DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable` (
`ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`REV_`  int(11) NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`EXECUTION_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`PROC_INST_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TASK_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`BYTEARRAY_ID_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`DOUBLE_`  double NULL DEFAULT NULL ,
`LONG_`  bigint(20) NULL DEFAULT NULL ,
`TEXT_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`TEXT2_`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
PRIMARY KEY (`ID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Records of act_ru_variable
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for edu_task
-- ----------------------------
-- DROP TABLE IF EXISTS `edu_task`;
CREATE TABLE `edu_task` (
`task_id`  int(11) NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  int(2) NULL DEFAULT NULL ,
`flow_task_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`task_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of edu_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_function
-- ----------------------------
-- DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function` (
`FUNCTION_ID`  int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID' ,
`PARENT_ID`  int(11) NULL DEFAULT 0 COMMENT '权限父ID' ,
`FUNCTION_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名' ,
`FUNCTION_URL`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限URL' ,
`FUNCTION_TYPE`  tinyint(1) NULL DEFAULT 0 COMMENT '权限类型 1菜单 2功能' ,
`CREATE_TIME`  timestamp NULL DEFAULT NULL COMMENT '创建时间' ,
`SORT`  int(11) NULL DEFAULT 0 COMMENT '排序' ,
PRIMARY KEY (`FUNCTION_ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='权限表'
AUTO_INCREMENT=150

;

-- ----------------------------
-- Records of sys_function
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
-- DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
`ROLE_ID`  int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID' ,
`ROLE_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名' ,
`CREATE_TIME`  timestamp NULL DEFAULT NULL COMMENT '创建时间' ,
PRIMARY KEY (`ROLE_ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='角色表'
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_function
-- ----------------------------
-- DROP TABLE IF EXISTS `sys_role_function`;
CREATE TABLE `sys_role_function` (
`ROLE_ID`  int(11) NULL DEFAULT 0 COMMENT '角色ID' ,
`FUNCTION_ID`  int(11) NULL DEFAULT 0 COMMENT '权限ID' 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='角色权限关联表'

;

-- ----------------------------
-- Records of sys_role_function
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
-- DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
`USER_ID`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`LOGIN_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PASSWORD`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USER_NAME`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`STATUS`  int(1) NULL DEFAULT NULL ,
`LAST_LOGIN_TIME`  datetime NULL DEFAULT NULL ,
`LAST_LOGIN_IP`  varchar(1002) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ROLE_ID`  int(2) NULL DEFAULT NULL ,
`IS_SUPRE`  bit(1) NULL DEFAULT NULL ,
`CREATE_TIME`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`USER_ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
-- DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
`id`  bigint(11) NOT NULL AUTO_INCREMENT ,
`userName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`passWord`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_sex`  int(1) NULL DEFAULT NULL ,
`nick_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`role_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=49

;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Auto increment value for act_evt_log
-- ----------------------------
ALTER TABLE `act_evt_log` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for edu_task
-- ----------------------------
ALTER TABLE `edu_task` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for sys_function
-- ----------------------------
ALTER TABLE `sys_function` AUTO_INCREMENT=150;

-- ----------------------------
-- Auto increment value for sys_role
-- ----------------------------
ALTER TABLE `sys_role` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for users
-- ----------------------------
ALTER TABLE `users` AUTO_INCREMENT=49;
