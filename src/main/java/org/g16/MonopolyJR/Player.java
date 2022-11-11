package org.g16.MonopolyJR;

public class Player {
    MoneyBalance balance = new MoneyBalance();
    private int playerPosition = 0;
    String name;
    public Player(){

    }

    public int getPlayerBalance(){
        return balance.getBalance();
    }
    public int getPlayerPosition(){
        return playerPosition;
    }

    public void AddBalance(int add){
        balance.addmoney(add);
    }

    public void setPlayerPosition(int position){
        playerPosition = position;
    }
    public void reset(){
        balance.reset();
    }

}
