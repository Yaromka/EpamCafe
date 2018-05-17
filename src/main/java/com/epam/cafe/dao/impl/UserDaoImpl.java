package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.EntityBuilder;
import com.epam.cafe.builder.UserBuilder;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.AbstractDao;
import com.epam.cafe.dao.UserDao;
import com.epam.cafe.util.ResultSetConverter;
import com.epam.cafe.entity.Role;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.cafe.constants.ParameterIndexes.*;

public class UserDaoImpl extends AbstractDao implements UserDao{
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE user_email=?";
    private static final String FIND_USER_BY_EMAIL_PASSWORD = "SELECT * FROM users WHERE user_email=? AND user_password=?";
    private static final String FIND_ALL_USERS = "SELECT * FROM users limit ?,?";
    private static final String COUNT_ALL_USERS = "SELECT count(*) FROM users";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";
    private static final String FIND_USER_BY_SURNAME = "SELECT * FROM users WHERE user_surname=? limit ?,?";
    private static final String COUNT_USERS_BY_SURNAME = "SELECT count(*) FROM users WHERE user_surname=?";
    private static final String INSERT_USER = "INSERT INTO users (user_name,user_surname,user_passport,user_phone," +
            "user_email,user_password,roles_role_id) VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_USER_PASSWORD = "UPDATE users SET user_password =? WHERE user_id =?";
    private static final String UPDATE_USER_INFO = "UPDATE users SET user_name =? ,user_surname = ? ,user_email = ? ," +
            "user_phone = ? ,user_passport = ? WHERE user_id =?";
    private static final String UPDATE_LOYALTY_POINT_BY_USER_ID = "UPDATE users SET user_loyaltyPoints = ? WHERE user_id = ?";

    private ConnectionProxy connection;

    public ConnectionProxy getConnection() {
        return connection;
    }

    public void setConnection(ConnectionProxy connection) {
        this.connection = connection;
    }

    public UserDaoImpl(ConnectionProxy connection) {
        this.connection = connection;
    }

    public User create(User user) throws DAOException {
        Integer autoIncrementedUserId;
        try(PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(FIRST_INDEX, user.getName());
            statement.setString(SECOND_INDEX, user.getSurname());
            statement.setString(THIRD_INDEX, user.getPassport());
            statement.setString(FOURTH_INDEX, user.getPhone());
            statement.setString(FIFTH_INDEX, user.getMail().toUpperCase());
            statement.setString(SIXTH_INDEX, user.getPassword());
            statement.setString(SEVENTH_INDEX, Role.CLIENT.getDbId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            autoIncrementedUserId = rs.getInt(FIRST_INDEX);
        } catch (SQLException e) {
            throw new DAOException("An exception occ" +
                    "urred during addition user: ", e);
        }
        user.setId(autoIncrementedUserId);
        return user;
    }

    public User getById(Integer id) throws DAOException {
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setInt(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching user by id: ", e);
        }
        return user;
    }

    public List<User> getAll(int from, int limit) throws DAOException {
        List<User> users = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            statement.setInt(FIRST_INDEX,from);
            statement.setInt(SECOND_INDEX,limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = ResultSetConverter.createUserEntity(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching all users: " , e);
        }
        return users;
    }

    public User getByEmail(String eMail) throws DAOException {
        User user = null;
        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)){
            statement.setString(FIRST_INDEX, eMail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching user by email: " + eMail + " ", e);
        }
        return user;
    }

    public User getByEmailAndPassword(String eMail, String password) throws DAOException {
        User user = null;

        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_PASSWORD)) {
            statement.setString(FIRST_INDEX, eMail);
            statement.setString(SECOND_INDEX, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                user = ResultSetConverter.createUserEntity(resultSet);
            }
        } catch (SQLException e) {
          throw new DAOException("An exception occurred during searching user by email and password:" + eMail + " ", e);
        }

        return user;
    }

    public List<User> getBySurname(String surname, int from, int limit) throws DAOException {
        List<User> users = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_SURNAME)) {
            statement.setString(FIRST_INDEX, surname);
            statement.setInt(SECOND_INDEX, from);
            statement.setInt(THIRD_INDEX, limit);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                users.add(ResultSetConverter.createUserEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching user by surname: " , e);
        }
        return users;
    }

    public void updatePassword(Integer id, String password) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
            statement.setString(FIRST_INDEX, password);
            statement.setInt(SECOND_INDEX, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during attempted update user password: ", e);
        }
    }

    public void updateLoyaltyPoints(long loyaltyPoint, int userId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_LOYALTY_POINT_BY_USER_ID)) {
            statement.setLong(FIRST_INDEX, loyaltyPoint);
            statement.setInt(SECOND_INDEX, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during attempted update loyalty point by user id: ", e);
        }
    }

    public void updateUser(User user) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_INFO)) {
            statement.setString(FIRST_INDEX, user.getName());
            statement.setString(SECOND_INDEX, user.getSurname());
            statement.setString(THIRD_INDEX, user.getMail());
            statement.setString(FOURTH_INDEX, user.getPhone());
            statement.setString(FIFTH_INDEX, user.getPassport());
            statement.setInt(SIXTH_INDEX, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during attempted update user info: ", e);
        }
    }

    public int countByParameters() throws DAOException {
        int numberOfRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(COUNT_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting all users: ", e);
        }

        return numberOfRows;
    }

    public int countByParameters(String surname) throws DAOException {
        int numberOfRows = 0;
        try (PreparedStatement statement = connection.prepareStatement(COUNT_USERS_BY_SURNAME)) {
            statement.setString(FIRST_INDEX, surname);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(FIRST_INDEX);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during counting users bt surname: ", e);
        }

        return numberOfRows;
    }

    @Override
    protected EntityBuilder getBuilder() {
        return new UserBuilder();
    }
}