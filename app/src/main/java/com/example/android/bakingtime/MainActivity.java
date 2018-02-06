package com.example.android.bakingtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.Adapters.RecipeListAdapter;
import com.example.android.bakingtime.Model.Recipes;
import com.example.android.bakingtime.Utils.RecipeInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecipeListAdapter recipeListAdapter;
    private TextView mOfflineText;
    private ImageView mOfflineImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit.Builder builder =  new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RecipeInterface mService = retrofit.create(RecipeInterface.class);
        Call<ArrayList<Recipes>>recipe =  mService.getRecipes();
        recipe.enqueue(new Callback<ArrayList<Recipes>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipes>> call, Response<ArrayList<Recipes>> response) {
                ArrayList<Recipes> recipe = response.body();
                mRecyclerView.setAdapter(new RecipeListAdapter(getApplicationContext(),recipe));
                recipeListAdapter.updateRecipes(recipe,getApplicationContext());
                Log.v(TAG,"status Code" + response.code());

            }

            @Override
            public void onFailure(Call<ArrayList<Recipes>> call, Throwable t) {

            }
        });

        inflateViews();


    }

    private void inflateViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        recipeListAdapter = new RecipeListAdapter(this, new ArrayList<Recipes>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(recipeListAdapter);


    }







}
