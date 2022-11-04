package utils;

import entity.User;
import exception.LoginException;

public class Validation {

    public static boolean isAdmin(User user) throws LoginException {
        if (user == null) throw new LoginException("Access denied! Please, logged in");
        return user.getRole() == User.Role.ROLE_ADMIN;
    }

    public static boolean isAuthUser(User user) throws LoginException {
        if (user == null) throw new LoginException("Access denied! Please, logged in");
        return user.getRole() == User.Role.ROLE_USER;
    }


}
