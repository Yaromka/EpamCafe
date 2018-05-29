package com.epam.cafe.filter;

import com.epam.cafe.command.CommandType;
import com.epam.cafe.command.page.PageType;
import com.epam.cafe.constants.LevelAccess;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.manager.ConfigurationManager;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/controller"})
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Checks access level for Admin, user and guest and rerouting response
     * to main page if access level lower than necessary.
     * @param servletRequest that comes from container.
     * @param servletResponse that comes from container.
     * @param filterChain chain of filters which check request.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String requiredCommand = request.getParameter(RequestParameter.COMMAND);
        CommandType command = CommandType.fromString(requiredCommand);

        if(command == null) {
            forwardErrorPage(request,response);
            return;
        }

        String userType = (String)session.getAttribute(SessionAttr.USER_TYPE);
        boolean isGuest = userType == null;

        String commandLevelAccess = command.getLevelAccess();
        boolean isGuestCommand = LevelAccess.GUEST.equalsIgnoreCase(commandLevelAccess);
        boolean isAvailableToAnyCommand = LevelAccess.ANY.equalsIgnoreCase(commandLevelAccess);
        if(isGuest && !isGuestCommand && !isAvailableToAnyCommand) {
            forwardToMainPage(request, response);
            return;
        }


        boolean isCommonCommand = LevelAccess.ONLY_AUTHORIZED.equalsIgnoreCase(commandLevelAccess);

        if(!isCommonCommand && !isAvailableToAnyCommand && !isGuestCommand) {
            boolean hasUserAccess = commandLevelAccess.equalsIgnoreCase(userType);

            if(!hasUserAccess) {
                forwardBlockAccessPage(request, response);
                return;
            }
        }

        if(CommandType.GET_PAGE == command) {
            String jspPageParameter = request.getParameter(RequestParameter.JSP_PAGE);
            PageType page = PageType.fromString(jspPageParameter);

            if(page == null) {
                forwardErrorPage(request, response);
                return;
            }

            String pageLevelAccess = page.getLevelAccess();
            boolean hasUserAccess = pageLevelAccess.equalsIgnoreCase(userType);
            boolean isCommonPage = LevelAccess.ONLY_AUTHORIZED.equalsIgnoreCase(pageLevelAccess);
            boolean isGuestPage = LevelAccess.GUEST.equalsIgnoreCase(pageLevelAccess);

            if(!hasUserAccess && !isCommonPage && !isGuestPage) {
                forwardBlockAccessPage(request, response);
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private void forwardToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        ServletContext servletContext = request.getServletContext();
        String mainPagePath = ConfigurationManager.getProperty(Paths.LOGIN_PAGE);
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(mainPagePath);
        dispatcher.forward(request, response);
    }

    private void forwardBlockAccessPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String accessDeniedPagePath = ConfigurationManager.getProperty(Paths.ACCESS_DENY_PAGE);
        ServletContext servletContext = request.getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(accessDeniedPagePath);
        dispatcher.forward(request, response);
    }

    private void forwardErrorPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String accessDeniedPagePath = ConfigurationManager.getProperty(Paths.ERROR_PAGE);
        ServletContext servletContext = request.getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher(accessDeniedPagePath);
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
