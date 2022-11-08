package org.g16.GUI;
import gui_main.GUI;
import org.g16.MonopolyJR.*;

import java.util.Scanner;

public class GUITest {


    public static void main(String[] args) {


       MonopolyGUI mpg = new MonopolyGUI();
        mpg.initGUI(
                new Field[]{
                        new VisitorField(),new Field(),new Field(),new Field(),new Field(),
                        new Field(),new GoToJailField(),new PropertyField(),new PropertyField(),new Field(),
                        new Field(),new VisitorField(),new ChanceField(),new Field(),new Field(),
                        new Field(),new PropertyField(),new PropertyField(),new Field(),new Field(),
                        new Field(),new Field(),new Field(),new Field()


                }
                );


        while(true){
            mpg.PromptThrowDice(0);
            mpg.DrawDie(4,2);
            mpg.DrawChanceCard("Du skal i fængsel!!!");
        }

}
}
