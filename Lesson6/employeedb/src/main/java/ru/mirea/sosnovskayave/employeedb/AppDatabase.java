package ru.mirea.sosnovskayave.employeedb;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {hero.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HeroDao heroDao();
}