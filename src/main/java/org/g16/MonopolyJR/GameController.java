package org.g16.MonopolyJR;

import org.g16.GUI.MonopolyGUI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class GameController {
    private boolean winnerFound = false;
    private Player[] players;
    ChanceCard chancecard =new ChanceCard(IntStream.range(1,21).toArray());
    int[] chanceArray=chancecard.Shufflechancecard();
    ChanceField chanceField= new ChanceField("chancefield");
    Initializer init = new Initializer();
    Field[] prop = init.InitFields();
    private MonopolyGUI monoGUI;
    public GameController(){

    }

    public void play(){
        monoGUI = new MonopolyGUI();
        monoGUI.initGUI(prop, this);
        setup();
    }



    private void setup(){
        String chosenLanguage = monoGUI.chooseLanguage();
        Language.SetLanguage(chosenLanguage);

        System.out.println(Language.GetString("noowner"));
        System.out.println(chosenLanguage);
        monoGUI.UpdateFields();
        monoGUI.SetupPlayers();
    }

    public void createPlayers(int[] ages, String[] names){
        players = new Player[ages.length];
        Token[] tokens = new Token[]{Token.Car,Token.Tractor,Token.Racecar,Token.Ufo};
        for(int i = 0; i < ages.length; i++){
            players[i] = new Player(tokens[i]);
            players[i].setAge(ages[i]);
            players[i].setID(i);
            players[i].setName(names[i]);
        }

        int firstTurnIndex = 0;
        int youngestAge = Integer.MAX_VALUE;
        for(int n = 0; n < players.length; n++){
            int playerAge = players[n].getAge();
            if(playerAge < youngestAge){
                firstTurnIndex = n;
                youngestAge = playerAge;
            }
        }

        //Start with the youngest lad
        playRound(firstTurnIndex+1);
    }
    private void playRound(int pt) {
        int playerIndex = pt-1;
        Player currentPlayer;
        currentPlayer = players[playerIndex];

        checkJail(currentPlayer);
        if (currentPlayer.getTokenChancecard()==true){
            TokenChanceCard(currentPlayer);
        } else {

            monoGUI.PromptThrowDice(playerIndex);
            int roll = Die.throwDie();
            monoGUI.DrawDie(roll);
            movePlayer(currentPlayer, roll);

            checkPassStart(currentPlayer);
            landOnField(currentPlayer);
        }
        if (!winnerFound){
            if (pt == players.length){
                pt = 1;
            }else {
                pt++;
            }
            playRound(pt);
        }

    }

    private void landOnField(Player currentPlayer) {
        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
            if (property.getOwner() == null) {
                currentPlayer.AddBalance(-1 * property.getPrice());
               // System.out.println("You pay " + property.getPrice());
                property.setOwner(currentPlayer);
                monoGUI.updateOwner(currentPlayer.getID(), currentPlayer.getPlayerPosition());
                System.out.println(property.getOwner());
                monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());
                monoGUI.Showmsg(currentPlayer.getName() +" "  + Language.GetString("BoughtField") + " " + Language.GetString(property.getName())  +  ". " + Language.GetString("YouPay") + " "+ property.getPrice() );
                checkBankrupt(currentPlayer);
            } else {
                if (property.getOwner() != currentPlayer) {
                    int rentMultiplier = AllColorsOwned(property) ? 2 : 1;
                    currentPlayer.AddBalance(-1 * rentMultiplier * property.getPrice());
                    property.getOwner().AddBalance(rentMultiplier * property.getPrice());
                    monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());
                    monoGUI.SetPlayerBalance(property.getOwner().getID(), property.getOwner().getPlayerBalance());
                    String notificationText = currentPlayer.getName() + " " + Language.GetString("PaysRent") + " " + property.getOwner().getName() + "." + Language.GetString("YouPay") + " " + (property.getPrice() * rentMultiplier) + ". ";
                    if (rentMultiplier == 2) {
                        notificationText += "Because all colors are owned, the rent was doubled!";
                    }
                    monoGUI.Showmsg(notificationText);
                    checkBankrupt(currentPlayer);
                }
            }
        } else if (getField(currentPlayer.getPlayerPosition()) instanceof VisitorField) {
            System.out.println("visit");

        } else if (getField(currentPlayer.getPlayerPosition()) instanceof ChanceField) {
            DoChanceCard(currentPlayer);
            System.out.println("Chance");

        } else if (getField(currentPlayer.getPlayerPosition()) instanceof GoToJailField){
            currentPlayer.setJailed(true);
            System.out.println("You're jailed");
            monoGUI.PromptGotoJail(currentPlayer.getID());
            currentPlayer.setPlayerPosition(6);
            monoGUI.DrawPlayerPosition(currentPlayer.getID(), 6);
        }
    }

    private boolean AllColorsOwned(PropertyField currentProperty){
        org.g16.MonopolyJR.Color propertyColor = currentProperty.getColor();
        Player propertyOwner = currentProperty.getOwner();

        if(currentProperty.getOwner() != null){
            for (Field field: prop) {
                if(field instanceof PropertyField property){
                    if(propertyColor == property.getColor()){
                        if(property.getOwner() == null) {
                            return false;
                        }
                        if(property.getOwner().getID() != propertyOwner.getID()){
                            return false;
                        }
                    }
                }
            }
            return true;
            }
        return false;
    }

    private void checkPassStart(Player currentPlayer) {
        if (currentPlayer.getPlayerPosition() < currentPlayer.getPrevPlayerPosition()){
            currentPlayer.AddBalance(2);
            monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance()+2);
        }
    }

    private void checkJail(Player currentPlayer) {
        if (currentPlayer.getJailed()){
            if (currentPlayer.getOutOfJailCards() > 0){
                currentPlayer.addOutOfJailCard(-1);
                currentPlayer.setJailed(false);
            }
            if (currentPlayer.getPlayerBalance() > 1){
                currentPlayer.AddBalance(-1);
                checkBankrupt(currentPlayer);
                currentPlayer.setJailed(false);
                monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());
            }
        }
    }

    private Field getField(int dieCount){
        return prop[dieCount];
    }
    private void movePlayer(Player player, int moves){
        int newPos = player.getPlayerPosition()+moves;

        //If the new position is out of bounds. Loop around until the index in within
        //The bounds of the array
        while(newPos >= prop.length){
            newPos -= prop.length;
        }
        monoGUI.DrawPlayerPosition(player.getID(),newPos);
        player.setPlayerPosition(newPos);
    }
    private void checkBankrupt(Player player){
        if (player.getBankrupt()){
            int max = 0;
            int playerNum = 0;
            for (int i = 0; i<players.length; i++){
                int balance = players[i].getPlayerBalance();
                if (balance > max){
                    max = balance;
                    playerNum = i;
                }
            }
            System.out.println("Player " + (playerNum+1) + " won");
            monoGUI.Showmsg(players[playerNum].getName() + " won the game!");
            winnerFound = true;
        }
    }
    public void DoChanceCard(Player currentPlayer ){

        chancecard.setNumchance(chanceArray);


        switch (chancecard.getNumchance()[0]) {
            case 1 -> {
                for (Player player : players) {
                    if (player.playerToken == Token.Ufo) {
                        player.setTokenChancecard(true);
                    }
                }
                monoGUI.Showmsg(Language.GetString("case1"));
            }
            case 2 -> {
                currentPlayer.setPlayerPosition(0);
                monoGUI.DrawPlayerPosition(currentPlayer.getID(), 0);
                monoGUI.Showmsg(Language.GetString("case2"));
            }
            case 3 -> {
                int move = monoGUI.getUserinterger5();
                movePlayer(currentPlayer,move);
                landOnField(currentPlayer);
            }
            case 4 -> {
                int move = monoGUI.Userselection2(Language.GetString("case4"),
                        Language.GetString("skate"), Language.GetString("pool"));
                if (move == 1) {
                    moveAndBuy(currentPlayer, 10);
                } else if (move == 2) {
                    moveAndBuy(currentPlayer, 11);
                }
                landOnField(currentPlayer);
            }
            case 5 -> {
                int action = monoGUI.Userselection2(Language.GetString("case5"),
                        Language.GetString("case5opt1"), Language.GetString("case5opt2"));
                if (action == 1) {
                    movePlayer(currentPlayer, 1);
                    landOnField(currentPlayer);
                } else if (action == 2) {
                    chanceArray = chanceField.drawChancecard();
                    DoChanceCard(currentPlayer);
                }
            }
            case 6 -> {
                for (Player player : players) {
                    if (player.playerToken == Token.Racecar) {
                        player.setTokenChancecard(true);
                    }
                }
                monoGUI.Showmsg(Language.GetString("case6"));
            }
            case 7 -> {
                currentPlayer.AddBalance(-2);
                monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());
            }
            case 8 -> {
                int move = monoGUI.Userselection4(Language.GetString("case8")
                        , Language.GetString("skate"), Language.GetString("pool"), Language.GetString("bowling"), Language.GetString("zoo"));
                if (move == 1) {
                    moveAndBuy(currentPlayer, 10);
                } else if (move == 2) {
                    moveAndBuy(currentPlayer, 11);
                } else if (move == 3) {
                    moveAndBuy(currentPlayer, 19);
                } else if (move == 4) {
                    moveAndBuy(currentPlayer, 20);
                }
                landOnField(currentPlayer);
            }
            case 9 -> {
                int move = monoGUI.Userselection2(Language.GetString("case9"),
                        Language.GetString("candy"), Language.GetString("ice"));
                if (move == 1) {
                    moveAndBuy(currentPlayer, 4);
                } else if (move == 2) {
                    moveAndBuy(currentPlayer, 5);
                }
                landOnField(currentPlayer);
            }
            case 10 -> {
                currentPlayer.addOutOfJailCard(1);
                monoGUI.Showmsg(Language.GetString("case10"));
            }
            case 11 -> {
                currentPlayer.setPlayerPosition(23);
                monoGUI.DrawPlayerPosition(currentPlayer.getID(), 23);
                landOnField(currentPlayer);
                monoGUI.Showmsg(Language.GetString("case11"));
            }
            case 12 -> {
                for (Player player : players) {
                    if (player.playerToken == Token.Car) {
                        player.setTokenChancecard(true);
                    }
                }
                monoGUI.Showmsg(Language.GetString("case12"));
            }
            case 13 -> {
                for (Player player : players) {
                    if (player.playerToken == Token.Tractor) {
                        player.setTokenChancecard(true);
                    }
                }
                monoGUI.Showmsg(Language.GetString("case13"));
            }
            case 14 -> {
                currentPlayer.AddBalance(players.length);
                for (Player player : players) {
                    player.AddBalance(-1);
                    monoGUI.SetPlayerBalance(player.getID(), player.getPlayerBalance());
                }
                monoGUI.Showmsg(Language.GetString("case14"));
            }
            case 15 -> {
                int move = monoGUI.Userselection4(Language.GetString("case15")
                        , Language.GetString("museum"), Language.GetString("library"), Language.GetString("waterpark"), Language.GetString("beach"));
                monoGUI.DrawPlayerPosition(currentPlayer.getID(), 7);
                if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                    System.out.println(property.getName());
                    if (property.getOwner() == null) {
                        property.setOwner(currentPlayer);
                        monoGUI.updateOwner(currentPlayer.getID(), 7);
                    }
                } else if (move == 2) {
                    moveAndBuy(currentPlayer, 8);
                } else if (move == 3) {
                    moveAndBuy(currentPlayer, 22);
                } else if (move == 4) {
                    moveAndBuy(currentPlayer, 23);
                }
                landOnField(currentPlayer);
            }
            case 16 -> {
                currentPlayer.AddBalance(2);
                monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());
                monoGUI.Showmsg(Language.GetString("case16"));
            }
            case 17 -> {
                int move = monoGUI.Userselection2(Language.GetString("case17"),
                        Language.GetString("arcade"), Language.GetString("cinema"));
                if (move == 1) {
                    moveAndBuy(currentPlayer, 13);
                } else if (move == 2) {
                    moveAndBuy(currentPlayer, 14);
                }
                landOnField(currentPlayer);
            }
            case 18 -> {
                moveAndBuy(currentPlayer, 10);
                monoGUI.Showmsg(Language.GetString("case18"));
                landOnField(currentPlayer);
            }
            case 19 -> {
                int move = monoGUI.Userselection4(Language.GetString("case19")
                        , Language.GetString("candy"), Language.GetString("ice"), Language.GetString("arcade"), Language.GetString("cinema"));
                if (move == 1) {
                    moveAndBuy(currentPlayer, 4);
                } else if (move == 2) {
                    moveAndBuy(currentPlayer, 5);
                } else if (move == 3) {
                    moveAndBuy(currentPlayer, 13);
                } else if (move == 4) {
                    moveAndBuy(currentPlayer, 14);
                }
                landOnField(currentPlayer);
            }
            case 20 -> {
                int move = monoGUI.Userselection4(Language.GetString("case20")
                        , Language.GetString("burger"), Language.GetString("pizza"), Language.GetString("toystore"), Language.GetString("petstore"));
                if (move == 1) {
                    moveAndBuy(currentPlayer, 1);
                } else if (move == 2) {
                    moveAndBuy(currentPlayer, 2);
                } else if (move == 3) {
                    moveAndBuy(currentPlayer, 16);
                } else if (move == 4) {
                    moveAndBuy(currentPlayer, 17);
                }
                landOnField(currentPlayer);
            }
        }
        chanceArray = chanceField.drawChancecard();
    }

    private void moveAndBuy(Player currentPlayer, int position) {
        currentPlayer.setPlayerPosition(position);
        monoGUI.DrawPlayerPosition(currentPlayer.getID(), position);
        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
            System.out.println(property.getName());
            if (property.getOwner() == null) {
                property.setOwner(currentPlayer);
                monoGUI.updateOwner(currentPlayer.getID(), position);
            }
        }
    }

    public void TokenChanceCard(Player currentPlayer){
        List<String> avbprops=new ArrayList<String>();
        List<String> allprops=new ArrayList<String>();
        for (int i=0;i<prop.length;i++){
            if (prop[i] instanceof PropertyField propertyField){
                if (propertyField.getOwner()==null){
                    avbprops.add(Language.GetString(propertyField.getName()));
                }
                allprops.add(Language.GetString(propertyField.getName()));
            }
        }
        if (avbprops.isEmpty()){
            String[] alloptions= new String[allprops.size()];
            allprops.toArray(alloptions);

            String selectedprop=monoGUI.Userselectionarray("dsfa", alloptions);
            for (int i=0;i<prop.length;i++){

                if (Language.GetString(prop[i].getName()).equals(selectedprop)){
                    currentPlayer.setPlayerPosition(i);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(),i);
                    PropertyField propertyField = (PropertyField) prop[i];
                    propertyField.getOwner().AddBalance(propertyField.getPrice());
                    monoGUI.SetPlayerBalance(propertyField.getOwner().getID(),propertyField.getOwner().getPlayerBalance());
                    propertyField.setOwner(currentPlayer);
                    monoGUI.updateOwner(currentPlayer.getID(),i);
                    currentPlayer.AddBalance(propertyField.getPrice()*(-1));
                    monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());
                }
            }
        }else {
                    String[] options= new String[avbprops.size()];
                    avbprops.toArray(options);
            String selectedprop=monoGUI.Userselectionarray(Language.GetString("tokenChance"), options);
            for (int i=0;i<prop.length;i++){

                  if (Language.GetString(prop[i].getName()).equals(selectedprop)){
                      currentPlayer.setPlayerPosition(i);
                      monoGUI.DrawPlayerPosition(currentPlayer.getID(),i);
                      PropertyField propertyField= (PropertyField) prop[i];
                      propertyField.setOwner(currentPlayer);
                      monoGUI.updateOwner(currentPlayer.getID(),i);
                  }
                }
            currentPlayer.setTokenChancecard(false);
        }
    }

    public void exitGame(){
        monoGUI.closeGUI();
    }

    public boolean askRestart(){
        return monoGUI.yesNoQuestion(Language.GetString("restartQuestion"));
    }
}
