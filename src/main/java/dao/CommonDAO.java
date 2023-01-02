package dao;


import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface CommonDAO<E> {
    Logger LOGGER = LoggerFactory.getLogger(CommonDAO.class);

    E create(E entity) throws DBException;

    List<E> findAll() throws DBException;

    E findById(int id) throws DBException;

    boolean delete(int id) throws DBException;

    int update(E entity) throws DBException;

    default void close(ResultSet rs, Statement statement, Connection connection) throws DBException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error close resources: " + e);
            throw new DBException("Error closing resources");
        }

    }

    default Connection getConnection() {
        return ConnectionPool.getInstance().getConnection();
    }


}
