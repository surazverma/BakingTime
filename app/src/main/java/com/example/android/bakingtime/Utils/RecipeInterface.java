package com.example.android.bakingtime.utils;

import com.example.android.bakingtime.Model.Recipes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Suraz Verma on 1/30/2018.
 */

public interface RecipeInterface {
    @GET("baking.json")
    Call<ArrayList<Recipes>> getRecipes();
}
