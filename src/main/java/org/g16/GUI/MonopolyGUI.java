package org.g16.GUI;
import gui_fields.*;
import org.g16.MonopolyJR.*;
import gui_main.GUI;

import java.awt.Color;
import java.lang.reflect.Type;
import java.util.Objects;

import static gui_fields.GUI_Car.Pattern.ZEBRA;
import static gui_fields.GUI_Car.Type.UFO;

public class MonopolyGUI {

    private GUI gui;
    private final int MAX_PLAYERS = 4;
    private final int startingBalance = 20;
    private final java.awt.Color[] playerCarColors = new java.awt.Color[]{
            Color.orange,
            Color.green,
            Color.blue,
            Color.black
    };
    private GUI_Player[] guiPlayers;
    private int[] guiAges;
    private String[] unavailablePlayerNames;
    private int playerCount = 0;

    private GameController gameController;

    private Field[] startingFields;


    /**
     * Initialize monopoly GUI
     * @param startingFields
     * The method will draw the playing board and return a GUI object,
     * which can be used to interact with the GUI.
     * @return GUI object.
     */
    public GUI initGUI(Field[] startingFields, GameController gameController){
        this.gameController = gameController;
        this.startingFields = startingFields;
        GUI_Field[] guiFields = new GUI_Field[startingFields.length];
        for(int i = 0; i < startingFields.length; i++){
            switch (startingFields[i].getClass().getSimpleName()){
                default:
                    //If i == 0, it's the start field
                    if(i == 0){
                        guiFields[i] = new GUI_Start();
                        guiFields[i].setSubText(Language.GetString("startsub"));

                    } else {
                        guiFields[i] = new GUI_Refuge("src/main/resources/parkering.jpg",Language.GetString("parkering"),Language.GetString("parking"),"",Color.lightGray,Color.black);
                    }
                    break;
                case  "Jail":
                        guiFields[i] = new GUI_Jail("src/main/resources/test.png", Language.GetString("prison"), Language.GetString("prison"), "", Color.lightGray, Color.black);
                    break;
                case "GoToJailField":
                        guiFields[i] = new GUI_Jail("src/main/resources/GoTo.jpg", Language.GetString("prison"), Language.GetString("prison"), "", Color.lightGray, Color.black);
                        break;
                case "ChanceField":
                    guiFields[i] = new GUI_Chance();
                    guiFields[i].setSubText(Language.GetString("tryluck"));
                    break;
                case "PropertyField":
                    guiFields[i] = new GUI_Street();
                    guiFields[i].setSubText(Language.GetString("noowner"));
                    PropertyField property = (PropertyField)startingFields[i];
                    guiFields[i].setDescription( Language.GetString("price") + " " + String.valueOf(property.getPrice()));
                    guiFields[i].setBackGroundColor(ConvertColor(property.getColor()));
                    //COlor
                    //Price/subtitle
                    break;
            }
            guiFields[i].setTitle(Language.GetString(startingFields[i].getName()));
        }

        gui = new GUI(guiFields);
        return gui;
    }

    public void UpdateFields() {
        GUI_Field[] currentFields = gui.getFields();
        for (int i = 0; i < startingFields.length; i++) {
            switch (startingFields[i].getClass().getSimpleName()) {
                default:
                    //If i == 0, it's the start field
                    if (i == 0) {
                        gui.getFields()[i].setSubText(Language.GetString("startsub"));
                    }
                    break;
                case "ChanceField":
                    gui.getFields()[i].setSubText(Language.GetString("tryluck"));
                    break;
                case "PropertyField":

                    gui.getFields()[i].setSubText(Language.GetString("noowner"));
                    PropertyField property = (PropertyField) startingFields[i];
                    gui.getFields()[i].setDescription(Language.GetString("price") + " " + String.valueOf(property.getPrice()));
                    break;
            }
            gui.getFields()[i].setTitle(Language.GetString(startingFields[i].getName()));

        }
    }

