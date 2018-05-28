package com.epam.cafe.command.user;

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
import com.epam.cafe.service.OrderService;
import com.epam.cafe.util.ValidationUtil;

public class AddReviewCommand implements Command{
    private static final String ADD_REVIEW_PAGE_PATH = ConfigurationManager.getProperty(Paths.GET_REVIEW_PAGE);
    private static final String CLIENT_MENU_PAGE_PATH = ConfigurationManager.getProperty(Paths.GET_CLIENT_MENU);
    private OrderService orderService;

    public AddReviewCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Returns processed result of request.
     * It is used to add a review for an order and validate the mark.
     * @param content sets attributes for session and give parameters from request.
     */
    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String orderIdParam = content.getRequestParameterByName(RequestParameter.ORDER_ID);
        String markParameter = content.getRequestParameterByName(RequestParameter.MARK);
        String comment = content.getRequestParameterByName(RequestParameter.COMMENT);

        if(comment == null || comment.isEmpty() || !ValidationUtil.isMarkValid(markParameter)) {
            content.setSessionAttributes(SessionAttr.REVIEWED_ORDER_ID_PARAM, orderIdParam);
            return new RequestResult(ADD_REVIEW_PAGE_PATH, NavigationType.FORWARD);
        }

        Integer mark = Integer.parseInt(markParameter);
        Integer orderId = Integer.valueOf(orderIdParam);
        orderService.addReview(orderId, mark, comment);

        content.setSessionAttributes(SessionAttr.OPERATION_STATUS_POSITIVE, Messages.SUCCESS);
        return new RequestResult(CLIENT_MENU_PAGE_PATH, NavigationType.REDIRECT);
    }
}

