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
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.cloudInformationInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CloudViewActivity extends AppCompatActivity implements SelectionFragmentInterface,
        SelectionFragmentInterface.Cloud, cloudInformationInterface
{

    private String activeDeviceProcess = KeyClassHolder.action_cloud;

    private ArrayList<String> categories;

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

        cb = new CommunicateCB(this);
        pullCategoies();

        communicate();

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
    }


    //Try to make contact
    private void communicate() {
        // CommunicateCB cb = new CommunicateCB(this);

        //cb.execute("ohYeah!!!");



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

    }
}


