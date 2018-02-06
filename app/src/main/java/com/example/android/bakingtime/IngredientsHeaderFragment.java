package com.example.android.bakingtime;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.bakingtime.Adapters.RecipeStepListAdapter;
import com.example.android.bakingtime.Model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraz Verma on 2/1/2018.
 */

public class IngredientsHeaderFragment extends Fragment implements RecipeStepListAdapter.ItemClickListener {
    private List<Step> recipeSteps;
    private Step currentStep;
    private RecipeStepListAdapter mAdapter;


    public IngredientsHeaderFragment(){

    }

    @Override
    public void onItemClicked(int itemPosition) {

        Toast.makeText(getContext(),"item position"+itemPosition,Toast.LENGTH_SHORT).show();

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient_card,container,false);
        Bundle incomingBundle = getArguments();
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
