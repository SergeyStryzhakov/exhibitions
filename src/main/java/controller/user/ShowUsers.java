package controller.user;

import entity.User;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.Pagination;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/show")
public class ShowUsers extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowUsers.class);
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext()
                .getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            processRequest(req);
        } catch (DBException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/users/users.jsp").forward(req, resp);
    }

    private void processRequest(HttpServletRequest req) throws DBException {
        int page = Pagination.setPage(req);
        int itemsPerPage = Pagination.setItemsPerPage(req, "usersPerPage");
        List<User> users = userService.getUsers();
        req.getSession().setAttribute("users",
                Pagination.createListWithPagination(users, page, itemsPerPage));
        req.setAttribute("pagination", Pagination.setPagination(users, itemsPerPage));
        req.getSession().setAttribute("origin", req.getRequestURI());
    }
}
