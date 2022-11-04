package controller.user;

import entity.User;
import exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.Utils;
import utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/delete")
public class DeleteUser extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteUser.class);
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            if (Validation.isAdmin(user)) {
                String uid = req.getParameter("uid");
                int userId = Integer.parseInt(uid);
                userService.deleteUser(userId);
                resp.sendRedirect(req.getContextPath() + "/users/show");
            }
        } catch (LoginException ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
            Utils.setErrorMessage(req, resp, ex.getMessage());
        }
    }

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext()
                .getAttribute("userService");
    }
}
