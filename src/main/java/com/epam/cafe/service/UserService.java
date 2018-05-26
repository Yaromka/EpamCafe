package com.epam.cafe.service;

import com.epam.cafe.dao.impl.DaoFactory;
import com.epam.cafe.dao.UserDao;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.exception.ServiceException;

import java.util.List;

public class UserService {

    public User logIn(String enterEmail, String enterPass) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            UserDao userDao = daoFactory.getUserDao();
            String email = enterEmail.toUpperCase();
            return userDao.getByEmailAndPassword(email, enterPass);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during log in attempt: ", e);
        }
    }

    public boolean isEmailExist(String enterEmail) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            UserDao userDao = daoFactory.getUserDao();
            User user = userDao.getByEmail(enterEmail.toUpperCase());
            return user!= null;
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during e-mail existence audit: ", e);
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
        try (DaoFactory daoFactory = new DaoFactory()){
            UserDao userDao = daoFactory.getUserDao();
            return userDao.getBySurname(surname, from, limit);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching user by surname: ", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers(int from, int limit) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            UserDao userDao = daoFactory.getUserDao();
            return userDao.getAll(from, limit);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during searching all users: ", e);
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
        try (DaoFactory daoFactory = new DaoFactory()){
            UserDao userDao = daoFactory.getUserDao();
            return userDao.countByParameters();
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during attempt to calculate all users: ", e);
        }
    }

    public int countUsersBySurname(String surname) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            UserDao userDao = daoFactory.getUserDao();
            return userDao.countByParameters(surname);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during attempt to calculate all users by surname: ", e);
        }
    }
}

