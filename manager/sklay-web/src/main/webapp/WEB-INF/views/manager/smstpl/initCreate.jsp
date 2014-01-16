<%@page import="com.sklay.core.enums.SwitchStatus"%>
<%@page import="com.sklay.core.enums.Sex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/static/thirdparty/datepicker/WdatePicker.js"></script>
<%@ include file="../../include.jsp"%>

	<div id="createForm">
		<form class="form-horizontal" action="${ctx }/admin/smstpl/create" method="post">
		  <input name="festival.id" value="${fid }" type="hidden">
	      <div class="control-group">
	      	<label class="control-label" for="inputName"><span style="color: red;">*</span> 模版名称</label>
	        <div class="controls">
	          <input class="span3" type="text" name="tpl" id="inputName" placeholder="模版名称">
	        </div>
	      </div>
	      
	      <div class="control-group">
	      	<label class="control-label" for="inputJobTime"><span style="color: red;">*</span> 短信内容</label>
	        <div class="controls">
	          <textarea rows="3" cols="3" class="span3" name="content"></textarea>
	        </div>
	      </div>
	    </form>
   	</div>
