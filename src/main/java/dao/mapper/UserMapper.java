package dao.mapper;

import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UserMapper {

    public static User extractUser(ResultSet rs) throws SQLException {
        return new User.Builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .pass(rs.getString("password"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .balance(Double.parseDouble(rs.getString("balance")))
                .role(User.Role.valueOf(rs.getString("role").toUpperCase(Locale.ROOT)))
                .build();

    }
}
