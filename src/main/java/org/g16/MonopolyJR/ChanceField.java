package org.g16.MonopolyJR;

import java.util.Arrays;

public class ChanceField extends Field {

    public ChanceField(String name) {
        super(name);
    }

    public int[] drawChancecard() {
        Chancecard chancecard = new Chancecard();
        chancecard.getNumchance();
        chancecard.setNumchance(chancecard.leftshiftarray());
        return chancecard.getNumchance();
    }

    public static void main(String[] args) {
        Chancecard chancecard = new Chancecard();
        chancecard.getNumchance();
        chancecard.setNumchance(chancecard.leftshiftarray());
        System.out.println(Arrays.toString(chancecard.getNumchance()));

    }
}
