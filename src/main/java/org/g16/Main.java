package org.g16;

import org.g16.MonopolyJR.*;

public class Main {
    public static void main(String[] args) {
        playGame();
    }

    public static void playGame(){
        GameController game = new GameController();
        game.play();
        System.out.println("TEST TEST TEST TEST");
        boolean shouldRestart = game.askRestart();
        game.exitGame();
        if(shouldRestart){
            playGame();
        }
    }
}