    private java.awt.Color ConvertColor(org.g16.MonopolyJR.Color col){
        if(org.g16.MonopolyJR.Color.DarkBlue == col){
         return Color.BLUE;
        }
        if(org.g16.MonopolyJR.Color.Blue == col){
            return Color.CYAN;
        }
        if(org.g16.MonopolyJR.Color.Brown == col){
            return Color.getHSBColor(0.063f,0.69f,0.65f);
        }
        if(org.g16.MonopolyJR.Color.Red == col){
            return Color.RED;
        }
        if(org.g16.MonopolyJR.Color.Pink == col){
            return Color.pink;
        }
        if(org.g16.MonopolyJR.Color.Green == col){
            return Color.green;
        }
        if(org.g16.MonopolyJR.Color.Orange == col){
            return Color.orange;
        }
        if(org.g16.MonopolyJR.Color.Yellow == col){
            return Color.YELLOW;
        }
        return Color.MAGENTA;
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
    public String chooseLanguage(){
        String chosenLanguage = gui.getUserSelection(
                Language.GetString("chooseLanguage"),
                "Dansk",
                        "English",
                        "Thai",
                        "Japanese"
        );

        return switch (chosenLanguage) {
            default -> "da";
            case "English" -> "en";
            case "Thai" -> "th";
            case "Japanese" -> "jp";
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
     *
     * This will draw the die at a random position with random rotation
     * with the given face values.
     */
    public void DrawDie(int faceValue1){
        gui.setDie(faceValue1);
    }

    /**
     * Prompt the player to throw the dies
     * @param playerID
     * This will show a message telling the player with the relevant ID
     * to throw
     * with the given face values.
     */
    public void PromptThrowDice(int playerID){
        gui.showMessage(guiPlayers[playerID].getName() + " "+ Language.GetString("throwDice"));
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
        int desiredPlayers = gui.getUserInteger( Language.GetString("howManyPlayers")+ " (2-4)");
        while(desiredPlayers < 2 || desiredPlayers > 4){
            gui.showMessage(Language.GetString("tooManyOrFew"));
            desiredPlayers = gui.getUserInteger( Language.GetString("howManyPlayers")+ " (2-4)");
        }

        guiPlayers = new GUI_Player[desiredPlayers];
        guiAges = new int[desiredPlayers];
        unavailablePlayerNames = new String[desiredPlayers];


        while (playerCount < desiredPlayers){
            GUI_Player newPlayer = AddPlayerPrompt();
            guiPlayers[playerCount] = newPlayer;
            unavailablePlayerNames[playerCount] = newPlayer.getName();
            guiAges[playerCount] = gui.getUserInteger(Language.GetString("enterAge"));
            DrawPlayerPosition(playerCount,0);
            playerCount++;
        }
        gameController.createPlayers(guiAges);


    }

    /**
     * Prompts the user to enter a player name.
     */
    private GUI_Player AddPlayerPrompt(){
        String player;
        player = gui.getUserString(Language.GetString("enterPlayerName"));
        boolean isTaken = playerNameTaken(player);

        while (isTaken){
            gui.showMessage(Language.GetString("playerNameTaken"));
            player = gui.getUserString(Language.GetString("enterPlayerName"));
            isTaken = playerNameTaken(player);
        }

        GUI_Car te;
       // GUI_Player es = new GUI_Player("tets",20, te);
        GUI_Car lol = new GUI_Car(java.awt.Color.black, Color.lightGray, UFO,ZEBRA);
       // GUI_Player oo = new GUI_Player("lol",20, te);
        if (playerCount == 0){
            te = new GUI_Car(java.awt.Color.black, java.awt.Color.lightGray, UFO,ZEBRA);
        } else{
            te = new GUI_Car(java.awt.Color.black, java.awt.Color.lightGray, UFO,ZEBRA);
        }

        GUI_Player guiPlayer = new GUI_Player(player, startingBalance, te);
        guiPlayer.setBalance(startingBalance);
        guiPlayer.getCar().setPrimaryColor(playerCarColors[playerCount]);
        gui.addPlayer(guiPlayer);
        gui.showMessage(Language.GetString("addedPlayer") +" "+ player);
        return guiPlayer;
    }

    private boolean playerNameTaken(String player){
        for (String name:
             unavailablePlayerNames) {
            if(name != null){
                if(name.equals(player)){
                    return true;
                }
            }

        }
        return false;
    }

    public void updateOwner(int playerID, int field) {
        if (playerID == -1) {
            gui.getFields()[field].setSubText(Language.GetString("noowner"));
        } else {
            gui.getFields()[field].setSubText(guiPlayers[playerID].getName());
        }
    }
}
