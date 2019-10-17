package com.sagar.thumbnaildownloader.screens.downloader;

import com.sagar.thumbnaildownloader.screens.downloader.imageadapter.ImageAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class DownloaderActivityModule {

    @Provides
    @DownloaderActivityScope
    ImageAdapter imageAdapter() {
        return new ImageAdapter();
    }
}
