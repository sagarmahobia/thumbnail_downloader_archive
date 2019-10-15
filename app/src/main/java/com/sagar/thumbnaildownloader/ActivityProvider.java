package com.sagar.thumbnaildownloader;

import com.sagar.thumbnaildownloader.screens.downloader.DownloaderActivity;
import com.sagar.thumbnaildownloader.screens.downloader.DownloaderActivityModule;
import com.sagar.thumbnaildownloader.screens.downloader.DownloaderActivityScope;
import com.sagar.thumbnaildownloader.screens.main.MainActivity;
import com.sagar.thumbnaildownloader.screens.main.MainActivityModule;
import com.sagar.thumbnaildownloader.screens.main.MainActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityProvider {

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    @MainActivityScope
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {DownloaderActivityModule.class})
    @DownloaderActivityScope
    abstract DownloaderActivity bindDownloaderActivity();

}