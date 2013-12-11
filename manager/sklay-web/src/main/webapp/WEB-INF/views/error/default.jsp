<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.jsp" %>

<style>
*{margin:0;padding: 0;}
.loginBox{width:520px;height:220px;padding:50px 20px;border:1px solid #fff; color:#000; margin-top:20%;margin-bottom:35%; border-radius:8px;background: white;box-shadow:0 0 15px #222; background: -moz-linear-gradient(top, #fff, #efefef 8%);background: -webkit-gradient(linear, 0 0, 0 100%, from(#f6f6f6), to(#f4f4f4));font:11px/1.5em 'Microsoft YaHei' ; left:50%;top:50%;margin-left:30%;}
.loginBox h2{height:45px;font-size:20px;font-weight:normal;}
.loginBox .left{border-right:1px solid #ccc;height:100%;padding-right: 20px; }
</style>
        <section class="loginBox row-fluid error">
	     	出错了：   <c:out value="${ex}"/>
        </section>
