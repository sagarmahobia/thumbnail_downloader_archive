package com.sagar.thumbnaildownloader;

import android.app.Application;

import com.sagar.thumbnaildownloader.utilityservice.SharedPreferenceService;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class ThumbnailDownloaderApplication extends Application implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> activityDispatchingAndroidInjector;

    @Inject
    SharedPreferenceService sharedPreferenceService;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationComponent
                .factory()
                .create(this)
                .inject(this);

        sharedPreferenceService.incrementLaunchCount();

    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return activityDispatchingAndroidInjector;
    }
}