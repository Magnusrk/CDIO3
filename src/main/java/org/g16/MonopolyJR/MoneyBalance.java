package org.g16.MonopolyJR;

public class MoneyBalance {
    private final int STARTBALANCE = 20;
    private int balance = STARTBALANCE;
    public MoneyBalance(){

    }

    //Adds money to the players balance. x will be a variable from the field class
    public void addmoney(int addCash){
        balance+=addCash;
    }

    public int getBalance(){
        return balance;
    }

    public void reset(){
        balance = STARTBALANCE;
    }
    public int getStartingbalance(){
        return STARTBALANCE;
    }
}
