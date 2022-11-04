package service;

import dao.HallDAO;
import dao.impl.HallDAOImpl;
import entity.Hall;
import utils.ConnectionPool;

import java.sql.Connection;
import java.util.List;

public class HallService {
    private HallDAO hallDAO;

    public HallService() {
        hallDAO = new HallDAOImpl();
    }

    public Hall createHall(Hall entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        hallDAO.setConnection(connection);
        Hall hall = hallDAO.create(entity);
        ConnectionPool.close(connection);
        return hall;
    }

    public List<Hall> getHalls() {
        Connection connection = ConnectionPool.getInstance().getConnection();
        hallDAO.setConnection(connection);
        List<Hall> halls = hallDAO.findAll();
        ConnectionPool.close(connection);
        return halls;
    }

    public Hall getHallById(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        hallDAO.setConnection(connection);
        Hall hall = hallDAO.findById(id);
        ConnectionPool.close(connection);
        return hall;
    }

    public void deleteHall(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        hallDAO.setConnection(connection);
        hallDAO.delete(id);
        ConnectionPool.close(connection);
    }

    public int updateHall(Hall hall) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        hallDAO.setConnection(connection);
        int result = hallDAO.update(hall);
        ConnectionPool.close(connection);
        return result;
    }

}
