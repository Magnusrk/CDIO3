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
    private final java.awt.Color[] playerCarColors = new java.awt.Color[]{
            Color.orange,
            Color.green,
            Color.blue,
            Color.black
    };
    private GUI_Player[] guiPlayers = new GUI_Player[MAX_PLAYERS];
    private int playerCount = 0;

    /**
     * Initialize monopoly GUI
     * @param startingFields
     * The method will draw the playing board and return a GUI object,
     * which can be used to interact with the GUI.
     * @return GUI object.
     */
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

    /**
     * Display a chance card
     * @param text
     * The method will display a chance card with the given text
     *
     */
    public void DrawChanceCard(String text){
        gui.displayChanceCard(text);
    }

    /**
     * Prompt the players to select a game language
     * The method will make the user select a desired language.
     * The method will return a string with the selected languagepack name
     * @return String object.
     */
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

    /**
     * Update a player's car's position.
     * @param playerID
     * playerID is the index of the player object in the player-array
     * @param position
     * Position is the index of the field in the field array.
     * The method will remove the car from the previous field and draw it on the next field
     */
    public void DrawPlayerPosition(int playerID, int position){
        GUI_Player selectedPlayer = guiPlayers[playerID];
        selectedPlayer.getCar().setPosition(gui.getFields()[position]);
    }

    /**
     * Draw dies
     * @param faceValue1
     * @param faceValue2
     * This will draw the dies at a random position with random rotation
     * with the given face values.
     */
    public void DrawDie(int faceValue1, int faceValue2){
        gui.setDice(faceValue1, faceValue2);
    }

    /**
     * Prompt the player to throw the dies
     * @param playerID
     * This will show a message telling the player with the relevant ID
     * to throw
     * with the given face values.
     */
    public void PromptThrowDice(int playerID){
        gui.showMessage(guiPlayers[playerID].getName() + " throw the dice!");
    }

    /**
     * Set a players balance
     * @param playerID
     * @param balance
     * Sets player with the corrosponding ID's balance
     */
    public void SetPlayerBalance(int playerID, int balance){
        guiPlayers[playerID].setBalance(balance);
    }

    /**
     * Starts the process of creating players. This will ask for name,
     * and call the GameController. When a player is created, the game
     * will ask it the user wants to create another player, unless
     * the amount of players is already at it's maximum
     */
    public void SetupPlayers(){
        GUI_Player newPlayer = AddPlayerPrompt();
        guiPlayers[playerCount] = newPlayer;
        DrawPlayerPosition(playerCount,0);
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

    /**
     * Prompts the user to enter a player name.
     */
    private GUI_Player AddPlayerPrompt(){
        String player = gui.getUserString("Enter player name. ");
        GUI_Player guiPlayer = new GUI_Player(player);
        guiPlayer.getCar().setPrimaryColor(playerCarColors[playerCount]);
        gui.addPlayer(guiPlayer);
        gui.showMessage("Added player " + player);
        return guiPlayer;
    }

}
