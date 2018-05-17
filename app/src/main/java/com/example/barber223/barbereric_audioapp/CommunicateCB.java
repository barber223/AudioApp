package com.example.barber223.barbereric_audioapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class CommunicateCB extends AsyncTask<String, String, String>{

    private Context mContext;

    private StorageReference mStorageRef;



    public CommunicateCB (Context _context){
        mContext = _context;

        mStorageRef = FirebaseStorage.getInstance().getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://audio_app_a");

        mStorageRef = storage.getReference();

        String bucketString = mStorageRef.getBucket();

        String path = mStorageRef.getPath();


       String[] fileList = new File (path).list();

        if (fileList != null){
            for (String thing: fileList){
                Log.i("Communicate Thing: ", thing);
            }
        }


        Log.i("CommunicatCB: ", bucketString);


        //Upload a file
        /*
        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
StorageReference riversRef = storageRef.child("images/rivers.jpg");

riversRef.putFile(file)
    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            // Get a URL to the uploaded content
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
        }
    })
    .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle unsuccessful uploads
            // ...
        }
    });
         */

        //Download a file
        /*

         */

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
