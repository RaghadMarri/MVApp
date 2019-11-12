package com.example.popularmovies.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<MovieData>> loadAllFavorites();

    @Query("SELECT * FROM favorites where id= :movie_id")
    LiveData<MovieData> loadMovieEntry(int movie_id);

    @Insert
    void insertFavorite(MovieData movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(MovieData movieEntry);

    @Delete
    void deleteFavorite(MovieData movieEntry);

}
