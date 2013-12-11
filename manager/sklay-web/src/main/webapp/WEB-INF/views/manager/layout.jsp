<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<style>

.dropdown-submenu:hover > a {
  color: #ffffff;
  text-decoration: none;
  background-color: #0081c2;
  background-image: -moz-linear-gradient(top, #0088cc, #0077b3);
  background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#0088cc), to(#0077b3));
  background-image: -webkit-linear-gradient(top, #0088cc, #0077b3);
  background-image: -o-linear-gradient(top, #0088cc, #0077b3);
  background-image: linear-gradient(to bottom, #0088cc, #0077b3);
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff0088cc', endColorstr='#ff0077b3', GradientType=0);
}
#container{
		min-height: 797px;
	}
</style>
<div class="row">
	<section class="span3 bs-docs-sidebar">
	<!-- <ul class=" nav nav-list bs-docs-sidenav affix" style="width: 15%;top: 6%;z-index: 10000000;"> -->
		<ul class=" nav nav-list bs-docs-sidenav" style="z-index: 10000000;">
			<c:set var="subbreadcrumb" value="" scope="request"/>
			<sklay:menuTag>
				<c:forEach items="${menus}" var="menu">
					<li class="dropdown-submenu <c:if test="${menu.nav eq nav}">active</c:if>"><a href="${menu.href }">${menu.title }</a>
						<c:if test="${menu.nav eq nav}">
							<c:set var="breadcrumb" value="${menu.title }" scope="request"/>
						</c:if>
						<c:if test="${!empty(menu.child) }">
							<ul class="dropdown-menu">
								<c:forEach items="${menu.child }" var="child">
									<li class="<c:if test="${child.nav eq subnav}">active</c:if>"><a href="${ctx}${child.href}">${child.title }</a>
									<c:if test="${child.nav eq subnav}">
										<c:set var="subbreadcrumb" value="${child.title }" scope="request"/>
									</c:if>
								</c:forEach>
				    		</ul>
						</c:if>
					</li>
				</c:forEach>
			</sklay:menuTag>
			<sklay:hasRole value="1">
			<li class="dropdown-submenu <c:if test="${'myApp' eq nav}">active</c:if>"><a href="${ctx }/app/page">我的应用</a>
				<c:if test="${'myApp' eq nav}">
					<c:set var="breadcrumb" value="我的应用" scope="request"/>
				</c:if>
			</li>
			</sklay:hasRole>
			<li class="dropdown-submenu <c:if test="${'payApply' eq nav}">active</c:if>"><a href="${ctx }/app/list"><strong>付费应用</strong></a>
				<c:if test="${'payApply' eq nav}">
					<c:set var="breadcrumb" value="付费应用" scope="request"/>
				</c:if>
			</li>
		 </ul>
	</section>
	<section class="span9">
		<div id="container">
			<ul class="breadcrumb">
				<c:if test="${ empty(breadcrumb)}">
					<li>欢迎进入系统 !</li>
				</c:if>
				<c:if test="${ not empty breadcrumb }">
					<li>
					<c:if test="${ empty href }">
						${breadcrumb}<span class="divider">/</span>
					</c:if><c:if test="${ not empty href }">
					<a href="${ctx}${href}">${breadcrumb }</a><span class="divider">/</span>
					</c:if>
					</li>
				</c:if>
				<c:if test="${ empty(thirdbreadcrumb)}">
				  	<li>${subbreadcrumb }</li>
				</c:if>
				<c:if test="${ not empty(thirdbreadcrumb)}">
					<c:if test="${not empty subbreadcrumb }">
						<li><a href="${ctx}${subHref}">${subbreadcrumb }</a><span class="divider">/</span></li>
					</c:if>
				  	<li>${thirdbreadcrumb }</li>
				</c:if>
			</ul>
			<tiles:insertAttribute name="right" ignore="true"/>
		</div>
	</section>
</div>