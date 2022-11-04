package controller.login;

import at.favre.lib.crypto.bcrypt.BCrypt;
import entity.User;
import exception.LoginException;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext()
                .getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            LOGGER.info(req.getSession().getAttribute("user").toString() + " logged out!");
            //req.getSession().invalidate();
            req.getSession().removeAttribute("user");
        }
        resp.sendRedirect(req.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String origin = req.getSession().getAttribute("origin").toString();
        String login = req.getParameter("login");
        User user = userService.getUserByLogin(login);
        try {
            if (user == null) {
                throw new LoginException("Login not found. Try again!");
            } else if (!BCrypt.verifyer().verify(
                    req.getParameter("password").toCharArray(),
                    user.getPass()).verified) {
                throw new LoginException("Incorrect password! Try again!");
            } else {
                req.getSession().setAttribute("user", user);
            }
        } catch (LoginException ex) {
            LOGGER.error(ex.getMessage());
            Utils.setErrorMessage(req, resp, ex.getMessage());
            return;
        }
        resp.sendRedirect(origin == null ? req.getContextPath() : origin);
    }
}
