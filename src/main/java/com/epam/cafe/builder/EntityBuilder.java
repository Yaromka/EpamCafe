package com.epam.cafe.builder;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.exception.DAOException;

import java.sql.ResultSet;

public interface EntityBuilder <T extends AbstractEntity>{
    T createEntity(ResultSet resultSet) throws DAOException;
}
