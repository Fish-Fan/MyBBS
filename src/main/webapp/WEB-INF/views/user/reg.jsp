<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册用户</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container" id="regBox">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 注册账号</span>
        </div>

        <validator name="validation">
            <form class="form-horizontal" id="regForm">
                <div class="control-group">
                    <label class="control-label">账号</label>
                    <div class="controls">
                        <input type="text"
                               name="username"
                               v-validate:username="{required: true,minlength: 3,maxlength: 10}"
                               @blur="validateUsername"
                               v-model="dataBody.username"
                        >
                        <p class="text-danger" v-if="$validation.username.required">请输入您注册的用户名</p>
                        <p class="text-danger" v-if="$validation.username.minlength">用户名长度不得少于3个字符</p>
                        <p class="text-danger" v-if="$validation.username.maxlength">用户名不得超过10个字符</p>
                        <p class="text-danger" v-if="checkedUsername">该用户名已经被注册，请重新输入</p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">密码</label>
                    <div class="controls">
                        <input
                                type="password"
                                name="password"
                                id="password"
                                v-validate:password="{required: true,minlength: 6,maxlength: 16}"
                                v-model="dataBody.password"
                        >
                        <p class="text-danger" v-if="$validation.password.required">在此输入您的密码</p>
                        <p class="text-danger" v-if="$validation.password.minlength">密码长度不得少于6字符</p>
                        <p class="text-danger" v-if="$validation.password.maxlength">密码长度不得超多10字符</p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">重复密码</label>
                    <div class="controls">
                        <input type="password"
                               name="repassword"
                               v-model="repassword"
                               @blur="validatePassword"
                        >
                        <p class="text-danger" v-if="checkedPassword">两次输入密码不一致，请重新输入</p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">电子邮件</label>
                    <div class="controls">
                        <input type="text"
                               name="email"
                               v-model="dataBody.email"
                               @blur="validateEmail"
                               v-validate:email="{required: true,email: true}"
                        >
                        <p class="text-dander" v-if="$validation.email.email">请输入正确的邮箱地址</p>
                        <p class="text-danger" v-if="checkedEmail">该邮箱已经被注册，请重新输入</p>
                    </div>
                </div>

                <div class="form-actions">
                    <button
                            type="button"
                            id="regBtn"
                            class="btn btn-primary"
                            @click="onSubmit($validation.valid,$event)"
                    >注册</button>
                    <span id="regMsg" v-if="regResult">注册成功,<span class="sec">{{second}}</span>秒后自动跳转到登录页面</span>
                    <a class="pull-right" href="/login.do">登录</a>
                </div>
            </form>
        </validator>



    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="//cdn.bootcss.com/vue/1.0.24/vue.js" type="text/javascript" charset="utf-8"></script>
<script src="http://cdn.bootcss.com/vue-validator/2.1.3/vue-validator.js"></script>
<script src="/static/js/vue-resource.js"></script>
<script>
    //设置post请求为form data形式
    Vue.http.options.emulateJSON = true;

    Vue.validator('email', function (val) {
        return /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(val)
    })

    var regBox = new Vue({
        el: "#regBox",
        data: {
            dataBody: {
                username: '',
                password: '',
                email: ''
            },
            repassword: '',
            checkedUsername: '',
            checkedEmail: '',
            checkedPassword: '',
            second: 3,
            regResult: false
        },
        methods: {
            validateUsername: function () {
                var vm = this;
                if(vm.dataBody.username.trim()) {
                    vm.$http.get("/validate/username?action=1&username=" + vm.dataBody.username).then((response) => {
                        var bool = response.data;
                        if(bool == "false") {
                            vm.checkedUsername = true;
                        } else {
                            vm.checkedUsername = false;
                        }
                    });
                }
            },
            validateEmail: function () {
                var vm = this;
                if(vm.dataBody.email.trim()) {
                    vm.$http.get("/validate/email?email="+vm.dataBody.email).then((response) => {
                        var bool = response.data;
                        if(bool == "false") {
                            vm.checkedEmail = true;
                        } else {
                            vm.checkedEmail = false;
                        }
                    });
                }
            },
            onSubmit: function (bool,event) {
                var vm = this;
                if(bool) {
                    vm.$http.post("/signUp.do",vm.dataBody).then((response) => {
                        vm.regResult = true;
                        setInterval(function () {
                            if(vm.second != 0) {
                                vm.second--;
                            } else {
                                window.location.href = "/login.do";
                            }
                        },1000);

                    });
                } else {
                    alert("请完善表单内容");
                    event.preventDefault();
                }
            },
            countSecond: function () {
                if(this.second != 0) {
                    this.second--;
                }
            },
            validatePassword: function () {
                var vm = this;
                if(vm.dataBody.password != vm.repassword) {
                    vm.checkedPassword = true;
                } else {
                    vm.checkedPassword = false;
                }
            }
        }
    });
</script>
<script>
//    $(function () {
//        $("#regForm").validate({
//            errorClass: "text-error",
//            errorElement: "span",
//            onkeyup: false,
//            rules: {
//                username: {
//                    required: true,
//                    minlength: 3,
//                    maxlength: 10,
//                    remote: "/validate/username?action=1"
//                },
//
//                password: {
//                    required: true,
//                    rangelength:[5,17]
//                },
//
//                repassword: {
//                    required: true,
//                    rangelength: [5,17],
//                    equalTo: "#password"
//                },
//
//                email: {
//                    required: true,
//                    email: true,
//                    remote: "/validate/email"
//                }
//
//            },
//
//            messages: {
//                username: {
//                    required: "在此输入您的账号",
//                    minlength: "账号长度不能低于3个字符",
//                    maxlength: "账号长度不能大于10个字符",
//                    remote: "该账号已经被注册，请重新填写"
//                },
//
//                password: {
//                    required: "在此输入您的密码",
//                    rangelength: "密码长度范围6-16个字符"
//                },
//
//                repassword: {
//                    required: "在此确认您的密码",
//                    rangelength: "密码长度范围6-16个字符",
//                    equalTo: "和原密码输入不一致，请重新输入"
//                },
//
//                email: {
//                    required: "在此输入您的注册邮箱",
//                    email: "请输入正确的邮箱地址",
//                    remote: "该邮箱已经被注册，请重新填写此项"
//                }
//
//            },
//
//            submitHandler: function (form) {
//                $.post("/signUp.do",$("#regForm").serialize())
//                    .done(function (result) {
//                        if(result.state == "error") {
//                            alert(result.message);
//                        } else {
//                            $("#regMsg").removeClass("hide");
//                            var sec = 3;
//                            setInterval(function () {
//                                sec--;
//                                if(sec == 0) {
//                                    window.location.href = "/login.do";
//                                    return;
//                                }
//                                $("#regMsg .sec").text(sec);
//                            },1000)
//                        }
//                    }).fail(function () {
//                    alert("服务器异常，请稍后再试")
//                });
//            }
//        });
//
//        $("#regBtn").click(function () {
//            $("#regForm").submit();
//        });
//
//
//
//    });
</script>
</body>
</html>