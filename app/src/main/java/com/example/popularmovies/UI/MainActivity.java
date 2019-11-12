package com.example.popularmovies.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.popularmovies.Data.AppDatabase;
import com.example.popularmovies.Data.MovieData;
import com.example.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String SORT_ORDER = "SortOrder";
    private static final String FAVORITE = "favorite";
    private static final String RECYCLER_POSITION = "RecyclerViewPosition";
    private AppDatabase mDb;

    private SharedPreferences mSharedPreferences;

    private String mSortOrder;

    private com.example.popularmovies.Movies.MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private Parcelable recyclerPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mRecyclerView = findViewById(R.id.recyclerView);



          mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        mMovieAdapter = new com.example.popularmovies.Movies.MovieAdapter(getApplicationContext(), new ArrayList<MovieData>());
        mRecyclerView.setAdapter(mMovieAdapter);

        mSortOrder = mSharedPreferences.getString(SORT_ORDER, com.example.popularmovies.Utils.Network.POPULAR);

        mDb = AppDatabase.getInstance(getApplicationContext());

        setupViewModel();


    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_POSITION,
                mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(RECYCLER_POSITION)) {
            recyclerPosition = savedInstanceState.getParcelable(RECYCLER_POSITION);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);

        MenuItem popular = menu.getItem(0);
        MenuItem top_rated = menu.getItem(1);
        MenuItem favorite = menu.getItem(2);


        // Check the correct radio button in the menu.
        switch (mSortOrder) {
            case FAVORITE:
                favorite.setChecked(true);
                break;
            case com.example.popularmovies.Utils.Network.TOP_RATED:
                top_rated.setChecked(true);
                break;
            case com.example.popularmovies.Utils.Network.POPULAR:
                popular.setChecked(true);
                break;
            default:
                popular.setChecked(true);
                break;
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.menu_item_popular:
                mSortOrder = com.example.popularmovies.Utils.Network.POPULAR;
                break;
            case R.id.menu_item_top_rated:
                mSortOrder = com.example.popularmovies.Utils.Network.TOP_RATED;
                break;
            case R.id.menu_item_favorite:
                mSortOrder = FAVORITE;
                break;
            default:
                return false;
        }

        editor.putString(SORT_ORDER, mSortOrder);
        editor.apply();
        setupViewModel();
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (mSortOrder.contentEquals(FAVORITE)) {
            setupViewModel();
        }
    }

    public void showMessage(String mtoaster){
        Toast.makeText(MainActivity.this,mtoaster, Toast.LENGTH_SHORT).show();
    }
    public void setupViewModel() {


        com.example.popularmovies.UI.MainViewModel viewModel = ViewModelProviders.of(this).get(com.example.popularmovies.UI.MainViewModel.class);


        viewModel.getToastObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message)
        {
                 showMessage(message);


        } });


        viewModel.getFavoriteMovies().observe(this, new Observer<List<MovieData>>() {
            @Override
            public void onChanged(@Nullable List<com.example.popularmovies.Data.MovieData> favoriteEntries) {
                Log.d(TAG, "Receiving changes from LiveData");

                if (mSortOrder.contentEquals(FAVORITE)) {
                    List<com.example.popularmovies.Data.MovieData> movieList = new ArrayList<com.example.popularmovies.Data.MovieData>();




                    if (favoriteEntries != null) {

                        for (com.example.popularmovies.Data.MovieData fave : favoriteEntries) {
                            fave.setFavorite(1);
                        }
                        setAdapter(favoriteEntries);
                    }
                }
            }
        });

        viewModel.getTopRatedMovies().observe(this, new Observer<List<com.example.popularmovies.Data.MovieData>>() {
            @Override
            public void onChanged(@Nullable List<com.example.popularmovies.Data.MovieData> movieData) {
                Log.i("Test",""+ movieData);


                if (movieData != null && mSortOrder.contentEquals(com.example.popularmovies.Utils.Network.TOP_RATED)) {
                    setAdapter(movieData);
                }
            }
        });

        viewModel.getPopularMovies().observe(this, new Observer<List<com.example.popularmovies.Data.MovieData>>() {

            @Override
            public void onChanged(@Nullable List<com.example.popularmovies.Data.MovieData> movieData) {
                Log.i("Test",""+ movieData);
                if (movieData != null && mSortOrder.contentEquals(com.example.popularmovies.Utils.Network.POPULAR)) {
                    setAdapter(movieData);
                }
            }
        });
    }

    public void setAdapter(List<com.example.popularmovies.Data.MovieData> movies) {
        mMovieAdapter.setMovies(movies);
        mMovieAdapter.notifyDataSetChanged();
        if (recyclerPosition != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerPosition);
        }
    }

}
