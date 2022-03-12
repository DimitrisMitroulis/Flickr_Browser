package com.example.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: Status = "+ status);

        if(status == DownloadStatus.OK){
            mPhotoList = new ArrayList<>();
            try{
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for (int i = 0; i<itemsArray.length(); i++){
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorid = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");
                    String description = jsonPhoto.getString("description");
                    String date = jsonData.getString("date_taken");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.", "_b.");//change the photo url to change it's size to b(big)

                    Photo photoObject = new Photo(title,author,authorid,link,tags,photoUrl,date,description);
                    mPhotoList.add(photoObject);

                    Log.d(TAG, "onDownloadComplete: " +photoObject.toString());

                }

            }catch(JSONException jsone){
                jsone.printStackTrace();

                Log.d(TAG, "onDownloadComplete: Exception" +jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
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

    void executeOnSameThread(String searchCriteria){
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
