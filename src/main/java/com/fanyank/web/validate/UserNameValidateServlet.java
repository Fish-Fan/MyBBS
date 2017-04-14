package com.fanyank.web.validate;

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
@WebServlet("/validate/username")
public class UserNameValidateServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String action = req.getParameter("action");

        ValidateService validateService = new ValidateService();
        String result = validateService.usernameNotExist(username);


        if(action.equals("forget") && action != null) {
            if(result.equals("true")) {
                rendText(resp,"false");
            } else {
                rendText(resp,"true");
            }
        } else {
            rendText(resp,result);
        }
    }
}
