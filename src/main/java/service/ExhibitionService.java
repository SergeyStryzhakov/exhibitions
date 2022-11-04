package service;

import dao.ExhibitionDAO;
import dao.HallDAO;
import dao.ThemeDAO;
import dao.TicketDao;
import dao.impl.ExhibitionDAOImpl;
import dao.impl.HallDAOImpl;
import dao.impl.ThemeDAOImpl;
import dao.impl.TicketDaoImpl;
import dto.ExhibitionDto;
import entity.Exhibition;
import entity.ExhibitionState;
import entity.Hall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExhibitionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExhibitionService.class);
    private ExhibitionDAO exhibitionDAO;
    private HallDAO hallDAO;
    private TicketDao ticketDao;
    private ThemeDAO themeDAO;

    public ExhibitionService() {
        exhibitionDAO = new ExhibitionDAOImpl();
        hallDAO = new HallDAOImpl();
        ticketDao = new TicketDaoImpl();
        themeDAO = new ThemeDAOImpl();
    }

    public ExhibitionDto getExhibitionById(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        exhibitionDAO.setConnection(connection);
        Exhibition exhibition = exhibitionDAO.findById(id);
        ExhibitionDto dto = convertExhibitionToDto(exhibition);
        ConnectionPool.close(connection);
        return dto;
    }

    public List<ExhibitionDto> getExhibitionsByThemeId(int themeId) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<ExhibitionDto> exhibitions = new ArrayList<>();
        exhibitionDAO.setConnection(connection);
        for (Exhibition exhibition : exhibitionDAO.getExhibitionByThemeId(themeId)) {
            exhibitions.add(convertExhibitionToDto(exhibition));
        }
        ConnectionPool.close(connection);
        return exhibitions;
    }

    public List<ExhibitionDto> getAllExhibitions() {
        Connection connection = ConnectionPool.getInstance().getConnection();
        exhibitionDAO.setConnection(connection);
        List<ExhibitionDto> exhibitions = new ArrayList<>();
        for (Exhibition exhibition : exhibitionDAO.findAll()) {
            exhibitions.add(convertExhibitionToDto(exhibition));
        }
        ConnectionPool.close(connection);
        return exhibitions;
    }

    public int createExhibition(ExhibitionDto dto) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        exhibitionDAO.setConnection(connection);
        hallDAO.setConnection(connection);
        int id = 0;
        try {
            connection.setAutoCommit(false);
            id = exhibitionDAO.create(convertExhibitionFromDto(dto)).getId();
            hallDAO.reserveHallsForExhibition(id, dto.getHalls());
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                LOGGER.error("Rollback transaction during create exhibition" + e.getMessage());
                e.printStackTrace();
            } catch (SQLException ex) {
                LOGGER.error("Error during create exhibition" + ex);
            }
        } finally {
            ConnectionPool.close(connection);
        }
        return id;
    }

    public void deleteExhibition(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        exhibitionDAO.setConnection(connection);
        exhibitionDAO.delete(id);
        ConnectionPool.close(connection);
    }

    public void updateExhibition(ExhibitionDto dto) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        exhibitionDAO.setConnection(connection);
        hallDAO.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            exhibitionDAO.update(convertExhibitionFromDto(dto));
            hallDAO.reserveHallsForExhibition(dto.getId(), dto.getHalls());
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                LOGGER.error("Rollback transaction during edit exhibition" + e);
                e.printStackTrace();
            } catch (SQLException ex) {
                LOGGER.error("Error during edit exhibition" + ex);
            }
        } finally {
            ConnectionPool.close(connection);
        }
    }

    public Map<Integer, Integer> getAvailableTickets(int exid) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        ticketDao.setConnection(connection);
        Map<Integer, Integer> availableTickets = new HashMap<>();
        List<Hall> halls = getExhibitionById(exid).getHalls();
        for (Hall hall : halls) {
            int ticketsCount = ticketDao.findTicketsByHallId(hall.getId(), exid).size();
            availableTickets.put(hall.getId(), hall.getCapacity() - ticketsCount);
        }
        ConnectionPool.close(connection);
        return availableTickets;
    }

    private ExhibitionDto convertExhibitionToDto(Exhibition exhibition) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        themeDAO.setConnection(connection);
        hallDAO.setConnection(connection);
        ExhibitionDto dto = new ExhibitionDto.Builder()
                .id(exhibition.getId())
                .title(exhibition.getTitle())
                .description(exhibition.getDescription())
                .startDate(exhibition.getStartDate())
                .finishDate(exhibition.getFinishDate())
                .openTime(exhibition.getOpenTime())
                .closeTime(exhibition.getCloseTime())
                .price(exhibition.getPrice())
                .image(exhibition.getImage())
                .theme(themeDAO.findById(exhibition.getThemeId()))
                .halls(hallDAO.findByExhibitionId(exhibition.getId()))
                .state(ExhibitionState.valueOf(exhibition.getStateFromDb()))
                .build();
        ConnectionPool.close(connection);
        return dto;
    }

    private Exhibition convertExhibitionFromDto(ExhibitionDto dto) {
        return new Exhibition.Builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .themeId(dto.getTheme().getId())
                .startDate(dto.getStartDate())
                .finishDate(dto.getFinishDate())
                .openTime(dto.getOpenTime())
                .closeTime(dto.getCloseTime())
                .price(dto.getPrice())
                .image(dto.getImage())
                .state(dto.getState().name())
                .build();
    }
}
