package com.fanyank.web.user;

import com.fanyank.service.UserService;
import com.fanyank.web.BaseServlet;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
@WebServlet("/signUp.do")
public class SignUpServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(req,resp,"user/reg");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");


        Map<String,String> result = Maps.newHashMap();

        if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(email)) {
            UserService userService = new UserService();
            userService.saveUser(username,password,email);

            result.put("state","success");
        } else {
            result.put("state","error");
            result.put("message","参数错误");
        }

        rendJson(resp,result);

    }
}
