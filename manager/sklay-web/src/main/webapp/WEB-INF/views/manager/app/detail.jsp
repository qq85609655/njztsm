<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<form class="form-horizontal" action="${ctx}/admin/app/audit" method="post">
     <div class="control-group">
       <label class="control-label">申请者姓名</label>
       <div class="control-label">
       <label class="inline info"><strong>${model.creator.name }</strong></label></div>
     </div>
     <div class="control-group">
       <label class="control-label">申请者手机</label>
       <div class="control-label inline"><strong>${model.creator.phone }</strong></div>
     </div>
     <div class="control-group">
       <label class="control-label">应用名称</label>
       <div class="control-label"><label class="inline"><strong>${model.product.title }</strong></label></div>
     </div>
     <div class="control-group">
       <label class="control-label">单价（元[RMB]）</label>
       <div class="control-label success"><label class="inline"><strong>${model.product.price }</strong></label></div>
     </div>
     <div class="control-group">
       <label class="control-label">付费金额（元[RMB]）</label>
       <div class="control-label"><label class="inline"><strong>${model.cost }</strong></label></div>
     </div>
     
     <div class="control-group">
       <label class="control-label">审核状态</label>
       <div class="controls">
		<label class="control-label success"><strong>${model.status.lable}</strong></label>
       </div>
    </div>
    <div class="control-group">
       <label class="control-label">申请时间</label>
       <div class="control-label success"><label class="inline"><strong><fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${model.createTime}" /></strong></label></div>
     </div>
     
     <div class="control-group">
       <label class="control-label">最后更新人</label>
       <div class="control-label success"><label class="inline"><strong>${model.updator.name}</strong></label></div>
     </div>
     
     <div class="control-group">
       <label class="control-label">最后更新时间</label>
       <div class="control-label success"><label class="inline"><strong><fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${model.updatorTime}" /></strong></label></div>
     </div>
</form>