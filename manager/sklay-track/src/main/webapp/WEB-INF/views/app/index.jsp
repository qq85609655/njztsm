<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>app</title>
    <meta name="tab" content="app"/>
    <%@ include file="../common/header.jsp" %>
</head>
<body>
<table class="list-table" style="width: 100%;">
    <caption>
        <a href="${ctx}/console/app/edit" class="a-btn">新建应用</a>
    </caption>
    <tr>
        <th>id</th>
        <th>名称</th>
        <th>类型</th>
        <th>域名</th>
        <th>会话域</th>
        <th>说明</th>
        <th style="width: 50px;">启用</th>
        <th>事件数</th>
        <th style="width:220px;">操作</th>
    </tr>
    <c:forEach items="${apps}" var="app">
        <tr>
            <td><c:out value="${app.id}"/></td>
            <td><c:out value="${app.name}"/></td>
            <td><c:out value="${app.type}"/></td>
            <td><c:out value="${app.attrs['domain']}"/></td>
            <td><c:out value="${app.attrs['sDomain']}"/></td>
            <td><c:out value="${app.description}"/></td>
            <td><c:out value="${app.enabled}"/></td>
            <td><c:out value="${events[app.id]}"/></td>
            <td>
                <a href="${ctx}/console/app/edit?id=${app.id}">编辑</a>&nbsp;
                <a href="${ctx}/console/event?appId=${app.id}">查看报告</a>&nbsp;
                <a href="${ctx}/console/app/code?id=${app.id}">获取代码</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
