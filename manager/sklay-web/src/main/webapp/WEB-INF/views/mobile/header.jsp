<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>
<script>
$(function(){
	
	function verifyHandset(str) {
	    //  var reg = /^(\+86)|(86)?1[3,5,4,8]{1}[0-9]{1}[0-9]{8}$/;
	    var reg = /^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$/ ;
	      if( reg.test(str)) {
	          return true;
	      } else {
	          return false;
	      }
	   }
	
	$('#but').click(function(){
		var val =  $('#phone').val() ;
	//	alert(verifyHandset(val)) ;
	}) ;
});


</script>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
  	<div class="container">
     <%-- <a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar btn-info">${loginView.menus }</a> --%>
      <a href="javascript:void(0);" class="brand">3G智能健康服务机</a>
  	</div>
  </div>
</div>
<div class="alert">
<form action="${ctx}/android/chart" method="post" class="navbar-form form-search">
	 <div class="input-append">
	    <input type="text" name="phone" id="phone" class="span2 search-query" placeholder="请输入要查询的主手机号..." required="required">
	    <button type="submit" class="btn" id="but"> 查询</button>
	 </div>
</form>
</div>
