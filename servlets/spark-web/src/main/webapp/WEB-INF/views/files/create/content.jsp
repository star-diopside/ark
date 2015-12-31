<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="page-header">ファイル登録</h1>
<spring:url var="submitUrl" value="/files" />
<form:form action="${submitUrl}" modelAttribute="fileCreateForm" cssClass="form-horizontal" role="form" enctype="multipart/form-data">
  <form:errors element="div" cssClass="alert alert-danger" />
  <div class="form-group<form:errors path='file'> has-error</form:errors>">
    <label for="file" class="col-sm-2 control-label">ファイル</label>
    <div class="col-sm-10">
      <input type="file" name="file" id="file" class="form-control" />
      <form:errors path="file" element="span" cssClass="help-block" />
    </div>
  </div>
  <div class="form-group mainbtn-area">
    <div class="col-sm-12">
      <div class="pull-right">
        <input type="submit" value="登録" class="btn btn-default" />
      </div>
    </div>
  </div>
</form:form>
