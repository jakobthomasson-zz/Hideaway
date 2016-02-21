package com.example.mam032.hideaways;
import android.annotation.TargetApi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mam032.hideaways.model.Globals;
import com.example.mam032.hideaways.model.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jakob on 2015-12-15.
 */
public class MapFragment extends Fragment implements ClusterManager.OnClusterClickListener<Place>, ClusterManager.OnClusterInfoWindowClickListener<Place>, ClusterManager.OnClusterItemClickListener<Place>, ClusterManager.OnClusterItemInfoWindowClickListener<Place>{
    private static final String IMAGE_CACHE_DIR = "thumbs";





    private ClusterManager<Place> mClusterManager;
    private Random mRandom = new Random(1984);

    MapView mMapView;
    private GoogleMap mMap;



    public MapFragment() {}




    public void onCreate(Bundle savedInstanceState) {
        Log.i("MyFragment", "on Create Fragment");
        super.onCreate(savedInstanceState);

        //Vet inte vad detta gör
        setHasOptionsMenu(true);




        //first time application is created, 0 is default value
        if(savedInstanceState==null){
        } else {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("MyFragment", "on CreateView Fragment");
        //inflate gives an java object corresponding to the xml file
        View v =  inflater.inflate(R.layout.fragment_map, container, false);

        if(savedInstanceState==null){

        }else{


        }
        //Set up the map if not already done
        if (mMap == null) {
            mMapView = (MapView) v.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMap = mMapView.getMap();

            // If a Google map has been created, find the current location
            if (mMap != null) {
                startDemo();
                //setUpMap();
            }
        }
        return v;
    }


    private void startDemo() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.704901, 13.203250), 9.5f));

        mClusterManager = new ClusterManager<Place>(getActivity(), mMap);
        mClusterManager.setRenderer(new PlaceRenderer());
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        addItems();
        mClusterManager.cluster();
    }

    private void addItems() {
        for (Place p: Globals.getInstance().getData()) {
            mClusterManager.addItem(p);
        }

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

    /*
 * onAttach(Context) is not called on pre API 23 versions of Android and onAttach(Activity) is deprecated
 * Use onAttachToContext instead
 */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


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
        mMapView.onPause();
    }

    /**
     * Same state when come back after onPause
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        Log.i("MyFragment", "on SaveInstanceState Fragment");
        super.onSaveInstanceState(outState);
        // outState.putInt("counter", counter);
        // Log.i("xText", String.valueOf(counter));

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
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDetach() {
        Log.i("MyFragment", "on Detatch Fragment");
        super.onDetach();

    }





/**
 * Draws profile photos inside markers (using IconGenerator).
 * When there are multiple people in the cluster, draw multiple photos (using MultiDrawable).
 */
private class PlaceRenderer extends DefaultClusterRenderer<Place> {
    private final IconGenerator mIconGenerator = new IconGenerator(getActivity().getApplicationContext());
    private final IconGenerator mClusterIconGenerator = new IconGenerator(getActivity().getApplicationContext());
    private final ImageView mImageView;
    private final ImageView mClusterImageView;
    private final int mDimension;




    public PlaceRenderer() {
        super(getActivity().getApplicationContext(), mMap, mClusterManager);
        LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );





        View multiProfile = inflater.inflate(R.layout.multi_profile, null);
        mIconGenerator.setStyle(R.style.MyIconstyle);
        mClusterIconGenerator.setStyle(R.style.MyIconstyle);
        //mIconGenerator.setColor(R.color.colorAccent);
        //mClusterIconGenerator.setColor(R.color.colorAccent);
        mClusterIconGenerator.setContentView(multiProfile);
        mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

        mImageView = new ImageView(getActivity().getApplicationContext());
        mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
        int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
        mImageView.setPadding(padding, padding, padding, padding);
        mIconGenerator.setContentView(mImageView);

    }

    @Override
    protected void onBeforeClusterItemRendered(Place Place, MarkerOptions markerOptions) {
        // Draw a single Place.
        // Set the info window to show their name.
        mImageView.setImageResource(Place.placePhoto);
        Bitmap icon = mIconGenerator.makeIcon();



        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(Place.name);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<Place> cluster, MarkerOptions markerOptions) {
        // Draw multiple people.
        // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
        List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
        int width = mDimension;
        int height = mDimension;


        for (Place p : cluster.getItems()) {
            // Draw 4 at most.
            if (profilePhotos.size() == 4) break;
            Drawable drawable = getResources().getDrawable(p.placePhoto);
            drawable.setBounds(0, 0, width, height);

            profilePhotos.add(drawable);
        }
        MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
        multiDrawable.setBounds(0, 0, width, height);


        mClusterImageView.setImageDrawable(multiDrawable);
        Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        // Always render clusters.
        return cluster.getSize() > 1;
    }
}

    private void showPopupFragment(String placeName, String placeDesc, int img) {
        popupFragment popupF = new popupFragment();
        popupF = popupF.newInstance(placeName, placeDesc, img);

        this.getFragmentManager().beginTransaction()
                .add(popupF, "POPUP")
                .addToBackStack(null)
                .commit();
        /**FragmentManager fm = getSupportFragmentManager();



         popupF.show(fm, "fragment_popup");**/
    }


    @Override
    public boolean onClusterClick(Cluster<Place> cluster) {
        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().name;

        // Toast.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Place> cluster) {
        // Does nothing, but you could go to a list of the users.
    }

    @Override
    public boolean onClusterItemClick(Place item) {

        showPopupFragment(item.name, item.descr, item.placePhoto);
        // Does nothing, but you could go into the user's profile page, for example.
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(Place item) {
        // Does nothing, but you could go into the user's profile page, for example.
    }









}
