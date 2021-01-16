import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class exit {
    private int width;
    private int height;
    private int x = 50;
    private int y = 60;
    public Image exitbutton;
    private String path = "src/imageFiles/exitkey.png";

    public exit(){
        loadPicture(path);
        //checkForClick();


    }

    private void loadPicture(String path){
        ImageIcon exit = new ImageIcon(path);
        exitbutton = exit.getImage();
        width = exitbutton.getWidth(null);
        height = exitbutton.getHeight(null);
    }

    private void checkForClick(KeyEvent key){
        int pressed = key.getKeyCode();

        if(pressed == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }


    }

    public Image getImage(){
        return exitbutton;
    }

    public int getX(){
        return x ;
    }

    public int getY(){
        return y;
    }


}
