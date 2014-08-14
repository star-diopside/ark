<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<tiles:insertAttribute name="head" />
<tiles:importAttribute name="title" />
<title><spring:message code="${title}" text="spark-web" /></title>
</head>
<body><tiles:insertAttribute name="content" /></body>
</html>
