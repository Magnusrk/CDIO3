package org.g16.MonopolyJR;

public class GameController {
    private boolean winnerFound = false;
    private Player[] players = new Player[]{
    new Player(Token.Cat),
    new Player(Token.Car),
    new Player(Token.Dog),
    new Player(Token.Ship)
    };
 

    Initializer init = new Initializer();
    Field prop[] = init.InitFields();

    public GameController(){

    }

    public void play(){
        Language.SetLanguage("da");
        playRound(1);
    }

    private void setup(){
        players[0].setAge(10);
        players[1].setAge(12);
        players[2].setAge(14);
        players[3].setAge(9);
    }
    private void playRound(int pt) {
        Player currentPlayer = null;
        currentPlayer = players[pt-1];

        checkJail(currentPlayer);

        int roll = Die.throwDie();
        movePlayer(currentPlayer,roll);

        checkPassStart(currentPlayer);
        landOnField(currentPlayer);

        if (!winnerFound){
            if (pt == 4){
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
                System.out.println(property.getOwner());
            } else {
                currentPlayer.AddBalance(-1 * property.getPrice());
                checkBankrupt(currentPlayer);
                property.getOwner().AddBalance(property.getPrice());
              //  System.out.println("You pay " + property.getPrice() + "to" + property.getOwner());
            }
        } else if (getField(currentPlayer.getPlayerPosition()) instanceof VisitorField visitor) {
            System.out.println("visit");

        } else if (getField(currentPlayer.getPlayerPosition()) instanceof ChanceField chance) {
            System.out.println("Chance");

        } else if (getField(currentPlayer.getPlayerPosition()) instanceof  GoToJailField goToJail){
            currentPlayer.setJailed(true);
            System.out.println("You're jailed");
        }
    }

    private static void checkPassStart(Player currentPlayer) {
        if (currentPlayer.getPlayerPosition() < currentPlayer.getPrevPlayerPosition()){
            currentPlayer.AddBalance(2);
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
            }
        }
    }

    private Field getField(int dieCount){
        return prop[dieCount];
    }
    private void movePlayer(Player player, int moves){
        int newPos = player.getPlayerPosition()+moves;
        if (newPos >23) {
            if (newPos % 23 == 0){
                newPos = 23;
            } else {
                newPos = (newPos % 23) + 1;
            }
        }
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
}
