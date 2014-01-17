<%@page import="com.sklay.core.enums.SwitchStatus"%>
<%@page import="com.sklay.core.enums.Sex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/static/thirdparty/datepicker/WdatePicker.js"></script>
<%@ include file="../../include.jsp"%>

	<div id="createForm">
		<form class="form-horizontal" action="${ctx }/admin/festival/update" method="post">
	      <div class="control-group">
	      	<label class="control-label" for="inputName"><span style="color: red;">*</span> 节日名称</label>
	        <div class="controls">
	          <input type="hidden" value="${ festival.id}" name="id" id="inputId">
	          <input class="span3" type="text" value="${ festival.name}" name="name" id="inputName" placeholder="节日名称">
	        </div>
	      </div>
	      
	      <div class="control-group">
	      	<label class="control-label" for="inputJobTime"><span style="color: red;">*</span> 节日时间</label>
	        <div class="controls">
	          <input class="span3 Wdate" value="${ festival.jobTime}" type="text" required="required" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true})" id="inputJobTime" name="jobTime" placeholder="节日日期">
	        </div>
	      </div>
	      
	      <div class="control-group">
	      	<label class="control-label" for="inputSendTime"><span style="color: red;">*</span> 短信时间</label>
	        <div class="controls">
	          <input class="span3 Wdate" value="<fmt:formatDate value="${festival.sendTime}" type="both" dateStyle="default" timeStyle="default"/>" type="text" required="required" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" id="inputSendTime" name="sendTime" placeholder="短信时间">
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label">状态</label>
	        <div class="controls">
	        	<label class="radio inline span1" for="inputSwitchStatus1">
				  <input type="radio" name="switchStatus" id="inputSwitchStatus1" value="<%=SwitchStatus.CLOSE %>" <c:if test="${festival.switchStatus.value eq 0}">checked</c:if>><%=SwitchStatus.CLOSE.getLable() %>
				</label>
	          	<label class="radio inline span1" for="inputSwitchStatus2">
				  <input type="radio" name="switchStatus" id="inputSwitchStatus2" value="<%=SwitchStatus.OPEN %>" <c:if test="${festival.switchStatus.value eq 1}">checked</c:if>><%=SwitchStatus.OPEN.getLable() %>
				</label>
	        </div>
	      </div>
	      
	    </form>
   	</div>
