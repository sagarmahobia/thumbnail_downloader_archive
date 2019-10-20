package com.sagar.thumbnaildownloader.screens.download;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.justadeveloper96.permissionhelper.PermissionHelper;
import com.sagar.thumbnaildownloader.R;
import com.sagar.thumbnaildownloader.alerts.MyToast;
import com.sagar.thumbnaildownloader.databinding.ActivityDownloadBinding;
import com.sagar.thumbnaildownloader.network.repo.YoutubeRepository;
import com.sagar.thumbnaildownloader.responsemodel.Response;
import com.sagar.thumbnaildownloader.responsemodel.Status;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DownloadActivity extends AppCompatActivity implements DownloadActivityHandler {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 200;

    @Inject
    DownloadActivityViewModelFactory viewModelFactory;

    @Inject
    YoutubeRepository youtubeRepository;

    private PermissionHelper permissionHelper;

    private ActivityDownloadBinding binding;

    private DownloadActivityViewModel viewModel;

    private DownloadActivityModel activityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_download);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DownloadActivityViewModel.class);
        activityModel = viewModel.getActivityModel();
        binding.setModel(activityModel);
        binding.setHandler(this);

        String url = getIntent().getStringExtra("url");
        String id = getIntent().getStringExtra("id");

        activityModel.setUrl(url);
        activityModel.setId(id);


        permissionHelper = new PermissionHelper(this).setListener(new PermissionHelper.PermissionsListener() {
            @Override
            public void onPermissionGranted(int request_code) {

                viewModel.save();

            }

            @Override
            public void onPermissionRejectedManyTimes(List<String> rejectedPerms, int request_code) {

                //if user keeps on denying request
            }
        });

        viewModel.getSaveResponse().observe(this, this::onSaveResponse);
    }

    private void onSaveResponse(Response<Boolean> response) {
        if (response.getStatus() == Status.SUCCESS) {
            if (response.getData()) {
                MyToast.show(binding.getRoot(), "Saved to gallery", MyToast.Type.SUCCESS_SNACK_BAR);
            }
            binding.downloadButton.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);

        } else if (response.getStatus() == Status.ERROR) {
            MyToast.show(binding.getRoot(), "Failed to save. Try Again.", MyToast.Type.FAILURE_SNACK_BAR);
            binding.downloadButton.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);

        } else if (response.getStatus() == Status.LOADING) {
            binding.downloadButton.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.save();
            } else {
                MyToast.show(binding.getRoot(), "Storage permission is required.", MyToast.Type.FAILURE_SNACK_BAR);
            }
        }
    }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void save(DownloadActivityModel model) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            /*    requestPermissions(
                        permissions,
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);*/
                permissionHelper.requestPermission(permissions, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            viewModel.save();
        }
    }

    @Override
    public void back() {
        this.finish();
    }


}