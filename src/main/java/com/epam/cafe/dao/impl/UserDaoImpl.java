package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.EntityBuilder;
import com.epam.cafe.builder.UserBuilder;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.UserDao;
import com.epam.cafe.entity.AbstractEntity;
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

    public UserDaoImpl(ConnectionProxy connection) {
        super.setConnection(connection);
    }

    /**
     * Add in DAO new user.
     * @param user object that should be inserted.
     */
    public User create(User user) throws DAOException {
        int userId = createEntityAndGetId(INSERT_USER, user.getName(), user.getSurname(), user.getPassport(),
                user.getPhone(), user.getMail().toUpperCase(), user.getPassword(), Role.CLIENT.getDbId());

        user.setId(userId);
        return user;
    }

    /**
     * Returns concrete user from DAO by id.
     * @param id primary key from the table.
     */
    public User getById(Integer id) throws DAOException {
        return (User) getSingleByParameters(FIND_USER_BY_ID, id);
    }

    /**
     * Returns all users from table by parameters.
     * @param args query and pagination info.
     */
    @SuppressWarnings("unchecked")
    public List<User> getAll(Object... args) throws DAOException {
        return executeQuery(FIND_ALL_USERS, args);
    }

    /**
     * Returns concrete user from DAO by e-mail.
     * @param eMail of concrete user to be returned.
     */
    public User getByEmail(String eMail) throws DAOException {
        return (User) getSingleByParameters(FIND_USER_BY_EMAIL, eMail);
    }

    /**
     * Returns concrete user from DAO by e-mail and password.
     * @param eMail of concrete user to be returned.
     * @param password user's password.
     */
    public User getByEmailAndPassword(String eMail, String password) throws DAOException {
        return (User) getSingleByParameters(FIND_USER_BY_EMAIL_PASSWORD, eMail, password);
    }

    /**
     * Returns concrete user from DAO by surname.
     * @param surname of concrete user to be returned.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    @SuppressWarnings("unchecked")
    public List<User> getBySurname(String surname, int from, int limit) throws DAOException {
        return executeQuery(FIND_USER_BY_SURNAME, surname, from, limit);
    }

    /**
     * Updates user's password by id.
     * @param id of concrete user to be updated.
     * @param password user's new password.
     */
    public void updatePassword(Integer id, String password) throws DAOException {
        executeUpdate(UPDATE_USER_PASSWORD, password, id);
    }

    /**
     * Updates user's loyalty points quantity by id.
     * @param userId of concrete user to be updated.
     * @param loyaltyPoint user's current loyalty points quantity.
     */
    public void updateLoyaltyPoints(long loyaltyPoint, int userId) throws DAOException {
        executeUpdate(UPDATE_LOYALTY_POINT_BY_USER_ID, loyaltyPoint, userId);
    }

    /**
     * Updates user's information.
     * @param user of concrete user to be updated.
     */
    public void updateUser(User user) throws DAOException {
        executeUpdate(UPDATE_USER_INFO, user.getName(), user.getSurname(), user.getMail(),
                user.getPhone(), user.getPassport(), user.getId());
    }

    /**
     * Count all users.
     */
    public int countByParameters() throws DAOException {
        return countByParameter(COUNT_ALL_USERS);
    }

    /**
     * Count users by surname.
     * @param surname users surname.
     */
    public int countByParameters(String surname) throws DAOException {
        return countByParameter(COUNT_USERS_BY_SURNAME, surname);
    }

    /**
     * Returns user with all fields filled.
     * @param resultSet that has all information about user.
     */
    @Override
    public AbstractEntity buildEntity(ResultSet resultSet) {
        User user = null;
        try {
            user = (User) getBuilder().createEntity(resultSet);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected EntityBuilder getBuilder() {
        return new UserBuilder();
    }
}