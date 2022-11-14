package org.g16.MonopolyJR;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ChanceField extends Field {
    static Chancecard chancecard = new Chancecard(IntStream.range(1,21).toArray());


    public ChanceField(String name) {
        super(name);
    }

    public int[] drawChancecard() {
        chancecard.getNumchance();
        chancecard.setNumchance(chancecard.leftshiftarray());
        return chancecard.getNumchance();
    }

    public static  void main(String[] args) {
        for (int i=0;i<20;i++){
            chancecard.getNumchance();
            chancecard.setNumchance(chancecard.leftshiftarray());
            System.out.println(Arrays.toString(chancecard.getNumchance()));
        }

    }
}
