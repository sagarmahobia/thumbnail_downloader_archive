package com.sagar.thumbnaildownloader.screens.downloadoptions.imageadapter;

import androidx.recyclerview.widget.RecyclerView;

import com.sagar.thumbnaildownloader.databinding.ImageListItemBinding;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private ImageListItemBinding binding;

    ImageViewHolder(ImageListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ImageListItemBinding getBinding() {
        return binding;
    }

    public void bindTo(ImageModel imageModel) {
        binding.setModel(imageModel);
    }

    public void handleWith(ImageHandler imageHandler) {
        binding.setHandler(imageHandler);
    }
}
