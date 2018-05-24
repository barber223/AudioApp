package com.example.barber223.barbereric_audioapp.Interfaces;

import com.example.barber223.barbereric_audioapp.AudioFileObject;

import java.util.ArrayList;

public interface cloudInformationInterface {

    void returnOfCategories(ArrayList<String> _categories);
    String getCurrentCategoryToDisplay();
    void setCurrentCategoryToSearch(String _activeCategory);

    void passNewAudioObject (AudioFileObject _obj);

    ArrayList<AudioFileObject> passAudioObjectList();

    //This will be used for the media player service class and the fragment which holds the values of AudioObject
    AudioFileObject getAudioFileToPlay();

    String getActiveDataBaseAction();
}
