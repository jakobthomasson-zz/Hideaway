package com.example.mam032.hideaways.model;

import java.util.ArrayList;

/**
 * Created by Dude on 2015-11-09.
 */
public class Globals{
    private static Globals instance;

    // Global variable
    private ArrayList<Place> data;

    // Restrict the constructor from being instantiated
    private Globals(){
        data = new ArrayList<>();
    }

    public void addData(Place place){
        this.data.add(place);
    }

    public ArrayList<Place> getData(){
        return this.data;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    public boolean isEmpty(){
        if(data.size() == 0){
            return true;
        }
        return false;
    }
}