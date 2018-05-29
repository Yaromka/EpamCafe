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

    /**
     * Add in DAO new category.
     * @param  category object that should be inserted.
     */
    @Override
    public Category create(Category category) throws DAOException {
        int categoryId = createEntityAndGetId(INSERT_NEW_CATEGORY, category.getName());
        category.setId(categoryId);
        return category;
    }

    /**
     * Returns concrete category from DAO by id.
     * @param id primary key from the table.
     */
    public Category getById(int id) throws DAOException {
        return (Category) getSingleByParameters(FIND_CATEGORY_BY_ID, id);
    }

    /**
     * Returns all categories from table.
     * @param args query.
     */
    @SuppressWarnings("unchecked")
    public List<Category> getAll(Object... args) throws DAOException {
        return getList(FIND_ALL_CATEGORY);
    }

    /**
     * Returns concrete category from DAO by name.
     * @param categoryName name of category.
     */
    public Category getByName(String categoryName) throws DAOException {
        return (Category) getSingleByParameters(FIND_CATEGORY_BY_NAME, categoryName);
    }

    /**
     * Returns category with all fields filled.
     * @param resultSet that has all information about category.
     */
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

