package com.fanyank.web.comment;

import com.fanyank.entity.Comment;
import com.fanyank.entity.Topic;
import com.fanyank.service.TopicService;
import com.fanyank.web.BaseServlet;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yanfeng-mac on 2017/3/30.
 */
@WebServlet("/topic/comment/load.do")
public class LoadCommentServlet extends BaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAjaxRequest(req)) {
            String topicId = req.getParameter("topicId");

            Map<String,Object> result = Maps.newHashMap();
            TopicService topicService = new TopicService();

            //判断topicId是否是数字类型
            if(StringUtils.isNumeric(topicId)) {
                //首先判断topic是否存在
                Topic topic = topicService.findById(topicId);

                if(topic == null) {
                    result.put("state","error");
                    result.put("message","参数错误");
                } else {
                    List<Comment> commentList = topicService.findCommentByTopicId(topicId);
                    result.put("data",commentList);
                    result.put("state","success");

                    rendJson(resp,result);
                }


            } else {
                result.put("state","error");
                result.put("message","参数错误");

            }
        }
    }
}
