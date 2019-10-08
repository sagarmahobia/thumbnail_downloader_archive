package com.sagar.tumbnaildownloader;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@ApplicationScope
@Component(modules = {AndroidSupportInjectionModule.class, ActivityProvider.class, ApplicationModule.class})
public interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        ApplicationComponent create(@BindsInstance Application application);
    }

    void inject(ThumbnailDownloaderApplication thumbnailDownloaderApplication);
}
