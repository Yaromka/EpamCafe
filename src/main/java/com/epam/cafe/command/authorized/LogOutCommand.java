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

    public RequestResult execute(RequestContent content) {
        content.sessionInvalidate();
        content.setSessionAttributes(SessionAttr.USER, null);
        content.setSessionAttributes(SessionAttr.USER_TYPE, null);
        return new RequestResult(LOGIN_PAGE, NavigationType.FORWARD);
    }
}
