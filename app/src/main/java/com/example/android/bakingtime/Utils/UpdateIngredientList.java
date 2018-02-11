package com.example.android.bakingtime.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.bakingtime.Model.Ingredient;

import java.util.ArrayList;

/**
 * Created by Suraz Verma on 2/9/2018.
 */

public class UpdateIngredientList extends IntentService {
    public static final String UPDATE_LIST_KEY = "update_list_key";
    public static final String UPDATE_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";



    public UpdateIngredientList() {
        super("UpdateIngredientList");
    }

    public static void startService(Context context, ArrayList<Ingredient> currentIngredient){
        Intent intent = new Intent(context,UpdateIngredientList.class);
        intent.putExtra(UPDATE_LIST_KEY,currentIngredient);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            ArrayList<Ingredient> currentIngredients =
                    intent.getExtras().getParcelableArrayList(UPDATE_LIST_KEY);
            handleActionUpdate(currentIngredients);
        }
    }

    private void handleActionUpdate(ArrayList<Ingredient> currentIngredients) {
        Intent updateIntent = new Intent(UPDATE_ACTION);
        updateIntent.setAction(UPDATE_ACTION);
        updateIntent.putExtra(UPDATE_LIST_KEY,currentIngredients);
        sendBroadcast(updateIntent);
    }


}
