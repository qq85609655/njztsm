<%@page import="com.sklay.core.enums.BindingMold"%>
<%@page import="com.sklay.core.enums.Level"%>
<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@page import="com.sklay.core.enums.MemberRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<form class="form-horizontal" action="${ctx}/admin/device/update" method="post">
     <div class="control-group">
       <label class="control-label" for="inputName">设备号码</label>
       <div class="controls">
       		<input class="span3" type="hidden" name="id" id="id"value="${model.id }" ${ readonly}>
       		
         <input class="span3" type="text" name="serialNumber" id="inputName" placeholder="设备号码" value="${model.serialNumber }" ${ readonly}>
       </div>
     </div>
      <div class="control-group">
	        <label class="control-label" for="inputBindingTypes">帐号类型</label>
	        <div class="controls">
				<label class="radio inline span1">
				  <input type="radio" name="level" id="inputBindingTypes3" value="<%=Level.FIRST %>" <c:if test="${model.level.value eq  0}">checked</c:if> ><%=Level.FIRST.getLable() %>
				</label>
				<label class="radio inline span1">
				  <input type="radio" name="level" id="inputBindingTypes2" value="<%=Level.SECOND %>" <c:if test="${model.level.value eq 1}">checked</c:if> ><%=Level.SECOND.getLable() %>
				</label>
       		</div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputMold">设备绑定类型</label>
       <div class="controls">
         <label class="radio inline span1" for="inputMold1">
		  <input type="radio" name="mold" id="inputMold1" value="<%=BindingMold.FREE %>" <c:if test="${model.mold.value eq 0}">checked</c:if>><%=BindingMold.FREE.getLable() %>
		 </label>
		<label class="radio inline span1" for="inputMold2">
		  <input type="radio" name="mold" id="inputMold2" value="<%=BindingMold.PAID %>" <c:if test="${model.mold.value eq 1}">checked</c:if>><%=BindingMold.PAID.getLable() %>
		</label>
       </div>
     </div>
     <sklay:hasRole value="2">
     <div class="control-group">
	        <label class="control-label" for="inputGroupRole">邦定者Id</label>
	        <div class="controls ">
				<label class=" inline span1">
				  <input type="text" name="targetUserId" id="inputGroupRole2" value="${model.targetUser.id }" ${readonly}>
				</label>
	       </div>
     </div>
     </sklay:hasRole>
</form>