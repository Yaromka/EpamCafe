package com.epam.cafe.command.user;

import com.epam.cafe.command.Command;
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

public class RemoveFromBasketCommand implements Command{
    private static final String BASKET_PATH = ConfigurationManager.getProperty(Paths.GET_BASKET_PAGE);

    private DishService dishService;

    public RemoveFromBasketCommand(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * Returns processed result of request.
     * It is used to remove from basket added dish.
     * @param content sets attributes for session and give parameters from request.
     */
    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String dishId = content.getRequestParameterByName(RequestParameter.DISH_ID);
        User user = (User)content.getSessionAttributeByName(SessionAttr.USER);

        dishService.removeFromBasket(user, dishId);
        return new RequestResult(BASKET_PATH, NavigationType.REDIRECT);
    }
}