package org.g16.MonopolyJR;

public class Player {
    private boolean jailed = false;
    Token playerToken;
    MoneyBalance balance = new MoneyBalance();
    public Player(Token playerToken) {
        this.playerToken = playerToken;

    }

    public Token getPlayerToken(){
        return playerToken;
    }

    public int getPlayerBalance(){
        return balance.getBalance();
    }

    public void addBalance(int add){
        balance.addmoney(add);
    }
    public void reset(){
        balance.reset();
    }

}
