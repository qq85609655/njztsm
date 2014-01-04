<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="globalSet" value="${golbalSetting}" scope="request"/>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>${globalSet.webSite }</title>
		<meta content="width=device-width, initial-scale=1.0" name="viewport">
		<meta content="${globalSet.webDescription }" name="description">
		<meta content="${globalSet.webAuthor }" name="author">
		 ${globalSet.webMeta } 
		<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap/css/bootstrap-responsive.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/base.css">
		<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
	    <!--[if lt IE 9]>
	      <script src="${ctx}/static/js/html5shiv.js"></script>
	      <script src="${ctx}/static/js/respond.min.js"></script>
	    <![endif]-->
		<script type="text/javascript" src="${ctx}/static/thirdparty/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/jquery.scrollUp.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
<style>
.controls .span1{
padding-left:0px;
}
</style>
	</head>
	<body>
		<tiles:insertAttribute name="header" ignore="true" />
		<div id="wrap">
			<div class="container">
				<tiles:insertAttribute name="content" ignore="true"/>
	    	</div>
	    	<div id="push"></div>
    	</div>
		<tiles:insertAttribute name="footer" ignore="true" />
		<shiro:notAuthenticated>
			<script type="text/javascript" src="${ctx}/static/js/login.js"></script>
		</shiro:notAuthenticated>
		<shiro:user>
			<script type="text/javascript" src="${ctx}/static/js/admin/manager.js"></script>
		</shiro:user>
	 </body>
</html>