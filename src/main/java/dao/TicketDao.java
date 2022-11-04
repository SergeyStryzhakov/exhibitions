package dao;

import entity.Ticket;

import java.util.List;

public interface TicketDao extends CommonDAO<Ticket> {
    List<Ticket> findTicketsByUserId(int userId);
    List<Ticket> findTicketsByHallId(int hallId, int exhibitionId);
}
