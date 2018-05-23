package com.epam.cafe.dao.impl;

import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.AbstractDao;
import com.epam.cafe.dao.DishDao;
import com.epam.cafe.util.ResultSetConverter;
import com.epam.cafe.entity.Category;
import com.epam.cafe.entity.Dish;
import com.epam.cafe.entity.Order;
import com.epam.cafe.exception.DAOException;
import com.mysql.jdbc.Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.cafe.constants.ParameterIndexes.*;

public class DishDaoImpl extends AbstractDao<Dish> implements DishDao{
    private static final String GET_ALL = "SELECT * FROM dishes limit ?,?";
    private static final String COUNT_ALL = "SELECT count(*) FROM dishes";
    private static final String GET_ALL_BY_ENABLE_STATUS = "SELECT * FROM dishes WHERE dish_isEnable = ? limit ?,?";
    private static final String COUNT_DISHES_BY_ENABLE_STATUS = "SELECT count(*) FROM dishes WHERE dish_isEnable = ? ";
    private static final String GET_BY_CATEGORY_AND_ENABLE_STATUS =
            "SELECT * FROM dishes WHERE dish_isEnable = ? AND categories_category_id = ? limit ?,?";
    private static final String COUNT_DISHES_BY_CATEGORY_AND_ENABLE_STATUS =
            "SELECT count(*) FROM dishes WHERE dish_isEnable = ? AND categories_category_id = ? ";
    private static final String GET_BY_CATEGORY = "SELECT * FROM dishes WHERE categories_category_id = ? limit ?,?";
    private static final String COUNT_DISHES_BY_CATEGORY = "SELECT count(*) FROM dishes WHERE categories_category_id = ?";
    private static final String GET_BY_ORDER_ID = "SELECT * FROM orders_has_dishes WHERE orders_order_id = ?";
    private static final String GET_BY_ID = "SELECT * FROM dishes WHERE dish_id = ?";
    private static final String INSERT_NEW_DISH =
            "INSERT INTO dishes (dish_name,dish_description,dish_weight,dish_picture,dish_price,categories_category_id) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
    private static final String INSERT_DISH_IN_ORDER =
            "INSERT INTO orders_has_dishes(orders_order_id, orders_has_dishes_quantity, dishes_dish_id) VALUES(?,?,?)";
    private static final String UPDATE_DISH_ENABLE_STATUS_BY_DISH_ID = "UPDATE dishes SET dish_isEnable = ? WHERE dish_id = ?";

    public DishDaoImpl(ConnectionProxy connection) {
        super.setConnection(connection);
    }

    public Dish create(Dish dish) throws DAOException{
        dish.setId(getInsertId(INSERT_NEW_DISH, dish.getName(), dish.getDescription(), dish.getWeight(),
                dish.getPicture(), dish.getPrice(), dish.getCategory().getId()));
        return dish;
    }

    public Dish getById(int id) throws DAOException {
        return getSingleByParameter(GET_BY_ID, id);
    }

    public List<Dish> getAll(int from, int limit) throws DAOException {
        return executeQuery(GET_ALL, from, limit);
    }

    public List<Dish> getByCategoryAndEnableStatus(Category category, Boolean enableStatus, int from, int limit)
            throws DAOException {

        int enableStatusForDataBase = enableStatus ? 1 : 0;
        int categoryId = category.getId();

        return executeQuery(GET_BY_CATEGORY_AND_ENABLE_STATUS, enableStatusForDataBase,
                categoryId, from, limit);
    }

    public List<Dish> getByCategory(Category category, int from, int limit) throws DAOException {
        int categoryId = category.getId();
        return executeQuery(GET_BY_CATEGORY, categoryId, from, limit);
    }

    public List<Dish> getByEnableStatus(Boolean enableStatus, int from, int limit) throws DAOException {
        int statusForDataBase = enableStatus ? 1 : 0;
        return executeQuery(GET_ALL_BY_ENABLE_STATUS, statusForDataBase, from, limit);
    }

    public Map<Dish, Integer> getByOrder(Order order) throws DAOException {
        Map<Dish, Integer> dishes = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_ORDER_ID)) {
            statement.setInt(1, order.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishId = resultSet.getInt(3);
                Dish dish = getById(dishId);
                int quantity = resultSet.getInt(2);
                dishes.put(dish, quantity);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching dishes by order id: ", e);
        }
        return dishes;
    }

    public void addDishInOrder(int orderId, int dishId, int number) throws DAOException {
        executeUpdate(INSERT_DISH_IN_ORDER, orderId, number, dishId);
    }

    public void updateEnableStatusByDishId(Boolean enableStatus, int dishId) throws DAOException {
        int enableStatusForDataBase = enableStatus ? 1 : 0;
        executeUpdate(UPDATE_DISH_ENABLE_STATUS_BY_DISH_ID, enableStatusForDataBase, dishId);
    }

    public int countNumberOfRecords() throws DAOException {
        return countByParameter(COUNT_ALL);
    }

    public int countNumberOfRecords(Boolean enableStatus) throws DAOException {
        int enableStatusForDb = enableStatus? 1: 0;
        return countByParameter(COUNT_DISHES_BY_ENABLE_STATUS, enableStatusForDb);
    }

    public int countNumberOfRecords(Category category, Boolean enableStatus) throws  DAOException {
        int enableStatusForDb = enableStatus? 1: 0;
        return countByParameter(COUNT_DISHES_BY_CATEGORY_AND_ENABLE_STATUS, enableStatusForDb, category.getId());
    }

    public int countNumberOfRecords(Category category) throws DAOException {
        return countByParameter(COUNT_DISHES_BY_CATEGORY, category.getId());
    }

    @Override
    protected Dish buildEntity(ResultSet resultSet) {
        Dish dish = null;
        try {
            dish = ResultSetConverter.createDishEntity(resultSet);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return dish;
    }
}