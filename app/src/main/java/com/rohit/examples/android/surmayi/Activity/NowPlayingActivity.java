package com.rohit.examples.android.surmayi.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rohit.examples.android.surmayi.Model.SongAlbum;
import com.rohit.examples.android.surmayi.Model.SongUtils;
import com.rohit.examples.android.surmayi.R;

import java.util.ArrayList;
import java.util.Random;

import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ALBUM_NAME;
import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ARTIST_NAME;
import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ITEM_POS;

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Handler to manage user delays
     */
    private final Handler handler = new Handler();
    /**
     * View variable declaration for layout
     */
    private TextView songTitle;
    private TextView songArtist;
    private TextView startTime;
    private TextView endTime;
    private ImageView songImg;
    private ImageView circleImg;
    private ImageView playpause;
    private ImageView fav;
    private ImageView repeat;
    private ImageView shuffle;
    private SeekBar seekBar;
    // @param toast to get Toast Notification
    private Toast toast;
    // Declaration to get instance of the elements
    private ArrayList<SongAlbum> songAlbums;
    private SongAlbum songAlbum;

    private MediaPlayer mMediaPlayer;
    // Runnable thread to handle Song Time and settinn text based on seekbar progress
    private final Runnable updateSeekTime = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer != null) {
                int maxTime = mMediaPlayer.getDuration();
                int currentTime = mMediaPlayer.getCurrentPosition();

                int seekProgress = getProgressPercentage(currentTime, maxTime);
                seekBar.setProgress(seekProgress);

                endTime.setText(msTime(maxTime));
                startTime.setText(msTime(currentTime));

                handler.postDelayed(this, 50);

            }
        }
    };
    private int cur_song_pos;
    // To check controls for the play activity
    private boolean isShuffled;
    private boolean isRepeated;
    private boolean isLiked;

    /**
     * handle Seekbar time, converting the seek bar time for a song being played
     *
     * @param milliSeconds to pass time in Millseconds(default)
     * @return to take the time to seekbar
     */
    private static String msTime(long milliSeconds) {
        String finalTimerString = "";
        String secondsString;

        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) (milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000;

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString += minutes + ":" + secondsString;

        return finalTimerString;
    }

    /**
     * Getting progress of a Media into seconds
     *
     * @param currentDuration to get Current Seekbar time
     * @param totalDuration   to get total time of song
     * @return time in percentage to update the seekbar
     */
    private static int getProgressPercentage(long currentDuration, long totalDuration) {
        double percentage;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        percentage = ((double) (currentSeconds) / totalSeconds) * 100;

        return (int) percentage;
    }

    /**
     * Handle progress time of seekbar
     *
     * @param progress      to get progress of a song
     * @param totalDuration to get the Maximum duration of the Song
     * @return current duration of song in milliseconds to persist progress
     */
    private static int progressTime(int progress, int totalDuration) {
        int currentDuration;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        return currentDuration * 1000;
    }

    /**
     * Handle Getting and setting duration for a song
     *
     * @param context to get current context of the Mediastate
     * @param songId  to get thw Current song ID
     * @return time duration estimated for the Song
     */
    private static String getDuration(Context context, int songId) {
        final Uri mediaPath = Uri.parse("android.resource://" + context.getPackageName() + "/" + songId);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, mediaPath);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        duration = msTime(Integer.parseInt(duration));
        return duration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        /*
         *  Fetching the VIewIds of all View elements
         */
        songTitle = findViewById(R.id.song_text);
        songArtist = findViewById(R.id.song_artist);
        startTime = findViewById(R.id.time_start);
        endTime = findViewById(R.id.time_end);
        songImg = findViewById(R.id.song_art);
        circleImg = findViewById(R.id.album_img);
        playpause = findViewById(R.id.play_pause);
        ImageView prev = findViewById(R.id.prev);
        fav = findViewById(R.id.favorite);
        ImageView next = findViewById(R.id.next);
        repeat = findViewById(R.id.repeat);
        shuffle = findViewById(R.id.shuffle);
        ImageView songList = findViewById(R.id.song_list);
        seekBar = findViewById(R.id.seekbar);

        /*
         *  Setup Action Bar with Up arrow navigation
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Getting intent from Library Activity
        Intent intent = getIntent();

        // Getting the song to the MediaState
        cur_song_pos = intent.getIntExtra(ITEM_POS, 0);

        //getting the name mapped with corresponding Song
        String artistName = intent.getStringExtra(ARTIST_NAME);

        String albumName = intent.getStringExtra(ALBUM_NAME);
        //fetching all songs or song by a particular artist/album based on intent results
        if (artistName != null && artistName.length() > 0) {
            songAlbums = SongUtils.filterSongByArtist(this, artistName);
        } else if (albumName != null && albumName.length() > 0) {
            songAlbums = SongUtils.filterSongByAlbum(this, albumName);
        } else {
            songAlbums = SongUtils.getSongLibrary(this);
        }

        // Getting view content for Now Playing Activity
        songAlbum = songAlbums.get(cur_song_pos);
        fav.setImageResource(R.drawable.ic_favorite);
        startTime.setText(R.string.initial_time);
        songTitle.setText(songAlbum.getSongName());
        songArtist.setText(songAlbum.getSongArtist());
        songImg.setImageResource(songAlbum.getAlbumArtId());
        circleImg.setImageResource(songAlbum.getAlbumArtId());
        endTime.setText(getDuration(this, songAlbum.getmSongId()));

        // Set button click listeners for player controls
        playpause.setOnClickListener(this);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        repeat.setOnClickListener(this);
        shuffle.setOnClickListener(this);
        fav.setOnClickListener(this);
        songList.setOnClickListener(this);

        // Setting seek touch event listeners
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                removeCallbacks();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                removeCallbacks();

                int maxTime = mMediaPlayer.getDuration();
                int currentTime = progressTime(seekBar.getProgress(), maxTime);

                mMediaPlayer.seekTo(currentTime);
                updateSeekBar();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            /*
             *  Switching logic to handle Player control click events
             */
            case R.id.play_pause:

                // Handle play/pause, update vector drawables
                if (mMediaPlayer != null) {
                    /*
                     *  if mMediaplayer is already initialized, verify if the instance was playing
                     *  or not and update the state accordingly
                     */
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        playpause.setImageResource(R.drawable.ic_play);
                    } else {
                        mMediaPlayer.start();
                        playpause.setImageResource(R.drawable.ic_pause);
                    }
                } else {
                    // Handle initializing the mMediaPlayer if there is no valid instance
                    playSong(cur_song_pos);
                }
                break;

            case R.id.next:
                // Handle playing next song
                playNext();
                break;

            case R.id.prev:
                // Handle playing previous song
                playPrev();
                break;

            case R.id.repeat:
                // Handle if repeat mode ON
                if (isRepeated) {
                    isRepeated = false;
                    updateVectorTint(repeat, android.R.color.holo_red_dark);
                } else {
                    isRepeated = true;
                    isShuffled = false;
                    updateVectorTint(repeat, android.R.color.black);
                    updateVectorTint(shuffle, android.R.color.black);
                }
                break;

            case R.id.shuffle:
                // Handle if shuffle mode ON
                if (isShuffled) {
                    isShuffled = false;
                    updateVectorTint(shuffle, android.R.color.holo_red_dark);
                } else {
                    isShuffled = true;
                    isRepeated = false;
                    updateVectorTint(shuffle, android.R.color.black);
                    updateVectorTint(repeat, android.R.color.black);
                }
                break;

            case R.id.favorite:
                // Add/remove song from favorites
                if (isLiked) {
                    isLiked = false;
                    fav.setImageResource(R.drawable.ic_favorite);
                } else {
                    isLiked = true;
                    fav.setImageResource(R.drawable.ic_favorite_black);
                }
                break;

            case R.id.song_list:
                // Intent to show all songs from NowPlaying screen
                Intent intentLibrary = new Intent(NowPlayingActivity.this, LibraryActivity.class);
                finish();
                startActivity(intentLibrary);
                break;
        }
    }

    /**
     * Plays the song at the specified position
     *
     * @param position to get the song from the list
     */
    private void playSong(final int position) {

        // Get the song object
        songAlbum = songAlbums.get(position);
        updateUI();
        // Reset the player
        mMediaPlayer.reset();

        // Create the player instance
        mMediaPlayer = MediaPlayer.create(this, songAlbum.getmSongId());

        // Replay the song
        mMediaPlayer.start();
        playpause.setImageResource(R.drawable.ic_pause);

        // Update the seekbar progress
        updateSeekBar();

        // Handle onCompletion state for song completed
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mMediaPlayer) {
                // Replay the song if repeat is enabled
                if (isRepeated) {
                    playSong(position);
                } else {
                    // Play a song at a random index if shuffle is enabled
                    if (isShuffled) {
                        cur_song_pos = new Random().nextInt((songAlbums.size() - 1) + 1);
                        playSong(cur_song_pos);
                    } else {
                        // If no shuffle is enabled, play the next song from the list
                        playNext();
                    }
                }
            }
        });
    }

    /**
     * Method to update tint for a vector
     *
     * @param button button that uses the vector drawable as a background
     * @param color  tint to be used for the button's vector drawable
     */
    private void updateVectorTint(ImageView button, int color) {
        DrawableCompat.setTint(button.getDrawable(), ContextCompat.getColor(this, color));
    }

    private void playPrev() {
        // Handle playing previous song
        if (cur_song_pos - 1 >= 0) {
            cur_song_pos--;
        } else {
            // Display toast message when there are no more previous songs available
            noSongToast();
            toast = Toast.makeText(NowPlayingActivity.this, R.string.first_song, Toast.LENGTH_SHORT);
            toast.show();
        }
        // Play previous song if available
        playSong(cur_song_pos);
    }

    /**
     * Plays the next song from the all songs list
     */
    private void playNext() {
        if ((cur_song_pos + 1) < songAlbums.size()) {
            cur_song_pos++;
        } else {
            // Play from the first song if the list has reached to the end
            cur_song_pos = 0;
        }
        songAlbum = songAlbums.get(cur_song_pos);
        playSong(cur_song_pos);
    }

    /**
     * Updates song details - the album image, song name, artist name when next/previous song is played
     */
    private void updateUI() {
        circleImg.setImageResource(songAlbum.getAlbumArtId());
        songImg.setImageResource(songAlbum.getAlbumArtId());
        fav.setImageResource(R.drawable.ic_favorite);
        songTitle.setText(songAlbum.getSongName());
        songArtist.setText(songAlbum.getSongArtist());
    }

    /**
     * Cancel any outstanding toast to dismiss it
     */
    private void noSongToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * Update seekbar via a handler
     */
    private void updateSeekBar() {
        handler.postDelayed(updateSeekTime, 100);
    }

    /**
     * Unregister handler when not required
     */
    private void removeCallbacks() {
        handler.removeCallbacks(updateSeekTime);
    }

    /**
     * Release media player resources when not required
     */
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    /**
     * Handle back navigation on action bar
     *
     * @return boolean flag indication whether navigation was successfully handled
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /**
     * Handles media player state with change in activity state to Pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        removeCallbacks();

        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            playpause.setImageResource(R.drawable.ic_play);
        }
    }

    /**
     * Handles media player state with change in activity state to Resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, songAlbum.getmSongId());
        seekBar.setMax(100);
        seekBar.setProgress(0);
        updateSeekBar();
    }

    /**
     * Handles media player state with change in activity state to Stop
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}