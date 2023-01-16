package controller.ticket;

import entity.User;
import exception.DBException;
import exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TicketService;
import utils.Utils;
import utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tickets/delete")
public class DeleteTicket extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTicket.class);
    private TicketService ticketService;

    @Override
    public void init() throws ServletException {
        ticketService = (TicketService) getServletContext().getAttribute("ticketService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            if (Validation.isAdmin(user)) {
                String tid = req.getParameter("ticketid");
                int ticketId = Integer.parseInt(tid);
                ticketService.deleteTicket(ticketId);
                resp.sendRedirect(req.getContextPath() + "/tickets/show");
            }
        } catch (LoginException | DBException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
    }
}
