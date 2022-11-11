package org.g16.MonopolyJR;

public class Initializer {
    Field fields[] = new Field[24];

    public Field[] InitFields(){
        //field 0
        VisitorField start = new VisitorField();
        fields[0] = start;
        //field 1
        PropertyField burgerBaren = new PropertyField(Color.Brown, 1);
        fields[1] = burgerBaren;
        //field 2
        PropertyField pizzaHuset = new PropertyField(Color.Brown, 1);
        fields[2] = pizzaHuset;
        //field 3
        ChanceField chance1 = new ChanceField();
        fields[3] = chance1;
        //field 4
        PropertyField slikButik = new PropertyField(Color.Blue, 1);
        fields[4] = slikButik;
        //field 5
        PropertyField isButik = new PropertyField(Color.Blue,1 );
        fields[5] = isButik;
        //field 6
        VisitorField fængsel = new VisitorField();
        fields[6] = fængsel;
        //field 7
        PropertyField museum = new PropertyField(Color.Pink, 2);
        fields[7] = museum;
        //field 8
        PropertyField biblotek = new PropertyField(Color.Pink,2 );
        fields[8] = biblotek;
        //field 9
        ChanceField chance2 = new ChanceField();
        fields[9] = chance2;
        //field 10
        PropertyField skatePark = new PropertyField(Color.Orange,2 );
        fields[10] = skatePark;
        //field 11
        PropertyField pool = new PropertyField(Color.Orange,2 );
        fields[11] = pool;
        //field 12
        VisitorField parkering = new VisitorField();
        fields[12] = parkering;
        //field 13
        PropertyField arcade = new PropertyField(Color.Red, 3);
        fields[13] = arcade;
        //field 14
        PropertyField biograf = new PropertyField(Color.Red,3);
        fields[14] = biograf;
        //field 15
        ChanceField chance3 = new ChanceField();
        fields[15] = chance3;
        //field 16
        PropertyField legetøjsbutik = new PropertyField(Color.Yellow,3 );
        fields[16] = legetøjsbutik;
        //field 17
        PropertyField dyrehandel = new PropertyField(Color.Yellow, 3);
        fields[17] = dyrehandel;
        //field 18
        GoToJailField gåtilfængsel = new GoToJailField();
        fields[18] = gåtilfængsel;
        //field 19
        PropertyField bowlinghal = new PropertyField(Color.Green, 4);
        fields[19] = bowlinghal;
        //field 20
        PropertyField zoo = new PropertyField(Color.Green, 4);
        fields[20] = zoo;
        //field 21
        ChanceField chance4 = new ChanceField();
        fields[21] = chance4;
        //field 22
        PropertyField strandpromenade = new PropertyField(Color.DarkBlue, 5);
        fields[22] = strandpromenade;
        //field 23
        PropertyField vandpark = new PropertyField(Color.DarkBlue, 5);
        fields[23] = vandpark;
        return fields;

    }
}
