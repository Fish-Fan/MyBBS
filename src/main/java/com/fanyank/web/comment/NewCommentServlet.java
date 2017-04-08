package com.fanyank.web.comment;

import com.fanyank.entity.Comment;
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
import java.util.Map;

/**
 * Created by yanfeng-mac on 2017/3/30.
 */
@WebServlet("/topic/comment/new.do")
public class NewCommentServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAjaxRequest(req)) {
            String value = req.getParameter("comment");
            String topicId = req.getParameter("topicId");

            Map<String,Object> map = Maps.newHashMap();

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("curr_user");

            if (user == null) {
                map.put("state","error");
                map.put("message","请您登陆后在评论");
                rendJson(resp,map);
            } else {
                TopicService topicService = new TopicService();
                topicService.saveNewComment(value,topicId,user);
                map.put("state","success");
                rendJson(resp,map);
            }
        }
    }
}
