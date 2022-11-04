package service;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import utils.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAOImpl();
    }

    public User createUser(User entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.create(entity);
        ConnectionPool.close(connection);
        return user;
    }

    public List<User> getUsers() {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDAO.setConnection(connection);
        List<User> users = userDAO.findAll();
        ConnectionPool.close(connection);
        return users;
    }

    public User getUserById(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.findById(id);
        ConnectionPool.close(connection);
        return user;
    }

    public int updateUser(User entity) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDAO.setConnection(connection);
        int result = userDAO.update(entity);
        ConnectionPool.close(connection);
        return result;
    }

    public void deleteUser(int id) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDAO.setConnection(connection);
        userDAO.delete(id);
        ConnectionPool.close(connection);
    }

    public User getUserByLogin(String login) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.findByLogin(login);
        ConnectionPool.close(connection);
        return user;
    }

}
