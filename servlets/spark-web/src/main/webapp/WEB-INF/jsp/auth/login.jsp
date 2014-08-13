<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<spring:url value='/static/bootstrap/css/bootstrap.min.css' />">
    <link rel="stylesheet" href="<spring:url value='/static/bootstrap/css/bootstrap-theme.min.css' />">
    <script src="<spring:url value='/static/bootstrap/js/bootstrap.min.js' />"></script>
    <title>ユーザログイン</title>
  </head>
  <body>
    <div class="container">
      <h1>ユーザログイン</h1>
      <spring:url var="mainFormUrl" value="/auth/login" />
      <form:form action="${mainFormUrl}" modelAttribute="loginForm" cssClass="form-horizontal">
        <form:errors element="div" cssClass="alert alert-danger" />
        <div class="form-group<form:errors path='username'> has-error</form:errors>">
          <label for="username" class="col-sm-2 control-label">ユーザ名</label>
          <div class="col-sm-10">
            <form:input path="username" id="username" cssClass="form-control" />
            <form:errors path="username" element="span" cssClass="help-block" />
          </div>
        </div>
        <div class="form-group<form:errors path='password'> has-error</form:errors>">
          <label for="password" class="col-sm-2 control-label">パスワード</label>
          <div class="col-sm-10">
            <form:password path="password" id="password" cssClass="form-control" autocomplete="off" />
            <form:errors path="password" element="span" cssClass="help-block" />
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-12">
            <input type="submit" name="login" value="ログイン" class="btn btn-default" />
          </div>
        </div>
      </form:form>
    </div>
  </body>
</html>
