package com.epam.cafe.builder;

import com.epam.cafe.dao.CategoryDao;
import com.epam.cafe.dao.impl.DaoFactory;
import com.epam.cafe.entity.Category;
import com.epam.cafe.entity.Dish;
import com.epam.cafe.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DishBuilder implements EntityBuilder {

    private static final String DISH_NAME_PARAM = "dish_name";
    private static final String DISH_ID_PARAM = "dish_id";
    private static final String DISH_CATEGORY_ID_PARAM = "categories_category_id";
    private static final String DISH_PRICE_PARAM = "dish_price";
    private static final String DISH_DESCRIPTION_PARAM = "dish_description";
    private static final String DISH_WEIGHT_PARAM = "dish_weight";
    private static final String DISH_PICTURE_PARAM = "dish_picture";
    private static final String DISH_IS_ENABLE_PARAM = "dish_isEnable";

    @Override
    public Dish createEntity(ResultSet resultSet) throws DAOException{
        Dish dish;
        try {
            int id = resultSet.getInt(DISH_ID_PARAM);
            String name = resultSet.getString(DISH_NAME_PARAM);
            long price = resultSet.getLong(DISH_PRICE_PARAM);
            String description = resultSet.getString(DISH_DESCRIPTION_PARAM);
            String picture = resultSet.getString(DISH_PICTURE_PARAM);
            int weight = resultSet.getInt(DISH_WEIGHT_PARAM);
            int categoryId = resultSet.getInt(DISH_CATEGORY_ID_PARAM);
            int enableParam = resultSet.getInt(DISH_IS_ENABLE_PARAM);
            Boolean isEnable = (enableParam == 1);

            dish = new Dish();

            dish.setId(id);
            dish.setName(name);
            dish.setDescription(description);
            dish.setPrice(price);
            dish.setPicture(picture);
            dish.setWeight(weight);

            DaoFactory daoFactory = new DaoFactory();
            daoFactory.beginTransaction();
            CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();
            Category category = categoryDaoImpl.getById(categoryId);
            daoFactory.endTransaction();

            dish.setCategory(category);
            dish.setEnable(isEnable);
        } catch (SQLException e) {
            throw new DAOException("Exception during creating Dish", e);
        }
        return dish;
    }
}
