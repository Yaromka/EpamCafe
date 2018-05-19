package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.EntityBuilder;
import com.epam.cafe.builder.UserBuilder;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.AbstractDao;
import com.epam.cafe.dao.UserDao;
import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.util.ResultSetConverter;
import com.epam.cafe.entity.Role;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.DAOException;

import java.sql.ResultSet;
import java.util.List;

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
        user.setId(getInsertId(INSERT_USER, connection, user.getName(), user.getSurname(), user.getPassport(),
                user.getPhone(), user.getMail().toUpperCase(), user.getPassword(), Role.CLIENT.getDbId()));
        return user;
    }

    public User getById(Integer id) throws DAOException {
        return (User) getSingleByParameter(FIND_USER_BY_ID, connection, id);
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll(int from, int limit) throws DAOException {
        return executeQuery(connection, FIND_ALL_USERS, from, limit);
    }

    public User getByEmail(String eMail) throws DAOException {
        return (User) getSingleByParameter(FIND_USER_BY_EMAIL, connection, eMail);
    }

    public User getByEmailAndPassword(String eMail, String password) throws DAOException {
        return (User) getSingleByParameter(FIND_USER_BY_EMAIL_PASSWORD, connection, eMail, password);
    }

    @SuppressWarnings("unchecked")
    public List<User> getBySurname(String surname, int from, int limit) throws DAOException {
        return executeQuery(connection, FIND_USER_BY_SURNAME, surname, from, limit);
    }

    public void updatePassword(Integer id, String password) throws DAOException {
        executeUpdate(connection, UPDATE_USER_PASSWORD, password, id);
    }

    public void updateLoyaltyPoints(long loyaltyPoint, int userId) throws DAOException {
        executeUpdate(connection, UPDATE_LOYALTY_POINT_BY_USER_ID, loyaltyPoint, userId);
    }

    public void updateUser(User user) throws DAOException {
        executeUpdate(connection, UPDATE_USER_INFO, user.getName(), user.getSurname(), user.getMail(),
                user.getPhone(), user.getPassport(), user.getId());
    }

    public int countByParameters() throws DAOException {
        return countByParameter(connection, COUNT_ALL_USERS);
    }

    public int countByParameters(String surname) throws DAOException {
        return countByParameter(connection, COUNT_USERS_BY_SURNAME, surname);
    }

    protected EntityBuilder getBuilder() {
        return new UserBuilder();
    }

    @Override
    protected AbstractEntity buildEntity(ResultSet resultSet) {
        User user = null;
        try {
            user = ResultSetConverter.createUserEntity(resultSet);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return user;
    }
}