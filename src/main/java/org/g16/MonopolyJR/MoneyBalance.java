package org.g16.MonopolyJR;
public class MoneyBalance {
    int players = 0;
    private int startbalance;
    private int balance;
    public MoneyBalance(){

    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    //Adds money to the players balance. x will be a variable from the field class
    public void addmoney(int addCash){
        balance+=addCash;
    }

    public int getBalance(){
        return balance;
    }

    public void reset(){
        balance = startbalance;
    }
    public int getStartingbalance(){
        return startbalance;
    }


}
