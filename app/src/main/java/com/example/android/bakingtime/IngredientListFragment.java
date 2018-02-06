package com.example.android.bakingtime;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.Adapters.IngredientListAdapter;
import com.example.android.bakingtime.Model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraz Verma on 2/1/2018.
 */

public class IngredientListFragment extends Fragment {
    private List<Ingredient> recipeIngredientList;
    private IngredientListAdapter mAdapter;
    private static final String CURRENT_STATE = "current_state";




    public IngredientListFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_ingredients_screen,container,false);
        if (savedInstanceState != null){
            recipeIngredientList = savedInstanceState.getParcelableArrayList(CURRENT_STATE);
        }

        Bundle incomingBundle = getArguments();
        recipeIngredientList = incomingBundle.getParcelableArrayList("data");

        RecyclerView recyclerView =  rootView.findViewById(R.id.ingredient_list_recycler_View);
        mAdapter = new IngredientListAdapter(getContext(),new ArrayList<Ingredient>());
        mAdapter.updateList(recipeIngredientList,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(mAdapter);
        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CURRENT_STATE, (ArrayList<? extends Parcelable>) recipeIngredientList);
    }
}
