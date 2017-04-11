package com.fanyank.web.user;

import com.fanyank.entity.User;
import com.fanyank.service.UserService;
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
 * Created by yanfeng-mac on 2017/4/11.
 */
@WebServlet("/user/changeavatar.do")
public class ChangeAvatarServlet extends BaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAjaxRequest(req)) {
            String key = req.getParameter("key");

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("curr_user");

            Map<String,String> result = Maps.newHashMap();
            UserService userService = new UserService();

            userService.changeAvatar(user,key);
            result.put("state","success");

            rendJson(resp,result);
        }
    }
}
