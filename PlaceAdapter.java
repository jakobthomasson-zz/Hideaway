package com.example.mam032.hideaways;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mam032.hideaways.model.Place;
import com.example.mam032.hideaways.util.AsyncDrawable;
import com.example.mam032.hideaways.util.BitmapWorkerTask;


import java.util.ArrayList;
import java.util.List;


public class PlaceAdapter extends
        RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private ArrayList<Place> placeList;

    public PlaceAdapter(ArrayList<Place> placeList) {
        this.placeList = placeList;
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder placeViewHolder, int i) {
        Place pi = placeList.get(i);
        placeViewHolder.pName.setText(pi.name);
        placeViewHolder.pDesc.setText(pi.descr);

        placeViewHolder.loadBitmap(pi.placePhoto);




    }



    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);
        return new PlaceViewHolder(itemView, viewGroup.getContext());
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        protected TextView pName;
        protected TextView pDesc;
        protected ImageView pImage;
       Context context;


        public PlaceViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            pName =  (TextView) v.findViewById(R.id.place_name);
            pDesc = (TextView)  v.findViewById(R.id.place_desc);
            pImage = (ImageView) v.findViewById(R.id.place_image);

        }

        private void loadBitmap(int resId) {


            BitmapWorkerTask task = new BitmapWorkerTask(pImage, context);
            task.execute(resId);
        }





    }
}
// See more at: http://www.survivingwithandroid.com/2014/11/a-guide-to-android-recyclerview-cardview.html#sthash.SFdqy2fg.dpuf