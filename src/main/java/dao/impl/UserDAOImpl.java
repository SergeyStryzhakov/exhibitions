package dao.impl;

import dao.UserDAO;
import dao.mapper.UserMapper;
import entity.User;
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
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User create(User user) {
        PreparedStatement statement = null;
        try {
            statement = connection
                    .prepareStatement(CREATE_USER,
                            Statement.RETURN_GENERATED_KEYS);
            fillUser(statement, user);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
                LOGGER.info("User with id " + rs.getInt(1) + " created");
            }
            return user;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(statement);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        Statement statement = null;
        List<User> users = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(FIND_ALL_USERS);
            while (rs.next()) {
                users.add(UserMapper.extractUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(statement);
        }
        return users;
    }

    @Override
    public User findById(int id) {
        PreparedStatement statement = null;
        User user = null;
        try {
            statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = UserMapper.extractUser(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(statement);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_USER);
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(statement);
        }
        return false;
    }

    @Override
    public int update(User user) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_USER);
            int counter = fillUser(statement, user);
            statement.setInt(++counter, user.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(statement);
        }
        return 0;
    }

    @Override
    public User findByLogin(String login) {
        PreparedStatement statement = null;
        User user = null;
        try {
            statement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = UserMapper.extractUser(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            close(statement);
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
