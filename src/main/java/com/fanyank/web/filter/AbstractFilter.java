package com.fanyank.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public abstract class AbstractFilter implements Filter{

    public void init(FilterConfig filterConfig) throws ServletException {}
    public void destroy() {}
}
