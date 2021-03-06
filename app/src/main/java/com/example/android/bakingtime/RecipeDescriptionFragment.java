package com.example.android.bakingtime;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.Model.Step;
import com.example.android.bakingtime.utils.StepsDescActivity;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Suraz Verma on 2/3/2018.
 */

public class RecipeDescriptionFragment extends Fragment {
    private static final String SELECTED_POSITION ="player_position" ;
    private static final String PLAYER_STATE = "player_state" ;
    private Step stepsDescription;
    private List<Step> currentRecipeStepList;
    private int currentStepPosition;
    private Uri mediaUri;

    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private static final String CURRENT_STATE = "current_state";
    private BandwidthMeter bandwidthMeter;
    private boolean mTwoPane;
    private long videoPosition;
    private boolean playWhenReady;


    public interface ButtonClickListener{
        void ButtonClicked(List<Step> steps,int stepPosition);
    }
    ButtonClickListener buttonClickListener;
    public RecipeDescriptionFragment(){

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_STATE,stepsDescription);
        outState.putLong(SELECTED_POSITION,videoPosition);
        outState.putBoolean(PLAYER_STATE,playWhenReady);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_step_description,container,false);
        ImageView videoReplacementImageView = rootView.findViewById(R.id.replacement_image);

        Bundle incomingBundle = getArguments();


        simpleExoPlayerView  = rootView.findViewById(R.id.video_player_view);
        videoPosition = C.TIME_UNSET;
        if (savedInstanceState!=null){
            playWhenReady = savedInstanceState.getBoolean(PLAYER_STATE);
            stepsDescription = savedInstanceState.getParcelable(CURRENT_STATE);
            videoPosition = savedInstanceState.getLong(SELECTED_POSITION,C.TIME_UNSET);
        }

        currentRecipeStepList = incomingBundle.getParcelableArrayList("ListOfSteps");
        currentStepPosition = incomingBundle.getInt("clickedPosition");
        mTwoPane = incomingBundle.getBoolean("mTwoPane");


        if(mTwoPane){
                    buttonClickListener = (RecipeDetailsActivity)getActivity();
        }else{
                    buttonClickListener = (StepsDescActivity)getActivity();
        }
        stepsDescription = currentRecipeStepList.get(currentStepPosition);
        bandwidthMeter = new DefaultBandwidthMeter();
        String videoURL;

        final String description;

        String thumbnailURL;


                 description = stepsDescription.getDescription();
                 videoURL = stepsDescription.getVideoURL();
                 thumbnailURL = stepsDescription.getThumbnailURL();

        final TextView descriptionTextView =  rootView.findViewById(R.id.description_text);
                descriptionTextView.setText(description);
        boolean playerAvailable;
        if(!videoURL.isEmpty()){
            mediaUri = Uri.parse(videoURL).buildUpon().build();
            initializePlayer(mediaUri);
            playerAvailable = true;
        }
        else if (!thumbnailURL.isEmpty()){
            simpleExoPlayer = null;
            simpleExoPlayerView.setVisibility(View.GONE);
            playerAvailable = false;
            videoReplacementImageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(thumbnailURL).placeholder(R.drawable.video_replacement_image).error(R.drawable.video_replacement_image).into(videoReplacementImageView);

        }else{
            simpleExoPlayer = null;
            simpleExoPlayerView.setVisibility(View.GONE);
            videoReplacementImageView.setImageResource(R.drawable.video_replacement_image);
            videoReplacementImageView.setVisibility(View.VISIBLE);
            playerAvailable = false;
        }

        FloatingActionButton nextButton =  rootView.findViewById(R.id.next_button);
        if (currentStepPosition == currentRecipeStepList.size()-1){
            nextButton.setVisibility(View.GONE);
        }
        FloatingActionButton prevButton =  rootView.findViewById(R.id.prev_button);

        if (currentStepPosition==0){
            prevButton.setVisibility(View.GONE);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentStepPosition!=currentRecipeStepList.size())
            buttonClickListener.ButtonClicked(currentRecipeStepList,currentStepPosition+1);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentStepPosition!=0){
                    buttonClickListener.ButtonClicked(currentRecipeStepList,currentStepPosition-1);
                }
            }
        });
        CardView descriptionCardView = rootView.findViewById(R.id.description_card);
        boolean landscape ;
        if(getContext().getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            landscape = true;
        }else{
            landscape = false;
        }

        if(!mTwoPane && landscape && playerAvailable){

            descriptionCardView.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            prevButton.setVisibility(View.GONE);
        }
        return rootView;

    }

    private void initializePlayer(Uri mediaURL){
        if(simpleExoPlayer == null){


            TrackSelection.Factory videoTrackSelectionFactory =  new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();


            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            String userAgent = Util.getUserAgent(getContext(),"BakingTime");
            MediaSource mediaSource = new ExtractorMediaSource(mediaURL,
                    new DefaultDataSourceFactory(getContext(),userAgent),
                    new DefaultExtractorsFactory(),null,null);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            if(videoPosition!=C.TIME_UNSET)
            {simpleExoPlayer.seekTo(videoPosition);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            }else {
                simpleExoPlayer.setPlayWhenReady(true);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (simpleExoPlayer!=null){

            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer!=null){
            videoPosition = simpleExoPlayer.getCurrentPosition();
             playWhenReady =  simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mediaUri!=null){
            initializePlayer(mediaUri);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (simpleExoPlayer!=null){

            simpleExoPlayer.release();

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (simpleExoPlayer!=null){
            simpleExoPlayer.stop();
            simpleExoPlayer.release();

        }
    }
}
