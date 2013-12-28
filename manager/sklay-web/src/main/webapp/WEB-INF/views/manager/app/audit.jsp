<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<form class="form-horizontal" action="${ctx}/admin/app/audit" method="post">
     <div class="control-group">
       <label class="control-label" for="inputName">申请者姓名</label>
       <div class="control-label"><label class="inline info"><strong>${model.creatorUser.name }</strong></label></div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputPhone">申请者手机</label>
       <div class="control-label success"><label class="inline"><strong>${model.creatorUser.phone }</strong></label></div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputStatus">审核状态</label>
       <div class="controls">
       	<input type="hidden" name="id" id="id" value="${model.id }">
         <label class="radio inline span1" for="inputStatus1">
		  <input type="radio" name="status" <c:if test="${model.status.value eq 1}"> checked</c:if> id="inputStatus1" value="<%=AuditStatus.PASS %>"><%=AuditStatus.PASS.getLable() %>
		 </label>
		<label class="radio inline span1" for="inputStatus2">
		  <input type="radio" name="status" <c:if test="${model.status.value eq 2}"> checked</c:if> id="inputStatus2" value="<%=AuditStatus.NOT %>"><%=AuditStatus.NOT.getLable() %>
		</label>
       </div>
    </div>
</form>