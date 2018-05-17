package com.epam.cafe.dao;

import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.DAOException;

import java.util.List;

public interface UserDao extends Dao{
    User create(User user) throws DAOException;

    User getById(Integer id) throws DAOException;

    List<User> getAll(int from, int limit) throws DAOException;

    User getByEmail(String eMail) throws DAOException;

    User getByEmailAndPassword(String eMail, String password) throws DAOException;

    List<User> getBySurname(String surname, int from, int limit) throws DAOException;

    void updatePassword(Integer id, String password) throws DAOException;

    void updateLoyaltyPoints(long loyaltyPoint, int userId) throws DAOException;

    void updateUser(User user) throws DAOException;

    int countByParameters() throws DAOException;

    int countByParameters(String surname) throws DAOException;

}