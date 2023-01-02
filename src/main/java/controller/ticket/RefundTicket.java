package controller.ticket;


import entity.User;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TicketService;
import service.UserService;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tickets/refund")
public class RefundTicket extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(RefundTicket.class);
    private TicketService ticketService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        ticketService = (TicketService) getServletContext().getAttribute("ticketService");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int ticketId = Integer.parseInt(req.getParameter("ticketid"));
            User user = (User) req.getSession().getAttribute("user");
            ticketService.refundTicket(ticketId);
            req.getSession().setAttribute("user", userService.getUserById(user.getId()));
            resp.sendRedirect((String) req.getSession().getAttribute("origin"));
        } catch (DBException | NumberFormatException e) {
            Utils.setErrorMessage(req, resp, e.getMessage());

        }
    }
}
