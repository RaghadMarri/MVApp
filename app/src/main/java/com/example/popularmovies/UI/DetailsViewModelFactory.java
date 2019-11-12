package com.example.popularmovies.UI;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.example.popularmovies.Data.AppDatabase;

public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final int mId;

    public DetailsViewModelFactory(AppDatabase db, int id) {
        mDb = db;
        mId = id;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailsViewModel(mDb, mId);
    }
}