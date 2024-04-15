package ru.mirea.sosnovskayave.employeedb;

import android.app.Application;


import androidx.room.Room;

public class App extends Application {
    public static App instance;
    private AppDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "hero")
                .fallbackToDestructiveMigration()
                .build();
    }
    public static App getInstance() {
        return instance;
    }
    public AppDatabase getDatabase() {
        return database;
    }
}
