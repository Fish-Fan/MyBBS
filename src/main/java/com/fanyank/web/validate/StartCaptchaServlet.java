package com.fanyank.web.validate;

import com.fanyank.util.ConfigProp;
import com.fanyank.util.GeetestLib;
import com.fanyank.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yanfeng-mac on 2017/4/13.
 */
public class StartCaptchaServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GeetestLib gtSdk = new GeetestLib(ConfigProp.get("geetest.login.id"),ConfigProp.get("geetest.login.key"),true);

        String resStr = "{}";

        //自定义userid
        String userid = "test";
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(userid);

        //将服务器状态设置到session中
        req.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        req.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();
        PrintWriter out = resp.getWriter();
        out.println(resStr);
    }
}
