package com.rohit.examples.android.surmayi.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohit.examples.android.surmayi.Adapter.ArtistAdapter;
import com.rohit.examples.android.surmayi.Model.SongAlbum;
import com.rohit.examples.android.surmayi.Model.SongUtils;
import com.rohit.examples.android.surmayi.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Fragment to get and populate Artist view for Favorites Activity and Artist Item clicks
 */
public class ArtistFragment extends Fragment {

    /**
     * Recycler view to populate Artist items from Artist Adapter
     * Click listener to get Artist item click events
     */
    private OnItemSelectedListener listener;
    private RecyclerView recyclerView;

    public ArtistFragment() {
    }

    @Override
    public void onAttach(Context context) {
        /*
         *  Handling Exception for intents sent from LibraryActivity
         */
        super.onAttach(context);
        try {
            listener = (OnItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
         *  Inflating recyclerview to populate album view layout items for AlbumAdapter
         */
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Getting Recycler view ID and setting Grid column to show items.
        recyclerView = view.findViewById(R.id.song_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Getting all songs based on their Artist metadata
        ArrayList<SongAlbum> artistItems = SongUtils.getArtistLibrary(Objects.requireNonNull(getContext()));
        ArtistAdapter artistAdapter = new ArtistAdapter(getContext(), artistItems, listener);
        recyclerView.setAdapter(artistAdapter);
    }

    /**
     * update recyclerview grid column number depending on the current orientation
     *
     * @param configuration reference to change configuration
     */
    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        checkViewOrientation(configuration);
    }

    /**
     * Handling screen orientation to repopulate Album Items
     *
     * @param configuration to change orientation
     */
    private void checkViewOrientation(Configuration configuration) {
        int orientation = configuration.orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
    }

    /**
     * Click Listener interface to send clicks to Album Fragment
     */
    public interface OnItemSelectedListener {
        void onArtistSelected(String artistName);
    }
}