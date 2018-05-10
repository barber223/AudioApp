package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.ListFragment;
import android.os.Bundle;

import com.example.barber223.barbereric_audioapp.Background_Activities.FileViewActivity;

public class File_View_Fragement extends ListFragment {

    public static File_View_Fragement newInstance() {

        Bundle args = new Bundle();

        File_View_Fragement fragment = new File_View_Fragement();
        fragment.setArguments(args);
        return fragment;
    }




}
