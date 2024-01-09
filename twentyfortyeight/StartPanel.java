package org.cis1200.twentyfortyeight;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    public static final int START_HEIGHT = GameBoard.GAME_HEIGHT + GameBoard.BOARD_HEIGHT;

    public StartPanel() {
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gfx = (Graphics2D) g;

        gfx.setColor(new Color(255, 153, 204));
        gfx.fillRoundRect(0, 0, GameBoard.BOARD_WIDTH, START_HEIGHT, 4, 4);

        gfx.setColor(Color.BLACK);
        gfx.setFont(new Font(GameBoard.FONT, Font.BOLD, 60));
        drawString(gfx, GameBoard.BOARD_WIDTH / 2, 150, 10, "2048");

        gfx.setFont(new Font(GameBoard.FONT, Font.BOLD, 24));
        gfx.setFont(new Font(GameBoard.FONT, Font.CENTER_BASELINE, 20));
        drawString(
                gfx, GameBoard.BOARD_WIDTH / 2, 230, 15,
                "Instructions!"
        );

        drawString(
                gfx, GameBoard.BOARD_WIDTH / 2, 180, 15,
                "Combine tiles to make 2048!"
        );


        drawString(
                gfx, GameBoard.BOARD_WIDTH / 2, 260, 15,
                "W: slide tiles up"
        );

        drawString(
                gfx, GameBoard.BOARD_WIDTH / 2, 290, 15,
                "A: slide tiles left"
        );

        drawString(
                gfx, GameBoard.BOARD_WIDTH / 2, 320, 15,
                "S: slide tiles down"
        );

        drawString(
                gfx, GameBoard.BOARD_WIDTH / 2, 350, 15,
                "D: slide tiles right"
        );
    }

    public static void drawString(
            Graphics gfx, int x, int y, int height, String text
    ) {
        FontMetrics font = gfx.getFontMetrics();
        int a = x - (font.stringWidth(text) / 2);
        int b = y + ((height - font.getHeight()) / 2);
        gfx.drawString(text, a, b);

    }


}