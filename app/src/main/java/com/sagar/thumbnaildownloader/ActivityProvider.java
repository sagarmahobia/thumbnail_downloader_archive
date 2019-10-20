package com.sagar.thumbnaildownloader;

import com.sagar.thumbnaildownloader.screens.download.DownloadActivity;
import com.sagar.thumbnaildownloader.screens.download.DownloadActivityModule;
import com.sagar.thumbnaildownloader.screens.download.DownloadActivityScope;
import com.sagar.thumbnaildownloader.screens.downloadoptions.DownloaderOptionsActivity;
import com.sagar.thumbnaildownloader.screens.downloadoptions.DownloaderOptionsActivityModule;
import com.sagar.thumbnaildownloader.screens.downloadoptions.DownloaderOptionsActivityScope;
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

    @ContributesAndroidInjector(modules = {DownloaderOptionsActivityModule.class})
    @DownloaderOptionsActivityScope
    abstract DownloaderOptionsActivity bindDownloaderActivity();


    @ContributesAndroidInjector(modules = {DownloadActivityModule.class})
    @DownloadActivityScope
    abstract DownloadActivity bindDownloadActivity();

}