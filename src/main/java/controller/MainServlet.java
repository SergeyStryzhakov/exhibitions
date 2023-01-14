package controller;

import dto.ExhibitionDto;
import entity.ExhibitionState;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ExhibitionService;
import service.ThemeService;
import service.TicketService;
import utils.Pagination;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainServlet.class);
    private ExhibitionService exhibitionService;
    private ThemeService themeService;

    @Override
    public void init() throws ServletException {
        exhibitionService = (ExhibitionService) getServletContext()
                .getAttribute("exhibitionService");
        themeService = (ThemeService) getServletContext()
                .getAttribute("themeService");
      }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req);
            req.getRequestDispatcher("/main.jsp")
                    .forward(req, resp);
        } catch (DBException e) {
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
    }

    private void processRequest(HttpServletRequest req) throws DBException {
        String sort = req.getParameter("sort");
        String topic = req.getParameter("topic");
        String fromDate = req.getParameter("from");
        String toDate = req.getParameter("to");
        int page = Pagination.setPage(req);
        int itemsPerPage = Pagination.setItemsPerPage(req, "exhibitionsMainPage");
        String lang = setLanguage(req);
        List<ExhibitionDto> exhibitions;
        if (topic != null) {
            int themeId = Integer.parseInt(topic);
            exhibitions = exhibitionService.getExhibitionsByThemeId(themeId);
        } else if (fromDate != "" && fromDate != null) {
            exhibitions = exhibitionService
                    .getExhibitionsByDate(fromDate, toDate);
        } else {
            exhibitions = exhibitionService.getAllExhibitions(sort);
        }
        exhibitions = exhibitions.stream()
                .filter(ex -> ex.getState().equals(ExhibitionState.ACTIVE))
                .collect(Collectors.toList());
        req.setAttribute("ex",
                Pagination.createListWithPagination(exhibitions, page, itemsPerPage));
        req.setAttribute("pagination",
                Pagination.setPagination(exhibitions, itemsPerPage));
        req.setAttribute("themes", themeService.getThemes());
        req.getSession().setAttribute("lang", lang);
        req.getSession().setAttribute("origin", req.getRequestURI() + "?" + req.getQueryString());
    }

    private String setLanguage(HttpServletRequest req) {
        return req.getParameter("lang") == null ?
                (String) req.getSession().getAttribute("lang") :
                req.getParameter("lang");
    }
}
