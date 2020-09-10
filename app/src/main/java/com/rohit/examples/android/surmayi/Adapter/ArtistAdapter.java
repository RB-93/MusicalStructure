package com.rohit.examples.android.surmayi.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohit.examples.android.surmayi.Fragment.ArtistFragment.OnItemSelectedListener;
import com.rohit.examples.android.surmayi.Model.SongAlbum;
import com.rohit.examples.android.surmayi.R;

import java.util.ArrayList;

/**
 * Adapter to get and inflate Artist items from Song Library
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistItemHolder> {

    /**
     * Context declaration to receive intent from current class
     * ArrayList to get list of artist
     * Click listener to get Artist item click events
     */
    private final Context context;
    private final ArrayList<SongAlbum> artistList;
    private final OnItemSelectedListener listener;

    public ArtistAdapter(Context context, ArrayList<SongAlbum> artistList, OnItemSelectedListener listener) {
        this.context = context;
        this.artistList = artistList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArtistItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         *  Inflating album view layout for AlbumAdapter
         */
        return new ArtistItemHolder(LayoutInflater.from(context).inflate(R.layout.layout_artist_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistItemHolder holder, int position) {
        /*
         * Getting resources from @param artists list with the corresponding position.
         */
        final SongAlbum artistLists = artistList.get(position);
        holder.artistImageView.setImageResource(artistLists.getAlbumArtId());
        holder.artistName.setText(artistLists.getSongArtist());

        /*
         * handling click events for CardView Artist item
         */
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onArtistSelected(artistLists.getSongArtist());
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    static class ArtistItemHolder extends RecyclerView.ViewHolder {

        /**
         * View items declaration to handle their data
         */
        final CardView cardView;
        final ImageView artistImageView;
        final TextView artistName;

        ArtistItemHolder(@NonNull View itemView) {
            super(itemView);

            /*
                Fetching view IDs from resource directory
             */
            cardView = itemView.findViewById(R.id.cardView);
            artistImageView = itemView.findViewById(R.id.artistImg);
            artistName = itemView.findViewById(R.id.artistName);
        }
    }
}