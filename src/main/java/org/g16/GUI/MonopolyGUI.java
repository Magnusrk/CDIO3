package org.g16.GUI;
import gui_fields.*;
import org.g16.MonopolyJR.*;
import gui_main.GUI;

import java.awt.*;
import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MonopolyGUI {

    private GUI gui;

    private final int MAX_PLAYERS = 4;

    private GUI_Player[] guiPlayers = new GUI_Player[MAX_PLAYERS];
    private int playerCount = 0;
    public GUI initGUI(Field[] startingFields){

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
        return gui;
    }




    private void StartGameScreen(){

    }

    public String ChooseLanguage(){
        String chosenLanguage = gui.getUserSelection(
                "Choose game language",
                "Dansk",
                        "English",
                        "Test"
        );

        return switch (chosenLanguage) {
            default -> "da";
            case "Engelsk" -> "en";
            case "Thai" -> "th";
            case "JP" -> "jp";
        };
    }

    public void SetupPlayers(){
        GUI_Player newPlayer = AddPlayerPrompt();
        //Tell GameController to add player
        //GameController.AddPlayer()...
        playerCount++;
        if(playerCount < MAX_PLAYERS){
            String addAnotherPlayerRequest = gui.getUserButtonPressed(
                    "Do you want to add another player?",
                    "Yes", "No"
            );
            if(Objects.equals(addAnotherPlayerRequest, "Yes")){
                SetupPlayers();
            }
        }
    }

    private GUI_Player AddPlayerPrompt(){
        String player = gui.getUserString("Enter player name. ");
        GUI_Player guiPlayer = new GUI_Player(player);
        gui.addPlayer(guiPlayer);
        gui.showMessage("Added player " + player);
        return guiPlayer;
    }

}
