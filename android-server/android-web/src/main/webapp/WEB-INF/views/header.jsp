<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jsp" %>
<div class="navbar">
  <div class="navbar-inner">
    <div class="container">
      <a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
      <a href="${ctx }/" class="brand"><strong>健康管家</strong></a>
      <div class="nav-collapse collapse">
        <shiro:guest>
        	<ul class="nav pull-right">
			    <li><button class="btn btn-link" type="button" data-toggle="modal" href="${ctx}/login" data-target="#loginModal">登录</button></li>
		    </ul>
	    </shiro:guest>
	    <shiro:user>
	    	<ul class="nav pull-right">
	    		<li>						
					<button class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/public/logout';">注销</button>
				</li>
	    	</ul>
	    </shiro:user>
      </div><!--/.nav-collapse -->
    </div>
  </div>
</div>
<tiles:insertAttribute name="subheader" ignore="true" />