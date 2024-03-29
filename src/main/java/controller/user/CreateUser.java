package controller.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
import java.sql.SQLException;

@WebServlet("/users/create")
public class CreateUser extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext()
                .getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            processRequest(req);
        } catch (DBException e) {
            LOGGER.error(e.getMessage());
            Utils.setErrorMessage(req, resp, e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/main");

    }

    private void processRequest(HttpServletRequest req) throws DBException {
        User user = new User.Builder()
                .login(req.getParameter("login"))
                .pass(BCrypt.withDefaults()
                        .hashToString(12, req.getParameter("password").toCharArray()))
                .firstName(req.getParameter("first-name"))
                .lastName(req.getParameter("last-name"))
                .email(req.getParameter("email"))
                .role(User.Role.ROLE_USER)
                .build();
        userService.createUser(user);
        req.getSession().setAttribute("message", "User created successfully! Try login ->");
        LOGGER.info("User created successfully!");
    }

}
