<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<c:set var="ctx" value="<%=request.getContextPath()%>" scope="request"/>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>登录页面</title>
		<meta name="description" content="">
		<meta name="author" content="cuongv">
		<meta name="robots" content="index, follow">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
				
		<!-- CSS styles -->
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/cssreset-min.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap/css/bootstrap-responsive.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/login.css">
		
		<!-- JS Libs -->
		<script type="text/javascript" src="${ctx}/static/js/html5.js"></script>
		<script src="${ctx}/static/thirdparty/jquery/jquery-1.9.1.min.js"></script>
		<script src="${ctx}/static/thirdparty/bootstrap/js/bootstrap.min.js"></script>
		
	</head>
	<body class="single-page">
		<div class="bc-social" style="background-color: #af2f22;height: 200px;">
    <div class="container">
          <a href="http://www.3335xxhzl.org/"><img src="${ctx }/static/img/logo1.png" style="float: left;margin-top: 20px;"></a>
          <h1 style="float:left;height:120px; line-height:120px; color:#CF0;font-size:40px;float: left;">全国长寿工程信息化助老行动官方网站</h1>
    </div>
  </div>
	<div class="container">
		<div class="content">
			<div id="login-container">
				<div id="login-header">						
				<c:choose>
					<c:when test="${ empty model }">
					<div class="row-fluid">
								<h3>找回密码</h3>						
							</div> <!-- /login-header -->
			
							<div id="login-content" class="clearfix">
								<form action="${ctx}/reset" method="post">
									<fieldset>
										<div class="control-group">
											<label class="control-label" for="phone">用户名</label>
											<div class="controls">
												<input type="text" class="" id="phone" name="phone" value="">
											</div>
										</div>
									</fieldset>
									<div class="pull-right">
										<button type="submit" class="btn btn-warning btn-large">
											提交
										</button>
									</div>
								</form>							
							</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-success" style="font-size: 14px;">
			              <strong>亲 , ${model.name } ! 您好</strong>  密码重置成功.<br>新密码已发送到您的手机,请查收,不能再丢了哦.<br>
			              	点击<a href="${ctx }/login">登入</a>.
			            </div>
					</c:otherwise>
				</c:choose>
				</div>
	       </div>
		</div>
	</div> <!-- /container -->
	</body>
	</html>		