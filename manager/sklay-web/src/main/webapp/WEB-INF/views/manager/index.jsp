<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.jsp"%>
<c:choose>
<c:when test="${ null == pageModel }">
<div class="widget-box">	
	<div class="widget-content tab-content nopadding">
		<div id="alert-content" class="alert alert-info hide"><!--?=base_url();?--><!--?=$currentPage; ?-->1</div>
		<div id="myCarousel" class="carousel slide">
                <ol class="carousel-indicators">
                  <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                  <li data-target="#myCarousel" data-slide-to="1" class=""></li>
                  <li data-target="#myCarousel" data-slide-to="2" class=""></li>
                </ol>
                <div class="carousel-inner">
                  <div class="item">
                    <img src="${ctx }/assets/advert/0001.jpg" alt="">
                    <div class="carousel-caption hide">
                      <h4>First Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                  <div class="item">
                    <img src="${ctx }/assets/advert/0002.jpg" alt="">
                    <div class="carousel-caption hide">
                      <h4>Second Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                  <div class="item active">
                    <img src="${ctx }/assets/advert/0003.jpg" alt="">
                    <div class="carousel-caption hide">
                      <h4>Third Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                </div>
                <a class="left carousel-control" href="#myCarousel" data-slide="prev">‹</a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next">›</a>
              </div>
	</div>
</div>

</c:when>
<c:otherwise>
<div class="widget-box">
	<div class="widget-content tab-content">
		<ul class="nav nav-pills" >
			<li>
				<div class="btn-group">
				  <a class="btn btn-danger" href="javascript:void(0);">短信推广</a>
				  <form action="${ctx}/app/initsms/" hidden="hidden" method="post" id="sendSms">
				  	<input type="hidden" name="phones" id="smsPhone">
				  </form>
				</div>
			</li>
		</ul>
		<div class="tab-pane active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="input-mini"><label class="checkbox" for="checkbox_all"><input id="checkbox_all" type="checkbox" name="checkbox">编号</label></th>
						<th>姓名</th>
						<th>手机号</th>
						<th>体检报告</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageModel.content}" var="model">
						<tr>
							<td><label class="checkbox" for="checkbox_${model.id}"><input id="checkbox_${model.id}" type="checkbox" data-widget="checkbox_all" data-phone="${model.targetUser.phone}" name="checkbox" value="${model.id}">  ${model.id}</label></td>
							<td class="taskDesc">${model.targetUser.name}</td>
							<td class="taskDesc">${model.targetUser.phone}</td>
							<td class="taskDesc">${model.smsContent}</td>
							<td>${model.remark}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${pageModel.totalPages>1}">
				<c:set var="page" value="${pageModel}" scope="request"/>
				<c:set var="pageQuery" value="${pageQuery}" scope="request"/>
				<%@include file="../pagination.jsp"%>
			</c:if>
		</div>
	</div>
</div>
</c:otherwise>
</c:choose>
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