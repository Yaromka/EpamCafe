package com.epam.cafe.builder;

import com.epam.cafe.dao.impl.DaoFactory;
import com.epam.cafe.dao.DishDao;
import com.epam.cafe.dao.UserDao;
import com.epam.cafe.entity.*;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.util.DateTimeConverter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class OrderBuilder implements EntityBuilder {
    private static final String ORDER_ID_PARAM = "order_id";
    private static final String ORDER_USER_ID_PARAM = "users_ownerId";
    private static final String ORDER_DATE_PARAM = "order_receipt_time";
    private static final String ORDER_REVIEW_PARAM = "order_review";
    private static final String ORDER_PAYMENT_METHOD_PARAM = "order_payment_method";
    private static final String ORDER_RATING_PARAM = "order_rating";
    private static final String ORDER_STATUS_PARAM = "order_status";

    /**
     * Returns order with all fields filled.
     * It is used in DAO in order to create entities outside.
     * @param resultSet that has all information about category.
     */
    @Override
    public Order createEntity(ResultSet resultSet) throws DAOException{
        Order order = new Order();
        try {

            int id = resultSet.getInt(ORDER_ID_PARAM);
            order.setId(id);

            String statusParameter = resultSet.getString(ORDER_STATUS_PARAM);
            OrderStatus status = OrderStatus.valueOf(statusParameter);
            order.setOrderStatus(status);

            String dateParameter = resultSet.getString(ORDER_DATE_PARAM);
            Date date = DateTimeConverter.convertStringToDateTime(dateParameter);
            order.setDate(date);

            String payMethodParameter = resultSet.getString(ORDER_PAYMENT_METHOD_PARAM);
            PaymentMethod paymentMethod = PaymentMethod.valueOf(payMethodParameter);
            order.setPaymentMethod(paymentMethod);

            String orderRatingParameter = resultSet.getString(ORDER_RATING_PARAM);
            if(orderRatingParameter != null) {
                OrderRating orderRating = OrderRating.stringToEnum(orderRatingParameter);
                order.setRating(orderRating);
            }

            String review = resultSet.getString(ORDER_REVIEW_PARAM);
            if(review != null) {
                order.setReview(review);
            }

            DaoFactory daoFactory = new DaoFactory();
            daoFactory.beginTransaction();
            UserDao userDaoImpl = daoFactory.getUserDao();
            int ownerId = resultSet.getInt(ORDER_USER_ID_PARAM);

            User user = userDaoImpl.getById(ownerId);
            daoFactory.endTransaction();

            order.setUser(user);

            DaoFactory dishDaoFactory = new DaoFactory();
            DishDao dishDaoImpl = dishDaoFactory.getDishDao();

            Map<Dish, Integer> orderDishes = dishDaoImpl.getByOrder(order);
            dishDaoFactory.endTransaction();

            order.setDishes(orderDishes);
        } catch (SQLException e) {
            throw new DAOException("Exception during creating Order", e);
        }
        return order;
    }
}
