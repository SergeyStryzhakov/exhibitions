package dao;

import entity.User;

public interface UserDAO extends CommonDAO<User>{
    User findByLogin(String login);

}
