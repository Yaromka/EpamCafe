package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.UserService;
import com.epam.cafe.util.ValidationUtil;

import java.util.List;

public class FindUserBySurnameCommand implements Command{
    private static final String ADMIN_CORT_PATH = ConfigurationManager.getProperty(Paths.ADMIN_CORT_PAGE);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 1;

    private UserService userService;

    public FindUserBySurnameCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String surname = content.getRequestParameterByName(RequestParameter.USER_SURNAME);

        if (!ValidationUtil.isSurnameValid(surname)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.SURNAME_ERROR);
            return new RequestResult(ADMIN_CORT_PATH, NavigationType.FORWARD);
        }

        int pageNumber = DEFAULT_PAGE_NUMBER;
        String currentPage = content.getRequestParameterByName(SessionAttr.CURRENT_PAGE);
        if(currentPage != null) {
            pageNumber = Integer.parseInt(currentPage);
        }
        int from = (pageNumber-1)*RECORDS_PER_PAGE;

        int numberOfUsers = userService.countUsersBySurname(surname);
        if(numberOfUsers == 0) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.USER_NOT_FOUND);
            return new RequestResult(ADMIN_CORT_PATH, NavigationType.FORWARD);
        }

        List<User> userList = userService.findUserBySurname(surname, from, RECORDS_PER_PAGE);
        int numberOfPages = (int) Math.ceil(numberOfUsers * 1.0 / RECORDS_PER_PAGE);

        String commandParameters = "&surname="+surname+"&";
        content.setSessionAttributesForPagination(numberOfPages, pageNumber, commandParameters);
        content.setSessionAttributes(SessionAttr.USER_LIST, userList);

        return new RequestResult(ADMIN_CORT_PATH, NavigationType.FORWARD);
    }
}