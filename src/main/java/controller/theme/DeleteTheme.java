package controller.theme;

import entity.User;
import exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ExhibitionService;
import service.ThemeService;
import utils.Utils;
import utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/themes/delete")
public class DeleteTheme extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTheme.class);
    private ThemeService themeService;

    @Override
    public void init() throws ServletException {
        themeService = (ThemeService) getServletContext()
                .getAttribute("themeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            if (Validation.isAdmin(user)) {
                int themeId = Integer.parseInt(req.getParameter("themeid"));
                themeService.deleteTheme(themeId);
                resp.sendRedirect(req.getContextPath() + "/themes/show");
            }
        } catch (LoginException ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
            Utils.setErrorMessage(req, resp, ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException("Try GET request");
    }


}
