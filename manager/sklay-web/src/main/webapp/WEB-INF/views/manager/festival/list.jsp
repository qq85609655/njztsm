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
	
		<form action="${ctx }/admin/festival/list" method="post">
		
			<div class="input-prepend">
			  	<span class="add-on">启用状态:</span>
			    <select class="span2" name="switchStatus">
				  <option value="" <c:if test="${empty checked}"> selected="selected" </c:if>>所有</option>
				  <option value="<%=SwitchStatus.CLOSE %>" <c:if test="${0 eq checked.value}"> selected="selected" </c:if>><%=SwitchStatus.CLOSE.getLable() %></option>
				  <option value="<%=SwitchStatus.OPEN %>" <c:if test="${1 eq checked.value}"> selected="selected" </c:if>><%=SwitchStatus.OPEN.getLable() %></option>
				</select>
			</div>
			
      		<div class="input-prepend">
	        	<span class="add-on">节日时间</span>
	          	<input class="Wdate" type="text" required="required" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" id="inputJobTime" name="jobTime" placeholder="节日日期">
	        </div>
	      
		    <div class=" input-prepend">
			  	<span class="add-on" data-name="tooltip" data-toggle="tooltip" data-original-title="【姓名、手机号、地址、区域、备注】">关键字:</span>
			    <input type="text" name="keyword" class="span3" value='${keyword }'>
			    <button type="submit" class="btn">搜索</button>
		    </div>
		</form>
		<div class="widget-content tab-content">
			<a class="modal-link btn btn-info" data-target="#modalTemplate" data-title="创建节日" href="${ctx}/admin/festival/initCreate" data-href="${ctx}/admin/festival/list" data-content="创建成功." data-original-title="消息...">创建</a>
		</div>
		<div class="tab-pane active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>编号</th>
						<th>节日名称</th>
						<th>节日时间</th>
						<th>短信时间</th>
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
									<td>${model.name}</td>
									<td>${model.jobTime}</td>
									<td><fmt:formatDate value="${model.sendTime}" type="both" dateStyle="default" timeStyle="default"/></td>
									<td>${model.switchStatus.lable}</td>
									<td>
									<a class="modal-link" data-target="#modalTemplate" data-title="编辑节日" href="${ctx}/admin/festival/initUpdate/${model.id}" data-href="${ctx}/admin/festival/list" data-content="编辑成功." data-original-title="消息...">编辑</a> |
									<c:if test="${model.switchStatus.value eq 1 }">
										<a class="modal-link-a" data-target="#modalTemplate" data-title="停用节日" href="javascript:void(0);" data-href="${ctx}/admin/festival/off/${model.id}">停用</a> |
									</c:if>
									<c:if test="${model.switchStatus.value eq 0 }">
										<a class="modal-link-a" data-target="#modalTemplate" data-title="启用节日" href="javascript:void(0);" data-href="${ctx}/admin/festival/on/${model.id}">启用</a> |
									</c:if>
									<a href="${ctx}/admin/smstpl/${model.id}/list">推送短信</a>
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