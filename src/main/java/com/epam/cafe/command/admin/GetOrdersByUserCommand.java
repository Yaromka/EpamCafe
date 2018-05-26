package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.Order;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.DishService;
import com.epam.cafe.service.OrderService;

import java.util.List;

public class GetOrdersByUserCommand implements Command{
    private static final String ORDERS_PATH = ConfigurationManager.getProperty(Paths.ORDERS_PAGE);
    private static final int RECORDS_PER_PAGE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 1;

    private OrderService orderService;
    private DishService dishService;

    public GetOrdersByUserCommand(OrderService orderService, DishService dishService) {
        this.orderService = orderService;
        this.dishService = dishService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String userIdParameter = content.getRequestParameterByName(RequestParameter.USER_ID);
        Integer userId = Integer.valueOf(userIdParameter);

        int pageNumber = DEFAULT_PAGE_NUMBER;
        String currentPage = content.getRequestParameterByName(SessionAttr.CURRENT_PAGE);
        if(currentPage != null) {
            pageNumber = Integer.parseInt(currentPage);
        }

        int from = (pageNumber-1)*RECORDS_PER_PAGE;
        int numberOfOrders = orderService.countAllUserOrders(userId);

        if(numberOfOrders == 0) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.HAVENT_ORDERS);
            return new RequestResult(ORDERS_PATH, NavigationType.FORWARD);
        }

        List<Order> orderList = orderService.findOrdersByUserId(userId, from, RECORDS_PER_PAGE);

        for (Order order: orderList) {
            order.setDishes(dishService.findDishesByOrder(order));
        }
        int numberOfPages = (int) Math.ceil(numberOfOrders * 1.0 / RECORDS_PER_PAGE);

        String commandParameters = "&userId="+userIdParameter+"&";
        content.setSessionAttributesForPagination(numberOfPages, pageNumber, commandParameters);
        content.setSessionAttributes(SessionAttr.ORDERS_LIST, orderList);
        return new RequestResult(ORDERS_PATH, NavigationType.FORWARD);
    }
}