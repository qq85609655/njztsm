<%@page import="com.sklay.enums.LogLevelType"%>
<%@page import="com.sklay.core.enums.Sex"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<div class="widget-box">
	<div class="widget-content tab-content">
		<form action="${ctx }/admin/initLogList" method="post">
			<div class=" input-prepend">
			  <span class="add-on">类型</span>
			    <select class="span2" name="levelType">
				  <option value="-1" <c:if test="${empty checkedType}"> selected="selected" </c:if>>所有</option>
				  <option value="<%=LogLevelType.API.getValue() %>" <c:if test="${checkedType eq 0}"> selected="selected" </c:if>><%=LogLevelType.API.getLable() %></option>
				  <option value="<%=LogLevelType.SERVICE.getValue() %>" <c:if test="${checkedType eq 1}"> selected="selected" </c:if>><%=LogLevelType.SERVICE.getLable() %></option>
				  <option value="<%=LogLevelType.ADMIN.getValue() %>" <c:if test="${checkedType eq 2}"> selected="selected" </c:if>><%=LogLevelType.ADMIN.getLable() %></option>
				</select>
			</div>
			<div class="offset1 input-prepend">
			    <button type="submit" class="btn">搜索</button>
			</div>
		</form>
		<div class="tab-pane active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th >编号</th>
						<th class="span2">名称</th>
						<th class="span2">参数</th>
						<th class="span2">类型</th>
						<th class="span2">描述</th>
						<th class="span3">时间</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${pageModel.totalElements==0}">
							<tr>
								<td colspan="5">还未添加过相关记录</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${pageModel.content}" var="model">
								<tr>
									<td><label class="checkbox" for="checkbox_${model.id}"> ${model.id}</label></td>
									<td>${model.name}</td>
									<td data-ellipsis='40'>${model.content}</td>
									<td>${model.type.lable} </td>
									<td>${model.desctiption}</td>
									<td>${model.createTime}</td>
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