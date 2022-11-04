package controller.user;

import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/edit")
public class EditUser extends HttpServlet {
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = req.getParameter("uid");
        int userId = Integer.parseInt(uid);
        req.setAttribute("editUser", userService.getUserById(userId));
        req.setAttribute("roles", User.Role.values());
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/users/edit_user.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("uid"));
        User editUser = userService.getUserById(userId);
        editUser.setLogin(req.getParameter("login"));
        editUser.setFirstName(req.getParameter("first-name"));
        editUser.setLastName(req.getParameter("last-name"));
        editUser.setEmail(req.getParameter("email"));
        editUser.setBalance(Double.parseDouble(req.getParameter("balance")));
        editUser.setRole(User.Role.valueOf(req.getParameter("role")));
        userService.updateUser(editUser);
        resp.sendRedirect(req.getContextPath() + "/users/show");
    }

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext()
                .getAttribute("userService");
    }
}
