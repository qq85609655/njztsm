<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap-datetimepicker/css/datetimepicker.css">

<script type="text/javascript">
    $(document).ready(function() {
 	    $('.icon-remove').on('click',function(){
 	    	if($('.icon-remove').size()>1){
 	    		$(this).parent().remove() ;
 	    		if($('.icon-remove').size()==1)   		
 	    			$('.icon-remove').remove() ;
 	    	}
 	    });
    });
</script>

<form class="form-horizontal" action="${ctx}/admin/member/${action}" method="post">
	 <div class="control-group">
       <label class="control-label">绑定者</label>
       <div class="controls">
       	<c:forEach items="${list }" var="model">
	         <label class=" span3 control-group">
			  <input type="hidden" name="userId" value="${model.id }"/>
				 <i class="icon-user"></i>
			  ${model.name} <i class="icon-hand-right"></i> ${owner.phone}
			  <c:if test="${fn:length(owners) > 1 }">
				  <i class="icon-remove"></i>
			  </c:if>
			 </label>
         </c:forEach>
       </div>
     </div>

     <div class="control-group">
       <label class="control-label" for="inputSN">设备号</label>
       <div class="controls">
         <input class="span3" type="text" name="sn" id="inputSN" placeholder="设备手机号码" value="${binding.serialNumber }">
       </div>
     </div>
</form>