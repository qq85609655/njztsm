<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@page import="com.sklay.core.enums.MemberRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<form class="form-horizontal" action="${ctx}/admin/group/update" method="post">
     <div class="control-group">
       <label class="control-label" for="inputName">分组名称</label>
       <div class="controls">
       	<input class="span3" type="hidden" name="id" id="id"value="${model.id }" ${ readonly}>
         <input class="span3" type="text" name="name" id="inputName" placeholder="分组名称" value="${model.name }" ${ readonly}>
       </div>
     </div>
     <sklay:hasRole value="2">
     <div class="control-group">
	        <label class="control-label" for="inputAuditStatus">审核状态</label>
	        <div class="controls">
				<label class="radio inline span1">
				  <input type="radio" name="status" id="inputAuditStatus3" value="<%=AuditStatus.PASS %>" <c:if test="${model.status.value eq  1}">checked</c:if> ${readonly}><%=AuditStatus.PASS.getLable() %>
				</label>
				<label class="radio inline span1">
				  <input type="radio" name="status" id="inputAuditStatus2" value="<%=AuditStatus.NOT %>" <c:if test="${model.status.value eq  2}">checked</c:if> ${readonly}><%=AuditStatus.NOT.getLable() %>
				</label>
       		</div>
     </div>
     </sklay:hasRole>
     <div class="control-group">
	        <label class="control-label" for="inputGroupRole">用户角色</label>
	        <div class="controls ">
				<label class="radio inline span1">
				  <input type="radio" name="role" id="inputGroupRole2" value="<%=MemberRole.AGENT %>" <c:if test="${model.role.value eq  1}">checked</c:if> ${readonly}><%=MemberRole.AGENT.getLable() %>
				</label>
				<label class="radio inline span1">
				  <input type="radio" name="role" id="inputGroupRole3" value="<%=MemberRole.MENBER %>" <c:if test="${model.role.value eq  0}">checked</c:if> ${readonly}><%=MemberRole.MENBER.getLable() %>
				</label>
				<sklay:hasRole value="2">
	          	<label class="radio inline span1">
				  <input type="radio" name="role" id="inputGroupRole1" value="<%=MemberRole.ADMINSTROTAR %>" <c:if test="${model.role.value eq  2}">checked</c:if> ${readonly}><%=MemberRole.ADMINSTROTAR.getLable() %>
				</label>
				</sklay:hasRole>
	       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputDescription">分组说明</label>
       <div class="controls">
         <textarea rows="3" cols="4" class="span3" name="description" id="inputDescription" placeholder="分组说明" ${ readonly}>${model.description }</textarea>
       </div>
     </div>
</form>