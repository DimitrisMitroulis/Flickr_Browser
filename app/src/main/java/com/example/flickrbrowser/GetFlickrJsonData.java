package com.example.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import java.util.List;

class GetFlickrJsonData implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

    }

    private List<Photo> mPhotoList = null;
    private String mBaseUrl;
    private String mLanguage;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;

    interface OnDataAvailable{
        void onDataAvailable(List<Photo> data, DownloadStatus status);

    }

    public GetFlickrJsonData(OnDataAvailable callBack, String language, String baseUrl, boolean matchAll)  {
        Log.d(TAG, "GetFlickrJsonData called");
        mBaseUrl = baseUrl;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallBack = callBack;
    }

    void excecuteOnSameThread(String searchCriteria){
        Log.d(TAG, "excecuteOnSameThread: starts ");
        String destinationUrl = createUri(searchCriteria, mLanguage , mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUrl);
        Log.d(TAG, "excecuteOnSameThread: ends");
    }

    private String createUri(String searchCriteria, String lang, boolean matchAll){
        Log.d(TAG, "createUri: starts");
        return Uri.parse(mBaseUrl).buildUpon()
                .appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("tagmode",matchAll ? "ALL": "ANY")
                .appendQueryParameter("lang",lang)
                .appendQueryParameter("format",lang)
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();

    }

}
