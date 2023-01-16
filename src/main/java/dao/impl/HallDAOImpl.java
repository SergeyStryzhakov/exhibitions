package dao.impl;

import dao.HallDAO;
import dao.mapper.HallMapper;
import entity.Hall;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallDAOImpl implements HallDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(HallDAOImpl.class);
    private static final String FIND_ALL_HALLS = "SELECT * FROM halls";
    public static final String FIND_HALL_BY_ID = "SELECT * FROM halls WHERE id=?";
    public static final String DELETE_HALL = "DELETE FROM halls WHERE id=?";
    public static final String UPDATE_HALL = "UPDATE halls SET name=?, address=?, capacity=? WHERE id=?";
    public static final String CREATE_HALL = "INSERT INTO halls (name, address, capacity) VALUES (?,?,?)";
    private static final String FIND_HALLS_BY_EXHIBITION_ID =
            "SELECT * FROM halls JOIN exhibitions_halls eh ON halls.id = eh.hall_id " +
                    "WHERE exhibition_id=?";
    private Connection connection;

    @Override
    public Hall create(Hall hall) throws DBException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            connection = getConnection();
            statement = connection
                    .prepareStatement(CREATE_HALL,
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(++counter, hall.getName());
            statement.setString(++counter, hall.getAddress());
            statement.setInt(++counter, hall.getCapacity());
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                hall.setId(rs.getInt(1));
                LOGGER.info("Hall with id " + rs.getInt(1) + " created");
            }
            return hall;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in create method " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
    }

    @Override
    public List<Hall> findAll() throws DBException {
        List<Hall> halls = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(FIND_ALL_HALLS);
            while (rs.next()) {
                halls.add(HallMapper.extractHall(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new DBException(
                    "Error in findAll method " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return halls;
    }

    @Override
    public Hall findById(int id) throws DBException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        Hall hall = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_HALL_BY_ID);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                hall = HallMapper.extractHall(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findById method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return hall;
    }

    @Override
    public boolean delete(int id) throws DBException {
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(DELETE_HALL);
            statement.setInt(1, id);
            result = statement.execute();
            LOGGER.info("Delete hall with id " + id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in delete method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
        return result;
    }

    @Override
    public int update(Hall hall) throws DBException {
        PreparedStatement statement = null;
        int counter = 0;
        int result;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(UPDATE_HALL);
            statement.setString(++counter, hall.getName());
            statement.setString(++counter, hall.getAddress());
            statement.setInt(++counter, hall.getCapacity());
            statement.setInt(++counter, hall.getId());
            result = statement.executeUpdate();
            LOGGER.info("Update " + result + " rows");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in update method with id " + hall.getId() + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
        return result;
    }

    @Override
    public List<Hall> findByExhibitionId(int id) throws DBException {
        List<Hall> halls = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_HALLS_BY_EXHIBITION_ID);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                halls.add(HallMapper.extractHall(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findByExhibitionId method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return halls;
    }
}
