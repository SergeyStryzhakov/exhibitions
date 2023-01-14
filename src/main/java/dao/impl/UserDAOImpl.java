package dao.impl;

import dao.UserDAO;
import dao.mapper.UserMapper;
import entity.User;
import exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
    public static final String FIND_ALL_USERS = "SELECT * FROM users";
    public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    public static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    public static final String UPDATE_USER =
            "UPDATE users SET login=?, password=?, first_name=?, last_name=?, email=?, balance=?, role=? WHERE id=?";
    public static final String CREATE_USER =
            "INSERT INTO users (login, password, first_name, last_name, email, balance, role) VALUES (?,?,?,?,?,?,?)";

    private Connection connection;

    @Override
    public User create(User user) throws DBException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection
                    .prepareStatement(CREATE_USER,
                            Statement.RETURN_GENERATED_KEYS);
            fillUser(statement, user);
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
                LOGGER.info("User with id " + rs.getInt(1) + " created");
            }
            return user;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in create method " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
    }

    @Override
    public List<User> findAll() throws DBException {
        Statement statement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(FIND_ALL_USERS);
            while (rs.next()) {
                users.add(UserMapper.extractUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findAll method " + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return users;
    }

    @Override
    public User findById(int id) throws DBException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            while (rs.next()) {
                user = UserMapper.extractUser(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findById method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return user;
    }

    @Override
    public boolean delete(int id) throws DBException {
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(DELETE_USER);
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in delete method with id " + id + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
    }

    @Override
    public int update(User user) throws DBException {
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(UPDATE_USER);
            int counter = fillUser(statement, user);
            statement.setInt(++counter, user.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in update method with id " + user.getId() + " : "
                            + e.getMessage());
        } finally {
            close(null, statement, connection);
        }
    }

    @Override
    public User findByLogin(String login) throws DBException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            rs = statement.executeQuery();
            while (rs.next()) {
                user = UserMapper.extractUser(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DBException(
                    "Error in findByLogin method with login " + login + " : "
                            + e.getMessage());
        } finally {
            close(rs, statement, connection);
        }
        return user;
    }

    private int fillUser(PreparedStatement statement, User user) throws SQLException {
        int counter = 0;
        statement.setString(++counter, user.getLogin());
        statement.setString(++counter, user.getPass());
        statement.setString(++counter, user.getFirstName());
        statement.setString(++counter, user.getLastName());
        statement.setString(++counter, user.getEmail());
        statement.setDouble(++counter, user.getBalance());
        statement.setString(++counter, user.getRole().name());
        return counter;
    }
}
