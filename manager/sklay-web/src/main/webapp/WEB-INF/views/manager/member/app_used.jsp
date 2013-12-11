<%@page import="com.sklay.core.enums.SMSStatus"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap-datetimepicker/css/datetimepicker.css">


<div class="widget-box">

<div class="widget-content tab-content">
	
	<form action="${ctx }/app/${appId }/used" method="post">
		<div class="control-group input-prepend">
		  <span class="add-on">短信状态</span>
		    <select class="span2" name="status">
			  <option value="" <c:if test="${empty status}"> selected="selected" </c:if>>所有状态</option>
				<option value="<%=SMSStatus.NOSEND %>" <c:if test="${status.value eq 2}"> selected="selected" </c:if>><%=SMSStatus.NOSEND.getLable() %></option>
				<option value="<%=SMSStatus.FAIL %>" <c:if test="${status.value eq 0}"> selected="selected" </c:if>><%=SMSStatus.FAIL.getLable() %></option>
				<option value="<%=SMSStatus.SUCCESS %>" <c:if test="${status.value eq 1}"> selected="selected" </c:if>><%=SMSStatus.SUCCESS.getLable() %></option>
			</select>
		    <button type="submit" class="btn">检索</button>
		</div>
	</form>
	<div class="tab-pane active">
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>编号</th>
					<th>发送者</th>
					<th class="span2">收信人手机</th>
					<th class="span1">状态</th>
					<th>发送时间</th>
					<th class="span4">短信内容</th>
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
								<td><label class="checkbox hiden" for="checkbox_${model.id}"><input id="checkbox_${model.id}" type="checkbox" name="checkbox" value="${model.id}">  ${model.id}</label></td>
								<td>${model.creator.name} </td>
								<td>${model.receiver}</td>
								<td>${model.status.lable}</td>
								<td> <fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${model.sendTime}" /></td>
								<td>${model.content} </td>
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