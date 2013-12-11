<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<ul class="breadcrumb">
  <li><a href="${ctx }/supervise">列表页面</a> <span class="divider">/</span></li>
  <li class="active">详情页面-${model.title }</li>
</ul>
<div class="news-detail" style="width: 100%; height: 100%;">
	<div class="news-detail-bar">
		<h4 class="text-center">${model.title }</h4>
	</div>
	<div class="news-detail-content">
		${model.body }
	</div>
	<div class="news-detail-footer hide">
		<a href="http://SKLAY.NET">SKLAY.NET</a>
	</div>
</div>