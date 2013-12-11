<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<div class="widget-box">
<c:choose>
<c:when test="${empty success }">

	<div class="widget-content tab-content" id="createForm">
		<form class="form-horizontal" action="${ctx }/app/sms" method="post">
	      <div class="control-group">
	        <label class="control-label" for="inputPhones">手机号码</label>
	        <div class="controls">
	        	<textarea rows="9" cols="7" class="span5" name="phones" id="inputPhones" placeholder="手机号码(多个以,隔开)">${phones }</textarea>
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputContent">短信内容</label>
	        <div class="controls">
	          	<textarea class="span5" rows="7" cols="5"  name="content" id="inputContent" placeholder="短信类容"></textarea>
	        </div>
	      </div>
	      <div class="control-group">
	        <div class="controls">
	          <button type="button" class="btn btn-primary"  title="" data-delay="5" data-html="true" data-href="${ctx}/admin/sms/list" data-content="已提交短信请求." data-original-title="消息...">提交</button>
	        </div>
	      </div>
	    </form>
   	</div>
</c:when>
<c:otherwise>
<div class="alert alert-error">
 ${success }
</div>
</c:otherwise>
</c:choose>
</div>