package dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface CommonDAO<E> {
    Logger LOGGER = LoggerFactory.getLogger(CommonDAO.class);

    E create(E entity);

    List<E> findAll();

    E findById(int id);

    boolean delete(int id);

    int update(E entity);

    void setConnection(Connection con);

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Error close statement: " + e);
            }
        }
    }


}
