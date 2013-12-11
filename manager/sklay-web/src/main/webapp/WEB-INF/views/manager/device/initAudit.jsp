<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<form class="form-horizontal" action="${ctx}/admin/device/audit" method="post">
     <div class="control-group">
       <label class="control-label" for="inputName">邦定者姓名</label>
       <div class="controls">
         <input class="span3" type="text" id="name" id="inputName" placeholder="未填写真实姓名" value="${model.targetUser.name }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputPhone">邦定者手机</label>
       <div class="controls">
         <input class="span3" type="tel" name="phone" id="inputPhone" placeholder="未填写手机号码" value="${model.targetUser.phone }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputStatus">审核状态</label>
       <div class="controls">
       	<input type="hidden" name="id" id="targetUserId" value="${model.id }">
         <label class="radio inline span1">
		  <input type="radio" name="auditStatus" <c:if test="${model.status.value eq 1}"> checked</c:if> id="inputStatus1" value="<%=AuditStatus.PASS %>"><%=AuditStatus.PASS.getLable() %>
		 </label>
		<label class="radio inline span1">
		  <input type="radio" name="auditStatus" <c:if test="${model.status.value eq 2}"> checked</c:if> id="inputStatus2" value="<%=AuditStatus.NOT %>"><%=AuditStatus.NOT.getLable() %>
		</label>
       </div>
    </div>
</form>