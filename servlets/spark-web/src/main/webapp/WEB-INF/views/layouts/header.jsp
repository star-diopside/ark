<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authorize access="isAuthenticated()">
<div class="col-sm-12">
<div class="pull-right">
<a href="<spring:url value='/j_spring_security_logout' />" class="btn btn-link">ログアウト</a>
</div>
</div>
</security:authorize>
