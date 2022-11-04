package dao;

import entity.Exhibition;

import java.util.List;

public interface ExhibitionDAO extends CommonDAO<Exhibition> {
    List<Exhibition> getExhibitionByThemeId(int id) ;

}
