package com.example.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.Adapters.RecipeStepListAdapter;
import com.example.android.bakingtime.Model.Step;
import com.example.android.bakingtime.Utils.StepsDescActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraz Verma on 2/1/2018.
 */

public class IngredientsHeaderFragment extends Fragment implements RecipeStepListAdapter.ItemClickListener {
    private List<Step> recipeSteps;
    private RecipeStepListAdapter mAdapter;
    private int clickedItemPosition;
    private static final String CURRENT_STATE = "current_state";


    public IngredientsHeaderFragment(){

    }

    @Override
    public void onItemClicked(int itemPosition) {
        Intent descriptionIntent = new Intent(getContext(), StepsDescActivity.class);
        Step currentStep = recipeSteps.get(itemPosition);
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelable("currentStep",currentStep);
        descriptionIntent.putExtras(stepBundle);
        getContext().startActivity(descriptionIntent);

//        RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
//        recipeDescriptionFragment.setArguments(stepBundle);
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.recipe_description_container,recipeDescriptionFragment).commit();



    }



    public interface OnCardClickListener{
        void OnCardSelected();
    }
    OnCardClickListener cardListener;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            cardListener = (OnCardClickListener) context;


        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+"implement OnCardListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CURRENT_STATE, (ArrayList<? extends Parcelable>) recipeSteps);
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

        RecyclerView stepList = rootView.findViewById(R.id.recipe_step_list_rv);
        mAdapter = new RecipeStepListAdapter(getContext(),new ArrayList<Step>(),this);
        mAdapter.updateList(recipeSteps,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        stepList.setLayoutManager(layoutManager);
        stepList.setAdapter(mAdapter);









        CardView ingredientCard =  rootView.findViewById(R.id.recipe_ingredient_card);
        ingredientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardListener.OnCardSelected();
            }
        });

        return rootView;

    }





}