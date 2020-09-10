package com.rohit.examples.android.surmayi.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rohit.examples.android.surmayi.R;

import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.FRAGMENT_TAG;

/**
 * Fragment to get and populate view for Favorites Activity and Empty state
 */
public class BaseFragment extends Fragment {

    /**
     * Listener for Back navigation through Back Button of Up navigation
     */
    private onBackListener listener;

    public BaseFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*
         *  Handling Exception for intents sent from LibraryActivity
         */
        try {
            listener = (onBackListener) context;
        } catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + cce.getMessage());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
         *  Inflating layout for Favorites Activity with Empty state
         */
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        /*
         *  Getting view IDs of favorites activity layout
         */

        Button button = view.findViewById(R.id.back_button);

        /*
         *  Handling click event for back button press, passing intent back to Library Activity
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBackToLibrary(getArguments() != null ? getArguments().getString(FRAGMENT_TAG) : null);
            }
        });

        return view;
    }

    /**
     * Listener interface to handle back button click events
     */
    public interface onBackListener {
        void onBackToLibrary(String backTag);
    }
}