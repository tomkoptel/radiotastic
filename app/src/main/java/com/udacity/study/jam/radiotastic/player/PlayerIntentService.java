/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.player;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

import timber.log.Timber;

public class PlayerIntentService extends Service
        implements AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener {

    private static final String LOG_TAG = PlayerIntentService.class.getSimpleName();

    private static final String ACTION_PLAY = "com.udacity.study.jam.radiotastic.player.action.PLAY";
    private static final String ACTION_STOP = "com.udacity.study.jam.radiotastic.player.action.STOP";

    private static final String EXTRA_STREAM_URL = "STREAM_URL";
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;
    private String mStreamUrl;

    /**
     * Starts this service to perform action Play with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see android.app.IntentService
     */
    public static void startActionPlay(Context context, String streamUrl) {
        Intent intent = new Intent(context, PlayerIntentService.class);
        intent.setAction(ACTION_PLAY);
        intent.putExtra(EXTRA_STREAM_URL, streamUrl);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see android.app.IntentService
     */
    public static void startActionStop(Context context) {
        Intent intent = new Intent(context, PlayerIntentService.class);
        intent.setAction(ACTION_STOP);
        context.startService(intent);
    }

    public PlayerIntentService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.tag(LOG_TAG);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PLAY.equals(action)) {
                final String streamUrl = intent.getStringExtra(EXTRA_STREAM_URL);
                handleActionPlay(streamUrl);
            } else if (ACTION_STOP.equals(action)) {
                handleActionStop();
            }
        }
        return START_STICKY;
    }

    private void handleActionPlay(String streamUrl) {
        Timber.i("User explicitly started play back");
        mStreamUrl = streamUrl;
        if (requestFocus()) {
            Timber.i("Audio focus was granted.");
            if (mMediaPlayer == null) {
                initMediaPlayer();
            }
        } else {
            Timber.e("Failed to request audio focus.");
            // Schedule Alarm event. Maybe exponential backoff
        }
    }

    private void handleActionStop() {
        Timber.i("User explicitly stopped play back");
        if (abandonFocus()) {
            Timber.i("Audio focus abandoned.");
            releasePlayer();
        } else {
            Timber.e("OMG! We can`t abandon audio focus. Something terrible happened!");
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                Timber.i("Resuming playback");
                if (mMediaPlayer == null) {
                    initMediaPlayer();
                } else if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();
                }
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                Timber.i("Focus lost. Releasing MediaPlayer");
                releasePlayer();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                Timber.i("Focus lost for short time. Pausing play back.");
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                Timber.i("Focus lost for short time, but lets lower play back.");
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.setVolume(0.1f, 0.1f);
                }
                break;
        }
    }

    private void releasePlayer() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void initMediaPlayer() {
        Timber.i("Initializing player");
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);

        try {
            Timber.i("Setting data source: " + mStreamUrl);
            mMediaPlayer.setDataSource(mStreamUrl);

            Timber.i("Preparing player");
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Timber.e(e, "Failed setup data source");
        }
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        Timber.i("Starting playback");
        player.start();
    }

    @Override
    public boolean onError(MediaPlayer player, int what, int extra) {
        Timber.e("Something bad happened with MediaPlayer \n What: " + what + " \n Extra: " + extra);
        Timber.i("Resetting media player");
        player.reset();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.i("Service is about to die.");
        releasePlayer();
    }

    private boolean requestFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                mAudioManager.requestAudioFocus(this,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
    }

    private boolean abandonFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                mAudioManager.abandonAudioFocus(this);
    }
}
