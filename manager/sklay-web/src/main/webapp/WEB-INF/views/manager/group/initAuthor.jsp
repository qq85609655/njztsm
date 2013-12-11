<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<script type="text/javascript">
    $(document).ready(function() {
    	
    	$("input:checkbox").on('click',function(){
    		$option = $(this) ;
    		if(typeof $option.attr('checked') ==='string'){
    			if(typeof $option.attr("data-widget") === 'string'){
    				$('#'+$option.attr("data-widget")).attr('checked','checked') ;
    			}else{
    				var parentWidgetName = $option.attr('id') ;
    				$('input[data-widget="'+parentWidgetName+'"]').attr('checked','checked') ;
    			}
    		}else{
    			
    			if(typeof $option.attr("data-widget") === 'string'){
    				var childWidgetName = $option.attr('data-name') ;
        			if(0 == $('input[data-name="'+childWidgetName+'"]:checked').length)
        				$('#'+$option.attr("data-widget")).removeAttr('checked') ;
    			}else{
    				var parentWidgetName = $option.attr('id') ;
    				$('input[data-widget="'+parentWidgetName+'"]').removeAttr('checked') ;
    			}
    			
    		} ;
    	}) ;
    });
</script>
<div class="widget-box">	
	<div class="widget-content tab-content" id="createForm">
		<form class="form-horizontal logform" action="${ctx}/admin/group/author" method="post">
		
			<div class="control-group">
			    <label class="controls span2 text-error" for="groupAuthor"><i class="icon-bell"></i>被授权对象</label>
			    <div class="controls muted">
					  <input type="hidden" id="group_${group.id}" value="${group.id}" name="groupId">${group.name}
			    </div>
		  	</div>
		
			<c:forEach items="${widgets}" var="widget" varStatus="status">
				<div class="control-group">
				    <label class="controls checkbox span7 text-info" for="widget_${status.index}">
				    <i class="icon-star"></i><input type="checkbox" name="authors" id="widget_${status.index}" value="${widget.value.authBase.name}" ${widget.value.checked }>${widget.value.authBase.description}
				    </label>
			    	<c:forEach items="${widget.value.child}" var="widgetChild" varStatus="childStatus">
					 	<label class=" checkbox span7 offset1" for="widgetChild_${widgetChild.authBase.name}_${childStatus.index}">
  							<i class="icon-hand-right"></i><input type="checkbox" name="authors" data-widget="widget_${status.index}" data-name="childWidget_${widget.value.authBase.name}" id="widgetChild_${widgetChild.authBase.name}_${childStatus.index}" value="${widgetChild.authBase.name }"  ${widgetChild.checked }> ${widgetChild.authBase.description }
						</label>
					</c:forEach>
			  	</div>
			</c:forEach>
			<div class="control-group">
		        <div class="controls">
					<button class="btn btn-primary" type="button" data-html="true" data-href="${ctx}/admin/group/list" data-content="授权成功." data-original-title="消息...">提交</button>
				</div>
			</div>
		</form>
	</div>
</div>