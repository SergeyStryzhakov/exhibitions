package dao;

import entity.Hall;
import exception.DBException;

import java.util.List;

public interface HallDAO extends CommonDAO<Hall> {
    List<Hall> findByExhibitionId(int id) throws DBException;
    //void reserveHallsForExhibition(int exId, List<Hall> halls) throws DBException;
    }
