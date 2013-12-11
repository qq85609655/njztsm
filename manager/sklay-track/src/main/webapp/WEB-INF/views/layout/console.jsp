<%@ page import="com.sklay.track.util.SitemeshHelper" %>
<%--@elvariable id="_meta" type="java.util.map"--%>
<%@ page session="false" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% SitemeshHelper.extractMeta(pageContext); %>
<c:set var="ctx" value="${pageContext.servletContext.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Track Console - ${_title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link href="${ctx}/static/css/main.css" type="text/css" media="screen" rel="stylesheet"/>
    <link href="${ctx}/static/css/console.css" type="text/css" media="screen" rel="stylesheet"/>
    <link href="${ctx}/static/js/thirdparty/jquery/jquery.validationEngine.css" type="text/css" media="screen" rel="stylesheet"/>
    <script src="${ctx}/static/js/thirdparty/jquery/jquery.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/thirdparty/jquery/jquery.validationEngine.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/console.js" type="text/javascript"></script>
    ${_head}
</head>
<body>
<div id="container">
    <div id="header">
        <h1 style="float: left;">Track Console</h1>
        <ul class="clearfix">
            <li${_meta.tab=='app'?' class="active"':''}><a href="${ctx}/console/app">应用管理</a></li>
            <li${_meta.tab=='dict'?' class="active"':''}><a href="#${ctx}/console/dict">字典管理</a></li>
            <%--<li${_meta.tab=='cache'?' class="active"':''}><a href="${ctx}/console/cache">缓存</a></li>--%>
            <li${_meta.tab=='online'?' class="active"':''}><a href="${ctx}/console/online">在线用户</a></li>
            <li${_meta.tab=='event'?' class="active"':''}><a href="${ctx}/console/event">最新事件</a></li>
        </ul>
    </div>
    <div id="tbody">
        <c:choose>
            <c:when test="${empty ret}"/>
            <c:when test="${ret}">
                <div class="ok">保存成功!</div>
            </c:when>
            <c:otherwise>
                <div class="error">${msg}</div>
            </c:otherwise>
        </c:choose>
        ${_body}
    </div>
</div>
<script src="http://atk.any123.com/atk/boot/2" type="text/javascript" async="async" defer="defer"></script>
</body>
</html>