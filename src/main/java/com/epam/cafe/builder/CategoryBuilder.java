package com.epam.cafe.builder;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryBuilder implements EntityBuilder {
    private static final String CATEGORY_NAME_PARAM = "category_name";
    private static final String CATEGORY_ID_PARAM = "category_id";

    @Override
    public Category createEntity(ResultSet resultSet) throws DAOException {
        Category category;
        try {
            int id = resultSet.getInt(CATEGORY_ID_PARAM);
            String name = resultSet.getString(CATEGORY_NAME_PARAM);

            category = new Category();

            category.setId(id);
            category.setName(name);
        } catch (SQLException e) {
            throw new DAOException("Exception during creating Category", e);
        }
        return category;
    }
}
