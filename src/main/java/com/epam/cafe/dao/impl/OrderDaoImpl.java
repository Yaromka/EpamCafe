package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.EntityBuilder;
import com.epam.cafe.builder.OrderBuilder;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.OrderDao;
import com.epam.cafe.entity.*;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.util.DateTimeConverter;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;


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

    /**
     * Add in DAO new order.
     * @param order object that should be inserted.
     */
    @Override
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

        int orderId = createEntityAndGetId(sqlStatement, orderDateString, paymentMethod, userId);
        order.setId(orderId);
        return order;
    }

    /**
     * Returns all orders from table by parameters.
     * @param args query, pagination info, start and end date.
     */
    @SuppressWarnings("unchecked")
    public List<Order> getAll(Object... args) throws DAOException {
        return executeQuery(FIND_ALL_ORDERS, args);
    }

    /**
     * Returns all orders made by concrete user.
     * @param id primary key from users table.
     * @param from start pagination position.
     * @param limit positions per page.
     */
    @SuppressWarnings("unchecked")
    public List<Order> getByUserId(int id, int from, int limit) throws DAOException {
        return executeQuery(FIND_ORDERS_BY_USER, id, from, limit);
    }

    /**
     * Updates paid status of concrete order in the table.
     * @param order concrete order.
     * @param status does order paid, canceled or violated
     */
    public void updateOrderPaidStatus(Order order, OrderStatus status) throws DAOException {
        executeUpdate(UPDATE_PAID_STATUS, status.toString(), order.getId());
    }

    /**
     * Add review about concrete order.
     * @param orderId primary order's key from the table.
     * @param mark mark that user leaves for order.
     * @param comment massage that explains mark.
     */
    public void addOrderReview(int orderId, int mark, String comment) throws DAOException {
        executeUpdate(UPDATE_REVIEW, mark, comment, orderId);
    }

    /**
     * Returns all orders those receipt date is in concrete time frame.
     * @param startDate first receipt date border.
     * @param endDate second receipt date border.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    @SuppressWarnings("unchecked")
    public List<Order> getByDatePeriod(String startDate, String endDate, int from, int limit) throws DAOException {
        return executeQuery(FIND_ORDER_BY_RECEIPT_DATE, startDate, endDate, from, limit);
    }

    /**
     * Returns all orders with the same pay status.
     * @param payStatus could be payed, cancelled, violated or expected.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    @SuppressWarnings("unchecked")
    public List<Order> getByPayStatus(String payStatus, int from, int limit) throws DAOException {
        return executeQuery(FIND_ORDERS_BY_PAY_STATUS, payStatus, from, limit);
    }

    /**
     * Returns all orders those receipt date is in concrete time frame.
     * @param startDate first receipt date border.
     * @param endDate second receipt date border.
     * @param payStatus could be payed, cancelled, violated or expected.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    @SuppressWarnings("unchecked")
    public List<Order> getByPayStatusAndReceiptDate(String startDate, String endDate,
                                                    String payStatus, int from, int limit) throws DAOException {

        return executeQuery(FIND_ORDERS_BY_PAY_STATUS_AND_RECEIPT_DATE, payStatus, startDate,
                endDate, from, limit);
    }

    /**
     * Count orders made by concrete user.
     * @param userId primary user's key from table.
     */
    public int countByParameters(int userId) throws DAOException {
        return countByParameter(COUNT_ORDERS_BY_USER, userId);
    }

    /**
     * Count orders by pay status.
     * @param payStatus could be payed, cancelled, violated or expected.
     */
    public int countByParameters(String payStatus) throws DAOException {
        return countByParameter(COUNT_ORDERS_BY_PAY_STATUS, payStatus);
    }

    /**
     * Count all orders.
     */
    public int countByParameters() throws DAOException {
        return countByParameter(COUNT_ALL_ORDERS);
    }

    /**
     * Count orders by receipt date period and pay status.
     * @param startDate first receipt date border.
     * @param endDate second receipt date border.
     * @param payStatus could be payed, cancelled, violated or expected.
     */
    public int countByParameters(String startDate, String endDate, String payStatus) throws DAOException {
        return countByParameter(COUNT_ORDERS_BY_PAY_STATUS_AND_RECEIPT_DATE, payStatus, startDate, endDate);
    }

    /**
     * Count orders by receipt date period.
     * @param startDate first receipt date border.
     * @param endDate second receipt date border.
     */
    public int countByParameters(String startDate, String endDate) throws DAOException {
        return countByParameter(COUNT_ORDER_BY_RECEIPT_DATE, startDate, endDate);
    }

    /**
     * Returns order with all fields filled.
     * @param resultSet that has all information about order.
     */
    @Override
    public AbstractEntity buildEntity(ResultSet resultSet) {
        Order order = null;
        try {
            order = (Order) getBuilder().createEntity(resultSet);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    protected EntityBuilder getBuilder() {
        return new OrderBuilder();
    }
}
