package controller.exhibition;

import dto.ExhibitionDto;
import entity.Exhibition;
import entity.User;
import exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ExhibitionService;
import service.HallService;
import service.ThemeService;
import utils.Pagination;
import utils.Utils;
import utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/exhibitions/show")
public class ShowExhibitions extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowExhibitions.class);
    private ExhibitionService exhibitionService;
    private ThemeService themeService;
    private HallService hallService;

    @Override
    public void init() throws ServletException {
        exhibitionService = (ExhibitionService) getServletContext()
                .getAttribute("exhibitionService");
        themeService = (ThemeService) getServletContext()
                .getAttribute("themeService");
        hallService = (HallService) getServletContext()
                .getAttribute("hallService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (req.getParameter("exid") != null) {
            processSingleExhibition(req);
            getServletContext()
                    .getRequestDispatcher("/WEB-INF/jsp/exhibitions/single.jsp")
                    .forward(req, resp);
            return;
        }
        try {
            if (Validation.isAdmin(user)) {
                processAdminRequest(req);
                getServletContext()
                        .getRequestDispatcher("/WEB-INF/jsp/exhibitions/exhibitions.jsp")
                        .forward(req, resp);
            }
        } catch (LoginException ex) {
            LOGGER.error(ex.getMessage());
            Utils.setErrorMessage(req, resp, ex.getMessage());
        }
    }

    private void processSingleExhibition(HttpServletRequest req) {
        int exId = Integer.parseInt(req.getParameter("exid"));
        Exhibition exhibition = exhibitionService.getExhibitionById(exId);
        Map<Integer, Integer> availableTickets = exhibitionService.getAvailableTickets(exId);
        req.setAttribute("ex", exhibition);
        req.setAttribute("available", availableTickets);
        req.getSession()
                .setAttribute("origin",
                        req.getRequestURI() + "?" + req.getQueryString());
    }

    private void processAdminRequest(HttpServletRequest req) {
        int page = Pagination.setPage(req);
        int itemsPerPage = Pagination.setItemsPerPage(req, "exhibitionsPerPage");
        List<ExhibitionDto> exhibitions = exhibitionService.getAllExhibitions();
        req.setAttribute("themes", themeService.getThemes());
        req.setAttribute("halls", hallService.getHalls());
        req.setAttribute("ex", Pagination
                .createListWithPagination(exhibitions, page, itemsPerPage));
        req.setAttribute("pagination",
                Pagination.setPagination(exhibitions, itemsPerPage));
                req.getSession().setAttribute("origin", req.getRequestURI());
    }
}
