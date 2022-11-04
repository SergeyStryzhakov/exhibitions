package dao.impl;


import dao.ExhibitionDAO;
import dao.mapper.ExhibitionMapper;
import entity.Exhibition;
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
    public static final String UPDATE_EXHIBITION = "UPDATE exhibitions " +
            "SET title=?, description=?, theme_id=?, start_date=?, finish_date=?, " +
            "open_time=?, close_time=?, price=?, image=?, state=?  WHERE id=?";
    public static final String CREATE_EXHIBITION = "INSERT INTO exhibitions " +
            "(title, description, theme_id, start_date, finish_date, open_time, close_time, price, image, state) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?)";
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Exhibition create(Exhibition exhibition) {
        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(CREATE_EXHIBITION,
                            Statement.RETURN_GENERATED_KEYS);
            fillExhibition(statement, exhibition);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                exhibition.setId(rs.getInt(1));
                LOGGER.info("Created exhibition with id " + rs.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(statement);
        }
        return exhibition;
    }

    @Override
    public List<Exhibition> findAll() {
        List<Exhibition> exhibitions = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_EXHIBITIONS);
            while (rs.next()) {
                exhibitions.add(ExhibitionMapper.extractExhibition(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return exhibitions;
    }

    @Override
    public Exhibition findById(int id) {
        Exhibition exhibition = null;
        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(FIND_EXHIBITION_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                exhibition = ExhibitionMapper.extractExhibition(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return exhibition;
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = connection.prepareStatement(DELETE_EXHIBITION);
            statement.setInt(1, id);
            result = statement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return result;
    }

    @Override
    public int update(Exhibition exhibition) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(UPDATE_EXHIBITION);
            int counter = fillExhibition(statement, exhibition);
            statement.setInt(++counter, exhibition.getId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return result;
    }

    @Override
    public List<Exhibition> getExhibitionByThemeId(int id) {
        List<Exhibition> exhibitions = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(FIND_EXHIBITION_BY_THEME_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                exhibitions.add(ExhibitionMapper.extractExhibition(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            close(statement);
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
