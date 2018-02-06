package com.example.android.bakingtime.Utils;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingtime.Model.Step;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.RecipeDescriptionFragment;

public class StepsDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_desc);
        Bundle incomingBundle = getIntent().getExtras();
        Step currentStep = incomingBundle.getParcelable("currentStep");

        Bundle outGoingBundle = new Bundle();
        outGoingBundle.putParcelable("step",currentStep);


        RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
        recipeDescriptionFragment.setArguments(outGoingBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.recipe_description_container,recipeDescriptionFragment).commit();

    }
}
