package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
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

public class GetDishCommand implements Command{
    private static final String ADMIN_FOOD_CORT_PATH = ConfigurationManager.getProperty(Paths.ADMIN_FOODCORT_PAGE);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final String EMPTY_PARAM_REGEX = "x";

    private DishService dishService;

    public GetDishCommand(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * Returns processed result of request.
     * It is used to show dishes that fall under some category or/and has concrete enable status.
     * @param content sets attributes for session and give parameters from request.
     */
    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String categoryParam = content.getRequestParameterByName(RequestParameter.CATEGORY);
        Category category = null;
        if (!EMPTY_PARAM_REGEX.equals(categoryParam)) {
            category = new Category();
            category.setId(Integer.valueOf(categoryParam));
        }

        int pageNumber = DEFAULT_PAGE_NUMBER;

        String currentPageNumber = content.getRequestParameterByName(SessionAttr.CURRENT_PAGE);
        if(currentPageNumber != null) {
            pageNumber = Integer.parseInt(currentPageNumber);
        }

        int from = (pageNumber-1)*RECORDS_PER_PAGE;

        String enableStatusParameter = content.getRequestParameterByName(RequestParameter.ENABLE_STATUS_PARAMETER);
        Boolean enableStatus = null;
        if(!EMPTY_PARAM_REGEX.equals(enableStatusParameter)) {
            enableStatus = Boolean.valueOf(enableStatusParameter);
        }

        int numberOfDishes = dishService.countDishesByParameters(category, enableStatus);
        List<Dish> dishList = dishService.findDishesByParameters(category, enableStatus, from, RECORDS_PER_PAGE);
        int numberOfPages = (int) Math.ceil(numberOfDishes * 1.0 / RECORDS_PER_PAGE);

        String commandParameters = "&category="+categoryParam+"&enable_status="+enableStatusParameter+"&";
        content.setSessionAttributesForPagination(numberOfPages, pageNumber, commandParameters);
        content.setSessionAttributes(SessionAttr.DISH_LIST, dishList);

        return new RequestResult(ADMIN_FOOD_CORT_PATH, NavigationType.FORWARD);
    }
}