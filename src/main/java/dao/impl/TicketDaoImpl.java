package dao.impl;

import dao.TicketDao;
import dao.mapper.TicketMapper;
import entity.Ticket;
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
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Ticket create(Ticket ticket) {
        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(CREATE_TICKET,
                            Statement.RETURN_GENERATED_KEYS);
            fillTicketData(statement, ticket);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                ticket.setId(rs.getInt(1));
                LOGGER.info("Ticket with id " + rs.getInt(1) + " created");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            close(statement);
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_TICKETS);
            while (rs.next()) {
                tickets.add(TicketMapper.extractTicket(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return tickets;
    }

    @Override
    public Ticket findById(int id) {
        PreparedStatement statement = null;
        Ticket ticket = new Ticket();
        try {
            statement = connection.prepareStatement(FIND_TICKET_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ticket = TicketMapper.extractTicket(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return ticket;
    }

    @Override
    public List<Ticket> findTicketsByUserId(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(FIND_TICKETS_BY_USER_ID);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tickets.add(TicketMapper.extractTicket(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return tickets;
    }

    @Override
    public List<Ticket> findTicketsByHallId(int hallId, int exhibitionId) {
        List<Ticket> tickets = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(FIND_TICKETS_BY_HALL_ID);
            statement.setInt(1, hallId);
            statement.setInt(2, exhibitionId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tickets.add(TicketMapper.extractTicket(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } finally {
            close(statement);
        }

        return tickets;
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = connection.prepareStatement(DELETE_TICKET);
            statement.setInt(1, id);
            result = statement.execute();
            LOGGER.info("Deleted ticket id " + id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            close(statement);
        }
        return result;
    }

    @Override
    public int update(Ticket ticket) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = connection.prepareStatement(UPDATE_TICKET);
            int counter = fillTicketData(statement, ticket);
            statement.setInt(++counter, ticket.getId());
            result = statement.executeUpdate();
            LOGGER.info("Update " + result + " tickets");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            close(statement);
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
}
