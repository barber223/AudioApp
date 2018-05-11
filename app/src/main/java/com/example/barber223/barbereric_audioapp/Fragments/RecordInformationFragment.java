package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barber223.barbereric_audioapp.R;

public class RecordInformationFragment extends Fragment {

    public static RecordInformationFragment newInstance() {

        Bundle args = new Bundle();

        RecordInformationFragment fragment = new RecordInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.record_file_information,container, false);
    }
}
