package org.g16.MonopolyJR;

import static org.junit.jupiter.api.Assertions.*;
import org.g16.MonopolyJR.Fields.*;
import org.junit.jupiter.api.Test;


public class PlayerTest {

    private static Player createPlayer(int age, int ID, String Test) {
        Player player = new Player(Token.Car);
        player.setAge(age);
        player.setID(ID);
        player.setName(Test);
        return player;
    }

    @Test
    public void passStartTest() {
        Player player = createPlayer(12, 0, "Test");
        player.setPlayerPosition(20);
        player.setBalance(20);
        int prevBalance = player.getPlayerBalance();
        player.setPlayerPosition(2);
        if (player.getPlayerPosition() < player.getPrevPlayerPosition()) {
            player.AddBalance(2);
        }
        assertEquals(prevBalance+2, player.getPlayerBalance());
    }

    @Test
    public void jailTest(){
        Player player = createPlayer(12, 0, "Test");
        player.setJailed(true);
        player.setBalance(20);
        int prevBalance = player.getPlayerBalance();
        if (player.getJailed()){
            if (player.getOutOfJailCards() > 0){
                player.addOutOfJailCard(-1);
                player.setJailed(false);
            }
            if (player.getPlayerBalance() > 1){
                player.AddBalance(-1);
                player.setJailed(false);
            }
        }
        assertEquals(prevBalance-1, player.getPlayerBalance());
    }

    @Test
    public void jailCardTest(){
        Player player = createPlayer(12, 0, "Test");
        player.setJailed(true);
        player.addOutOfJailCard(1);
        int prevBalance = player.getPlayerBalance();
        if (player.getJailed()){
            if (player.getOutOfJailCards() > 0){
                player.addOutOfJailCard(-1);
                player.setJailed(false);
            }else if (player.getPlayerBalance() > 1){
                player.AddBalance(-1);
                player.setJailed(false);
            }
        }
        assertEquals(prevBalance, player.getPlayerBalance());
    }

    @Test
    public void bankruptTest(){
        Player player1 = createPlayer(1, 0, "One");
        player1.AddBalance(-100);

        Player player2 = createPlayer(2, 1, "Two");
        player2.AddBalance(100);

        Player player3 = createPlayer(3, 2, "Three");

        Player[] players = {player1, player2, player3};
        if (player1.getBankrupt()){
            int max = 0;
            int playerNum = 0;
            for (int i = 0; i<players.length; i++){
                int balance = players[i].getPlayerBalance();
                if (balance > max){
                    max = balance;
                    playerNum = i;
                }
            }
            assertEquals(player2,players[playerNum]);
        }
    }

    Initializer init = new Initializer();
    Field[] prop = init.InitFields();
    private Field getField(int dieCount){
        return prop[dieCount];
    }

    @Test
    public void payRentTest(){
        GameController controller = new GameController();
       Player player1 = createPlayer(12, 0, "One");
       Player player2 = createPlayer(11,1,"Two");
       player1.setPlayerPosition(1);
       int prevBalance1 = player1.getPlayerBalance();
       int prevBalance2 = player2.getPlayerBalance();

        PropertyField prop1 = (PropertyField) getField(1);
        prop1.setOwner(player2);

        landOnField( player1, player2);
        assertEquals(prevBalance1-1,player1.getPlayerBalance());
        assertEquals(prevBalance2+1,player2.getPlayerBalance());

    }

    @Test
    public void payDoubleRentTest(){
        Player player1 = createPlayer(12, 0, "One");
        Player player2 = createPlayer(11,1,"Two");
        player1.setPlayerPosition(1);
        int prevBalance1 = player1.getPlayerBalance();
        int prevBalance2 = player2.getPlayerBalance();
        PropertyField prop1 = (PropertyField) getField(1);
        prop1.setOwner(player2);
        PropertyField prop2 = (PropertyField) getField(2);
        prop2.setOwner(player2);

        landOnField(player1, player2);
        assertEquals(prevBalance1-2,player1.getPlayerBalance());
        assertEquals(prevBalance2+2,player2.getPlayerBalance());

    }

    private void landOnField(Player player1, Player player2) {
        if (getField(player1.getPlayerPosition()) instanceof PropertyField property) {
             if (property.getOwner() == null) {
                 player1.AddBalance(-1 * property.getPrice());
                 property.setOwner(player1);
             } else {
                 if (property.getOwner() != player1) {
                     int rentMultiplier = AllColorsOwned(property) ? 2 : 1;
                     player1.AddBalance(-1 * rentMultiplier * property.getPrice());
                     property.getOwner().AddBalance(rentMultiplier * property.getPrice());
                 }
             }
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
}