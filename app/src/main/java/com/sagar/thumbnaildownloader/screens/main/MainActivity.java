package com.sagar.thumbnaildownloader.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sagar.thumbnaildownloader.R;
import com.sagar.thumbnaildownloader.alerts.MyToast;
import com.sagar.thumbnaildownloader.databinding.ActivityMainBinding;
import com.sagar.thumbnaildownloader.responsemodel.Status;
import com.sagar.thumbnaildownloader.screens.downloadoptions.DownloaderOptionsActivity;
import com.sagar.thumbnaildownloader.screens.main.videoadapter.VideoAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainActivityHandler, TextWatcher {

    @Inject
    MainActivityViewModelFactory viewModelFactory;

    @Inject
    VideoAdapter videoAdapter;

    private ActivityMainBinding binding;

    private MainActivityViewModel viewModel;

    private MainActivityModel activityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
        activityModel = viewModel.getActivityModel();
        binding.setModel(activityModel);
        binding.setHandler(this);
        binding.searchbarEdittext.addTextChangedListener(this);

        binding.searchRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.searchRecycler.setAdapter(videoAdapter);
        videoAdapter.setClickListener(videoModel -> {
            Intent intent = new Intent(this, DownloaderOptionsActivity.class);
            intent.putExtra("id", videoModel.getId());
            startActivity(intent);
        });

        viewModel.getPagedListLiveData().observe(this, postModels -> {
            videoAdapter.submitList(postModels);

        });
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.invalidateListData());


        viewModel.getPagedListStateLiveData().observe(this, pagingState -> {
            binding.swipeRefreshLayout.setRefreshing(pagingState.getStatus() == Status.LOADING);

            if (pagingState.getStatus() == Status.LOADING) {

                binding.errorMessageTextAndImage.setVisibility(View.GONE);

            } else if (pagingState.getStatus() == Status.SUCCESS) {

                binding.searchRecycler.setVisibility(View.VISIBLE);

            } else if (pagingState.getStatus() == Status.ERROR) {
                binding.errorMessageTextAndImage.setVisibility(View.VISIBLE);
                binding.errorIconImageView.setImageResource(R.drawable.ic_sad_emoticon);

                if (pagingState.getError() instanceof NoResultException) {
                    binding.errorMessageText.setText("No matches were found. Try something else.");

                } else {
                    binding.errorMessageText.setText("Something went wrong. Swipe down to refresh.");

                }
            }
        });

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
                        Intent intent = new Intent(this, DownloaderOptionsActivity.class);
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
            binding.searchbarEdittext.requestFocus();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String query = editable.toString();
        viewModel.query(query);

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
