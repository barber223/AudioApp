//Eric Barber
//DVP6-1806
//RecordMainActivity.java

package com.example.barber223.barbereric_audioapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barber223.barbereric_audioapp.Fragments.Activity_Selection_Fragment_Top;
import com.example.barber223.barbereric_audioapp.Fragments.File_View_Fragement;
import com.example.barber223.barbereric_audioapp.Fragments.RecordInformationFragment;
import com.example.barber223.barbereric_audioapp.Interfaces.InformationInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;
import com.example.barber223.barbereric_audioapp.Services.MediaRecorderServiceClass;

import java.io.File;

public class RecordMainActivity extends AppCompatActivity implements SelectionFragmentInterface,
        RecordInformationFragment.PlayBackCommandListener, InformationInterface, View.OnClickListener{

    String newCategoryText = "";

    private String activeDeviceProcess = KeyClassHolder.action_record;

    private File[] categoryFolder;

    String catOfRecord = "";
    String activeTitle = "";

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

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void save() {

    }

    @Override
    public void newCategory() {
        //pop up a alert asking for the new category name
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Category");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!input.getText().toString().equals("")){
                    addNewCategoryToSystem(input.getText().toString());
                    getFragmentManager().beginTransaction()
                            .replace(R.id.files_fragment_frame, File_View_Fragement.newInstance()).commit();
                }
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void record(String _title) {
        activeTitle = _title;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_record_alert_layout, null);

        builder.setView(view);

        TextView title = view.findViewById(R.id.title_of_track_text);
        title.setText(_title);

        Button _button = view.findViewById(R.id.cancel_button);
        _button.setOnClickListener(this);

        _button = view.findViewById(R.id.continue_to_record_button);
        _button.setOnClickListener(this);

        //need to set up the spinner within the view

        //This will be used within the pulling of the categories that is also within the file_ViewFragment
        final String[] catNames = getListOfCategoryNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1
                );

        adapter.addAll(catNames);

        final Spinner spinner = view.findViewById(R.id.category_spinner);

        spinner.setAdapter(adapter);
        spinner.setTag("1");

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag().toString().equals("1")){
                    //Need to check the position based off of the position of the spinner
                    for (String name: catNames){
                        if (name.equals(spinner.getItemAtPosition(position).toString())){
                            //they have the same
                           catOfRecord = (String)spinner.getItemAtPosition(position);
                        }
                    }
                }
            }
        });


    }


    @Override
    public void seekBarWasAltered(int _newPosition) {

    }

    private void addNewCategoryToSystem(String _categoryName){

        if (!_categoryName.equals("")){
            //Add the category to the file system

            File pStorage = getExternalFilesDir(null);
            File categoriesFolder = new File(pStorage, "AudioFiles");

            categoriesFolder.mkdir();

            File[] files = categoriesFolder.listFiles();

            //need to check to make sure there is not already a file with the category
            boolean exists = false;
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.getAbsolutePath().endsWith(_categoryName)) {
                        exists = true;
                    }
                }
            }
            if (exists){
                //alert the user category already is in system
                Toast.makeText(this, "There is already a category with the name: " +
                        _categoryName, Toast.LENGTH_LONG).show();
            } else{
                File newFile = new File(categoriesFolder, _categoryName);
                try {
                    newFile.createNewFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public File[] getcategoryList() {
        File pStorage = getExternalFilesDir(null);
        File categoriesFolder = new File(pStorage, "AudioFiles");

        categoryFolder = categoriesFolder.listFiles();

        return categoryFolder;
    }

    @Override
    public void forceReload() {
        getFragmentManager().beginTransaction()
                .replace(R.id.files_fragment_frame, File_View_Fragement.newInstance()).commit();
    }


    @Override
    public void onClick(View v) {
        //This can get called from the record alert
        if (v.getId() == R.id.continue_to_record_button){
            if (!catOfRecord.equals("")){
                MediaRecorderServiceClass mRSC = new MediaRecorderServiceClass();
                File pStorage = getExternalFilesDir(null);
                File catFolder = new File(pStorage, "AudioFiles");

                try{
                    File directory = new File(catFolder, catOfRecord);
                    // We are now within the file
                    directory.mkdir();

                    File newTrack = new File(directory, activeTitle);

                    mRSC.Record(newTrack.getAbsolutePath());


                }catch (Exception e){
                    e.printStackTrace();
                }

                mRSC.Record("");

            }else{
                Log.i("RecordMain: ", "Dont do anything there is no category");
            }
        }
        else{
            Log.i("RecordMain: ", "Dont do anything there is no category");
        }
    }



    private String[] getListOfCategoryNames(){
        //this will need to pull the data from the users devices file system

        //pull the categories from the device
        File[] files = getcategoryList();

        if (files != null){
            String[] categoryNames = new String[files.length];
            //need to populate the list with a list adapter cycling through the different categories
            //This will allow me to only pull the categories for displaying
            int lastindexofChar = files[0].getAbsolutePath().lastIndexOf('/');
            String filePath = "";

            for (int i = 0; i < files.length; i ++){
                //All of the categories should have the same last index due to the directories of when they get saved
                //need to get the name of the category
                char[] path = files[i].getAbsolutePath().toCharArray();
                char[] catName = new char[(path.length - lastindexofChar) - 1];
                int indexOfCatName = 0;
                for (int l = lastindexofChar + 1; l < path.length; l ++){
                    catName[indexOfCatName] = path[l];
                    indexOfCatName ++;
                }
                String name = String.valueOf(catName);
                categoryNames[i] = name;
            }
            return categoryNames;
        }
        return  null;
    }
}
