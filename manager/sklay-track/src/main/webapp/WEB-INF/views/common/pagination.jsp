<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${page.pageCount>1}">
    <div class="pagination-info clearFix">
        <span title='共<c:out value="${page.total}"/>条记录 分<c:out value="${page.pageCount}"/>页显示'>
            <c:out value="${page.total}"/>/<c:out value="${page.pageCount}"/>
        </span>
        <c:if test="${page.index>1}">
            <a href="${param.url}&index=1">首页</a>
            <a href="${param.url}&index=${page.index-1}">上一页</a>
        </c:if>
        <c:forEach items="${page.showPages}" var="p">
            <c:choose>
                <c:when test="${p==page.index}">
                    <div>${p}</div>
                </c:when>
                <c:otherwise>
                    <a href="${param.url}&index=${p}" title='点击跳转到第${p}页'>${p}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${page.index<page.pageCount}">
            <a href="${param.url}&index=${page.index+1}">下一页</a>
            <a href="${param.url}&index=${page.pageCount}">尾页</a>
        </c:if>
    </div>
</c:if>