package org.g16.MonopolyJR;

public class Player {
    private boolean jailed = false;
    private int playerPosition = 0;
    Token playerToken;
    MoneyBalance balance = new MoneyBalance();
    public Player(Token playerToken) {
        this.playerToken = playerToken;

    }


    public int getPlayerBalance(){
        return balance.getBalance();
    }
    public int getPlayerPosition(){
        return playerPosition;
    }
    public Token getPlayerToken(){
        return playerToken;
    }

    public void addBalance(int add){
        balance.addmoney(add);
    }

    public void setPlayerPosition(int position){
        playerPosition = position;
    }
    public void reset(){
        balance.reset();
    }

}
