package org.g16.MonopolyJR;

public class MoneyBalance {
    private static int startingbalance = 1000;
    private int balance = startingbalance;
    public MoneyBalance(){

    }

    //Adds money to the players balance. x will be a variable from the field class
    public void addmoney(int addCash){
        balance+=addCash;
        if (balance<0){
            balance=0;
        }
    }

    public int getBalance(){
        return balance;
    }

    public void reset(){
        balance = startingbalance;
    }
    public int getStartingbalance(){
        return startingbalance;
    }
    public void setStartingbalance (int newStartingbalance){
        startingbalance = newStartingbalance;
    }
}
