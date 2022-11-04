package dao.mapper;

import entity.Exhibition;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExhibitionMapper {

    public static Exhibition extractExhibition(ResultSet rs) throws SQLException {
        return new Exhibition.Builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .themeId(rs.getInt("theme_id"))
                .startDate(rs.getString("start_date"))
                .finishDate(rs.getString("finish_date"))
                .openTime(rs.getString("open_time"))
                .closeTime(rs.getString("close_time"))
                .price(rs.getInt("price"))
                .image(rs.getString("image"))
                .state(rs.getString("state"))
                .build();

    }
}
