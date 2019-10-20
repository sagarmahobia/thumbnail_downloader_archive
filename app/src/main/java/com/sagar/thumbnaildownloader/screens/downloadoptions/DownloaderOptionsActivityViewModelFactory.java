package com.sagar.thumbnaildownloader.screens.downloadoptions;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sagar.thumbnaildownloader.network.repo.YoutubeRepository;

import javax.inject.Inject;

@DownloaderOptionsActivityScope
public class DownloaderOptionsActivityViewModelFactory implements ViewModelProvider.Factory {

    private YoutubeRepository youtubeRepository;

    @Inject
    DownloaderOptionsActivityViewModelFactory(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(DownloaderOptionsActivityViewModel.class)) {
            return (T) new DownloaderOptionsActivityViewModel(youtubeRepository);
        } else {
            throw new IllegalArgumentException("Can not find ViewModel class " + modelClass.getName());
        }
    }
}