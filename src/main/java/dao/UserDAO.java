package dao;

import entity.User;
import exception.DBException;

public interface UserDAO extends CommonDAO<User>{
    User findByLogin(String login) throws DBException;

}
