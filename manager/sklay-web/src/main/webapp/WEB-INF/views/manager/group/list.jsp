<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<div class="widget-box">
	<div class="widget-content tab-content">
		<ul class="nav nav-pills" style="margin-bottom: 0px;">
			<li>
				<div class="control-group span2">
					<form action="${ctx }/admin/group/list" method="post">
						<div class="input-prepend">
						  	<span class="add-on" data-name="tooltip" data-toggle="tooltip" data-original-title="【名称、备注】"><abbr class="initialism">关键字</abbr></span>
						    <input type="text" name="keyword" class="span3" value='${keyword }'>
						    <button type="submit" class="btn btn-info"><i class="icon-search"></i>搜索</button>
						</div>
					</form>
				</div>
				<shiro:hasPermission name="group:create">
				<div class="control-group span2 offset4">
		           <a class="modal-link  btn btn-info" data-target="#modalTemplate" data-title="创建新分组" href="${ctx}/admin/group/initCreate">创建分组</a>
				</div>
				</shiro:hasPermission>
			</li>
		</ul>
		
		<div class="tab-pane active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>编号</th>
						<th>名称</th>
						<th>隶属于</th>
						<th>成员数</th>
						<th>审核</th>
						<th>备注</th>
						<th colspan="4">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${pageModel.totalElements==0}">
							<tr>
								<td colspan="5">还未添加过相关记录 
								<shiro:hasPermission name="group:create">
								   ,  <a class="modal-link" data-target="#modalTemplate" data-title="创建新分组" href="${ctx}/admin/group/initCreate">加一个</a> ?
								</shiro:hasPermission>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${pageModel.content}" var="model">
								<tr>
									<td>${model.id}</td>
									<td class="taskDesc">${model.name}</td>
									<td class="taskDesc">${model.owner.name}</td>
									<td class="taskDesc">${model.memberCount}</td>
									<td class="taskDesc">${model.status.lable}</td>
									<td>${model.description}</td>
									<td colspan="4">
										<shiro:hasPermission name="group:update">
											<a class="modal-link" data-target="#modalTemplate" data-title="编辑" href="${ctx}/admin/group/initUpdate/${model.id}">编辑</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="group:audit">
											| <a class="modal-link" data-target="#modalTemplate" data-title="审核" href="${ctx}/admin/group/initAudit/${model.id}">审核</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="group:author">
											| <a href="${ctx }/admin/group/initAuthor/${model.id}">授权</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="group:delete">
										<c:if test="${(model.memberCount eq 0)}">
											<c:if test="${!(model.status.value eq 1)}">
												| <a class="modal-link" data-target="#modalTemplate" data-title="删除分组" data-delete="true" data-msg="您确定要删除吗?" href="javascript:void(0);" data-href="${ctx}/admin/group/delete/${model.id}">删除</a> 
											</c:if>
										</c:if>
										</shiro:hasPermission>
										<shiro:hasPermission name="group:give">
											| <a class="modal-link" data-target="#modalTemplate" data-title="分配分组" href="${ctx}/admin/group/initGive/${model.id}">分配</a>
										</shiro:hasPermission>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<c:if test="${pageModel.totalPages>1}">
				<c:set var="page" value="${pageModel}" scope="request"/>
				<c:set var="pageQuery" value="${pageQuery}" scope="request"/>
				<%@include file="../../pagination.jsp"%>
			</c:if>
		</div>
	</div>
</div>