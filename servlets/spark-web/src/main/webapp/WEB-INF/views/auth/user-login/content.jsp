<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
  <h1 class="page-header">ユーザログイン</h1>
  <spring:url var="mainFormUrl" value="/auth/user-login" />
  <form:form action="${mainFormUrl}" modelAttribute="userLoginForm" cssClass="form-horizontal" role="form">
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
    <div class="form-group mainbtn-area">
      <div class="col-sm-12">
        <div class="pull-right">
          <input type="submit" name="login" value="ログイン" class="btn btn-default" />
        </div>
        <div class="pull-left">
          <a href="<spring:url value='/auth/user-registration' />" class="btn btn-link">ユーザを新規登録する場合はこちら</a>
        </div>
      </div>
    </div>
  </form:form>
</div>
