//Eric Barber
//DVP6-1805
//CloudViewActivity.java

package com.example.barber223.barbereric_audioapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.barber223.barbereric_audioapp.AudioFileObject;
import com.example.barber223.barbereric_audioapp.CommunicateCB;
import com.example.barber223.barbereric_audioapp.Fragments.Activity_Selection_Fragment_Top;
import com.example.barber223.barbereric_audioapp.Fragments.File_View_Fragement;
import com.example.barber223.barbereric_audioapp.Fragments.RecordInformationFragment;
import com.example.barber223.barbereric_audioapp.Interfaces.InformationInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.cloudInformationInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;

public class CloudViewActivity extends AppCompatActivity implements SelectionFragmentInterface,
         cloudInformationInterface, InformationInterface
{

    private String activeDeviceProcess = KeyClassHolder.action_cloud;
    private String activeDBProcess = KeyClassHolder.action_cloud_tracks;

    private ArrayList<String> categories;
    private ArrayList<AudioFileObject> mTracks;
    private AudioFileObject mTrackToPlay;

    private CommunicateCB cb;

    private String desiredCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_view);

        getFragmentManager().beginTransaction()
                .replace(R.id.activity_selection_fragment_frame, Activity_Selection_Fragment_Top.newInstance())
                .replace(R.id.files_fragment_frame, File_View_Fragement.newInstance())
                .replace(R.id.information_fragment_frame, RecordInformationFragment.newInstance())
                .commit();

        //TODO need to remember to reset this value if the user would like to go back to catergories menu
        mTracks = new ArrayList<>();

        cb = new CommunicateCB(this);
        pullCategoies();

    }

    @Override
    public void actionOfViewS(String _action) {
        activeDeviceProcess = _action;

        //need to load the new activity
        switch (_action) {
            case KeyClassHolder.action_file:
                Intent newActivity = new Intent(this, FileViewActivity.class);
                newActivity.setAction(KeyClassHolder.action_file);
                startActivity(newActivity);
                this.finish();
                break;

            case KeyClassHolder.action_record:
                newActivity = new Intent(this, RecordMainActivity.class);
                newActivity.setAction(KeyClassHolder.action_record);
                startActivity(newActivity);
                this.finish();
                break;
        }
    }

    @Override
    public String getActiveProcess() {
        return activeDeviceProcess;
    }



    @Override
    public ArrayList<String> categories() {
        return categories;
    }


    private void pullCategoies(){
        cb.execute(KeyClassHolder.key_action_pullCats);
        activeDeviceProcess = KeyClassHolder.key_action_pullCats;
    }

    @Override
    public void returnOfCategories(ArrayList<String> _categories) {

        categories = _categories;
        getFragmentManager().beginTransaction()
               .replace(R.id.files_fragment_frame, File_View_Fragement.newInstance()).commit();

    }

    @Override
    public String getCurrentCategoryToDisplay() {
        if (desiredCategory != null){
            return desiredCategory;
        }
        return "";
    }

    @Override
    public void setCurrentCategoryToSearch(String _activeCategory) {
        //Need to execute the service to allow the comunication to traspire
        //need to create a new instance a task can only be excuded once
        desiredCategory = _activeCategory;
        cb = new CommunicateCB(this);
        cb.execute(KeyClassHolder.key_action_pullTrackList);

    }

    @Override
    public void passNewAudioObject(AudioFileObject _obj) {

        mTracks.add(_obj);
        //need to set the active process
        activeDeviceProcess = KeyClassHolder.key_action_pullTrackList;

        //need to reset the fragment
        getFragmentManager().beginTransaction()
                .replace(R.id.files_fragment_frame, File_View_Fragement.newInstance()).commit();

    }

    @Override
    public ArrayList<AudioFileObject> passAudioObjectList() {
        return mTracks;
    }

    @Override
    public AudioFileObject getAudioFileToPlay() {
        return mTrackToPlay;
    }

    @Override
    public String getActiveDataBaseAction() {
        return activeDBProcess;
    }


    @Override
    public File[] getcategoryList() {
        return new File[0];
    }

    @Override
    public void forceReload() {

    }

    @Override
    public void passPosition(int _position) {
        //song to play
        mTrackToPlay = mTracks.get(_position);
        CommunicateCB cb = new CommunicateCB(this);
        cb.execute(KeyClassHolder.key_action_pull_audioTracks);
    }
}


