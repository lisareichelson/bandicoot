import javax.swing.*;
import java.awt.*;

public class Rock {

    private int width;
    private int height;
    //initial x & y position
    private int x = (int) (1100*Math.random());
    private int y = (int)(-1000*Math.random() - 100);
    public Image rock;

    public Rock(){
        loadPicture();
        update();
    }


    //Load file path for image, sets image size.
    private void loadPicture(){
        ImageIcon boulder = new ImageIcon("src/imageFiles/rockPic.gif");
        rock = boulder.getImage();
        width = rock.getWidth(null);
        height = rock.getHeight(null);
    }

    public void update(){
        //if rock is still above ground, fall down 3 pixels
        if(y <= 600){
            y += 5;
        }
        //no else needed, if y == 0 remove rock object from arraylist in board class.
    }

    public Image getImage(){
        return rock;
    }

    public int getX(){
        return x ;
    }

    public int getY(){
        return y;
    }


    //creates rectangle of allotted distance to rock
    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }


}
