package com.example.barber223.barbereric_audioapp.Interfaces;

import com.example.barber223.barbereric_audioapp.AudioFileObject;

import java.util.ArrayList;

public interface cloudInformationInterface {
    void returnOfCategories(ArrayList<String> _categories);
    String getCurrentCategoryToDisplay();
    void setCurrentCategoryToSearch(String _activeCategory);

    void passNewAudioObject (AudioFileObject _obj);


    ArrayList<AudioFileObject> passAudioObjectList();
}
