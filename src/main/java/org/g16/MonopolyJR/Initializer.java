package org.g16.MonopolyJR;

public class Initializer {
    Field fields[] = new Field[24];

    public Field[] InitFields(){
        //field 0
        VisitorField start = new VisitorField(Language.GetString("start"));
        fields[0] = start;
        //field 1
        PropertyField burgerBaren = new PropertyField(Language.GetString("burger"), Color.Brown, 1);
        fields[1] = burgerBaren;
        //field 2
        PropertyField pizzaHuset = new PropertyField(Language.GetString("pizza"), Color.Brown, 1);
        fields[2] = pizzaHuset;
        //field 3
        ChanceField chance1 = new ChanceField(Language.GetString("chance"));
        fields[3] = chance1;
        //field 4
        PropertyField slikButik = new PropertyField(Language.GetString("candy"),Color.Blue, 1);
        fields[4] = slikButik;
        //field 5
        PropertyField isButik = new PropertyField(Language.GetString("ice"),Color.Blue,1 );
        fields[5] = isButik;
        //field 6
        VisitorField fængsel = new VisitorField("Fængsel");
        fields[6] = fængsel;
        //field 7
        PropertyField museum = new PropertyField(Language.GetString("museum"),Color.Pink, 2);
        fields[7] = museum;
        //field 8
        PropertyField biblotek = new PropertyField(Language.GetString("library"),Color.Pink,2 );
        fields[8] = biblotek;
        //field 9
        ChanceField chance2 = new ChanceField(Language.GetString("chance"));
        fields[9] = chance2;
        //field 10
        PropertyField skatePark = new PropertyField(Language.GetString("skate"),Color.Orange,2 );
        fields[10] = skatePark;
        //field 11
        PropertyField pool = new PropertyField(Language.GetString("pool"),Color.Orange,2 );
        fields[11] = pool;
        //field 12
        VisitorField parkering = new VisitorField("Parkering");
        fields[12] = parkering;
        //field 13
        PropertyField arcade = new PropertyField(Language.GetString("arcade"),Color.Red, 3);
        fields[13] = arcade;
        //field 14
        PropertyField biograf = new PropertyField(Language.GetString("cinema"),Color.Red,3);
        fields[14] = biograf;
        //field 15
        ChanceField chance3 = new ChanceField(Language.GetString("chance"));
        fields[15] = chance3;
        //field 16
        PropertyField legetøjsbutik = new PropertyField(Language.GetString("toystore"),Color.Yellow,3 );
        fields[16] = legetøjsbutik;
        //field 17
        PropertyField dyrehandel = new PropertyField(Language.GetString("petstore"),Color.Yellow, 3);
        fields[17] = dyrehandel;
        //field 18
        GoToJailField gåtilfængsel = new GoToJailField("Gå til fængsel");
        fields[18] = gåtilfængsel;
        //field 19
        PropertyField bowlinghal = new PropertyField(Language.GetString("bowling"),Color.Green, 4);
        fields[19] = bowlinghal;
        //field 20
        PropertyField zoo = new PropertyField(Language.GetString("zoo"),Color.Green, 4);
        fields[20] = zoo;
        //field 21
        ChanceField chance4 = new ChanceField(Language.GetString("chance"));
        fields[21] = chance4;
        //field 22
        PropertyField strandpromenade = new PropertyField(Language.GetString("beach"),Color.DarkBlue, 5);
        fields[22] = strandpromenade;
        //field 23
        PropertyField vandpark = new PropertyField(Language.GetString("waterpark"),Color.DarkBlue, 5);
        fields[23] = vandpark;
        return fields;
    }
}
