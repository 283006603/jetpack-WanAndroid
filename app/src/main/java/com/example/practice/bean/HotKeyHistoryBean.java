package com.example.practice.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HotKeyHistoryBean{
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "hot_name_history")
    public String hot_name_history;

    @ColumnInfo(name = "type_history")
    public int type;
}
