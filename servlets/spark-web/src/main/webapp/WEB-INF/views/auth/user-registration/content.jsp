<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="page-header">ユーザ登録</h1>
<spring:url var="submitUrl" value="/auth/user-registration" />
<form:form action="${submitUrl}" modelAttribute="userRegistrationForm" cssClass="form-horizontal" role="form">
  <form:errors element="div" cssClass="alert alert-danger" />
  <div class="form-group<form:errors path='username'> has-error</form:errors>">
    <label for="username" class="col-sm-2 control-label">ユーザ名</label>
    <div class="col-sm-10">
      <form:input path="username" id="username" cssClass="form-control" />
      <form:errors path="username" element="span" cssClass="help-block" />
    </div>
  </div>
  <div class="form-group<form:errors path='nickname'> has-error</form:errors>">
    <label for="nickname" class="col-sm-2 control-label">ニックネーム</label>
    <div class="col-sm-10">
      <form:input path="nickname" id="nickname" cssClass="form-control" />
      <form:errors path="nickname" element="span" cssClass="help-block" />
    </div>
  </div>
  <div class="form-group<form:errors path='password'> has-error</form:errors>">
    <label for="password" class="col-sm-2 control-label">パスワード</label>
    <div class="col-sm-10">
      <form:password path="password" id="password" cssClass="form-control" autocomplete="off" />
      <form:errors path="password" element="span" cssClass="help-block" />
    </div>
  </div>
  <div class="form-group<form:errors path='passwordConfirm'> has-error</form:errors>">
    <label for="passwordConfirm" class="col-sm-2 control-label">パスワード(確認)</label>
    <div class="col-sm-10">
      <form:password path="passwordConfirm" id="passwordConfirm" cssClass="form-control" autocomplete="off" />
      <form:errors path="passwordConfirm" element="span" cssClass="help-block" />
    </div>
  </div>
  <div class="form-group mainbtn-area">
    <div class="col-sm-12">
      <div class="pull-right">
        <input type="submit" name="login" value="新規登録" class="btn btn-default" />
      </div>
      <div class="pull-left">
        <a href="<spring:url value='/auth/user-login' />" class="btn btn-default">戻る</a>
      </div>
    </div>
  </div>
</form:form>
