package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barber223.barbereric_audioapp.Background_Activities.FileViewActivity;
import com.example.barber223.barbereric_audioapp.R;

public class File_View_Fragement extends ListFragment {

    public static File_View_Fragement newInstance() {

        Bundle args = new Bundle();

        File_View_Fragement fragment = new File_View_Fragement();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_selection_fragment_layout, container, false);
    }

    //need a way for 3 diffrent views within for data

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //perform method

    }
    private void pulldata(){
        //start the intent service

    }
}
