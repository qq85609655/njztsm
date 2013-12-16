<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@page import="com.sklay.core.enums.BindingMold"%>
<%@page import="com.sklay.core.enums.Level"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<div class="widget-box">
	<div class="widget-content tab-content">
		<ul class="nav nav-pills" >
			<li>
				<shiro:hasPermission name="sms:job">
				<div class="btn-group">
				  <a class="btn btn-warning" href="javascript:void(0);">发送短信</a>
				  <form action="${ctx}/admin/sms/initJob" hidden="hidden" method="post" id="sendSms">
				  	<input type="hidden" name="phones" id="smsPhone">
				  </form>
				</div>
				</shiro:hasPermission>
				
				<sklay:hasRole value="1">
				<div class="btn-group">
				  <a class="btn btn-danger" href="javascript:void(0);">短信推广</a>
				  <form action="${ctx}/app/initsms/" hidden="hidden" method="post" id="sendSms">
				  	<input type="hidden" name="phones" id="smsPhone">
				  </form>
				</div>
				</sklay:hasRole>
			</li>
		</ul>
		<form action="${ctx }/admin/device/list" method="post">
		
			<div class="input-prepend">
			  	<span class="add-on">分组:</span>
			    <select class="span2" name="groupId">
				  <option value="" <c:if test="${empty checkedId}"> selected="selected" </c:if>>所有分组</option>
				  <c:forEach items="${groups}" var="group">
					<option value="${group.id}" <c:if test="${group.id eq checkedId}"> selected="selected" </c:if>>${group.name}</option>
				  </c:forEach>
				</select>
			</div>
		
			<div class="input-prepend">
			  <span class="add-on">等级</span>
			    <select class="span2" name="levelValue">
				  <option value="" <c:if test="${empty checkedOption}"> selected="selected" </c:if>>所有等级</option>
					<option value="<%=Level.FIRST.getValue() %>" <c:if test="${ checkedOption.value eq 0}"> selected="selected" </c:if>><%=Level.FIRST.getLable() %></option>
					<option value="<%=Level.SECOND.getValue() %>" <c:if test="${ checkedOption.value eq 1}"> selected="selected" </c:if>><%=Level.SECOND.getLable() %></option>
				</select>
			</div>
			
			<div class="input-prepend">
			  <span class="add-on">类型</span>
			    <select class="span2" name="bindingMold">
				  <option value="" <c:if test="${empty checkedType}"> selected="selected" </c:if>>所有类型</option>
					<option value="<%=BindingMold.FREE %>" <c:if test="${ checkedType.value eq 0}"> selected="selected" </c:if>><%=BindingMold.FREE.getLable() %></option>
					<option value="<%=BindingMold.PAID %>" <c:if test="${ checkedType.value eq 1}"> selected="selected" </c:if>><%=BindingMold.PAID.getLable() %></option>
				</select>
			</div>
			
			<div class="input-prepend">
			  <span class="add-on">绑定状态</span>
			    <select class="span2" name="status">
				  <option value="" <c:if test="${empty checkedStatus}"> selected="selected" </c:if>>所有状态</option>
					<option value="<%=AuditStatus.WAIT %>" <c:if test="${ checkedStatus.value eq 0}"> selected="selected" </c:if>><%=AuditStatus.WAIT.getLable() %></option>
					<option value="<%=AuditStatus.PASS %>" <c:if test="${ checkedStatus.value eq 1}"> selected="selected" </c:if>><%=AuditStatus.PASS.getLable() %></option>
					<option value="<%=AuditStatus.NOT %>" <c:if test="${ checkedStatus.value eq 2}"> selected="selected" </c:if>><%=AuditStatus.NOT.getLable() %></option>
				</select>
			</div>
			
			<div class="input-prepend">
			  <span class="add-on">付费状态</span>
			    <select class="span2" name="moldStatus">
				  <option value="" <c:if test="${empty checkedMoldStatus}"> selected="selected" </c:if>>所有状态</option>
				  	<option value="<%=AuditStatus.WAIT %>" <c:if test="${ checkedMoldStatus.value eq 0}"> selected="selected" </c:if>><%=AuditStatus.WAIT.getLable() %></option>
					<option value="<%=AuditStatus.PASS %>" <c:if test="${ checkedMoldStatus.value eq 1}"> selected="selected" </c:if>><%=AuditStatus.PASS.getLable() %></option>
					<option value="<%=AuditStatus.NOT %>" <c:if test="${ checkedMoldStatus.value eq 2}"> selected="selected" </c:if>><%=AuditStatus.NOT.getLable() %></option>
				</select>
			</div>
			
			<div class="input-prepend">
			  	<span class="add-on" data-name="tooltip" data-toggle="tooltip" data-original-title="【姓名、手机号、地址、区域、备注】">关键字:</span>
			    <input type="text" name="keyword" class="span3" value='${keyword }'>
			    <button type="submit" class="btn" id="searchBtn">搜索</button>
			</div>
		</form>
		<div class="tab-pane active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="input-mini"><label class="checkbox" for="checkbox_all"><input id="checkbox_all" type="checkbox" name="checkbox">编号</label></th>
						<th class="span2">绑定者</th>
						<th class="span1">手机号</th>
						<th class="span1">设备号</th>
						<th class="input-mini">绑定类型</th>
						<th class="input-mini">付费类型</th>
						<th class="input-mini">审核状态</th>
						<th class="span2">创建日期</th>
						<th class="span2">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${pageModel.totalElements==0}">
							<tr>
								<td colspan="5">还未添加过相关记录 , <a href="${ctx}/admin/member/initCreate">加一个</a> ?
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${pageModel.content}" var="model" varStatus="vstatus">
								<tr>
									<td><label class="checkbox" for="checkbox_${vstatus.index+1}"><input id="checkbox_${vstatus.index+1}" type="checkbox" data-widget="checkbox_all" name="checkbox" data-phone="${model.targetUser.phone}" value="${model.targetUser.phone}_${model.serialNumber}">${model.id}</label></td>
									<td>${model.targetUser.name} </td>
									<td>${model.targetUser.phone}</td>
									<td>${model.serialNumber}</td>
									<td>${model.level.lable} </td>
									<td>${model.mold.lable} </td>
									<td <c:if test="${model.status.value != 1 }">style="color:red;"</c:if>>${model.status.lable}</td>
									<td><fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${model.createTime}" /></td>
									<td>
										<shiro:hasPermission name="device:audit">
										 <a class="modal-link" data-target="#modalTemplate" data-title="设备审核" href="${ctx}/admin/device/initAudit/${model.id}">审核</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="device:update">
										 | <a class="modal-link" data-target="#modalTemplate" data-title="设备修改" href="${ctx}/admin/device/initUpdate/${model.id}" >修改</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="device:delete">
										 | <a class="modal-link" data-target="#modalTemplate" data-title="设备删除"  data-delete="true" data-msg="您确定要删除吗? (删除主机号时将级联删除附属号!)" href="javascript:void(0);" data-href="${ctx}/admin/device/delete/${model.id}" >删除</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="device:moldAudit">
											<c:if test="${model.mold.value eq 1}">
										 | <a class="modal-link" data-target="#modalTemplate" data-title="付费设备审核" href="${ctx}/admin/device/initMoldAudit/${model.id}">付费审核</a>
											</c:if>
										</shiro:hasPermission>
										| <a class="modal-link" data-target="#modalTemplate" data-title="健康报告" data-nofooter='true' href="${ctx}/admin/member/initLineChart/${model.targetUser.id}">健康报告</a>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<c:if test="${pageModel.totalPages>1}">
				<c:set var="page" value="${pageModel}" scope="request"/>
				<c:set var="pageQuery" value="${pageQuery}" scope="request"/>
				<%@include file="../../pagination.jsp"%>
			</c:if>
		</div>
	</div>
