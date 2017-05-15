<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${topic.title}</title>

    <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <%--<script src="/static/js/vue.js"></script>--%>
    <script src="http://cdn.bootcss.com/vue/1.0.25-csp/vue.js"></script>

</head>
<body>
<div class="container" id="container">

    <ul>
        <li v-for="item in commentList">
            <p><span style="color: #00a2d4">评论ID:</span> {{item.id}}</p>
            <p><span style="color: #00a2d4">评论内容：</span> {{{item.comment}}}</p>
            <p><span style="color: #00a2d4">评论发布时间：</span>{{item.createtime}}</p>
            <ul>
                <li v-for="reply in item.replyList">
                    <p><span style="color: darkgreen">回复评论的ID：</span> {{reply.comment_id}}</p>
                    <p><span style="color: darkgreen">回复评论的标题：</span> {{reply.topic_title}}</p>
                    <p><span style="color: darkgreen">回复评论的内容：</span> {{{reply.content}}}</p>
                    <p><span style="color: darkgreen">回复的时间：</span> {{reply.replytime}}</p>
                </li>
            </ul>
        </li>
    </ul>

</div>
<script>
    $(function () {
        var jsonObject;


        function initComment() {
            $.ajax({
                url: "/topic/comment/load.do",
                type: "post",
                data: {"topicId": "${topic.id}"},
                success: function (json) {
                    if(json.state == "error") {
                        alert(json.message);
                    } else {
                        console.log(json)
                        jsonObject = json.data;
                        console.log(jsonObject);
                        container.commentList = jsonObject;
                    }
                },
                error: function () {
                    alert("服务器错误");
                },
                complete: function () {

                }
            });

        }
        initComment();

        var container = new Vue({
            el: '#container',
            data: {
                commentList: jsonObject
            }
        });


    });
</script>
<script src="/static/js/main.js"></script>
</body>
</html>