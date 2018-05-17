package com.epam.cafe.dao;

import com.epam.cafe.entity.Order;
import com.epam.cafe.entity.OrderStatus;
import com.epam.cafe.exception.DAOException;

import java.util.List;

public interface OrderDao extends Dao{
    Order create(Order order) throws DAOException;

    List<Order> getByUserId(int id, int from, int limit) throws DAOException;

    List<Order> getByPayStatusAndReceiptDate(String startDate, String endDate, String payStatus, int from, int limit) throws DAOException;

    List<Order> getByPayStatus(String payStatus, int from, int limit) throws DAOException;

    List<Order> getAll(int from, int limit) throws DAOException;

    List<Order> getByDatePeriod(String startDate, String endDate, int from, int limit) throws DAOException;

    void addOrderReview(int orderId, int mark, String comment) throws DAOException;

    void updateOrderPaidStatus(Order order, OrderStatus status) throws DAOException;

    int countByParameters() throws DAOException;

    int countByParameters(int userId) throws DAOException;

    int countByParameters(String startDate, String endDate, String payStatus) throws DAOException;

    int countByParameters(String startDate, String endDate) throws DAOException;

    int countByParameters(String payStatus) throws DAOException;
}