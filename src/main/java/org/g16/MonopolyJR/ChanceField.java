package org.g16.MonopolyJR;

public class ChanceField extends Field {

    public ChanceField(String name) {
        super(name);
    }

    public int[] drawChancecard() {
        ChancecardController chancecardController = new ChancecardController();
        chancecardController.getNumchance();
        chancecardController.leftshiftarray();
        chancecardController.setNumchance(chancecardController.leftshiftarray());
        return chancecardController.getNumchance();
    }
}
