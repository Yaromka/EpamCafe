package com.epam.cafe.builder;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.entity.User;

public class UserBuilder implements EntityBuilder {
    @Override
    public AbstractEntity createEntity() {
        return new User();
    }
}
