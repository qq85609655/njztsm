<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
    <link rel="stylesheet" type="text/css" href="${rs}/theme/blue/theme.css">

<c:if test="${not empty currentPage.customCss}">
	<style>
		${currentPage.customCss}
	</style>
</c:if>
<!-- navbar start -->
<c:set var="navPage" value="${currentPage.parent==null?currentPage:currentPage.parent}" scope="request"></c:set>
<div class="navbar navbar-static-top navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">菜单</a>
            <a href="${ctx}/" class=" logo"> </a> 
			<div class="nav-collapse">
				<ul class="nav">
					<c:forEach items="${pages}" var="p" varStatus="status">
						<c:choose>
							<c:when test="${not empty p.children}">
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">${p.name}<b class="caret"></b></a>
									<ul class="dropdown-menu">
										<c:forEach items="${p.children}" var="cp">
											<li><a href="${ctx}/${cp.alias}">${cp.name}</a></li>
										</c:forEach>
									</ul>
								</li>
							</c:when>
							<c:otherwise>
								<li <c:if test="${p.id eq navPage.id}">class="active"</c:if>>
									<a href="${ctx}/${status.first?'':p.alias}">${p.name}</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					    <shiro:guest>
						 	<li class="nav pull-right">
		        				<a href="${ctx}/login">登录</a>
							</li>
				 	    </shiro:guest>
                    </ul>
				</div>
			</div>
		</div>	
	</div>
	<!-- navbar end-->
    

<div id="wrap" class="container">
	<div id="content" class="row">
		<tiles:insertAttribute name="content" ></tiles:insertAttribute>
	</div>
</div>
<footer class="footer">
    <div class="container">
        <p>Designed and built with all the love in the world by <a href="http://twitter.com/mdo" target="_blank">@mdo</a> and <a href="http://twitter.com/fat" target="_blank">@fat</a>.</p>
        <p>Code licensed under <a href="http://www.apache.org/licenses/LICENSE-2.0" target="_blank">Apache License v2.0</a>, documentation under <a href="http://creativecommons.org/licenses/by/3.0/" target="_blank">CC BY 3.0</a>.</p>
        <p><a href="http://glyphicons.com" target="_blank">Glyphicons Free</a> licensed under <a href="http://creativecommons.org/licenses/by/3.0/" target="_blank">CC BY 3.0</a>.</p>
        <p>本网站所列开源项目的中文版文档全部由<a href="http://www.bootcss.com/">Bootstrap中文网</a>成员翻译，并全部遵循 <a href="http://creativecommons.org/licenses/by/3.0/" target="_blank">CC BY 3.0</a>协议发布。</p>
        <ul class="footer-links">
          <li><a href="about.html" target="_blank">关于</a></li>
          <li class="muted">·</li>
          <li><a href="https://github.com/twitter/bootstrap/issues?state=open" target="_blank">问题反馈</a></li>
          <li class="muted">·</li>
          <li><a href="https://github.com/twitter/bootstrap/blob/master/CHANGELOG.md">版本变更记录</a></li>
          <li><a href="http://www.miibeian.gov.cn/" target="_blank">京ICP备11008151号</a></li>
        </ul>
    </div>
</footer>