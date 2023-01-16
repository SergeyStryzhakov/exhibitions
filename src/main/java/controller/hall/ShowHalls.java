package controller.hall;

import entity.Hall;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.HallService;
import utils.Pagination;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/halls/show")
public class ShowHalls extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowHalls.class);
    private HallService hallService;

    @Override
    public void init() throws ServletException {
        hallService = (HallService) getServletContext().getAttribute("hallService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req);
        } catch (DBException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/halls/halls.jsp")
                .forward(req, resp);
    }

    private void processRequest(HttpServletRequest req) throws DBException {
        int page = Pagination.setPage(req);
        int itemsPerPage = Pagination.setItemsPerPage(req, "hallsPerPage");
        List<Hall> halls = hallService.getHalls();
        req.setAttribute("halls", Pagination.createListWithPagination(halls, page, itemsPerPage));
        req.setAttribute("pagination", Pagination.setPagination(halls, itemsPerPage));
        req.getSession().setAttribute("origin", req.getRequestURI());
    }
}
