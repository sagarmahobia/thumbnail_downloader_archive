package com.sagar.thumbnaildownloader.screens.main.videoadapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;

import com.sagar.thumbnaildownloader.network.models.Item;
import com.sagar.thumbnaildownloader.network.models.Snippet;
import com.sagar.thumbnaildownloader.network.models.VideoSearch;
import com.sagar.thumbnaildownloader.network.repo.YoutubeRepository;
import com.sagar.thumbnaildownloader.responsemodel.Response;
import com.sagar.thumbnaildownloader.screens.main.NoResultException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class VideoDataSource extends ItemKeyedDataSource<String, VideoModel> {

    private CompositeDisposable disposable;
    private YoutubeRepository youtubeRepository;
    private MutableLiveData<Response> stateLiveData;
    private String query;

    private String nextPageToken = "";
    private int size = 0;

    VideoDataSource(
            @NonNull CompositeDisposable disposable,
            @NonNull YoutubeRepository youtubeRepository,
            @NonNull MutableLiveData<Response> stateLiveData,
            @NonNull String query
    ) {

        this.disposable = disposable;
        this.youtubeRepository = youtubeRepository;
        this.stateLiveData = stateLiveData;
        this.query = query;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<VideoModel> callback) {
        if (query == null || query.isEmpty() || query.length() <= 2) {
            return;
        }

        stateLiveData.postValue(Response.loading());
        disposable.add(
                youtubeRepository.getVideoSearch(query)
                        .map(videoSearch -> {
                            nextPageToken = videoSearch.getNextPageToken();
                            List<VideoModel> viewModels = getViewModels(videoSearch);
                            size = size + viewModels.size();
                            if (size == 0) {
                                throw new NoResultException();
                            }
                            return viewModels;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            callback.onResult(data);
                            stateLiveData.setValue(Response.success(0));
                        }, throwable -> stateLiveData.setValue(Response.error(throwable)))
        );
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<VideoModel> callback) {
        if (query == null || query.isEmpty() || query.length() <= 2) {
            return;
        }
        if (nextPageToken == null || nextPageToken.isEmpty()) {
            return;
        }
        if (size > 24) {
            return;
        }
        stateLiveData.postValue(Response.loading());
        disposable.add(
                youtubeRepository.getVideoSearch(query, nextPageToken)
                        .map(videoSearch -> {
                            nextPageToken = videoSearch.getNextPageToken();
                            List<VideoModel> viewModels = getViewModels(videoSearch);
                            size = size + viewModels.size();
                            return viewModels;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            callback.onResult(data);
                            stateLiveData.setValue(Response.success(0));
                        }, throwable -> stateLiveData.setValue(Response.error(throwable)))
        );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<VideoModel> callback) {

    }

    @NonNull
    @Override
    public String getKey(@NonNull VideoModel item) {
        return item.getId();
    }

    private static List<VideoModel> getViewModels(VideoSearch videoSearch) {
        ArrayList<VideoModel> videoModels = new ArrayList<>();
        for (Item item : videoSearch.getItems()) {
            VideoModel videoModel = new VideoModel();

            Snippet snippet = item.getSnippet();
            videoModel.setId(item.getId().getVideoId());
            videoModel.setTitle(snippet.getTitle());
            videoModel.setImage(snippet.getThumbnails().getMedium().getUrl());
            videoModel.setChannel(snippet.getChannelId());
            videoModels.add(videoModel);
        }

        return videoModels;
    }
}
