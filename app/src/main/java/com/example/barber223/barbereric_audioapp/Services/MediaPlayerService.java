package com.example.barber223.barbereric_audioapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class MediaPlayerService extends Service {


    //Need to hold the values of the state of the media player
    private static final int STATE_IDLE = 0;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_PREPARING = 2;
    public static final int STATE_PREPARED = 3;
    public static final int STATE_STARTED = 4;
    public static final int STATE_PAUSED = 5;
    public static final int STATE_STOPPED = 6;
    public static final int STATE_PLAYBACK_COMPLETED = 7;
    public static final int STATE_END = 8;

    //need a saved instance of the mediaPlayer
    private MediaPlayer mMediaPlayer;
    //hold state
    private int mState;

    public MediaPlayerService(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mState = STATE_IDLE;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
