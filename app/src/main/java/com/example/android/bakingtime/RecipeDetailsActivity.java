package com.example.android.bakingtime;

import android.content.Intent;
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
        IngredientsHeaderFragment.OnCardClickListener{
    private Recipes recipe;
    private List<Ingredient> recipeIngredients ;
    private List<Step> recipeSteps;
    private Step currentStep;
    private int itemPostion;
    private boolean itemClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Bundle incomingBundle = getIntent().getExtras();
        if (incomingBundle!=null){
            recipe = incomingBundle.getParcelable("recipe");

        } else{
            recipe = savedInstanceState.getParcelable("recipe");
        }

        String recipeName = recipe.getName();
        setTitle(recipeName);
        recipeSteps = recipe.getSteps();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) recipeSteps);
        recipeIngredients = recipe.getIngredients();



        IngredientsHeaderFragment ingredientsFragment = new IngredientsHeaderFragment();
        ingredientsFragment.setArguments(stepsBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.ingredient_card_container,ingredientsFragment)
                .commit();




    }

    @Override
    public void OnCardSelected() {

        Intent recipeWork = new Intent(this,RecipeWork.class);
        Bundle ingredientsBundle = new Bundle();
        ingredientsBundle.putParcelableArrayList("ing", (ArrayList<? extends Parcelable>) recipeIngredients);
        recipeWork.putExtras(ingredientsBundle);
        this.startActivity(recipeWork);

    }



}
