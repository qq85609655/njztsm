<%@page import="org.springframework.data.domain.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include.jsp"%>
<script type="text/javascript" src="${ctx }/static/thirdparty/bootstrap/js/bootstrap-paginator.js"></script>
<div class="row-fluid">
	<div class="span8">
		<div id="pagination"></div>
	</div>
	<div class="span4 pagination">
		<div class="pages-summary">共 <em>${page.totalElements }</em> 条记录 , 第 <em>${page.number+1}</em> / <em>${page.totalPages}</em> 页</div>
	</div>		
</div>
<script>
$(function(){
	var options = {
            currentPage: '${page.number+1}',
            totalPages: '${page.totalPages}',
            onPageClicked: function(e,originalEvent,type,page){
             //   $('#alert-content').text("Page item clicked, type: "+type+" page: "+page);
            },
          	pageUrl: function(type, page, current){
                return "?${pageQuery}page.page="+page;
            }

    } ;

   $('#pagination').bootstrapPaginator(options);
         
});
</script>



