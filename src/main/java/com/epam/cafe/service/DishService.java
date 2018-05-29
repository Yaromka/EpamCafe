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

    /**
     * Returns all dishes from table by parameters.
     * @param category contains particular dishes.
     * @param enableStatus does dish added in stock or not.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
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

    /**
     * Returns dishes from table.
     * @param category contains particular dishes.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
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

    /**
     * Add in DAO new dish.
     * @param dish object that should be inserted.
     */
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

    /**
     * Add dishes in concrete order.
     * @param order dishes should be added there.
     * @param dishes map with dishes and their quantity.
     */
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

    /**
     * Returns all dishes those contains concrete order.
     * Structured in map due to show quantity of dishes.
     * @param order concrete order with dishes.
     */
    public Map<Dish, Integer> findDishesByOrder(Order order) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            DishDao dishDaoImpl = daoFactory.getDishDao();
            return dishDaoImpl.getByOrder(order);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching all dishes by order: ", e);
        }
    }

    /**
     * Add dish in basket by it's id.
     * @param user shop basket that holds current user;
     * @param dishId primary dish's key from table;
     */
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

    /**
     * Remove dish from basket by it's id.
     * @param user shop basket that holds current user;
     * @param dishId primary dish's key from table;
     */
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

    /**
     * Updates enable status of concrete dish in the table.
     * @param dishId primary dish's key from table.
     * @param newEnableStatus does dish added in stock or not.
     */
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

    /**
     * Returns quantity of dishes in particular category by enable status.
     * @param category concrete category that contains dishes.
     * @param enableStatus does dish added in stock or not.
     */
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