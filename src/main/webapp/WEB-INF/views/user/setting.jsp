<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户设置</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/webuploader/webuploader.css">
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container" id="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>

        <form action="" class="form-horizontal" validate>
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text"
                           value="${sessionScope.curr_user.username}"
                           v-model="username"
                           readonly>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="email"
                           name="email"
                           v-model="email"
                           value="${sessionScope.curr_user.email}"
                           required
                           @blur="checkEmail"
                    >
                    <p class="text-danger" v-if="emailError">该邮件地址已经被注册，请重新填写该项</p>
                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary"
                        type="button"
                        @click="emailSubmit"
                        id="btnSaveEmail">保存</button>
                <span id="emailHelp"
                      v-if="emailSubmitState"
                      class="text-success">电子邮件修改成功</span>
            </div>

        </form>

    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form action="" class="form-horizontal" id="passwordForm">
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password"
                           name="password"
                           v-model="password"
                           id="password"
                           required
                           minlength="6"
                           maxlength="16"
                    >
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password"
                           v-model="repassword"
                           name="repassword"
                           required
                           minlength="6"
                           maxlength="16"
                           @blur="checkPassword"
                    >
                    <p class="text-error" v-if="passwordError">两次输入密码需保持一致</p>

                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary"
                        id="btnSavePassword"
                        @click="passwordSubmit"
                        type="button">保存</button>
                <span id="passwordHelp"
                      v-if="passwordSubmitState"
                      class="text-success">密码修改成功,请重新登录</span>
            </div>

        </form>

    </div>
    <!--box end-->

    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img src="http://ok2crkjlq.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView2/1/w/40/h/40" class="img-circle avatar2" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>
            <div class="form-actions">
                <div id="picker">上传新头像</div>
            </div>


        </form>

    </div>
    <!--box end-->

</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/webuploader/webuploader.min.js"></script>
<script src="http://cdn.bootcss.com/vue/1.0.25-csp/vue.js"></script>
<script src="/static/js/vue-resource.js"></script>
<%--<script src="http://cdn.bootcss.com/vue-validator/2.1.3/vue-validator.js"></script>--%>
<script>
    //设置post请求为form data形式
    Vue.http.options.emulateJSON = true;

    var container = new Vue({
        el: "#container",
        data: {
            username: '',
            password: '',
            email: '',
            repassword: '',
            passwordError: false,
            emailError: false,
            emailSubmitState: false,
            passwordSubmitState: false
        },
        methods: {
            checkEmail: function () {
                var vm = this;
                if(vm.email.trim()) {
                    vm.$http.get("/validate/email?email=" + vm.email).then((response) =>  {
                        if(response.data == "false") {
                            vm.emailError = true;
                        } else {
                            vm.emailError = false;
                        }
                    });
                }
            },
            checkPassword: function () {
                var vm = this;
                if(vm.password != vm.repassword) {
                    vm.passwordError = true;
                } else {
                    vm.passwordError = false;
                }
            },
            emailSubmit: function () {
                var vm = this;
                var email = vm.email;
                if(email.trim()) {
                    vm.$http.post("/user/changeemail.do",{email: email}).then((response) => {
                        var result = response.data.state;
                        if(result == "success") {
                            this.emailSubmitState = true;
                        }
                    });
                }

            },
            passwordSubmit: function () {
                var vm = this;
                var password = vm.password;
                if(password.trim()) {
                    vm.$http.post("/user/changepassword.do",{password: password}).then((response) => {
                        var result = response.data.state;
                        if(result == "success") {
                            vm.passwordSubmitState = true;
                        }
                    });
                }
            }

        }
    });
</script>
<script>
    $(function(){

        var uploader = WebUploader.create({

            // swf文件路径
            swf: '/js/Uploader.swf',
            // 文件接收服务端。
            server: 'http://up-z1.qiniu.com/',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',
            formData:{'token':'${token}'},
            fileVal:'file',
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            auto:true,
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
            //限制文件大小为100k以内
            fileSingleSizeLimit:100*1024
        });

        uploader.on("uploadProgress",function(file){
          $(".webuploader-pick").text("头像上传中...").attr("disabled","disabled");
        });

        //文件上传失败时调用
        uploader.on("uploadError",function(file){
            alert("上传服务器错误");
        });

        //无论上传成功还是失败都调用
        uploader.on("uploadComplete",function(){
           $(".webuploader-pick").text("上传新头像").removeAttr("disabled");
        });


        uploader.on("uploadSuccess",function(file,result){
            var key = result.key;
            $.post("/user/changeavatar.do",{"key":key}).done(function(json){
                if(json.state == "success") {
                    $(".avatar2").attr("src","http://ok2crkjlq.bkt.clouddn.com/"+key+"?imageView2/1/w/40/h/40");

                    uploader.removeFile(file,true);
                }
            }).fail(function(){
                alert("服务器忙，请稍后再试");
            });

        });


    });
</script>



</body>
</html>