import javax.swing.*;
import java.awt.*;

public class GoldenBug {
    private int width;
    private int height;
    //initial x & y position
    private int x = (int)(1100*Math.random() );
    private int y = (int)(-10000*Math.random() - 100);
    public Image goldbug;

    public GoldenBug(){
        loadPicture();
        update();
    }

    private void loadPicture(){
        ImageIcon lady = new ImageIcon("src/imageFiles/goldenladybug.png");
        goldbug = lady.getImage();
        width = goldbug.getWidth(null);
        height = goldbug.getHeight(null);
    }

    public void update(){
        //if GoldenBug is still above ground, fall down 3 pixels
        if(y <= 600){
            y += 5; // slow speed
        }
        //no else needed, if y == 0 remove GoldenBug object from arraylist in board class.
    }

    public Image getImage(){
        return goldbug;
    }

    public int getX(){
        return x ;
    }

    public int getY(){
        return y;
    }

    //boundary rectangle for collision method
    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }


}
