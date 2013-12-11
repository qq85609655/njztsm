/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : sklay_cms

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2013-09-10 15:11:49
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `sklay_cms_comment`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_comment`;
CREATE TABLE `sklay_cms_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz` varchar(255) DEFAULT NULL,
  `commented` bigint(20) DEFAULT NULL,
  `content` longtext NOT NULL,
  `create_at` datetime NOT NULL,
  `fragment` varchar(500) DEFAULT NULL,
  `img_count` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `liked` bigint(20) DEFAULT NULL,
  `modify_at` datetime DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `rank` bigint(20) DEFAULT NULL,
  `referer` bigint(20) DEFAULT NULL,
  `tag_ids` varchar(255) DEFAULT NULL,
  `title` varchar(240) DEFAULT NULL,
  `unliked` bigint(20) DEFAULT NULL,
  `ver` int(11) DEFAULT NULL,
  `viewed` bigint(20) DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9064DDF699A2076B` (`creator`),
  KEY `FK9064DDF637A38A16` (`modifier`),
  CONSTRAINT `FK9064DDF637A38A16` FOREIGN KEY (`modifier`) REFERENCES `sklay_user` (`id`),
  CONSTRAINT `FK9064DDF699A2076B` FOREIGN KEY (`creator`) REFERENCES `sklay_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_comment
-- ----------------------------
INSERT INTO `sklay_cms_comment` VALUES ('8', 'pushs', '0', '<span style=\"color:#333333;font-family:\'Microsoft YaHei\', Arial, Helvetica, sans-serif, 宋体;font-size:14px;line-height:20px;background-color:#FFFFFF;\"><img src=\"/file/newsImg/8/0/orginal.jpg?ver=0\" width=\"250\" height=\"140\" align=\"left\" alt=\"\" />当考虑重新设计你的公司网站,您所需要做的事,就是研究什么是你想要的。这是您唯一需要做的。</span>', '2013-09-07 14:26:47', '当考虑重新设计你的公司网站,您所需要做的事,就是研究什么是你想要的。这是您唯一需要做的。', '1', '0', '0', null, '1', '0', '0', null, '量身打造', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('9', 'pushs', '0', '<span style=\"color:#333333;font-family:\'Microsoft YaHei\', Arial, Helvetica, sans-serif, 宋体;font-size:14px;line-height:20px;background-color:#FFFFFF;\"><img src=\"/file/newsImg/9/0/orginal.jpg?ver=0\" width=\"250\" height=\"140\" align=\"left\" alt=\"\" />云服务帮助您自由、快速地实现内部流程，让它们有条不紊地运行。而你只需要关注统计结果，来辅佐您的决策。</span>', '2013-09-07 14:28:18', '云服务帮助您自由、快速地实现内部流程，让它们有条不紊地运行。而你只需要关注统计结果，来辅佐您的决策。', '1', '0', '0', null, '1', '0', '0', null, '完美的云计算', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('10', 'pushs', '0', '<span style=\"color:#333333;font-family:\'Microsoft YaHei\', Arial, Helvetica, sans-serif, 宋体;font-size:14px;line-height:20px;background-color:#FFFFFF;\">我们提供一些简单的步骤，来完成某些典型业务的个性化，这为您后续的业务调整提供便利。</span><img src=\"/file/newsImg/10/0/orginal.jpg?ver=0\" width=\"250\" height=\"140\" align=\"left\" alt=\"\" />', '2013-09-07 14:28:43', '我们提供一些简单的步骤，来完成某些典型业务的个性化，这为您后续的业务调整提供便利。', '1', '0', '0', null, '1', '0', '0', null, '个性化', '0', '0', '1', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('11', 'pushs', '0', '<span style=\"color:#333333;font-family:\'Microsoft YaHei\', Arial, Helvetica, sans-serif, 宋体;font-size:14px;line-height:20px;background-color:#FFFFFF;\">您可以通过设置电子邮件、手机的预警来监控您的业务，这让一切繁杂变得更简单、放心。</span><img src=\"/file/newsImg/11/0/orginal.jpg?ver=0\" width=\"250\" height=\"140\" align=\"left\" alt=\"\" />', '2013-09-07 14:29:07', '您可以通过设置电子邮件、手机的预警来监控您的业务，这让一切繁杂变得更简单、放心。', '1', '0', '0', null, '1', '0', '0', null, '快速响应', '0', '0', '20', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('12', 'pushs', '0', '<img src=\"/file/newsImg/12/0/orginal.jpg?ver=0\" width=\"68\" height=\"40\" alt=\"\" />', '2013-09-07 22:55:36', null, '1', '0', '0', null, '3', '0', '0', null, '思科', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('13', 'pushs', '0', '<img src=\"/file/newsImg/13/0/orginal.jpg?ver=1\" width=\"68\" height=\"51\" alt=\"\" />', '2013-09-07 22:56:07', null, '1', '0', '0', '2013-09-07 22:56:54', '3', '0', '0', null, '戴尔', '0', '1', '1', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('14', 'pushs', '0', '<img src=\"/file/newsImg/14/0/orginal.jpg?ver=0\" alt=\"\" />', '2013-09-07 22:56:25', null, '1', '0', '0', null, '3', '0', '0', null, '微软', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('15', 'pushs', '0', '<img src=\"/file/newsImg/15/0/orginal.jpg?ver=0\" alt=\"\" />', '2013-09-07 22:56:38', null, '1', '0', '0', null, '3', '0', '0', null, '惠普', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('16', 'pushs', '0', '<img src=\"/file/newsImg/16/0/orginal.jpg?ver=0\" alt=\"\" />', '2013-09-07 22:57:21', null, '1', '0', '0', null, '3', '0', '0', null, '华为', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('17', 'pushs', '0', '<img src=\"/file/newsImg/17/0/orginal.jpg?ver=0\" alt=\"\" />', '2013-09-07 22:57:36', null, '1', '0', '0', null, '3', '0', '0', null, 'IBM', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('18', 'pushs', '0', '<h2>\r\n	<span>合作伙伴</span>\r\n</h2>\r\n<div class=\"panel-content\">\r\n	<ul class=\"row-fluid list-images\">\r\n		<li class=\"span2\">\r\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\"> <img src=\"/file/newsImg/18/0/orginal.jpg?ver=0\" style=\"width:68px;height:40px;\" /> </a> \r\n			<h6>\r\n				<a href=\"\" target=\"_blank\">IBM</a>\r\n			</h6>\r\n		</li>\r\n		<li class=\"span2\">\r\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\"> <img src=\"/file/newsImg/18/1/orginal.jpg?ver=0\" style=\"width:68px;height:40px;\" /> </a> \r\n		</li>\r\n		<li class=\"span2\">\r\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\"> <img src=\"/file/newsImg/18/2/orginal.jpg?ver=0\" style=\"width:68px;height:40px;\" /> </a> \r\n		</li>\r\n		<li class=\"span2\">\r\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\"> <img src=\"/file/newsImg/18/3/orginal.jpg?ver=0\" style=\"width:68px;height:40px;\" /> </a> \r\n		</li>\r\n		<li class=\"span2\">\r\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\"> <img src=\"/file/newsImg/18/4/orginal.jpg?ver=0\" style=\"width:68px;height:40px;\" /> </a> \r\n		</li>\r\n		<li class=\"span2\">\r\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\"> <img src=\"/file/newsImg/18/5/orginal.jpg?ver=0\" style=\"width:68px;height:40px;\" /> </a> \r\n		</li>\r\n	</ul>\r\n</div>', '2013-09-08 00:44:09', '\r\n	合作伙伴\r\n\r\n\r\n	\r\n		\r\n			   \r\n			\r\n				IBM\r\n			\r\n		\r\n		\r\n			   \r\n		\r\n		\r\n			   \r\n		\r\n		\r\n			   \r\n		\r\n		\r\n			   \r\n		\r\n		\r\n			   \r\n		\r\n	\r\n', '6', '0', '0', null, '4', '0', '0', null, '合作伙伴', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('19', 'pushs', '0', '<p style=\"text-indent:2em;\">\r\n	<img src=\"/file/newsImg/19/0/orginal.jpg?ver=0\" width=\"500\" height=\"129\" align=\"left\" alt=\"\" />\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"font-size:16px;font-family:\'Microsoft YaHei\';line-height:2.5;\"><br />\r\n</span>\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"font-size:16px;font-family:\'Microsoft YaHei\';line-height:2.5;\">上海诚业网络科技有限责任公司是一家专业的信息网络基础设施服务和解决方案提供商。专注于帮助客户规划、构建、维护和管理</span><span style=\"font-size:16px;font-family:\'Microsoft YaHei\';line-height:2.5;\">IT</span><span style=\"font-size:16px;font-family:\'Microsoft YaHei\';line-height:2.5;\">基础设施。公司通过咨询、监控、集成、维护和管理方面的专业技术，为客户定制和提供网络架构、网络监控、系统集成、服务管理等解决方案和专业服务。最终实现帮助客户利用高新技术更快的获得成功，提高网络业务的价值和投资回报。</span><span></span>\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<br />\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"font-size:16px;font-family:\'Microsoft YaHei\';line-height:2.5;\">诚业网络拥有一支经验丰富的、富有活力的专业技术团队，能够为不同行业的企业单位提供软、硬件技术支持，设计出切实可行的解决方案，满足客户需求。提高客户网络系统的可用性，增强网络系统对客户业务的应变能力。</span><span></span>\r\n</p>', '2013-09-08 09:29:03', '\r\n	\r\n\r\n\r\n	\r\n\r\n\r\n\r\n	上海诚业网络科技有限责任公司是一家专业的信息网络基础设施服务和解决方案提供商。专注于帮助客户规划、构建、维护和管理IT基础设施。公司通过咨询、监控、集成、维护和管理方面的专业技术，为客户定制', '1', '0', '0', null, '5', '0', '0', null, '公司概况', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('20', 'pushs', '0', '<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">诚业网络致力于打造国内首家专业的网络监控预警管理系统整体解决方案提供商。</span><span></span>\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">整套系统包括专业定制的硬件服务器，操作系统，数据库，网络监控预警管理软件。该套系统采用</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">B/S</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">架构，是集网络监测、设备性能维护管理、故障监控报警、网络实时流量监控和历史数据统计、汇总和历史数据分析等功能于一体的网络管理系统。整套系统整体解决方案提供的服务包括需求分析、网络评估、部署实施、维护支持、人员培训等服务。</span><span></span>\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">该套系统有着方便管理人员检查网络性能，缩减网络故障对企业单位造成的影响，节省网络管理开支，合理优化网络资源等优势，同时还能够随着网络的快速发展以及不断扩张的网络需求而灵活应变。</span><span></span>\r\n</p>', '2013-09-08 09:32:44', '\r\n	诚业网络致力于打造国内首家专业的网络监控预警管理系统整体解决方案提供商。\r\n\r\n\r\n	整套系统包括专业定制的硬件服务器，操作系统，数据库，网络监控预警管理软件。该套系统采用B/S架构，是集网络监测、设备性能维护管理', '0', '0', '0', null, '6', '0', '0', null, '网络监系统业务', '0', '0', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('21', 'pushs', '0', '<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">系统集成通过提供项目咨询，系统设计，部署实施、系统维护以及培训支持等系统服务，满足了用户对信息系统的根本需求，实现了用户的投资价值。</span><span></span>\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">诚业网络为客户提供的系统集成服务，可以分为以下三类：</span><span></span>\r\n</p>\r\n<p style=\"text-indent:2em;\">\r\n	<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">1)新建信息系统集成：根据用户的实际需求，为用户制订切实可行的技术方案，协助用户完成设备选型。在此基础上进行实施，最终为用户建立起可靠和高效的信息系统平台。</span><span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">&nbsp;&nbsp;</span><br />\r\n<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\"> &nbsp;&nbsp;&nbsp; 2)</span></span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">保修维护服务：在用户的信息系统使用一定时间并超过合同规定的免费保修期后，我们能够为用户提供实惠而可靠的保修维护服务。确保其业务系统始终具有令人满意的高可靠性和高可用性。</span><span><br />\r\n<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\"> &nbsp;&nbsp;&nbsp; 3)</span></span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">高端增值服务：根据用户的需求，我们能够为用户提供一系列的高端增值服务，包括新旧系统割接、数据迁移、生产系统搬迁、信息系统性能分析、疑难故障排除等内容。</span>\r\n</p>', '2013-09-08 09:33:27', '\r\n	系统集成通过提供项目咨询，系统设计，部署实施、系统维护以及培训支持等系统服务，满足了用户对信息系统的根本需求，实现了用户的投资价值。\r\n\r\n\r\n	诚业网络为客户提供的系统集成服务，可以分为以下三类：\r\n\r\n\r\n	1)新建', '0', '0', '0', '2013-09-08 09:33:50', '6', '0', '0', null, '系统集成业务', '0', '1', '0', '1', '1');
INSERT INTO `sklay_cms_comment` VALUES ('22', 'pushs', '0', '<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">诚业网络为用户提供包含企业内部从桌面计算机及外设到网络设备运维和服务器运维的一站式</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">IT</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">服务。 作为专业的</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">IT</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">外包服务提供商，我们建立了基于</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">ITIL</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">方法和实际管理经验相结合，符合业界最佳实践和工业标准的服务管理体系，能够为用户提供切实可行的管理和服务方案，包括建立计算机系统设备档案、系统维护记录和系统管理分析报告等服务项目，保障用户</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">IT</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">系统正常运行，为用户提供更高质量的</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">IT</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">服务。</span><span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">&nbsp;</span>\r\n</p>\r\n<p class=\"MsoNormal\" align=\"left\" style=\"text-indent:2em;\">\r\n	<span style=\"line-height:2.5;font-size:16px;font-family:\'Microsoft YaHei\';\">我们的服务模式灵活多样，可满足用户不同的需求。服务模式可以分为电话技术支持服务、远程网络支持服务、单次上门现场服务、工程师驻客户现场服务等多种方式。</span><span></span>\r\n</p>', '2013-09-08 09:34:30', '\r\n	诚业网络为用户提供包含企业内部从桌面计算机及外设到网络设备运维和服务器运维的一站式IT服务。 作为专业的IT外包服务提供商，我们建立了基于ITIL方法和实际管理经验相结合，符合业界最佳实践和工业标准的服务管理', '0', '0', '0', null, '6', '0', '0', null, 'IT外包业务', '0', '0', '0', '1', '1');

-- ----------------------------
-- Table structure for `sklay_cms_config`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_config`;
CREATE TABLE `sklay_cms_config` (
  `biz` varchar(50) NOT NULL,
  `owner` varchar(50) NOT NULL,
  `data` longtext,
  `name` varchar(1000) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`biz`,`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_config
-- ----------------------------
INSERT INTO `sklay_cms_config` VALUES ('newsType', '1', '{\"imgSizes\":[{\"height\":140,\"width\":250}],\"maxHeight\":600,\"maxWidth\":800}', null, '0');
INSERT INTO `sklay_cms_config` VALUES ('newsType', '2', '{\"imgSizes\":[{}],\"maxHeight\":600,\"maxWidth\":800}', null, '0');
INSERT INTO `sklay_cms_config` VALUES ('newsType', '3', '{\"imgSizes\":[{\"height\":200,\"width\":200}],\"maxHeight\":600,\"maxWidth\":800}', null, '0');
INSERT INTO `sklay_cms_config` VALUES ('newsType', '4', '{\"imgSizes\":[{}],\"maxHeight\":600,\"maxWidth\":800}', null, '0');
INSERT INTO `sklay_cms_config` VALUES ('newsType', '5', '{\"imgSizes\":[{}],\"maxHeight\":600,\"maxWidth\":800}', null, '0');
INSERT INTO `sklay_cms_config` VALUES ('newsType', '6', '{\"imgSizes\":[{}],\"maxHeight\":600,\"maxWidth\":800}', null, '0');
INSERT INTO `sklay_cms_config` VALUES ('newsType', '7', '{\"imgSizes\":[{}],\"maxHeight\":600,\"maxWidth\":800}', null, '0');

-- ----------------------------
-- Table structure for `sklay_cms_golbal`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_golbal`;
CREATE TABLE `sklay_cms_golbal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `css` longtext,
  `jsp` longtext,
  `ver` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_golbal
-- ----------------------------
INSERT INTO `sklay_cms_golbal` VALUES ('3', '#wrap{\r\n	margin-top: 0px;\r\n}\r\n\r\n.inline {\r\n	white-space: nowrap;\r\n}\r\n\r\nhtml{\r\n}\r\n\r\nbody {\r\n	background: transparent;\r\n	font-family:\"Microsoft YaHei\",Arial,Helvetica,sans-serif,\"宋体\";\r\n}\r\n\r\n\r\n.breadcrumb{\r\n	margin-bottom: 0px;\r\n}\r\n\r\n.noborder{\r\n	padding: 0px !important;\r\n	border:0px !important;\r\n	-webkit-box-shadow: none !important;\r\n          box-shadow:none !important;\r\n}\r\n/* Footer\r\n-------------------------------------------------- */\r\n\r\n.footer {\r\n  text-align: center;\r\n  margin-top: 70px;\r\n  border-top: 1px solid #e5e5e5;\r\n}\r\n.footer p {\r\n  margin-bottom: 0;\r\n}\r\n.footer-links {\r\n  margin: 10px 0;\r\n}\r\n.footer-links li {\r\n  display: inline;\r\n  padding: 0 2px;\r\n}\r\n.footer-links li:first-child {\r\n  padding-left: 0;\r\n}\r\n\r\n\r\n.logo {\r\nfloat: left;\r\nheight: 0px;\r\nwidth: 100px;\r\nbackground-image: url(../images/log01.png);\r\nbackground-repeat: no-repeat;\r\ntext-decoration: none;\r\ndisplay: block;\r\npadding:20px 20px;\r\nmargin-top:10px;\r\n}\r\n', '<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"\r\n    pageEncoding=\"UTF-8\"%>\r\n<%@ include file=\"include.jsp\"%>\r\n    \r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"${rs}/theme/blue/theme.css\">\r\n<c:if test=\"${not empty currentPage.customCss}\">\r\n	<style>\r\n		${currentPage.customCss}\r\n	</style>\r\n</c:if>\r\n<!-- navbar start -->\r\n<c:set var=\"navPage\" value=\"${currentPage.parent==null?currentPage:currentPage.parent}\" scope=\"request\"></c:set>\r\n<div class=\"container\">\r\n<div class=\"navbar\">\r\n	<div class=\"navbar-inner\"  style=\"margin-left: -30px;\">\r\n		<div class=\"container\">\r\n			 <a data-target=\".nav-collapse\" data-toggle=\"collapse\" class=\"btn btn-navbar\">菜单</a>\r\n			 \r\n            <a href=\"/\" class=\" logo\"> </a> \r\n			\r\n			<div class=\"nav-collapse\">\r\n				<ul class=\"nav\">\r\n					<c:forEach items=\"${pages}\" var=\"p\" varStatus=\"status\">\r\n						<c:choose>\r\n							<c:when test=\"${not empty p.children}\">\r\n								<li class=\"dropdown\">\r\n									<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">${p.name}<b class=\"caret\"></b></a>\r\n									<ul class=\"dropdown-menu\">\r\n										<c:forEach items=\"${p.children}\" var=\"cp\">\r\n											<li><a href=\"${ctx}/${cp.alias}\">${cp.name}</a></li>\r\n										</c:forEach>\r\n									</ul>\r\n								</li>\r\n							</c:when>\r\n							<c:otherwise>\r\n								<li <c:if test=\"${p.id eq navPage.id}\">class=\"active\"</c:if>>\r\n									<a href=\"${ctx}/${status.first?\'\':p.alias}\">${p.name}</a>\r\n								</li>\r\n							</c:otherwise>\r\n						</c:choose>\r\n					</c:forEach>\r\n					    <shiro:guest>\r\n						 	<li class=\"nav pull-right\">\r\n		        				<a href=\"${ctx}/login\">登录</a>\r\n							</li>\r\n				 	    </shiro:guest>\r\n                    </ul>\r\n				</div>\r\n			</div>\r\n		</div>	\r\n	</div>\r\n	<!-- navbar end-->\r\n</div>\r\n<div id=\"wrap\" class=\"container\">\r\n	<div id=\"content\" class=\"row\">\r\n		<tiles:insertAttribute name=\"content\" ></tiles:insertAttribute>\r\n	</div>\r\n</div>\r\n<footer class=\"footer\">\r\n    <div class=\"container\">\r\n        <p>Designed and built with all the love in the world by <a href=\"http://twitter.com/mdo\" target=\"_blank\">@mdo</a> and <a href=\"http://twitter.com/fat\" target=\"_blank\">@fat</a>.</p>\r\n        <p>Code licensed under <a href=\"http://www.apache.org/licenses/LICENSE-2.0\" target=\"_blank\">Apache License v2.0</a>, documentation under <a href=\"http://creativecommons.org/licenses/by/3.0/\" target=\"_blank\">CC BY 3.0</a>.</p>\r\n        <p><a href=\"http://glyphicons.com\" target=\"_blank\">Glyphicons Free</a> licensed under <a href=\"http://creativecommons.org/licenses/by/3.0/\" target=\"_blank\">CC BY 3.0</a>.</p>\r\n        <!--\r\n        <p>本网站所列开源项目的中文版文档全部由<a href=\"http://www.bootcss.com/\">Bootstrap中文网</a>成员翻译，并全部遵循 <a href=\"http://creativecommons.org/licenses/by/3.0/\" target=\"_blank\">CC BY 3.0</a>协议发布。</p>\r\n        <ul class=\"footer-links\">\r\n          <li><a href=\"about.html\" target=\"_blank\">关于</a></li>\r\n          <li class=\"muted\">·</li>\r\n          <li><a href=\"https://github.com/twitter/bootstrap/issues?state=open\" target=\"_blank\">问题反馈</a></li>\r\n          <li class=\"muted\">·</li>\r\n          <li><a href=\"https://github.com/twitter/bootstrap/blob/master/CHANGELOG.md\">版本变更记录</a></li>\r\n          <li><a href=\"http://www.miibeian.gov.cn/\" target=\"_blank\">京ICP备11008151号</a></li>\r\n        </ul>\r\n        -->\r\n    </div>\r\n</footer>', '78');

-- ----------------------------
-- Table structure for `sklay_cms_news_group`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_news_group`;
CREATE TABLE `sklay_cms_news_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz` varchar(255) DEFAULT NULL,
  `leaf` tinyint(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `path` varchar(1000) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_news_group
-- ----------------------------
INSERT INTO `sklay_cms_news_group` VALUES ('1', 'pushs', '1', '团队', 'pushs', '|1|', '0');
INSERT INTO `sklay_cms_news_group` VALUES ('2', 'pushs', '1', '图片资源', 'pushs', '|2|', '0');
INSERT INTO `sklay_cms_news_group` VALUES ('3', 'pushs', '1', '合作伙伴', 'pushs', '|3|', '0');
INSERT INTO `sklay_cms_news_group` VALUES ('4', 'pushs', '1', '合作伙伴详情', 'pushs', '|4|', '0');
INSERT INTO `sklay_cms_news_group` VALUES ('5', 'pushs', '1', '公司概况', 'pushs', '|5|', '0');
INSERT INTO `sklay_cms_news_group` VALUES ('6', 'pushs', '1', '产品&服务', 'pushs', '|6|', '0');
INSERT INTO `sklay_cms_news_group` VALUES ('7', 'pushs', '1', '联系我们', 'pushs', '|7|', '0');

-- ----------------------------
-- Table structure for `sklay_cms_page`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_page`;
CREATE TABLE `sklay_cms_page` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alias` varchar(50) DEFAULT NULL,
  `custom_css` longtext,
  `description` varchar(1000) DEFAULT NULL,
  `keywords` varchar(500) DEFAULT NULL,
  `layout` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `rank` int(11) DEFAULT NULL,
  `can_show` tinyint(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC8E27258D7B7AED3` (`parent_id`),
  CONSTRAINT `FKC8E27258D7B7AED3` FOREIGN KEY (`parent_id`) REFERENCES `sklay_cms_page` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_page
-- ----------------------------
INSERT INTO `sklay_cms_page` VALUES ('1', 'index', '				\r\n			', '', '', '1_1-1-1_1.tpl', '首页', '0', '1', '首页', '0', null);
INSERT INTO `sklay_cms_page` VALUES ('2', 'about', null, null, null, '1-1.tpl', '关于我们', '0', '1', '关于我们', '0', null);
INSERT INTO `sklay_cms_page` VALUES ('3', 'introduction', null, null, null, '3-1_1.tpl', '公司概况', '0', '1', '公司概况', '0', '2');
INSERT INTO `sklay_cms_page` VALUES ('4', 'partners', '				\r\n			', '', '', '3-1_1.tpl', '合作伙伴', '-2', '1', '合作伙伴', '0', null);
INSERT INTO `sklay_cms_page` VALUES ('5', 'products', null, null, null, '1-1.tpl', '产品&服务', '0', '1', '产品&服务', '0', null);
INSERT INTO `sklay_cms_page` VALUES ('6', 'monitor', null, null, null, '3-1_1.tpl', '网络监控', '0', '1', '网络监控', '0', '5');
INSERT INTO `sklay_cms_page` VALUES ('7', 'integration', null, null, null, '3-1_1.tpl', '系统集成', '0', '1', '系统集成', '0', '5');
INSERT INTO `sklay_cms_page` VALUES ('8', 'it', null, null, null, '3-1_1.tpl', 'IT外包', '0', '1', 'IT外包', '0', '5');
INSERT INTO `sklay_cms_page` VALUES ('9', 'drafting', null, null, null, '3-1_1.tpl', '招贤纳士', '-1', '1', '招贤纳士', '0', null);
INSERT INTO `sklay_cms_page` VALUES ('10', 'contact', null, null, null, '3-1_1.tpl', '联系我们', '-3', '1', '联系我们', '0', null);
INSERT INTO `sklay_cms_page` VALUES ('12', 'news_detail', null, null, null, '1_3-1_1.tpl', '资讯详情', '0', '0', '资讯详情', '0', null);

-- ----------------------------
-- Table structure for `sklay_cms_tag`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_tag`;
CREATE TABLE `sklay_cms_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `used` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_tag
-- ----------------------------

-- ----------------------------
-- Table structure for `sklay_cms_widget`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_widget`;
CREATE TABLE `sklay_cms_widget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `border_class` varchar(100) DEFAULT NULL,
  `borderTpl` varchar(100) NOT NULL,
  `container` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `page_id` bigint(20) DEFAULT NULL,
  `rank` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_widget
-- ----------------------------
INSERT INTO `sklay_cms_widget` VALUES ('5', '', 'none.tpl', 'row0', 'content.simple', '1', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('8', 'panel-success', 't-c-f.tpl', 'row0', 'pushs.list', '1', '2', '我们的团队');
INSERT INTO `sklay_cms_widget` VALUES ('10', 'panel-primary', 'c-f.tpl', 'row1', 'pushs.detail', '12', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('12', 'panel-primary', 't-c-f.tpl', 'row1', 'pushs.pagination', '1', '0', '最新资讯');
INSERT INTO `sklay_cms_widget` VALUES ('13', 'panel-info', 't-c-f.tpl', 'row2', 'pushs.pagination', '1', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('14', 'panel-warning', 't-c-f.tpl', 'row3', 'pushs.pagination', '1', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('16', '', 'none.tpl', 'row0', 'slider.nivoSlider', '1', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('19', 'panel-primary', 't-c-f.tpl', 'row2', 'pushs.list', '12', '0', '最新资讯');
INSERT INTO `sklay_cms_widget` VALUES ('21', '', 'none.tpl', 'row0', 'content.simple', '12', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('23', 'panel-primary', 't-c-f.tpl', 'row1', 'pushs.pagination', '9', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('25', '', 'none.tpl', 'row0', 'pushs.showDetail', '3', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('32', '', 'none.tpl', 'row2', 'content.simple', '4', '0', '合作伙伴');
INSERT INTO `sklay_cms_widget` VALUES ('33', '', 'none.tpl', 'row0', 'pushs.showDetail', '6', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('34', '', 'none.tpl', 'row0', 'pushs.showDetail', '7', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('35', '', 'none.tpl', 'row0', 'pushs.showDetail', '8', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('36', '', 'none.tpl', 'row2', 'content.simple', '3', '0', '合作伙伴');
INSERT INTO `sklay_cms_widget` VALUES ('37', '', 'none.tpl', 'row2', 'content.simple', '6', '0', '合作伙伴');
INSERT INTO `sklay_cms_widget` VALUES ('38', '', 'none.tpl', 'row2', 'content.simple', '7', '0', '合作伙伴');
INSERT INTO `sklay_cms_widget` VALUES ('39', '', 'none.tpl', 'row2', 'content.simple', '8', '0', '合作伙伴');
INSERT INTO `sklay_cms_widget` VALUES ('40', '', 'none.tpl', 'row2', 'content.simple', '9', '0', '合作伙伴');
INSERT INTO `sklay_cms_widget` VALUES ('41', '', 'none.tpl', 'row2', 'content.simple', '10', '0', '合作伙伴');
INSERT INTO `sklay_cms_widget` VALUES ('42', 'panel-primary', 't-c-f.tpl', 'row0', 'pushs.showDetail', '10', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('43', 'panel-primary', 't-c-f.tpl', 'row1', 'pushs.pagination', '3', '0', '特别推荐');
INSERT INTO `sklay_cms_widget` VALUES ('45', 'panel-danger', 't-c-f.tpl', 'row1', 'pushs.list', '3', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('47', 'panel-danger', 't-c-f.tpl', 'row1', 'pushs.list', '4', '2', '');
INSERT INTO `sklay_cms_widget` VALUES ('48', 'panel-primary', 't-c-f.tpl', 'row1', 'pushs.pagination', '4', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('49', 'panel-danger', 't-c-f.tpl', 'row1', 'pushs.list', '6', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('50', 'panel-primary', 't-c-f.tpl', 'row1', 'pushs.pagination', '6', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('51', 'panel-danger', 't-c-f.tpl', 'row1', 'pushs.list', '7', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('52', 'panel-primary', 't-c-f.tpl', 'row1', 'pushs.pagination', '7', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('53', 'panel-primary', 't-c-f.tpl', 'row1', 'pushs.pagination', '8', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('54', 'panel-danger', 't-c-f.tpl', 'row1', 'pushs.list', '8', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('56', 'panel-danger', 't-c-f.tpl', 'row1', 'pushs.list', '9', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('57', 'panel-danger', 't-c-f.tpl', 'row1', 'pushs.list', '10', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('59', 'panel-primary', 't-c-f.tpl', 'row1', 'pushs.pagination', '10', '0', '');
INSERT INTO `sklay_cms_widget` VALUES ('60', 'panel-primary', 't-c-f.tpl', 'row0', 'pushs.showDetail', '9', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('61', null, 't-c-f.tpl', 'row0', 'pushs.list', '4', '1', '');
INSERT INTO `sklay_cms_widget` VALUES ('63', '', 'none.tpl', 'row0', 'content.simple', '10', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('64', '', 'none.tpl', 'row0', 'content.simple', '6', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('65', '', 'none.tpl', 'row0', 'content.simple', '7', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('66', '', 'none.tpl', 'row0', 'content.simple', '8', '-1', '');
INSERT INTO `sklay_cms_widget` VALUES ('67', '', 'none.tpl', 'row0', 'content.simple', '3', '-1', '');

-- ----------------------------
-- Table structure for `sklay_cms_widget_setting`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_cms_widget_setting`;
CREATE TABLE `sklay_cms_widget_setting` (
  `widget_id` bigint(20) NOT NULL,
  `attr_value` longtext,
  `attr_key` varchar(64) NOT NULL,
  PRIMARY KEY (`widget_id`,`attr_key`),
  KEY `FKE36133DE7F0DA9AE` (`widget_id`),
  CONSTRAINT `FKE36133DE7F0DA9AE` FOREIGN KEY (`widget_id`) REFERENCES `sklay_cms_widget` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_cms_widget_setting
-- ----------------------------
INSERT INTO `sklay_cms_widget_setting` VALUES ('5', '<div class=\"well centered\">\n		<h3>\n			欢迎来到SKLAY(天籁)官方首页，我们为您提供个人、企业软件的个性化解决方案。\n			 <a class=\"btn btn-primary btn-large\" href=\"javascript:void(0) ;\">了解更多 \n			 	<i class=\"icon icon-caret-right\"></i>\n			 </a>\n		 </h3>\n	</div>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '3', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '4', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', '[\"1\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('8', 'listImageDesc', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('10', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('10', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('12', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('12', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('12', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('13', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('13', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('13', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('14', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('14', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('14', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('16', '<a href=\"\"><img src=\"/widgetResource/slider/nivo-slider/demo/images/slide-4.jpg\" alt=\"\" title=\"slide-4.jpg\" /></a> <a href=\"\"><img src=\"/widgetResource/slider/nivo-slider/demo/images/slide-5.jpg\" alt=\"\" title=\"slide-5.jpg\" /></a>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('16', '{controlNav:false}', 'options');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', '', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', '4', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('19', 'listNormal', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('21', '<div class=\"well centered\">\n		<h3>\n			欢迎来到SKLAY(天籁)官方首页，我们为您提供个人、企业软件的个性化解决方案。\n			 <a class=\"btn btn-primary btn-large\" href=\"javascript:void(0) ;\">了解更多 \n			 	<i class=\"icon icon-caret-right\"></i>\n			 </a>\n		 </h3>\n	</div>\n', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('23', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('23', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('23', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('25', '100%', 'height');
INSERT INTO `sklay_cms_widget_setting` VALUES ('25', '19', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('25', 'false', 'showTitle');
INSERT INTO `sklay_cms_widget_setting` VALUES ('25', '100%', 'width');
INSERT INTO `sklay_cms_widget_setting` VALUES ('32', '<style>\nh2 {\ntext-align: center;\nmargin: 15px 0 10px 0;\nbackground: url(\'/static/images/line.gif\') center repeat-x;\n}\nh2 span {\nbackground: #FFF;\npadding: 0 20px;\n}\n</style>\n<h2><span>合作伙伴</span></h2>\n<div class=\"panel-content\">\n		<link rel=\"stylesheet\" href=\"/widgetResource/pushs/list-images.css\">\n\n	<ul class=\"row-fluid list-images\">\n		\n			<li class=\"span2\">\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/17/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/16/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/15/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/14/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/13/0/c0.jpg?ver=1\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/12/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n	 </ul>\n\n	</div>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('33', '100%', 'height');
INSERT INTO `sklay_cms_widget_setting` VALUES ('33', '21', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('33', 'true', 'showTitle');
INSERT INTO `sklay_cms_widget_setting` VALUES ('33', '100%', 'width');
INSERT INTO `sklay_cms_widget_setting` VALUES ('34', '100%', 'height');
INSERT INTO `sklay_cms_widget_setting` VALUES ('34', '21', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('34', 'true', 'showTitle');
INSERT INTO `sklay_cms_widget_setting` VALUES ('34', '100%', 'width');
INSERT INTO `sklay_cms_widget_setting` VALUES ('35', '100%', 'height');
INSERT INTO `sklay_cms_widget_setting` VALUES ('35', '22', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('35', 'true', 'showTitle');
INSERT INTO `sklay_cms_widget_setting` VALUES ('35', '100%', 'width');
INSERT INTO `sklay_cms_widget_setting` VALUES ('36', '<style>\nh2 {\ntext-align: center;\nmargin: 15px 0 10px 0;\nbackground: url(\'/static/images/line.gif\') center repeat-x;\n}\nh2 span {\nbackground: #FFF;\npadding: 0 20px;\n}\n</style>\n<h2><span>合作伙伴</span></h2>\n<div class=\"panel-content\">\n		<link rel=\"stylesheet\" href=\"/widgetResource/pushs/list-images.css\">\n\n	<ul class=\"row-fluid list-images\">\n		\n			<li class=\"span2\">\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/17/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/16/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/15/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/14/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/13/0/c0.jpg?ver=1\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/12/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n	 </ul>\n\n	</div>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('37', '<style>\nh2 {\ntext-align: center;\nmargin: 15px 0 10px 0;\nbackground: url(\'/static/images/line.gif\') center repeat-x;\n}\nh2 span {\nbackground: #FFF;\npadding: 0 20px;\n}\n</style>\n<h2><span>合作伙伴</span></h2>\n<div class=\"panel-content\">\n		<link rel=\"stylesheet\" href=\"/widgetResource/pushs/list-images.css\">\n\n	<ul class=\"row-fluid list-images\">\n		\n			<li class=\"span2\">\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/17/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/16/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/15/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/14/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/13/0/c0.jpg?ver=1\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/12/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n	 </ul>\n\n	</div>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('38', '<style>\nh2 {\ntext-align: center;\nmargin: 15px 0 10px 0;\nbackground: url(\'/static/images/line.gif\') center repeat-x;\n}\nh2 span {\nbackground: #FFF;\npadding: 0 20px;\n}\n</style>\n<h2><span>合作伙伴</span></h2>\n<div class=\"panel-content\">\n		<link rel=\"stylesheet\" href=\"/widgetResource/pushs/list-images.css\">\n\n	<ul class=\"row-fluid list-images\">\n		\n			<li class=\"span2\">\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/17/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/16/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/15/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/14/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/13/0/c0.jpg?ver=1\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/12/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n	 </ul>\n\n	</div>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('39', '<style>\nh2 {\ntext-align: center;\nmargin: 15px 0 10px 0;\nbackground: url(\'/static/images/line.gif\') center repeat-x;\n}\nh2 span {\nbackground: #FFF;\npadding: 0 20px;\n}\n</style>\n<h2><span>合作伙伴</span></h2>\n<div class=\"panel-content\">\n		<link rel=\"stylesheet\" href=\"/widgetResource/pushs/list-images.css\">\n\n	<ul class=\"row-fluid list-images\">\n		\n			<li class=\"span2\">\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/17/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/16/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/15/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/14/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/13/0/c0.jpg?ver=1\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/12/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n	 </ul>\n\n	</div>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('40', '<style>\nh2 {\ntext-align: center;\nmargin: 15px 0 10px 0;\nbackground: url(\'/static/images/line.gif\') center repeat-x;\n}\nh2 span {\nbackground: #FFF;\npadding: 0 20px;\n}\n</style>\n<h2><span>合作伙伴</span></h2>\n<div class=\"panel-content\">\n		<link rel=\"stylesheet\" href=\"/widgetResource/pushs/list-images.css\">\n\n	<ul class=\"row-fluid list-images\">\n		\n			<li class=\"span2\">\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/17/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/16/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/15/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/14/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/13/0/c0.jpg?ver=1\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/12/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n	 </ul>\n\n	</div>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('41', '<style>\nh2 {\ntext-align: center;\nmargin: 15px 0 10px 0;\nbackground: url(\'/static/images/line.gif\') center repeat-x;\n}\nh2 span {\nbackground: #FFF;\npadding: 0 20px;\n}\n</style>\n<h2><span>合作伙伴</span></h2>\n<div class=\"panel-content\">\n		<link rel=\"stylesheet\" href=\"/widgetResource/pushs/list-images.css\">\n\n	<ul class=\"row-fluid list-images\">\n		\n			<li class=\"span2\">\n			<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/17/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/16/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/15/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/14/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/13/0/c0.jpg?ver=1\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n			<li class=\"span2\">\n				<a class=\"thumbnail\" href=\"\" target=\"_blank\">\n			      <img src=\"/file/newsImg/12/0/c0.jpg?ver=0\" \n\nstyle=\"width:68px;height:40px;\">\n		        </a>\n			</li>\n	 </ul>\n\n	</div>', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('42', '100%', 'height');
INSERT INTO `sklay_cms_widget_setting` VALUES ('42', '23', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('42', '', 'showTitle');
INSERT INTO `sklay_cms_widget_setting` VALUES ('42', '100%', 'width');
INSERT INTO `sklay_cms_widget_setting` VALUES ('43', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('43', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('43', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '2', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '2', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', '[\"1\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('45', 'listImages', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '1', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '2', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', '[\"1\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('47', 'listImages', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('48', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('48', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('48', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '2', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', '[\"1\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('49', 'listImages', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('50', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('50', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('50', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '2', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', '[\"1\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('51', 'listImages', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('52', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('52', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('52', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('53', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('53', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('53', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '2', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', '[\"1\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('54', 'listImages', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '1', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '2', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', '[\"1\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('56', 'listImages', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '2', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', '[\"1\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('57', 'listImages', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('59', '20', 'pageSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('59', '1', 'type');
INSERT INTO `sklay_cms_widget_setting` VALUES ('59', 'paginationLeftimg', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('60', '100%', 'height');
INSERT INTO `sklay_cms_widget_setting` VALUES ('60', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('60', '', 'showTitle');
INSERT INTO `sklay_cms_widget_setting` VALUES ('60', '100%', 'width');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '10', 'commentSize');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '', 'dateRegion');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '', 'imgSizeType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '10', 'limit');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '0', 'logic');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '', 'newsIds');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '0', 'offset');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', 'true', 'showType');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '6', 'sizePerLine');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', '[\"3\"]', 'types');
INSERT INTO `sklay_cms_widget_setting` VALUES ('61', 'listImageDesc', 'view');
INSERT INTO `sklay_cms_widget_setting` VALUES ('63', '<img src=\"/static/images/contact.png\" alt=\"联系我们\" style=\"width:900px;height:150px;\">', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('64', '<img src=\"/static/images/service.png\" alt=\"联系我们\" style=\"width:900px;height:150px;\">', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('65', '<img src=\"/static/images/service.png\" alt=\"联系我们\" style=\"width:900px;height:150px;\">', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('66', '<img src=\"/static/images/service.png\" alt=\"联系我们\" style=\"width:900px;height:150px;\">', 'content');
INSERT INTO `sklay_cms_widget_setting` VALUES ('67', '<img src=\"/static/images/about.png\" alt=\"联系我们\" style=\"width:900px;height:150px;\">', 'content');

-- ----------------------------
-- Table structure for `sklay_user`
-- ----------------------------
DROP TABLE IF EXISTS `sklay_user`;
CREATE TABLE `sklay_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `roles` longtext,
  `salt` varchar(30) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sklay_user
-- ----------------------------
INSERT INTO `sklay_user` VALUES ('1', '测试', 'ks4VLOpIhmHiz1j6FAOdoogy02dtZB83LK6gxW7/9vU=', '15077827845', 'superadmin', '123', '1');
