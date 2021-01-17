import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import javax.sound.sampled.*;



public class Board extends JPanel {
    private Timer timer;
    public Bandy bandy = new Bandy();
    public Exit button = new Exit();
    public GoldenBug gold = new GoldenBug();
    boolean gameOver = false;
    boolean gameWon = false;
    boolean pause = false;
    boolean start = false;
    private int scorex = 5;
    private int scorey = 20;
    private int timeDelay = 50;
    private double normMeanBug = 1.5;
    private double normMeanRock = 5.0;
    private long rockTime = System.nanoTime();
    private long bugTime = System.nanoTime();
    private double nextBugTime = timeNextBug(normMeanBug);
    private double nextRockTime = timeNextRock(normMeanRock);
    private int level = 0;
    private int bugCatch = 4;
    private int score = 0;
    private ArrayList<Bug> bugs = createBugArrayList();
    private ArrayList<Rock> rocks = createRockArrayList();

    private ArrayList<Rock> createRockArrayList() {
        ArrayList<Rock> thisArrayList = new ArrayList<Rock>();
        for (int i = 0; i < 3; i++) {
            thisArrayList.add(new Rock());
        }
        return thisArrayList;
    }

    private ArrayList<Bug> createBugArrayList() {
        ArrayList<Bug> thisArrayList = new ArrayList<Bug>();
        for (int i = 0; i < 10; i++) {
            thisArrayList.add(new Bug());
        }
        return thisArrayList;
    }

    private double timeNextBug(double normMean) {
        Random num = new Random();
        double returnNum;
        returnNum = num.nextGaussian();
        returnNum /= 2;
        returnNum += normMean;
        if (returnNum <= 0) {
            returnNum = 0;
        }
        return returnNum;
    }

    private long elapsTime(long startTime, long currTime) {
        return (currTime - startTime);
    }

    private void addBug() {
        long currTime = System.nanoTime();
        if (elapsTime(bugTime, currTime) >= nextBugTime*1000000000L) {
            bugs.add(new Bug());
            nextBugTime = timeNextBug(normMeanBug);
            bugTime = currTime;
        }
    }
    private void addRock() {
        long currTime = System.nanoTime();
        if (elapsTime(rockTime, currTime) >= nextRockTime*1000000000L) {
            rocks.add(new Rock());
            nextRockTime = timeNextRock(normMeanRock);
            rockTime = currTime;
        }
    }

    private double timeNextRock(double normMean) {
        Random num = new Random();
        double returnNum;
        returnNum = num.nextGaussian();
        returnNum /= 2;
        returnNum += normMean;
        if (returnNum <= 0) {
            returnNum = 0;
        }
        return returnNum;
    }

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter()); // Takes in input from keyboard
        setBackground(Color.BLACK); // Default background color
        setFocusable(true); // Allows for focus on mouse and keyboard on object

        initGame();

    }

    private void updateLevel() {
        level = (int) (score)/(int) (bugCatch);
    }

    private void initGame() {
        timer = new Timer(timeDelay, new GameCycle());
        timer.start();
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!(pause)) {
                repaint();
                checkCollision();
                addRock();
                addBug();
                updateLevel();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!(start))
            drawStartScreen(g);
        else if (pause)
            drawPauseScreen(g);
        else if (!(gameOver))
            doDrawing(g);
        else if (gameOver && !(gameWon))
            drawLoseScreen(g);
        else if (gameOver && gameWon)
            drawWinScreen(g);
        else {
            System.out.println("Something went wrong...");
            doDrawing(g);
        }

    }

