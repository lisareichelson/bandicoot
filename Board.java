import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel {
    private Timer timer;
    public Bandy bandy = new Bandy();
    private Image player;
    public Exit button = new Exit();
    public Rock stone = new Rock();
    private int timeDelay = 50;
    private int level = 1;
    private int score = 0;

    //temporary, replace both with an arraylist
    public Bug[] bugs = {new Bug(), new Bug(), new Bug(), new Bug(), new Bug(),
            new Bug(), new Bug(), new Bug(), new Bug(), new Bug(),
            new Bug(), new Bug(), new Bug(), new Bug(), new Bug(),
            new Bug(), new Bug(), new Bug(), new Bug(), new Bug(),
            new Bug(), new Bug(), new Bug(), new Bug(), new Bug(),
            new Bug(), new Bug()};

    public Rock[] rocks = {new Rock(), new Rock(), new Rock()};


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
            checkCollision();
        }
    }

    private void doDrawing(Graphics g) {
        //Draws bandicoot and exit button at start of game
        g.drawImage(bandy.bandicoot, bandy.getX(), bandy.getY(), this);
        g.drawImage(button.exitbutton, button.getX(), button.getY(), this);

        //Draws bug continously as game runs
        for (Bug r : bugs) {
            g.drawImage(r.ladybug, r.getX(), r.getY(), this);
            r.update();
        }

        //Draws rock continously as game runs
        for (Rock s : rocks) {
            g.drawImage(s.rock, s.getX(), s.getY(), this);
            s.update();
        }
    }

    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            bandy.checkForMotion(e);
            button.checkForClick(e);
        }
    }

    public void checkCollision() {
        //Checks for BUG collision
        Rectangle bandBound = bandy.getBounds();
        for (Bug r : bugs) {
            Rectangle bugBound = r.getBounds();
            if (bandBound.intersects(bugBound)) {
                //remove from arraylist
                score++;
            }
        }

        //checks for ROCK collision
        for( Rock r : rocks){
            Rectangle rockBound = r.getBounds();
            if(bandBound.intersects(rockBound)){
                //remove rock from arraylist
                score--; //run away, game over?
            }
        }
    }


}



