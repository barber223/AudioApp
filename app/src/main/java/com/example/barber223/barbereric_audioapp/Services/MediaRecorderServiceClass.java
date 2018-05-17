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

public class MediaRecorderServiceClass extends Service implements AudioRouting,MediaRecorder.OnInfoListener {

    //need the states of the mediaRecorder
    private static final int STATE_INITIAL = 0;
    private static final int STATE_ERROR = -1;
    private static final int STATE_RELEASED = 1;
    private static final int STATE_INITIALIZED = 2;
    private static final int STATE_RECORDING = 3;
    private static final int STATE_DATASOURCECONFIG = 4;
    private static final int STATE_PREPARED = 5;
    private static final int STATE_STOPED_WIDDATA = 6;

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

        if (mState == STATE_INITIALIZED) {
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //I dont want people to record for longer than 3 minutes.
            mMediaRecorder.setMaxDuration(180000);
            //need to obtain the audio file location from the selected category within the fileView Fragment

        }
    }

    private void Record(String pathForSave) {
        if (mState == STATE_INITIALIZED) {
            //for now will use a template and change when the user selects save:)
            mMediaRecorder.setOutputFile(pathForSave);

            mState = STATE_DATASOURCECONFIG;
        }

        if (mState == STATE_DATASOURCECONFIG){
            try {
                mMediaRecorder.prepare();
                mState = STATE_PREPARED;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mState == STATE_PREPARED){
            mMediaRecorder.start();
            mState = STATE_RECORDING;
        }

    }

    private void stopRecording(){
        if (mState == STATE_RECORDING){
            mMediaRecorder.stop();
            //There is data available
            mState = STATE_STOPED_WIDDATA;
        }
    }

    private void saveFile(){
        if (mState == STATE_RECORDING || mState == STATE_STOPED_WIDDATA){
            if (mState == STATE_RECORDING){
                mMediaRecorder.stop();
                mState = STATE_STOPED_WIDDATA;
            }
            //need to start the saving process
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

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
            //need to notify the user and save the file/ reset the systemProcesses

            mr.release();
            mState = STATE_INITIAL;
        }
    }
}
