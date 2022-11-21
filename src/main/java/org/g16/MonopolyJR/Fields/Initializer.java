package org.g16.MonopolyJR.Fields;

public class Initializer {
    Field[] fields = new Field[24];

    /**
     * Sets values for every field in the game
     * And adds them into an array
     * @return array with fields
     */
    public Field[] InitFields(){
        //field 0
        VisitorField start = new VisitorField("start");
        fields[0] = start;
        //field 1
        PropertyField burgerBaren = new PropertyField("burger", Color.Brown, 1);
        fields[1] = burgerBaren;
        //field 2
        PropertyField pizzaHuset = new PropertyField("pizza", Color.Brown, 1);
        fields[2] = pizzaHuset;
        //field 3
        ChanceField chance1 = new ChanceField("chance");
        fields[3] = chance1;
        //field 4
        PropertyField slikButik = new PropertyField("candy",Color.Blue, 1);
        fields[4] = slikButik;
        //field 5
        PropertyField isButik = new PropertyField("ice",Color.Blue,1 );
        fields[5] = isButik;
        //field 6
        Jail fængsel = new Jail("prison");
        fields[6] = fængsel;
        //field 7
        PropertyField museum = new PropertyField("museum",Color.Pink, 2);
        fields[7] = museum;
        //field 8
        PropertyField bibliotek = new PropertyField("library",Color.Pink,2 );
        fields[8] = bibliotek;
        //field 9
        ChanceField chance2 = new ChanceField("chance");
        fields[9] = chance2;
        //field 10
        PropertyField skatePark = new PropertyField("skate",Color.Orange,2 );
        fields[10] = skatePark;
        //field 11
        PropertyField pool = new PropertyField("pool",Color.Orange,2 );
        fields[11] = pool;
        //field 12
        VisitorField parkering = new VisitorField("parking");
        fields[12] = parkering;
        //field 13
        PropertyField arcade = new PropertyField("arcade",Color.Red, 3);
        fields[13] = arcade;
        //field 14
        PropertyField biograf = new PropertyField("cinema",Color.Red,3);
        fields[14] = biograf;
        //field 15
        ChanceField chance3 = new ChanceField("chance");
        fields[15] = chance3;
        //field 16
        PropertyField legetøjsbutik = new PropertyField("toystore",Color.Yellow,3 );
        fields[16] = legetøjsbutik;
        //field 17
        PropertyField dyrehandel = new PropertyField("petstore",Color.Yellow, 3);
        fields[17] = dyrehandel;
        //field 18
        GoToJailField gåtilfængsel = new GoToJailField("gotojail");
        fields[18] = gåtilfængsel;
        //field 19
        PropertyField bowlinghal = new PropertyField("bowling",Color.Green, 4);
        fields[19] = bowlinghal;
        //field 20
        PropertyField zoo = new PropertyField("zoo",Color.Green, 4);
        fields[20] = zoo;
        //field 21
        ChanceField chance4 = new ChanceField("chance");
        fields[21] = chance4;
        //field 22
        PropertyField vandpark = new PropertyField("waterpark",Color.DarkBlue, 5);
        fields[22] = vandpark;
        //field 23
        PropertyField strandpromenade = new PropertyField("beach",Color.DarkBlue, 5);
        fields[23] = strandpromenade;
        return fields;
    }
}
