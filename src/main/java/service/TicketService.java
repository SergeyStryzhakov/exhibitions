package service;

import dao.ExhibitionDAO;
import dao.HallDAO;
import dao.TicketDao;
import dao.UserDAO;
import dto.TicketDto;
import entity.Ticket;
import entity.TicketState;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);
    private final UserDAO userDAO;
    private final TicketDao ticketDao;
    private final ExhibitionDAO exhibitionDAO;
    private final HallDAO hallDAO;

    public TicketService(UserDAO userDAO,
                         TicketDao ticketDao,
                         ExhibitionDAO exhibitionDAO,
                         HallDAO hallDAO) {
        this.userDAO = userDAO;
        this.ticketDao = ticketDao;
        this.exhibitionDAO = exhibitionDAO;
        this.hallDAO = hallDAO;
    }

    public List<TicketDto> getAllTickets() throws DBException {
        List<TicketDto> tickets = new ArrayList<>();
        for (Ticket ticket : ticketDao.findAll()) {
            tickets.add(convertTicketToDto(ticket));
        }
        return tickets;
    }

    public List<TicketDto> getTicketsByUserId(int userId) throws DBException {
        List<TicketDto> tickets = new ArrayList<>();
        for (Ticket ticket : ticketDao.findTicketsByUserId(userId)) {
            tickets.add(convertTicketToDto(ticket));
        }

        return tickets;
    }

    public void createTicket(Ticket ticket) throws DBException {
        ticketDao.create(ticket);
    }

    public void refundTicket(int id) throws DBException {
        Ticket ticket = ticketDao.findById(id);
        ticket.setStateFromDb(TicketState.REFUNDED.name());
        ticketDao.update(ticket);
    }

    public void deleteTicket(int ticketId) throws DBException {
        ticketDao.delete(ticketId);

    }


    private TicketDto convertTicketToDto(Ticket ticket) throws DBException {
        TicketDto dto = new TicketDto.Builder().id(ticket.getId())
                .user(userDAO.findById(ticket.getUserId()))
                .exhibition(exhibitionDAO.findById(ticket.getExhibitionId()))
                .hall(hallDAO.findById(ticket.getHallId()))
                .price(ticket.getPrice())
                .state(TicketState.valueOf(ticket.getStateFromDb()))
                .operationDate(ticket.getOperationDate())
                .build();
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
