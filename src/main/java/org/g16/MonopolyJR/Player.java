package org.g16.MonopolyJR;

public class Player {
    MoneyBalance balance = new MoneyBalance();
    private int playerPosition = 0;
    private int prevPlayerPosition = 0;
    Token playerToken;
    private int age;
    private boolean jailed = false;

    public Player(Token playerToken) {
        this.playerToken = playerToken;

    }
    public int getPlayerBalance(){
        return balance.getBalance();
    }
    public int getPlayerPosition(){
        return playerPosition;
    }
    public int getPrevPlayerPosition(){
        return prevPlayerPosition;
    }
    public Token getPlayerToken(){
        return playerToken;
    }
    public int getAge(){
        return age;
    }
    public boolean getJailed(){
        return jailed;
    }
    public void setAge(int age){
        this.age = age;
    }

    public void AddBalance(int add){
        balance.addmoney(add);
    }

    public void setPlayerPosition(int position){
        prevPlayerPosition = playerPosition;
        playerPosition = position;
    }
    public void setJailed(){
        jailed = true;
    }
    public void reset(){
        balance.reset();
    }

}
