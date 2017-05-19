<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 登录</span>
        </div>
        <c:choose>
            <c:when test="${param.state == '1001'}">
                <div class="alert alert-error">请登录后再继续操作</div>
            </c:when>
            <c:when test="${param.state == '1002'}">
                <div class="alert alert-success">你已经安全退出</div>
            </c:when>
            <c:when test="${param.state == '1003'}">
                <div class="alert alert-success">密码设置成功，请重新登录</div>
            </c:when>
        </c:choose>

        <div id="loginBox">
            <validator name="validation">
                <form action="" class="form-horizontal" id="loginForm">

                    <div class="control-group">
                        <label class="control-label">账号</label>
                        <div class="controls">
                            <input type="text"
                                   name="username"
                                   v-validate:username="{required: true}"
                                   v-model="username"
                            >
                            <p class="text-danger" v-if="$validation.username.required">在此输入您的用户名</p>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">密码</label>
                        <div class="controls">
                            <input type="password"
                                   name="password"
                                   v-validate:password="{required: true}"
                                   v-model="password"
                            >
                            <p class="text-danger" v-if="$validation.password.required">在此输入您的密码</p>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                            <a href="/forget/password.do">忘记密码</a>
                        </div>
                    </div>

                    <div id="catch-box">

                    </div>

                    <div class="form-actions">
                        <button class="btn btn-primary"
                                type="button"
                                id="loginBtn"
                                @click="onLogin($validation.valid,$event)"
                                :disabled="disabled"
                        >{{loginBtnMsg}}</button>
                        <a class="pull-right" href="/signUp.do">注册账号</a>
                    </div>

                </form>
            </validator>
        </div>



    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="//cdn.bootcss.com/vue/1.0.24/vue.js" type="text/javascript" charset="utf-8"></script>
<script src="http://cdn.bootcss.com/vue-validator/2.1.3/vue-validator.js"></script>
<script src="/static/js/vue-resource.js"></script>
<script src="/static/js/login.js">

</script>

<%--<script src="/static/js/jquery-1.11.3.min.js"></script>--%>
<%--<script src="/static/js/jquery.validate.min.js"></script>--%>
<%--<script src="http://static.geetest.com/static/tools/gt.js"></script>--%>
<%--<script>--%>
    <%--$(function () {--%>

        <%--$("#loginForm").validate({--%>
            <%--errorClass: "text-error",--%>
            <%--errorElement: "span",--%>
            <%--rules: {--%>
                <%--username: {--%>
                    <%--required: true--%>
                <%--},--%>
                <%--password: {--%>
                    <%--required: true--%>
                <%--}--%>
            <%--},--%>
            <%--messages: {--%>
                <%--username: {--%>
                    <%--required: "请输入账号"--%>
                <%--},--%>
                <%--password: {--%>
                    <%--required: "请输入密码"--%>
                <%--}--%>
            <%--},--%>
            <%--submitHandler: function (form) {--%>
                <%--var $btn = $("#loginBtn");--%>
                <%--$.ajax({--%>
                    <%--url: "/login.do",--%>
                    <%--type: "post",--%>
                    <%--data: $(form).serialize(),--%>
                    <%--beforeSend: function () {--%>
                        <%--$btn.text("登录中...").attr("disabled","disabled")--%>
                    <%--},--%>
                    <%--success: function (json) {--%>
                        <%--if(json.state == "error") {--%>
                            <%--alert(json.message);--%>
                        <%--} else {--%>
                            <%--&lt;%&ndash;window.location.href = "${not empty param.redirecturl ? param.redirecturl : '/index.do'}";&ndash;%&gt;--%>
                            <%--window.location.href = "/index.do";--%>
                        <%--}--%>
                    <%--},--%>
                    <%--error: function () {--%>
                        <%--alert("服务器异常");--%>
                    <%--},--%>
                    <%--complete: function () {--%>
                        <%--$btn.text("登录").removeAttr("disabled");--%>
                    <%--}--%>
                <%--});--%>

            <%--}--%>

        <%--});--%>

        <%--$("#loginBtn").click(function () {--%>
            <%--$("#loginForm").submit();--%>
        <%--});--%>


    <%--});--%>

<%--</script>--%>

</body>
</html>