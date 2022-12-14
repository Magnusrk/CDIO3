package org.g16.GUI;
import gui_fields.*;
import org.g16.MonopolyJR.*;
import gui_main.GUI;
import org.g16.MonopolyJR.Fields.Field;
import org.g16.MonopolyJR.Fields.PropertyField;

import java.awt.Color;

import static gui_fields.GUI_Car.Pattern.*;
import static gui_fields.GUI_Car.Type.*;

public class MonopolyGUI {

    private GUI gui;
    private final java.awt.Color[] playerCarColors = new java.awt.Color[]{
            Color.orange,
            Color.green,
            Color.blue,
            Color.black
    };
    private GUI_Player[] guiPlayers;
    private String[] guiNames;
    private String[] unavailablePlayerNames;
    private int playerCount = 0;
    private GameController gameController;
    private Field[] startingFields;

    /**
     * Initialize monopoly GUI
     *
     * @param startingFields The method will draw the playing board and return a GUI object,
     *                       which can be used to interact with the GUI.
     */
    public void initGUI(Field[] startingFields, GameController gameController){
        this.gameController = gameController;
        this.startingFields= startingFields;
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
                        guiFields[i] = new GUI_Jail("src/main/resources/jail.jpg", Language.GetString("prison"), Language.GetString("prison"), "", Color.lightGray, Color.black);
                    break;
                case "GoToJailField":
                        guiFields[i] = new GUI_Jail("src/main/resources/GoTo.jpg", Language.GetString("gotojail"), Language.GetString("gotojail"), "", Color.lightGray, Color.black);
                        break;
                case "ChanceField":
                    guiFields[i] = new GUI_Chance();
                    guiFields[i].setSubText(Language.GetString("tryluck"));
                    break;
                case "PropertyField":
                    guiFields[i] = new GUI_Street();
                    guiFields[i].setSubText(Language.GetString("noowner"));
                    PropertyField property = (PropertyField)startingFields[i];
                    guiFields[i].setDescription( Language.GetString("price") + " " + property.getPrice());
                    guiFields[i].setBackGroundColor(ConvertColor(property.getColor()));
                    break;
            }
            guiFields[i].setTitle(startingFields[i].getName());
        }

        gui = new GUI(guiFields);
    }


    /**
     * Update fields text and descriptions
     *If the language is changed, this method can be called to change and re-render all fields on the board.
     */
    public void UpdateFields() {
        for (int i = 0; i < startingFields.length; i++) {
            switch (startingFields[i].getClass().getSimpleName()) {
                default:
                    //If i == 0, it's the start field
                    if (i == 0) {
                        gui.getFields()[i].setSubText(Language.GetString("startsub"));
                    } else {
                        gui.getFields()[i].setSubText(Language.GetString("parking"));
                    }
                    break;
                case "ChanceField":
                    gui.getFields()[i].setSubText(Language.GetString("tryluck"));
                    break;
                case "Jail":
                    gui.getFields()[i].setSubText(Language.GetString("prison"));
                    break;
                case "GoToJailField":
                    gui.getFields()[i].setSubText(Language.GetString("gotojail"));
                    break;
                case "PropertyField":

                    gui.getFields()[i].setSubText(Language.GetString("noowner"));
                    PropertyField property = (PropertyField) startingFields[i];
                    gui.getFields()[i].setDescription(Language.GetString("price") + " " + property.getPrice());
                    break;
            }
            gui.getFields()[i].setTitle(Language.GetString(startingFields[i].getName()));

        }
    }

    /** Used to convert from MonopolyJR.color to java.awt.color
     * @param col the color from the MonopolyJR class.
     *@return java.awt.Color
     */
    private java.awt.Color ConvertColor(org.g16.MonopolyJR.Fields.Color col){
        if(org.g16.MonopolyJR.Fields.Color.DarkBlue == col){
         return Color.BLUE;
        }
        if(org.g16.MonopolyJR.Fields.Color.Blue == col){
            return Color.CYAN;
        }
        if(org.g16.MonopolyJR.Fields.Color.Brown == col){
            return Color.getHSBColor(0.063f,0.69f,0.65f);
        }
        if(org.g16.MonopolyJR.Fields.Color.Red == col){
            return Color.RED;
        }
        if(org.g16.MonopolyJR.Fields.Color.Pink == col){
            return Color.pink;
        }
        if(org.g16.MonopolyJR.Fields.Color.Green == col){
            return Color.green;
        }
        if(org.g16.MonopolyJR.Fields.Color.Orange == col){
            return Color.orange;
        }
        if(org.g16.MonopolyJR.Fields.Color.Yellow == col){
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
     *
     * @param playerID ID of the player
     * @param balance  Sets player with the corrosponding ID's balance
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
        int MAX_PLAYERS = 4;
        while(desiredPlayers < 2 || desiredPlayers > MAX_PLAYERS){
            gui.showMessage(Language.GetString("tooManyOrFew"));
            desiredPlayers = gui.getUserInteger( Language.GetString("howManyPlayers")+ " (2-4)");
        }

        guiPlayers = new GUI_Player[desiredPlayers];
        int[] guiAges = new int[desiredPlayers];
        guiNames = new String[desiredPlayers];
        unavailablePlayerNames = new String[desiredPlayers];


        while (playerCount < desiredPlayers){
            GUI_Player newPlayer = AddPlayerPrompt();
            guiPlayers[playerCount] = newPlayer;
            unavailablePlayerNames[playerCount] = newPlayer.getName();
            guiAges[playerCount] = gui.getUserInteger(Language.GetString("enterAge"));
            DrawPlayerPosition(playerCount,0);
            playerCount++;
        }
        gameController.createPlayers(guiAges, guiNames);


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
        guiNames[playerCount] = player;
        GUI_Car car;
        GUI_Car tractor;
        GUI_Car racer;
        GUI_Car ufo;

        GUI_Player guiPlayer;

        int startingBalance = 24 -(2* guiPlayers.length);
        switch (playerCount) {
            case 0 -> {
                car = new GUI_Car(Color.black, Color.red, CAR, HORIZONTAL_GRADIANT);
                guiPlayer = new GUI_Player(player, startingBalance, car);
            }
            case 1 -> {
                tractor = new GUI_Car(Color.blue, Color.green, TRACTOR, HORIZONTAL_DUAL_COLOR);
                guiPlayer = new GUI_Player(player, startingBalance, tractor);
            }
            case 2 -> {
                racer = new GUI_Car(Color.black, Color.red, RACECAR, HORIZONTAL_LINE);
                guiPlayer = new GUI_Player(player, startingBalance, racer);
            }
            case 3 -> {
                ufo = new GUI_Car(Color.lightGray, Color.lightGray, UFO, HORIZONTAL_DUAL_COLOR);
                guiPlayer = new GUI_Player(player, startingBalance, ufo);
            }
            default -> {
                car = new GUI_Car(Color.black, Color.lightGray, CAR, ZEBRA);
                guiPlayer = new GUI_Player(player, startingBalance, car);
            }
        }


        guiPlayer.setBalance(startingBalance);
        guiPlayer.getCar().setPrimaryColor(playerCarColors[playerCount]);
        gui.addPlayer(guiPlayer);
        gui.showMessage(Language.GetString("addedPlayer") +" "+ player);
        return guiPlayer;
    }

    /** Checks if the player name is already taken
     * @param player the player name that is wanted to be used
     *@return boolean
     */
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

    /** Updates the displayed owner of a field.
     *@param playerID the ID of the new owner
     *@param field the index of the field, which the player is to be the owner of
     */
    public void updateOwner(int playerID, int field) {
        if (playerID == -1) {
            gui.getFields()[field].setSubText(Language.GetString("noowner"));
        } else {
            gui.getFields()[field].setSubText(guiPlayers[playerID].getName());
        }
    }


    public int getUserinterger5() {
        return gui.getUserInteger("Move up to five spaces", 0, 5);
    }



    public int Userselection2(String msg, String opt1, String opt2){
        String selection=gui.getUserSelection(msg,opt1,opt2);
        if (selection.equals(opt1)){
            return 1;
        } else {
            return 2;
        }
    }
    public int Userselection4(String msg, String opt1, String opt2,String opt3, String opt4){
        String selection=gui.getUserSelection(msg,opt1,opt2,opt3,opt4);
        if (selection.equals(opt1)){
            return 1;
        } else if (selection.equals(opt2)) {
            return 2;
        } else if (selection.equals(opt3)) {
            return 3;
        } else {
            return 4;
        }
    }

    public void Showmsg(String msg){
        gui.showMessage(msg);

    }
    public String Userselectionarray(String msg, String[] options){
       return gui.getUserSelection(msg, options);
    }

    public void PromptGotoJail(int playerID) {
        gui.showMessage( guiPlayers[playerID].getName() + " "+ Language.GetString("gotojailprompt"));
    }

    /** Closes the monopoly GUI
     */
    public void closeGUI(){
        gui.close();
    }

    /** Askes the use a question which can be answered with a 'yes' or 'no'
     * @param msg The question of which will be asked
     * @return boolean True if the answer was 'yes', otherwise 'no'
     */
    public boolean yesNoQuestion(String msg){
        String yesString = Language.GetString("yesTxt");
        String noString = Language.GetString("noTxt");
        String input = gui.getUserSelection(msg, yesString, noString);
        return input.equals(yesString);
    }



}
