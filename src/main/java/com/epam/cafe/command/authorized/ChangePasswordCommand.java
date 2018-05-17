package com.epam.cafe.command.authorized;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.Role;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.UserService;
import com.epam.cafe.util.HashPasswordUtil;
import com.epam.cafe.util.ValidationUtil;

public class ChangePasswordCommand implements Command{
    private static final String LOGIN_PATH = ConfigurationManager.getProperty(Paths.LOGIN_PAGE);
    private static final String ADMIN_CHANGE_PASS_PATH = ConfigurationManager.getProperty(Paths.ADMIN_CHANGE_PASS_PAGE);
    private static final String CLIENT_CHANGE_PASS_PATH = ConfigurationManager.getProperty(Paths.CLIENT_CHANGE_PASS_PAGE);

    private UserService userService;

    public ChangePasswordCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String newPassword = content.getRequestParameterByName(RequestParameter.NEW_PASSWORD_PARAM);
        String newPasswordConfirm = content.getRequestParameterByName(RequestParameter.NEW_PASSWORD_REPEAT_PARAM);
        String oldPassword = content.getRequestParameterByName(RequestParameter.PASSWORD_PARAM);

        User user = (User)content.getSessionAttributeByName(SessionAttr.USER);
        Role userRole = user.getRole();
        String nextPath = (Role.ADMIN == userRole)? ADMIN_CHANGE_PASS_PATH : CLIENT_CHANGE_PASS_PATH;

        if (!ValidationUtil.isPasswordValid(newPassword)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.NEW_PASS_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isPasswordRepeatValid(newPassword, newPasswordConfirm)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PASSWORD_REPEAT_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        String hashedPassword = HashPasswordUtil.sha1(oldPassword);
        if (!hashedPassword.equals(user.getPassword())) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PASS_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        String hashedNewPassword = HashPasswordUtil.sha1(newPassword);
        userService.changePassword(user, hashedNewPassword);

        content.sessionInvalidate();
        content.setSessionAttributes(SessionAttr.USER, null);
        content.setSessionAttributes(SessionAttr.USER_TYPE, null);
        return new RequestResult(LOGIN_PATH, NavigationType.FORWARD);
    }
}