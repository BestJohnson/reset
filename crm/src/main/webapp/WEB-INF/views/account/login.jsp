<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
  
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="/static/dist/css/skins/_all-skins.min.css">
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="../../index2.html"><b>凯盛软件</b></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg"></p>
	<div class="alert alert-danger" hidden id="message"></div>
    <form id="loginForm" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" id="username" name="username" value="${username}" placeholder="用户名">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" id="password" name="password" value="${password}" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox">
		    <label>
		   <%--  <c:choose>
		    	<c:when test="${ not empty username}">
			      <input type="checkbox"  name="remember" checked value="remember" id="remember"> 记住密码
		    	</c:when>
				<c:otherwise>
			      <input type="checkbox"  name="remember" value="remember" id="remember"> 记住密码
				</c:otherwise>		    
		    </c:choose> --%>
		     <%-- <input type="hidden" id="callback" name="callback" value="${param.callback}"/> --%>
		     
		     <input type="checkbox"  name="remember" 
		     	<c:if test="${not empty username}"> checked</c:if>
		      value="remember" id="remember"> 记住账号
		    </label>
		  </div>
        </div>
        <!-- /.col -->
        <div class="col-offset-8 col-xs-4">
          <button type="button" id="loginBtn" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/dist/js/jquery.validate.min.js"></script>
	
	<script>
		$(function() {
			var callback = "${param.callback}";
			$("#loginBtn").click(function() {
				$("#loginForm").submit();
			});
			
			//表单校验
			$("#loginForm").validate({
				errorClass: "text-danger",
				errorElement: "span",
				rules : {
					username :{
						"required" : true
					},
					password :{
						"required" : true
					}
				},
				messages : { 
					username :{
						"required" : "账户不能为空"
					},
					password :{
						"required" : "密码不能为空"
					}
				},
				
				//ajax提交
				submitHandler : function(form) {
					$.ajax({
						url: "/login",
						type: "post",
						data: $("#loginForm").serialize(),
						
						beforeSend : function() {
							$("#loginBtn").text("登录中...").attr("disabled","disabled");
						},
						success : function(data) {
							//var callback = $("#callback").val();
							if(data.state == 'success') {
								if(callback) {
									window.location.href = callback;
								} else {
									window.location.href = "/account/home";
								}
							} else {
								$("#message").text(data.message).show();
							}
						}, 
						error : function() {
							alert("系统异常");
						},
						complete : function() {
							$("#loginBtn").text("登录").removeAttr("disabled");
						}
											});
				}
				
			});
			
			
			
			
		})
	
	</script>
	
</body>
</html>