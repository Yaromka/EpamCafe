package com.epam.cafe.command.guest;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.SignUpBean;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.UserService;
import com.epam.cafe.util.HashPasswordUtil;
import com.epam.cafe.util.ValidationUtil;

public class SignUpCommand implements Command{
    private static final String LOGIN_PATH = ConfigurationManager.getProperty(Paths.GET_LOGIN_PAGE);
    private static final String REGISTRATION_PATH = ConfigurationManager.getProperty(Paths.SIGN_UP_PAGE);

    private UserService userService;

    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String email = content.getRequestParameterByName(RequestParameter.EMAIL_PARAM);
        String passport = content.getRequestParameterByName(RequestParameter.PASSPORT_PARAM);
        String password = content.getRequestParameterByName(RequestParameter.PASSWORD_PARAM);
        String repeatPassword = content.getRequestParameterByName(RequestParameter.REPEAT_PASSWORD_PARAM);
        String name = content.getRequestParameterByName(RequestParameter.NAME_PARAM);
        String surname = content.getRequestParameterByName(RequestParameter.SURNAME_PARAM);
        String phoneNumber = content.getRequestParameterByName(RequestParameter.PHONE_PARAM);

        SignUpBean registrationBean = new SignUpBean(name,surname,email,phoneNumber,passport);
        content.setSessionAttributes(SessionAttr.REGISTRATION_BEAN, registrationBean);

        if (!ValidationUtil.isEmailValid(email)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.EMAIL_ERROR);
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }


        if (userService.isEmailExist(email)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.EXIST_EMAIL_ERROR);
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isSurnameValid(surname)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.SURNAME_ERROR);
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isNameValid(name)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.NAME_ERROR);
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isPassportValid(passport)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PASSPORT_ERROR);
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isPhoneValid(phoneNumber)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PHONE_ERROR);
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isPasswordValid(password)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PASSWORD_ERROR);
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }

        if (!ValidationUtil.isPasswordRepeatValid(password, repeatPassword)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.PASSWORD_REPEAT_ERROR);
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }

        String hashedPassword = HashPasswordUtil.sha1(password);
        User user = new User(name, surname, phoneNumber, passport, hashedPassword, email);

        if(!userService.signUp(user)) {
            return new RequestResult(REGISTRATION_PATH, NavigationType.FORWARD);
        }

        return new RequestResult(LOGIN_PATH, NavigationType.REDIRECT);
    }
}
