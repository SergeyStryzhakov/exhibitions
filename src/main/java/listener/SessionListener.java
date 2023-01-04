package listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Pagination;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOGGER.info("Session id: " + se.getSession().getId());
        se.getSession().setAttribute("lang", "en");
        se.getSession().setAttribute("exhibitionsMainPage", Pagination.DEFAULT_EXHIBITIONS_MAIN_PAGE);
        se.getSession().setAttribute("exhibitionsPerPage", Pagination.DEFAULT_EXHIBITIONS_PER_PAGE);
        se.getSession().setAttribute("hallsPerPage", Pagination.DEFAULT_HALLS_PER_PAGE);
        se.getSession().setAttribute("ticketsPerPage", Pagination.DEFAULT_TICKETS_PER_PAGE);
        se.getSession().setAttribute("themesPerPage", Pagination.DEFAULT_THEMES_PER_PAGE);
        se.getSession().setAttribute("usersPerPage", Pagination.DEFAULT_USERS_PER_PAGE);
    }
}
