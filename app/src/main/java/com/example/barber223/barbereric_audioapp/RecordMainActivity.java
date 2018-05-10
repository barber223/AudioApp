//Eric Barber
//DVP6-1806
//RecordMainActivity.java

package com.example.barber223.barbereric_audioapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.barber223.barbereric_audioapp.Background_Activities.FileViewActivity;
import com.example.barber223.barbereric_audioapp.Fragments.Activity_Selection_Fragment_Top;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;

public class RecordMainActivity extends AppCompatActivity implements SelectionFragmentInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //need to inflate the fragments for the views
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_selection_fragment_frame, Activity_Selection_Fragment_Top.newInstance()).commit();
        //have to decide if the files are going to be held within a service or an activity




    }

    @Override
    public void actionOfViewS(String _action) {

        //perform the actions for the users desired interactions
        //needs to start the background activity
        Intent startFileProceses = new Intent(this, FileViewActivity.class);
        startFileProceses.setAction(_action);
        //may switch to results if need be
        startActivity(startFileProceses);


    }
}
