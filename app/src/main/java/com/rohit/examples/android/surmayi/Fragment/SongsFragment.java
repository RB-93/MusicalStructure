package com.rohit.examples.android.surmayi.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rohit.examples.android.surmayi.Adapter.SongAdapter;
import com.rohit.examples.android.surmayi.Model.SongAlbum;
import com.rohit.examples.android.surmayi.Model.SongUtils;
import com.rohit.examples.android.surmayi.R;

import java.util.ArrayList;
import java.util.Objects;

import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ARTIST;
import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ARTIST_NAME;

/**
 * Fragment handled for Song List in Library activity
 */
public class SongsFragment extends Fragment {
    /**
     * Array to store Albums
     * variable to store Song name and artist
     */
    private ArrayList<SongAlbum> mSongAlbums;
    private String mSongArtist;

    public SongsFragment() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         *  Getting Tags for storage variables to show appropriate content
         */
        if (getArguments() != null) {
            mSongAlbums = (ArrayList<SongAlbum>) getArguments().getSerializable(ARTIST);
            mSongArtist = getArguments().getString(ARTIST_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
         *  Inflating Recycler view ot populate list of songs
         */
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
         *  Getting View ID for Recycler view
         *  Configuring Item view style with Orientation
         */
        RecyclerView recyclerView = view.findViewById(R.id.song_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        /*
         *  If Albums are not found, then populate List
         */
        if (mSongAlbums == null || mSongAlbums.size() == 0) {
            mSongAlbums = SongUtils.getSongLibrary(getContext());
        }
        // Setting adapter for the Song List
        SongAdapter songAdapter = new SongAdapter(getContext(), mSongAlbums, getTag(), mSongArtist);
        recyclerView.setAdapter(songAdapter);
    }
}