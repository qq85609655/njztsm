<%@ page session="false" pageEncoding="UTF-8" %>
<%@ include file="../common/includes.jsp" %>
<html>
<head>
    <title>app</title>
    <meta name="tab" content="app"/>
    <%@ include file="../common/header.jsp" %>
</head>
<body>
<div class="t-title">${empty param.id?'添加':'编辑'}应用 ${param.id}</div>
<form:form commandName="app" method="post">
    <table class="form-table" style="margin: 10px 5px;width: 800px;">
        <tr>
            <th>名称</th>
            <td><form:input path="name" size="60" cssClass="validate[required]"/></td>
        </tr>
        <tr>
            <th>说明</th>
            <td><form:input path="description" size="60"/></td>
        </tr>
        <tr>
            <th>域名</th>
            <td><form:input path="attrs['domain']" size="60"/></td>
        </tr>
        <tr>
            <th>会话域</th>
            <td>
                <form:input path="attrs['sDomain']" size="60"/>
                <div class="info">会话的域,例如:gankao.any123.com, 除非你了解这个配置项含义,否则留空,注意,父域必须以.开头,例如: .any123.com</div>
            </td>
        </tr>
        <tr>
            <th>自定义脚本</th>
            <td>
                <form:textarea path="attrs['customJs']" cols="80" rows="8"/>
                <div class="info">一些自定义的脚本代码,可以在跟踪脚本载入时运行,方便做一些自定义</div>
            </td>
        </tr>
        <tr>
            <th></th>
            <td>启用<form:checkbox path="enabled"/></td>
        </tr>
        <tr>
            <th style="vertical-align: top;">参数</th>
            <td>
                <table style="width: 100%" class="list-table input-table">
                    <caption style="text-align:right;"><input type="button" class="a-btn" onclick="return addRow(this,'#varTpl');" value="添加"><input type="button" class="a-btn" onclick="return removeSelected(this);" value="删除"></caption>
                    <tr>
                        <th style="width:1px;"><input type="checkbox" onclick="changeSelect(this);"></th>
                        <th style="width: 150px;">Key</th>
                        <th>Value</th>
                    </tr>
                    <c:forEach items="${app.configs}" var="v">
                        <tr>
                            <td><input type="checkbox"></td>
                            <td><input type="text" name="varKey" value="${v.key}"></td>
                            <td><input type="text" name="varValue" value="${v.value}"></td>
                        </tr>
                    </c:forEach>
                </table>
<pre style="padding:10px;" class="info">
目前支持参数
debug： true,false  是否启用调试模式(打开console输出),默认false
uidKey:  保存用户id的cookie key 名,默认为ak_u
sidKey:  保存应用会话id的cookie key名,默认为any123_ut
sTimeout:  跟踪会话有效时间,单位秒,默认为关闭浏览器结束
trackClick:  true,false  是否跟踪页面点击,默认true
trackInput:  true,false  是否跟踪页面输入,默认true
trackAjax:  true,false  是否跟踪jquery ajax请求,默认true
sessionId:  跟踪会话id,仅供调试用
</pre>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" class="a-btn" value="保存">
                <input type="button" class="a-btn" value="返回" onclick="location.href='${ctx}/console/app';">
            </td>
        </tr>
    </table>
</form:form>
<textarea id="varTpl" style="display:none;">
    <tr>
        <td><input type="checkbox"></td>
        <td><input type="text" name="varKey"></td>
        <td><input type="text" name="varValue"></td>
    </tr>
</textarea>
</body>
</html>
