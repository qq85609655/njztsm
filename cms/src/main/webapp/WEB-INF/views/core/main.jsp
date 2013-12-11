<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${currentPage.title}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="${currentPage.description}">
<meta name="keywords" content="${currentPage.keywords}">
<c:set var="ctx" value="<%=request.getContextPath()%>" scope="request"/>
<c:set var="rs" value="${ctx}/static" scope="request"/>
<!-- html5 -->
<script type="text/javascript" src="${ctx}/static/js/html5.js"></script>
<script src="${rs}/thirdparty/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctx}/static/thirdparty/jquery/jquery.json.js"></script>
<script src="${rs}/thirdparty/bootstrap/js/bootstrap.min.js"></script>
<script src="${rs}/js/base.js"></script>

<link rel="stylesheet" type="text/css" href="${rs}/css/cssreset-min.css">
<link rel="stylesheet" type="text/css" href="${rs}/thirdparty/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${rs}/thirdparty/bootstrap/css/bootstrap-responsive.css">
<link rel="stylesheet" type="text/css" href="${rs}/css/widget.css?ver=${global.ver}">
<link rel="stylesheet" type="text/css" href="${rs}/css/global.css?ver=${global.ver}">

<link rel="apple-touch-icon-precomposed" sizes="144x144" href="${rs}/ico/favicon.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="${rs}/ico/favicon.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="${rs}/ico/favicon.png">
<link rel="apple-touch-icon-precomposed" href="${rs}/ico/favicon.png">
<link rel="shortcut icon" href="${rs}/ico/favicon.png">

<!-- admin area start-->
<shiro:hasRole name="superadmin">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/admin.css?ver=${global.ver}">
	<script src="${ctx}/static/thirdparty/jquery/jquery-ui.min.js"></script>
	<script src="${ctx}/static/thirdparty/ace/ace.js"></script>
	<script src="${ctx}/static/js/admin.js?ver=${global.ver}"></script>
	<script>
		$(function(){
			$.admin({ctx:'${ctx}',currentPageId:'${currentPage.id}'});
		});
	</script>
</shiro:hasRole>
<!-- admin area end -->
<!-- Baidu Button BEGIN -->
<script type="text/javascript" id="bdshare_js" data="type=slide&img=2&pos=right&uid=6810405" ></script>
<script type="text/javascript" id="bdshell_js"></script>
<script type="text/javascript">
document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
</script>
<!-- Baidu Button END -->
</head>
<body>
	<tiles:insertAttribute name="content" />
</body>
</html>