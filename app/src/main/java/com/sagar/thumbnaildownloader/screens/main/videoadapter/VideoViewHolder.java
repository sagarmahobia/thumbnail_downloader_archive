package com.sagar.thumbnaildownloader.screens.main.videoadapter;

import androidx.recyclerview.widget.RecyclerView;

import com.sagar.thumbnaildownloader.databinding.VideoListItemBinding;


public class VideoViewHolder extends RecyclerView.ViewHolder {

    private VideoListItemBinding binding;

    VideoViewHolder(VideoListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public VideoListItemBinding getBinding() {
        return binding;
    }

    public void bindTo(VideoModel videoModel) {
        binding.setModel(videoModel);
    }
}
