package com.fanyank.web;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public class BaseServlet extends HttpServlet {
    public void forward(HttpServletRequest request, HttpServletResponse response, String viewname) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + viewname + ".jsp").forward(request,response);
    }
}
