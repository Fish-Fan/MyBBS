package com.fanyank.web.user;

import com.fanyank.entity.User;
import com.fanyank.service.UserService;
import com.fanyank.web.BaseServlet;
import com.google.common.collect.Maps;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang3.*;


/**
 * Created by yanfeng-mac on 2017/3/28.
 */
@WebServlet("/login.do")
public class LoginServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(req,resp,"user/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Map<String,Object> result = Maps.newHashMap();

        //服务器端二次验证
        if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            UserService userService = new UserService();
            User user = userService.login(username,password,getRemoteIp(req));
            if(user == null) {
                result.put("state","error");
                result.put("message","账号或密码错误");
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("curr_user",user);
            }
        } else {
            result.put("state","error");
            result.put("message","参数错误");
        }

        rendJson(resp,result);
    }
}
