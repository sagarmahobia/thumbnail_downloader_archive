package com.sagar.thumbnaildownloader.screens.main;

import com.sagar.thumbnaildownloader.screens.main.videoadapter.VideoAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @MainActivityScope
    @Provides
    VideoAdapter videoAdapter(){
        return  new VideoAdapter();
    }

}
