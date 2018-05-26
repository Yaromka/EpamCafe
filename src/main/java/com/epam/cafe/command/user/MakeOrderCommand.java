package com.epam.cafe.command.user;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.*;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.DishService;
import com.epam.cafe.service.OrderService;
import com.epam.cafe.util.DateTimeConverter;
import com.epam.cafe.util.ValidationUtil;

import java.util.Date;
import java.util.Map;

public class MakeOrderCommand implements Command{
    private static final String USER_ORDERS_PATH = ConfigurationManager.getProperty(Paths.GET_MY_ORDERS_PAGE);
    private static final String BASKET_PATH = ConfigurationManager.getProperty(Paths.BASKET_PAGE);

    private OrderService orderService;
    private DishService dishService;

    public MakeOrderCommand(OrderService orderService, DishService dishService) {
        this.orderService = orderService;
        this.dishService = dishService;
    }

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        User user = (User)content.getSessionAttributeByName(SessionAttr.USER);
        ShopBasket shopBasket = user.getShopBasket();
        Map<Dish, Integer> dishesFromBasket = shopBasket.getBasket();

        if(dishesFromBasket.isEmpty()) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.BASKET_IS_EMPTY_ERROR);
            return new RequestResult(BASKET_PATH, NavigationType.FORWARD);
        }

        String date = content.getRequestParameterByName(RequestParameter.ODER_DATE);
        if (!ValidationUtil.isDateValid(date)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.NOT_VALID_DATE);
            return new RequestResult(BASKET_PATH, NavigationType.FORWARD);
        }

        String time = content.getRequestParameterByName(RequestParameter.ODER_TIME);
        if(!ValidationUtil.isTimeValid(time)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.TIME_NOT_VALID);
            return new RequestResult(BASKET_PATH, NavigationType.FORWARD);
        }

        String dateTime = date +" "+ time;
        Date orderDate = DateTimeConverter.convertStringToDateTime(dateTime);
        if(!ValidationUtil.isDateAfterNow(orderDate)) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.DATETIME_NOT_VALID);
            return new RequestResult(BASKET_PATH, NavigationType.FORWARD);
        }

        Order order = new Order();
        order.setUser(user);
        order.setDate(orderDate);

        String payMethodParameter = content.getRequestParameterByName(RequestParameter.ORDER_PAY_METHOD);
        PaymentMethod payMethod = PaymentMethod.valueOf(payMethodParameter);
        order.setPaymentMethod(payMethod);

        order = orderService.createOrder(order);

        if(order == null) {
            content.setSessionAttributes(SessionAttr.OPERATION_STATUS_NEGATIVE, Messages.DONT_ENOUGH_MONEY);
            return  new RequestResult(BASKET_PATH, NavigationType.FORWARD);
        }

        dishService.addDishesInOrder(order, dishesFromBasket);
        shopBasket.clean();

        return new RequestResult(USER_ORDERS_PATH, NavigationType.FORWARD);
    }
}
