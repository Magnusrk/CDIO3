package org.g16.MonopolyJR;

public class GameController {
    private boolean winnerFound = false;
    public GameController(){

    }

    public void play(){
        Language.SetLanguage("dansk");
        playRound();
    }
    private void playRound(){
        Player player1 = new Player(Token.Cat);
        int roll = Die.throwDie();
        player1.setPlayerPosition(4);
        System.out.println(player1.getPlayerPosition());
        if (getField(player1.getPlayerPosition()) instanceof PropertyField property){
            System.out.println(property.getPrice());
            System.out.println(property.getColor());
            System.out.println(Language.GetString("roll"));
            System.out.println(property.getName());
            property.setOwner(player1);
            System.out.println(property.getOwner().getPlayerToken());
        } else if (getField(player1.getPlayerPosition()) instanceof VisitorField visitor) {
            System.out.println(visitor.getName());
        } else if (getField(player1.getPlayerPosition()) instanceof  ChanceField chance) {
            System.out.println(chance.getName());
        }

    }

    private Field getField(int dieCount){
        Initializer init = new Initializer();
        Field prop[] = init.InitFields();
        return prop[dieCount];
    }
}
