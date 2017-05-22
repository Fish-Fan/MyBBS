/**
 * Created by yanfeng-mac on 2017/5/18.
 */
Vue.http.options.emulateJSON = true;

var loginBox = new Vue({
    el: "#loginBox",
    data: {
        username: '',
        password: '',
        loginBtnMsg: '登录',
        disabled: false
    },
    methods: {
        onLogin: function (bool, event) {
            if(!bool) {
                event.preventDefault();
            } else {
                this.loginBtnMsg = '登录中...';
                this.disabled = true;
                this.$http.post("/login.do",{username: this.username,password: this.password}).then((response) => {
                    "use strict";
                    if(response.data.state != "error") {
                        window.location.href = "/index.do";
                    } else {
                        alert(response.data.message);
                        this.disabled = false;
                        this.loginBtnMsg = '登录';
                    }
                },(response) => {
                    "use strict";
                    alert("服务器连接异常");
                })
            }

        }
    }
});