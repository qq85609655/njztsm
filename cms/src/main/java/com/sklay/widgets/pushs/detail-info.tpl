<#if comments??>
<#list comments as comment>
	<link rel="stylesheet" href="${resource}/detail.css" />
	<div class="news-detail" style="width:${widget.settings.width!!}; height:${widget.settings.height!!};">
		<div class="news-detail-bar">
			<#if widget.settings.showTitle == "true" >
			<h4 class="text-center">${comment.title}</h4>
			</#if>
		</div>
		<div class="news-detail-content">
			<#if comment.fragment??>
				${comment.content}
				<#else>
				<p class="text-center">
					${comment.content}
				</p>
			</#if>
		</div>
		<div class="news-detail-footer">
			<a href="http://${(comment.from)!'SKLAY.NET'}">${(comment.from)!'SKLAY.NET'}</a>
		</div>
   </div>
</#list>
</#if>					