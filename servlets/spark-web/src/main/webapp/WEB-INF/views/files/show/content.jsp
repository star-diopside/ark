<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1 class="page-header">ファイル詳細</h1>
<div class="form-horizontal">
  <div class="form-group">
    <label for="file" class="col-sm-2 control-label">ファイル名</label>
    <div class="col-sm-10">
      <p class="form-control-static"><a href="<spring:url value='/files/${fileShowForm.attachedFileId}/data' />">${fileShowForm.fileName}</a></p>
    </div>
  </div>
  <div class="form-group">
    <label for="file" class="col-sm-2 control-label">ファイルサイズ</label>
    <div class="col-sm-10">
      <p class="form-control-static"><fmt:formatNumber value="${fileShowForm.size}" type="number" groupingUsed="true" /> バイト</p>
    </div>
  </div>
  <div class="form-group">
    <label for="file" class="col-sm-2 control-label">ハッシュ値</label>
    <div class="col-sm-10">
      <p class="form-control-static">${fileShowForm.digest}</p>
    </div>
  </div>
</div>
