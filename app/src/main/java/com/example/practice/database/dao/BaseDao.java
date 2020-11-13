package com.example.practice.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T item);

    @Insert
    void insertItems(List<T> items);

    @Delete
    void delete(T item);

    @Delete
    void deleteItems(T items);

}
