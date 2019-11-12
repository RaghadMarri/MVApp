package com.example.popularmovies.Trailers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("results")
    @Expose
    private List<Trailer> mResults = null;

    public List<Trailer> getVideoList() {
        return mResults;
    }

    public interface VideoAcquiredListener {
        void onVideosAcquired(List<Trailer> videos);
    }
}
