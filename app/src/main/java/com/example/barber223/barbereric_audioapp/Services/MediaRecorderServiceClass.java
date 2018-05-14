package com.example.barber223.barbereric_audioapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioRouting;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

public class MediaRecorderServiceClass extends Service implements AudioRouting{

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

    private int mState = -1;

    @Override
    public void onCreate() {
        super.onCreate();



        mMediaRecorder = new MediaRecorder();
        mState = STATE_INITIAL;

        if (mState == STATE_INITIAL) {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mState = STATE_INITIALIZED;
        }

        if (mState == STATE_INITIALIZED){
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            //I dont want people to record for longer than 3 minutes.
            mMediaRecorder.setMaxDuration(180000);

            //need to obtain the audio file location from the selected category within the fileView Fragment

            //for now will use a template and change when the user selects save:)
            mMediaRecorder.setOutputFile("recordings/temp_r_record");

            mState = STATE_DATASOURCECONFIG;
        }




        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean setPreferredDevice(AudioDeviceInfo deviceInfo) {
        return false;
    }

    @Override
    public AudioDeviceInfo getPreferredDevice() {
        return null;
    }

    @Override
    public AudioDeviceInfo getRoutedDevice() {
        return null;
    }

    @Override
    public void addOnRoutingChangedListener(OnRoutingChangedListener listener, Handler handler) {

    }

    @Override
    public void removeOnRoutingChangedListener(OnRoutingChangedListener listener) {

    }
}
