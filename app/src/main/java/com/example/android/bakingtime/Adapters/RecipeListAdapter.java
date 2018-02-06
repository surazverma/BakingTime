package com.example.android.bakingtime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingtime.Model.Recipes;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.RecipeDetailsActivity;

import java.util.List;

/**
 * Created by Suraz Verma on 1/30/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private  List<Recipes> recipes;
    private  Context context;

    public RecipeListAdapter(Context context,List<Recipes> recipes) {
        this.context = context;
        this.recipes = recipes;
    }


    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String currentRecipeName = recipes.get(position).getName();

        holder.recipeName.setText(currentRecipeName);
        holder.recipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"It is working for the whole card nibba and the position is "+position,Toast.LENGTH_SHORT).show();

                Intent recipeDetailIntent = new Intent(context, RecipeDetailsActivity.class);
                Recipes currentRecipe = recipes.get(position);
                Bundle currentRecipeInfo = new Bundle();
                currentRecipeInfo.putParcelable("recipe",currentRecipe);
                recipeDetailIntent.putExtras(currentRecipeInfo);
                context.startActivity(recipeDetailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
    public void updateRecipes(List<Recipes> recipes,Context context) {
        this.recipes = recipes;
        this.context = context;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView recipeName;
        CardView recipeCard;
        public ViewHolder(View view){
            super(view);
            recipeCard = view.findViewById(R.id.recipe_card);
            recipeName = view.findViewById(R.id.recipe_name);
        }
    }
}
