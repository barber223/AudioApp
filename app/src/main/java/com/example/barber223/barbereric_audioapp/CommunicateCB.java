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

import com.example.barber223.barbereric_audioapp.Interfaces.cloudInformationInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private ArrayList<String> categories;

    private cloudInformationInterface mListener;



    //This method is used to pull audio file base TODO:// work through this methos and subregate
    public CommunicateCB (Context _context){
        mContext = _context;

        //need to set up the interface listener for communication
        if (mContext instanceof cloudInformationInterface){
            mListener = (cloudInformationInterface) _context;
        }

        /*
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

        */

    }

    //doesnt do anything
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
        if (strings != null) {
            DatabaseReference mDBRef;

            switch (strings[0]) {
                case KeyClassHolder.key_action_pullCats:
                    //this will be used to pull all of the categories within the database

                    mDBRef = FirebaseDatabase.getInstance().getReference();
                    final DatabaseReference categorys = mDBRef.child("Audio");
                    mDBRef.orderByChild("Audio").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            Log.i("SnapShot:", "\n " + dataSnapshot.toString());
                            Iterable<DataSnapshot> files = dataSnapshot.getChildren();
                            categories = new ArrayList<String>();
                            for (DataSnapshot shot: files){
                                Log.i("looping: Shot" , "\n " + shot.toString());
                                //pull all of the keys and add them to a list to populate the categories within the list
                                String key = shot.getKey();
                                categories.add(key);
                            }
                            onPostExecute("Fuck");
                        }
                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }
                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                    break;

                case KeyClassHolder.key_action_pullTrackList:
                        // need to dig through the database childs to pull the different tracks within the file system
                    //Tricky part will be having Track ad the key and track0 as track but will be able to pull through the
                    // and pull the different tracks, do you think the user would like to see the title within the track
                    // or can there be multiple tracks with the same not being unique?
                    String desiredCategory = mListener.getCurrentCategoryToDisplay();
                    if (!desiredCategory.equals("")){
                        //There is a category for me to dig to

//TODO: Need tp finish this or fix it not sure yet
                        mDBRef = FirebaseDatabase.getInstance().getReference();
                       // final DatabaseReference categorys = mDBRef.child("Audio");


                        mDBRef.orderByChild("Audio/"+desiredCategory).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                Log.i("Well Shit:", "There is somthing here@!");
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }


                    break;
                default:
                    Log.i("CommunicateCB://","There is no task to perform");
                    Toast.makeText(mContext, "There is an issue please contact developer", Toast.LENGTH_LONG).show();
                    break;
            }
        }




        return "Hekki";

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (categories != null && mListener != null){
            mListener.returnOfCategories(categories);
        }
    }
}
