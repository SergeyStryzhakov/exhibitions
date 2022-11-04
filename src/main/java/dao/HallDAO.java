package dao;

import entity.Hall;

import java.util.List;

public interface HallDAO extends CommonDAO<Hall> {
    List<Hall> findByExhibitionId(int id);
    void reserveHallsForExhibition(int exId, List<Hall> halls);
    }
