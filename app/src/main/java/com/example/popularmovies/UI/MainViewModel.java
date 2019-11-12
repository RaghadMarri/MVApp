package com.example.popularmovies.UI;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.popularmovies.API.ApiResults;
import com.example.popularmovies.Data.AppDatabase;
import com.example.popularmovies.Data.MovieData;
import com.example.popularmovies.Utils.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<MovieData>> favoriteMovies;
    private MutableLiveData<List<MovieData>> popularMovies = new MutableLiveData<>();
    private MutableLiveData<List<MovieData>> topRatedMovies = new MutableLiveData<>();
    private MutableLiveData<String> toastMessageObserver = new MutableLiveData();
    private String TAG = "MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        favoriteMovies = appDatabase.favoriteDao().loadAllFavorites();

        Call<ApiResults> call = Network.buildAPICall(Network.POPULAR);
        call.enqueue(new Callback<ApiResults>() {

            @Override
            public void onResponse(Call<ApiResults> call, Response<ApiResults> response) {

                if (response.message().contentEquals("OK")) {
                    popularMovies.setValue(response.body().getMovies());
                } else {

                    Log.e(TAG, "Something unexpected happened to our request: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResults> call, Throwable t) {
                toastMessageObserver.setValue("Something unexpected happened to our request: ");
                Log.i(TAG, "Something unexpected happened to our request: " + t.getMessage());
                Log.e(TAG, t.getMessage());


            }
        });

        Call<ApiResults> call2 = Network.buildAPICall(Network.TOP_RATED);
        call2.enqueue(new Callback<ApiResults>() {

            @Override
            public void onResponse(Call<ApiResults> call, Response<ApiResults> response) {

                if (response.message().contentEquals("OK")) {
                    topRatedMovies.setValue(response.body().getMovies());

                } else {
                    Log.e(TAG, "Something unexpected happened to our request: " + response.message());

                }
            }

            @Override
            public void onFailure(Call<ApiResults> call, Throwable t) {
                Log.i(TAG, "Something unexpected happened to our request: " );
                toastMessageObserver.setValue("Something unexpected happened to our request: "); // Whenever you want to show toast use setValue.
                Log.e(TAG, t.getMessage());

            }
        });

    }

    public LiveData<String> getToastObserver(){
        return toastMessageObserver;
    }

    public LiveData<List<MovieData>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public LiveData<List<MovieData>> getPopularMovies() {
        return popularMovies;
    }

    public LiveData<List<MovieData>> getTopRatedMovies() {
        return topRatedMovies;
    }


}