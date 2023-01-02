package service;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import exception.DBException;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAOImpl();
    }

    public User createUser(User entity) throws DBException {
        return userDAO.create(entity);
    }

    public List<User> getUsers() throws DBException {
        return userDAO.findAll();
    }

    public User getUserById(int id) throws DBException {
        return userDAO.findById(id);
    }

    public int updateUser(User entity) throws DBException {
        return userDAO.update(entity);
    }

    public void deleteUser(int id) throws DBException {
        userDAO.delete(id);
    }

    public User getUserByLogin(String login) throws DBException {
        return userDAO.findByLogin(login);
    }

}
