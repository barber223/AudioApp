package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barber223.barbereric_audioapp.R;

public class RecordInformationFragment extends Fragment implements View.OnClickListener {

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

    //this will allow the communication with the activity
    //I need a way to obtain the state of the media player. To see if it should be set up as a media Recorder
    public interface PlayBackCommandListener{
        void play();

        void pause();

        void stop();

        void save();

        void newCategory();

        void record(String _title);

        void seekBarWasAltered(int _newPosition);

    }

    private PlayBackCommandListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayBackCommandListener){
            mListener = (PlayBackCommandListener) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view != null){
            view.findViewById(R.id.play_btn).setOnClickListener(this);
            view.findViewById(R.id.pause_btn).setOnClickListener(this);
            view.findViewById(R.id.save_btn).setOnClickListener(this);
            view.findViewById(R.id.skip_forward_btn).setOnClickListener(this);
            view.findViewById(R.id.track_notes).setOnClickListener(this);
            view.findViewById(R.id.track_information_edit_text).setOnClickListener(this);
            view.findViewById(R.id.record_btn).setOnClickListener(this);
            view.findViewById(R.id.add_categoryButton).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener == null){
            return;
        }
        switch (v.getId()){
            case R.id.play_btn:
                mListener.play();
                break;

            case R.id.pause_btn:
                mListener.pause();
                break;

            case R.id.save_btn:
                //need to make sure that if were recording that there is values within the title and notes, and somthing has been recorded
                mListener.save();
                break;

            case R.id.skip_forward_btn:

                break;

            case R.id.track_notes:
                break;

            case R.id.track_information_edit_text:
                break;

            case R.id.record_btn:
                //need to set up a way to specify the file location before the recording process has begun
                EditText eT = null;
                try {
                     eT = getView().findViewById(R.id.track_information_edit_text);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //This will only allow the user to even think about starting to record without setting
                // the values within the edit text
                if (eT != null && !eT.getText().toString().trim().equals("") ){
                    mListener.record(eT.getText().toString());
                }else{
                    Toast.makeText(getContext(), "There please add Title", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.add_categoryButton:
                //this needs to pop an alert for adding a new category
                mListener.newCategory();
                break;
        }
    }
}
