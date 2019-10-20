package com.sagar.thumbnaildownloader.screens.download;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sagar.thumbnaildownloader.network.repo.YoutubeRepository;

import javax.inject.Inject;

@DownloadActivityScope
public class DownloadActivityViewModelFactory implements ViewModelProvider.Factory {

    private YoutubeRepository youtubeRepository;

    @Inject
    DownloadActivityViewModelFactory(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(DownloadActivityViewModel.class)) {
            return (T) new DownloadActivityViewModel(youtubeRepository);
        } else {
            throw new IllegalArgumentException("Can not find ViewModel class " + modelClass.getName());
        }
    }
}


