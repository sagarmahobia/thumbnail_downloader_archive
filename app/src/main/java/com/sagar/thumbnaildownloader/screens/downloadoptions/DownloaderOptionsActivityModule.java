package com.sagar.thumbnaildownloader.screens.downloadoptions;

import com.sagar.thumbnaildownloader.screens.downloadoptions.imageadapter.ImageAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class DownloaderOptionsActivityModule {

    @Provides
    @DownloaderOptionsActivityScope
    ImageAdapter imageAdapter() {
        return new ImageAdapter();
    }
}
