package com.fanyank.web.user;

import com.fanyank.entity.User;
import com.fanyank.service.UserService;
import com.fanyank.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/5/19.
 */
@WebServlet("/user/getunreadmsg")
public class UnReadMsgServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        if(user != null) {
            rendJson(resp,userService.getUnReadMsg(user));
        }
    }
}
