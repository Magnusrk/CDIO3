package org.g16.MonopolyJR;

import org.g16.GUI.MonopolyGUI;

import java.util.stream.IntStream;


public class GameController {
    private boolean winnerFound = false;
    private Player[] players;

    ChanceCard chancecard =new ChanceCard(IntStream.range(1,21).toArray());
    int[] chanceArray=chancecard.Shufflechancecard();
    ChanceField chanceField= new ChanceField("chancefield");


 

    Initializer init = new Initializer();
    Field prop[] = init.InitFields();

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

    public void createPlayers(int[] ages){
        players = new Player[ages.length];
        Token[] tokens = new Token[]{Token.Cat,Token.Car,Token.Dog,Token.Ship};
        for(int i = 0; i < ages.length; i++){
            players[i] = new Player(tokens[i]);
            players[i].setAge(ages[i]);
            players[i].setID(i);
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
        Player currentPlayer = null;
        currentPlayer = players[playerIndex];

        checkJail(currentPlayer);

        monoGUI.PromptThrowDice(playerIndex);
        int roll = Die.throwDie();
        monoGUI.DrawDie(roll);
        movePlayer(currentPlayer,roll);


        checkPassStart(currentPlayer);
        landOnField(currentPlayer);

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
            System.out.println(property.getName());
            if (property.getOwner() == null) {
                currentPlayer.AddBalance(-1 * property.getPrice());
                checkBankrupt(currentPlayer);
               // System.out.println("You pay " + property.getPrice());
                property.setOwner(currentPlayer);
                monoGUI.updateOwner(currentPlayer.getID(), currentPlayer.getPlayerPosition());
                System.out.println(property.getOwner());
                monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());

            } else {
                int rentMultiplier = AllColorsOwned(property) ? 2 : 1;
                currentPlayer.AddBalance(-1*rentMultiplier * property.getPrice());
                checkBankrupt(currentPlayer);
                property.getOwner().AddBalance(rentMultiplier*property.getPrice());

                monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());
                monoGUI.SetPlayerBalance(property.getOwner().getID(), property.getOwner().getPlayerBalance());

                //  System.out.println("You pay " + property.getPrice() + "to" + property.getOwner());
            }
        } else if (getField(currentPlayer.getPlayerPosition()) instanceof VisitorField visitor) {
            System.out.println("visit");

        } else if (getField(currentPlayer.getPlayerPosition()) instanceof ChanceField chance) {
            DoChanceCard(currentPlayer);
            System.out.println("Chance");

        } else if (getField(currentPlayer.getPlayerPosition()) instanceof  GoToJailField goToJail){
            currentPlayer.setJailed(true);
            System.out.println("You're jailed");
        }
    }

    private boolean AllColorsOwned(PropertyField currentProperty){
        Color propertyColor = currentProperty.getColor();
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
            monoGUI.SetPlayerBalance(currentPlayer.getID(), currentPlayer.getPlayerBalance());
            System.out.println("Passed start");
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
        System.out.println(player.getPlayerBalance());
        if (player.getBankrupt() == true){
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
            winnerFound = true;
        }
    }
    public int[] DoChanceCard(Player currentPlayer ){

        chancecard.setNumchance(chanceArray);

        //switch (chancecard.getNumchance()[0]) {
        switch (1){
            case 1:{
                for (int i=0;i<players.length;i++){
                    if (players[i].playerToken==Token.Car){
                        players[i].setTokenChancecard();
                    }
                }
                monoGUI.Showmsg(Language.GetString("case1"));
                break;
            }

            case 2:{
                currentPlayer.setPlayerPosition(0);
                monoGUI.DrawPlayerPosition(currentPlayer.getID(),0);
                monoGUI.Showmsg(Language.GetString("case2"));
                break;
            }
            case 3:{
                int move= monoGUI.getUserinterger5();
                if (move==0){
                } else if (move==1) {
                    movePlayer(currentPlayer,1);
                } else if (move==2) {
                    movePlayer(currentPlayer,2);
                } else if (move==3) {
                    movePlayer(currentPlayer,3);
                } else if (move==4) {
                    movePlayer(currentPlayer,4);
                } else if (move==5) {
                    movePlayer(currentPlayer,5);
                }
                landOnField(currentPlayer);
                break;
            }
            case 4:{
                int move=monoGUI.Userselection2(Language.GetString("case4"),
                        Language.GetString("skate"),Language.GetString("pool"));
                if (move==1) {
                    currentPlayer.setPlayerPosition(10);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(),10);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner()==null) {
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(), 10);}}
                } else if (move==2) {
                    currentPlayer.setPlayerPosition(11);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(),11);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner()==null) {
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(), 11);}}
                }
                landOnField(currentPlayer);
                break;
            }
            case 5:{
                int action= monoGUI.Userselection2(Language.GetString("Case5"),
                        Language.GetString("Case5opt1"),Language.GetString("Case5opt2"));
                if (action==1){
                    movePlayer(currentPlayer,1);
                    landOnField(currentPlayer);
                } else if (action==2) {
                    DoChanceCard(currentPlayer);
                }
                break;
            }
            case 6:{
                for (int i=0;i<players.length;i++){
                    if (players[i].playerToken==Token.Car){
                        players[i].setTokenChancecard();
                    }
                }
                monoGUI.Showmsg(Language.GetString("case6"));
                break;
            }
            case 7:{
                currentPlayer.AddBalance(-2);
                monoGUI.SetPlayerBalance(currentPlayer.getID(),-2);
                break;
            }
            case 8:{
                int move = monoGUI.Userselection4(Language.GetString("Case8")
                ,Language.GetString("skate"),Language.GetString("pool"),Language.GetString("bowling"),Language.GetString("zoo"));
                if (move == 1) {
                    currentPlayer.setPlayerPosition(10);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(), 10);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner() == null) {
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(), 10);
                        }
                    }
                } else if (move == 2) {
                    currentPlayer.setPlayerPosition(11);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(), 11);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner() == null) {
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(), 11);
                        }
                    }
                }else if (move == 3) {
                    currentPlayer.setPlayerPosition(19);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(), 19);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner() == null) {
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(), 19);
                        }
                    }
                }
                else if (move == 4) {
                    currentPlayer.setPlayerPosition(20);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(), 20);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner() == null) {
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(), 20);
                        }
                    }
                }
                landOnField(currentPlayer);
                break;
            }
            case 9: {
                int move = monoGUI.Userselection2(Language.GetString("case9"),
                        Language.GetString("candy"),Language.GetString("ice"));
                if (move == 1) {
                    currentPlayer.setPlayerPosition(4);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(), 4);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner() == null) {
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(), 4);
                        }
                    }
                } else if (move == 2) {
                    currentPlayer.setPlayerPosition(5);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(), 5);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner() == null) {
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(), 5);
                        }
                    }
                }
                landOnField(currentPlayer);
                break;
            }
            case 10:{
                currentPlayer.addOutOfJailCard(1);
                monoGUI.Showmsg(Language.GetString("case10"));
                break;
            }
            case 11:{
                currentPlayer.setPlayerPosition(23);
                monoGUI.DrawPlayerPosition(currentPlayer.getID(),23);
                landOnField(currentPlayer);
                monoGUI.Showmsg(Language.GetString("case11"));
                break;
            }
            case 12:{
                for (int i=0;i<players.length;i++){
                    if (players[i].playerToken==Token.Car){
                        players[i].setTokenChancecard();
                    }
                }
                monoGUI.Showmsg(Language.GetString("case12"));
                break;
                }
            case 13:{
                for (int i=0;i<players.length;i++){
                    if (players[i].playerToken==Token.Car){
                        players[i].setTokenChancecard();
                    }
                }
                monoGUI.Showmsg(Language.GetString("case13"));
                break;
            }
            case 14: {
                currentPlayer.AddBalance(1 * players.length);
                monoGUI.SetPlayerBalance(currentPlayer.getID(), 1 * players.length);

                for (int i = 0; i < players.length; i++) {
                    players[i].AddBalance(-1);
                }
                monoGUI.Showmsg(Language.GetString("case14"));
                break;
                }
                case 15: {
                    int move = monoGUI.Userselection4(Language.GetString("case15")
                            ,Language.GetString("museum"),Language.GetString("library"),Language.GetString("waterpark"),Language.GetString("beach"));
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 7);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 7);
                            }
                        }
                    else if (move == 2) {
                        currentPlayer.setPlayerPosition(8);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 8);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 8);
                            }
                        }
                    }else if (move == 3) {
                        currentPlayer.setPlayerPosition(22);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 22);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 22);
                            }
                        }
                    }
                    else if (move == 4) {
                        currentPlayer.setPlayerPosition(23);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 23);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 23);
                            }
                        }
                    }
                    landOnField(currentPlayer);
                    break;
                }
                case 16: {
                    currentPlayer.AddBalance(2);
                    monoGUI.SetPlayerBalance(currentPlayer.getID(), 2);
                    monoGUI.Showmsg(Language.GetString("case16"));
                    break;
                }
                case 17: {
                    int move =monoGUI.Userselection2(Language.GetString("case17"),
                            Language.GetString("arcade"),Language.GetString("cinema"));
                    if (move == 1) {
                        currentPlayer.setPlayerPosition(13);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 13);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 13);
                            }
                        }
                    } else if (move == 2) {
                        currentPlayer.setPlayerPosition(14);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 14);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 14);
                            }
                        }
                    }
                    landOnField(currentPlayer);
                    break;
                }
                case 18: {
                    currentPlayer.setPlayerPosition(10);
                    monoGUI.DrawPlayerPosition(currentPlayer.getID(),10);
                    if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                        System.out.println(property.getName());
                        if (property.getOwner()==null){
                            property.setOwner(currentPlayer);
                            monoGUI.updateOwner(currentPlayer.getID(),10);
                        }
                    }
                    monoGUI.Showmsg(Language.GetString("case18"));
                    landOnField(currentPlayer);
                    break;
                }
                case 19: {
                    int move = monoGUI.Userselection4(Language.GetString("case19")
                            ,Language.GetString("candy"),Language.GetString("ice"),Language.GetString("arcade"),Language.GetString("cinema"));
                    if (move == 1) {
                        currentPlayer.setPlayerPosition(4);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 4);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 4);
                            }
                        }
                    } else if (move == 2) {
                        currentPlayer.setPlayerPosition(5);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 5);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 5);
                            }
                        }
                    }else if (move == 3) {
                        currentPlayer.setPlayerPosition(13);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 13);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 13);
                            }
                        }
                    }
                    else if (move == 4) {
                        currentPlayer.setPlayerPosition(14);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 14);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 14);
                            }
                        }
                    }
                    landOnField(currentPlayer);
                    break;
                }
                case 20: {
                    int move = monoGUI.Userselection4(Language.GetString("case20")
                            ,Language.GetString("burger"),Language.GetString("pizza"),Language.GetString("toystore"),Language.GetString("petstore"));
                    if (move == 1) {
                        currentPlayer.setPlayerPosition(1);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 1);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 1);
                            }
                        }
                    } else if (move == 2) {
                        currentPlayer.setPlayerPosition(2);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 2);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 2);
                            }
                        }
                    }else if (move == 3) {
                        currentPlayer.setPlayerPosition(16);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 16);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 16);
                            }
                        }
                    }
                    else if (move == 4) {
                        currentPlayer.setPlayerPosition(17);
                        monoGUI.DrawPlayerPosition(currentPlayer.getID(), 17);
                        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
                            System.out.println(property.getName());
                            if (property.getOwner() == null) {
                                property.setOwner(currentPlayer);
                                monoGUI.updateOwner(currentPlayer.getID(), 17);
                            }
                        }
                    }
                    landOnField(currentPlayer);
                    break;
                }
            }
            return chanceArray=chanceField.drawChancecard();
    }
    public void TokenChanceCard(){

    }
}
