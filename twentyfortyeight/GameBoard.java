package org.cis1200.twentyfortyeight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameBoard extends JPanel {

    private final TwentyFortyEight game; // model for the game
    private final JButton newGame;
    //private JButton undo;
    private final JButton tryAgain;

    // Game constants
    public static final int GAME_HEIGHT = 111;
    public static final int BOARD_WIDTH = 888;
    public static final int BOARD_HEIGHT = 888;
    public static final String FONT = "ComicSans";

    /**
     * Initializes the game board.
     */
    public GameBoard(int rows, int columns) {

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        game = new TwentyFortyEight(rows, columns);
        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                move(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        newGame = new JButton("New Game");
        newGame.setVisible(true);
        setLayout(null);
        newGame.setBounds(420, 22, 150, 45);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                //game.boardList.add(game.board);
            }
        });
        add(newGame);

        /*undo = new JButton("Undo");
        undo.setVisible(true);
        undo.setBounds(420, 22, 150, 45);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        add(undo); */

        // creating and setting the try again button whenever player wins/loses
        tryAgain = new JButton("Try Again");
        tryAgain.setVisible(false);
        tryAgain.setBounds(384, GAME_HEIGHT + 300, 150, 45);
        tryAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        add(tryAgain);


    }

    /**
     * Loads the game from the game state file if a previous game exists
     */
    public void load() {
        try {
            game.load("game.txt");
        } catch (Exception e) {
            System.out.println("exception");
        }

        repaint();
        requestFocusInWindow();

    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        tryAgain.setVisible(false);
//        if (game.getScore() != 0) {
//            game.writeScoreTo("score.txt");
//        }

        game.reset("game.txt");

        repaint();
        requestFocusInWindow();
    }

    /*public void undo() {
        if(game.undo()){
            game.writeTo("game.txt");
            game.readHighScoreFrom("score.txt");
            repaint();
            requestFocusInWindow();
        }
    } */

    /**
     * Response to action event of keys being pressed on either WASD or arrow keys
     * to
     * shift game board
     *
     * @param e KeyEvent that will move the game board
     */
    public void move(KeyEvent e) {
        // check if game is over
        if (!game.checkLoss() && !game.checkWin()) {
            int keyCode = e.getKeyCode();
            boolean moved = false;
            if (keyCode == KeyEvent.VK_A) {
                moved = game.moveLeft();
            } else if (keyCode == KeyEvent.VK_D) {
                moved = game.moveRight();
            } else if (keyCode == KeyEvent.VK_W) {
                moved = game.moveUp();
            } else if (keyCode == KeyEvent.VK_S) {
                moved = game.moveDown();
            }

            //game.boardList.add(game.board);

            if (moved) {
                game.addSquare();
            }
            game.writeTo("game.txt");
            game.writeHighScoreTo("score.txt");
            repaint();
            requestFocusInWindow();
        }
    }

    public void paintEnd(Graphics x) {
        Graphics2D gfx = (Graphics2D) x;
        if (game.checkWin()) {
            gfx.setColor(new Color(255, 255, 204));
        } else {
            gfx.setColor(new Color(255, 204, 153));
        }

        gfx.fillRoundRect(0, GAME_HEIGHT, BOARD_WIDTH, BOARD_HEIGHT, 4, 4);
        gfx.setColor(Color.BLACK);
        gfx.setFont(new Font(FONT, Font.PLAIN, 60));
        if (game.checkLoss()) {
            StartPanel.drawString(
                    gfx, BOARD_WIDTH / 2, (BOARD_HEIGHT / 2) + GAME_HEIGHT, 50, "YOU LOSE"
            );
        } else if (game.checkWin()) {
            StartPanel.drawString(
                    gfx, BOARD_WIDTH / 2, (BOARD_HEIGHT / 2) + GAME_HEIGHT, 50, "YOU WIN"
            );
        }

        tryAgain.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D gfx = (Graphics2D) graphics;

        gfx.setColor(new Color(145, 200, 255));
        gfx.fillRoundRect(0, 0, BOARD_WIDTH, GAME_HEIGHT + BOARD_HEIGHT, 4, 4);

        // drawing score and best score labels
        gfx.setColor(Color.BLACK);
        gfx.setFont(new Font(GameBoard.FONT, Font.BOLD, 15));
        FontMetrics font = gfx.getFontMetrics();
        StartPanel.drawString(gfx, 300, 30, 50, "High Score: ");
        StartPanel.drawString(
                gfx, 360 + (font.stringWidth(game.getHighScore() + "") / 2),
                30, 50, "" + game.getHighScore()
        );
        StartPanel.drawString(gfx, 75, 30, 50, "Score: ");
        StartPanel.drawString(
                gfx, 100 + (font.stringWidth(game.getScore() + "") / 2),
                30, 50, "" + game.getScore()
        );

        int[][] intBoard = game.getValueBoard();

        for (int i = 0; i < intBoard.length; i++) {
            for (int j = 0; j < intBoard[0].length; j++) {
                int value = intBoard[i][j];

                int x = (j * 160) + ((j + 1) * 16);
                int y = (i * 160) + ((i + 1) * 16) + GAME_HEIGHT;

                if (value != 0) {
                    gfx.setColor(Square.getColor(intBoard[i][j]));
                    gfx.fillRoundRect(x, y, 160, 160, 4, 4);

                    gfx.setFont(new Font(FONT, Font.PLAIN, 60));
                    if (value == 4 || value == 2) {
                        gfx.setColor(new Color(126, 117, 108));
                    } else {
                        gfx.setColor(new Color(248, 247, 242));
                    }
                    StartPanel.drawString(gfx, x + 80, y + 80, 128, value + "");

                } else {
                    gfx.setColor(new Color(205, 193, 180));
                    gfx.fillRoundRect(x, y, 160, 160, 4, 4);
                }
            }
        }

        if (game.checkWin()) {
            paintEnd(gfx);
        } else if (game.checkLoss()) {
            paintEnd(gfx);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, GAME_HEIGHT + BOARD_HEIGHT);
    }
}
