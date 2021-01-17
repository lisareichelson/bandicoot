import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
* Version - 0.0.0
*
*
*
* Sources:
* -- Golden Bandicoot Image: http://animalia.bio/golden-bandicoot
* */

public class Application extends JFrame {
    public Application() {
        initUI();
    }

    private void initUI() {
        add(new Board());

        setSize(1000, 600); // Size on screen



        setTitle("Bandicoot Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Note: the () -> {} expression is a lambda operator in java
        // A lambda is basically a "spot-method". Takes in parameter (in this case 0 parameters)
        // and returns an output (in this case something of the class Application)

        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });

    }
}
