<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${topic.title}</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/js/code/hemisu-light.css">
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
        }
    </style>
</head>
<body>
<%@ include file="../include/nav.jsp" %>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/index.do">首页</a> <span class="divider">/</span></li>
            <li class="active">${topic.node.nodename}</li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="http://ok2crkjlq.bkt.clouddn.com/${topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${topic.title}</h3>
            <p class="topic-msg muted"><a href="">${topic.user.username}</a> · <span class="timeago" title="${topic.createtime}"></span> </p>
        </div>
        <div class="topic-body">
            ${topic.text}
        </div>
        <div class="topic-toolbar">
            <c:if test="${not empty sessionScope.curr_user}">
                <ul class="unstyled inline pull-left">
                    <c:choose>
                        <c:when test="${action == 'fav'}">
                            <li><a href="javascript:;" class="fav">取消收藏</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:;" class="fav">加入收藏</a></li>
                        </c:otherwise>
                    </c:choose>

                    <li><a href="">感谢</a></li>
                </ul>
            </c:if>
            <ul class="unstyled inline pull-right muted">
                <li>${topic.viewnum}次点击</li>
                <li><span id="favNum">${topic.favnum}</span>人收藏</li>
                <li>${topic.likenum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;" id="commentContainer">
        <div class="talk-item muted" style="font-size: 12px">
            <span id="replyNum">{{commentNum}}</span>个回复 | 直到 <span id="replyTime">{{lastReplyTime}}</span>
        </div>
        <div id="comment-list">
            <div class="talk-item">

                <table class="talk-table">
                    <tr v-for="item in commentList">
                        <td width="50">
                            <img class="avatar" :src="computedAvatar(item.user.avatar)" alt="">
                        </td>
                        <td width="auto">
                            <div class="reply-content">
                                <a href="" class="username">{{item.user.username}}</a> <span style="font-size: 12px" class="reply timeago" title="{{item.createtime}}">{{item.createtime}}</span>
                                <br>
                                {{{item.comment}}}
                                <a name="reply{{$index}}"></a>
                            </div>
                            <!-----回复列表开始---->

                            <div class="reply-container" v-if="item.replyList">
                                <ul class="reply-ul">



                                    <table class="table">

                                        <tr id="reply-{{reply.id}}" v-for="reply in item.replyList">
                                            <td width="50">
                                                <img class="avatar" :src="computedAvatar(reply.user.avatar)" alt="">
                                            </td>
                                            <td width="auto">
                                                <div class="reply-content">
                                                    <a href="" class="username">{{reply.user.username}}</a> <span style="font-size: 12px" class="reply timeago" title="{{reply.replytime}}">{{reply.replytime}}</span>
                                                    <br>
                                                    {{{reply.content}}}
                                                </div>
                                            </td>
                                            <td width="50" style="vertical-align: bottom">
                                                <c:if test="${sessionScope.curr_user != null}" >
                                                    <a href="javascript:;" @click="initReplyBody(reply)"><i class="fa fa-reply"></i></a>
                                                </c:if>
                                            </td>
                                        </tr>

                                    </table>



                                </ul>

                            </div>

                            <!-----回复列表结束---->


                            <!-----回复框开始------>
                            <c:if test="${sessionScope.curr_user != null}">
                                <a href="javascript:;" @click="InitReplyBox(item)" class="replyButton btn pull-right" commentid="{{item.id}}" touserid="{{item.userid}}">{{item.replyBtnMsg}}</a>
                                <div class="box replyBox" v-show="item.isShow" style="margin:20px 0px;" :id="computedReplyId(item)">
                                    <a name="new"></a>
                                    <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                                    <form class="replyCommentForm" style="padding: 15px;margin-bottom:0px;">
                                        <textarea v-model="replyValue" class="editor-reply" :id="computedReplyInputId(item)"></textarea>
                                    </form>
                                    <div class="talk-item muted" style="text-align: right;font-size: 12px">
                                        <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                                        <button class="btn btn-primary" @click="sendReply(item)" id="sendReplyComment">发布</button>
                                    </div>
                                </div>
                            </c:if>

                            <!-----回复框结束------>


                        </td>
                        <!----当replyList为空时渲染---->

                        <td width="70" align="right" style="font-size: 12px" v-if="!item.replyList">
                            <c:if test="${sessionScope.curr_user != null}">
                                <a href="javascript:;" class="replyLink"  data-count="{{$index}}" title="回复" commentid="{{item.id}}" touserid="{{item.userid}}"><i class="fa fa-reply"></i></a>&nbsp;
                            </c:if>
                            <span class="badge">1</span>
                        </td>


                    </tr>
                </table>



            </div>

        </div>
    </div>

    <c:choose>
        <c:when test="${not empty sessionScope.curr_user}">
            <div class="box" style="margin:20px 0px;" id="commentForm">
                <a name="new"></a>
                <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form style="padding: 15px;margin-bottom:0px;">
                    <textarea name="" id="editor"></textarea>
                </form>
                <div class="talk-item muted" style="text-align: right;font-size: 12px">
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button class="btn btn-primary" id="sendComment" @click="sendComment">发布</button>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="box" style="margin:20px 0px;">
                <div style="padding: 20px">
                    请 <a href="/login.do?redirecturl=/topic/view.do?id=${topic.id}">登录</a> 后在发表回复
                </div>
            </div>
        </c:otherwise>
    </c:choose>


</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/timeago.js"></script>
<script src="/static/js/code/prettify.js"></script>
<script src="/static/js/moment.min.js"></script>
<script src="http://cdn.bootcss.com/vue/1.0.25-csp/vue.js"></script>
<script src="/static/js/vue-resource.js"></script>


<%--<template id="replyFormTemplate">--%>
    <%----%>
<%--</template>--%>

<script>
    Vue.http.options.emulateJSON = true;




    var commentContainer = new Vue({
        el: "#commentContainer",
        data: {
            commentList: [],
            commentUserAvatar: '',
            requestUrl: '/topic/comment/load.do',
            topicId: {topicId:${topic.id}},
            commentNum: '',
            lastReplyTime: '',
            replyBtnMsg: '我也来说一句',
            replyBody: {
                topicId: ${topic.id},
                content: '',
                commentId: '',
                toUserId: ''
            },
            replySimditor: ''
        },
        ready: function () {
            this.getJsonObject();
        },
        methods: {
            computedAvatar: function (avatar) {
                return "http://ok2crkjlq.bkt.clouddn.com/"+ avatar +"?imageView2/1/w/40/h/40";
            },
            getJsonObject: function () {
                var vm = this;
                vm.$http.post(this.requestUrl,this.topicId).then((response) => {
                    vm.$set('commentList',response.data.data);
                    var commentList = response.data.data;
                    vm.commentNum = vm.commentList.length;
                    vm.lastReplyTime = vm.commentList[parseInt(vm.commentNum)-1].createtime;
                    for(var i = 0;i < vm.commentList.length;i++) {
                        Vue.set(commentList[i],'index',i);
                        Vue.set(commentList[i],'isShow',false);
                        Vue.set(commentList[i],'replyBtnMsg','我也来说一句');
//                        vm.commentList[i].index = i;
//                        vm.commentList[i].isShow = false;
//                        vm.commentList[i].replyBtnMsg = '我也来说一句';
//                        this.$set('commentList[i].index',i);
//                        this.$set('commentList[i].isShow',false);
//                        this.$set('commentList[i].replyBtnMsg','我也来说一句');
                    }
                })
            },
            InitReplyBox: function (item) {
                if(!item.isShow) {
                    var replyEditor = new Simditor({
                        textarea: '#replyInput' + item.id,
                        toolbar: false
                    });
                    var index = item.index;
                    item.isShow = true;
                    item.replyBtnMsg = '收起评论框';
                    this.replySimditor = replyEditor;
                } else {
//                    replyEditor.destroy();
                    item.isShow = false;
                    item.replyBtnMsg = '我也来说一句';
                }

            },
            computedReplyId: function (item) {
                return "replyBox" + item.id;
            },
            computedReplyInputId: function (item) {
                return "replyInput" + item.id;
            },
            sendReply: function (item) {
                console.log("111");
                var vm = this;
                vm.replyBody.content = vm.replySimditor.getValue();

                if(vm.replyBody.content.trim()) {
                    if(vm.replyBody.commentId == "" || vm.replyBody.toUserId == "") {
                        vm.replyBody.commentId = item.id;
                        vm.replyBody.toUserId = item.userid;
                        vm.$http.post('/topic/comment/reply.do',vm.replyBody).then((response) => {
                            commentContainer.getJsonObject();
                        })
                    }
                }

            },
            initReplyBody: function (reply) {
                this.replyBody.commentId = reply.comment_id;
                this.replyBody.toUserId = reply.to_user_id;
                this.InitReplyBox(reply);
            }
        }
     

    });

    var editor = new Simditor({
        textarea: "#editor",
        toolbar: false
    });

    var commentForm = new Vue({
        el: "#commentForm",
        data: {
            commentValue: editor.getValue(),
            requestUrl: '/topic/comment/new.do'
        },
        methods: {
            sendComment: function () {
                var vm = this;
                var value = editor.getValue();
                vm.$http.post(vm.requestUrl,{comment:value,topicId:${topic.id}}).then((response) => {
                    vm.commentValue = '';
                    editor.setValue('');
                    commentContainer.getJsonObject();
                })
            }
        }
    });
</script>



</body>
</html>