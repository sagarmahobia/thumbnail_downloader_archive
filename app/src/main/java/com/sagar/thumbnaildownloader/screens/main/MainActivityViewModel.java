package com.sagar.thumbnaildownloader.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.sagar.thumbnaildownloader.network.repo.YoutubeRepository;
import com.sagar.thumbnaildownloader.responsemodel.Response;
import com.sagar.thumbnaildownloader.screens.main.videoadapter.VideoDataSourceFactory;
import com.sagar.thumbnaildownloader.screens.main.videoadapter.VideoModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MainActivityViewModel extends ViewModel {

    private final YoutubeRepository youtubeRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private MainActivityModel activityModel = new MainActivityModel();
    private MutableLiveData<Response> pagedListStateLiveData = new MutableLiveData<>();

    private VideoDataSourceFactory videoDataSourceFactory;
    private LiveData<PagedList<VideoModel>> pagedListLiveData;
    private Subject<String> throttle;

    MainActivityViewModel(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
        initList();
    }


    LiveData<PagedList<VideoModel>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    MutableLiveData<Response> getPagedListStateLiveData() {
        return pagedListStateLiveData;
    }

    void initList() {
        videoDataSourceFactory = new VideoDataSourceFactory(disposable, youtubeRepository, pagedListStateLiveData);
        PagedList.Config config = new PagedList.Config.Builder().setPrefetchDistance(2).build();
        pagedListLiveData = new LivePagedListBuilder<>(videoDataSourceFactory, config).build();

        throttle = PublishSubject.create();
        disposable.add(throttle.filter(q -> q.length() > 0)
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(q -> {

                            videoDataSourceFactory.setQuery(q);
                            videoDataSourceFactory.invalidate();

//                    if (searchResult != null) {
//                        searchResult.clear();
//                        view.notifyAdapter();
//                    }
//                    view.resetEndlessLoader();
//                    view.showProgress();
//                    disposable.add(
//                            youtubeService.getVideoSearch(q).
//                                    subscribeOn(Schedulers.io()).
//                                    observeOn(AndroidSchedulers.mainThread()).
//                                    subscribe(searchResult -> {
//                                                nextPageToken = searchResult.getNextPageToken();
//                                                if (searchResult.getItems().size() < 1) {
//                                                    view.showNoMatchMessage();
//                                                } else {
//                                                    view.hideErrorMessage();
//                                                }
//                                                this.searchResult = searchResult.getItems();
//
//                                                view.hideProgress();
//                                                view.notifyAdapter();
//                                            },
//                                            error -> {
//                                                view.onErrorLoadingSearchList();
//                                            })
//                    );

                        })
        );
    }

    MainActivityModel getActivityModel() {
        return activityModel;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public void query(String query) {
        if (query == null || query.isEmpty()) {
            return;
        }
        throttle.onNext(query);
    }

    public void invalidateListData() {
        videoDataSourceFactory.invalidate();
    }
}
