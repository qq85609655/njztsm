<%@page import="com.sklay.core.enums.Sex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<div class="widget-content tab-content" id="createForm">
	<form class="form-horizontal" action="${ctx }/admin/account/profile" method="post">
	      <div class="control-group">
	        <label class="control-label" for="inputName">真实姓名</label>
	        <div class="controls">
	          <input type="hidden" name="id" id="inputId" value="${member.id }" ${ readonly}>
	          <input class="span3" type="text" name="name" id="inputName" placeholder="未填写真实姓名" value="${member.name }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputPhone">手机号码</label>
	        <div class="controls">
	          <input class="span3" type="tel" name="phone" id="inputPhone" placeholder="未填写手机号码" value="${member.phone }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputAge">年龄</label>
	        <div class="controls">
	          <input class="span3" type="text" name="age" id="inputAge" placeholder="未填写年龄" value="${member.age }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputSex">性别</label>
	        <div class="controls">
	          	<label class="radio inline">
				  <input type="radio" name="sex" id="inputSex1" value="<%=Sex.MALE %>" <c:if test="${ member.sex.value eq '1' }">checked</c:if>><%=Sex.MALE.getLable() %>
				</label>
				<label class="radio inline">
				  <input type="radio" name="sex" id="inputSex2" value="<%=Sex.FEMALE %>" <c:if test="${ member.sex.value eq '2' }">checked</c:if>><%=Sex.FEMALE.getLable() %>
				</label>
	        </div>
	      </div>
	      
	      
	      <div class="control-group">
	        <label class="control-label" for="inputHeight">身高</label>
	        <div class="controls">
	          	<input class="span3" type="text" name="height" id="inputHeight" placeholder="未填写净身高" value="${member.height }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputWeight">体重</label>
	        <div class="controls">
	          	<input class="span3" type="text" name="weight" id="inputWeight" placeholder="未填写体重" value="${member.weight }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputMedicalHistory">病史</label>
	        <div class="controls">
	        	<textarea class="span3" rows="4" cols="5"  name="medicalHistory" id="inputMedicalHistory" placeholder="未填写病史">${member.medicalHistory }</textarea>
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputArea">地区</label>
	        <div class="controls">
	          <input class="span3" type="text" name="area" id="inputArea" placeholder="未填写地区" value="${member.area }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputAddress">详细地址</label>
	        <div class="controls">
	          <input class="span3" type="text" name="address" id="inputAddress" placeholder="未填写详细地址" value="${member.address }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputDescription">备注</label>
	        <div class="controls">
	          	<textarea class="span3" rows="3" cols="5"  name="description" id="inputDescription" placeholder="未填写备注">${member.description }</textarea>
	        </div>
	      </div>
     </form>
</div>