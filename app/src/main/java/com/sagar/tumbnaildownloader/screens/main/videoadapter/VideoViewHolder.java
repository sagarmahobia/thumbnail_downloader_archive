package com.sagar.tumbnaildownloader.screens.main.videoadapter;

import androidx.recyclerview.widget.RecyclerView;

import com.sagar.tumbnaildownloader.databinding.VideoListItemBinding;

class VideoViewHolder extends RecyclerView.ViewHolder {

    private VideoListItemBinding binding;

    VideoViewHolder(VideoListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    VideoListItemBinding getBinding() {
        return binding;
    }

    void bindTo(VideoModel videoModel) {
        binding.setModel(videoModel);
    }
}
