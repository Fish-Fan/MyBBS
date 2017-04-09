package com.fanyank.web.comment;

import com.fanyank.entity.User;
import com.fanyank.service.TopicService;
import com.fanyank.web.BaseServlet;
import com.google.common.collect.Maps;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanfeng-mac on 2017/4/6.
 */
@WebServlet("/topic/comment/reply.do")
public class NewReplyServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        String content = req.getParameter("content");
        String commentId = req.getParameter("commentId");
        String toUserId = req.getParameter("toUserId");


        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        Map<String,Object> result = Maps.newHashMap();

        if(user != null) {
            TopicService topicService = new TopicService();
            topicService.saveNewReply(content,commentId,user.getId(),toUserId,topicId);
            result.put("state","success");
            result.put("data",topicService.findCommentByTopicId(topicId));


        } else {
            result.put("state","error");
            result.put("message","参数错误");
        }

        rendJson(resp,result);
    }
}
