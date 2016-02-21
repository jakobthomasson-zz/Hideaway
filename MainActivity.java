package com.example.mam032.hideaways;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;

import com.example.mam032.hideaways.model.Globals;
import com.example.mam032.hideaways.model.Place;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Removed toolbar,
   // private Toolbar toolbar;

    private ViewPager mViewPager;
    Bitmap bip;


    // Array with all object, passed as an extra in intent
    protected ArrayList<Place> placeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Globals g = Globals.getInstance();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        toolbar.setLogoDescription(getResources().getString(R.string.logo_desc));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(mViewPager);
        //Data in application
        placeList = new ArrayList<>();
        initDataset(g);

        //Add a new hideaway visible at all times, every layout
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new Hideaway", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
//        if(id == R.id.action_new){
//            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 0);
//        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        String filename = "pippo.png";
        File sd = Environment.getExternalStorageDirectory();
        File dest = new File(sd, filename);

        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, NewHideawayActivity.class);
        intent.putExtra("PATH", dest.getAbsolutePath());
        startActivity(intent);
    }

    /**
     * Adding fragments to ViewPager
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {

        //Not tabview any more, might change back Different fragments in
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MapFragment(), "MAP");
        adapter.addFrag(new CardFragment(), "NEARBY");
        adapter.addFrag(new SocialFragment(), "SOCIAL");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void initDataitem(Globals g, Bitmap b){

    }
    private void initDataset(Globals g) {

        Log.d("Yolo", "här har vi varit innan");
        if(g.isEmpty()) {
            g.addData(new Place(position(55.704901, 13.203250), "Botaniska Trädgården", "Ställe där man kan mysa", 5, R.drawable.botaniska));
            g.addData(new Place(position(55.703930, 13.193158), "Domkyrkan", "Prisa gudarna, hallelujah", 2, R.drawable.domkyrkan));
            g.addData(new Place(position(55.705682, 13.194831), "Lundagård", "Alla studenternas Mecka", 5, R.drawable.lundagard));
            g.addData(new Place(position(55.723326, 13.190024), "Sankt Hans Backar", "Perfekt ställe att åka pulka på", 4, R.drawable.sankthansbackar));
            g.addData(new Place(position(55.709049, 13.196959), "Universitetsbiblioteket", "Lär dig massa saker", 3, R.drawable.stadsbibilotek));
            g.addData(new Place(position(55.702846, 13.193115), "Stortorget", "Ät glass här", 3, R.drawable.stortorget));
            placeList = g.getData();
        }

       /*
        placeList.add(new Place(position(55.704901, 13.203250), "Botaniska Trädgården", "Ställe där man kan mysa", 5, R.drawable.botaniska));
        placeList.add(new Place(position(55.703930, 13.193158), "Domkyrkan", "Prisa gudarna, hallelujah", 2, R.drawable.domkyrkan));
        placeList.add(new Place(position(55.705682, 13.194831), "Lundagård", "Alla studenternas Mecka", 5, R.drawable.lundagard));
        placeList.add(new Place(position(55.723326, 13.190024), "Sankt Hans Backar", "Perfekt ställe att åka pulka på", 4, R.drawable.sankthansbackar));
        placeList.add(new Place(position(55.709049, 13.196959), "Universitetsbiblioteket", "Lär dig massa saker", 3, R.drawable.stadsbibilotek));
        placeList.add(new Place(position(55.702846, 13.193115), "Stortorget", "Ät glass här", 3, R.drawable.stortorget));
         */
    }



    private LatLng position(double n, double o) {
        return new LatLng(n, o);
        //return new LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683));
    }
}

