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
@WebServlet("/user/changepassword.do")
public class ChangePasswordServlet extends BaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String new_password = req.getParameter("password");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        Map<String,String> result = Maps.newHashMap();
        UserService userService = new UserService();

        userService.changePassword(user,new_password);

        result.put("state","success");
        rendJson(resp,result);
    }
}
