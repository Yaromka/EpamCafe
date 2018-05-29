package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.DishBuilder;
import com.epam.cafe.builder.EntityBuilder;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.DishDao;
import com.epam.cafe.entity.Category;
import com.epam.cafe.entity.Dish;
import com.epam.cafe.entity.Order;
import com.epam.cafe.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    /**
     * Add in DAO new dish.
     * @param dish object that should be inserted.
     */
    public Dish create(Dish dish) throws DAOException {
        int dishId = createEntityAndGetId(INSERT_NEW_DISH, dish.getName(), dish.getDescription(), dish.getWeight(),
                dish.getPicture(), dish.getPrice(), dish.getCategory().getId());

        dish.setId(dishId);
        return dish;
    }

    /**
     * Returns concrete dish from DAO by id.
     * @param id primary key from the table.
     */
    public Dish getById(int id) throws DAOException {
        return getSingleByParameters(GET_BY_ID, id);
    }

    /**
     * Returns all dishes from table.
     * @param args query, pagination info and enable status.
     */
    public List<Dish> getAll(Object... args) throws DAOException {
        return executeQuery(GET_ALL, args);
    }

    /**
     * Returns all dishes from table by parameters.
     * @param category contains particular dishes.
     * @param enableStatus does dish added in stock or not.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    public List<Dish> getByCategoryAndEnableStatus(Category category, Boolean enableStatus, int from, int limit)
            throws DAOException {

        int enableStatusForDataBase = enableStatus ? 1 : 0;
        int categoryId = category.getId();

        return executeQuery(GET_BY_CATEGORY_AND_ENABLE_STATUS, enableStatusForDataBase,
                categoryId, from, limit);
    }

    /**
     * Returns all dishes from table by parameters.
     * @param category contains particular dishes.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    public List<Dish> getByCategory(Category category, int from, int limit) throws DAOException {
        int categoryId = category.getId();
        return executeQuery(GET_BY_CATEGORY, categoryId, from, limit);
    }

    /**
     * Returns all dishes from table by parameters.
     * @param enableStatus does dish added in stock or not.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    public List<Dish> getByEnableStatus(Boolean enableStatus, int from, int limit) throws DAOException {
        int statusForDataBase = enableStatus ? 1 : 0;
        return executeQuery(GET_ALL_BY_ENABLE_STATUS, statusForDataBase, from, limit);
    }

    /**
     * Returns all dishes those contains concrete order.
     * Structured in map due to show quantity of dishes.
     * @param order concrete order with dishes.
     */
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

    /**
     * Add dish in concrete order.
     * @param orderId primary order's key from the table.
     * @param dishId primary dish's key from table.
     * @param number quantity of dishes.
     */
    public void addDishInOrder(int orderId, int dishId, int number) throws DAOException {
        executeUpdate(INSERT_DISH_IN_ORDER, orderId, number, dishId);
    }

    /**
     * Updates enable status of concrete dish in the table.
     * @param dishId primary dish's key from table.
     * @param enableStatus does dish added in stock or not.
     */
    public void updateEnableStatusByDishId(Boolean enableStatus, int dishId) throws DAOException {
        int enableStatusForDataBase = enableStatus ? 1 : 0;
        executeUpdate(UPDATE_DISH_ENABLE_STATUS_BY_DISH_ID, enableStatusForDataBase, dishId);
    }

    /**
     * Returns whole quantity of dishes.
     */
    public int countNumberOfRecords() throws DAOException {
        return countByParameter(COUNT_ALL);
    }

    /**
     * Returns quantity of dishes by enable status.
     * @param enableStatus does dish added in stock or not.
     */
    public int countNumberOfRecords(Boolean enableStatus) throws DAOException {
        int enableStatusForDb = enableStatus? 1: 0;
        return countByParameter(COUNT_DISHES_BY_ENABLE_STATUS, enableStatusForDb);
    }

    /**
     * Returns quantity of dishes in particular category by enable status.
     * @param category concrete category that contains dishes.
     * @param enableStatus does dish added in stock or not.
     */
    public int countNumberOfRecords(Category category, Boolean enableStatus) throws  DAOException {
        int enableStatusForDb = enableStatus? 1: 0;
        return countByParameter(COUNT_DISHES_BY_CATEGORY_AND_ENABLE_STATUS, enableStatusForDb, category.getId());
    }

    /**
     * Returns quantity of dishes in particular category.
     * @param category concrete category that contains dishes.
     */
    public int countNumberOfRecords(Category category) throws DAOException {
        return countByParameter(COUNT_DISHES_BY_CATEGORY, category.getId());
    }

    /**
     * Returns dish with all fields filled.
     * @param resultSet that has all information about dish.
     */
    @Override
    public Dish buildEntity(ResultSet resultSet) {
        Dish dish = null;
        try {
            dish = (Dish) getBuilder().createEntity(resultSet);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return dish;
    }

    @Override
    protected EntityBuilder getBuilder() {
        return new DishBuilder();
    }
}