//Eric Barber
//DVP6-1806
//Activity_Selection_Fragment_Top.java

package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;

public class Activity_Selection_Fragment_Top extends Fragment implements View.OnClickListener{
    //This fragment will be used for selecting between files, record, or cloud menu

    private SelectionFragmentInterface mListener;


    public static Activity_Selection_Fragment_Top newInstance() {

        Bundle args = new Bundle();

        Activity_Selection_Fragment_Top fragment = new Activity_Selection_Fragment_Top();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_selection_fragment_layout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectionFragmentInterface){
            mListener = (SelectionFragmentInterface) context;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set the navagational btns
        Button btn = view.findViewById(R.id.record_btn);
            btn.setOnClickListener(this);

            btn = view.findViewById(R.id.cloud_btn);
            btn.setOnClickListener(this);

            btn = view.findViewById(R.id.files_btn);
            btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Communicate with the launching activity of which file was active

        switch (v.getId()){
            case R.id.record_btn:
                mListener.actionOfViewS(KeyClassHolder.action_record);
                break;

            case R.id.cloud_btn:
                mListener.actionOfViewS(KeyClassHolder.action_cloud);
                break;

            case R.id.files_btn:
                mListener.actionOfViewS(KeyClassHolder.action_file);
                break;

                default:
                    Log.e("SelectionFragment: ", "There was unknown buttonPressed");
                    break;
        }

    }
}
