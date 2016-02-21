package com.example.mam032.hideaways;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.mam032.hideaways.R;
import com.example.mam032.hideaways.model.Globals;
import com.example.mam032.hideaways.model.Place;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.IconSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.TextSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by jakob on 2015-12-18 .
 */
public class CardFragment extends Fragment{
    CardArrayRecyclerViewAdapter mCardArrayAdapter;

    protected boolean mListShown;
    protected View mProgressContainer;
    protected View mListContainer;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupListFragment(view);
    }
    /**
     * Setup the list fragment
     *
     * @param root
     */
    protected void setupListFragment(View root) {

        mListContainer = root.findViewById(R.id.carddemo_listContainer);
        mProgressContainer = root.findViewById(R.id.carddemo_progressContainer);
        mListShown = true;
    }

    protected void displayList(){
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    protected void hideList(boolean animate){
        setListShown(false,animate);
    }

    /**
     * @param shown
     * @param animate
     */
    protected void setListShown(boolean shown, boolean animate) {
        if (mListShown == shown) {
            return;
        }
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
            }
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.INVISIBLE);
        }
    }

    public void setListShown(boolean shown) {
        setListShown(shown, true);
    }

    public void setListShownNoAnimation(boolean shown) {
        setListShown(shown, false);
    }


    public void onCreate(Bundle savedInstanceState) {
        Log.i("MyFragment", "on Create Fragment");
        super.onCreate(savedInstanceState);
        //first time application is created, 0 is default value

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.demo_fragment_native_recyclerview_materialcard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hideList(false);

        //Set the arrayAdapter
        ArrayList<Card> cards = new ArrayList<Card>();

        mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) getActivity().findViewById(R.id.carddemo_recyclerview2);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        //Load cards
        new LoaderAsyncTask().execute();
    }

    //-------------------------------------------------------------------------------------------------------------
    // Images loader
    //-------------------------------------------------------------------------------------------------------------
    /**
     * Async Task to elaborate images
     */
    class LoaderAsyncTask extends AsyncTask<Void, Void, ArrayList<Card>> {

        LoaderAsyncTask() {
        }

        @Override
        protected ArrayList<Card> doInBackground(Void... params) {
            //elaborate images
            SystemClock.sleep(1000); //delay to simulate download, don't use it in a real app
            if (isAdded()) {
                ArrayList<Card> cards = initCard();
                return cards;
            }else
                return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            //Update the adapter
            updateAdapter(cards);
            displayList();
        }
    }


    /**
     * This method builds a simple list of cards
     */
    private ArrayList<Card> initCard() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        ArrayList<Place> placeList = Globals.getInstance().getData();

        for (Place p : placeList) {
            ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();

            // Set supplemental actions
            TextSupplementalAction t1 = new TextSupplementalAction(getActivity(), R.id.text1);
            t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getActivity()," Click on Text SHARE "+card.getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
            actions.add(t1);

            TextSupplementalAction t2 = new TextSupplementalAction(getActivity(), R.id.text2);
            t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getActivity()," Click on Text LEARN "+card.getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
            actions.add(t2);

            //Create a Card, set the title over the image and set the thumbnail
            MaterialLargeImageCard card =
                    MaterialLargeImageCard.with(getActivity())
                            .setTextOverImage(p.name)
                            .setTitle(p.name)
                            .setSubTitle(p.descr)
                            .useDrawableId(p.placePhoto)
                            .setupSupplementalActions(R.layout.carddemo_native_material_supplemental_actions_large, actions)
                            .build();

            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(getActivity()," Click on ActionArea ",Toast.LENGTH_SHORT).show();
                }
            });

            cards.add(card);
        }

        return cards;
    }

    /**
     * Update the adapter
     */
    private void updateAdapter(ArrayList<Card> cards) {
        if (cards != null) {
            mCardArrayAdapter.addAll(cards);
        }
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
