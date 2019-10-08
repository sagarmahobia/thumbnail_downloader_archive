package com.sagar.tumbnaildownloader;

import com.sagar.tumbnaildownloader.screens.downloader.DownloaderActivity;
import com.sagar.tumbnaildownloader.screens.downloader.DownloaderActivityModule;
import com.sagar.tumbnaildownloader.screens.downloader.DownloaderActivityScope;
import com.sagar.tumbnaildownloader.screens.main.MainActivity;
import com.sagar.tumbnaildownloader.screens.main.MainActivityModule;
import com.sagar.tumbnaildownloader.screens.main.MainActivityScope;

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