</div>

<script>

$(function() {
	
	$("input:checkbox").on('click',function(){
		$option = $(this) ;
		
		if(typeof $option.attr('checked') ==='string'){
			$option.attr('checked','checked') ;
			
			if(!(typeof $option.attr("data-widget") === 'string')){
				$('input[data-widget="checkbox_all"]').attr('checked','checked') ;
				return ;
			}
			if($('input[data-widget="checkbox_all"]').length == $('input[data-widget="checkbox_all"]:checked').length){
				$('input:checkbox').attr('checked','checked') ;
				return ;
			}
			
		}else{
			$option.removeAttr('checked') ;
			if(!(typeof $option.attr("data-widget") === 'string')){
   				$('input[data-widget="checkbox_all"]').removeAttr('checked') ;
   				return ;
			}else{
				$('#checkbox_all').removeAttr('checked') ;
			}
		} ;
	}) ;
	
    $(".modal-link").click(function(event) {
    	event.preventDefault() ;
    	$target = $(this).attr('data-target') ;
        $header = $(this).attr('data-title') ;
        $nofooter = $(this).attr('data-nofooter') ;
        
        if($nofooter){
        	$($target).find(".modal-header").html("<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button><h3>"+$header+"</h3>") ;
        	$($target).find(".modal-footer").hide() ;
        }
        else{
        	$($target).find(".modal-header").html("<h3>"+$header+"</h3>") ;        	
        	$($target).find(".modal-footer").show() ;
        }
        
        $($target).removeData("modal") ;
        $($target).modal({remote: $(this).attr("href")});
        $($target).modal("show");
    }) ;
    
    $('.btn-warning').click(function(event) {
    	event.preventDefault() ;
    	var c = $('input[data-widget="checkbox_all"]:checked').size();
    	if(c ==0)
    		return ;
    	$ids = '' ;
    	$.each($('input[data-widget="checkbox_all"]:checked'),function(i,node){
    		c -= 1 ;
    		$ids += ( 0 == c ) ? $(node).attr('data-phone') : $(node).attr('data-phone')+',';
    	});
    	$('#smsPhone').val($ids) ;
    	$('#sendSms').submit() ;
    }) ;
    
    $('.btn-danger').click(function(event) {
    	event.preventDefault() ;
    	var c = $('input[data-widget="checkbox_all"]:checked').size();
    	if(c ==0){
    		$('#smsPhone').val("") ;
        	$('#sendSms').submit() ;    		
    		return ;
    	}
    	$ids = '' ;
    	$.each($('input[data-widget="checkbox_all"]:checked'),function(i,node){
    		c -= 1 ;
    		$ids += ( 0 == c ) ? $(node).attr('data-phone') : $(node).attr('data-phone')+',';
    	});
    	$('#smsPhone').val($ids) ;
    	$('#sendSms').submit() ;
    }) ;
}) ;

</script>