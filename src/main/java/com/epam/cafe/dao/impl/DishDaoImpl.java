package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.DishBuilder;
import com.epam.cafe.builder.EntityBuilder;
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

    private ConnectionProxy connection;

    public ConnectionProxy getConnection() {
        return connection;
    }

    public void setConnection(ConnectionProxy connection) {
        this.connection = connection;
    }

    public DishDaoImpl(ConnectionProxy connection) {
        this.connection = connection;
    }

    public Dish create(Dish dish) throws DAOException{
        Integer autoIncrementedDishId;

        // executeUpdate(INSERT_NEW_DISH, dish.getName(),

        try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_DISH,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(FIRST_INDEX, dish.getName());
            statement.setString(SECOND_INDEX, dish.getDescription());
            statement.setInt(THIRD_INDEX, dish.getWeight());
            statement.setString(FOURTH_INDEX, dish.getPicture());
            statement.setLong(FIFTH_INDEX, dish.getPrice());
            statement.setInt(SIXTH_INDEX, dish.getCategory().getId());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            autoIncrementedDishId = rs.getInt(FIRST_INDEX);

        } catch (SQLException e) {
            throw new DAOException("An exception occurred during addition new dish: ", e);
        }
        dish.setId(autoIncrementedDishId);
        return dish;
    }


    public Dish getById(int id) throws DAOException {

        //return getAll(GET_BY_ID, id);

        Dish dish = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setInt(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = ResultSetConverter.createDishEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching dish by id: ", e);
        }
        return dish;
    }


    public List<Dish> getAll(int from, int limit) throws DAOException {
        List<Dish> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            statement.setInt(FIRST_INDEX, from);
            statement.setInt(SECOND_INDEX, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = ResultSetConverter.createDishEntity(resultSet);
                list.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching all dishes: ", e);
        }
        return list;
    }


    public List<Dish> getByCategoryAndEnableStatus(Category category, Boolean enableStatus, int from, int limit)
            throws DAOException {

        int enableStatusForDataBase = enableStatus ? 1 : 0;

        List<Dish> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_CATEGORY_AND_ENABLE_STATUS)) {
            Integer id = category.getId();
            statement.setInt(FIRST_INDEX, enableStatusForDataBase);
            statement.setInt(SECOND_INDEX, id);
            statement.setInt(THIRD_INDEX, from);
            statement.setInt(FOURTH_INDEX, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = ResultSetConverter.createDishEntity(resultSet);
                list.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching dish by category and enable status: ", e);
        }
        return list;
    }


    public List<Dish> getByCategory(Category category, int from, int limit) throws DAOException {
        List<Dish> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_CATEGORY)) {
            Integer categoryId = category.getId();
            statement.setInt(FIRST_INDEX, categoryId);
            statement.setInt(SECOND_INDEX, from);
            statement.setInt(THIRD_INDEX, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = ResultSetConverter.createDishEntity(resultSet);
                list.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching dish by category: ", e);
        }
        return list;
    }


    public List<Dish> getByEnableStatus(Boolean enableStatus, int from, int limit) throws DAOException {
        int statusForDataBase = enableStatus ? 1 : 0;

        List<Dish> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_ENABLE_STATUS)) {
            statement.setInt(FIRST_INDEX, statusForDataBase);
            statement.setInt(SECOND_INDEX, from);
            statement.setInt(THIRD_INDEX, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = ResultSetConverter.createDishEntity(resultSet);
                list.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching all dishes by enable status: ", e);
        }

        return list;
    }


    public Map<Dish, Integer> getByOrder(Order order) throws DAOException {
        Map<Dish, Integer> dishes = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_ORDER_ID)) {
            statement.setInt(FIRST_INDEX, order.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dishId = resultSet.getInt(THIRD_INDEX);
                Dish dish = getById(dishId);
                int quantity = resultSet.getInt(SECOND_INDEX);
                dishes.put(dish, quantity);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching dishes by order id: ", e);
        }
        return dishes;
    }


    public void addDishInOrder(int orderId, int dishId, int number) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DISH_IN_ORDER)) {
            statement.setInt(FIRST_INDEX, orderId);
            statement.setInt(SECOND_INDEX, number);
            statement.setInt(THIRD_INDEX, dishId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during addition dish in the order: ", e);
        }
    }


    public void updateEnableStatusByDishId(Boolean enableStatus, int dishId) throws DAOException {
        int enableStatusForDataBase = enableStatus ? 1 : 0;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DISH_ENABLE_STATUS_BY_DISH_ID)) {
            statement.setInt(FIRST_INDEX, enableStatusForDataBase);
            statement.setInt(SECOND_INDEX, dishId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during updating dish archive status: ", e);
        }
    }


    public int countNumberOfRecords() throws DAOException {
        int numberOfRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(COUNT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting all dishes: ", e);
        }

        return numberOfRows;
    }


    public int countNumberOfRecords(Boolean enableStatus) throws DAOException {
        int enableStatusForDb = enableStatus? 1: 0;

        int numberOfRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(COUNT_DISHES_BY_ENABLE_STATUS)) {
            statement.setInt(FIRST_INDEX,enableStatusForDb);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting all dishes by enable status: ", e);
        }

        return numberOfRows;
    }


    public int countNumberOfRecords(Category category, Boolean enableStatus) throws  DAOException {
        int enableStatusForDb = enableStatus? 1: 0;

        int numberOfRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(COUNT_DISHES_BY_CATEGORY_AND_ENABLE_STATUS)) {
            statement.setInt(FIRST_INDEX,enableStatusForDb);
            statement.setInt(SECOND_INDEX,category.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
           throw new DAOException("An exception occurred during counting all dishes by enable status and category: ", e);
        }

        return numberOfRows;
    }


    public int countNumberOfRecords(Category category) throws DAOException {
        int numberOfRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(COUNT_DISHES_BY_CATEGORY)) {
            statement.setInt(FIRST_INDEX,category.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting all dishes by category: ", e);
        }
        return numberOfRows;
    }

    protected EntityBuilder getBuilder() {
        return new DishBuilder();
    }
}