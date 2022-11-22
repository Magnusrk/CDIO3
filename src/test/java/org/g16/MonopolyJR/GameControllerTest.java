package org.g16.MonopolyJR;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameControllerTest {
    Player[] players;
    @Test
    public void testSetStartingBalance4Players() {
        int[] ages = {1,2,3,4};
        String[] names = {"p1","p2","p3","p4"};
        createPlayers(ages,names);
        setStartingBalance();
        assertEquals(16,players[0].getPlayerBalance());
        assertEquals(16,players[1].getPlayerBalance());
        assertEquals(16,players[2].getPlayerBalance());
        assertEquals(16,players[3].getPlayerBalance());
        
    }
    @Test
    public void testSetStartingBalance3Players() {
        int[] ages = {1,2,3};
        String[] names = {"p1","p2","p3"};
        createPlayers(ages,names);
        setStartingBalance();
        assertEquals(18,players[0].getPlayerBalance());
        assertEquals(18,players[1].getPlayerBalance());
        assertEquals(18,players[2].getPlayerBalance());

    }
    @Test
    public void testSetStartingBalance2Players() {
        int[] ages = {1,2};
        String[] names = {"p1","p2"};
        createPlayers(ages,names);
        setStartingBalance();
        assertEquals(20,players[0].getPlayerBalance());
        assertEquals(20,players[1].getPlayerBalance());
    }

    @Test
    public void testYoungestPlayerStarts(){
        int[] ages = {11,15,30,31};
        String[] names = {"p1","p2","p3","p4"};
        createPlayers(ages,names);
        assertEquals(0,createPlayers(ages,names));
    }

    public int createPlayers(int[] ages, String[] names){
        players = new Player[ages.length];
        Token[] tokens = new Token[]{Token.Car,Token.Tractor,Token.Racecar,Token.Ufo};
        for(int i = 0; i < ages.length; i++){
            players[i] = new Player(tokens[i]);
            players[i].setAge(ages[i]);
            players[i].setID(i);
            players[i].setName(names[i]);
        }

        int firstTurnIndex = 0;
        int youngestAge = Integer.MAX_VALUE;
        for(int n = 0; n < players.length; n++){
            int playerAge = players[n].getAge();
            if(playerAge < youngestAge){
                firstTurnIndex = n;
                youngestAge = playerAge;
            }
        }
        //Start with the youngest lad
        return firstTurnIndex;
    }
    public void setStartingBalance(){
        int playerCount = players.length;
        for (int i = 0; i<players.length; i++){
            players[i].setBalance(24-(2*playerCount));
        }
    }
}