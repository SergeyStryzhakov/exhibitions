package dao.mapper;

import entity.Hall;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HallMapper {
    public static Hall extractHall(ResultSet rs) throws SQLException {
        return new Hall.Builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .address(rs.getString("address"))
                .capacity(Integer.parseInt(rs.getString("capacity")))
                .build();
    }
}
