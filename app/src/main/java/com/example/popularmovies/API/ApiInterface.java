package com.example.popularmovies.API;

import com.example.popularmovies.Reviews.ReviewResponse;
import com.example.popularmovies.Trailers.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("{sortOrder}")
    Call<ApiResults> getMovieData(
            @Path("sortOrder") String sortOrder,
            @Query("api_key") String apiKey
    );


    @GET("{id}/videos")
    Call<TrailerResponse> getVideoData(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("{id}/reviews")
    Call<ReviewResponse> getReviewData(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );
}
