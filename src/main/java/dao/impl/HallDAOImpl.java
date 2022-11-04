package dao.impl;

import dao.HallDAO;
import dao.mapper.HallMapper;
import entity.Exhibition;
import entity.Hall;
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
    public static final String RESERVE_HALLS_FOR_EXHIBITION = "INSERT INTO exhibitions_halls " +
            "(exhibition_id, hall_id) VALUES (?,?)";
    public static final String UNRESERVE_HALLS_FOR_EXHIBITION = "DELETE FROM exhibitions_halls WHERE exhibition_id=?";

    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Hall create(Hall hall) {
        PreparedStatement statement = null;
        int counter = 0;
        try {
            statement = connection
                    .prepareStatement(CREATE_HALL,
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(++counter, hall.getName());
            statement.setString(++counter, hall.getAddress());
            statement.setInt(++counter, hall.getCapacity());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                hall.setId(rs.getInt(1));
                LOGGER.info("Hall with id " + rs.getInt(1) + " created");
            }
            return hall;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            close(statement);
        }
        return hall;
    }

    @Override
    public List<Hall> findAll() {
        List<Hall> halls = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_HALLS);
            while (rs.next()) {
                halls.add(HallMapper.extractHall(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            close(statement);
        }
        return halls;
    }

    @Override
    public Hall findById(int id) {
        PreparedStatement statement = null;
        Hall hall = null;
        try {
            statement = connection.prepareStatement(FIND_HALL_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                hall = HallMapper.extractHall(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            close(statement);
        }
        return hall;
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = connection.prepareStatement(DELETE_HALL);
            statement.setInt(1, id);
            result = statement.execute();
            LOGGER.info("Delete hall with id " + id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            close(statement);
        }
        return result;
    }

    @Override
    public int update(Hall hall) {
        PreparedStatement statement = null;
        int counter = 0;
        int result = 0;
        try {
            statement = connection.prepareStatement(UPDATE_HALL);
            statement.setString(++counter, hall.getName());
            statement.setString(++counter, hall.getAddress());
            statement.setInt(++counter, hall.getCapacity());
            statement.setInt(++counter, hall.getId());
            result = statement.executeUpdate();
            LOGGER.info("Update " + result + " rows");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            close(statement);
        }
        return result;
    }

    @Override
    public List<Hall> findByExhibitionId(int id) {
        List<Hall> halls = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(FIND_HALLS_BY_EXHIBITION_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                halls.add(HallMapper.extractHall(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            close(statement);
        }
        return halls;
    }

    @Override
    public void reserveHallsForExhibition(int exId, List<Hall> halls) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UNRESERVE_HALLS_FOR_EXHIBITION);
            statement.setInt(1, exId);
            statement.executeUpdate();
            statement = connection.prepareStatement(RESERVE_HALLS_FOR_EXHIBITION);
            for (Hall hall : halls) {
                statement.setInt(1, exId);
                statement.setInt(2, hall.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            LOGGER.info(halls.size() + " reserved for exhibition id " + exId);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            close(statement);
        }
    }
}
