package org.g16.MonopolyJR;

public class Player {
    MoneyBalance balance = new MoneyBalance();
    String name;
    public Player(){

    }

    public int GetPlayerBalance(){
        return balance.getBalance();
    }

    public void AddBalance(int add){
        balance.addmoney(add);
    }
    public void reset(){
        balance.reset();
    }

}
