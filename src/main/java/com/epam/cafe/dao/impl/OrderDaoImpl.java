package com.epam.cafe.dao.impl;

import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.AbstractDao;
import com.epam.cafe.dao.OrderDao;
import com.epam.cafe.entity.*;
import com.epam.cafe.util.ResultSetConverter;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.util.DateTimeConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.epam.cafe.constants.ParameterIndexes.*;

public class OrderDaoImpl extends AbstractDao implements OrderDao{
    private static final String FIND_ALL_ORDERS = "SELECT * FROM orders limit ?,?";
    private static final String COUNT_ALL_ORDERS = "SELECT count(*) FROM orders";
    private static final String FIND_ORDERS_BY_USER = "SELECT * FROM orders WHERE users_ownerId = ? limit ?,?";
    private static final String COUNT_ORDERS_BY_USER = "SELECT count(*) FROM orders WHERE users_ownerId = ?";
    private static final String FIND_ORDER_BY_RECEIPT_DATE =
            "SELECT * FROM orders WHERE order_receipt_time >= STR_TO_DATE(?,'%Y-%m-%d') " +
                    "AND order_receipt_time <= STR_TO_DATE(?,'%Y-%m-%d') limit ?,?";
    private static final String COUNT_ORDER_BY_RECEIPT_DATE =
            "SELECT count(*) FROM orders WHERE order_receipt_time >= STR_TO_DATE(?,'%Y-%m-%d') " +
                    "AND order_receipt_time <= STR_TO_DATE(?,'%Y-%m-%d')";
    private static final String FIND_ORDERS_BY_PAY_STATUS = "SELECT * FROM orders WHERE order_status = ? limit ?,?";
    private static final String COUNT_ORDERS_BY_PAY_STATUS = "SELECT count(*) FROM orders WHERE order_status = ?";
    private static final String FIND_ORDERS_BY_PAY_STATUS_AND_RECEIPT_DATE =
            "SELECT * FROM orders WHERE order_status = ? AND order_receipt_time >= STR_TO_DATE(?,'%Y-%m-%d') " +
                    "AND order_receipt_time <= STR_TO_DATE(?,'%Y-%m-%d') limit ?,?";
    private static final String COUNT_ORDERS_BY_PAY_STATUS_AND_RECEIPT_DATE =
            "SELECT count(*) FROM orders WHERE order_status = ? AND order_receipt_time >= STR_TO_DATE(?,'%Y-%m-%d') " +
                    "AND order_receipt_time <= STR_TO_DATE(?,'%Y-%m-%d')";
    private static final String INSERT_NEW_ORDER =
            "INSERT INTO orders (order_receipt_time, order_payment_method, users_ownerId) " +
                    "VALUES(STR_TO_DATE(?,'%Y-%m-%d %H:%i'),?,?)";
    private static final String INSERT_NEW_PAYED_ORDER =
            "INSERT INTO orders (order_receipt_time, order_payment_method, users_ownerId, order_status) " +
                    "VALUES(STR_TO_DATE(?,'%Y-%m-%d %H:%i'),?,?,'PAID')";
    private static final String UPDATE_PAID_STATUS = "UPDATE orders SET order_status = ? WHERE order_id = ?";
    private static final String UPDATE_REVIEW = "UPDATE orders SET order_rating = ?, order_review = ? WHERE order_id = ?";

    public OrderDaoImpl(ConnectionProxy connection) {
        super.setConnection(connection);
    }

    public Order create(Order order) throws DAOException {
        Date orderDate = order.getDate();
        String orderDateString = DateTimeConverter.convertDateTimeToString(orderDate);

        PaymentMethod payMethod = order.getPaymentMethod();
        String paymentMethod = payMethod.toString();

        User user = order.getUser();
        Integer userId = user.getId();

        String sqlStatement = INSERT_NEW_ORDER;
        if(payMethod == PaymentMethod.CLIENTBILL) {
            sqlStatement = INSERT_NEW_PAYED_ORDER;
        }

        order.setId(getInsertId(sqlStatement, orderDateString, paymentMethod, userId));
        return order;
    }

    @SuppressWarnings("unchecked")
    public List<Order> getAll(int from, int limit) throws DAOException {
        return executeQuery(FIND_ALL_ORDERS, from, limit);
    }

    @SuppressWarnings("unchecked")
    public List<Order> getByUserId(int id, int from, int limit) throws DAOException {
        return executeQuery(FIND_ORDERS_BY_USER, id, from, limit);
    }


    public void updateOrderPaidStatus(Order order, OrderStatus status) throws DAOException {
        executeUpdate(UPDATE_PAID_STATUS, status.toString(), order.getId());
    }


    public void addOrderReview(int orderId, int mark, String comment) throws DAOException {
        executeUpdate(UPDATE_REVIEW, mark, comment, orderId);
    }

    @SuppressWarnings("unchecked")
    public List<Order> getByDatePeriod(String startDate, String endDate, int from, int limit) throws DAOException {
        return executeQuery(FIND_ORDER_BY_RECEIPT_DATE, startDate, endDate, from, limit);
    }

    @SuppressWarnings("unchecked")
    public List<Order> getByPayStatus(String payStatus, int from, int limit) throws DAOException {
        return executeQuery(FIND_ORDERS_BY_PAY_STATUS, payStatus, from, limit);
    }

    @SuppressWarnings("unchecked")
    public List<Order> getByPayStatusAndReceiptDate(String startDate, String endDate,
                                                    String payStatus, int from, int limit) throws DAOException {

        return executeQuery(FIND_ORDERS_BY_PAY_STATUS_AND_RECEIPT_DATE, payStatus, startDate,
                endDate, from, limit);
    }

    public int countByParameters(int userId) throws DAOException {
        return countByParameter(COUNT_ORDERS_BY_USER, userId);
    }

    public int countByParameters(String payStatus) throws DAOException {
        return countByParameter(COUNT_ORDERS_BY_PAY_STATUS, payStatus);
    }

    public int countByParameters() throws DAOException {
        return countByParameter(COUNT_ALL_ORDERS);
    }

    public int countByParameters(String startDate, String endDate, String payStatus) throws DAOException {
        return countByParameter(COUNT_ORDERS_BY_PAY_STATUS_AND_RECEIPT_DATE, payStatus, startDate, endDate);
    }

    public int countByParameters(String startDate, String endDate) throws DAOException {
        return countByParameter(COUNT_ORDER_BY_RECEIPT_DATE, startDate, endDate);
    }

    @Override
    protected AbstractEntity buildEntity(ResultSet resultSet) {
        Order order = null;
        try {
            order = ResultSetConverter.createOrderEntity(resultSet);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return order;
    }
}
