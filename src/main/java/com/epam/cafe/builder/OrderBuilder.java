package com.epam.cafe.builder;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.entity.Order;

public class OrderBuilder implements EntityBuilder {
    @Override
    public AbstractEntity createEntity() {
        return new Order();
    }
}
