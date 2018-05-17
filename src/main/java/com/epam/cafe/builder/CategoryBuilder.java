package com.epam.cafe.builder;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.entity.Category;

public class CategoryBuilder implements EntityBuilder {
    @Override
    public AbstractEntity createEntity() {
        return new Category();
    }
}
