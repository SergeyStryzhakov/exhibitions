package dao.mapper;

import entity.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMapper {
    public static Theme extractTheme(ResultSet rs) throws SQLException {
        return new Theme.Builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
