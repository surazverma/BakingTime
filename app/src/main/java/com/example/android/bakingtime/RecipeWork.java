package com.example.android.bakingtime;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingtime.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeWork extends AppCompatActivity {
    List<Ingredient> ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_work);


        Bundle incomingBundle = getIntent().getExtras();
        if (incomingBundle!=null){
            ingredients = incomingBundle.getParcelableArrayList("ing");

        }else{
            ingredients = savedInstanceState.getParcelableArrayList("ing");


        }
        if(savedInstanceState == null) {

            Bundle outGoingBundle = new Bundle();
            outGoingBundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) ingredients);

            IngredientListFragment ingredientListFragment = new IngredientListFragment();
            ingredientListFragment.setArguments(outGoingBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_list_container, ingredientListFragment)
                    .commit();
        }
    }


}
