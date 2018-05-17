package com.epam.cafe.builder;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.entity.Dish;

public class DishBuilder implements EntityBuilder {
    @Override
    public AbstractEntity createEntity() {
        return new Dish();
    }
}
