<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<div class="form-horizontal">
	  <div class="control-group">
	     <label class="control-label" for="inputDevice_">已绑定的设备</label>
	     <div class="controls">
        	<c:if test="${fn:length(devices) eq 0}">
        		<label class="inline span2 error">暂无绑定信息</label>
        	</c:if>
			<c:forEach items="${devices}" var="device">
	          	<label class="inline span2 ">
			  		${device.level.lable }<i class="icon-hand-right"></i>${device.serialNumber }<br/>
				</label>
			</c:forEach>
        </div>
  	 </div>
     <div class="control-group">
       <label class="control-label" for="inputName">真实姓名</label>
       <div class="controls">
         <input class="span3" type="text" name="name" id="inputName" placeholder="未填写真实姓名" value="${member.name }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputGroup">所在分组</label>
       <div class="controls">
         <input class="span3" type="text" name="group" id="inputGroup" placeholder="所在分组" value="${member.group.name}" ${ readonly}>
       </div>
     </div>
     <sklay:hasRole value="2">
     <div class="control-group">
       <label class="control-label" for="inputRole">角色</label>
       <div class="controls">
         <input class="span3" type="text" name="role" id="inputRole" placeholder="角色" value="${member.group.role.lable}" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputOwner">隶属于</label>
       <div class="controls">
         <input class="span3" type="text" name="owner" id="inputOwner" placeholder="未填写真实姓名" value="${member.group.owner.name}" ${ readonly}>
       </div>
     </div>
     </sklay:hasRole>
     <div class="control-group">
       <label class="control-label" for="inputPhone">手机号码</label>
       <div class="controls">
         <input class="span3" type="tel" name="phone" id="inputPhone" placeholder="未填写手机号码" value="${member.phone }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputAge">年龄</label>
       <div class="controls">
         <input class="span3" type="text" name="age" id="inputAge" placeholder="未填写年龄" value="${member.age }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label">性别</label>
       <div class="controls"><input class="span3" type="text" placeholder="性别" value="${ member.sex.lable }" ${ readonly}></div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputHeight">身高</label>
       <div class="controls">
         	<input class="span3" type="text" name="height" id="inputHeight" placeholder="未填写净身高" value="${member.height }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputWeight">体重</label>
       <div class="controls">
         	<input class="span3" type="text" name="weight" id="inputWeight" placeholder="未填写体重" value="${member.weight }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputMedicalHistory">病史</label>
       <div class="controls">
       	<textarea class="span3" rows="4" cols="5"  name="medicalHistory" id="inputMedicalHistory" placeholder="未填写病史" ${ readonly}>${member.medicalHistory }</textarea>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputArea">地区</label>
       <div class="controls">
         <input class="span3" type="text" name="area" id="inputArea" placeholder="未填写地区" value="${member.area }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputAddress">详细地址</label>
       <div class="controls">
         <input class="span3" type="text" name="address" id="inputAddress" placeholder="未填写详细地址" value="${member.address }" ${ readonly}>
       </div>
     </div>
     <div class="control-group">
       <label class="control-label" for="inputDescription">备注</label>
       <div class="controls">
         	<textarea class="span3" rows="3" cols="5"  name="description" id="inputDescription" placeholder="未填写备注" ${ readonly}>${member.description }</textarea>
       </div>
     </div>
</div>