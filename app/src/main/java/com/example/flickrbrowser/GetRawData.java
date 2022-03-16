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
    private final OnDownloadComplete mCallback;

    interface OnDownloadComplete {
        void onDownloadComplete(String data,DownloadStatus status);
    }


    public GetRawData(OnDownloadComplete callback){
        this.mDownloadStatus = DownloadStatus.IDLE;
        mCallback = callback;

    }
    void runInSameThread(String s){
        //when you call .execute method from async task it creates
        //a new thread and doInBackground method, then onPost execute is called on main thread

        Log.d(TAG, "runInSameThread: starts");

        onPostExecute(doInBackground(s));

        Log.d(TAG, "runInSameThread: ends");
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: parameter = " + s);
        if (mCallback != null){
            mCallback.onDownloadComplete(s,mDownloadStatus);
        }

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

//            String line;
//            while (null != (line = reader.readLine())){
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                result.append(line).append("\n");
            }


            // this gets executed after finally from try catch
            mDownloadStatus = DownloadStatus.OK;
            return result.toString();

        }catch(MalformedURLException e){
            Log.e(TAG, "doInBackground: " + e.getMessage() );
        }catch(IOException e){
            Log.e(TAG, "doInBackground: " + e.getMessage() );
        }catch(SecurityException e ){
            Log.e(TAG, "doInBackground: Security Exception. Needs permission?" + e.getMessage() );
        }finally{
            if(connection != null){
                connection.disconnect();

            }if(reader !=null){
                try{
                    reader.close();
                }catch(IOException e){
                    Log.e(TAG, "doInBackground:Error closing stream "+  e.getMessage() );
                }


            }
        }


        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
        


}
