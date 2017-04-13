package com.fanyank.web.user;

import com.fanyank.entity.User;
import com.fanyank.util.QiniuUtil;
import com.fanyank.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/4/11.
 */
@WebServlet("/user/setting.do")
public class SettingServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) req.getAttribute("curr_user");

        req.setAttribute("token", QiniuUtil.getDefaultToken());

        forward(req,resp,"user/setting");
    }
}
