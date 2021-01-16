import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel {
    private Timer timer;
    private String beginMessage = "Start!";
    private String endMessage = "Game over!";
    public Bandy bandy = new Bandy();
    private Image player;
    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter()); // Takes in input from keyboard
        setBackground(Color.BLACK); // Default background color
        setFocusable(true); // Allows for focus on mouse and keyboard on object

        // bandy.bandicoot

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.drawImage(bandy.bandicoot, bandy.getX(), bandy.getY(), this);
    }

//    private void loadImages(String bandy_path) { // Temporary Method to demonstrate working image loading capabilities
//        ImageIcon bandy = new ImageIcon(bandy_path);
//        player = bandy.getImage();
//    }

    private class TAdapter extends KeyAdapter {

    }

}

