package com.example.mam032.hideaways.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;


public class Place implements ClusterItem {
    public final String name, descr;
    public final int placePhoto, rating;

    private final LatLng mPosition;

    public Place(LatLng position, String name, String descr, int rating,  int pictureResource) {
        this.name = name;
        this.descr = descr;
        this.rating = rating;
        placePhoto = pictureResource;
        mPosition = position;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


}