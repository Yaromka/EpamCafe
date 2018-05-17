package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.EntityBuilder;
import com.epam.cafe.builder.OrderBuilder;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.AbstractDao;
import com.epam.cafe.dao.OrderDao;
import com.epam.cafe.util.ResultSetConverter;
import com.epam.cafe.entity.Order;
import com.epam.cafe.entity.OrderStatus;
import com.epam.cafe.entity.PaymentMethod;
import com.epam.cafe.entity.User;
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

    private ConnectionProxy connection;

    public ConnectionProxy getConnection() {
        return connection;
    }

    public void setConnection(ConnectionProxy connection) {
        this.connection = connection;
    }

    public OrderDaoImpl(ConnectionProxy connection) {
        this.connection = connection;
    }

    public Order create(Order order) throws DAOException {
        Date orderDate = order.getDate();
        String orderDateString = DateTimeConverter.convertDateTimeToString(orderDate);

        PaymentMethod payMethod = order.getPaymentMethod();
        String paymentMethod = payMethod.toString();

        User user = order.getUser();
        Integer userId = user.getId();


        String sqlStatement = INSERT_NEW_ORDER;
        if(order.getPaymentMethod() == PaymentMethod.CLIENTBILL) {
            sqlStatement = INSERT_NEW_PAYED_ORDER;
        }
        Integer autoIncrementedOrderId;
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)){

            statement.setString(FIRST_INDEX, orderDateString);
            statement.setString(SECOND_INDEX, paymentMethod);
            statement.setInt(THIRD_INDEX, userId);

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            autoIncrementedOrderId = rs.getInt(FIRST_INDEX);

        } catch (SQLException e) {
            throw new DAOException("An exception occurred during addition new order: ", e);
        }
        order.setId(autoIncrementedOrderId);
        return order;
    }


    public List<Order> getAll(int from, int limit) throws DAOException {
        List<Order> orders = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS)) {
            statement.setInt(FIRST_INDEX,from);
            statement.setInt(SECOND_INDEX,limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = ResultSetConverter.createOrderEntity(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching all orders: " , e);
        }
        return orders;
    }


    public List<Order> getByUserId(int id, int from, int limit) throws DAOException {
        List<Order> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ORDERS_BY_USER)) {
            statement.setInt(FIRST_INDEX, id);
            statement.setInt(SECOND_INDEX,from);
            statement.setInt(THIRD_INDEX,limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = ResultSetConverter.createOrderEntity(resultSet);
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching orders by user: ", e);
        }
        return list;
    }


    public void updateOrderPaidStatus(Order order, OrderStatus status) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PAID_STATUS)) {
            statement.setString(FIRST_INDEX, status.toString());
            statement.setInt(SECOND_INDEX, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("An exception occurred attempted update of paid status: ", e);
        }
    }


    public void addOrderReview(int orderId, int mark, String comment) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_REVIEW)) {
            statement.setInt(FIRST_INDEX,mark);
            statement.setString(SECOND_INDEX,comment);
            statement.setInt(THIRD_INDEX,orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during attempted update of order review: ", e);
        }
    }


    public List<Order> getByDatePeriod(String startDate, String endDate, int from, int limit) throws DAOException {
        List<Order> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_RECEIPT_DATE)) {
            statement.setString(FIRST_INDEX, startDate);
            statement.setString(SECOND_INDEX, endDate);
            statement.setInt(THIRD_INDEX, from);
            statement.setInt(FOURTH_INDEX, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order  order = ResultSetConverter.createOrderEntity(resultSet);
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching order by date: ", e);
        }
        return list;
    }


    public List<Order> getByPayStatus(String payStatus, int from, int limit) throws DAOException {
        List<Order> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ORDERS_BY_PAY_STATUS)) {
            statement.setString(FIRST_INDEX, payStatus);
            statement.setInt(SECOND_INDEX, from);
            statement.setInt(THIRD_INDEX,limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order  order = ResultSetConverter.createOrderEntity(resultSet);
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching order by payment status: ", e);
        }
        return list;
    }


    public List<Order> getByPayStatusAndReceiptDate(String startDate, String endDate,
                                                    String payStatus, int from, int limit) throws DAOException {

        List<Order> list  = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(FIND_ORDERS_BY_PAY_STATUS_AND_RECEIPT_DATE)) {
            statement.setString(FIRST_INDEX, payStatus);
            statement.setString(SECOND_INDEX, startDate);
            statement.setString(THIRD_INDEX, endDate);
            statement.setInt(FOURTH_INDEX, from);
            statement.setInt(FIFTH_INDEX, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order  order = ResultSetConverter.createOrderEntity(resultSet);
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching orders by date and payment status: ", e);
        }
        return list;
    }


    public int countByParameters(int userId) throws DAOException {
        int numberOfRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(COUNT_ORDERS_BY_USER)) {
            statement.setInt(FIRST_INDEX,userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting orders by user id: ", e);
        }

        return numberOfRows;
    }


    public int countByParameters(String payStatus) throws DAOException {
        int numberOfRows = 0;
        try(PreparedStatement statement = connection.prepareStatement(COUNT_ORDERS_BY_PAY_STATUS)) {
            statement.setString(FIRST_INDEX,payStatus);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting orders by payment status: ", e);
        }

        return numberOfRows;
    }


    public int countByParameters() throws DAOException {
        int numberOfRows = 0;
        try(PreparedStatement statement = connection.prepareStatement(COUNT_ALL_ORDERS)) {
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting all orders: ", e);
        }

        return numberOfRows;
    }


    public int countByParameters(String startDate, String endDate, String payStatus) throws DAOException {
        int numberOfRows = 0;
        try(PreparedStatement statement = connection.prepareStatement(COUNT_ORDERS_BY_PAY_STATUS_AND_RECEIPT_DATE)) {
            statement.setString(FIRST_INDEX, payStatus);
            statement.setString(SECOND_INDEX, startDate);
            statement.setString(THIRD_INDEX, endDate);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting orders by payment status: ", e);
        }

        return numberOfRows;
    }


    public int countByParameters(String startDate, String endDate) throws DAOException {
        int numberOfRows = 0;
        try(PreparedStatement statement = connection.prepareStatement(COUNT_ORDER_BY_RECEIPT_DATE)) {
            statement.setString(FIRST_INDEX, startDate);
            statement.setString(SECOND_INDEX, endDate);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting orders by date: ", e);
        }

        return numberOfRows;
    }

    @Override
    protected EntityBuilder getBuilder() {
        return new OrderBuilder();
    }
}
