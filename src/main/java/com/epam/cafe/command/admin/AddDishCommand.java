package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.Category;
import com.epam.cafe.entity.Dish;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.CategoryService;
import com.epam.cafe.service.DishService;
import com.epam.cafe.util.ValidationUtil;

public class AddDishCommand implements Command{
    private static final String NEW_DISH_PATH = ConfigurationManager.getProperty(Paths.GET_NEW_DISH_PAGE);

    private DishService dishService;
    private CategoryService categoryService;

    public AddDishCommand(DishService dishService, CategoryService categoryService) {
        this.dishService = dishService;
        this.categoryService = categoryService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String price = content.getRequestParameterByName(RequestParameter.DISH_PRICE_PARAM);
        if (!ValidationUtil.isPriceValid(price)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.ADD_PRICE_ERROR);
            return new RequestResult(NEW_DISH_PATH, NavigationType.FORWARD);
        }

        String dishDescription = content.getRequestParameterByName(RequestParameter.DISH_DESCRIPTION_PARAM);
        if (!ValidationUtil.isDescriptionValid(dishDescription)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.ADD_DESCRIPTION_ERROR);
            return new RequestResult(NEW_DISH_PATH, NavigationType.FORWARD);
        }

        String dishAmount = content.getRequestParameterByName(RequestParameter.DISH_AMOUNT_PARAM);
        if (!ValidationUtil.isAmountValid(dishAmount)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.ADD_AMOUNT_ERROR);
            return new RequestResult(NEW_DISH_PATH, NavigationType.FORWARD);
        }

        String categoryId = content.getRequestParameterByName(RequestParameter.DISH_CATEGORY_ID_PARAM);

        Integer categoryIdNumber = Integer.parseInt(categoryId);
        Category category = categoryService.getCategoryById(categoryIdNumber);

        Integer weight = Integer.parseInt(dishAmount);
        Double doublePrice = Double.parseDouble(price);
        long dishPrice = Math.round(doublePrice * 100);
        String dishName = content.getRequestParameterByName(RequestParameter.DISH_NAME_PARAM);
        Dish dish = new Dish(dishName, dishDescription, weight, dishPrice, category);

        if(!dishService.addDish(dish)) {
            return new RequestResult(NEW_DISH_PATH, NavigationType.FORWARD);
        }

        content.setSessionAttributes(SessionAttr.OPERATION_STATUS_POSITIVE, Messages.SUCCESS);

        return new RequestResult(NEW_DISH_PATH, NavigationType.REDIRECT);
    }
}

