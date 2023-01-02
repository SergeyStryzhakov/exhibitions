package service;

import dao.ThemeDAO;
import dao.impl.ThemeDAOImpl;
import entity.Theme;
import exception.DBException;

import java.util.List;

public class ThemeService {
    private final ThemeDAO themeDAO;

    public ThemeService() {
        themeDAO = new ThemeDAOImpl();
    }

    public Theme createTheme(Theme entity) throws DBException {
        return themeDAO.create(entity);
    }

    public List<Theme> getThemes() throws DBException {
        return themeDAO.findAll();
    }

    public Theme getThemeById(int id) throws DBException {
        return themeDAO.findById(id);
    }

    public void deleteTheme(int id) throws DBException {
        themeDAO.delete(id);
    }

    public int updateTheme(Theme theme) throws DBException {
        return themeDAO.update(theme);
    }
}
