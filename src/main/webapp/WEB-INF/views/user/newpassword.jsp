<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>设置新密码</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container" id="passwordForm">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 设置新密码</span>
        </div>

        <validator name="validation">
            <form action="" class="form-horizontal">
                <input
                        type="hidden"
                        name="token"
                        value="${token}"
                        v-model="dataBody.token"
                >
                <div class="control-group">
                    <label class="control-label">新密码</label>
                    <div class="controls">
                        <input
                                type="password"
                                name="password"
                                id="password"
                                v-validate:password="{required: true,minlength: 6,maxlength: 18}"
                                v-model="dataBody.password"
                        >
                        <span class="text-danger" v-if="$validation.password.required">请输入您的新密码</span>
                        <span class="text-danger" v-if="$validation.password.minlength">长度不得少于6个字符</span>
                        <span class="text-danger" v-if="$validation.password.maxlength">长度不得超过18个字符</span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">重复密码</label>
                    <div class="controls">
                        <input
                                type="password"
                                name="repassword"
                                @blur="validateNewPassword"
                                v-model="dataBody.repassword"
                        >
                        <span class="text-danger" v-if="checked">两次输入的密码不一致，请重新输入</span>
                    </div>
                </div>
                <div class="form-actions">
                    <button
                            class="btn btn-primary"
                            type="button"
                            id="setBtn"
                            @click="onSubmit($validation,valid,$event)"
                            :disabled="disabled"
                    >设置</button>
                </div>
            </form>
        </validator>



    </div>
    <!--box end-->
</div>
<!--container end-->

<script src="http://cdn.bootcss.com/vue/1.0.25-csp/vue.js"></script>
<script src="/static/js/vue-resource.js"></script>
<script src="http://cdn.bootcss.com/vue-validator/2.1.3/vue-validator.js"></script>
<script>

    var passwordForm = new Vue({
        el: "#passwordForm",
        data: {
            checked: false,
            disabled: false,
            dataBody: {
                token: '',
                password: '',
                repassword: ''
            }
        },
        methods: {
            validateNewPassword: function () {
                var vm = this;
                if(vm.dataBody.password != vm.dataBody.repassword) {
                    vm.checked = true;
                } else {
                    vm.checked = false;
                }
            },
            onSubmit: function (bool,event) {
                var vm = this;
                if(bool) {
                    vm.disabled = true;
                    if(vm.dataBody.password == vm.dataBody.repassword) {
                        vm.$http.post("/forgetpassword/setpassword.do",vm.dataBody).then((response) => {
                            window.location.href = "/login.do?state=1003";
                        });
                    } else {
                        vm.disabled = false;
                        vm.checked = true;
                    }
                } else {
                    event.preventDefault();
                }
            }
        }
    });
</script>


</body>
</html>