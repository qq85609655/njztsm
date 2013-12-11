<%@page import="com.sklay.core.enums.Sex"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<div class="widget-box">

	<div class="btn-group" style="padding: 5px;">
	  <a class="btn btn-info " data-target="#modalTemplate" data-title="删除元数据" data-alert='true' href="${ctx}/admin/matadata/delete">删除元数据</a>
	</div>

	<div class="widget-content tab-content">
	
		<form action="${ctx }/admin/matadata/list" method="post">
			<div class="input-prepend">
			  <span class="add-on">年龄范围</span>
			  <input type="number" min="1" name="minAge" class="input-mini" value='${minAge }'><i class="icon-arrow-right"></i><input type="number" min="1" name="maxAge" class="input-mini" value='${maxAge }'>
			</div>
			<div class="offset1 input-prepend">
			  <span class="add-on">血压低值</span>
			  <input type="number" min="1" name="lowPressure" class="input-mini" value='${lowPressure }'>
			</div>
			<div class="offset1 input-prepend">
			  <span class="add-on">血压高值</span>
			  <input type="number" min="1" name="highPressure" class="input-mini" value='${highPressure }'>
			</div>
			<div class=" input-prepend">
			  <span class="add-on">性别</span>
			    <select class="span2" name="sex">
				  <option value="-1" <c:if test="${empty checkedSex}"> selected="selected" </c:if>>所有</option>
				  <option value="<%=Sex.FEMALE.getValue() %>" <c:if test="${checkedSex eq 2}"> selected="selected" </c:if>><%=Sex.FEMALE.getLable() %></option>
				  <option value="<%=Sex.MALE.getValue() %>" <c:if test="${checkedSex eq 1}"> selected="selected" </c:if>><%=Sex.MALE.getLable() %></option>
				</select>
			</div>
			<div class="offset1 input-prepend">
			  	<span class="add-on" data-name="tooltip" data-toggle="tooltip" data-original-title="【姓名、手机号、地址、区域、备注】">关键字:</span>
			    <input type="text" name="keyword" class="span3" value='${keyword }'>
			    <button type="submit" class="btn">搜索</button>
			</div>
		</form>
		<div class="tab-pane active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th >编号</th>
						<th class="input-mini">年龄范围</th>
						<th >性别</th>
						<th class="input-mini">血压低值范围</th>
						<th class="input-mini">血压高值范围</th>
						<th class="span3">诊断结果</th>
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
									<td><label class="checkbox" for="checkbox_${model.id}"><input id="checkbox_${model.id}" type="checkbox" name="checkbox" value="${model.id}">  ${model.id}</label></td>
									<td>${model.ageMin} <i class="icon-arrow-right"></i> ${model.ageMax} </td>
									<td>${model.sex.lable}</td>
									<td>${model.lowPressureMin} <i class="icon-arrow-right"></i> ${model.lowPressureMax} </td>
									<td>${model.highPressureMin} <i class="icon-arrow-right"></i> ${model.highPressureMax}</td>
									<td>${model.result}</td>
									<td>${model.remark}</td>
									<td>
									<shiro:hasPermission name="matadata:update">
										<a class="modal-link" data-target="#modalTemplate" data-title="编辑元数据" href="${ctx}/admin/matadata/initUpdate/${model.id}">编辑</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="matadata:delete">	
									 	| <a class="modal-link" data-target="#modalTemplate" data-title="删除 元数据" data-delete="true" data-msg="您确定要删除吗?" href="javascript:void(0);" data-href="${ctx}/admin/matadata/delete/${model.id}">删除</a> 
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