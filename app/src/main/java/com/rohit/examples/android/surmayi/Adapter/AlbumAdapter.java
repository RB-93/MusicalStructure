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

import com.rohit.examples.android.surmayi.Fragment.AlbumFragment.OnItemSelectedListener;
import com.rohit.examples.android.surmayi.Model.SongAlbum;
import com.rohit.examples.android.surmayi.R;

import java.util.ArrayList;

/**
 * Adapter to get and inflate Album items from Song Library
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumItemHolder> {

    /**
     * Context declaration to receive intent from current class
     * ArrayList to get list of albums
     * Click listener to get Album item click events
     */
    private final Context context;
    private final ArrayList<SongAlbum> albumList;
    private final OnItemSelectedListener listener;

    public AlbumAdapter(Context context, ArrayList<SongAlbum> albumList, OnItemSelectedListener listener) {
        this.context = context;
        this.albumList = albumList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlbumItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         *  Inflating album view layout for AlbumAdapter
         */
        return new AlbumItemHolder(LayoutInflater.from(context).inflate(R.layout.layout_album_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumItemHolder holder, int position) {

        /*
         * Getting resources from @param album lists with the corresponding position.
         */
        final SongAlbum albumLists = albumList.get(position);
        holder.albumImageView.setImageResource(albumLists.getAlbumArtId());
        holder.albumName.setText(albumLists.getSongArtist());

        /*
         * handling click events for CardView Album item
         */
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAlbumSelected(albumLists.getSongArtist());
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    static class AlbumItemHolder extends RecyclerView.ViewHolder {

        /**
         * View items declaration to handle their data
         */
        final CardView cardView;
        final ImageView albumImageView;
        final TextView albumName;

        AlbumItemHolder(@NonNull View itemView) {
            super(itemView);

            /*
                Fetching view IDs from resource directory
             */
            cardView = itemView.findViewById(R.id.cardView);
            albumImageView = itemView.findViewById(R.id.albumImg);
            albumName = itemView.findViewById(R.id.albumName);
        }
    }
}
