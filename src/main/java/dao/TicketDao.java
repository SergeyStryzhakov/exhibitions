package dao;

import entity.Ticket;
import exception.DBException;

import java.util.List;

public interface TicketDao extends CommonDAO<Ticket> {
    List<Ticket> findTicketsByUserId(int userId) throws DBException;
    List<Ticket> findTicketsByHallId(int hallId, int exhibitionId) throws DBException;
}
