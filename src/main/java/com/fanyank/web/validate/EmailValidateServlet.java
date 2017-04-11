package com.fanyank.web.validate;

import com.fanyank.entity.User;
import com.fanyank.service.UserService;
import com.fanyank.service.ValidateService;
import com.fanyank.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
@WebServlet("/validate/email")
public class EmailValidateServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAjaxRequest(req)) {
            String email = req.getParameter("email");

            ValidateService validateService = new ValidateService();
            rendText(resp,validateService.emailNotExist(email));
        }
    }
}
