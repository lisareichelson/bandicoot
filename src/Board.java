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
    public boolean muteStatus = false;
    boolean start = false;
    private AudioPlayer1 ap1 = new AudioPlayer1();
    private int scorex = 5;
    private int scorey = 20;
    private int numRocks = 0;
    private int timeDelay = 50;
    private double normMeanBug = 1.5;
    private double normMeanRock = 4.0;
    private double normMeanGB = 8.0;
    private long rockTime = System.nanoTime();
    private long bugTime = System.nanoTime();
    private long GBTime = System.nanoTime();
    private double nextBugTime = timeNextBug(normMeanBug);
    private double nextRockTime = timeNextRock(normMeanRock);
    private double nextGBTime = timeNextRock(normMeanGB);
    private int level = 0;
    private int bugCatch = 4;
    private int score = 0;
    private ArrayList<Bug> bugs = createBugArrayList();
    private ArrayList<Rock> rocks = createRockArrayList();
    private ArrayList<GoldenBug> goldens = createGBugArrayList();

    private ArrayList<Rock> createRockArrayList() {
        ArrayList<Rock> thisArrayList = new ArrayList<Rock>();
        for (int i = 0; i < 3; i++) {
            thisArrayList.add(new Rock());
        }
        return thisArrayList;
    }

    private ArrayList<GoldenBug> createGBugArrayList() {
        ArrayList<GoldenBug> thisArrayList = new ArrayList<GoldenBug>();
        for (int i = 0; i < 3; i++) {
            thisArrayList.add(new GoldenBug());
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
    private void addGB() {
        long currTime = System.nanoTime();
        if (elapsTime(GBTime, currTime) >= nextGBTime*1000000000L) {
            goldens.add(new GoldenBug());
            nextRockTime = timeNextRock(normMeanGB);
            GBTime = currTime;
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
                addGB();
                updateLevel();
                if(numRocks >= 3){
                    gameOver = true;
                }
                if (ap1.type == LineEvent.Type.STOP && !(muteStatus))
                    ap1.play("src/sounds/calmsound.wav");
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

    public void muteGame(String filepath) {
        muteStatus = !(muteStatus);
        if (muteStatus) {
            ap1.type = LineEvent.Type.STOP;
            ap1.stopSound();
        }
        else {
            ap1.play(filepath);
        }
    }
    private class AudioPlayer1 implements LineListener {
        LineEvent.Type type = LineEvent.Type.STOP;
        Clip audioClip;

        public void stopSound() {
            audioClip.stop();
            audioClip.close();
        }

        public void play(String audioPath) {
            File audioFile = new File(audioPath);
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                audioClip = (Clip) AudioSystem.getLine(info);
                audioClip.addLineListener(this);
                audioClip.open(audioStream);
                audioClip.start();
            }
            catch (Exception e) {
            }

        }
        @Override
        public void update(LineEvent event) {
            type = event.getType();
        }

    }


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
        g.drawString("Press 's'", 800, 200);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Cooper Black", Font.BOLD, 25));
        g.drawString("2000 pts  Increase lives  Current lives:     " + (3 - numRocks), 50, 230);
        g.setColor(Color.BLUE);
        g.drawString("Press 'l'", 800, 230);


        g.setColor(Color.RED);
        g.setFont(new Font("Cooper Black", Font.BOLD, 35));
        g.drawString("Press [ESC] to exit game", 250, 500);

        g.setColor(Color.CYAN);
        g.setFont(new Font("Cooper Black", Font.BOLD, 35));
        g.drawString("Press 'm' to mute/unmute", 240, 450);

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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Cooper Black", Font.BOLD, 50));
        g.drawString("Hungry at Home Bandicoot", 135, 50);

        g.setFont(new Font("Cooper Black", Font.PLAIN, 25));
        g.drawString("Press any key to begin...", 350, 80);
        g.setColor(new Color(92, 197, 194));
        g.setFont(new Font("Cooper Black", Font.PLAIN, 30));
        g.drawString("Bandy, a Golden Bandicoot, an endangered species", 100, 150);
        g.drawString("native to the savannahs of Australia, has been", 100, 180);
        g.drawString("forced to scavenge from her home due to the pandemic!!", 100, 210);
        g.drawString("Help her find her dinner before she gets tired!", 100, 240);

        g.setColor(new Color(183, 123, 249));
        g.drawString("Press left and right arrows to move", 100, 350);
        g.drawString("Press [SPACE] to pause game", 100, 380);




    }

    private void doDrawing(Graphics g) {
        g.drawImage(new ImageIcon("src/imageFiles/background2.png").getImage(), 0, 0, this);
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
        if(score >= 30){ // TODO: FIXME
            for (GoldenBug r : goldens) {
                g.drawImage(r.goldbug, r.getX(), r.getY(), this);
                r.update();
            }
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
                    if (!(muteStatus))
                        ap1.play("src/sounds/buy.wav");
                    if (score >= 10) {
                        score -= 10;

                        bandy.increaseSpeed();
                    }
                }
                if (e.getKeyChar() == 'l') {
                    if (!(muteStatus))
                        ap1.play("src/sounds/buy.wav");
                    if (score >= 20) {
                        score -= 20;
                        numRocks -= 1;
                    }
                }
                if (e.getKeyChar() == 'm') {
                    muteGame("src/sounds/calmsound.wav");
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
            r.setSpeed(level);
            Rectangle bugBound = r.getBounds();
            if (bandBound.intersects(bugBound)) {
                //remove from arraylist
                score++;
                toRemove1.add(r);
            }
        }

        //checks for GOLDEN BUG collision
        for(GoldenBug r : goldens) {
            Rectangle goldBound = r.getBounds();
            if (bandBound.intersects(goldBound)) {
                //YOU WIN screen!!! exit game.
                gameWon = true;
                gameOver = true;

            }
        }


        //checks for ROCK collision
        for(Rock r : rocks){
            r.setSpeed(level);
            Rectangle rockBound = r.getBounds();
            if(bandBound.intersects(rockBound)){
                //remove rock from arraylist
                score--; //run away, game over?
                toRemove2.add(r);
                numRocks++;
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
