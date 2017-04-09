package com.fanyank.web;

import com.fanyank.entity.User;
import com.fanyank.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
@WebServlet("/index.do")
public class IndexServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("node");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        TopicService topicService = new TopicService();
        req.setAttribute("topicList",topicService.findTopicByNode(nodeId));
        req.setAttribute("nodeList",topicService.getAllNode());

        if(user != null) {
            getUnReadMsgCount(user,req);
        }


        forward(req,resp,"index");
    }
}
