package com.fanyank.web.topic;

import com.fanyank.entity.Topic;
import com.fanyank.entity.User;
import com.fanyank.service.TopicService;
import com.fanyank.web.BaseServlet;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by yanfeng-mac on 2017/4/12.
 */
@WebServlet("/topic/fav.do")
public class FavTopicServlet extends BaseServlet{

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Object> result = Maps.newHashMap();
        TopicService topicService = new TopicService();

        String topicId = req.getParameter("topicId");
        String action = req.getParameter("action");


        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        if(!StringUtils.isNumeric(topicId) || (!"fav".equals(action) && !"unfav".equals(action)) || user == null) {
            result.put("state","error");
            result.put("message","参数错误");
        } else {
            Topic topic = topicService.findById(topicId);
            if(topic == null) {
                result.put("state","error");
                result.put("message","参数错误");
            } else {
                try {

                    topicService.favTopic(topic,user,action);
                    result.put("state","success");
                } catch (Exception ex) {
                    result.put("state","error");
                    result.put("message",ex.getMessage());
                }

            }
        }
        rendJson(resp,result);

    }
}
