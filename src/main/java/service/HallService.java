package service;

import dao.HallDAO;
import entity.Hall;
import exception.DBException;

import java.util.List;

public class HallService {
    private final HallDAO hallDAO;

    public HallService(HallDAO hallDAO) {
        this.hallDAO = hallDAO;
    }

    public Hall createHall(Hall entity) throws DBException {
        return hallDAO.create(entity);

    }

    public List<Hall> getHalls() throws DBException {
        return hallDAO.findAll();
    }

    public Hall getHallById(int id) throws DBException {
        return hallDAO.findById(id);
    }

    public void deleteHall(int id) throws DBException {
        hallDAO.delete(id);
    }

    public int updateHall(Hall hall) throws DBException {
        return hallDAO.update(hall);

    }

}
