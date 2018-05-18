package com.example.barber223.barbereric_audioapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CommunicateCB extends AsyncTask<String, String, String>{

    private Context mContext;

    private StorageReference mStorageRef;



    public CommunicateCB (Context _context){
        mContext = _context;

        mStorageRef = FirebaseStorage.getInstance().getReference();
        File localFile = null;
        try {
            localFile = File.createTempFile("test", ".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageReference storage = mStorageRef.child("Music").child("Beats").child("Bill-Bailey-Wont-You-Please-Come-Home_FULL_WSR1802001.mp3");


        final File finalLocalFile = localFile;
        StorageTask<FileDownloadTask.TaskSnapshot> taskSnapshotStorageTask = storage.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Boolean b = finalLocalFile.canRead();

                if (b) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(finalLocalFile.getAbsolutePath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void digInFile(File file){
        if (file != null){
            File[] cats = file.listFiles();
            if (cats.length > 0){

                for (File mFile:cats){
                    Log.i("FilesPulled: ", " FilePath: " + mFile.getAbsolutePath() + "n");
                }

            }else{

                Log.i("jf", "This didn't work");
            }
        }
    }



    @Override
    protected String doInBackground(String... strings) {

        try{

            AssetManager am = mContext.getAssets();

            String StorageUrl = "https://console.cloud.google.com/storage/browser/audio_app_a";

            InputStream is = new URL(StorageUrl).openStream();

            //InputStream is = am.open(url);

            //InputStream is = FileUtils

            File file = new File("android/com/temp/storage/");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.copy(
                  is,
                  file.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }
            else{
                Toast.makeText(mContext, "Get a new Phone", Toast.LENGTH_LONG).show();
            }

            is.close();


        }catch (Exception E){
            E.printStackTrace();
        }


        return "Hekki";

    }


}
