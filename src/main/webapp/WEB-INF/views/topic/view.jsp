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
                <li>${topic.favnum}人收藏</li>
                <li>${topic.likenum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;">
        <div class="talk-item muted" style="font-size: 12px">
            <span id="replyNum"></span>个回复 | 直到 <span id="replyTime"></span>
        </div>
        <div id="comment-list"></div>
    </div>

    <c:choose>
        <c:when test="${not empty sessionScope.curr_user}">
            <div class="box" style="margin:20px 0px;">
                <a name="new"></a>
                <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form id="commentForm" style="padding: 15px;margin-bottom:0px;">
                    <textarea name="" id="editor"></textarea>
                </form>
                <div class="talk-item muted" style="text-align: right;font-size: 12px">
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button class="btn btn-primary" id="sendComment">发布</button>
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
<script src="/static/js/handlebars-v4.0.5.js"></script>
<script src="/static/js/moment.min.js"></script>
<script type="text/mytemplate" id="commentListTemplate">
    {{#each data}}
    <div class="talk-item">

        <table class="talk-table">
            <tr>
                <td width="50">
                    <img class="avatar" src="http://ok2crkjlq.bkt.clouddn.com/{{user.avatar}}?imageView2/1/w/40/h/40" alt="">
                </td>
                <td width="auto">
                    <div class="reply-content">
                        <a href="" class="username">{{user.username}}</a> <span style="font-size: 12px" class="reply timeago" title="{{createtime}}"></span>
                        <br>
                        {{{comment}}}
                        <a name="reply{{counter @index}}"></a>
                    </div>
                    <!-----回复列表开始---->
                    {{#if replyList}}
                    <div class="reply-container">
                        <ul class="reply-ul">



                            <table class="table">
                                {{#each replyList}}
                                <tr id="reply-{{id}}">
                                    <td width="50">
                                        <img class="avatar" src="http://ok2crkjlq.bkt.clouddn.com/{{user.avatar}}?imageView2/1/w/40/h/40" alt="">
                                    </td>
                                    <td width="auto">
                                        <div class="reply-content">
                                            <a href="" class="username">{{user.username}}</a> <span style="font-size: 12px" class="reply timeago" title="{{replytime}}"></span>
                                            <br>
                                            {{{content}}}
                                        </div>
                                    </td>
                                    <td width="50" style="vertical-align: bottom">
                                        <a href="javascript:;" class="reply-icon" name="{{user.username}}" commentid="{{comment_id}}" touserid="{{user_id}}"> <i class="fa fa-reply"></i>回复</a>
                                    </td>
                                </tr>
                                {{/each}}
                            </table>



                        </ul>
                        <a href="javascript:;" class="replyButton btn pull-right" commentid="{{id}}" touserid="{{userid}}">我也来说一句</a>
                    </div>
                    {{/if}}
                    <!-----回复列表结束---->


                    <!-----回复框开始------>
                    <div class="box replyBox hide" style="margin:20px 0px;">
                        <a name="new"></a>
                        <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                        <form class="replyCommentForm" id="replyForm" style="padding: 15px;margin-bottom:0px;">
                            <textarea  id="replyEditor{{id}}" class="editor-reply"></textarea>
                        </form>
                        <div class="talk-item muted" style="text-align: right;font-size: 12px">
                            <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                            <button class="btn btn-primary" id="sendReplyComment">发布</button>
                        </div>
                    </div>
                    <!-----回复框结束------>


                </td>
                <!----当replyList为空时渲染---->
                {{#unless replyList}}
                <td width="70" align="right" style="font-size: 12px">
                    <a href="javascript:;" class="replyLink" data-count="{{counter @index}}" title="回复" commentid="{{id}}" touserid="{{userid}}"><i class="fa fa-reply"></i></a>&nbsp;
                    <span class="badge">{{counter @index}}</span>
                </td>
                {{/unless}}

            </tr>
        </table>



    </div>
    {{/each}}
</script>
<script>
    $(function () {
        <c:if test="${not empty sessionScope.curr_user}">
        //simditor初始化
        var editor = new Simditor({
            textarea: "#editor",
            toolbar: false
        });

        var replyEditor = null;


        function sendComment() {
            var $btn = $("#sendComment");
            $.ajax({
                url: "/topic/comment/new.do",
                type: "post",
                data: {"comment":editor.getValue(),"topicId":${topic.id}},
                beforeSend: function () {
                    $btn.text("发布中...").attr("disabled","disabled");
                },
                success: function (json) {
                    if(json.state == "error") {
                        alert(json.message);
                    } else {
                        editor.setValue("");
                        initComment();
                    }
                },
                error: function () {
                    alert("服务器忙，请稍后再试")
                },
                complete: function () {
                    $btn.text("发布").removeAttr("disabled");
                }
            });

        }

        </c:if>


        function initComment() {
            $.ajax({
                url: "/topic/comment/load.do",
                type: "post",
                data: {"topicId": "${topic.id}"},
                success: function (json) {
                    if(json.state == "error") {
                        alert(json.message);
                    } else {
                        //拼接回复列表
                        $("#comment-list").html("");
                        var source = $("#commentListTemplate").html();
                        var template = Handlebars.compile(source);
                        var html = template(json);
                        $("#comment-list").append(html);

                        //统计总回复数量
                        $("#replyNum").text(json.data.length);
                        //更新最后回复时间
                        if(json.data.length == 0) {
                            $("#replyTime").text(moment().format("YYYY:MM:DD HH:mm:ss"));
                        } else {
                            $("#replyTime").text(json.data[json.data.length-1].createtime);
                        }

                        $(".timeago").timeago();

                    }
                },
                error: function () {
                    alert("服务器错误");
                },
                complete: function () {

                }
            });
        }

        $("#sendComment").click(function () {
            var value = editor.getValue();
            if(value) {
                sendComment();
            } else {
                editor.focus();
            }
        });


        $(".timeago").timeago();

        $("pre").addClass("prettyprint");
        prettyPrint();

        initComment();

        Handlebars.registerHelper("counter", function (index){
            return index + 1;
        });


        //回复功能
        $(document).on("click",".replyLink",function (e) {

            var that = e.target;
            var commentid = $(this).attr("commentid");
            var touserid = $(this).attr("touserid");

            replyRender(that,commentid,touserid);

        });


        $(document).on("click","#sendReplyComment",function () {

            var $replyBox = $(this).parents(".replyBox");
            var $replyForm = $replyBox.find("#replyForm");

            var object = {
                topicId: ${topic.id},
                content: replyEditor.getValue(),
                commentId: $replyForm.attr("commentid"),
                toUserId: $replyForm.attr("touserid")
            };

            console.log(object);
            if(object.content) {
                sendReply(object);
            } else {
                replyEditor.focus();
            }
        });

        function sendReply(object) {

            $.ajax({
                url: "/topic/comment/reply.do",
                type: "post",
                data: {"topicId":object.topicId,"content":object.content,"commentId":object.commentId,"toUserId":object.toUserId},
                success: function (json) {
                    if(json.state == "error") {
                        alert(json.message);
                    } else {
                        //拼接回复列表
                        $("#comment-list").html("");
                        var source = $("#commentListTemplate").html();
                        var template = Handlebars.compile(source);
                        var html = template(json);
                        $("#comment-list").append(html);

                        //统计总回复数量
                        $("#replyNum").text(json.data.length);
                        //更新最后回复时间
                        if(json.data.length == 0) {
                            $("#replyTime").text(moment().format("YYYY:MM:DD HH:mm:ss"));
                        } else {
                            $("#replyTime").text(json.data[json.data.length-1].createtime);
                        }

                        $(".timeago").timeago();

                        //初始化回复框
                        replyEditor = new Simditor({
                            textarea: $(".editor-reply"),
                            toolbar: false
                        });
                    }
                },
                error: function () {
                    alert("服务器连接异常")
                },
                complete: function () {

                }
            });
        }

        //回复评论功能
        $(document).on("click",".replyButton",function (e) {

            var that = e.target;
            var commentid = $(this).attr("commentid");
            var touserid = $(this).attr("touserid");

            replyRender(that,commentid,touserid);

        });

        //@功能
        $(document).on("click",".reply-icon",function (e) {

            var that = e.target;
            var commentId = $(this).attr("commentid");
            var touserid = $(this).attr("touserid");

            replyRender(that,commentId,touserid);

            var name = $(this).prop("name");

            var toName = "<a href='#" + name + "'>" + "@" + name + "</a>&nbsp;&nbsp;";
            replyEditor.setValue(toName);


        });








        //回复评论框render函数
        function replyRender(target,commentid,touserid) {

            var $talkItem = $(target).parents(".talk-item");
            var $replyBox = $talkItem.find(".replyBox");
            var $replyButton = $talkItem.find(".replyButton");
            var $replyEditor = $talkItem.find(".editor-reply");

            if($replyBox.hasClass("hide")) {
                //为所有replyBox加上hide
                $(".replyBox").each(function (index, item) {
                    if(!$(item).hasClass("hide")) {
                        $(item).addClass("hide");
                    }
                });

                $replyBox.removeClass("hide");
                $replyButton.text("收起回复框");

                //为评论框设置attr(commentId)和about属性(toUserId)
                var $replyForm = $replyBox.find("#replyForm");
                $replyForm.attr("commentid",commentid);
                $replyForm.attr("touserid",touserid);

                //初始化simditor
                replyEditor = new Simditor({
                    textarea: $replyEditor,
                    toolbar: false
                });

            } else {
                $replyButton.text("我也来说一句");
                $replyBox.addClass("hide");

                replyEditor.destory();
            }
        }


    });
</script>




</body>
</html>