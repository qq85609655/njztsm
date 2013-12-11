<%@page import="com.sklay.core.enums.Sex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<div class="widget-box">

<div class="alert alert-info">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <h4>说明!</h4>
   <label class="control-label">请认真填写如下信息！</label>
</div>
	
	<div class="widget-content tab-content" id="createForm">
		<form class="form-horizontal" action="${ctx}/admin/member/update" method="post">
		<div class="control-group">
	        <div class="controls">
	        <input type="hidden" name="id" value="${member.id }">
	        </div>
	    </div>
	    <c:if test="${session.group.id eq member.group.parentGroupId }">
	    <div class="control-group">
	        <label class="control-label" for="inputUserGroup">所在分组</label>
	        <div class="controls">
	        	<select name="groupId" id="inputUserGroup" ${disabled }>
		        	<c:forEach items="${groups }" var="group" varStatus="status">
					  <option value="${group.id }" <c:if test="${member.group.id eq group.id }">selected="selected"</c:if>>${group.name }</option>
		        	</c:forEach>
				</select>
	        </div>
	      </div>
	      </c:if>
	      <div class="control-group">
	        <label class="control-label" for="inputName">真实姓名</label>
	        <div class="controls">
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
	        <label class="control-label" for="inputSex">性别</label>
	        <div class="controls">
	          	<label class="radio inline">
				  <input type="radio" name="sex" id="inputSex1" value="<%=Sex.MALE %>" <c:if test="${member.sex.value eq 1}">checked</c:if>><%=Sex.MALE.getLable() %>
				</label>
				<label class="radio inline">
				  <input type="radio" name="sex" id="inputSex2" value="<%=Sex.FEMALE %>" <c:if test="${member.sex.value eq 2}">checked</c:if>><%=Sex.FEMALE.getLable() %>
				</label>
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputAge">年龄</label>
	        <div class="controls">
	          <input class="span3" type="number" min="1" required="required" name="age" id="inputAge" placeholder="未填写年龄" value="${member.age }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputHeight">身高</label>
	        <div class="controls">
	          	<input class="span3" type="number" min="1" required="required" name="height" id="inputHeight" placeholder="未填写净身高" value="${member.height }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputWeight">体重</label>
	        <div class="controls">
	          	<input class="span3" type="number" min="1" required="required" name="weight" id="inputWeight" placeholder="未填写体重" value="${member.weight }">
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
	          	<textarea class="span3" rows="3" cols="5"  name="description" id="inputDescription" placeholder="未填写备注">${member.description }${member.description }</textarea>
	        </div>
	      </div>
	      <div class="control-group">
	        <div class="controls">
	          <button type="button" class="btn btn-primary"  title="" data-delay="5" data-html="true" data-href="${ctx}/admin/member/list" data-content="修改成功." data-original-title="消息...">提交</button>
	        </div>
	      </div>
	    </form>
   	</div>
</div>