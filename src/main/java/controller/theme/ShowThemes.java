package controller.theme;

import entity.Theme;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ThemeService;
import utils.Pagination;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/themes/show")
public class ShowThemes extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowThemes.class);
    private ThemeService themeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            processRequest(req);
        } catch (DBException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/themes/themes.jsp")
                .forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        themeService = (ThemeService) getServletContext().getAttribute("themeService");
    }

    private void processRequest(HttpServletRequest req) throws DBException {
        int page = Pagination.setPage(req);
        int itemsPerPage = Pagination.setItemsPerPage(req, "themesPerPage");
        List<Theme> themes = themeService.getThemes();
        req.setAttribute("themes", Pagination.createListWithPagination(themes, page, itemsPerPage));
        req.setAttribute("pagination", Pagination.setPagination(themes, itemsPerPage));
        req.getSession().setAttribute("origin", req.getRequestURI());
    }
}