package org.g16.MonopolyJR;

public abstract class Field {
    int playerPos;

    //playerPos = fields[]
    protected String name;

    public Field(String name){
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int fieldGet(int dieCount) {
        switch (playerPos) {
            case 0:
                //start
                //add 2M
                return 0;
            case 1:

                return 1;
            case 2:

                return 2;
            case 3:
                //chance
                return 3;
            case 4:

                return 4;
            case 5:

                return 5;
            case 6:
                //jail
                return 6;
            case 7:

                return 7;
            case 8:

                return 8;
            case 9:
                //chance
                return 9;
            case 10:

                return 10;
            case 11:

                return 11;
            case 12:
                //Parking
                return 12;
            case 13:

                return 13;
            case 14:

                return 14;
            case 15:
                //chance
                return 15;
            case 16:

                return 16;
            case 17:

                return 17;
            case 18:
                //gotojail
                return 18;
            case 19:

                return 19;
            case 20:

                return 20;
            case 21:
                //chance
                return 21;
            case 22:

                return 22;
            case 23:

                return 23;
        }
        return 0;
    }
}
