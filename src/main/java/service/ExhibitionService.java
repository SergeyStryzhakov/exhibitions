package service;

import dao.ExhibitionDAO;
import dao.HallDAO;
import dao.ThemeDAO;
import dao.TicketDao;
import dto.ExhibitionDto;
import entity.Exhibition;
import entity.ExhibitionState;
import entity.Hall;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExhibitionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExhibitionService.class);
    private final ExhibitionDAO exhibitionDAO;
    private final HallDAO hallDAO;
    private final TicketDao ticketDao;
    private final ThemeDAO themeDAO;

    public ExhibitionService(ExhibitionDAO exhibitionDAO,
                             HallDAO hallDAO,
                             TicketDao ticketDao,
                             ThemeDAO themeDAO) {
        this.exhibitionDAO = exhibitionDAO;
        this.hallDAO = hallDAO;
        this.ticketDao = ticketDao;
        this.themeDAO = themeDAO;
    }

    public ExhibitionDto getExhibitionById(int id) throws DBException {
        return convertExhibitionToDto(exhibitionDAO.findById(id));
    }


    public List<ExhibitionDto> getExhibitionsByThemeId(int themeId) throws DBException {
        List<ExhibitionDto> exhibitions = new ArrayList<>();
        for (Exhibition exhibition : exhibitionDAO.getExhibitionByThemeId(themeId)) {
            exhibitions.add(convertExhibitionToDto(exhibition));
        }
        return exhibitions;
    }

    public List<ExhibitionDto> getExhibitionsByDate(String from, String to) throws DBException {
        List<ExhibitionDto> exhibitions = new ArrayList<>();
        for (Exhibition exhibition : exhibitionDAO.getExhibitionByDate(from, to)) {
            exhibitions.add(convertExhibitionToDto(exhibition));
        }
        return exhibitions;
    }

    public List<ExhibitionDto> getAllExhibitions(String sortParam) throws DBException {
        Map<String, Comparator<ExhibitionDto>> sortConfig = new HashMap<>();
        ExhibitionDto dto;
        sortConfig.put("id", Comparator.comparingInt(ExhibitionDto::getId));
        sortConfig.put("title", Comparator.comparing(ExhibitionDto::getTitle));
        sortConfig.put("price", Comparator.comparing(ExhibitionDto::getPrice));
        sortConfig.put("date", Comparator.comparing(ExhibitionDto::getStartDate));

        String sort = sortConfig.get(sortParam) != null ? sortParam : "id";

        List<ExhibitionDto> exhibitions = new ArrayList<>();
        for (Exhibition exhibition : exhibitionDAO.findAll()) {
            dto = convertExhibitionToDto(exhibition);
            updateExhibitionStateByFinishDate(dto);
            exhibitions.add(dto);
        }
        exhibitions = exhibitions
                .stream()
                .sorted(sortConfig.get(sort))
                .collect(Collectors.toList());
        return exhibitions;
    }

    public int createExhibition(ExhibitionDto dto) throws DBException {
        return exhibitionDAO.create(convertExhibitionFromDto(dto)).getId();
    }

    public void deleteExhibition(int id) throws DBException {
        exhibitionDAO.delete(id);
    }

    public void updateExhibition(ExhibitionDto dto) throws DBException {
        exhibitionDAO.update(convertExhibitionFromDto(dto));
    }

    public Map<Integer, Integer> getAvailableTickets(int exid) throws DBException {
        Map<Integer, Integer> availableTickets = new HashMap<>();
        List<Hall> halls = getExhibitionById(exid).getHalls();
        for (Hall hall : halls) {
            int ticketsCount = ticketDao.findTicketsByHallId(hall.getId(), exid).size();
            availableTickets.put(hall.getId(), hall.getCapacity() - ticketsCount);
        }
        return availableTickets;
    }

    private void updateExhibitionStateByFinishDate(ExhibitionDto exhibition) throws DBException {
        LocalDate nowDate = LocalDate.now();
        LocalDate exFinishDate;
        exFinishDate = LocalDate.parse(exhibition.getFinishDate());
        if (nowDate.isAfter(exFinishDate)
                && exhibition.getState().equals(ExhibitionState.ACTIVE)) {
            exhibition.setState(ExhibitionState.FINISHED);
            updateExhibition(exhibition);
            LOGGER.info("Change state to finished for exhibition with id "
                    + exhibition.getId());
        }

    }

    private ExhibitionDto convertExhibitionToDto(Exhibition exhibition) throws DBException {
        return new ExhibitionDto.Builder()
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
                .halls(dto.getHalls())
                .state(dto.getState().name())
                .build();
    }
}
