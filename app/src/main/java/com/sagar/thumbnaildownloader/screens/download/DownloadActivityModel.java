package com.sagar.thumbnaildownloader.screens.download;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class DownloadActivityModel extends BaseObservable {

    private String url;
    private String id;

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(com.sagar.thumbnaildownloader.BR.url);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
