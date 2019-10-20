package com.sagar.thumbnaildownloader.screens.download;

import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.sagar.thumbnaildownloader.network.repo.YoutubeRepository;
import com.sagar.thumbnaildownloader.responsemodel.Response;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DownloadActivityViewModel extends ViewModel {

    private YoutubeRepository youtubeRepository;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private DownloadActivityModel activityModel = new DownloadActivityModel();

    private MutableLiveData<Response> saveResponse = new MutableLiveData<>();

    DownloadActivityViewModel(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    DownloadActivityModel getActivityModel() {
        return activityModel;
    }

    public MutableLiveData<Response> getSaveResponse() {
        return saveResponse;
    }

    void save() {
        saveResponse.setValue(Response.loading());
        disposable.add(youtubeRepository
                .downloadFileByUrlRx(activityModel.getUrl())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        responseBody -> writeResponseBodyToDisk(responseBody).
                                subscribe(
                                        aBoolean -> saveResponse.postValue(Response.success(aBoolean)),
                                        throwable -> saveResponse.postValue(Response.error(throwable))
                                ),
                        throwable -> saveResponse.postValue(Response.error(throwable))
                ));
    }


    private Single<Boolean> writeResponseBodyToDisk(ResponseBody body) {

        return Single.create(emitter -> {
            try {
                String destinationDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ThumbnailDownloader";
                String destinationFilePath = destinationDirPath + "/" + activityModel.getId() + ".jpg";

                // todo change the file location/name according to your needs
                File futureStudioIconFile = new File(destinationFilePath);

                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
               /*     byte[] fileReader = new byte[4096];

                    long fileSize = body.contentLength();
                    long fileSizeDownloaded = 0;
*/
                    inputStream = body.byteStream();

                    File destination = new File(destinationFilePath);
                    FileUtils.copyInputStreamToFile(inputStream, destination);

                    /*
                    outputStream = new FileOutputStream(futureStudioIconFile);

                    while (true) {
                        int read = inputStream.read(fileReader);

                        if (read == -1) {
                            break;
                        }

                        outputStream.write(fileReader, 0, read);

                        fileSizeDownloaded += read;

                        Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }

                    outputStream.flush();*/
                    emitter.onSuccess(true);
                } catch (IOException e) {
                    emitter.onError(e);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
