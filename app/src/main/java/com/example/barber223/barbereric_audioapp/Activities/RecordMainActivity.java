//Eric Barber
//DVP6-1806
//RecordMainActivity.java

package com.example.barber223.barbereric_audioapp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.barber223.barbereric_audioapp.Fragments.Activity_Selection_Fragment_Top;
import com.example.barber223.barbereric_audioapp.Fragments.File_View_Fragement;
import com.example.barber223.barbereric_audioapp.Fragments.RecordInformationFragment;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;

public class RecordMainActivity extends AppCompatActivity implements SelectionFragmentInterface{

    private String activeDeviceProcess = KeyClassHolder.action_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                //need to inflate the fragments for the views
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_selection_fragment_frame, Activity_Selection_Fragment_Top.newInstance())
                .replace(R.id.files_fragment_frame, File_View_Fragement.newInstance())
                .replace(R.id.information_fragment_frame, RecordInformationFragment.newInstance())
                .commit();
        //have to decide if the files are going to be held within a service or an activity
        String[] mPermissions = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};

        if (!hasPermisisons(this, mPermissions)){
            ActivityCompat.requestPermissions(this, mPermissions, 0x0101);
        }
    }

    private boolean hasPermisisons(Context _context, String... permissions){
        if (_context != null && permissions != null){
            for (String permission: permissions){
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionOfViewS(String _action) {
        //set the ui elements place holder based off the action
        activeDeviceProcess = _action;

        //if the user selects a different menu option then there needs to be a way to load in the
        //new activity

        switch (_action){
            case KeyClassHolder.action_cloud:
                Intent newActivity = new Intent(this, CloudViewActivity.class);
                newActivity.setAction(KeyClassHolder.action_cloud);
                startActivity(newActivity);
                break;

            case KeyClassHolder.action_file:
                Intent activity = new Intent(this, FileViewActivity.class);
                activity.setAction(KeyClassHolder.action_file);
                startActivity(activity);
                break;
        }



    }

    @Override
    public String getActiveProcess() {
        return activeDeviceProcess;
    }




}