package com.fanyank.web.user;

import com.fanyank.service.UserService;
import com.fanyank.web.BaseServlet;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by yanfeng-mac on 2017/4/12.
 */
@WebServlet("/forgetpassword/setpassword.do")
public class ForgetPasswordSetNewPasswordServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String token = req.getParameter("token");

        Map<String,String> result = Maps.newHashMap();
        UserService userService = new UserService();

        if(StringUtils.isEmpty(password)) {
            result.put("state","error");
        } else {
            userService.forgetPasswordSetNew(password,token);
            result.put("state","success");
        }
    }
}
