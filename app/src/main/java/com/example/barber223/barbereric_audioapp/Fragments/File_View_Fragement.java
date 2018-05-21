package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.barber223.barbereric_audioapp.Interfaces.InformationInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;
import com.example.barber223.barbereric_audioapp.baseAdapter;

import java.io.File;
import java.util.ArrayList;

public class File_View_Fragement extends ListFragment implements AdapterView.OnItemClickListener {

    //need a listener to see if were pulling from the cloud or the file system
    private SelectionFragmentInterface mListener;

    //listener for the file information interface
    private InformationInterface mInfoListener;

    ArrayList<String> categories;

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
                   //need to obatin the list of categories from the cloud activity if it is not null :)
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
                    //Thgis will only get selectded whent there is the items from the cloud becauser only in the cloud
                    //view users will be able to select the category



                break;

               case KeyClassHolder.action_file:
                   //this will need to pull the data from the users devices file system
                   String[] categoryes = getListOfCategoryNames();
                   if (categoryes != null){
                       baseAdapter adapter = new baseAdapter(categoryes, getContext(), KeyClassHolder.action_file);
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




    }
}
