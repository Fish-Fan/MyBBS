package com.fanyank.web.topic;

import com.fanyank.entity.User;
import com.fanyank.service.TopicService;
import com.fanyank.service.UserService;
import com.fanyank.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
@WebServlet("/topic/view.do")
public class TopicServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("id");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        TopicService topicService = new TopicService();


        req.setAttribute("topic",topicService.findById(topicId));
        req.setAttribute("commentList",topicService.findCommentByTopicId(topicId));

        if(user != null) {
            getUnReadMsgCount(user,req);
        }

        forward(req,resp,"topic/view2");


    }
}
