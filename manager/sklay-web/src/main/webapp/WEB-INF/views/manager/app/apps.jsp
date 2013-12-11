<%@page import="com.sklay.core.enums.SMSStatus"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<div class="widget-box">
<div class="widget-content tab-content">
	<div class="tab-pane active">
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>编号</th>
					<th class="span2">名称</th>
					<th class="span2">资费标准(元)</th>
					<th class="span2">说明</th>
					<th class="span2">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
					<c:when test="${fn:length(models)<=0}">
						<tr>
							<td colspan="5" class="center"><h3>暂未开放 ,敬请期待!</h3></td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${models}" var="model">
							<tr>
								<td><label class="checkbox hiden" for="checkbox_${model.id}"><input id="checkbox_${model.id}" type="checkbox" name="checkbox" value="${model.id}">  ${model.id}</label></td>
								<td>${model.title} </td>
								<td>${model.price}</td>
								<td>${model.description}</td>
								<%-- <td> <fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${model.sendTime}" /></td> --%>
								<td>
									<a class="modal-link" data-target="#modalTemplate" data-title="申请购买短信应用" href="${ctx}/app/initApply/${model.id}">购买</a>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
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