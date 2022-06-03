package userInterface;

import game.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.Timer;


import javax.swing.*;

/**
 *
 * @author kwhiting
 */
public class ScraggleUi
{
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem exit;
    private JMenuItem newGame;

    // Scraggle board
    private JPanel scragglePanel;
    private JButton[][] diceButtons;

    // Enter found words
    private JPanel wordsPanel;
    private JScrollPane scrollPane;
    private JTextPane wordsArea;

    // time label
    private JLabel timeLabel;
    private JButton shakeDice;

    // Enter current word
    private JPanel currentPanel;
    private JLabel currentLabel;
    private JButton currentSubmit;

    // player's score
    private JLabel scoreLabel;

    Game game;
    int score;

    private final int GRID = 4;
    private ArrayList<String> foundWords;
   // private ArrayList<String> foundWords = new ArrayList<String>;
    private final int MAX_INDEX = 3;
    private final String PLUS = "+";
    private final static String MINUS = "-";
    private ResetGameListener resetGameListener;
    private Timer timer;
    private int minutes = 3;
    private int seconds = 0;


    public ScraggleUi(Game inGame)
    {
        game = inGame;
        initObjects();
        setupTimer();
        initComponents();
    }
    private void initObjects()
    {
        resetGameListener = new ResetGameListener();
        foundWords = new ArrayList();
    }
    //sets speed
    private void setupTimer()
    {
        timer = new Timer(1000, new TimerListener());
        timer.start();
    }
    //updates text area
    private void updateTextArea(String data)
    {
        wordsArea.setText(wordsArea.getText() + "\n" + data);
        wordsArea.setCaretPosition(wordsArea.getDocument().getLength());
    }
    private void initComponents()
    {
        // Initialize the JFrame
        frame = new JFrame("Scraggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(660, 500);

        // Initialize the JMenuBar and add to the JFrame
        createMenu();

        // Initialize the JPane for the current word
        setupCurrentPanel();

        // Initialize the JPanel for the word entry
        setupWordPanel();

        // Initialize the JPanel for the Scraggle dice
        setupScragglePanel();

        // Add everything to the JFrame
        frame.setJMenuBar(menuBar);
        frame.add(scragglePanel, BorderLayout.WEST);
        frame.add(wordsPanel, BorderLayout.CENTER);
        frame.add(currentPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    //creates the menu
    private void createMenu()
    {
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Scraggle");
        gameMenu.setMnemonic('S');

        newGame = new JMenuItem("New Game");
        newGame.setMnemonic('N');
        newGame.addActionListener(new ResetGameListener());

        exit = new JMenuItem("Exit");
        exit.setMnemonic('E');
        exit.addActionListener(new ExitListener());

        gameMenu.add(newGame);
        gameMenu.add(exit);

        menuBar.add(gameMenu);
    }

    //sets up current panel
    private void setupCurrentPanel()
    {
        currentPanel = new JPanel();
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));

        currentLabel = new JLabel();
        currentLabel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        currentLabel.setMinimumSize(new Dimension(300, 50));
        currentLabel.setPreferredSize(new Dimension(300,50));
        currentLabel.setHorizontalAlignment(SwingConstants.LEFT);

        currentSubmit = new JButton("Submit Word");
        currentSubmit.setMinimumSize(new Dimension(200, 100));
        currentSubmit.setPreferredSize(new Dimension(200, 50));
        currentSubmit.addActionListener(new SubmitListener());
        //currentSubmit.addActionListener(new ResetGameListener());

        scoreLabel = new JLabel();
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
        scoreLabel.setMinimumSize(new Dimension(100, 50));
        scoreLabel.setPreferredSize(new Dimension(100,50));

        currentPanel.add(currentLabel);
        currentPanel.add(currentSubmit);
        currentPanel.add(scoreLabel);
    }
    
    //sets up word panel
    private void setupWordPanel()
    {
        wordsPanel = new JPanel();
        wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.Y_AXIS));
        wordsPanel.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));

        wordsArea = new JTextPane();
        scrollPane = new JScrollPane(wordsArea);
        scrollPane.setPreferredSize(new Dimension(180, 330));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        timeLabel = new JLabel("3:00");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        timeLabel.setPreferredSize(new Dimension(240, 100));
        timeLabel.setMinimumSize(new Dimension(240, 100));
        timeLabel.setMaximumSize(new Dimension(240, 100));
        timeLabel.setBorder(BorderFactory.createTitledBorder("Time Left"));

        shakeDice = new JButton("Shake Dice");
        shakeDice.setPreferredSize(new Dimension(240, 100));
        shakeDice.setMinimumSize(new Dimension(240, 100));
        shakeDice.setMaximumSize(new Dimension(240, 100));
        shakeDice.addActionListener(new ResetGameListener());

        wordsPanel.add(scrollPane);
        wordsPanel.add(timeLabel);
        wordsPanel.add(shakeDice);
    }

    //sets up scraggle panel
    private void setupScragglePanel()
    {
        TileListener tileListener = new TileListener();
        LetterListener letterListener = new LetterListener();
        
        scragglePanel = new JPanel();
        scragglePanel.setLayout(new GridLayout(4, 4));
        scragglePanel.setMinimumSize(new Dimension(400, 400));
        scragglePanel.setPreferredSize(new Dimension(400, 400));
        scragglePanel.setBorder(BorderFactory.createTitledBorder("Scraggle Board"));

        diceButtons = new JButton[GRID][GRID];

        //gets letters
        for(int row = 0; row < GRID; row++)
            for(int col = 0; col < GRID; col++)
        {
            URL imgURL = getClass().getResource(game.getGrid()[row][col].getImgPath());
            ImageIcon icon = new ImageIcon(imgURL);
            diceButtons[row][col] = new JButton(icon);
            diceButtons[row][col].putClientProperty("row", row);
            diceButtons[row][col].putClientProperty("col", col);
            diceButtons[row][col].putClientProperty("letter", game.getGrid()[row][col].getLetter());
            diceButtons[row][col].addActionListener(tileListener);
            diceButtons[row][col].addActionListener(letterListener);
            scragglePanel.add(diceButtons[row][col]);
        }
    }
    //waits for exit command
    private class ExitListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
           int response = JOptionPane.showConfirmDialog(frame, "Confirm to exit Scraggle?",
                    "Exit?", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
        }
        
    }
    //waits for reset command
    private class ResetGameListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            score = 0;
            game.populateGrid();
            frame.remove(scragglePanel);
            scragglePanel.removeAll();
            setupScragglePanel();
            scragglePanel.revalidate();
            scragglePanel.repaint();
            frame.add(scragglePanel, BorderLayout.WEST);
            foundWords.removeAll(foundWords);
            foundWords.clear();
            timer.stop();
            minutes = 3;
            seconds = 0;
            timer.start();
            score = 0;
            
        }
        
    }
    //waits for submit command
    private class SubmitListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int wordScore = game.getDictionary().search(currentLabel.getText().toLowerCase());
            if(foundWords.contains(currentLabel.getText().toLowerCase()))
                JOptionPane.showMessageDialog(frame, "Word found already");
            else
            {
                if(wordScore > 0)
                {
                    updateTextArea(currentLabel.getText());
                    foundWords.add(currentLabel.getText().toLowerCase());
                    score += wordScore;
                    scoreLabel.setText(String.valueOf(score));
                }
            }
            currentLabel.setText("");
            for(int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    diceButtons[i][j].setEnabled(true);
                }
            }
        }
    
    }
    //waits to recieve each letter
    private class LetterListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource() instanceof JButton)
            {
                JButton button = (JButton)ae.getSource();
                String letter = (String)button.getClientProperty("letter");
                currentLabel.setText(currentLabel.getText() + letter);
               
            }
        }
    
    }
    //waits for each tile button
    private class TileListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource() instanceof JButton)
            {
                JButton button = (JButton)ae.getSource();
                int row = (int)button.getClientProperty("row");
                int col = (int)button.getClientProperty("col");
                
                for(int i = 0; i < 4; i++)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        diceButtons[i][j].setEnabled(false);
                    }
                }
                if(row == 0 && col == 0)
                {
                    diceButtons[row+1][col+1].setEnabled(true);
                    diceButtons[row+1][col].setEnabled(true);
                    diceButtons[row][col+1].setEnabled(true);
                }
                else if(row == 0 && (col != 0 && col != 3))
                {
                    diceButtons[row+1][col+1].setEnabled(true);
                    diceButtons[row+1][col].setEnabled(true);
                    diceButtons[row][col+1].setEnabled(true);
                    diceButtons[row][col-1].setEnabled(true);
                    diceButtons[row+1][col-1].setEnabled(true);
                }
                else if(row == 3 && col == 3)
                {
                    diceButtons[row-1][col-1].setEnabled(true);
                    diceButtons[row-1][col].setEnabled(true);
                    diceButtons[row][col-1].setEnabled(true);
                }
                else if(col == 0 && (row != 0 && row != 3))
                {
                    diceButtons[row-1][col+1].setEnabled(true);
                    diceButtons[row-1][col].setEnabled(true);
                    diceButtons[row+1][col].setEnabled(true);
                    diceButtons[row][col+1].setEnabled(true);
                    diceButtons[row+1][col+1].setEnabled(true);   
                }
                else if(row == 3 && (col != 0 && col != 3))
                {
                    diceButtons[row-1][col-1].setEnabled(true);
                    diceButtons[row-1][col].setEnabled(true);
                    diceButtons[row][col-1].setEnabled(true);
                    diceButtons[row][col+1].setEnabled(true);
                    diceButtons[row-1][col+1].setEnabled(true);
                }
                else if(col == 3 && (row != 0 && row != 3))
                {
                    diceButtons[row+1][col-1].setEnabled(true);
                    diceButtons[row+1][col].setEnabled(true);
                    diceButtons[row-1][col].setEnabled(true);
                    diceButtons[row][col-1].setEnabled(true);
                    diceButtons[row-1][col-1].setEnabled(true);
                }
                else if(row == 0 && col == 3)
                {
                    diceButtons[row+1][col].setEnabled(true);
                    diceButtons[row][col-1].setEnabled(true);
                    diceButtons[row+1][col-1].setEnabled(true);
                }
                else if(row == 3 && col == 0)
                {
                    diceButtons[row-1][col].setEnabled(true);
                    diceButtons[row-1][col+1].setEnabled(true);
                    diceButtons[row][col+1].setEnabled(true);
                }
                else
                {
                    diceButtons[row+1][col].setEnabled(true);
                    diceButtons[row+1][col-1].setEnabled(true);
                    diceButtons[row+1][col+1].setEnabled(true);
                    diceButtons[row][col+1].setEnabled(true);
                    diceButtons[row-1][col+1].setEnabled(true);
                    diceButtons[row-1][col].setEnabled(true);
                    diceButtons[row-1][col-1].setEnabled(true);
                    diceButtons[row][col-1].setEnabled(true);
                }
                    
            }
                
        }
        
    }
    //counts down
    private class TimerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(seconds == 0 && minutes == 0)
            {              
                timer.stop();
            }
            else
            {
                if(seconds == 0)
                {
                    seconds = 59;
                    minutes--;
                }
                else
                {
                    seconds--;
                }
            }
                        if(seconds < 10)
            {
                String strSeconds = "0" + String.valueOf(seconds);
                timeLabel.setText(String.valueOf(minutes) + ":" + strSeconds);
            }
            else
            {
                timeLabel.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }
            if (seconds == 0 && minutes == 0)
            {
                int gameover = JOptionPane.showConfirmDialog(frame, "Timer ended, would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if(gameover == JOptionPane.YES_OPTION)
                {
                
                }
                else
                    System.exit(0);
            }
        }
    }
}