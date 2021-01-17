import javax.swing.*;
import java.awt.*;

public class Bug {
    private int width;
    private int height;
    //initial x & y position
    private int x = (int)(1100*Math.random() );
    //private int x = 500;
    // private int y = 300;
    private int y = (int)(-10000*Math.random() - 100);
    public Image ladybug;



    public Bug(){
        loadPicture();
        update();
    }

    private void loadPicture(){
        ImageIcon lady = new ImageIcon("src/imageFiles/ladybug.png");
        ladybug = lady.getImage();
        width = ladybug.getWidth(null);
        height = ladybug.getHeight(null);
    }

    public void update(){
        //if bug is still above ground, fall down 3 pixels
        if(y <= 600){
            y += 10;
        }
        //no else needed, if y == 0 remove bug object from arraylist in board class.
    }

    public Image getImage(){
        return ladybug;
    }

    public int getX(){
        return x ;
    }

    public int getY(){
        return y;
    }

}