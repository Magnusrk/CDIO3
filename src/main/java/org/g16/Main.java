package org.g16;

import org.g16.MonopolyJR.*;

public class Main {
    public static void main(String[] args) {
        playGame();
    }

    public static void playGame(){
        GameController game = new GameController();
        game.play();
        boolean shouldRestart = game.askRestart();
        game.exitGame();
        if(shouldRestart){
            playGame();
        }
    }
}