<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>
<style>
#scrollUp {
	bottom: 20px;
	right: 20px;
	height: 38px; /* Height of image */
	width: 38px; /* Width of image */
	background: url("${ctx}/static/img/top.png") no-repeat;
}
</style>
<a id="scrollUp" href="#top" title="" style="position: fixed; z-index: 2147483647; display: none;"></a>
<footer id="footer">
  <div class="container">
  <c:if test="${empty copyright }">
	  <div id="div">
			<a href="${ctx }/android" class="gohome"><span>进入首页</span></a>
	  </div>
  </c:if>
  <c:if test="${!(empty copyright) }">
   <p class="credit">
    Create by <a href="mailto('1988fuyu@163.com')">FuYu</a>
    </p>
    <p>${globalSet.copyRight }</p>
  </c:if>
   
  </div>
</footer>


