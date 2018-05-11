//Eric Barber
//DVP6-1806
//RecordMainActivity.java

package com.example.barber223.barbereric_audioapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.barber223.barbereric_audioapp.Background_Activities.FileViewActivity;
import com.example.barber223.barbereric_audioapp.Fragments.Activity_Selection_Fragment_Top;
import com.example.barber223.barbereric_audioapp.Fragments.File_View_Fragement;
import com.example.barber223.barbereric_audioapp.Fragments.RecordInformationFragment;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;

public class RecordMainActivity extends AppCompatActivity implements SelectionFragmentInterface{

    private String[] mPermissions = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};

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

        /*
        for (int i = 0; i < mPermissions.length; i ++){
            if (ContextCompat.checkSelfPermission(this, mPermissions[i]) != PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{mPermissions[i]}, 0x0010);
                }
            }else{
                finish();
            }
        }
        */
    }

    @Override
    public void actionOfViewS(String _action) {

        //perform the actions for the users desired interactions
        //needs to start the background activity
        Intent startFileProceses = new Intent(this, FileViewActivity.class);
        startFileProceses.setAction(_action);
        //may switch to results if need be
        //startActivity(startFileProceses);


    }
}
