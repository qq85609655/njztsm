<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>App ${param.id} events</title>
    <meta name="tab" content="event"/>
    <script language="javascript" type="text/javascript" src="${ctx}/static/js/thirdparty/My97DatePicker/WdatePicker.js"></script>
    <link href="${ctx}/static/js/thirdparty/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    <%@ include file="../common/header.jsp" %>
</head>
<body>
	<c:set var="isAdvanceMod" value="${not empty param.jsonQuery}" scope="request"/>
	<form action="${ctx}/console/event" method="get" id="eventSearch">
    <table class="list-table" style="width: 100%;">
        <caption>
        	<input type="hidden" name="index" value="1"/>
        	<span id="normalMod" <c:if test="${isAdvanceMod}">style="display: none;"</c:if>>
	        	<c:if test="${not empty apps }">
	                               应用  <select name="appId">
	                   <option value="">--</option>            
	               	   <c:forEach items="${apps}" var="app">
	               	   		<option value="${app.id}" <c:if test="${app.id eq param.appId}">selected="selected"</c:if>>${app.name}</option>
	               	   </c:forEach>                
	               </select>
	            </c:if>
	            path <input type="text" name="path" value="${param.path}">
	                               用户ID <input type="text" name="userId" value="${param.userId}"> 
	                               开始时间  <input type="text" id="startDate" name="startDate" value="${param.startDate}" size="18" class="Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'});">
	                               结束时间  <input type="text" id="endDate" name="endDate" value="${param.endDate}" size="18" class="Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'});">
            </span>
            <span id="advanceMod" <c:if test="${not isAdvanceMod}">style="display: none;"</c:if>>
	            JsonQuery<textarea name="jsonQuery" rows="1" style="margin-top: -2px;padding-top: -2px">${param.jsonQuery}</textarea>按actions拆分<input type="checkbox" name="unwind" value="1" autocomplete="off" <c:if test="${param.unwind==1}">checked="checked"</c:if>>
	        </span>
	        <input type="submit" class="a-btn" value="检 索">
	        <a href="javascript:void(0)" id="modselector"><c:choose><c:when test="${isAdvanceMod}">一般模式</c:when><c:otherwise>高级模式</c:otherwise></c:choose></a>
        </caption>
        <tr>
            <th style="width: 110px;">id</th>
            <th style="width: 120px;">开始时间</th>
            <th style="width: 80px;">持续时间(s)</th>
            <th>标题</th>
            <th>页面加载时间(ms)</th>
            <th>页面内存占用(kb)</th>
            <th>发呆时间(s)</th>
            <th>错误</th>
            <th style="width: 400px;">属性</th>
        </tr>
        <c:forEach items="${page.items}" var="event">
            <tr>
                <td><c:out value="${event.id}"/></td>
                <td><fmt:formatDate value="${event.beginAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><fmt:formatNumber value="${event.duration/1000}" pattern="#0"/></td>
                <td><a href="${event.url}" title="${event.url}" target="_blank"><c:out value="${event.attrs['title']}"/></a></td>
                <td><c:out value="${event.attrs['lt']}"/></td>
                <td><c:out value="${event.attrs['mem']}"/></td>
                <td><c:out value="${event.attrs['idle']}"/></td>
                <td><a href="error/${event.id}"><c:out value="${errors[event.id]}"/></a></td>
                <td><c:out value="${event.attrs}"/></td>
            </tr>
        </c:forEach>
    </table>
    <c:import url="../common/pagination.jsp">
        <c:param name="url" value="?size=${size}"/>
    </c:import>
    </form>
<br/>
<!-- 表单状态保持 -->
<script type="text/javascript">
	$(function(){
		var _f = $("#eventSearch");
		var _index = _f.find("input[name=index]");
		_f.find("input[name!=index]").change(function(){
			_index.val(1);
		});
		_f.on("submit",function(){
			_f.find("span:hidden").remove();
		});
		$(".pagination-info a").click(function(e){
				e.preventDefault();
				var _href = $(this).attr("href");
				_index.val(_href.substr(_href.indexOf("index=")+6));
				_f.submit();
		});
		
		var toAdvanceMod = function(){
			$("#normalMod").hide();
			$("#advanceMod").show();
			$(this).text("一般模式");
		};
		
		var toNormalMod = function(){
			$("#advanceMod").hide();
			$("#normalMod").show();
			$(this).text("高级模式");
		};
		var advanceMod = '${isAdvanceMod}';
		
		if(advanceMod){
			$("#modselector").toggle(toAdvanceMod,toNormalMod);
		}else{
			$("#modselector").toggle(toNormalMod,toAdvanceMod);
		}
	});
</script>
</body>
</html>
