package com.epam.cafe.command.guest;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.Category;
import com.epam.cafe.entity.Role;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.CategoryService;
import com.epam.cafe.service.UserService;
import com.epam.cafe.util.HashPasswordUtil;

import java.util.List;

public class LogInCommand implements Command{
    private static final String GET_CLIENT_FOOD_CORT_PAGE = ConfigurationManager.getProperty(Paths.GET_CLIENT_MENU);
    private static final String GET_ADMIN_CORT_PAGE = ConfigurationManager.getProperty(Paths.GET_ADMIN_CORT);
    private static final String GET_LOGIN_PAGE = ConfigurationManager.getProperty(Paths.GET_LOGIN_PAGE);
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    private UserService userService;
    private CategoryService categoryService;

    public LogInCommand(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public RequestResult execute(RequestContent content) throws ServiceException {

        String enterEmail = content.getRequestParameterByName(RequestParameter.EMAIL);
        String enterPassword = content.getRequestParameterByName(RequestParameter.PASSWORD);
        String hashPassword = HashPasswordUtil.sha1(enterPassword);

        User user = userService.logIn(enterEmail, hashPassword);

        if(user == null) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.LOGIN_ERROR);
            return new RequestResult(GET_LOGIN_PAGE, NavigationType.FORWARD);
        }

        if(user.getLoyaltyPoints() < 0) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.YOU_ARE_BLOCK);
            return new RequestResult(GET_LOGIN_PAGE, NavigationType.FORWARD);
        }

        content.setSessionAttributes(SessionAttr.USER, user);

        List<Category> categoryList = categoryService.getAll();
        content.setSessionAttributes(SessionAttr.CATEGORY_LIST, categoryList);

        if(user.getRole() == Role.CLIENT) {
            content.setSessionAttributes(SessionAttr.USER_TYPE, USER);
            return new RequestResult(GET_CLIENT_FOOD_CORT_PAGE, NavigationType.FORWARD);
        }

        content.setSessionAttributes(SessionAttr.USER_TYPE, ADMIN);
        return new RequestResult(GET_ADMIN_CORT_PAGE, NavigationType.FORWARD);
    }
}

