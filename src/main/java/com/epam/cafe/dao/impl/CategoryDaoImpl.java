package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.CategoryBuilder;
import com.epam.cafe.builder.EntityBuilder;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.CategoryDao;
import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.DAOException;

import java.sql.ResultSet;
import java.util.List;


public class CategoryDaoImpl extends AbstractDao implements CategoryDao {
    private static final String FIND_CATEGORY_BY_ID = "SELECT * FROM categories WHERE category_id=?";
    private static final String FIND_ALL_CATEGORY = "SELECT * FROM categories";
    private static final String INSERT_NEW_CATEGORY = "INSERT INTO categories (category_name) VALUES(?)";
    private static final String FIND_CATEGORY_BY_NAME = "SELECT * FROM categories WHERE category_name = ?";

    public CategoryDaoImpl(ConnectionProxy connection) {
        super.setConnection(connection);
    }

    @Override
    public Category create(Category category) throws DAOException {
        int categoryId = createAndGetId(INSERT_NEW_CATEGORY, category.getName());
        category.setId(categoryId);
        return category;
    }

    public Category getById(int id) throws DAOException {
        return (Category) getSingleByParameter(FIND_CATEGORY_BY_ID, id);
    }

    @SuppressWarnings("unchecked")
    public List<Category> getAll(Object... args) throws DAOException {
        return getList(FIND_ALL_CATEGORY);
    }

    public Category getByName(String categoryName) throws DAOException {
        return (Category) getSingleByParameter(FIND_CATEGORY_BY_NAME, categoryName);
    }

    @Override
    public AbstractEntity buildEntity(ResultSet resultSet) {
        Category category = null;
        try {
            category = (Category) getBuilder().createEntity(resultSet);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    protected EntityBuilder getBuilder() {
        return new CategoryBuilder();
    }
}

