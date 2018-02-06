package com.example.android.bakingtime.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.Model.Ingredient;
import com.example.android.bakingtime.R;

import java.util.List;

/**
 * Created by Suraz Verma on 2/1/2018.
 */

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder>  {
    private List<Ingredient> ingredients;
    private Context context;
    public IngredientListAdapter(Context context,List<Ingredient> ingredients){
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredients_list_elements,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nameOfIngredient = ingredients.get(position).getIngredient();
        double quantityOfIngredient = ingredients.get(position).getQuantity();
        String measurementOfIngredient = ingredients.get(position).getMeasure();

        holder.ingredientName.setText(nameOfIngredient);
        holder.ingredientQuantity.setText(String.valueOf(quantityOfIngredient));
        holder.ingredientMeasurement.setText(measurementOfIngredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
    public void updateList(List<Ingredient> ingredient,Context context){
        this.ingredients = ingredient;
        this.context = context;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ingredientName;
        TextView ingredientQuantity;
        TextView ingredientMeasurement;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.name_of_ingredient);
            ingredientQuantity = itemView.findViewById(R.id.quantity_of_ingredient);
            ingredientMeasurement = itemView.findViewById(R.id.measurement_of_ingredient);
        }


    }
}
