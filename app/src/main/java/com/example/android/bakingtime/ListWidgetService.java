package com.example.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingtime.Model.Ingredient;

import java.util.List;

/**
 * Created by Suraz Verma on 2/9/2018.
 */

public class ListWidgetService extends RemoteViewsService {
    List<Ingredient> ingredientListForWidget;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        Context mContext;

        public ListRemoteViewsFactory (Context applicationContext,Intent intent ){
            mContext = applicationContext;
        }
        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredientListForWidget = BakingWidgetProvider.ingredientsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if(ingredientListForWidget==null){
            return 0;}
            return ingredientListForWidget.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if(mContext == null){
                return null;
            }
            RemoteViews views  = new RemoteViews(mContext.getPackageName(),R.layout.ingredients_list_widgets);
            String currentIngredientName = ingredientListForWidget.get(position).getIngredient();
            String currentMeasurement = ingredientListForWidget.get(position).getMeasure();
            double currentQuantity = ingredientListForWidget.get(position).getQuantity();

            views.setTextViewText(R.id.ingredient_quantity,String.valueOf(currentQuantity));
            views.setTextViewText(R.id.ingredient_measure,currentMeasurement);
            views.setTextViewText(R.id.ingredient_name,currentIngredientName);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
