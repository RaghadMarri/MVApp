package com.example.popularmovies.API;

import com.example.popularmovies.Data.MovieData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResults {

    @SerializedName("results")
    @Expose
    private List<MovieData> mResults;

    public interface DataAcquiredListener {
        void onMovieDataAcquired(List<MovieData> movies);
    }

    public List<MovieData> getMovies() {
        return mResults;
    }
}
