<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>report</title>
    <meta name="tab" content="event"/>
    <%@ include file="../common/header.jsp" %>
</head>
<body>
    <table class="list-table" style="width: 100%;">
        <caption>
            事件列表
        </caption>
        <tr>
            <th>id</th>
            <th>发生时间</th>
            <th>类型</th>
            <th>事件名</th>
            <th>属性</th>
        </tr>
        <c:forEach items="${actions}" var="action">
            <tr>
                <td><c:out value="${action.id}"/></td>
                <td><fmt:formatDate value="${action.happenAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><c:out value="${action.type}"/></td>
                <td><c:out value="${action.name}"/></td>
                <td><c:out value="${action.attrs}"/></td>
            </tr>
        </c:forEach>
    </table>
    <div style="padding: 10px 0;">
        <input type="button" class="a-btn" value="返回" onclick="location.href='${ctx}/console/event';">
    </div>
</body>
</html>
