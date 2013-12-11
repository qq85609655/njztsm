<%@page import="java.io.PrintStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Exception ex = (Exception)request.getAttribute("ex");
	ex.printStackTrace(new java.io.PrintWriter(out));
%>