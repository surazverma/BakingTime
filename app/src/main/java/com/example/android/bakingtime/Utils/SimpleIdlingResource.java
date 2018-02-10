package com.example.android.bakingtime.Utils;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import com.example.android.bakingtime.MainActivity;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Suraz Verma on 2/10/2018.
 */

public class SimpleIdlingResource implements IdlingResource {



    @Nullable
    private volatile ResourceCallback mCallback;
    private MainActivity activity;

    public SimpleIdlingResource(MainActivity activity){
        this.activity = activity;
    }
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }
    public void setIdleState(boolean isIdleNow){
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback !=null){
            mCallback.onTransitionToIdle();
        }
    }
}
