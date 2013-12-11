<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap-datetimepicker/css/datetimepicker.css">


<form class="form-horizontal" action="${ctx}/admin/device/moldAudit" method="post">
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
		  <input type="radio" name="moldStatus" <c:if test="${model.moldStatus.value eq 1}"> checked</c:if> id="inputStatus1" value="<%=AuditStatus.PASS %>"><%=AuditStatus.PASS.getLable() %>
		 </label>
		<label class="radio inline span1">
		  <input type="radio" name="moldStatus" <c:if test="${model.moldStatus.value eq 2}"> checked</c:if> id="inputStatus2" value="<%=AuditStatus.NOT %>"><%=AuditStatus.NOT.getLable() %>
		</label>
       </div>
    </div>
    
    <div class="control-group">
       <label class="control-label" for="inputValidTime">有效期起始</label>
       <div class="controls">
	    	<div class="input-append date" id="startTime" data-date="<fmt:formatDate value="${mold.startTime }" pattern="yyyy-MM-dd"/>" data-date-format="yyyy-mm-dd" data-link-field="startTime">
                <input class="dateInput span2" type="text" name="startTime" value="<fmt:formatDate value="${mold.startTime }" pattern="yyyy-MM-dd"/>" readonly="readonly">
                <span class="add-on"><i class="icon-remove"></i></span>
				<span class="add-on"><i class="icon-th"></i></span>
			</div>
		</div>
	</div>
    
    <div class="control-group">
       <label class="control-label" for="inputValidTime">有效期截至</label>
       <div class="controls">
	    	<div class="input-append date" id="endTime" data-date="<fmt:formatDate value="${mold.endTime }" pattern="yyyy-MM-dd"/>" data-date-format="yyyy-mm-dd" data-link-field="endTime">
                <input class="dateInput span2" type="text" name="endTime" value="<fmt:formatDate value="${mold.endTime }" pattern="yyyy-MM-dd"/>" readonly="readonly">
                <span class="add-on"><i class="icon-remove"></i></span>
				<span class="add-on"><i class="icon-th"></i></span>
			</div>
		</div>
	</div>
</form>
<script>
$(function() {
	$('#startTime').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
		minView: 2 ,
		autoclose: 1,
		format: "yyyy-mm-dd",
        todayBtn: true,
		todayHighlight: 1
    });
	
	$('#endTime').datetimepicker({
		language:  'zh-CN',
		weekStart: 1,
		minView: 2 ,
		autoclose: 1,
		format: "yyyy-mm-dd",
		todayHighlight: 1 
	});
	
}) ;

</script>