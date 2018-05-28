package com.epam.cafe.command.authorized;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.manager.ConfigurationManager;

public class LogOutCommand implements Command{
    private static final String LOGIN_PAGE = ConfigurationManager.getProperty(Paths.LOGIN_PAGE);

    public LogOutCommand() {}

    /**
     * Returns processed result of request.
     * It is used to log out from account.
     * The only for authorized (both users and admin).
     * @param content sets attributes for session and give parameters from request.
     */
    public RequestResult execute(RequestContent content) {
        content.sessionInvalidate();
        content.setSessionAttributes(SessionAttr.USER, null);
        content.setSessionAttributes(SessionAttr.USER_TYPE, null);
        return new RequestResult(LOGIN_PAGE, NavigationType.FORWARD);
    }
}
