<%@page import="com.sklay.core.enums.SwitchStatus"%>
<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@page import="com.sklay.core.enums.Sex"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<script type="text/javascript" src="${ctx}/static/thirdparty/datepicker/WdatePicker.js"></script>
<div class="widget-box">

	<div class="widget-content tab-content">
	
		<form action="${ctx }/admin/smstpl/${fid }/list" method="post">
			<input type="hidden" name="fid" value='${fid }'>
			<div class="input-prepend">
			  <span class="add-on">绑定状态</span>
			    <select class="span2" name="status">
				  <option value="" <c:if test="${empty checkedStatus}"> selected="selected" </c:if>>所有状态</option>
					<option value="<%=AuditStatus.WAIT %>" <c:if test="${ checkedStatus.value eq 0}"> selected="selected" </c:if>><%=AuditStatus.WAIT.getLable() %></option>
					<option value="<%=AuditStatus.PASS %>" <c:if test="${ checkedStatus.value eq 1}"> selected="selected" </c:if>><%=AuditStatus.PASS.getLable() %></option>
					<option value="<%=AuditStatus.NOT %>" <c:if test="${ checkedStatus.value eq 2}"> selected="selected" </c:if>><%=AuditStatus.NOT.getLable() %></option>
				</select>
			</div>
			
		    <div class=" input-prepend">
			  	<span class="add-on" data-name="tooltip" data-toggle="tooltip" data-original-title="【姓名、手机号、地址、区域、备注】">关键字:</span>
			    <input type="text" name="keyword" class="span3" value='${keyword }'>
			    <button type="submit" class="btn">搜索</button>
		    </div>
		</form>
		<div class="widget-content tab-content">
			<a class="modal-link btn btn-info" data-target="#modalTemplate" data-title="创建短信" href="${ctx}/admin/smstpl/initCreate/${fid }" data-href="${ctx}/admin/smstpl/list" data-content="创建成功." data-original-title="消息...">创建</a>
		</div>
		<div class="tab-pane active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>编号</th>
						<th>模版名称</th>
						<th>短信内容</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${pageModel.totalElements==0}">
							<tr>
								<td colspan="5">还未添加过相关记录 </td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${pageModel.content}" var="model">
								<tr>
									<td>${model.id}</td>
									<td>${model.tpl}</td>
									<td>${model.content}</td>
									<td>${model.status.lable}</td>
									<td>
									<a class="modal-link" data-target="#modalTemplate" data-title="编辑短信" href="${ctx}/admin/smstpl/initUpdate/${model.id}" data-href="${ctx}/admin/smstpl/list" data-content="编辑成功." data-original-title="消息...">编辑</a> |
									<c:if test="${model.status.value eq 2 }">
										<a class="modal-link-a" data-target="#modalTemplate" data-title="启用短信" href="javascript:void(0);" data-href="${ctx}/admin/smstpl/on/${model.id}">通过</a>
									</c:if>
									<c:if test="${model.status.value eq 1 }">
										<a class="modal-link-a" data-target="#modalTemplate" data-title="停用短信" href="javascript:void(0);" data-href="${ctx}/admin/smstpl/off/${model.id}">不通过</a>
									</c:if>
									<c:if test="${model.status.value eq 0 }">
										<a class="modal-link-a" data-target="#modalTemplate" data-title="启用短信" href="javascript:void(0);" data-href="${ctx}/admin/smstpl/on/${model.id}">通过</a> |
										<a class="modal-link-a" data-target="#modalTemplate" data-title="停用短信" href="javascript:void(0);" data-href="${ctx}/admin/smstpl/off/${model.id}">不通过</a>
									</c:if>
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
	
	$(".modal-link-a").click(function(event) {
    	event.preventDefault() ;
    	$target = $(this).attr('data-href') ;
        $.post($target,function(data){
        	if(data.code == 0){
        		var $reload = $('#pagination').find('.active') ;
       			if($reload.size() > 0){
       				window.location.href = $reload.find('a').attr('href') ;
       			}else if($('#searchBtn').size() > 0){
       				 $("#searchBtn").click() ;
       			}else{
       				window.location.reload() ;
       			}
        	}
        }) ;
    }) ;
	
    $(".modal-link").click(function(event) {
    	event.preventDefault() ;
    	$target = $(this).attr('data-target') ;
        $header = $(this).attr('data-title') ;
        $nofooter = $(this).attr('data-nofooter') ;
        $delete = $(this).attr('data-delete') ;
        
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
    
    $(".btn-info").click(function(event) {
    	event.preventDefault() ;
    	if($('input:checked').size() ==0)
    		return ;
    	$ids = '' ;
    	var c = $('input:checked').size();
    	$.each($('input:checked'),function(i,node){
    		c -= 1 ;
    		$ids += 0 == c ? $(node).val() : $(node).val()+',';
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