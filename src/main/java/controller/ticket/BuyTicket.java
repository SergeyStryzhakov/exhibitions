package controller.ticket;

import entity.Ticket;
import entity.User;
import exception.DBException;
import exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TicketService;
import service.UserService;
import utils.Utils;
import utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/tickets/buy")
public class BuyTicket extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyTicket.class);
    private TicketService ticketService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        ticketService = (TicketService) getServletContext()
                .getAttribute("ticketService");
        userService = (UserService) getServletContext()
                .getAttribute("userService");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (LoginException | DBException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }

    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws LoginException, DBException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int hallId = Integer.parseInt(req.getParameter("hid"));
        int exhibitionId = Integer.parseInt(req.getParameter("exid"));
        int price = Integer.parseInt(req.getParameter("price"));
        if (Validation.isAuthUser(user)) {
            Ticket ticket = new Ticket();
            ticket.setUserId(user.getId());
            ticket.setExhibitionId(exhibitionId);
            ticket.setHallId(hallId);
            ticket.setPrice(price);
            ticket.setOperationDate(new Timestamp(System.currentTimeMillis()));
            ticketService.createTicket(ticket);
            req.getSession().setAttribute("user", userService.getUserById(user.getId()));
        }
        resp.sendRedirect(req.getContextPath() + "/exhibitions/show?exid=" + exhibitionId);
    }


}
