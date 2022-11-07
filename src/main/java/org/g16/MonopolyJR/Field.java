package org.g16.MonopolyJR;

public class Field {

    String field;
    String fieldTxt;
    int addCash;
    /*Switch case to choose which field is landed on.
     It chooses based on the dice throw and outputs the corresponding field.*/
    public int fieldGet(int dieCount) {
        if (dieCount >12) {
            if (dieCount % 12 == 0){
                dieCount = 12;
            } else {
                dieCount = (dieCount % 12) + 1;
            }
        }
        switch (dieCount){
            case 2:
                field = "Tower";
                addCash = 250; //addCash sets acquired gold to be used in the MoneyBalance-class.
                fieldTxt = Language.GetString("tower") + " " + Language.GetString("gain") + " " + Math.abs(addCash) + Language.GetString("gold");
                /*fieldTxt is used in the Language-class to find to right line of string to be used on this field.*/
                return 2;
            case 3:
                field = "Crater";
                addCash = -100;
                fieldTxt = Language.GetString("crater")+ " " + Language.GetString("lose") + " " + Math.abs(addCash) + Language.GetString("gold");
                return 3;
            case 4:
                field = "Palace gates";
                addCash = 100;
                fieldTxt = Language.GetString("palace")+ " " + Language.GetString("gain")+ " " + Math.abs(addCash) + Language.GetString("gold");
                return 4;
            case 5:
                field = "Cold Desert";
                addCash = -20;
                fieldTxt = Language.GetString("desert")+ " " + Language.GetString("lose") + " " + Math.abs(addCash) + Language.GetString("gold");
                return 5;
            case 6:
                field = "Walled city";
                addCash = 180;
                fieldTxt = Language.GetString("city") + " " + Language.GetString("gain" ) + " " + Math.abs(addCash) + Language.GetString("gold");
                return 6;
            case 7:
                field = "Monastery";
                addCash = 0;
                fieldTxt = Language.GetString("monastery");
                return 7;
            case 8:
                field = "Black cave";
                addCash = -70;
                fieldTxt = Language.GetString("cave") + " " + Language.GetString("lose") + " " + Math.abs(addCash) + Language.GetString("gold");
                return 8;
            case 9:
                field = "Huts in the mountain";
                addCash = 60;
                fieldTxt = Language.GetString("huts") + " " + Language.GetString("gain") + " " + Math.abs(addCash)+ Language.GetString("gold");
                return 9;
            case 10:
                field = "The Werewall";
                addCash = -80;
                fieldTxt =  Language.GetString("werewall") + " " + Language.GetString("lose") + " " + Math.abs(addCash)+ Language.GetString("gold");
                return 10;
            case 11:
                field = "The pit";
                addCash = -50;
                fieldTxt = Language.GetString("pit") + " " + Language.GetString("lose") + " " + Math.abs(addCash) + Language.GetString("gold");
                return 11;
            case 12:
                field = "Goldmine";
                addCash = 650;
                fieldTxt = Language.GetString("goldmine")+ " " + Language.GetString("gain") + " " + Math.abs(addCash) + Language.GetString("gold");
                return 12;
        }
        return 0;
    }

}
