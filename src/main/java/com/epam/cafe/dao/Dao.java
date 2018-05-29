package com.epam.cafe.dao;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.exception.DAOException;

import java.util.List;


public interface Dao <T extends AbstractEntity>{

    /**
     * Returns all entities from table.
     * @param args query and some parameters.
     */
    List<T> getAll(Object... args) throws DAOException;
}
