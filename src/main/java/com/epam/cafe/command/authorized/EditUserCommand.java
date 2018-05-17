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

public class EditUserCommand implements Command{
    private static final String CLIENT_ACCOUNT_PATH = ConfigurationManager.getProperty(Paths.CLIENT_ACCOUNT_PAGE);
    private static final String ADMIN_ACCOUNT_PATH = ConfigurationManager.getProperty(Paths.ADMIN_ACCOUNT_PAGE);
    private static final String GET_CLIENT_ACCOUNT_PATH = ConfigurationManager.getProperty(Paths.GET_CLIENT_ACCOUNT_PAGE);
    private static final String GET_ADMIN_ACCOUNT_PATH = ConfigurationManager.getProperty(Paths.GET_ADMIN_ACCOUNT_PAGE);

    private UserService userService;

    public EditUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String email = content.getRequestParameterByName(RequestParameter.EMAIL_PARAM);
        String passport = content.getRequestParameterByName(RequestParameter.PASSPORT_PARAM);
        String password = content.getRequestParameterByName(RequestParameter.PASSWORD_PARAM);
        String name = content.getRequestParameterByName(RequestParameter.NAME_PARAM);
        String surname = content.getRequestParameterByName(RequestParameter.SURNAME_PARAM);
        String phone = content.getRequestParameterByName(RequestParameter.PHONE_PARAM);

        User user = (User)content.getSessionAttributeByName(SessionAttr.USER);
        String nextPath = (user.getRole() == Role.ADMIN)? ADMIN_ACCOUNT_PATH : CLIENT_ACCOUNT_PATH;

        if (!ValidationUtil.isEmailValid(email)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.EMAIL_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }


        String currentUserEmail = user.getMail();
        boolean isCurrentUserEmail = currentUserEmail.equalsIgnoreCase(email);
        if (userService.isEmailExist(email) && !isCurrentUserEmail) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.EXIST_EMAIL_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isSurnameValid(surname)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.SURNAME_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isNameValid(name)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.NAME_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isPassportValid(passport)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PASSPORT_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isPhoneValid(phone)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PHONE_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        String hashedEnterPassword = HashPasswordUtil.sha1(password);
        String currentUserPassword = user.getPassword();
        if (!hashedEnterPassword.equals(currentUserPassword)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PASSWORD_ERROR);
            return new RequestResult(nextPath, NavigationType.FORWARD);
        }

        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setMail(email);
        user.setPassport(passport);

        userService.editUserInfo(user);
        content.setSessionAttributes(SessionAttr.USER, user);
        content.setSessionAttributes(SessionAttr.OPERATION_STATUS_POSITIVE, Messages.EDIT_USER_SUCCESS);

        nextPath = (user.getRole() == Role.ADMIN)? GET_ADMIN_ACCOUNT_PATH : GET_CLIENT_ACCOUNT_PATH;
        return new RequestResult(nextPath, NavigationType.REDIRECT);
    }
}