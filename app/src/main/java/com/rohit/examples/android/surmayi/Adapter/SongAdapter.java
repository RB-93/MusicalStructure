package com.rohit.examples.android.surmayi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohit.examples.android.surmayi.Activity.NowPlayingActivity;
import com.rohit.examples.android.surmayi.Model.SongAlbum;
import com.rohit.examples.android.surmayi.R;

import java.util.ArrayList;

import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ALBUM_TAG;
import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ARTIST_TAG;
import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ITEM_POS;

/**
 * Adapter to get and inflate Artist/Albums items from Song Library
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongItemViewHolder> {

    /**
     * Context declaration to receive intent from current class
     * ArrayList, fragment tag & Artist name to get list of album, fragment instance & Artist list respectively
     */
    private final Context mContext;
    private final ArrayList<SongAlbum> mSongAlbums;
    private final String mFragTag;
    private final String mSongArtistName;

    public SongAdapter(Context context, ArrayList<SongAlbum> songAlbums, String fgTag, String songArtistName) {
        this.mContext = context;
        this.mSongAlbums = songAlbums;
        this.mFragTag = fgTag;
        this.mSongArtistName = songArtistName;
    }

    /**
     * Handle Viewholder for the selected fragment layout
     *
     * @param parent   to inflate layout on Parent viewGroup
     * @param viewType to get view bounds
     * @return new instance of Viewholder with the intended layout
     */
    @NonNull
    @Override
    public SongItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (mFragTag) {
            case ALBUM_TAG:
                return new SongItemViewHolder((LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.layout_album_list, parent, false)));

            case ARTIST_TAG:
                return new SongItemViewHolder((LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.layout_artist_list, parent, false)));

            default:
                return new SongItemViewHolder(LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.fragments_song_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final SongItemViewHolder songItemViewHolder, final int position) {
        final SongAlbum songAlbum = mSongAlbums.get(position);

        /*
         *  Setting content for View in layout
         */
        songItemViewHolder.songImgView.setImageResource(songAlbum.getAlbumArtId());
        songItemViewHolder.songTitle.setText(songAlbum.getSongName());
        songItemViewHolder.songArtist.setText(songAlbum.getSongArtist());

        /*
         *  If not null, then handling click listener for overflow menu items click events
         */
        if (songItemViewHolder.ic_more != null) {
            songItemViewHolder.ic_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(mContext, view);
                    popupMenu.inflate(R.menu.overflow_menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.play_next:
                                    break;

                                case R.id.add_to_playlist:
                                    break;

                                case R.id.add_to_favorites:
                                    break;

                                case R.id.share:
                                    break;

                                default:
                                    return false;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });

        }

        /*
         *  handling click events over CardView clicks
         *  Sending intent to Now Playing activity with specified Tag
         */
        songItemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Now Playing Activity
                Intent playIntent = new Intent(mContext, NowPlayingActivity.class);
                playIntent.putExtra(ITEM_POS, songItemViewHolder.getAdapterPosition());

                if (mSongArtistName != null && mSongArtistName.length() > 0) {
                    playIntent.putExtra("ARTIST_NAME", mSongArtistName);
                }
                mContext.startActivity(playIntent);
            }
        });

        /*
         *  handling click events over CardView clicks
         *  Sending intent to Now Playing activity with specified Tag
         */
        songItemViewHolder.songImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Now Playing Activity
                Intent playIntent = new Intent(mContext, NowPlayingActivity.class);
                playIntent.putExtra(ITEM_POS, songItemViewHolder.getAdapterPosition());

                if (mSongArtistName != null && mSongArtistName.length() > 0) {
                    playIntent.putExtra("ARTIST_NAME", mSongArtistName);
                }
                mContext.startActivity(playIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongAlbums.size();
    }

    public class SongItemViewHolder extends RecyclerView.ViewHolder {
        /**
         * View declaration for layout
         */
        final CardView cardView;
        final ImageView songImgView;
        final TextView songTitle;
        final TextView songArtist;
        final ImageButton ic_more;

        SongItemViewHolder(View itemView) {
            super(itemView);
            /*
             *  Fetching ViewIDs for the views
             */
            cardView = itemView.findViewById(R.id.cardView);
            songImgView = itemView.findViewById(R.id.song_art);
            songTitle = itemView.findViewById(R.id.song_text);
            songArtist = itemView.findViewById(R.id.song_artist);
            ic_more = itemView.findViewById(R.id.action_more);
        }
    }
}