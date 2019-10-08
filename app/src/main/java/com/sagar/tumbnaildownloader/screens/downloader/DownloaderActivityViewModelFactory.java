package com.sagar.tumbnaildownloader.screens.downloader;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sagar.tumbnaildownloader.network.repo.YoutubeRepository;

import javax.inject.Inject;

@DownloaderActivityScope
public class DownloaderActivityViewModelFactory implements ViewModelProvider.Factory {

    private YoutubeRepository youtubeRepository;

    @Inject
    DownloaderActivityViewModelFactory(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(DownloaderActivityViewModel.class)) {
            return (T) new DownloaderActivityViewModel(youtubeRepository);
        } else {
            throw new IllegalArgumentException("Can not find ViewModel class " + modelClass.getName());
        }
    }
}