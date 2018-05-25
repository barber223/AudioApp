package com.example.barber223.barbereric_audioapp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.barber223.barbereric_audioapp.Activities.CloudViewActivity;
import com.example.barber223.barbereric_audioapp.AudioFileObject;
import com.example.barber223.barbereric_audioapp.CommunicateCB;
import com.example.barber223.barbereric_audioapp.Interfaces.SelectionFragmentInterface;
import com.example.barber223.barbereric_audioapp.Interfaces.cloudInformationInterface;
import com.example.barber223.barbereric_audioapp.KeyClassHolder;
import com.example.barber223.barbereric_audioapp.R;

import java.util.ArrayList;

public class RecordInformationFragment extends Fragment implements View.OnClickListener {

    private cloudInformationInterface mCloudListener;

    private SelectionFragmentInterface mSelectionListener;


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

        void record(String _title);

        void seekBarWasAltered(int _newPosition);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null){
            ImageButton iB = view.findViewById(R.id.record_btn);

        }
    }

    private PlayBackCommandListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayBackCommandListener){
            mListener = (PlayBackCommandListener) context;
        }
        if (context instanceof cloudInformationInterface){
            mCloudListener = (cloudInformationInterface)context;
        }
        if (context instanceof SelectionFragmentInterface){
            mSelectionListener = (SelectionFragmentInterface) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        //need acess if this will be used as a recorder or as a player

        String activeProcess = mSelectionListener.getActiveProcess();

        switch (mSelectionListener.getActiveProcess()){
            case KeyClassHolder.key_action_pullCats:

                disableButtonsForCloud(view);

                break;

            case KeyClassHolder.action_file:
                activateAllViews(view);
                break;


            case KeyClassHolder.action_record:
                activateAllViews(view);
                break;
        }

    }

    private void disableButtonsForCloud(View view){
        if (view != null){
            //need to remove specific buttons
            Log.i("CloudMenu_", "Going to be removing unneccassary buttons");

            view.findViewById(R.id.play_btn).setOnClickListener(this);
            view.findViewById(R.id.play_btn).setTag("p");
            view.findViewById(R.id.save_btn).setEnabled(false);

            view.findViewById(R.id.skip_forward_btn).setEnabled(false);
            view.findViewById(R.id.track_notes).setEnabled(false);
            view.findViewById(R.id.track_information_edit_text).setEnabled(false);
            view.findViewById(R.id.record_btn).setEnabled(false);
            //view.findViewById(R.id.add_categoryButton).setEnabled(false);

        }

    }

    private void activateAllViews(View view){
        if (view != null){
            view.findViewById(R.id.play_btn).setOnClickListener(this);
            view.findViewById(R.id.play_btn).setTag("p");

            view.findViewById(R.id.save_btn).setOnClickListener(this);
            view.findViewById(R.id.skip_forward_btn).setOnClickListener(this);

            if (mSelectionListener.getActiveProcess().equals(KeyClassHolder.action_record)){
               ImageButton ib = view.findViewById(R.id.skip_forward_btn);
               //need to remove this button from the view within recording
                ib.setImageBitmap(null);
                ib.setBackgroundColor(0);
            }

            view.findViewById(R.id.track_notes).setOnClickListener(this);
            view.findViewById(R.id.track_information_edit_text).setOnClickListener(this);
            view.findViewById(R.id.record_btn).setOnClickListener(this);
            //view.findViewById(R.id.add_categoryButton).setOnClickListener(this);
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
                ImageButton p = v.findViewById(R.id.play_btn);

                if (p.getTag().equals("p")){
                    p.setTag("pa");
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_pause_black_24dp);
                    p.setImageBitmap(bmp);
                }else{
                    p.setTag("p");
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_play_arrow_black_24dp);
                    p.setImageBitmap(bmp);
                }

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
        }
    }
}
