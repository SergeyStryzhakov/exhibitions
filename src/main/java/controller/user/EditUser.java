package controller.user;

import entity.User;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/edit")
public class EditUser extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditUser.class);
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext()
                .getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uid = req.getParameter("uid");
        try {
            int userId = Integer.parseInt(uid);
            req.setAttribute("editUser", userService.getUserById(userId));
            req.setAttribute("roles", User.Role.values());
        } catch (DBException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/users/edit_user.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            processRequest(req);
        } catch (DBException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/users/show");
    }

    private void processRequest(HttpServletRequest req) throws DBException {
        int userId = Integer.parseInt(req.getParameter("uid"));
        User editUser = userService.getUserById(userId);
        editUser.setLogin(req.getParameter("login"));
        editUser.setFirstName(req.getParameter("first-name"));
        editUser.setLastName(req.getParameter("last-name"));
        editUser.setEmail(req.getParameter("email"));
        editUser.setBalance(Double.parseDouble(req.getParameter("balance")));
        editUser.setRole(User.Role.valueOf(req.getParameter("role")));
        userService.updateUser(editUser);
    }


}
