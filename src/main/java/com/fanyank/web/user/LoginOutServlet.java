package com.fanyank.web.user;

import com.fanyank.entity.User;
import com.fanyank.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/3/31.
 */
@WebServlet("/loginOut.do")
public class LoginOutServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("curr_user");

        if(user == null) {
            resp.sendRedirect("login.do?state=1001");
        } else {
            session.invalidate();
            resp.sendRedirect("login.do?state=1002");
        }
    }
}
