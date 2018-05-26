package com.epam.cafe.service;

import com.epam.cafe.dao.DishDao;
import com.epam.cafe.dao.impl.DaoFactory;
import com.epam.cafe.entity.*;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DishService {

    @SuppressWarnings("unchecked")
    public List<Dish> findDishesByParameters(Category category, Boolean enableStatus, int from, int limit)
            throws ServiceException {
        List<Dish> dishList = new ArrayList<>();

        try (DaoFactory daoFactory = new DaoFactory()){
            DishDao dishDaoImpl = daoFactory.getDishDao();

            if(category == null && enableStatus == null) {
                dishList = dishDaoImpl.getAll(from, limit);
            }

            if(category != null && enableStatus != null) {
                dishList = dishDaoImpl.getByCategoryAndEnableStatus(category, enableStatus, from, limit);
            }

            if(category != null && enableStatus == null) {
                dishList = dishDaoImpl.getByCategory(category, from, limit);
            }

            if(category == null && enableStatus != null) {
                dishList = dishDaoImpl.getByEnableStatus(enableStatus, from, limit);
            }
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching dish by parameters: ", e);
        }

        return dishList;
    }

    public List<Dish> getMenu(Category category, int from, int limit) throws ServiceException {
        List<Dish> dishList;

        try (DaoFactory daoFactory = new DaoFactory()){
            DishDao dishDaoImpl = daoFactory.getDishDao();

            if(category == null) {
                dishList = dishDaoImpl.getByEnableStatus(true, from, limit);
            } else {
                dishList = dishDaoImpl.getByCategoryAndEnableStatus(category,true, from, limit);
            }
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during attempt to get menu: ",e);
        }

        return dishList;
    }

    public boolean addDish(Dish dish) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        DishDao dishDaoImpl = daoFactory.getDishDao();

        Dish addedDish;
        try {
            addedDish = dishDaoImpl.create(dish);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during addition a new dish", e);
        } finally {
            daoFactory.endTransaction();
        }
        return addedDish != null;
    }

    public void addDishesInOrder(Order order, Map<Dish, Integer> dishes) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        DishDao dishDaoImpl = daoFactory.getDishDao();

        int orderId = order.getId();
        try {
            for (Dish dish : dishes.keySet()) {
                int amount = dishes.get(dish);
                int dishId = dish.getId();

                dishDaoImpl.addDishInOrder(orderId, dishId, amount);
            }
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during addition a new dish to order :", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public Map<Dish, Integer> findDishesByOrder(Order order) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            DishDao dishDaoImpl = daoFactory.getDishDao();
            return dishDaoImpl.getByOrder(order);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching all dishes by order: ", e);
        }
    }

    public void addToBasket(User user, int dishId) throws ServiceException {
        Dish dish;

        try (DaoFactory daoFactory = new DaoFactory()){
            DishDao dishDaoImpl = daoFactory.getDishDao();
            dish = dishDaoImpl.getById(dishId);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during addition dish to the Basket: ", e);
        }

        ShopBasket shopBasket = user.getShopBasket();
        shopBasket.addDish(dish);
    }

    public void removeFromBasket(User user, String dishId) throws ServiceException {
        Dish dish;
        try (DaoFactory daoFactory = new DaoFactory()){
            DishDao dishDaoImpl = daoFactory.getDishDao();
            dish = dishDaoImpl.getById(Integer.parseInt(dishId));
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during removing dish from Basket: ", e);
        }

        ShopBasket shopBasket = user.getShopBasket();
        shopBasket.removeDish(dish);
    }

    public void updateEnableStatus(Boolean newEnableStatus, int dishId) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        DishDao dishDaoImpl = daoFactory.getDishDao();

        try {
            dishDaoImpl.updateEnableStatusByDishId(newEnableStatus, dishId);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during attempt to update enable status: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public int countDishesByParameters(Category category, Boolean enableStatus) throws ServiceException {
        int numberOfDishes = 0;

        try (DaoFactory daoFactory = new DaoFactory()){
            DishDao dishDaoImpl = daoFactory.getDishDao();

            if(category == null && enableStatus == null) {
                numberOfDishes = dishDaoImpl.countNumberOfRecords();
            }

            if(category != null && enableStatus != null) {
                numberOfDishes = dishDaoImpl.countNumberOfRecords(category, enableStatus);
            }

            if(category != null && enableStatus == null) {
                numberOfDishes = dishDaoImpl.countNumberOfRecords(category);
            }

            if(category == null && enableStatus != null) {
                numberOfDishes = dishDaoImpl.countNumberOfRecords(enableStatus);
            }
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during calculation dishes by parameters: ", e);
        }

        return numberOfDishes;
    }
}