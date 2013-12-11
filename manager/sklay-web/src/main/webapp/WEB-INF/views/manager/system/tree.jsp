<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<!-- CSS Files -->
<link type="text/css" href="${ctx}/static/thirdparty/tree/Spacetree.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/static/thirdparty/tree/base.css" rel="stylesheet" />
<!--[if IE]><script language="javascript" type="text/javascript" src="${ctx}/static/thirdparty/tree/excanvas.js"></script><![endif]-->

<!-- JIT Library File -->
<script type="text/javascript" src="${ctx}/static/thirdparty/tree/jit.js"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/tree/treeData.js"></script>
<script>
$(function(){
	init('${treeJson}','${ctx}') ;
}) ;
</script>
<style>
#wrap{
	padding-bottom: 8%;
}
</style>
<div class="widget-box">	
	<div class="widget-content">
		<div class="control-group">
			<input type="button" data-lay="lay" class="btn btn-success" data-value="left" value="left">
			<input type="button" data-lay="lay" class="btn btn-success" data-value="right" value="right">
			<input type="button" data-lay="lay" class="btn btn-success" data-value="top" value="top">
			<input type="button" data-lay="lay" class="btn btn-success" data-value="bottom" value="bottom">
		</div>
		<div id="container" class="container">
			<div id="center-container">
			    <div id="infovis"></div>    
			</div>
			<div id="log" style="left: 10%;"></div>
		</div>
	</div>
</div>