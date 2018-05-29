package com.epam.cafe.dao;

import com.epam.cafe.entity.Category;
import com.epam.cafe.entity.Dish;
import com.epam.cafe.entity.Order;
import com.epam.cafe.exception.DAOException;

import java.util.List;
import java.util.Map;

public interface DishDao extends Dao{

    /**
     * Add in DAO new dish.
     * @param dish object that should be inserted.
     */
    Dish create(Dish dish) throws DAOException;

    /**
     * Returns concrete dish from DAO by id.
     * @param id primary key from the table.
     */
    Dish getById(int id) throws DAOException;

    /**
     * Returns all dishes from table by parameters.
     * @param category contains particular dishes.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    List<Dish> getByCategory(Category category, int from, int limit) throws DAOException;

    /**
     * Returns all dishes from table by parameters.
     * @param enableStatus does dish added in stock or not.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    List<Dish> getByEnableStatus(Boolean enableStatus, int from, int limit) throws DAOException;

    /**
     * Returns all dishes from table by parameters.
     * @param category contains particular dishes.
     * @param enableStatus does dish added in stock or not.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    List<Dish> getByCategoryAndEnableStatus(Category category, Boolean enableStatus, int from, int limit) throws DAOException;

    /**
     * Returns all dishes those contains concrete order.
     * Structured in map due to show quantity of dishes.
     * @param order concrete order with dishes.
     */
    Map<Dish, Integer> getByOrder(Order order) throws DAOException;

    /**
     * Add dish in concrete order.
     * @param orderId primary order's key from the table.
     * @param dishId primary dish's key from table.
     * @param amount quantity of dishes.
     */
    void addDishInOrder(int orderId, int dishId, int amount) throws DAOException;

    /**
     * Updates enable status of concrete dish in the table.
     * @param dishId primary dish's key from table.
     * @param enableStatus does dish added in stock or not.
     */
    void updateEnableStatusByDishId(Boolean enableStatus, int dishId) throws DAOException;

    /**
     * Returns whole quantity of dishes.
     */
    int countNumberOfRecords() throws DAOException;

    /**
     * Returns quantity of dishes in particular category.
     * @param category concrete category that contains dishes.
     */
    int countNumberOfRecords(Category category) throws DAOException;

    /**
     * Returns quantity of dishes in particular category by enable status.
     * @param category concrete category that contains dishes.
     * @param enableStatus does dish added in stock or not.
     */
    int countNumberOfRecords(Category category, Boolean enableStatus) throws  DAOException;

    /**
     * Returns quantity of dishes by enable status.
     * @param enableStatus does dish added in stock or not.
     */
    int countNumberOfRecords(Boolean enableStatus) throws DAOException;
}