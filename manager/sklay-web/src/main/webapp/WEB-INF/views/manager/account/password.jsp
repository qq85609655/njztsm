<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<div class="widget-content" id="createForm">
	<form class="form-horizontal" action="${ctx }/admin/account/password" method="post">
	      <div class="control-group">
	        <label class="control-label" for="inputOldPwd">输入原密码</label>
	        <div class="controls">
	          <input type="hidden" name="userId" id="inputId" value="${userId }">
	          <input class="span3" type="password" name="oldPwd" id="inputOldPwd" placeholder="输入原密码">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputNewPwd">输入新密码</label>
	        <div class="controls">
	          <input class="span3" type="password" name="newPwd" id="inputNewPwd" placeholder="再次输入新密码">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputNewPwd1">再次输入新密码</label>
	        <div class="controls">
	          <input class="span3" type="password" name="newPwd" id="inputNewPwd1" placeholder="再次输入新密码">
	        </div>
	      </div>
     </form>
</div>