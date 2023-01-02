package dao.impl;

import dao.TicketDao;
import dao.UserDAO;
import dao.mapper.TicketMapper;
import entity.Ticket;
import entity.User;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketDaoImpl.class);
    private static final String FIND_ALL_TICKETS = "SELECT * FROM tickets";
    public static final String FIND_TICKET_BY_ID = "SELECT * FROM tickets WHERE id=?";
    public static final String FIND_TICKETS_BY_USER_ID = "SELECT * FROM tickets WHERE user_id=?";
    public static final String FIND_TICKETS_BY_HALL_ID = "SELECT * FROM tickets " +
            "WHERE hall_id=? AND exhibition_id=? AND state='ACTIVE'";
    public static final String DELETE_TICKET = "DELETE FROM tickets WHERE id=?";
    public static final String UPDATE_TICKET =
            "UPDATE tickets SET user_id=?, exhibition_id=?, hall_id=?, price=?, state=?, operation_date=? " +
                    "WHERE id=?";
    public static final String CREATE_TICKET =
            "INSERT INTO tickets (user_id, exhibition_id, hall_id, price, state, operation_date) " +
                    "VALUES (?,?,?,?,?,?)";
    public static final String UPDATE_BALANCE = "UPDATE users SET balance=? WHERE id=?";
    private UserDAO userDAO;

    @Override
    public Ticket create(Ticket ticket) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        userDAO = new UserDAOImpl();
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection
                    .prepareStatement(CREATE_TICKET,
                            Statement.RETURN_GENERATED_KEYS);
            fillTicketData(statement, ticket);
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                ticket.setId(rs.getInt(1));
            }
            double newBalance = changeBalance(
                    userDAO.findById(ticket.getUserId()), ticket.getPrice() * -1);
            statement = connection.prepareStatement(UPDATE_BALANCE);
            statement.setDouble(1, newBalance);
            statement.setInt(2, ticket.getUserId());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Ticket with id " + rs.getInt(1) + " created");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in create method " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAll() throws DBException {
        List<Ticket> tickets = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(FIND_ALL_TICKETS);
            while (rs.next()) {
                tickets.add(TicketMapper.extractTicket(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException("Error in findAll method: " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return tickets;
    }

    @Override
    public Ticket findById(int id) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Ticket ticket = new Ticket();
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_TICKET_BY_ID);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                ticket = TicketMapper.extractTicket(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findById method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return ticket;
    }

    @Override
    public List<Ticket> findTicketsByUserId(int userId) throws DBException {
        List<Ticket> tickets = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_TICKETS_BY_USER_ID);
            statement.setInt(1, userId);
            rs = statement.executeQuery();
            while (rs.next()) {
                tickets.add(TicketMapper.extractTicket(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findTicketsByUserId method with id " + userId + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return tickets;
    }

    @Override
    public List<Ticket> findTicketsByHallId(int hallId, int exhibitionId) throws DBException {
        List<Ticket> tickets = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_TICKETS_BY_HALL_ID);
            statement.setInt(1, hallId);
            statement.setInt(2, exhibitionId);
            rs = statement.executeQuery();
            while (rs.next()) {
                tickets.add(TicketMapper.extractTicket(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findTicketsByHallId method with id " + hallId + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }

        return tickets;
    }

    @Override
    public boolean delete(int id) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(DELETE_TICKET);
            statement.setInt(1, id);
            result = statement.execute();
            LOGGER.info("Deleted ticket id " + id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in deleteTicket method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
        return result;
    }

    @Override
    public int update(Ticket ticket) throws DBException {
        Connection connection = null;
        PreparedStatement statement = null;
        userDAO = new UserDAOImpl();
        int result;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_TICKET);
            int counter = fillTicketData(statement, ticket);
            statement.setInt(++counter, ticket.getId());
            result = statement.executeUpdate();
            double newBalance = changeBalance(
                    userDAO.findById(ticket.getUserId()), ticket.getPrice());
            statement = connection.prepareStatement(UPDATE_BALANCE);
            statement.setDouble(1, newBalance);
            statement.setInt(2, ticket.getUserId());
            statement.executeUpdate();
            connection.commit();
            LOGGER.info("Update " + result + " tickets");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in update method with id " + ticket.getId() + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
        return result;
    }

    private int fillTicketData(PreparedStatement statement,
                               Ticket ticket) throws SQLException {
        int counter = 0;
        statement.setInt(++counter, ticket.getUserId());
        statement.setInt(++counter, ticket.getExhibitionId());
        statement.setInt(++counter, ticket.getHallId());
        statement.setDouble(++counter, ticket.getPrice());
        statement.setString(++counter, ticket.getStateFromDb());
        statement.setTimestamp(++counter, ticket.getOperationDate());
        return counter;
    }

    private double changeBalance(User user, int balance) {
        return user.getBalance() + balance;
    }
}
