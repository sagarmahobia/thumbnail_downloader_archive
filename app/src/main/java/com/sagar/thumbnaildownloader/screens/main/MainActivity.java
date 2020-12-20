package com.sagar.thumbnaildownloader.screens.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.sagar.thumbnaildownloader.R;
import com.sagar.thumbnaildownloader.alerts.MyToast;
import com.sagar.thumbnaildownloader.databinding.ActivityMainBinding;
import com.sagar.thumbnaildownloader.screens.download.DownloadActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainActivityHandler {

    @Inject
    MainActivityViewModelFactory viewModelFactory;

    private ActivityMainBinding binding;

    private MainActivityViewModel viewModel;

    private MainActivityModel activityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel.class);
        activityModel = viewModel.getActivityModel();
        binding.setModel(activityModel);
        binding.setHandler(this);

        Intent shareIntent = getIntent();
        String action = shareIntent.getAction();
        String type = shareIntent.getType();

        boolean shouldRequest = true;

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String extra = shareIntent.getStringExtra(Intent.EXTRA_TEXT);
                if (extra != null) {
                    String youTubeId = getYouTubeId(extra);
                    if (!youTubeId.equalsIgnoreCase("error")) {
                        Intent intent = new Intent(this, DownloadActivity.class);
                        intent.putExtra("id", youTubeId);
                        startActivity(intent);
                        shouldRequest = false;
                    } else {
                        MyToast.show(binding.getRoot(), "Couldn't find youtube video.", MyToast.Type.FAILURE_SNACK_BAR);
                    }
                }
            }
        }

        if (shouldRequest) {

        }
    }


    private String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed/)[^#&?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "error";
        }
    }
}
