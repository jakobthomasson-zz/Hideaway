package com.example.mam032.hideaways;
import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class popupFragment extends DialogFragment  {

    private TextView placeName;
    private TextView placeDesc;
    private ImageView placeImg;


    String name;
    String desc;
    int img;
    Bitmap bip;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

    }

    public popupFragment() {



    }

    public static popupFragment newInstance(String one, String two, int three) {

      popupFragment frag = new popupFragment();

        Bundle args = new Bundle();

      /*  args.putString("name", one);
        args.putString("desc", two);
        args.putInt("img", three);*/
        args.putString("name", one);
        args.putString("desc", two);
        args.putInt("img", three);

        frag.setArguments(args);

        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup, container);

        name = getArguments().getString("name");
        desc = getArguments().getString("desc");
        img = getArguments().getInt("img");

        placeName = (TextView) view.findViewById(R.id.placeName);
        placeDesc = (TextView) view.findViewById(R.id.placeDescription);
        placeImg = (ImageView) view.findViewById(R.id.placeImage);



        placeImg.setImageResource(img);

        getDialog().setTitle(name);

        return view;
    }

}