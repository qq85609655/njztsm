<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

	<div class="control-group">
		<label class="controls checkbox span3 info">所在分组:</label>
		<label class=" checkbox span3 offset1">${group.name }</label>
	</div>
	<div class="control-group">
		<label class="controls checkbox span3 info">拥有权限:</label>
	</div>
	<c:forEach items="${widgets}" var="widget" varStatus="status">
		<div class="control-group">
			<label class="controls checkbox span3 info offset1" for="widget_${status.index}">${widget.value.description}
			</label>
			<c:forEach items="${widget.value.childBase}" var="widgetChild" varStatus="childStatus">
				<label class=" checkbox span3 offset2"
					for="widgetChild_${widgetChild.name}_${childStatus.index}">
					 ${widgetChild.description }
				</label>
			</c:forEach>
		</div>
	</c:forEach>


