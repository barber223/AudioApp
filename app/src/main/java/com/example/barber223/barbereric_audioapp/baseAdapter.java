package com.example.barber223.barbereric_audioapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.barber223.barbereric_audioapp.Interfaces.InformationInterface;

import java.io.File;

import static com.example.barber223.barbereric_audioapp.R.color.selectedCategory;

public class baseAdapter extends BaseAdapter implements View.OnClickListener, View.OnLongClickListener{

    private Context mContext;
    private String[] mStrings;

    private InformationInterface mListener;


    private String mActiveProcess;

    private int currentHighlightedPosition = -1;

    final int base_Id = 0x01010;

    public baseAdapter(String[] _strings, Context _context, String _activeProcess){
        mContext = _context;
        mStrings = _strings;

        if (_context instanceof InformationInterface){
            mListener = (InformationInterface) _context;
        }

        mActiveProcess = _activeProcess;

    }

    @Override
    public int getCount() {
        if (mStrings != null){
            return mStrings.length;
        }
        return 0;
    }

    @Override
    public String getItem(int position) {
        if (mStrings != null && position >= 0 && position < mStrings.length){
            return mStrings[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mStrings != null && position >= 0 && position < mStrings.length){
            return base_Id + position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.base_adapter_view, parent, false);
                holder = new ViewHolder();
                holder.textView = convertView.findViewById(R.id.text_view);
                holder.deleteButton = convertView.findViewById(R.id.delete_btn);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            setClickListener(holder.deleteButton);

            if (mActiveProcess.equals(KeyClassHolder.action_record)){
                holder.textView.setText(mStrings[position]);
                holder.deleteButton.setTag(position);
            }
            else if (mActiveProcess.equals(KeyClassHolder.action_file)){
                holder.textView.setText(mStrings[position]);
                holder.deleteButton.setTag(position);

            }else if (mActiveProcess.equals(KeyClassHolder.action_cloud)){
                holder.textView.setText(mStrings[position]);
                holder.deleteButton.setTag( position);
                Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_play_arrow_black_24dp);
                holder.deleteButton.setImageBitmap(bmp);
            }


        return convertView;
    }

    private void setClickListener(View view){
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        int position = -1;

        switch (mActiveProcess){
            case KeyClassHolder.action_cloud:
                position = (Integer) v.getTag();
                //need to pull from the cloud storage
                if (position != -1 && mListener != null){
                    mListener.passPosition(position);
                }
                break;

            case KeyClassHolder.action_file:
                //for now will contain usual operatorions of removing a cat, soon
                    //need adding of another button to allow the ability to play the entire list of tracks from
                    //directory
                position = (Integer) v.getTag();
                deleteFileFromSystem(position);
                break;

            case KeyClassHolder.action_cloud_tracks:
                //might need to change to allow the ability to play specified Track
               position = (Integer) v.getTag();
               mListener.passPosition(position);
                break;

            case KeyClassHolder.action_record:
                //Record load normal Removal of categories
                position = (Integer) v.getTag();
                deleteFileFromSystem(position);
                break;
        }



    }
    //For deletion of a category at a selected position
    private void deleteFileFromSystem( int _position){
        final String catName = mStrings[_position];

        // now that I have the value of which object was selected
        // I need the ability to select the delete button option with the use of the alert dialog builder

        File pStorage = mContext.getExternalFilesDir(null);
        final File categoriesFolder = new File(pStorage, "AudioFiles");

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle("Are you sure?");
        builder.setMessage("If you delete this category all of files within this category will be deleted"
                + "\n Category: " + mStrings[_position]
        );

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File fileToDelete = new File(categoriesFolder, catName);
                //delete the file from the system
                fileToDelete.delete();
                //Need to force reload all of the information within the file system being displayed
                mListener.forceReload();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onLongClick(View v) {
        if (mActiveProcess.equals(KeyClassHolder.action_file)){
            //This will allow the user to be able to open the file list within the category

            // I don't want the user to be able to view all of the audio tracks when they are within the
                //the audioRecording menu
            final int position = (Integer) v.getTag();

            final String catName = mStrings[position];

            // now I need to dig into the file system
            File pStorage = mContext.getExternalFilesDir(null);
            final File categoriesFolder = new File(pStorage, "AudioFiles");

            File categoryFolder = new File(categoriesFolder, catName);

            if (categoriesFolder.listFiles() != null){
                //there is audio tracks within this category

            }else{
                // load there is no data

            }


        }else {
            //do nothing
        }


        return true;
    }



    public class ViewHolder{
     TextView textView;
     ImageButton deleteButton;
    }
}
