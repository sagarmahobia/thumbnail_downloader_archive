package com.sagar.tumbnaildownloader.screens.downloader;

import androidx.lifecycle.ViewModel;

import com.sagar.tumbnaildownloader.network.repo.YoutubeRepository;

import io.reactivex.disposables.CompositeDisposable;

public class DownloaderActivityViewModel extends ViewModel {

    private final YoutubeRepository youtubeRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private DownloaderActivityModel activityModel = new DownloaderActivityModel();

    DownloaderActivityViewModel(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    DownloaderActivityModel getActivityModel() {
        return activityModel;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
