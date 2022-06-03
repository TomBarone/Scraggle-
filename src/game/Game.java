package game;

import dictionary.Alphabet;
import dictionary.Dictionary;
import model.GridPoint;
import model.GridUnit;

/**
 *
 * @author Thomas Barone
 * Karin Whiting
 * July 1st, 2021
 */
public class Game 
{
    public final GridUnit[][] grid;  
    private Dictionary dictionary;
//making the grid
    public Game(Dictionary dictionary) 
    {
        this.dictionary = dictionary;
        this.grid = new GridUnit[4][4];
        this.populateGrid();
    }
    //returns the grid
    public GridUnit[][] getGrid() 
    {
        return grid;
    }
    //returns the point of a grid
    public GridUnit getGridUnit(GridPoint point) 
    {
        return grid[point.x][point.y];
    }
    //populates the grid
    public void populateGrid() 
    {
        for (int i = 0; i < 4; ++i) 
        {
            for (int j = 0; j < 4; ++j) 
            {
                grid[i][j] = new GridUnit(Alphabet.newRandom(), new GridPoint(i, j));
            }
        }
    }
    //prints grid
    public void displayGrid()
    {
        System.out.println("-------------------------");
        for (int i = 0; i < 4; ++i) 
        {
            System.out.print("|  ");
            
            for (int j = 0; j < 4; ++j) 
            {
                System.out.print(grid[i][j].getLetter());
                System.out.print("  |  ");
            }
            
            System.out.println("\n-------------------------");
        }        
    }

    /**
     * @return the dictionary
     */
    public Dictionary getDictionary() {
        return dictionary;
    }
}

