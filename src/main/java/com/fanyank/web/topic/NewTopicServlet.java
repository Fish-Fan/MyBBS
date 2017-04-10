package com.fanyank.web.topic;

import com.fanyank.entity.User;
import com.fanyank.service.TopicService;
import com.fanyank.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.UnknownServiceException;

/**
 * Created by yanfeng-mac on 2017/4/10.
 */
@WebServlet("/topic/new.do")
public class NewTopicServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        TopicService topicService = new TopicService();

        if(user != null) {
            req.setAttribute("nodeList",topicService.getAllNode());
            forward(req,resp,"topic/new");
        } else {
            resp.sendError(404,"请登录后再使用");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String nodeId = req.getParameter("nodeid");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        if(user == null && StringUtils.isEmpty(title.trim()) && StringUtils.isEmpty(nodeId)) {
            resp.sendError(400,"参数错误");
        } else {
            TopicService topicService = new TopicService();
            int id = topicService.saveNewTopic(title,text,nodeId,user);

            resp.sendRedirect("/topic/view.do?id=" + id);
        }
    }
}
