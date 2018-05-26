package com.epam.cafe.dao.impl;

import com.epam.cafe.builder.EntityBuilder;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao <T extends AbstractEntity> {

    protected ConnectionProxy connection;

    public void setConnection(ConnectionProxy connection) {
        this.connection = connection;
    }

    public abstract T buildEntity(ResultSet resultSet);

    protected List<T> getList(String query) throws DAOException {
        List<T> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching all categories: ", e);
        }
        return list;
    }

    protected T getSingleByParameter(String query, Object... args) throws DAOException{
        T concreteEntity = null;
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            setParams(statement, args);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                concreteEntity = buildEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("An exception occurred during searching entity by id: ", e);
        }
        return concreteEntity;
    }

    protected List<T> executeQuery(String query, Object... args) throws DAOException{
        List<T> objectList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = setParams(statement, args).executeQuery();
            while (resultSet.next()){
                objectList.add(buildEntity(resultSet));
            }
        }catch (SQLException e){
            throw new DAOException("An exception occurred during attempted executing query :", e);
        }
        return objectList;
    }

    protected void executeUpdate(String query, Object... args) throws DAOException{
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParams(statement, args);
            statement.executeUpdate();
        } catch (SQLException e){
            throw new DAOException("An exception occurred during attempted executing query :", e);
        }
    }

    protected int createAndGetId(String query, Object... args) throws DAOException{
        Integer autoIncrementedId;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            setParams(statement, args);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            autoIncrementedId = resultSet.getInt(1);
        }catch (SQLException e){
            throw new DAOException("Failed to execute insert query", e);
        }
        return autoIncrementedId;
    }

    protected int countByParameter(String query, Object... args) throws DAOException {
        int numberOfRows = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (args.length>0) {
                setParams(statement, args);
            }

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(1);
            }

        } catch (SQLException e){
            throw new DAOException("An exception occurred during attempted counting :", e);
        }
        return numberOfRows;
    }

    protected abstract EntityBuilder getBuilder();

    private PreparedStatement setParams(PreparedStatement statement, Object... args) throws DAOException {
        try {
            int i = 1;
            for (Object arg : args) {
                statement.setObject(i, arg);
                i++;
            }
            return statement;

        } catch (SQLException e) {
            throw new DAOException("An exception occurred during attempted executing query :", e);
        }
    }
}