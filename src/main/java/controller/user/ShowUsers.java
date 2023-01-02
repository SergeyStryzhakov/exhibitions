package controller.user;

import entity.User;
import exception.DBException;
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
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req);
        } catch (DBException | NumberFormatException e) {
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/users/users.jsp").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext()
                .getAttribute("userService");
    }

    private void processRequest(HttpServletRequest req) throws DBException {
        int page = Pagination.setPage(req);
        int itemsPerPage = Pagination.setItemsPerPage(req, "usersPerPage");
        List<User> users = userService.getUsers();
        req.getSession().setAttribute("users", Pagination.createListWithPagination(users, page, itemsPerPage));
        req.setAttribute("pagination", Pagination.setPagination(users, itemsPerPage));
        req.getSession().setAttribute("origin", req.getRequestURI());
    }
}
