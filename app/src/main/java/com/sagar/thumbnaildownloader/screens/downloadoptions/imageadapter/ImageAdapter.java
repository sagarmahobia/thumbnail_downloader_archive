package com.sagar.thumbnaildownloader.screens.downloadoptions.imageadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sagar.thumbnaildownloader.databinding.ImageListItemBinding;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private LayoutInflater inflater;
    private List<ImageModel> imageModels;
    private OnItemClickListener clickListener;
    private ImageHandler imageHandler;

    public ImageAdapter() {
    }

    public void setImageModels(List<ImageModel> imageModels) {
        this.imageModels = imageModels;
        notifyDataSetChanged();
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setImageHandler(ImageHandler imageHandler) {
        this.imageHandler = imageHandler;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ImageListItemBinding binding = ImageListItemBinding.inflate(inflater, parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageModel imageModel = imageModels.get(position);

        holder.getBinding().setModel(imageModel);
        holder.bindTo(imageModel);
        holder.handleWith(imageHandler);
        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onClick(imageModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageModels != null ? imageModels.size() : 0;
    }

    public interface OnItemClickListener {
        void onClick(ImageModel imageModel);
    }
}

