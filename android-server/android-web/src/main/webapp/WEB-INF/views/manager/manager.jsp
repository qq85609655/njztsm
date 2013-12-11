<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>

<link rel="stylesheet" href="${ctx}/static/thirdparty/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.js"></script>

<style>
	#widget-news-table td{
		vertical-align:middle;
	}
	
	#widget-news-table input{
		margin:0px;
	}
</style>
<div class="row">
	<section class="span3 bs-docs-sidebar">
		<div class="nav hide">
			<a id="saveTypeBtn" class="btn" data-toggle='modal' data-target='#saveType' href="/action/89/pushs/saveTypeView">新增</a>
			<a id="editTypeBtn" class="btn" disabled='disabled' data-toggle='modal' data-target='#editType' href="/action/89/pushs/saveTypeView?id=0">修改</a>
			<a id="removeTypeBtn" class="btn" disabled='disabled' href="/action/89/pushs/removeType?id=0">删除</a>
		</div>
	<!-- <ul class=" nav nav-list bs-docs-sidenav affix" style="width: 15%;top: 6%;z-index: 10000000;"> -->
		<ul class="nav nav-list bs-docs-sidenav">
			<li class="dropdown-submenu <c:if test="${empty nav}">active</c:if>"><a href="${ctx}/supervise/">所有文章</a>
			<c:forEach items="${types }" var="type">
				<li class="dropdown-submenu <c:if test="${type.id eq nav}">active</c:if>"><a href="${ctx}/supervise/news/page?newsTypeId=${type.id}">${type.attachment }</a>
					<c:if test="${type.id eq nav}">
						<c:set var="breadcrumb" value="${type.attachment}" scope="request"/>
					</c:if>
				</li>
			</c:forEach>
		</ul>
	</section>
	<section class="span9">
		<div class="row-fluid">
			<div class="span6">
				<form class="form-search" method="get" action="${ctx}/supervise/news/page">
					<div class="input-append">
				 		<input type="hidden" name="newsTypeId" value="${nav}">
				   	 	<input type="text" class="search-query" name="keyword" value="${keyword}">
				    	<button type="submit" class="btn">搜索</button>
				  	 </div>
				</form>
			</div>
			<div class="pull-right">
				<button autocomplete="off" class="btn" id="widget-news-add">新增</button>
				<button autocomplete="off" class="btn" disabled='disabled' id="widget-news-edit" rel="${ctx}/supervise/news/get">修改</button>
				<c:if test="${ not empty nav}">
					<button autocomplete="off" class="btn" disabled='disabled' id="widget-news-remove" rel="${ctx}/supervise/news/category/delete">删除</button>
					<button autocomplete="off" class="btn" disabled='disabled' id="widget-news-pass" rel="${ctx}/supervise/news/category/audit/pass">审核通过</button>
					<button autocomplete="off" class="btn" disabled='disabled' id="widget-news-nopass" rel="${ctx}/supervise/news/category/audit/not">审核不通过</button>
				</c:if>
			</div>
		</div>
		<table class="table table-bordered table-striped table-hover" id="widget-news-table">
				<tr>
					<th class="span2"><input type="checkbox" class="no-margin">编号</th>
					<th class="span3">标题</th>
					<th class="span2">分类</th>
					<c:if test="${ not empty nav}">
					<th class="span2">状态</th>
					</c:if>
					<th class="span3">创建时间</th>
					<th class="span2">作者</th>
					<c:if test="${empty nav }">
					<th class="span2">操作</th>
					</c:if>
				</tr>
				<c:choose>
				<c:when test="${pageModel.totalElements==0}">
					<tr>
						<td colspan="5">还未添加过相关记录.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${modelList}" var="model" varStatus="status">
						<tr class="form-horizontal" newsId="${model.newsCategory.news.id}-${model.newsCategory.newsType.id}">
							<td><input autocomplete="off" type="checkbox" class="no-margin" newsId="${model.newsCategory.news.id}-${model.newsCategory.newsType.id}"></td>
							<td><a class="modal-link" target="blank" href="${ctx }/news/detail/${model.newsCategory.news.id}?ver=${model.newsCategory.news.ver}">${model.newsCategory.news.title}</a> </td>
							<td>
								<c:forEach items="${mapsTypes[model.newsCategory.news.id] }" var="newsType" varStatus="status">
									${newsType.attachment} <c:if test="${(status.index +1 ) < fn:length(mapsTypes[model.newsCategory.news.id]) }">,</c:if>
								</c:forEach>
							</td>
							<c:if test="${ not empty nav}">
							<td>${model.status.lable }</td>
							</c:if>
							<td><fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${model.newsCategory.news.pubDate}" /></td>
							<td>${model.newsCategory.news.creator.nickName }</td>
							<c:if test="${empty nav}">
								<td><a class="modal-link" href="${ctx }/supervise/news/delete/${model.newsCategory.news.id}">刪除</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
		<c:if test="${pageModel.totalPages>1}">
			<c:set var="page" value="${pageModel}" scope="request"/>
			<c:set var="pageQuery" value="${pageQuery}" scope="request"/>
			<%@include file="../pagination.jsp"%>
		</c:if>
		
		<form action="${ctx}/supervise/news/add" method="post" id="newsManagerForm">
			<input type="hidden" name="newsCategory.news.id" id="newsId">
			<fieldset>
				<legend>编辑内容</legend>
				<c:if test="${!(empty error) }">
					<div class="alert alert-error"><strong>${error }</strong></div>
				</c:if>
				
				<label>标题</label>
				<input type="text" name="newsCategory.news.title" id="newsTitle" placeholder="必填">
			
				<label id="categoryLabel">分类</label>
				<select id="categorySelect" name="newsCategory.newsType.id" autocomplete="off">
					<c:forEach items="${types }" var="type">
						<option value="${type.id}">${type.attachment}</option>
					</c:forEach>
				</select>
									
				<label>内容</label>
				<textarea name="newsCategory.news.body" style="width:100%;height:250px;"></textarea>
				<hr>
				
				<button type="submit" class="btn btn-primary">发布</button>
			</fieldset>
			
		</form>
	</section>
</div>

<script>
	var newsParam = {};
	newsParam.cssPath = '${ctx}/static/thirdparty/kindeditor/plugins/code/prettify.css';
	newsParam.uploadJson = '${ctx}/file/textEditor/upload';
	newsParam.fileManagerJson = '${ctx}/file/textEditor';
	newsParam.widgetId = '-news';
	newsParam.newsType = '0';
	newsParam.deleteNews = '${ctx}/supervise/news/delete';
	newsParam.removeType = '${ctx}/supervise/news/removeType';
	newsParam.topNews = '${ctx}/supervise/news/top';
	newsParam.editNews = '${ctx}/supervise/news/edit';
	newsParam.addNews = '${ctx}/supervise/news/add';
	newsParam.reloadUrl = '${ctx}/supervise/news/page?newsTypeId=';
	newsParam.changeTypeUrl = '${ctx}/supervise/news/changeType';
</script>
<script charset="utf-8" src="${ctx}/static/js/manager.js"></script>