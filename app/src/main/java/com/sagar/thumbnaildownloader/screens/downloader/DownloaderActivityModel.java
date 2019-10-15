package com.sagar.thumbnaildownloader.screens.downloader;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class DownloaderActivityModel extends BaseObservable {

    private String image;
    private String id;

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String id) {
        this.id = id;
        this.image = "https://img.youtube.com/vi/" + id + "/maxresdefault.jpg";
        notifyPropertyChanged(BR.image);
    }

    public String getId() {
        return id;
    }
}
