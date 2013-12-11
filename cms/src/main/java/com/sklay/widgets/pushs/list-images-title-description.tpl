<link rel="stylesheet" href="${resource}/list-images.css" />

<#if comments?? && comments?size gt 0>
	<#include "img-url.tpl">
	<#macro loopRenderComment cline row>
		<#if cline*sizePerLine lte comments?size-1>
			<#assign currentIndex=row-1+cline*sizePerLine>
			<#if currentIndex lt comments?size>
				<#assign comment = comments[row-1+cline*sizePerLine]>
				<#assign offset=0>
				<#assign tmp=''>
				<#assign step=2>
	          <img style="width:100%" class="img-rounded" src="${ctx}/file/newsImg<@splitId idstr=comment.id/>/0/c0.jpg?ver=${comment.ver}">
	          <h2><span>${comment.title}</span></h2>
	          <p>当考虑重新设计你的公司网站,您所需要做的事,就是研究什么是你想要的。这是您唯一需要做的。</p>
	          <p><a class="btn" href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>">了解详情 »</a></p>
			<@loopRenderComment cline=cline+1 row=row/>
			</#if>	
		</#if>
	</#macro>
	<div class="row-fluid list-images">
		<#assign index = 0>
		<#assign sizePerLine = widget.settings.sizePerLine?number>
		<#assign spanClass = 'span'+12/sizePerLine>
		<#assign lineCount = (comments?size/sizePerLine)?ceiling>
		
		<#list 1..sizePerLine as rn>
			<div class="${spanClass}">
				<@loopRenderComment cline=0 row=rn />
			</div>
		</#list>
	 </div>
 </#if>