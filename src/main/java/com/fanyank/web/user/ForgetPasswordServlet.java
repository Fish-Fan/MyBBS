package com.fanyank.web.user;

import com.fanyank.service.UserService;
import com.fanyank.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/4/11.
 */
@WebServlet("/forget/password.do")
public class ForgetPasswordServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(req,resp,"user/forget");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        UserService userService = new UserService();
        userService.forgetPassword(username);

        resp.sendRedirect("/forget/password.do?state=1001");
    }
}
