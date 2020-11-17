package com.example.practice.database;

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
        }).addMigrations(MIGRATION_1_2).addMigrations(MIGRATION_2_1).
                addMigrations(MIGRATION_2_3).addMigrations(MIGRATION_3_4).allowMainThreadQueries().build();
    }

    static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS LessonVerBean");
            database.execSQL("CREATE TABLE IF NOT EXISTS LessonVerBean(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "lesson_id INTEGER NOT NULL" +
                    ",version INTEGER NOT NULL,type INTEGER NOT NULL)");
        }
    };

    static Migration MIGRATION_2_3 = new Migration(2, 3){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database){
            database.execSQL("CREATE TABLE IF NOT EXISTS SounExerciseBean(" + "exercise_id INTEGER NOT NULL," + "challenge_id INTEGER NOT NULL" + ",total_score INTEGER NOT NULL," + "PRIMARY KEY (exercise_id,challenge_id))");
        }
    };

    static Migration MIGRATION_3_4 = new Migration(3, 4){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database){
            database.execSQL("CREATE TABLE IF NOT EXISTS FollowReadExerciseBean(" + "scene_category_id INTEGER NOT NULL," + "follow_read_id INTEGER NOT NULL" + ",total_score INTEGER NOT NULL," + "PRIMARY KEY (scene_category_id,follow_read_id))");
        }
    };

    static Migration MIGRATION_2_1 = new Migration(2, 1){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database){
            database.execSQL("DROP TABLE IF EXISTS LessonVerBean");
        }
    };
}
