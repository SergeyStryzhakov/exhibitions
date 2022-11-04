package controller.exhibition;

import entity.User;
import exception.DBException;
import exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ExhibitionService;
import utils.Utils;
import utils.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/exhibitions/delete")
public class DeleteExhibition extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteExhibition.class);
    private ExhibitionService exhibitionService;

    @Override
    public void init() throws ServletException {
        exhibitionService = (ExhibitionService) getServletContext()
                .getAttribute("exhibitionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            if (Validation.isAdmin(user)) {
                String exid = req.getParameter("exid");
                int exhibitionId = Integer.parseInt(exid);
                exhibitionService.deleteExhibition(exhibitionId);
                resp.sendRedirect(req.getContextPath() + "/exhibitions/show");
            }
        }catch (LoginException ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
            Utils.setErrorMessage(req, resp, ex.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException("Try GET request");
    }


}
