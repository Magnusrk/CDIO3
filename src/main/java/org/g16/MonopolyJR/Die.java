package org.g16.MonopolyJR;

public class Die {
    private static final int DIESIDE = 6;

    public Die(){

    }

    public static int[] throwDice(){
        int die1 = (int) ((Math.random() * (DIESIDE)) + 1);
        int die2 = (int) ((Math.random() * (DIESIDE)) + 1);
        return new int[]{die1, die2};
    }

    public static int getDIESIDE() {
        return DIESIDE;
    }
}
