package org.g16.MonopolyJR;

public class Player {
    MoneyBalance balance = new MoneyBalance();
    private int playerPosition = 0;
    String name;
    public Player(String name){
        this.name = name;

    }

    public int getPlayerBalance(){
        return balance.getBalance();
    }
    public int getPlayerPosition(){
        return playerPosition;
    }
    public String getName(){
        return name;
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
