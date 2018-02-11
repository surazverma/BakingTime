package com.example.android.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.Adapters.IngredientListAdapter;
import com.example.android.bakingtime.Adapters.RecipeStepListAdapter;
import com.example.android.bakingtime.Model.Ingredient;
import com.example.android.bakingtime.Model.Step;
import com.example.android.bakingtime.utils.StepsDescActivity;
import com.example.android.bakingtime.utils.UpdateIngredientList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraz Verma on 2/1/2018.
 */

public class IngredientsHeaderFragment extends Fragment implements RecipeStepListAdapter.ItemClickListener {
    private List<Step> recipeSteps;
    private RecipeStepListAdapter mAdapter;
    private ArrayList<Ingredient> recipeIngredients;
    private IngredientListAdapter mIngredientAdapter;
    private static final String BUNDLE_RECYCLER_LAYOUT = "RecipeDetailsActivity.RecyclerView.layout";
    private static final String CURRENT_STATE = "current_state";
    private Bundle currentStateForFragment;
    private boolean mTwoPane;
    private String recipeName;
    private RecyclerView stepList;
    private RecyclerView ingredientRecyclerView;


    public IngredientsHeaderFragment(){

    }

    @Override
    public void onItemClicked(int itemPosition) {


        Bundle stepBundle = new Bundle();

        stepBundle.putParcelableArrayList("ListOfSteps", (ArrayList<? extends Parcelable>) recipeSteps);
        stepBundle.putInt("clickedPosition",itemPosition);
        stepBundle.putString("recipe_name",recipeName);

        if(mTwoPane) {
            stepBundle.putBoolean("mTwoPane",mTwoPane);
            if (currentStateForFragment == null) {

                RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
                recipeDescriptionFragment.setArguments(stepBundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.recipe_description_container, recipeDescriptionFragment).commit();
            }
        }else{
            stepBundle.putBoolean("mTwoPane",mTwoPane);
            Intent descriptionIntent = new Intent(getContext(), StepsDescActivity.class);
            descriptionIntent.putExtras(stepBundle);
            getContext().startActivity(descriptionIntent);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CURRENT_STATE, (ArrayList<? extends Parcelable>) recipeSteps);
        currentStateForFragment = outState;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient_card,container,false);

        Bundle incomingBundle = getArguments();
        if (savedInstanceState!=null){
            recipeSteps = savedInstanceState.getParcelableArrayList(CURRENT_STATE);
        }
        recipeSteps = incomingBundle.getParcelableArrayList("steps");
        recipeIngredients = incomingBundle.getParcelableArrayList("ingredients");
        mTwoPane = incomingBundle.getBoolean("mTwoPane");
        recipeName = incomingBundle.getString("recipe_name");
        stepList = rootView.findViewById(R.id.recipe_step_list_rv);
        mAdapter = new RecipeStepListAdapter(getContext(),new ArrayList<Step>(),this);
        mAdapter.updateList(recipeSteps,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        stepList.setLayoutManager(layoutManager);
        stepList.setAdapter(mAdapter);

        ingredientRecyclerView= rootView.findViewById(R.id.ingredients_list);
        RecyclerView.LayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext());
        mIngredientAdapter = new IngredientListAdapter(getContext(),new ArrayList<Ingredient>());
        mIngredientAdapter.updateList(recipeIngredients,getContext());
        ingredientRecyclerView.setLayoutManager(ingredientLayoutManager);
        ingredientRecyclerView.setAdapter(mIngredientAdapter);

        UpdateIngredientList.startService(getContext(),recipeIngredients);
        return rootView;

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState!=null){
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            stepList.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            ingredientRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    //    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
}
