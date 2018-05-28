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
import com.epam.cafe.entity.OrderStatus;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.OrderService;

import java.util.List;

public class ChangePayStatusCommand implements Command{
    private static final String ORDERS_PATH = ConfigurationManager.getProperty(Paths.ORDERS_PAGE);

    private OrderService orderService;

    public ChangePayStatusCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Returns processed result of request.
     * It updates pay status after changing by administrator.
     * @param requestContent that has all information about current order.
     */
    @Override
    @SuppressWarnings("unchecked")
    public RequestResult execute(RequestContent requestContent) throws ServiceException {
        String orderId = requestContent.getRequestParameterByName(RequestParameter.ORDER_ID);
        Integer currentOrderId = Integer.parseInt(orderId);

        List<Order> orders = (List<Order>)requestContent.getSessionAttributeByName(SessionAttr.ORDERS_LIST);

        Order currentUserOrder = null;
        for (Order order : orders) {
            Integer id = order.getId();

            if(id.equals(currentOrderId)) {
                currentUserOrder = order;
                break;
            }
        }

        String orderStatus = requestContent.getRequestParameterByName(RequestParameter.ORDER_STATUS);
        orderService.updatePaidStatus(currentUserOrder, OrderStatus.valueOf(orderStatus));
        requestContent.setSessionAttributes(SessionAttr.PAID_COMMAND_STATUS, Messages.SUCCESS);

        return new RequestResult(ORDERS_PATH, NavigationType.REDIRECT);
    }
}
