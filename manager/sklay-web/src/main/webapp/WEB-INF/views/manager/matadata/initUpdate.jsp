<%@page import="com.sklay.core.enums.Sex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<div class="widget">	
	<div class="widget-content" id="createForm" style="border-bottom:0px;">
		<form class="form-horizontal" action="${ctx }/admin/matadata/update" method="post">
	      <div class="control-group">
	        <label class="control-label">年龄范围</label>
	        <div class="controls">
	        	<input type="hidden" name="id" id="id" value="${mataData.id }">
	          <input class="input-mini" type="number" name="ageMin" id="ageMin" placeholder="年龄小值" value="${mataData.ageMin }">
	          <i class="icon-arrow-right"></i>
	          <input class="input-mini" type="number" name="ageMax" id="ageMax" placeholder="年龄大值" value="${mataData.ageMax }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputSex">性别</label>
	        <div class="controls">
	          	<label class="radio inline">
				  <input type="radio" name="sex" id="inputSex1" value="<%=Sex.MALE %>" <c:if test="${mataData.sex.value eq 1 }">checked</c:if>><%=Sex.MALE.getLable() %>
				</label>
				<label class="radio inline">
				  <input type="radio" name="sex" id="inputSex2" value="<%=Sex.FEMALE %>" <c:if test="${mataData.sex.value eq 2 }">checked</c:if>><%=Sex.FEMALE.getLable() %>
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label">血压低值范围</label>
	        <div class="controls">
	          <input class="input-mini" type="number" name="lowPressureMin" id="lowPressureMin" placeholder="血压低值小值" value="${mataData.lowPressureMin }">
	          <i class="icon-arrow-right"></i>
	          <input class="input-mini" type="number" name="lowPressureMax" id="lowPressureMax" placeholder="血压低值大值" value="${mataData.lowPressureMax }">
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label">血压高值范围</label>
	        <div class="controls">
	          <input class="input-mini" type="number" name="highPressureMin" id="highPressureMin" placeholder="血压高值小值" value="${mataData.highPressureMin }">
	          <i class="icon-arrow-right"></i>
	          <input class="input-mini" type="number" name="highPressureMax" id="highPressureMax" placeholder="血压高值大值" value="${mataData.highPressureMax }">
	        </div>
	      </div>
	      
	      
	      <div class="control-group">
	        <label class="control-label" for="result">诊断结果</label>
	        <div class="controls">
	         	<textarea class="span3" rows="3" cols="5"  name="result" id="result" placeholder="诊断结果">${mataData.result }</textarea>
	        </div>
	      </div>
	      <div class="control">
	        <label class="control-label" for="remark">备注</label>
	        <div class="controls">
	          	<textarea class="span3" rows="3" cols="5"  name="remark" id="remark" placeholder="备注">${mataData.remark }</textarea>
	        </div>
	      </div>
	    </form>
   	</div>
</div>