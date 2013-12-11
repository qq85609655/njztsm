<%@page import="com.sklay.core.enums.BindingMold"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap-datetimepicker/css/datetimepicker.css">

<script type="text/javascript">
    $(document).ready(function() {
 	    $('.icon-remove').on('click',function(){
 	    	if($('.icon-remove').size()>0){
 	    		$(this).parent().remove() ;
 	    	}
 	    });
    });
</script>

<form class="form-horizontal" action="${ctx}/admin/member/${action}" method="post">
	 <div class="control-group">
       <label class="control-label">绑定者</label>
       <div class="controls">
       	<c:forEach items="${owners }" var="owner">
	         <label class=" span3 control-group">
			  <input type="hidden" name="userId" value="${owner.id }"/>
				 <i class="icon-user"></i>${owner.name} <i class="icon-hand-right"></i> ${owner.phone}
			  <c:if test="${fn:length(owners) > 1 }">
				  <i class="icon-remove"></i>
			  </c:if>
			 </label>
         </c:forEach>
       </div>
     </div>
     <c:if test="${'multipleBinding' eq action }">
	 <div class="control-group">
       <label class="control-label" for="inputMold">设备绑定类型</label>
       <div class="controls">
         <label class="radio inline span1" for="inputMold1">
		  <input type="radio" name="mold" id="inputMold1" value="<%=BindingMold.FREE %>" checked><%=BindingMold.FREE.getLable() %>
		 </label>
		<label class="radio inline span1" for="inputMold2">
		  <input type="radio" name="mold" id="inputMold2" value="<%=BindingMold.PAID %>"><%=BindingMold.PAID.getLable() %>
		</label>
       </div>
     </div>
     </c:if>
     <div class="control-group">
       <label class="control-label" for="inputSN">主设备号</label>
       <div class="controls">
       	<c:forEach items="${bindings }" var="binding">
	         <label class=" span3 control-group">
			  <input type="hidden" name="devices" value="${binding.serialNumber }"/>
				 <i class="icon-star"></i>${binding.serialNumber}
				 <i class="icon-remove"></i>
			 </label>
         </c:forEach>
         <input class="span3" type="text" name="devices" id="inputSN" placeholder="设备手机号码" value="">
       </div>
     </div>
</form>