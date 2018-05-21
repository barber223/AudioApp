package com.example.barber223.barbereric_audioapp.Interfaces;

import java.util.ArrayList;

public interface cloudInformationInterface {
    void returnOfCategories(ArrayList<String> _categories);
    String getCurrentCategoryToDisplay();
    void setCurrentCategoryToSearch(String _activeCategory);

}
