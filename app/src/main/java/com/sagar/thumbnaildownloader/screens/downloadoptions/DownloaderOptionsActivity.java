package com.sagar.thumbnaildownloader.screens.downloadoptions;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.justadeveloper96.permissionhelper.PermissionHelper;
import com.sagar.thumbnaildownloader.R;
import com.sagar.thumbnaildownloader.databinding.ActivityDownloaderOptionsBinding;
import com.sagar.thumbnaildownloader.screens.download.DownloadActivity;
import com.sagar.thumbnaildownloader.screens.downloadoptions.imageadapter.ImageAdapter;
import com.sagar.thumbnaildownloader.screens.downloadoptions.imageadapter.ImageModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DownloaderOptionsActivity extends AppCompatActivity implements DownloaderOptionsActivityHandler {

    @Inject
    DownloaderOptionsActivityViewModelFactory viewModelFactory;

    @Inject
    ImageAdapter imageAdapter;

    private ActivityDownloaderOptionsBinding binding;

    private DownloaderOptionsActivityViewModel viewModel;

    private DownloaderOptionsActivityModel activityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_downloader_options);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DownloaderOptionsActivityViewModel.class);
        activityModel = viewModel.getActivityModel();
        binding.setModel(activityModel);
        binding.setHandler(this);

        String id = getIntent().getStringExtra("id");

        ArrayList<ImageModel> imageModels = new ArrayList<>();

        imageModels.add(new ImageModel(id, "Download (Maximum)", "maxresdefault.jpg"));
        imageModels.add(new ImageModel(id, "Download (High)", "hqdefault.jpg"));
        imageModels.add(new ImageModel(id, "Download (Medium)", "mqdefault.jpg"));
        imageModels.add(new ImageModel(id, "Download (Standard)", "sddefault.jpg"));

        imageAdapter.setClickListener((imageModel) -> {
            String imageUrl = imageModel.getImageUrl();
            Intent intent = new Intent(this, DownloadActivity.class);
            intent.putExtra("url", imageUrl);
            intent.putExtra("id", imageModel.getId());
            startActivity(intent);
        });

        imageAdapter.setImageModels(imageModels);

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(imageAdapter);


    }

    @Override
    public void back() {
        finish();
    }

}
