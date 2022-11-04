package dao.impl;

import dao.ThemeDAO;
import dao.mapper.ThemeMapper;
import entity.Theme;
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
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Theme create(Theme theme) {
        PreparedStatement statement = null;
        int counter = 0;
        try {
            statement = connection
                    .prepareStatement(CREATE_THEME,
                            Statement.RETURN_GENERATED_KEYS);
            statement.setString(++counter, theme.getName());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) theme.setId(rs.getInt(1));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return theme;
    }

    @Override
    public List<Theme> findAll() {
        List<Theme> themes = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_THEMES);
            while (rs.next()) {
                themes.add(ThemeMapper.extractTheme(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
        } finally {
            close(statement);
        }
        return themes;
    }

    @Override
    public Theme findById(int id) {
        PreparedStatement statement = null;
        Theme theme = null;
        try {
            statement = connection.prepareStatement(FIND_THEME_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                theme = ThemeMapper.extractTheme(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
        } finally {
            close(statement);
        }
        return theme;
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_THEME);
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
        } finally {
            close(statement);
        }
        return false;
    }

    @Override
    public int update(Theme theme) {
        PreparedStatement statement = null;
        int counter = 0;
        try {
            statement = connection.prepareStatement(UPDATE_THEME);
            statement.setString(++counter, theme.getName());
            statement.setInt(++counter, theme.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
        } finally {
            close(statement);
        }
        return 0;
    }
}
