<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authorize access="isAuthenticated()">
<div class="col-sm-12">
<div class="pull-right">
<spring:url var="logoutUrl" value="/logout" /><form:form action="${logoutUrl}">
<input type="submit" value="ログアウト" class="btn btn-link" />
</form:form>
</div>
</div>
</security:authorize>
