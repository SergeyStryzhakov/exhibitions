package controller;


import dto.ExhibitionDto;
import service.ExhibitionService;
import service.ThemeService;
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

@WebServlet(value = {"/main"})
public class MainServlet extends HttpServlet {
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
        processRequest(req);
        getServletContext()
                .getRequestDispatcher("/main.jsp")
                .forward(req, resp);
    }

    private void processRequest(HttpServletRequest req) {
        int page = Pagination.setPage(req);
        int itemsPerPage = Pagination.setItemsPerPage(req, "exhibitionsMainPage");
        String lang = setLanguage(req);
        List<ExhibitionDto> exhibitions;
        if (req.getParameter("topic") != null) {
            int themeId = Integer.parseInt(req.getParameter("topic"));
            exhibitions = exhibitionService.getExhibitionsByThemeId(themeId);
        } else {
            exhibitions = exhibitionService.getAllExhibitions();
        }
        req.setAttribute("ex",
                Pagination.createListWithPagination(exhibitions, page, itemsPerPage));
        req.setAttribute("pagination",
                Pagination.setPagination(exhibitions, itemsPerPage));
        req.setAttribute("themes", themeService.getThemes());
                req.getSession().setAttribute("lang", lang);
        req.getSession().setAttribute("origin", req.getRequestURI());
    }


    private String setLanguage(HttpServletRequest req) {
        return req.getParameter("lang") == null ?
                req.getSession().getAttribute("lang").toString() :
                req.getParameter("lang");
    }


}
