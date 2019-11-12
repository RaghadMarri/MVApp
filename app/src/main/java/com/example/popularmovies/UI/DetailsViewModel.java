package com.example.popularmovies.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.popularmovies.Data.AppDatabase;

import com.example.popularmovies.Data.MovieData;

public class DetailsViewModel extends ViewModel {
    private LiveData<MovieData> favorite;

    public DetailsViewModel(AppDatabase db, int id) {
        favorite = db.favoriteDao().loadMovieEntry(id);
    }

    public LiveData<MovieData> getFavorite() {
        return favorite;
    }


}
