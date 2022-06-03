/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.LinkedHashMap;
import java.util.Map;
import model.WordResult;

/**
 *
 * @author Thomas Barone
 * Karin Whiting
 * July 1st, 2021
 */
public class UserResult {
    private final Map<String, WordResult> wordToResultMap = new LinkedHashMap<>();
    private int totalScore = 0;
    
    //adds the result score to the total score
    public void add(String word, WordResult result)
    {
        this.wordToResultMap.put(word, result);
        this.totalScore += result.getScore();
    }
    //gets the word result
    public WordResult get(String word)
    {
        return this.wordToResultMap.get(word);
    }
    //shows result of map
    public Map<String, WordResult> all()
    {
        return this.wordToResultMap;
    }
    //returns a boolean of key word
    public boolean exist(String word)
    {
        return this.wordToResultMap.containsKey(word);
    }
    //gets total score
    public int getTotalScore()
    {
        return this.totalScore;
    }
    //gets wordcount
    public int getWordCount()
    {
        return this.wordToResultMap.size();
    }
}
