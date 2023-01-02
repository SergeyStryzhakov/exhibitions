package dao;

import entity.Exhibition;
import exception.DBException;

import java.util.List;

public interface ExhibitionDAO extends CommonDAO<Exhibition> {
    List<Exhibition> getExhibitionByThemeId(int id) throws DBException;
    List<Exhibition> getExhibitionByDate(String from, String to) throws DBException;

}
