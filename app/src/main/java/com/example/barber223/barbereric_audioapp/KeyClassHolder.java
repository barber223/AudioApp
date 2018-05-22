package com.example.barber223.barbereric_audioapp;

public class KeyClassHolder {
    public static final String Key_Record = "Record";
    public static final String Key_Cloud = "Cloud";
    public static final String Key_File = "File";


    //File Processes.java (FileViewActivity.java)
    public static final String action_file = "android.intent.action.file_view";

    public static final String action_cloud = "android.intent.action.cloud_view";
    public static final String action_cloud_tracks = "android.intent.action";
    public static final String action_record = "android.intent.action.record_view";

    private static final String KEY_holder = "";

    //Process keys for the communicateCB async task//
    public static final String key_action_pullCats = "Categories";
    public static final String key_action_pullTrackList = "pulling_track_list_from_selected.category";


    public static final int key_request_cloud_categories = 0x101;
    public static final int getKey_request_cloud_tracks = 0x10101;
}
