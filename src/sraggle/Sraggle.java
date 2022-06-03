/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sraggle;

import dictionary.Dictionary;
import game.Game;
import userInterface.ScraggleUi;

/**
 *
 * @author Thomas Barone
 * Karin Whiting
 * July 1st, 2021
 */
public class Sraggle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //calling the displayGrid function
        Dictionary dictionary = new Dictionary();
        Game game = new Game(dictionary);
        ScraggleUi ui = new ScraggleUi(game);
        game.displayGrid();
        
    }
    
}
