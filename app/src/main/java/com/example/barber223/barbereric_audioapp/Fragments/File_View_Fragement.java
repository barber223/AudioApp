package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.barber223.barbereric_audioapp.Interfaces.InformationInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;

import java.io.File;

public class File_View_Fragement extends ListFragment {

    //need a listener to see if were pulling from the cloud or the file system
    private SelectionFragmentInterface mListener;

    //listener for the file information interface
    private InformationInterface mInfoListener;

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
            switch (mListener.getActiveProcess()){
               case KeyClassHolder.action_cloud:
                   //This will need to pull the data from the cloud bucket
                break;

               case KeyClassHolder.action_file:
                   //this will need to pull the data from the users devices file system
                break;

                case KeyClassHolder.action_record:
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
                        adapter.addAll(categoryNames);
                        this.setListAdapter(adapter);
                    }
                    break;

            }
        }
    }
}
