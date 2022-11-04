package listener;

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
        context.setAttribute("userService", new UserService());
        context.setAttribute("exhibitionService", new ExhibitionService());
        context.setAttribute("hallService", new HallService());
        context.setAttribute("themeService", new ThemeService());
        context.setAttribute("ticketService", new TicketService());
    }
}
