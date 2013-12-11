/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50015
Source Host           : localhost:3306
Source Database       : sklay

Target Server Type    : MYSQL
Target Server Version : 50015
File Encoding         : 65001

Date: 2013-09-19 12:43:57
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `sklay_cms_comment`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_comment`;
CREATE TABLE `sklay_cms_comment` (
  `id` bigint(20) NOT NULL auto_increment,
  `biz` varchar(255) default NULL,
  `commented` bigint(20) default NULL,
  `content` longtext NOT NULL,
  `create_at` datetime NOT NULL,
  `fragment` varchar(500) default NULL,
  `img_count` int(11) default NULL,
  `level` int(11) default NULL,
  `liked` bigint(20) default NULL,
  `modify_at` datetime default NULL,
  `owner` varchar(255) default NULL,
  `rank` bigint(20) default NULL,
  `referer` bigint(20) default NULL,
  `tag_ids` varchar(255) default NULL,
  `title` varchar(240) default NULL,
  `unliked` bigint(20) default NULL,
  `ver` int(11) default NULL,
  `viewed` bigint(20) default NULL,
  `creator` bigint(20) default NULL,
  `modifier` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK9064DDF699A2076B` (`creator`),
  KEY `FK9064DDF637A38A16` (`modifier`),
  CONSTRAINT `FK9064DDF637A38A16` FOREIGN KEY (`modifier`) REFERENCES `sklay_user` (`id`),
  CONSTRAINT `FK9064DDF699A2076B` FOREIGN KEY (`creator`) REFERENCES `sklay_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_cms_config`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_config`;
CREATE TABLE `sklay_cms_config` (
  `biz` varchar(50) NOT NULL,
  `owner` varchar(50) NOT NULL,
  `data` longtext,
  `name` varchar(1000) default NULL,
  `status` int(11) default NULL,
  PRIMARY KEY  (`biz`,`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_config
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_cms_golbal`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_golbal`;
CREATE TABLE `sklay_cms_golbal` (
  `id` bigint(20) NOT NULL auto_increment,
  `css` longtext,
  `jsp` longtext,
  `ver` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_golbal
-- ----------------------------
INSERT INTO `sklay_cms_golbal` VALUES ('1', '#wrap{\n	margin-top: 20px;\n}\n\n.inline {\n	white-space: nowrap;\n}\n\nhtml{\n}\n\nbody {\n	background: transparent;\n	font-family:\"Microsoft YaHei\",Arial,Helvetica,sans-serif,\"宋体\";\n}\n\n\n.breadcrumb{\n	margin-bottom: 0px;\n}\n\n.noborder{\n	padding: 0px !important;\n	border:0px !important;\n	-webkit-box-shadow: none !important;\n          box-shadow:none !important;\n}', '<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"\n	pageEncoding=\"UTF-8\"%>\n<%@ include file=\"include.jsp\"%>\n<c:if test=\"${not empty currentPage.customCss}\">\n	<style>\n		${currentPage.customCss}\n	</style>\n</c:if>\n\n<!-- navbar start -->\n<c:set var=\"navPage\" value=\"${currentPage.parent==null?currentPage:currentPage.parent}\" scope=\"request\"/>\n<div class=\"navbar navbar-static-top\">\n	<div class=\"navbar-inner\">\n		<div class=\"container\">\n			<a data-target=\".nav-collapse\" data-toggle=\"collapse\" class=\"btn btn-navbar\">菜单</a>\n			<a href=\"/\" class=\"brand\">SKLAY</a>\n			<div class=\"nav-collapse\">\n				<ul class=\"nav\">\n					<c:forEach items=\"${pages}\" var=\"p\" varStatus=\"status\">\n						<c:choose>\n							<c:when test=\"${not empty p.children}\">\n								<li class=\"dropdown\">\n									<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">${p.name}<b class=\"caret\"></b></a>\n									<ul class=\"dropdown-menu\">\n										<c:forEach items=\"${p.children}\" var=\"cp\">\n											<li><a href=\"${ctx}/${cp.alias}\">${cp.name}</a></li>\n										</c:forEach>\n									</ul>\n								</li>\n							</c:when>\n							<c:otherwise>\n								<li <c:if test=\"${p.id eq navPage.id}\">class=\"active\"</c:if>>\n									<a href=\"${ctx}/${status.first?\'\':p.alias}\">${p.name}</a>\n								</li>\n							</c:otherwise>\n						</c:choose>\n					</c:forEach>\n					</ul>\n					<ul class=\"nav pull-right\">\n						<shiro:guest>\n				    		<li>						\n								<a class=\"dropdown-toggle\"  href=\"${ctx}/login\">登录</a>\n							</li>\n						</shiro:guest>\n						<shiro:user>\n							<li class=\"dropdown\">						\n								<a data-toggle=\"dropdown\" class=\"dropdown-toggle\" href=\"javascript:void(0);\">\n									<i class=\"icon-user\"></i> \n										<shiro:principal property=\"name\" type=\"com.sklay.model.User\"/>\n									<b class=\"caret\"></b>\n								</a>\n								<ul class=\"dropdown-menu\">\n									<shiro:hasPermission name=\"admin:model\">\n										<li><a class=\"modal-link\" href=\"${ctx}/admin/\">后台管理</a></li>\n										<li class=\"divider\"></li>\n									</shiro:hasPermission>					\n									<li><a href=\"${ctx}/logout\">登出</a></li>\n								</ul>\n							</li>\n						</shiro:user>\n			    	</ul>\n				</div>\n			</div>\n		</div>	\n	</div>\n	<!-- navbar end -->\n\n<div id=\"wrap\" class=\"container\">\n	<div id=\"content\" class=\"row\">\n		<tiles:insertAttribute name=\"content\" />\n	</div>\n	\n	<div id=\"footer\"></div>\n</div>', '0');

-- ----------------------------
-- Table structure for `sklay_cms_news_group`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_news_group`;
CREATE TABLE `sklay_cms_news_group` (
  `id` bigint(20) NOT NULL auto_increment,
  `biz` varchar(255) default NULL,
  `leaf` tinyint(1) default NULL,
  `name` varchar(255) NOT NULL,
  `owner` varchar(255) default NULL,
  `path` varchar(1000) default NULL,
  `pid` bigint(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_news_group
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_cms_page`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_page`;
CREATE TABLE `sklay_cms_page` (
  `id` bigint(20) NOT NULL auto_increment,
  `alias` varchar(50) default NULL,
  `custom_css` longtext,
  `description` varchar(1000) default NULL,
  `keywords` varchar(500) default NULL,
  `layout` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `rank` int(11) default NULL,
  `can_show` tinyint(1) NOT NULL,
  `title` varchar(255) default NULL,
  `version` int(11) NOT NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKC8E27258D7B7AED3` (`parent_id`),
  CONSTRAINT `FKC8E27258D7B7AED3` FOREIGN KEY (`parent_id`) REFERENCES `sklay_cms_page` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_page
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_cms_tag`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_tag`;
CREATE TABLE `sklay_cms_tag` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `used` bigint(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_tag
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_cms_widget`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_widget`;
CREATE TABLE `sklay_cms_widget` (
  `id` bigint(20) NOT NULL auto_increment,
  `border_class` varchar(100) default NULL,
  `borderTpl` varchar(100) NOT NULL,
  `container` varchar(50) default NULL,
  `name` varchar(50) default NULL,
  `page_id` bigint(20) default NULL,
  `rank` int(11) NOT NULL,
  `title` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_widget
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_cms_widget_setting`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_widget_setting`;
CREATE TABLE `sklay_cms_widget_setting` (
  `widget_id` bigint(20) NOT NULL,
  `attr_value` longtext,
  `attr_key` varchar(64) NOT NULL,
  PRIMARY KEY  (`widget_id`,`attr_key`),
  KEY `FKE36133DE7F0DA9AE` (`widget_id`),
  CONSTRAINT `FKE36133DE7F0DA9AE` FOREIGN KEY (`widget_id`) REFERENCES `sklay_cms_widget` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_widget_setting
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_device_binding`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_device_binding`;
CREATE TABLE `sklay_device_binding` (
  `id` bigint(20) NOT NULL auto_increment,
  `create_time` datetime default NULL,
  `level` int(11) default NULL,
  `mold` int(11) default NULL,
  `serial_number` varchar(255) default NULL,
  `status` int(11) default NULL,
  `update_time` datetime default NULL,
  `updator` bigint(20) default NULL,
  `creator` bigint(20) default NULL,
  `tuid` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKE7B7B5EF99A2076B` (`creator`),
  KEY `FKE7B7B5EF5C8A069B` (`tuid`),
  CONSTRAINT `FKE7B7B5EF5C8A069B` FOREIGN KEY (`tuid`) REFERENCES `sklay_user` (`id`),
  CONSTRAINT `FKE7B7B5EF99A2076B` FOREIGN KEY (`creator`) REFERENCES `sklay_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_device_binding
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_global_setting`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_global_setting`;
CREATE TABLE `sklay_global_setting` (
  `id` bigint(20) NOT NULL auto_increment,
  `sms_fetch` int(11) default '0',
  `user_audite` int(11) default '0',
  `visit_count` int(11) default '0',
  `send_sms_time` bigint(20) default NULL,
  `auto_create_group` int(11) default '0',
  `group_audite` int(11) default '0',
  `send_time` bigint(20) default NULL,
  `device_binding_status` int(11) default '0',
  `send_physical` int(11) default NULL,
  `send_end_time` varchar(20) default NULL,
  `send_start_time` varchar(20) default NULL,
  `send_job_switch` int(11) default NULL,
  `send_job_time` bigint(20) default NULL,
  `copy_right` varchar(255) default NULL,
  `web_description` longtext,
  `web_site` varchar(255) default NULL,
  `web_service` longtext,
  `web_author` longtext,
  `binding_count` int(11) default '0',
  `send_sos` int(11) default '0',
  `api_log_switch` int(11) default '0',
  `web_meta` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sklay_global_setting
-- ----------------------------
INSERT INTO `sklay_global_setting` VALUES ('1', '0', '0', '600', null, '1', '0', '3600', '2', '2', '00:00', '20:00', '0', null, 'Copyright © 2013-2016 njztsm.net All Rights Reserved 版权所有，未经授权，不得复制、镜像。苏ICP备13032906号', '南京市梓拓商贸有限公司 ', '南京市梓拓商贸有限公司', '', '1988fuyu@163.com,付玉，57013652@qq.com', '2', '0', '0', '<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\">\r\n');

-- ----------------------------
-- Table structure for `sklay_group`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_group`;
CREATE TABLE `sklay_group` (
  `id` bigint(20) NOT NULL auto_increment,
  `description` longtext,
  `member_count` int(11) default '0',
  `name` varchar(255) NOT NULL,
  `pgid` bigint(20) default NULL,
  `perms` longtext,
  `role` int(11) default NULL,
  `status` int(11) default NULL,
  `owner` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK8F6EC4CC62A779F2` (`owner`),
  CONSTRAINT `FK8F6EC4CC62A779F2` FOREIGN KEY (`owner`) REFERENCES `sklay_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_group
-- ----------------------------
INSERT INTO `sklay_group` VALUES ('1', '超级管理员组', '1', '管理员', null, '[\"group:model\",\"group:list\",\"group:delete\",\"group:detail\",\"group:create\",\"group:update\",\"group:audit\",\"group:author\",\"group:give\",\"sms:model\",\"sms:list\",\"sms:setting\",\"sms:job\",\"sms:resend\",\"statistics:model\",\"statistics:tree\",\"device:model\",\"device:list\",\"device:delete\",\"device:update\",\"device:audit\",\"member:model\",\"member:list\",\"member:delete\",\"member:detail\",\"member:create\",\"member:update\",\"member:audit\",\"member:binding\",\"member:multipleBinding\",\"admin:model\",\"admin:cms\",\"admin:setting\",\"admin:logList\",\"matadata:model\",\"matadata:list\",\"matadata:delete\",\"matadata:matadata\",\"matadata:create\",\"matadata:update\",\"matadata:delete\"]', '2', '1', '1');

-- ----------------------------
-- Table structure for `sklay_init`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_init`;
CREATE TABLE `sklay_init` (
  `id` bigint(20) NOT NULL auto_increment,
  `item` varchar(200) NOT NULL,
  `uid` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK67BA19235C554B8F` (`uid`),
  CONSTRAINT `FK67BA19235C554B8F` FOREIGN KEY (`uid`) REFERENCES `sklay_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_init
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_login_record`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_login_record`;
CREATE TABLE `sklay_login_record` (
  `id` bigint(20) NOT NULL auto_increment,
  `last_login_address` varchar(255) default NULL,
  `last_login_date` datetime default NULL,
  `login_address` varchar(255) default NULL,
  `login_date` datetime default NULL,
  `uid` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK72D9F2FA5C554B8F` (`uid`),
  CONSTRAINT `FK72D9F2FA5C554B8F` FOREIGN KEY (`uid`) REFERENCES `sklay_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_login_record
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_matadata`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_matadata`;
CREATE TABLE `sklay_matadata` (
  `id` bigint(20) NOT NULL auto_increment,
  `sex` int(11) NOT NULL,
  `age_min` int(11) default NULL,
  `age_max` int(11) default NULL,
  `low_min` int(11) default NULL,
  `low_max` int(11) default NULL,
  `high_min` int(11) default NULL,
  `high_max` int(11) default NULL,
  `result` longtext,
  `remark` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_matadata
-- ----------------------------
INSERT INTO `sklay_matadata` VALUES ('1', '1', '36', '40', '51', '59', '70', '79', '您好，本次监测您的血压已达血压最低值，如有不适，建议及时就医。', '您好，本次监测您的血压已达血压最低值，如有不适，建议及时就医.');
INSERT INTO `sklay_matadata` VALUES ('2', '2', '36', '40', '51', '59', '70', '79', '您好，本次监测您的血压已达血压最低值，如有不适，建议及时就医。', '您好，本次监测您的血压已达血压最低值，如有不适，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('3', '1', '36', '40', '51', '59', '80', '89', '您好，本次监测您的血压已达低血压下限，如有不适，建议及时就医。', '您好，本次监测您的血压已达低血压下限，如有不适，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('4', '2', '36', '40', '51', '59', '80', '89', '您好，本次监测您的血压已达低血压低限，如无其它疾病及不适，建议及时体检', '您好，本次监测您的血压已达低血压低限，如无其它疾病及不适，建议及时体检');
INSERT INTO `sklay_matadata` VALUES ('5', '1', '36', '40', '51', '59', '90', '99', '您好，本次监测您的血压已达低血压低限，如无其它疾病及不适，建议及时体检', '您好，本次监测您的血压已达低血压低限，如无其它疾病及不适，建议及时体检');
INSERT INTO `sklay_matadata` VALUES ('6', '2', '36', '40', '51', '59', '90', '99', '您好，本次监测您的血压判定为低血压，请注意休息，如有不适，建议及时体检。', '您好，本次监测您的血压判定为低血压，请注意休息，如有不适，建议及时体检。');
INSERT INTO `sklay_matadata` VALUES ('7', '1', '36', '40', '51', '59', '100', '109', '您好，本次监测您的血压为低血压状态，如有不适，建议及时检查身体并注意饮食和休息。', '您好，本次监测您的血压为低血压状态，如有不适，建议及时检查身体并注意饮食和休息。');
INSERT INTO `sklay_matadata` VALUES ('8', '2', '36', '40', '51', '59', '100', '109', '您好，本次监测您的血压值偏低，压差在正常范围，请定时测量血压，以便及时了解自身身体状况。', '您好，本次监测您的血压值偏低，压差在正常范围，请定时测量血压，以便及时了解自身身体状况。');
INSERT INTO `sklay_matadata` VALUES ('9', '1', '36', '40', '51', '59', '110', '119', '您好，本次监测您的血压值偏低，压差在正常范围，请定时测量血压，以便及时了解自身身体状况。', '您好，本次监测您的血压值偏低，压差在正常范围，请定时测量血压，以便及时了解自身身体状况。');
INSERT INTO `sklay_matadata` VALUES ('10', '2', '36', '40', '51', '59', '110', '119', '您好，本次监测您此时的低压与正常值相较偏低，脉压差较大（﹥40mmHg），请注意生活及作息习惯。', '您好，本次监测您此时的低压与正常值相较偏低，脉压差较大（﹥40mmHg），请注意生活及作息习惯。');
INSERT INTO `sklay_matadata` VALUES ('11', '1', '36', '40', '51', '59', '120', '129', '您好，本次监测您此时的低压与正常值相较偏低，脉压差较大（﹥40mmHg），请注意生活及作息习惯。', '您好，本次监测您此时的低压与正常值相较偏低，脉压差较大（﹥40mmHg），请注意生活及作息习惯。');
INSERT INTO `sklay_matadata` VALUES ('12', '2', '36', '40', '51', '59', '120', '129', '您好，本次监测您此时的血压与正常值舒张压（低压）相较偏低，脉压差过大（﹥40mmHg），如有不适，建议及时体检。', '您好，本次监测您此时的血压与正常值舒张压（低压）相较偏低，脉压差过大（﹥40mmHg），如有不适，建议及时体检。');
INSERT INTO `sklay_matadata` VALUES ('13', '1', '36', '40', '51', '59', '130', '139', '您好，本次监测您此时的血压与正常值舒张压（低压）相较偏低，脉压差过大（﹥40mmHg），如有不适，建议及时体检。', '您好，本次监测您此时的血压与正常值舒张压（低压）相较偏低，脉压差过大（﹥40mmHg），如有不适，建议及时体检。');
INSERT INTO `sklay_matadata` VALUES ('14', '2', '36', '40', '51', '59', '130', '139', '您好，本次监测您此时的血压与正常值舒张压（低压）相较偏低，脉压差过大（﹥40mmHg），请及时咨询医生，并谨遵医嘱。', '您好，本次监测您此时的血压与正常值舒张压（低压）相较偏低，脉压差过大（﹥40mmHg），请及时咨询医生，并谨遵医嘱。');
INSERT INTO `sklay_matadata` VALUES ('15', '1', '36', '40', '51', '59', '140', '229', '您好，本次监测您的血压显示压差太大，请及时就医！', '您好，本次监测您的血压显示压差太大，请及时就医！');
INSERT INTO `sklay_matadata` VALUES ('16', '2', '36', '40', '51', '59', '140', '229', '您好，本次监测您的血压显示压差太大，请及时就医！', '您好，本次监测您的血压显示压差太大，请及时就医！');
INSERT INTO `sklay_matadata` VALUES ('17', '1', '36', '40', '60', '69', '70', '79', '您好，本次监测您的血压显示脉压差间过小（﹤40mmHg），身体如有不适，建议及时咨询医生、祝您健康。', '您好，本次监测您的血压显示脉压差间过小（﹤40mmHg），身体如有不适，建议及时咨询医生、祝您健康。');
INSERT INTO `sklay_matadata` VALUES ('18', '2', '36', '40', '60', '69', '70', '79', '您好，本次监测您的血压判定低血压偏低，如有不适，建议定时体检，合理饮食，科学养生。', '您好，本次监测您的血压判定低血压偏低，如有不适，建议定时体检，合理饮食，科学养生。');
INSERT INTO `sklay_matadata` VALUES ('19', '1', '36', '40', '60', '69', '80', '89', '您的血压判定低血压偏低，如有不适，建议定时体检，合理饮食，科学养生。', '您的血压判定低血压偏低，如有不适，建议定时体检，合理饮食，科学养生。');
INSERT INTO `sklay_matadata` VALUES ('20', '2', '36', '40', '60', '69', '80', '89', '您好，本次监测您的血压低血压已达临界值，建议定时体检，身体如有不适，请及时咨询医生，合理饮食，科学养生。', '您好，本次监测您的血压低血压已达临界值，建议定时体检，身体如有不适，请及时咨询医生，合理饮食，科学养生。');
INSERT INTO `sklay_matadata` VALUES ('21', '1', '36', '40', '60', '69', '90', '99', '您好，本次监测您的血压低血压已达临界值，建议定时体检，身体如有不适，请及时咨询医生，合理饮食，科学养生。', '您好，本次监测您的血压低血压已达临界值，建议定时体检，身体如有不适，请及时咨询医生，合理饮食，科学养生。');
INSERT INTO `sklay_matadata` VALUES ('22', '2', '36', '40', '60', '69', '90', '99', '您好，本次监测您的血压判定为低血压，请注意生活习惯及饮食营养，祝您健康', '您好，本次监测您的血压判定为低血压，请注意生活习惯及饮食营养，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('23', '1', '36', '40', '60', '69', '100', '109', '您好，本次监测您的血压判定为低血压，请注意生活习惯及饮食营养，祝您健康', '您好，本次监测您的血压判定为低血压，请注意生活习惯及饮食营养，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('24', '2', '36', '40', '60', '69', '100', '109', '您好，本次监测您的血压值，对比正常血压低压偏低，为低血压状态，压差稍大，请注意营养摄取，祝您健康', '您好，本次监测您的血压值，对比正常血压低压偏低，为低血压状态，压差稍大，请注意营养摄取，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('25', '1', '36', '40', '60', '69', '110', '119', '您好，本次监测您的血压值，对比正常血压低压偏低，为低血压状态，压差稍大，请注意营养摄取，祝您健康', '您好，本次监测您的血压值，对比正常血压低压偏低，为低血压状态，压差稍大，请注意营养摄取，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('26', '2', '36', '40', '60', '69', '110', '119', '您好，本次监测您的血压基本符合政常范围，请定时测量血压，如有不适，建议及时体检', '您好，本次监测您的血压基本符合政常范围，请定时测量血压，如有不适，建议及时体检');
INSERT INTO `sklay_matadata` VALUES ('27', '1', '36', '40', '60', '69', '120', '129', '您好，本次监测您的血压基本符合正常范围，请定时测量血压，如有不适，建议及时体检', '您好，本次监测您的血压基本符合正常范围，请定时测量血压，如有不适，建议及时体检');
INSERT INTO `sklay_matadata` VALUES ('28', '2', '36', '40', '60', '69', '120', '129', '您好，本次监测您的血压稍微有点偏差，请定时测量血压，如有不适，并伴有其它疾病，建议及时就医。', '您好，本次监测您的血压稍微有点偏差，请定时测量血压，如有不适，并伴有其它疾病，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('29', '1', '36', '40', '60', '69', '130', '139', '您好，本次监测您的血压显示压差较大，请定时测量血压，如有不适，并伴有其它疾病，建议及时就医。', '您好，本次监测您的血压显示压差较大，请定时测量血压，如有不适，并伴有其它疾病，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('30', '2', '36', '40', '60', '69', '130', '139', '您好，本次监测您的血压显示压差过大，请定时测量血压，如有明显不适及有其它疾病，建议及时就医。', '您好，本次监测您的血压显示压差过大，请定时测量血压，如有明显不适及有其它疾病，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('31', '1', '36', '40', '60', '69', '140', '149', '您好，本次监测您的血压显示压差过大，临近高血压状态，请定时测量血压，如有明显不适及有其它疾病，建议及时就医。', '您好，本次监测您的血压显示压差过大，临近高血压状态，请定时测量血压，如有明显不适及有其它疾病，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('32', '2', '36', '40', '60', '69', '140', '149', '您好，本次监测您的血压显示压差过大，属于高血压的范围，建议及时就医。', '您好，本次监测您的血压显示压差过大，属于高血压的范围，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('33', '1', '36', '40', '60', '69', '150', '229', '您好，本次监测您的血压显示压差过大，建议及时就医。', '您好，本次监测您的血压显示压差过大，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('34', '2', '36', '40', '60', '69', '150', '229', '您好，本次监测您的血压显示压差过大，建议及时就医。', '您好，本次监测您的血压显示压差过大，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('35', '1', '36', '40', '70', '79', '80', '89', '您好，本次监测您的血压压力差间小，不排除低血压可能性，建议营养饮食，科学锻炼，如有头晕目眩等不适，咨询医生', '您好，本次监测您的血压压力差间小，不排除低血压可能性，建议营养饮食，科学锻炼，如有头晕目眩等不适，咨询医生');
INSERT INTO `sklay_matadata` VALUES ('36', '2', '36', '40', '70', '79', '80', '89', '您好，本次监测您的血压压力差间小，不排除低血压可能性，建议营养饮食，科学锻炼，如有头晕目眩等不适，咨询医生', '您好，本次监测您的血压压力差间小，不排除低血压可能性，建议营养饮食，科学锻炼，如有头晕目眩等不适，咨询医生');
INSERT INTO `sklay_matadata` VALUES ('37', '1', '36', '40', '70', '79', '90', '99', '您好，本次监测您的血压显示高压较低，血压差间小，建议增强运动锻炼，适量食盐及肉类，如有不适，请咨询医生', '您好，本次监测您的血压显示高压较低，血压差间小，建议增强运动锻炼，适量食盐及肉类，如有不适，请咨询医生');
INSERT INTO `sklay_matadata` VALUES ('38', '2', '36', '40', '70', '79', '90', '99', '您好，本次监测您的血压显示高压偏低，建议增强运动，多锻炼，适量食盐及肉类，如有不适，请咨询医生', '您好，本次监测您的血压显示高压偏低，建议增强运动，多锻炼，适量食盐及肉类，如有不适，请咨询医生');
INSERT INTO `sklay_matadata` VALUES ('39', '1', '36', '40', '70', '79', '100', '109', '您好，本次监测您的血压显示高压偏低，建议增强运动，多锻炼，适量食盐及肉类，如有不适，请咨询医生', '您好，本次监测您的血压显示高压偏低，建议增强运动，多锻炼，适量食盐及肉类，如有不适，请咨询医生');
INSERT INTO `sklay_matadata` VALUES ('40', '2', '36', '40', '70', '79', '100', '109', '您好，本次监测您的血压值在正常血压范围，建议定时测量血压，请注意营养摄取，科学运动，祝您健康', '您好，本次监测您的血压值在正常血压范围，建议定时测量血压，请注意营养摄取，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('41', '1', '36', '40', '70', '79', '110', '119', '您好，本次监测您的血压为理想值范围，好的生活习惯会有好的身体，祝您健康快乐！', '您好，本次监测您的血压为理想值范围，好的生活习惯会有好的身体，祝您健康快乐！');
INSERT INTO `sklay_matadata` VALUES ('42', '2', '36', '40', '70', '79', '110', '119', '您好，本次监测您的血压为理想血压值，祝您享受生活、健康快乐！', '您好，本次监测您的血压为理想血压值，祝您享受生活、健康快乐！');
INSERT INTO `sklay_matadata` VALUES ('43', '1', '36', '40', '70', '79', '120', '129', '您好，本次监测您的血压值在正常血压范围，建议定时测量血压，请注意营养摄取，科学运动，祝您健康', '您好，本次监测您的血压值在正常血压范围，建议定时测量血压，请注意营养摄取，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('44', '2', '36', '40', '70', '79', '120', '129', '您好，本次监测您的血压为理想值范围，好的生活习惯会有好的身体，祝您健康快乐！', '您好，本次监测您的血压为理想值范围，好的生活习惯会有好的身体，祝您健康快乐！');
INSERT INTO `sklay_matadata` VALUES ('45', '1', '36', '40', '70', '79', '130', '139', '您好，本次监测您的血压为正常值范围，注意压差并定时测量血压，祝您健康快乐！', '您好，本次监测您的血压为正常值范围，注意压差并定时测量血压，祝您健康快乐！');
INSERT INTO `sklay_matadata` VALUES ('46', '2', '36', '40', '70', '79', '130', '139', '您好，本次监测您的血压压差间大，有血压偏高的可能，建议定时测量血压，请注意休息及营养摄取，科学运动，祝您健康', '您好，本次监测您的血压压差间大，有血压偏高的可能，建议定时测量血压，请注意休息及营养摄取，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('47', '1', '36', '40', '70', '79', '140', '149', '您好，本次监测您的血压高压为轻度高血压临界值，注意压差并定时测量血压，合理饮食，科学运动，祝您健康', '您好，本次监测您的血压高压为轻度高血压临界值，注意压差并定时测量血压，合理饮食，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('48', '2', '36', '40', '70', '79', '140', '149', '您好，本次监测您的血压高压为轻度高血压临界值，注意压差并定时测量血压，合理饮食，科学运动，祝您健康', '您好，本次监测您的血压高压为轻度高血压临界值，注意压差并定时测量血压，合理饮食，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('49', '1', '36', '40', '70', '79', '150', '159', '您好，本次监测您的高压高压为中度高血压临界值，注意压差并定时测量血压，合理饮食，科学运动，祝您健康', '您好，本次监测您的高压高压为中度高血压临界值，注意压差并定时测量血压，合理饮食，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('50', '2', '36', '40', '70', '79', '150', '159', '您好，本次监测您的高压高压为中度高血压临界值，注意压差并定时测量血压，合理饮食，科学运动，祝您健康', '您好，本次监测您的高压高压为中度高血压临界值，注意压差并定时测量血压，合理饮食，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('51', '1', '36', '40', '70', '79', '160', '169', '您好，本次监测你的高压已达重度高血压临界值，低盐饮食，低脂肪饮食。戒烟限酒，并在医生指导下降压。', '您好，本次监测你的高压已达重度高血压临界值，低盐饮食，低脂肪饮食。戒烟限酒，并在医生指导下降压。');
INSERT INTO `sklay_matadata` VALUES ('52', '2', '36', '40', '70', '79', '160', '169', '您好，本次监测你的高压已达重度高血压临界值，低盐饮食，低脂肪饮食。戒烟限酒，并在医生指导下降压。', '您好，本次监测你的高压已达重度高血压临界值，低盐饮食，低脂肪饮食。戒烟限酒，并在医生指导下降压。');
INSERT INTO `sklay_matadata` VALUES ('53', '1', '36', '40', '70', '79', '170', '229', '您好，本次监测您的血压值在极高危血压范围,如有不适，请及时就医。', '您好，本次监测您的血压值在极高危血压范围,如有不适，请及时就医。');
INSERT INTO `sklay_matadata` VALUES ('54', '2', '36', '40', '70', '79', '170', '229', '您好，本次监测您的血压值在极高危血压范围,如有不适，请及时就医。', '您好，本次监测您的血压值在极高危血压范围,如有不适，请及时就医。');
INSERT INTO `sklay_matadata` VALUES ('55', '1', '36', '40', '80', '85', '90', '99', '您好，本次监测您的血压显示高压较低,压差间小，如有不适，建议及时就医。', '您好，本次监测您的血压显示高压较低,压差间小，如有不适，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('56', '2', '36', '40', '80', '85', '90', '99', '您好，本次监测您的血压显示高压较低,压差间小，如有不适，建议及时就医。', '您好，本次监测您的血压显示高压较低,压差间小，如有不适，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('57', '1', '36', '40', '80', '85', '100', '109', '您好，本次监测您的血压值在正常血压范围，压差间小，建议定时测量血压，请注意营养摄取，科学运动，祝您健康', '您好，本次监测您的血压值在正常血压范围，压差间小，建议定时测量血压，请注意营养摄取，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('58', '2', '36', '40', '80', '85', '100', '109', '您好，本次监测您的血压值在正常血压范围，压差间小，建议定时测量血压，请注意营养摄取，科学运动，祝您健康', '您好，本次监测您的血压值在正常血压范围，压差间小，建议定时测量血压，请注意营养摄取，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('59', '1', '36', '40', '80', '85', '110', '119', '您好，本次监测您的血压值在正常血压范围，建议定时测量血压，请注意营养摄取，科学运动，祝您健康', '您好，本次监测您的血压值在正常血压范围，建议定时测量血压，请注意营养摄取，科学运动，祝您健康');
INSERT INTO `sklay_matadata` VALUES ('60', '2', '36', '40', '80', '85', '110', '119', '您好，本次监测您的血压为正常值范围，好的生活习惯会有好的身体，祝您健康快乐！', '您好，本次监测您的血压为正常值范围，好的生活习惯会有好的身体，祝您健康快乐！');
INSERT INTO `sklay_matadata` VALUES ('61', '1', '36', '40', '80', '85', '120', '129', '您好，本次监测您的血压为正常血压值，祝您享受生活、健康快乐！', '您好，本次监测您的血压为正常血压值，祝您享受生活、健康快乐！');
INSERT INTO `sklay_matadata` VALUES ('62', '2', '36', '40', '80', '85', '120', '129', '您好，本次监测您的血压为正常血压值，祝您享受生活、健康快乐！', '您好，本次监测您的血压为正常血压值，祝您享受生活、健康快乐！');
INSERT INTO `sklay_matadata` VALUES ('63', '1', '36', '40', '80', '85', '130', '139', '您好，本次监测您的血压值在正常血压范围，高压稍高，建议定时测量血压，注意多休息，适当运动，多吃蔬菜水果，此血压数值还是可以恢复正常的。', '您好，本次监测您的血压值在正常血压范围，高压稍高，建议定时测量血压，注意多休息，适当运动，多吃蔬菜水果，此血压数值还是可以恢复正常的。');
INSERT INTO `sklay_matadata` VALUES ('64', '2', '36', '40', '80', '85', '130', '139', '您好，本次监测您的血压值在正常血压范围，高压偏高，建议定时测量血压，适当运动锻炼，多吃蔬菜水果，如有不适，及时咨询医生', '您好，本次监测您的血压值在正常血压范围，高压偏高，建议定时测量血压，适当运动锻炼，多吃蔬菜水果，如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('65', '1', '36', '40', '80', '85', '140', '149', '您好，本次监测您的血压值高压处于临界上限，建议定时测量血压，养成健康生活方式，多可使血压恢复正常：低盐、低脂、控制体重、适当运动、保持心理平衡。如有不适，及时咨询医生', '您好，本次监测您的血压值高压处于临界上限，建议定时测量血压，养成健康生活方式，多可使血压恢复正常：低盐、低脂、控制体重、适当运动、保持心理平衡。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('66', '2', '36', '40', '80', '85', '140', '149', '您好，本次监测您的血压值显示压差间较大，高压处于临界上限，建议定时测量血压，养成健康生活方式，多可使血压恢复正常，饮食注意低盐，适当运动，保持愉快的心态。', '您好，本次监测您的血压值显示压差间较大，高压处于临界上限，建议定时测量血压，养成健康生活方式，多可使血压恢复正常，饮食注意低盐，适当运动，保持愉快的心态。');
INSERT INTO `sklay_matadata` VALUES ('67', '1', '36', '40', '80', '85', '150', '159', '您好，本次监测您的血压值显示为高血压，合理降压，养成好的生活习惯，饮食清淡，少盐，多运动，少吃油脂高的食物，多吃新鲜蔬菜水果。尽量戒烟限酒。', '您好，本次监测您的血压值显示为高血压，合理降压，养成好的生活习惯，饮食清淡，少盐，多运动，少吃油脂高的食物，多吃新鲜蔬菜水果。尽量戒烟限酒。');
INSERT INTO `sklay_matadata` VALUES ('68', '2', '36', '40', '80', '85', '150', '159', '您好，本次监测您的血压值显示为高血压,可合理降压，注意生活习惯：低盐饮食，多食蔬菜水果，忌食生冷刺激性食物，合理作息，保持一个良好的心态。', '您好，本次监测您的血压值显示为高血压,可合理降压，注意生活习惯：低盐饮食，多食蔬菜水果，忌食生冷刺激性食物，合理作息，保持一个良好的心态。');
INSERT INTO `sklay_matadata` VALUES ('69', '1', '36', '40', '80', '85', '160', '169', '您好，本次监测您当前的血压显示低压正常，高压较高，压差大，为高血压状态，如有不适，建议及时就医。', '您好，本次监测您当前的血压显示低压正常，高压较高，压差大，为高血压状态，如有不适，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('70', '2', '36', '40', '80', '85', '160', '169', '您好，本次监测您当前的血压显示低压正常，高压较高，压差大，为高血压状态，如有不适，建议及时就医。', '您好，本次监测您当前的血压显示低压正常，高压较高，压差大，为高血压状态，如有不适，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('71', '1', '36', '40', '80', '85', '170', '229', '您好，本次监测您当前的血压显示压差太大，为高血压状态，如有不适，建议及时就医。', '您好，本次监测您当前的血压显示压差太大，为高血压状态，如有不适，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('72', '2', '36', '40', '80', '85', '170', '229', '您好，本次监测您当前的血压显示压差太大，为高血压状态，如有不适，建议及时就医。', '您好，本次监测您当前的血压显示压差太大，为高血压状态，如有不适，建议及时就医。');
INSERT INTO `sklay_matadata` VALUES ('73', '1', '36', '40', '85', '90', '100', '109', '您好，本次监测您的血压显示压差较小，如有不适，可咨询医生，日常保养可应用补气、补血的方法提高高压；科学锻炼，合理饮食，祝您健康。', '您好，本次监测您的血压显示压差较小，如有不适，可咨询医生，日常保养可应用补气、补血的方法提高高压；科学锻炼，合理饮食，祝您健康。');
INSERT INTO `sklay_matadata` VALUES ('74', '2', '36', '40', '85', '90', '100', '109', '您好，本次监测您的血压显示为正常血压范围，压差间小，注意劳逸结合,保持足够的睡眠，合理的劳动和锻炼.注意饮食调节.肥胖者适当控制食量和总热量,适当减轻体重,避免情绪激动。', '您好，本次监测您的血压显示为正常血压范围，压差间小，注意劳逸结合,保持足够的睡眠，合理的劳动和锻炼.注意饮食调节.肥胖者适当控制食量和总热量,适当减轻体重,避免情绪激动。');
INSERT INTO `sklay_matadata` VALUES ('75', '1', '36', '40', '85', '90', '110', '119', '您好，本次监测您的血压测量结果是正常的，请定时测量血压，保持健康。合理饮食，适当的运动，多吃蔬菜水果。', '您好，本次监测您的血压测量结果是正常的，请定时测量血压，保持健康。合理饮食，适当的运动，多吃蔬菜水果。');
INSERT INTO `sklay_matadata` VALUES ('76', '2', '36', '40', '85', '90', '110', '119', '您好，本次监测您的血压测量结果是正常的，请定时测量血压，保持健康。合理饮食，适当的运动，多吃蔬菜水果。', '您好，本次监测您的血压测量结果是正常的，请定时测量血压，保持健康。合理饮食，适当的运动，多吃蔬菜水果。');
INSERT INTO `sklay_matadata` VALUES ('77', '1', '36', '40', '85', '90', '120', '129', '您好，本次监测您的血压显示为正常血压上限范围，注意戒烟限酒，劳逸结合,保持足够的睡眠，合理的劳动和锻炼.注意饮食调节,适当调整心情,避免情绪激动。', '您好，本次监测您的血压显示为正常血压上限范围，注意戒烟限酒，劳逸结合,保持足够的睡眠，合理的劳动和锻炼.注意饮食调节,适当调整心情,避免情绪激动。');
INSERT INTO `sklay_matadata` VALUES ('78', '2', '36', '40', '85', '90', '120', '129', '您好，本次监测您的血压显示为正常血压上限范围，注意饮食调节，劳逸结合,保持足够的睡眠，合理的身体锻炼.。适当调整心情,避免情绪激动。', '您好，本次监测您的血压显示为正常血压上限范围，注意饮食调节，劳逸结合,保持足够的睡眠，合理的身体锻炼.。适当调整心情,避免情绪激动。');
INSERT INTO `sklay_matadata` VALUES ('79', '1', '36', '40', '85', '90', '130', '139', '您好，本次监测您的血压属于正常血压高临界值，建议定时测量血压，养成健康生活方式，多可使血压恢复正常：低盐、低脂、控制体重、适当运动、保持心理平衡。如有不适，及时咨询医生', '您好，本次监测您的血压属于正常血压高临界值，建议定时测量血压，养成健康生活方式，多可使血压恢复正常：低盐、低脂、控制体重、适当运动、保持心理平衡。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('80', '2', '36', '40', '85', '90', '130', '139', '您好，本次监测您的血压显示为单纯收缩压期高血压，建议定时测量血压，养成健康生活方式，低盐、低脂、控制体重、适当运动、保持心理平衡。如有不适，及时咨询医生', '您好，本次监测您的血压显示为单纯收缩压期高血压，建议定时测量血压，养成健康生活方式，低盐、低脂、控制体重、适当运动、保持心理平衡。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('81', '1', '36', '40', '85', '90', '140', '149', '您好，本次监测您的血压显示为单纯收缩压期高血压，建议定时测量血压，养成健康生活方式，低盐、低脂、控制体重、适当运动、保持心理平衡。如有不适，及时咨询医生', '您好，本次监测您的血压显示为单纯收缩压期高血压，建议定时测量血压，养成健康生活方式，低盐、低脂、控制体重、适当运动、保持心理平衡。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('82', '2', '36', '40', '85', '90', '140', '149', '您的血压显示为临界收缩压期高血压，议定时测量血压，养成健康生活方式，饮食注意低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生', '您的血压显示为临界收缩压期高血压，议定时测量血压，养成健康生活方式，饮食注意低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('83', '1', '36', '40', '85', '90', '150', '159', '您好，本次监测您的血压显示为临界收缩压期高血压，议定时测量血压，饮食注意低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生', '您好，本次监测您的血压显示为临界收缩压期高血压，议定时测量血压，饮食注意低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('84', '2', '36', '40', '85', '90', '150', '159', '您好，本次监测您的血压值显示压差间较大，高压处于轻度上限，建议定时测量血压，饮食注意低盐，适当运动，保持愉快的心态。如有不适，请及时就医。', '您好，本次监测您的血压值显示压差间较大，高压处于轻度上限，建议定时测量血压，饮食注意低盐，适当运动，保持愉快的心态。如有不适，请及时就医。');
INSERT INTO `sklay_matadata` VALUES ('85', '1', '36', '40', '85', '90', '160', '169', '您好，本次监测您的血压值显示压差间较大，高压处于轻度上限，建议定时测量血压，饮食注意低盐，适当运动，保持愉快的心态。如有不适，请及时就医。', '您好，本次监测您的血压值显示压差间较大，高压处于轻度上限，建议定时测量血压，饮食注意低盐，适当运动，保持愉快的心态。如有不适，请及时就医。');
INSERT INTO `sklay_matadata` VALUES ('86', '2', '36', '40', '85', '90', '160', '169', '您好，本次监测您的血压显示高血压状态，高压处于中度上限，建议定时测量血压，注意饮食低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生', '您好，本次监测您的血压显示高血压状态，高压处于中度上限，建议定时测量血压，注意饮食低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('87', '1', '36', '40', '85', '90', '170', '179', '您好，本次监测您的血压显示为高血压状态，高压处于中度上限，建议定时测量血压，注意饮食低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生', '您好，本次监测您的血压显示为高血压状态，高压处于中度上限，建议定时测量血压，注意饮食低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('88', '2', '36', '40', '85', '90', '170', '179', '您好，本次监测您的血压显示为高血压状态，高压处于重度上限，建议定时测量血压，注意饮食低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生', '您好，本次监测您的血压显示为高血压状态，高压处于重度上限，建议定时测量血压，注意饮食低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('89', '1', '36', '40', '85', '90', '180', '189', '您好，本次监测您的血压显示高血压，高压处于重度上限，建议定时测量血压，注意饮食低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生', '您好，本次监测您的血压显示高血压，高压处于重度上限，建议定时测量血压，注意饮食低盐，适当运动，保持愉快的心态。如有不适，及时咨询医生');
INSERT INTO `sklay_matadata` VALUES ('90', '2', '36', '40', '85', '90', '180', '189', '您好，本次监测您的血压显示属于高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。', '您好，本次监测您的血压显示属于高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。');
INSERT INTO `sklay_matadata` VALUES ('91', '1', '36', '40', '85', '90', '190', '229', '您好，本次监测您的血压显示属于高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。', '您好，本次监测您的血压显示属于高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。');
INSERT INTO `sklay_matadata` VALUES ('92', '2', '36', '40', '85', '90', '190', '229', '您好，本次监测您的血压显示属于高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。', '您好，本次监测您的血压显示属于高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。');
INSERT INTO `sklay_matadata` VALUES ('93', '1', '36', '40', '91', '99', '110', '119', '您好，本次监测您的血压显示低压稍高，高压处于正常范围内，日常生活请注意戒烟限酒，合理运动，饮食少盐少油，少食富含脂肪食物，多吃水果蔬菜。', '您好，本次监测您的血压显示低压稍高，高压处于正常范围内，日常生活请注意戒烟限酒，合理运动，饮食少盐少油，少食富含脂肪食物，多吃水果蔬菜。');
INSERT INTO `sklay_matadata` VALUES ('94', '2', '36', '40', '91', '99', '110', '119', '您好，本次监测您的血压显示低压稍高，请保持足够的睡眠,合理的体力劳动和体育锻炼。注意饮食调节,以低盐,低动物脂肪饮食为宜,并避免进食富含胆固醇的食物。', '您好，本次监测您的血压显示低压稍高，请保持足够的睡眠,合理的体力劳动和体育锻炼。注意饮食调节,以低盐,低动物脂肪饮食为宜,并避免进食富含胆固醇的食物。');
INSERT INTO `sklay_matadata` VALUES ('95', '1', '36', '40', '91', '99', '120', '129', '您好，本次监测您的血压显示低压稍高，请注意戒烟限酒，进行合理的体力劳动和体育锻炼。注意饮食调节,以低盐,低动物脂肪饮食为宜,并避免进食富含胆固醇的食物. ', '您好，本次监测您的血压显示低压稍高，请注意戒烟限酒，进行合理的体力劳动和体育锻炼。注意饮食调节,以低盐,低动物脂肪饮食为宜,并避免进食富含胆固醇的食物. ');
INSERT INTO `sklay_matadata` VALUES ('96', '2', '36', '40', '91', '99', '120', '129', '您好，本次监测您的血压处于轻度一级的高血压低限，如无不适症状，建议非药物治疗，合理控制生活方式，清淡饮食，少盐少油，科学运动。建议咨询医生，及早控制。', '您好，本次监测您的血压处于轻度一级的高血压低限，如无不适症状，建议非药物治疗，合理控制生活方式，清淡饮食，少盐少油，科学运动。建议咨询医生，及早控制。');
INSERT INTO `sklay_matadata` VALUES ('97', '1', '36', '40', '91', '99', '130', '139', '您好，本次监测您的血压处于轻度一级的高血压低限，请定时测量血压，如无不适症状，建议非药物治疗，合理控制生活方式，戒烟限酒，清淡饮食，少盐少油，科学运动。', '您好，本次监测您的血压处于轻度一级的高血压低限，请定时测量血压，如无不适症状，建议非药物治疗，合理控制生活方式，戒烟限酒，清淡饮食，少盐少油，科学运动。');
INSERT INTO `sklay_matadata` VALUES ('98', '2', '36', '40', '91', '99', '130', '139', '您好，本次监测您的血压显示为轻度一级高血压，建议饮食上要求低补、清淡的原则，保持均衡的饮食，多食高纤维食物，减少进补、甜食和高胆固醇饮食，祝健康。', '您好，本次监测您的血压显示为轻度一级高血压，建议饮食上要求低补、清淡的原则，保持均衡的饮食，多食高纤维食物，减少进补、甜食和高胆固醇饮食，祝健康。');
INSERT INTO `sklay_matadata` VALUES ('99', '1', '36', '40', '91', '99', '140', '149', '您好，本次监测您的血压值为亚组临界高血压，请注意定时测量，戒烟限酒，饮食上要求低补、清淡的原则，保持均衡的饮食，多食高纤维食物，减少进补、甜食和高胆固醇饮食，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('100', '2', '36', '40', '91', '99', '140', '149', '您好，本次监测您的血压值为亚组临界高血压，建议低盐饮食,低脂肪饮食，请咨询医生，在医生指导下选择合理降压，多吃些果蔬。祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('101', '1', '36', '40', '91', '99', '150', '159', '您好，本次监测您的血压显示为轻度高血压高限值，如有不适，请及时就医，注意定时测量，戒烟限酒，饮食上要求低补、清淡的原则，保持均衡的饮食，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('102', '2', '36', '40', '91', '99', '150', '159', '您好，本次监测您的血压显示为中度高血压，如有不适，请及时就医，注意定时测量，饮食上要求低补、清淡的原则，保持均衡的饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('103', '1', '36', '40', '91', '99', '160', '169', '您好，本次监测您的血压显示为中度高血压，如有不适，请及时就医，注意定时测量，戒烟限酒，饮食上要求低补、清淡的原则，保持均衡的饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('104', '2', '36', '40', '91', '99', '160', '169', '您好，本次监测您的血压显示为高度高血压，如有不适，请及时就医，注意定时测量血压，改变自己不良生活习惯，合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('105', '1', '36', '40', '91', '99', '170', '179', '您好，本次监测您的血压显示为高度高血压，如有不适，请及时就医，注意定时测量血压，改变自己不良生活习惯，戒烟限酒，合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('106', '2', '36', '40', '91', '99', '170', '179', '您好，本次监测您的血压显示为高危高血压，高压处于高危上限，建议定时测量血压。如有不适，及时就医。', '');
INSERT INTO `sklay_matadata` VALUES ('107', '1', '36', '40', '91', '99', '180', '189', '您的血压显示为高危高血压，高压处于高危上限，建议定时测量血压。如有不适，及时就医。', '');
INSERT INTO `sklay_matadata` VALUES ('108', '2', '36', '40', '91', '99', '180', '189', '您的血压显示为极危高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。', '');
INSERT INTO `sklay_matadata` VALUES ('109', '1', '36', '40', '91', '99', '190', '229', '您好，本次监测您的血压显示为极危高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。', '');
INSERT INTO `sklay_matadata` VALUES ('110', '2', '36', '40', '91', '99', '190', '229', '您好，本次监测您的血压显示为极危高血压，高压处于极危上限，建议定时测量血压。如有不适，及时就医。', '');
INSERT INTO `sklay_matadata` VALUES ('111', '1', '36', '40', '100', '109', '115', '119', '您好，本次监测您的血压显示值判定为中度二级高血压，低压较高，压差间小，如有不适，请及时体检，咨询医生。戒烟限酒，饮食上低补、清淡的原则，保持均衡的饮食并遵医嘱合理降压。', '');
INSERT INTO `sklay_matadata` VALUES ('112', '2', '36', '40', '100', '109', '115', '119', '您好，本次监测您的血压显示值为中度二级高血压，低压较高，压差间小，请保持足够睡眠,合理的劳动和锻炼。注意饮食调节,以低盐,低动物脂肪饮食为宜,并避免进食富含胆固醇的食物', '');
INSERT INTO `sklay_matadata` VALUES ('113', '1', '36', '40', '100', '109', '120', '129', '您好，本次监测您的血压显示值为中度二级高血压，低压稍高，压差间小，请注意戒烟限酒，合理的劳动和锻炼。注意饮食调节,以低盐,低动物脂肪饮食为宜,并避免进食富含胆固醇的食物.', '');
INSERT INTO `sklay_matadata` VALUES ('114', '2', '36', '40', '100', '109', '120', '129', '您好，本次监测您的血压显示值为中度二级高血压，低压稍高，压差间小，如有不适，请及时体检，咨询医生。合理控制生活方式，清淡饮食，少盐少油，科学运动，及早控制。', '');
INSERT INTO `sklay_matadata` VALUES ('115', '1', '36', '40', '100', '109', '130', '139', '您好，本次监测您的血压显示值为中度二级高血压，如有不适，请及时体检，咨询医生。合理控制生活方式，戒烟限酒，清淡饮食，少盐少油，科学运动，及早控制。祝健康。', '');
INSERT INTO `sklay_matadata` VALUES ('116', '2', '36', '40', '100', '109', '130', '139', '您好，本次监测您的血压显示值为中度二级高血压，建议改变自己不良生活习惯和嗜好，饮食上要求低补、清淡的原则，保持均衡的饮食，多食高纤维食物，减少进补、甜食和高胆固醇饮食，祝健康。', '');
INSERT INTO `sklay_matadata` VALUES ('117', '1', '36', '40', '100', '109', '140', '149', '您好，本次监测您的血压显示值为中度二级高血压，如有不适，请及时咨询医生。定时测量，戒烟限酒，饮食上低补、清淡的原则，保持均衡的饮食，多食高纤维食物，减少进补、甜食和高胆固醇饮食，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('118', '2', '36', '40', '100', '109', '140', '149', '您好，本次监测您的血压显示值为中度二级高血压，如有不适，请及时咨询医生。建议低盐饮食,低脂肪饮食，请咨询医生，在医生指导下选择合理降压，多吃些果蔬。祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('119', '1', '36', '40', '100', '109', '150', '159', '您好，本次监测您的血压显示值为中度二级高血压，如有不适，请及时就医。注意定时测量血压，改变自己不良生活习惯和嗜好，戒烟限酒，饮食上要求低补、清淡的原则，保持均衡的饮食，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('120', '2', '36', '40', '100', '109', '150', '159', '您好，本次监测您的血压显示值为中度二级高血压，如有不适，请及时就医。注意定时测量血压，改变自己不良生活习惯，饮食上要求低补、清淡的原则，保持均衡的饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('121', '1', '36', '40', '100', '109', '160', '169', '您好，本次监测您的血压显示值为中度二级高血压，如有不适，请及时就医。注意定时测量血压，改变自己不良生活习惯，合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('122', '2', '36', '40', '100', '109', '160', '169', '您好，本次监测您的血压显示值为中度二级高血压，如有不适，请及时就医。注意定时测量血压，改变自己不良生活习惯，合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('123', '1', '36', '40', '100', '109', '170', '179', '您的血压显示值判定为重度高血压，如有不适，请及时就医。注意定时测量血压，改变自己不良生活习惯，合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('124', '2', '36', '40', '100', '109', '170', '179', '您好，本次监测您的血压显示值为重度高血压，如有不适，请及时就医。注意定时测量血压，改变自己不良生活习惯，合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('125', '1', '36', '40', '100', '109', '180', '189', '您好，本次监测您的血压显示值为重度高危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('126', '2', '36', '40', '100', '109', '180', '189', '您好，本次监测您的血压显示值为重度高危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('127', '1', '36', '40', '100', '109', '190', '229', '您好，本次监测您的血压显示值为重度极危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('128', '2', '36', '40', '100', '109', '190', '229', '您好，本次监测您的血压显示值为重度极危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('129', '1', '36', '40', '110', '119', '125', '129', '您好，本次监测您的血压显示值为高三级高血压，低压稍高，压差间小，如有不适，请及时咨询医生。请注意戒烟限酒，改正生活习惯，注意饮食调节,低盐少油。', '');
INSERT INTO `sklay_matadata` VALUES ('130', '2', '36', '40', '110', '119', '125', '129', '您好，本次监测您的血压显示值为高度三级高血压，低压稍高，压差间小，如有不适，请及时咨询医生。合理控制生活方式，清淡饮食，少盐少油，科学运动，及早控制。', '');
INSERT INTO `sklay_matadata` VALUES ('131', '1', '36', '40', '110', '119', '130', '139', '您好，本次监测您的血压显示值为高度三级高血压，如有不适，请及时咨询医生。合理控制生活方式，戒烟限酒，清淡饮食，少盐少油，科学运动，及早控制。祝健康。', '');
INSERT INTO `sklay_matadata` VALUES ('132', '2', '36', '40', '110', '119', '130', '139', '您好，本次监测您的血压显示值为高度三级高血压，如有不适，请及时咨询医生。建议改变自己不良生活习惯，低补、清淡保持均衡饮食，多食高纤维食物，减少进补、甜食和高胆固醇饮食，祝健康。', '');
INSERT INTO `sklay_matadata` VALUES ('133', '1', '36', '40', '110', '119', '140', '149', '您好，本次监测您的血压显示值为高度三级高血压，如有不适，请及时咨询医生。定时测量血压，改变不良生活习惯和嗜好，戒烟限酒，饮食上低补、清淡的原则，保持均衡的饮食，多食高纤维食物，减少进补、甜食和高胆固醇饮食，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('134', '2', '36', '40', '110', '119', '140', '149', '您好，本次监测您的血压显示为重度三级高血压，如有不适或伴有其它疾病，血压持续升高，建议及时就医。如有服用药用，请遵医嘱。保持均衡的饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('135', '1', '36', '40', '110', '119', '150', '159', '您好，本次监测您的血压显示为重度三级高血压，如有不适或伴有其它疾病，血压持续升高，建议及时就医。戒烟限酒，保持均衡的饮食，多食高纤维食物，减少进补、甜食和高胆固醇饮食，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('136', '2', '36', '40', '110', '119', '150', '159', '您好，本次监测您的血压显示为重度三级高血压，如有不适或伴有其它疾病，血压持续升高，建议及时就医。饮食上低补、清淡的原则，保持均衡的饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('137', '1', '36', '40', '110', '119', '160', '169', '您好，本次监测您的血压显示为重度三级高血压，如有不适或伴有其它疾病，血压持续升高，建议及时就医。注意定时测量血压，改变自己不良生活习惯，合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('138', '2', '36', '40', '110', '119', '160', '169', '您好，本次监测您的血压显示为重度三级高血压，如有不适或伴有其它疾病，血压持续升高，建议及时就医。合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('139', '1', '36', '40', '110', '119', '170', '179', '您好，本次监测您的血压显示为重度三级高血压，如有不适或伴有其它疾病，血压持续升高，建议及时就医。注意定时测量血压，改变自己不良生活习惯，合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('140', '2', '36', '40', '110', '119', '170', '179', '您好，本次监测您的血压显示为重度三级高血压，如有不适或伴有其它疾病，血压持续升高，建议及时就医。合理饮食并遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('141', '1', '36', '40', '110', '119', '180', '189', '您好，本次监测您的血压显示值为重度高危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('142', '2', '36', '40', '110', '119', '180', '189', '您好，本次监测您的血压显示值为重度高危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('143', '1', '36', '40', '110', '119', '190', '229', '您好，本次监测您的血压显示值为重度极危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('144', '2', '36', '40', '110', '119', '190', '229', '您好，本次监测您的血压显示值为重度极危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('145', '1', '36', '40', '120', '180', '135', '139', '您好，本次监测您的血压显示值为重度高危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('146', '2', '36', '40', '120', '180', '135', '139', '您好，本次监测您的血压显示值为重度高危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('147', '1', '36', '40', '120', '180', '140', '149', '您好，本次监测您的血压显示值为重度高危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('148', '2', '36', '40', '120', '180', '140', '149', '您好，本次监测您的血压显示值为重度高危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('149', '1', '36', '40', '120', '180', '150', '159', '您好，本次监测您的血压显示值为极危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('150', '2', '36', '40', '120', '180', '150', '159', '您好，本次监测您的血压显示值为极危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('151', '1', '36', '40', '120', '180', '160', '169', '您好，本次监测您的血压显示值为极危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('152', '2', '36', '40', '120', '180', '160', '169', '您好，本次监测您的血压显示值为极危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('153', '1', '36', '40', '120', '180', '170', '179', '您好，本次监测您的血压显示值为超危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('154', '2', '36', '40', '120', '180', '170', '179', '您好，本次监测您的血压显示值为超危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('155', '1', '36', '40', '120', '180', '180', '189', '您好，本次监测您的血压显示值为超危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('156', '2', '36', '40', '120', '180', '180', '189', '您好，本次监测您的血压显示值为超危高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('157', '1', '36', '40', '120', '180', '190', '229', '您好，本次监测您的血压显示值为超危重度高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');
INSERT INTO `sklay_matadata` VALUES ('158', '2', '36', '40', '120', '180', '190', '229', '您好，本次监测您的血压显示值判定为超危重度高血压，如有不适，请及时就医。谨遵医嘱合理降压，祝您健康。', '');

-- ----------------------------
-- Table structure for `sklay_operation`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_operation`;
CREATE TABLE `sklay_operation` (
  `id` bigint(20) NOT NULL auto_increment,
  `content` longtext,
  `create_time` datetime default NULL,
  `desctiption` longtext,
  `name` varchar(255) NOT NULL,
  `type` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_operation
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_sms_log`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_sms_log`;
CREATE TABLE `sklay_sms_log` (
  `id` bigint(20) NOT NULL auto_increment,
  `content` longtext NOT NULL,
  `send_operator` int(11) NOT NULL,
  `remark` varchar(255) default NULL,
  `report_time` bigint(20) NOT NULL,
  `send_time` datetime NOT NULL,
  `status` int(11) default '0',
  `receiver` varchar(50) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKE141AA6B5C554B8F` (`uid`),
  KEY `FKE141AA6B2C1F712E` (`receiver`),
  CONSTRAINT `FKE141AA6B2C1F712E` FOREIGN KEY (`receiver`) REFERENCES `sklay_user` (`phone`),
  CONSTRAINT `FKE141AA6B5C554B8F` FOREIGN KEY (`uid`) REFERENCES `sklay_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_user`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_user`;
CREATE TABLE `sklay_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `address` varchar(255) default NULL,
  `age` int(11) default NULL,
  `area` varchar(255) default NULL,
  `description` longtext,
  `height` int(11) default NULL,
  `medical_history` varchar(255) default NULL,
  `name` varchar(20) default NULL,
  `password` varchar(64) default NULL,
  `phone` varchar(50) default NULL,
  `salt` varchar(30) default NULL,
  `sex` int(11) default NULL,
  `status` int(11) default NULL,
  `weight` int(11) default NULL,
  `gid` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK67BF9FDE65B3D6E` (`phone`),
  KEY `FK67BF9FDE2D59594D` (`gid`),
  CONSTRAINT `FK67BF9FDE2D59594D` FOREIGN KEY (`gid`) REFERENCES `sklay_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_user
-- ----------------------------
INSERT INTO `sklay_user` VALUES ('1', '扬州市', '24', '江苏省', null, '170', '无', '超级管理员', 'ks4VLOpIhmHiz1j6FAOdoogy02dtZB83LK6gxW7/9vU=', '15077827845', null, '1', '1', '77', '1');

-- ----------------------------
-- Table structure for `sklay_user_attr`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_user_attr`;
CREATE TABLE `sklay_user_attr` (
  `id` bigint(20) NOT NULL auto_increment,
  `insert_time` datetime default NULL,
  `key` varchar(255) NOT NULL,
  `remark` longtext,
  `uid` bigint(20) NOT NULL,
  `value` longtext,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_user_attr
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_user_medical_report`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_user_medical_report`;
CREATE TABLE `sklay_user_medical_report` (
  `id` bigint(20) NOT NULL auto_increment,
  `remark` longtext,
  `report_time` bigint(20) NOT NULL,
  `report_type` int(11) default NULL,
  `result` longtext,
  `smsContent` longtext,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_user_medical_report
-- ----------------------------
