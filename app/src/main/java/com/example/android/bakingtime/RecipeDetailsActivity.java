package com.example.android.bakingtime;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingtime.Model.Ingredient;
import com.example.android.bakingtime.Model.Recipes;
import com.example.android.bakingtime.Model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements
        RecipeDescriptionFragment.ButtonClickListener{

    private Recipes recipe;
    private List<Ingredient> recipeIngredients ;
    private List<Step> recipeSteps;
    private String recipeName;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if(findViewById(R.id.recipe_details_linear_layout)!=null){
            mTwoPane = true;
        }else{
            mTwoPane = false;
        }
        Bundle incomingBundle = getIntent().getExtras();
        if (incomingBundle!=null){
            recipe = incomingBundle.getParcelable("recipe");

        } else{
            recipe = savedInstanceState.getParcelable("recipe");
        }

        recipeName = recipe.getName();
        setTitle(recipeName);
        recipeSteps = recipe.getSteps();
        recipeIngredients = recipe.getIngredients();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) recipeSteps);
        stepsBundle.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) recipeIngredients);
        stepsBundle.putBoolean("mTwoPane",mTwoPane);
        stepsBundle.putString("recipe_name",recipeName);


        if (savedInstanceState == null) {
            IngredientsHeaderFragment ingredientsFragment = new IngredientsHeaderFragment();
            ingredientsFragment.setArguments(stepsBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_card_container, ingredientsFragment)
                    .commit();
        }



    }





    @Override
    public void ButtonClicked(List<Step> steps, int stepPosition) {
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList("ListOfSteps", (ArrayList<? extends Parcelable>) steps);
        stepBundle.putInt("clickedPosition",stepPosition);
        stepBundle.putBoolean("mTwoPane",true);

        RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
        recipeDescriptionFragment.setArguments(stepBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.recipe_description_container,recipeDescriptionFragment).commit();

    }
}
