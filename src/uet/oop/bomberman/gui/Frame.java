package uet.oop.bomberman.gui;
import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public GamePanel gamepane;
    private final InfoPanel infoPanel;

    public Frame() {

        JPanel containerpane = new JPanel(new BorderLayout());
        gamepane = new GamePanel(this);
        infoPanel = new InfoPanel(gamepane.getGame());

        containerpane.add(infoPanel, BorderLayout.PAGE_START);
        containerpane.add(gamepane, BorderLayout.PAGE_END);

        Game game = gamepane.getGame();

        add(containerpane);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        game.start();
    }

    public void setTime(int time) {
        infoPanel.setTime(time);
    }

    public void setPoints(int points) {
        infoPanel.setPoints(points);
    }
}
