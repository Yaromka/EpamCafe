package com.epam.cafe.dao;

import com.epam.cafe.entity.Order;
import com.epam.cafe.entity.OrderStatus;
import com.epam.cafe.exception.DAOException;

import java.util.List;

public interface OrderDao extends Dao{

    /**
     * Add in DAO new order.
     * @param order object that should be inserted.
     */
    Order create(Order order) throws DAOException;

    /**
     * Returns all orders made by concrete user.
     * @param id primary key from users table.
     * @param from start pagination position.
     * @param limit positions per page.
     */
    List<Order> getByUserId(int id, int from, int limit) throws DAOException;

    /**
     * Returns all orders those receipt date is in concrete time frame.
     * @param startDate first receipt date border.
     * @param endDate second receipt date border.
     * @param payStatus could be payed, cancelled, violated or expected.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    List<Order> getByPayStatusAndReceiptDate(String startDate, String endDate, String payStatus, int from, int limit) throws DAOException;

    /**
     * Returns all orders with the same pay status.
     * @param payStatus could be payed, cancelled, violated or expected.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    List<Order> getByPayStatus(String payStatus, int from, int limit) throws DAOException;

    /**
     * Returns all orders those receipt date is in concrete time frame.
     * @param startDate first receipt date border.
     * @param endDate second receipt date border.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    List<Order> getByDatePeriod(String startDate, String endDate, int from, int limit) throws DAOException;

    /**
     * Add review about concrete order.
     * @param orderId primary order's key from the table.
     * @param mark mark that user leaves for order.
     * @param comment massage that explains mark.
     */
    void addOrderReview(int orderId, int mark, String comment) throws DAOException;

    /**
     * Updates paid status of concrete order in the table.
     * @param order concrete order.
     * @param status does order paid, canceled or violated
     */
    void updateOrderPaidStatus(Order order, OrderStatus status) throws DAOException;

    /**
     * Count all orders.
     */
    int countByParameters() throws DAOException;

    /**
     * Count orders made by concrete user.
     * @param userId primary user's key from table.
     */
    int countByParameters(int userId) throws DAOException;

    /**
     * Count orders by receipt date period and pay status.
     * @param startDate first receipt date border.
     * @param endDate second receipt date border.
     * @param payStatus could be payed, cancelled, violated or expected.
     */
    int countByParameters(String startDate, String endDate, String payStatus) throws DAOException;

    /**
     * Count orders by receipt date period.
     * @param startDate first receipt date border.
     * @param endDate second receipt date border.
     */
    int countByParameters(String startDate, String endDate) throws DAOException;

    /**
     * Count orders by pay status.
     * @param payStatus could be payed, cancelled, violated or expected.
     */
    int countByParameters(String payStatus) throws DAOException;
}