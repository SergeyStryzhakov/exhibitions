package dao.mapper;

import entity.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper {
    public static Ticket extractTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setUserId(rs.getInt("user_id"));
        ticket.setExhibitionId(rs.getInt("exhibition_id"));
        ticket.setHallId(rs.getInt("hall_id"));
        ticket.setPrice(rs.getInt("price"));
        ticket.setStateFromDb(rs.getString("state"));
        ticket.setOperationDate(rs.getTimestamp("operation_date"));
        return ticket;

    }
}
