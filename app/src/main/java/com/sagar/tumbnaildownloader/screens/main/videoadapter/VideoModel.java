package com.sagar.tumbnaildownloader.screens.main.videoadapter;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class VideoModel extends BaseObservable {
    private String title;
    private String channel;
    private String image;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
