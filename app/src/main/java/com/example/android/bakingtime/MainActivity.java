package com.example.android.bakingtime;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.Adapters.RecipeListAdapter;
import com.example.android.bakingtime.Model.Recipes;
import com.example.android.bakingtime.utils.RecipeInterface;
import com.example.android.bakingtime.utils.SimpleIdlingResource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public final static String LIST_STATE_KEY = "recycler_list_state";
    private RecyclerView mRecyclerView;
    private RecipeListAdapter recipeListAdapter;
    private TextView mOfflineText;
    private ImageView mOfflineImage;
    private boolean mTwoPane;
    private boolean landscape;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Recipes> recipes;

    private Parcelable listState;


    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource(this);
        }
        return mIdlingResource;
    }




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            landscape = true;
            }else{
            landscape = false;
        }
        if(findViewById(R.id.main_screen)!=null){
            mTwoPane = true;
        }else{
            mTwoPane = false;
        }

        internetHandling();
        inflateViews();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = layoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, listState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }
    }
    private void inflateViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        if(mTwoPane){
             layoutManager = new GridLayoutManager(this,2);
        }else{
         layoutManager = new LinearLayoutManager(this);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

    }

    private void internetHandling(){
        Retrofit.Builder builder =  new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RecipeInterface mService = retrofit.create(RecipeInterface.class);
        final Call<ArrayList<Recipes>> recipe =  mService.getRecipes();
        if(mIdlingResource!=null){
            mIdlingResource.setIdleState(false);
        }
        recipe.enqueue(new Callback<ArrayList<Recipes>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipes>> call, Response<ArrayList<Recipes>> response) {
                recipes = response.body();

                mRecyclerView.setAdapter(new RecipeListAdapter(getApplicationContext(),recipes));
                if(mIdlingResource != null){
                    mIdlingResource.setIdleState(true);
                }
                Log.v(TAG,"status Code" + response.code());

            }

            @Override
            public void onFailure(Call<ArrayList<Recipes>> call, Throwable t) {
                mOfflineText = (TextView) findViewById(R.id.offline_text_view);
                mOfflineImage = (ImageView) findViewById(R.id.offline_image);
                mOfflineText.setVisibility(View.VISIBLE);
                mOfflineImage.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        });
        getIdlingResource();
    }

}
