package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.UserService;

public class UpdateLoyaltyPointsCommand implements Command{
    private static final String MAX_LOYALTY_POINT = ConfigurationManager.getProperty("max.loyalty.point");
    private static final String ADMIN_CORT_PATH = ConfigurationManager.getProperty(Paths.GET_ADMIN_CORT);
    private static final String ADD_LOYALTY_POINT = "+";
    private static final String DEDUCT_LOYALTY_POINT = "-";
    private static final int MIN_LOYALTY_POINT_VALUE = 0;
    private UserService userService;

    public UpdateLoyaltyPointsCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws ServiceException {
        String loyaltyPointsParameter = requestContent.getRequestParameterByName(RequestParameter.USER_LOYALTY_POINTS);
        Long loyaltyPoints = Long.valueOf(loyaltyPointsParameter);

        String loyaltyOperationParameter = requestContent.getRequestParameterByName(RequestParameter.LOYALTY_OPERATION);
        if(loyaltyPoints < MIN_LOYALTY_POINT_VALUE && DEDUCT_LOYALTY_POINT.equals(loyaltyOperationParameter)) {
            requestContent.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.USER_ALREADY_BLOCKED);
            return new RequestResult(ADMIN_CORT_PATH, NavigationType.FORWARD);
        }

        if(MAX_LOYALTY_POINT.equals(loyaltyPointsParameter) && ADD_LOYALTY_POINT.equals(loyaltyOperationParameter)) {
            requestContent.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.USER_HAS_MAX_LOYALTY_POINT);
            return new RequestResult(ADMIN_CORT_PATH, NavigationType.FORWARD);
        }

        long newLoyaltyPointsValue = loyaltyPoints;

        if(ADD_LOYALTY_POINT.equals(loyaltyOperationParameter)) {
            newLoyaltyPointsValue += 1;
        }

        if(DEDUCT_LOYALTY_POINT.equals(loyaltyOperationParameter)) {
            newLoyaltyPointsValue -= 1;
        }

        String userIdParameter = requestContent.getRequestParameterByName(RequestParameter.USER_ID);
        Integer userId = Integer.valueOf(userIdParameter);
        userService.updateLoyaltyPointsByUserId(newLoyaltyPointsValue, userId);

        return new RequestResult(ADMIN_CORT_PATH, NavigationType.FORWARD);
    }
}