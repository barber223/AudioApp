//Eric Barber
//DVP6-1805
//FileViewActivity.java

package com.example.barber223.barbereric_audioapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barber223.barbereric_audioapp.Fragments.Activity_Selection_Fragment_Top;
import com.example.barber223.barbereric_audioapp.Fragments.File_View_Fragement;
import com.example.barber223.barbereric_audioapp.Fragments.RecordInformationFragment;
import com.example.barber223.barbereric_audioapp.Interfaces.InformationInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;

import java.io.File;
import java.util.ArrayList;

public class FileViewActivity extends AppCompatActivity implements SelectionFragmentInterface,
        RecordInformationFragment.PlayBackCommandListener, InformationInterface {

    private String activeDeviceProcess = KeyClassHolder.action_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);

        getFragmentManager().beginTransaction()
                .replace(R.id.activity_selection_fragment_frame, Activity_Selection_Fragment_Top.newInstance())
                .replace(R.id.files_fragment_frame, File_View_Fragement.newInstance())
                .replace(R.id.information_fragment_frame, RecordInformationFragment.newInstance())
                .commit();
    }

    @Override
    public void actionOfViewS(String _action) {
        activeDeviceProcess = _action;

        //need to load the new activity
        switch (_action){
            case KeyClassHolder.action_cloud:
                Intent newActivity = new Intent(this, CloudViewActivity.class);
                newActivity.setAction(KeyClassHolder.action_cloud);
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
        //This allows navigation between all of the activities and processes within the application
    }

    @Override
    public String getActiveProcess() {
        return activeDeviceProcess;
    }

    @Override
    public ArrayList<String> categories() {
        return null;
    }

    @Override
    public void setCurrentCategoryToSearch(String _activeCategory) {

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
    public void record(String _title) {

    }


    @Override
    public void seekBarWasAltered(int _newPosition) {

    }

    @Override
    public File[] getcategoryList() {
        File pStorage = getExternalFilesDir(null);
        File categoriesFolder = new File(pStorage, "AudioFiles");
        return categoriesFolder.listFiles();
    }

    @Override
    public void forceReload() {
        getFragmentManager().beginTransaction()
                .replace(R.id.files_fragment_frame, File_View_Fragement.newInstance()).commit();
    }
    @Override
    public void passPosition(int _position) {
        //Will need to pull the track information

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_category_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        return true;
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
}
