<%@page import="com.sklay.core.enums.AppType"%>
<%@page import="com.sklay.core.enums.Sex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<style>
<!--
.alert-detail {
padding: 8px 35px 8px 14px;
margin-bottom: 20px;
text-shadow: 0 1px 0 rgba(255,255,255,0.5);
background-color: #fcf8e3;
border: 1px solid #fbeed5;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
border-radius: 4px;
color: #3a87ad;
background-color: #d9edf7;
border-color: #bce8f1;
}
-->
</style>
<div class="" id="createForm">
	<div class="alert-detail alert-info">
	  <h4>说明!</h4>
	 单价：${model.price }元/条 ; ${model.description }
	</div>
	<form class="form-horizontal" action="${ctx }/app/apply" method="post">
	      <div class="control-group">
	        <label class="control-label" for="inputCost">费用</label>
	        <div class="controls">
	          <input type="hidden" name="product.id" id="product" value="${model.id }" >
	          <input type="hidden" name="appType" id="appType" value="${model.appType }" >
	          <input class="span3" type="text" name="cost" id="inputCost" placeholder="请输入金额">
	        </div>
	      </div>
	      <div class="control-group">
	        <label class="control-label" for="inputRemark">备注</label>
	        <div class="controls">
	          	<textarea class="span3" rows="3" cols="5"  name="remark" id="inputRemark" placeholder="备注说明"></textarea>
	        </div>
	      </div>
     </form>
</div>