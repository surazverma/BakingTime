package com.example.android.bakingtime;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingtime.Model.Ingredient;

import java.util.ArrayList;

import static com.example.android.bakingtime.Utils.UpdateIngredientList.UPDATE_ACTION;
import static com.example.android.bakingtime.Utils.UpdateIngredientList.UPDATE_LIST_KEY;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static ArrayList<Ingredient> ingredientsList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        Intent intent = new Intent(context,ListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view,intent);
        views.setEmptyView(R.id.widget_list_view,R.id.empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

    }
    public static void updateWidget (Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,BakingWidgetProvider.class));

        final String action = intent.getAction();

        if (action.equals(UPDATE_ACTION)){
            ingredientsList = intent.getExtras().getParcelableArrayList(UPDATE_LIST_KEY);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_list_view);

            BakingWidgetProvider.updateWidget(context, appWidgetManager, appWidgetIds);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

