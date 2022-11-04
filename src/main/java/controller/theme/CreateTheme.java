package controller.theme;

import entity.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ThemeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/themes/create")
public class CreateTheme extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTheme.class);
    private ThemeService themeService;

    @Override
    public void init() throws ServletException {
        themeService = (ThemeService) getServletContext()
                .getAttribute("themeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/themes/create_theme.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            processRequest(req);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/themes/show");
    }

    private void processRequest(HttpServletRequest req) throws SQLException {
        Theme theme = new Theme.Builder()
                .name(req.getParameter("name"))
                .build();
        themeService.createTheme(theme);
    }
}
