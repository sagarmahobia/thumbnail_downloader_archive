package com.sagar.thumbnaildownloader.screens.main.videoadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.sagar.thumbnaildownloader.databinding.VideoListItemBinding;

public class VideoAdapter extends PagedListAdapter<VideoModel, VideoViewHolder> {

    private LayoutInflater inflater;
    private OnItemClickListener clickListener;

    private static DiffUtil.ItemCallback<VideoModel> itemCallback = new DiffUtil.ItemCallback<VideoModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull VideoModel oldItem, @NonNull VideoModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull VideoModel oldItem, @NonNull VideoModel newItem) {
            return oldItem.getId().equals(newItem.getId());//todo
        }
    };

    public VideoAdapter() {
        super(itemCallback);
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        VideoListItemBinding binding = VideoListItemBinding.inflate(inflater, parent, false);
        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoModel videoModel = getItem(position);

        holder.getBinding().setModel(videoModel);
        holder.bindTo(videoModel);
        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onClick(videoModel);
            }
        });

    }

    public interface OnItemClickListener {
        void onClick(VideoModel videoModel);
    }
}

