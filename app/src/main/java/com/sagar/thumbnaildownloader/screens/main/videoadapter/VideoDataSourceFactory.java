package com.sagar.thumbnaildownloader.screens.main.videoadapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sagar.thumbnaildownloader.network.repo.YoutubeRepository;
import com.sagar.thumbnaildownloader.responsemodel.PagingState;
import com.sagar.thumbnaildownloader.responsemodel.Response;

import io.reactivex.disposables.CompositeDisposable;

public class VideoDataSourceFactory extends DataSource.Factory<String, VideoModel> {

    private CompositeDisposable disposable;
    private YoutubeRepository youtubeRepository;
    private MutableLiveData<Response> stateLiveData;

    private VideoDataSource dataSource;
    private String query;

    public VideoDataSourceFactory(CompositeDisposable disposable,
                                  YoutubeRepository youtubeRepository,
                                  MutableLiveData<Response> stateLiveData
    ) {
        this.disposable = disposable;
        this.youtubeRepository = youtubeRepository;
        this.stateLiveData = stateLiveData;
    }

    @NonNull
    @Override
    public DataSource<String, VideoModel> create() {
        dataSource = new VideoDataSource(disposable, youtubeRepository, stateLiveData, query);
        return dataSource;
    }

    public void invalidate() {
        dataSource.invalidate();
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
