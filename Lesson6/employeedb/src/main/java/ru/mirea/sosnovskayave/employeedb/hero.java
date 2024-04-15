package ru.mirea.sosnovskayave.employeedb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class hero {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public int salary;
}