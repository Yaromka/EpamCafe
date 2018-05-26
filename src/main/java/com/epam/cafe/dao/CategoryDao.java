package com.epam.cafe.dao;

import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.DAOException;


public interface CategoryDao extends Dao{
    Category create(Category category) throws DAOException;

    Category getById(int id) throws DAOException;

    Category getByName(String categoryName) throws DAOException;
}