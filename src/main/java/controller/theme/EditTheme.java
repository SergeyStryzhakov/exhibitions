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

@WebServlet("/themes/edit")
public class EditTheme extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditTheme.class);
    private ThemeService themeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int themeId = Integer.parseInt(req.getParameter("themeid"));
        req.setAttribute("theme", themeService.getThemeById(themeId));
        LOGGER.info("Edit theme id " + themeId + " started (GET request)");
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/themes/edit_theme.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int themeId = Integer.parseInt(req.getParameter("themeid"));
        processRequest(req, themeId);
        resp.sendRedirect(req.getContextPath() + "/themes/show");
    }

    @Override
    public void init() throws ServletException {
        themeService = (ThemeService) getServletContext()
                .getAttribute("themeService");

    }

    private void processRequest(HttpServletRequest req, int themeId) {
        Theme editTheme = themeService.getThemeById(themeId);
        editTheme.setName(req.getParameter("name"));
        themeService.updateTheme(editTheme);
    }
}
