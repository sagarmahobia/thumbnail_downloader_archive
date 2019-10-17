package com.sagar.thumbnaildownloader.screens.downloader.imageadapter;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ImageModel extends BaseObservable {

    private String id;
    private String title;
    private String fileName;

    public String getId() {
        return id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getImageUrl() {
        return "https://img.youtube.com/vi/" + id + "/" + fileName;
    }

    public ImageModel(String id, String title, String fileName) {
        this.id = id;
        this.title = title;
        this.fileName = fileName;
    }
}
