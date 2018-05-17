package com.epam.cafe.dao;

import com.epam.cafe.entity.Category;
import com.epam.cafe.entity.Dish;
import com.epam.cafe.entity.Order;
import com.epam.cafe.exception.DAOException;

import java.util.List;
import java.util.Map;

public interface DishDao extends Dao{
    Dish create(Dish dish) throws DAOException;

    Dish getById(int id) throws DAOException;

    List<Dish> getAll(int from, int limit) throws DAOException;

    List<Dish> getByCategory(Category category, int from, int limit) throws DAOException;

    List<Dish> getByEnableStatus(Boolean enableStatus, int from, int limit) throws DAOException;

    List<Dish> getByCategoryAndEnableStatus(Category category, Boolean enableStatus, int from, int limit) throws DAOException;

    Map<Dish, Integer> getByOrder(Order order) throws DAOException;

    void addDishInOrder(int orderId, int dishId, int amount) throws DAOException;

    void updateEnableStatusByDishId(Boolean enableStatus, int dishId) throws DAOException;

    int countNumberOfRecords() throws DAOException;

    int countNumberOfRecords(Category category) throws DAOException;

    int countNumberOfRecords(Category category, Boolean enableStatus) throws  DAOException;

    int countNumberOfRecords(Boolean enableStatus) throws DAOException;
}