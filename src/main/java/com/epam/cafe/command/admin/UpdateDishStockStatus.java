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
import com.epam.cafe.service.DishService;

public class UpdateDishStockStatus implements Command{
    private static final String ADMIN_FOOD_CORT_PATH = ConfigurationManager.getProperty(Paths.GET_ADMIN_FOODCORT_PAGE);

    private DishService dishService;

    public UpdateDishStockStatus(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * Returns processed result of request.
     * It is used to update information on the availability of concrete dish.
     * @param content sets attributes for session and give parameters from request.
     */
    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String newEnableStatusParameter = content.getRequestParameterByName(RequestParameter.NEW_ENABLE_STATUS);
        Boolean newEnableStatus = Boolean.valueOf(newEnableStatusParameter);

        String dishIdParameter = content.getRequestParameterByName(RequestParameter.DISH_ID);
        Integer dishId = Integer.valueOf(dishIdParameter);
        dishService.updateEnableStatus(newEnableStatus, dishId);


        content.setSessionAttributes(SessionAttr.OPERATION_STATUS_POSITIVE, Messages.SUCCESS);

        return new RequestResult(ADMIN_FOOD_CORT_PATH, NavigationType.FORWARD);
    }
}

