<%@page import="com.sklay.core.enums.SMSStatus"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap-datetimepicker/css/datetimepicker.css">


<div class="widget-box">

	<div class="btn-group hiden" style="padding: 5px; display: none;">
	  <a class="btn btn-info " data-target="#modalTemplate" data-title="重新发送短信" href="${ctx}/admin/sms/initResend">重新发送短信</a>
	</div>

<div class="widget-content tab-content">
	
	<form action="${ctx }/admin/sms/list" method="post">
		<%-- <div class="input-prepend">
		  	<span class="add-on">分组:</span>
		    <select class="span2" name="groupId">
			  <option value="" <c:if test="${empty checkedGroup}"> selected="selected" </c:if>>所有分组</option>
			  <c:forEach items="${groups}" var="group">
				<option value="${group.id}" <c:if test="${group.id eq checkedGroup}"> selected="selected" </c:if>>${group.name}</option>
			  </c:forEach>
			</select>
		</div>
		<div class="control-group input-prepend hide">
               <span class="add-on">起始时间</span>
               	<div class="input-append date form_datetime" data-date="2013-09-16T05:25:07Z" data-date-format="yyyy-mm-dd hh:mm" data-link-field="startTime">
                    <input class="dateInput span2" type="text" value="" readonly="readonly">
                    <span class="add-on"><i class="icon-remove"></i></span>
					<span class="add-on"><i class="icon-th"></i></span>
				</div>
			<input type="hidden" id="startTime" name="startDates" >
		</div>
			
		<div class="control-group input-prepend offset1 hide">
               <span class="add-on">截至时间</span>
               	<div class="input-append date form_datetime" data-date="2013-09-16T05:25:07Z" data-date-format="yyyy-mm-dd hh:mm" data-link-field="endTime">
                    <input class="dateInput span2" type="text" value="" readonly="readonly">
                    <span class="add-on"><i class="icon-remove"></i></span>
					<span class="add-on"><i class="icon-th"></i></span>
				</div>
			<input type="hidden" id="endTime" name="endDates">
		</div>
		<div class="input-prepend">
		  <span class="add-on">短信状态</span>
		    <select class="span2" name="status">
			  <option value="-1" <c:if test="${empty checkedStatus}"> selected="selected" </c:if>>所有状态</option>
				<option value="<%=SMSStatus.NOSEND.getValue() %>" <c:if test="${checkedStatus.value eq 2}"> selected="selected" </c:if>><%=SMSStatus.NOSEND.getLable() %></option>
				<option value="<%=SMSStatus.FAIL.getValue() %>" <c:if test="${checkedStatus.value eq 0}"> selected="selected" </c:if>><%=SMSStatus.FAIL.getLable() %></option>
				<option value="<%=SMSStatus.SUCCESS.getValue() %>" <c:if test="${checkedStatus.value eq 1}"> selected="selected" </c:if>><%=SMSStatus.SUCCESS.getLable() %></option>
			</select>
		</div> --%>
		<div class=" input-prepend">
		  	<span class="add-on" data-name="tooltip" data-toggle="tooltip" data-original-title="【收信姓名、手机号、备注】">关键字</span>
		    <input type="text" name="keyword" class="span3" value='${keyword }'>
		    <button type="submit" class="btn" id="searchBtn">搜索</button>
		</div>
	</form>
	<div class="tab-pane active">
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>编号</th>
					<th class="span2">接收者</th>
					<th class="span2">收信人手机</th>
					<th class="span1">发送状态</th>
					<th class="span3">发送时间</th>
					<th class="span4">短信内容</th>
					<th class="span3">备注</th>
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
						<c:forEach items="${pageModel.content}" var="model">
							<tr>
								<td><label class="checkbox hiden" for="checkbox_${model.id}"><input id="checkbox_${model.id}" type="checkbox" name="checkbox" value="${model.id}">  ${model.id}</label></td>
								<td>${model.reciverUser}</td>
								<td>${model.mobile}</td>
								<td>${model.result}</td>
								<td> <fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${model.sendTime}" /></td>
								<td>${model.content} </td>
								<td>${model.remark}</td>
								<td>
									<c:if test="${!(model.status.value eq 1 )}"> 
										<a class="modal-link" data-target="#modalTemplate" data-title="重新发送短信" href="${ctx}/admin/sms/initResend/${model.id}">重新发送</a>
									</c:if>
									<c:if test="${(model.status.value eq 1 )}">尽请期待</c:if>
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
	
	 $('.form_datetime').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			forceParse: true,
			pickDate: false,            // disables the date picker
			pickTime: false,
			pickSeconds: false
	    });
	
	
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