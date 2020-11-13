package com.example.practice.database.dao;

import com.example.practice.bean.HotKeyHistoryBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface HotKeyHistoryDao extends BaseDao<HotKeyHistoryBean> {
//    @Query("select * from hotkeyhistorybean where type_history = (:type) ")
    @Query("select * from hotkeyhistorybean ")
    List<HotKeyHistoryBean> queryList();

    @Query("Delete  from hotkeyhistorybean ")
    void deleteData();
}
