package com.example.popularmovies.Utils;

import com.example.popularmovies.API.ApiInterface;
import com.example.popularmovies.API.ApiResults;
import com.example.popularmovies.Reviews.ReviewResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static final String API_KEY = "b115318eb98e234f64b273e765a1761e";
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static ApiInterface sMovieAPIInterface = retrofit.create(ApiInterface.class);

    // Use retrofit to build the API call.

    public static Call<ApiResults> buildAPICall(String sortOrder) {

        Call<ApiResults> call = sMovieAPIInterface.getMovieData(sortOrder, API_KEY);

        return call;
    }

    public static Call<com.example.popularmovies.Trailers.TrailerResponse> buildVideoCall(int videoId) {

        Call<com.example.popularmovies.Trailers.TrailerResponse> call = sMovieAPIInterface.getVideoData(videoId, API_KEY);

        return call;
    }

    public static Call<ReviewResponse> buildReviewCall(int videoId) {
        Call<ReviewResponse> call = sMovieAPIInterface.getReviewData(videoId, API_KEY);
        return call;
    }
}
