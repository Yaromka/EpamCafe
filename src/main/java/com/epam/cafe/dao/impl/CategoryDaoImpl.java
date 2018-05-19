package com.epam.cafe.dao.impl;

import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.AbstractDao;
import com.epam.cafe.dao.CategoryDao;
import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.DAOException;
import com.epam.cafe.util.ResultSetConverter;

import java.sql.ResultSet;
import java.util.List;

public class CategoryDaoImpl extends AbstractDao implements CategoryDao {
    private static final String FIND_CATEGORY_BY_ID = "SELECT * FROM categories WHERE category_id=?";
    private static final String FIND_ALL_CATEGORY = "SELECT * FROM categories";
    private static final String INSERT_NEW_CATEGORY = "INSERT INTO categories (category_name) VALUES(?)";
    private static final String FIND_CATEGORY_BY_NAME = "SELECT * FROM categories WHERE category_name = ?";

    private ConnectionProxy connection;

    public ConnectionProxy getConnection() {
        return connection;
    }

    public void setConnection(ConnectionProxy connection) {
        this.connection = connection;
    }

    public CategoryDaoImpl(ConnectionProxy connection) {
        this.connection = connection;
    }

    public Category create(Category category) throws DAOException {
        category.setId(getInsertId(INSERT_NEW_CATEGORY, connection, category.getName()));
        return category;
    }

    public Category getById(int id) throws DAOException {
        return (Category) getSingleByParameter(FIND_CATEGORY_BY_ID, connection, id);
    }

    @SuppressWarnings("unchecked")
    public List<Category> getAll() throws DAOException {
        return getList(connection, FIND_ALL_CATEGORY);
    }

    public Category getByName(String categoryName) throws DAOException {
        return (Category) getSingleByParameter(FIND_CATEGORY_BY_NAME, connection, categoryName);
    }

    @Override
    protected AbstractEntity buildEntity(ResultSet resultSet) {
        Category category = null;
        try {
            category = ResultSetConverter.createCategoryEntity(resultSet);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return category;
    }
}
