package com.example.android.bakingtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.Model.Step;

/**
 * Created by Suraz Verma on 2/3/2018.
 */

public class RecipeDescriptionFragment extends Fragment {
    private Step stepsDescription;
    public RecipeDescriptionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_step_description,container,false);
        Bundle incomingBundle = getArguments();
        if (incomingBundle!=null){
        stepsDescription = incomingBundle.getParcelable("step");
            TextView descriptionTextView =  rootView.findViewById(R.id.description_text);
            if (stepsDescription != null) {
                String description = stepsDescription.getDescription();

                descriptionTextView.setText(description);

            }else{
                descriptionTextView.setText("Not done");
            }



        }


//        String videoURL = stepsDescription.getVideoURL();
//        String thumbnailURL = stepsDescription.getThumbnailURL();

        return rootView;

    }
}
