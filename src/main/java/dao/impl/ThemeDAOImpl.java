package dao.impl;

import dao.ThemeDAO;
import dao.mapper.ThemeMapper;
import entity.Theme;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeDAOImpl implements ThemeDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThemeDAOImpl.class);
    private static final String FIND_ALL_THEMES = "SELECT * FROM themes";
    public static final String FIND_THEME_BY_ID = "SELECT * FROM themes WHERE id=?";
    public static final String DELETE_THEME = "DELETE FROM themes WHERE id=?";
    public static final String UPDATE_THEME = "UPDATE themes SET name=? WHERE id=?";
    public static final String CREATE_THEME = "INSERT INTO themes (name) VALUES (?)";

    @Override
    public Theme create(Theme theme) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            connection = getConnection();
            statement = connection
                    .prepareStatement(CREATE_THEME,
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(++counter, theme.getName());
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) theme.setId(rs.getInt(1));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in create method " + e.getMessage());
        } finally {
            close(rs, statement, connection);

        }
        return theme;
    }

    @Override
    public List<Theme> findAll() throws DBException {
        List<Theme> themes = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(FIND_ALL_THEMES);
            while (rs.next()) {
                themes.add(ThemeMapper.extractTheme(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findAll method " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return themes;
    }

    @Override
    public Theme findById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Theme theme = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_THEME_BY_ID);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                theme = ThemeMapper.extractTheme(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findById method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return theme;
    }

    @Override
    public boolean delete(int id) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(DELETE_THEME);
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in delete method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
    }

    @Override
    public int update(Theme theme) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        int counter = 0;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(UPDATE_THEME);
            statement.setString(++counter, theme.getName());
            statement.setInt(++counter, theme.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in update method with id " + theme.getId() + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
    }
}
