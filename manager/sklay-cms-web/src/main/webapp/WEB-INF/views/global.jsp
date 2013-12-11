<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
<c:if test="${not empty currentPage.customCss}">
	<style>
		${currentPage.customCss}
	</style>
</c:if>

<!-- navbar start -->
<c:set var="navPage" value="${currentPage.parent==null?currentPage:currentPage.parent}" scope="request"/>
<div class="navbar navbar-static-top">
	<div class="navbar-inner">
		<div class="container">
			<a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">菜单</a>
			<a href="/" class="brand">SKLAY</a>
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
					</ul>
					<shiro:guest>
			        	<ul class="nav pull-right">
						    <li><button class="btn btn-link" type="button" data-toggle="modal" href="${ctx}/regist" data-target="#registModal">Regist</button></li>
						    <li><button class="btn btn-link" type="button" data-toggle="modal" href="${ctx}/login" data-target="#loginModal">Login</button></li>
					    </ul>
				    </shiro:guest>
				    <shiro:user>
				    	<ul class="nav pull-right">
				    		<li class="dropdown">						
								<a data-toggle="dropdown" class="dropdown-toggle" href="#">
									<i class="icon-cog"></i>
									Settings
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="javascript:;">Account Settings</a></li>
									<li><a href="javascript:;">Privacy Settings</a></li>
									<li class="divider"></li>
									<li><a href="javascript:;">Help</a></li>
								</ul>						
							</li>
							<li class="dropdown">						
								<a data-toggle="dropdown" class="dropdown-toggle" href="#">
									<i class="icon-user"></i> 
									<shiro:principal property="name" type="com.sklay.model.User"/>
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="javascript:;">My Profile</a></li>
									<li><a href="javascript:;">My Groups</a></li>
									<li class="divider"></li>
									<li><a href="${ctx}/logout">Logout</a></li>
								</ul>						
							</li>
				    	</ul>
				    </shiro:user>
				</div>
			</div>
		</div>	
	</div>
	<!-- navbar end -->

<div id="wrap" class="container">
	<div id="content" class="row">
		<tiles:insertAttribute name="content" />
	</div>
	
	<div id="footer"></div>
</div>