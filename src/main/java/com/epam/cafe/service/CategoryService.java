package com.epam.cafe.service;

import com.epam.cafe.dao.CategoryDao;
import com.epam.cafe.dao.DaoFactory;
import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.exception.ServiceException;

import java.util.List;

public class CategoryService {

    public List<Category> getCategory() throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();

        try {
            return categoryDaoImpl.getAll();
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during attempt to show category: ", e);
        } finally {
            daoFactory.close();
        }
    }

    public boolean addCategory(Category category) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();
        daoFactory.beginTransaction();

        Category addedCategory;
        try {
            addedCategory = categoryDaoImpl.create(category);
            daoFactory.commit();
        } catch (DAOException e) {
            daoFactory.rollback();
            throw new ServiceException("An exception occurred during addition new category: " , e);
        } finally {
            daoFactory.endTransaction();
        }

        return addedCategory != null;
    }

    public boolean isCategoryExist(String categoryName) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();

        try {
            Category category = categoryDaoImpl.getByName(categoryName.toUpperCase());
            return category != null;
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during the audit category existence: ", e);
        } finally {
            daoFactory.close();
        }
    }

    public Category getCategoryById(int id) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();

        try {
            return categoryDaoImpl.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred while getting category by id: ", e);
        } finally {
            daoFactory.close();
        }
    }
}
