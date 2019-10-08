package com.sagar.tumbnaildownloader.screens.main;

import androidx.lifecycle.ViewModel;

import com.sagar.tumbnaildownloader.network.repo.YoutubeRepository;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {

    private final YoutubeRepository youtubeRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private MainActivityModel activityModel = new MainActivityModel();

    MainActivityViewModel(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    MainActivityModel getActivityModel() {
        return activityModel;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
