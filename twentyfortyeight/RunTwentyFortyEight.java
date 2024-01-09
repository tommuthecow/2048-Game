package org.cis1200.twentyfortyeight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * <p>
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * <p>
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunTwentyFortyEight implements Runnable {
    public void run() {
        GameBoard game = new GameBoard(4, 4);
        StartPanel start = new StartPanel();

        JFrame frame = new JFrame("2048");
        frame.setSize(GameBoard.BOARD_WIDTH + 16, StartPanel.START_HEIGHT + 39);
        frame.add(start, BorderLayout.CENTER);

        JButton load = new JButton("Load Game");
        load.setVisible(true);
        load.setBounds(519, 480, 150, 50);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame("2048");
                frame.setSize(GameBoard.BOARD_WIDTH + 16, StartPanel.START_HEIGHT + 39);
                jFrame.add(game, BorderLayout.CENTER);
                game.load();
                frame.setVisible(false);
                jFrame.setVisible(true);
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        start.add(load);

        JButton newGame = new JButton("New Game");
        newGame.setVisible(true);
        start.setLayout(null);
        newGame.setBounds(189, 480, 150, 50);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame("2048");
                frame.setSize(GameBoard.BOARD_WIDTH + 16, StartPanel.START_HEIGHT + 39);
                jFrame.add(game, BorderLayout.CENTER);
                game.reset();
                frame.setVisible(false);
                jFrame.setVisible(true);
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        start.add(newGame);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}