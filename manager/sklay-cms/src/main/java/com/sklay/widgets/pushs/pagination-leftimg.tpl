<#if page.content??>
	<#include "img-url.tpl">
	<ul class="pagination-leftimg clearfix">
		<#list page.content as comment>
			<#assign offset=0>
			<#assign tmp=''>
			<#assign step=2>
		    <li>
		      <div class="span5">
				<a class="thumbnail" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
		          <img src="${ctx}/file/newsImg/<@splitId idstr=comment.id/>/0/c0.jpg?ver=${comment.ver}" style="width:100%">
		        </a>
			  </div>
			  <div class="span7">
		      	<h6><a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">${comment.title}</a></h6>
		      	<p>
		      		${comment.fragment!!}
		      	</p>
		      </div>
		    </li>
	    </#list>
	    <#include "pagination.tpl">
	 </ul>
 </#if>