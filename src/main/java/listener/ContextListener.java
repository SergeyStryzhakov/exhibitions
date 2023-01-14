package listener;

import dao.*;
import dao.impl.*;
import service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        UserDAO userDAO = new UserDAOImpl();
        ExhibitionDAO exhibitionDAO = new ExhibitionDAOImpl();
        HallDAO hallDAO = new HallDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
        TicketDao ticketDao = new TicketDaoImpl();
        context.setAttribute("userService", new UserService(userDAO));
        context.setAttribute("exhibitionService", new ExhibitionService(
                exhibitionDAO, hallDAO, ticketDao, themeDAO));
        context.setAttribute("hallService", new HallService(hallDAO));
        context.setAttribute("themeService", new ThemeService(themeDAO));
        context.setAttribute("ticketService", new TicketService(
                userDAO, ticketDao, exhibitionDAO, hallDAO));
    }
}
