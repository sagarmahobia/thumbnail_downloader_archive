package com.sagar.thumbnaildownloader.utilityservice;

import android.content.SharedPreferences;

import com.sagar.thumbnaildownloader.ApplicationScope;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 15-Nov-18. at 23:27
 */

@ApplicationScope
public class SharedPreferenceService {

    private SharedPreferences sharedPreferences;

    public void setRated() {
        sharedPreferences.edit().putBoolean(GlobalConstants.OPENED_RATE_APP, true).apply();
    }

    interface GlobalConstants {

        String LAUNCH_COUNT = "launch_count";
        String OPENED_RATE_APP = "open_rate_app";
    }

    @Inject
    SharedPreferenceService(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void incrementLaunchCount() {
        long launchCount = sharedPreferences.getLong(GlobalConstants.LAUNCH_COUNT, 0);
        sharedPreferences.edit().putLong(GlobalConstants.LAUNCH_COUNT, launchCount + 1).apply();
    }

    public long getLaunchCount() {
        return sharedPreferences.getLong(GlobalConstants.LAUNCH_COUNT, 1);
    }

    public boolean checkRated() {
        return sharedPreferences.getBoolean(GlobalConstants.OPENED_RATE_APP, false);
    }

}
