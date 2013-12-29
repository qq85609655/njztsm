<%@page import="com.sklay.core.enums.SwitchStatus"%>
<%@page import="com.sklay.core.enums.FetchType"%>
<%@page import="com.sklay.core.enums.AuditStatus"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/thirdparty/bootstrap-timepicker/css/bootstrap-timepicker.min.css">
<script type="text/javascript" src="${ctx}/static/thirdparty/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>

	<ul class="nav nav-tabs">
	    <li <c:if test="${('server' eq active) || (empty active)}"> class="active" </c:if>><a href="#widget-server" data-toggle="tab">服务器配置</a></li>
	    <li <c:if test="${'client' eq active}"> class="active" </c:if>><a href="#widget-client" data-toggle="tab">客户端配置</a></li>
	</ul>
<div class="tab-content ">	
	<div class="tab-pane <c:if test="${('server' eq active) || (empty active)}"> active </c:if>" id="widget-server">
		<form class="form-horizontal" action="${ctx }/admin/setting?active=server" method="post">
	      <input type="hidden" name="id" value="${setting.id }" />
	      <div class="control-group">
	        <label class="control-label" for="inputUserAuditStatus">用户审核状态</label>
	        <div class="controls">
	          	<label class="radio inline span1" for='inputUserAuditStatus1'>
				  <input type="radio" name="userAudite" id="inputUserAuditStatus1" value="<%=AuditStatus.WAIT %>" <c:if test="${setting.userAudite.value eq 0}">checked</c:if> /><%=AuditStatus.WAIT.getLable() %>
				</label>
				<label class="radio inline span1" for='inputUserAuditStatus3'>
				  <input type="radio" name="userAudite" id="inputUserAuditStatus3" value="<%=AuditStatus.PASS %>" <c:if test="${setting.userAudite.value eq 2}">checked</c:if> /><%=AuditStatus.PASS.getLable() %>
				</label>
				<label class="radio inline span1" for='inputUserAuditStatus2'>
				  <input type="radio" name="userAudite" id="inputUserAuditStatus2" value="<%=AuditStatus.NOT %>" <c:if test="${setting.userAudite.value eq 1}">checked</c:if> /><%=AuditStatus.NOT.getLable() %>
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label">分组审核状态</label>
	         <div class="controls">
	          	<label class="radio inline span1" for="inputGroupAudite1">
				  <input type="radio" name="groupAudite" id="inputGroupAudite1" value="<%=AuditStatus.WAIT %>" <c:if test="${setting.groupAudite.value eq 0}">checked</c:if>><%=AuditStatus.WAIT.getLable() %>
				</label>
				<label class="radio inline span1" for="inputGroupAudite3">
				  <input type="radio" name="groupAudite" id="inputGroupAudite3" value="<%=AuditStatus.PASS %>" <c:if test="${setting.groupAudite.value eq 1}">checked</c:if>><%=AuditStatus.PASS.getLable() %>
				</label>
				<label class="radio inline span1" for="inputGroupAudite2">
				  <input type="radio" name="groupAudite" id="inputGroupAudite2" value="<%=AuditStatus.NOT %>" <c:if test="${setting.groupAudite.value eq 2}">checked</c:if>><%=AuditStatus.NOT.getLable() %>
				</label>
	        </div>
	      </div>
	      
	       <div class="control-group">
	        <label class="control-label">设备绑定默认状态</label>
	         <div class="controls">
	          	<label class="radio inline span1" for="inputDeviceBindingStatus1">
				  <input type="radio" name="deviceBindingStatus" id="inputDeviceBindingStatus1" value="<%=AuditStatus.WAIT %>" <c:if test="${setting.deviceBindingStatus.value eq 0}">checked</c:if>><%=AuditStatus.WAIT.getLable() %>
				</label>
				<label class="radio inline span1" for="inputDeviceBindingStatus3">
				  <input type="radio" name="deviceBindingStatus" id="inputDeviceBindingStatus3" value="<%=AuditStatus.PASS %>" <c:if test="${setting.deviceBindingStatus.value eq 1}">checked</c:if>><%=AuditStatus.PASS.getLable() %>
				</label>
				<label class="radio inline span1" for="inputDeviceBindingStatus2">
				  <input type="radio" name="deviceBindingStatus" id="inputDeviceBindingStatus2" value="<%=AuditStatus.NOT %>" <c:if test="${setting.deviceBindingStatus.value eq 2}">checked</c:if>><%=AuditStatus.NOT.getLable() %>
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label">级联创建分组</label>
	        <div class="controls">
	        	<label class="radio inline span1" for="inputAutoCreateGroup1">
				  <input type="radio" name="autoCreateGroup" id="inputAutoCreateGroup1" value="<%=SwitchStatus.CLOSE %>" <c:if test="${setting.autoCreateGroup.value eq 0}">checked</c:if>><%=SwitchStatus.CLOSE.getLable() %>
				</label>
	          	<label class="radio inline span1" for="inputAutoCreateGroup2">
				  <input type="radio" name="autoCreateGroup" id="inputAutoCreateGroup2" value="<%=SwitchStatus.OPEN %>" <c:if test="${setting.autoCreateGroup.value eq 1}">checked</c:if>><%=SwitchStatus.OPEN.getLable() %>
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label">体检级联发送附属手机</label>
	         <div class="controls">
				<label class="radio inline span1">
				  <input type="radio" name="smsFetch" id="inputSMSFetch2" value="<%=SwitchStatus.CLOSE %>" <c:if test="${setting.smsFetch.value eq 0}">checked</c:if>><%=SwitchStatus.CLOSE.getLable() %>
				</label>
	          	<label class="radio inline span1">
				  <input type="radio" name="smsFetch" id="inputSMSFetch1" value="<%=SwitchStatus.OPEN %>" <c:if test="${setting.smsFetch.value eq 1}">checked</c:if>><%=SwitchStatus.OPEN.getLable() %>
				</label>
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label">求救短信开关</label>
	        <div class="controls">
	        	<label class="radio inline span1" for="sosSMS1">
				  <input type="radio" name="sosSMS" id="sosSMS1" value="<%=SwitchStatus.CLOSE %>" <c:if test="${setting.sosSMS.value eq 0}">checked</c:if>><%=SwitchStatus.CLOSE.getLable() %>
				</label>
	          	<label class="radio inline span1" for="sosSMS2">
				  <input type="radio" name="sosSMS" id="sosSMS2" value="<%=SwitchStatus.OPEN %>" <c:if test="${setting.sosSMS.value eq 1}">checked</c:if>><%=SwitchStatus.OPEN.getLable() %>
				</label>
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label">体检短信开关</label>
	         <div class="controls">
	         	<label class="radio inline span1" for="inputPhysicalSMS2">
				  <input onclick="javascript:$('.time-switch').hide() ;" type="radio" name="physicalSMS" id="inputPhysicalSMS2" value="<%=SwitchStatus.CLOSE %>" <c:if test="${setting.physicalSMS.value eq 0}">checked</c:if> title="(不发送)"><%=SwitchStatus.CLOSE.getLable() %>
				</label>
	          	<label class="radio inline span1" for="inputPhysicalSMS1">
				  <input onclick="javascript:$('.time-switch').hide() ;" type="radio" name="physicalSMS" id="inputPhysicalSMS1" value="<%=SwitchStatus.OPEN %>" <c:if test="${setting.physicalSMS.value eq 1}">checked</c:if> title="(全程发送)">全程发送
				</label>
				<label onclick="javascript:$('.time-switch').show() ;" class="radio inline span1" for="inputPhysicalSMS3">
				  <input type="radio" name="physicalSMS" id="inputPhysicalSMS3" value="<%=SwitchStatus.PERIOD %>" <c:if test="${setting.physicalSMS.value eq 2}">checked</c:if> title="(时段发送)"><%=SwitchStatus.PERIOD.getLable() %>
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group time-switch <c:if test="${!(setting.physicalSMS.value eq 2)}">hide</c:if>">
	        <label class="control-label">上午发送时间点</label>
	         <div class="controls">
				<label class="inline span input-append bootstrap-timepicker" for="inputSendStartTime">
				 	<input type="text" data-pick="pick" class="input-mini" name="sendStartTime" id="inputSendStartTime" value="${setting.sendStartTime}" readonly="readonly"><span class="add-on"><i class="icon-time"></i></span>
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group time-switch <c:if test="${!(setting.physicalSMS.value eq 2)}">hide</c:if>">
	        <label class="control-label">下午发送时间点</label>
	         <div class="controls">
	          	<label class=" inline span input-append bootstrap-timepicker" for="inputSendEndTime">
				 	<input type="text" data-pick="pick" class="input-mini" name="sendEndTime" id="inputSendEndTime" value="${setting.sendEndTime}" readonly="readonly"><span class="add-on"><i class="icon-time"></i></span>
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputVisitCount">每天每人体检短信</label>
	        <div class="controls">
       		  <label class=" inline span1" for="inputVisitCount">
				  <input type="number" class="input-mini" min="1" name="visitCount" id="inputVisitCount" value="${setting.visitCount}">
			  </label>
	        </div>
	      </div>
	      
	      <div class="control-group hide">
	        <label class="control-label">短信任务开关</label>
	         <div class="controls">
	         	<label class="radio inline span1" for="inputSendSMSJob2">
				  <input type="radio" name="sendSMSJob" id="inputSendSMSJob2" value="<%=SwitchStatus.CLOSE %>" <c:if test="${setting.sendSMSJob.value eq 0}">checked</c:if> ><%=SwitchStatus.CLOSE.getLable() %>
				</label>
	          	<label class="radio inline span1" for="inputSendSMSJob1">
				  <input type="radio" name="sendSMSJob" id="inputSendSMSJob1" value="<%=SwitchStatus.OPEN %>" <c:if test="${setting.sendSMSJob.value eq 1}">checked</c:if> ><%=SwitchStatus.OPEN.getLable() %>
				</label>
	        </div>
	      </div>
	      <div class="control-group hide">
	        <label class="control-label" for="inputSendSMSTime">短信任务时间</label>
	         <div class="controls">
	         	<label class=" inline span1" for="inputSendSMSTime">
				  <input type="datetime" name="sendSMSTime" id="inputSendSMSTime" value="${setting.sendSMSTime }" >
				  </label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label">开启api日志跟踪 </label>
	        <div class="controls">
	        	<label class="radio inline span1" for="apiLogSwitch1">
				  <input type="radio" name="apiLogSwitch" id="apiLogSwitch1" value="<%=SwitchStatus.CLOSE %>" <c:if test="${setting.apiLogSwitch.value eq 0}">checked</c:if>><%=SwitchStatus.CLOSE.getLable() %>
				</label>
	          	<label class="radio inline span1" for="apiLogSwitch2">
				  <input type="radio" name="apiLogSwitch" id="apiLogSwitch2" value="<%=SwitchStatus.OPEN %>" <c:if test="${setting.apiLogSwitch.value eq 1}">checked</c:if>><%=SwitchStatus.OPEN.getLable() %>
				</label>
	        </div>
	      </div>
	      <div class="control-group hide">
	        <label class="control-label" for="inputBindingCount">允许绑定免费设备数</label>
	        <div class="controls">
       		  <label class=" inline span1" for="inputBindingCount">
				  <input type="number" class="input-mini" min="1" name="bindingCount" id="inputBindingCount" value="${setting.bindingCount}">
			  </label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputWebSite">网站标题</label>
	         <div class="controls">
	         	<label class=" inline span1" for="inputWebSite">
				  <input type="text" class="span4" name="webSite" id="inputWebSite" value="${setting.webSite }" >
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputCopyRight">网站版权</label>
	         <div class="controls offset4">
	         	<label class=" inline span1" for="inputCopyRight">
				 <textarea class="span4" rows="4" cols="5" name="copyRight" id="inputCopyRight" >${setting.copyRight }</textarea>
				</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputWebService">网站服务内容</label>
	         <div class="controls">
	         	<label class=" inline span1" for="inputWebService">
				  <textarea class="span4" rows="4" cols="5" name="webService" id="inputWebService" >${setting.webService }</textarea>
	        	</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputWebDescription">自定义Meta</label>
	         <div class="controls">
	         	<label class=" inline span1" for="inputWebMeta">
				  <textarea class="span4" rows="4" cols="5" name="webMeta" id="inputWebMeta" >${setting.webMeta }</textarea>
	        	</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <label class="control-label" for="inputWebDescription">网站描述</label>
	         <div class="controls">
	         	<label class=" inline span1" for="inputWebDescription">
				  <textarea class="span4" rows="4" cols="5" name="webDescription" id="inputWebDescription" >${setting.webDescription }</textarea>
	        	</label>
	        </div>
	      </div>
	      
	      <div class="control-group">
	        <div class="controls">
	          <button type="button" class="btn btn-primary"  title="" data-delay="5" data-html="true" data-content="操作成功." data-original-title="消息..." data-href="${ctx}/admin/initSetting">提交</button>
	        </div>
	      </div>
	    </form>
   	</div>
   	
   	<div class="tab-pane <c:if test="${'client' eq active}"> active </c:if>" id="widget-client">
		<form id="form_upVer" class="form-horizontal" action="${ctx }/admin/upver?active=client" method="post" enctype="multipart/form-data">
	      <input type="hidden" name="token" value="${token}">
	      <c:if test="${!(empty error) }">
		      <div class="alert alert-error">
	            <strong>${error }</strong>
	          </div>
	      </c:if>
	      <c:if test="${!(empty success) }">
		      <div class="alert alert-info">
	            <strong>${success }</strong>
	          </div>
	      </c:if>
	      <div class="control-group">
	        <label class="control-label" for="versionCode">版本号</label>
	        <div class="controls">
	          <input type="text" class="span3" id="versionCode" name="versionCode" value="${mobileVer.versionCode }" >
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="versionName">版本名称</label>
	        <div class="controls">
	          <input type="text" class="span3" id="versionName" name="versionName" value="${mobileVer.versionName }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="downloadUrl">更新地址</label>
	        <div class="controls">
	          <input type="text" class="span5" id="downloadUrl" name="downloadUrl" value="${mobileVer.downloadUrl }">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="updateLog">更新内容</label>
	        <div class="controls">
	          <textarea cols="7" rows="5" class="span5" id="updateLog" name="updateLog" >${mobileVer.updateLog }</textarea>
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputTelecom">版本文件</label>
	        <div class="controls">
	          <input type="file" class="span3" id="verFile" name="ver">
	        </div>
	      </div>
	      <div class="control-group">
	        <div class="controls">
	          <button type="submit" class="btn "  title="" data-delay="5" data-html="true" data-href='${ctx}/admin/sms/initSetting' data-original-title="消息...">发布</button>
	        </div>
	      </div>
	  </form>
	</div>
</div>
<script type="text/javascript">
     $('[data-pick="pick"]').timepicker({
    	 minuteStep:0,
    	 showMeridian:false,
    	 showInputs:false
     });
     
</script>