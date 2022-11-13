package org.g16.MonopolyJR;

import java.util.Arrays;

public class ChanceField extends Field {

    public int[] drawChancecard() {
        ChancecardController chancecardController = new ChancecardController();
        chancecardController.getNumchance();
        chancecardController.leftshiftarray();
        chancecardController.setNumchance(chancecardController.leftshiftarray());
        return chancecardController.getNumchance();
    }
    public ChanceField(String name) {
        super(name);
    }
}
