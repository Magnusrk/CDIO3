package org.g16;

import org.g16.MonopolyJR.*;

public class Main {
    public static void main(String[] args) {
        Initializer init = new Initializer();
        Field prop[] = init.InitFields();
        PropertyField burger = (PropertyField) prop[1];
        Field skate =  prop[10];
        //skate.getColor();
        burger.getPrice();
        if (skate instanceof PropertyField sk){
            sk.getPrice();
        }
    }
public static void casting(){

}

}