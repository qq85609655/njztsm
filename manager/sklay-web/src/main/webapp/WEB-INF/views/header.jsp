<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp" %>

<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      <a href="${ctx }/admin" class="brand">后台管理</a>
      <div class="nav-collapse collapse">
	    <shiro:user>
	    	<ul class="nav pull-right">
	    		<li class="dropdown">						
					<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0);">
						<i class="icon-cog"></i>设置<b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li><a class="modal-link" data-target="#modalTemplate" data-title="个人设置" href="${ctx}/admin/account/initProfile/<shiro:principal property="id" type="com.sklay.model.User"/>">个人设置</a></li>
						<li><a class="modal-link" data-target="#modalTemplate" data-title="密码修改" href="${ctx}/admin/account/initPwd/<shiro:principal property="id" type="com.sklay.model.User"/>">密码修改</a></li>
					</ul>						
				</li>
				<li class="dropdown">						
					<a data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0);">
						<i class="icon-user"></i> 
						<shiro:principal property="name" type="com.sklay.model.User"/>
						<b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li><a class="modal-link" data-target="#modalTemplate" data-nofooter="true" data-title="我的小组" href="${ctx}/admin/account/group">我的小组</a></li>
						<li class="divider"></li>
						<li><a href="${ctx}/logout">登出</a></li>
					</ul>						
				</li>
				<li>						
					<a href="${ctx }/" target="blank">
						<i class="icon-home"></i>查看首页
					</a>
				</li>
	    	</ul>
	    </shiro:user>
      </div><!--/.nav-collapse -->
    </div>
  </div>
</div>
<tiles:insertAttribute name="subheader" ignore="true" />
