<#if comment??>
	<link rel="stylesheet" href="${resource}/detail.css" />
	<div class="news-detail">
		<div class="news-detail-bar">
			<div class="pull-right">
				<div class="btn-group">
					<button type="button" class="btn news-detail-like disabled"><i class="icon icon-heart"></i>&nbsp;喜欢 ${comment.liked}</button>
				</div>
				<div class="btn-group">
					<button type="button" class="btn news-detail-comments"><i class="icon icon-comment"></i>&nbsp;评论 <span class="news-detail-comment-count">${comment.commented}</span></button>
				</div>
				<div class="btn-group jiathis_style">
					<span class="jiathis_txt">分享到：</span>
					<a class="jiathis_button_qzone"></a>
					<a class="jiathis_button_tsina"></a>
					<a class="jiathis_button_tqq"></a>
					<a class="jiathis_button_weixin"></a>
					<a class="jiathis_button_renren"></a>
					<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
					<a class="jiathis_counter_style"></a>
				</div>
			</div>
			<h4 class="pull-left">${comment.title}</h4>
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
	
	<#if (widget.settings.commentSize)?number gt 0>
		<div class="news-comment">
			<#if children??>
				<#list children as cc>
					<div class="news-comment-item media news-comment-coment">
						<a class="pull-left" href="javascript:void(0)">
							<img class="media-object" src="${ctx}/static/images/user-34.jpg">
						</a>
						<div class="media-body">
							<h5 class="media-heading">
							<#if cc.creator?? >${cc.creator.name}<#else>某骚年</#if>
							<span class="news-comment-date">${cc.createAt?string('yyyy-MM-dd HH:mm')}</span></h5>
							<p>${cc.content}</p>
						</div>
					</div>
					
					
					
				</#list>
			</#if>
			<#if comment.commented gt (widget.settings.commentSize)?number>
				<div class="news-comment-more alert alert-info text-center">点此加载更多评论</div>
			</#if>
			<div class="news-comment-item media news-comment-area">
				<a class="pull-left" href="javascript:void(0)">
					<img class="media-object" src="${ctx}/static/images/user-34.jpg">
				</a>
				<div class="media-body">
					<form action="${action}/pushs/addComment" method="post">
						<input type="hidden" name="referer" value="${comment.id}"/>
						<h5 class="media-heading">就是你，骚年！</h5>
						<textarea name="content"></textarea>
						<input type="submit" class="btn btn-primary sendComment" id="sendComment" value="发表评论">
					</form>
				</div>
			</div>
		</div>
		<div class="data-holder" commentId="${comment.id}" ctx="${ctx}" commentCount="${comment.commented}" commentLimit="${(widget.settings.commentSize)?number}" moreCommentUrl="${action}/pushs/moreComment?referer=${comment.id}&limit=${(widget.settings.commentSize)?number}"></div>
	</#if>
	<script charset="utf-8" src="${resource}/detail.js"></script>
</#if>					