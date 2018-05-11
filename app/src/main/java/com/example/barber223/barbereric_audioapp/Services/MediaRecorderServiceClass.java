package com.example.barber223.barbereric_audioapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MediaRecorderServiceClass extends Service {

    //need the states of the mediaRecorder
    private static final int STATE_INITIAL = 0;
    private static final int STATE_ERROR = -1;
    private static final int STATE_RELEASED = 1;
    private static final int STATE_INITIALIZED = 2;
    private static final int STATE_RECORDING = 3;
    private static final int STATE_DATASOURCECONFIG = 4;
    private static final int STATE_PREPARED = 5;

    //INSTANCE OF THE RECORDER
    private MediaRecorder mMediaRecorder;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaRecorder = new MediaRecorder();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
