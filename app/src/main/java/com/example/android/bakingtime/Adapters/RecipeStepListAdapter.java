package com.example.android.bakingtime.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.Model.Step;
import com.example.android.bakingtime.R;

import java.util.List;

/**
 * Created by Suraz Verma on 2/2/2018.
 */

public class RecipeStepListAdapter extends RecyclerView.Adapter<RecipeStepListAdapter.ViewHolder> {
    private List<Step> steps;
    private Context context;
    final private  ItemClickListener stepClickListener;

    public RecipeStepListAdapter(Context context, List<Step> steps, ItemClickListener stepClickListener ){
        this.stepClickListener = stepClickListener;
        this.steps = steps;
        this.context = context;
    }


    public interface ItemClickListener{
        void onItemClicked(int itemPosition);
    }

    @Override
    public RecipeStepListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_steps_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int currentStepNumber = steps.get(position).getId();
        if(String.valueOf(currentStepNumber).equals("0")){
            holder.stepText.setVisibility(View.GONE);
            holder.stepNumber.setVisibility(View.GONE);
        }else{

        holder.stepText.setVisibility(View.VISIBLE);
            holder.stepNumber.setVisibility(View.VISIBLE);
        holder.stepNumber.setText(String.valueOf(position));}
        holder.stepShortDescription.setText(steps.get(position).getShortDescription());


    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void updateList(List<Step> recipeSteps, Context context) {
        this.steps = recipeSteps;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stepText;
        TextView stepNumber;
        TextView stepShortDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            stepText = itemView.findViewById(R.id.step_text);
            stepNumber = itemView.findViewById(R.id.step_number);
            stepShortDescription = itemView.findViewById(R.id.short_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            stepClickListener.onItemClicked(clickedPosition);
        }
    }
}
