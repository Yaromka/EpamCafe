package com.epam.cafe.dao;

import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.DAOException;


public interface CategoryDao extends Dao{

    /**
     * Returns concrete category from DAO by name.
     * @param category name of category.
     */
    Category create(Category category) throws DAOException;

    /**
     * Returns concrete category from DAO by id.
     * @param id primary key from the table.
     */
    Category getById(int id) throws DAOException;

    /**
     * Returns concrete category from DAO by name.
     * @param categoryName name of category.
     */
    Category getByName(String categoryName) throws DAOException;
}