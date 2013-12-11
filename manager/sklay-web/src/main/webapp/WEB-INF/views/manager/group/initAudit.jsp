<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<form class="form-horizontal" action="${ctx}/admin/group/audit" method="post">
     <div class="control-group">
       <label class="control-label" for="inputName">分组名称</label>
       <div class="controls">
         <input class="span3" type="text" id="inputName" placeholder="分组名称" value="${model.name }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputDescription">分组说明</label>
       <div class="controls">
         <textarea rows="3" cols="4" class="span3" id="inputDescription" placeholder="分组说明" ${ readonly}>${model.description }</textarea>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputStatus">审核状态</label>
       <div class="controls">
       	<input type="hidden" name="groupId" id="groupId" value="${model.id }">
         <label class="radio inline span1">
		  <input type="radio" name="auditStatus" <c:if test="${model.status.value eq 1}"> checked</c:if> id="inputStatus1" value="<%=AuditStatus.PASS.getValue() %>"><%=AuditStatus.PASS.getLable() %>
		 </label>
		<label class="radio inline span1">
		  <input type="radio" name="auditStatus" <c:if test="${model.status.value eq 2}"> checked</c:if> id="inputStatus2" value="<%=AuditStatus.NOT.getValue() %>"><%=AuditStatus.NOT.getLable() %>
		</label>
       </div>
    </div>
</form>