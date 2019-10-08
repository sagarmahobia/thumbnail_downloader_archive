package com.sagar.tumbnaildownloader.screens.downloader;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.sagar.tumbnaildownloader.R;
import com.sagar.tumbnaildownloader.databinding.ActivityDownloaderBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DownloaderActivity extends AppCompatActivity implements DownloaderActivityHandler {

    @Inject
    DownloaderActivityViewModelFactory viewModelFactory;

    private ActivityDownloaderBinding binding;

    private DownloaderActivityViewModel viewModel;

    private DownloaderActivityModel activityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_downloader);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DownloaderActivityViewModel.class);
        activityModel = viewModel.getActivityModel();
        binding.setModel(activityModel);
        binding.setHandler(this);
    }
}
