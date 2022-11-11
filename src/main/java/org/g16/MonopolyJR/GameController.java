package org.g16.MonopolyJR;

public class GameController {
    private boolean winnerFound = false;
    private Player player1 = new Player(Token.Cat);
    private Player player2 = new Player(Token.Car);
    private Player player3 = new Player(Token.Dog);
    private Player player4 = new Player(Token.Ship);

    public GameController(){

    }

    public void play(){
        Language.SetLanguage("dansk");
        playRound(1);
    }

    private void setup(){
        player1.setAge(10);
        player2.setAge(12);
        player3.setAge(14);
        player4.setAge(9);
    }
    private void playRound(int pt) {
        Player currentPlayer = null;
        switch (pt){
            case 1:
                currentPlayer = player1;
                break;
            case 2:
                currentPlayer = player2;
                break;
            case 3:
                currentPlayer = player3;
                break;
            case 4:
                currentPlayer = player4;
                break;
        }
        if (currentPlayer.getJailed()){
            if (currentPlayer.getPlayerBalance() > 1){
                currentPlayer.AddBalance(-1);
            }
        }

        int roll = Die.throwDie();
        currentPlayer.setPlayerPosition(1);
        System.out.println(currentPlayer.getPlayerPosition());

        if (currentPlayer.getPlayerPosition() < currentPlayer.getPrevPlayerPosition()){
            currentPlayer.AddBalance(2);
            System.out.println("Passed start");
        }
        if (getField(currentPlayer.getPlayerPosition()) instanceof PropertyField property) {
            System.out.println(property);
            if (property.getOwner() == null) {
                currentPlayer.AddBalance(-1 * property.getPrice());
                System.out.println("You pay " + property.getPrice());
                getField(1).getOwner(currentPlayer);
                System.out.println(property.getOwner());
            }else {
                currentPlayer.AddBalance(-1*property.getPrice());
                property.getOwner().AddBalance(property.getPrice());
                System.out.println("You pay " + property.getPrice() + "to" + property.getOwner());
            }
        } else if (getField(currentPlayer.getPlayerPosition()) instanceof VisitorField visitor) {
            System.out.println("visit");

        } else if (getField(currentPlayer.getPlayerPosition()) instanceof ChanceField chance) {
            System.out.println("Chance");

        } else if (getField(currentPlayer.getPlayerPosition()) instanceof  GoToJailField goToJail){
            currentPlayer.setJailed();
            System.out.println("You're jailed");
        }
        if (!winnerFound){
            if (pt == 4){
                pt = 1;
            }else {
                pt++;
            }
            playRound(pt);
        }

    }

    private Field getField(int dieCount){
        Initializer init = new Initializer();
        Field prop[] = init.InitFields();
        return prop[dieCount];
    }
}
