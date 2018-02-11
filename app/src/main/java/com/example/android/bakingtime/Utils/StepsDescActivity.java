package com.example.android.bakingtime.utils;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingtime.Model.Step;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.RecipeDescriptionFragment;

import java.util.ArrayList;
import java.util.List;

public class StepsDescActivity extends AppCompatActivity implements RecipeDescriptionFragment.ButtonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_desc);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle incomingBundle = getIntent().getExtras();
        List<Step> steps = incomingBundle.getParcelableArrayList("ListOfSteps");
        int currentStepPosition = incomingBundle.getInt("clickedPosition");
        String recipeName = incomingBundle.getString("recipe_name");
        setTitle(recipeName);


        Bundle outGoingBundle = new Bundle();
        outGoingBundle.putParcelableArrayList("ListOfSteps", (ArrayList<? extends Parcelable>) steps);
        outGoingBundle.putInt("clickedPosition",currentStepPosition);
        if(savedInstanceState == null) {

            RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
            recipeDescriptionFragment.setArguments(outGoingBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_description_container, recipeDescriptionFragment).commit();
        }
    }

    @Override
    public void ButtonClicked(List<Step> steps, int stepPosition) {

        Bundle outGoingBundle = new Bundle();
        outGoingBundle.putParcelableArrayList("ListOfSteps", (ArrayList<? extends Parcelable>) steps);
        outGoingBundle.putInt("clickedPosition",stepPosition);
        RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
        recipeDescriptionFragment.setArguments(outGoingBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.recipe_description_container, recipeDescriptionFragment).commit();
    }
}
