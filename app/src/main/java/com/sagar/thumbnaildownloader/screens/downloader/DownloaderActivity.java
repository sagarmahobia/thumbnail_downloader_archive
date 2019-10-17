package com.sagar.thumbnaildownloader.screens.downloader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sagar.thumbnaildownloader.R;
import com.sagar.thumbnaildownloader.alerts.MyToast;
import com.sagar.thumbnaildownloader.databinding.ActivityDownloaderBinding;
import com.sagar.thumbnaildownloader.screens.downloader.imageadapter.ImageAdapter;
import com.sagar.thumbnaildownloader.screens.downloader.imageadapter.ImageModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DownloaderActivity extends AppCompatActivity implements DownloaderActivityHandler {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;

    @Inject
    DownloaderActivityViewModelFactory viewModelFactory;

    @Inject
    ImageAdapter imageAdapter;

    private ActivityDownloaderBinding binding;

    private DownloaderActivityViewModel viewModel;

    private DownloaderActivityModel activityModel;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_downloader);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DownloaderActivityViewModel.class);
        activityModel = viewModel.getActivityModel();
        binding.setModel(activityModel);
        binding.setHandler(this);

        String id = getIntent().getStringExtra("id");

//        activityModel.setImage(id);


        ArrayList<ImageModel> imageModels = new ArrayList<>();

        imageModels.add(new ImageModel(id, "Downlaod (Maximum)", "maxresdefault.jpg"));
        imageModels.add(new ImageModel(id, "Downlaod (High)", "hqdefault.jpg"));
        imageModels.add(new ImageModel(id, "Downlaod (Medium)", "mqdefault.jpg"));
        imageModels.add(new ImageModel(id, "Downlaod (Standard)", "sddefault.jpg"));

        imageAdapter.setClickListener((imageView, imageModel) -> {
            this.imageView = imageView;
            save(imageModel.getId(), imageView);
        });

        imageAdapter.setImageModels(imageModels);

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(imageAdapter);

    }

    public void save(String id, ImageView imageView) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            saveImage(id, imageView);
        }
    }

    @Override
    public void back() {
        finish();
    }

    void saveImage(String id, ImageView imageView) {

        String destinationDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ThumbnailDownloader";
        String destinationFilePath = destinationDirPath + "/" + id + ".jpg";

        File destinationDirFile = new File(destinationDirPath);


        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        try {

            boolean ignored = destinationDirFile.mkdirs();// don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(destinationFilePath); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();


            MyToast.show(this.binding.getRoot(), "Image saved in gallery.", MyToast.Type.SUCCESS_SNACK_BAR);

        } catch (NullPointerException | IOException e) {
            MyToast.show(this.binding.getRoot(), "Something went wrong.", MyToast.Type.FAILURE_SNACK_BAR);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                save(getIntent().getStringExtra("id"), imageView);
            } else {
                MyToast.show(binding.getRoot(), "Storage permission is required.", MyToast.Type.FAILURE_SNACK_BAR);
            }
        }
    }
}
