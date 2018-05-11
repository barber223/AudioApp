package com.example.barber223.barbereric_audioapp.Background_Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.barber223.barbereric_audioapp.KeyClassHolder;

public class FileViewActivity extends Activity {

    //This activity is for downloading and configuring all of the files within the system.

        //this needs a way to allow communication back to the active activity

    private final String action_file = "android.intent.action.file_view";

    private final String action_cloud = "android.intent.action.cloud_view";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent startingIntent = getIntent();

        if (startingIntent.getAction() != null) {

            switch (startingIntent.getAction()) {
                case action_cloud:
                    //start the service to pull the files from the cloud to push into the category view

                   // Intent startFile_View = new Intent(this, FileViewActivity.class);
                   // startFile_View.setAction(KeyClassHolder.action_cloud);


                    break;

                case action_file:
                    //start the ability to pull the files from users directory
                        //Orginize them

                    break;

                default:
                    Log.e("FileViewActivity:", "There is an unknownAction");
                    break;
            }

        }
    }





}
