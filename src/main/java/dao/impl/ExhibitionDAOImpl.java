package dao.impl;

import dao.ExhibitionDAO;
import dao.mapper.ExhibitionMapper;
import entity.Exhibition;
import entity.Hall;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionDAOImpl implements ExhibitionDAO {
    public static Logger LOGGER = LoggerFactory.getLogger(ExhibitionDAO.class);
    public static final String DELETE_EXHIBITION = "DELETE FROM exhibitions WHERE id=?";
    private static final String FIND_ALL_EXHIBITIONS = "SELECT * FROM exhibitions";
    private static final String FIND_EXHIBITION_BY_ID = "SELECT * FROM exhibitions WHERE id=?";
    private static final String FIND_EXHIBITION_BY_THEME_ID = "SELECT * FROM exhibitions WHERE theme_id=?";
    private static final String FIND_EXHIBITION_BY_DATE = "SELECT * FROM exhibitions WHERE start_date BETWEEN ? AND ?";
    public static final String UPDATE_EXHIBITION = "UPDATE exhibitions " +
            "SET title=?, description=?, theme_id=?, start_date=?, finish_date=?, " +
            "open_time=?, close_time=?, price=?, image=?, state=?  WHERE id=?";
    public static final String CREATE_EXHIBITION = "INSERT INTO exhibitions " +
            "(title, description, theme_id, start_date, finish_date, open_time, close_time, price, image, state) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?)";
    public static final String RESERVE_HALLS_FOR_EXHIBITION = "INSERT INTO exhibitions_halls " +
            "(exhibition_id, hall_id) VALUES (?,?)";
    public static final String UNRESERVE_HALLS_FOR_EXHIBITION = "DELETE FROM exhibitions_halls WHERE exhibition_id=?";


    @Override
    public Exhibition create(Exhibition exhibition) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection
                    .prepareStatement(CREATE_EXHIBITION,
                            Statement.RETURN_GENERATED_KEYS);
            fillExhibition(statement, exhibition);
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                exhibition.setId(rs.getInt(1));
                LOGGER.info("Created exhibition with id " + rs.getInt(1));
            }
            statement = connection.prepareStatement(RESERVE_HALLS_FOR_EXHIBITION);
            for (Hall hall : exhibition.getHalls()) {
                statement.setInt(1, exhibition.getId());
                statement.setInt(2, hall.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in create method " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return exhibition;
    }

    @Override
    public List<Exhibition> findAll() throws DBException {
        List<Exhibition> exhibitions = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(FIND_ALL_EXHIBITIONS);
            while (rs.next()) {
                exhibitions.add(ExhibitionMapper.extractExhibition(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("Error in findAll method: " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return exhibitions;
    }

    @Override
    public Exhibition findById(int id) throws DBException {
        Exhibition exhibition = null;
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection
                    .prepareStatement(FIND_EXHIBITION_BY_ID);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                exhibition = ExhibitionMapper.extractExhibition(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findById method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return exhibition;
    }

    @Override
    public boolean delete(int id) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(DELETE_EXHIBITION);
            statement.setInt(1, id);
            return statement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in deleteExhibition method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }

    }

    @Override
    public int update(Exhibition exhibition) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        int result;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_EXHIBITION);
            int counter = fillExhibition(statement, exhibition);
            statement.setInt(++counter, exhibition.getId());
            result = statement.executeUpdate();
            statement = connection.prepareStatement(UNRESERVE_HALLS_FOR_EXHIBITION);
            statement.setInt(1, exhibition.getId());
            statement.executeUpdate();
            statement = connection.prepareStatement(RESERVE_HALLS_FOR_EXHIBITION);
            for (Hall hall : exhibition.getHalls()) {
                statement.setInt(1, exhibition.getId());
                statement.setInt(2, hall.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in update method with id " + exhibition.getId() + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
        return result;
    }

    @Override
    public List<Exhibition> getExhibitionByThemeId(int id) throws DBException {
        List<Exhibition> exhibitions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_EXHIBITION_BY_THEME_ID);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                exhibitions.add(ExhibitionMapper.extractExhibition(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in getExhibitionByThemeId method with id "
                            + id + " : " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return exhibitions;
    }

    @Override
    public List<Exhibition> getExhibitionByDate(String from, String to) throws DBException {
        List<Exhibition> exhibitions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_EXHIBITION_BY_DATE);
            statement.setString(1, from);
            statement.setString(2, to);
            rs = statement.executeQuery();
            while (rs.next()) {
                exhibitions.add(ExhibitionMapper.extractExhibition(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in getExhibitionByDate method: from "
                            + from + " to " + to + ": " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return exhibitions;
    }

    private int fillExhibition(PreparedStatement statement,
                               Exhibition exhibition) throws SQLException {
        int counter = 0;
        statement.setString(++counter, exhibition.getTitle());
        statement.setString(++counter, exhibition.getDescription());
        statement.setInt(++counter, exhibition.getThemeId());
        statement.setString(++counter, exhibition.getStartDate());
        statement.setString(++counter, exhibition.getFinishDate());
        statement.setString(++counter, exhibition.getOpenTime());
        statement.setString(++counter, exhibition.getCloseTime());
        statement.setDouble(++counter, exhibition.getPrice());
        statement.setString(++counter, exhibition.getImage());
        statement.setString(++counter, exhibition.getStateFromDb());
        return counter;
    }
}
