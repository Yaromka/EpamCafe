package com.epam.cafe.command.user;

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
import com.epam.cafe.service.DishService;

import java.util.List;

public class GetMenuCommand implements Command{
    private static final String CLIENT_FOOD_CORT_PATH = ConfigurationManager.getProperty(Paths.CLIENT_FOOD_CORT_PAGE);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final String EMPTY_PARAM_REGEX = "x";

    private DishService dishService;

    public GetMenuCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String categoryParam = content.getRequestParameterByName(RequestParameter.CATEGORY);
        Category category = null;
        if (!EMPTY_PARAM_REGEX.equals(categoryParam)) {
            category = new Category();
            category.setId(Integer.valueOf(categoryParam));
        }

        int pageNumber = DEFAULT_PAGE_NUMBER;

        String currentPage = content.getRequestParameterByName(SessionAttr.CURRENT_PAGE);
        if(currentPage != null) {
            pageNumber = Integer.parseInt(currentPage);
        }

        int from = (pageNumber-1)*RECORDS_PER_PAGE;

        int numberOfDishes = dishService.countDishesByParameters(category, true);
        if(numberOfDishes == 0) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.DISHES_NOT_FOUND);
            return new RequestResult(CLIENT_FOOD_CORT_PATH, NavigationType.FORWARD);
        }

        List<Dish> dishList = dishService.getMenu(category,from,RECORDS_PER_PAGE);
        int numberOfPages = (int) Math.ceil(numberOfDishes * 1.0 / RECORDS_PER_PAGE);

        String commandParameters = "&category="+categoryParam+"&";
        content.setSessionAttributesForPagination(numberOfPages, pageNumber, commandParameters);
        content.setSessionAttributes(SessionAttr.DISH_LIST, dishList);

        return new RequestResult(CLIENT_FOOD_CORT_PATH, NavigationType.FORWARD);
    }
}