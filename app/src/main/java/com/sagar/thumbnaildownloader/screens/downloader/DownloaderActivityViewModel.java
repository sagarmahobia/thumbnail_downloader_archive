package com.sagar.thumbnaildownloader.screens.downloader;

import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;

import com.sagar.thumbnaildownloader.network.repo.YoutubeRepository;

import java.util.ArrayList;

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

    public static final ArrayList<String> urls = new ArrayList<>();

    static {
        urls.add("https://img.youtube.com/vi/{}/sddefault.jpg");
        urls.add("https://img.youtube.com/vi/{}/default.jpg");
        urls.add("https://img.youtube.com/vi/{}/mqdefault.jpg");
        urls.add("https://img.youtube.com/vi/{}/hqdefault.jpg");
        urls.add("https://img.youtube.com/vi/{}/maxresdefault.jpg");
    }
}
