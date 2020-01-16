package com.sagar.thumbnaildownloader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

@Module(includes = NetworkModule.class)
public class ApplicationModule {


    @Provides
    @ApplicationScope
    Context context(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
