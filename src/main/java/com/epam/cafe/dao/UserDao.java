package com.epam.cafe.dao;

import com.epam.cafe.entity.User;
import com.epam.cafe.exception.DAOException;

import java.util.List;

public interface UserDao extends Dao{
    /**
     * Add in DAO new user.
     * @param user object that should be inserted.
     */
    User create(User user) throws DAOException;

    /**
     * Returns concrete user from DAO by id.
     * @param id primary key from the table.
     */
    User getById(Integer id) throws DAOException;

    /**
     * Returns concrete user from DAO by e-mail.
     * @param eMail of concrete user to be returned.
     */
    User getByEmail(String eMail) throws DAOException;

    /**
     * Returns concrete user from DAO by e-mail and password.
     * @param eMail of concrete user to be returned.
     * @param password user's password.
     */
    User getByEmailAndPassword(String eMail, String password) throws DAOException;

    /**
     * Returns concrete user from DAO by surname.
     * @param surname of concrete user to be returned.
     * @param from which element should be first on the page.
     * @param limit how much elements should be on the page.
     */
    List<User> getBySurname(String surname, int from, int limit) throws DAOException;

    /**
     * Updates user's password by id.
     * @param id of concrete user to be updated.
     * @param password user's new password.
     */
    void updatePassword(Integer id, String password) throws DAOException;

    /**
     * Updates user's loyalty points quantity by id.
     * @param userId of concrete user to be updated.
     * @param loyaltyPoint user's current loyalty points quantity.
     */
    void updateLoyaltyPoints(long loyaltyPoint, int userId) throws DAOException;

    /**
     * Updates user's information.
     * @param user of concrete user to be updated.
     */
    void updateUser(User user) throws DAOException;

    /**
     * Count all users.
     */
    int countByParameters() throws DAOException;

    /**
     * Count users by surname.
     * @param surname users surname.
     */
    int countByParameters(String surname) throws DAOException;
}