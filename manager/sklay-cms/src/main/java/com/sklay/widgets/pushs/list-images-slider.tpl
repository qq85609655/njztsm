<link rel="stylesheet" href="${resource}/nivo-slider/themes/${widget.settings.themes!!'default'}/${widget.settings.themes!!'default'}.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${resource}/nivo-slider/nivo-slider.css" type="text/css" media="screen" />
<div class="slider-wrapper theme-${widget.settings.themes!!'default'}">
    <div id="widget-${widget.id}-slider" class="nivoSlider">
       <#if comments?? && comments?size gt 0>
		   <#include "img-url.tpl">
	       <#list comments as comment>
	       		<#assign offset=0>
				<#assign tmp=''>
				<#assign step=2>
			 	<a href="<#if types[comment.owner]??><#assign t = types[comment.owner]><#if (t.detailPage)??>${ctx}/${t.detailPage.alias}?newsId=${comment.id}</#if></#if>" target="_blank">
			      <img src="${ctx}/file/newsImg<@splitId idstr=comment.id/>/0/c0.jpg?ver=${comment.ver}" data-thumb="${ctx}/file/newsImg<@splitId idstr=comment.id/>/0/c0.jpg?ver=${comment.ver}" title="${comment.title!!""}">
		        </a>
			</#list>
	   </#if>
    </div>
</div>
<script type="text/javascript" src="${resource}/nivo-slider/jquery.nivo.slider.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        $('#widget-${widget.id}-slider').nivoSlider(${widget.settings.options!!});
    });
</script>
