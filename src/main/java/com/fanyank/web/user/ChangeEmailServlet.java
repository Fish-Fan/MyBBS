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
@WebServlet("/user/changeemail.do")
public class ChangeEmailServlet extends BaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       if(isAjaxRequest(req)) {
           String new_email = req.getParameter("email");

           HttpSession session = req.getSession();
           User user = (User) session.getAttribute("curr_user");

           Map<String,String> result = Maps.newHashMap();
           UserService userService = new UserService();

           userService.changeEmail(user,new_email);

           result.put("state","success");
           rendJson(resp,result);
       }

    }
}
