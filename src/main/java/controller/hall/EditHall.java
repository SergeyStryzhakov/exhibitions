package controller.hall;

import entity.Hall;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.HallService;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/halls/edit")
public class EditHall extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditHall.class);
    private HallService hallService;

    @Override
    public void init() throws ServletException {
        hallService = (HallService) getServletContext().getAttribute("hallService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int hallId = Integer.parseInt(req.getParameter("hallid"));
            req.setAttribute("hall", hallService.getHallById(hallId));
            LOGGER.info("Edit hall id " + hallId + " started (GET request)");
        } catch (DBException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/halls/edit_hall.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int hallId = Integer.parseInt(req.getParameter("hallid"));
            processRequest(req, hallId);
        } catch (DBException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/halls/show");
    }

    private void processRequest(HttpServletRequest req, int hallId) throws DBException {
        Hall editHall = hallService.getHallById(hallId);
        editHall.setName(req.getParameter("name"));
        editHall.setAddress(req.getParameter("address"));
        editHall.setCapacity(Integer.parseInt(req.getParameter("capacity")));
        hallService.updateHall(editHall);
    }
}
