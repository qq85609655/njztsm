<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<div class="widget-box">
	<div class="widget-content tab-content">
		<ul class="nav nav-pills" >
			<li>
				<shiro:hasPermission name="member:multipleBinding">
				<div class="btn-group">
				  <a class="btn btn-info " data-target="#modalTemplate" data-title="设备绑定附属会员" href="${ctx}/admin/member/initMultipleBinding">绑定附属账号</a>
				</div>
				</shiro:hasPermission>
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
		<form action="${ctx }/admin/member/list">
			<div class="input-prepend">
			  	<span class="add-on">分组:</span>
			    <select class="span2" name="groupId">
				  <option value="" <c:if test="${empty checkedGroup}"> selected="selected" </c:if>>所有分组</option>
				  <c:forEach items="${groups}" var="group">
					<option value="${group.id}" <c:if test="${group.id eq checkedGroup}"> selected="selected" </c:if>>${group.name}</option>
				  </c:forEach>
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
						<th>姓名</th>
						<th>手机号</th>
						<th>审核</th>
						<sklay:hasRole value="2">
						<th>隶属于</th>
						</sklay:hasRole>
						<th>备注</th>
						<th colspan="4">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${pageModel.totalElements==0}">
							<tr>
								<td colspan="5">还未添加过相关记录 
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${pageModel.content}" var="model">
								<tr>
									<td><label class="checkbox" for="checkbox_${model.id}"><input id="checkbox_${model.id}" type="checkbox" data-widget="checkbox_all" data-phone="${model.phone}" name="checkbox" value="${model.id}">  ${model.id}</label></td>
									<td class="taskDesc">${model.name}</td>
									<td class="taskDesc">${model.phone}</td>
									<td class="taskDesc" <c:if test="${model.status.value != 1 }">style="color:red;"</c:if>>${model.status.lable}</td>
									<sklay:hasRole value="2">
									<td class="taskDesc">${model.group.owner.name}</td>
									</sklay:hasRole>
									<td>${model.description}</td>
									<td colspan="4">
										<shiro:hasPermission name="member:update">
											<a href="${ctx}/admin/member/initUpdate/${model.id}">编辑</a>
										</shiro:hasPermission>
										<shiro:hasPermission name="member:password">
										 	| <a class="modal-link" data-target="#modalTemplate" data-title="重置密码" href="${ctx}/admin/member/initPassword/${model.id}">重置密码</a>
										 </shiro:hasPermission>
										<shiro:hasPermission name="member:audit">
										 	| <a class="modal-link" data-target="#modalTemplate" data-title="会员审核" href="${ctx}/admin/member/initAudit/${model.id}" >审核</a>
										 </shiro:hasPermission>
										<shiro:hasPermission name="member:delete">
										 	| <a class="modal-link" data-target="#modalTemplate" data-title="会员删除"  data-delete="true" data-msg="您确定要删除吗?" href="javascript:void(0);" data-href="${ctx}/admin/member/delete/${model.id}">删除</a>
										 </shiro:hasPermission>
										 <shiro:hasPermission name="member:detail">
										 	| <a class="modal-link" data-target="#modalTemplate"  data-nofooter="true" data-title="会员详情" href="${ctx}/admin/member/detail/${model.id}" >详情</a>
										 </shiro:hasPermission>
									 	<shiro:hasPermission name="member:binding">
									 		<c:if test="${model.status.value eq 1 }">
										 	 | <a class="modal-link" data-target="#modalTemplate" data-title="设备绑定主号" href="${ctx}/admin/member/initBinding/${model.id}">绑定主设备</a>
											</c:if>
										</shiro:hasPermission>
										 <shiro:hasPermission name="member:remove">
										 	| <a class="modal-link" data-target="#modalTemplate" data-title="会员删除"  data-delete="true" data-msg="您确定要删除吗?此操作将删除与该有用户的所有信息" href="javascript:void(0);" data-href="${ctx}/admin/member/remove/${model.id}">硬删除</a>
										 </shiro:hasPermission>
										
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
        	$($target).find(".modal-header").html("<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button><h3>"+$header+"</h3>") ;        	
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
    
    $(".btn-info").click(function(event) {
    	event.preventDefault() ;
    	if($('input[data-widget="checkbox_all"]:checked').size() ==0)
    		return ;
    	$ids = '' ;
    	var c = $('input[data-widget="checkbox_all"]:checked').size();
    	$.each($('input[data-widget="checkbox_all"]:checked'),function(i,node){
    		c -= 1 ;
    		$ids +=  ( 0 == c ) ? $(node).val() : $(node).val()+',';
    	});
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
        $($target).modal({remote: $(this).attr("href")+"?userIds="+$ids});
        $($target).modal("show");
    }) ;
    
}) ;

</script>