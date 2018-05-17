package com.epam.cafe.filter;

import com.epam.cafe.constants.Paths;
import com.epam.cafe.manager.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"})

public class JspSecurityFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String mainPagePath = ConfigurationManager.getProperty(Paths.INDEX_PAGE);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        session.invalidate();

        ServletContext servletContext = servletRequest.getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(mainPagePath);
        dispatcher.forward(request,response);
    }

    @Override
    public void destroy() {

    }
}
