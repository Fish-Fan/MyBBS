package com.fanyank.web;

import com.fanyank.entity.User;
import com.fanyank.service.UserService;
import com.google.gson.Gson;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public class BaseServlet extends HttpServlet {
    public void forward(HttpServletRequest request, HttpServletResponse response, String viewname) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + viewname + ".jsp").forward(request,response);
    }

    public String getRemoteIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    public void rendJson(HttpServletResponse response,Object result) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(result));
            out.flush();
            out.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rendText(HttpServletResponse response,String result) {
        response.setContentType("text/plain;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断是否是Ajax请求
     * @param req
     * @return
     */
    public boolean isAjaxRequest(HttpServletRequest req) {
        return "XMLHttpRequest".endsWith(req.getHeader("X-Requested-With"));
    }

    /**
     * 获得当前用户未读消息的数量
     * @param user
     * @param req
     */
    public void getUnReadMsgCount(User user,HttpServletRequest req) {
        UserService userService = new UserService();
        req.setAttribute("msgNum",userService.getUnReadMsgCount(user));

    }
}
