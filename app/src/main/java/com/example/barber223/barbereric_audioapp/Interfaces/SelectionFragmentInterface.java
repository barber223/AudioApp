//Eric Barber
//DVP6-1806
//SelectionFragmentInterface.java

package com.example.barber223.barbereric_audioapp.Interfaces;

import java.util.ArrayList;

public  interface SelectionFragmentInterface {

    void actionOfViewS(String _action);

    String getActiveProcess();

    ArrayList<String> categories();

     interface Cloud {
        void setCurrentCategoryToSearch(String _activeCategory);
    }

    //Need

}


