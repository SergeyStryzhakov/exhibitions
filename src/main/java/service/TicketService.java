package service;

import dao.ExhibitionDAO;
import dao.HallDAO;
import dao.TicketDao;
import dao.UserDAO;
import dao.impl.ExhibitionDAOImpl;
import dao.impl.HallDAOImpl;
import dao.impl.TicketDaoImpl;
import dao.impl.UserDAOImpl;
import dto.TicketDto;
import entity.Ticket;
import entity.TicketState;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);
    private UserDAO userDAO;
    private TicketDao ticketDao;
    private ExhibitionDAO exhibitionDAO;
    private HallDAO hallDAO;

    public TicketService() {
        userDAO = new UserDAOImpl();
        ticketDao = new TicketDaoImpl();
        exhibitionDAO = new ExhibitionDAOImpl();
        hallDAO = new HallDAOImpl();
    }

    public List<TicketDto> getAllTickets() {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ticketDao.setConnection(connection);
        List<TicketDto> tickets = new ArrayList<>();
        for (Ticket ticket : ticketDao.findAll()) {
            tickets.add(convertTicketToDto(ticket));
        }
        ConnectionPool.close(connection);
        return tickets;
    }

    public List<TicketDto> getTicketsByUserId(int userId) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ticketDao.setConnection(connection);
        List<TicketDto> tickets = new ArrayList<>();
        for (Ticket ticket : ticketDao.findTicketsByUserId(userId)) {
            tickets.add(convertTicketToDto(ticket));
        }
        ConnectionPool.close(connection);
        return tickets;
    }

    public void createTicket(Ticket ticket) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ticketDao.setConnection(connection);
        userDAO.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            ticketDao.create(ticket);
            User currentUser = userDAO.findById(ticket.getUserId());
            double reduceBalance = currentUser.getBalance() - ticket.getPrice();
            currentUser.setBalance(reduceBalance);
            userDAO.update(currentUser);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Rollback creating ticket");
                e.printStackTrace();
            }
            LOGGER.error(ex.getMessage(), ex.getCause());
            ex.printStackTrace();
        } finally {
            ConnectionPool.close(connection);
        }
    }

    public void refundTicket(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ticketDao.setConnection(connection);
        userDAO.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            Ticket ticket = ticketDao.findById(id);
            ticket.setStateFromDb(TicketState.REFUNDED.name());
            ticketDao.update(ticket);
            User currentUser = userDAO.findById(ticket.getUserId());
            currentUser.setBalance(currentUser.getBalance() + ticket.getPrice());
            userDAO.update(currentUser);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Rollback creating ticket");
                e.printStackTrace();
            }
            LOGGER.error(ex.getMessage(), ex.getCause());
            ex.printStackTrace();
        } finally {
            ConnectionPool.close(connection);
        }
    }

    public void deleteTicket(int ticketId) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ticketDao.setConnection(connection);
        ticketDao.delete(ticketId);
        ConnectionPool.close(connection);
    }


    private TicketDto convertTicketToDto(Ticket ticket) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        exhibitionDAO.setConnection(connection);
        hallDAO.setConnection(connection);
        userDAO.setConnection(connection);
        TicketDto dto = new TicketDto.Builder().id(ticket.getId())
                .user(userDAO.findById(ticket.getUserId()))
                .exhibition(exhibitionDAO.findById(ticket.getExhibitionId()))
                .hall(hallDAO.findById(ticket.getHallId()))
                .price(ticket.getPrice())
                .state(TicketState.valueOf(ticket.getStateFromDb()))
                .operationDate(ticket.getOperationDate())
                .build();
        ConnectionPool.close(connection);
        return dto;
    }

    private Ticket convertTicketFromDto(TicketDto dto) {
        return new Ticket.Builder()
                .id(dto.getId())
                .userId(dto.getUser().getId())
                .exhibitionId(dto.getExhibition().getId())
                .hallId(dto.getHall().getId())
                .price(dto.getPrice())
                .stateFromDb(dto.getState().name())
                .operationDate(dto.getOperationDate())
                .build();
    }


}
