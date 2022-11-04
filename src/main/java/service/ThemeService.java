package service;

import dao.ThemeDAO;
import dao.impl.ThemeDAOImpl;
import entity.Theme;
import utils.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ThemeService {
    private ThemeDAO themeDAO;

    public ThemeService() {
        themeDAO = new ThemeDAOImpl();
    }

    public Theme createTheme(Theme entity) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        themeDAO.setConnection(connection);
        Theme theme = themeDAO.create(entity);
        ConnectionPool.close(connection);
        return theme;
    }

    public List<Theme> getThemes() {
        Connection connection = ConnectionPool.getInstance().getConnection();
        themeDAO.setConnection(connection);
        List<Theme> themes = themeDAO.findAll();
        ConnectionPool.close(connection);
        return themes;
    }

    public Theme getThemeById(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        themeDAO.setConnection(connection);
        Theme theme = themeDAO.findById(id);
        ConnectionPool.close(connection);
        return theme;
    }

    public void deleteTheme(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        themeDAO.setConnection(connection);
        themeDAO.delete(id);
        ConnectionPool.close(connection);
    }

    public int updateTheme(Theme theme) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        themeDAO.setConnection(connection);
        int result = themeDAO.update(theme);
        ConnectionPool.close(connection);
        return result;
    }
}
