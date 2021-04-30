package com.example.practice.database;

import android.content.Context;
import android.util.Log;

import com.example.practice.bean.HotKeyHistoryBean;
import com.example.practice.config.Constants;
import com.example.practice.database.dao.HotKeyHistoryDao;
import com.example.practice.utils.ContextProvider;
import com.example.practice.utils.SharePrefUtil;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {HotKeyHistoryBean.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{

    private static AppDataBase instance;
    private static final String DBName = "wanAndroid_aac";

    public abstract HotKeyHistoryDao getHotKeyHistoryDao();

    public static AppDataBase getInstance(){
        if(instance == null){
            synchronized(AppDataBase.class){
                if(instance == null){
                    instance = createDB();
                }
            }
        }
        return instance;
    }

    private static AppDataBase createDB(){
        Context context = ContextProvider.get().getContext();
        if (context == null) {
            return null;
        }
        int userId = SharePrefUtil.getInt(ContextProvider.get().getContext(), Constants.USERID, 0);
        return Room.databaseBuilder(ContextProvider.get().getContext(), AppDataBase.class, DBName + userId + ".db").addCallback(new Callback(){
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db){
                super.onCreate(db);
                Log.d("AppDataBase", "oncreat");
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                Log.d("AppDataBase", "onOpen");
            }
        })/*.addMigrations(MIGRATION_1_2)*/.allowMainThreadQueries().build();//升级才会用到
    }

    static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS LessonVerBean(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "lesson_id INTEGER NOT NULL" +
                    ",version INTEGER NOT NULL,type INTEGER NOT NULL)");
        }
    };


}
