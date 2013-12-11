<link rel="stylesheet" href="${resource}/pagination-images.css" />
<#if page?? && page.content??>
	<#include "img-url.tpl">
	
	<ul class="row-fluid pagination-images clearfix">
	
		<#list page.content as comment>
			<#assign offset=0>
			<#assign tmp=''>
			<#assign step=2>
		    <li class="span3">
				<a class="thumbnail" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
		          <img src="${ctx}/file/newsImg/<@splitId idstr=comment.id/>/0/c0.jpg?ver=${comment.ver}" style="width:100%;height:189px;">
		        </a>
		      	<h6><a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">${comment.title}</a></h6>
		    </li>
	    </#list>
	
	 </ul>
	
 </#if>