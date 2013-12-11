<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>app</title>
    <meta name="tab" content="app"/>
</head>
<body>
<div class="t-title">获取应用 ${app.name} 跟踪代码</div>
请将下面的JavaScript代码插入你的网站中
<div style="padding: 10px;">
<textarea style="width: 800px;height: 30px;padding: 2px;" onmouseover="this.select();">
<script src="${trackUrl}/boot/${app.id}" type="text/javascript" async="async" defer="defer"></script>
</textarea>
</div>
或者
<div style="padding: 10px;">
<textarea style="width: 800px;height: 200px;padding: 2px" onmouseover="this.select();">
(function () {
    var c = document.createElement('script');
    c.type = 'text/javascript';
    c.async = true;
    c.src = '${trackUrl}/boot/${app.id}';
    var h = document.getElementsByTagName('script')[0];
    h.parentNode.insertBefore(c, h);
})();
</textarea>
</div>
<input type="button" class="a-btn" value="返回" onclick="location.href='${ctx}/console/app';">
</body>
</html>
