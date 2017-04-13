package com.fanyank.web.user;

import com.fanyank.service.UserService;
import com.fanyank.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/4/12.
 */
@WebServlet("/forget/callback.do")
public class ForgetPasswordCallbackServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        UserService userService = new UserService();

        if(StringUtils.isEmpty(token)) {
           resp.sendError(400);
        } else {
            Integer user_id = userService.validateCallbackToken(token);
            if(user_id != null) {
                req.setAttribute("token",token);
                forward(req,resp,"user/newpassword");
            }
        }
    }
}
