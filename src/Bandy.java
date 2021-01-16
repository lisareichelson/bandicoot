

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Bandy {
    private int width;
    private int height;
    private int x = 50;
    private int y = 60;
    public Image bandicoot;



    public Bandy(){
        loadPicture();

    }



    //Loads bandicoot image and stores its size(which is probably too big right now)
    private void loadPicture(){
        ImageIcon bandy = new ImageIcon("src/imageFiles/Bandy.png");
        bandicoot = bandy.getImage();
        width = bandicoot.getWidth(null);
        height = bandicoot.getHeight(null);


    }

    public Image getImage(){
        return bandicoot;
    }

    public int getX(){
        return x ;
    }

    public int getY(){
        return y;
    }


    //can delete up and down if only want L/R options
    public void checkForMotion(KeyEvent key){
        int direction = key.getKeyCode();

        //if left arrow
        if(direction == KeyEvent.VK_LEFT){
            x -= 2; //Adjust based on desired movement
        }

        //if UP
        if(direction == KeyEvent.VK_UP){
            y -= 2; //Adjust based on desired movement
        }

        //if DOWN
        if(direction == KeyEvent.VK_DOWN){
            y += 2; //Adjust based on desired movement
        }

        //if RIGHT
        if(direction == KeyEvent.VK_RIGHT){
            x += 2; //Adjust based on desired movement
        }


    }




}
