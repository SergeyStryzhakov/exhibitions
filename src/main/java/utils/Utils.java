package utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Utils {

    public static void setErrorMessage(HttpServletRequest req,
                                       HttpServletResponse resp,
                                       String msg) throws IOException, ServletException {
        req.getSession().setAttribute("errorMessage", msg);
        resp.sendRedirect(req.getContextPath() + "/error");
    }
}
