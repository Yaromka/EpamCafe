package com.epam.cafe.command.user;

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
import com.epam.cafe.service.DishService;

public class AddToBasketCommand implements Command{
    private static final String GET_MENU_PAGE = ConfigurationManager.getProperty(Paths.GET_CLIENT_FOOD_CORT_PAGE);
    private DishService dishService;

    public AddToBasketCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String dishIdParameter = content.getRequestParameterByName(RequestParameter.DISH_ID);
        User user = (User)content.getSessionAttributeByName(SessionAttr.USER);

        Integer dishId = Integer.valueOf(dishIdParameter);
        dishService.addToBasket(user, dishId);

        content.setSessionAttributes(SessionAttr.OPERATION_STATUS_POSITIVE, Messages.SUCCESS);

        return new RequestResult(GET_MENU_PAGE, NavigationType.REDIRECT);
    }
}