//    private class AudioPlayer1 implements LineListener {
//        void play(String audioPath) {
//            File audioFile = new File(audioPath);
//            try {
//                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
//                AudioFormat format = audioStream.getFormat();
//                DataLine.Info info = new DataLine.Info(Clip.class, format);
//                Clip audioClip = (Clip) AudioSystem.getLine(info);
//                audioClip.addLineListener(this);
//                audioClip.open(audioStream);
//                audioClip.start()
//            }
//            catch (Exception e) {
//                System.out.println("Error of some sort...");
//            }
//
//        }
//    }


    private void drawPauseScreen(Graphics g) {
        g.setColor(Color.WHITE);


        g.setFont(new Font("Cooper Black", Font.ITALIC, 50));
        g.drawString("Your game is PAUSED", 225, 50);
        g.setFont(new Font("Cooper Black", Font.PLAIN, 30));
        g.drawString("Press [SPACE] to Resume", 325, 90);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Cooper Black", Font.BOLD, 25));
        g.drawString("1000 pts  Increase speed  Current Speed:     " + bandy.getSpeed(), 50, 200);
        g.setColor(Color.BLUE);
//        g.drawString("Press 's'", 75, 200);
        g.drawString("Press 's'", 800, 200);

        g.setColor(Color.RED);
        g.setFont(new Font("Cooper Black", Font.BOLD, 35));
        g.drawString("Press [ESC] to exit game", 250, 500);

        g.setColor(Color.WHITE);
        String scoreBoard = "Score: " + score*100;
        g.setFont(new Font("Cooper Black", Font.PLAIN, 25));
        g.drawString(scoreBoard, scorex, scorey);

    }

    private void drawLoseScreen(Graphics g) {
        g.drawImage(new ImageIcon("src/imageFiles/Lose.png").getImage(), 0, 0, this);
    }
    private void drawWinScreen(Graphics g) {
        g.drawImage(new ImageIcon("src/imageFiles/Win.png").getImage(), 0, 0, this);
    }
    private void drawStartScreen(Graphics g) {
    }

    private void doDrawing(Graphics g) {
        //Sets font color to white
        g.setColor(Color.WHITE);

        //Displays current score at top left of screen
        String scoreBoard = "Score: " + score*100;
        g.setFont(new Font("Cooper Black", Font.PLAIN, 25));
        g.drawString(scoreBoard, scorex, scorey);
        g.drawString("Level: " + level, 875, 20);

        //Draws bandicoot and exit button at start of game
        g.drawImage(bandy.bandicoot, bandy.getX(), bandy.getY(), this);
        g.drawImage(button.exitbutton, button.getX(), button.getY(), this);
        if(score >= 10){
            g.drawImage(gold.goldbug, gold.getX(), gold.getY(), this);
            gold.update();
        }

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
            /*
            * Create multiple screens (Start Screen)
            * -- Start Screen
            * -- Screen 1
            * -- Screen 2
            * -- Screen 3
            * -- ...
            * -- Game screen
            * -- End of game
            * -- -- Game over (lost)
            * -- -- You Won (winner)
            * */
            if (e.getKeyChar() == ' ' && start)
                pause = !(pause);

            if (!(start))
                start = true;
            bandy.checkForMotion(e);
            button.checkForClick(e);

            if (pause) {
                if (e.getKeyChar() == 's') {
                    if (score >= 10) {
                        score -= 10;
                        bandy.increaseSpeed();
                    }
                }
            }
        }
    }

    public void checkCollision() {
        ArrayList<Bug> toRemove1 = new ArrayList<Bug>();
        ArrayList<Rock> toRemove2 = new ArrayList<Rock>();
        //Checks for BUG collision
        Rectangle bandBound = bandy.getBounds();
        for (Bug r : bugs) {
            Rectangle bugBound = r.getBounds();
            if (bandBound.intersects(bugBound)) {
                //remove from arraylist
                score++;
                toRemove1.add(r);
            }
        }

        //checks for GOLDEN BUG collision
        Rectangle goldBound = gold.getBounds();
        if(bandBound.intersects(goldBound)){
            //YOU WIN screen!!! exit game.
            System.out.println("YOU WIN");

        }


        //checks for ROCK collision
        for(Rock r : rocks){
            Rectangle rockBound = r.getBounds();
            if(bandBound.intersects(rockBound)){
                //remove rock from arraylist
                score--; //run away, game over?
                toRemove2.add(r);
            }
        }
        for (Bug bug : toRemove1) {
            bugs.remove(bug);
        }
        for (Rock rock : toRemove2) {
            rocks.remove(rock);
        }
    }


}





