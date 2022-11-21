package org.g16.MonopolyJR;

public class Player {
    MoneyBalance balance = new MoneyBalance();

    private String name;
    private int playerPosition = 0;
    private int prevPlayerPosition = 0;
    Token playerToken;
    private int age;
    private boolean jailed = false;
    private boolean bankrupt = false;
    private int outOfJailCards = 0;
    private int ID = -1;

    private boolean tokenChancecard= false;

    public Player(Token playerToken) {
        this.playerToken = playerToken;

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setID(int ID){
        this.ID = ID;
    }
    public int getID(){
        return ID;
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
    public boolean getBankrupt(){
        return bankrupt;
    }
    public boolean getJailed(){
        return jailed;
    }
    public void addOutOfJailCard(int card){
        outOfJailCards = outOfJailCards + card;
    }
    public int getOutOfJailCards(){
        return outOfJailCards;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setTokenChancecard(boolean tcard){
        this.tokenChancecard=tcard;
    }
    public boolean getTokenChancecard(){
        return tokenChancecard;
    }
    public void AddBalance(int add){
        balance.addmoney(add);
        if (balance.getBalance() < 0){
            bankrupt = true;
        }
    }
    public void setBalance(int ba) {
        balance.setBalance(ba);
    }

    public void setPlayerPosition(int position){
        prevPlayerPosition = playerPosition;
        playerPosition = position;
    }
    public void setJailed(boolean jail){
        jailed = jail;
    }
    public void reset(){
        balance.reset();
    }

}
