package org.g16.MonopolyJR;

public class Die {
    private static final int DIESIDE = 6;

    public Die(){

    }
    public static int throwDie(){
        return (int) ((Math.random() * (DIESIDE)) + 1);
    }

    public static int getDIESIDE() {
        return DIESIDE;
    }
}
