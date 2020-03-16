/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.240
Source Server Version : 50722
Source Host           : 192.168.0.240:3306
Source Database       : product_mis

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-10-15 10:27:09
*/
-- ----------------------------
-- Table structure for `sys_oper_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(64) NOT NULL COMMENT '操作员',
  `ip` varchar(15) NOT NULL COMMENT '地址',
  `oper_content` varchar(200) NOT NULL COMMENT '操作',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `sys_organization`
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '部门名称',
  `address` varchar(100) DEFAULT NULL COMMENT '部门地址',
  `code` varchar(64) NOT NULL COMMENT '部门编号',
  `pid` bigint(10) DEFAULT NULL COMMENT '父id',
  `seq` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1', '北京趋势科特有限公司', '北京市', 'TR00', null, '1', '2017-07-17 12:56:16', '2018-10-12 15:54:58');
INSERT INTO `sys_organization` VALUES ('5', '技术部', '', 'TR03', '1', '3', '2017-07-17 12:57:46', null);
INSERT INTO `sys_organization` VALUES ('3', '财务/行政部', '', 'TR04', '1', '4', '2018-06-20 19:03:07', null);
INSERT INTO `sys_organization` VALUES ('4', '销售部', '', 'TR02', '1', '2', '2018-06-20 19:03:28', null);
INSERT INTO `sys_organization` VALUES ('6', '生产部', '', 'TR05', '1', '5', '2018-06-20 19:03:52', null);
INSERT INTO `sys_organization` VALUES ('2', '管理部', '', 'TR01', '1', '1', '2018-06-20 19:04:44', null);

-- ----------------------------
-- Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '资源名称',
  `url` varchar(100) DEFAULT NULL COMMENT '资源路径',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `pid` bigint(11) DEFAULT NULL COMMENT '父id',
  `seq` tinyint(1) NOT NULL DEFAULT '0' COMMENT '序列',
  `resource_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0菜单，1功能',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8 COMMENT='资源';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '系统管理', '', null, null, '1', '0', '2017-07-17 11:40:18', null);
INSERT INTO `sys_resource` VALUES ('2', '资源管理', '/resource/manager', '资源管理', '1', '1', '0', '2018-09-26 23:13:15', '2018-09-26 23:13:16');
INSERT INTO `sys_resource` VALUES ('3', '添加', '/resource/add', null, '2', '2', '1', '2017-07-17 11:51:22', null);
INSERT INTO `sys_resource` VALUES ('4', '编辑', '/resource/edit', null, '2', '3', '1', '2017-07-17 11:51:37', null);
INSERT INTO `sys_resource` VALUES ('5', '删除', '/resource/delete', null, '2', '4', '1', '2017-07-17 11:51:32', null);
INSERT INTO `sys_resource` VALUES ('6', '角色管理', '/role/manager', '角色管理', '1', '2', '0', '2014-02-19 01:00:00', null);
INSERT INTO `sys_resource` VALUES ('7', '添加', '/role/add', null, '6', '2', '1', '2017-07-17 11:51:53', null);
INSERT INTO `sys_resource` VALUES ('8', '编辑', '/role/edit', null, '6', '3', '1', '2017-07-17 11:51:58', null);
INSERT INTO `sys_resource` VALUES ('9', '删除', '/role/delete', null, '6', '4', '1', '2017-07-17 11:52:02', null);
INSERT INTO `sys_resource` VALUES ('10', '授权', '/role/grant', null, '6', '5', '1', '2017-07-17 11:52:07', null);
INSERT INTO `sys_resource` VALUES ('11', '操作员管理', '/user/manager', null, '1', '3', '0', '2017-08-03 18:45:38', null);
INSERT INTO `sys_resource` VALUES ('12', '添加', '/user/add', null, '11', '2', '1', '2017-07-17 11:52:21', null);
INSERT INTO `sys_resource` VALUES ('13', '编辑', '/user/edit', null, '11', '3', '1', '2017-07-17 11:52:25', null);
INSERT INTO `sys_resource` VALUES ('14', '删除', '/user/delete', null, '11', '4', '1', '2017-07-17 11:52:30', null);
INSERT INTO `sys_resource` VALUES ('15', '批量删除', '/log/batchDelete', '', '21', '2', '0', '2018-10-12 16:02:48', '2018-10-12 16:02:47');
INSERT INTO `sys_resource` VALUES ('16', '部门管理', '/organization/manager', '部门管理', '1', '4', '0', '2014-02-19 01:00:00', null);
INSERT INTO `sys_resource` VALUES ('17', '添加', '/organization/add', null, '16', '2', '1', '2017-07-17 11:52:56', null);
INSERT INTO `sys_resource` VALUES ('18', '编辑', '/organization/edit', null, '16', '3', '1', '2017-07-17 11:53:01', null);
INSERT INTO `sys_resource` VALUES ('19', '删除', '/organization/delete', null, '16', '4', '1', '2017-07-17 11:53:05', null);
INSERT INTO `sys_resource` VALUES ('20', '删除', '/log/delete', '', '21', '1', '1', '2018-10-12 16:01:32', '2018-10-12 16:01:32');
INSERT INTO `sys_resource` VALUES ('21', '日志管理', '/log/manager', null, '1', '5', '0', '2018-09-20 15:56:44', '2018-09-20 15:56:44');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0有效1无效',
  `code` varchar(20) NOT NULL COMMENT '角色编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '超级管理员', '2018-10-11 16:22:21', '0', '1', '2018-10-11 16:22:21');

-- ----------------------------
-- Table structure for `sys_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` bigint(10) NOT NULL,
  `resource_id` bigint(10) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源';

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1');
INSERT INTO `sys_role_resource` VALUES ('1', '2');
INSERT INTO `sys_role_resource` VALUES ('1', '3');
INSERT INTO `sys_role_resource` VALUES ('1', '4');
INSERT INTO `sys_role_resource` VALUES ('1', '5');
INSERT INTO `sys_role_resource` VALUES ('1', '6');
INSERT INTO `sys_role_resource` VALUES ('1', '7');
INSERT INTO `sys_role_resource` VALUES ('1', '8');
INSERT INTO `sys_role_resource` VALUES ('1', '9');
INSERT INTO `sys_role_resource` VALUES ('1', '10');
INSERT INTO `sys_role_resource` VALUES ('1', '11');
INSERT INTO `sys_role_resource` VALUES ('1', '12');
INSERT INTO `sys_role_resource` VALUES ('1', '13');
INSERT INTO `sys_role_resource` VALUES ('1', '14');
INSERT INTO `sys_role_resource` VALUES ('1', '15');
INSERT INTO `sys_role_resource` VALUES ('1', '16');
INSERT INTO `sys_role_resource` VALUES ('1', '17');
INSERT INTO `sys_role_resource` VALUES ('1', '18');
INSERT INTO `sys_role_resource` VALUES ('1', '19');
INSERT INTO `sys_role_resource` VALUES ('1', '20');
INSERT INTO `sys_role_resource` VALUES ('1', '21');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL COMMENT '登录名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0男1女',
  `age` tinyint(3) NOT NULL COMMENT '年龄',
  `isdefault` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0有效1无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '编号',
  `organization_id` bigint(10) NOT NULL COMMENT '部门id',
  `name` varchar(64) NOT NULL COMMENT '操作员姓名',
  PRIMARY KEY (`id`,`create_time`),
  KEY `code` (`code`) USING BTREE,
  KEY `loginname` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1', '18', '0', '2018-10-10 17:07:47', '', '2018-10-10 20:50:02', '1', '3', 'admin');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(10) NOT NULL,
  `role_id` bigint(10) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
