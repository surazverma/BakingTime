package com.example.android.bakingtime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.Model.Recipes;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.RecipeDetailsActivity;
import com.squareup.picasso.Picasso;

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
        final int currentRecipeServings = recipes.get(position).getServings();
        String currentRecipeImageUrl = recipes.get(position).getImage();
        holder.recipeName.setText(currentRecipeName);
        holder.servingNumber.setText(String.valueOf(currentRecipeServings));

        if(!currentRecipeImageUrl.isEmpty()) {
            Picasso.with(context).load(currentRecipeImageUrl).error(R.drawable.default_baking_image).into(holder.recipeImage);
        } else{
            holder.recipeImage.setImageResource(R.drawable.default_baking_image);
        }
            holder.recipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent recipeDetailIntent = new Intent(context, RecipeDetailsActivity.class);
                Recipes currentRecipe = recipes.get(position);
                Bundle currentRecipeInfo = new Bundle();
                currentRecipeInfo.putParcelable("recipe",currentRecipe);
                recipeDetailIntent.putExtras(currentRecipeInfo);
                recipeDetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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
        TextView servingNumber;
        CardView recipeCard;
        ImageView recipeImage;
        public ViewHolder(View view){
            super(view);

            servingNumber = view.findViewById(R.id.number_of_serving);
            recipeCard = view.findViewById(R.id.recipe_card);
            recipeName = view.findViewById(R.id.recipe_name);
            recipeImage = view.findViewById(R.id.recipe_image);
        }
    }
}
