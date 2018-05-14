package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;

public class File_View_Fragement extends ListFragment {

    //need a listener to see if were pulling from the cloud or the file system
    private SelectionFragmentInterface mListener;

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


                    break;
            }
        }

    }
}
