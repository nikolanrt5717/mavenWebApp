package com.msg.laza.project.filter;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HttpRequestLoggingFilter implements Filter {
    private static Logger logger;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger = LoggerFactory.getLogger(HttpRequestLoggingFilter.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        filterChain.doFilter(servletRequest,servletResponse);
        logger.info("Method: {}, URL: {}, CODE {}", req.getMethod(),req.getRequestURI(),resp.getStatus());
    }

    @Override
    public void destroy() {

    }
}
