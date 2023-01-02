package controller.ticket;

import dto.ExhibitionDto;
import dto.TicketDto;
import entity.Ticket;
import entity.User;
import exception.DBException;
import exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.TicketService;
import utils.Pagination;
import utils.Utils;
import utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/tickets/show")
public class ShowTicket extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowTicket.class);
    private TicketService ticketService;

    @Override
    public void init() throws ServletException {
        ticketService = (TicketService) getServletContext()
                .getAttribute("ticketService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req);
            req.getRequestDispatcher("/WEB-INF/jsp/tickets/tickets.jsp").forward(req, resp);
        } catch (LoginException | DBException ex) {
            LOGGER.error(ex.getMessage());
            Utils.setErrorMessage(req, resp, ex.getMessage());
        }
    }

    private void processRequest(HttpServletRequest req) throws LoginException, DBException {
        User user = (User) req.getSession().getAttribute("user");
        int page = Pagination.setPage(req);
        int itemsPerPage = Pagination.setItemsPerPage(req, "ticketsPerPage");
        List<TicketDto> tickets = new ArrayList<>();
        if (Validation.isAuthUser(user)) {
            tickets = ticketService.getTicketsByUserId(user.getId());
        }
        if (Validation.isAdmin(user)) {
            tickets = ticketService.getAllTickets();
        }
        req.setAttribute("tickets", Pagination.createListWithPagination(tickets, page, itemsPerPage));
        req.setAttribute("pagination",
                Pagination.setPagination(tickets, itemsPerPage));
        req.getSession().setAttribute("origin", req.getRequestURI());
    }


}
