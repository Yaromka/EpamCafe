package com.epam.cafe.command.page;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;

public class GetPageCommand implements Command{

    @Override
    public RequestResult execute(RequestContent requestContent) throws ServiceException {
        String page = "";
        String jspPageParameter = requestContent.getRequestParameterByName(RequestParameter.JSP_PAGE);

        PageType pageType = PageType.fromString(jspPageParameter);
        if(pageType == null) {
            pageType = PageType.NOT_FOUND_PAGE_JSP;
        }

        page = getString(page, pageType);

        String pagePath = ConfigurationManager.getProperty(page);
        return new RequestResult(pagePath, NavigationType.FORWARD);
    }

    private String getString(String page, PageType pageType) {
        switch (pageType){
            case LOGIN_JSP :
                page = Paths.LOGIN_PAGE;
                break;
            case REGISTRATION_JSP:
                page = Paths.SIGN_UP_PAGE;
                break;
            case MY_ORDERS_JSP:
                page = Paths.MY_ORDERS_PAGE;
                break;
            case CLIENT_ACCOUNT_JSP:
                page = Paths.CLIENT_ACCOUNT_PAGE;
                break;
            case ADMIN_ACCOUNT_JSP:
                page = Paths.ADMIN_ACCOUNT_PAGE;
                break;
            case BASKET_JSP:
                page = Paths.BASKET_PAGE;
                break;
            case ADMIN_CHANGE_PASS_JSP:
                page = Paths.ADMIN_CHANGE_PASS_PAGE;
                break;
            case CLIENT_CHANGE_PASS_JSP:
                page = Paths.CLIENT_CHANGE_PASS_PAGE;
                break;
            case ADD_REVIEW_JSP:
                page = Paths.ADD_REVIEW_PAGE;
                break;
            case ADMIN_CORT_JSP:
                page = Paths.ADMIN_CORT_PAGE;
                break;
            case CLIENT_CORT_JSP:
                page = Paths.CLIENT_FOOD_CORT_PAGE;
                break;
            case ORDERS_JSP:
                page = Paths.ORDERS_PAGE;
                break;
            case NEW_DISH_JSP:
                page = Paths.NEW_DISH_PAGE;
                break;
            case NEW_CATEGORY_JSP:
                page = Paths.NEW_CATEGORY_PAGE;
                break;
            case NOT_FOUND_PAGE_JSP:
                page = Paths.ERROR_PAGE;
                break;
        }
        return page;
    }
}
