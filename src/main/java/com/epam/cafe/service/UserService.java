package com.epam.cafe.service;

import com.epam.cafe.dao.DaoFactory;
import com.epam.cafe.dao.UserDao;
import com.epam.cafe.dao.impl.UserDaoImpl;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.exception.ServiceException;

import java.util.List;

public class UserService {

    public User logIn(String enterEmail, String enterPass) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();

        String email = enterEmail.toUpperCase();
        try {
            return userDao.getByEmailAndPassword(email, enterPass);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during log in attempt: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public boolean isEmailExist(String enterEmail) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();

        try {
            User user = userDao.getByEmail(enterEmail.toUpperCase());
            return user!= null;
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during e-mail existence audit: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public boolean signUp(User user) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();


        User resultUser;
        try {
            resultUser = userDao.create(user);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during the sign-up attempt: ", e);
        } finally {
            daoFactory.endTransaction();
        }

        return resultUser != null;
    }

    public List<User> findUserBySurname(String surname, int from, int limit) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();

        try {
            return userDao.getBySurname(surname, from, limit);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching user by surname: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public List<User> findAllUsers(int from, int limit) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();

        try {
            return userDao.getAll(from, limit);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching all users: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public void changePassword(User user, String newPassword) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();


        Integer userId = user.getId();
        try {
            userDao.updatePassword(userId, newPassword);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw  new ServiceException("An exception occurred during update user password attempt: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public void editUserInfo(User user) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();


        try {
            userDao.updateUser(user);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during update user profile attempt: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public void updateLoyaltyPointsByUserId(long newLoyaltyPointsValue, int userId) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();


        try {
            userDao.updateLoyaltyPoints(newLoyaltyPointsValue, userId);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during attempt to update loyalty points by user id: ",e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public int countAllUsers() throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();

        try {
            return userDao.countByParameters();
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during attempt to calculate all users: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }

    public int countUsersBySurname(String surname) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        UserDao userDao = daoFactory.getUserDao();

        try {
            return userDao.countByParameters(surname);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during attempt to calculate all users by surname: ", e);
        } finally {
            daoFactory.endTransaction();
        }
    }
}

