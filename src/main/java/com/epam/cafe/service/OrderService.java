package com.epam.cafe.service;

import com.epam.cafe.dao.OrderDao;
import com.epam.cafe.dao.impl.DaoFactory;
import com.epam.cafe.entity.*;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    public Order createOrder(Order order) throws ServiceException {
        PaymentMethod paymentMethod = order.getPaymentMethod();
        User user = order.getUser();
        if(PaymentMethod.CLIENTBILL == paymentMethod && !isEnoughMoneyToPay(user)) {
            return null;
        }

        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        OrderDao orderDaoImpl = daoFactory.getOrderDao();


        try {
            order = orderDaoImpl.create(order);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during creation a new order: ", e);
        } finally {
            daoFactory.endTransaction();
        }

        return order;
    }

    public List<Order> findOrdersByUserId(int id, int from, int limit) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            OrderDao orderDaoImpl = daoFactory.getOrderDao();
            return orderDaoImpl.getByUserId(id, from, limit);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching all orders by particular user: ", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Order> findOrdersByParameters(String startDate, String endDate, String payStatus,
                                              int from, int limit) throws ServiceException {

        List<Order> orderList = new ArrayList<>();

        try (DaoFactory daoFactory = new DaoFactory()){
            OrderDao orderDaoImpl = daoFactory.getOrderDao();

            if(startDate == null && payStatus == null) {
                orderList = orderDaoImpl.getAll(from, limit);
            }

            if(startDate != null && payStatus != null) {
                orderList = orderDaoImpl.getByPayStatusAndReceiptDate(startDate, endDate, payStatus, from, limit);
            }

            if(startDate != null && payStatus == null) {
                orderList = orderDaoImpl.getByDatePeriod(startDate, endDate, from, limit);
            }

            if(startDate == null && payStatus != null) {
                orderList = orderDaoImpl.getByPayStatus(payStatus, from, limit);
            }
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching all orders by parameters: ", e);
        }

        return orderList;
    }

    public void updatePaidStatus(Order order, OrderStatus status) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        OrderDao orderDaoImpl = daoFactory.getOrderDao();

        try {
            orderDaoImpl.updateOrderPaidStatus(order, status);
            order.setOrderStatus(status);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during attempt to update order payment status: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public void addReview(int orderId, int mark, String comment) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        OrderDao orderDaoImpl = daoFactory.getOrderDao();

        try {
            orderDaoImpl.addOrderReview(orderId, mark, comment);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during addition review: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public int countAllUserOrders(int userId) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            OrderDao orderDaoImpl = daoFactory.getOrderDao();
            return orderDaoImpl.countByParameters(userId);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during calculation all orders made by user: ", e);
        }
    }

    public int countOrdersByParameters(String startDate, String endDate, String payStatus) throws ServiceException {
        int numberOfOrders = 0;

        try (DaoFactory daoFactory = new DaoFactory()){
            OrderDao orderDaoImpl = daoFactory.getOrderDao();

            if(startDate == null && payStatus == null) {
                numberOfOrders = orderDaoImpl.countByParameters();
            }

            if(startDate != null && payStatus != null) {
                numberOfOrders = orderDaoImpl.countByParameters(startDate, endDate, payStatus);
            }

            if(startDate != null && payStatus == null) {
                numberOfOrders = orderDaoImpl.countByParameters(startDate, endDate);
            }

            if(startDate == null && payStatus != null) {
                numberOfOrders = orderDaoImpl.countByParameters(payStatus);
            }
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during attempt to calculate all orders by parameters: ", e);
        }

        return numberOfOrders;
    }

    private boolean isEnoughMoneyToPay(User user) {
        long userMoney = user.getMoney();
        long userLoyaltyPoints = user.getLoyaltyPoints();
        ShopBasket shopBasket = user.getShopBasket();
        long orderPrice = shopBasket.getBasketPrice();

        return orderPrice < (userMoney + userLoyaltyPoints);
    }
}