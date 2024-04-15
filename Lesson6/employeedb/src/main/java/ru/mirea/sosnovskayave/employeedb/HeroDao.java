package ru.mirea.sosnovskayave.employeedb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HeroDao {
    @Query("SELECT * FROM hero")
    List<hero> getAll();
    @Query("SELECT * FROM hero WHERE id = :id")
    hero getById(long id);
    @Insert
    void insert(hero hero);
    @Update
    void update(hero hero);
    @Delete
    void delete(hero hero);

}
