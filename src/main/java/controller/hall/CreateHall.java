package controller.hall;

import entity.Hall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.HallService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/halls/create")
public class CreateHall extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateHall.class);
    private HallService hallService;

    @Override
    public void init() throws ServletException {
        hallService = (HallService) getServletContext().getAttribute("hallService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Create hall started (GET request)");
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/halls/create_hall.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req);
        resp.sendRedirect(req.getContextPath() + "/halls/show");
    }

    private void processRequest(HttpServletRequest req) {
        Hall hall = new Hall.Builder()
                .name(req.getParameter("name"))
                .address(req.getParameter("address"))
                .capacity(Integer.parseInt(req.getParameter("capacity")))
                .build();
        hallService.createHall(hall);
    }
}
