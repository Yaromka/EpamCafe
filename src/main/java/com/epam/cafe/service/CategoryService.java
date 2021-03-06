package com.epam.cafe.service;

import com.epam.cafe.dao.CategoryDao;
import com.epam.cafe.dao.impl.DaoFactory;
import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.exception.ServiceException;

import java.util.List;

public class CategoryService {

    /**
     * Returns all categories from table.
     */
    @SuppressWarnings("unchecked")
    public List<Category> getAll() throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();
            return categoryDaoImpl.getAll();
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during attempt to show category: ", e);
        }
    }

    /**
     * Add in DAO new category.
     * @param  category object that should be inserted.
     */
    public boolean addCategory(Category category) throws ServiceException {
        DaoFactory daoFactory = new DaoFactory();
        daoFactory.beginTransaction();
        CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();

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

    /**
     * Checks if category exists in DAO.
     * @param categoryName name of category.
     */
    public boolean isCategoryExist(String categoryName) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()){
            CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();
            Category category = categoryDaoImpl.getByName(categoryName.toUpperCase());

            return category != null;
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred during the audit category existence: ", e);
        }
    }

    /**
     * Returns concrete category from DAO by id.
     * @param id primary key from the table.
     */
    public Category getCategoryById(int id) throws ServiceException {

        try (DaoFactory daoFactory = new DaoFactory()){
            CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();
            return categoryDaoImpl.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("An exception occurred while getting category by id: ", e);
        }
    }
}
