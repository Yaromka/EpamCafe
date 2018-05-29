package com.epam.cafe.builder;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.exception.DAOException;

import java.sql.ResultSet;

public interface EntityBuilder <T extends AbstractEntity>{

    /**
     * Returns entity with all fields filled.
     * It is used in DAO in order to create entities outside.
     * @param resultSet that has all information about entity.
     */
    T createEntity(ResultSet resultSet) throws DAOException;
}
