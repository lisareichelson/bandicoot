import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel {
    private Timer timer;
    private String beginMessage = "Start!";
    private String endMessage = "Game over!";
    public Bandy bandy = new Bandy();
    private Image player;
    public Exit button = new Exit();
    private int timeDelay = 50;
    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter()); // Takes in input from keyboard
        setBackground(Color.BLACK); // Default background color
        setFocusable(true); // Allows for focus on mouse and keyboard on object

        initGame();

    }

    private void initGame() {
        timer = new Timer(timeDelay, new GameCycle());
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }

    }

    private void doDrawing(Graphics g) {
        g.drawImage(bandy.bandicoot, bandy.getX(), bandy.getY(), this);
        g.drawImage(button.exitbutton, button.getX(), button.getY(), this);
    }

    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            bandy.checkForMotion(e);
            button.checkForClick(e);
        }
    }

}

