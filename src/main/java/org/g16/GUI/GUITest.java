package org.g16.GUI;
import gui_main.GUI;
import org.g16.MonopolyJR.Field;

public class GUITest {


    public static void main(String[] args) {
        MonopolyGUI mpg = new MonopolyGUI();
        mpg.initGUI(
                new Field[]{
                        new Field(),new Field(),new Field(),new Field(),new Field(),
                        new Field(),new Field(),new Field(),new Field(),new Field(),
                        new Field(),new Field(),new Field(),new Field(),new Field(),
                        new Field(),new Field(),new Field(),new Field(),new Field(),
                        new Field(),new Field(),new Field(),new Field()


                }
                );
}
}
