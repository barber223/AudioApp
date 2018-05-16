package com.example.barber223.barbereric_audioapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.barber223.barbereric_audioapp.Fragments.File_View_Fragement;

import java.io.File;
import java.util.ArrayList;

public class baseAdapter extends BaseAdapter implements View.OnClickListener, View.OnDragListener{

    private Context mContext;
    private String[] mStrings;

    final int base_Id = 0x01010;

    public baseAdapter(String[] _strings, Context _context){
        mContext = _context;
        mStrings = _strings;
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

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.base_adapter_view, parent, false);
        }

        TextView text = convertView.findViewById(R.id.text_view);
        text.setText(mStrings[position]);

        //convertView.setOnClickListener(this);
        convertView.setOnDragListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public boolean onDrag(View v, DragEvent event) {


        TextView textView = v.findViewById(R.id.text_view);

        String cat = textView.getText().toString();

        File pStorage = mContext.getExternalFilesDir(null);
        final File categoriesFolder = new File(pStorage, "AudioFiles");

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle("Are you sure?");

        builder.setMessage("If you delete this category all of files within this category will be deleted");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  File fileToDelete = new File(categoriesFolder);
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
        return true;
    }
}
