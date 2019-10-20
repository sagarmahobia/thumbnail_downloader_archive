package com.sagar.thumbnaildownloader.network.repo;

import com.sagar.thumbnaildownloader.network.models.VideoSearch;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by SAGAR MAHOBIA
 */

public interface YoutubeRepository {

    @GET("search?part=snippet&type=video")
    Single<VideoSearch> getVideoSearch(@Query("q") String q);

    @GET("search?part=snippet&type=video")
    Single<VideoSearch> getVideoSearch(@Query("q") String q, @Query("pageToken") String nextPageToken);


    @GET
    Single<ResponseBody> downloadFileByUrlRx(@Url String fileUrl);


}
