<link rel="stylesheet" href="${resource}/pagination-normal.css" />
<#if page.content??>
	<#include "img-url.tpl">
	<div class="pagination-bigimg">
		<#list page.content as comment>
			<#assign offset=0>
			<#assign tmp=''>
			<#assign step=2>
		    <div class="item">
			  <div>
		      	<h6><a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">${comment.title}</a></h6>
		      	<div class="muted">
		      		创建于 ${comment.createAt?string("yyyy-MM-dd")} 浏览数 ${comment.viewed} 评论数 ${comment.commented}
		      	</div>
		      	<div style="position:relative;">
		      		${comment.fragment!!}
		      		<a style="position:absolute;right:0;bottom:0" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">[查看详情]</a>
		      	</div>
		      </div>
		    </div>
	    </#list>
	    <#include "pagination.tpl">
	 </div>
 </#if>