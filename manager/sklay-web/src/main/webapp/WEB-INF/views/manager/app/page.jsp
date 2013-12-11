<%@page import="com.sklay.core.enums.Sex"%>
<%@page import="com.sklay.core.enums.AppType"%>
<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<div class="widget-box">

	<div class="widget-content tab-content">
	
		<form action="${ctx }/admin/app/list" method="post">
			<div class=" input-prepend">
			  <span class="add-on">应用类型</span>
			    <select class="span2" name="appType">
				  <option value="" <c:if test="${empty app}"> selected="selected" </c:if>>所有</option>
				  <option value="<%=AppType.SMS %>" <c:if test="${app.value eq 0}"> selected="selected" </c:if>><%=AppType.SMS.getLable() %></option>
				</select>
			</div>
			
			<div class=" input-prepend">
			  <span class="add-on">审核状态</span>
			    <select class="span2" name="status">
				  <option value="" <c:if test="${empty status}"> selected="selected" </c:if>>所有</option>
				  <option value="<%=AuditStatus.WAIT %>" <c:if test="${status.value eq 0}"> selected="selected" </c:if>><%=AuditStatus.WAIT.getLable() %></option>
				  <option value="<%=AuditStatus.PASS %>" <c:if test="${status.value eq 1}"> selected="selected" </c:if>><%=AuditStatus.PASS.getLable() %></option>
				  <option value="<%=AuditStatus.NOT%>" <c:if test="${status.value eq 2}"> selected="selected" </c:if>><%=AuditStatus.NOT.getLable() %></option>
				</select>
			</div>
			
			<div class="input-prepend">
			  	<span class="add-on" data-name="tooltip" data-toggle="tooltip" data-original-title="">关键字:</span>
			    <input type="text" name="keyword" class="span2" value='${keyword }'>
			    <button type="submit" class="btn">搜索</button>
			</div>
		</form>
		<div class="tab-pane active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>编号</th>
						<th>应用名称</th>
						<th>单价</th>
						<th>申请者</th>
						<th>付费金额</th>
						<th>审核状态</th>
						<th class="span3">购买者说明</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${pageModel.totalElements==0}">
							<tr>
								<td colspan="5">还未有相关记录 </td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${pageModel.content}" var="model">
								<tr>
									<td><label class="checkbox" for="checkbox_${model.id}"><input id="checkbox_${model.id}" type="checkbox" name="checkbox" value="${model.id}">  ${model.id}</label></td>
									<td>${model.product.title}</td>
									<td>${model.product.price}</td>
									<td>${model.creator.name}</td>
									<td>${model.cost}</td>
									<td>${model.status.lable}</td>
									<td>${model.remark}</td>
									<td>
										<a class="modal-link" data-target="#modalTemplate" data-title="应用审核" href="${ctx}/admin/app/${model.id}/audit">审核</a>
										| <a class="modal-link" data-target="#modalTemplate" data-title="应用详情" href="${ctx}/admin/app/${model.id}/detail">详情</a>
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