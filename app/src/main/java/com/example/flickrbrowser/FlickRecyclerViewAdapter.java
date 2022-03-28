package com.example.flickrbrowser;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlickRecyclerViewAdapter extends RecyclerView.Adapter<FlickRecyclerViewAdapter.FlickrImageViewHolder>{
    private static final String TAG = "FlickRecyclerViewAdapter";
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickRecyclerViewAdapter(List<Photo> photoList, Context context) {
        mPhotoList = photoList;
        mContext = context;
    }

    static class FLickrImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FLickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public FLickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "Constructor: itemView ");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.textView);
        }


    }
}
