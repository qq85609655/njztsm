<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

	<div class="tab-content" id="createForm">
		<form class="form-horizontal" action="${ctx }/admin/sms/resend/${model.id}" method="post">
		  <div class="control-group">
	        <label class="control-label">收信人姓名  </label>
	        <div class="controls">
	        	<label class="control-label"><strong>${model.receiver.name}</strong></label>
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label">收信人手机号</label>
	        <div class="controls">
	        	<label class="control-label"><strong>${model.receiver.phone}</strong></label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputContent">短信内容</label>
	        <div class="controls">
	        	<textarea rows="5" cols="5" class="span3" name="content" id="inputContent">${model.content }</textarea>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputRemark">备注</label>
	        <div class="controls">
	          <textarea rows="5" cols="5" class="span3" name="remark" id="inputRemark">${model.remark }</textarea>
	        </div>
	      </div>
	    </form>
   	</div>
