package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.UserService;

import java.util.List;

public class FindAllUsersCommand implements Command{
    private static final String ADMIN_CORT_PATH = ConfigurationManager.getProperty(Paths.ADMIN_CORT_PAGE);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 1;

    private UserService userService;

    public FindAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        int pageNumber = DEFAULT_PAGE_NUMBER;

        String currentPageNumber = content.getRequestParameterByName(SessionAttr.CURRENT_PAGE);
        if(currentPageNumber != null) {
            pageNumber = Integer.parseInt(currentPageNumber);
        }
        int from = (pageNumber-1)*RECORDS_PER_PAGE;

        int numberOfUsers = userService.countAllUsers();

        if(numberOfUsers == 0) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.USER_NOT_FOUND);
            return new RequestResult(ADMIN_CORT_PATH, NavigationType.FORWARD);
        }

        List<User> userList = userService.findAllUsers(from, RECORDS_PER_PAGE);
        int numberOfPages = (int) Math.ceil(numberOfUsers * 1.0 / RECORDS_PER_PAGE);

        String commandParameters = "&";
        content.setSessionAttributesForPagination(numberOfPages, pageNumber, commandParameters);
        content.setSessionAttributes(SessionAttr.USER_LIST, userList);

        return new RequestResult(ADMIN_CORT_PATH, NavigationType.FORWARD);
    }
}
