package com.sagar.thumbnaildownloader.screens.main.videoadapter;

import androidx.databinding.BaseObservable;

public class VideoModel extends BaseObservable {

    private String id;

    private String image;
    private String title;
    private String channel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
