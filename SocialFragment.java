package com.example.mam032.hideaways;
import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by jakob on 2015-01-03.
 */
public class SocialFragment extends Fragment{
    Button button;
    int counter = 0;
    //Communicator comm;


    public void onCreate(Bundle savedInstanceState) {
        Log.i("MyFragment", "on Create Fragment");
        super.onCreate(savedInstanceState);
        //first time application is created, 0 is default value
        if(savedInstanceState==null){
            counter = 0;
        } else {
            counter = savedInstanceState.getInt("counter", 0);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MyFragment", "on CreateView Fragment");
        //inflate gives an java object corresponding to the xml file
        return inflater.inflate(R.layout.fragment_social, container, false);
    }

    /**
     * Indication that the activitys onCreate has completed running, and is free to use UI elements
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Log.i("MyFragment", "on Activity Created Fragment");
        super.onActivityCreated(savedInstanceState);
        //comm= (Communicator) getActivity();
        //button= (Button) getActivity().findViewById(R.id.button);
        //button.setOnClickListener(this);
    }

    @Override
    public void onStart(){
        Log.i("MyFragment", "on Start Fragment");
        super.onStart();
    }

    @Override
    public void onResume(){
        Log.i("MyFragment", "on Resume Fragment");
        super.onStart();
    }

    @Override
    public void onPause(){
        Log.i("MyFragment", "on Pause Fragment");
        super.onStart();
    }

    /**
     * Same state when come back after onPause
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        Log.i("MyFragment", "on SaveInstanceState Fragment");
        super.onSaveInstanceState(outState);
        outState.putInt("counter", counter);
        Log.i("xText", String.valueOf(counter));

    }

    @Override
    public void onStop(){
        Log.i("MyFragment", "on Stop Fragment");
        super.onStop();
    }

    @Override
    public void onDestroyView(){
        Log.i("MyFragment", "on DestroyView Fragment");
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        Log.i("MyFragment", "on Destroy Fragment");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("MyFragment", "on Detatch Fragment");
        super.onDetach();

    }



}
