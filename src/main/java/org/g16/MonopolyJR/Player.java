package org.g16.MonopolyJR;

public class Player {
    private boolean jailed = false;
    MoneyBalance balance = new MoneyBalance();
    public Player(){

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
