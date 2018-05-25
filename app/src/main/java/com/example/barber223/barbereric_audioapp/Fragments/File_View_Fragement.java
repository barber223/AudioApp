package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.barber223.barbereric_audioapp.AudioFileObject;
import com.example.barber223.barbereric_audioapp.Interfaces.InformationInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.cloudInformationInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;
import com.example.barber223.barbereric_audioapp.baseAdapter;

import java.io.File;
import java.util.ArrayList;

public class File_View_Fragement extends ListFragment implements AdapterView.OnItemClickListener, InformationInterface {

    //need a listener to see if were pulling from the cloud or the file system
    private SelectionFragmentInterface mListener;

    //listener for the file information interface
    private InformationInterface mInfoListener;

    //i guess i need another interfacve Listener
    private cloudInformationInterface mCloudAdaptionInterface;
    ArrayList<AudioFileObject> mTrack;

    ArrayList<String> categories;
    String[] tracks;

    public static File_View_Fragement newInstance() {

        Bundle args = new Bundle();
        File_View_Fragement fragment = new File_View_Fragement();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.file_view_fragment_layout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectionFragmentInterface){
            mListener = (SelectionFragmentInterface) context;
        }
        if (context instanceof InformationInterface){
            mInfoListener = (InformationInterface) context;
        }
        if (context instanceof cloudInformationInterface){
            mCloudAdaptionInterface = (cloudInformationInterface) context;
        }
    }
    //need a way for 3 different views within for data

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //perform method
        pulldata();
    }
    private void pulldata(){
        //start the intent service
        if (mListener != null){

            String g = mListener.getActiveProcess();
            switch (mListener.getActiveProcess()){

                //TODO:// Fix issue with key for the active process.

                    //Cloud is the only one where the activity selection fragment top is different from the file
                        // case list
                case KeyClassHolder.action_cloud:

                   //This will need to pull the data from the cloud bucket
                   //need to obtain the list of categories from the cloud activity if it is not null :)
                     categories = mListener.categories();
                    if (categories != null){

                        String[] cats = new String[categories.size()];
                        for (int i = 0 ; i < categories.size(); i ++){
                            cats[i] = categories.get(i);
                        }
                        ArrayAdapter<String> stringadapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_list_item_1);
                        stringadapter.addAll(cats);
                        this.setListAdapter(stringadapter);
                    }
                    //This will only get selected went there is the items from the cloud becauser only in the cloud
                    //view users will be able to select the category
                break;

                    //This is a part of action cloud
                case KeyClassHolder.key_action_pullTrackList:
                    //Load the same as cat but with cats and a little bit of separate functionality;
                    //TODO finish this to allow the tracks to be displayed
                    mTrack = mCloudAdaptionInterface.passAudioObjectList();

                    if (mTrack != null) {
                        tracks = new String[mTrack.size()];
                        String track = "";
                        String author = "";
                        String notes = "";

                        for(int i = 0;i < tracks.length; i ++){

                            track = mTrack.get(i).getName();
                            author = mTrack.get(i).getAuthor();
                            notes = mTrack.get(i).getNotes();
                            tracks[i] = "Title:\n  " + track + "\nAuthor:\n  " + author
                            + "\nNotes:\n  " + notes;
                            //add to list for adapter
                        }
                        baseAdapter adp = new baseAdapter(tracks, getContext(),KeyClassHolder.action_cloud);
                       this.setListAdapter(adp);
                    }
                    break;

               case KeyClassHolder.action_file:
                   //this will need to pull the data from the users devices file system
                   String[] categoryes = getListOfCategoryNames();
                   if (categoryes != null){ baseAdapter adapter = new baseAdapter(categoryes, getContext(), KeyClassHolder.action_file);
                       this.setListAdapter(adapter);
                   }
                   //This will need the functionality for playing and viewing all of the files within a selected category

                break;

                case KeyClassHolder.action_record:
                    String[] catNames = getListOfCategoryNames();
                    if (catNames != null){
                        baseAdapter adapter = new baseAdapter(catNames, getContext(), KeyClassHolder.action_record);
                        this.setListAdapter(adapter);
                    }
                    break;

                    default:
                        Log.i("Need to fix this Issue within cloud pulling?ActiveProcess:\n" , mListener.getActiveProcess()
                                + "\nActiveDBPro: " + mCloudAdaptionInterface.getActiveDataBaseAction() );

                        if (mCloudAdaptionInterface.getActiveDataBaseAction().equals(KeyClassHolder.action_cloud_tracks)){
                            categories = mListener.categories();
                            if (categories != null){

                                String[] cats = new String[categories.size()];
                                for (int i = 0 ; i < categories.size(); i ++){
                                    cats[i] = categories.get(i);
                                }
                                ArrayAdapter<String> stringadapter = new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_list_item_1);
                                stringadapter.addAll(cats);
                                this.setListAdapter(stringadapter);
                            }
                        }
                        break;
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(android.R.id.list);
        if (listView != null){
            listView.setOnItemClickListener(this);
        }
    }
    private String[] getListOfCategoryNames(){
        //this will need to pull the data from the users devices file system

        //pull the categories from the device
        File[] files = mInfoListener.getcategoryList();

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("ListItemSelected", "I wonder how without a setonItemClick listener");
        mCloudAdaptionInterface.setCurrentCategoryToSearch(categories.get(position));
    }

    @Override
    public File[] getcategoryList() {//Not used
        return null;
    }

    @Override
    public void forceReload() {//NOt used

    }

    @Override
    public void passPosition(int _position) {
        //used from base adapter
        AudioFileObject activeobj = mTrack.get(_position);
        if (activeobj != null){
            //need this to populate into the Record information views;

        }
    }
}
