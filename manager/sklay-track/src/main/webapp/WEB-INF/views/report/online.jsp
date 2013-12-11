<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>report</title>
    <meta name="tab" content="online"/>
    <%@ include file="../common/header.jsp" %>
</head>
<body>
    <table class="list-table" style="width: 100%;">
        <caption>
            在线用户列表
        </caption>
        <tr>
            <th>id</th>
            <th>开始时间</th>
            <th>ip</th>
            <th>最后访问地址</th>
            <th>标题</th>
            <th>页面加载时间</th>
        </tr>
        <c:forEach items="${onlines}" var="event">
            <tr>
                <td><c:out value="${event.id}"/></td>
                <td><fmt:formatDate value="${event.beginAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><c:out value="${clients[event.clientId].attrs.ip}"/></td>
                <td><c:out value="${events[event.eventId].url}"/></td>
                <td><c:out value="${events[event.eventId].attrs['title']}"/></td>
                <td><c:out value="${events[event.eventId].attrs['lt']}"/></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
