package com.example.roy.gamebacklog;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {gameObject.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GameDao gameDao();

    private final static String NAME_DATABASE = "gamebacklog_db";

    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {

        if(sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE).allowMainThreadQueries().build();
        }
        return sInstance;
    }

}
