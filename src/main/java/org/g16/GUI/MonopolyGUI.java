package org.g16.GUI;
import gui_fields.*;
import org.g16.MonopolyJR.*;
import gui_main.GUI;

import java.awt.*;
import java.awt.Color;

public class MonopolyGUI {

    private GUI gui;
    public void initGUI(Field[] startingFields){

        GUI_Field[] guiFields = new GUI_Field[startingFields.length];

        for(int i = 0; i < startingFields.length; i++){

            switch (startingFields[i].getClass().getSimpleName()){
                default:
                    //If i == 0, it's the start field
                    if(i == 0){
                        guiFields[i] = new GUI_Start();

                    } else {
                        guiFields[i] = new GUI_Refuge();
                    }
                    break;
                case "ChanceField":
                    guiFields[i] = new GUI_Chance();
                    break;
                case "PropertyField":
                    guiFields[i] = new GUI_Street();
                    //COlor
                    //Price/subtitle
                    break;
            }
            //guiFields[i].setTitle(startingFields[i].getName());
        }

        gui = new GUI(guiFields);
        StartGameScreen();
    }

    private void StartGameScreen(){


    }

}
