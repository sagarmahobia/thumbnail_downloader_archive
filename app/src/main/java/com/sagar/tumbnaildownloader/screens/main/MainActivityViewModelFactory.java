package com.sagar.tumbnaildownloader.screens.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sagar.tumbnaildownloader.network.repo.YoutubeRepository;

import javax.inject.Inject;

@MainActivityScope
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private YoutubeRepository youtubeRepository;

    @Inject
    MainActivityViewModelFactory(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(youtubeRepository);
        } else {
            throw new IllegalArgumentException("Can not find ViewModel class " + modelClass.getName());
        }
    }
}
