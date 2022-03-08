package com.example.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

class GetRawData extends AsyncTask<String, Void, String> {

    private static final String TAG = "GetRawData";
    private DownloadStatus mDownloadStatus; //lowercase m at the start stands for member variable



    public GetRawData(){
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        if(strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }
        try{
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL (strings[0]);

            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int response  = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was  " +response);
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


        }catch(MalformedURLException e){
            Log.e(TAG, "doInBackground: " + e.getMessage() );
        }catch(IOException e){
            Log.e(TAG, "doInBackground: " + e.getMessage() );
        }catch(SecurityException e ){
            Log.e(TAG, "doInBackground: Security Exception. Needs permission?" + e.getMessage() );
        }


        return null;
    }
}
