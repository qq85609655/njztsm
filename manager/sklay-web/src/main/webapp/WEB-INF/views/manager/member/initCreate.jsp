<%@page import="com.sklay.core.enums.MemberRole"%>
<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@page import="com.sklay.core.enums.Sex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<script type="text/javascript" src="${ctx}/static/thirdparty/datepicker/WdatePicker.js"></script>

<div class="widget-box">	
	<div class="alert alert-info" style="font-size: 14px;text-align: center;">
	  <h4>说明!</h4>
	   <p>请认真填写如下信息！红色带星号的为必填项</p>
	</div>
	<div class="widget-content tab-content" id="createForm">
		<form class="form-horizontal" action="${ctx }/admin/member/create" method="post">
	      <sklay:hasRole value="2">
	      <div class="control-group">
	        <label class="control-label" for="inputAuditStatus">审核状态</label>
	        <div class="controls">
	          	<label class="radio inline span1">
				  <input type="radio" name="status" id="inputAuditStatus1" value="<%=AuditStatus.WAIT %>" checked><%=AuditStatus.WAIT.getLable() %>
				</label>
				<label class="radio inline span1">
				  <input type="radio" name="status" id="inputAuditStatus3" value="<%=AuditStatus.PASS %>"><%=AuditStatus.PASS.getLable() %>
				</label>
				<label class="radio inline span1">
				  <input type="radio" name="status" id="inputAuditStatus2" value="<%=AuditStatus.NOT %>"><%=AuditStatus.NOT.getLable() %>
				</label>
	        </div>
	      </div>
	      </sklay:hasRole>
	      <div class="control-group">
	        <label class="control-label" for="inputUserGroup"><span style="color: red;">*</span> 所在分组</label>
	        <div class="controls">
	        	<select name="groupId" id="inputUserGroup">
		        	<c:forEach items="${groups }" var="group" varStatus="status">
					  <option value="${group.id }" <c:if test="${status.index eq 0 }">selected="selected"</c:if>>${group.name }</option>
		        	</c:forEach>
				</select>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputName"><span style="color: red;">*</span> 真实姓名</label>
	        <div class="controls">
	          <input class="span3" type="text" name="name" id="inputName" placeholder="真实姓名">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputPhone"><span style="color: red;">*</span> 手机号码</label>
	        <div class="controls">
	          <input class="span3" type="tel" name="phone" id="inputPhone" placeholder="手机号码">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputSex"><span style="color: red;">*</span> 性别</label>
	        <div class="controls">
	          	<label class="radio inline">
				  <input type="radio" name="sex" id="inputSex1" value="<%=Sex.MALE %>" checked><%=Sex.MALE.getLable() %>
				</label>
				<label class="radio inline">
				  <input type="radio" name="sex" id="inputSex2" value="<%=Sex.FEMALE %>"><%=Sex.FEMALE.getLable() %>
				</label>
	        </div>
	      </div>
	      <div class="control-group">
	        	<label class="control-label" for="inputBirthday"><span style="color: red;">*</span> 生日</label>
	        	<div class=" controls">
				    <input class="span3 Wdate" type="text" required="required" onfocus="WdatePicker({startDate:'1949-01-01',dateFmt:'yyyy-MM-dd' ,maxDate:'{%y-10}-%M-%d'})" id="inputBirthday" name="birth" placeholder="生日">
				</div>      
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputArea"><span style="color: red;">*</span> 地区</label>
	        <div class="controls">
	          <input class="span3" type="text" name="area" id="inputArea" placeholder="地区">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputAge"><span style="color: red;">*</span> 年龄</label>
	        <div class="controls">
	          <input class="span3" type="number" min="1" required="required"  name="age" id="inputAge" placeholder="年龄">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputHeight">身高</label>
	        <div class=" controls">
	          	<input class="span3" type="number" min="1" required="required" name="height" id="inputHeight" placeholder="净身高(CM)">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputWeight">体重</label>
	        <div class="controls">
	          	<input class="span3" type="number" min="1" required="required" data-content="ssss"  name="weight" id="inputWeight" placeholder="体重(KG)">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputMedicalHistory">病史</label>
	        <div class="controls">
	        	<textarea class="span3" rows="4" cols="5"  name="medicalHistory" id="inputMedicalHistory" placeholder="有无病史情况"></textarea>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputAddress">详细地址</label>
	        <div class="controls">
	          <input class="span3" type="text" name="address" id="inputAddress" placeholder="详细联系地址">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputDescription">备注</label>
	        <div class="controls">
	          	<textarea class="span3" rows="3" cols="5"  name="description" id="inputDescription" placeholder="备注"></textarea>
	        </div>
	      </div>
	      <div class="control-group">
	        <div class="controls">
	          <button type="button" class="btn btn-primary"  title="" data-delay="5" data-html="true" data-href="${ctx}/admin/member/list" data-content="添加成功." data-original-title="消息...">提交</button>
	        </div>
	      </div>
	    </form>
   	</div>
</div>