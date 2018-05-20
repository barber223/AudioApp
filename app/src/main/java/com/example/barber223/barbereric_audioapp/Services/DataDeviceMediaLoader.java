package com.example.barber223.barbereric_audioapp.Services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class DataDeviceMediaLoader extends Service {



    DataDeviceMediaLoader(){

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DDMLoaderBinder();
    }
    class DDMLoaderBinder extends Binder{
        DataDeviceMediaLoader getService() {
            return new DataDeviceMediaLoader();
        }
    }

    public void pullCloudCategories(){
        //This will pull from the actual database which will contain a list of all the possiable categories

    }
    public void pullAllTracksWithinCategory(String _category){
        //This will dig into the actual db and pull from a category that has a list of music objects
        //These objects can be pulled down
            //objects need to match with our local datamodel for ease of pulling

        //when a track is about to play there needs to be a selection to allow the cloud base to know the path
            // of the audio track location within the cloud storage bucket
    }

}
