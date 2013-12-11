<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<form class="form-horizontal" action="${ctx}/login" method="post">
	<div class="control-group">
		<label class="control-label" for="username">用户名</label>
		<div class="controls">
			<input type="text" class="email" id="username" placeholder="手机号码" name="username">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="password">密码</label>
		<div class="controls">
			<input type="password" id="password" placeholder="**********" name="password">
		</div>
	</div>
	<div class="control-group">
		<div class="controls">
			<label class="checkbox"><input type="checkbox" name="rememberMe">记住我</label>
			<p>没有帐号? <a href="javascript:;">注册</a>忘记密码? <a href="forgot_password.html">找回它</a></p>
		</div>
	</div>
</form>