package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.Dish;
import com.epam.cafe.entity.Order;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.DishService;
import com.epam.cafe.service.OrderService;
import com.epam.cafe.util.DateTimeConverter;
import com.epam.cafe.util.ValidationUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GetOrdersByParametersCommand implements Command{
    private static final String ADMIN_ORDER_PATH = ConfigurationManager.getProperty(Paths.ORDERS_PAGE);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final String EMPTY_PARAM_REGEX = "x";

    private OrderService orderService;
    private DishService dishService;

    public GetOrdersByParametersCommand(OrderService orderService, DishService dishService) {
        this.orderService = orderService;
        this.dishService = dishService;
    }

    /**
     * Returns processed result of request.
     * It is used to show orders that are in concrete time-bound.
     * @param content sets attributes for session and give parameters from request.
     */
    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String payStatusParam = content.getRequestParameterByName(RequestParameter.PAY_STATUS);
        String payStatus = null;
        if (!EMPTY_PARAM_REGEX.equals(payStatusParam)) {
            payStatus = payStatusParam;
        }

        int pageNumber = DEFAULT_PAGE_NUMBER;
        String currentPage = content.getRequestParameterByName(SessionAttr.CURRENT_PAGE);
        if(currentPage != null) {
            pageNumber = Integer.parseInt(currentPage);
        }
        int from = (pageNumber-1)*RECORDS_PER_PAGE;

        String startDateParam = content.getRequestParameterByName(RequestParameter.START_DATE);
        String endDateParam = content.getRequestParameterByName(RequestParameter.END_DATE);

        String startDate = null;
        String endDate = null;
        if(!startDateParam.isEmpty() || !endDateParam.isEmpty()) {
            if(!isDateParametersValid(startDateParam, endDateParam)) {
                content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.NOT_VALID_DATE);
                return new RequestResult(ADMIN_ORDER_PATH, NavigationType.FORWARD);
            }
            startDate = startDateParam;
            endDate = endDateParam;
        }

        int numberOfOrders = orderService.countOrdersByParameters(startDate, endDate, payStatus);

        if(numberOfOrders == 0) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.ORDERS_NOT_FOUND);
            return new RequestResult(ADMIN_ORDER_PATH, NavigationType.FORWARD);
        }

        List<Order> orderList = orderService.findOrdersByParameters(startDate,endDate,payStatus,from,RECORDS_PER_PAGE);

        for (Order order: orderList) {
            Map<Dish, Integer> dishes = dishService.findDishesByOrder(order);
            order.setDishes(dishes);
        }

        int numberOfPages = (int) Math.ceil(numberOfOrders * 1.0 / RECORDS_PER_PAGE);

        String commandParameters = "&pay_status="+payStatusParam+"&startDate="+startDateParam+"&endDate="+endDateParam+"&";
        content.setSessionAttributesForPagination(numberOfPages, pageNumber, commandParameters);
        content.setSessionAttributes(SessionAttr.ORDERS_LIST, orderList);

        return new RequestResult(ADMIN_ORDER_PATH, NavigationType.FORWARD);
    }

    private boolean isDateParametersValid(String startDate, String endDate) {
        if (!ValidationUtil.isDateValid(startDate) || !ValidationUtil.isDateValid(endDate)) {
            return false;
        }
        Date start = DateTimeConverter.convertStringToDate(startDate);
        Date end = DateTimeConverter.convertStringToDate(endDate);

        return ValidationUtil.isTimePeriodValid(start, end);
    }
}
