package com.epam.cafe.command.locale;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.Role;
import com.epam.cafe.entity.User;
import com.epam.cafe.manager.ConfigurationManager;

public class LocaleCommand implements Command{
    private static final String LOGIN_PAGE = ConfigurationManager.getProperty(Paths.LOGIN_PAGE);
    private static final String CLIENT_MENU_PAGE = ConfigurationManager.getProperty(Paths.CLIENT_FOOD_CORT_PAGE);
    private static final String ADMIN_CORT_PAGE = ConfigurationManager.getProperty(Paths.ADMIN_CORT_PAGE);
    private static final String EN_LOCALE_MARKER = "EN";
    private static final String EN_LOCALE_ATR = "en_US";
    private static final String RU_LOCALE_ATR = "ru_RU";

    public LocaleCommand() {}

    @Override
    public RequestResult execute(RequestContent content){
        String localeParameter = content.getRequestParameterByName(SessionAttr.LOCALE);

        String locale = chooseLocale(localeParameter);
        content.setSessionAttributes(SessionAttr.LOCALE, locale);

        User user = (User)content.getSessionAttributeByName(SessionAttr.USER);

        if (user == null) {
            return new RequestResult(LOGIN_PAGE , NavigationType.FORWARD);
        }

        Role userRole = user.getRole();
        if(userRole == Role.CLIENT) {
            return new RequestResult(CLIENT_MENU_PAGE , NavigationType.FORWARD);
        }

        return new RequestResult(ADMIN_CORT_PAGE, NavigationType.FORWARD);
    }

    private String chooseLocale(String localeParameter) {
        String locale;
        switch (localeParameter) {
            case EN_LOCALE_MARKER :
                locale = EN_LOCALE_ATR;
                break;
            default:
                locale = RU_LOCALE_ATR;
                break;
        }
        return locale;
    }
}