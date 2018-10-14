package com.example.roy.gamebacklog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM gameBacklog")
    public List<gameObject> getAllGames();

    @Insert
    public void insertGame (gameObject game);

    @Delete
    public void deleteGame (gameObject game);

    @Update
    public void updateGame (gameObject game);
}
